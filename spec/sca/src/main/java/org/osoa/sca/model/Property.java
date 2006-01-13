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
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * <p/>
 * The following features are supported:
 * <ul>
 * <li>{@link Property#getAny <em>Any</em>}</li>
 * <li>{@link Property#getDefault <em>Default</em>}</li>
 * <li>{@link Property#isMany <em>Many</em>}</li>
 * <li>{@link Property#getName <em>Name</em>}</li>
 * <li>{@link Property#isRequired <em>Required</em>}</li>
 * <li>{@link Property#getPropertyType <em>Property Type</em>}</li>
 * <li>{@link Property#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @model extendedMetaData="name='Property' kind='elementOnly'"
 * @generated
 * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getProperty()
 */
public interface Property {
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
     * extendedMetaData="kind='elementWildcard' wildcards='##other' name=':0' processing='lax'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getProperty_Any()
     */
    Sequence getAny();

    /**
     * Returns the value of the '<em><b>Default</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Default</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Default</em>' attribute.
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     * extendedMetaData="kind='attribute' name='default'"
     * @generated
     * @see #setDefault(String)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getProperty_Default()
     */
    String getDefault();

    /**
     * Sets the value of the '{@link Property#getDefault <em>Default</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Default</em>' attribute.
     * @generated
     * @see #getDefault()
     */
    void setDefault(String value);

    /**
     * Returns the value of the '<em><b>Many</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Many</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Many</em>' attribute.
     * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * extendedMetaData="kind='attribute' name='many'"
     * @generated
     * @see #isSetMany()
     * @see #unsetMany()
     * @see #setMany(boolean)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getProperty_Many()
     */
    boolean isMany();

    /**
     * Sets the value of the '{@link Property#isMany <em>Many</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Many</em>' attribute.
     * @generated
     * @see #isSetMany()
     * @see #unsetMany()
     * @see #isMany()
     */
    void setMany(boolean value);

    /**
     * Unsets the value of the '{@link Property#isMany <em>Many</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @see #isSetMany()
     * @see #isMany()
     * @see #setMany(boolean)
     */
    void unsetMany();

    /**
     * Returns whether the value of the '{@link Property#isMany <em>Many</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return whether the value of the '<em>Many</em>' attribute is set.
     * @generated
     * @see #unsetMany()
     * @see #isMany()
     * @see #setMany(boolean)
     */
    boolean isSetMany();

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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getProperty_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link Property#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Name</em>' attribute.
     * @generated
     * @see #getName()
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Required</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Required</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Required</em>' attribute.
     * @model default="false" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * extendedMetaData="kind='attribute' name='required'"
     * @generated
     * @see #isSetRequired()
     * @see #unsetRequired()
     * @see #setRequired(boolean)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getProperty_Required()
     */
    boolean isRequired();

    /**
     * Sets the value of the '{@link Property#isRequired <em>Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Required</em>' attribute.
     * @generated
     * @see #isSetRequired()
     * @see #unsetRequired()
     * @see #isRequired()
     */
    void setRequired(boolean value);

    /**
     * Unsets the value of the '{@link Property#isRequired <em>Required</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @see #isSetRequired()
     * @see #isRequired()
     * @see #setRequired(boolean)
     */
    void unsetRequired();

    /**
     * Returns whether the value of the '{@link Property#isRequired <em>Required</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return whether the value of the '<em>Required</em>' attribute is set.
     * @generated
     * @see #unsetRequired()
     * @see #isRequired()
     * @see #setRequired(boolean)
     */
    boolean isSetRequired();

    /**
     * Returns the value of the '<em><b>Property Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Property Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Property Type</em>' attribute.
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.QName" required="true"
     * extendedMetaData="kind='attribute' name='type'"
     * @generated
     * @see #setType(Object)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getProperty_PropertyType()
     */
    Object getType_();

    /**
     * Sets the value of the '{@link Property#getPropertyType <em>Property Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Property Type</em>' attribute.
     * @generated
     * @see #getType_()
     */
    void setType(Object value);

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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getProperty_AnyAttribute()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='attributeWildcard' wildcards='##any' name=':6' processing='lax'"
     * @generated
     */
	Sequence getAnyAttribute();

} // Property
