/*   1:    */ package org.apache.http.entity;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import org.apache.http.Header;
/*   5:    */ import org.apache.http.HttpEntity;
/*   6:    */ import org.apache.http.message.BasicHeader;
/*   7:    */ 
/*   8:    */ public abstract class AbstractHttpEntity
/*   9:    */   implements HttpEntity
/*  10:    */ {
/*  11:    */   protected Header contentType;
/*  12:    */   protected Header contentEncoding;
/*  13:    */   protected boolean chunked;
/*  14:    */   
/*  15:    */   public Header getContentType()
/*  16:    */   {
/*  17: 69 */     return this.contentType;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Header getContentEncoding()
/*  21:    */   {
/*  22: 81 */     return this.contentEncoding;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean isChunked()
/*  26:    */   {
/*  27: 92 */     return this.chunked;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setContentType(Header contentType)
/*  31:    */   {
/*  32:105 */     this.contentType = contentType;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setContentType(String ctString)
/*  36:    */   {
/*  37:117 */     Header h = null;
/*  38:118 */     if (ctString != null) {
/*  39:119 */       h = new BasicHeader("Content-Type", ctString);
/*  40:    */     }
/*  41:121 */     setContentType(h);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setContentEncoding(Header contentEncoding)
/*  45:    */   {
/*  46:134 */     this.contentEncoding = contentEncoding;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setContentEncoding(String ceString)
/*  50:    */   {
/*  51:146 */     Header h = null;
/*  52:147 */     if (ceString != null) {
/*  53:148 */       h = new BasicHeader("Content-Encoding", ceString);
/*  54:    */     }
/*  55:150 */     setContentEncoding(h);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setChunked(boolean b)
/*  59:    */   {
/*  60:169 */     this.chunked = b;
/*  61:    */   }
/*  62:    */   
/*  63:    */   /**
/*  64:    */    * @deprecated
/*  65:    */    */
/*  66:    */   public void consumeContent()
/*  67:    */     throws IOException
/*  68:    */   {}
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.AbstractHttpEntity
 * JD-Core Version:    0.7.0.1
 */