/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.StockSummary;
import com.bigbank.account.purchaseStockResponse;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>purchase Stock Response</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.purchaseStockResponseImpl#getPurchaseSummary <em>Purchase Summary</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class purchaseStockResponseImpl extends DataObjectBase implements purchaseStockResponse
{
  /**
   * The feature id for the '<em><b>Purchase Summary</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int PURCHASE_SUMMARY = 0;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 1;

  /**
   * The cached value of the '{@link #getPurchaseSummary() <em>Purchase Summary</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPurchaseSummary()
   * @generated
   * @ordered
   */
  
  protected StockSummary purchaseSummary = null;
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public purchaseStockResponseImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getpurchaseStockResponse();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StockSummary getPurchaseSummary()
  {
    return purchaseSummary;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChangeContext basicSetPurchaseSummary(StockSummary newPurchaseSummary, ChangeContext changeContext)
  {
    StockSummary oldPurchaseSummary = purchaseSummary;
    purchaseSummary = newPurchaseSummary;
    return changeContext;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPurchaseSummary(StockSummary newPurchaseSummary)
  {
    if (newPurchaseSummary != purchaseSummary)
    {
      ChangeContext changeContext = null;
      if (purchaseSummary != null)
        changeContext = inverseRemove(purchaseSummary, this, OPPOSITE_FEATURE_BASE - PURCHASE_SUMMARY, null, changeContext);
      if (newPurchaseSummary != null)
        changeContext = inverseAdd(newPurchaseSummary, this, OPPOSITE_FEATURE_BASE - PURCHASE_SUMMARY, null, changeContext);
      changeContext = basicSetPurchaseSummary(newPurchaseSummary, changeContext);
      if (changeContext != null) dispatch(changeContext);
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChangeContext inverseRemove(Object otherEnd, int propertyIndex, ChangeContext changeContext)
  {
    switch (propertyIndex)
    {
      case PURCHASE_SUMMARY:
        return basicSetPurchaseSummary(null, changeContext);
    }
    return super.inverseRemove(otherEnd, propertyIndex, changeContext);
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
      case PURCHASE_SUMMARY:
        return getPurchaseSummary();
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
      case PURCHASE_SUMMARY:
        setPurchaseSummary((StockSummary)newValue);
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
      case PURCHASE_SUMMARY:
        setPurchaseSummary((StockSummary)null);
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
      case PURCHASE_SUMMARY:
        return purchaseSummary != null;
    }
    return super.isSet(propertyIndex);
  }

} //purchaseStockResponseImpl
