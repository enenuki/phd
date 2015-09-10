/*   1:    */ package org.apache.http.entity;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import org.apache.http.Header;
/*   7:    */ import org.apache.http.HttpEntity;
/*   8:    */ 
/*   9:    */ public class HttpEntityWrapper
/*  10:    */   implements HttpEntity
/*  11:    */ {
/*  12:    */   protected HttpEntity wrappedEntity;
/*  13:    */   
/*  14:    */   public HttpEntityWrapper(HttpEntity wrapped)
/*  15:    */   {
/*  16: 60 */     if (wrapped == null) {
/*  17: 61 */       throw new IllegalArgumentException("wrapped entity must not be null");
/*  18:    */     }
/*  19: 64 */     this.wrappedEntity = wrapped;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public boolean isRepeatable()
/*  23:    */   {
/*  24: 70 */     return this.wrappedEntity.isRepeatable();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean isChunked()
/*  28:    */   {
/*  29: 74 */     return this.wrappedEntity.isChunked();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public long getContentLength()
/*  33:    */   {
/*  34: 78 */     return this.wrappedEntity.getContentLength();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Header getContentType()
/*  38:    */   {
/*  39: 82 */     return this.wrappedEntity.getContentType();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Header getContentEncoding()
/*  43:    */   {
/*  44: 86 */     return this.wrappedEntity.getContentEncoding();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public InputStream getContent()
/*  48:    */     throws IOException
/*  49:    */   {
/*  50: 91 */     return this.wrappedEntity.getContent();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void writeTo(OutputStream outstream)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56: 96 */     this.wrappedEntity.writeTo(outstream);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isStreaming()
/*  60:    */   {
/*  61:100 */     return this.wrappedEntity.isStreaming();
/*  62:    */   }
/*  63:    */   
/*  64:    */   /**
/*  65:    */    * @deprecated
/*  66:    */    */
/*  67:    */   public void consumeContent()
/*  68:    */     throws IOException
/*  69:    */   {
/*  70:108 */     this.wrappedEntity.consumeContent();
/*  71:    */   }
/*  72:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.HttpEntityWrapper
 * JD-Core Version:    0.7.0.1
 */