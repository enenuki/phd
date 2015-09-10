/*  1:   */ package org.apache.commons.collections;
/*  2:   */ 
/*  3:   */ public class BufferOverflowException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   private final Throwable throwable;
/*  7:   */   
/*  8:   */   public BufferOverflowException()
/*  9:   */   {
/* 10:42 */     this.throwable = null;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public BufferOverflowException(String message)
/* 14:   */   {
/* 15:51 */     this(message, null);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public BufferOverflowException(String message, Throwable exception)
/* 19:   */   {
/* 20:61 */     super(message);
/* 21:62 */     this.throwable = exception;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public final Throwable getCause()
/* 25:   */   {
/* 26:71 */     return this.throwable;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BufferOverflowException
 * JD-Core Version:    0.7.0.1
 */