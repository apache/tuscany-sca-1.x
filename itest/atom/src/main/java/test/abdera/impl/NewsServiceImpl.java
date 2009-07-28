package test.abdera.impl;

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
        return (List<Entry>) collection.values();
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
