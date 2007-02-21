/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account;

import java.io.Serializable;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Log</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.AccountLog#getAccountLogEntries <em>Account Log Entries</em>}</li>
 *   <li>{@link com.bigbank.account.AccountLog#getStockLogEntries <em>Stock Log Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface AccountLog extends Serializable
{
  /**
   * Returns the value of the '<em><b>Account Log Entries</b></em>' containment reference list.
   * The list contents are of type {@link com.bigbank.account.AccountLogEntry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Account Log Entries</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Account Log Entries</em>' containment reference list.
   * @generated
   */
  List getAccountLogEntries();

  /**
   * Returns the value of the '<em><b>Stock Log Entries</b></em>' containment reference list.
   * The list contents are of type {@link com.bigbank.account.StockLogEntry}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Stock Log Entries</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Stock Log Entries</em>' containment reference list.
   * @generated
   */
  List getStockLogEntries();

} // AccountLog
