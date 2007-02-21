/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountReport;
import com.bigbank.account.AccountSummary;
import com.bigbank.account.StockSummary;

import commonj.sdo.Type;

import java.util.Collection;
import java.util.List;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Report</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.AccountReportImpl#getAccountSummaries <em>Account Summaries</em>}</li>
 *   <li>{@link com.bigbank.account.impl.AccountReportImpl#getStockSummaries <em>Stock Summaries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountReportImpl extends DataObjectBase implements AccountReport
{
  /**
   * The feature id for the '<em><b>Account Summaries</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACCOUNT_SUMMARIES = 0;

  /**
   * The feature id for the '<em><b>Stock Summaries</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int STOCK_SUMMARIES = 1;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 2;

  /**
   * The cached value of the '{@link #getAccountSummaries() <em>Account Summaries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountSummaries()
   * @generated
   * @ordered
   */
  
  protected List accountSummaries = null;
  
  /**
   * The cached value of the '{@link #getStockSummaries() <em>Stock Summaries</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStockSummaries()
   * @generated
   * @ordered
   */
  
  protected List stockSummaries = null;
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountReportImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getAccountReport();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List getAccountSummaries()
  {
    if (accountSummaries == null)
    {
      accountSummaries = createPropertyList(ListKind.CONTAINMENT, AccountSummary.class, ACCOUNT_SUMMARIES);
    }
    return accountSummaries;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List getStockSummaries()
  {
    if (stockSummaries == null)
    {
      stockSummaries = createPropertyList(ListKind.CONTAINMENT, StockSummary.class, STOCK_SUMMARIES);
    }
    return stockSummaries;
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
      case ACCOUNT_SUMMARIES:
        return removeFromList(getAccountSummaries(), otherEnd, changeContext);
      case STOCK_SUMMARIES:
        return removeFromList(getStockSummaries(), otherEnd, changeContext);
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
      case ACCOUNT_SUMMARIES:
        return getAccountSummaries();
      case STOCK_SUMMARIES:
        return getStockSummaries();
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
      case ACCOUNT_SUMMARIES:
        getAccountSummaries().clear();
        getAccountSummaries().addAll((Collection)newValue);
        return;
      case STOCK_SUMMARIES:
        getStockSummaries().clear();
        getStockSummaries().addAll((Collection)newValue);
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
      case ACCOUNT_SUMMARIES:
        getAccountSummaries().clear();
        return;
      case STOCK_SUMMARIES:
        getStockSummaries().clear();
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
      case ACCOUNT_SUMMARIES:
        return accountSummaries != null && !accountSummaries.isEmpty();
      case STOCK_SUMMARIES:
        return stockSummaries != null && !stockSummaries.isEmpty();
    }
    return super.isSet(propertyIndex);
  }

} //AccountReportImpl
