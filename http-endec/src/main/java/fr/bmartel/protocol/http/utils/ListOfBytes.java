/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Bertrand Martel
 *
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
package fr.bmartel.protocol.http.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * List of byte object defining a list of byte array items for resolution of
 * buffer issue when stocking huge byte array or huge string
 * 
 * @author Bertrand Martel
 * 
 */
public class ListOfBytes implements IByteList {

	/**
	 * number of block to read for each communication with socket
	 */
	public final static int BLOCK_SIZE = 4095;

	/** common byte array to be managed */
	private List<byte[]> classicList = new ArrayList<byte[]>();

	/** default constructor for list of bytes */
	public ListOfBytes() {
		this.classicList = new ArrayList<byte[]>();
	}

	/** construct a list of byte array from a data string */
	public ListOfBytes(String data) {
		this.classicList = new ArrayList<byte[]>();
		try {
			byte[] dataConvertedToByte = data.getBytes("UTF-8");
			/* identify content length */
			int length = dataConvertedToByte.length;
			if (length > 0) {

				int numberOfBlockToWrite = length % BLOCK_SIZE;

				/* define number of block to write */
				int numberOfBlock = 0;
				if (numberOfBlockToWrite == 0) {
					numberOfBlock = length / BLOCK_SIZE;
				} else {
					numberOfBlock = (length / BLOCK_SIZE) + 1;
				}
				for (int i = 0; i < numberOfBlock; i++) {
					if (i == (numberOfBlock - 1)) {
						/* this is the last block to write */
						int size = length - i * BLOCK_SIZE;
						byte[] data1 = new byte[size];
						System.arraycopy(dataConvertedToByte, i * BLOCK_SIZE,
								data1, 0, size);
						this.classicList.add(data1);
					} else {
						/* this is not the last block to write */
						byte[] data1 = new byte[BLOCK_SIZE];
						System.arraycopy(dataConvertedToByte, i * BLOCK_SIZE,
								data1, 0, BLOCK_SIZE);
						this.classicList.add(data1);
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return all byte for classic List
	 * 
	 * @return byte array
	 */
	public byte[] getBytes() {
		int size = 0;
		for (int i = 0; i < classicList.size(); i++) {
			size += classicList.get(i).length;
		}
		byte[] ret = new byte[size];
		int tempSize = 0;
		for (int i = 0; i < classicList.size(); i++) {
			System.arraycopy(classicList.get(i), 0, ret, tempSize,
					classicList.get(i).length);
			tempSize += classicList.get(i).length;
		}
		return ret;
	}

	@Override
	public int add(byte[] data) {
		classicList.add(data);
		return (classicList.size() - 1);
	}

	@Override
	public int getSize() {
		return classicList.size();
	}

	/**
	 * Retrieve list of byte array
	 * 
	 * @return list of byte generated from input string or byte data
	 */
	public List<byte[]> getList() {
		return this.classicList;
	}

	@Override
	public byte[] get(int index) {
		if (index > 0 && index <= this.classicList.size() - 1) {
			return this.classicList.get(index);
		} else {
			return null;
		}
	}

}
