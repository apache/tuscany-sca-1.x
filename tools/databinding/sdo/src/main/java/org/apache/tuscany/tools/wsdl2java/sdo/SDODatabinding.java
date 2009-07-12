/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.tuscany.tools.wsdl2java.sdo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.tools.common.ToolConstants;
import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.common.model.DefaultValueWriter;
import org.apache.cxf.tools.wsdlto.core.DataBindingProfile;
import org.apache.tuscany.sdo.api.SDOUtil;
import org.apache.tuscany.sdo.generate.XSD2JavaGenerator;
import org.apache.tuscany.sdo.helper.HelperContextImpl;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;
import commonj.sdo.helper.HelperContext;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XSDHelper;

public class SDODatabinding extends XSD2JavaGenerator implements DataBindingProfile {
    private static final String DATABINDING_SDO = "sdo";
    private static final String DATABINDING_STATIC_SDO = "static-sdo";

    private TypeHelper typeHelper;
    private XSDHelper xsdHelper;

    private boolean dynamic;
    private ExtendedMetaData extendedMetaData;

    public void generate(ToolContext context) throws ToolException {
        if (dynamic) {
            // Node XSD2Java is needed for dynamic SDO
            return;
        }

        Map<String, String> ns2pkgMap = context.getNamespacePackageMap();

        String srcd = (String)context.get(ToolConstants.CFG_OUTPUTDIR);
        String pkg = context.getPackageName();

        String wsdl = (String)context.get(ToolConstants.CFG_WSDLLOCATION);

        // preparing the directories where files to be written.
        File srcDir;
        if (srcd == null) {
            try {
                srcd = new File(new URI(wsdl)).getParentFile().getAbsolutePath();
            } catch (URISyntaxException e) {
                srcd = new File(".").getAbsolutePath();
            }
        }
        srcDir = new File(srcd);
        srcDir.mkdirs();

        List<String> argList = new ArrayList<String>();
        argList.add("-targetDirectory");
        argList.add(srcDir.getAbsolutePath());

        if (pkg != null) {
            argList.add("-javaPackage");
            argList.add(pkg);
        }

        // We need to copy the wsdl to a local file if it is not
        argList.add(new File(URI.create(wsdl)).getAbsolutePath());

        String[] args = argList.toArray(new String[argList.size()]);

        try {
            processArguments(args);
            run(args);
        } catch (Exception e) {
            e.printStackTrace();
            printUsage();
        }

        HelperContext hc = new HelperContextImpl(extendedMetaData, false);
        xsdHelper = hc.getXSDHelper();
        typeHelper = hc.getTypeHelper();

    }

    protected void run(String args[]) {
        String xsdFileName = args[inputIndex];
        EPackage.Registry packageRegistry = new EPackageRegistryImpl(EPackage.Registry.INSTANCE);
        extendedMetaData = new BasicExtendedMetaData(packageRegistry);
        String packageURI = getSchemaNamespace(xsdFileName);
        Hashtable packageInfoTable =
            createPackageInfoTable(packageURI, schemaNamespace, javaPackage, prefix, namespaceInfo);
        generateFromXMLSchema(xsdFileName,
                              packageRegistry,
                              extendedMetaData,
                              targetDirectory,
                              packageInfoTable,
                              genOptions,
                              generateBuiltIn,
                              allNamespaces);
    }

    private static Hashtable createPackageInfoTable(String packageURI,
                                                    String schemaNamespace,
                                                    String javaPackage,
                                                    String prefix,
                                                    String namespaceInfo) {
        Hashtable packageInfoTable = new Hashtable();

        if (namespaceInfo != null) {
            try {
                FileReader inputFile = new FileReader(namespaceInfo);
                BufferedReader bufRead = new BufferedReader(inputFile);

                String line = bufRead.readLine();
                while (line != null) {
                    if (line.length() > 0) {
                        String[] options = line.split(";");
                        if (options.length > 1) {
                            if (options.length > 2)
                                packageInfoTable.put(options[0], new PackageInfo(options[1], options[2], options[0],
                                                                                 null));
                            else
                                packageInfoTable.put(options[0], new PackageInfo(options[1], null, options[0], null));
                        } else
                            packageInfoTable.put(options[0], new PackageInfo(null, null, options[0], null));
                    }
                    line = bufRead.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (schemaNamespace != null)
                packageInfoTable.put(schemaNamespace, new PackageInfo(javaPackage, prefix, schemaNamespace, null));
            else if (packageURI != null)
                packageInfoTable.put(packageURI, new PackageInfo(javaPackage, prefix, null, null));
        }
        return packageInfoTable;
    }

    public void initialize(ToolContext context) throws ToolException {
        String databinding = (String)context.get(ToolConstants.CFG_DATABINDING);
        if (!DATABINDING_STATIC_SDO.equalsIgnoreCase(databinding) || DATABINDING_SDO.equalsIgnoreCase(databinding)) {
            dynamic = true;
        }

        generatedPackages = null;
        String wsdl = (String)context.get(ToolConstants.CFG_WSDLLOCATION);

        if (dynamic) {
            HelperContext helperContext = SDOUtil.createHelperContext();
            xsdHelper = helperContext.getXSDHelper();
            URL location;
            try {
                location = new URL(wsdl);
                InputStream is = location.openStream();
                xsdHelper.define(is, wsdl);
            } catch (IOException e) {
                throw new ToolException(e);
            }
            this.typeHelper = helperContext.getTypeHelper();
        }
    }

    public String getType(QName qName, boolean element) {
        Type type = null;
        if (element) {
            Property property = xsdHelper.getGlobalProperty(qName.getNamespaceURI(), qName.getLocalPart(), true);
            if (property != null) {
                type = property.getType();
            }
        }
        if (type == null) {
            type = typeHelper.getType(qName.getNamespaceURI(), qName.getLocalPart());
        }
        if (type != null) {
            return getClassName(type);
        }
        return DataObject.class.getName();
    }

    private String getClassName(Type type) {
        EClassifier eClass = (EClassifier)type;
        String name = eClass.getInstanceClassName();
        if (name == null) {
            return type.getName();
        } else {
            return name;
        }
    }

    public String getWrappedElementType(QName wrapperElement, QName item) {
        Type type = null;
        Property property =
            xsdHelper.getGlobalProperty(wrapperElement.getNamespaceURI(), wrapperElement.getLocalPart(), true);
        if (property != null) {
            type = property.getType();
            Property itemProp = type.getProperty(item.getLocalPart());
            if (itemProp != null) {
                type = itemProp.getType();
            }
        }
        if (type != null) {
            return getClassName(type);
        }
        return DataObject.class.getName();
    }

    public DefaultValueWriter createDefaultValueWriter(QName qName, boolean b) {
        // since we dont need any sample client/server code with default values we return null
        return null;
    }

    public DefaultValueWriter createDefaultValueWriterForWrappedElement(QName qName, QName qName1) {
        // since we dont need any sample client/server code with default values we return null
        return null;
    }

}
