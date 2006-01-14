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
package org.apache.tuscany.samples.bigbank.webclient.services.account.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.sdo.impl.EDataObjectImpl;

import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountPackage;
import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountSummary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Summary</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * The following features are implemented:
 * <ul>
 * <li>{@link services.account.impl.AccountSummaryImpl#getAccountNumber <em>Account Number</em>}</li>
 * <li>{@link services.account.impl.AccountSummaryImpl#getAccountType <em>Account Type</em>}</li>
 * <li>{@link services.account.impl.AccountSummaryImpl#getBalance <em>Balance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountSummaryImpl extends EDataObjectImpl implements AccountSummary {
    /**
     * The default value of the '{@link #getAccountNumber() <em>Account Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getAccountNumber()
     */
    protected static final String ACCOUNT_NUMBER_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAccountNumber() <em>Account Number</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getAccountNumber()
     */
    protected String accountNumber = ACCOUNT_NUMBER_EDEFAULT;

    /**
     * The default value of the '{@link #getAccountType() <em>Account Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getAccountType()
     */
    protected static final String ACCOUNT_TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAccountType() <em>Account Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getAccountType()
     */
    protected String accountType = ACCOUNT_TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #getBalance() <em>Balance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBalance()
     */
    protected static final float BALANCE_EDEFAULT = 0.0F;

    /**
     * The cached value of the '{@link #getBalance() <em>Balance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getBalance()
     */
    protected float balance = BALANCE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected AccountSummaryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected EClass eStaticClass() {
        return AccountPackage.eINSTANCE.getAccountSummary();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setAccountNumber(String newAccountNumber) {
        String oldAccountNumber = accountNumber;
        accountNumber = newAccountNumber;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_NUMBER, oldAccountNumber, accountNumber));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setAccountType(String newAccountType) {
        String oldAccountType = accountType;
        accountType = newAccountType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_TYPE, oldAccountType, accountType));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public float getBalance() {
        return balance;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setBalance(float newBalance) {
        float oldBalance = balance;
        balance = newBalance;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AccountPackage.ACCOUNT_SUMMARY__BALANCE, oldBalance, balance));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
        case AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_NUMBER:
            return getAccountNumber();
        case AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_TYPE:
            return getAccountType();
        case AccountPackage.ACCOUNT_SUMMARY__BALANCE:
            return new Float(getBalance());
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
        case AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_NUMBER:
            setAccountNumber((String) newValue);
            return;
        case AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_TYPE:
            setAccountType((String) newValue);
            return;
        case AccountPackage.ACCOUNT_SUMMARY__BALANCE:
            setBalance(((Float) newValue).floatValue());
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
        case AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_NUMBER:
            setAccountNumber(ACCOUNT_NUMBER_EDEFAULT);
            return;
        case AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_TYPE:
            setAccountType(ACCOUNT_TYPE_EDEFAULT);
            return;
        case AccountPackage.ACCOUNT_SUMMARY__BALANCE:
            setBalance(BALANCE_EDEFAULT);
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
        case AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_NUMBER:
            return ACCOUNT_NUMBER_EDEFAULT == null ? accountNumber != null : !ACCOUNT_NUMBER_EDEFAULT.equals(accountNumber);
        case AccountPackage.ACCOUNT_SUMMARY__ACCOUNT_TYPE:
            return ACCOUNT_TYPE_EDEFAULT == null ? accountType != null : !ACCOUNT_TYPE_EDEFAULT.equals(accountType);
        case AccountPackage.ACCOUNT_SUMMARY__BALANCE:
            return balance != BALANCE_EDEFAULT;
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
        result.append(" (accountNumber: ");
        result.append(accountNumber);
        result.append(", accountType: ");
        result.append(accountType);
        result.append(", balance: ");
        result.append(balance);
        result.append(')');
        return result.toString();
	}

} //AccountSummaryImpl
