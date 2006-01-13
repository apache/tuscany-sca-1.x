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

import java.util.List;

import commonj.sdo.Sequence;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Service</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * <p/>
 * The following features are supported:
 * <ul>
 * <li>{@link ExternalService#getInterfaceGroup <em>Interface Group</em>}</li>
 * <li>{@link ExternalService#getInterface <em>Interface</em>}</li>
 * <li>{@link ExternalService#getBindingsGroup <em>Bindings Group</em>}</li>
 * <li>{@link ExternalService#getBindings <em>Bindings</em>}</li>
 * <li>{@link ExternalService#getAny <em>Any</em>}</li>
 * <li>{@link ExternalService#getName <em>Name</em>}</li>
 * <li>{@link ExternalService#getOverridable <em>Overridable</em>}</li>
 * <li>{@link ExternalService#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @model extendedMetaData="name='ExternalService' kind='elementOnly'"
 * @generated
 * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getExternalService()
 */
public interface ExternalService {
    /**
     * Returns the value of the '<em><b>Interface Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Interface Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Interface Group</em>' attribute list.
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" required="true"
     * extendedMetaData="kind='group' name='interface:group' namespace='##targetNamespace'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getExternalService_InterfaceGroup()
     */
    Sequence getInterfaceGroup();

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
     * @model containment="true" resolveProxies="false" required="true" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='interface' namespace='##targetNamespace' group='interface:group'"
     * @generated
     * @see #setInterface(Interface)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getExternalService_Interface()
     */
    Interface getInterface();

    /**
     * Sets the value of the '{@link ExternalService#getInterface <em>Interface</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Interface</em>' containment reference.
     * @generated
     * @see #getInterface()
     */
    void setInterface(Interface value);

    /**
     * Returns the value of the '<em><b>Bindings Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Bindings Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Bindings Group</em>' attribute list.
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     * extendedMetaData="kind='group' name='binding:group' namespace='##targetNamespace'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getExternalService_BindingsGroup()
     */
    Sequence getBindingsGroup();

    /**
     * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
     * The list contents are of type {@link Binding}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Bindings</em>' containment reference list.
     * @model type="org.apache.tuscany.model.assembly.binding.Binding" containment="true" resolveProxies="false" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='binding' namespace='##targetNamespace' group='binding:group'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getExternalService_Bindings()
     */
    List getBindings();

    /**
     * Returns the value of the '<em><b>Any</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Any</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Any</em>' attribute list.
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     * extendedMetaData="kind='elementWildcard' wildcards='##other' name=':4' processing='lax'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getExternalService_Any()
     */
    Sequence getAny();

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NCName" required="true"
     * extendedMetaData="kind='attribute' name='name'"
     * @generated
     * @see #setName(String)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getExternalService_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link ExternalService#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Name</em>' attribute.
     * @generated
     * @see #getName()
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Any Attribute</em>' attribute list.
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getExternalService_AnyAttribute()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='attributeWildcard' wildcards='##any' name=':7' processing='lax'"
     * @generated
     */
    Sequence getAnyAttribute();

} // ExternalService
