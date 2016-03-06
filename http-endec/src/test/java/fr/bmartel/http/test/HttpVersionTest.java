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

import static org.junit.Assert.*;

import org.junit.Test;

import fr.bmartel.protocol.http.HttpVersion;

/**
 * Test class for Http version class
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpVersionTest {

	@Test
	public void httpVersionTest() throws Exception {
		String httpVersion = "HTTP/1.1 200 OK";
		assertTrue("HTTP VERSION test 1 digit",
				(new HttpVersion(httpVersion)).versionDigit1 == 1);
		assertTrue("HTTP VERSION test 2 digit",
				(new HttpVersion(httpVersion)).versionDigit2 == 1);

		httpVersion = "HTTP/2.0 200 NOT FOUND";
		assertTrue("HTTP VERSION test 1 digit",
				(new HttpVersion(httpVersion)).versionDigit1 == 2);
		assertTrue("HTTP VERSION test 2 digit",
				(new HttpVersion(httpVersion)).versionDigit2 == 0);

	}

	@Test
	public final void testToString() {
		String httpVersion = "HTTP/1.1";
		assertTrue("HTTP VERSION test HTTP/1.1 digit", (new HttpVersion(1, 1))
				.toString().equals(httpVersion));

		httpVersion = "HTTP/2.1";
		assertTrue("HTTP VERSION test HTTP/2.1 digit", (new HttpVersion(2, 1))
				.toString().equals(httpVersion));
	}
}
