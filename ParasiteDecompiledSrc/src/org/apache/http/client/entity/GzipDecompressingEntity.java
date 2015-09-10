/*  1:   */ package org.apache.http.client.entity;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.InputStream;
/*  5:   */ import java.util.zip.GZIPInputStream;
/*  6:   */ import org.apache.http.Header;
/*  7:   */ import org.apache.http.HttpEntity;
/*  8:   */ 
/*  9:   */ public class GzipDecompressingEntity
/* 10:   */   extends DecompressingEntity
/* 11:   */ {
/* 12:   */   public GzipDecompressingEntity(HttpEntity entity)
/* 13:   */   {
/* 14:51 */     super(entity);
/* 15:   */   }
/* 16:   */   
/* 17:   */   InputStream getDecompressingInputStream(InputStream wrapped)
/* 18:   */     throws IOException
/* 19:   */   {
/* 20:56 */     return new GZIPInputStream(wrapped);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Header getContentEncoding()
/* 24:   */   {
/* 25:66 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public long getContentLength()
/* 29:   */   {
/* 30:76 */     return -1L;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.entity.GzipDecompressingEntity
 * JD-Core Version:    0.7.0.1
 */