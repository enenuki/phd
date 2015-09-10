/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Description;
/*  4:   */ import org.junit.runners.model.Statement;
/*  5:   */ 
/*  6:   */ public abstract class ExternalResource
/*  7:   */   extends TestRule
/*  8:   */ {
/*  9:   */   protected Statement apply(Statement base, Description description)
/* 10:   */   {
/* 11:38 */     return statement(base);
/* 12:   */   }
/* 13:   */   
/* 14:   */   private Statement statement(final Statement base)
/* 15:   */   {
/* 16:42 */     new Statement()
/* 17:   */     {
/* 18:   */       public void evaluate()
/* 19:   */         throws Throwable
/* 20:   */       {
/* 21:45 */         ExternalResource.this.before();
/* 22:   */         try
/* 23:   */         {
/* 24:47 */           base.evaluate();
/* 25:   */         }
/* 26:   */         finally
/* 27:   */         {
/* 28:49 */           ExternalResource.this.after();
/* 29:   */         }
/* 30:   */       }
/* 31:   */     };
/* 32:   */   }
/* 33:   */   
/* 34:   */   protected void before()
/* 35:   */     throws Throwable
/* 36:   */   {}
/* 37:   */   
/* 38:   */   protected void after() {}
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.ExternalResource
 * JD-Core Version:    0.7.0.1
 */