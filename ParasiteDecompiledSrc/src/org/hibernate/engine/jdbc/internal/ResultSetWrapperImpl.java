/*  1:   */ package org.hibernate.engine.jdbc.internal;
/*  2:   */ 
/*  3:   */ import java.sql.ResultSet;
/*  4:   */ import org.hibernate.engine.jdbc.ColumnNameCache;
/*  5:   */ import org.hibernate.engine.jdbc.ResultSetWrapperProxy;
/*  6:   */ import org.hibernate.engine.jdbc.spi.ResultSetWrapper;
/*  7:   */ 
/*  8:   */ public class ResultSetWrapperImpl
/*  9:   */   implements ResultSetWrapper
/* 10:   */ {
/* 11:38 */   public static ResultSetWrapper INSTANCE = new ResultSetWrapperImpl();
/* 12:   */   
/* 13:   */   public ResultSet wrap(ResultSet resultSet, ColumnNameCache columnNameCache)
/* 14:   */   {
/* 15:47 */     return ResultSetWrapperProxy.generateProxy(resultSet, columnNameCache);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.ResultSetWrapperImpl
 * JD-Core Version:    0.7.0.1
 */