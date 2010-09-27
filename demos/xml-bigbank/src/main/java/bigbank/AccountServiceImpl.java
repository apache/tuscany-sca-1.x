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

package bigbank;

import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerRepository;
import org.osoa.sca.ServiceRuntimeException;
import org.osoa.sca.annotations.Property;
import org.osoa.sca.annotations.Reference;
import org.osoa.sca.annotations.Service;

/**
 * @version $Rev$ $Date$
 */
@Service(AccountService.class)
public class AccountServiceImpl implements AccountService {
    private static final String STOCK_QUOTE_REQUEST =
        "<q:symbol xmlns:q=\"http://www.webserviceX.NET/\">IBM</q:symbol>";

    private XMLInputFactory factory = XMLInputFactory.newInstance();

    @Reference
    protected ExchangeRate exchangeRate;

    @Reference
    protected StockQuote stockQuote;

    @Reference
    protected AccountData accountData;

    @Reference
    protected StockValue stockValue;

    @Property
    protected String currency;

    public double getTotalValue() {
        try {
            double rate = exchangeRate.getExchangeRate(currency);

            System.out.println("Loading account data...");
            XMLStreamReader accounts = accountData.getAccounts();

            System.out.println("Getting stock quote...");
            XMLStreamReader request = factory.createXMLStreamReader(new StringReader(STOCK_QUOTE_REQUEST));

            // temporarily disable INFO logging before calling the web service
            LoggerRepository repository = LogManager.getLoggerRepository();
            Level threshold = repository.getThreshold();
            repository.setThreshold(Level.WARN);

            // first try to get a live stock quote from the web service
            String xml = null;
            try {
                OMElement quotes = stockQuote.GetQuote(request);
                xml = quotes.getText();
                if (!xml.startsWith("<")) {
                    System.out.println("Server responded: " + xml);
                    throw new IllegalStateException("Unexpected response from server");
                }
            } catch (Exception e) {
                if (e.getMessage().contains("Transport error: 503")) {
                    // server is down, use local historical data
                } else {
                    // report any other errors
                    throw e;
                }
            
            // restore the previous logging setting
            } finally {
                repository.setThreshold(threshold);
            }

            // if the web service invocation was successful, process the response
            XMLStreamReader qts = null;
            if (xml != null) {
                System.out.println("Server responded: " + xml);
                qts = factory.createXMLStreamReader(new StringReader(xml));

            // if the web service isn't responding, continue with the demo using historical data 
            } else {
                System.out.println("Stock price live quote not available, using historical data");
                qts = factory.createXMLStreamReader(new StringReader(
                        "<StockQuotes>"+
                          "<Stock>"+
                            "<Symbol>IBM</Symbol>"+
                            "<Last>134.11</Last>"+
                            "<Date>9/24/2010</Date>"+
                            "<Time>4:00pm</Time>"+
                            "<Change>+2.44</Change>"+
                            "<Open>132.42</Open>"+
                            "<High>134.15</High>"+
                            "<Low>132.34</Low>"+
                            "<Volume>7122325</Volume>"+
                            "<MktCap>169.1B</MktCap>"+
                            "<PreviousClose>131.67</PreviousClose>"+
                            "<PercentageChange>+1.85%</PercentageChange>"+
                            "<AnnRange>116.00 - 134.25</AnnRange>"+
                            "<Earns>10.582</Earns>"+
                            "<P-E>12.44</P-E>"+
                            "<Name>International Bus</Name>"+
                          "</Stock>"+
                        "</StockQuotes>"));
            }

            System.out.println("Calculating total value...");
            double value = stockValue.calculate(qts, accounts);

            System.out.println("Total Value=USD " + value);

            return value * rate;
        } catch (Exception e) {
            throw new ServiceRuntimeException(e);
        }
    }

}
