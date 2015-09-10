/*   1:    */ package org.apache.bcel.verifier.exc;
/*   2:    */ 
/*   3:    */ public final class AssertionViolatedException
/*   4:    */   extends RuntimeException
/*   5:    */ {
/*   6:    */   private String detailMessage;
/*   7:    */   
/*   8:    */   public AssertionViolatedException() {}
/*   9:    */   
/*  10:    */   public AssertionViolatedException(String message)
/*  11:    */   {
/*  12: 76 */     super(message = "INTERNAL ERROR: " + message);
/*  13: 77 */     this.detailMessage = message;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public void extendMessage(String pre, String post)
/*  17:    */   {
/*  18: 85 */     if (pre == null) {
/*  19: 85 */       pre = "";
/*  20:    */     }
/*  21: 86 */     if (this.detailMessage == null) {
/*  22: 86 */       this.detailMessage = "";
/*  23:    */     }
/*  24: 87 */     if (post == null) {
/*  25: 87 */       post = "";
/*  26:    */     }
/*  27: 88 */     this.detailMessage = (pre + this.detailMessage + post);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getMessage()
/*  31:    */   {
/*  32: 95 */     return this.detailMessage;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static void main(String[] args)
/*  36:    */   {
/*  37:102 */     AssertionViolatedException ave = new AssertionViolatedException("Oops!");
/*  38:103 */     ave.extendMessage("\nFOUND:\n\t", "\nExiting!!\n");
/*  39:104 */     throw ave;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getStackTrace()
/*  43:    */   {
/*  44:112 */     return Utility.getStackTrace(this);
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.exc.AssertionViolatedException
 * JD-Core Version:    0.7.0.1
 */