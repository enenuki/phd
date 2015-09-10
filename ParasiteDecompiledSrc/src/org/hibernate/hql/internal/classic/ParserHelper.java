/*  1:   */ package org.hibernate.hql.internal.classic;
/*  2:   */ 
/*  3:   */ import java.util.StringTokenizer;
/*  4:   */ import org.hibernate.QueryException;
/*  5:   */ 
/*  6:   */ public final class ParserHelper
/*  7:   */ {
/*  8:   */   public static final String HQL_VARIABLE_PREFIX = ":";
/*  9:   */   public static final String HQL_SEPARATORS = " \n\r\f\t,()=<>&|+-=/*'^![]#~\\";
/* 10:   */   public static final String PATH_SEPARATORS = ".";
/* 11:   */   
/* 12:   */   public static boolean isWhitespace(String str)
/* 13:   */   {
/* 14:40 */     return " \n\r\f\t".indexOf(str) > -1;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static void parse(Parser p, String text, String seperators, QueryTranslatorImpl q)
/* 18:   */     throws QueryException
/* 19:   */   {
/* 20:48 */     StringTokenizer tokens = new StringTokenizer(text, seperators, true);
/* 21:49 */     p.start(q);
/* 22:50 */     while (tokens.hasMoreElements()) {
/* 23:50 */       p.token(tokens.nextToken(), q);
/* 24:   */     }
/* 25:51 */     p.end(q);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.classic.ParserHelper
 * JD-Core Version:    0.7.0.1
 */