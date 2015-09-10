/*  1:   */ package org.apache.commons.lang.exception;
/*  2:   */ 
/*  3:   */ public class CloneFailedException
/*  4:   */   extends NestableRuntimeException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 20091223L;
/*  7:   */   
/*  8:   */   public CloneFailedException(String message)
/*  9:   */   {
/* 10:40 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public CloneFailedException(Throwable cause)
/* 14:   */   {
/* 15:50 */     super(cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CloneFailedException(String message, Throwable cause)
/* 19:   */   {
/* 20:61 */     super(message, cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.exception.CloneFailedException
 * JD-Core Version:    0.7.0.1
 */