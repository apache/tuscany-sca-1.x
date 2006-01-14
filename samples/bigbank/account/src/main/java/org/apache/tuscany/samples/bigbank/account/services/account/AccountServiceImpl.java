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

import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;

import org.apache.tuscany.core.deprecated.sdo.util.DataFactory;
import org.apache.tuscany.core.deprecated.sdo.util.impl.HelperProviderImpl;
import org.apache.tuscany.samples.bigbank.account.services.accountdata.AccountDataService;
import org.apache.tuscany.samples.bigbank.account.services.accountdata.CheckingAccount;
import org.apache.tuscany.samples.bigbank.account.services.accountdata.SavingsAccount;
import org.apache.tuscany.samples.bigbank.account.services.accountdata.StockAccount;
import org.apache.tuscany.samples.bigbank.account.services.stockquote.StockQuoteService;

public class AccountServiceImpl implements AccountService {

    private DataFactory dataFactory;

    @Property
    private String currency = "USD";

    @Reference
    private AccountDataService accountDataService;
    @Reference
    private StockQuoteService stockQuoteService;

    public AccountServiceImpl() {
        //FIXME How do we get a DataFactory now?? looks like there's no way to inject one into the component...
        dataFactory = new HelperProviderImpl().getDataFactory();
    }

    public AccountReport getAccountReport(String customerID) {

        AccountReport accountReport = (AccountReport) dataFactory.create(AccountReport.class);
        List accountSummaries = accountReport.getAccountSummaries();

        CheckingAccount checkingAccount = accountDataService.getCheckingAccount(customerID);
        AccountSummary checkingAccountSummary = (AccountSummary) dataFactory.create(AccountSummary.class);
        checkingAccountSummary.setAccountNumber(checkingAccount.getAccountNumber());
        checkingAccountSummary.setAccountType("checking");
        checkingAccountSummary.setBalance(fromUSDollarToCurrency(checkingAccount.getBalance()));
        accountSummaries.add(checkingAccountSummary);

        SavingsAccount savingsAccount = accountDataService.getSavingsAccount(customerID);
        AccountSummary savingsAccountSummary = (AccountSummary) dataFactory.create(AccountSummary.class);
        savingsAccountSummary.setAccountNumber(savingsAccount.getAccountNumber());
        savingsAccountSummary.setAccountType("savings");
        savingsAccountSummary.setBalance(fromUSDollarToCurrency(savingsAccount.getBalance()));
        accountSummaries.add(savingsAccountSummary);

        StockAccount stockAccount = accountDataService.getStockAccount(customerID);
        AccountSummary stockAccountSummary = (AccountSummary) dataFactory.create(AccountSummary.class);
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
