package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

public class NamingTokenizer extends Tokenizer {

	private int offset = 0, bufferIndex = 0, dataLen = 0;
	private static final int MAX_WORD_LEN = 255;
	private static final int IO_BUFFER_SIZE = 4096;
	private final char[] ioBuffer = new char[IO_BUFFER_SIZE];

	public NamingTokenizer(Reader reader) {
		super(reader);
	}

	@Override
	public Token next(Token reusableToken) throws IOException {
		assert reusableToken != null;
		reusableToken.clear();
		int length = 0;
		int start = bufferIndex;
		char[] buffer = reusableToken.termBuffer();
		
		boolean lowercaseCharFound = false;
		boolean digitFound = false;
		
		while (true) {

			if (bufferIndex >= dataLen) {
				offset += dataLen;
				int incr;
				
				if (lowercaseCharFound || length == 0) {
					incr = 0;
					
				} else {
					incr = 2;
					ioBuffer[0] = ioBuffer[bufferIndex - 1];
					ioBuffer[1] = ioBuffer[bufferIndex];
					
				}
				
				dataLen = input.read(ioBuffer, incr, ioBuffer.length - incr);
				if (dataLen == -1) {
					if (length > 0)
						break;
					else
						return null;
				}
				bufferIndex = incr;
				dataLen += incr;
				
			}

			final char c = ioBuffer[bufferIndex++];
			boolean breakChar = true;
			
			if (Character.isDigit(c)) {
				
				if (digitFound || length == 0) {
					breakChar = false;
					digitFound = true;
					
				} else {
					bufferIndex--;
				}
 				
				// TODO: normalize accent, it does not index accents for now
			} else if (c >= 65 && c<= 90 || c >= 97 && c<= 122) {
				
				if (digitFound) {
					bufferIndex--;
				
				} else if (Character.isLowerCase(c)) {
				
					if (!(lowercaseCharFound || length <= 1)) {
						length--;
						bufferIndex -= 2;
						
					} else {
						lowercaseCharFound = true;
						breakChar = false;
						
					}
				
				} else if (!lowercaseCharFound) { // && uppercase
					breakChar = false;
					
				} else {
					bufferIndex--;
				}
				
			}
			
			if (!breakChar) { 
				
				if (length == 0) // start of token
					start = offset + bufferIndex - 1;
				else if (length == buffer.length)
					buffer = reusableToken.resizeTermBuffer(1 + length);

				buffer[length++] = Character.toLowerCase(c); // buffer it, normalized

				if (length == MAX_WORD_LEN) // buffer overflow!
					break;

			} else if (length > 0) {// at non-Letter w/ chars
				
				
				break; // return 'em
				
			}
			
		}

		reusableToken.setTermLength(length);
		reusableToken.setStartOffset(start);
		reusableToken.setEndOffset(start + length);
		
		return reusableToken;
		
	}
}
