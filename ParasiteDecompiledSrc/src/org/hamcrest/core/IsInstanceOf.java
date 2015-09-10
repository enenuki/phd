/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import org.hamcrest.BaseMatcher;
/*  4:   */ import org.hamcrest.Description;
/*  5:   */ import org.hamcrest.Factory;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ 
/*  8:   */ public class IsInstanceOf
/*  9:   */   extends BaseMatcher<Object>
/* 10:   */ {
/* 11:   */   private final Class<?> theClass;
/* 12:   */   
/* 13:   */   public IsInstanceOf(Class<?> theClass)
/* 14:   */   {
/* 15:24 */     this.theClass = theClass;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean matches(Object item)
/* 19:   */   {
/* 20:28 */     return this.theClass.isInstance(item);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void describeTo(Description description)
/* 24:   */   {
/* 25:32 */     description.appendText("an instance of ").appendText(this.theClass.getName());
/* 26:   */   }
/* 27:   */   
/* 28:   */   @Factory
/* 29:   */   public static Matcher<Object> instanceOf(Class<?> type)
/* 30:   */   {
/* 31:41 */     return new IsInstanceOf(type);
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.IsInstanceOf
 * JD-Core Version:    0.7.0.1
 */