/*  1:   */ package org.junit.internal.runners.statements;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import org.junit.runners.model.FrameworkMethod;
/*  5:   */ import org.junit.runners.model.Statement;
/*  6:   */ 
/*  7:   */ public class RunBefores
/*  8:   */   extends Statement
/*  9:   */ {
/* 10:   */   private final Statement fNext;
/* 11:   */   private final Object fTarget;
/* 12:   */   private final List<FrameworkMethod> fBefores;
/* 13:   */   
/* 14:   */   public RunBefores(Statement next, List<FrameworkMethod> befores, Object target)
/* 15:   */   {
/* 16:19 */     this.fNext = next;
/* 17:20 */     this.fBefores = befores;
/* 18:21 */     this.fTarget = target;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void evaluate()
/* 22:   */     throws Throwable
/* 23:   */   {
/* 24:26 */     for (FrameworkMethod before : this.fBefores) {
/* 25:27 */       before.invokeExplosively(this.fTarget, new Object[0]);
/* 26:   */     }
/* 27:28 */     this.fNext.evaluate();
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.statements.RunBefores
 * JD-Core Version:    0.7.0.1
 */