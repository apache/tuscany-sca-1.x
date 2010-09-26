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
package org.apache.tuscany.sca.databinding.saxon;

import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;

import net.sf.saxon.Configuration;
import net.sf.saxon.event.Builder;
import net.sf.saxon.om.NodeInfo;

import org.apache.tuscany.sca.databinding.DataPipe;
import org.apache.tuscany.sca.databinding.PullTransformer;
import org.apache.tuscany.sca.databinding.TransformationContext;
import org.apache.tuscany.sca.databinding.TransformationException;
import org.apache.tuscany.sca.databinding.impl.BaseTransformer;
import org.apache.tuscany.sca.databinding.xml.SAX2DOMPipe;
import org.apache.tuscany.sca.databinding.xml.StAX2SAXAdapter;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

/**
 * Transforms an XMLStreamReader to a NodeInfo object needed by Saxon parser.
 *
 * Any namespaces that are defined are deleted, because otherwise
 * the SaxonB parser does not work
 *
 * @version $Rev: 671701 $ $Date: 2008-06-26 02:29:06 +0100 (Thu, 26 Jun 2008) $
 */
public class XMLStreamReader2NodeInfoTransformer extends BaseTransformer<XMLStreamReader, NodeInfo> implements
    PullTransformer<XMLStreamReader, NodeInfo> {

    private StAX2SAXAdapter stax2sax = new StAX2SAXAdapter(false);
    private SAX2DOMPipe sax2dom = new SAX2DOMPipe();

    public NodeInfo transform(XMLStreamReader source, TransformationContext context) {      
        Configuration configuration = SaxonDataBindingHelper.CURR_EXECUTING_CONFIG;
        if (configuration == null) {
            configuration = new Configuration();
        }        
        
        NodeInfo docInfo = null;
        try {
            DataPipe<ContentHandler, Node> pipe = sax2dom.newInstance();
            stax2sax.parse(source, pipe.getSink());
            Node node = pipe.getResult();
            source.close();
            docInfo = Builder.build(new DOMSource(node), null, configuration);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
        return docInfo;
    }

    @Override
    protected Class<XMLStreamReader> getSourceType() {
        return XMLStreamReader.class;
    }

    @Override
    protected Class<NodeInfo> getTargetType() {
        return NodeInfo.class;
    }

    @Override
    public int getWeight() {
        return 10;
    }

}
