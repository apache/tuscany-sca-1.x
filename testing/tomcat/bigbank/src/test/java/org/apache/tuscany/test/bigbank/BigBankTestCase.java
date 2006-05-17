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
package org.apache.tuscany.test.bigbank;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BigBankTestCase extends TestCase {
    // public static final String testUrl = "http://localhost:8080/tuscany-samples-bigbank-webclient";
    public static final String testUrl = "http://localhost:8080/sample-bigbank-webclient/";

    public void testBigBankDefault() throws Exception {
        System.err.println("Running: testcase:");
        // New HTMLunit web client
        WebClient client = new WebClient();
        client.setRedirectEnabled(true);

        // Going to have the WebClient connect to this URL
        URL url = new URL(testUrl);
        HtmlPage page = (HtmlPage) client.getPage(url);
        // System.out.println(page.getTitleText());

        // Check response code
        WebResponse resp = page.getWebResponse();
        assertTrue(resp.getStatusCode() <= 200);
        List forms = page.getForms();
        

        HtmlPage resultPage = null;
        WebResponse resultResponse= null;
        boolean formfound = false;
        for (Iterator iter = forms.iterator(); formfound != true && iter.hasNext();) {
            HtmlForm form = (HtmlForm) iter.next();

            if ("loginAction".equals(form.getActionAttribute())) {
                formfound = true;
                HtmlInput login = form.getInputByName("login");
                HtmlInput password = form.getInputByName("password");
                login.setValueAttribute("test");
                password.setValueAttribute("password");

                resultPage = (HtmlPage) form.submit();
                // Check response from servlet
                resultResponse = resultPage.getWebResponse();
                assertTrue(resultResponse.getStatusCode() <= 200);

              
                String response = resultResponse.getContentAsString();
                assertTrue(-1 != response.indexOf("Account Information for"));
                assertTrue(-1 != response.indexOf("test"));
                assertTrue(-1 != response.indexOf("134-42-6231"));
                assertTrue(-1 != response.indexOf("123.43"));
                assertTrue(-1 != response.indexOf("134-43-3941"));
                assertTrue(-1 != response.indexOf("23.12"));

                Pattern pat = Pattern.compile("LU.*7", Pattern.DOTALL);
                Matcher test = pat.matcher(response);
                assertTrue(test.find());
            }

        }
        forms = resultPage.getForms();
        
        formfound = false;
        for(Iterator iter = forms.iterator();  formfound != true && iter.hasNext();) {
            HtmlForm form = (HtmlForm) iter.next();

            if ("purchaseStock.jsp".equals(form.getActionAttribute())) {
                formfound = true;
                resultPage = (HtmlPage) form.submit();
            }

        }
        assertTrue(formfound);
        forms = resultPage.getForms();
        formfound = false;
        for (Iterator iter = forms.iterator(); formfound != true && iter.hasNext();) {
            HtmlForm form = (HtmlForm) iter.next();

            if ("FormServlet".equals(form.getActionAttribute())) {
                formfound = true;

                HtmlInput login = form.getInputByName("symbol");
                HtmlInput password = form.getInputByName("quantity");
                login.setValueAttribute("EDS");
                password.setValueAttribute("131");
                
                resultPage = (HtmlPage) form.submit();
                // Check response from servlet
                resultResponse = resultPage.getWebResponse();
                assertTrue(resultResponse.getStatusCode() <= 200);

             
                String response = resultResponse.getContentAsString();
                assertTrue(-1 != response.indexOf("Account Information for"));
                assertTrue(-1 != response.indexOf("test"));
                assertTrue(-1 != response.indexOf("134-42-6231"));
                assertTrue(-1 != response.indexOf("123.43"));
                assertTrue(-1 != response.indexOf("134-43-3941"));
                assertTrue(-1 != response.indexOf("23.12"));

                Pattern pat = Pattern.compile("LU.*7", Pattern.DOTALL);
                Matcher test = pat.matcher(response);
                assertTrue(test.find());
                pat = Pattern.compile("EDS.*131", Pattern.DOTALL);
                test = pat.matcher(response);
                assertTrue(test.find());

            }

        }
        assertTrue(formfound);
        forms = resultPage.getForms();
        formfound = false;
        for (Iterator iter = forms.iterator(); formfound != true && iter.hasNext();) {
            HtmlForm form = (HtmlForm) iter.next();

            if ("stockSale.jsp".equals(form.getActionAttribute())) {
//                System.err.println(form.asText());
                
                
                
                Pattern pat = Pattern.compile("EDS.*131", Pattern.DOTALL);
                
                Matcher test = pat.matcher(form.asText());
                if(!test.find()) continue;

                
                
                
                formfound = true;
                resultPage = (HtmlPage) form.submit();
                // Check response from servlet
                resultResponse = resultPage.getWebResponse();
                assertTrue(resultResponse.getStatusCode() <= 200);


            }

        }
        forms = resultPage.getForms();
        
        
        
        formfound = false;
        for(Iterator iter = forms.iterator();  formfound != true && iter.hasNext();) {
            HtmlForm form = (HtmlForm) iter.next();

            if ("FormServlet".equals(form.getActionAttribute())) {
                formfound = true;

                
                HtmlInput password = form.getInputByName("quantity");
                
                password.setValueAttribute("131");

                resultPage = (HtmlPage) form.submit();
                // Check response from servlet
                resultResponse = resultPage.getWebResponse();
                assertTrue(resultResponse.getStatusCode() <= 200);

                
                String response = resultResponse.getContentAsString();
                assertTrue(-1 != response.indexOf("Account Information for"));
                assertTrue(-1 != response.indexOf("test"));
                assertTrue(-1 != response.indexOf("134-42-6231"));
                assertTrue(-1 != response.indexOf("123.43"));
                assertTrue(-1 != response.indexOf("134-43-3941"));
                assertTrue(-1 != response.indexOf("23.12"));

                Pattern pat = Pattern.compile("LU.*7", Pattern.DOTALL);
                Matcher test = pat.matcher(response);
                assertTrue(test.find());
                // pat = Pattern.compile("EDS.*131", Pattern.DOTALL);
                // test = pat.matcher(response);
                //                assertFalse(test.find());


            }

        }
        assertTrue(formfound);
        
        forms = resultPage.getForms();
        formfound = false;
        for (Iterator iter = forms.iterator(); formfound != true && iter.hasNext();) {
            HtmlForm form = (HtmlForm) iter.next();

            if ("loginAction".equals(form.getActionAttribute())) {
                formfound = true;
                
                // FIXME this seems to performs a log-in again instead of logout...
                 resultPage = (HtmlPage) form.submit();

            }

        }
        assertTrue(formfound);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

}
