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

import java.util.List;

import org.apache.tuscany.samples.bigbank.account.AccountFactory;
import org.apache.tuscany.samples.bigbank.account.AccountReport;
import org.apache.tuscany.samples.bigbank.account.AccountSummary;
import org.apache.tuscany.samples.bigbank.account.services.accountdata.AccountDataService;
import org.apache.tuscany.samples.bigbank.account.services.accountdata.CheckingAccount;
import org.apache.tuscany.samples.bigbank.account.services.accountdata.SavingsAccount;
import org.apache.tuscany.samples.bigbank.account.services.accountdata.StockAccount;
import org.apache.tuscany.samples.bigbank.account.services.stockquote.StockQuoteService;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import com.bigbank.account.service.AccountService;

public class AccountServiceImpl implements AccountService {

    private final static AccountFactory accountFactory=new AccountFactory();

    @Property
    private String currency = "USD";

    @Reference
    private AccountDataService accountDataService;
    @Reference
    private StockQuoteService stockQuoteService;

    public AccountServiceImpl() {
    }

    public AccountReport getAccountReport(String customerID) {

        AccountReport accountReport = accountFactory.createAccountReport();
        List accountSummaries = accountReport.getAccountSummaries();

        CheckingAccount checkingAccount = accountDataService.getCheckingAccount(customerID);
        AccountSummary checkingAccountSummary = accountFactory.createAccountSummary();
        checkingAccountSummary.setAccountNumber(checkingAccount.getAccountNumber());
        checkingAccountSummary.setAccountType("checking");
        checkingAccountSummary.setBalance(fromUSDollarToCurrency(checkingAccount.getBalance()));
        accountSummaries.add(checkingAccountSummary);

        SavingsAccount savingsAccount = accountDataService.getSavingsAccount(customerID);
        AccountSummary savingsAccountSummary = accountFactory.createAccountSummary();
        savingsAccountSummary.setAccountNumber(savingsAccount.getAccountNumber());
        savingsAccountSummary.setAccountType("savings");
        savingsAccountSummary.setBalance(fromUSDollarToCurrency(savingsAccount.getBalance()));
        accountSummaries.add(savingsAccountSummary);

        StockAccount stockAccount = accountDataService.getStockAccount(customerID);
        AccountSummary stockAccountSummary = accountFactory.createAccountSummary();
        stockAccountSummary.setAccountNumber(stockAccount.getAccountNumber());
        stockAccountSummary.setAccountType("stock");
        float balance = (stockQuoteService.getQuote(stockAccount.getSymbol())) * stockAccount.getQuantity();
        stockAccountSummary.setBalance(fromUSDollarToCurrency(balance));
        accountSummaries.add(stockAccountSummary);

        return accountReport;
    }

    private float fromUSDollarToCurrency(float value) {

        if (currency.equals("USD")) return value;
        else if (currency.equals("EURO")) return value * 0.8f;
        else
            return 0.0f;
    }
}
