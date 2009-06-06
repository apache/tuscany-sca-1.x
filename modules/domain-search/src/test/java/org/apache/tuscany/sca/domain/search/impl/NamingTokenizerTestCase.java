package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

import junit.framework.TestCase;

public class NamingTokenizerTestCase extends TestCase {

	private Token reusableToken = new Token();

	public void testDigits() throws IOException {
		NamingTokenizer tokenizer = new NamingTokenizer(new StringReader(
				"123tuscany"));

		assertNextToken("123", tokenizer);
		assertNextToken("tuscany", tokenizer);

		tokenizer.reset(new StringReader("tuscany123"));

		assertNextToken("tuscany", tokenizer);
		assertNextToken("123", tokenizer);

		tokenizer.reset(new StringReader("TUSCANY123"));

		assertNextToken("tuscany", tokenizer);
		assertNextToken("123", tokenizer);

		tokenizer.reset(new StringReader("123TUSCANY"));

		assertNextToken("123", tokenizer);
		assertNextToken("tuscany", tokenizer);

		tokenizer.reset(new StringReader("tuscany.123"));

		assertNextToken("tuscany", tokenizer);
		assertNextToken("123", tokenizer);

		tokenizer.reset(new StringReader("123.tuscany"));

		assertNextToken("123", tokenizer);
		assertNextToken("tuscany", tokenizer);

	}

	public void testUppercasedTokens() throws IOException {
		NamingTokenizer tokenizer = new NamingTokenizer(new StringReader(
				"SCATuscany"));

		assertNextToken("sca", tokenizer);
		assertNextToken("tuscany", tokenizer);

		tokenizer.reset(new StringReader("TuscanySCA"));

		assertNextToken("tuscany", tokenizer);
		assertNextToken("sca", tokenizer);

		tokenizer.reset(new StringReader("Tuscany.SCA"));

		assertNextToken("tuscany", tokenizer);
		assertNextToken("sca", tokenizer);

		tokenizer.reset(new StringReader("SCA.Tuscany"));

		assertNextToken("sca", tokenizer);
		assertNextToken("tuscany", tokenizer);

		tokenizer.reset(new StringReader("SCA"));

		assertNextToken("sca", tokenizer);

	}

	public void testRegularTokens() throws IOException {
		NamingTokenizer tokenizer = new NamingTokenizer(new StringReader(
				"scaTuscany"));

		assertNextToken("sca", tokenizer);
		assertNextToken("tuscany", tokenizer);

		tokenizer.reset(new StringReader("ScaTuscany"));

		assertNextToken("sca", tokenizer);
		assertNextToken("tuscany", tokenizer);

		tokenizer.reset(new StringReader("ScaTuscanY"));

		assertNextToken("sca", tokenizer);
		assertNextToken("tuscan", tokenizer);
		assertNextToken("y", tokenizer);

	}

	public void testSingleCharTokens() throws IOException {

		NamingTokenizer tokenizer = new NamingTokenizer(new StringReader("1"));

		assertNextToken("1", tokenizer);

		tokenizer.reset(new StringReader("a"));

		assertNextToken("a", tokenizer);

		tokenizer.reset(new StringReader("A"));

		assertNextToken("a", tokenizer);

	}

	public void testNullTokens() throws IOException {
		NamingTokenizer tokenizer = new NamingTokenizer(new StringReader("_"));
		assertNull(tokenizer.next(this.reusableToken));

		tokenizer.reset(new StringReader("."));
		assertNull(tokenizer.next(this.reusableToken));

		tokenizer.reset(new StringReader(" "));
		assertNull(tokenizer.next(this.reusableToken));

		tokenizer.reset(new StringReader(""));
		assertNull(tokenizer.next(this.reusableToken));

		tokenizer.reset(new StringReader(" )(%*%"));
		assertNull(tokenizer.next(this.reusableToken));

	}

	private void assertNextToken(String expected, Tokenizer tokenizer)
			throws IOException {
		Token token = tokenizer.next(reusableToken);

		assertNotNull(token);
		assertEquals(expected, token.term());

	}

}
