/*  1:   */ package org.hibernate.hql.internal.ast;
/*  2:   */ 
/*  3:   */ public class InvalidWithClauseException
/*  4:   */   extends QuerySyntaxException
/*  5:   */ {
/*  6:   */   public InvalidWithClauseException(String message)
/*  7:   */   {
/*  8:35 */     super(message);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public InvalidWithClauseException(String message, String queryString)
/* 12:   */   {
/* 13:39 */     super(message, queryString);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.InvalidWithClauseException
 * JD-Core Version:    0.7.0.1
 */