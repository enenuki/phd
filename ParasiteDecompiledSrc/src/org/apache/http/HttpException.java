/*  1:   */ package org.apache.http;
/*  2:   */ 
/*  3:   */ import org.apache.http.util.ExceptionUtils;
/*  4:   */ 
/*  5:   */ public class HttpException
/*  6:   */   extends Exception
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = -5437299376222011036L;
/*  9:   */   
/* 10:   */   public HttpException() {}
/* 11:   */   
/* 12:   */   public HttpException(String message)
/* 13:   */   {
/* 14:54 */     super(message);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public HttpException(String message, Throwable cause)
/* 18:   */   {
/* 19:65 */     super(message);
/* 20:66 */     ExceptionUtils.initCause(this, cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.HttpException
 * JD-Core Version:    0.7.0.1
 */