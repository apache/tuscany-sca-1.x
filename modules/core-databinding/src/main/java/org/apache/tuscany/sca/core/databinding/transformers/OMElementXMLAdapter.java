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
package org.apache.tuscany.sca.core.databinding.transformers;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.tuscany.sca.databinding.xml.XMLStreamReader2String;

/**
 * This special XmlAdapter can be used by JAXB classes to convert from OMElement to Source
 */
public class OMElementXMLAdapter extends XmlAdapter<Source, OMElement> {
	
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    
    @Override
    public Source marshal(OMElement v) throws Exception {
    	XMLStreamReader reader = v.getXMLStreamReaderWithoutCaching();
        XMLStreamReader2String t2 = new XMLStreamReader2String();
        String xml = t2.transform(reader, null);
        return new StreamSource(new StringReader(xml));    	
    }

    @Override
    public OMElement unmarshal(Source v) throws Exception {        
    	StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformerFactory.newTransformer().transform(v, result);
        XMLStreamReader reader = inputFactory.createXMLStreamReader(new StringReader(sw.toString())); 
        // Build OMElement from XMLStreamReader
        StAXOMBuilder builder = new StAXOMBuilder(reader);
        return builder.getDocumentElement();
    }
}
