package org.apache.tuscany.sca.domain.search.impl;

import javax.xml.namespace.QName;

import org.apache.lucene.document.Field;
import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;

public class CompositeDocumentProcessor implements DocumentProcessor {

	public Document process(DocumentProcessor parentProcessor,
			DocumentMap documents, Object object, Document doc, String parent) {

		if (object instanceof Composite) {
			Composite composite = (Composite) object;
			QName name = composite.getName();
			String uri = (name == null ? "" : name.getNamespaceURI() + ';'  + name.getLocalPart());
			
			if (uri.length() == 0) {
				uri = null;
				
			} else if (doc == null) {
				doc = documents.get(uri);
			}
			
			if (uri != null) {
				parent += DomainPathAnalyzer.PATH_SEPARATOR + SearchFields.COMPOSITE_FIELD + DomainPathAnalyzer.TYPE_SEPARATOR + uri;
			}
			
			for (Component component : composite.getComponents()) {
				
				Document componentDoc = parentProcessor.process(parentProcessor,
						documents, component, null, parent);

				if (uri != null) {

					componentDoc.add(new Field(SearchFields.PARENT_FIELD, parent,
							Field.Store.YES, Field.Index.ANALYZED));

				}

			}

			if (uri != null) {

				doc.add(new Field(SearchFields.COMPOSITE_FIELD, uri,
						Field.Store.YES, Field.Index.ANALYZED));

			}

			for (Composite include : composite.getIncludes()) {
				Document compositeDoc = parentProcessor.process(parentProcessor,
						documents, include, null, parent);

				if (uri != null) {

					compositeDoc.add(new Field(SearchFields.INCLUDEDBY_FIELD,
							uri, Field.Store.YES, Field.Index.ANALYZED));

				}

			}

			for (Component component : composite.getComponents()) {
				Document componentDoc = parentProcessor.process(parentProcessor,
						documents, component, null, parent);

				if (uri != null) {

					componentDoc.add(new Field(SearchFields.PARENT_FIELD, parent,
							Field.Store.YES, Field.Index.ANALYZED));

				}

			}

			return doc == null ? FAKE_DOCUMENT : doc;

		}

		throw new IllegalArgumentException();

	}

	public Object getDocumentKey(Object object) {
		
		if (object instanceof Composite) {
			Composite composite = (Composite) object;
			String uri = composite.getURI();
			QName name = composite.getName();

			uri = (uri == null ? "" : uri) + (name == null ? "" : name.toString());
			
			return uri.length() == 0 ? null : uri;
			
		}
		
		throw new IllegalArgumentException();
			
	}
	
}
