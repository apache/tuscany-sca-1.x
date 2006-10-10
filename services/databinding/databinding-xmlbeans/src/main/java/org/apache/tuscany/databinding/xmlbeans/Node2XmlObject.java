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
package org.apache.tuscany.databinding.xmlbeans;

import org.apache.tuscany.spi.databinding.PullTransformer;
import org.apache.tuscany.spi.databinding.TransformationContext;
import org.apache.tuscany.spi.databinding.TransformationException;
import org.apache.tuscany.spi.databinding.Transformer;
import org.apache.tuscany.spi.databinding.extension.TransformerExtension;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.osoa.sca.annotations.Service;
import org.w3c.dom.Node;

@Service(Transformer.class)
public class Node2XmlObject extends TransformerExtension<Node, XmlObject> implements
    PullTransformer<Node, XmlObject> {
    // private XmlOptions options;

    public XmlObject transform(Node source, TransformationContext context) {
        try {
            return XmlObject.Factory.parse(source);
        } catch (XmlException e) {
            throw new TransformationException(e);
        }
    }

    public Class getTargetType() {
        return XmlObject.class;
    }

    public Class getSourceType() {
        return Node.class;
    }

    public int getWeight() {
        return 30;
    }

}
