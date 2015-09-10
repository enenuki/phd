/*  1:   */ package org.hibernate.loader.custom;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.Type;
/*  4:   */ 
/*  5:   */ public class ScalarReturn
/*  6:   */   implements Return
/*  7:   */ {
/*  8:   */   private final Type type;
/*  9:   */   private final String columnAlias;
/* 10:   */   
/* 11:   */   public ScalarReturn(Type type, String columnAlias)
/* 12:   */   {
/* 13:38 */     this.type = type;
/* 14:39 */     this.columnAlias = columnAlias;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Type getType()
/* 18:   */   {
/* 19:43 */     return this.type;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getColumnAlias()
/* 23:   */   {
/* 24:47 */     return this.columnAlias;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.custom.ScalarReturn
 * JD-Core Version:    0.7.0.1
 */