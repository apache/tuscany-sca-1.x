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
 * A representation of the model object '<em><b>sell Stock</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.sellStock#getPurchaseLotNumber <em>Purchase Lot Number</em>}</li>
 *   <li>{@link com.bigbank.account.sellStock#getQuantity <em>Quantity</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface sellStock extends Serializable
{
  /**
   * Returns the value of the '<em><b>Purchase Lot Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Purchase Lot Number</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Purchase Lot Number</em>' attribute.
   * @see #setPurchaseLotNumber(int)
   * @generated
   */
  int getPurchaseLotNumber();

  /**
   * Sets the value of the '{@link com.bigbank.account.sellStock#getPurchaseLotNumber <em>Purchase Lot Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Purchase Lot Number</em>' attribute.
   * @see #getPurchaseLotNumber()
   * @generated
   */
  void setPurchaseLotNumber(int value);

  /**
   * Returns the value of the '<em><b>Quantity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Quantity</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Quantity</em>' attribute.
   * @see #setQuantity(int)
   * @generated
   */
  int getQuantity();

  /**
   * Sets the value of the '{@link com.bigbank.account.sellStock#getQuantity <em>Quantity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Quantity</em>' attribute.
   * @see #getQuantity()
   * @generated
   */
  void setQuantity(int value);

} // sellStock
