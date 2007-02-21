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
 * A representation of the model object '<em><b>get Customer Profile Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.getCustomerProfileResponse#getCustomerProfile <em>Customer Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface getCustomerProfileResponse extends Serializable
{
  /**
   * Returns the value of the '<em><b>Customer Profile</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Customer Profile</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Customer Profile</em>' containment reference.
   * @see #setCustomerProfile(CustomerProfileData)
   * @generated
   */
  CustomerProfileData getCustomerProfile();

  /**
   * Sets the value of the '{@link com.bigbank.account.getCustomerProfileResponse#getCustomerProfile <em>Customer Profile</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Customer Profile</em>' containment reference.
   * @see #getCustomerProfile()
   * @generated
   */
  void setCustomerProfile(CustomerProfileData value);

} // getCustomerProfileResponse
