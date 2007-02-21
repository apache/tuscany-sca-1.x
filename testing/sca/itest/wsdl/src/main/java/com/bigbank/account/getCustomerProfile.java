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
 * A representation of the model object '<em><b>get Customer Profile</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.getCustomerProfile#getLoginID <em>Login ID</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface getCustomerProfile extends Serializable
{
  /**
   * Returns the value of the '<em><b>Login ID</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Login ID</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Login ID</em>' attribute.
   * @see #setLoginID(String)
   * @generated
   */
  String getLoginID();

  /**
   * Sets the value of the '{@link com.bigbank.account.getCustomerProfile#getLoginID <em>Login ID</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Login ID</em>' attribute.
   * @see #getLoginID()
   * @generated
   */
  void setLoginID(String value);

} // getCustomerProfile
