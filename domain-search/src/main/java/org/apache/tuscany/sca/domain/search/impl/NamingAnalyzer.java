package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

public class NamingAnalyzer extends Analyzer {

	public TokenStream tokenStream(String fieldName, Reader reader) {
		return new NamingTokenizer(reader);
	}

	public TokenStream reusableTokenStream(String fieldName, Reader reader)
			throws IOException {
		Tokenizer tokenizer = (Tokenizer) getPreviousTokenStream();
		if (tokenizer == null) {
			tokenizer = new NamingTokenizer(reader);
			setPreviousTokenStream(tokenizer);
		} else
			tokenizer.reset(reader);
		return tokenizer;
	}

}
