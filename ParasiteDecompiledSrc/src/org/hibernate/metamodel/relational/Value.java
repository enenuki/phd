/*  1:   */ package org.hibernate.metamodel.relational;
/*  2:   */ 
/*  3:   */ public abstract interface Value
/*  4:   */ {
/*  5:   */   public abstract TableSpecification getTable();
/*  6:   */   
/*  7:   */   public abstract String toLoggableString();
/*  8:   */   
/*  9:   */   public abstract void validateJdbcTypes(JdbcCodes paramJdbcCodes);
/* 10:   */   
/* 11:   */   public static class JdbcCodes
/* 12:   */   {
/* 13:   */     private final int[] typeCodes;
/* 14:54 */     private int index = 0;
/* 15:   */     
/* 16:   */     public JdbcCodes(int[] typeCodes)
/* 17:   */     {
/* 18:57 */       this.typeCodes = typeCodes;
/* 19:   */     }
/* 20:   */     
/* 21:   */     public int nextJdbcCde()
/* 22:   */     {
/* 23:61 */       return this.typeCodes[(this.index++)];
/* 24:   */     }
/* 25:   */     
/* 26:   */     public int getIndex()
/* 27:   */     {
/* 28:65 */       return this.index;
/* 29:   */     }
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Value
 * JD-Core Version:    0.7.0.1
 */