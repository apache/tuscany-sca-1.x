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
 * A representation of the model object '<em><b>get Account Report Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.getAccountReportResponse#getAccountReport <em>Account Report</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface getAccountReportResponse extends Serializable
{
  /**
   * Returns the value of the '<em><b>Account Report</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Account Report</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Account Report</em>' containment reference.
   * @see #setAccountReport(AccountReport)
   * @generated
   */
  AccountReport getAccountReport();

  /**
   * Sets the value of the '{@link com.bigbank.account.getAccountReportResponse#getAccountReport <em>Account Report</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Account Report</em>' containment reference.
   * @see #getAccountReport()
   * @generated
   */
  void setAccountReport(AccountReport value);

} // getAccountReportResponse
