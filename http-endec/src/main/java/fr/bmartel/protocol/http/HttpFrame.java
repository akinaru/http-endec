/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Bertrand Martel
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.bmartel.protocol.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import fr.bmartel.protocol.http.constants.HttpConstants;
import fr.bmartel.protocol.http.constants.HttpHeader;
import fr.bmartel.protocol.http.constants.HttpMethod;
import fr.bmartel.protocol.http.inter.IHttpFrame;
import fr.bmartel.protocol.http.states.HttpStates;
import fr.bmartel.protocol.http.utils.IByteList;
import fr.bmartel.protocol.http.utils.ListOfBytes;
import fr.bmartel.protocol.http.utils.StringUtils;
import fr.bmartel.protocol.socket.DataBufferConst;

/**
 * Http request frame builder and parser
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpFrame implements IHttpFrame {

	/** http request version */
	private HttpVersion httpVersion = new HttpVersion(1, 1);

	/** identify frame as a response frame */
	private boolean isResponseFrame = false;

	/** identify frame as a request frame */
	private boolean isRequestFrame = false;

	/**
	 * idnetify if http frame is chunked
	 */
	private boolean chunked = false;

	/**
	 * http reader object used to extract http request/response frame from
	 * inputstream
	 */
	private HttpReader reader = new HttpReader();

	/** http Host name */
	private String host = "";

	/** list of http headers */
	private HashMap<String, String> headers = new HashMap<String, String>();

	/** request method */
	private String method = "";

	/** request uri */
	private String uri = "";

	/** queryString for http request */
	private String queryString = "";

	/** request body content */
	private IByteList body = new ListOfBytes();

	/** http frame status code (-1 if not exists) */
	private int statusCode = -1;

	/** http frame reason phrase (empty string if not exists) */
	private String reasonPhrase = "";

	/** Default constructor used in parse */
	public HttpFrame() {
		this.reader = new HttpReader();
	}

	/**
	 * Http builder with all parameters (RFC)
	 * 
	 * @param method
	 *            request method
	 * @param httpVersion
	 *            http version
	 * @param headers
	 *            request http headers
	 * @param uri
	 *            request uri
	 * @param body
	 *            request body content
	 */
	public HttpFrame(String method, HttpVersion httpVersion, HashMap<String, String> headers, String uri, IByteList body) {
		this.httpVersion = new HttpVersion(1, 1);
		this.headers = headers;
		this.method = method;
		this.uri = uri;
		this.body = body;
	}

	/**
	 * Format request to string to be sent to outputStream (be careful NOT to
	 * insert space between header name and ":")
	 */
	public String toString() {
		String ret = "";
		if (!this.method.equals("")) {
			ret = this.method + " " + this.uri + " " + this.httpVersion.toString() + HttpConstants.HEADER_DELEMITER;
		} else {
			ret = this.uri + " " + this.httpVersion.toString() + HttpConstants.HEADER_DELEMITER;
		}

		if (!headers.containsKey(HttpHeader.CONTENT_LENGTH) && this.body.getSize() > 0) {
			headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(new String(this.body.getBytes()).length()));
		}

		Set<String> cles = this.headers.keySet();
		Iterator<String> it = cles.iterator();

		while (it.hasNext()) {
			Object cle = it.next();
			Object valeur = this.headers.get(cle);
			ret += cle.toString() + HttpConstants.HEADER_VALUE_DELIMITER + " " + valeur.toString() + HttpConstants.HEADER_DELEMITER;
		}

		ret += HttpConstants.HEADER_DELEMITER;
		try {
			ret += new String(body.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret += HttpConstants.HEADER_DELEMITER;
		return ret;
	}

	private void cleanParser() {
		httpVersion = new HttpVersion(1, 1);
		headers = new HashMap<String, String>();
		method = "";
		uri = "";
		body = new ListOfBytes();
		statusCode = -1;
		reasonPhrase = "";
		isRequestFrame = false;
		isResponseFrame = false;
	}

	public HttpStates parseHttp(InputStream in) throws IOException, InterruptedException {
		try {
			HttpStates errorCode = HttpStates.HTTP_STATE_NONE;
			cleanParser();
			synchronized (in) {
				errorCode = decodeFrame(in);

				if (errorCode == HttpStates.HTTP_FRAME_OK) {
					/* parse header */
					HttpStates headerError = parseHeader(in);

					if (headerError == HttpStates.HTTP_FRAME_OK && getHeaders().containsKey(HttpHeader.TRANSFER_ENCODING.toLowerCase())
							&& getHeaders().get(HttpHeader.TRANSFER_ENCODING.toLowerCase()).toString().equals("chunked")) {

						setChunked(true);

						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						String t;
						String ret = "";

						int init = 0;
						int numberOfChar = 0;
						boolean text = false;
						while ((t = br.readLine()) != null) {
							text = true;
							if (init == 0 || numberOfChar == 0) {
								try {
									numberOfChar = (int) Long.parseLong(t.toUpperCase(), 16);
								} catch (java.lang.NumberFormatException e) {
									break;
								}
								if (numberOfChar == 0) {
									break;
								}
								text = false;
								if (init == 0) {
									init = 1;
								}
							}
							if (text == true) {
								if (t.trim().equals("0")) {
									numberOfChar = 0;
								} else {
									ret += t;
									numberOfChar = numberOfChar - t.length();
								}
							}
						}
						br.close();

						this.body = new ListOfBytes(ret);

					} else if (headerError == HttpStates.HTTP_FRAME_OK) {
						setChunked(false);
						/* parse body request */
						return parseBody(in);
					}
					return headerError;
				}
			}
			// displayInformation();

			return errorCode;
		} catch (SocketTimeoutException e) {
			return HttpStates.SOCKET_ERROR;
		}
	}

	private void setChunked(boolean chunked) {
		this.chunked = chunked;
	}

	@Override
	public boolean isChunked() {
		return chunked;
	}

	/**
	 * Method used to parse inputstream data extracted from socket in order to
	 * retrieve method / uri / host and request body
	 * 
	 * @param inputstream
	 *            socket inputStream
	 * @return null if socket connection failed or bad request frame and String
	 *         terminating by '\r\n' if successfully parsed
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public HttpStates decodeFrame(InputStream inputstream) throws IOException, InterruptedException {

		String httpFrame = null;

		/* read data from inputstream (until no data left on inputstream) */
		/* if data is not present return exception EOF */
		httpFrame = this.reader.readLine(inputstream);

		if (httpFrame == null) {
			return HttpStates.HTTP_READING_ERROR;
		}

		if (!httpFrame.contains("HTTP")) {
			return HttpStates.MALFORMED_HTTP_FRAME;
		}

		/* identify key with delimeter space " " */
		StringTokenizer st = new StringTokenizer(httpFrame, " ");

		String firstToken = st.nextToken();

		if (firstToken.equals(HttpMethod.POST_REQUEST) || firstToken.equals(HttpMethod.GET_REQUEST) || firstToken.equals(HttpMethod.OPTIONS_REQUEST)
				|| firstToken.equals(HttpMethod.DELETE_REQUEST) || firstToken.equals(HttpMethod.PUT_REQUEST)) {

			this.method = firstToken;

			if (st.hasMoreTokens())
				this.uri = st.nextToken();
			else
				return HttpStates.MALFORMED_HTTP_FRAME;

			if (!st.hasMoreTokens())
				return HttpStates.MALFORMED_HTTP_FRAME;

			String httpVersion = st.nextToken();

			if (httpVersion.startsWith("HTTP/"))
				this.httpVersion = new HttpVersion(httpVersion);
			else
				return HttpStates.HTTP_WRONG_VERSION;

			isRequestFrame = true;
		} else if (firstToken.startsWith("HTTP/")) {

			this.httpVersion = new HttpVersion(firstToken);

			if (httpVersion.versionDigit1 == 0) {
				return HttpStates.HTTP_WRONG_VERSION;
			}
			if (!st.hasMoreTokens())
				return HttpStates.MALFORMED_HTTP_FRAME;

			String statusCodeTemp = st.nextToken();

			if (StringUtils.isInteger(statusCodeTemp)) {
				this.statusCode = Integer.parseInt(statusCodeTemp);
			} else {
				return HttpStates.MALFORMED_HTTP_FRAME;
			}

			if (st.hasMoreTokens())
				this.reasonPhrase = st.nextToken();
			else
				return HttpStates.MALFORMED_HTTP_FRAME;

			isResponseFrame = true;
		} else {
			return HttpStates.MALFORMED_HTTP_FRAME;
		}
		return HttpStates.HTTP_FRAME_OK;
	}

	/**
	 * Parse header putting all headers keys and values found into a map object
	 * 
	 * @param inputstream
	 *            socket inputstream
	 * @throws IOException
	 */
	public HttpStates parseHeader(InputStream inputstream) throws IOException {

		for (String s = this.reader.readLine(inputstream); (s != null) && (s.length() != 0); s = this.reader.readLine(inputstream)) {
			/* identify separator ":" of fields */
			int index = s.indexOf(":");

			if (index > 0) {

				/* extract the key (to the left of ":" separator) */
				String header = s.substring(0, index).trim();
				/* extract the value (to the right of ":" separator) */
				String value = s.substring(index + 1).trim();
				/* key must be set in lower case */
				String key = header.toLowerCase();
				this.headers.put(key, value);
			}
		}
		/* get host headers and set object value */
		if (this.headers.containsKey(HttpHeader.HOST.toLowerCase())) {
			this.host = this.headers.get(HttpHeader.HOST.toLowerCase()).toString();
		}
		return HttpStates.HTTP_FRAME_OK;
	}

	/**
	 * parse request body only in order to set value of requestBody String
	 * 
	 * @param inputstream
	 *            inputstream used to read data from socket object
	 * @throws IOException
	 */
	public HttpStates parseBody(InputStream inputstream) throws IOException {

		/* identify content length */
		int length = getContentLength();

		if (length > 0) {
			int numberOfBlockToWrite = length % DataBufferConst.DATA_BLOCK_SIZE_LIMIT;
			/* define number of block to write */
			int numberOfBlock = 0;
			if (numberOfBlockToWrite == 0) {
				numberOfBlock = length / DataBufferConst.DATA_BLOCK_SIZE_LIMIT;
			} else {
				numberOfBlock = (length / DataBufferConst.DATA_BLOCK_SIZE_LIMIT) + 1;
			}
			ListOfBytes list = new ListOfBytes();
			for (int i = 0; i < numberOfBlock; i++) {
				if (numberOfBlockToWrite != 0 && i == (numberOfBlock - 1)) {
					/* this is the last block to write */
					int size = length - i * DataBufferConst.DATA_BLOCK_SIZE_LIMIT;
					byte[] data = new byte[size];

					for (int j = 0; j < data.length; j++) {
						byte byteToBeRead = (byte) inputstream.read();

						data[j] = byteToBeRead;
					}

					list.add(data);

				} else {
					/* this is not the last block to write */
					byte[] data = new byte[DataBufferConst.DATA_BLOCK_SIZE_LIMIT];

					for (int j = 0; j < data.length; j++) {
						byte byteToBeRead = (byte) inputstream.read();
						data[j] = byteToBeRead;
					}

					list.add(data);
				}
			}

			if (inputstream.available() > 0) {
				if (inputstream.read() == '\r') {

					if (inputstream.read() != '\n') {
						return HttpStates.HTTP_BODY_PARSE_ERROR;
					}

				} else {
					return HttpStates.HTTP_BODY_PARSE_ERROR;
				}
			}

			this.body = list;
		} else {
			this.body = new ListOfBytes();
		}

		return HttpStates.HTTP_FRAME_OK;
	}

	/**
	 * get Content-Length value
	 * 
	 * @return http content length
	 */
	public int getContentLength() {
		if (this.headers.containsKey(HttpHeader.CONTENT_LENGTH.toLowerCase())) {
			try {
				int length = Integer.parseInt(this.headers.get(HttpHeader.CONTENT_LENGTH.toLowerCase()));
				return length;
			} catch (NumberFormatException e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

	@Override
	public HttpVersion getHttpVersion() {
		return this.httpVersion;
	}

	@Override
	public HttpReader getReader() {
		return this.reader;
	}

	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public HashMap<String, String> getHeaders() {
		return this.headers;
	}

	/**
	 * Setter for headers
	 */
	public void setHeaders(HashMap<String, String> newHeaders) {
		this.headers = newHeaders;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getUri() {
		return this.uri;
	}

	@Override
	public String getQueryString() {
		return this.queryString;
	}

	@Override
	public IByteList getBody() {
		return this.body;
	}

	@Override
	public String getReasonPhrase() {
		return reasonPhrase;
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public boolean isHttpRequestFrame() {
		return isRequestFrame;
	}

	@Override
	public boolean isHttpResponseFrame() {
		return isResponseFrame;
	}
}
