/*  1:   */ package org.apache.bcel.verifier.exc;
/*  2:   */ 
/*  3:   */ public class ClassConstraintException
/*  4:   */   extends VerificationException
/*  5:   */ {
/*  6:   */   private String detailMessage;
/*  7:   */   
/*  8:   */   public ClassConstraintException() {}
/*  9:   */   
/* 10:   */   public ClassConstraintException(String message)
/* 11:   */   {
/* 12:80 */     super(message);
/* 13:81 */     this.detailMessage = message;
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.exc.ClassConstraintException
 * JD-Core Version:    0.7.0.1
 */