/*  1:   */ package org.junit.internal.runners.statements;
/*  2:   */ 
/*  3:   */ import org.junit.runners.model.Statement;
/*  4:   */ 
/*  5:   */ public class Fail
/*  6:   */   extends Statement
/*  7:   */ {
/*  8:   */   private final Throwable fError;
/*  9:   */   
/* 10:   */   public Fail(Throwable e)
/* 11:   */   {
/* 12:10 */     this.fError = e;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void evaluate()
/* 16:   */     throws Throwable
/* 17:   */   {
/* 18:15 */     throw this.fError;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.statements.Fail
 * JD-Core Version:    0.7.0.1
 */