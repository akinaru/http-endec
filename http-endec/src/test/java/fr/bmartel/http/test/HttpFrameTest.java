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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.junit.Test;

import fr.bmartel.protocol.http.HttpFrame;
import fr.bmartel.protocol.http.HttpVersion;
import fr.bmartel.protocol.http.states.HttpStates;
import fr.bmartel.http.mock.ListOfBytesMock;

/**
 * Http request frame test class
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpFrameTest {

	@Test
	public void toStringTest() {
		String expectation = "POST /rest/help/todo HTTP/1.1\r\nheaders1:  value1\r\nheaders2:  value2\r\n\r\nkind of body\r\n";

		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("headers1", "value1");
		headers.put("headers2", "value2");

		HttpVersion version = new HttpVersion(1, 1);

		HttpFrame frame = new HttpFrame("POST", version, headers,
				"/rest/help/todo", new ListOfBytesMock("kind of body"));

		assertTrue("HTTP request frame test",
				frame.toString().equals(expectation));

		expectation = "POST /rest/help/todo HTTP/1.1\r\n\r\nkind of body\r\ntestCarriage\r\n";

		headers = new HashMap<String, String>();

		version = new HttpVersion(1, 1);

		frame = new HttpFrame("POST", version, headers, "/rest/help/todo",
				new ListOfBytesMock("kind of body\r\ntestCarriage"));

		assertTrue("HTTP request frame test",
				frame.toString().equals(expectation));

	}

	/*
	 * @Test public void parseRequestLineTest() { // badic http header response
	 * String basicHttpHeader = "HTTP/1.1 200 OK";
	 * 
	 * InputStream inputstream = new ByteArrayInputStream(
	 * basicHttpHeader.getBytes());
	 * 
	 * try { try { assertTrue( "request is OK", new
	 * HttpFrame().parseRequestLine(inputstream) == 200); } catch (IOException
	 * e) { fail("IOException"); } } catch (InterruptedException e) {
	 * fail("InterruptedException"); }
	 * 
	 * try { inputstream.close(); } catch (IOException e) { e.printStackTrace();
	 * }
	 * 
	 * // invalid http header basicHttpHeader = "invalid http request/response";
	 * inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());
	 * 
	 * try { try { assertTrue( "request is invalid", new
	 * HttpFrame().parseRequestLine(inputstream) == 400); } catch (IOException
	 * e) { fail("IOException"); } } catch (InterruptedException e) {
	 * fail("InterruptedException"); }
	 * 
	 * try { inputstream.close(); } catch (IOException e) { e.printStackTrace();
	 * }
	 * 
	 * // null inputstream inputstream = null; try { try { assertTrue(
	 * "bad request or invalid inputstream", new
	 * HttpFrame().parseRequestLine(inputstream) == 500); } catch (IOException
	 * e) { fail("IOException"); } } catch (InterruptedException e) {
	 * fail("InterruptedException"); }
	 * 
	 * // post valid request basicHttpHeader = "POST /rest/fake HTTP/1.1";
	 * inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());
	 * 
	 * HttpFrame frame = new HttpFrame();
	 * 
	 * try { try { assertTrue("request is POST and valid",
	 * frame.parseRequestLine(inputstream) == 200);
	 * assertTrue("request is POST and valid", frame.getMethod()
	 * .equals("POST")); assertTrue("request is POST and valid",
	 * frame.getUri().equals("/rest/fake")); } catch (IOException e) {
	 * fail("IOException"); } } catch (InterruptedException e) {
	 * fail("InterruptedException"); }
	 * 
	 * try { inputstream.close(); } catch (IOException e) { e.printStackTrace();
	 * }
	 * 
	 * // post invalid request basicHttpHeader = "POS1T /rest/fake HTTP/1.1";
	 * inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());
	 * 
	 * frame = new HttpFrame();
	 * 
	 * try { try { assertTrue("request is POST and invalid",
	 * frame.parseRequestLine(inputstream) == 400); } catch (IOException e) {
	 * fail("IOException"); } } catch (InterruptedException e) {
	 * fail("InterruptedException"); }
	 * 
	 * try { inputstream.close(); } catch (IOException e) { e.printStackTrace();
	 * }
	 * 
	 * // get valid request basicHttpHeader = "GET /rest/fake HTTP/1.1";
	 * inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());
	 * 
	 * frame = new HttpFrame();
	 * 
	 * try { try { assertTrue("request is GET and valid",
	 * frame.parseRequestLine(inputstream) == 200);
	 * assertTrue("request is GET and valid", frame.getMethod() .equals("GET"));
	 * assertTrue("request is GET and valid",
	 * frame.getUri().equals("/rest/fake")); } catch (IOException e) {
	 * fail("IOException"); } } catch (InterruptedException e) {
	 * fail("InterruptedException"); }
	 * 
	 * try { inputstream.close(); } catch (IOException e) { e.printStackTrace();
	 * }
	 * 
	 * // get valid request basicHttpHeader = "HTTP/1.1 /test/fake"; inputstream
	 * = new ByteArrayInputStream(basicHttpHeader.getBytes());
	 * 
	 * frame = new HttpFrame();
	 * 
	 * try { try { assertTrue("request is beginning with http version",
	 * frame.parseRequestLine(inputstream) == 200);
	 * assertTrue("request is beginning with http version", frame
	 * .getUri().equals("/test/fake")); } catch (IOException e) {
	 * fail("IOException"); } } catch (InterruptedException e) {
	 * fail("InterruptedException"); }
	 * 
	 * try { inputstream.close(); } catch (IOException e) { e.printStackTrace();
	 * }
	 * 
	 * // get valid request basicHttpHeader = "HTTP/1 /test/fake"; inputstream =
	 * new ByteArrayInputStream(basicHttpHeader.getBytes());
	 * 
	 * frame = new HttpFrame();
	 * 
	 * try { try { assertTrue("request is beginning with wrong http version",
	 * frame.parseRequestLine(inputstream) == 400); } catch (IOException e) {
	 * fail("IOException"); } } catch (InterruptedException e) {
	 * fail("InterruptedException"); }
	 * 
	 * try { inputstream.close(); } catch (IOException e) { e.printStackTrace();
	 * } }
	 */

	@Test
	public void getContentLengthTest() {
		/* test with no headers */
		HashMap<String, String> headers = new HashMap<String, String>();
		HttpFrame frame = new HttpFrame();
		frame.setHeaders(headers);
		assertTrue("test content-length : 0", frame.getContentLength() == 0);
		headers = new HashMap<String, String>();
		headers.put("content-length", "125");
		frame.setHeaders(headers);
		assertTrue("test content-length : 125", frame.getContentLength() == 125);
		headers = new HashMap<String, String>();
		headers.put("CONTENT-LENGTH", "125");
		frame.setHeaders(headers);
		assertTrue("test CONTENT-LENGTH : 125", frame.getContentLength() == 0);
		headers = new HashMap<String, String>();
		headers.put("content-length", "abcd");
		frame.setHeaders(headers);
		assertTrue("test content-length with invalid format",
				frame.getContentLength() == 0);
	}

	@Test
	public void parseBodyTest() {
		/* test body */
		String basicBody = "body ............................\r\n";
		InputStream inputstream = new ByteArrayInputStream(basicBody.getBytes());

		try {
			HttpFrame frame = new HttpFrame();
			frame.parseBody(inputstream);
			assertTrue("test basic body with no content-length", frame
					.getBody().getBytes().length == 0);
		} catch (IOException e) {
			fail("IOException");
		}
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("content-length", String.valueOf(basicBody.length() - 2));
		HttpFrame frame = new HttpFrame();
		frame.setHeaders(headers);
		inputstream = new ByteArrayInputStream(basicBody.getBytes());
		try {
			frame.parseBody(inputstream);

			assertTrue("test basic body with content-length", frame.getBody()
					.getBytes().length == (basicBody.length() - 2));
		} catch (IOException e) {
			fail("IOException");
		}

		/* string with 4089 character */
		basicBody = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111\r\n";
		headers = new HashMap<String, String>();
		headers.put("content-length", String.valueOf(basicBody.length()-2));
		frame = new HttpFrame();
		frame.setHeaders(headers);
		inputstream = new ByteArrayInputStream(basicBody.getBytes());
		try {
			frame.parseBody(inputstream);
			assertTrue(
					"test basic body with content-length of 4089",
					frame.getBody().getBytes().length == (basicBody.length()-2));
		} catch (IOException e) {
			fail("IOException");
		}

		/* string more that 4089 character */
		basicBody = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111112222222\r\n";
		headers = new HashMap<String, String>();
		headers.put("content-length", String.valueOf(basicBody.length()-2));
		frame = new HttpFrame();
		frame.setHeaders(headers);
		inputstream = new ByteArrayInputStream(basicBody.getBytes());
		try {
			frame.parseBody(inputstream);
			assertTrue("test basic body with content-length > 4089", frame
					.getBody().getBytes().length == (basicBody.length()-2));
		} catch (IOException e) {
			fail("IOException");
		}
	}

	@Test
	public void parseHttpTest() {
		/* valid post request */
		String basicHttpHeader = "POST /rest/fake HTTP/1.1\r\nheaders1: value1\r\nheaders2: value2\r\ncontent-length: 4\r\n\r\nbody\r\n";
		InputStream inputstream = new ByteArrayInputStream(
				basicHttpHeader.getBytes());

		try {
			try {
				assertTrue(
						"test valid post request",
						new HttpFrame().parseHttp(inputstream) == HttpStates.HTTP_FRAME_OK);
			} catch (InterruptedException e) {
				fail("InterruptedException");
			}
		} catch (IOException e) {
			fail("IOException");
		}
		/* invalid request */
		basicHttpHeader = "POSTIT /rest/fake HTTP/1.1\r\nheaders1: value1\r\nheaders2: value2\r\ncontent-length: 4\r\n\r\nbody\r\n";
		inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());

		try {
			try {
				assertTrue(
						"test invalid request",
						new HttpFrame().parseHttp(inputstream) == HttpStates.MALFORMED_HTTP_FRAME);
			} catch (InterruptedException e) {
				fail("InterruptedException");
			}
		} catch (IOException e) {
			fail("IOException");
		}
		/* no content length */
		basicHttpHeader = "POSTIT /rest/fake HTTP/1.1\r\nheaders1: value1\r\nheaders2: value2\r\n\r\nbody\r\n";
		inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());

		try {
			try {
				assertTrue(
						"test invalid content length (not present)",
						new HttpFrame().parseHttp(inputstream) == HttpStates.MALFORMED_HTTP_FRAME);
			} catch (InterruptedException e) {
				fail("InterruptedException");
			}
		} catch (IOException e) {
			fail("IOException");
		}

		/* invalid content length */
		basicHttpHeader = "POSTIT /rest/fake HTTP/1.1\r\nheaders1: value1\r\ncontent-length: 3\r\nheaders2: value2\r\n\r\nbody\r\n";
		inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());

		try {
			try {
				assertTrue(
						"test invalid content length",
						new HttpFrame().parseHttp(inputstream) == HttpStates.MALFORMED_HTTP_FRAME);
			} catch (InterruptedException e) {
				fail("InterruptedException");
			}
		} catch (IOException e) {
			fail("IOException");
		}

		/* no body to be read */
		basicHttpHeader = "POSTIT /rest/fake HTTP/1.1\r\nheaders1: value1\r\ncontent-length: 3\r\nheaders2: value2\r\n\r\n";
		inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());

		try {
			try {
				assertTrue(
						"no body to be read",
						new HttpFrame().parseHttp(inputstream) == HttpStates.MALFORMED_HTTP_FRAME);
			} catch (InterruptedException e) {
				fail("InterruptedException");
			}
		} catch (IOException e) {
			fail("IOException");
		}
	}

	/*
	 * @Test public void parseHeaderTest() { // test headers size String
	 * basicHttpHeader = "value1: header1\r\nvalue2: headers2"; InputStream
	 * inputstream = new ByteArrayInputStream( basicHttpHeader.getBytes());
	 * 
	 * try { assertTrue("test headers size", new
	 * HttpFrame().parseHeader(inputstream).size() == 2); } catch (IOException
	 * e) { fail("IOException"); } // test headers value inputstream = new
	 * ByteArrayInputStream(basicHttpHeader.getBytes());
	 * 
	 * try { assertTrue( "test headers value1", new
	 * HttpFrame().parseHeader(inputstream)
	 * .get("value1").toString().equals("header1")); } catch (IOException e) {
	 * fail("IOException"); } inputstream = new
	 * ByteArrayInputStream(basicHttpHeader.getBytes()); try { assertTrue(
	 * "test headers value2", new HttpFrame().parseHeader(inputstream)
	 * .get("value2").toString().equals("headers2")); } catch (IOException e) {
	 * fail("IOException"); } // test host headers basicHttpHeader =
	 * "value1: header1\r\nvalue2: headers2\r\nhost: www.google.com";
	 * inputstream = new ByteArrayInputStream(basicHttpHeader.getBytes());
	 * 
	 * HttpFrame frame = new HttpFrame(); try { frame.parseHeader(inputstream);
	 * } catch (IOException e1) { fail("IOException"); }
	 * assertTrue("test headers value2",
	 * frame.getHost().equals("www.google.com")); try { inputstream.close(); }
	 * catch (IOException e) { e.printStackTrace(); } }
	 */
}
