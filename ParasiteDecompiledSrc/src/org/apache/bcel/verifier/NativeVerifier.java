/*   1:    */ package org.apache.bcel.verifier;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public abstract class NativeVerifier
/*   6:    */ {
/*   7:    */   public static void main(String[] args)
/*   8:    */   {
/*   9: 79 */     if (args.length != 1)
/*  10:    */     {
/*  11: 80 */       System.out.println("Verifier front-end: need exactly one argument.");
/*  12: 81 */       System.exit(1);
/*  13:    */     }
/*  14: 84 */     int dotclasspos = args[0].lastIndexOf(".class");
/*  15: 85 */     if (dotclasspos != -1) {
/*  16: 85 */       args[0] = args[0].substring(0, dotclasspos);
/*  17:    */     }
/*  18: 86 */     args[0] = args[0].replace('/', '.');
/*  19:    */     try
/*  20:    */     {
/*  21: 91 */       Class.forName(args[0]);
/*  22:    */     }
/*  23:    */     catch (ExceptionInInitializerError eiie)
/*  24:    */     {
/*  25: 94 */       System.out.println("NativeVerifier: ExceptionInInitializerError encountered on '" + args[0] + "'.");
/*  26: 95 */       System.out.println(eiie);
/*  27: 96 */       System.exit(1);
/*  28:    */     }
/*  29:    */     catch (LinkageError le)
/*  30:    */     {
/*  31: 99 */       System.out.println("NativeVerifier: LinkageError encountered on '" + args[0] + "'.");
/*  32:100 */       System.out.println(le);
/*  33:101 */       System.exit(1);
/*  34:    */     }
/*  35:    */     catch (ClassNotFoundException cnfe)
/*  36:    */     {
/*  37:104 */       System.out.println("NativeVerifier: FILE NOT FOUND: '" + args[0] + "'.");
/*  38:105 */       System.exit(1);
/*  39:    */     }
/*  40:    */     catch (Throwable t)
/*  41:    */     {
/*  42:108 */       System.out.println("NativeVerifier: Unspecified verification error on'" + args[0] + "'.");
/*  43:109 */       System.exit(1);
/*  44:    */     }
/*  45:112 */     System.out.println("NativeVerifier: Class file '" + args[0] + "' seems to be okay.");
/*  46:113 */     System.exit(0);
/*  47:    */   }
/*  48:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.NativeVerifier
 * JD-Core Version:    0.7.0.1
 */