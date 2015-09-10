/*  1:   */ package org.apache.http.entity.mime.content;
/*  2:   */ 
/*  3:   */ public abstract class AbstractContentBody
/*  4:   */   implements ContentBody
/*  5:   */ {
/*  6:   */   private final String mimeType;
/*  7:   */   private final String mediaType;
/*  8:   */   private final String subType;
/*  9:   */   
/* 10:   */   public AbstractContentBody(String mimeType)
/* 11:   */   {
/* 12:42 */     if (mimeType == null) {
/* 13:43 */       throw new IllegalArgumentException("MIME type may not be null");
/* 14:   */     }
/* 15:45 */     this.mimeType = mimeType;
/* 16:46 */     int i = mimeType.indexOf('/');
/* 17:47 */     if (i != -1)
/* 18:   */     {
/* 19:48 */       this.mediaType = mimeType.substring(0, i);
/* 20:49 */       this.subType = mimeType.substring(i + 1);
/* 21:   */     }
/* 22:   */     else
/* 23:   */     {
/* 24:51 */       this.mediaType = mimeType;
/* 25:52 */       this.subType = null;
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String getMimeType()
/* 30:   */   {
/* 31:57 */     return this.mimeType;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String getMediaType()
/* 35:   */   {
/* 36:61 */     return this.mediaType;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String getSubType()
/* 40:   */   {
/* 41:65 */     return this.subType;
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.content.AbstractContentBody
 * JD-Core Version:    0.7.0.1
 */