/*  1:   */ package org.hibernate.bytecode.buildtime.spi;
/*  2:   */ 
/*  3:   */ public class ExecutionException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public ExecutionException(String message)
/*  7:   */   {
/*  8:34 */     super(message);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public ExecutionException(Throwable cause)
/* 12:   */   {
/* 13:38 */     super(cause);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public ExecutionException(String message, Throwable cause)
/* 17:   */   {
/* 18:42 */     super(message, cause);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.buildtime.spi.ExecutionException
 * JD-Core Version:    0.7.0.1
 */