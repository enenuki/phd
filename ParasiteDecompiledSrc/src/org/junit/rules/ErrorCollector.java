/*  1:   */ package org.junit.rules;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.concurrent.Callable;
/*  6:   */ import org.hamcrest.Matcher;
/*  7:   */ import org.junit.Assert;
/*  8:   */ import org.junit.runners.model.MultipleFailureException;
/*  9:   */ 
/* 10:   */ public class ErrorCollector
/* 11:   */   extends Verifier
/* 12:   */ {
/* 13:36 */   private List<Throwable> errors = new ArrayList();
/* 14:   */   
/* 15:   */   protected void verify()
/* 16:   */     throws Throwable
/* 17:   */   {
/* 18:40 */     MultipleFailureException.assertEmpty(this.errors);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void addError(Throwable error)
/* 22:   */   {
/* 23:47 */     this.errors.add(error);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public <T> void checkThat(final T value, final Matcher<T> matcher)
/* 27:   */   {
/* 28:55 */     checkSucceeds(new Callable()
/* 29:   */     {
/* 30:   */       public Object call()
/* 31:   */         throws Exception
/* 32:   */       {
/* 33:57 */         Assert.assertThat(value, matcher);
/* 34:58 */         return value;
/* 35:   */       }
/* 36:   */     });
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Object checkSucceeds(Callable<Object> callable)
/* 40:   */   {
/* 41:   */     try
/* 42:   */     {
/* 43:70 */       return callable.call();
/* 44:   */     }
/* 45:   */     catch (Throwable e)
/* 46:   */     {
/* 47:72 */       addError(e);
/* 48:   */     }
/* 49:73 */     return null;
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.rules.ErrorCollector
 * JD-Core Version:    0.7.0.1
 */