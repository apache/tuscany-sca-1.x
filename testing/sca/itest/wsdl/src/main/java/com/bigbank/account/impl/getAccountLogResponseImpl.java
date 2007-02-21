/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountLog;
import com.bigbank.account.getAccountLogResponse;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>get Account Log Response</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.getAccountLogResponseImpl#getAccountLog <em>Account Log</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class getAccountLogResponseImpl extends DataObjectBase implements getAccountLogResponse
{
  /**
   * The feature id for the '<em><b>Account Log</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACCOUNT_LOG = 0;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 1;

  /**
   * The cached value of the '{@link #getAccountLog() <em>Account Log</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountLog()
   * @generated
   * @ordered
   */
  
  protected AccountLog accountLog = null;
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getAccountLogResponseImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getgetAccountLogResponse();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountLog getAccountLog()
  {
    return accountLog;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChangeContext basicSetAccountLog(AccountLog newAccountLog, ChangeContext changeContext)
  {
    AccountLog oldAccountLog = accountLog;
    accountLog = newAccountLog;
    return changeContext;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAccountLog(AccountLog newAccountLog)
  {
    if (newAccountLog != accountLog)
    {
      ChangeContext changeContext = null;
      if (accountLog != null)
        changeContext = inverseRemove(accountLog, this, OPPOSITE_FEATURE_BASE - ACCOUNT_LOG, null, changeContext);
      if (newAccountLog != null)
        changeContext = inverseAdd(newAccountLog, this, OPPOSITE_FEATURE_BASE - ACCOUNT_LOG, null, changeContext);
      changeContext = basicSetAccountLog(newAccountLog, changeContext);
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
      case ACCOUNT_LOG:
        return basicSetAccountLog(null, changeContext);
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
      case ACCOUNT_LOG:
        return getAccountLog();
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
      case ACCOUNT_LOG:
        setAccountLog((AccountLog)newValue);
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
      case ACCOUNT_LOG:
        setAccountLog((AccountLog)null);
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
      case ACCOUNT_LOG:
        return accountLog != null;
    }
    return super.isSet(propertyIndex);
  }

} //getAccountLogResponseImpl
