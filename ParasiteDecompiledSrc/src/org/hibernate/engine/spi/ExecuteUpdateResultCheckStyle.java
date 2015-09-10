/*  1:   */ package org.hibernate.engine.spi;
/*  2:   */ 
/*  3:   */ public enum ExecuteUpdateResultCheckStyle
/*  4:   */ {
/*  5:39 */   NONE("none"),  COUNT("rowcount"),  PARAM("param");
/*  6:   */   
/*  7:   */   private final String name;
/*  8:   */   
/*  9:   */   private ExecuteUpdateResultCheckStyle(String name)
/* 10:   */   {
/* 11:60 */     this.name = name;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static ExecuteUpdateResultCheckStyle fromExternalName(String name)
/* 15:   */   {
/* 16:64 */     if (name.equals(NONE.name)) {
/* 17:65 */       return NONE;
/* 18:   */     }
/* 19:67 */     if (name.equals(COUNT.name)) {
/* 20:68 */       return COUNT;
/* 21:   */     }
/* 22:70 */     if (name.equals(PARAM.name)) {
/* 23:71 */       return PARAM;
/* 24:   */     }
/* 25:74 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static ExecuteUpdateResultCheckStyle determineDefault(String customSql, boolean callable)
/* 29:   */   {
/* 30:79 */     if (customSql == null) {
/* 31:80 */       return COUNT;
/* 32:   */     }
/* 33:83 */     return callable ? PARAM : COUNT;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle
 * JD-Core Version:    0.7.0.1
 */