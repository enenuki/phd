/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public enum EntityMode
/*  4:   */ {
/*  5:32 */   POJO("pojo"),  MAP("dynamic-map");
/*  6:   */   
/*  7:   */   private final String name;
/*  8:   */   
/*  9:   */   private EntityMode(String name)
/* 10:   */   {
/* 11:38 */     this.name = name;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String toString()
/* 15:   */   {
/* 16:43 */     return this.name;
/* 17:   */   }
/* 18:   */   
/* 19:46 */   private static final String DYNAMIC_MAP_NAME = MAP.name.toUpperCase();
/* 20:   */   
/* 21:   */   public static EntityMode parse(String entityMode)
/* 22:   */   {
/* 23:57 */     if (entityMode == null) {
/* 24:58 */       return POJO;
/* 25:   */     }
/* 26:60 */     entityMode = entityMode.toUpperCase();
/* 27:61 */     if (DYNAMIC_MAP_NAME.equals(entityMode)) {
/* 28:62 */       return MAP;
/* 29:   */     }
/* 30:64 */     return valueOf(entityMode);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.EntityMode
 * JD-Core Version:    0.7.0.1
 */