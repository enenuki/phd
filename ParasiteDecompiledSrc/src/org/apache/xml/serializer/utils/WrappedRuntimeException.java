/*  1:   */ package org.apache.xml.serializer.utils;
/*  2:   */ 
/*  3:   */ public final class WrappedRuntimeException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   static final long serialVersionUID = 7140414456714658073L;
/*  7:   */   private Exception m_exception;
/*  8:   */   
/*  9:   */   public WrappedRuntimeException(Exception e)
/* 10:   */   {
/* 11:52 */     super(e.getMessage());
/* 12:   */     
/* 13:54 */     this.m_exception = e;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public WrappedRuntimeException(String msg, Exception e)
/* 17:   */   {
/* 18:67 */     super(msg);
/* 19:   */     
/* 20:69 */     this.m_exception = e;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Exception getException()
/* 24:   */   {
/* 25:79 */     return this.m_exception;
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.utils.WrappedRuntimeException
 * JD-Core Version:    0.7.0.1
 */