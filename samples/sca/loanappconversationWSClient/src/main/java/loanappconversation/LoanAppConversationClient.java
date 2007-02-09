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
package loanappconversation;

//import org.apache.tuscany.spi.component.TargetNotFoundException;
import org.osoa.sca.CompositeContext;
import org.osoa.sca.CurrentCompositeContext;

public class LoanAppConversationClient {

    public static void main(String[] args) throws Exception {
        // Locate the MyClient component and invoke it
        CompositeContext context = CurrentCompositeContext.getContext();

        LoanClient loanClient = context.locateService(LoanClient.class, "LoanClientComponent");
        assert loanClient != null : "loanClient was not resolved!";
        loanClient.applyForLoan("John Doe", 1000.0f);
        assert loanClient.isOpen() : "Failed loan not in approved state";
        System.out.println("Loan opened: " + loanClient.displayLoan());
        
        
        loanClient.cancelLoan();
        System.out.println("Sleeping to let cancel complete ...");
        Thread.sleep(5000);
        
        assert loanClient.isCancelled() : "Failed to cancel loan";
        System.out.println("Cancelled: " + loanClient.displayLoan());
        
        loanClient.closeLoan();
        
        System.out.println("Closing for a second time!");
        try{
            loanClient.closeLoan();
            assert false : "close for second time incorrectly succeed";
            System.out.println("Failed: close for second time incorrectly succeed.");
        }catch(Exception e){
            System.out.println("Second close successfully produced: '" + e.getClass().getName() +"'");
        }
        
    }
}
