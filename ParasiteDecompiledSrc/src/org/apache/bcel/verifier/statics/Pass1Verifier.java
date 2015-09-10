/*   1:    */ package org.apache.bcel.verifier.statics;
/*   2:    */ 
/*   3:    */ import org.apache.bcel.Repository;
/*   4:    */ import org.apache.bcel.classfile.JavaClass;
/*   5:    */ import org.apache.bcel.verifier.PassVerifier;
/*   6:    */ import org.apache.bcel.verifier.VerificationResult;
/*   7:    */ import org.apache.bcel.verifier.Verifier;
/*   8:    */ import org.apache.bcel.verifier.exc.LoadingException;
/*   9:    */ import org.apache.bcel.verifier.exc.Utility;
/*  10:    */ import org.apache.bcel.verifier.exc.VerifierConstraintViolatedException;
/*  11:    */ 
/*  12:    */ public final class Pass1Verifier
/*  13:    */   extends PassVerifier
/*  14:    */ {
/*  15:    */   private JavaClass jc;
/*  16:    */   private Verifier myOwner;
/*  17:    */   
/*  18:    */   private JavaClass getJavaClass()
/*  19:    */   {
/*  20: 88 */     if (this.jc == null) {
/*  21: 89 */       this.jc = Repository.lookupClass(this.myOwner.getClassName());
/*  22:    */     }
/*  23: 91 */     return this.jc;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Pass1Verifier(Verifier owner)
/*  27:    */   {
/*  28:100 */     this.myOwner = owner;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public VerificationResult do_verify()
/*  32:    */   {
/*  33:    */     JavaClass jc;
/*  34:    */     try
/*  35:    */     {
/*  36:164 */       jc = getJavaClass();
/*  37:166 */       if (jc != null) {
/*  38:168 */         if (!this.myOwner.getClassName().equals(jc.getClassName())) {
/*  39:171 */           throw new LoadingException("Wrong name: the internal name of the .class file '" + jc.getClassName() + "' does not match the file's name '" + this.myOwner.getClassName() + "'.");
/*  40:    */         }
/*  41:    */       }
/*  42:    */     }
/*  43:    */     catch (LoadingException e)
/*  44:    */     {
/*  45:177 */       return new VerificationResult(2, e.getMessage());
/*  46:    */     }
/*  47:    */     catch (ClassFormatError e)
/*  48:    */     {
/*  49:181 */       return new VerificationResult(2, e.getMessage());
/*  50:    */     }
/*  51:    */     catch (RuntimeException e)
/*  52:    */     {
/*  53:186 */       return new VerificationResult(2, "Parsing via BCEL did not succeed. " + e.getClass().getName() + " occured:\n" + Utility.getStackTrace(e));
/*  54:    */     }
/*  55:189 */     if (jc != null) {
/*  56:190 */       return VerificationResult.VR_OK;
/*  57:    */     }
/*  58:195 */     return new VerificationResult(2, "Repository.lookup() failed. FILE NOT FOUND?");
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String[] getMessages()
/*  62:    */   {
/*  63:211 */     return super.getMessages();
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.statics.Pass1Verifier
 * JD-Core Version:    0.7.0.1
 */