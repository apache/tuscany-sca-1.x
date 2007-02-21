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
 * A representation of the model object '<em><b>withdraw Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.withdrawResponse#getBalance <em>Balance</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface withdrawResponse extends Serializable
{
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
   * Sets the value of the '{@link com.bigbank.account.withdrawResponse#getBalance <em>Balance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Balance</em>' attribute.
   * @see #getBalance()
   * @generated
   */
  void setBalance(float value);

} // withdrawResponse
