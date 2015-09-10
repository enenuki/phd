/*  1:   */ package org.apache.commons.lang;
/*  2:   */ 
/*  3:   */ import org.apache.commons.lang.exception.NestableRuntimeException;
/*  4:   */ 
/*  5:   */ public class UnhandledException
/*  6:   */   extends NestableRuntimeException
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1832101364842773720L;
/*  9:   */   
/* 10:   */   public UnhandledException(Throwable cause)
/* 11:   */   {
/* 12:60 */     super(cause);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public UnhandledException(String message, Throwable cause)
/* 16:   */   {
/* 17:70 */     super(message, cause);
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.UnhandledException
 * JD-Core Version:    0.7.0.1
 */