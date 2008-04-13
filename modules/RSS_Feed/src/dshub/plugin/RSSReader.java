package dshub.plugin;

/* RSS Feed Plugin 0.01
 * http://forum.java.sun.com/thread.jspa?threadID=5275485
 */

import dshub.ClientHandler;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
public class RSSReader {
        
	private static RSSReader instance = null;
        
	private RSSReader() {
	}
             
	public static RSSReader getInstance() {
		if(instance == null) {
			instance = new RSSReader();	
		}
		return instance;
	}
	
	public void writeNews(ClientHandler cur_client) {
            
		try {
 
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			URL u = new URL("http://www.adcportal.com/?mode=rss");
			
			Document doc = builder.parse(u.openStream());
			
			NodeList nodes = doc.getElementsByTagName("item");
			
			for(int i=0;i<nodes.getLength();i++) {
				
				Element element = (Element)nodes.item(i);
								
				String ToastRegexp = "((<.{1,15}>|&(lt|gt|quot|nbsp);( )?)|(- )?<a href=\"|[0-9]{1,2} comments|<img src=\"http://www.*\" class=\"vertical\" alt=\".*\" (title=\".*\" )?/>|<ol style=\"list-style-type: arabic-numbers\">|<blockquote( class=\"uncited\")?>|\">)";
                                cur_client.sendFromBotPM("Title: " + getElementValue(element,"title").replaceAll(ToastRegexp,""));
                                cur_client.sendFromBotPM(" ");
                                cur_client.sendFromBotPM("Description: " + getElementValue(element,"description").replaceAll(ToastRegexp,""));				
                                cur_client.sendFromBotPM(" ");
                                cur_client.sendFromBotPM("Link: " + getElementValue(element,"link"));
				cur_client.sendFromBotPM("Publish Date: " + getElementValue(element,"pubDate"));
				System.out.println();
			}//for			
		}//try
		catch(Exception ex) {
		ex.printStackTrace();	
		}
		
	}
		
		
		private String getCharacterDataFromElement(Element e) {
			try {
				Node child = e.getFirstChild();
				if(child instanceof CharacterData) {
					CharacterData cd = (CharacterData) child;
					return cd.getData();
				}
			}
			catch(Exception ex) {
				
			}
			return "";			
		} //private String getCharacterDataFromElement
		
		protected float getFloat(String value) {
			if(value != null && !value.equals("")) {
				return Float.parseFloat(value);	
			}
			return 0;
		}
		
		protected String getElementValue(Element parent,String label) {
			return getCharacterDataFromElement((Element)parent.getElementsByTagName(label).item(0));	
		}
}
		

