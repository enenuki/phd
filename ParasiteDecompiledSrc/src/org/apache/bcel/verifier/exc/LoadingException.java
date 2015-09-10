/*  1:   */ package org.apache.bcel.verifier.exc;
/*  2:   */ 
/*  3:   */ public class LoadingException
/*  4:   */   extends VerifierConstraintViolatedException
/*  5:   */ {
/*  6:   */   private String detailMessage;
/*  7:   */   
/*  8:   */   public LoadingException() {}
/*  9:   */   
/* 10:   */   public LoadingException(String message)
/* 11:   */   {
/* 12:79 */     super(message);
/* 13:80 */     this.detailMessage = message;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.exc.LoadingException
 * JD-Core Version:    0.7.0.1
 */