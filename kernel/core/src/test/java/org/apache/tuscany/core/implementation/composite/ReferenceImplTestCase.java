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
package org.apache.tuscany.core.implementation.composite;

import java.net.URI;

import org.apache.tuscany.spi.component.Reference;
import org.apache.tuscany.spi.component.ReferenceBinding;

import junit.framework.TestCase;
import org.easymock.EasyMock;

/**
 * @version $Rev$ $Date$
 */
public class ReferenceImplTestCase extends TestCase {

    public void testStart() {
        ReferenceBinding binding = EasyMock.createMock(ReferenceBinding.class);
        binding.setReference(EasyMock.isA(Reference.class));
        binding.start();
        EasyMock.replay(binding);
        Reference reference = new ReferenceImpl(URI.create("ref"), null, null);
        reference.addReferenceBinding(binding);
        reference.start();
        EasyMock.verify(binding);

    }

    public void testStop() {
        ReferenceBinding binding = EasyMock.createMock(ReferenceBinding.class);
        binding.setReference(EasyMock.isA(Reference.class));
        binding.stop();
        EasyMock.replay(binding);
        Reference reference = new ReferenceImpl(URI.create("ref"), null, null);
        reference.addReferenceBinding(binding);
        reference.stop();
        EasyMock.verify(binding);

    }
}
