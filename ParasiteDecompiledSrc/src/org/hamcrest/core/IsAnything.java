/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.Description;
/*  5:   */ import org.hamcrest.Factory;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ 
/*  8:   */ public class IsAnything<T>
/*  9:   */   extends BaseMatcher<T>
/* 10:   */ {
/* 11:   */   private final String description;
/* 12:   */   
/* 13:   */   public IsAnything()
/* 14:   */   {
/* 15:19 */     this("ANYTHING");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public IsAnything(String description)
/* 19:   */   {
/* 20:23 */     this.description = description;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean matches(Object o)
/* 24:   */   {
/* 25:27 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void describeTo(Description description)
/* 29:   */   {
/* 30:31 */     description.appendText(this.description);
/* 31:   */   }
/* 32:   */   
/* 33:   */   @Factory
/* 34:   */   public static <T> Matcher<T> anything()
/* 35:   */   {
/* 36:39 */     return new IsAnything();
/* 37:   */   }
/* 38:   */   
/* 39:   */   @Factory
/* 40:   */   public static <T> Matcher<T> anything(String description)
/* 41:   */   {
/* 42:49 */     return new IsAnything(description);
/* 43:   */   }
/* 44:   */   
/* 45:   */   @Factory
/* 46:   */   public static <T> Matcher<T> any(Class<T> type)
/* 47:   */   {
/* 48:57 */     return new IsAnything();
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.IsAnything
 * JD-Core Version:    0.7.0.1
 */