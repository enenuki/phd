/*  1:   */ package org.hibernate.hql.internal.classic;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ import org.hibernate.internal.util.StringHelper;
/*  5:   */ 
/*  6:   */ public class GroupByParser
/*  7:   */   implements Parser
/*  8:   */ {
/*  9:   */   private final PathExpressionParser pathExpressionParser;
/* 10:   */   
/* 11:   */   public GroupByParser()
/* 12:   */   {
/* 13:47 */     this.pathExpressionParser = new PathExpressionParser();
/* 14:48 */     this.pathExpressionParser.setUseThetaStyleJoin(true);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void token(String token, QueryTranslatorImpl q)
/* 18:   */     throws QueryException
/* 19:   */   {
/* 20:53 */     if (q.isName(StringHelper.root(token)))
/* 21:   */     {
/* 22:54 */       ParserHelper.parse(this.pathExpressionParser, q.unalias(token), ".", q);
/* 23:55 */       q.appendGroupByToken(this.pathExpressionParser.getWhereColumn());
/* 24:56 */       this.pathExpressionParser.addAssociation(q);
/* 25:   */     }
/* 26:   */     else
/* 27:   */     {
/* 28:59 */       q.appendGroupByToken(token);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void start(QueryTranslatorImpl q)
/* 33:   */     throws QueryException
/* 34:   */   {}
/* 35:   */   
/* 36:   */   public void end(QueryTranslatorImpl q)
/* 37:   */     throws QueryException
/* 38:   */   {}
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.GroupByParser
 * JD-Core Version:    0.7.0.1
 */