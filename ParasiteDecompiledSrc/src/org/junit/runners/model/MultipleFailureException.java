/*  1:   */ package org.junit.runners.model;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class MultipleFailureException
/*  8:   */   extends Exception
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:   */   private final List<Throwable> fErrors;
/* 12:   */   
/* 13:   */   public MultipleFailureException(List<Throwable> errors)
/* 14:   */   {
/* 15:18 */     this.fErrors = new ArrayList(errors);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public List<Throwable> getFailures()
/* 19:   */   {
/* 20:22 */     return Collections.unmodifiableList(this.fErrors);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getMessage()
/* 24:   */   {
/* 25:27 */     StringBuilder sb = new StringBuilder(String.format("There were %d errors:", new Object[] { Integer.valueOf(this.fErrors.size()) }));
/* 26:29 */     for (Throwable e : this.fErrors) {
/* 27:30 */       sb.append(String.format("\n  %s(%s)", new Object[] { e.getClass().getName(), e.getMessage() }));
/* 28:   */     }
/* 29:32 */     return sb.toString();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void assertEmpty(List<Throwable> errors)
/* 33:   */     throws Throwable
/* 34:   */   {
/* 35:46 */     if (errors.isEmpty()) {
/* 36:47 */       return;
/* 37:   */     }
/* 38:48 */     if (errors.size() == 1) {
/* 39:49 */       throw ((Throwable)errors.get(0));
/* 40:   */     }
/* 41:58 */     throw new org.junit.internal.runners.model.MultipleFailureException(errors);
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.model.MultipleFailureException
 * JD-Core Version:    0.7.0.1
 */