/*  1:   */ package org.apache.xalan.xsltc;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class ProcessorVersion
/*  6:   */ {
/*  7:41 */   private static int MAJOR = 1;
/*  8:42 */   private static int MINOR = 0;
/*  9:43 */   private static int DELTA = 0;
/* 10:   */   
/* 11:   */   public static void main(String[] args)
/* 12:   */   {
/* 13:46 */     System.out.println("XSLTC version " + MAJOR + "." + MINOR + (DELTA > 0 ? "." + DELTA : ""));
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.ProcessorVersion
 * JD-Core Version:    0.7.0.1
 */