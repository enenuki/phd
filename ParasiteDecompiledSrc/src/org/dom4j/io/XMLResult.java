/*  1:   */ package org.dom4j.io;
/*  2:   */ 
/*  3:   */ import java.io.OutputStream;
/*  4:   */ import java.io.UnsupportedEncodingException;
/*  5:   */ import java.io.Writer;
/*  6:   */ import javax.xml.transform.sax.SAXResult;
/*  7:   */ import org.xml.sax.ContentHandler;
/*  8:   */ import org.xml.sax.ext.LexicalHandler;
/*  9:   */ 
/* 10:   */ public class XMLResult
/* 11:   */   extends SAXResult
/* 12:   */ {
/* 13:   */   private XMLWriter xmlWriter;
/* 14:   */   
/* 15:   */   public XMLResult()
/* 16:   */   {
/* 17:33 */     this(new XMLWriter());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public XMLResult(Writer writer)
/* 21:   */   {
/* 22:37 */     this(new XMLWriter(writer));
/* 23:   */   }
/* 24:   */   
/* 25:   */   public XMLResult(Writer writer, OutputFormat format)
/* 26:   */   {
/* 27:41 */     this(new XMLWriter(writer, format));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public XMLResult(OutputStream out)
/* 31:   */     throws UnsupportedEncodingException
/* 32:   */   {
/* 33:45 */     this(new XMLWriter(out));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public XMLResult(OutputStream out, OutputFormat format)
/* 37:   */     throws UnsupportedEncodingException
/* 38:   */   {
/* 39:50 */     this(new XMLWriter(out, format));
/* 40:   */   }
/* 41:   */   
/* 42:   */   public XMLResult(XMLWriter xmlWriter)
/* 43:   */   {
/* 44:54 */     super(xmlWriter);
/* 45:55 */     this.xmlWriter = xmlWriter;
/* 46:56 */     setLexicalHandler(xmlWriter);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public XMLWriter getXMLWriter()
/* 50:   */   {
/* 51:60 */     return this.xmlWriter;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void setXMLWriter(XMLWriter writer)
/* 55:   */   {
/* 56:64 */     this.xmlWriter = writer;
/* 57:65 */     setHandler(this.xmlWriter);
/* 58:66 */     setLexicalHandler(this.xmlWriter);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public ContentHandler getHandler()
/* 62:   */   {
/* 63:70 */     return this.xmlWriter;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public LexicalHandler getLexicalHandler()
/* 67:   */   {
/* 68:74 */     return this.xmlWriter;
/* 69:   */   }
/* 70:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.XMLResult
 * JD-Core Version:    0.7.0.1
 */