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
 * A representation of the model object '<em><b>purchase Stock Response</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.purchaseStockResponse#getPurchaseSummary <em>Purchase Summary</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface purchaseStockResponse extends Serializable
{
  /**
   * Returns the value of the '<em><b>Purchase Summary</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Purchase Summary</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Purchase Summary</em>' containment reference.
   * @see #setPurchaseSummary(StockSummary)
   * @generated
   */
  StockSummary getPurchaseSummary();

  /**
   * Sets the value of the '{@link com.bigbank.account.purchaseStockResponse#getPurchaseSummary <em>Purchase Summary</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Purchase Summary</em>' containment reference.
   * @see #getPurchaseSummary()
   * @generated
   */
  void setPurchaseSummary(StockSummary value);

} // purchaseStockResponse
