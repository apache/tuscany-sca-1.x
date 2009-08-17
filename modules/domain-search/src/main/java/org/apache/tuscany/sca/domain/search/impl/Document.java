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
package org.apache.tuscany.sca.domain.search.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

import org.apache.lucene.document.Fieldable;

/**
 * 
 * @version $Rev$ $Date$
 */
public class Document {

    private Hashtable<String, Hashtable<String, Fieldable>> fieldablesTable =
        new Hashtable<String, Hashtable<String, Fieldable>>();

    private Hashtable<String, LinkedList<Fieldable>> readerMap = new Hashtable<String, LinkedList<Fieldable>>();;

    public Document() {
        // empty constructor
    }

    public void add(Fieldable fieldable) {

        String strValue = fieldable.stringValue();

        if (strValue != null) {

            Hashtable<String, Fieldable> fieldables = this.fieldablesTable.get(fieldable.name());

            if (fieldables == null) {
                fieldables = new Hashtable<String, Fieldable>();
                this.fieldablesTable.put(fieldable.name(), fieldables);

            }

            fieldables.put(strValue, fieldable);

        } else {

            LinkedList<Fieldable> fieldables = this.readerMap.get(fieldable.name());

            if (fieldables == null) {
                fieldables = new LinkedList<Fieldable>();
                this.readerMap.put(fieldable.name(), fieldables);

            }

            fieldables.add(fieldable);

        }

    }

    public org.apache.lucene.document.Document createLuceneDocument() {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();

        for (Hashtable<String, Fieldable> fieldables : this.fieldablesTable.values()) {

            for (Fieldable fieldable : fieldables.values()) {
                doc.add(fieldable);
            }

        }

        for (LinkedList<Fieldable> fieldables : this.readerMap.values()) {

            for (Fieldable fieldable : fieldables) {
                doc.add(fieldable);
            }

        }

        return doc;

    }

    public Collection<String> getFieldValues(String field) {
        Hashtable<String, Fieldable> fieldables = this.fieldablesTable.get(field);

        if (fieldables != null) {
            return fieldables.keySet();
        }

        return Collections.emptyList();

    }

    public boolean containsField(String field) {
        return this.fieldablesTable.containsKey(field);
    }

}
