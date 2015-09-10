/*  1:   */ package org.apache.http.entity;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.io.OutputStream;
/*  6:   */ 
/*  7:   */ public class EntityTemplate
/*  8:   */   extends AbstractHttpEntity
/*  9:   */ {
/* 10:   */   private final ContentProducer contentproducer;
/* 11:   */   
/* 12:   */   public EntityTemplate(ContentProducer contentproducer)
/* 13:   */   {
/* 14:46 */     if (contentproducer == null) {
/* 15:47 */       throw new IllegalArgumentException("Content producer may not be null");
/* 16:   */     }
/* 17:49 */     this.contentproducer = contentproducer;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public long getContentLength()
/* 21:   */   {
/* 22:53 */     return -1L;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public InputStream getContent()
/* 26:   */   {
/* 27:57 */     throw new UnsupportedOperationException("Entity template does not implement getContent()");
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean isRepeatable()
/* 31:   */   {
/* 32:61 */     return true;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void writeTo(OutputStream outstream)
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:65 */     if (outstream == null) {
/* 39:66 */       throw new IllegalArgumentException("Output stream may not be null");
/* 40:   */     }
/* 41:68 */     this.contentproducer.writeTo(outstream);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean isStreaming()
/* 45:   */   {
/* 46:72 */     return false;
/* 47:   */   }
/* 48:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.EntityTemplate
 * JD-Core Version:    0.7.0.1
 */