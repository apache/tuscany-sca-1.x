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

importPackage(Packages.org.apache.tuscany.samples.bigbankjs.account);

function getAccountReport(customerID) {

    var accountFactory = new AccountFactory();

    var accountReport = accountFactory.createAccountReport();
    var accountSummaries = accountReport.getAccountSummaries();

    var checkingAccount = accountDataService.getCheckingAccount(customerID);
    var checkingAccountSummary = accountFactory.createAccountSummary();
    checkingAccountSummary.setAccountNumber(checkingAccount.getAccountNumber());
    checkingAccountSummary.setAccountType("checking");
    checkingAccountSummary.setBalance(fromUSDollarToCurrency(checkingAccount.getBalance()));
    accountSummaries.add(checkingAccountSummary);

    var savingsAccount = accountDataService.getSavingsAccount(customerID);
    var savingsAccountSummary = accountFactory.createAccountSummary();
    savingsAccountSummary.setAccountNumber(savingsAccount.getAccountNumber());
    savingsAccountSummary.setAccountType("savings");
    savingsAccountSummary.setBalance(fromUSDollarToCurrency(savingsAccount.getBalance()));
    accountSummaries.add(savingsAccountSummary);

    var stockAccount = accountDataService.getStockAccount(customerID);
    var stockAccountSummary = accountFactory.createAccountSummary();
    stockAccountSummary.setAccountNumber(stockAccount.getAccountNumber());
    stockAccountSummary.setAccountType("stock");

    var balance = stockQuoteService.getQuote(stockAccount.getSymbol()) * stockAccount.getQuantity();
    stockAccountSummary.setBalance(fromUSDollarToCurrency(balance));
    accountSummaries.add(stockAccountSummary);

    return accountReport;
}

function fromUSDollarToCurrency(value) {
   if (currency.equals("USD")) {
       return value;
   } else if (currency.equals("EURO")) {
       return value * 0.8;
   } else {
       return 0.0;
   }
}

