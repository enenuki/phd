/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ import org.apache.xalan.templates.DecimalFormatProperties;
/*  4:   */ import org.apache.xalan.templates.ElemTemplateElement;
/*  5:   */ import org.apache.xalan.templates.Stylesheet;
/*  6:   */ import org.xml.sax.Attributes;
/*  7:   */ import org.xml.sax.SAXException;
/*  8:   */ 
/*  9:   */ class ProcessorDecimalFormat
/* 10:   */   extends XSLTElementProcessor
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -5052904382662921627L;
/* 13:   */   
/* 14:   */   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes)
/* 15:   */     throws SAXException
/* 16:   */   {
/* 17:65 */     DecimalFormatProperties dfp = new DecimalFormatProperties(handler.nextUid());
/* 18:   */     
/* 19:67 */     dfp.setDOMBackPointer(handler.getOriginatingNode());
/* 20:68 */     dfp.setLocaterInfo(handler.getLocator());
/* 21:   */     
/* 22:70 */     setPropertiesFromAttributes(handler, rawName, attributes, dfp);
/* 23:71 */     handler.getStylesheet().setDecimalFormat(dfp);
/* 24:   */     
/* 25:73 */     handler.getStylesheet().appendChild(dfp);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.ProcessorDecimalFormat
 * JD-Core Version:    0.7.0.1
 */