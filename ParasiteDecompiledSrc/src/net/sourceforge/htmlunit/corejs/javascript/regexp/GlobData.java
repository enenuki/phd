/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.regexp;
/*   2:    */ 
/*   3:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*   4:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*   5:    */ 
/*   6:    */ final class GlobData
/*   7:    */ {
/*   8:    */   int mode;
/*   9:    */   int optarg;
/*  10:    */   boolean global;
/*  11:    */   String str;
/*  12:    */   Scriptable arrayobj;
/*  13:    */   Function lambda;
/*  14:    */   String repstr;
/*  15:754 */   int dollar = -1;
/*  16:    */   StringBuilder charBuf;
/*  17:    */   int leftIndex;
/*  18:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.regexp.GlobData
 * JD-Core Version:    0.7.0.1
 */