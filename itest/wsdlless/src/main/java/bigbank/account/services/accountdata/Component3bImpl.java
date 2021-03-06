/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

package bigbank.account.services.accountdata;

import org.osoa.sca.annotations.Reference;

import com.bigbank.account.AccountLog;
import com.bigbank.account.AccountReport;
import com.bigbank.account.CustomerProfileData;
import com.bigbank.account.StockSummary;

public class Component3bImpl implements AccountDataService {

    AccountDataService accountService;
    
    @Reference
    public void setAccountService(AccountDataService accountService) {
        this.accountService = accountService;
    }
    
    public CustomerProfileData createAccount(CustomerProfileData param9, boolean param10, boolean param11) {
        return accountService.createAccount(param9, param10, param11);
    }

    public float deposit(String param6, float param7) {
        
        return 0;
    }

    public AccountLog getAccountLog(int param0) {
        
        return null;
    }

    public AccountReport getAccountReport(int param19) {
        
        return accountService.getAccountReport(param19);
    }

    public CustomerProfileData getCustomerProfile(String param4) {
        
        return null;
    }

    public StockSummary purchaseStock(int param0, StockSummary parm1) {
        
        return null;
    }

    public StockSummary sellStock(int param13, int param14) {
        
        return null;
    }

    public float withdraw(String param16, float param17) {
        
        return 0;
    }

}
