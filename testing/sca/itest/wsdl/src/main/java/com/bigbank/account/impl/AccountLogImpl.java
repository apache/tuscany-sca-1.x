/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountLog;
import com.bigbank.account.AccountLogEntry;
import com.bigbank.account.StockLogEntry;

import commonj.sdo.Type;

import java.util.Collection;
import java.util.List;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Log</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.AccountLogImpl#getAccountLogEntries <em>Account Log Entries</em>}</li>
 *   <li>{@link com.bigbank.account.impl.AccountLogImpl#getStockLogEntries <em>Stock Log Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountLogImpl extends DataObjectBase implements AccountLog
{
  /**
   * The feature id for the '<em><b>Account Log Entries</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACCOUNT_LOG_ENTRIES = 0;

  /**
   * The feature id for the '<em><b>Stock Log Entries</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int STOCK_LOG_ENTRIES = 1;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 2;

  /**
   * The cached value of the '{@link #getAccountLogEntries() <em>Account Log Entries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountLogEntries()
   * @generated
   * @ordered
   */
  
  protected List accountLogEntries = null;
  
  /**
   * The cached value of the '{@link #getStockLogEntries() <em>Stock Log Entries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStockLogEntries()
   * @generated
   * @ordered
   */
  
  protected List stockLogEntries = null;
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountLogImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getAccountLog();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List getAccountLogEntries()
  {
    if (accountLogEntries == null)
    {
      accountLogEntries = createPropertyList(ListKind.CONTAINMENT, AccountLogEntry.class, ACCOUNT_LOG_ENTRIES);
    }
    return accountLogEntries;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List getStockLogEntries()
  {
    if (stockLogEntries == null)
    {
      stockLogEntries = createPropertyList(ListKind.CONTAINMENT, StockLogEntry.class, STOCK_LOG_ENTRIES);
    }
    return stockLogEntries;
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
      case ACCOUNT_LOG_ENTRIES:
        return removeFromList(getAccountLogEntries(), otherEnd, changeContext);
      case STOCK_LOG_ENTRIES:
        return removeFromList(getStockLogEntries(), otherEnd, changeContext);
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
      case ACCOUNT_LOG_ENTRIES:
        return getAccountLogEntries();
      case STOCK_LOG_ENTRIES:
        return getStockLogEntries();
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
      case ACCOUNT_LOG_ENTRIES:
        getAccountLogEntries().clear();
        getAccountLogEntries().addAll((Collection)newValue);
        return;
      case STOCK_LOG_ENTRIES:
        getStockLogEntries().clear();
        getStockLogEntries().addAll((Collection)newValue);
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
      case ACCOUNT_LOG_ENTRIES:
        getAccountLogEntries().clear();
        return;
      case STOCK_LOG_ENTRIES:
        getStockLogEntries().clear();
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
      case ACCOUNT_LOG_ENTRIES:
        return accountLogEntries != null && !accountLogEntries.isEmpty();
      case STOCK_LOG_ENTRIES:
        return stockLogEntries != null && !stockLogEntries.isEmpty();
    }
    return super.isSet(propertyIndex);
  }

} //AccountLogImpl
