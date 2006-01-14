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
package org.apache.tuscany.samples.bigbank.account.services.account.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.apache.tuscany.samples.bigbank.account.services.account.AccountFactory;
import org.apache.tuscany.samples.bigbank.account.services.account.AccountPackage;
import org.apache.tuscany.samples.bigbank.account.services.account.AccountReport;
import org.apache.tuscany.samples.bigbank.account.services.account.AccountSummary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class AccountPackageImpl extends EPackageImpl implements AccountPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass accountReportEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass accountSummaryEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see services.account.AccountPackage#NS_URI
     * @see #init()
     */
    private AccountPackageImpl() {
        super(eNS_URI, AccountFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this
     * model, and for any others upon which it depends.  Simple
     * dependencies are satisfied by calling this method on all
     * dependent packages before doing anything else.  This method drives
     * initialization for interdependent packages directly, in parallel
     * with this package, itself.
     * <p>Of this package and its interdependencies, all packages which
     * have not yet been registered by their URI values are first created
     * and registered.  The packages are then initialized in two steps:
     * meta-model objects for all of the packages are created before any
     * are initialized, since one package's meta-model objects may refer to
     * those of another.
     * <p>Invocation of this method will not affect any packages that have
     * already been initialized.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     */
    public static AccountPackage init() {
        if (isInited) return (AccountPackage) EPackage.Registry.INSTANCE.getEPackage(AccountPackage.eNS_URI);

        // Obtain or create and register package
        AccountPackageImpl theAccountPackage = (AccountPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof AccountPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new AccountPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theAccountPackage.createPackageContents();

        // Initialize created meta-data
        theAccountPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theAccountPackage.freeze();

        return theAccountPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public EClass getAccountReport() {
        return accountReportEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public EReference getAccountReport_AccountSummaries() {
        return (EReference) accountReportEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public EClass getAccountSummary() {
        return accountSummaryEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public EAttribute getAccountSummary_AccountNumber() {
        return (EAttribute) accountSummaryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public EAttribute getAccountSummary_AccountType() {
        return (EAttribute) accountSummaryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public EAttribute getAccountSummary_Balance() {
        return (EAttribute) accountSummaryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public AccountFactory getAccountFactory() {
        return (AccountFactory) getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        accountReportEClass = createEClass(ACCOUNT_REPORT);
        createEReference(accountReportEClass, ACCOUNT_REPORT__ACCOUNT_SUMMARIES);

        accountSummaryEClass = createEClass(ACCOUNT_SUMMARY);
        createEAttribute(accountSummaryEClass, ACCOUNT_SUMMARY__ACCOUNT_NUMBER);
        createEAttribute(accountSummaryEClass, ACCOUNT_SUMMARY__ACCOUNT_TYPE);
        createEAttribute(accountSummaryEClass, ACCOUNT_SUMMARY__BALANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(accountReportEClass, AccountReport.class, "AccountReport", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAccountReport_AccountSummaries(), this.getAccountSummary(), null, "accountSummaries", null, 0, -1, AccountReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(accountSummaryEClass, AccountSummary.class, "AccountSummary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAccountSummary_AccountNumber(), ecorePackage.getEString(), "accountNumber", null, 0, 1, AccountSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAccountSummary_AccountType(), ecorePackage.getEString(), "accountType", null, 0, 1, AccountSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAccountSummary_Balance(), ecorePackage.getEFloat(), "balance", null, 0, 1, AccountSummary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Create resource
        createResource(eNS_URI);
	}

} //AccountPackageImpl
