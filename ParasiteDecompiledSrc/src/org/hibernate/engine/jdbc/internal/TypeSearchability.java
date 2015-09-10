/*  1:   */ package org.hibernate.engine.jdbc.internal;
/*  2:   */ 
/*  3:   */ public enum TypeSearchability
/*  4:   */ {
/*  5:38 */   NONE,  FULL,  CHAR,  BASIC;
/*  6:   */   
/*  7:   */   private TypeSearchability() {}
/*  8:   */   
/*  9:   */   public static TypeSearchability interpret(short code)
/* 10:   */   {
/* 11:64 */     switch (code)
/* 12:   */     {
/* 13:   */     case 3: 
/* 14:66 */       return FULL;
/* 15:   */     case 0: 
/* 16:69 */       return NONE;
/* 17:   */     case 2: 
/* 18:72 */       return BASIC;
/* 19:   */     case 1: 
/* 20:75 */       return CHAR;
/* 21:   */     }
/* 22:78 */     throw new IllegalArgumentException("Unknown type searchability code [" + code + "] enountered");
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.TypeSearchability
 * JD-Core Version:    0.7.0.1
 */