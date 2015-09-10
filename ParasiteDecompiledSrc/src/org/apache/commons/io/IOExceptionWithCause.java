/*  1:   */ package org.apache.commons.io;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ public class IOExceptionWithCause
/*  6:   */   extends IOException
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1L;
/*  9:   */   
/* 10:   */   public IOExceptionWithCause(String message, Throwable cause)
/* 11:   */   {
/* 12:50 */     super(message);
/* 13:51 */     initCause(cause);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public IOExceptionWithCause(Throwable cause)
/* 17:   */   {
/* 18:65 */     super(cause == null ? null : cause.toString());
/* 19:66 */     initCause(cause);
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.IOExceptionWithCause
 * JD-Core Version:    0.7.0.1
 */