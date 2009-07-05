package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.assembly.Binding;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

public class BindingDocumentProcessor implements DocumentProcessor {

	public Document process(DocumentProcessor parentProcessor,
			DocumentMap documents, Object object, Document document, String parent) {

		if (object instanceof Binding) {
			Binding binding = (Binding) object;
			String uri = binding.getURI();
			
			if (uri != null && uri.length() == 0) {
				uri = null;
			}

			if (uri != null) {
				
				if (document == null) {
					document = documents.get(uri);
				}

				document.add(new Field(SearchFields.BINDING_FIELD, uri,
						Field.Store.YES, Field.Index.ANALYZED));

			}
			
			return document == null ? FAKE_DOCUMENT : document;

		}

		throw new IllegalArgumentException();

	}

	public Object getDocumentKey(Object obj) {

		if (obj instanceof Binding) {
			Binding binding = (Binding) obj;
			String uri = binding.getURI();
			
			if (uri != null && uri.length() == 0) {
				return null;
			}

			return uri;

		}

		throw new IllegalArgumentException();

	}
	
}
