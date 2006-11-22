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
package org.apache.tuscany.persistence.store.journal;

import java.util.UUID;

import junit.framework.TestCase;

/**
 * @version $Rev$ $Date$
 */
public class RecordKeyTestCase extends TestCase {

    public void testEquals() throws Exception {
        UUID id = UUID.randomUUID();
        RecordKey key1 = new RecordKey(id, "foo");
        RecordKey key2 = new RecordKey(id, "foo");
        assertEquals(key1, key2);
    }

    public void testNotEqualsId() throws Exception {
        UUID id = UUID.randomUUID();
        RecordKey key1 = new RecordKey(id, "foo");
        RecordKey key2 = new RecordKey(UUID.randomUUID(), "foo");
        assertFalse(key1.equals(key2));
    }

    public void testNotEqualsOwner() throws Exception {
        UUID id = UUID.randomUUID();
        RecordKey key1 = new RecordKey(id, "foo");
        RecordKey key2 = new RecordKey(id, "bar");
        assertFalse(key1.equals(key2));
    }

}
