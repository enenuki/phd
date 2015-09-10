/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.query.spi.sql.NativeSQLQueryReturn;
/*  4:   */ import org.hibernate.type.Type;
/*  5:   */ 
/*  6:   */ public abstract interface SQLQueryResultMappingBuilder
/*  7:   */ {
/*  8:   */   public static class ScalarReturn
/*  9:   */   {
/* 10:   */     private final SQLQueryResultMappingBuilder.ReturnsHolder returnsHolder;
/* 11:   */     private String name;
/* 12:   */     private Type type;
/* 13:   */     
/* 14:   */     public ScalarReturn(SQLQueryResultMappingBuilder.ReturnsHolder returnsHolder)
/* 15:   */     {
/* 16:48 */       this.returnsHolder = returnsHolder;
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static abstract interface ReturnsHolder
/* 21:   */   {
/* 22:   */     public abstract void add(NativeSQLQueryReturn paramNativeSQLQueryReturn);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.SQLQueryResultMappingBuilder
 * JD-Core Version:    0.7.0.1
 */