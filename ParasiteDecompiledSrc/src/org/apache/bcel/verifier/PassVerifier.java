/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public abstract class PassVerifier
/*   6:    */ {
/*   7: 88 */   private ArrayList messages = new ArrayList();
/*   8: 91 */   private VerificationResult verificationResult = null;
/*   9:    */   
/*  10:    */   public VerificationResult verify()
/*  11:    */   {
/*  12:107 */     if (this.verificationResult == null) {
/*  13:108 */       this.verificationResult = do_verify();
/*  14:    */     }
/*  15:110 */     return this.verificationResult;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public abstract VerificationResult do_verify();
/*  19:    */   
/*  20:    */   public void addMessage(String message)
/*  21:    */   {
/*  22:125 */     this.messages.add(message);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String[] getMessages()
/*  26:    */   {
/*  27:136 */     verify();
/*  28:137 */     String[] ret = new String[this.messages.size()];
/*  29:138 */     for (int i = 0; i < this.messages.size(); i++) {
/*  30:139 */       ret[i] = ((String)this.messages.get(i));
/*  31:    */     }
/*  32:141 */     return ret;
/*  33:    */   }
/*  34:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.PassVerifier
 * JD-Core Version:    0.7.0.1
 */