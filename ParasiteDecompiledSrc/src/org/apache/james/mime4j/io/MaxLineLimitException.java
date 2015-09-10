/*  1:   */ package org.apache.james.mime4j.io;
/*  2:   */ 
/*  3:   */ import org.apache.james.mime4j.MimeIOException;
/*  4:   */ 
/*  5:   */ public class MaxLineLimitException
/*  6:   */   extends MimeIOException
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 8039001187837730773L;
/*  9:   */   
/* 10:   */   public MaxLineLimitException(String message)
/* 11:   */   {
/* 12:33 */     super(message);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.io.MaxLineLimitException
 * JD-Core Version:    0.7.0.1
 */