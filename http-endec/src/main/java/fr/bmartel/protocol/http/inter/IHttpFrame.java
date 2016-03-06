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
package fr.bmartel.protocol.http.inter;

import java.util.HashMap;

import fr.bmartel.protocol.http.HttpReader;
import fr.bmartel.protocol.http.HttpVersion;
import fr.bmartel.protocol.http.utils.IByteList;

/**
 * Interface for http request frame template
 * 
 * @author Bertrand Martel
 * 
 */
public interface IHttpFrame {

	/** http request version */
	public HttpVersion getHttpVersion();

	/**
	 * Retrieve reason phrase of http frame (empty string if not exists)
	 *
	 */
	public String getReasonPhrase();

	/**
	 * Retrieve status code of http frame (-1 if not exists)
	 *
	 */
	public int getStatusCode();

	/**
	 * http reader permitting to read and parse http frame on inputstream
	 * channel
	 */
	public HttpReader getReader();

	/** http Host name */
	public String getHost();

	/** list of http headers */
	public HashMap<String, String> getHeaders();

	/** request method */
	public String getMethod();

	/** request uri */
	public String getUri();

	/** queryString for http request */
	public String getQueryString();

	/** request body content */
	public IByteList getBody();

	/** identify the frame as a request frame */
	public boolean isHttpRequestFrame();

	/** identify the frame as a response frame */
	public boolean isHttpResponseFrame();

	/** identify if the frame has chunked body or nto */
	public boolean isChunked();
}
