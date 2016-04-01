package com.Reader;

import org.dom4j.DocumentFactory;
import org.dom4j.ElementHandler;
import org.dom4j.io.SAXContentHandler;
import org.xml.sax.Locator;

/**
 * @author Big_TANG
 * 
 * extends SAXContentHandler
 *
 */
public class GokuSAXContentHandler extends SAXContentHandler {  
    private DocumentFactoryWithLocator documentFactory = null;  
  
    public GokuSAXContentHandler(DocumentFactory documentFactory2, ElementHandler dispatchHandler) {  
        super(documentFactory2, dispatchHandler);  
    }  
  
    public void setDocFactory(DocumentFactoryWithLocator fac) {  
        this.documentFactory = fac;  
    }  
  
    @Override  
    public void setDocumentLocator(Locator documentLocator) {  
        super.setDocumentLocator(documentLocator);  
        if (this.documentFactory != null)  
            this.documentFactory.setLocator(documentLocator);  
    }  
}  