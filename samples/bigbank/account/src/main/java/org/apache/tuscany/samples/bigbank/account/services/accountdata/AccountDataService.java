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

import java.rmi.RemoteException;

import com.bigbank.account.AccountReport;
import com.bigbank.account.CustomerProfileData;

public interface AccountDataService {
    public CustomerProfileData getCustomerProfile(String logonID) throws RemoteException ;

//    public CheckingAccount getCheckingAccount(int customerID);

//    public SavingsAccount getSavingsAccount(int  customerID);
//
//    public StockAccount getStockAccount(int  customerID);
    
    
    
    public AccountReport getAccountReport(int customerID ) throws RemoteException;
    public CustomerProfileData createAccount(CustomerProfileData customerProfile, boolean createSavings, boolean createCheckings) throws RemoteException ;
    
}
