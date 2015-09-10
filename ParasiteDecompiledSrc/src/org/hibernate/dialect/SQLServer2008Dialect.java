/*  1:   */ package org.hibernate.dialect;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*  4:   */ import org.hibernate.type.StandardBasicTypes;
/*  5:   */ 
/*  6:   */ public class SQLServer2008Dialect
/*  7:   */   extends SQLServer2005Dialect
/*  8:   */ {
/*  9:   */   public SQLServer2008Dialect()
/* 10:   */   {
/* 11:37 */     registerColumnType(91, "date");
/* 12:38 */     registerColumnType(92, "time");
/* 13:39 */     registerColumnType(93, "datetime2");
/* 14:   */     
/* 15:41 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.SQLServer2008Dialect
 * JD-Core Version:    0.7.0.1
 */