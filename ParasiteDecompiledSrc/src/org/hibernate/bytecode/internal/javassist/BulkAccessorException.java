/*  1:   */ package org.hibernate.bytecode.internal.javassist;
/*  2:   */ 
/*  3:   */ public class BulkAccessorException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   private Throwable myCause;
/*  7:   */   private int index;
/*  8:   */   
/*  9:   */   public Throwable getCause()
/* 10:   */   {
/* 11:40 */     return this.myCause == this ? null : this.myCause;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public synchronized Throwable initCause(Throwable cause)
/* 15:   */   {
/* 16:48 */     this.myCause = cause;
/* 17:49 */     return this;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public BulkAccessorException(String message)
/* 21:   */   {
/* 22:58 */     super(message);
/* 23:59 */     this.index = -1;
/* 24:60 */     initCause(null);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public BulkAccessorException(String message, int index)
/* 28:   */   {
/* 29:69 */     this(message + ": " + index);
/* 30:70 */     this.index = index;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public BulkAccessorException(String message, Throwable cause)
/* 34:   */   {
/* 35:77 */     super(message);
/* 36:78 */     this.index = -1;
/* 37:79 */     initCause(cause);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public BulkAccessorException(Throwable cause, int index)
/* 41:   */   {
/* 42:88 */     this("Property " + index);
/* 43:89 */     this.index = index;
/* 44:90 */     initCause(cause);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int getIndex()
/* 48:   */   {
/* 49:99 */     return this.index;
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.BulkAccessorException
 * JD-Core Version:    0.7.0.1
 */