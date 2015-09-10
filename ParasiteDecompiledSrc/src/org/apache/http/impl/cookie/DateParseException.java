/*  1:   */ package org.apache.http.impl.cookie;
/*  2:   */ 
/*  3:   */ import org.apache.http.annotation.Immutable;
/*  4:   */ 
/*  5:   */ @Immutable
/*  6:   */ public class DateParseException
/*  7:   */   extends Exception
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 4417696455000643370L;
/* 10:   */   
/* 11:   */   public DateParseException() {}
/* 12:   */   
/* 13:   */   public DateParseException(String message)
/* 14:   */   {
/* 15:56 */     super(message);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.DateParseException
 * JD-Core Version:    0.7.0.1
 */