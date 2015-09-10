/*  1:   */ package org.apache.james.mime4j.field;
/*  2:   */ 
/*  3:   */ import org.apache.james.mime4j.MimeException;
/*  4:   */ 
/*  5:   */ public class ParseException
/*  6:   */   extends MimeException
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1L;
/*  9:   */   
/* 10:   */   protected ParseException(String message)
/* 11:   */   {
/* 12:38 */     super(message);
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected ParseException(Throwable cause)
/* 16:   */   {
/* 17:48 */     super(cause);
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected ParseException(String message, Throwable cause)
/* 21:   */   {
/* 22:61 */     super(message, cause);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.ParseException
 * JD-Core Version:    0.7.0.1
 */