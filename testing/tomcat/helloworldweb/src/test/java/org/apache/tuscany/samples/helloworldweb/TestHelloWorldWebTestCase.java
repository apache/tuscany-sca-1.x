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
package org.apache.tuscany.samples.helloworldweb;

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


public class TestHelloWorldWebTestCase extends TestCase {
	public static final String testUrl = "http://localhost:8080/helloworldWeb-SNAPSHOT/";

	public void testHelloWorldDefault() throws Exception
	{
		System.err.println("Running: testcase:");
		// New HTMLunit web client
		WebClient client = new WebClient();
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
			// Get the "name" form input field
			HtmlInput login = form.getInputByName("name");
			System.err.println(login.getValueAttribute());
			Page resultPage = form.submit();
			// Check response from servlet
			WebResponse resultResponse  = resultPage.getWebResponse();
			assertTrue(resultResponse.getStatusCode() <= 200);
			
			// TODO Check response content..
			String response = resultResponse.getContentAsString();
			
			Pattern pattern = Pattern.compile("Hello World");
			Matcher matcher = pattern.matcher(response.subSequence(0,response.length()));
			assertTrue(matcher.find());
			
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
