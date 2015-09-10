/*  1:   */ package org.dom4j.io;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.sax.SAXResult;
/*  4:   */ import org.dom4j.Document;
/*  5:   */ import org.xml.sax.ContentHandler;
/*  6:   */ import org.xml.sax.ext.LexicalHandler;
/*  7:   */ 
/*  8:   */ public class DocumentResult
/*  9:   */   extends SAXResult
/* 10:   */ {
/* 11:   */   private SAXContentHandler contentHandler;
/* 12:   */   
/* 13:   */   public DocumentResult()
/* 14:   */   {
/* 15:30 */     this(new SAXContentHandler());
/* 16:   */   }
/* 17:   */   
/* 18:   */   public DocumentResult(SAXContentHandler contentHandler)
/* 19:   */   {
/* 20:34 */     this.contentHandler = contentHandler;
/* 21:35 */     super.setHandler(this.contentHandler);
/* 22:36 */     super.setLexicalHandler(this.contentHandler);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Document getDocument()
/* 26:   */   {
/* 27:45 */     return this.contentHandler.getDocument();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void setHandler(ContentHandler handler)
/* 31:   */   {
/* 32:51 */     if ((handler instanceof SAXContentHandler))
/* 33:   */     {
/* 34:52 */       this.contentHandler = ((SAXContentHandler)handler);
/* 35:53 */       super.setHandler(this.contentHandler);
/* 36:   */     }
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setLexicalHandler(LexicalHandler handler)
/* 40:   */   {
/* 41:58 */     if ((handler instanceof SAXContentHandler))
/* 42:   */     {
/* 43:59 */       this.contentHandler = ((SAXContentHandler)handler);
/* 44:60 */       super.setLexicalHandler(this.contentHandler);
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.DocumentResult
 * JD-Core Version:    0.7.0.1
 */