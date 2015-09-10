/*  1:   */ package org.apache.http.entity;
/*  2:   */ 
/*  3:   */ import java.io.ByteArrayInputStream;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import java.io.OutputStream;
/*  7:   */ 
/*  8:   */ public class ByteArrayEntity
/*  9:   */   extends AbstractHttpEntity
/* 10:   */   implements Cloneable
/* 11:   */ {
/* 12:   */   protected final byte[] content;
/* 13:   */   
/* 14:   */   public ByteArrayEntity(byte[] b)
/* 15:   */   {
/* 16:46 */     if (b == null) {
/* 17:47 */       throw new IllegalArgumentException("Source byte array may not be null");
/* 18:   */     }
/* 19:49 */     this.content = b;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean isRepeatable()
/* 23:   */   {
/* 24:53 */     return true;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public long getContentLength()
/* 28:   */   {
/* 29:57 */     return this.content.length;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public InputStream getContent()
/* 33:   */   {
/* 34:61 */     return new ByteArrayInputStream(this.content);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void writeTo(OutputStream outstream)
/* 38:   */     throws IOException
/* 39:   */   {
/* 40:65 */     if (outstream == null) {
/* 41:66 */       throw new IllegalArgumentException("Output stream may not be null");
/* 42:   */     }
/* 43:68 */     outstream.write(this.content);
/* 44:69 */     outstream.flush();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public boolean isStreaming()
/* 48:   */   {
/* 49:79 */     return false;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object clone()
/* 53:   */     throws CloneNotSupportedException
/* 54:   */   {
/* 55:83 */     return super.clone();
/* 56:   */   }
/* 57:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.ByteArrayEntity
 * JD-Core Version:    0.7.0.1
 */