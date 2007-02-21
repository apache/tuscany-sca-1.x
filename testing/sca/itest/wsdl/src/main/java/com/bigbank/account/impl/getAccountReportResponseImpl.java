/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountReport;
import com.bigbank.account.getAccountReportResponse;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>get Account Report Response</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.getAccountReportResponseImpl#getAccountReport <em>Account Report</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class getAccountReportResponseImpl extends DataObjectBase implements getAccountReportResponse
{
  /**
   * The feature id for the '<em><b>Account Report</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACCOUNT_REPORT = 0;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 1;

  /**
   * The cached value of the '{@link #getAccountReport() <em>Account Report</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountReport()
   * @generated
   * @ordered
   */
  
  protected AccountReport accountReport = null;
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getAccountReportResponseImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getgetAccountReportResponse();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountReport getAccountReport()
  {
    return accountReport;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChangeContext basicSetAccountReport(AccountReport newAccountReport, ChangeContext changeContext)
  {
    AccountReport oldAccountReport = accountReport;
    accountReport = newAccountReport;
    return changeContext;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAccountReport(AccountReport newAccountReport)
  {
    if (newAccountReport != accountReport)
    {
      ChangeContext changeContext = null;
      if (accountReport != null)
        changeContext = inverseRemove(accountReport, this, OPPOSITE_FEATURE_BASE - ACCOUNT_REPORT, null, changeContext);
      if (newAccountReport != null)
        changeContext = inverseAdd(newAccountReport, this, OPPOSITE_FEATURE_BASE - ACCOUNT_REPORT, null, changeContext);
      changeContext = basicSetAccountReport(newAccountReport, changeContext);
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
      case ACCOUNT_REPORT:
        return basicSetAccountReport(null, changeContext);
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
      case ACCOUNT_REPORT:
        return getAccountReport();
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
      case ACCOUNT_REPORT:
        setAccountReport((AccountReport)newValue);
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
      case ACCOUNT_REPORT:
        setAccountReport((AccountReport)null);
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
      case ACCOUNT_REPORT:
        return accountReport != null;
    }
    return super.isSet(propertyIndex);
  }

} //getAccountReportResponseImpl
