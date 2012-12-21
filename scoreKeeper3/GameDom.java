package scoreKeeper3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.String;
import java.util.Vector;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;






public class GameDom {
	
	//No generics
	List myEmpls;
	int nRounds = 0;
	int round = 0;
	int nQSets = 0;
	int qSet = 0;
	int nItems = 0;
	int item = 0;
	Document dom;
	//Ordered list of nodes currently displayed
	Vector<Node> displayNodeList = new Vector(); 
	//Stack indicating the current position in the dom and it's parent heirarchy
	Stack<Node> activeNodeStack =  new Stack();
	
	public GameDom(){
		
		displayNodeList.clear();
		
	}
	
	public void parseXmlFile(String file){
		
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//Using factory get an instance of document builder
			dbf.setValidating(true);
			dbf.setIgnoringElementContentWhitespace(true);
			dbf.setCoalescing(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			//TODO: choose which xml to load 
			dom = db.parse(file);
		}
		catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		catch(SAXException se) {
			se.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		//get the root element
		Element docEle = dom.getDocumentElement();
		NodeList nl = docEle.getElementsByTagName("round");
		if(nl != null && nl.getLength() > 0) {
			nRounds = nl.getLength();
			NodeList qsnl = ((Element)nl.item(round)).getElementsByTagName("questionset");
			if(qsnl != null && qsnl.getLength() > 0) {
				nQSets = qsnl.getLength();
				NodeList inl = ((Element)qsnl.item(qSet)).getElementsByTagName("item");
				if(inl != null && inl.getLength() > 0) {
					nItems = inl.getLength();
				}
			}
		}

		activeNodeStack.push(docEle);
		System.out.println(activeNodeStack);
	}
	

	public boolean loadNextItemBrute(){

		//System.out.println("----------------------------------");
		//System.out.println(activeNodeStack);
		//Drill down		
		if (activeNodeStack.peek().getFirstChild() != null ){
			activeNodeStack.push(activeNodeStack.peek().getFirstChild());
		}
		//Step Accross
		else if (activeNodeStack.peek().getNextSibling() != null){
			activeNodeStack.push(activeNodeStack.peek().getNextSibling());
		}
		//Move up and over.
		else if (!activeNodeStack.empty()){
			//TODO: what if we run out of nodes...
			while(activeNodeStack.peek().getNextSibling() == null){
				//System.out.println("Pop up ^");
				Node popped = activeNodeStack.pop();
				if (popped.getNodeName() == "answer"){
					int n = displayNodeList.size();
					displayNodeList.remove(n-1);
					displayNodeList.remove(n-2);
				}
				if(popped.getNodeName() == "item"){
					displayNodeList.clear();
				}
				if(activeNodeStack.empty()){
					return false;
				}
			}
			activeNodeStack.push(activeNodeStack.pop().getNextSibling());
		}
		//Empty active node stack.
		else{
			return false;
		}
		//Decide what to do with displaynode list depending on what was just loaded
		if (activeNodeStack.lastElement().getNodeName() == "questionset" || activeNodeStack.lastElement().getNodeName() == "round"){
			displayNodeList.clear();
			return loadNextItemBrute();
		}
		if(activeNodeStack.lastElement().getNodeName() == "item"){
			return loadNextItemBrute();
		}
		if(activeNodeStack.lastElement().getNodeName() == "title" || activeNodeStack.lastElement().getNodeName() == "number" || activeNodeStack.lastElement().getNodeName() == "directions" || activeNodeStack.lastElement().getNodeName() == "question" || activeNodeStack.lastElement().getNodeName() == "answer"){
			displayNodeList.add(activeNodeStack.lastElement());
			return true;
		}
		//TODO: we're going to miss any elements here that are immediately usable.
		//TODO: this next call will immediatly start drilling down.
		return loadNextItemBrute();
				
	}

	
	//Show the display results as a string
	public String displayAsString(){
		int i=0;
		int len = displayNodeList.size();
		//System.out.println(len);
		String output = new String("");
		for(i = 0; i<len; i++){
			output = output.concat("...");
			output = output.concat(((Element)displayNodeList.get(i)).getTagName());
			output = output.concat(" --> ");
			output = output.concat(((Element)displayNodeList.get(i)).getTextContent());
			output = output.concat("\n");
		}
		return output;
		
	}
}

	


	
	
/*	
	
	/**
	 * I take an employee element and read the values in, create
	 * an Employee object and return it
	 *
	private Employee getEmployee(Element empEl) {

		//for each <employee> element get text or int values of
		//name ,id, age and name
		String name = getTextValue(empEl,"Name");
		int id = getIntValue(empEl,"Id");
		int age = getIntValue(empEl,"Age");

		String type = empEl.getAttribute("type");

		//Create a new Employee with the value read from the xml nodes
		Employee e = new Employee(name,id,age,type);

		return e;
	}


	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is 'name' I will return John
	 *
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 *
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}

	
	private void printData(){

		System.out.println("No of Employees '" + myEmpls.size() + "'.");

		Iterator it = myEmpls.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

}*/
