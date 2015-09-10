/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.StringReader;
/*   6:    */ import java.io.StringWriter;
/*   7:    */ import org.dom4j.Document;
/*   8:    */ import org.xml.sax.InputSource;
/*   9:    */ 
/*  10:    */ class DocumentInputSource
/*  11:    */   extends InputSource
/*  12:    */ {
/*  13:    */   private Document document;
/*  14:    */   
/*  15:    */   public DocumentInputSource() {}
/*  16:    */   
/*  17:    */   public DocumentInputSource(Document document)
/*  18:    */   {
/*  19: 36 */     this.document = document;
/*  20: 37 */     setSystemId(document.getName());
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Document getDocument()
/*  24:    */   {
/*  25: 49 */     return this.document;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setDocument(Document document)
/*  29:    */   {
/*  30: 59 */     this.document = document;
/*  31: 60 */     setSystemId(document.getName());
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setCharacterStream(Reader characterStream)
/*  35:    */     throws UnsupportedOperationException
/*  36:    */   {
/*  37: 78 */     throw new UnsupportedOperationException();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Reader getCharacterStream()
/*  41:    */   {
/*  42:    */     try
/*  43:    */     {
/*  44: 90 */       StringWriter out = new StringWriter();
/*  45: 91 */       XMLWriter writer = new XMLWriter(out);
/*  46: 92 */       writer.write(this.document);
/*  47: 93 */       writer.flush();
/*  48:    */       
/*  49: 95 */       return new StringReader(out.toString());
/*  50:    */     }
/*  51:    */     catch (IOException e)
/*  52:    */     {
/*  53:100 */       new Reader()
/*  54:    */       {
/*  55:    */         private final IOException val$e;
/*  56:    */         
/*  57:    */         public int read(char[] ch, int offset, int length)
/*  58:    */           throws IOException
/*  59:    */         {
/*  60:103 */           throw this.val$e;
/*  61:    */         }
/*  62:    */         
/*  63:    */         public void close()
/*  64:    */           throws IOException
/*  65:    */         {}
/*  66:    */       };
/*  67:    */     }
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.DocumentInputSource
 * JD-Core Version:    0.7.0.1
 */