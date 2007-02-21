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
 * A representation of the model object '<em><b>create Account</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.createAccount#getCustomerProfile <em>Customer Profile</em>}</li>
 *   <li>{@link com.bigbank.account.createAccount#isCreateSavings <em>Create Savings</em>}</li>
 *   <li>{@link com.bigbank.account.createAccount#isCreateCheckings <em>Create Checkings</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface createAccount extends Serializable
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
   * Sets the value of the '{@link com.bigbank.account.createAccount#getCustomerProfile <em>Customer Profile</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Customer Profile</em>' containment reference.
   * @see #getCustomerProfile()
   * @generated
   */
  void setCustomerProfile(CustomerProfileData value);

  /**
   * Returns the value of the '<em><b>Create Savings</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Create Savings</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Create Savings</em>' attribute.
   * @see #setCreateSavings(boolean)
   * @generated
   */
  boolean isCreateSavings();

  /**
   * Sets the value of the '{@link com.bigbank.account.createAccount#isCreateSavings <em>Create Savings</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Create Savings</em>' attribute.
   * @see #isCreateSavings()
   * @generated
   */
  void setCreateSavings(boolean value);

  /**
   * Returns the value of the '<em><b>Create Checkings</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Create Checkings</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Create Checkings</em>' attribute.
   * @see #setCreateCheckings(boolean)
   * @generated
   */
  boolean isCreateCheckings();

  /**
   * Sets the value of the '{@link com.bigbank.account.createAccount#isCreateCheckings <em>Create Checkings</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Create Checkings</em>' attribute.
   * @see #isCreateCheckings()
   * @generated
   */
  void setCreateCheckings(boolean value);

} // createAccount
