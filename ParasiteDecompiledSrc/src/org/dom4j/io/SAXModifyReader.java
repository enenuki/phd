/*  1:   */ package org.dom4j.io;
/*  2:   */ 
/*  3:   */ import org.dom4j.DocumentFactory;
/*  4:   */ import org.xml.sax.SAXException;
/*  5:   */ import org.xml.sax.XMLReader;
/*  6:   */ 
/*  7:   */ class SAXModifyReader
/*  8:   */   extends SAXReader
/*  9:   */ {
/* 10:   */   private XMLWriter xmlWriter;
/* 11:   */   private boolean pruneElements;
/* 12:   */   
/* 13:   */   public SAXModifyReader() {}
/* 14:   */   
/* 15:   */   public SAXModifyReader(boolean validating)
/* 16:   */   {
/* 17:36 */     super(validating);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public SAXModifyReader(DocumentFactory factory)
/* 21:   */   {
/* 22:40 */     super(factory);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public SAXModifyReader(DocumentFactory factory, boolean validating)
/* 26:   */   {
/* 27:44 */     super(factory, validating);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public SAXModifyReader(XMLReader xmlReader)
/* 31:   */   {
/* 32:48 */     super(xmlReader);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public SAXModifyReader(XMLReader xmlReader, boolean validating)
/* 36:   */   {
/* 37:52 */     super(xmlReader, validating);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public SAXModifyReader(String xmlReaderClassName)
/* 41:   */     throws SAXException
/* 42:   */   {
/* 43:56 */     super(xmlReaderClassName);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public SAXModifyReader(String xmlReaderClassName, boolean validating)
/* 47:   */     throws SAXException
/* 48:   */   {
/* 49:61 */     super(xmlReaderClassName, validating);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setXMLWriter(XMLWriter writer)
/* 53:   */   {
/* 54:65 */     this.xmlWriter = writer;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean isPruneElements()
/* 58:   */   {
/* 59:69 */     return this.pruneElements;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void setPruneElements(boolean pruneElements)
/* 63:   */   {
/* 64:73 */     this.pruneElements = pruneElements;
/* 65:   */   }
/* 66:   */   
/* 67:   */   protected SAXContentHandler createContentHandler(XMLReader reader)
/* 68:   */   {
/* 69:77 */     SAXModifyContentHandler handler = new SAXModifyContentHandler(getDocumentFactory(), getDispatchHandler());
/* 70:   */     
/* 71:   */ 
/* 72:80 */     handler.setXMLWriter(this.xmlWriter);
/* 73:   */     
/* 74:82 */     return handler;
/* 75:   */   }
/* 76:   */   
/* 77:   */   protected XMLWriter getXMLWriter()
/* 78:   */   {
/* 79:86 */     return this.xmlWriter;
/* 80:   */   }
/* 81:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXModifyReader
 * JD-Core Version:    0.7.0.1
 */