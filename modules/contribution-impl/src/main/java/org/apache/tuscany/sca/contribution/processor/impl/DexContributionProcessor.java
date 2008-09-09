package org.apache.tuscany.sca.contribution.processor.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.sca.android.DexResource;
import org.apache.tuscany.sca.contribution.processor.PackageProcessor;
import org.apache.tuscany.sca.contribution.service.ContributionException;

public class DexContributionProcessor implements PackageProcessor {

	public URL getArtifactURL(URL packageSourceURL, URI artifact)
			throws MalformedURLException {
		return new URL(artifact.toString());
	}

	public List<URI> getArtifacts(URL packageSourceURL, InputStream inputStream)
			throws ContributionException, IOException {

		ArrayList<URI> uris = new ArrayList<URI>();
		DexResource res = new DexResource(packageSourceURL);

		URI[] contentFiles = res.getContentFiles();

		for (URI uri : contentFiles) {
			String fileName = DexResource.getFile(uri.getPath());
			URL url = uri.toURL();

			if (fileName != null) {

				if (fileName.endsWith("_composite")) {

					url.openConnection();
					try {
						XMLStreamReader r = XMLInputFactory.newInstance()
								.createXMLStreamReader(url.openStream());

						while (r.hasNext()) {

							if (r.isStartElement()) {
								QName name = r.getName();

								if ("implementation.java".equals(name
										.getLocalPart())) {
									int attributeCount = r.getAttributeCount();

									for (int i = 0; i < attributeCount; i++) {

										if (r.getAttributeLocalName(i).equals(
												"class")) {
											StringBuffer sb = new StringBuffer(
													"dex://");
											sb.append(
													r.getAttributeValue(i)
															.replace('.', '/'))
													.append(".class");

											try {
												uris
														.add(new URI(sb
																.toString()));
											} catch (URISyntaxException e) {
											}

											break;

										}

									}

								}

							}

							r.next();

						}

					} catch (XMLStreamException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FactoryConfigurationError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					StringBuffer sb = new StringBuffer("dex://");
					sb.append(url.getHost()).append(url.getPath());
					sb.delete(sb.length() - 10, sb.length()).append(
							".composite");

					try {
						uris.add(new URI(sb.toString()));
					} catch (URISyntaxException e) {
						continue;
					}

				}

			}

		}

		return uris;

	}

	public String getPackageType() {
		return "application/x-dex";
	}

}
