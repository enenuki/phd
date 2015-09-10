/*  1:   */ package org.apache.commons.collections;
/*  2:   */ 
/*  3:   */ import java.util.NoSuchElementException;
/*  4:   */ 
/*  5:   */ public class BufferUnderflowException
/*  6:   */   extends NoSuchElementException
/*  7:   */ {
/*  8:   */   private final Throwable throwable;
/*  9:   */   
/* 10:   */   public BufferUnderflowException()
/* 11:   */   {
/* 12:45 */     this.throwable = null;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public BufferUnderflowException(String message)
/* 16:   */   {
/* 17:54 */     this(message, null);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public BufferUnderflowException(String message, Throwable exception)
/* 21:   */   {
/* 22:64 */     super(message);
/* 23:65 */     this.throwable = exception;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public final Throwable getCause()
/* 27:   */   {
/* 28:74 */     return this.throwable;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BufferUnderflowException
 * JD-Core Version:    0.7.0.1
 */