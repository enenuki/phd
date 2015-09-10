/*  1:   */ package org.hibernate.metamodel.binding;
/*  2:   */ 
/*  3:   */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  4:   */ 
/*  5:   */ public class CustomSQL
/*  6:   */ {
/*  7:   */   private final String sql;
/*  8:   */   private final boolean isCallable;
/*  9:   */   private final ExecuteUpdateResultCheckStyle checkStyle;
/* 10:   */   
/* 11:   */   public CustomSQL(String sql, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 12:   */   {
/* 13:39 */     this.sql = sql;
/* 14:40 */     this.isCallable = callable;
/* 15:41 */     this.checkStyle = checkStyle;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getSql()
/* 19:   */   {
/* 20:45 */     return this.sql;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean isCallable()
/* 24:   */   {
/* 25:49 */     return this.isCallable;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public ExecuteUpdateResultCheckStyle getCheckStyle()
/* 29:   */   {
/* 30:53 */     return this.checkStyle;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.CustomSQL
 * JD-Core Version:    0.7.0.1
 */