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

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.sdo.impl.EDataObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;

import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountPackage;
import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountReport;
import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountSummary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Report</b></em>'.
 * <!-- end-user-doc -->
 * <p/>
 * The following features are implemented:
 * <ul>
 * <li>{@link services.account.impl.AccountReportImpl#getAccountSummaries <em>Account Summaries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountReportImpl extends EDataObjectImpl implements AccountReport {
    /**
     * The cached value of the '{@link #getAccountSummaries() <em>Account Summaries</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getAccountSummaries()
     */
    protected EList accountSummaries = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected AccountReportImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected EClass eStaticClass() {
        return AccountPackage.eINSTANCE.getAccountReport();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public List getAccountSummaries() {
        if (accountSummaries == null) {
            accountSummaries = new EObjectContainmentEList(AccountSummary.class, this, AccountPackage.ACCOUNT_REPORT__ACCOUNT_SUMMARIES);
        }
        return accountSummaries;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
        case AccountPackage.ACCOUNT_REPORT__ACCOUNT_SUMMARIES:
            return getAccountSummaries();
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
        case AccountPackage.ACCOUNT_REPORT__ACCOUNT_SUMMARIES:
            getAccountSummaries().clear();
            getAccountSummaries().addAll((Collection) newValue);
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
        case AccountPackage.ACCOUNT_REPORT__ACCOUNT_SUMMARIES:
            getAccountSummaries().clear();
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
        case AccountPackage.ACCOUNT_REPORT__ACCOUNT_SUMMARIES:
            return accountSummaries != null && !accountSummaries.isEmpty();
        }
        return eDynamicIsSet(eFeature);
    }

} //AccountReportImpl
