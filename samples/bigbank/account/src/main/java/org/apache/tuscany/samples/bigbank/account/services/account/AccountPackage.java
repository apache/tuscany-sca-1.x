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
package org.apache.tuscany.samples.bigbank.account.services.account;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @model kind="package"
 * @generated
 * @see services.account.AccountFactory
 */
public interface AccountPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "account";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.bigbank.com/Account/";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "account";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    AccountPackage eINSTANCE = org.apache.tuscany.samples.bigbank.account.services.account.impl.AccountPackageImpl.init();

    /**
     * The meta object id for the '{@link services.account.impl.AccountReportImpl <em>Report</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @see services.account.impl.AccountReportImpl
     * @see services.account.impl.AccountPackageImpl#getAccountReport()
     */
    int ACCOUNT_REPORT = 0;

    /**
     * The feature id for the '<em><b>Account Summaries</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ACCOUNT_REPORT__ACCOUNT_SUMMARIES = 0;

    /**
     * The number of structural features of the the '<em>Report</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ACCOUNT_REPORT_FEATURE_COUNT = 1;

    /**
     * The meta object id for the '{@link services.account.impl.AccountSummaryImpl <em>Summary</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @see services.account.impl.AccountSummaryImpl
     * @see services.account.impl.AccountPackageImpl#getAccountSummary()
     */
    int ACCOUNT_SUMMARY = 1;

    /**
     * The feature id for the '<em><b>Account Number</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ACCOUNT_SUMMARY__ACCOUNT_NUMBER = 0;

    /**
     * The feature id for the '<em><b>Account Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ACCOUNT_SUMMARY__ACCOUNT_TYPE = 1;

    /**
     * The feature id for the '<em><b>Balance</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ACCOUNT_SUMMARY__BALANCE = 2;

    /**
     * The number of structural features of the the '<em>Summary</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int ACCOUNT_SUMMARY_FEATURE_COUNT = 3;


    /**
     * Returns the meta object for class '{@link services.account.AccountReport <em>Report</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Report</em>'.
     * @generated
     * @see services.account.AccountReport
     */
    EClass getAccountReport();

    /**
     * Returns the meta object for the reference list '{@link services.account.AccountReport#getAccountSummaries <em>Account Summaries</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the reference list '<em>Account Summaries</em>'.
     * @generated
     * @see services.account.AccountReport#getAccountSummaries()
     * @see #getAccountReport()
     */
    EReference getAccountReport_AccountSummaries();

    /**
     * Returns the meta object for class '{@link services.account.AccountSummary <em>Summary</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Summary</em>'.
     * @generated
     * @see services.account.AccountSummary
     */
    EClass getAccountSummary();

    /**
     * Returns the meta object for the attribute '{@link services.account.AccountSummary#getAccountNumber <em>Account Number</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Account Number</em>'.
     * @generated
     * @see services.account.AccountSummary#getAccountNumber()
     * @see #getAccountSummary()
     */
    EAttribute getAccountSummary_AccountNumber();

    /**
     * Returns the meta object for the attribute '{@link services.account.AccountSummary#getAccountType <em>Account Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Account Type</em>'.
     * @generated
     * @see services.account.AccountSummary#getAccountType()
     * @see #getAccountSummary()
     */
    EAttribute getAccountSummary_AccountType();

    /**
     * Returns the meta object for the attribute '{@link services.account.AccountSummary#getBalance <em>Balance</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Balance</em>'.
     * @generated
     * @see services.account.AccountSummary#getBalance()
     * @see #getAccountSummary()
     */
    EAttribute getAccountSummary_Balance();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    AccountFactory getAccountFactory();

} //AccountPackage
