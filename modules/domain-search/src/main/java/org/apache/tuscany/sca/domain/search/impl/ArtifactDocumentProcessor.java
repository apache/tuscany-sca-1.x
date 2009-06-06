package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;
import org.apache.tuscany.sca.domain.search.DocumentProcessorsMap;

public class ArtifactDocumentProcessor implements DocumentProcessor {

	public Document process(DocumentProcessorsMap processors,
			DocumentMap documents, Object object, Document document, String parent) {

		if (object instanceof Artifact) {
			Artifact artifact = (Artifact) object;
			String uri = artifact.getURI();
			
			if (uri != null && uri.length() == 0) {
				uri = null;
			}
			
			if (uri != null) {
				
				parent += DomainPathAnalyzer.PATH_SEPARATOR + SearchFields.ARTIFACT_FIELD + DomainPathAnalyzer.TYPE_SEPARATOR + uri;
				
				if (document == null) {
					document = documents.get(uri);
				}
				
				document.add(new Field(SearchFields.ARTIFACT_FIELD, uri,
						Field.Store.YES, Field.Index.ANALYZED));
				
			}
			
			if (document != null) {
				
				document.add(new Field(SearchFields.LOCATION_FIELD, artifact.getLocation(),
						Field.Store.YES, Field.Index.ANALYZED));
				
			}
			
			return document == null ? FAKE_DOCUMENT : document;

		}

		throw new IllegalArgumentException();

	}

	public Object getDocumentKey(Object obj) {
		
		if (obj instanceof Artifact) {
			Artifact artifact = (Artifact) obj;
			String uri = artifact.getURI();
			
			if (uri != null && uri.length() == 0) {
				return null;
			}
			
			return uri;
			
		}
		
		throw new IllegalArgumentException();
		
	}

}
