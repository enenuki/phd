/*  1:   */ package org.apache.log4j;
/*  2:   */ 
/*  3:   */ class CategoryKey
/*  4:   */ {
/*  5:   */   String name;
/*  6:   */   int hashCache;
/*  7:   */   
/*  8:   */   CategoryKey(String name)
/*  9:   */   {
/* 10:31 */     this.name = name;
/* 11:32 */     this.hashCache = name.hashCode();
/* 12:   */   }
/* 13:   */   
/* 14:   */   public final int hashCode()
/* 15:   */   {
/* 16:38 */     return this.hashCache;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public final boolean equals(Object rArg)
/* 20:   */   {
/* 21:44 */     if (this == rArg) {
/* 22:45 */       return true;
/* 23:   */     }
/* 24:47 */     if ((rArg != null) && (CategoryKey.class == rArg.getClass())) {
/* 25:48 */       return this.name.equals(((CategoryKey)rArg).name);
/* 26:   */     }
/* 27:50 */     return false;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.CategoryKey
 * JD-Core Version:    0.7.0.1
 */