/*  1:   */ package org.apache.commons.lang;
/*  2:   */ 
/*  3:   */ import org.apache.commons.lang.exception.NestableRuntimeException;
/*  4:   */ 
/*  5:   */ public class SerializationException
/*  6:   */   extends NestableRuntimeException
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 4029025366392702726L;
/*  9:   */   
/* 10:   */   public SerializationException() {}
/* 11:   */   
/* 12:   */   public SerializationException(String msg)
/* 13:   */   {
/* 14:54 */     super(msg);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public SerializationException(Throwable cause)
/* 18:   */   {
/* 19:65 */     super(cause);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public SerializationException(String msg, Throwable cause)
/* 23:   */   {
/* 24:77 */     super(msg, cause);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.SerializationException
 * JD-Core Version:    0.7.0.1
 */