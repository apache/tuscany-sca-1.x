package org.apache.tuscany.sca.domain.search.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.osoa.sca.annotations.AllowsPassByReference;

public class DomainSearchImpl implements DomainSearch {

	@AllowsPassByReference
	public void contributionAdded(ContributionRepository repository,
			Contribution contribution) {
		System.out.println("contributionAdded:");
		System.out.println(repository);
		System.out.println(contribution);
		
		try {
			Directory dir = new RAMDirectory();
			Analyzer analyzer = new DomainSearchAnalyzer();
			IndexWriter indexWriter = new IndexWriter(dir, analyzer, IndexWriter.MaxFieldLength.UNLIMITED);
			DomainSearchDocumentProcessorsMap docProcessors = new DomainSearchDocumentProcessorsMap();
			DocumentMap docs = new DocumentMap();
			
			docProcessors.process(docProcessors, docs, contribution, null, "");
			
			for (Document doc : docs.values()) {
				indexWriter.addDocument(doc.createLuceneDocument());
			}
			
			indexWriter.close();
			
			final IndexSearcher searcher = new IndexSearcher(dir);
			BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
			QueryParser qp = new QueryParser("", analyzer);
			
			qp.setAllowLeadingWildcard(true);
			
			while (true) {
				System.out.print("query: ");
				String queryString = consoleReader.readLine();
				
				if (queryString.equals("exit")) {
					break;
				}
				
				try {
					Query query = qp.parse(queryString);
					
					searcher.search(query, new HitCollector() {

						@Override
						public void collect(int doc, float score) {
							try {
								org.apache.lucene.document.Document document = searcher.doc(doc);
								List<?> fields = document.getFields();
								System.out.println("---------");
								System.out.println("doc = " + doc);
								
								for (Object obj : fields) {
									Field field = (Field) obj;
									String[] values = document.getValues(field.name());
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
				
				System.out.println();
				
			}
			
			
			
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

}
