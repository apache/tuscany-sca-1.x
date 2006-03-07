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
package org.apache.tuscany.samples.bigbank;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


public class TestBigBankTestCase extends TestCase {
	// public static final String testUrl = "http://localhost:8080/tuscany-samples-bigbank-webclient";
	public static final String testUrl = "http://localhost:8080/webclient-SNAPSHOT/login.html";
	

	public void testBigBankDefault() throws Exception
	{
		System.err.println("Running: testcase:");
		// New HTMLunit web client
		WebClient client = new WebClient();
		client.setRedirectEnabled(true);
		
		// Going to have the WebClient connect to this URL
		URL url = new URL(testUrl);
		HtmlPage page = (HtmlPage)client.getPage(url);
		//System.out.println(page.getTitleText());
		
		// Check response code
		WebResponse resp = page.getWebResponse();
		assertTrue(resp.getStatusCode() <= 200);
		List forms = page.getForms();
		Iterator iter = forms.iterator();
		
		// Iterate over all forms on page, should only be one.
		while(iter.hasNext())
		{
			HtmlForm form = (HtmlForm)iter.next();
			
			
			HtmlInput login = form.getInputByName("login");
			HtmlInput password = form.getInputByName("password");
			login.setValueAttribute("test");
			password.setValueAttribute("password");

			Page resultPage = form.submit();
			// Check response from servlet
			WebResponse resultResponse  = resultPage.getWebResponse();
			assertTrue(resultResponse.getStatusCode() <= 200);
			
			// TODO Check response content..
			String response = resultResponse.getContentAsString();
			assertTrue(-1 != response.indexOf("Account"));
			assertTrue(-1 != response.indexOf("Balance"));
			assertTrue(-1 != response.indexOf("12345_CHA12345"));
			assertTrue(-1 != response.indexOf("1200.0"));
			
			
			
			/**	
			 * 	or..
			 * 
			 * 	HtmlPage htmlResponse = (HtmlPage)resultPage;
			 *	HtmlElement element = htmlResponse.getHtmlElementById("ELEMENT-ID");
			 *	assertEquals(element.getNodeValue(), ... );
			 *	
			 *	or..
			 *
			 *	String resultString = resultResponse.getContentAsString();
			 *	assertEquals(resultString, ... );
			 *		
			 *  etc.
			 * 
			 **/
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	

	}

}
