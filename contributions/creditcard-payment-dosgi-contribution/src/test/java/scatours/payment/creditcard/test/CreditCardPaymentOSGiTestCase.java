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

package scatours.payment.creditcard.test;

import static scatours.payment.creditcard.test.OSGiTestUtils.bundleStatus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.apache.tuscany.sca.node.equinox.launcher.EquinoxHost;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

/**
 *
 */
public class CreditCardPaymentOSGiTestCase {
    private static EquinoxHost host;
    private static BundleContext context;
    private static Bundle ccBundle;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            host = new EquinoxHost();
            context = host.start();

            for (Bundle b : context.getBundles()) {
                System.out.println(b);
                if (b.getSymbolicName().equals("org.eclipse.equinox.ds") || b.getSymbolicName()
                    .startsWith("org.apache.tuscany.sca.")) {
                    try {
                        if (b.getHeaders().get(Constants.FRAGMENT_HOST) == null) {
                            // Start the non-fragment bundle
                            b.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(bundleStatus(b, false));
                }
                if ("scatours.creditcard.payment".equals(b.getSymbolicName())) {
                    ccBundle = b;
                }
            }

            if (ccBundle != null) {
                ccBundle.start();
                System.out.println(bundleStatus(ccBundle, false));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    // @Ignore("Now we use the testClient")
    public void testWS() throws Exception {
        URL url = new URL("http://localhost:8082/CreditCardPayment?wsdl");
        InputStream is = url.openStream();
        Reader reader = new InputStreamReader(is);
        char[] content = new char[10240]; // 10k
        int len = 0;
        while (true) {
            int size = reader.read(content, len, content.length - len);
            if (size < 0) {
                break;
            }
            len += size;
        }
        Assert.assertTrue(len > 0);
        String str = new String(content, 0, len);
        System.out.println(str);
        Assert.assertTrue(str.indexOf("<wsdl:definitions") != -1);
    }

    /**
     * Test the Web service exposed by the Calculator
     */
    @Test
    public void testClient() {
        String status = CreditCardPaymentClient.invoke();
        Assert.assertTrue(status.startsWith("SUCCESS"));
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (host != null) {
            host.stop();
            host = null;
            context = null;
        }
    }

}
