/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  5:   */ import net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  7:   */ 
/*  8:   */ public final class StringCustom
/*  9:   */ {
/* 10:   */   public static String trimLeft(Context context, Scriptable thisObj, Object[] args, Function function)
/* 11:   */   {
/* 12:42 */     String string = Context.toString(thisObj);
/* 13:43 */     int start = 0;
/* 14:44 */     int length = string.length();
/* 15:45 */     while ((start < length) && (ScriptRuntime.isJSWhitespaceOrLineTerminator(string.charAt(start)))) {
/* 16:46 */       start++;
/* 17:   */     }
/* 18:48 */     if (start == 0) {
/* 19:49 */       return string;
/* 20:   */     }
/* 21:51 */     return string.substring(start, length);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static String trimRight(Context context, Scriptable thisObj, Object[] args, Function function)
/* 25:   */   {
/* 26:64 */     String string = Context.toString(thisObj);
/* 27:65 */     int length = string.length();
/* 28:66 */     int end = length;
/* 29:67 */     while ((end > 0) && (ScriptRuntime.isJSWhitespaceOrLineTerminator(string.charAt(end - 1)))) {
/* 30:68 */       end--;
/* 31:   */     }
/* 32:70 */     if (end == length) {
/* 33:71 */       return string;
/* 34:   */     }
/* 35:73 */     return string.substring(0, end);
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.StringCustom
 * JD-Core Version:    0.7.0.1
 */