/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.Description;
/*  5:   */ import org.hamcrest.Factory;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ 
/*  8:   */ public class IsSame<T>
/*  9:   */   extends BaseMatcher<T>
/* 10:   */ {
/* 11:   */   private final T object;
/* 12:   */   
/* 13:   */   public IsSame(T object)
/* 14:   */   {
/* 15:18 */     this.object = object;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean matches(Object arg)
/* 19:   */   {
/* 20:22 */     return arg == this.object;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void describeTo(Description description)
/* 24:   */   {
/* 25:26 */     description.appendText("same(").appendValue(this.object).appendText(")");
/* 26:   */   }
/* 27:   */   
/* 28:   */   @Factory
/* 29:   */   public static <T> Matcher<T> sameInstance(T object)
/* 30:   */   {
/* 31:37 */     return new IsSame(object);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.IsSame
 * JD-Core Version:    0.7.0.1
 */