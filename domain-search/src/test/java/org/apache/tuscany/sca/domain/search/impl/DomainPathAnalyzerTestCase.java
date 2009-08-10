package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

import junit.framework.TestCase;

public class DomainPathAnalyzerTestCase extends TestCase {

	private Token reusableToken = new Token();

	public void test() throws IOException {
		
		Tokenizer tokenizer = new DomainPathAnalyzer.DomainPathTokenizer(new StringReader(
				Character.toString(DomainPathAnalyzer.PATH_START) + SearchFields.CONTRIBUTION_FIELD + DomainPathAnalyzer.TYPE_SEPARATOR + "123tuscany" + DomainPathAnalyzer.PATH_START + DomainPathAnalyzer.TYPE_SEPARATOR + "SCA" +
				DomainPathAnalyzer.PATH_SEPARATOR + DomainPathAnalyzer.TYPE_SEPARATOR + "TuscanySCA" + DomainPathAnalyzer.TYPE_SEPARATOR + "321" + DomainPathAnalyzer.URI_SEPARATOR + "123"));

		assertNextToken(Character.toString(DomainPathAnalyzer.PATH_START), tokenizer);
		assertNextToken("123", tokenizer);
		assertNextToken("tuscany", tokenizer);
		assertNextToken(Character.toString(DomainPathAnalyzer.PATH_START), tokenizer);
		assertNextToken("sca", tokenizer);
		assertNextToken(Character.toString(DomainPathAnalyzer.PATH_SEPARATOR), tokenizer);
		assertNextToken("tuscany", tokenizer);
		assertNextToken("sca", tokenizer);
		assertNextToken("123", tokenizer);

	}
	
	private void assertNextToken(String expected, Tokenizer tokenizer)
			throws IOException {
		Token token = tokenizer.next(reusableToken);

		assertNotNull(token);
		assertEquals(expected, token.term());

	}

}
