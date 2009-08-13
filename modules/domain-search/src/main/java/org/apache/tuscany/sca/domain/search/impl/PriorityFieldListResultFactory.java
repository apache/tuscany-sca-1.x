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

import java.util.LinkedList;

import org.apache.lucene.document.Document;
import org.apache.tuscany.sca.domain.search.Result;
import org.apache.tuscany.sca.domain.search.ResultFactory;

/**
 * 
 * @version $Rev$ $Date$
 */
public class PriorityFieldListResultFactory extends LinkedList<String> implements ResultFactory<Result> {
	
	private static final long serialVersionUID = 6806221945324235828L;

	public PriorityFieldListResultFactory() {
		// empty constructor
	}
	
	public Result createResult(Document document) {
		
		for (String field : this) {
			String value = document.get(field);
			
			if (value != null) {
				return new ResultImpl(field, value);
			}
			
		}
		
		return null;
		
	}

	public Result createResult(String field, String value) {
		return new ResultImpl(field, value);
	}

}
