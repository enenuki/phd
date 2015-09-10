/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.engine.spi.Mapping;
/*  6:   */ import org.hibernate.type.StandardBasicTypes;
/*  7:   */ import org.hibernate.type.Type;
/*  8:   */ 
/*  9:   */ public class ClassicAvgFunction
/* 10:   */   extends StandardSQLFunction
/* 11:   */ {
/* 12:   */   public ClassicAvgFunction()
/* 13:   */   {
/* 14:41 */     super("avg");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public Type getReturnType(Type columnType, Mapping mapping)
/* 18:   */     throws QueryException
/* 19:   */   {
/* 20:   */     int[] sqlTypes;
/* 21:   */     try
/* 22:   */     {
/* 23:47 */       sqlTypes = columnType.sqlTypes(mapping);
/* 24:   */     }
/* 25:   */     catch (MappingException me)
/* 26:   */     {
/* 27:50 */       throw new QueryException(me);
/* 28:   */     }
/* 29:52 */     if (sqlTypes.length != 1) {
/* 30:52 */       throw new QueryException("multi-column type in avg()");
/* 31:   */     }
/* 32:53 */     int sqlType = sqlTypes[0];
/* 33:54 */     if ((sqlType == 4) || (sqlType == -5) || (sqlType == -6)) {
/* 34:55 */       return StandardBasicTypes.FLOAT;
/* 35:   */     }
/* 36:58 */     return columnType;
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.ClassicAvgFunction
 * JD-Core Version:    0.7.0.1
 */