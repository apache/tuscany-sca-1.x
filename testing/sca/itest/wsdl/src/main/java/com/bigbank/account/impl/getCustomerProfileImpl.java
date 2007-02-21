/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.getCustomerProfile;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>get Customer Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.getCustomerProfileImpl#getLoginID <em>Login ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class getCustomerProfileImpl extends DataObjectBase implements getCustomerProfile
{
  /**
   * The feature id for the '<em><b>Login ID</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int LOGIN_ID = 0;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 1;

  /**
   * The default value of the '{@link #getLoginID() <em>Login ID</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLoginID()
   * @generated
   * @ordered
   */
  protected static final String LOGIN_ID_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getLoginID() <em>Login ID</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLoginID()
   * @generated
   * @ordered
   */
  protected String loginID = LOGIN_ID_DEFAULT_;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getCustomerProfileImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getgetCustomerProfile();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLoginID()
  {
    return loginID;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLoginID(String newLoginID)
  {
    loginID = newLoginID;
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
      case LOGIN_ID:
        return getLoginID();
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
      case LOGIN_ID:
        setLoginID((String)newValue);
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
      case LOGIN_ID:
        setLoginID(LOGIN_ID_DEFAULT_);
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
      case LOGIN_ID:
        return LOGIN_ID_DEFAULT_ == null ? loginID != null : !LOGIN_ID_DEFAULT_.equals(loginID);
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
    result.append(" (loginID: ");
    result.append(loginID);
    result.append(')');
    return result.toString();
  }

} //getCustomerProfileImpl
