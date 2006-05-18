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
package bigbank.account.services.accountdata;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.tuscany.das.rdb.ApplyChangesCommand;
import org.apache.tuscany.das.rdb.Command;
import org.apache.tuscany.das.rdb.CommandGroup;
import org.apache.tuscany.das.rdb.Converter;
import org.osoa.sca.annotations.Service;

import bigbank.account.services.account.AccountServiceImpl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountReport;
import com.bigbank.account.AccountSummary;
import com.bigbank.account.CustomerProfileData;
import com.bigbank.account.DataGraphRoot;
import com.bigbank.account.StockSummary;
import commonj.sdo.DataObject;
import commonj.sdo.helper.TypeHelper;

@Service(AccountDataService.class)
public class AccountDataServiceDASImpl implements AccountDataService {

    static public String dbDirectory = null;

    public static final DateFormat tsformatXSDDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz");

    public static final DateFormat sqlformatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSz");

    static {
        tsformatXSDDateTime.setTimeZone(TimeZone.getTimeZone("UTC"));

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
            final AccountFactory accountFactory = AccountFactory.INSTANCE;
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
            throw new RemoteException(e.getClass().getName(), e);
        }

    }

    public StockSummary sellStock(int purchaseLotNumber, int quantity) throws RemoteException {
        try {
            CommandGroup commandGroup = CommandGroup.FACTORY.createCommandGroup(createConfigStream());
            commandGroup.setConnection(getConnection());

            Command read = commandGroup.getCommand("stockbylotSelect");
            TypeHelper helper = TypeHelper.INSTANCE;
            read.setDataObjectModel(helper.getType(DataGraphRoot.class));
            read.setParameterValue("PURCHASELOTNUMBER", purchaseLotNumber);// autoboxing :-)
            DataGraphRoot root = (DataGraphRoot) read.executeQuery();
            List stocks = root.getStockSummaries();
            if (null != stocks && !stocks.isEmpty()) {
                StockSummary stock = (StockSummary) stocks.get(0);
                int newQuatity = Math.max(stock.getQuantity() - quantity, 0);
                if (newQuatity < 1) {

                    Command delete = Command.FACTORY.createCommand("DELETE FROM STOCKS WHERE PURCHASELOTNUMBER = ?");
                    delete.setParameterValue(1, purchaseLotNumber);
                    delete.setConnection(getConnection());
                    delete.execute();

                } else {

                    Command update = commandGroup.getCommand("stockbylot");

                    update.setParameterValue("QUANTITY", newQuatity);
                    update.setParameterValue("PURCHASELOTNUMBER", purchaseLotNumber);
                    update.execute();

                    stock.setQuantity(newQuatity);
                }

            }

            return null;
        } catch (Exception e) {
            throw new RemoteException("sellStock", e);
        }
    }

    public StockSummary purchaseStock(int id, StockSummary stock) throws RemoteException {

        try {

            Command insert = Command.FACTORY
                    .createCommand("insert into stocks (id, symbol, quantity, purchasePrice, purchaseDate) values (?,?,?,?,?)");
            insert.setParameterValue(1, new Integer(id));
            insert.setParameterValue(2, stock.getSymbol());
            insert.setParameterValue(3, stock.getQuantity());
            insert.setParameterValue(4, stock.getPurchasePrice());
            insert.setParameterValue(5, DateConverter.INSTANCE.getColumnValue(stock.getPurchaseDate()));

            insert.setConnection(getConnection());
            insert.execute();

            return stock;
        } catch (Exception e) {
            if (e instanceof RemoteException)
                throw (RemoteException) e;
            throw new RemoteException("purchaseStock " + e.getClass().getName() + "'. " + e.getMessage(), e);
        }
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
        public final static DateConverter INSTANCE = new DateConverter();

        public DateConverter() {
        }

        public Object getPropertyValue(Object columnData) {

            try {

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

            if (propertyData instanceof java.util.Date) {
                // Need to convert back to local time for DB and remove timezone notation at the end..
                String ret = sqlformatDateTime.format(propertyData);
                char lc = ret.charAt(ret.length() - 1);
                while (!Character.isDigit(lc)) {
                    ret = ret.substring(0, ret.length() - 1);
                    lc = ret.charAt(ret.length() - 1);
                }
                return ret;
            } else if (propertyData instanceof String) {

                try {
                    String time = (String) propertyData;
                    char last = time.charAt(time.length() - 1);
                    if (last == 'z' || last == 'Z') {
                        time = time.substring(0, time.length() - 1);
                    }
                    if (!time.endsWith("UTC")) {
                        time = time + "UTC";
                    }
                    return getColumnValue(tsformatXSDDateTime.parse(time));
                } catch (ParseException e) {
                    throw new IllegalArgumentException("'" + propertyData + "' does not parse to date.");
                }
            } else
                throw new IllegalArgumentException();

        }

    }
}
