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
package org.apache.tuscany.samples.helloworldwebdas;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class TestHelloWorldWebDasTestCase extends TestCase {
    public static final String testUrl = "http://localhost:8080/tuscany-samples-customerWEB/";

    public void testHelloWorldDefault() throws Exception
    {
        System.err.println("Running: testcase:");

        // New HTMLunit web client
        WebClient client = new WebClient();

        // Going to have the WebClient connect to this URL
        URL url = new URL(testUrl);
        HtmlPage page = (HtmlPage)client.getPage(url);
        
        // Check response code
        WebResponse resp = page.getWebResponse();
        assertTrue(resp.getStatusCode() <= 200);
        List forms = page.getForms();

        //There is one form
        HtmlForm form = (HtmlForm)page.getForms().get(0);
        HtmlSubmitInput button = (HtmlSubmitInput)form.getInputByName("submit");

        // Click the button and get back the second page
        final HtmlPage page2 = (HtmlPage)button.click();

        HtmlTable table = (HtmlTable)page2.getHtmlElementById("table1");
        assertEquals("1", (table.getCellAt(1,0)).asText());
        assertEquals("Phil", (table.getCellAt(1,1)).asText());
        assertEquals("Smith", (table.getCellAt(1,2)).asText());

    }


}
