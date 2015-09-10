/*   1:    */ package org.apache.http.entity;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.OutputStream;
/*   7:    */ import org.apache.http.HttpEntity;
/*   8:    */ import org.apache.http.util.EntityUtils;
/*   9:    */ 
/*  10:    */ public class BufferedHttpEntity
/*  11:    */   extends HttpEntityWrapper
/*  12:    */ {
/*  13:    */   private final byte[] buffer;
/*  14:    */   
/*  15:    */   public BufferedHttpEntity(HttpEntity entity)
/*  16:    */     throws IOException
/*  17:    */   {
/*  18: 58 */     super(entity);
/*  19: 59 */     if ((!entity.isRepeatable()) || (entity.getContentLength() < 0L)) {
/*  20: 60 */       this.buffer = EntityUtils.toByteArray(entity);
/*  21:    */     } else {
/*  22: 62 */       this.buffer = null;
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public long getContentLength()
/*  27:    */   {
/*  28: 67 */     if (this.buffer != null) {
/*  29: 68 */       return this.buffer.length;
/*  30:    */     }
/*  31: 70 */     return this.wrappedEntity.getContentLength();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public InputStream getContent()
/*  35:    */     throws IOException
/*  36:    */   {
/*  37: 75 */     if (this.buffer != null) {
/*  38: 76 */       return new ByteArrayInputStream(this.buffer);
/*  39:    */     }
/*  40: 78 */     return this.wrappedEntity.getContent();
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isChunked()
/*  44:    */   {
/*  45: 88 */     return (this.buffer == null) && (this.wrappedEntity.isChunked());
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean isRepeatable()
/*  49:    */   {
/*  50: 97 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void writeTo(OutputStream outstream)
/*  54:    */     throws IOException
/*  55:    */   {
/*  56:102 */     if (outstream == null) {
/*  57:103 */       throw new IllegalArgumentException("Output stream may not be null");
/*  58:    */     }
/*  59:105 */     if (this.buffer != null) {
/*  60:106 */       outstream.write(this.buffer);
/*  61:    */     } else {
/*  62:108 */       this.wrappedEntity.writeTo(outstream);
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean isStreaming()
/*  67:    */   {
/*  68:115 */     return (this.buffer == null) && (this.wrappedEntity.isStreaming());
/*  69:    */   }
/*  70:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.BufferedHttpEntity
 * JD-Core Version:    0.7.0.1
 */