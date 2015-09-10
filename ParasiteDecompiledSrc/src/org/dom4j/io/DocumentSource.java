/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.sax.SAXSource;
/*   4:    */ import org.dom4j.Document;
/*   5:    */ import org.dom4j.Node;
/*   6:    */ import org.xml.sax.InputSource;
/*   7:    */ import org.xml.sax.XMLFilter;
/*   8:    */ import org.xml.sax.XMLReader;
/*   9:    */ 
/*  10:    */ public class DocumentSource
/*  11:    */   extends SAXSource
/*  12:    */ {
/*  13:    */   public static final String DOM4J_FEATURE = "http://org.dom4j.io.DoucmentSource/feature";
/*  14: 38 */   private XMLReader xmlReader = new SAXWriter();
/*  15:    */   
/*  16:    */   public DocumentSource(Node node)
/*  17:    */   {
/*  18: 47 */     setDocument(node.getDocument());
/*  19:    */   }
/*  20:    */   
/*  21:    */   public DocumentSource(Document document)
/*  22:    */   {
/*  23: 57 */     setDocument(document);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Document getDocument()
/*  27:    */   {
/*  28: 69 */     DocumentInputSource source = (DocumentInputSource)getInputSource();
/*  29: 70 */     return source.getDocument();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setDocument(Document document)
/*  33:    */   {
/*  34: 80 */     super.setInputSource(new DocumentInputSource(document));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public XMLReader getXMLReader()
/*  38:    */   {
/*  39: 92 */     return this.xmlReader;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setInputSource(InputSource inputSource)
/*  43:    */     throws UnsupportedOperationException
/*  44:    */   {
/*  45:107 */     if ((inputSource instanceof DocumentInputSource)) {
/*  46:108 */       super.setInputSource((DocumentInputSource)inputSource);
/*  47:    */     } else {
/*  48:110 */       throw new UnsupportedOperationException();
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setXMLReader(XMLReader reader)
/*  53:    */     throws UnsupportedOperationException
/*  54:    */   {
/*  55:125 */     if ((reader instanceof SAXWriter))
/*  56:    */     {
/*  57:126 */       this.xmlReader = ((SAXWriter)reader);
/*  58:    */     }
/*  59:127 */     else if ((reader instanceof XMLFilter))
/*  60:    */     {
/*  61:128 */       XMLFilter filter = (XMLFilter)reader;
/*  62:    */       for (;;)
/*  63:    */       {
/*  64:131 */         XMLReader parent = filter.getParent();
/*  65:133 */         if (!(parent instanceof XMLFilter)) {
/*  66:    */           break;
/*  67:    */         }
/*  68:134 */         filter = (XMLFilter)parent;
/*  69:    */       }
/*  70:141 */       filter.setParent(this.xmlReader);
/*  71:142 */       this.xmlReader = filter;
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75:144 */       throw new UnsupportedOperationException();
/*  76:    */     }
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.DocumentSource
 * JD-Core Version:    0.7.0.1
 */