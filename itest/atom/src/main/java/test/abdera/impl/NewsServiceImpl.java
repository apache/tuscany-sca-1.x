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
package test.abdera.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osoa.sca.annotations.Init;

import test.abdera.Entry;
import test.abdera.Item;
import test.abdera.NewsService;
import test.abdera.NotFoundException_Exception;

public class NewsServiceImpl implements NewsService {
	private static Map<String, Entry> collection = new HashMap<String, Entry>();
	
	@Init
	public void init() {
		Item item = new Item();
		item.setName("Item Name 01");
		item.setTitle("Item title 01");
		
		Entry entry = new Entry();
		entry.setKey("1");
		entry.setData(item);
		
		collection.put((String)entry.getKey(), entry);
	}
	
	public List<Entry> getAll() {
		List<Entry> entries = new ArrayList<Entry>();
		for(Entry entry : collection.values()) {
			entries.add(entry);
		}
        return entries;
	}

	public Item get(String arg0) throws NotFoundException_Exception {
		return (Item) collection.get(arg0).getData();
	}

	public String post(String arg0, Item arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void put(String arg0, Item arg1) throws NotFoundException_Exception {
		// TODO Auto-generated method stub

	}

	public void delete(String arg0) throws NotFoundException_Exception {
		// TODO Auto-generated method stub

	}
	
	public List<Entry> query(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
