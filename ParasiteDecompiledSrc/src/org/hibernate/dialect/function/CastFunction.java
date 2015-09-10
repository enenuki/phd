/*  1:   */ package org.hibernate.dialect.function;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.engine.spi.Mapping;
/*  7:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  8:   */ import org.hibernate.type.Type;
/*  9:   */ import org.hibernate.type.TypeResolver;
/* 10:   */ 
/* 11:   */ public class CastFunction
/* 12:   */   implements SQLFunction
/* 13:   */ {
/* 14:   */   public boolean hasArguments()
/* 15:   */   {
/* 16:39 */     return true;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean hasParenthesesIfNoArguments()
/* 20:   */   {
/* 21:43 */     return true;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Type getReturnType(Type columnType, Mapping mapping)
/* 25:   */     throws QueryException
/* 26:   */   {
/* 27:47 */     return columnType;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String render(Type columnType, List args, SessionFactoryImplementor factory)
/* 31:   */     throws QueryException
/* 32:   */   {
/* 33:51 */     if (args.size() != 2) {
/* 34:52 */       throw new QueryException("cast() requires two arguments");
/* 35:   */     }
/* 36:54 */     String type = (String)args.get(1);
/* 37:55 */     int[] sqlTypeCodes = factory.getTypeResolver().heuristicType(type).sqlTypes(factory);
/* 38:56 */     if (sqlTypeCodes.length != 1) {
/* 39:57 */       throw new QueryException("invalid Hibernate type for cast()");
/* 40:   */     }
/* 41:59 */     String sqlType = factory.getDialect().getCastTypeName(sqlTypeCodes[0]);
/* 42:60 */     if (sqlType == null) {
/* 43:62 */       sqlType = type;
/* 44:   */     }
/* 45:71 */     return "cast(" + args.get(0) + " as " + sqlType + ')';
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.function.CastFunction
 * JD-Core Version:    0.7.0.1
 */