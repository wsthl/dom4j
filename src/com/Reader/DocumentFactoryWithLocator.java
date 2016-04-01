package com.Reader;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;

import org.xml.sax.Locator;

/**
 * @author Big_TANG
 * 
 * add Attributes into DocumentFactory
 *
 */
public class DocumentFactoryWithLocator extends DocumentFactory {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Locator locator;  //attribute locator
  
    public DocumentFactoryWithLocator(Locator locator) {  
        super();  
        this.locator = locator;  
    }  
  
    /**
     *  get the Element by locator 
     *  @param qname	name
	 * 
	 */
    @Override  
    public Element createElement(QName qname) {  
        GokuElement element = new GokuElement(qname);  
        element.setLocation(this.locator.getLineNumber(), this.locator.getColumnNumber());  
        return  element;  
    }  
    
    /**
     *  get the Element by locator 
     *  @param String	name
	 * 
	 */
    @Override  
    public Element createElement(String name) {  
        GokuElement element = new GokuElement(name);  
        element.setLocation(this.locator.getLineNumber(), this.locator.getColumnNumber());  
        return element;  
    }  
    
    /**
     *  setters for locator
     *  @param Locator locator
	 * 
	 */
    public void setLocator(Locator locator) {  
        this.locator = locator;  
    }  
}  
