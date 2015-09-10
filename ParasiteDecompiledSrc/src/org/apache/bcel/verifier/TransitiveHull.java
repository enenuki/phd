/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.apache.bcel.Repository;
/*   5:    */ import org.apache.bcel.classfile.JavaClass;
/*   6:    */ 
/*   7:    */ public class TransitiveHull
/*   8:    */   implements VerifierFactoryObserver
/*   9:    */ {
/*  10: 73 */   private int indent = 0;
/*  11:    */   
/*  12:    */   public void update(String classname)
/*  13:    */   {
/*  14:    */     
/*  15: 84 */     for (int i = 0; i < this.indent; i++) {
/*  16: 85 */       System.out.print(" ");
/*  17:    */     }
/*  18: 87 */     System.out.println(classname);
/*  19: 88 */     this.indent += 1;
/*  20:    */     
/*  21: 90 */     Verifier v = VerifierFactory.getVerifier(classname);
/*  22:    */     
/*  23:    */ 
/*  24: 93 */     VerificationResult vr = v.doPass1();
/*  25: 94 */     if (vr != VerificationResult.VR_OK) {
/*  26: 95 */       System.out.println("Pass 1:\n" + vr);
/*  27:    */     }
/*  28: 97 */     vr = v.doPass2();
/*  29: 98 */     if (vr != VerificationResult.VR_OK) {
/*  30: 99 */       System.out.println("Pass 2:\n" + vr);
/*  31:    */     }
/*  32:101 */     if (vr == VerificationResult.VR_OK)
/*  33:    */     {
/*  34:102 */       JavaClass jc = Repository.lookupClass(v.getClassName());
/*  35:103 */       for (int i = 0; i < jc.getMethods().length; i++)
/*  36:    */       {
/*  37:104 */         vr = v.doPass3a(i);
/*  38:105 */         if (vr != VerificationResult.VR_OK) {
/*  39:106 */           System.out.println(v.getClassName() + ", Pass 3a, method " + i + " ['" + jc.getMethods()[i] + "']:\n" + vr);
/*  40:    */         }
/*  41:108 */         vr = v.doPass3b(i);
/*  42:109 */         if (vr != VerificationResult.VR_OK) {
/*  43:110 */           System.out.println(v.getClassName() + ", Pass 3b, method " + i + " ['" + jc.getMethods()[i] + "']:\n" + vr);
/*  44:    */         }
/*  45:    */       }
/*  46:    */     }
/*  47:114 */     this.indent -= 1;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static void main(String[] args)
/*  51:    */   {
/*  52:124 */     if (args.length != 1)
/*  53:    */     {
/*  54:125 */       System.out.println("Need exactly one argument: The root class to verify.");
/*  55:126 */       System.exit(1);
/*  56:    */     }
/*  57:129 */     int dotclasspos = args[0].lastIndexOf(".class");
/*  58:130 */     if (dotclasspos != -1) {
/*  59:130 */       args[0] = args[0].substring(0, dotclasspos);
/*  60:    */     }
/*  61:131 */     args[0] = args[0].replace('/', '.');
/*  62:    */     
/*  63:133 */     TransitiveHull th = new TransitiveHull();
/*  64:134 */     VerifierFactory.attach(th);
/*  65:135 */     VerifierFactory.getVerifier(args[0]);
/*  66:136 */     VerifierFactory.detach(th);
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.TransitiveHull
 * JD-Core Version:    0.7.0.1
 */