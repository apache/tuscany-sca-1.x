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
 * A representation of the model object '<em><b>System Wire</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * <p/>
 * The following features are supported:
 * <ul>
 * <li>{@link SystemWire#getSourceGroup <em>Source Group</em>}</li>
 * <li>{@link SystemWire#getSource <em>Source</em>}</li>
 * <li>{@link SystemWire#getTargetGroup <em>Target Group</em>}</li>
 * <li>{@link SystemWire#getTarget <em>Target</em>}</li>
 * <li>{@link SystemWire#getAny <em>Any</em>}</li>
 * </ul>
 * </p>
 *
 * @model extendedMetaData="name='SystemWire' kind='elementOnly'"
 * @generated
 * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSystemWire()
 */
public interface SystemWire {
    /**
     * Returns the value of the '<em><b>Source Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Source Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Group</em>' attribute list.
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" required="true"
     * extendedMetaData="kind='group' name='source:group' namespace='##targetNamespace'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSystemWire_SourceGroup()
     */
    Sequence getSourceGroup();

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
     * @model containment="true" resolveProxies="false" required="true" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='source' namespace='##targetNamespace' group='source:group'"
     * @generated
     * @see #setSource(Object)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSystemWire_Source()
     */
    Object getSource();

    /**
     * Sets the value of the '{@link SystemWire#getSource <em>Source</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Source</em>' containment reference.
     * @generated
     * @see #getSource()
     */
    void setSource(Object value);

    /**
     * Returns the value of the '<em><b>Target Group</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p/>
     * If the meaning of the '<em>Target Group</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Group</em>' attribute list.
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" required="true"
     * extendedMetaData="kind='group' name='target:group' namespace='##targetNamespace'"
     * @generated
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSystemWire_TargetGroup()
     */
    Sequence getTargetGroup();

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
     * @model containment="true" resolveProxies="false" required="true" transient="true" volatile="true" derived="true"
     * extendedMetaData="kind='element' name='target' namespace='##targetNamespace' group='target:group'"
     * @generated
     * @see #setTarget(Object)
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSystemWire_Target()
     */
    Object getTarget();

    /**
     * Sets the value of the '{@link SystemWire#getTarget <em>Target</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Target</em>' containment reference.
     * @generated
     * @see #getTarget()
     */
    void setTarget(Object value);

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
     * @see org.apache.tuscany.model.assembly.binding.AssemblyPackage#getSystemWire_Any()
     */
    Sequence getAny();

} // SystemWire
