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
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 
 * @version $Rev$ $Date$
 */
public class ZipFileContent implements FileContent {

	final private ZipFile file;

	final private ZipEntry entry;

	private FileContent[] children;

	private ZipFileContent(ZipFile file, ZipEntry entry) {
		this.file = file;
		this.entry = entry;

	}

	public InputStream getInputStream() throws IOException {
		return this.file.getInputStream(this.entry);
	}

	public String getPath() {
		return this.file.getName() + DomainPathAnalyzer.ARCHIVE_SEPARATOR + '/'
				+ this.entry.getName();
	}

	public FileContent[] getChildren() {
		return this.children;
	}

	public String getName() {
		return this.entry.getName();
//		int lastSlashIndex = name.lastIndexOf('/');
//
//		if (lastSlashIndex == -1) {
//			return name;
//		}
//
//		if (lastSlashIndex == name.length() - 1 && name.length() > 1) {
//			lastSlashIndex = name.lastIndexOf('/', name.length() - 2);
//
//			if (lastSlashIndex == -1) {
//				return name.substring(0, name.length() - 1);
//			}
//
//			return name.substring(lastSlashIndex + 1, name.length() - 1);
//
//		}
//
//		return name.substring(lastSlashIndex + 1);

	}

	public boolean isLeaf() {
		return !this.entry.isDirectory();
	}

	public static ZipFileContent[] createZipFileContent(ZipFile file) {
		Enumeration<? extends ZipEntry> entries = file.entries();

		if (!entries.hasMoreElements()) {
			return null;
		}

		HashMap<String, ZipMap> roots = new HashMap<String, ZipMap>();

		do {
			ZipEntry entry = entries.nextElement();
			String name = entry.getName();

			if (name.length() > 0) {

				String[] path = name.split("/");

				ZipMap current = roots.get(path[0]);

				if (current == null) {
					current = new ZipMap();
					roots.put(path[0], current);

					if (path.length == 1) {
						current.setEntry(file, entry);
						continue;

					}

				}

				for (int i = 1; i < path.length - 1; i++) {
					ZipMap actual = current.get(path[i]);

					if (actual == null) {
						actual = new ZipMap();
						current.put(path[i], actual);

					}

					current = actual;

				}

				ZipMap entryMap = current.get(path[path.length - 1]);

				if (entryMap == null) {
					entryMap = new ZipMap();
					current.put(path[path.length - 1], entryMap);

				}

				entryMap.setEntry(file, entry);

			}

		} while (entries.hasMoreElements());

		for (ZipMap map : roots.values()) {
			createZipFileContentChildren(map);
		}

		ZipFileContent[] ret = new ZipFileContent[roots.size()];
		int i = 0;

		for (ZipMap rootMap : roots.values()) {
			ret[i++] = rootMap.zipContent;
		}

		return ret;

	}

	public static ZipFileContent createZipFileContent(ZipFile file,
			String filePath) {

		Enumeration<? extends ZipEntry> entries = file.entries();

		if (!entries.hasMoreElements()) {
			return null;
		}

		int beginIndex;
		int endIndex;

		if (filePath.charAt(0) == '/') {
			beginIndex = 1;

		} else {
			beginIndex = 0;
		}

		ZipMap root = new ZipMap();
		
		if (filePath.length() > 1
				&& filePath.charAt(filePath.length() - 1) == '/') {
			endIndex = filePath.length() - 1;

		} else {
			endIndex = filePath.length();
		}

		filePath = filePath.substring(beginIndex, endIndex);
		// HashMap<String, ZipMap> roots = new HashMap<String, ZipMap>();

		do {
			ZipEntry entry = entries.nextElement();
			String name = entry.getName();

			if (name.length() > 0) {
				
				if (name.charAt(name.length() - 1) == '/') {
					endIndex = 1;
					
				} else {
					endIndex = 0;
				}
				
				if (name.length() - endIndex == filePath.length()) {
					root.setEntry(file, entry);
					
				} else if (filePath.length() == 0 || (name.startsWith(filePath) && name.charAt(filePath.length()) == '/')) {
				
					name = name.substring(filePath.length());
					String[] path = name.split("/");

					ZipMap current = root;

					// if (current == null) {
					// current = new ZipMap();
					// roots.put(path[0], current);
					//
					// if (path.length == 1) {
					// current.setEntry(file, entry);
					// continue;
					//
					// }
					//
					// }

					if (path.length > 0) {

						int i;
						
						if (path[0].length() == 0) {
							i = 1;
							
						} else {
							i = 0;
						}
						
						for (; i < path.length - 1; i++) {
							ZipMap actual = current.get(path[i]);

							if (actual == null) {
								actual = new ZipMap();
								current.put(path[i], actual);

							}

							current = actual;

						}

						ZipMap entryMap = current.get(path[path.length - 1]);

						if (entryMap == null) {
							entryMap = new ZipMap();
							current.put(path[path.length - 1], entryMap);

						}
						
						entryMap.setEntry(file, entry);

					}

				}

			}

		} while (entries.hasMoreElements());

		createZipFileContentChildren(root);

		return root.zipContent;

	}

	private static void createZipFileContentChildren(ZipMap map) {
		ZipFileContent[] children = new ZipFileContent[map.size()];
		int i = 0;

		for (ZipMap childMap : map.values()) {

			if (childMap.zipContent == null) {
				throw new RuntimeException(
						"could not load zip file hierarchy for file: "
								+ map.zipContent.file);
			}

			children[i++] = childMap.zipContent;

			createZipFileContentChildren(childMap);

		}

		map.zipContent.children = children;

	}

	@Override
	public String toString() {
		return this.file.getName() + '/' + this.entry.getName();
	}

	private static class ZipMap extends HashMap<String, ZipMap> {

		private static final long serialVersionUID = 6514645087432837480L;

		ZipFileContent zipContent;

		void setEntry(ZipFile zipFile, ZipEntry entry) {
			this.zipContent = new ZipFileContent(zipFile, entry);
		}

		ZipFileContent[] getChildren() {

			ZipFileContent ret[] = new ZipFileContent[this.size()];
			int i = 0;

			for (ZipMap actual : this.values()) {
				ret[i++] = actual.zipContent;
			}

			return ret;

		}

	}

}
