/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.osoa.sca.model;

import java.util.Map;

import commonj.sdo.Sequence;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * <p/>
 * The following features are supported:
 * <ul>
 * <li>{@link DocumentRoot#getMixed <em>Mixed</em>}</li>
 * <li>{@link DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 * <li>{@link DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 * <li>{@link DocumentRoot#getBinding <em>Binding</em>}</li>
 * <li>{@link DocumentRoot#getBindingSca <em>Binding Sca</em>}</li>
 * <li>{@link DocumentRoot#getComponentType <em>Component Type</em>}</li>
 * <li>{@link DocumentRoot#getImplementation <em>Implementation</em>}</li>
 * <li>{@link DocumentRoot#getInterface <em>Interface</em>}</li>
 * <li>{@link DocumentRoot#getInterfaceJava <em>Interface Java</em>}</li>
 * <li>{@link DocumentRoot#getInterfaceWsdl <em>Interface Wsdl</em>}</li>
 * <li>{@link DocumentRoot#getModule <em>Module</em>}</li>
 * <li>{@link DocumentRoot#getModuleFragment <em>Module Fragment</em>}</li>
 * <li>{@link DocumentRoot#getSource <em>Source</em>}</li>
 * <li>{@link DocumentRoot#getSourceEpr <em>Source Epr</em>}</li>
 * <li>{@link DocumentRoot#getSourceUri <em>Source Uri</em>}</li>
 * <li>{@link DocumentRoot#getSubsystem <em>Subsystem</em>}</li>
 * <li>{@link DocumentRoot#getTarget <em>Target</em>}</li>
 * <li>{@link DocumentRoot#getTargetEpr <em>Target Epr</em>}</li>
 * <li>{@link DocumentRoot#getTargetUri <em>Target Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot()
 */
public interface DocumentRoot {
    /**
     * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Mixed</em>' attribute list.
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     * extendedMetaData="kind='elementWildcard' name=':mixed'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_Mixed()
     */
    Sequence getMixed();

    /**
     * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
     * The key is of type {@link String},
     * and the value is of type {@link String},
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>XMLNS Prefix Map</em>' map.
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
     * extendedMetaData="kind='attribute' name='xmlns:prefix'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_XMLNSPrefixMap()
     */
    Map getXMLNSPrefixMap();

    /**
     * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
     * The key is of type {@link String},
     * and the value is of type {@link String},
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>XSI Schema Location</em>' map.
     * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
     * extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_XSISchemaLocation()
     */
    Map getXSISchemaLocation();

    /**
     * Returns the value of the '<em><b>Binding</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Binding</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Binding</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='binding' namespace='##targetNamespace'"
     * @generated
     * @see #setBinding(Binding)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_Binding()
     */
    Binding getBinding();

    /**
     * Sets the value of the '{@link DocumentRoot#getBinding <em>Binding</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Binding</em>' containment reference.
     * @generated
     * @see #getBinding()
     */
    void setBinding(Binding value);

    /**
     * Returns the value of the '<em><b>Binding Sca</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Binding Sca</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Binding Sca</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='binding.sca' namespace='##targetNamespace' affiliation='binding'"
     * @generated
     * @see #setBindingSca(SCABinding)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_BindingSca()
     */
    SCABinding getBindingSca();

    /**
     * Sets the value of the '{@link DocumentRoot#getBindingSca <em>Binding Sca</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Binding Sca</em>' containment reference.
     * @generated
     * @see #getBindingSca()
     */
    void setBindingSca(SCABinding value);

    /**
     * Returns the value of the '<em><b>Component Type</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Component Type</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Component Type</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='componentType' namespace='##targetNamespace'"
     * @generated
     * @see #setComponentType(ComponentType)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_ComponentType()
     */
    ComponentType getComponentType();

    /**
     * Sets the value of the '{@link DocumentRoot#getComponentType <em>Component Type</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Component Type</em>' containment reference.
     * @generated
     * @see #getComponentType()
     */
    void setComponentType(ComponentType value);

    /**
     * Returns the value of the '<em><b>Implementation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Implementation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Implementation</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='implementation' namespace='##targetNamespace'"
     * @generated
     * @see #setImplementation(Implementation)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_Implementation()
     */
    Implementation getImplementation();

    /**
     * Sets the value of the '{@link DocumentRoot#getImplementation <em>Implementation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Implementation</em>' containment reference.
     * @generated
     * @see #getImplementation()
     */
    void setImplementation(Implementation value);

    /**
     * Returns the value of the '<em><b>Interface</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Interface</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Interface</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='interface' namespace='##targetNamespace'"
     * @generated
     * @see #setInterface(Interface)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_Interface()
     */
    Interface getInterface();

    /**
     * Sets the value of the '{@link DocumentRoot#getInterface <em>Interface</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Interface</em>' containment reference.
     * @generated
     * @see #getInterface()
     */
    void setInterface(Interface value);

    /**
     * Returns the value of the '<em><b>Interface Java</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Interface Java</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Interface Java</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='interface.java' namespace='##targetNamespace' affiliation='interface'"
     * @generated
     * @see #setInterfaceJava(JavaInterface)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_InterfaceJava()
     */
    JavaInterface getInterfaceJava();

    /**
     * Sets the value of the '{@link DocumentRoot#getInterfaceJava <em>Interface Java</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Interface Java</em>' containment reference.
     * @generated
     * @see #getInterfaceJava()
     */
    void setInterfaceJava(JavaInterface value);

    /**
     * Returns the value of the '<em><b>Interface Wsdl</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Interface Wsdl</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Interface Wsdl</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='interface.wsdl' namespace='##targetNamespace' affiliation='interface'"
     * @generated
     * @see #setInterfaceWsdl(WSDLPortType)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_InterfaceWsdl()
     */
    WSDLPortType getInterfaceWsdl();

    /**
     * Sets the value of the '{@link DocumentRoot#getInterfaceWsdl <em>Interface Wsdl</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Interface Wsdl</em>' containment reference.
     * @generated
     * @see #getInterfaceWsdl()
     */
    void setInterfaceWsdl(WSDLPortType value);

    /**
     * Returns the value of the '<em><b>Module</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Module</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Module</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='module' namespace='##targetNamespace'"
     * @generated
     * @see #setModule(Module)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_Module()
     */
    Module getModule();

    /**
     * Sets the value of the '{@link DocumentRoot#getModule <em>Module</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Module</em>' containment reference.
     * @generated
     * @see #getModule()
     */
    void setModule(Module value);

    /**
     * Returns the value of the '<em><b>Module Fragment</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Module Fragment</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Module Fragment</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='moduleFragment' namespace='##targetNamespace'"
     * @generated
     * @see #setModuleFragment(ModuleFragment)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_ModuleFragment()
     */
    ModuleFragment getModuleFragment();

    /**
     * Sets the value of the '{@link DocumentRoot#getModuleFragment <em>Module Fragment</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Module Fragment</em>' containment reference.
     * @generated
     * @see #getModuleFragment()
     */
    void setModuleFragment(ModuleFragment value);

    /**
     * Returns the value of the '<em><b>Source</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Source</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='source' namespace='##targetNamespace'"
     * @generated
     * @see #setSource(Object)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_Source()
     */
    Object getSource();

    /**
     * Sets the value of the '{@link DocumentRoot#getSource <em>Source</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Source</em>' containment reference.
     * @generated
     * @see #getSource()
     */
    void setSource(Object value);

    /**
     * Returns the value of the '<em><b>Source Epr</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Source Epr</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Epr</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='source.epr' namespace='##targetNamespace' affiliation='source'"
     * @generated
     * @see #setSourceEpr(Object)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_SourceEpr()
     */
    Object getSourceEpr();

    /**
     * Sets the value of the '{@link DocumentRoot#getSourceEpr <em>Source Epr</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Source Epr</em>' containment reference.
     * @generated
     * @see #getSourceEpr()
     */
    void setSourceEpr(Object value);

    /**
     * Returns the value of the '<em><b>Source Uri</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Source Uri</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Uri</em>' attribute.
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='source.uri' namespace='##targetNamespace' affiliation='source'"
     * @generated
     * @see #setSourceUri(String)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_SourceUri()
     */
    String getSourceUri();

    /**
     * Sets the value of the '{@link DocumentRoot#getSourceUri <em>Source Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Source Uri</em>' attribute.
     * @generated
     * @see #getSourceUri()
     */
    void setSourceUri(String value);

    /**
     * Returns the value of the '<em><b>Subsystem</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Subsystem</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Subsystem</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='subsystem' namespace='##targetNamespace'"
     * @generated
     * @see #setSubsystem(Subsystem)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_Subsystem()
     */
    Subsystem getSubsystem();

    /**
     * Sets the value of the '{@link DocumentRoot#getSubsystem <em>Subsystem</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Subsystem</em>' containment reference.
     * @generated
     * @see #getSubsystem()
     */
    void setSubsystem(Subsystem value);

    /**
     * Returns the value of the '<em><b>Target</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Target</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='target' namespace='##targetNamespace'"
     * @generated
     * @see #setTarget(Object)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_Target()
     */
    Object getTarget();

    /**
     * Sets the value of the '{@link DocumentRoot#getTarget <em>Target</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Target</em>' containment reference.
     * @generated
     * @see #getTarget()
     */
    void setTarget(Object value);

    /**
     * Returns the value of the '<em><b>Target Epr</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Target Epr</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Epr</em>' containment reference.
     * @model containment="true" resolveProxies="false" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='target.epr' namespace='##targetNamespace' affiliation='target'"
     * @generated
     * @see #setTargetEpr(Object)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_TargetEpr()
     */
    Object getTargetEpr();

    /**
     * Sets the value of the '{@link DocumentRoot#getTargetEpr <em>Target Epr</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Target Epr</em>' containment reference.
     * @generated
     * @see #getTargetEpr()
     */
    void setTargetEpr(Object value);

    /**
     * Returns the value of the '<em><b>Target Uri</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Target Uri</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Uri</em>' attribute.
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" upper="-2" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='target.uri' namespace='##targetNamespace' affiliation='target'"
     * @generated
     * @see #setTargetUri(String)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getDocumentRoot_TargetUri()
     */
    String getTargetUri();

    /**
     * Sets the value of the '{@link org.osoa.sca.model.DocumentRoot#getTargetUri <em>Target Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target Uri</em>' attribute.
	 * @see #getTargetUri()
	 * @generated
	 */
	void setTargetUri(String value);

} // DocumentRoot
