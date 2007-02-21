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
 * A representation of the model object '<em><b>get Account Log Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.getAccountLogResponse#getAccountLog <em>Account Log</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface getAccountLogResponse extends Serializable
{
  /**
   * Returns the value of the '<em><b>Account Log</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Account Log</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Account Log</em>' containment reference.
   * @see #setAccountLog(AccountLog)
   * @generated
   */
  AccountLog getAccountLog();

  /**
   * Sets the value of the '{@link com.bigbank.account.getAccountLogResponse#getAccountLog <em>Account Log</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Account Log</em>' containment reference.
   * @see #getAccountLog()
   * @generated
   */
  void setAccountLog(AccountLog value);

} // getAccountLogResponse
