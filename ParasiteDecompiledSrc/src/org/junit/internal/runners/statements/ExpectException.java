/*  1:   */ package org.junit.internal.runners.statements;
/*  2:   */ 
/*  3:   */ import org.junit.internal.AssumptionViolatedException;
/*  4:   */ import org.junit.runners.model.Statement;
/*  5:   */ 
/*  6:   */ public class ExpectException
/*  7:   */   extends Statement
/*  8:   */ {
/*  9:   */   private Statement fNext;
/* 10:   */   private final Class<? extends Throwable> fExpected;
/* 11:   */   
/* 12:   */   public ExpectException(Statement next, Class<? extends Throwable> expected)
/* 13:   */   {
/* 14:14 */     this.fNext = next;
/* 15:15 */     this.fExpected = expected;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void evaluate()
/* 19:   */     throws Exception
/* 20:   */   {
/* 21:20 */     boolean complete = false;
/* 22:   */     try
/* 23:   */     {
/* 24:22 */       this.fNext.evaluate();
/* 25:23 */       complete = true;
/* 26:   */     }
/* 27:   */     catch (AssumptionViolatedException e)
/* 28:   */     {
/* 29:25 */       throw e;
/* 30:   */     }
/* 31:   */     catch (Throwable e)
/* 32:   */     {
/* 33:27 */       if (!this.fExpected.isAssignableFrom(e.getClass()))
/* 34:   */       {
/* 35:28 */         String message = "Unexpected exception, expected<" + this.fExpected.getName() + "> but was<" + e.getClass().getName() + ">";
/* 36:   */         
/* 37:   */ 
/* 38:31 */         throw new Exception(message, e);
/* 39:   */       }
/* 40:   */     }
/* 41:34 */     if (complete) {
/* 42:35 */       throw new AssertionError("Expected exception: " + this.fExpected.getName());
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.statements.ExpectException
 * JD-Core Version:    0.7.0.1
 */