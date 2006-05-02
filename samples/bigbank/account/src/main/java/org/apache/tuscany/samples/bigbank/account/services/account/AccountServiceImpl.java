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
package org.apache.tuscany.samples.bigbank.account.services.account;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.tuscany.samples.bigbank.account.services.accountdata.AccountDataService;
import org.apache.tuscany.samples.bigbank.account.services.stockquote.StockQuote;
import org.apache.tuscany.samples.bigbank.account.services.stockquote.StockQuoteService;
import org.apache.tuscany.sdo.util.SDOUtil;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountReport;
import com.bigbank.account.AccountService;
import com.bigbank.account.CustomerProfileData;
import com.bigbank.account.StockSummary;

@Service(interfaces=AccountService.class)
public class AccountServiceImpl implements AccountService {
    
    public static final String CHECKING_ACCOUNT_PREFIX= "134-43-394";
    public static final String SAVINGS_ACCOUNT_PREFIX= "134-42-623";
    public static final String ACCOUNT_TYPE_SAVINGS = "savings";
    public static final String ACCOUNT_TYPE_CHECKINGS = "checkings";

    static {
        SDOUtil.registerStaticTypes(AccountFactory.class);
    }

    private String currency = "USD";

    @Property
    public void setCurrency(String currency) {
        this.currency = currency;
    }


    
    private AccountDataService accountDataService;
    @Reference
    public void setAccountDataService(AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    private StockQuoteService stockQuoteService;
    @Reference
    public void setStockQuoteService(StockQuoteService stockQuoteService) {
        this.stockQuoteService = stockQuoteService;
    }


    public AccountServiceImpl() {
    }

//    public AccountReport getAccountReport2(int customerID) {
//        
//        AccountFactory accountFactory=new AccountFactory();
//
//        AccountReport accountReport = accountFactory.createAccountReport();
//        List accountSummaries = accountReport.getAccountSummaries();
//
//        CheckingAccount checkingAccount = accountDataService.getCheckingAccount(customerID);
//        AccountSummary checkingAccountSummary = accountFactory.createAccountSummary();
//        checkingAccountSummary.setAccountNumber(checkingAccount.getAccountNumber());
//        checkingAccountSummary.setAccountType("checking");
//        checkingAccountSummary.setBalance(fromUSDollarToCurrency(checkingAccount.getBalance()));
//        accountSummaries.add(checkingAccountSummary);
//
//        SavingsAccount savingsAccount = accountDataService.getSavingsAccount(customerID);
//        AccountSummary savingsAccountSummary = accountFactory.createAccountSummary();
//        savingsAccountSummary.setAccountNumber(savingsAccount.getAccountNumber());
//        savingsAccountSummary.setAccountType("savings");
//        savingsAccountSummary.setBalance(fromUSDollarToCurrency(savingsAccount.getBalance()));
//        accountSummaries.add(savingsAccountSummary);
//
//        StockAccount stockAccount = accountDataService.getStockAccount(customerID);
//        AccountSummary stockAccountSummary = accountFactory.createAccountSummary();
//        stockAccountSummary.setAccountNumber(stockAccount.getAccountNumber());
//        stockAccountSummary.setAccountType("stock");
//        float balance = (stockQuoteService.getQuote(stockAccount.getSymbol())) * stockAccount.getQuantity();
//        stockAccountSummary.setBalance(fromUSDollarToCurrency(balance));
//        accountSummaries.add(stockAccountSummary);
//
//        return accountReport;
//    }

    @SuppressWarnings("unchecked")
    public AccountReport getAccountReport(int customerID) throws RemoteException{
        
        try {
            AccountReport accountReport = accountDataService.getAccountReport(customerID);
            return updateStockInformation(accountReport);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof RemoteException) throw (RemoteException)e;
            else throw new RemoteException(e.getClass() + " " +e.getMessage(),e); 
        }        
    }
    
    private AccountReport updateStockInformation(AccountReport accountReport) throws RemoteException {
        List<StockSummary> stocks = accountReport.getStockSummaries();
        if(stocks.size() <1) return accountReport; //nothing todo
        HashSet<String> owned = new HashSet<String>(stocks.size());
        for(StockSummary stock : stocks){
            owned.add(stock.getSymbol());
        }
        ArrayList<String> ownedStr= new ArrayList<String>(owned.size()*5);
        for(String s : owned){
            
            ownedStr.add(s);
        }
        
         Map<String, StockQuote> stockInfo = stockQuoteService.getQuotes((String[])ownedStr.toArray(new String[owned.size()]));
         
         for(StockSummary stock : stocks){
            String symbol = stock.getSymbol();
            StockQuote stockquote= stockInfo.get(symbol);
            if(stockquote == null){
                stock.setCurrentPrice(Float.NaN);
                stock.setCompany("*not found*");
                stock.setHighPrice(Float.NaN);
                stock.setLowPrice(Float.NaN);
                
            }else{
            stock.setCurrentPrice(convertToFloat(stockquote.getStockQuote()));
            stock.setCompany(stockquote.getCompanyName());
            stock.setHighPrice(convertToFloat(stockquote.getDayHighPrice()));
            stock.setLowPrice(convertToFloat(stockquote.getDayLowPrice()));
            }
         }
        
        return accountReport;
    }
    
    float convertToFloat( final String s){
        
        
    try {
        return Float.parseFloat(s);
    } catch (Exception e) {
        return Float.NaN;
    }      
       
    }



    private float fromUSDollarToCurrency(float value) {

        if (currency.equals("USD")) return value;
        else if (currency.equals("EURO")) return value * 0.8f;
        else
            return 0.0f;
    }

    public CustomerProfileData getCustomerProfile(String logonID) throws RemoteException { 
        
    try{
        return accountDataService.getCustomerProfile(logonID);
    } catch (Exception e) {
        e.printStackTrace();
        if (e instanceof RemoteException) throw (RemoteException)e;
        else throw new RemoteException(e.getClass() + " " +e.getMessage(),e); 
    }        
    
    }

    public float deposit(String account, float ammount) throws RemoteException {
        try{
            return accountDataService.deposit(account, ammount);
        } catch (RemoteException  e){
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
             throw new RemoteException(e.getClass() + " " +e.getMessage(),e); 
        }        
    }

    public StockSummary purchaseStock(int param0, String param1, int param2) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public StockSummary sellStock(int param9, int param10) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    public float withdraw(String account, float ammount) throws RemoteException {
        try{
            return accountDataService.withdraw(account, ammount);
        } catch (RemoteException  e){
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
             throw new RemoteException(e.getClass() + " " +e.getMessage(),e); 
        }        
    }

    public CustomerProfileData createAccount(CustomerProfileData customerProfile, boolean createSavings, boolean createCheckings) throws RemoteException {
        try{
            return accountDataService.createAccount(customerProfile, createSavings, createCheckings);
        } catch (RemoteException  e){
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
             throw new RemoteException(e.getClass() + " " +e.getMessage(),e); 
        }        
    }
}
