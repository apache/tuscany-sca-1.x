/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.StockSummary;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stock Summary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getPurchaseLotNumber <em>Purchase Lot Number</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getSymbol <em>Symbol</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getPurchaseDate <em>Purchase Date</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getPurchasePrice <em>Purchase Price</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getCurrentPrice <em>Current Price</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getCompany <em>Company</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getHighPrice <em>High Price</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockSummaryImpl#getLowPrice <em>Low Price</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StockSummaryImpl extends DataObjectBase implements StockSummary
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
   * The feature id for the '<em><b>Symbol</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int SYMBOL = 1;

  /**
   * The feature id for the '<em><b>Quantity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int QUANTITY = 2;

  /**
   * The feature id for the '<em><b>Purchase Date</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int PURCHASE_DATE = 3;

  /**
   * The feature id for the '<em><b>Purchase Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int PURCHASE_PRICE = 4;

  /**
   * The feature id for the '<em><b>Current Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int CURRENT_PRICE = 5;

  /**
   * The feature id for the '<em><b>Company</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int COMPANY = 6;

  /**
   * The feature id for the '<em><b>High Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int HIGH_PRICE = 7;

  /**
   * The feature id for the '<em><b>Low Price</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int LOW_PRICE = 8;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 9;

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
   * The default value of the '{@link #getSymbol() <em>Symbol</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSymbol()
   * @generated
   * @ordered
   */
  protected static final String SYMBOL_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getSymbol() <em>Symbol</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSymbol()
   * @generated
   * @ordered
   */
  protected String symbol = SYMBOL_DEFAULT_;

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
   * The default value of the '{@link #getPurchaseDate() <em>Purchase Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPurchaseDate()
   * @generated
   * @ordered
   */
  protected static final String PURCHASE_DATE_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getPurchaseDate() <em>Purchase Date</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPurchaseDate()
   * @generated
   * @ordered
   */
  protected String purchaseDate = PURCHASE_DATE_DEFAULT_;

  /**
   * The default value of the '{@link #getPurchasePrice() <em>Purchase Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPurchasePrice()
   * @generated
   * @ordered
   */
  protected static final float PURCHASE_PRICE_DEFAULT_ = 0.0F;

  /**
   * The cached value of the '{@link #getPurchasePrice() <em>Purchase Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPurchasePrice()
   * @generated
   * @ordered
   */
  protected float purchasePrice = PURCHASE_PRICE_DEFAULT_;

  /**
   * The default value of the '{@link #getCurrentPrice() <em>Current Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentPrice()
   * @generated
   * @ordered
   */
  protected static final float CURRENT_PRICE_DEFAULT_ = 0.0F;

  /**
   * The cached value of the '{@link #getCurrentPrice() <em>Current Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCurrentPrice()
   * @generated
   * @ordered
   */
  protected float currentPrice = CURRENT_PRICE_DEFAULT_;

  /**
   * The default value of the '{@link #getCompany() <em>Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompany()
   * @generated
   * @ordered
   */
  protected static final String COMPANY_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getCompany() <em>Company</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCompany()
   * @generated
   * @ordered
   */
  protected String company = COMPANY_DEFAULT_;

  /**
   * The default value of the '{@link #getHighPrice() <em>High Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHighPrice()
   * @generated
   * @ordered
   */
  protected static final float HIGH_PRICE_DEFAULT_ = 0.0F;

  /**
   * The cached value of the '{@link #getHighPrice() <em>High Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHighPrice()
   * @generated
   * @ordered
   */
  protected float highPrice = HIGH_PRICE_DEFAULT_;

  /**
   * The default value of the '{@link #getLowPrice() <em>Low Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLowPrice()
   * @generated
   * @ordered
   */
  protected static final float LOW_PRICE_DEFAULT_ = 0.0F;

  /**
   * The cached value of the '{@link #getLowPrice() <em>Low Price</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLowPrice()
   * @generated
   * @ordered
   */
  protected float lowPrice = LOW_PRICE_DEFAULT_;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StockSummaryImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getStockSummary();
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
  public String getSymbol()
  {
    return symbol;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSymbol(String newSymbol)
  {
    symbol = newSymbol;
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
  public String getPurchaseDate()
  {
    return purchaseDate;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPurchaseDate(String newPurchaseDate)
  {
    purchaseDate = newPurchaseDate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public float getPurchasePrice()
  {
    return purchasePrice;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPurchasePrice(float newPurchasePrice)
  {
    purchasePrice = newPurchasePrice;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public float getCurrentPrice()
  {
    return currentPrice;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCurrentPrice(float newCurrentPrice)
  {
    currentPrice = newCurrentPrice;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getCompany()
  {
    return company;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCompany(String newCompany)
  {
    company = newCompany;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public float getHighPrice()
  {
    return highPrice;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHighPrice(float newHighPrice)
  {
    highPrice = newHighPrice;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public float getLowPrice()
  {
    return lowPrice;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLowPrice(float newLowPrice)
  {
    lowPrice = newLowPrice;
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
      case SYMBOL:
        return getSymbol();
      case QUANTITY:
        return new Integer(getQuantity());
      case PURCHASE_DATE:
        return getPurchaseDate();
      case PURCHASE_PRICE:
        return new Float(getPurchasePrice());
      case CURRENT_PRICE:
        return new Float(getCurrentPrice());
      case COMPANY:
        return getCompany();
      case HIGH_PRICE:
        return new Float(getHighPrice());
      case LOW_PRICE:
        return new Float(getLowPrice());
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
      case SYMBOL:
        setSymbol((String)newValue);
        return;
      case QUANTITY:
        setQuantity(((Integer)newValue).intValue());
        return;
      case PURCHASE_DATE:
        setPurchaseDate((String)newValue);
        return;
      case PURCHASE_PRICE:
        setPurchasePrice(((Float)newValue).floatValue());
        return;
      case CURRENT_PRICE:
        setCurrentPrice(((Float)newValue).floatValue());
        return;
      case COMPANY:
        setCompany((String)newValue);
        return;
      case HIGH_PRICE:
        setHighPrice(((Float)newValue).floatValue());
        return;
      case LOW_PRICE:
        setLowPrice(((Float)newValue).floatValue());
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
      case SYMBOL:
        setSymbol(SYMBOL_DEFAULT_);
        return;
      case QUANTITY:
        setQuantity(QUANTITY_DEFAULT_);
        return;
      case PURCHASE_DATE:
        setPurchaseDate(PURCHASE_DATE_DEFAULT_);
        return;
      case PURCHASE_PRICE:
        setPurchasePrice(PURCHASE_PRICE_DEFAULT_);
        return;
      case CURRENT_PRICE:
        setCurrentPrice(CURRENT_PRICE_DEFAULT_);
        return;
      case COMPANY:
        setCompany(COMPANY_DEFAULT_);
        return;
      case HIGH_PRICE:
        setHighPrice(HIGH_PRICE_DEFAULT_);
        return;
      case LOW_PRICE:
        setLowPrice(LOW_PRICE_DEFAULT_);
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
      case SYMBOL:
        return SYMBOL_DEFAULT_ == null ? symbol != null : !SYMBOL_DEFAULT_.equals(symbol);
      case QUANTITY:
        return quantity != QUANTITY_DEFAULT_;
      case PURCHASE_DATE:
        return PURCHASE_DATE_DEFAULT_ == null ? purchaseDate != null : !PURCHASE_DATE_DEFAULT_.equals(purchaseDate);
      case PURCHASE_PRICE:
        return purchasePrice != PURCHASE_PRICE_DEFAULT_;
      case CURRENT_PRICE:
        return currentPrice != CURRENT_PRICE_DEFAULT_;
      case COMPANY:
        return COMPANY_DEFAULT_ == null ? company != null : !COMPANY_DEFAULT_.equals(company);
      case HIGH_PRICE:
        return highPrice != HIGH_PRICE_DEFAULT_;
      case LOW_PRICE:
        return lowPrice != LOW_PRICE_DEFAULT_;
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
    result.append(", symbol: ");
    result.append(symbol);
    result.append(", quantity: ");
    result.append(quantity);
    result.append(", purchaseDate: ");
    result.append(purchaseDate);
    result.append(", purchasePrice: ");
    result.append(purchasePrice);
    result.append(", currentPrice: ");
    result.append(currentPrice);
    result.append(", company: ");
    result.append(company);
    result.append(", highPrice: ");
    result.append(highPrice);
    result.append(", lowPrice: ");
    result.append(lowPrice);
    result.append(')');
    return result.toString();
  }

} //StockSummaryImpl
