package com.asep.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.asep.util.Topic;

public class TopicDao {
DatabaseXMLAcces dataAccess = new DatabaseXMLAcces();	
	
	
public List<Topic> getTopics()	{
String param = "o=get";
Document doc = dataAccess.getResultXML("topics.php", param);
List<Topic> topics = new ArrayList<Topic>(); 
Element document = doc.getDocumentElement();
NodeList nodes = document.getElementsByTagName("result");

for(int i = 0; i<nodes.getLength();i++){
	
NodeList rowNodes = nodes.item(i).getChildNodes();
   
  String title = rowNodes.item(0).getChildNodes().item(0).getNodeValue();
  int topicId = Integer.parseInt(rowNodes.item(1).getChildNodes().item(0).getNodeValue());		 
  Topic topic = new Topic(title,topicId);
  topics.add(topic);   
  System.out.println("Topic title-"+title);
  
  
  }


return topics;
	
}
	 

}
