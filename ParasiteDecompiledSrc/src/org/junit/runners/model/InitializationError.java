/*  1:   */ package org.junit.runners.model;
/*  2:   */ 
/*  3:   */ import java.util.Arrays;
/*  4:   */ import java.util.List;
/*  5:   */ 
/*  6:   */ public class InitializationError
/*  7:   */   extends Exception
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 1L;
/* 10:   */   private final List<Throwable> fErrors;
/* 11:   */   
/* 12:   */   public InitializationError(List<Throwable> errors)
/* 13:   */   {
/* 14:18 */     this.fErrors = errors;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public InitializationError(Throwable error)
/* 18:   */   {
/* 19:22 */     this(Arrays.asList(new Throwable[] { error }));
/* 20:   */   }
/* 21:   */   
/* 22:   */   public InitializationError(String string)
/* 23:   */   {
/* 24:30 */     this(new Exception(string));
/* 25:   */   }
/* 26:   */   
/* 27:   */   public List<Throwable> getCauses()
/* 28:   */   {
/* 29:37 */     return this.fErrors;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.runners.model.InitializationError
 * JD-Core Version:    0.7.0.1
 */