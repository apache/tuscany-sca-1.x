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
package bigbank.account.client;

import java.util.Iterator;

import org.apache.tuscany.core.client.TuscanyRuntime;
import org.osoa.sca.CurrentModuleContext;
import org.osoa.sca.ModuleContext;
import org.osoa.sca.SCA;

import com.bigbank.account.AccountReport;
import com.bigbank.account.AccountService;
import com.bigbank.account.AccountSummary;

public class AccountClient extends SCA {

    public void start() {
    }

    public void stop() {
    }

    public static void main(String[] args) throws Exception {
        TuscanyRuntime tuscany = new TuscanyRuntime("bigbank.account.testclient", null);
        tuscany.start();
        ModuleContext moduleContext = CurrentModuleContext.getContext();

        AccountService accountService = (AccountService) moduleContext.locateService("AccountServiceComponent");

        AccountReport accountReport = accountService.getAccountReport(12345);

        for (Iterator i = accountReport.getAccountSummaries().iterator(); i.hasNext();) {
            AccountSummary accountSummary = (AccountSummary) i.next();

            System.out.println(accountSummary.getAccountNumber());
            System.out.println(accountSummary.getAccountType());
            System.out.println(accountSummary.getBalance());
        }

    }

}
