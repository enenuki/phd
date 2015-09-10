/*  1:   */ package org.dom4j.io;
/*  2:   */ 
/*  3:   */ import javax.xml.parsers.DocumentBuilder;
/*  4:   */ import javax.xml.parsers.DocumentBuilderFactory;
/*  5:   */ import javax.xml.parsers.SAXParser;
/*  6:   */ import javax.xml.parsers.SAXParserFactory;
/*  7:   */ import org.w3c.dom.Document;
/*  8:   */ import org.xml.sax.XMLReader;
/*  9:   */ 
/* 10:   */ class JAXPHelper
/* 11:   */ {
/* 12:   */   public static XMLReader createXMLReader(boolean validating, boolean namespaceAware)
/* 13:   */     throws Exception
/* 14:   */   {
/* 15:46 */     SAXParserFactory factory = SAXParserFactory.newInstance();
/* 16:47 */     factory.setValidating(validating);
/* 17:48 */     factory.setNamespaceAware(namespaceAware);
/* 18:   */     
/* 19:50 */     SAXParser parser = factory.newSAXParser();
/* 20:   */     
/* 21:52 */     return parser.getXMLReader();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static Document createDocument(boolean validating, boolean namespaceAware)
/* 25:   */     throws Exception
/* 26:   */   {
/* 27:57 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 28:58 */     factory.setValidating(validating);
/* 29:59 */     factory.setNamespaceAware(namespaceAware);
/* 30:   */     
/* 31:61 */     DocumentBuilder builder = factory.newDocumentBuilder();
/* 32:   */     
/* 33:63 */     return builder.newDocument();
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.JAXPHelper
 * JD-Core Version:    0.7.0.1
 */