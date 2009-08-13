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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSLockFactory;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DomainSearch;
import org.apache.tuscany.sca.domain.search.Result;
import org.osoa.sca.annotations.AllowsPassByReference;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Scope;

/**
 * 
 * @version $Rev$ $Date$
 */
@Scope("COMPOSITE")
public class DomainSearchImpl implements DomainSearch {

	@Property
	public String indexDirectoryPath;

	private Directory dir;

	private Analyzer analyzer = new DomainSearchAnalyzer();

	private MultiFieldQueryParser qp = new MultiFieldQueryParser(new String[] {
			SearchFields.ARTIFACT_FIELD, SearchFields.BINDING_FIELD,
			SearchFields.COMPONENT_FIELD, SearchFields.COMPOSITE_FIELD,
			SearchFields.CONTRIBUTION_FIELD, SearchFields.EXPORTEDBY_FIELD,
			SearchFields.FILE_CONTENT_FIELD, SearchFields.IMPLEMENTS_FIELD,
			SearchFields.IMPORTEDBY_FIELD, SearchFields.INCLUDEDBY_FIELD,
			SearchFields.PROPERTY_KEY_FIELD, SearchFields.REFERENCE_FIELD,
			SearchFields.REFERENCE_INTERFACE_CALLBACK_FIELD,
			SearchFields.REFERENCE_INTERFACE_FIELD,
			SearchFields.REFERENCE_NAME_FIELD, SearchFields.SERVICE_FIELD,
			SearchFields.SERVICE_INTERFACE_CALLBACK_FIELD,
			SearchFields.SERVICE_INTERFACE_FIELD,
			SearchFields.SERVICE_NAME_FIELD, SearchFields.TYPE_FIELD,
			SearchFields.VALUE_FIELD }, this.analyzer);

	public DomainSearchImpl() {
		this.qp.setAllowLeadingWildcard(true);

	}

	private Directory getIndexDirectory() throws IOException {

		if (this.dir == null) {

			if (this.indexDirectoryPath == null
					|| this.indexDirectoryPath.length() == 0) {
				this.dir = new RAMDirectory();

			} else {

				try {
					this.dir = new FSDirectory(
							new File(this.indexDirectoryPath),
							new SimpleFSLockFactory(this.indexDirectoryPath));

				} catch (IOException e) {
					System.err.println("Could not open index at "
							+ this.indexDirectoryPath);

					throw e;

				}

			}

		}

		return this.dir;

	}

	@AllowsPassByReference
	public void contributionAdded(Contribution contribution) {

		IndexWriter indexWriter = null;

		try {
			indexWriter = new IndexWriter(getIndexDirectory(), this.analyzer,
					IndexWriter.MaxFieldLength.UNLIMITED);

			contributionAdded(contribution, indexWriter);

			indexWriter.commit();

		} catch (Exception e) {

			if (indexWriter != null) {

				try {
					indexWriter.rollback();

				} catch (Exception e1) {
					// ignore exception
				}

			}

			throw new RuntimeException("Problem while indexing!", e);

		} finally {

			if (indexWriter != null) {

				try {
					indexWriter.close();

				} catch (Exception e) {
					// ignore exception
				}

			}

		}

	}

	@AllowsPassByReference
	public void contributionRemoved(Contribution contribution) {

		IndexWriter indexWriter = null;

		try {
			indexWriter = new IndexWriter(getIndexDirectory(), this.analyzer,
					IndexWriter.MaxFieldLength.UNLIMITED);

			contributionRemoved(contribution, indexWriter);

			indexWriter.commit();

		} catch (Exception e) {

			if (indexWriter != null) {

				try {
					indexWriter.rollback();

				} catch (Exception e1) {
					// ignore exception
				}

			}

			throw new RuntimeException("Problem while indexing!", e);

		} finally {

			if (indexWriter != null) {

				try {
					indexWriter.close();

				} catch (Exception e) {
					// ignore exception
				}

			}

		}

	}

	private void contributionRemoved(Contribution contribution,
			IndexWriter indexWriter) throws CorruptIndexException, IOException {

		String contributionURI = contribution.getURI();
		StringBuilder sb = new StringBuilder(SearchFields.PARENT_FIELD);
		sb.append(":\"");
		sb.append(DomainPathAnalyzer.PATH_START);
		sb.append(contributionURI);
		sb.append("\" OR ");
		sb.append(SearchFields.CONTRIBUTION_FIELD);
		sb.append(":\"");
		sb.append(contributionURI);
		sb.append('"');

		try {
			Query query = this.qp.parse(sb.toString());
			indexWriter.deleteDocuments(query);

		} catch (ParseException e) {
			throw new RuntimeException("Could not parse query: "
					+ sb.toString(), e);
		}

	}

	private void contributionAdded(Contribution contribution,
			IndexWriter indexWriter) throws CorruptIndexException, IOException {

		DomainSearchDocumentProcessorsMap docProcessors = new DomainSearchDocumentProcessorsMap();
		DocumentMap docs = new DocumentMap();

		try {
			docProcessors.process(docProcessors, docs, contribution, null, "");

		} catch (Exception e) {
			e.printStackTrace();
		}

		FileWriter writer = new FileWriter("indexed.txt");
		for (Document doc : docs.values()) {
			org.apache.lucene.document.Document luceneDoc = doc
					.createLuceneDocument();
			writer.write(luceneDoc.toString());
			writer.write('\n');
			writer.write('\n');
			indexWriter.addDocument(luceneDoc);

		}

		writer.close();

		// BufferedReader consoleReader = new BufferedReader(
		// new InputStreamReader(System.in));
		//
		// while (true) {
		// System.out.print("query: ");
		// String queryString = consoleReader.readLine();
		//
		// if (queryString.equals("exit")) {
		// break;
		// }
		//				
		// parseAndSearch(queryString, false);
		//
		// }

	}

	@AllowsPassByReference
	public void contributionUpdated(Contribution oldContribution,
			Contribution contribution) {

		IndexWriter indexWriter = null;

		try {
			indexWriter = new IndexWriter(getIndexDirectory(), this.analyzer,
					IndexWriter.MaxFieldLength.UNLIMITED);

			contributionRemoved(oldContribution, indexWriter);
			contributionAdded(contribution, indexWriter);

			indexWriter.commit();

		} catch (Exception e) {

			if (indexWriter != null) {

				try {
					indexWriter.rollback();

				} catch (Exception e1) {
					// ignore exception
				}

			}

			throw new RuntimeException("Problem while indexing!", e);

		} finally {

			if (indexWriter != null) {

				try {
					indexWriter.close();

				} catch (Exception e) {
					// ignore exception
				}

			}

		}

	}

	public Result[] parseAndSearch(String searchQuery, final boolean highlight)
			throws Exception {

		final IndexSearcher searcher = new IndexSearcher(getIndexDirectory());

		DomainSearchResultProcessor resultProcessor = new DomainSearchResultProcessor(
				new DomainSearchResultFactory());

		final Query query = qp.parse(searchQuery);
		System.out.println("query: " + searchQuery);

		TopDocs topDocs = searcher.search(query, 1000);

		int indexed = 0;
		HashSet<String> set = new HashSet<String>();
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			org.apache.lucene.document.Document luceneDocument = searcher
					.doc(scoreDoc.doc);

			resultProcessor.process(luceneDocument, null);

			indexed++;
			set.add(luceneDocument.toString());

			System.out.println(luceneDocument);

		}

		/*
		 * searcher.search(query, new HitCollector() {
		 * 
		 * @Override public void collect(int doc, float score) { try {
		 * org.apache.lucene.document.Document document = searcher .doc(doc);
		 * 
		 * luceneDocuments.put(doc, document);
		 * 
		 * System.out.println(doc);
		 * 
		 * } catch (CorruptIndexException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 * 
		 * });
		 */

		System.out.println("indexed = " + indexed);
		System.out.println("set.size() = " + set.size());

		Result[] results = resultProcessor.createResultRoots();

		if (highlight) {

			for (Result result : results) {
				HighlightingUtil.highlightResult(result, query);
			}

		}

		return results;

	}

	public Result[] search(Query searchQuery, boolean hightlight) {
		// TODO Auto-generated method stub
		return null;
	}

	public String highlight(String field, String text, String searchQuery)
			throws Exception {
		final Query query = qp.parse(searchQuery);

		return HighlightingUtil.highlight(field, query, text);

	}

	public boolean indexExists() {

		if ((this.indexDirectoryPath == null || this.indexDirectoryPath
				.length() == 0)
				&& this.dir == null) {

			return false;

		} else {
			return this.dir != null || IndexReader.indexExists(new File(this.indexDirectoryPath));
		}

	}

}
