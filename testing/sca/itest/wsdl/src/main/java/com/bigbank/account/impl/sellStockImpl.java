/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.sellStock;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>sell Stock</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.sellStockImpl#getPurchaseLotNumber <em>Purchase Lot Number</em>}</li>
 *   <li>{@link com.bigbank.account.impl.sellStockImpl#getQuantity <em>Quantity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class sellStockImpl extends DataObjectBase implements sellStock
{
  /**
   * The feature id for the '<em><b>Purchase Lot Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int PURCHASE_LOT_NUMBER = 0;

  /**
   * The feature id for the '<em><b>Quantity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int QUANTITY = 1;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 2;

  /**
   * The default value of the '{@link #getPurchaseLotNumber() <em>Purchase Lot Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPurchaseLotNumber()
   * @generated
   * @ordered
   */
  protected static final int PURCHASE_LOT_NUMBER_DEFAULT_ = 0;

  /**
   * The cached value of the '{@link #getPurchaseLotNumber() <em>Purchase Lot Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPurchaseLotNumber()
   * @generated
   * @ordered
   */
  protected int purchaseLotNumber = PURCHASE_LOT_NUMBER_DEFAULT_;

  /**
   * The default value of the '{@link #getQuantity() <em>Quantity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQuantity()
   * @generated
   * @ordered
   */
  protected static final int QUANTITY_DEFAULT_ = 0;

  /**
   * The cached value of the '{@link #getQuantity() <em>Quantity</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQuantity()
   * @generated
   * @ordered
   */
  protected int quantity = QUANTITY_DEFAULT_;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public sellStockImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type getType()
  {
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getsellStock();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getPurchaseLotNumber()
  {
    return purchaseLotNumber;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPurchaseLotNumber(int newPurchaseLotNumber)
  {
    purchaseLotNumber = newPurchaseLotNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getQuantity()
  {
    return quantity;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setQuantity(int newQuantity)
  {
    quantity = newQuantity;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Object get(int propertyIndex, boolean resolve)
  {
    switch (propertyIndex)
    {
      case PURCHASE_LOT_NUMBER:
        return new Integer(getPurchaseLotNumber());
      case QUANTITY:
        return new Integer(getQuantity());
    }
    return super.get(propertyIndex, resolve);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void set(int propertyIndex, Object newValue)
  {
    switch (propertyIndex)
    {
      case PURCHASE_LOT_NUMBER:
        setPurchaseLotNumber(((Integer)newValue).intValue());
        return;
      case QUANTITY:
        setQuantity(((Integer)newValue).intValue());
        return;
    }
    super.set(propertyIndex, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unset(int propertyIndex)
  {
    switch (propertyIndex)
    {
      case PURCHASE_LOT_NUMBER:
        setPurchaseLotNumber(PURCHASE_LOT_NUMBER_DEFAULT_);
        return;
      case QUANTITY:
        setQuantity(QUANTITY_DEFAULT_);
        return;
    }
    super.unset(propertyIndex);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSet(int propertyIndex)
  {
    switch (propertyIndex)
    {
      case PURCHASE_LOT_NUMBER:
        return purchaseLotNumber != PURCHASE_LOT_NUMBER_DEFAULT_;
      case QUANTITY:
        return quantity != QUANTITY_DEFAULT_;
    }
    return super.isSet(propertyIndex);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String toString()
  {
    if (isProxy(this)) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (purchaseLotNumber: ");
    result.append(purchaseLotNumber);
    result.append(", quantity: ");
    result.append(quantity);
    result.append(')');
    return result.toString();
  }

} //sellStockImpl
