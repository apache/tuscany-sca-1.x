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

package org.apache.tuscany.sdo.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.tools.common.ToolConstants;
import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.common.model.DefaultValueWriter;
import org.apache.cxf.tools.wsdlto.core.DataBindingProfile;
import org.apache.tuscany.sdo.generate.JavaGenerator;
import org.apache.tuscany.sdo.helper.HelperContextImpl;
import org.apache.tuscany.sdo.helper.XSDHelperImpl;
import org.apache.tuscany.sdo.util.DataObjectUtil;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.xsd.XSDSchema;

import commonj.sdo.helper.HelperContext;
import commonj.sdo.helper.XSDHelper;

class SDOToolingDatabinding extends JavaGenerator implements DataBindingProfile {

    private EPackage.Registry packageRegistry;
    private ExtendedMetaData extendedMetaData;
    private String packageURI;
    private Hashtable packageInfoTable;

    protected String schemaNamespace = null;
    protected String namespaceInfo = null;
    protected String generateBuiltIn = null;
    protected static GeneratedPackages generatedPackages = null;
    protected boolean allNamespaces = false;

    public void generate(ToolContext context) throws ToolException {
        String srcd = (String)context.get(ToolConstants.CFG_OUTPUTDIR);
        String classesd = (String)context.get(ToolConstants.CFG_CLASSDIR);

        // preparing the directories where files to be written.
        File srcDir;
        File classesDir;
        if (srcd == null) {
            String wsdl = (String)context.get(ToolConstants.CFG_WSDLLOCATION);
            try {
                srcd = new File(new URI(wsdl)).getAbsolutePath();
            } catch (URISyntaxException e) {
                srcd = new File(".").getAbsolutePath();
            }
        }
        srcDir = new File(srcd);
        srcDir.mkdirs();

        if (classesd == null) {
            classesDir = srcDir;
        } else {
            classesDir = new File(classesd);
            classesDir.mkdirs();
        }
    }

    public void initialize(ToolContext context) throws ToolException {
        String wsdl = (String)context.get(ToolConstants.CFG_WSDLLOCATION);
        String catalog = (String)context.get(ToolConstants.CFG_CATALOG);
        Object o = context.get(ToolConstants.CFG_BINDING);
        String bindingFiles[];
        if (o instanceof String) {
            bindingFiles = new String[] {o.toString()};
        } else {
            bindingFiles = (String[])o;
        }

        packageRegistry = new EPackageRegistryImpl(EPackage.Registry.INSTANCE);
        extendedMetaData = new BasicExtendedMetaData(packageRegistry);
        packageURI = getSchemaNamespace(xsdFileName);
        packageInfoTable = createPackageInfoTable(packageURI, schemaNamespace, javaPackage, prefix, null);

    }

    public String getType(QName qName, boolean b) {
        // im not sure of the usage of element.
        String type = "commonj.sdo.DataObject"; // since dynamic sdo always refer to
        //the same object type

        return type;
    }

    public String getWrappedElementType(QName qName, QName qName1) {
        //TODO:implement the functionality
        return null;
    }

    public DefaultValueWriter createDefaultValueWriter(QName qName, boolean b) {
        // since we dont need any sample client/server code with default values we return null
        return null;
    }

    public DefaultValueWriter createDefaultValueWriterForWrappedElement(QName qName, QName qName1) {
        // since we dont need any sample client/server code with default values we return null
        return null;
    }

    private void addSchema() {

    }

    protected void run(String[] strings) {
        // We extended the JavaGenerator class in order to make use of its utility methods
        //but we are not going to implement standalone java generator.
        // So no functionality in this method.
    }

    public static String getSchemaNamespace(String xsdFileName) {
        ResourceSet resourceSet = DataObjectUtil.createResourceSet();
        File inputFile = new File(xsdFileName).getAbsoluteFile();
        Resource model =
            resourceSet.getResource(org.eclipse.emf.common.util.URI.createURI(inputFile.toURI().toString()), true);
        XSDSchema schema = (XSDSchema)model.getContents().get(0);
        String targetNS = schema.getTargetNamespace();
        if (targetNS == null) {
            targetNS = schema.getSchemaLocation();
        }

        return targetNS;
    }

    protected static GenModel generateFromXMLSchema(String xsdFileName,
                                                    EPackage.Registry packageRegistry,
                                                    ExtendedMetaData extendedMetaData,
                                                    String targetDirectory,
                                                    Hashtable packageInfoTable,
                                                    int genOptions,
                                                    String regenerateBuiltIn,
                                                    boolean allNamespaces) {
        GenModel genModel = null;

        HelperContext hc = new HelperContextImpl(extendedMetaData, false);
        XSDHelper xsdHelper = hc.getXSDHelper();
        ((XSDHelperImpl)xsdHelper).setRedefineBuiltIn(regenerateBuiltIn);

        try {
            File inputFile = new File(xsdFileName).getAbsoluteFile();
            InputStream inputStream = new FileInputStream(inputFile);
            xsdHelper.define(inputStream, inputFile.toURI().toString());

            if (targetDirectory == null) {
                targetDirectory = new File(xsdFileName).getCanonicalFile().getParent();
            } else {
                targetDirectory = new File(targetDirectory).getCanonicalPath();
            }

            if (!packageRegistry.values().isEmpty()) {
                genModel =
                    generatePackages(packageRegistry.values(),
                                     targetDirectory,
                                     packageInfoTable,
                                     genOptions,
                                     allNamespaces);

            } else {
                System.err.println("Input schema file " + xsdFileName + " defined no metadata.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return genModel;
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

    protected class GeneratedPackages {
        private List genPackages = null;

        GeneratedPackages(GenModel genModel, ExtendedMetaData extendedMetaData) {
            List packages = genModel.getGenPackages();
            Hashtable genClasses = new Hashtable();
            for (Iterator iter = packages.iterator(); iter.hasNext();) {
                // loop through the list, once to build up the eclass to genclass mapper
                GenPackage genPackage = (GenPackage)iter.next();
                List classes = genPackage.getGenClasses();
                for (Iterator classIter = classes.iterator(); classIter.hasNext();) {
                    GenClass genClass = (GenClass)classIter.next();
                    genClasses.put(genClass.getEcoreClass(), genClass);
                }
            }
            genPackages = new ArrayList();
            for (Iterator iter = packages.iterator(); iter.hasNext();) {
                // now process the pckage list
                GenPackage genPackage = (GenPackage)iter.next();
                genPackages.add(new GeneratedPackage(genPackage, extendedMetaData, genClasses));
            }
        }

        List getPackageList() {
            return genPackages;
        }
    }

    public class GeneratedPackage {
        private String namespace;
        private Hashtable classes;

        public String getNamespace() {
            return namespace;
        }

        public List getClasses() {
            return new ArrayList(classes.values());
        }

        GeneratedPackage(GenPackage genPackage, ExtendedMetaData extendedMetaData, Hashtable eclassGenClassMap) {
            classes = new Hashtable();

            EPackage ePackage = genPackage.getEcorePackage();
            namespace = extendedMetaData.getNamespace(ePackage);

            List genClasses = genPackage.getGenClasses();
            for (Iterator iterClass = genClasses.iterator(); iterClass.hasNext();) {
                GenClass genClass = (GenClass)iterClass.next();
                if ("DocumentRoot".equals(genClass.getEcoreClass().getName())) {
                    List features = genClass.getGenFeatures();
                    for (Iterator iterFeatures = features.iterator(); iterFeatures.hasNext();) {
                        GenFeature feature = (GenFeature)iterFeatures.next();
                        addGlobalElement(feature.getEcoreFeature(), extendedMetaData, eclassGenClassMap);
                    }
                }
            }

        }

        private void addGlobalElement(EStructuralFeature eFeature,
                                      ExtendedMetaData extendedMetaData,
                                      Hashtable eclassGenClassMap) {

            String name = eFeature.getName();
            String classname = "";
            boolean anonymous = false;
            List propertyClassNames = null;

            EClassifier eClassifier = eFeature.getEType();

            if (eClassifier instanceof EClass) {
                // complex type
                EClass eClass = (EClass)eClassifier;
                GenClass genEClass = (GenClass)eclassGenClassMap.get(eClassifier);
                if (genEClass != null) {
                    classname =
                        genEClass.getGenPackage().getInterfacePackageName() + '.' + genEClass.getInterfaceName();
                    anonymous = extendedMetaData.isAnonymous(eClass);

                    // Build list of property names
                    propertyClassNames = new ArrayList();
                    List properties = eClass.getEStructuralFeatures();
                    for (Iterator iterProperties = properties.iterator(); iterProperties.hasNext();) {
                        EStructuralFeature property = (EStructuralFeature)iterProperties.next();
                        EClassifier propertyType = property.getEType();
                        if (propertyType instanceof EClass) {
                            GenClass propertyGenClass = (GenClass)eclassGenClassMap.get(propertyType);
                            if (propertyGenClass != null) {
                                String propertyClassName =
                                    propertyGenClass.getGenPackage().getInterfacePackageName() + '.'
                                        + propertyGenClass.getInterfaceName();
                                propertyClassNames.add(propertyClassName);
                            }
                        } else if (propertyType instanceof EClassifier) {
                            String propertyClassName = propertyType.getInstanceClass().getName();
                            propertyClassNames.add(propertyClassName);
                        }
                    }
                }
            } else {
                // simple type
                classname = eClassifier.getInstanceClass().getName();
            }
            classes.put(name, new PackageClassInfo(name, classname, anonymous, propertyClassNames));
        }

        public class PackageClassInfo {
            private String name;
            private String className = null;
            private boolean anonymous = false;
            private List properties = null;

            PackageClassInfo(String name, String className, boolean anonymous, List properties) {
                this.name = name;
                this.className = className;
                this.anonymous = anonymous;
                this.properties = properties;
            }

            public String getName() {
                return name;
            }

            public String getClassName() {
                return className;
            }

            public boolean getAnonymous() {
                return anonymous;
            }

            public List getProperties() {
                return properties;
            }
        }
    }

}
