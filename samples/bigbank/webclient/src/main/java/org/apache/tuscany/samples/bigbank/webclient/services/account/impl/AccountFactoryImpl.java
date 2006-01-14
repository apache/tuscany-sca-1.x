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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountFactory;
import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountPackage;
import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountReport;
import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountSummary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AccountFactoryImpl extends EFactoryImpl implements AccountFactory {
    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public AccountFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case AccountPackage.ACCOUNT_REPORT:
            return (EObject) createAccountReport();
        case AccountPackage.ACCOUNT_SUMMARY:
            return (EObject) createAccountSummary();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public AccountReport createAccountReport() {
        AccountReportImpl accountReport = new AccountReportImpl();
        return accountReport;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public AccountSummary createAccountSummary() {
        AccountSummaryImpl accountSummary = new AccountSummaryImpl();
        return accountSummary;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public AccountPackage getAccountPackage() {
        return (AccountPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @deprecated
     */
    public static AccountPackage getPackage() {
        return AccountPackage.eINSTANCE;
    }

} //AccountFactoryImpl
