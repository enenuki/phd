/*  1:   */ package org.apache.http.entity.mime;
/*  2:   */ 
/*  3:   */ public class MinimalField
/*  4:   */ {
/*  5:   */   private final String name;
/*  6:   */   private final String value;
/*  7:   */   
/*  8:   */   MinimalField(String name, String value)
/*  9:   */   {
/* 10:42 */     this.name = name;
/* 11:43 */     this.value = value;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getName()
/* 15:   */   {
/* 16:47 */     return this.name;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getBody()
/* 20:   */   {
/* 21:51 */     return this.value;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String toString()
/* 25:   */   {
/* 26:56 */     StringBuilder buffer = new StringBuilder();
/* 27:57 */     buffer.append(this.name);
/* 28:58 */     buffer.append(": ");
/* 29:59 */     buffer.append(this.value);
/* 30:60 */     return buffer.toString();
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.entity.mime.MinimalField
 * JD-Core Version:    0.7.0.1
 */