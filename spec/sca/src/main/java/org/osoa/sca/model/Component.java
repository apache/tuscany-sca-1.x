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

import commonj.sdo.Sequence;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * <p/>
 * The following features are supported:
 * <ul>
 * <li>{@link Component#getImplementation <em>Implementation</em>}</li>
 * <li>{@link Component#getProperties <em>Properties</em>}</li>
 * <li>{@link Component#getReferences <em>References</em>}</li>
 * <li>{@link Component#getAny <em>Any</em>}</li>
 * <li>{@link Component#getName <em>Name</em>}</li>
 * <li>{@link Component#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @model extendedMetaData="name='Component' kind='elementOnly'"
 * @generated
 * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getComponent()
 */
public interface Component {
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
     * @model containment="true" resolveProxies="false" required="true"
     * extendedMetaData="kind='element' name='implementation' namespace='##targetNamespace'"
     * @generated
     * @see #setImplementation(Implementation)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getComponent_Implementation()
     */
    Implementation getImplementation();

    /**
     * Sets the value of the '{@link Component#getImplementation <em>Implementation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Implementation</em>' containment reference.
     * @generated
     * @see #getImplementation()
     */
    void setImplementation(Implementation value);

    /**
     * Returns the value of the '<em><b>Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Properties</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Properties</em>' containment reference.
     * @model containment="true" resolveProxies="false"
     * extendedMetaData="kind='element' name='properties' namespace='##targetNamespace'"
     * @generated
     * @see #setPropertyValues(PropertyValues)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getComponent_Properties()
     */
    PropertyValues getPropertyValues();

    /**
     * Sets the value of the '{@link Component#getProperties <em>Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Properties</em>' containment reference.
     * @generated
     * @see #getPropertyValues()
     */
    void setPropertyValues(PropertyValues value);

    /**
     * Returns the value of the '<em><b>References</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>References</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>References</em>' containment reference.
     * @model containment="true" resolveProxies="false"
     * extendedMetaData="kind='element' name='references' namespace='##targetNamespace'"
     * @generated
     * @see #setReferenceValues(ReferenceValues)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getComponent_References()
     */
    ReferenceValues getReferenceValues();

    /**
     * Sets the value of the '{@link Component#getReferences <em>References</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>References</em>' containment reference.
     * @generated
     * @see #getReferenceValues()
     */
    void setReferenceValues(ReferenceValues value);

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
     * extendedMetaData="kind='elementWildcard' wildcards='##other' name=':3' processing='lax'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getComponent_Any()
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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getComponent_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link Component#getName <em>Name</em>}' attribute.
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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getComponent_AnyAttribute()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='attributeWildcard' wildcards='##any' name=':5' processing='lax'"
     * @generated
     */
    Sequence getAnyAttribute();

} // Component
