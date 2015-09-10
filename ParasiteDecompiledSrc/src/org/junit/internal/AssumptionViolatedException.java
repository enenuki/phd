/*  1:   */ package org.junit.internal;
/*  2:   */ 
/*  3:   */ import org.hamcrest.Description;
/*  4:   */ import org.hamcrest.Matcher;
/*  5:   */ import org.hamcrest.SelfDescribing;
/*  6:   */ import org.hamcrest.StringDescription;
/*  7:   */ 
/*  8:   */ public class AssumptionViolatedException
/*  9:   */   extends RuntimeException
/* 10:   */   implements SelfDescribing
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 1L;
/* 13:   */   private final Object fValue;
/* 14:   */   private final Matcher<?> fMatcher;
/* 15:   */   
/* 16:   */   public AssumptionViolatedException(Object value, Matcher<?> matcher)
/* 17:   */   {
/* 18:16 */     super((value instanceof Throwable) ? (Throwable)value : null);
/* 19:17 */     this.fValue = value;
/* 20:18 */     this.fMatcher = matcher;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public AssumptionViolatedException(String assumption)
/* 24:   */   {
/* 25:22 */     this(assumption, null);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getMessage()
/* 29:   */   {
/* 30:27 */     return StringDescription.asString(this);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void describeTo(Description description)
/* 34:   */   {
/* 35:31 */     if (this.fMatcher != null)
/* 36:   */     {
/* 37:32 */       description.appendText("got: ");
/* 38:33 */       description.appendValue(this.fValue);
/* 39:34 */       description.appendText(", expected: ");
/* 40:35 */       description.appendDescriptionOf(this.fMatcher);
/* 41:   */     }
/* 42:   */     else
/* 43:   */     {
/* 44:37 */       description.appendText("failed assumption: " + this.fValue);
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.AssumptionViolatedException
 * JD-Core Version:    0.7.0.1
 */