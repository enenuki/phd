/*  1:   */ package org.junit.internal;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class RealSystem
/*  6:   */   implements JUnitSystem
/*  7:   */ {
/*  8:   */   public void exit(int code)
/*  9:   */   {
/* 10: 8 */     System.exit(code);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public PrintStream out()
/* 14:   */   {
/* 15:12 */     return System.out;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.RealSystem
 * JD-Core Version:    0.7.0.1
 */