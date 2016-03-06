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

import fr.bmartel.protocol.http.constants.HttpHeader;
import fr.bmartel.protocol.http.utils.IByteList;

/**
 * Http request builder
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpBuilder {

	/**
	 * Send an httpRequest with content length dynamic
	 * 
	 * @param method
	 *            request method
	 * @param uri
	 *            request uri
	 * @param content
	 *            request body content
	 * @param headers
	 *            hashmap containing all http headers for this request
	 *            (content-length will be added if not here and only if body
	 *            length not 0)
	 * @return request in String format
	 * @throws UnsupportedEncodingException
	 */
	public static String httpRequest(String method, String uri, IByteList content,
			HashMap<String, String> headers)
			throws UnsupportedEncodingException {
		HttpVersion version = new HttpVersion(1, 1);
		if (!headers.containsKey(HttpHeader.CONTENT_LENGTH)
				&& content.getBytes().length != 0) {
			headers.put(HttpHeader.CONTENT_LENGTH,
					String.valueOf(content.getBytes().length));
		}
		HttpFrame request = new HttpFrame(method, version,
				headers, uri, content );
		return request.toString();
	}
}
