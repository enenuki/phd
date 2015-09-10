/*  1:   */ package org.junit.internal.runners.statements;
/*  2:   */ 
/*  3:   */ import org.junit.runners.model.FrameworkMethod;
/*  4:   */ import org.junit.runners.model.Statement;
/*  5:   */ 
/*  6:   */ public class InvokeMethod
/*  7:   */   extends Statement
/*  8:   */ {
/*  9:   */   private final FrameworkMethod fTestMethod;
/* 10:   */   private Object fTarget;
/* 11:   */   
/* 12:   */   public InvokeMethod(FrameworkMethod testMethod, Object target)
/* 13:   */   {
/* 14:14 */     this.fTestMethod = testMethod;
/* 15:15 */     this.fTarget = target;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void evaluate()
/* 19:   */     throws Throwable
/* 20:   */   {
/* 21:20 */     this.fTestMethod.invokeExplosively(this.fTarget, new Object[0]);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.statements.InvokeMethod
 * JD-Core Version:    0.7.0.1
 */