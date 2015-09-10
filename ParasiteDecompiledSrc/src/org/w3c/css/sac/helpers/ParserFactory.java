/*  1:   */ package org.w3c.css.sac.helpers;
/*  2:   */ 
/*  3:   */ import org.w3c.css.sac.Parser;
/*  4:   */ 
/*  5:   */ public class ParserFactory
/*  6:   */ {
/*  7:   */   public Parser makeParser()
/*  8:   */     throws ClassNotFoundException, IllegalAccessException, InstantiationException, NullPointerException, ClassCastException
/*  9:   */   {
/* 10:33 */     String str = System.getProperty("org.w3c.css.sac.parser");
/* 11:34 */     if (str == null) {
/* 12:35 */       throw new NullPointerException("No value for sac.parser property");
/* 13:   */     }
/* 14:37 */     return (Parser)Class.forName(str).newInstance();
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.helpers.ParserFactory
 * JD-Core Version:    0.7.0.1
 */