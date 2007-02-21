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
 * A representation of the model object '<em><b>Stock Summary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.bigbank.account.StockSummary#getPurchaseLotNumber <em>Purchase Lot Number</em>}</li>
 *   <li>{@link com.bigbank.account.StockSummary#getSymbol <em>Symbol</em>}</li>
 *   <li>{@link com.bigbank.account.StockSummary#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link com.bigbank.account.StockSummary#getPurchaseDate <em>Purchase Date</em>}</li>
 *   <li>{@link com.bigbank.account.StockSummary#getPurchasePrice <em>Purchase Price</em>}</li>
 *   <li>{@link com.bigbank.account.StockSummary#getCurrentPrice <em>Current Price</em>}</li>
 *   <li>{@link com.bigbank.account.StockSummary#getCompany <em>Company</em>}</li>
 *   <li>{@link com.bigbank.account.StockSummary#getHighPrice <em>High Price</em>}</li>
 *   <li>{@link com.bigbank.account.StockSummary#getLowPrice <em>Low Price</em>}</li>
 * </ul>
 * </p>
 *
 * @extends Serializable
 * @generated
 */
public interface StockSummary extends Serializable
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
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getPurchaseLotNumber <em>Purchase Lot Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Purchase Lot Number</em>' attribute.
   * @see #getPurchaseLotNumber()
   * @generated
   */
  void setPurchaseLotNumber(int value);

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
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getSymbol <em>Symbol</em>}' attribute.
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
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getQuantity <em>Quantity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Quantity</em>' attribute.
   * @see #getQuantity()
   * @generated
   */
  void setQuantity(int value);

  /**
   * Returns the value of the '<em><b>Purchase Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Purchase Date</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Purchase Date</em>' attribute.
   * @see #setPurchaseDate(String)
   * @generated
   */
  String getPurchaseDate();

  /**
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getPurchaseDate <em>Purchase Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Purchase Date</em>' attribute.
   * @see #getPurchaseDate()
   * @generated
   */
  void setPurchaseDate(String value);

  /**
   * Returns the value of the '<em><b>Purchase Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Purchase Price</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Purchase Price</em>' attribute.
   * @see #setPurchasePrice(float)
   * @generated
   */
  float getPurchasePrice();

  /**
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getPurchasePrice <em>Purchase Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Purchase Price</em>' attribute.
   * @see #getPurchasePrice()
   * @generated
   */
  void setPurchasePrice(float value);

  /**
   * Returns the value of the '<em><b>Current Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Current Price</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Current Price</em>' attribute.
   * @see #setCurrentPrice(float)
   * @generated
   */
  float getCurrentPrice();

  /**
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getCurrentPrice <em>Current Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Current Price</em>' attribute.
   * @see #getCurrentPrice()
   * @generated
   */
  void setCurrentPrice(float value);

  /**
   * Returns the value of the '<em><b>Company</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Company</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Company</em>' attribute.
   * @see #setCompany(String)
   * @generated
   */
  String getCompany();

  /**
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getCompany <em>Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Company</em>' attribute.
   * @see #getCompany()
   * @generated
   */
  void setCompany(String value);

  /**
   * Returns the value of the '<em><b>High Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>High Price</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>High Price</em>' attribute.
   * @see #setHighPrice(float)
   * @generated
   */
  float getHighPrice();

  /**
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getHighPrice <em>High Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>High Price</em>' attribute.
   * @see #getHighPrice()
   * @generated
   */
  void setHighPrice(float value);

  /**
   * Returns the value of the '<em><b>Low Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Low Price</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Low Price</em>' attribute.
   * @see #setLowPrice(float)
   * @generated
   */
  float getLowPrice();

  /**
   * Sets the value of the '{@link com.bigbank.account.StockSummary#getLowPrice <em>Low Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Low Price</em>' attribute.
   * @see #getLowPrice()
   * @generated
   */
  void setLowPrice(float value);

} // StockSummary
