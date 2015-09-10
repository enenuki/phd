/*  1:   */ package org.hibernate.stat.internal;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class CategorizedStatistics
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private final String categoryName;
/*  9:   */   
/* 10:   */   CategorizedStatistics(String categoryName)
/* 11:   */   {
/* 12:38 */     this.categoryName = categoryName;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getCategoryName()
/* 16:   */   {
/* 17:42 */     return this.categoryName;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.internal.CategorizedStatistics
 * JD-Core Version:    0.7.0.1
 */