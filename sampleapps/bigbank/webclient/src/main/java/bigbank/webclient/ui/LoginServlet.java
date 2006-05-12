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
package bigbank.webclient.ui;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osoa.sca.CurrentModuleContext;
import org.osoa.sca.ModuleContext;

import bigbank.webclient.services.profile.LoginService;


public class LoginServlet extends HttpServlet {
    
    

    private ServletContext mContext;

    public void init(ServletConfig pCfg) throws ServletException {
        mContext = pCfg.getServletContext();
    }

    public void doPost(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException {
        
        if("logout".equals(pReq.getParameter("logout"))){
            HttpSession sess = pReq.getSession();
            if(sess != null) sess.invalidate();
            try {
                pResp.sendRedirect("login.html");
            } catch (IOException e) {
                
                e.printStackTrace();
                throw new ServletException(e);
            }
           
        }else{


        String login = pReq.getParameter("login");
        String password = pReq.getParameter("password");
        try {
            int resp = login(login, password);
            if (resp == LoginService.SUCCESS) {
               // mContext.getRequestDispatcher("/summary.jsp").forward(pReq, pResp);
                pResp.sendRedirect("summary.jsp");
            } else {
               // mContext.getRequestDispatcher("/login.html").forward(pReq, pResp);
                pResp.sendRedirect("login.html");
            }
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }
    }
    static int login(final String login, final String password) throws ServletException{


        ModuleContext moduleContext = CurrentModuleContext.getContext();
        LoginService loginMgr = (LoginService)
                moduleContext.locateService("LoginServiceComponent");

        if (loginMgr == null) {
            throw new ServletException("LoginManager not found");
        }

            try {
                return loginMgr.login(login, password);
            } catch (RemoteException e) {
                
                throw new ServletException(e);
            }
    
    }
        
    }
