/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountSummary;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Summary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.AccountSummaryImpl#getAccountNumber <em>Account Number</em>}</li>
 *   <li>{@link com.bigbank.account.impl.AccountSummaryImpl#getAccountType <em>Account Type</em>}</li>
 *   <li>{@link com.bigbank.account.impl.AccountSummaryImpl#getBalance <em>Balance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountSummaryImpl extends DataObjectBase implements AccountSummary
{
  /**
   * The feature id for the '<em><b>Account Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACCOUNT_NUMBER = 0;

  /**
   * The feature id for the '<em><b>Account Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACCOUNT_TYPE = 1;

  /**
   * The feature id for the '<em><b>Balance</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int BALANCE = 2;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 3;

  /**
   * The default value of the '{@link #getAccountNumber() <em>Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountNumber()
   * @generated
   * @ordered
   */
  protected static final String ACCOUNT_NUMBER_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getAccountNumber() <em>Account Number</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountNumber()
   * @generated
   * @ordered
   */
  protected String accountNumber = ACCOUNT_NUMBER_DEFAULT_;

  /**
   * The default value of the '{@link #getAccountType() <em>Account Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountType()
   * @generated
   * @ordered
   */
  protected static final String ACCOUNT_TYPE_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getAccountType() <em>Account Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAccountType()
   * @generated
   * @ordered
   */
  protected String accountType = ACCOUNT_TYPE_DEFAULT_;

  /**
   * The default value of the '{@link #getBalance() <em>Balance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBalance()
   * @generated
   * @ordered
   */
  protected static final float BALANCE_DEFAULT_ = 0.0F;

  /**
   * The cached value of the '{@link #getBalance() <em>Balance</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBalance()
   * @generated
   * @ordered
   */
  protected float balance = BALANCE_DEFAULT_;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountSummaryImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getAccountSummary();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAccountNumber()
  {
    return accountNumber;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAccountNumber(String newAccountNumber)
  {
    accountNumber = newAccountNumber;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAccountType()
  {
    return accountType;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAccountType(String newAccountType)
  {
    accountType = newAccountType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public float getBalance()
  {
    return balance;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBalance(float newBalance)
  {
    balance = newBalance;
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
      case ACCOUNT_NUMBER:
        return getAccountNumber();
      case ACCOUNT_TYPE:
        return getAccountType();
      case BALANCE:
        return new Float(getBalance());
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
      case ACCOUNT_NUMBER:
        setAccountNumber((String)newValue);
        return;
      case ACCOUNT_TYPE:
        setAccountType((String)newValue);
        return;
      case BALANCE:
        setBalance(((Float)newValue).floatValue());
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
      case ACCOUNT_NUMBER:
        setAccountNumber(ACCOUNT_NUMBER_DEFAULT_);
        return;
      case ACCOUNT_TYPE:
        setAccountType(ACCOUNT_TYPE_DEFAULT_);
        return;
      case BALANCE:
        setBalance(BALANCE_DEFAULT_);
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
      case ACCOUNT_NUMBER:
        return ACCOUNT_NUMBER_DEFAULT_ == null ? accountNumber != null : !ACCOUNT_NUMBER_DEFAULT_.equals(accountNumber);
      case ACCOUNT_TYPE:
        return ACCOUNT_TYPE_DEFAULT_ == null ? accountType != null : !ACCOUNT_TYPE_DEFAULT_.equals(accountType);
      case BALANCE:
        return balance != BALANCE_DEFAULT_;
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
    result.append(" (accountNumber: ");
    result.append(accountNumber);
    result.append(", accountType: ");
    result.append(accountType);
    result.append(", balance: ");
    result.append(balance);
    result.append(')');
    return result.toString();
  }

} //AccountSummaryImpl
