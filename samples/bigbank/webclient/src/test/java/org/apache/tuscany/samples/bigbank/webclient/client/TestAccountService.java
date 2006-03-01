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
package org.apache.tuscany.samples.bigbank.webclient.client;

import java.util.List;

import org.apache.tuscany.core.client.TuscanyRuntime;
import org.apache.tuscany.samples.bigbank.account.AccountReport;
import org.osoa.sca.CurrentModuleContext;
import org.osoa.sca.ModuleContext;
import org.osoa.sca.SCA;

import com.bigbank.account.service.AccountService;


public class TestAccountService extends SCA {

    public void start() {
    }

    public void stop() {
    }

    public static void main(String[] args) throws Exception {
        TuscanyRuntime tuscany = new TuscanyRuntime("bigbank.webclient.testclient", null);
        tuscany.start();
        ModuleContext moduleContext = CurrentModuleContext.getContext();

        AccountService accountService = (AccountService) moduleContext.locateService("AccountServiceComponent");

        AccountReport report = accountService.getAccountReport("12345");
        List summaries = report.getAccountSummaries();

        System.out.println("retrieved " + summaries.size() + " summaries");

        tuscany.stop();
    }
}
