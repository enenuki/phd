/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ public class VerificationResult
/*   4:    */ {
/*   5:    */   public static final int VERIFIED_NOTYET = 0;
/*   6:    */   public static final int VERIFIED_OK = 1;
/*   7:    */   public static final int VERIFIED_REJECTED = 2;
/*   8:    */   private static final String VERIFIED_NOTYET_MSG = "Not yet verified.";
/*   9:    */   private static final String VERIFIED_OK_MSG = "Passed verification.";
/*  10: 89 */   public static final VerificationResult VR_NOTYET = new VerificationResult(0, "Not yet verified.");
/*  11: 91 */   public static final VerificationResult VR_OK = new VerificationResult(1, "Passed verification.");
/*  12:    */   private int numeric;
/*  13:    */   private String detailMessage;
/*  14:    */   
/*  15:    */   private VerificationResult() {}
/*  16:    */   
/*  17:    */   public VerificationResult(int status, String message)
/*  18:    */   {
/*  19:104 */     this.numeric = status;
/*  20:105 */     this.detailMessage = message;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int getStatus()
/*  24:    */   {
/*  25:110 */     return this.numeric;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getMessage()
/*  29:    */   {
/*  30:115 */     return this.detailMessage;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean equals(Object o)
/*  34:    */   {
/*  35:122 */     if (!(o instanceof VerificationResult)) {
/*  36:122 */       return false;
/*  37:    */     }
/*  38:123 */     VerificationResult other = (VerificationResult)o;
/*  39:124 */     return (other.numeric == this.numeric) && (other.detailMessage.equals(this.detailMessage));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String toString()
/*  43:    */   {
/*  44:131 */     String ret = "";
/*  45:132 */     if (this.numeric == 0) {
/*  46:132 */       ret = "VERIFIED_NOTYET";
/*  47:    */     }
/*  48:133 */     if (this.numeric == 1) {
/*  49:133 */       ret = "VERIFIED_OK";
/*  50:    */     }
/*  51:134 */     if (this.numeric == 2) {
/*  52:134 */       ret = "VERIFIED_REJECTED";
/*  53:    */     }
/*  54:135 */     ret = ret + "\n" + this.detailMessage + "\n";
/*  55:136 */     return ret;
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.VerificationResult
 * JD-Core Version:    0.7.0.1
 */