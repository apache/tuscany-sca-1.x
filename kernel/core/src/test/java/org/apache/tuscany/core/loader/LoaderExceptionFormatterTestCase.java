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
package org.apache.tuscany.core.loader;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.tuscany.spi.loader.LoaderException;

import junit.framework.TestCase;
import org.easymock.EasyMock;
import org.apache.tuscany.host.monitor.FormatterRegistry;

/**
 * @version $Rev$ $Date$
 */
public class LoaderExceptionFormatterTestCase extends TestCase {

    public void testLog() {
        FormatterRegistry registry = EasyMock.createNiceMock(FormatterRegistry.class);
        LoaderExceptionFormatter formatter = new LoaderExceptionFormatter(registry);
        LoaderException e = new LoaderException("test");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        formatter.write(pw, e);
        pw.close();
        assertTrue(!"message".equals(writer.toString()));
    }


}
