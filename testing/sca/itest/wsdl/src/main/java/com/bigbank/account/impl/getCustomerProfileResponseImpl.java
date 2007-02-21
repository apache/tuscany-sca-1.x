/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.CustomerProfileData;
import com.bigbank.account.getCustomerProfileResponse;

import commonj.sdo.Type;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>get Customer Profile Response</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.bigbank.account.impl.getCustomerProfileResponseImpl#getCustomerProfile <em>Customer Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class getCustomerProfileResponseImpl extends DataObjectBase implements getCustomerProfileResponse
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
   * This represents the number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  
  public final static int SDO_PROPERTY_COUNT = 1;

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
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getCustomerProfileResponseImpl()
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
    return ((AccountFactoryImpl)AccountFactory.INSTANCE).getgetCustomerProfileResponse();
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
    }
    return super.isSet(propertyIndex);
  }

} //getCustomerProfileResponseImpl
