/*  1:   */ package org.junit.experimental.results;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.Description;
/*  5:   */ import org.hamcrest.Matcher;
/*  6:   */ import org.junit.internal.matchers.TypeSafeMatcher;
/*  7:   */ 
/*  8:   */ public class ResultMatchers
/*  9:   */ {
/* 10:   */   public static Matcher<PrintableResult> isSuccessful()
/* 11:   */   {
/* 12:21 */     return failureCountIs(0);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static Matcher<PrintableResult> failureCountIs(int count)
/* 16:   */   {
/* 17:28 */     new TypeSafeMatcher()
/* 18:   */     {
/* 19:   */       public void describeTo(Description description)
/* 20:   */       {
/* 21:30 */         description.appendText("has " + this.val$count + " failures");
/* 22:   */       }
/* 23:   */       
/* 24:   */       public boolean matchesSafely(PrintableResult item)
/* 25:   */       {
/* 26:35 */         return item.failureCount() == this.val$count;
/* 27:   */       }
/* 28:   */     };
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static Matcher<Object> hasSingleFailureContaining(String string)
/* 32:   */   {
/* 33:44 */     new BaseMatcher()
/* 34:   */     {
/* 35:   */       public boolean matches(Object item)
/* 36:   */       {
/* 37:46 */         return (item.toString().contains(this.val$string)) && (ResultMatchers.failureCountIs(1).matches(item));
/* 38:   */       }
/* 39:   */       
/* 40:   */       public void describeTo(Description description)
/* 41:   */       {
/* 42:50 */         description.appendText("has single failure containing " + this.val$string);
/* 43:   */       }
/* 44:   */     };
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static Matcher<PrintableResult> hasFailureContaining(String string)
/* 48:   */   {
/* 49:60 */     new BaseMatcher()
/* 50:   */     {
/* 51:   */       public boolean matches(Object item)
/* 52:   */       {
/* 53:62 */         return item.toString().contains(this.val$string);
/* 54:   */       }
/* 55:   */       
/* 56:   */       public void describeTo(Description description)
/* 57:   */       {
/* 58:66 */         description.appendText("has failure containing " + this.val$string);
/* 59:   */       }
/* 60:   */     };
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.results.ResultMatchers
 * JD-Core Version:    0.7.0.1
 */