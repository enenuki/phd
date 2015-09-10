/*  1:   */ package org.hamcrest.core;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Array;
/*  4:   */ import org.hamcrest.BaseMatcher;
/*  5:   */ import org.hamcrest.Description;
/*  6:   */ import org.hamcrest.Factory;
/*  7:   */ import org.hamcrest.Matcher;
/*  8:   */ 
/*  9:   */ public class IsEqual<T>
/* 10:   */   extends BaseMatcher<T>
/* 11:   */ {
/* 12:   */   private final Object object;
/* 13:   */   
/* 14:   */   public IsEqual(T equalArg)
/* 15:   */   {
/* 16:21 */     this.object = equalArg;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean matches(Object arg)
/* 20:   */   {
/* 21:25 */     return areEqual(this.object, arg);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void describeTo(Description description)
/* 25:   */   {
/* 26:29 */     description.appendValue(this.object);
/* 27:   */   }
/* 28:   */   
/* 29:   */   private static boolean areEqual(Object o1, Object o2)
/* 30:   */   {
/* 31:33 */     if ((o1 == null) || (o2 == null)) {
/* 32:34 */       return (o1 == null) && (o2 == null);
/* 33:   */     }
/* 34:35 */     if (isArray(o1)) {
/* 35:36 */       return (isArray(o2)) && (areArraysEqual(o1, o2));
/* 36:   */     }
/* 37:38 */     return o1.equals(o2);
/* 38:   */   }
/* 39:   */   
/* 40:   */   private static boolean areArraysEqual(Object o1, Object o2)
/* 41:   */   {
/* 42:43 */     return (areArrayLengthsEqual(o1, o2)) && (areArrayElementsEqual(o1, o2));
/* 43:   */   }
/* 44:   */   
/* 45:   */   private static boolean areArrayLengthsEqual(Object o1, Object o2)
/* 46:   */   {
/* 47:48 */     return Array.getLength(o1) == Array.getLength(o2);
/* 48:   */   }
/* 49:   */   
/* 50:   */   private static boolean areArrayElementsEqual(Object o1, Object o2)
/* 51:   */   {
/* 52:52 */     for (int i = 0; i < Array.getLength(o1); i++) {
/* 53:53 */       if (!areEqual(Array.get(o1, i), Array.get(o2, i))) {
/* 54:53 */         return false;
/* 55:   */       }
/* 56:   */     }
/* 57:55 */     return true;
/* 58:   */   }
/* 59:   */   
/* 60:   */   private static boolean isArray(Object o)
/* 61:   */   {
/* 62:59 */     return o.getClass().isArray();
/* 63:   */   }
/* 64:   */   
/* 65:   */   @Factory
/* 66:   */   public static <T> Matcher<T> equalTo(T operand)
/* 67:   */   {
/* 68:68 */     return new IsEqual(operand);
/* 69:   */   }
/* 70:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.core.IsEqual
 * JD-Core Version:    0.7.0.1
 */