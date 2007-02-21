/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.CustomerProfileData;
import com.bigbank.account.createAccount;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>create Account</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.createAccountImpl#getCustomerProfile <em>Customer Profile</em>}</li>
 *   <li>{@link com.bigbank.account.impl.createAccountImpl#isCreateSavings <em>Create Savings</em>}</li>
 *   <li>{@link com.bigbank.account.impl.createAccountImpl#isCreateCheckings <em>Create Checkings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class createAccountImpl extends DataObjectBase implements createAccount
{
  /**
   * The feature id for the '<em><b>Customer Profile</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int CUSTOMER_PROFILE = 0;

  /**
   * The feature id for the '<em><b>Create Savings</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int CREATE_SAVINGS = 1;

  /**
   * The feature id for the '<em><b>Create Checkings</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */	 
  public final static int CREATE_CHECKINGS = 2;

  /**
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 3;

  /**
   * The cached value of the '{@link #getCustomerProfile() <em>Customer Profile</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCustomerProfile()
   * @generated
   * @ordered
   */
  
  protected CustomerProfileData customerProfile = null;
  
  /**
   * The default value of the '{@link #isCreateSavings() <em>Create Savings</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isCreateSavings()
   * @generated
   * @ordered
   */
  protected static final boolean CREATE_SAVINGS_DEFAULT_ = false;

  /**
   * The cached value of the '{@link #isCreateSavings() <em>Create Savings</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isCreateSavings()
   * @generated
   * @ordered
   */
  protected boolean createSavings = CREATE_SAVINGS_DEFAULT_;

  /**
   * The default value of the '{@link #isCreateCheckings() <em>Create Checkings</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isCreateCheckings()
   * @generated
   * @ordered
   */
  protected static final boolean CREATE_CHECKINGS_DEFAULT_ = false;

  /**
   * The cached value of the '{@link #isCreateCheckings() <em>Create Checkings</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isCreateCheckings()
   * @generated
   * @ordered
   */
  protected boolean createCheckings = CREATE_CHECKINGS_DEFAULT_;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public createAccountImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getcreateAccount();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CustomerProfileData getCustomerProfile()
  {
    return customerProfile;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChangeContext basicSetCustomerProfile(CustomerProfileData newCustomerProfile, ChangeContext changeContext)
  {
    CustomerProfileData oldCustomerProfile = customerProfile;
    customerProfile = newCustomerProfile;
    return changeContext;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCustomerProfile(CustomerProfileData newCustomerProfile)
  {
    if (newCustomerProfile != customerProfile)
    {
      ChangeContext changeContext = null;
      if (customerProfile != null)
        changeContext = inverseRemove(customerProfile, this, OPPOSITE_FEATURE_BASE - CUSTOMER_PROFILE, null, changeContext);
      if (newCustomerProfile != null)
        changeContext = inverseAdd(newCustomerProfile, this, OPPOSITE_FEATURE_BASE - CUSTOMER_PROFILE, null, changeContext);
      changeContext = basicSetCustomerProfile(newCustomerProfile, changeContext);
      if (changeContext != null) dispatch(changeContext);
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isCreateSavings()
  {
    return createSavings;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCreateSavings(boolean newCreateSavings)
  {
    createSavings = newCreateSavings;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isCreateCheckings()
  {
    return createCheckings;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCreateCheckings(boolean newCreateCheckings)
  {
    createCheckings = newCreateCheckings;
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
      case CUSTOMER_PROFILE:
        return basicSetCustomerProfile(null, changeContext);
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
      case CUSTOMER_PROFILE:
        return getCustomerProfile();
      case CREATE_SAVINGS:
        return isCreateSavings() ? Boolean.TRUE : Boolean.FALSE;
      case CREATE_CHECKINGS:
        return isCreateCheckings() ? Boolean.TRUE : Boolean.FALSE;
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
      case CUSTOMER_PROFILE:
        setCustomerProfile((CustomerProfileData)newValue);
        return;
      case CREATE_SAVINGS:
        setCreateSavings(((Boolean)newValue).booleanValue());
        return;
      case CREATE_CHECKINGS:
        setCreateCheckings(((Boolean)newValue).booleanValue());
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
      case CUSTOMER_PROFILE:
        setCustomerProfile((CustomerProfileData)null);
        return;
      case CREATE_SAVINGS:
        setCreateSavings(CREATE_SAVINGS_DEFAULT_);
        return;
      case CREATE_CHECKINGS:
        setCreateCheckings(CREATE_CHECKINGS_DEFAULT_);
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
      case CUSTOMER_PROFILE:
        return customerProfile != null;
      case CREATE_SAVINGS:
        return createSavings != CREATE_SAVINGS_DEFAULT_;
      case CREATE_CHECKINGS:
        return createCheckings != CREATE_CHECKINGS_DEFAULT_;
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
    result.append(" (createSavings: ");
    result.append(createSavings);
    result.append(", createCheckings: ");
    result.append(createCheckings);
    result.append(')');
    return result.toString();
  }

} //createAccountImpl
