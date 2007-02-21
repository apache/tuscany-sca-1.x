/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.bigbank.account.impl;

import commonj.sdo.helper.HelperContext;
import org.apache.tuscany.sdo.helper.TypeHelperImpl;

import com.bigbank.account.*;

import commonj.sdo.DataObject;
import commonj.sdo.Property;
import commonj.sdo.Type;

import org.apache.tuscany.sdo.SDOFactory;

import org.apache.tuscany.sdo.impl.FactoryBase;

import org.apache.tuscany.sdo.model.ModelFactory;

import org.apache.tuscany.sdo.model.impl.ModelFactoryImpl;

import org.apache.tuscany.sdo.util.SDOUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * Generator information:
 * patternVersion=1.0; -noNotification -noUnsettable
 * <!-- end-user-doc -->
 * @generated
 */
public class AccountFactoryImpl extends FactoryBase implements AccountFactory
{

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final String NAMESPACE_URI = "http://www.bigbank.com/account";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final String NAMESPACE_PREFIX = "account";

  /**
   * The version of the generator pattern used to generate this class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final String PATTERN_VERSION = "1.0";
  
  public static final int ACCOUNT_LOG = 1;	
  public static final int ACCOUNT_LOG_ENTRY = 2;	
  public static final int ACCOUNT_REPORT = 3;	
  public static final int ACCOUNT_SUMMARY = 4;	
  public static final int CREATE_ACCOUNT = 5;	
  public static final int CREATE_ACCOUNT_RESPONSE = 6;	
  public static final int CUSTOMER_PROFILE_DATA = 7;	
  public static final int DEPOSIT = 8;	
  public static final int DEPOSIT_RESPONSE = 9;	
  public static final int GET_ACCOUNT_LOG = 10;	
  public static final int GET_ACCOUNT_LOG_RESPONSE = 11;	
  public static final int GET_ACCOUNT_REPORT = 12;	
  public static final int GET_ACCOUNT_REPORT_RESPONSE = 13;	
  public static final int GET_CUSTOMER_PROFILE = 14;	
  public static final int GET_CUSTOMER_PROFILE_RESPONSE = 15;	
  public static final int PURCHASE_STOCK = 16;	
  public static final int PURCHASE_STOCK_RESPONSE = 17;	
  public static final int SELL_STOCK = 18;	
  public static final int STOCK_LOG_ENTRY = 19;	
  public static final int STOCK_SUMMARY = 20;	
  public static final int WITHDRAW = 21;	
  public static final int WITHDRAW_RESPONSE = 22;
  
  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountFactoryImpl()
  {
    super(NAMESPACE_URI, NAMESPACE_PREFIX, "com.bigbank.account");
  }

  /**
   * Registers the Factory instance so that it is available within the supplied scope.
   * @argument scope a HelperContext instance that will make the types supported by this Factory available.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */	
  public void register(HelperContext scope) {
    if(scope == null) {
       throw new IllegalArgumentException("Scope can not be null");
    } 
    TypeHelperImpl th = (TypeHelperImpl)scope.getTypeHelper();
    th.getExtendedMetaData().putPackage(NAMESPACE_URI, this);
  }
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DataObject create(int typeNumber)
  {
    switch (typeNumber)
    {
      case ACCOUNT_LOG: return (DataObject)createAccountLog();
      case ACCOUNT_LOG_ENTRY: return (DataObject)createAccountLogEntry();
      case ACCOUNT_REPORT: return (DataObject)createAccountReport();
      case ACCOUNT_SUMMARY: return (DataObject)createAccountSummary();
      case CREATE_ACCOUNT: return (DataObject)createcreateAccount();
      case CREATE_ACCOUNT_RESPONSE: return (DataObject)createcreateAccountResponse();
      case CUSTOMER_PROFILE_DATA: return (DataObject)createCustomerProfileData();
      case DEPOSIT: return (DataObject)createdeposit();
      case DEPOSIT_RESPONSE: return (DataObject)createdepositResponse();
      case GET_ACCOUNT_LOG: return (DataObject)creategetAccountLog();
      case GET_ACCOUNT_LOG_RESPONSE: return (DataObject)creategetAccountLogResponse();
      case GET_ACCOUNT_REPORT: return (DataObject)creategetAccountReport();
      case GET_ACCOUNT_REPORT_RESPONSE: return (DataObject)creategetAccountReportResponse();
      case GET_CUSTOMER_PROFILE: return (DataObject)creategetCustomerProfile();
      case GET_CUSTOMER_PROFILE_RESPONSE: return (DataObject)creategetCustomerProfileResponse();
      case PURCHASE_STOCK: return (DataObject)createpurchaseStock();
      case PURCHASE_STOCK_RESPONSE: return (DataObject)createpurchaseStockResponse();
      case SELL_STOCK: return (DataObject)createsellStock();
      case STOCK_LOG_ENTRY: return (DataObject)createStockLogEntry();
      case STOCK_SUMMARY: return (DataObject)createStockSummary();
      case WITHDRAW: return (DataObject)createwithdraw();
      case WITHDRAW_RESPONSE: return (DataObject)createwithdrawResponse();
      default:
        return super.create(typeNumber);
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountLog createAccountLog()
  {
    AccountLogImpl accountLog = new AccountLogImpl();
    return accountLog;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountLogEntry createAccountLogEntry()
  {
    AccountLogEntryImpl accountLogEntry = new AccountLogEntryImpl();
    return accountLogEntry;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountReport createAccountReport()
  {
    AccountReportImpl accountReport = new AccountReportImpl();
    return accountReport;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AccountSummary createAccountSummary()
  {
    AccountSummaryImpl accountSummary = new AccountSummaryImpl();
    return accountSummary;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public createAccount createcreateAccount()
  {
    createAccountImpl createAccount = new createAccountImpl();
    return createAccount;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public createAccountResponse createcreateAccountResponse()
  {
    createAccountResponseImpl createAccountResponse = new createAccountResponseImpl();
    return createAccountResponse;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CustomerProfileData createCustomerProfileData()
  {
    CustomerProfileDataImpl customerProfileData = new CustomerProfileDataImpl();
    return customerProfileData;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public deposit createdeposit()
  {
    depositImpl deposit = new depositImpl();
    return deposit;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public depositResponse createdepositResponse()
  {
    depositResponseImpl depositResponse = new depositResponseImpl();
    return depositResponse;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getAccountLog creategetAccountLog()
  {
    getAccountLogImpl getAccountLog = new getAccountLogImpl();
    return getAccountLog;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getAccountLogResponse creategetAccountLogResponse()
  {
    getAccountLogResponseImpl getAccountLogResponse = new getAccountLogResponseImpl();
    return getAccountLogResponse;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getAccountReport creategetAccountReport()
  {
    getAccountReportImpl getAccountReport = new getAccountReportImpl();
    return getAccountReport;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getAccountReportResponse creategetAccountReportResponse()
  {
    getAccountReportResponseImpl getAccountReportResponse = new getAccountReportResponseImpl();
    return getAccountReportResponse;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getCustomerProfile creategetCustomerProfile()
  {
    getCustomerProfileImpl getCustomerProfile = new getCustomerProfileImpl();
    return getCustomerProfile;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public getCustomerProfileResponse creategetCustomerProfileResponse()
  {
    getCustomerProfileResponseImpl getCustomerProfileResponse = new getCustomerProfileResponseImpl();
    return getCustomerProfileResponse;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public purchaseStock createpurchaseStock()
  {
    purchaseStockImpl purchaseStock = new purchaseStockImpl();
    return purchaseStock;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public purchaseStockResponse createpurchaseStockResponse()
  {
    purchaseStockResponseImpl purchaseStockResponse = new purchaseStockResponseImpl();
    return purchaseStockResponse;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public sellStock createsellStock()
  {
    sellStockImpl sellStock = new sellStockImpl();
    return sellStock;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StockLogEntry createStockLogEntry()
  {
    StockLogEntryImpl stockLogEntry = new StockLogEntryImpl();
    return stockLogEntry;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StockSummary createStockSummary()
  {
    StockSummaryImpl stockSummary = new StockSummaryImpl();
    return stockSummary;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public withdraw createwithdraw()
  {
    withdrawImpl withdraw = new withdrawImpl();
    return withdraw;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public withdrawResponse createwithdrawResponse()
  {
    withdrawResponseImpl withdrawResponse = new withdrawResponseImpl();
    return withdrawResponse;
  }
  
  // Following creates and initializes SDO metadata for the supported types.		
  protected Type accountLogType = null;

  public Type getAccountLog()
  {
    return accountLogType;
  }
    
  protected Type accountLogEntryType = null;

  public Type getAccountLogEntry()
  {
    return accountLogEntryType;
  }
    
  protected Type accountReportType = null;

  public Type getAccountReport()
  {
    return accountReportType;
  }
    
  protected Type accountSummaryType = null;

  public Type getAccountSummary()
  {
    return accountSummaryType;
  }
    
  protected Type createAccountType = null;

  public Type getcreateAccount()
  {
    return createAccountType;
  }
    
  protected Type createAccountResponseType = null;

  public Type getcreateAccountResponse()
  {
    return createAccountResponseType;
  }
    
  protected Type customerProfileDataType = null;

  public Type getCustomerProfileData()
  {
    return customerProfileDataType;
  }
    
  protected Type depositType = null;

  public Type getdeposit()
  {
    return depositType;
  }
    
  protected Type depositResponseType = null;

  public Type getdepositResponse()
  {
    return depositResponseType;
  }
      
  protected Type getAccountLogType = null;

  public Type getgetAccountLog()
  {
    return getAccountLogType;
  }
    
  protected Type getAccountLogResponseType = null;

  public Type getgetAccountLogResponse()
  {
    return getAccountLogResponseType;
  }
    
  protected Type getAccountReportType = null;

  public Type getgetAccountReport()
  {
    return getAccountReportType;
  }
    
  protected Type getAccountReportResponseType = null;

  public Type getgetAccountReportResponse()
  {
    return getAccountReportResponseType;
  }
    
  protected Type getCustomerProfileType = null;

  public Type getgetCustomerProfile()
  {
    return getCustomerProfileType;
  }
    
  protected Type getCustomerProfileResponseType = null;

  public Type getgetCustomerProfileResponse()
  {
    return getCustomerProfileResponseType;
  }
    
  protected Type purchaseStockType = null;

  public Type getpurchaseStock()
  {
    return purchaseStockType;
  }
    
  protected Type purchaseStockResponseType = null;

  public Type getpurchaseStockResponse()
  {
    return purchaseStockResponseType;
  }
    
  protected Type sellStockType = null;

  public Type getsellStock()
  {
    return sellStockType;
  }
    
  protected Type stockLogEntryType = null;

  public Type getStockLogEntry()
  {
    return stockLogEntryType;
  }
    
  protected Type stockSummaryType = null;

  public Type getStockSummary()
  {
    return stockSummaryType;
  }
    
  protected Type withdrawType = null;

  public Type getwithdraw()
  {
    return withdrawType;
  }
    
  protected Type withdrawResponseType = null;

  public Type getwithdrawResponse()
  {
    return withdrawResponseType;
  }
  

  private static boolean isInited = false;

  public static AccountFactoryImpl init()
  {
    if (isInited) return (AccountFactoryImpl)FactoryBase.getStaticFactory(AccountFactoryImpl.NAMESPACE_URI);
    AccountFactoryImpl theAccountFactoryImpl = new AccountFactoryImpl();
    isInited = true;

    // Initialize simple dependencies
    SDOUtil.registerStaticTypes(SDOFactory.class);
    SDOUtil.registerStaticTypes(ModelFactory.class);

    // Create package meta-data objects
    theAccountFactoryImpl.createMetaData();

    // Initialize created meta-data
    theAccountFactoryImpl.initializeMetaData();

    // Mark meta-data to indicate it can't be changed
    //theAccountFactoryImpl.freeze(); //FB do we need to freeze / should we freeze ????

    return theAccountFactoryImpl;
  }
  
  private boolean isCreated = false;

  public void createMetaData()
  {
    if (isCreated) return;
    isCreated = true;	

    // Create types and their properties
    accountLogType = createType(false, ACCOUNT_LOG);
    createProperty(false, accountLogType, AccountLogImpl.ACCOUNT_LOG_ENTRIES);
    createProperty(false, accountLogType, AccountLogImpl.STOCK_LOG_ENTRIES);

    accountLogEntryType = createType(false, ACCOUNT_LOG_ENTRY);
    createProperty(true, accountLogEntryType, AccountLogEntryImpl.LOG_SEQ_NO);
    createProperty(true, accountLogEntryType, AccountLogEntryImpl.ID);
    createProperty(true, accountLogEntryType, AccountLogEntryImpl.ACCOUNT_NUMBER);
    createProperty(true, accountLogEntryType, AccountLogEntryImpl.ACTION_TYPE);
    createProperty(true, accountLogEntryType, AccountLogEntryImpl.AMOUNT);

    accountReportType = createType(false, ACCOUNT_REPORT);
    createProperty(false, accountReportType, AccountReportImpl.ACCOUNT_SUMMARIES);
    createProperty(false, accountReportType, AccountReportImpl.STOCK_SUMMARIES);

    accountSummaryType = createType(false, ACCOUNT_SUMMARY);
    createProperty(true, accountSummaryType, AccountSummaryImpl.ACCOUNT_NUMBER);
    createProperty(true, accountSummaryType, AccountSummaryImpl.ACCOUNT_TYPE);
    createProperty(true, accountSummaryType, AccountSummaryImpl.BALANCE);

    createAccountType = createType(false, CREATE_ACCOUNT);
    createProperty(false, createAccountType, createAccountImpl.CUSTOMER_PROFILE);
    createProperty(true, createAccountType, createAccountImpl.CREATE_SAVINGS);
    createProperty(true, createAccountType, createAccountImpl.CREATE_CHECKINGS);

    createAccountResponseType = createType(false, CREATE_ACCOUNT_RESPONSE);
    createProperty(false, createAccountResponseType, createAccountResponseImpl.CUSTOMER_PROFILE);

    customerProfileDataType = createType(false, CUSTOMER_PROFILE_DATA);
    createProperty(true, customerProfileDataType, CustomerProfileDataImpl.FIRST_NAME);
    createProperty(true, customerProfileDataType, CustomerProfileDataImpl.LAST_NAME);
    createProperty(true, customerProfileDataType, CustomerProfileDataImpl.ADDRESS);
    createProperty(true, customerProfileDataType, CustomerProfileDataImpl.EMAIL);
    createProperty(true, customerProfileDataType, CustomerProfileDataImpl.LOGIN_ID);
    createProperty(true, customerProfileDataType, CustomerProfileDataImpl.PASSWORD);
    createProperty(true, customerProfileDataType, CustomerProfileDataImpl.ID);

    depositType = createType(false, DEPOSIT);
    createProperty(true, depositType, depositImpl.ACCOUNT_NUMBER);
    createProperty(true, depositType, depositImpl.AMOUNT);

    depositResponseType = createType(false, DEPOSIT_RESPONSE);
    createProperty(true, depositResponseType, depositResponseImpl.BALANCE);

    getAccountLogType = createType(false, GET_ACCOUNT_LOG);
    createProperty(true, getAccountLogType, getAccountLogImpl.CUSTOMER_ID);

    getAccountLogResponseType = createType(false, GET_ACCOUNT_LOG_RESPONSE);
    createProperty(false, getAccountLogResponseType, getAccountLogResponseImpl.ACCOUNT_LOG);

    getAccountReportType = createType(false, GET_ACCOUNT_REPORT);
    createProperty(true, getAccountReportType, getAccountReportImpl.CUSTOMER_ID);

    getAccountReportResponseType = createType(false, GET_ACCOUNT_REPORT_RESPONSE);
    createProperty(false, getAccountReportResponseType, getAccountReportResponseImpl.ACCOUNT_REPORT);

    getCustomerProfileType = createType(false, GET_CUSTOMER_PROFILE);
    createProperty(true, getCustomerProfileType, getCustomerProfileImpl.LOGIN_ID);

    getCustomerProfileResponseType = createType(false, GET_CUSTOMER_PROFILE_RESPONSE);
    createProperty(false, getCustomerProfileResponseType, getCustomerProfileResponseImpl.CUSTOMER_PROFILE);

    purchaseStockType = createType(false, PURCHASE_STOCK);
    createProperty(true, purchaseStockType, purchaseStockImpl.ID);
    createProperty(false, purchaseStockType, purchaseStockImpl.STOCK);

    purchaseStockResponseType = createType(false, PURCHASE_STOCK_RESPONSE);
    createProperty(false, purchaseStockResponseType, purchaseStockResponseImpl.PURCHASE_SUMMARY);

    sellStockType = createType(false, SELL_STOCK);
    createProperty(true, sellStockType, sellStockImpl.PURCHASE_LOT_NUMBER);
    createProperty(true, sellStockType, sellStockImpl.QUANTITY);

    stockLogEntryType = createType(false, STOCK_LOG_ENTRY);
    createProperty(true, stockLogEntryType, StockLogEntryImpl.LOG_SEQ_NO);
    createProperty(true, stockLogEntryType, StockLogEntryImpl.ID);
    createProperty(true, stockLogEntryType, StockLogEntryImpl.SYMBOL);
    createProperty(true, stockLogEntryType, StockLogEntryImpl.QUANTITY);
    createProperty(true, stockLogEntryType, StockLogEntryImpl.ACTION_TYPE);
    createProperty(true, stockLogEntryType, StockLogEntryImpl.PURCHASE_LOT_NUMBER);

    stockSummaryType = createType(false, STOCK_SUMMARY);
    createProperty(true, stockSummaryType, StockSummaryImpl.PURCHASE_LOT_NUMBER);
    createProperty(true, stockSummaryType, StockSummaryImpl.SYMBOL);
    createProperty(true, stockSummaryType, StockSummaryImpl.QUANTITY);
    createProperty(true, stockSummaryType, StockSummaryImpl.PURCHASE_DATE);
    createProperty(true, stockSummaryType, StockSummaryImpl.PURCHASE_PRICE);
    createProperty(true, stockSummaryType, StockSummaryImpl.CURRENT_PRICE);
    createProperty(true, stockSummaryType, StockSummaryImpl.COMPANY);
    createProperty(true, stockSummaryType, StockSummaryImpl.HIGH_PRICE);
    createProperty(true, stockSummaryType, StockSummaryImpl.LOW_PRICE);

    withdrawType = createType(false, WITHDRAW);
    createProperty(true, withdrawType, withdrawImpl.ACCOUNT_NUMBER);
    createProperty(true, withdrawType, withdrawImpl.AMOUNT);

    withdrawResponseType = createType(false, WITHDRAW_RESPONSE);
    createProperty(true, withdrawResponseType, withdrawResponseImpl.BALANCE);
  }
  
  private boolean isInitialized = false;

  public void initializeMetaData()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Obtain other dependent packages
    ModelFactoryImpl theModelPackageImpl = (ModelFactoryImpl)FactoryBase.getStaticFactory(ModelFactoryImpl.NAMESPACE_URI);
    Property property = null;

    // Add supertypes to types

    // Initialize types and properties
    initializeType(accountLogType, AccountLog.class, "AccountLog", false);
    property = (Property)accountLogType.getProperties().get(AccountLogImpl.ACCOUNT_LOG_ENTRIES);
    initializeProperty(property, this.getAccountLogEntry(), "accountLogEntries", null, 1, -1, AccountLog.class, false, false, false, true, null);
    property = (Property)accountLogType.getProperties().get(AccountLogImpl.STOCK_LOG_ENTRIES);
    initializeProperty(property, this.getStockLogEntry(), "stockLogEntries", null, 1, -1, AccountLog.class, false, false, false, true, null);

    initializeType(accountLogEntryType, AccountLogEntry.class, "AccountLogEntry", false);
    property = (Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.LOG_SEQ_NO);
    initializeProperty(property, theModelPackageImpl.getInt(), "logSeqNo", null, 0, 1, AccountLogEntry.class, false, false, false);
    property = (Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.ID);
    initializeProperty(property, theModelPackageImpl.getInt(), "id", null, 0, 1, AccountLogEntry.class, false, false, false);
    property = (Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.ACCOUNT_NUMBER);
    initializeProperty(property, theModelPackageImpl.getString(), "accountNumber", null, 0, 1, AccountLogEntry.class, false, false, false);
    property = (Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.ACTION_TYPE);
    initializeProperty(property, theModelPackageImpl.getString(), "actionType", null, 0, 1, AccountLogEntry.class, false, false, false);
    property = (Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.AMOUNT);
    initializeProperty(property, theModelPackageImpl.getFloat(), "amount", null, 0, 1, AccountLogEntry.class, false, false, false);

    initializeType(accountReportType, AccountReport.class, "AccountReport", false);
    property = (Property)accountReportType.getProperties().get(AccountReportImpl.ACCOUNT_SUMMARIES);
    initializeProperty(property, this.getAccountSummary(), "accountSummaries", null, 1, -1, AccountReport.class, false, false, false, true, null);
    property = (Property)accountReportType.getProperties().get(AccountReportImpl.STOCK_SUMMARIES);
    initializeProperty(property, this.getStockSummary(), "stockSummaries", null, 1, -1, AccountReport.class, false, false, false, true, null);

    initializeType(accountSummaryType, AccountSummary.class, "AccountSummary", false);
    property = (Property)accountSummaryType.getProperties().get(AccountSummaryImpl.ACCOUNT_NUMBER);
    initializeProperty(property, theModelPackageImpl.getString(), "accountNumber", null, 0, 1, AccountSummary.class, false, false, false);
    property = (Property)accountSummaryType.getProperties().get(AccountSummaryImpl.ACCOUNT_TYPE);
    initializeProperty(property, theModelPackageImpl.getString(), "accountType", null, 0, 1, AccountSummary.class, false, false, false);
    property = (Property)accountSummaryType.getProperties().get(AccountSummaryImpl.BALANCE);
    initializeProperty(property, theModelPackageImpl.getFloat(), "balance", null, 0, 1, AccountSummary.class, false, false, false);

    initializeType(createAccountType, createAccount.class, "createAccount", false);
    property = (Property)createAccountType.getProperties().get(createAccountImpl.CUSTOMER_PROFILE);
    initializeProperty(property, this.getCustomerProfileData(), "customerProfile", null, 1, 1, createAccount.class, false, false, false, true, null);
    property = (Property)createAccountType.getProperties().get(createAccountImpl.CREATE_SAVINGS);
    initializeProperty(property, theModelPackageImpl.getBoolean(), "createSavings", null, 1, 1, createAccount.class, false, false, false);
    property = (Property)createAccountType.getProperties().get(createAccountImpl.CREATE_CHECKINGS);
    initializeProperty(property, theModelPackageImpl.getBoolean(), "createCheckings", null, 1, 1, createAccount.class, false, false, false);

    initializeType(createAccountResponseType, createAccountResponse.class, "createAccountResponse", false);
    property = (Property)createAccountResponseType.getProperties().get(createAccountResponseImpl.CUSTOMER_PROFILE);
    initializeProperty(property, this.getCustomerProfileData(), "customerProfile", null, 1, 1, createAccountResponse.class, false, false, false, true, null);

    initializeType(customerProfileDataType, CustomerProfileData.class, "CustomerProfileData", false);
    property = (Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.FIRST_NAME);
    initializeProperty(property, theModelPackageImpl.getString(), "firstName", null, 1, 1, CustomerProfileData.class, false, false, false);
    property = (Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.LAST_NAME);
    initializeProperty(property, theModelPackageImpl.getString(), "lastName", null, 1, 1, CustomerProfileData.class, false, false, false);
    property = (Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.ADDRESS);
    initializeProperty(property, theModelPackageImpl.getString(), "address", null, 1, 1, CustomerProfileData.class, false, false, false);
    property = (Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.EMAIL);
    initializeProperty(property, theModelPackageImpl.getString(), "email", null, 1, 1, CustomerProfileData.class, false, false, false);
    property = (Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.LOGIN_ID);
    initializeProperty(property, theModelPackageImpl.getString(), "loginID", null, 1, 1, CustomerProfileData.class, false, false, false);
    property = (Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.PASSWORD);
    initializeProperty(property, theModelPackageImpl.getString(), "password", null, 1, 1, CustomerProfileData.class, false, false, false);
    property = (Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.ID);
    initializeProperty(property, theModelPackageImpl.getInt(), "id", null, 1, 1, CustomerProfileData.class, false, false, false);

    initializeType(depositType, deposit.class, "deposit", false);
    property = (Property)depositType.getProperties().get(depositImpl.ACCOUNT_NUMBER);
    initializeProperty(property, theModelPackageImpl.getString(), "accountNumber", null, 1, 1, deposit.class, false, false, false);
    property = (Property)depositType.getProperties().get(depositImpl.AMOUNT);
    initializeProperty(property, theModelPackageImpl.getFloat(), "amount", null, 1, 1, deposit.class, false, false, false);

    initializeType(depositResponseType, depositResponse.class, "depositResponse", false);
    property = (Property)depositResponseType.getProperties().get(depositResponseImpl.BALANCE);
    initializeProperty(property, theModelPackageImpl.getFloat(), "balance", null, 1, 1, depositResponse.class, false, false, false);

    initializeType(getAccountLogType, getAccountLog.class, "getAccountLog", false);
    property = (Property)getAccountLogType.getProperties().get(getAccountLogImpl.CUSTOMER_ID);
    initializeProperty(property, theModelPackageImpl.getInt(), "customerID", null, 1, 1, getAccountLog.class, false, false, false);

    initializeType(getAccountLogResponseType, getAccountLogResponse.class, "getAccountLogResponse", false);
    property = (Property)getAccountLogResponseType.getProperties().get(getAccountLogResponseImpl.ACCOUNT_LOG);
    initializeProperty(property, this.getAccountLog(), "accountLog", null, 1, 1, getAccountLogResponse.class, false, false, false, true, null);

    initializeType(getAccountReportType, getAccountReport.class, "getAccountReport", false);
    property = (Property)getAccountReportType.getProperties().get(getAccountReportImpl.CUSTOMER_ID);
    initializeProperty(property, theModelPackageImpl.getInt(), "customerID", null, 1, 1, getAccountReport.class, false, false, false);

    initializeType(getAccountReportResponseType, getAccountReportResponse.class, "getAccountReportResponse", false);
    property = (Property)getAccountReportResponseType.getProperties().get(getAccountReportResponseImpl.ACCOUNT_REPORT);
    initializeProperty(property, this.getAccountReport(), "accountReport", null, 1, 1, getAccountReportResponse.class, false, false, false, true, null);

    initializeType(getCustomerProfileType, getCustomerProfile.class, "getCustomerProfile", false);
    property = (Property)getCustomerProfileType.getProperties().get(getCustomerProfileImpl.LOGIN_ID);
    initializeProperty(property, theModelPackageImpl.getString(), "loginID", null, 1, 1, getCustomerProfile.class, false, false, false);

    initializeType(getCustomerProfileResponseType, getCustomerProfileResponse.class, "getCustomerProfileResponse", false);
    property = (Property)getCustomerProfileResponseType.getProperties().get(getCustomerProfileResponseImpl.CUSTOMER_PROFILE);
    initializeProperty(property, this.getCustomerProfileData(), "customerProfile", null, 1, 1, getCustomerProfileResponse.class, false, false, false, true, null);

    initializeType(purchaseStockType, purchaseStock.class, "purchaseStock", false);
    property = (Property)purchaseStockType.getProperties().get(purchaseStockImpl.ID);
    initializeProperty(property, theModelPackageImpl.getInt(), "id", null, 1, 1, purchaseStock.class, false, false, false);
    property = (Property)purchaseStockType.getProperties().get(purchaseStockImpl.STOCK);
    initializeProperty(property, this.getStockSummary(), "stock", null, 1, 1, purchaseStock.class, false, false, false, true, null);

    initializeType(purchaseStockResponseType, purchaseStockResponse.class, "purchaseStockResponse", false);
    property = (Property)purchaseStockResponseType.getProperties().get(purchaseStockResponseImpl.PURCHASE_SUMMARY);
    initializeProperty(property, this.getStockSummary(), "purchaseSummary", null, 1, 1, purchaseStockResponse.class, false, false, false, true, null);

    initializeType(sellStockType, sellStock.class, "sellStock", false);
    property = (Property)sellStockType.getProperties().get(sellStockImpl.PURCHASE_LOT_NUMBER);
    initializeProperty(property, theModelPackageImpl.getInt(), "purchaseLotNumber", null, 1, 1, sellStock.class, false, false, false);
    property = (Property)sellStockType.getProperties().get(sellStockImpl.QUANTITY);
    initializeProperty(property, theModelPackageImpl.getInt(), "quantity", null, 1, 1, sellStock.class, false, false, false);

    initializeType(stockLogEntryType, StockLogEntry.class, "StockLogEntry", false);
    property = (Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.LOG_SEQ_NO);
    initializeProperty(property, theModelPackageImpl.getInt(), "logSeqNo", null, 0, 1, StockLogEntry.class, false, false, false);
    property = (Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.ID);
    initializeProperty(property, theModelPackageImpl.getInt(), "id", null, 0, 1, StockLogEntry.class, false, false, false);
    property = (Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.SYMBOL);
    initializeProperty(property, theModelPackageImpl.getString(), "symbol", null, 0, 1, StockLogEntry.class, false, false, false);
    property = (Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.QUANTITY);
    initializeProperty(property, theModelPackageImpl.getInt(), "quantity", null, 0, 1, StockLogEntry.class, false, false, false);
    property = (Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.ACTION_TYPE);
    initializeProperty(property, theModelPackageImpl.getString(), "actionType", null, 0, 1, StockLogEntry.class, false, false, false);
    property = (Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.PURCHASE_LOT_NUMBER);
    initializeProperty(property, theModelPackageImpl.getInt(), "purchaseLotNumber", null, 0, 1, StockLogEntry.class, false, false, false);

    initializeType(stockSummaryType, StockSummary.class, "StockSummary", false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.PURCHASE_LOT_NUMBER);
    initializeProperty(property, theModelPackageImpl.getInt(), "purchaseLotNumber", null, 0, 1, StockSummary.class, false, false, false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.SYMBOL);
    initializeProperty(property, theModelPackageImpl.getString(), "symbol", null, 0, 1, StockSummary.class, false, false, false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.QUANTITY);
    initializeProperty(property, theModelPackageImpl.getInt(), "quantity", null, 0, 1, StockSummary.class, false, false, false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.PURCHASE_DATE);
    initializeProperty(property, theModelPackageImpl.getDateTime(), "purchaseDate", null, 0, 1, StockSummary.class, false, false, false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.PURCHASE_PRICE);
    initializeProperty(property, theModelPackageImpl.getFloat(), "purchasePrice", null, 0, 1, StockSummary.class, false, false, false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.CURRENT_PRICE);
    initializeProperty(property, theModelPackageImpl.getFloat(), "currentPrice", null, 0, 1, StockSummary.class, false, false, false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.COMPANY);
    initializeProperty(property, theModelPackageImpl.getString(), "company", null, 0, 1, StockSummary.class, false, false, false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.HIGH_PRICE);
    initializeProperty(property, theModelPackageImpl.getFloat(), "highPrice", null, 0, 1, StockSummary.class, false, false, false);
    property = (Property)stockSummaryType.getProperties().get(StockSummaryImpl.LOW_PRICE);
    initializeProperty(property, theModelPackageImpl.getFloat(), "lowPrice", null, 0, 1, StockSummary.class, false, false, false);

    initializeType(withdrawType, withdraw.class, "withdraw", false);
    property = (Property)withdrawType.getProperties().get(withdrawImpl.ACCOUNT_NUMBER);
    initializeProperty(property, theModelPackageImpl.getString(), "accountNumber", null, 1, 1, withdraw.class, false, false, false);
    property = (Property)withdrawType.getProperties().get(withdrawImpl.AMOUNT);
    initializeProperty(property, theModelPackageImpl.getFloat(), "amount", null, 1, 1, withdraw.class, false, false, false);

    initializeType(withdrawResponseType, withdrawResponse.class, "withdrawResponse", false);
    property = (Property)withdrawResponseType.getProperties().get(withdrawResponseImpl.BALANCE);
    initializeProperty(property, theModelPackageImpl.getFloat(), "balance", null, 1, 1, withdrawResponse.class, false, false, false);

    createXSDMetaData(theModelPackageImpl);
  }
    
  protected void createXSDMetaData(ModelFactoryImpl theModelPackageImpl)
  {
    super.initXSD();
    
    Property property = null;
    
    addXSDMapping
      (accountLogType,
       new String[] 
       {
       "name", "AccountLog",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)accountLogType.getProperties().get(AccountLogImpl.ACCOUNT_LOG_ENTRIES),
       new String[]
       {
       "kind", "element",
       "name", "accountLogEntries"
       });

    addXSDMapping
      ((Property)accountLogType.getProperties().get(AccountLogImpl.STOCK_LOG_ENTRIES),
       new String[]
       {
       "kind", "element",
       "name", "stockLogEntries"
       });

    addXSDMapping
      (accountLogEntryType,
       new String[] 
       {
       "name", "AccountLogEntry",
       "kind", "empty"
       });

    addXSDMapping
      ((Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.LOG_SEQ_NO),
       new String[]
       {
       "kind", "attribute",
       "name", "logSeqNo"
       });

    addXSDMapping
      ((Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.ID),
       new String[]
       {
       "kind", "attribute",
       "name", "id"
       });

    addXSDMapping
      ((Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.ACCOUNT_NUMBER),
       new String[]
       {
       "kind", "attribute",
       "name", "accountNumber"
       });

    addXSDMapping
      ((Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.ACTION_TYPE),
       new String[]
       {
       "kind", "attribute",
       "name", "actionType"
       });

    addXSDMapping
      ((Property)accountLogEntryType.getProperties().get(AccountLogEntryImpl.AMOUNT),
       new String[]
       {
       "kind", "attribute",
       "name", "amount"
       });

    addXSDMapping
      (accountReportType,
       new String[] 
       {
       "name", "AccountReport",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)accountReportType.getProperties().get(AccountReportImpl.ACCOUNT_SUMMARIES),
       new String[]
       {
       "kind", "element",
       "name", "accountSummaries"
       });

    addXSDMapping
      ((Property)accountReportType.getProperties().get(AccountReportImpl.STOCK_SUMMARIES),
       new String[]
       {
       "kind", "element",
       "name", "stockSummaries"
       });

    addXSDMapping
      (accountSummaryType,
       new String[] 
       {
       "name", "AccountSummary",
       "kind", "empty"
       });

    addXSDMapping
      ((Property)accountSummaryType.getProperties().get(AccountSummaryImpl.ACCOUNT_NUMBER),
       new String[]
       {
       "kind", "attribute",
       "name", "accountNumber"
       });

    addXSDMapping
      ((Property)accountSummaryType.getProperties().get(AccountSummaryImpl.ACCOUNT_TYPE),
       new String[]
       {
       "kind", "attribute",
       "name", "accountType"
       });

    addXSDMapping
      ((Property)accountSummaryType.getProperties().get(AccountSummaryImpl.BALANCE),
       new String[]
       {
       "kind", "attribute",
       "name", "balance"
       });

    addXSDMapping
      (createAccountType,
       new String[] 
       {
       "name", "createAccount_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)createAccountType.getProperties().get(createAccountImpl.CUSTOMER_PROFILE),
       new String[]
       {
       "kind", "element",
       "name", "customerProfile"
       });

    addXSDMapping
      ((Property)createAccountType.getProperties().get(createAccountImpl.CREATE_SAVINGS),
       new String[]
       {
       "kind", "element",
       "name", "createSavings"
       });

    addXSDMapping
      ((Property)createAccountType.getProperties().get(createAccountImpl.CREATE_CHECKINGS),
       new String[]
       {
       "kind", "element",
       "name", "createCheckings"
       });

    addXSDMapping
      (createAccountResponseType,
       new String[] 
       {
       "name", "createAccountResponse_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)createAccountResponseType.getProperties().get(createAccountResponseImpl.CUSTOMER_PROFILE),
       new String[]
       {
       "kind", "element",
       "name", "customerProfile"
       });

    addXSDMapping
      (customerProfileDataType,
       new String[] 
       {
       "name", "CustomerProfileData",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.FIRST_NAME),
       new String[]
       {
       "kind", "element",
       "name", "firstName"
       });

    addXSDMapping
      ((Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.LAST_NAME),
       new String[]
       {
       "kind", "element",
       "name", "lastName"
       });

    addXSDMapping
      ((Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.ADDRESS),
       new String[]
       {
       "kind", "element",
       "name", "address"
       });

    addXSDMapping
      ((Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.EMAIL),
       new String[]
       {
       "kind", "element",
       "name", "email"
       });

    addXSDMapping
      ((Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.LOGIN_ID),
       new String[]
       {
       "kind", "element",
       "name", "loginID"
       });

    addXSDMapping
      ((Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.PASSWORD),
       new String[]
       {
       "kind", "element",
       "name", "password"
       });

    addXSDMapping
      ((Property)customerProfileDataType.getProperties().get(CustomerProfileDataImpl.ID),
       new String[]
       {
       "kind", "element",
       "name", "id"
       });

    addXSDMapping
      (depositType,
       new String[] 
       {
       "name", "deposit_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)depositType.getProperties().get(depositImpl.ACCOUNT_NUMBER),
       new String[]
       {
       "kind", "element",
       "name", "accountNumber"
       });

    addXSDMapping
      ((Property)depositType.getProperties().get(depositImpl.AMOUNT),
       new String[]
       {
       "kind", "element",
       "name", "amount"
       });

    addXSDMapping
      (depositResponseType,
       new String[] 
       {
       "name", "depositResponse_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)depositResponseType.getProperties().get(depositResponseImpl.BALANCE),
       new String[]
       {
       "kind", "element",
       "name", "balance"
       });

    property = createGlobalProperty
      ("createAccount",
      this.getcreateAccount(),
       new String[]
       {
       "kind", "element",
       "name", "createAccount",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("createAccountResponse",
      this.getcreateAccountResponse(),
       new String[]
       {
       "kind", "element",
       "name", "createAccountResponse",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("deposit",
      this.getdeposit(),
       new String[]
       {
       "kind", "element",
       "name", "deposit",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("depositResponse",
      this.getdepositResponse(),
       new String[]
       {
       "kind", "element",
       "name", "depositResponse",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("getAccountLog",
      this.getgetAccountLog(),
       new String[]
       {
       "kind", "element",
       "name", "getAccountLog",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("getAccountLogResponse",
      this.getgetAccountLogResponse(),
       new String[]
       {
       "kind", "element",
       "name", "getAccountLogResponse",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("getAccountReport",
      this.getgetAccountReport(),
       new String[]
       {
       "kind", "element",
       "name", "getAccountReport",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("getAccountReportResponse",
      this.getgetAccountReportResponse(),
       new String[]
       {
       "kind", "element",
       "name", "getAccountReportResponse",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("getCustomerProfile",
      this.getgetCustomerProfile(),
       new String[]
       {
       "kind", "element",
       "name", "getCustomerProfile",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("getCustomerProfileResponse",
      this.getgetCustomerProfileResponse(),
       new String[]
       {
       "kind", "element",
       "name", "getCustomerProfileResponse",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("purchaseStock",
      this.getpurchaseStock(),
       new String[]
       {
       "kind", "element",
       "name", "purchaseStock",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("purchaseStockResponse",
      this.getpurchaseStockResponse(),
       new String[]
       {
       "kind", "element",
       "name", "purchaseStockResponse",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("sellStock",
      this.getsellStock(),
       new String[]
       {
       "kind", "element",
       "name", "sellStock",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("withdraw",
      this.getwithdraw(),
       new String[]
       {
       "kind", "element",
       "name", "withdraw",
       "namespace", "##targetNamespace"
       });
                
    property = createGlobalProperty
      ("withdrawResponse",
      this.getwithdrawResponse(),
       new String[]
       {
       "kind", "element",
       "name", "withdrawResponse",
       "namespace", "##targetNamespace"
       });
                
    addXSDMapping
      (getAccountLogType,
       new String[] 
       {
       "name", "getAccountLog_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)getAccountLogType.getProperties().get(getAccountLogImpl.CUSTOMER_ID),
       new String[]
       {
       "kind", "element",
       "name", "customerID"
       });

    addXSDMapping
      (getAccountLogResponseType,
       new String[] 
       {
       "name", "getAccountLogResponse_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)getAccountLogResponseType.getProperties().get(getAccountLogResponseImpl.ACCOUNT_LOG),
       new String[]
       {
       "kind", "element",
       "name", "accountLog"
       });

    addXSDMapping
      (getAccountReportType,
       new String[] 
       {
       "name", "getAccountReport_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)getAccountReportType.getProperties().get(getAccountReportImpl.CUSTOMER_ID),
       new String[]
       {
       "kind", "element",
       "name", "customerID"
       });

    addXSDMapping
      (getAccountReportResponseType,
       new String[] 
       {
       "name", "getAccountReportResponse_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)getAccountReportResponseType.getProperties().get(getAccountReportResponseImpl.ACCOUNT_REPORT),
       new String[]
       {
       "kind", "element",
       "name", "accountReport"
       });

    addXSDMapping
      (getCustomerProfileType,
       new String[] 
       {
       "name", "getCustomerProfile_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)getCustomerProfileType.getProperties().get(getCustomerProfileImpl.LOGIN_ID),
       new String[]
       {
       "kind", "element",
       "name", "loginID"
       });

    addXSDMapping
      (getCustomerProfileResponseType,
       new String[] 
       {
       "name", "getCustomerProfileResponse_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)getCustomerProfileResponseType.getProperties().get(getCustomerProfileResponseImpl.CUSTOMER_PROFILE),
       new String[]
       {
       "kind", "element",
       "name", "customerProfile"
       });

    addXSDMapping
      (purchaseStockType,
       new String[] 
       {
       "name", "purchaseStock_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)purchaseStockType.getProperties().get(purchaseStockImpl.ID),
       new String[]
       {
       "kind", "element",
       "name", "id"
       });

    addXSDMapping
      ((Property)purchaseStockType.getProperties().get(purchaseStockImpl.STOCK),
       new String[]
       {
       "kind", "element",
       "name", "stock"
       });

    addXSDMapping
      (purchaseStockResponseType,
       new String[] 
       {
       "name", "purchaseStockResponse_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)purchaseStockResponseType.getProperties().get(purchaseStockResponseImpl.PURCHASE_SUMMARY),
       new String[]
       {
       "kind", "element",
       "name", "purchaseSummary"
       });

    addXSDMapping
      (sellStockType,
       new String[] 
       {
       "name", "sellStock_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)sellStockType.getProperties().get(sellStockImpl.PURCHASE_LOT_NUMBER),
       new String[]
       {
       "kind", "element",
       "name", "purchaseLotNumber"
       });

    addXSDMapping
      ((Property)sellStockType.getProperties().get(sellStockImpl.QUANTITY),
       new String[]
       {
       "kind", "element",
       "name", "quantity"
       });

    addXSDMapping
      (stockLogEntryType,
       new String[] 
       {
       "name", "StockLogEntry",
       "kind", "empty"
       });

    addXSDMapping
      ((Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.LOG_SEQ_NO),
       new String[]
       {
       "kind", "attribute",
       "name", "logSeqNo"
       });

    addXSDMapping
      ((Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.ID),
       new String[]
       {
       "kind", "attribute",
       "name", "id"
       });

    addXSDMapping
      ((Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.SYMBOL),
       new String[]
       {
       "kind", "attribute",
       "name", "symbol"
       });

    addXSDMapping
      ((Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.QUANTITY),
       new String[]
       {
       "kind", "attribute",
       "name", "quantity"
       });

    addXSDMapping
      ((Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.ACTION_TYPE),
       new String[]
       {
       "kind", "attribute",
       "name", "actionType"
       });

    addXSDMapping
      ((Property)stockLogEntryType.getProperties().get(StockLogEntryImpl.PURCHASE_LOT_NUMBER),
       new String[]
       {
       "kind", "attribute",
       "name", "purchaseLotNumber"
       });

    addXSDMapping
      (stockSummaryType,
       new String[] 
       {
       "name", "StockSummary",
       "kind", "empty"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.PURCHASE_LOT_NUMBER),
       new String[]
       {
       "kind", "attribute",
       "name", "purchaseLotNumber"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.SYMBOL),
       new String[]
       {
       "kind", "attribute",
       "name", "symbol"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.QUANTITY),
       new String[]
       {
       "kind", "attribute",
       "name", "quantity"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.PURCHASE_DATE),
       new String[]
       {
       "kind", "attribute",
       "name", "purchaseDate"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.PURCHASE_PRICE),
       new String[]
       {
       "kind", "attribute",
       "name", "purchasePrice"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.CURRENT_PRICE),
       new String[]
       {
       "kind", "attribute",
       "name", "currentPrice"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.COMPANY),
       new String[]
       {
       "kind", "attribute",
       "name", "company"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.HIGH_PRICE),
       new String[]
       {
       "kind", "attribute",
       "name", "highPrice"
       });

    addXSDMapping
      ((Property)stockSummaryType.getProperties().get(StockSummaryImpl.LOW_PRICE),
       new String[]
       {
       "kind", "attribute",
       "name", "lowPrice"
       });

    addXSDMapping
      (withdrawType,
       new String[] 
       {
       "name", "withdraw_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)withdrawType.getProperties().get(withdrawImpl.ACCOUNT_NUMBER),
       new String[]
       {
       "kind", "element",
       "name", "accountNumber"
       });

    addXSDMapping
      ((Property)withdrawType.getProperties().get(withdrawImpl.AMOUNT),
       new String[]
       {
       "kind", "element",
       "name", "amount"
       });

    addXSDMapping
      (withdrawResponseType,
       new String[] 
       {
       "name", "withdrawResponse_._type",
       "kind", "elementOnly"
       });

    addXSDMapping
      ((Property)withdrawResponseType.getProperties().get(withdrawResponseImpl.BALANCE),
       new String[]
       {
       "kind", "element",
       "name", "balance"
       });

  }
  
} //AccountFactoryImpl
