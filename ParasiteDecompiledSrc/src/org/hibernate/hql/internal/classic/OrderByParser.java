/*  1:   */ package org.hibernate.hql.internal.classic;
/*  2:   */ 
/*  3:   */ import org.hibernate.QueryException;
/*  4:   */ import org.hibernate.internal.util.StringHelper;
/*  5:   */ 
/*  6:   */ public class OrderByParser
/*  7:   */   implements Parser
/*  8:   */ {
/*  9:   */   private final PathExpressionParser pathExpressionParser;
/* 10:   */   
/* 11:   */   public OrderByParser()
/* 12:   */   {
/* 13:46 */     this.pathExpressionParser = new PathExpressionParser();
/* 14:47 */     this.pathExpressionParser.setUseThetaStyleJoin(true);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void token(String token, QueryTranslatorImpl q)
/* 18:   */     throws QueryException
/* 19:   */   {
/* 20:52 */     if (q.isName(StringHelper.root(token)))
/* 21:   */     {
/* 22:53 */       ParserHelper.parse(this.pathExpressionParser, q.unalias(token), ".", q);
/* 23:54 */       q.appendOrderByToken(this.pathExpressionParser.getWhereColumn());
/* 24:55 */       this.pathExpressionParser.addAssociation(q);
/* 25:   */     }
/* 26:57 */     else if (token.startsWith(":"))
/* 27:   */     {
/* 28:58 */       q.addNamedParameter(token.substring(1));
/* 29:59 */       q.appendOrderByToken("?");
/* 30:   */     }
/* 31:   */     else
/* 32:   */     {
/* 33:62 */       q.appendOrderByToken(token);
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void start(QueryTranslatorImpl q)
/* 38:   */     throws QueryException
/* 39:   */   {}
/* 40:   */   
/* 41:   */   public void end(QueryTranslatorImpl q)
/* 42:   */     throws QueryException
/* 43:   */   {}
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.OrderByParser
 * JD-Core Version:    0.7.0.1
 */