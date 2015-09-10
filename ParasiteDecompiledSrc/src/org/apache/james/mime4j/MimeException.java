/*  1:   */ package org.apache.james.mime4j;
/*  2:   */ 
/*  3:   */ public class MimeException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 8352821278714188542L;
/*  7:   */   
/*  8:   */   public MimeException(String message)
/*  9:   */   {
/* 10:35 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public MimeException(Throwable cause)
/* 14:   */   {
/* 15:44 */     super(cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public MimeException(String message, Throwable cause)
/* 19:   */   {
/* 20:54 */     super(message);
/* 21:55 */     initCause(cause);
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.MimeException
 * JD-Core Version:    0.7.0.1
 */