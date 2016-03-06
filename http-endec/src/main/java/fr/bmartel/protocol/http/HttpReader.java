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

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * Http reader : read http frame separated by \r\n from inputstream
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpReader {

	/**
	 * <p>
	 * Read a character sequence from the inpustream. Return the character
	 * sequence as String object and null if no bytes can be read from the
	 * inputstream. Data is stored in string buffer object and retruned by this
	 * function
	 * </p>
	 * 
	 * @return The next line in the input stream or null for EOF
	 * @throws IOException
	 *             on I/O error
	 * @param in
	 *            socket inputStream
	 * @return parsed line terminated by \r\n
	 * @throws IOException
	 */
	public String readLine(InputStream in) throws IOException {
		try {
			String buffer = "";
			int bytesRead = 0;
			int i = 0;
			boolean stop = false;
			if (in != null) {
				while (stop == false && (i = in.read()) >= 0) {
					bytesRead++;
					if ('\n' == (char) i) {
						stop = true;
					} else if ('\r' != (char) i) {
						buffer = buffer + ((char) i);
					}
				}
			}
			if (bytesRead == 0) {
				return null;
			}
			return buffer;
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}
	}
}
