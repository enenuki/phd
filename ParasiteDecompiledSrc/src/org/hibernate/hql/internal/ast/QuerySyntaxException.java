/*  1:   */ package org.hibernate.hql.internal.ast;
/*  2:   */ 
/*  3:   */ import antlr.RecognitionException;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ 
/*  6:   */ public class QuerySyntaxException
/*  7:   */   extends QueryException
/*  8:   */ {
/*  9:   */   public QuerySyntaxException(String message)
/* 10:   */   {
/* 11:38 */     super(message);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public QuerySyntaxException(String message, String hql)
/* 15:   */   {
/* 16:42 */     this(message);
/* 17:43 */     setQueryString(hql);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static QuerySyntaxException convert(RecognitionException e)
/* 21:   */   {
/* 22:47 */     return convert(e, null);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static QuerySyntaxException convert(RecognitionException e, String hql)
/* 26:   */   {
/* 27:51 */     String positionInfo = (e.getLine() > 0) && (e.getColumn() > 0) ? " near line " + e.getLine() + ", column " + e.getColumn() : "";
/* 28:   */     
/* 29:   */ 
/* 30:54 */     return new QuerySyntaxException(e.getMessage() + positionInfo, hql);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.QuerySyntaxException
 * JD-Core Version:    0.7.0.1
 */