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
 * A representation of the model object '<em><b>WSDL Port Type</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * <p/>
 * The following features are supported:
 * <ul>
 * <li>{@link WSDLPortType#getAny <em>Any</em>}</li>
 * <li>{@link WSDLPortType#getCallbackInterface <em>Callback Interface</em>}</li>
 * <li>{@link WSDLPortType#getInterface <em>Interface</em>}</li>
 * <li>{@link WSDLPortType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @model extendedMetaData="name='WSDLPortType' kind='elementOnly'"
 * @generated
 * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getWSDLPortType()
 */
public interface WSDLPortType extends Interface {
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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getWSDLPortType_Any()
     */
    Sequence getAny();

    /**
     * Returns the value of the '<em><b>Callback Interface</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Callback Interface</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Callback Interface</em>' attribute.
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI"
     * extendedMetaData="kind='attribute' name='callbackInterface'"
     * @generated
     * @see #setCallbackInterface(String)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getWSDLPortType_CallbackInterface()
     */
    String getCallbackInterface();

    /**
     * Sets the value of the '{@link WSDLPortType#getCallbackInterface <em>Callback Interface</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Callback Interface</em>' attribute.
     * @generated
     * @see #getCallbackInterface()
     */
    void setCallbackInterface(String value);

    /**
     * Returns the value of the '<em><b>Interface</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Interface</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Interface</em>' attribute.
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
     * extendedMetaData="kind='attribute' name='interface'"
     * @generated
     * @see #setInterface(String)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getWSDLPortType_Interface()
     */
    String getInterface();

    /**
     * Sets the value of the '{@link WSDLPortType#getInterface <em>Interface</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Interface</em>' attribute.
     * @generated
     * @see #getInterface()
     */
    void setInterface(String value);

    /**
     * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Any Attribute</em>' attribute list.
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     * extendedMetaData="kind='attributeWildcard' wildcards='##any' name=':3' processing='lax'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getWSDLPortType_AnyAttribute()
     */
    Sequence getAnyAttribute();

} // WSDLPortType
