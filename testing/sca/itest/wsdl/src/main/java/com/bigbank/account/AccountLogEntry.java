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
 * A representation of the model object '<em><b>Log Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.AccountLogEntry#getLogSeqNo <em>Log Seq No</em>}</li>
 *   <li>{@link com.bigbank.account.AccountLogEntry#getId <em>Id</em>}</li>
 *   <li>{@link com.bigbank.account.AccountLogEntry#getAccountNumber <em>Account Number</em>}</li>
 *   <li>{@link com.bigbank.account.AccountLogEntry#getActionType <em>Action Type</em>}</li>
 *   <li>{@link com.bigbank.account.AccountLogEntry#getAmount <em>Amount</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface AccountLogEntry extends Serializable
{
  /**
   * Returns the value of the '<em><b>Log Seq No</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Log Seq No</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Log Seq No</em>' attribute.
   * @see #setLogSeqNo(int)
   * @generated
   */
  int getLogSeqNo();

  /**
   * Sets the value of the '{@link com.bigbank.account.AccountLogEntry#getLogSeqNo <em>Log Seq No</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Log Seq No</em>' attribute.
   * @see #getLogSeqNo()
   * @generated
   */
  void setLogSeqNo(int value);

  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(int)
   * @generated
   */
  int getId();

  /**
   * Sets the value of the '{@link com.bigbank.account.AccountLogEntry#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(int value);

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
   * Sets the value of the '{@link com.bigbank.account.AccountLogEntry#getAccountNumber <em>Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Account Number</em>' attribute.
   * @see #getAccountNumber()
   * @generated
   */
  void setAccountNumber(String value);

  /**
   * Returns the value of the '<em><b>Action Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Action Type</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Action Type</em>' attribute.
   * @see #setActionType(String)
   * @generated
   */
  String getActionType();

  /**
   * Sets the value of the '{@link com.bigbank.account.AccountLogEntry#getActionType <em>Action Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Action Type</em>' attribute.
   * @see #getActionType()
   * @generated
   */
  void setActionType(String value);

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
   * Sets the value of the '{@link com.bigbank.account.AccountLogEntry#getAmount <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Amount</em>' attribute.
   * @see #getAmount()
   * @generated
   */
  void setAmount(float value);

} // AccountLogEntry
