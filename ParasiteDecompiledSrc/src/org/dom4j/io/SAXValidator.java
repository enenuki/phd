/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.dom4j.Document;
/*   5:    */ import org.xml.sax.ContentHandler;
/*   6:    */ import org.xml.sax.ErrorHandler;
/*   7:    */ import org.xml.sax.SAXException;
/*   8:    */ import org.xml.sax.XMLReader;
/*   9:    */ import org.xml.sax.helpers.DefaultHandler;
/*  10:    */ 
/*  11:    */ public class SAXValidator
/*  12:    */ {
/*  13:    */   private XMLReader xmlReader;
/*  14:    */   private ErrorHandler errorHandler;
/*  15:    */   
/*  16:    */   public SAXValidator() {}
/*  17:    */   
/*  18:    */   public SAXValidator(XMLReader xmlReader)
/*  19:    */   {
/*  20: 43 */     this.xmlReader = xmlReader;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void validate(Document document)
/*  24:    */     throws SAXException
/*  25:    */   {
/*  26: 59 */     if (document != null)
/*  27:    */     {
/*  28: 60 */       XMLReader reader = getXMLReader();
/*  29: 62 */       if (this.errorHandler != null) {
/*  30: 63 */         reader.setErrorHandler(this.errorHandler);
/*  31:    */       }
/*  32:    */       try
/*  33:    */       {
/*  34: 67 */         reader.parse(new DocumentInputSource(document));
/*  35:    */       }
/*  36:    */       catch (IOException e)
/*  37:    */       {
/*  38: 69 */         throw new RuntimeException("Caught and exception that should never happen: " + e);
/*  39:    */       }
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public XMLReader getXMLReader()
/*  44:    */     throws SAXException
/*  45:    */   {
/*  46: 87 */     if (this.xmlReader == null)
/*  47:    */     {
/*  48: 88 */       this.xmlReader = createXMLReader();
/*  49: 89 */       configureReader();
/*  50:    */     }
/*  51: 92 */     return this.xmlReader;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setXMLReader(XMLReader reader)
/*  55:    */     throws SAXException
/*  56:    */   {
/*  57:105 */     this.xmlReader = reader;
/*  58:106 */     configureReader();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ErrorHandler getErrorHandler()
/*  62:    */   {
/*  63:115 */     return this.errorHandler;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setErrorHandler(ErrorHandler errorHandler)
/*  67:    */   {
/*  68:126 */     this.errorHandler = errorHandler;
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected XMLReader createXMLReader()
/*  72:    */     throws SAXException
/*  73:    */   {
/*  74:142 */     return SAXHelper.createXMLReader(true);
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void configureReader()
/*  78:    */     throws SAXException
/*  79:    */   {
/*  80:152 */     ContentHandler handler = this.xmlReader.getContentHandler();
/*  81:154 */     if (handler == null) {
/*  82:155 */       this.xmlReader.setContentHandler(new DefaultHandler());
/*  83:    */     }
/*  84:159 */     this.xmlReader.setFeature("http://xml.org/sax/features/validation", true);
/*  85:    */     
/*  86:    */ 
/*  87:162 */     this.xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
/*  88:163 */     this.xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
/*  89:    */   }
/*  90:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXValidator
 * JD-Core Version:    0.7.0.1
 */