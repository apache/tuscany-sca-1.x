/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account;

import commonj.sdo.helper.HelperContext;


/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @generated
 */
public interface AccountFactory
{

  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  AccountFactory INSTANCE = com.bigbank.account.impl.AccountFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Log</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Log</em>'.
   * @generated
   */
  AccountLog createAccountLog();

  /**
   * Returns a new object of class '<em>Log Entry</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Log Entry</em>'.
   * @generated
   */
  AccountLogEntry createAccountLogEntry();

  /**
   * Returns a new object of class '<em>Report</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Report</em>'.
   * @generated
   */
  AccountReport createAccountReport();

  /**
   * Returns a new object of class '<em>Summary</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Summary</em>'.
   * @generated
   */
  AccountSummary createAccountSummary();

  /**
   * Returns a new object of class '<em>create Account</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>create Account</em>'.
   * @generated
   */
  createAccount createcreateAccount();

  /**
   * Returns a new object of class '<em>create Account Response</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>create Account Response</em>'.
   * @generated
   */
  createAccountResponse createcreateAccountResponse();

  /**
   * Returns a new object of class '<em>Customer Profile Data</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Customer Profile Data</em>'.
   * @generated
   */
  CustomerProfileData createCustomerProfileData();

  /**
   * Returns a new object of class '<em>deposit</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>deposit</em>'.
   * @generated
   */
  deposit createdeposit();

  /**
   * Returns a new object of class '<em>deposit Response</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>deposit Response</em>'.
   * @generated
   */
  depositResponse createdepositResponse();

  /**
   * Returns a new object of class '<em>get Account Log</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>get Account Log</em>'.
   * @generated
   */
  getAccountLog creategetAccountLog();

  /**
   * Returns a new object of class '<em>get Account Log Response</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>get Account Log Response</em>'.
   * @generated
   */
  getAccountLogResponse creategetAccountLogResponse();

  /**
   * Returns a new object of class '<em>get Account Report</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>get Account Report</em>'.
   * @generated
   */
  getAccountReport creategetAccountReport();

  /**
   * Returns a new object of class '<em>get Account Report Response</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>get Account Report Response</em>'.
   * @generated
   */
  getAccountReportResponse creategetAccountReportResponse();

  /**
   * Returns a new object of class '<em>get Customer Profile</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>get Customer Profile</em>'.
   * @generated
   */
  getCustomerProfile creategetCustomerProfile();

  /**
   * Returns a new object of class '<em>get Customer Profile Response</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>get Customer Profile Response</em>'.
   * @generated
   */
  getCustomerProfileResponse creategetCustomerProfileResponse();

  /**
   * Returns a new object of class '<em>purchase Stock</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>purchase Stock</em>'.
   * @generated
   */
  purchaseStock createpurchaseStock();

  /**
   * Returns a new object of class '<em>purchase Stock Response</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>purchase Stock Response</em>'.
   * @generated
   */
  purchaseStockResponse createpurchaseStockResponse();

  /**
   * Returns a new object of class '<em>sell Stock</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>sell Stock</em>'.
   * @generated
   */
  sellStock createsellStock();

  /**
   * Returns a new object of class '<em>Stock Log Entry</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Stock Log Entry</em>'.
   * @generated
   */
  StockLogEntry createStockLogEntry();

  /**
   * Returns a new object of class '<em>Stock Summary</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Stock Summary</em>'.
   * @generated
   */
  StockSummary createStockSummary();

  /**
   * Returns a new object of class '<em>withdraw</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>withdraw</em>'.
   * @generated
   */
  withdraw createwithdraw();

  /**
   * Returns a new object of class '<em>withdraw Response</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>withdraw Response</em>'.
   * @generated
   */
  withdrawResponse createwithdrawResponse();

  /**
   * Registers the types supported by this Factory within the supplied scope.argument
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param scope an instance of HelperContext used to manage the scoping of types.
   * @generated
   */
  public void register(HelperContext scope);
   
} //AccountFactory
