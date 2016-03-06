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

/**
 * Http version builder and parser
 * 
 * @author Bertrand Martel
 * 
 */
public class HttpVersion {

	/** version first digit (before ".") */
	public int versionDigit1;

	/** version second digit */
	public int versionDigit2;

	/**
	 * Http version builder according to two digit parameters (RFC)
	 * 
	 * @param versionDigit1
	 * @param versionDigit2
	 */
	public HttpVersion(int versionDigit1, int versionDigit2) {
		this.versionDigit1 = versionDigit1;
		this.versionDigit2 = versionDigit2;
	}

	/**
	 * Parser for http version
	 * 
	 * @param stringToParse
	 *            http request to parse
	 */
	public HttpVersion(String stringToParse) {
		if (stringToParse.startsWith("HTTP/")
				&& stringToParse.length() > "HTTP/".length() + 2) {
			int version1 = Integer.parseInt(stringToParse.substring(
					stringToParse.indexOf('/') + 1,
					stringToParse.indexOf('/') + 2));
			int version2 = Integer.parseInt(stringToParse.substring(
					stringToParse.indexOf('/') + 3,
					stringToParse.indexOf('/') + 4));
			this.versionDigit1 = version1;
			this.versionDigit2 = version2;
		}
	}

	/**
	 * format http version according to RFC
	 */
	public String toString() {
		return "HTTP/" + this.versionDigit1 + "." + this.versionDigit2;
	}
}
