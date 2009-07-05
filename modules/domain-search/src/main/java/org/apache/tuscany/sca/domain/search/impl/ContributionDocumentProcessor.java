package org.apache.tuscany.sca.domain.search.impl;

import org.apache.lucene.document.Field;

import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.contribution.Artifact;
import org.apache.tuscany.sca.contribution.Contribution;
import org.apache.tuscany.sca.domain.search.DocumentMap;
import org.apache.tuscany.sca.domain.search.DocumentProcessor;
import org.apache.tuscany.sca.domain.search.Result;

public class ContributionDocumentProcessor implements DocumentProcessor {

	public Document process(DocumentProcessor parentProcessor,
			DocumentMap documents, Object object, Document doc, String parent) {

		if (object instanceof Contribution) {
			Contribution contribution = (Contribution) object;
			String uri = contribution.getURI();

			if (uri != null) {

				if (uri.length() == 0) {
					uri = null;

				} else {
					
					parent += DomainPathAnalyzer.PATH_SEPARATOR
							+ SearchFields.CONTRIBUTION_FIELD
							+ DomainPathAnalyzer.TYPE_SEPARATOR + uri;
					
				}

			}

			if (uri != null && doc == null) {
				doc = documents.get(uri);

				doc.add(new Field(SearchFields.CONTRIBUTION_FIELD, uri,
						Field.Store.YES, Field.Index.ANALYZED));

			} else {
				doc = FAKE_DOCUMENT;
			}

			for (Artifact artifact : contribution.getArtifacts()) {
				Document artifactDoc = parentProcessor.process(parentProcessor,
						documents, artifact, null, parent);

				if (uri != null) {

					artifactDoc.add(new Field(SearchFields.PARENT_FIELD,
							parent, Field.Store.YES, Field.Index.ANALYZED));

				}

			}

			// for (Import imprt : contribution.getImports()) {
			// Document importDoc = processors.process(processors, documents,
			// imprt, null);
			//
			// if (uri != null) {
			//
			// importDoc.add(new Field(SearchFields.IMPORTEDBY_FIELD, uri,
			// Field.Store.YES, Field.Index.ANALYZED));
			//
			// }
			//
			// }
			//
			// for (Export export : contribution.getExports()) {
			// Document exportDoc = processors.process(processors, documents,
			// export, null);
			//
			// if (uri != null) {
			//
			// exportDoc.add(new Field(SearchFields.EXPORTEDBY_FIELD, uri,
			// Field.Store.YES, Field.Index.ANALYZED));
			//
			// }
			//
			// }

			if (!object.getClass().getSimpleName().contains("Workspace")) {

				for (Composite composite : contribution.getDeployables()) {
					Document compositeDoc = parentProcessor.process(parentProcessor,
							documents, composite, null, parent);

					if (uri != null) {

						compositeDoc.add(new Field(SearchFields.PARENT_FIELD,
								parent, Field.Store.YES, Field.Index.ANALYZED));

					}

				}

			}

			return doc;

		}

		throw new IllegalArgumentException();

	}

	public Object getDocumentKey(Object object) {

		if (object instanceof Contribution) {
			Contribution contribution = (Contribution) object;
			String uri = contribution.getURI();

			if (uri != null && uri.length() == 0) {
				return null;
			}

			return uri;

		}

		throw new IllegalArgumentException();

	}

	public Result processDocument(org.apache.lucene.document.Document document,
			Result result) {
		String contributionName = document.get(SearchFields.CONTRIBUTION_FIELD);

		if (contributionName != null) {

			if (result == null) {
				result = new ContributionResult(contributionName);

			} else if (!(result instanceof ContributionResult)) {
				throw new IllegalArgumentException();
			}

			result.setName(contributionName);

		}

		return result;

	}

}
