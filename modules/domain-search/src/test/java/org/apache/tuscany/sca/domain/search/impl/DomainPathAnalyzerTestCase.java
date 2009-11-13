/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.Tokenizer;

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
