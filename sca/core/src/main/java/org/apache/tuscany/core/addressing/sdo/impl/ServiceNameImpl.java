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
package org.apache.tuscany.core.addressing.sdo.impl;

import java.util.Collection;

import commonj.sdo.Sequence;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.sdo.impl.EDataObjectImpl;
import org.eclipse.emf.ecore.sdo.util.BasicESequence;
import org.eclipse.emf.ecore.sdo.util.ESequence;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.apache.tuscany.core.addressing.sdo.AddressingElementPackage;
import org.apache.tuscany.core.addressing.sdo.ServiceName;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Name</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * The following features are implemented:
 * <ul>
 * <li>{@link ServiceNameImpl#getValue <em>Value</em>}</li>
 * <li>{@link ServiceNameImpl#getPortName <em>Port Name</em>}</li>
 * <li>{@link ServiceNameImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceNameImpl extends EDataObjectImpl implements ServiceName {
    /**
     * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getValue()
     */
    protected static final Object VALUE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getValue()
     */
    protected Object value = VALUE_EDEFAULT;

    /**
     * The default value of the '{@link #getPortName() <em>Port Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPortName()
     */
    protected static final String PORT_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPortName() <em>Port Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getPortName()
     */
    protected String portName = PORT_NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getAnyAttribute()
     */
    protected ESequence anyAttribute = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected ServiceNameImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected EClass eStaticClass() {
        return AddressingElementPackage.eINSTANCE.getServiceName();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public Object getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setValue(Object newValue) {
        Object oldValue = value;
        value = newValue;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AddressingElementPackage.SERVICE_NAME__VALUE, oldValue, value));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public String getPortName() {
        return portName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setPortName(String newPortName) {
        String oldPortName = portName;
        portName = newPortName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AddressingElementPackage.SERVICE_NAME__PORT_NAME, oldPortName, portName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public Sequence getAnyAttribute() {
        if (anyAttribute == null) {
            anyAttribute = new BasicESequence(new BasicFeatureMap(this, AddressingElementPackage.SERVICE_NAME__ANY_ATTRIBUTE));
        }
        return anyAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
            case AddressingElementPackage.SERVICE_NAME__ANY_ATTRIBUTE:
                return ((InternalEList) ((ESequence) getAnyAttribute()).featureMap()).basicRemove(otherEnd, msgs);
            default:
                return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
            }
        }
        return eBasicSetContainer(null, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
        case AddressingElementPackage.SERVICE_NAME__VALUE:
            return getValue();
        case AddressingElementPackage.SERVICE_NAME__PORT_NAME:
            return getPortName();
        case AddressingElementPackage.SERVICE_NAME__ANY_ATTRIBUTE:
            return ((ESequence) getAnyAttribute()).featureMap();
        }
        return eDynamicGet(eFeature, resolve);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void eSet(EStructuralFeature eFeature, Object newValue) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
        case AddressingElementPackage.SERVICE_NAME__VALUE:
            setValue((Object) newValue);
            return;
        case AddressingElementPackage.SERVICE_NAME__PORT_NAME:
            setPortName((String) newValue);
            return;
        case AddressingElementPackage.SERVICE_NAME__ANY_ATTRIBUTE:
            ((ESequence) getAnyAttribute()).featureMap().clear();
            ((ESequence) getAnyAttribute()).featureMap().addAll((Collection) newValue);
            return;
        }
        eDynamicSet(eFeature, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void eUnset(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
        case AddressingElementPackage.SERVICE_NAME__VALUE:
            setValue(VALUE_EDEFAULT);
            return;
        case AddressingElementPackage.SERVICE_NAME__PORT_NAME:
            setPortName(PORT_NAME_EDEFAULT);
            return;
        case AddressingElementPackage.SERVICE_NAME__ANY_ATTRIBUTE:
            ((ESequence) getAnyAttribute()).featureMap().clear();
            return;
        }
        eDynamicUnset(eFeature);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public boolean eIsSet(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
        case AddressingElementPackage.SERVICE_NAME__VALUE:
            return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
        case AddressingElementPackage.SERVICE_NAME__PORT_NAME:
            return PORT_NAME_EDEFAULT == null ? portName != null : !PORT_NAME_EDEFAULT.equals(portName);
        case AddressingElementPackage.SERVICE_NAME__ANY_ATTRIBUTE:
            return anyAttribute != null && !anyAttribute.featureMap().isEmpty();
        }
        return eDynamicIsSet(eFeature);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (value: ");
        result.append(value);
        result.append(", portName: ");
        result.append(portName);
        result.append(", anyAttribute: ");
        result.append(anyAttribute);
        result.append(')');
        return result.toString();
	}

} //ServiceNameImpl
