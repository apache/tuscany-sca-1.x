/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.CustomerProfileData;

import commonj.sdo.Type;

import org.apache.tuscany.api.annotation.DataType;
import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Customer Profile Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.CustomerProfileDataImpl#getFirstName <em>First Name</em>}</li>
 *   <li>{@link com.bigbank.account.impl.CustomerProfileDataImpl#getLastName <em>Last Name</em>}</li>
 *   <li>{@link com.bigbank.account.impl.CustomerProfileDataImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link com.bigbank.account.impl.CustomerProfileDataImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link com.bigbank.account.impl.CustomerProfileDataImpl#getLoginID <em>Login ID</em>}</li>
 *   <li>{@link com.bigbank.account.impl.CustomerProfileDataImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link com.bigbank.account.impl.CustomerProfileDataImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CustomerProfileDataImpl extends DataObjectBase implements CustomerProfileData
{
  /**
   * The feature id for the '<em><b>First Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int FIRST_NAME = 0;

  /**
   * The feature id for the '<em><b>Last Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int LAST_NAME = 1;

  /**
   * The feature id for the '<em><b>Address</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ADDRESS = 2;

  /**
   * The feature id for the '<em><b>Email</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int EMAIL = 3;

  /**
   * The feature id for the '<em><b>Login ID</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int LOGIN_ID = 4;

  /**
   * The feature id for the '<em><b>Password</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int PASSWORD = 5;

  /**
   * The feature id for the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int ID = 6;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 7;

  /**
   * The default value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstName()
   * @generated
   * @ordered
   */
  protected static final String FIRST_NAME_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFirstName()
   * @generated
   * @ordered
   */
  protected String firstName = FIRST_NAME_DEFAULT_;

  /**
   * The default value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLastName()
   * @generated
   * @ordered
   */
  protected static final String LAST_NAME_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLastName()
   * @generated
   * @ordered
   */
  protected String lastName = LAST_NAME_DEFAULT_;

  /**
   * The default value of the '{@link #getAddress() <em>Address</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAddress()
   * @generated
   * @ordered
   */
  protected static final String ADDRESS_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getAddress() <em>Address</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAddress()
   * @generated
   * @ordered
   */
  protected String address = ADDRESS_DEFAULT_;

  /**
   * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEmail()
   * @generated
   * @ordered
   */
  protected static final String EMAIL_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEmail()
   * @generated
   * @ordered
   */
  protected String email = EMAIL_DEFAULT_;

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
   * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPassword()
   * @generated
   * @ordered
   */
  protected static final String PASSWORD_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPassword()
   * @generated
   * @ordered
   */
  protected String password = PASSWORD_DEFAULT_;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CustomerProfileDataImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getCustomerProfileData();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFirstName()
  {
    return firstName;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFirstName(String newFirstName)
  {
    firstName = newFirstName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLastName()
  {
    return lastName;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLastName(String newLastName)
  {
    lastName = newLastName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getAddress()
  {
    return address;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAddress(String newAddress)
  {
    address = newAddress;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getEmail()
  {
    return email;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEmail(String newEmail)
  {
    email = newEmail;
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
  public String getPassword()
  {
    return password;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPassword(String newPassword)
  {
    password = newPassword;
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
  public Object get(int propertyIndex, boolean resolve)
  {
    switch (propertyIndex)
    {
      case FIRST_NAME:
        return getFirstName();
      case LAST_NAME:
        return getLastName();
      case ADDRESS:
        return getAddress();
      case EMAIL:
        return getEmail();
      case LOGIN_ID:
        return getLoginID();
      case PASSWORD:
        return getPassword();
      case ID:
        return new Integer(getId());
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
      case FIRST_NAME:
        setFirstName((String)newValue);
        return;
      case LAST_NAME:
        setLastName((String)newValue);
        return;
      case ADDRESS:
        setAddress((String)newValue);
        return;
      case EMAIL:
        setEmail((String)newValue);
        return;
      case LOGIN_ID:
        setLoginID((String)newValue);
        return;
      case PASSWORD:
        setPassword((String)newValue);
        return;
      case ID:
        setId(((Integer)newValue).intValue());
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
      case FIRST_NAME:
        setFirstName(FIRST_NAME_DEFAULT_);
        return;
      case LAST_NAME:
        setLastName(LAST_NAME_DEFAULT_);
        return;
      case ADDRESS:
        setAddress(ADDRESS_DEFAULT_);
        return;
      case EMAIL:
        setEmail(EMAIL_DEFAULT_);
        return;
      case LOGIN_ID:
        setLoginID(LOGIN_ID_DEFAULT_);
        return;
      case PASSWORD:
        setPassword(PASSWORD_DEFAULT_);
        return;
      case ID:
        setId(ID_DEFAULT_);
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
      case FIRST_NAME:
        return FIRST_NAME_DEFAULT_ == null ? firstName != null : !FIRST_NAME_DEFAULT_.equals(firstName);
      case LAST_NAME:
        return LAST_NAME_DEFAULT_ == null ? lastName != null : !LAST_NAME_DEFAULT_.equals(lastName);
      case ADDRESS:
        return ADDRESS_DEFAULT_ == null ? address != null : !ADDRESS_DEFAULT_.equals(address);
      case EMAIL:
        return EMAIL_DEFAULT_ == null ? email != null : !EMAIL_DEFAULT_.equals(email);
      case LOGIN_ID:
        return LOGIN_ID_DEFAULT_ == null ? loginID != null : !LOGIN_ID_DEFAULT_.equals(loginID);
      case PASSWORD:
        return PASSWORD_DEFAULT_ == null ? password != null : !PASSWORD_DEFAULT_.equals(password);
      case ID:
        return id != ID_DEFAULT_;
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
    result.append(" (firstName: ");
    result.append(firstName);
    result.append(", lastName: ");
    result.append(lastName);
    result.append(", address: ");
    result.append(address);
    result.append(", email: ");
    result.append(email);
    result.append(", loginID: ");
    result.append(loginID);
    result.append(", password: ");
    result.append(password);
    result.append(", id: ");
    result.append(id);
    result.append(')');
    return result.toString();
  }

} //CustomerProfileDataImpl
