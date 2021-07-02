package com.qa.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class TestUtils {

    public static final long WAIT = 10;

    public HashMap<String, String> parseStringXML(InputStream file) throws ParserConfigurationException, IOException, SAXException {
        HashMap<String, String> stringMap = new HashMap<String, String>();
        //Get document builder
        DocumentBuilderFactory factory = new DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build Document
        Document document = builder.parse(file);

        //Normalize the XML structure; it is too important
        document.getDocumentElement().normalize();

        //Root node
        Element root = document.getDocumentElement();

        //Get all the elements
        NodeList nlist = document.getElementsByTagName("string");

        for(int temp=0; temp < nlist.getLength(); temp++){
            Node node = nlist.item(temp);

            if(node.getNodeType() == node.ELEMENT_NODE){
                Element eElement = (Element) node;
                //Store each element in key value
                stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
            }
        }
        return stringMap;
    }
}
