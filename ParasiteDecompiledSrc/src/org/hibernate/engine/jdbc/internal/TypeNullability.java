/*  1:   */ package org.hibernate.engine.jdbc.internal;
/*  2:   */ 
/*  3:   */ public enum TypeNullability
/*  4:   */ {
/*  5:38 */   NULLABLE,  NON_NULLABLE,  UNKNOWN;
/*  6:   */   
/*  7:   */   private TypeNullability() {}
/*  8:   */   
/*  9:   */   public static TypeNullability interpret(short code)
/* 10:   */   {
/* 11:59 */     switch (code)
/* 12:   */     {
/* 13:   */     case 1: 
/* 14:61 */       return NULLABLE;
/* 15:   */     case 0: 
/* 16:64 */       return NON_NULLABLE;
/* 17:   */     case 2: 
/* 18:67 */       return UNKNOWN;
/* 19:   */     }
/* 20:70 */     throw new IllegalArgumentException("Unknown type nullability code [" + code + "] enountered");
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.TypeNullability
 * JD-Core Version:    0.7.0.1
 */