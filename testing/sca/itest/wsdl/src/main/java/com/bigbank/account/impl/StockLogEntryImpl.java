/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.StockLogEntry;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stock Log Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.StockLogEntryImpl#getLogSeqNo <em>Log Seq No</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockLogEntryImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockLogEntryImpl#getSymbol <em>Symbol</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockLogEntryImpl#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockLogEntryImpl#getActionType <em>Action Type</em>}</li>
 *   <li>{@link com.bigbank.account.impl.StockLogEntryImpl#getPurchaseLotNumber <em>Purchase Lot Number</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StockLogEntryImpl extends DataObjectBase implements StockLogEntry
{
  /**
   * The feature id for the '<em><b>Log Seq No</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int LOG_SEQ_NO = 0;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ID = 1;

  /**
   * The feature id for the '<em><b>Symbol</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int SYMBOL = 2;

  /**
   * The feature id for the '<em><b>Quantity</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int QUANTITY = 3;

  /**
   * The feature id for the '<em><b>Action Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACTION_TYPE = 4;

  /**
   * The feature id for the '<em><b>Purchase Lot Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int PURCHASE_LOT_NUMBER = 5;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 6;

  /**
   * The default value of the '{@link #getLogSeqNo() <em>Log Seq No</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLogSeqNo()
   * @generated
   * @ordered
   */
  protected static final int LOG_SEQ_NO_DEFAULT_ = 0;

  /**
   * The cached value of the '{@link #getLogSeqNo() <em>Log Seq No</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLogSeqNo()
   * @generated
   * @ordered
   */
  protected int logSeqNo = LOG_SEQ_NO_DEFAULT_;

  /**
   * The default value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected static final int ID_DEFAULT_ = 0;

  /**
   * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getId()
   * @generated
   * @ordered
   */
  protected int id = ID_DEFAULT_;

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
   * The default value of the '{@link #getActionType() <em>Action Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getActionType()
   * @generated
   * @ordered
   */
  protected static final String ACTION_TYPE_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getActionType() <em>Action Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getActionType()
   * @generated
   * @ordered
   */
  protected String actionType = ACTION_TYPE_DEFAULT_;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StockLogEntryImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getStockLogEntry();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getLogSeqNo()
  {
    return logSeqNo;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLogSeqNo(int newLogSeqNo)
  {
    logSeqNo = newLogSeqNo;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getId()
  {
    return id;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setId(int newId)
  {
    id = newId;
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
  public String getActionType()
  {
    return actionType;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setActionType(String newActionType)
  {
    actionType = newActionType;
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
  public Object get(int propertyIndex, boolean resolve)
  {
    switch (propertyIndex)
    {
      case LOG_SEQ_NO:
        return new Integer(getLogSeqNo());
      case ID:
        return new Integer(getId());
      case SYMBOL:
        return getSymbol();
      case QUANTITY:
        return new Integer(getQuantity());
      case ACTION_TYPE:
        return getActionType();
      case PURCHASE_LOT_NUMBER:
        return new Integer(getPurchaseLotNumber());
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
      case LOG_SEQ_NO:
        setLogSeqNo(((Integer)newValue).intValue());
        return;
      case ID:
        setId(((Integer)newValue).intValue());
        return;
      case SYMBOL:
        setSymbol((String)newValue);
        return;
      case QUANTITY:
        setQuantity(((Integer)newValue).intValue());
        return;
      case ACTION_TYPE:
        setActionType((String)newValue);
        return;
      case PURCHASE_LOT_NUMBER:
        setPurchaseLotNumber(((Integer)newValue).intValue());
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
      case LOG_SEQ_NO:
        setLogSeqNo(LOG_SEQ_NO_DEFAULT_);
        return;
      case ID:
        setId(ID_DEFAULT_);
        return;
      case SYMBOL:
        setSymbol(SYMBOL_DEFAULT_);
        return;
      case QUANTITY:
        setQuantity(QUANTITY_DEFAULT_);
        return;
      case ACTION_TYPE:
        setActionType(ACTION_TYPE_DEFAULT_);
        return;
      case PURCHASE_LOT_NUMBER:
        setPurchaseLotNumber(PURCHASE_LOT_NUMBER_DEFAULT_);
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
      case LOG_SEQ_NO:
        return logSeqNo != LOG_SEQ_NO_DEFAULT_;
      case ID:
        return id != ID_DEFAULT_;
      case SYMBOL:
        return SYMBOL_DEFAULT_ == null ? symbol != null : !SYMBOL_DEFAULT_.equals(symbol);
      case QUANTITY:
        return quantity != QUANTITY_DEFAULT_;
      case ACTION_TYPE:
        return ACTION_TYPE_DEFAULT_ == null ? actionType != null : !ACTION_TYPE_DEFAULT_.equals(actionType);
      case PURCHASE_LOT_NUMBER:
        return purchaseLotNumber != PURCHASE_LOT_NUMBER_DEFAULT_;
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
    result.append(" (logSeqNo: ");
    result.append(logSeqNo);
    result.append(", id: ");
    result.append(id);
    result.append(", symbol: ");
    result.append(symbol);
    result.append(", quantity: ");
    result.append(quantity);
    result.append(", actionType: ");
    result.append(actionType);
    result.append(", purchaseLotNumber: ");
    result.append(purchaseLotNumber);
    result.append(')');
    return result.toString();
  }

} //StockLogEntryImpl
