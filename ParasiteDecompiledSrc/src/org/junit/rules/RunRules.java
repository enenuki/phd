/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import org.junit.runner.Description;
/*  4:   */ import org.junit.runners.model.Statement;
/*  5:   */ 
/*  6:   */ public class RunRules
/*  7:   */   extends Statement
/*  8:   */ {
/*  9:   */   private final Statement statement;
/* 10:   */   
/* 11:   */   public RunRules(Statement base, Iterable<TestRule> rules, Description description)
/* 12:   */   {
/* 13:13 */     this.statement = applyAll(base, rules, description);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void evaluate()
/* 17:   */     throws Throwable
/* 18:   */   {
/* 19:18 */     this.statement.evaluate();
/* 20:   */   }
/* 21:   */   
/* 22:   */   private static Statement applyAll(Statement result, Iterable<TestRule> rules, Description description)
/* 23:   */   {
/* 24:23 */     for (TestRule each : rules) {
/* 25:24 */       result = each.apply(result, description);
/* 26:   */     }
/* 27:25 */     return result;
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.RunRules
 * JD-Core Version:    0.7.0.1
 */