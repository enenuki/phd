/*  1:   */ package org.apache.http.client.utils;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class JdkIdn
/*  9:   */   implements Idn
/* 10:   */ {
/* 11:   */   private final Method toUnicode;
/* 12:   */   
/* 13:   */   public JdkIdn()
/* 14:   */     throws ClassNotFoundException
/* 15:   */   {
/* 16:52 */     Class<?> clazz = Class.forName("java.net.IDN");
/* 17:   */     try
/* 18:   */     {
/* 19:54 */       this.toUnicode = clazz.getMethod("toUnicode", new Class[] { String.class });
/* 20:   */     }
/* 21:   */     catch (SecurityException e)
/* 22:   */     {
/* 23:57 */       throw new IllegalStateException(e.getMessage(), e);
/* 24:   */     }
/* 25:   */     catch (NoSuchMethodException e)
/* 26:   */     {
/* 27:60 */       throw new IllegalStateException(e.getMessage(), e);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String toUnicode(String punycode)
/* 32:   */   {
/* 33:   */     try
/* 34:   */     {
/* 35:66 */       return (String)this.toUnicode.invoke(null, new Object[] { punycode });
/* 36:   */     }
/* 37:   */     catch (IllegalAccessException e)
/* 38:   */     {
/* 39:68 */       throw new IllegalStateException(e.getMessage(), e);
/* 40:   */     }
/* 41:   */     catch (InvocationTargetException e)
/* 42:   */     {
/* 43:70 */       Throwable t = e.getCause();
/* 44:71 */       throw new RuntimeException(t.getMessage(), t);
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.utils.JdkIdn
 * JD-Core Version:    0.7.0.1
 */