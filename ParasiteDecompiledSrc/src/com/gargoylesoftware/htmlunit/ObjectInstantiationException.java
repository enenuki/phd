/*  1:   */ package com.gargoylesoftware.htmlunit;
/*  2:   */ 
/*  3:   */ public class ObjectInstantiationException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public ObjectInstantiationException(String message, Throwable cause)
/*  7:   */   {
/*  8:31 */     super(message, cause);
/*  9:   */   }
/* 10:   */   
/* 11:   */   @Deprecated
/* 12:   */   public Throwable getCauseException()
/* 13:   */   {
/* 14:41 */     return getCause();
/* 15:   */   }
/* 16:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.ObjectInstantiationException
 * JD-Core Version:    0.7.0.1
 */