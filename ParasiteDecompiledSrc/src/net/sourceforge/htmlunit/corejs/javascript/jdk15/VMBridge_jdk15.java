/*  1:   */ package net.sourceforge.htmlunit.corejs.javascript.jdk15;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Constructor;
/*  4:   */ import java.lang.reflect.Member;
/*  5:   */ import java.lang.reflect.Method;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  8:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  9:   */ import net.sourceforge.htmlunit.corejs.javascript.Wrapper;
/* 10:   */ import net.sourceforge.htmlunit.corejs.javascript.jdk13.VMBridge_jdk13;
/* 11:   */ 
/* 12:   */ public class VMBridge_jdk15
/* 13:   */   extends VMBridge_jdk13
/* 14:   */ {
/* 15:   */   public VMBridge_jdk15()
/* 16:   */     throws SecurityException, InstantiationException
/* 17:   */   {
/* 18:   */     try
/* 19:   */     {
/* 20:53 */       Method.class.getMethod("isVarArgs", (Class[])null);
/* 21:   */     }
/* 22:   */     catch (NoSuchMethodException e)
/* 23:   */     {
/* 24:57 */       throw new InstantiationException(e.getMessage());
/* 25:   */     }
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isVarArgs(Member member)
/* 29:   */   {
/* 30:63 */     if ((member instanceof Method)) {
/* 31:64 */       return ((Method)member).isVarArgs();
/* 32:   */     }
/* 33:65 */     if ((member instanceof Constructor)) {
/* 34:66 */       return ((Constructor)member).isVarArgs();
/* 35:   */     }
/* 36:68 */     return false;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Iterator<?> getJavaIterator(Context cx, Scriptable scope, Object obj)
/* 40:   */   {
/* 41:78 */     if ((obj instanceof Wrapper))
/* 42:   */     {
/* 43:79 */       Object unwrapped = ((Wrapper)obj).unwrap();
/* 44:80 */       Iterator<?> iterator = null;
/* 45:81 */       if ((unwrapped instanceof Iterator)) {
/* 46:82 */         iterator = (Iterator)unwrapped;
/* 47:   */       }
/* 48:83 */       if ((unwrapped instanceof Iterable)) {
/* 49:84 */         iterator = ((Iterable)unwrapped).iterator();
/* 50:   */       }
/* 51:85 */       return iterator;
/* 52:   */     }
/* 53:87 */     return null;
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.jdk15.VMBridge_jdk15
 * JD-Core Version:    0.7.0.1
 */