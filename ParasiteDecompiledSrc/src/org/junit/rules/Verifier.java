/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Description;
/*  4:   */ import org.junit.runners.model.Statement;
/*  5:   */ 
/*  6:   */ public class Verifier
/*  7:   */   extends TestRule
/*  8:   */ {
/*  9:   */   protected Statement apply(final Statement base, Description description)
/* 10:   */   {
/* 11:31 */     new Statement()
/* 12:   */     {
/* 13:   */       public void evaluate()
/* 14:   */         throws Throwable
/* 15:   */       {
/* 16:34 */         base.evaluate();
/* 17:35 */         Verifier.this.verify();
/* 18:   */       }
/* 19:   */     };
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected void verify()
/* 23:   */     throws Throwable
/* 24:   */   {}
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.Verifier
 * JD-Core Version:    0.7.0.1
 */