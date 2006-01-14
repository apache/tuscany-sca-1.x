/**
 * <copyright>
 *
 * Service Data Objects
 * Version 2.0
 * Licensed Materials - Property of BEA and IBM
 *
 * (c) Copyright BEA Systems, Inc. and International Business Machines Corp 2005.  All rights reserved.
 *
 * </copyright>
 * 
 */

package commonj.sdo.impl;


import commonj.sdo.helper.CopyHelper;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.DataHelper;
import commonj.sdo.helper.EqualityHelper;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XMLHelper;
import commonj.sdo.helper.XSDHelper;
import commonj.sdo.impl.ExternalizableDelegator.Resolvable;

/**
 * This class instantiates a HelperProviderImpl that returns concrete helpers.
 * This class may be replaced by another implementation.
 */
public abstract class HelperProvider
{
  static HelperProvider INSTANCE = getHelperProviderImpl();
  
  static HelperProvider getHelperProviderImpl() 
  {
    try
    {
      return (HelperProvider)Class.forName("commonj.sdo.impl.HelperProviderImpl").newInstance();
    } catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  } // end method getHelperProvider()
  
  HelperProvider()
  {
  }
  
  /**
   * Gets a CopyHelper
   * @return a CopyHelper object
   */
  public static CopyHelper getCopyHelper()
  {
    return INSTANCE.copyHelper();
  }

  /**
   * Gets a DataFactory
   * @return a DataFactory object
   */
  public static DataFactory getDataFactory()
  {
    return INSTANCE.dataFactory();
  }

  /**
   * Gets a DataHelper
   * @return a DataHelper object
   */
  public static DataHelper getDataHelper()
  {
    return INSTANCE.dataHelper();
  }

  /**
   * Gets an EqualityHelper
   * @return an EqualityHelper object
   */
  public static EqualityHelper getEqualityHelper()
  {
    return INSTANCE.equalityHelper();
  }
  
  /**
   * Gets a TypeHelper 
   * @return a TypeHelper object
   */
  public static TypeHelper getTypeHelper()
  {
    return INSTANCE.typeHelper();
  }

  /**
   * Gets an XMLHelper
   * @return an XMLHelper object
   */
  public static XMLHelper getXMLHelper()
  {
    return INSTANCE.xmlHelper();
  }
  
  /**
   * Gets an XSDHelper 
   * @return an XSDHelper object
   */
  public static XSDHelper getXSDHelper()
  {
    return INSTANCE.xsdHelper();
  }

  /**
   * Gets a Resolvable 
   * @return a Resolvable object
   * @see ExternalizableDelegator
   */
  public static Resolvable createResolvable()
  {
    return INSTANCE.resolvable();
  }
  
  /**
   * Gets a Resolvable 
   * @param target the target object for the Resolvable
   * @return a Resolvable object
   * @see ExternalizableDelegator
   */
  public static Resolvable createResolvable(Object target)
  {
    return INSTANCE.resolvable(target);
  }

  abstract CopyHelper copyHelper();
  abstract DataFactory dataFactory();
  abstract DataHelper dataHelper();
  abstract EqualityHelper equalityHelper();
  abstract TypeHelper typeHelper();
  abstract XMLHelper xmlHelper();
  abstract XSDHelper xsdHelper();
  abstract Resolvable resolvable();
  abstract Resolvable resolvable(Object target);
}
