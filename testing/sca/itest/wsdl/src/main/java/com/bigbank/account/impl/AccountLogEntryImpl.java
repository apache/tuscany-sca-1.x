/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountLogEntry;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Log Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.AccountLogEntryImpl#getLogSeqNo <em>Log Seq No</em>}</li>
 *   <li>{@link com.bigbank.account.impl.AccountLogEntryImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.bigbank.account.impl.AccountLogEntryImpl#getAccountNumber <em>Account Number</em>}</li>
 *   <li>{@link com.bigbank.account.impl.AccountLogEntryImpl#getActionType <em>Action Type</em>}</li>
 *   <li>{@link com.bigbank.account.impl.AccountLogEntryImpl#getAmount <em>Amount</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountLogEntryImpl extends DataObjectBase implements AccountLogEntry
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
   * The feature id for the '<em><b>Account Number</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACCOUNT_NUMBER = 2;

  /**
   * The feature id for the '<em><b>Action Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ACTION_TYPE = 3;

  /**
   * The feature id for the '<em><b>Amount</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int AMOUNT = 4;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 5;

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
   * The default value of the '{@link #getAmount() <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAmount()
   * @generated
   * @ordered
   */
  protected static final float AMOUNT_DEFAULT_ = 0.0F;

  /**
   * The cached value of the '{@link #getAmount() <em>Amount</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAmount()
   * @generated
   * @ordered
   */
  protected float amount = AMOUNT_DEFAULT_;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountLogEntryImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getAccountLogEntry();
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
  public float getAmount()
  {
    return amount;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAmount(float newAmount)
  {
    amount = newAmount;
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
      case ACCOUNT_NUMBER:
        return getAccountNumber();
      case ACTION_TYPE:
        return getActionType();
      case AMOUNT:
        return new Float(getAmount());
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
      case ACCOUNT_NUMBER:
        setAccountNumber((String)newValue);
        return;
      case ACTION_TYPE:
        setActionType((String)newValue);
        return;
      case AMOUNT:
        setAmount(((Float)newValue).floatValue());
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
      case ACCOUNT_NUMBER:
        setAccountNumber(ACCOUNT_NUMBER_DEFAULT_);
        return;
      case ACTION_TYPE:
        setActionType(ACTION_TYPE_DEFAULT_);
        return;
      case AMOUNT:
        setAmount(AMOUNT_DEFAULT_);
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
      case ACCOUNT_NUMBER:
        return ACCOUNT_NUMBER_DEFAULT_ == null ? accountNumber != null : !ACCOUNT_NUMBER_DEFAULT_.equals(accountNumber);
      case ACTION_TYPE:
        return ACTION_TYPE_DEFAULT_ == null ? actionType != null : !ACTION_TYPE_DEFAULT_.equals(actionType);
      case AMOUNT:
        return amount != AMOUNT_DEFAULT_;
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
    result.append(", accountNumber: ");
    result.append(accountNumber);
    result.append(", actionType: ");
    result.append(actionType);
    result.append(", amount: ");
    result.append(amount);
    result.append(')');
    return result.toString();
  }

} //AccountLogEntryImpl
