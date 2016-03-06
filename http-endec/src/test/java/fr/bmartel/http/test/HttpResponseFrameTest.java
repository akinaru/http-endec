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
package fr.bmartel.http.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import fr.bmartel.protocol.http.HttpResponseFrame;
import fr.bmartel.protocol.http.HttpVersion;
import fr.bmartel.protocol.http.StatusCodeObject;

/**
 * Http response frame test
 *
 * @author Bertrand Martel
 * 
 */
public class HttpResponseFrameTest {

	@Test
	public void toStringTest() {

		String expectation = "HTTP/1.1 200 OK\r\nheaders1:  value1\r\nheaders2:  value2\r\n\r\n";

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("headers1", "value1");
		headers.put("headers2", "value2");

		StatusCodeObject object = new StatusCodeObject(200, "OK");
		HttpVersion version = new HttpVersion(1, 1);

		HttpResponseFrame frame = new HttpResponseFrame(object, version,
				headers, new byte[] {});

		assertTrue("HTTP response frame test",
				frame.toString().equals(expectation));
	}
}
