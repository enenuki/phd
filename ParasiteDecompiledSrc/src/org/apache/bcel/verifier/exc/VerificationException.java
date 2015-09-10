/*  1:   */ package org.apache.bcel.verifier.exc;
/*  2:   */ 
/*  3:   */ public abstract class VerificationException
/*  4:   */   extends VerifierConstraintViolatedException
/*  5:   */ {
/*  6:   */   VerificationException() {}
/*  7:   */   
/*  8:   */   VerificationException(String message)
/*  9:   */   {
/* 10:80 */     super(message);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.exc.VerificationException
 * JD-Core Version:    0.7.0.1
 */