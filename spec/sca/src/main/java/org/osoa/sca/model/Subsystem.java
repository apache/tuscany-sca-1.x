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
 * A representation of the model object '<em><b>Subsystem</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * <p/>
 * The following features are supported:
 * <ul>
 * <li>{@link Subsystem#getEntryPoints <em>Entry Points</em>}</li>
 * <li>{@link Subsystem#getModuleComponents <em>Module Components</em>}</li>
 * <li>{@link Subsystem#getExternalServices <em>External Services</em>}</li>
 * <li>{@link Subsystem#getWires <em>Wires</em>}</li>
 * <li>{@link Subsystem#getAny <em>Any</em>}</li>
 * <li>{@link Subsystem#getName <em>Name</em>}</li>
 * <li>{@link Subsystem#getUri <em>Uri</em>}</li>
 * <li>{@link Subsystem#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @model extendedMetaData="name='Subsystem' kind='elementOnly'"
 * @generated
 * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem()
 */
public interface Subsystem {
    /**
     * Returns the value of the '<em><b>Entry Points</b></em>' containment reference list.
     * The list contents are of type {@link EntryPoint}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Entry Points</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Entry Points</em>' containment reference list.
     * @model type="org.apache.tuscany.model.assembly.binding.EntryPoint" containment="true" resolveProxies="false"
     * extendedMetaData="kind='element' name='entryPoint' namespace='##targetNamespace'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem_EntryPoints()
     */
    List getEntryPoints();

    /**
     * Returns the value of the '<em><b>Module Components</b></em>' containment reference list.
     * The list contents are of type {@link ModuleComponent}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Module Components</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Module Components</em>' containment reference list.
     * @model type="org.apache.tuscany.model.assembly.binding.ModuleComponent" containment="true" resolveProxies="false"
     * extendedMetaData="kind='element' name='moduleComponent' namespace='##targetNamespace'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem_ModuleComponents()
     */
    List getModuleComponents();

    /**
     * Returns the value of the '<em><b>External Services</b></em>' containment reference list.
     * The list contents are of type {@link ExternalService}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>External Services</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>External Services</em>' containment reference list.
     * @model type="org.apache.tuscany.model.assembly.binding.ExternalService" containment="true" resolveProxies="false"
     * extendedMetaData="kind='element' name='externalService' namespace='##targetNamespace'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem_ExternalServices()
     */
    List getExternalServices();

    /**
     * Returns the value of the '<em><b>Wires</b></em>' containment reference list.
     * The list contents are of type {@link SystemWire}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Wires</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Wires</em>' containment reference list.
     * @model type="org.apache.tuscany.model.assembly.binding.SystemWire" containment="true" resolveProxies="false"
     * extendedMetaData="kind='element' name='wire' namespace='##targetNamespace'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem_Wires()
     */
    List getWires();

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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem_Any()
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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link Subsystem#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Name</em>' attribute.
     * @generated
     * @see #getName()
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Uri</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Uri</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Uri</em>' attribute.
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
     * extendedMetaData="kind='attribute' name='uri'"
     * @generated
     * @see #setUri(String)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem_Uri()
     */
    String getUri();

    /**
     * Sets the value of the '{@link Subsystem#getUri <em>Uri</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Uri</em>' attribute.
     * @generated
     * @see #getUri()
     */
    void setUri(String value);

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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSubsystem_AnyAttribute()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='attributeWildcard' wildcards='##any' name=':7' processing='lax'"
     * @generated
     */
    Sequence getAnyAttribute();

} // Subsystem
