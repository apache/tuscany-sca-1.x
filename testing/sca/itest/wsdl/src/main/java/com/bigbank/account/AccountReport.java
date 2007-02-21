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
 * A representation of the model object '<em><b>Report</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.AccountReport#getAccountSummaries <em>Account Summaries</em>}</li>
 *   <li>{@link com.bigbank.account.AccountReport#getStockSummaries <em>Stock Summaries</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface AccountReport extends Serializable
{
  /**
   * Returns the value of the '<em><b>Account Summaries</b></em>' containment reference list.
   * The list contents are of type {@link com.bigbank.account.AccountSummary}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Account Summaries</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Account Summaries</em>' containment reference list.
   * @generated
   */
  List getAccountSummaries();

  /**
   * Returns the value of the '<em><b>Stock Summaries</b></em>' containment reference list.
   * The list contents are of type {@link com.bigbank.account.StockSummary}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Stock Summaries</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Stock Summaries</em>' containment reference list.
   * @generated
   */
  List getStockSummaries();

} // AccountReport
