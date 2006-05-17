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
package bigbank.account.services.accountdb;

import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.tuscany.das.rdb.ApplyChangesCommand;
import org.apache.tuscany.das.rdb.Command;
import org.apache.tuscany.das.rdb.CommandGroup;
import org.apache.tuscany.das.rdb.Converter;
import org.apache.tuscany.das.rdb.config.ConfigFactory;
import org.apache.tuscany.sdo.util.SDOUtil;

import bigbank.account.services.account.AccountServiceImpl;
import bigbank.account.services.accountdata.AccountDataServiceDASImpl;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountSummary;
import com.bigbank.account.CustomerProfileData;
import com.bigbank.account.DataGraphRoot;
import com.bigbank.account.StockSummary;
import com.bigbank.account.purchaseStock;
import com.bigbank.account.withdraw;
import commonj.sdo.DataObject;
import commonj.sdo.helper.TypeHelper;

public class AccountDBInit extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -4795999792460944805L;

    protected static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";

    protected static final String protocol = "jdbc:derby:";

    boolean deleteExisting = false;

    protected String dbDirectory = null;


    @Override
    public void init() throws ServletException {
        try {

            registerTypes();
            ServletConfig servletConfig = getServletConfig();
            ServletContext servletContext = servletConfig.getServletContext();
            this.dbDirectory = servletContext.getRealPath("WEB-INF/bigbankdb/accounts");
            this.deleteExisting = false;

            createDB(dbDirectory);
           

        } catch (Exception e) {

            e.printStackTrace();
            log(e.toString(), e);
            throw new ServletException(e);
        }
    }

    public AccountDBInit() {
    }

    public AccountDBInit(String dbDirectory, Boolean deleteExisting) {
        this.dbDirectory = dbDirectory;
        this.deleteExisting = deleteExisting;
    }

    public static void  createDB(final String location) throws Exception {
        Connection conn = null;
        AccountDataServiceDASImpl.dbDirectory = location;
        Exception processessingException = null;
        try {
            conn = createConnection(location);

            creatTables(conn);

            int id = createCustomer(conn, "Test", "User", "304 Fox Trot ln, Apex, NC", "test@das.org", "test", "password");
            createAccount(conn, id, AccountServiceImpl.SAVINGS_ACCOUNT_PREFIX + id, AccountServiceImpl.ACCOUNT_TYPE_SAVINGS, 123.43F);
            createAccount(conn, id, AccountServiceImpl.CHECKING_ACCOUNT_PREFIX+id, AccountServiceImpl.ACCOUNT_TYPE_CHECKINGS, 23.12F);
            createStockPurchase(conn, id, "IBM", 33, 66.20F, "2005-11-23 13:22:02");
            createStockPurchase(conn, id, "DELL", 13, 12.74F, "2003-01-03 11:04:03");
            createStockPurchase(conn, id, "LU", 7, 2.74F, "2003-01-04 16:04:03");
            createStockPurchase(conn, id, "IBM", 22, 81.43F, "2004-02-03 13:04:33");

            id = createCustomer(conn, "test2", "demo2", "Pleasant Plains Rd, ViewMount, CO","test2@das.org", "test2", "password");
            createAccount(conn, id, AccountServiceImpl.SAVINGS_ACCOUNT_PREFIX + id, AccountServiceImpl.ACCOUNT_TYPE_SAVINGS, 924.40F);
            createAccount(conn, id, AccountServiceImpl.CHECKING_ACCOUNT_PREFIX+id, AccountServiceImpl.ACCOUNT_TYPE_CHECKINGS, 33.26F);
            createStockPurchase(conn, id, "FOO", 3, 6.20F, "2000-09-11 09:11:01");



            conn.commit();
            
            

        } catch (org.apache.derby.impl.jdbc.EmbedSQLException e) {
            if (conn != null && !conn.isClosed())
                conn.rollback();
            if (e.getErrorCode() == 20000 && "X0Y32".equalsIgnoreCase(e.getSQLState()) && -1 != e.getMessage().indexOf("already exists")) {
                // this is ok the database is there.

            } else {

                e.printStackTrace();
                processessingException = e;
                throw e;
            }
        } finally {
            try {
                if (null != conn && !conn.isClosed()) {

                    conn.close();
                }
                conn = null;
            } catch (Exception e) {
                e.printStackTrace();
                if (null != processessingException) {
                    throw e;
                }
            }
        }

    }

    private Connection createConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        return createConnection(dbDirectory);
    }

    private static Connection createConnection(final String location) throws InstantiationException, IllegalAccessException, ClassNotFoundException,
            SQLException {
        Connection conn;
        Class.forName(driver).newInstance();
        Properties props = new Properties();
        conn = DriverManager.getConnection(protocol + location + ";create=true", props);

        conn.setAutoCommit(false);
        return conn;
    }

    protected static void creatTables(Connection conn) throws Exception {
        
        
        
        Statement s = conn.createStatement();
        s
                .execute("create table customers(firstName varchar(80) NOT NULL, lastName varchar(80), address varchar(180),email varchar(40),loginID varchar(80) NOT NULL UNIQUE, password varchar(80), id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY)");

        s.execute("create table accounts(id int NOT NULL, accountNumber varchar(80) NOT NULL UNIQUE, accountType varchar(80), balance real )");

        s
                .execute("create table stocks(id int NOT NULL, Symbol varchar(8) NOT NULL, quantity int NOT NULL, purchasePrice real NOT NULL, purchaseDate TIMESTAMP, purchaseLotNumber int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY  )");

        s.close();
    }

    protected static int createCustomer(Connection conn, final String firstName, final String lastName, final String address, final String email,  final String logonID, final String password)
            throws SQLException, Exception {
        Statement s = conn.createStatement();

        s.execute("insert into customers (firstName,lastName,address,email, loginID, password  ) values ('" + firstName + "', '" + lastName + "', '"+ address + "', '" + email + "', '"  + logonID
                + "', '" + password + "')");

        ResultSet rs = s.executeQuery("SELECT loginID, id FROM customers where loginID='" + logonID + "'");
        if (!rs.next()) {
            throw new Exception("Wrong number of rows");
        }
        int id = rs.getInt(2);
        s.close();

        return id;
    }

    protected static void createAccount(Connection conn, int customerID, final String accountNumber, final String accountType, final float balance)
            throws SQLException, Exception {
        Statement s = conn.createStatement();

        s.execute("insert into accounts (id,accountNumber, accountType, balance  ) values (" + customerID + ", '" + accountNumber + "', '"
                + accountType + "', " + balance + ")");

        s.close();


    }

    protected InputStream createConfigStream() {
        InputStream mapping = getClass().getClassLoader().getResourceAsStream("basicStaticCustomer.xml");
        return mapping;
    }

    protected static void createStockPurchase(Connection conn, int customerID, final String stockSymbol, final int quantity, final float purchasePrice,
            String purchaseDate) throws SQLException, Exception {
        Statement s = conn.createStatement();

        s.execute("insert into stocks (id, symbol, quantity, purchasePrice, purchaseDate  ) values (" + customerID + ", '" + stockSymbol + "', "
                + quantity + ", " + purchasePrice + ", '" + purchaseDate + "')");

        s.close();
       
    }

    protected static void registerTypes() {
        SDOUtil.registerStaticTypes(AccountFactory.class);
        // TODO remove
        SDOUtil.registerStaticTypes(ConfigFactory.class);

    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        registerTypes();
        String dbDirectory = null; // "c:/derbydbtest/foo";
        Boolean deleteExisting = false;
        for (String x : args) {
            if ("-d".equals(x)) {
                deleteExisting = true;
            }
            if (!x.startsWith("-")) {
                dbDirectory = x;
            }

        }
        AccountDBInit accountDBInit = new AccountDBInit(dbDirectory, deleteExisting);

        createDB(dbDirectory);
        accountDBInit.readDBstdout(System.out);


        // Test withdrawl
        withdraw wd = AccountFactory.INSTANCE.createwithdraw();
        wd.setAccountNumber("134-43-39438");
        wd.setAmount(1.00F);
        // accountDBInit.testWithdrawThroughDAS(wd);

        // test stock purchase.

        purchaseStock sp = AccountFactory.INSTANCE.createpurchaseStock();
        StockSummary stock= AccountFactory.INSTANCE.createStockSummary();
        sp.setStock(stock);
        stock.setSymbol("GOOG");
        sp.setId(1);
        stock.setQuantity(10);
        accountDBInit.testStrockPurchaseThroughDAS(sp);

        accountDBInit.readDBstdout(System.out);

        System.out.flush();
    }

    protected void testStrockPurchaseThroughDAS(purchaseStock sp) throws InstantiationException, IllegalAccessException, ClassNotFoundException,
            SQLException {

        CommandGroup commandGroup = CommandGroup.FACTORY.createCommandGroup(createConfigStream());
        commandGroup.setConnection(createConnection());
        Command read = commandGroup.getCommand("all stocks");

        DataObject root = read.executeQuery();

        // Create a new stockPurchase
        DataObject stockPurchase = root.createDataObject("STOCKS");
        stockPurchase.set("ID", new Integer(sp.getId()));
        stockPurchase.set("SYMBOL", sp.getStock().getSymbol());
        stockPurchase.set("QUANTITY", new Integer(sp.getStock().getQuantity()));
        stockPurchase.set("PURCHASEPRICE", new Float(11.00));
        stockPurchase.set("PURCHASEDATE", new Date());

        ApplyChangesCommand apply = commandGroup.getApplyChangesCommand();

        apply.execute(root);



    }

    public CustomerProfileData testgetCustomerByLoginIDThroughDASRead(final String logonID) throws Exception {

        InputStream mapping = createConfigStream();

        Command select = Command.FACTORY.createCommand("SELECT firstName, lastName, loginID, password, id FROM customers where loginID = :loginID",
                mapping);
        Connection conn = createConnection();
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

    protected void readDBstdout(PrintStream stream) throws Exception {
        Connection conn = null;
        try {
            conn = createConnection(dbDirectory);
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT firstName, lastName, loginID, password, id FROM customers ORDER BY lastName");
            while (rs.next()) {
                stream.print(rs.getString(1));
                stream.print(" ");
                stream.print(rs.getString(2));
                stream.print(" ");
                stream.print(rs.getString(3));
                stream.print(" ");
                stream.print(rs.getString(4));
                stream.print(" ");
                stream.print(rs.getString(5));
                stream.println();
                int id = rs.getInt(5);
                Statement s1 = conn.createStatement();
                ResultSet rs1 = s1.executeQuery("SELECT accountNumber, accountType, balance FROM accounts where id=" + id);
                while (rs1.next()) {
                    stream.print("\t");
                    stream.print(rs1.getString(1));
                    stream.print(" ");
                    stream.print(rs1.getString(2));
                    stream.print(" ");
                    stream.print(rs1.getString(3));
                    stream.println();
                }
                rs1.close();
                rs1 = s1.executeQuery("SELECT symbol, quantity, purchasePrice, purchaseDate,  purchaseLotNumber FROM stocks where id=" + id);
                while (rs1.next()) {
                    stream.print("\t");
                    stream.print(rs1.getString(1));
                    stream.print(" ");
                    stream.print(rs1.getString(2));
                    stream.print(" ");
                    stream.print(rs1.getString(3));
                    stream.print(" ");
                    stream.print(rs1.getString(4));
                    stream.print(" ");
                    stream.print(rs1.getString(5));
                    stream.println();
                }
                s1.close();
                stream.println();

            }
            s.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.close();
            conn = null;
        }

    }

    public void testWithdrawThroughDAS(withdraw wd) throws Exception {

        Command select = Command.FACTORY.createCommand("SELECT accountNumber, balance FROM accounts where accountNumber = :accountNumber",
                createConfigStream());
        Connection conn = createConnection();
        select.setConnection(conn);
        select.setParameterValue("accountNumber", wd.getAccountNumber());
        TypeHelper helper = TypeHelper.INSTANCE;

        select.setDataObjectModel(helper.getType(DataGraphRoot.class));

        DataGraphRoot root = (DataGraphRoot) select.executeQuery();

        Collection accounts = root.getAccountSummaries();
        AccountSummary account = (AccountSummary) accounts.iterator().next();
        float newbalance = account.getBalance() - wd.getAmount();
        account.setBalance(newbalance);
        // update department set companyid = ? where department.name = ?

        CommandGroup commandGroup = CommandGroup.FACTORY.createCommandGroup(createConfigStream());
        commandGroup.setConnection(conn);
        Command update = commandGroup.getCommand("update balance");
        update.setParameterValue("BALANCE", new Float(newbalance));
        update.setParameterValue("ACCOUNTNUMBER", wd.getAccountNumber());

        update.execute();
        conn.close();

    }

    public static class MyDateConverter implements Converter {

        private static DateFormat tsformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        public Object getPropertyValue(Object columnData) {

            try {
                return tsformat.parse(columnData.toString());
            } catch (ParseException e) {

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
