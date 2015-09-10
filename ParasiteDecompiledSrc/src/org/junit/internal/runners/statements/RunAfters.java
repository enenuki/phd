/*  1:   */ package org.junit.internal.runners.statements;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import org.junit.runners.model.FrameworkMethod;
/*  7:   */ import org.junit.runners.model.MultipleFailureException;
/*  8:   */ import org.junit.runners.model.Statement;
/*  9:   */ 
/* 10:   */ public class RunAfters
/* 11:   */   extends Statement
/* 12:   */ {
/* 13:   */   private final Statement fNext;
/* 14:   */   private final Object fTarget;
/* 15:   */   private final List<FrameworkMethod> fAfters;
/* 16:   */   
/* 17:   */   public RunAfters(Statement next, List<FrameworkMethod> afters, Object target)
/* 18:   */   {
/* 19:21 */     this.fNext = next;
/* 20:22 */     this.fAfters = afters;
/* 21:23 */     this.fTarget = target;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void evaluate()
/* 25:   */     throws Throwable
/* 26:   */   {
/* 27:28 */     List<Throwable> errors = new ArrayList();
/* 28:29 */     errors.clear();
/* 29:   */     try
/* 30:   */     {
/* 31:31 */       this.fNext.evaluate();
/* 32:   */     }
/* 33:   */     catch (Throwable e)
/* 34:   */     {
/* 35:   */       Iterator i$;
/* 36:   */       FrameworkMethod each;
/* 37:33 */       errors.add(e);
/* 38:   */     }
/* 39:   */     finally
/* 40:   */     {
/* 41:   */       Iterator i$;
/* 42:   */       FrameworkMethod each;
/* 43:35 */       for (FrameworkMethod each : this.fAfters) {
/* 44:   */         try
/* 45:   */         {
/* 46:37 */           each.invokeExplosively(this.fTarget, new Object[0]);
/* 47:   */         }
/* 48:   */         catch (Throwable e)
/* 49:   */         {
/* 50:39 */           errors.add(e);
/* 51:   */         }
/* 52:   */       }
/* 53:   */     }
/* 54:42 */     MultipleFailureException.assertEmpty(errors);
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.statements.RunAfters
 * JD-Core Version:    0.7.0.1
 */