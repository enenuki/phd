/*  1:   */ package org.apache.xalan.xsltc.runtime;
/*  2:   */ 
/*  3:   */ public final class Operators
/*  4:   */ {
/*  5:   */   public static final int EQ = 0;
/*  6:   */   public static final int NE = 1;
/*  7:   */   public static final int GT = 2;
/*  8:   */   public static final int LT = 3;
/*  9:   */   public static final int GE = 4;
/* 10:   */   public static final int LE = 5;
/* 11:36 */   private static final String[] names = { "=", "!=", ">", "<", ">=", "<=" };
/* 12:   */   
/* 13:   */   public static final String getOpNames(int operator)
/* 14:   */   {
/* 15:41 */     return names[operator];
/* 16:   */   }
/* 17:   */   
/* 18:45 */   private static final int[] swapOpArray = { 0, 1, 3, 2, 5, 4 };
/* 19:   */   
/* 20:   */   public static final int swapOp(int operator)
/* 21:   */   {
/* 22:55 */     return swapOpArray[operator];
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.Operators
 * JD-Core Version:    0.7.0.1
 */