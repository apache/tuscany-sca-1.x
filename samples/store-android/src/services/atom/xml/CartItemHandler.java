/**
 * 
 */
package services.atom.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import services.Item;


/**
 * @author Lookouster
 *
 */
public class CartItemHandler extends DefaultHandler {
	

    
    private boolean in_entry = false;
    private boolean in_id = false;
    private boolean in_title = false;
    private boolean in_content=false;
    private boolean in_item=false;
    private boolean in_link=false;
    private boolean in_name=false;
    private boolean in_price=false;
    private String currentKey,currentName,currentPrice;
    private List<Item> items=new ArrayList<Item>();
    
    

    
    /**
	 * @return the currentName
	 */
	public String getCurrentName() {
		return currentName;
	}


	/**
	 * @return the currentPrice
	 */
	public String getCurrentPrice() {
		return currentPrice;
	}


	public void startElement(String namespaceURI, String localName,
              String qName, Attributes atts) throws SAXException {
         if (localName.equalsIgnoreCase("entry")) {
              this.in_entry = true;
         }else if (localName.equalsIgnoreCase("id")) {
              this.in_id = true;
         }else if (localName.equalsIgnoreCase("title")) {
              this.in_title = true;
         }else if (localName.equalsIgnoreCase("content")) {
             this.in_content = true;
         }else if (localName.equalsIgnoreCase("item")) {
             this.in_item = true;
         }else if (localName.equals("link")) {
              this.in_link=true;
         }
         else if (localName.equalsIgnoreCase("name")) {
             this.in_name=true;
        }
         else if (localName.equalsIgnoreCase("price")) {
             this.in_price=true;
        }
    }
    
    
    public void endElement(String namespaceURI, String localName, String qName)
              throws SAXException {
    	if (localName.equalsIgnoreCase("id"))
            this.in_id = false;
    	if (localName.equalsIgnoreCase("entry")) {
            this.in_entry = false;
       }else if (localName.equalsIgnoreCase("id")) {
            this.in_id = false;
       }else if (localName.equalsIgnoreCase("title")) {
            this.in_title = false;
       }else if (localName.equalsIgnoreCase("content")) {
           this.in_content = false;
       }else if (localName.equalsIgnoreCase("item")) {
           this.in_item = false;
           items.add(new Item(currentName, currentPrice, currentKey));
       }else if (localName.equalsIgnoreCase("link")) {
            this.in_link=false;
       }
       else if (localName.equalsIgnoreCase("name")) {
           this.in_name=false;
      }
       else if (localName.equalsIgnoreCase("price")) {
           this.in_price=false;
      }
    }
    
    
   public void characters(char ch[], int start, int length) {
	   
         if(this.in_id){
        	 if(this.in_entry)
        	 {
        		 currentKey=new String(ch,start, length);
        		 Log.e("kjhkh", currentKey);
        	 }
        		
    }
         if(this.in_name)
        	 currentName=new String(ch,start, length);
         if(this.in_price)
        	 currentPrice=new String(ch,start, length);        
   
   }

	/**
	 * @return the key
	 */
	public String getCurrentKey() {
		return currentKey;
	} 
	
	public Item[] getItemsCollection()
	{
		return items.toArray(new Item[items.size()]);
	}
	
	

}
