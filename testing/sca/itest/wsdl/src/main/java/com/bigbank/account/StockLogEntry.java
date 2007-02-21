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
 * A representation of the model object '<em><b>Stock Log Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.StockLogEntry#getLogSeqNo <em>Log Seq No</em>}</li>
 *   <li>{@link com.bigbank.account.StockLogEntry#getId <em>Id</em>}</li>
 *   <li>{@link com.bigbank.account.StockLogEntry#getSymbol <em>Symbol</em>}</li>
 *   <li>{@link com.bigbank.account.StockLogEntry#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link com.bigbank.account.StockLogEntry#getActionType <em>Action Type</em>}</li>
 *   <li>{@link com.bigbank.account.StockLogEntry#getPurchaseLotNumber <em>Purchase Lot Number</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface StockLogEntry extends Serializable
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
   * Sets the value of the '{@link com.bigbank.account.StockLogEntry#getLogSeqNo <em>Log Seq No</em>}' attribute.
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
   * Sets the value of the '{@link com.bigbank.account.StockLogEntry#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(int value);

  /**
   * Returns the value of the '<em><b>Symbol</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Symbol</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Symbol</em>' attribute.
   * @see #setSymbol(String)
   * @generated
   */
  String getSymbol();

  /**
   * Sets the value of the '{@link com.bigbank.account.StockLogEntry#getSymbol <em>Symbol</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Symbol</em>' attribute.
   * @see #getSymbol()
   * @generated
   */
  void setSymbol(String value);

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
   * Sets the value of the '{@link com.bigbank.account.StockLogEntry#getQuantity <em>Quantity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Quantity</em>' attribute.
   * @see #getQuantity()
   * @generated
   */
  void setQuantity(int value);

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
   * Sets the value of the '{@link com.bigbank.account.StockLogEntry#getActionType <em>Action Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Action Type</em>' attribute.
   * @see #getActionType()
   * @generated
   */
  void setActionType(String value);

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
   * Sets the value of the '{@link com.bigbank.account.StockLogEntry#getPurchaseLotNumber <em>Purchase Lot Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Purchase Lot Number</em>' attribute.
   * @see #getPurchaseLotNumber()
   * @generated
   */
  void setPurchaseLotNumber(int value);

} // StockLogEntry
