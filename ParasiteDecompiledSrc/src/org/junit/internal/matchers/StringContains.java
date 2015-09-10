/*  1:   */ package org.junit.internal.matchers;
/*  2:   */ 
/*  3:   */ import org.hamcrest.Factory;
/*  4:   */ import org.hamcrest.Matcher;
/*  5:   */ 
/*  6:   */ public class StringContains
/*  7:   */   extends SubstringMatcher
/*  8:   */ {
/*  9:   */   public StringContains(String substring)
/* 10:   */   {
/* 11:13 */     super(substring);
/* 12:   */   }
/* 13:   */   
/* 14:   */   protected boolean evalSubstringOf(String s)
/* 15:   */   {
/* 16:18 */     return s.indexOf(this.substring) >= 0;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected String relationship()
/* 20:   */   {
/* 21:23 */     return "containing";
/* 22:   */   }
/* 23:   */   
/* 24:   */   @Factory
/* 25:   */   public static Matcher<String> containsString(String substring)
/* 26:   */   {
/* 27:28 */     return new StringContains(substring);
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.matchers.StringContains
 * JD-Core Version:    0.7.0.1
 */