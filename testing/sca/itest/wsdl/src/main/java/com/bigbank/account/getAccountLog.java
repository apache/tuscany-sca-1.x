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
 * A representation of the model object '<em><b>get Account Log</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.getAccountLog#getCustomerID <em>Customer ID</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface getAccountLog extends Serializable
{
  /**
   * Returns the value of the '<em><b>Customer ID</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Customer ID</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Customer ID</em>' attribute.
   * @see #setCustomerID(int)
   * @generated
   */
  int getCustomerID();

  /**
   * Sets the value of the '{@link com.bigbank.account.getAccountLog#getCustomerID <em>Customer ID</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Customer ID</em>' attribute.
   * @see #getCustomerID()
   * @generated
   */
  void setCustomerID(int value);

} // getAccountLog
