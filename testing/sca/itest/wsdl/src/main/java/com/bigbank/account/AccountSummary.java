/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account;

import java.io.Serializable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Summary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.AccountSummary#getAccountNumber <em>Account Number</em>}</li>
 *   <li>{@link com.bigbank.account.AccountSummary#getAccountType <em>Account Type</em>}</li>
 *   <li>{@link com.bigbank.account.AccountSummary#getBalance <em>Balance</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface AccountSummary extends Serializable
{
  /**
   * Returns the value of the '<em><b>Account Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Account Number</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Account Number</em>' attribute.
   * @see #setAccountNumber(String)
   * @generated
   */
  String getAccountNumber();

  /**
   * Sets the value of the '{@link com.bigbank.account.AccountSummary#getAccountNumber <em>Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Account Number</em>' attribute.
   * @see #getAccountNumber()
   * @generated
   */
  void setAccountNumber(String value);

  /**
   * Returns the value of the '<em><b>Account Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Account Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Account Type</em>' attribute.
   * @see #setAccountType(String)
   * @generated
   */
  String getAccountType();

  /**
   * Sets the value of the '{@link com.bigbank.account.AccountSummary#getAccountType <em>Account Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Account Type</em>' attribute.
   * @see #getAccountType()
   * @generated
   */
  void setAccountType(String value);

  /**
   * Returns the value of the '<em><b>Balance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Balance</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Balance</em>' attribute.
   * @see #setBalance(float)
   * @generated
   */
  float getBalance();

  /**
   * Sets the value of the '{@link com.bigbank.account.AccountSummary#getBalance <em>Balance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Balance</em>' attribute.
   * @see #getBalance()
   * @generated
   */
  void setBalance(float value);

} // AccountSummary
