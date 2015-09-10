/*  1:   */ package org.apache.http.client.entity;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ import org.apache.http.HttpEntity;
/*  7:   */ import org.apache.http.entity.HttpEntityWrapper;
/*  8:   */ 
/*  9:   */ abstract class DecompressingEntity
/* 10:   */   extends HttpEntityWrapper
/* 11:   */ {
/* 12:   */   private static final int BUFFER_SIZE = 2048;
/* 13:   */   private InputStream content;
/* 14:   */   
/* 15:   */   public DecompressingEntity(HttpEntity wrapped)
/* 16:   */   {
/* 17:60 */     super(wrapped);
/* 18:   */   }
/* 19:   */   
/* 20:   */   abstract InputStream getDecompressingInputStream(InputStream paramInputStream)
/* 21:   */     throws IOException;
/* 22:   */   
/* 23:   */   public InputStream getContent()
/* 24:   */     throws IOException
/* 25:   */   {
/* 26:70 */     if (this.wrappedEntity.isStreaming())
/* 27:   */     {
/* 28:71 */       if (this.content == null) {
/* 29:72 */         this.content = getDecompressingInputStream(this.wrappedEntity.getContent());
/* 30:   */       }
/* 31:74 */       return this.content;
/* 32:   */     }
/* 33:76 */     return getDecompressingInputStream(this.wrappedEntity.getContent());
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void writeTo(OutputStream outstream)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:85 */     if (outstream == null) {
/* 40:86 */       throw new IllegalArgumentException("Output stream may not be null");
/* 41:   */     }
/* 42:88 */     InputStream instream = getContent();
/* 43:   */     try
/* 44:   */     {
/* 45:90 */       byte[] buffer = new byte[2048];
/* 46:   */       int l;
/* 47:94 */       while ((l = instream.read(buffer)) != -1) {
/* 48:95 */         outstream.write(buffer, 0, l);
/* 49:   */       }
/* 50:   */     }
/* 51:   */     finally
/* 52:   */     {
/* 53:98 */       instream.close();
/* 54:   */     }
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.entity.DecompressingEntity
 * JD-Core Version:    0.7.0.1
 */