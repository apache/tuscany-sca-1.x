/**
 *
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tuscany.samples.bigbank.account.services.accountdata;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.tuscany.das.rdb.ApplyChangesCommand;
import org.apache.tuscany.das.rdb.Command;
import org.apache.tuscany.das.rdb.CommandGroup;
import org.apache.tuscany.das.rdb.Converter;
import org.apache.tuscany.samples.bigbank.account.services.account.AccountServiceImpl;
import org.apache.tuscany.sdo.util.SDOUtil;
import org.osoa.sca.annotations.Service;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountReport;
import com.bigbank.account.AccountService;
import com.bigbank.account.AccountSummary;
import com.bigbank.account.CustomerProfileData;
import com.bigbank.account.DataGraphRoot;
import com.bigbank.account.StockSummary;

import commonj.sdo.DataObject;
import commonj.sdo.helper.TypeHelper;

@Service(AccountDataService.class)
public class AccountDataServiceDASImpl implements AccountDataService {
    // TODO get rid of this!!
    static public String dbDirectory = null;
    static {
        SDOUtil.registerStaticTypes(AccountFactory.class);
    }

    public CustomerProfileData getCustomerProfile(String logonID) throws RemoteException {

        try {
            InputStream mapping = createConfigStream();
            Command select = Command.FACTORY.createCommand(
                    "SELECT firstName, lastName, loginID, password, id FROM customers where loginID = :loginID", mapping);
            Connection conn = getConnection();
            select.setConnection(conn);
            select.setParameterValue("loginID", logonID);
            TypeHelper helper = TypeHelper.INSTANCE;

            select.setDataObjectModel(helper.getType(DataGraphRoot.class));

            DataGraphRoot root = (DataGraphRoot) select.executeQuery();
            conn.close();

            Collection customers = root.getCustomerProfileData();
            CustomerProfileData customerProfileData = (CustomerProfileData) customers.iterator().next();
            System.out.println(customerProfileData);
            System.out.flush();
            return customerProfileData;
        } catch (Exception e) {

            e.printStackTrace();
            RemoteException re = new RemoteException("Failed to get customer profile'" + logonID + "' ", e);
            re.printStackTrace();
            throw re;
        }
    }

    protected static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    protected static final String protocol = "jdbc:derby:";

    public CustomerProfileData testgetCustomerByLoginIDThroughDASRead(final String logonID) throws Exception {

        InputStream mapping = createConfigStream();

        Command select = Command.FACTORY.createCommand("SELECT firstName, lastName, loginID, password, id FROM customers where loginID = :loginID",
                mapping);
        Connection conn = getConnection();
        select.setConnection(conn);
        select.setParameterValue("loginID", logonID);
        TypeHelper helper = TypeHelper.INSTANCE;

        select.setDataObjectModel(helper.getType(DataGraphRoot.class));

        DataGraphRoot root = (DataGraphRoot) select.executeQuery();
        conn.close();

        Collection customers = root.getCustomerProfileData();
        CustomerProfileData customerProfileData = (CustomerProfileData) customers.iterator().next();
System.out.println(customerProfileData);
System.out.flush();
        return customerProfileData;

    }

    public CustomerProfileData createAccount(CustomerProfileData customerProfile, boolean createSavings, boolean createCheckings)
            throws RemoteException {

        try {
            Command insert = Command.FACTORY.createCommand("insert into customers (firstName,lastName,address,email, loginID, password  ) values ('"
                    + customerProfile.getFirstName() + "', '" + customerProfile.getLastName() + "', '" + customerProfile.getAddress() + "', '"
                    + customerProfile.getEmail() + "', '" + customerProfile.getLoginID() + "', '" + customerProfile.getPassword() + "')");
            insert.setConnection(getConnection());
            insert.execute();
            CustomerProfileData ret = getCustomerProfile(customerProfile.getLoginID());
            String cid = ret.getId() + "";
            if (createSavings) {
                insert = Command.FACTORY.createCommand("insert into accounts (id,accountNumber, accountType, balance  ) values (" + cid + ", '"
                        + AccountServiceImpl.SAVINGS_ACCOUNT_PREFIX + cid + "', '" + AccountServiceImpl.ACCOUNT_TYPE_SAVINGS + "', " + 1.0F + ")");
                insert.setConnection(getConnection());
                insert.execute();

            }
            if (createCheckings) {
                insert = Command.FACTORY.createCommand("insert into accounts (id,accountNumber, accountType, balance  ) values (" + cid + ", '"
                        + AccountServiceImpl.CHECKING_ACCOUNT_PREFIX + cid + "', '" + AccountServiceImpl.ACCOUNT_TYPE_CHECKINGS + "', " + 1.0F + ")");
                insert.setConnection(getConnection());
                insert.execute();

            }

            return ret;
        } catch (Exception e) {
            if (e instanceof RemoteException)
                throw (RemoteException) e;
            throw new RemoteException("createAccount " + e.getClass().getName() + "'. " + e.getMessage(), e);
        }
    }

    public CustomerProfileData createAccountNOTWORKING(CustomerProfileData customerProfile, boolean createSavings, boolean createCheckings)
            throws RemoteException {
        try {
            CommandGroup commandGroup = CommandGroup.FACTORY.createCommandGroup(createConfigStream());
            commandGroup.setConnection(getConnection());
            Command read = commandGroup.getCommand("all customers");

            // select.setDataObjectModel();
            TypeHelper helper = TypeHelper.INSTANCE;
            read.setDataObjectModel(helper.getType(DataGraphRoot.class));
            DataObject root = read.executeQuery();

            // Create a new stockPurchase
            DataObject customer = root.createDataObject("customerProfileData");

            // THIS SEEMS TO BE THE ONLY WAY TO DO THIS .. NO WAY TO JUST ADD AN EXISTING CUSTOMER.
            customer.set("firstName", customerProfile.getFirstName());
            customer.set("lastName", customerProfile.getLastName());
            customer.set("address", customerProfile.getAddress());
            customer.set("email", customerProfile.getEmail());
            customer.set("loginID", customerProfile.getLoginID());
            customer.set("password", customerProfile.getPassword());

            ApplyChangesCommand apply = commandGroup.getApplyChangesCommand();
            apply.execute(root);
            return getCustomerProfile(customerProfile.getLoginID());

        } catch (Exception e) {
            if (e instanceof RemoteException)
                throw (RemoteException) e;
            throw new RemoteException("createAccount " + e.getClass().getName() + "'. " + e.getMessage(), e);
        }

    }

    public AccountReport getAccountReport(final int customerID) throws RemoteException {
        try {
            final AccountFactory accountFactory = AccountFactory.eINSTANCE;
            final AccountReport accountReport = accountFactory.createAccountReport();
            InputStream mapping = createConfigStream();

            Command select = Command.FACTORY.createCommand("SELECT accountNumber, accountType, balance FROM accounts where id = :id", mapping);
            Connection conn = getConnection();
            select.setConnection(conn);
            select.setParameterValue("id", customerID);
            TypeHelper helper = TypeHelper.INSTANCE;
            select.setDataObjectModel(helper.getType(DataGraphRoot.class));
            DataGraphRoot root = (DataGraphRoot) select.executeQuery();
            accountReport.getAccountSummaries().addAll(root.getAccountSummaries());

            // Get Stocks

            select = Command.FACTORY.createCommand(
                    "SELECT Symbol, quantity, purchasePrice, purchaseDate, purchaseLotNumber  FROM stocks where id = :id", createConfigStream());
            select.setConnection(conn);
            select.setParameterValue("id", customerID);
            select.setDataObjectModel(helper.getType(DataGraphRoot.class));

            // select.addConverter("STOCKS.PURCHASEDATE", DateConverter.class.getName());

            root = (DataGraphRoot) select.executeQuery();
            accountReport.getStockSummaries().addAll(root.getStockSummaries());

            conn.close();

            return accountReport;
        } catch (Exception e) {
            if (e instanceof RemoteException)
                throw (RemoteException) e;
            throw new RemoteException("getAccountReport failed. customerID ('" + customerID + "')" + e.getClass().getName() + "'. " + e.getMessage(),
                    e);
        }
    }

    public float withdraw(String account, float ammount) throws RemoteException {
        
        return deposit(account, -ammount);
    }

    public float deposit(String account, float ammount) throws RemoteException {

        try {
            Command select = Command.FACTORY.createCommand("SELECT accountNumber, balance FROM accounts where accountNumber = :accountNumber",
                    createConfigStream());
            Connection conn = getConnection();
            select.setConnection(conn);
            select.setParameterValue("accountNumber", account);
            TypeHelper helper = TypeHelper.INSTANCE;
            select.setDataObjectModel(helper.getType(DataGraphRoot.class));
            DataGraphRoot root = (DataGraphRoot) select.executeQuery();
            Collection accounts = root.getAccountSummaries();
            AccountSummary accountData = (AccountSummary) accounts.iterator().next();
            float newbalance = accountData.getBalance() + ammount;
            accountData.setBalance(newbalance);
            // update department set companyid = ? where department.name = ?
            CommandGroup commandGroup = CommandGroup.FACTORY.createCommandGroup(createConfigStream());
            commandGroup.setConnection(conn);
            Command update = commandGroup.getCommand("update balance");
            update.setParameterValue("BALANCE", new Float(newbalance));
            update.setParameterValue("ACCOUNTNUMBER", account);
            update.execute();
            conn.close();
            return newbalance;
        } catch (Exception e) {
            throw new RemoteException(e.getClass().getName() ,e);
        }        

    }

    public StockSummary sellStock(int param13, int param14) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public StockSummary purchaseStock(int param0, String param1, int param2) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    protected Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName(driver).newInstance();
        Properties props = new Properties();
        // props.put("user", "tuscany");
        // props.put("password", "tuscany");
        conn = DriverManager.getConnection(protocol + dbDirectory + ";create=true", props);
    
        conn.setAutoCommit(false);
        return conn;
    }

    protected InputStream createConfigStream() {
        InputStream mapping = getClass().getClassLoader().getResourceAsStream("DasAccountConfiguration.xml");
        return mapping;
    }

    public static class DateConverter implements Converter {
        public DateConverter() {
        }
    
        private static final DateFormat tsformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
        private static final DateFormat tsformatXSDDate = new SimpleDateFormat("yyyy-MM-dd");
    
        private static final DateFormat tsformatXSDDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");
    
        public Object getPropertyValue(Object columnData) {
    
            try {
    
                tsformatXSDDateTime.setTimeZone(TimeZone.getTimeZone("UTC"));
                String ret = tsformatXSDDateTime.format(columnData);
                if (ret.endsWith("UTC"))
                    ret = ret.substring(0, ret.length() - 3) + "Z";
                return ret;
    
            } catch (Exception e) {
    
                e.printStackTrace();
                throw new IllegalArgumentException(e);
            }
    
        }
    
        public Object getColumnValue(Object propertyData) {
    
            if (propertyData instanceof Date) {
                return tsformat.format(propertyData);
            } else
                throw new IllegalArgumentException();
    
        }
    
    }
}
