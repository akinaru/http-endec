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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import fr.bmartel.protocol.http.constants.HttpConstants;
import fr.bmartel.protocol.http.constants.HttpHeader;
import fr.bmartel.protocol.http.inter.IHttpResponseFrame;

/**
 * HTTP response frame builder/parser
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpResponseFrame implements IHttpResponseFrame {

	/** return code for response frame */
	private StatusCodeObject returnCode;

	/** http version for response frame */
	private HttpVersion httpVersion;

	/** headers for response frame */
	private HashMap<String, String> headers = new HashMap<String, String>();

	/** body for http response frame */
	private byte[] body = new byte[] {};

	/**
	 * Builder for http response frame
	 * 
	 * @param returnCode
	 *            status return code
	 * @param httpVersion
	 *            http version
	 * @param headers
	 *            hashmap of all headers
	 */
	public HttpResponseFrame(StatusCodeObject returnCode,
			HttpVersion httpVersion, HashMap<String, String> headers,
			byte[] body) {
		this.returnCode = returnCode;
		this.httpVersion = httpVersion;
		this.headers = headers;
		this.body = body;
	}

	/**
	 * Format request to string to be sent to outputStream (be careful NOT to
	 * insert space between header name and ":")
	 */
	public String toString() {
		String ret = this.httpVersion.toString() + " "
				+ this.returnCode.toString() + HttpConstants.HEADER_DELEMITER;

		if (!headers.containsKey(HttpHeader.CONTENT_LENGTH)
				&& this.body.length > 0) {
			headers.put(HttpHeader.CONTENT_LENGTH,
					String.valueOf(this.body.length));
		}

		Set<String> cles = this.headers.keySet();
		Iterator<String> it = cles.iterator();
		while (it.hasNext()) {
			Object cle = it.next();
			Object valeur = this.headers.get(cle);
			ret += cle.toString() + HttpConstants.HEADER_VALUE_DELIMITER + " "
					+ valeur.toString() + HttpConstants.HEADER_DELEMITER;
		}
		if (body.length > 0) {
			ret += HttpConstants.HEADER_DELEMITER;
			try {
				ret += new String(body, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ret += HttpConstants.HEADER_DELEMITER;
		} else {
			ret += HttpConstants.HEADER_DELEMITER;
		}
		return ret;
	}

	@Override
	public StatusCodeObject getReturnCode() {
		return this.returnCode;
	}

	@Override
	public HttpVersion getHttpVersion() {
		return this.httpVersion;
	}

	@Override
	public HashMap<String, String> getHeaders() {
		return this.headers;
	}

	public byte[] getBody() {
		return body;
	}
}
