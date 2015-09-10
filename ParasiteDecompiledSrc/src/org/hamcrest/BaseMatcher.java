/*  1:   */ package org.hamcrest;
/*  2:   */ 
/*  3:   */ public abstract class BaseMatcher<T>
/*  4:   */   implements Matcher<T>
/*  5:   */ {
/*  6:   */   public final void _dont_implement_Matcher___instead_extend_BaseMatcher_() {}
/*  7:   */   
/*  8:   */   public String toString()
/*  9:   */   {
/* 10:21 */     return StringDescription.toString(this);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.BaseMatcher
 * JD-Core Version:    0.7.0.1
 */