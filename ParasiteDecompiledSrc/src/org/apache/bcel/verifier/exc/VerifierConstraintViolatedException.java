/*   1:    */ package org.apache.bcel.verifier.exc;
/*   2:    */ 
/*   3:    */ public abstract class VerifierConstraintViolatedException
/*   4:    */   extends RuntimeException
/*   5:    */ {
/*   6:    */   private String detailMessage;
/*   7:    */   
/*   8:    */   VerifierConstraintViolatedException() {}
/*   9:    */   
/*  10:    */   VerifierConstraintViolatedException(String message)
/*  11:    */   {
/*  12: 84 */     super(message);
/*  13: 85 */     this.detailMessage = message;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public void extendMessage(String pre, String post)
/*  17:    */   {
/*  18: 95 */     if (pre == null) {
/*  19: 95 */       pre = "";
/*  20:    */     }
/*  21: 96 */     if (this.detailMessage == null) {
/*  22: 96 */       this.detailMessage = "";
/*  23:    */     }
/*  24: 97 */     if (post == null) {
/*  25: 97 */       post = "";
/*  26:    */     }
/*  27: 98 */     this.detailMessage = (pre + this.detailMessage + post);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getMessage()
/*  31:    */   {
/*  32:105 */     return this.detailMessage;
/*  33:    */   }
/*  34:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.exc.VerifierConstraintViolatedException
 * JD-Core Version:    0.7.0.1
 */