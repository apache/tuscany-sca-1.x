package org.apache.tuscany.sca.domain.search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.tuscany.sca.domain.search.impl.Document;

public class DocumentProcessorsMap extends
		HashMap<Class<?>, List<DocumentProcessor>> implements DocumentProcessor {

	private static final long serialVersionUID = 3967390896890947159L;

	private Object documentKey;

	public void addDocumentProcessor(Class<?> clazz, DocumentProcessor processor) {
		List<DocumentProcessor> processors = get(clazz);

		if (processors == null) {
			processors = new LinkedList<DocumentProcessor>();
			put(clazz, processors);

		}

		processors.add(processor);

	}

	private void appendProcessors(LinkedList<DocumentProcessor> processorsList,
			List<DocumentProcessor> processors, Object object) {

		if (processors != null) {

			for (DocumentProcessor processor : processors) {

				if (this.documentKey == null) {
					this.documentKey = processor.getDocumentKey(object);
					
					if (processorsList == null) {
						return;
					}
					
				}

				if (processorsList != null) {
					processorsList.add(processor);
				}

			}

		}

	}

	private void findAllDocumentProcessors(
			LinkedList<DocumentProcessor> processorsList, Object object) {
		Class<?> clazz = object.getClass();
		appendProcessors(processorsList, get(clazz), object);

		while (clazz != null) {
			Class<?>[] interfaces = clazz.getInterfaces();

			for (Class<?> interfac : interfaces) {
				Class<?>[] interfaces2 = interfac.getInterfaces();
				appendProcessors(processorsList, get(interfac), object);

				for (Class<?> interface2 : interfaces2) {
					appendProcessors(processorsList, get(interface2), object);
				}

			}

			clazz = clazz.getSuperclass();
			appendProcessors(processorsList, get(clazz), object);

		}

	}

	public Document process(DocumentProcessor parentProcessor,
			DocumentMap documents, Object object, Document document, String parent) {

		LinkedList<DocumentProcessor> processorsList;

		try {

			this.documentKey = document;
			processorsList = new LinkedList<DocumentProcessor>();
			findAllDocumentProcessors(processorsList, object);

			if (document == null && this.documentKey != null) {
				document = documents.get(this.documentKey);

				if (document == null) {
					document = FAKE_DOCUMENT;
				}

			}

		} finally {
			this.documentKey = null;
		}

		for (DocumentProcessor processor : processorsList) {
			processor.process(parentProcessor, documents, object, document, parent);
		}

		return document;

	}

	public Object getDocumentKey(Object object) {
		
		try {
			findAllDocumentProcessors(null, object);
			
			return this.documentKey;
			
		} finally {
			this.documentKey = null;
		}
		
	}

}
