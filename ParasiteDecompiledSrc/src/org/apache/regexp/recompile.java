/*   1:    */ package org.apache.regexp;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public class recompile
/*   6:    */ {
/*   7:    */   public static void main(String[] paramArrayOfString)
/*   8:    */   {
/*   9:109 */     RECompiler localRECompiler = new RECompiler();
/*  10:112 */     if ((paramArrayOfString.length <= 0) || (paramArrayOfString.length % 2 != 0))
/*  11:    */     {
/*  12:114 */       System.out.println("Usage: recompile <patternname> <pattern>");
/*  13:115 */       System.exit(0);
/*  14:    */     }
/*  15:119 */     for (int i = 0; i < paramArrayOfString.length; i += 2) {
/*  16:    */       try
/*  17:    */       {
/*  18:124 */         String str1 = paramArrayOfString[i];
/*  19:125 */         String str2 = paramArrayOfString[(i + 1)];
/*  20:126 */         String str3 = str1 + "PatternInstructions";
/*  21:    */         
/*  22:    */ 
/*  23:129 */         System.out.print("\n    // Pre-compiled regular expression '" + str2 + "'\n" + 
/*  24:130 */           "    private static char[] " + str3 + " = \n    {");
/*  25:    */         
/*  26:    */ 
/*  27:133 */         REProgram localREProgram = localRECompiler.compile(str2);
/*  28:    */         
/*  29:    */ 
/*  30:136 */         int j = 7;
/*  31:    */         
/*  32:    */ 
/*  33:139 */         char[] arrayOfChar = localREProgram.getInstructions();
/*  34:140 */         for (int k = 0; k < arrayOfChar.length; k++)
/*  35:    */         {
/*  36:143 */           if (k % j == 0) {
/*  37:145 */             System.out.print("\n        ");
/*  38:    */           }
/*  39:149 */           String str4 = Integer.toHexString(arrayOfChar[k]);
/*  40:150 */           while (str4.length() < 4) {
/*  41:152 */             str4 = "0" + str4;
/*  42:    */           }
/*  43:154 */           System.out.print("0x" + str4 + ", ");
/*  44:    */         }
/*  45:158 */         System.out.println("\n    };");
/*  46:159 */         System.out.println("\n    private static RE " + str1 + "Pattern = new RE(new REProgram(" + str3 + "));");
/*  47:    */       }
/*  48:    */       catch (RESyntaxException localRESyntaxException)
/*  49:    */       {
/*  50:163 */         System.out.println("Syntax error in expression \"" + paramArrayOfString[i] + "\": " + localRESyntaxException.toString());
/*  51:    */       }
/*  52:    */       catch (Exception localException)
/*  53:    */       {
/*  54:167 */         System.out.println("Unexpected exception: " + localException.toString());
/*  55:    */       }
/*  56:    */       catch (Error localError)
/*  57:    */       {
/*  58:171 */         System.out.println("Internal error: " + localError.toString());
/*  59:    */       }
/*  60:    */     }
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.recompile
 * JD-Core Version:    0.7.0.1
 */