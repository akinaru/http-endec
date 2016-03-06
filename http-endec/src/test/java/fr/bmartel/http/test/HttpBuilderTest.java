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
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.junit.Test;

import fr.bmartel.protocol.http.HttpBuilder;
import fr.bmartel.protocol.http.constants.StatusCodeList;
import fr.bmartel.http.mock.ListOfBytesMock;

/**
 * Http builder test class
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpBuilderTest {

	@Test
	public void httpRequestTest() {
		/* post request */
		String expectation = "POST /rest/help/todo HTTP/1.1\r\nheaders1:  value1\r\nheaders2:  value2\r\nContent-Length:  15\r\n\r\nbodyTobeWritten\r\n";

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("headers1", "value1");
		headers.put("headers2", "value2");

		try {
			assertTrue(
					"HTTP request frame test POST",
					HttpBuilder.httpRequest("POST", "/rest/help/todo",
							new ListOfBytesMock("bodyTobeWritten"), headers).equals(expectation));
		} catch (UnsupportedEncodingException e) {
			fail("UnsupportedEncodingException");
		}
		/* get request */
		expectation = "GET /rest/help/todo HTTP/1.1\r\nheaders1:  value1\r\nheaders2:  value2\r\nContent-Length:  15\r\n\r\nbodyTobeWritten\r\n";

		headers = new HashMap<String, String>();
		headers.put("headers1", "value1");
		headers.put("headers2", "value2");

		try {
			assertTrue(
					"HTTP request frame test GET",
					HttpBuilder.httpRequest("GET", "/rest/help/todo",
							new ListOfBytesMock("bodyTobeWritten"), headers).equals(expectation));
		} catch (UnsupportedEncodingException e) {
			fail("UnsupportedEncodingException");
		}

		/* http response OK test */
		expectation = "200 OK HTTP/1.1\r\nheaders1:  value1\r\nheaders2:  value2\r\n\r\n\r\n";

		headers = new HashMap<String, String>();
		headers.put("headers1", "value1");
		headers.put("headers2", "value2");

		try {
			assertTrue(
					"HTTP request frame test GET",
					HttpBuilder.httpRequest("", StatusCodeList.OK.toString(),
							new ListOfBytesMock(""), headers).equals(
							expectation));
		} catch (UnsupportedEncodingException e) {
			fail("UnsupportedEncodingException");
		}
	}
}
