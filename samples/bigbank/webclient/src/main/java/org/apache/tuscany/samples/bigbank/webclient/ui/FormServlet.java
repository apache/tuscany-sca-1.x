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
package org.apache.tuscany.samples.bigbank.webclient.ui;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tuscany.samples.bigbank.webclient.services.account.AccountServiceComponentImpl;
import org.apache.tuscany.sdo.util.SDOUtil;
import org.osoa.sca.CurrentModuleContext;
import org.osoa.sca.ModuleContext;

import com.bigbank.account.AccountFactory;
import com.bigbank.account.AccountService;
import com.bigbank.account.CustomerProfileData;
import commonj.sdo.helper.TypeHelper;
import commonj.sdo.helper.XSDHelper;


public class FormServlet extends HttpServlet {
    
    static {
        SDOUtil.registerStaticTypes(AccountFactory.class);
        TypeHelper th = SDOUtil.createTypeHelper();
        XSDHelper xsdHelper = SDOUtil.createXSDHelper(th);

        InputStream xsdInputStream = AccountServiceComponentImpl.class.getClassLoader().getResourceAsStream("wsdl/AccountService.wsdl");
        xsdHelper.define(xsdInputStream, null);
    }
    private ServletContext mContext;
    public void init(ServletConfig pCfg) throws ServletException {
        mContext = pCfg.getServletContext();
    }

    
    public void doPost(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException {

      
        try {
            ModuleContext moduleContext = CurrentModuleContext.getContext();
            AccountService accountServices = (AccountService) moduleContext.locateService("AccountServiceComponent");
            if (accountServices == null) {
                throw new ServletException("AccountServiceComponent");
            }
            final String action = pReq.getParameter("action");
            if ("createAccount".equals(action)) {
                createAccount(pReq, pResp, accountServices);
            } else if ("account".equals(action)) {
                accountTransaction(pReq, pResp, accountServices);
            }
            // mContext.getRequestDispatcher("summary.jsp").forward(pReq, pResp);
             pResp.sendRedirect("summary.jsp");
        } catch (ServletException e) {
            e.printStackTrace();
            throw e;
            
        } catch(Exception e){
            e.printStackTrace();
            throw new ServletException(e);
        }
        

      
    }

    private void accountTransaction(HttpServletRequest req, HttpServletResponse resp, AccountService accountServices) throws NumberFormatException, RemoteException {
        String account = req.getParameter("account");
        String amount = req.getParameter("Amount");
        
        if("deposit".equals(req.getParameter("actionType")))
            accountServices.deposit(account, Float.parseFloat(amount));
        else
            accountServices.withdraw(account,  Float.parseFloat(amount));
        
    }

    private void createAccount(HttpServletRequest pReq, HttpServletResponse pResp, AccountService accountServices) throws ServletException {
        try {
            CustomerProfileData customerProfileData = AccountFactory.eINSTANCE.createCustomerProfileData();
            customerProfileData.setFirstName(pReq.getParameter("firstName"));
            customerProfileData.setLastName(pReq.getParameter("lastName"));
            customerProfileData.setAddress(pReq.getParameter("address"));
            customerProfileData.setEmail(pReq.getParameter("email"));
            customerProfileData.setLoginID(pReq.getParameter("loginID"));
            customerProfileData.setPassword(pReq.getParameter("password"));
            
            
            CustomerProfileData resp = accountServices.createAccount(customerProfileData, "savings".equals(pReq.getParameter("savings")) , "checkings".equals(pReq.getParameter("checkings")));
            LoginServlet.login(resp.getLoginID(), resp.getPassword());
             
        } catch (IOException e) {
            throw new ServletException(e);
        }
        
    }
}
