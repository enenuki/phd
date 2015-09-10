/*  1:   */ package org.hibernate.annotations;
/*  2:   */ 
/*  3:   */ public enum SourceType
/*  4:   */ {
/*  5:37 */   VM("timestamp"),  DB("dbtimestamp");
/*  6:   */   
/*  7:   */   private final String typeName;
/*  8:   */   
/*  9:   */   private SourceType(String typeName)
/* 10:   */   {
/* 11:47 */     this.typeName = typeName;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String typeName()
/* 15:   */   {
/* 16:51 */     return this.typeName;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.SourceType
 * JD-Core Version:    0.7.0.1
 */