/*  1:   */ package org.apache.bcel.verifier.exc;
/*  2:   */ 
/*  3:   */ import java.io.PrintWriter;
/*  4:   */ import java.io.StringWriter;
/*  5:   */ 
/*  6:   */ public final class Utility
/*  7:   */ {
/*  8:   */   public static String getStackTrace(Throwable t)
/*  9:   */   {
/* 10:71 */     StringWriter sw = new StringWriter();
/* 11:72 */     PrintWriter pw = new PrintWriter(sw);
/* 12:73 */     t.printStackTrace(pw);
/* 13:74 */     return sw.toString();
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.bcel.verifier.exc.Utility
 * JD-Core Version:    0.7.0.1
 */