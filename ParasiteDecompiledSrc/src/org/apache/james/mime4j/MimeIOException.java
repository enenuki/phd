/*  1:   */ package org.apache.james.mime4j;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ 
/*  5:   */ public class MimeIOException
/*  6:   */   extends IOException
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 5393613459533735409L;
/*  9:   */   
/* 10:   */   public MimeIOException(String message)
/* 11:   */   {
/* 12:38 */     this(new MimeException(message));
/* 13:   */   }
/* 14:   */   
/* 15:   */   public MimeIOException(MimeException cause)
/* 16:   */   {
/* 17:48 */     super(cause.getMessage());
/* 18:49 */     initCause(cause);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.MimeIOException
 * JD-Core Version:    0.7.0.1
 */