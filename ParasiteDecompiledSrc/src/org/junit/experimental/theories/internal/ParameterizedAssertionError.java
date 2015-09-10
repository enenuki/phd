/*  1:   */ package org.junit.experimental.theories.internal;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.Iterator;
/*  6:   */ 
/*  7:   */ public class ParameterizedAssertionError
/*  8:   */   extends RuntimeException
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:   */   
/* 12:   */   public ParameterizedAssertionError(Throwable targetException, String methodName, Object... params)
/* 13:   */   {
/* 14:16 */     super(String.format("%s(%s)", new Object[] { methodName, join(", ", params) }), targetException);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean equals(Object obj)
/* 18:   */   {
/* 19:21 */     return toString().equals(obj.toString());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static String join(String delimiter, Object... params)
/* 23:   */   {
/* 24:25 */     return join(delimiter, Arrays.asList(params));
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static String join(String delimiter, Collection<Object> values)
/* 28:   */   {
/* 29:30 */     StringBuffer buffer = new StringBuffer();
/* 30:31 */     Iterator<Object> iter = values.iterator();
/* 31:32 */     while (iter.hasNext())
/* 32:   */     {
/* 33:33 */       Object next = iter.next();
/* 34:34 */       buffer.append(stringValueOf(next));
/* 35:35 */       if (iter.hasNext()) {
/* 36:36 */         buffer.append(delimiter);
/* 37:   */       }
/* 38:   */     }
/* 39:39 */     return buffer.toString();
/* 40:   */   }
/* 41:   */   
/* 42:   */   private static String stringValueOf(Object next)
/* 43:   */   {
/* 44:   */     try
/* 45:   */     {
/* 46:44 */       return String.valueOf(next);
/* 47:   */     }
/* 48:   */     catch (Throwable e) {}
/* 49:46 */     return "[toString failed]";
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.experimental.theories.internal.ParameterizedAssertionError
 * JD-Core Version:    0.7.0.1
 */