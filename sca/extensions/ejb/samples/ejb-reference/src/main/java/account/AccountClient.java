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
package account;

import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

/**
 * This shows how to test ejb reference bidning sample in J2SE env
 */

public class AccountClient {

    public static void main(String[] args) throws Exception {
        CompositeContext context = CurrentCompositeContext.getContext();
        Customer customer = (Customer)context.locateService(Customer.class, "CustomerComponent");
        String accountNo = "1234567890"; // This is one of the customer
                                            // numbers in bank application
                                            // running on Geronimo
        // invoke composite method
        Double balance = customer.depositAmount(accountNo, new Double(100));
        System.out.println("Balance amount for account " + accountNo + " is $" + balance);
    }
}
