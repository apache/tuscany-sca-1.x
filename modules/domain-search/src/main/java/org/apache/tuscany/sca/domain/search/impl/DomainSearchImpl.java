package org.apache.tuscany.sca.domain.search.impl;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.contribution.service.ContributionRepository;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DomainSearch;
import org.apache.tuscany.sca.domain.search.Result;
import org.osoa.sca.annotations.AllowsPassByReference;
import org.osoa.sca.annotations.Scope;

@Scope("COMPOSITE")
public class DomainSearchImpl implements DomainSearch {

	private Directory dir = new RAMDirectory();

	private Analyzer analyzer = new DomainSearchAnalyzer();

	public DomainSearchImpl() {
		// empty constructor
	}

	@AllowsPassByReference
	public void contributionAdded(ContributionRepository repository,
			Contribution contribution) {
		System.out.println("contributionAdded:");
		System.out.println(repository);
		System.out.println(contribution);

		try {
			IndexWriter indexWriter = new IndexWriter(dir, analyzer,
					IndexWriter.MaxFieldLength.UNLIMITED);

			try {
				DomainSearchDocumentProcessorsMap docProcessors = new DomainSearchDocumentProcessorsMap();
				DocumentMap docs = new DocumentMap();

				try {
				docProcessors.process(docProcessors, docs, contribution, null, "");
				} catch (Exception e) {
					e.printStackTrace();
				}

				for (Document doc : docs.values()) {
					indexWriter.addDocument(doc.createLuceneDocument());
				}

			} finally {
				indexWriter.close();
			}

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

		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void contributionRemoved(ContributionRepository repository,
			Contribution contribution) {
		System.out.println("contributionRemoved:");
		System.out.println(repository);
		System.out.println(contribution);

	}

	public void contributionUpdated(ContributionRepository repository,
			Contribution oldContribution, Contribution contribution) {

		System.out.println("contributionUpdated:");
		System.out.println(repository);
		System.out.println(oldContribution);
		System.out.println(contribution);

	}

	@SuppressWarnings("deprecation")
	public Result[] parseAndSearch(String searchQuery, boolean highlight) {

		try {
			final IndexSearcher searcher = new IndexSearcher(dir);
			final DomainSearchResultProcessor resultProcessor = new DomainSearchResultProcessor(
					new DomainSearchResultFactory());

			QueryParser qp = new QueryParser("", analyzer);

			qp.setAllowLeadingWildcard(true);

			try {
				final Query query = qp.parse(searchQuery);

				searcher.search(query, new HitCollector() {

					@Override
					public void collect(int doc, float score) {
						try {
							org.apache.lucene.document.Document document = searcher
									.doc(doc);

							resultProcessor.process(document, query, null);

							List<?> fields = document.getFields();
							System.out.println("---------");
							System.out.println("doc = " + doc);

							for (Object obj : fields) {
								Field field = (Field) obj;
								String[] values = document.getValues(field
										.name());
								System.out.println(field);

								for (String value : values) {
									System.out.println("\t" + value);
								}

							}

						} catch (CorruptIndexException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});

			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}

			Result[] results = resultProcessor.getResultRoots();

			// for (Result result : results) {
			// System.out.println(result);
			// }

			System.out.println();

			return results;

		} catch (CorruptIndexException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;

	}

	public Result[] search(Query searchQuery, boolean hightlight) {
		// TODO Auto-generated method stub
		return null;
	}

}
