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

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import fr.bmartel.protocol.http.HttpReader;
import fr.bmartel.http.mock.SocketInputstreamExceptionMock;

/**
 * Generate test for socket exception inside http reader inputstream
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpReaderSocketExceptionTest {

	@Test
	public void readLineTest() {
		/* inputstream with socket exception when reading */
		SocketInputstreamExceptionMock socket = new SocketInputstreamExceptionMock();
		InputStream inputstream = socket.getInputstream();
		HttpReader reader = new HttpReader();
		try {
			assertTrue("http inputstream socket exception",
					reader.readLine(inputstream) == null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
