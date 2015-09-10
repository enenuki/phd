/*  1:   */ package org.hibernate.engine.jdbc.internal.proxy;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationHandler;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ 
/*  7:   */ public abstract class AbstractProxyHandler
/*  8:   */   implements InvocationHandler
/*  9:   */ {
/* 10:36 */   private boolean valid = true;
/* 11:   */   private final int hashCode;
/* 12:   */   
/* 13:   */   public AbstractProxyHandler(int hashCode)
/* 14:   */   {
/* 15:40 */     this.hashCode = hashCode;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected abstract Object continueInvocation(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
/* 19:   */     throws Throwable;
/* 20:   */   
/* 21:   */   public String toString()
/* 22:   */   {
/* 23:46 */     return super.toString() + "[valid=" + this.valid + "]";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final int hashCode()
/* 27:   */   {
/* 28:50 */     return this.hashCode;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected final boolean isValid()
/* 32:   */   {
/* 33:54 */     return this.valid;
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected final void invalidate()
/* 37:   */   {
/* 38:58 */     this.valid = false;
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected final void errorIfInvalid()
/* 42:   */   {
/* 43:62 */     if (!isValid()) {
/* 44:63 */       throw new HibernateException("proxy handle is no longer valid");
/* 45:   */     }
/* 46:   */   }
/* 47:   */   
/* 48:   */   public final Object invoke(Object proxy, Method method, Object[] args)
/* 49:   */     throws Throwable
/* 50:   */   {
/* 51:68 */     String methodName = method.getName();
/* 52:71 */     if ("toString".equals(methodName)) {
/* 53:72 */       return toString();
/* 54:   */     }
/* 55:74 */     if ("hashCode".equals(methodName)) {
/* 56:75 */       return Integer.valueOf(hashCode());
/* 57:   */     }
/* 58:77 */     if ("equals".equals(methodName)) {
/* 59:78 */       return Boolean.valueOf(equals(args[0]));
/* 60:   */     }
/* 61:81 */     return continueInvocation(proxy, method, args);
/* 62:   */   }
/* 63:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.proxy.AbstractProxyHandler
 * JD-Core Version:    0.7.0.1
 */