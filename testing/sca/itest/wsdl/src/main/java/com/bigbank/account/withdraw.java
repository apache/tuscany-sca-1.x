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
 * A representation of the model object '<em><b>withdraw</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.withdraw#getAccountNumber <em>Account Number</em>}</li>
 *   <li>{@link com.bigbank.account.withdraw#getAmount <em>Amount</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface withdraw extends Serializable
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
   * Sets the value of the '{@link com.bigbank.account.withdraw#getAccountNumber <em>Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Account Number</em>' attribute.
   * @see #getAccountNumber()
   * @generated
   */
  void setAccountNumber(String value);

  /**
   * Returns the value of the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Amount</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Amount</em>' attribute.
   * @see #setAmount(float)
   * @generated
   */
  float getAmount();

  /**
   * Sets the value of the '{@link com.bigbank.account.withdraw#getAmount <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Amount</em>' attribute.
   * @see #getAmount()
   * @generated
   */
  void setAmount(float value);

} // withdraw
