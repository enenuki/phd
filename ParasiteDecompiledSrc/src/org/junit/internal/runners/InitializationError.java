/*  1:   */ package org.junit.internal.runners;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.List;
/*  5:   */ 
/*  6:   */ @Deprecated
/*  7:   */ public class InitializationError
/*  8:   */   extends Exception
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 1L;
/* 11:   */   private final List<Throwable> fErrors;
/* 12:   */   
/* 13:   */   public InitializationError(List<Throwable> errors)
/* 14:   */   {
/* 15:16 */     this.fErrors = errors;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public InitializationError(Throwable... errors)
/* 19:   */   {
/* 20:20 */     this(Arrays.asList(errors));
/* 21:   */   }
/* 22:   */   
/* 23:   */   public InitializationError(String string)
/* 24:   */   {
/* 25:24 */     this(new Throwable[] { new Exception(string) });
/* 26:   */   }
/* 27:   */   
/* 28:   */   public List<Throwable> getCauses()
/* 29:   */   {
/* 30:28 */     return this.fErrors;
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.InitializationError
 * JD-Core Version:    0.7.0.1
 */