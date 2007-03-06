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

package org.apache.tuscany.core.services.deployment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.core.util.FileHelper;
import org.apache.tuscany.core.util.IOHelper;
import org.apache.tuscany.spi.deployer.ContributionRepository;
import org.osoa.sca.annotations.Constructor;
import org.osoa.sca.annotations.Destroy;
import org.osoa.sca.annotations.EagerInit;
import org.osoa.sca.annotations.Init;
import org.osoa.sca.annotations.Property;

/**
 * The default implementation of ContributionRepository
 * 
 * @version $Rev$ $Date$
 */
@EagerInit
public class ContributionRepositoryImpl implements ContributionRepository {
    protected final File rootFile;
    protected Map<URI, URL> contributionMap = new HashMap<URI, URL>();

    private XMLInputFactory factory;

    /**
     * Constructor with repository root
     * 
     * @param repository
     */
    @Constructor
    public ContributionRepositoryImpl(@Property(name = "repository")
                                      final String repository) throws IOException {
        String root = repository;
        if (repository == null) {
            root = AccessController.doPrivileged(new PrivilegedAction<String>() {
                public String run() {
                    // Default to <user.home>/tuscany/domains/local/
                    String userHome = System.getProperty("user.home");
                    String slash = File.separator;
                    return userHome + slash + "tuscany" + slash + "domains" + slash + "local" + slash;
                }
            });
        }
        this.rootFile = new File(root);
        FileHelper.forceMkdir(rootFile);
        if (!rootFile.exists() || !rootFile.isDirectory() || !rootFile.canRead()) {
            throw new IOException("The root is not a directory: " + repository);
        }
        factory = XMLInputFactory.newInstance("javax.xml.stream.XMLInputFactory", getClass().getClassLoader());
    }

    public URI getDomain() {
        return rootFile.toURI();
    }

    /**
     * Resolve contribution location in the repository -> root repository /
     * contribution file -> contribution group id / artifact id / version
     * 
     * @param contribution
     * @return
     */
    private File mapToFile(URI contribution) {
        // FIXME: Map the contribution URI to a file?
        return new File(rootFile, contribution.getPath());
    }

    /**
     * Write a specific source inputstream to a file on disk
     * 
     * @param source contents of the file to be written to disk
     * @param target file to be written
     * @throws IOException
     */
    public static void copy(InputStream source, File target) throws IOException {
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            out = new BufferedOutputStream(new FileOutputStream(target));
            in = new BufferedInputStream(source);
            IOHelper.copy(in, out);
        } finally {
            IOHelper.closeQuietly(out);
            IOHelper.closeQuietly(in);
        }
    }

    public URL store(URI contribution, InputStream contributionStream) throws IOException {
        // where the file should be stored in the repository
        File location = mapToFile(contribution);
        FileHelper.forceMkdir(location.getParentFile());
        
        copy(contributionStream, location);

        // add contribution to repositoryContent
        URL contributionURL = location.toURL();
        contributionMap.put(contribution, contributionURL);
        saveMap();

        return contributionURL;
    }

    public URL store(URI contribution, URL sourceURL) throws IOException {
        // where the file should be stored in the repository
        File location = mapToFile(contribution);
        File source = FileHelper.toFile(sourceURL);
        if (source == null || source.isFile()) {
            InputStream is = sourceURL.openStream();
            try {
                return store(contribution, is);
            } finally {
                IOHelper.closeQuietly(is);
            }
        }

        FileHelper.forceMkdir(location);
        FileHelper.copyDirectory(source, location);

        // add contribution to repositoryContent
        URL contributionURL = location.toURL();
        contributionMap.put(contribution, contributionURL);
        saveMap();

        return contributionURL;
    }

    public URL find(URI contribution) {
        if (contribution == null) {
            return null;
        }
        return this.contributionMap.get(contribution);
    }

    public void remove(URI contribution) {
        URL contributionURL = this.find(contribution);
        if (contributionURL != null) {
            // remove
            try {
                FileHelper.forceDelete(FileHelper.toFile(contributionURL));
                this.contributionMap.remove(contribution);
                saveMap();
            } catch (IOException ioe) {
                // handle file could not be removed
            }
        }
    }

    public List<URI> list() {
        return new ArrayList<URI>(contributionMap.keySet());
    }

    @Init
    public void init() {
        File domainFile = new File(rootFile, "sca-domain.xml");
        if (!domainFile.isFile()) {
            return;
        }
        FileInputStream is;
        try {
            is = new FileInputStream(domainFile);
        } catch (FileNotFoundException e) {
            return;
        }
        try {
            XMLStreamReader reader = factory.createXMLStreamReader(new InputStreamReader(is, "UTF-8"));
            while (reader.hasNext()) {
                int event = reader.getEventType();
                if (event == XMLStreamConstants.START_ELEMENT 
                    && reader.getName().getLocalPart().equals("contribution")) {
                    String uri = reader.getAttributeValue(null, "uri");
                    String url = reader.getAttributeValue(null, "url");
                    contributionMap.put(URI.create(uri), new URL(url));
                }
                reader.next();
            }
        } catch (Exception e) {
            // Ignore
        } finally {
            IOHelper.closeQuietly(is);
        }
    }

    private void saveMap() {
        File domainFile = new File(rootFile, "sca-domain.xml");
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(domainFile);
            Writer writer = new OutputStreamWriter(os, "UTF-8");
            writer.write("<?xml version='1.0' encoding='UTF-8'?>\n");
            writer.write("<contributions>\n");
            for (Map.Entry<URI, URL> e : contributionMap.entrySet()) {
                writer.write("<contribution uri='" + e.getKey() + "' url='" + e.getValue() + "'/>\n");
            }
            writer.write("</contributions>\n");
            writer.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            IOHelper.closeQuietly(os);
        }
    }

    @Destroy
    public void destroy() {
    }

}
