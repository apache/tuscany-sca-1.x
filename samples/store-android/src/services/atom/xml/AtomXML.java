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
package services.atom.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import services.Item;
import android.util.Log;

/**
 *
 */
public class AtomXML {
	
	public static boolean postItem(String ServiceURI, final Item item)
	{
		DefaultHttpClient client=new DefaultHttpClient();
		HttpPost httpost = new HttpPost(ServiceURI);
		
		httpost.setEntity(new HttpEntity(){
			
			String entry="<entry xmlns=\"http://www.w3.org/2005/Atom\">" +
			 "<title>item</title>" +
			 "<content type=\"text/xml\">" +
			  "<Item xmlns=\"http://services/\">" +
			  "<name xmlns=\"\">" + item.getName()+ "</name>" +
			 "<price xmlns=\"\">" +item.getPrice()+"</price>" +
			  "</Item></content></entry>";
			
			class mHeader implements Header
			{
				public HeaderElement[] getElements() throws ParseException {
					// TODO Auto-generated method stub
					return null;
				}

				public String getName() {
					// TODO Auto-generated method stub
					return "Content-type";
				}

				public String getValue() {
					// TODO Auto-generated method stub
					return "application/atom+xml;type=entry";
				}
			}

			public void consumeContent() throws IOException {
				// TODO Auto-generated method stub
				
			}

			public InputStream getContent() throws IOException,
					IllegalStateException {
				// TODO Auto-generated method stub
				return new InputStream(){

					public int read() throws IOException {
						// TODO Auto-generated method stub
						return this.available();
					}
					
				};
			}

			public Header getContentEncoding() {
				// TODO Auto-generated method stub
				return new mHeader();
			}

			public long getContentLength() {
				// TODO Auto-generated method stub
				return entry.length();
			}

			public Header getContentType() {
				// TODO Auto-generated method stub
				return new mHeader();
			}

			public boolean isChunked() {
				// TODO Auto-generated method stub
				return false;
			}

			public boolean isRepeatable() {
				// TODO Auto-generated method stub
				return true;
			}

			public boolean isStreaming() {
				// TODO Auto-generated method stub
				return false;
			}

			public void writeTo(OutputStream arg0) throws IOException {
				// TODO Auto-generated method stub
				
				arg0.write(entry.getBytes());
				arg0.flush();
				Log.i("Tuscany", "Entry: "+item+" posted via atom/xml");
			}		
			
		});
				 
		try {
			HttpResponse response = client.execute(httpost);
			Log.i("Tuscany", "Atom entry post status: "+response.getStatusLine().toString());
			return true;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("Tuscany",e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("Tuscany",e.getMessage());
		}
		
		return false;
	}	

}
