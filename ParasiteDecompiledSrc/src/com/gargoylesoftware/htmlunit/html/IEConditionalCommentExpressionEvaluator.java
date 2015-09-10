/*  1:   */ package com.gargoylesoftware.htmlunit.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import org.apache.commons.lang.StringUtils;
/*  5:   */ 
/*  6:   */ public final class IEConditionalCommentExpressionEvaluator
/*  7:   */ {
/*  8:   */   public static boolean evaluate(String condition, BrowserVersion browserVersion)
/*  9:   */   {
/* 10:44 */     condition = condition.trim();
/* 11:45 */     if ("IE".equals(condition)) {
/* 12:46 */       return true;
/* 13:   */     }
/* 14:48 */     if ("true".equals(condition)) {
/* 15:49 */       return true;
/* 16:   */     }
/* 17:51 */     if ("false".equals(condition)) {
/* 18:52 */       return false;
/* 19:   */     }
/* 20:54 */     if (condition.contains("&")) {
/* 21:55 */       return (evaluate(StringUtils.substringBefore(condition, "&"), browserVersion)) && (evaluate(StringUtils.substringAfter(condition, "&"), browserVersion));
/* 22:   */     }
/* 23:58 */     if (condition.contains("|")) {
/* 24:59 */       return (evaluate(StringUtils.substringBefore(condition, "|"), browserVersion)) || (evaluate(StringUtils.substringAfter(condition, "|"), browserVersion));
/* 25:   */     }
/* 26:62 */     if (condition.startsWith("!")) {
/* 27:63 */       return !evaluate(condition.substring(1), browserVersion);
/* 28:   */     }
/* 29:65 */     if (condition.startsWith("IE"))
/* 30:   */     {
/* 31:66 */       String currentVersion = Float.toString(browserVersion.getBrowserVersionNumeric());
/* 32:67 */       return currentVersion.startsWith(condition.substring(2).trim());
/* 33:   */     }
/* 34:69 */     if (condition.startsWith("lte IE")) {
/* 35:70 */       return browserVersion.getBrowserVersionNumeric() <= parseVersion(condition.substring(6));
/* 36:   */     }
/* 37:72 */     if (condition.startsWith("lt IE")) {
/* 38:73 */       return browserVersion.getBrowserVersionNumeric() < parseVersion(condition.substring(5));
/* 39:   */     }
/* 40:75 */     if (condition.startsWith("gt IE")) {
/* 41:76 */       return browserVersion.getBrowserVersionNumeric() > parseVersion(condition.substring(5));
/* 42:   */     }
/* 43:78 */     if (condition.startsWith("gte IE")) {
/* 44:79 */       return browserVersion.getBrowserVersionNumeric() >= parseVersion(condition.substring(6));
/* 45:   */     }
/* 46:81 */     if (condition.startsWith("lt")) {
/* 47:82 */       return true;
/* 48:   */     }
/* 49:84 */     if (condition.startsWith("gt")) {
/* 50:85 */       return false;
/* 51:   */     }
/* 52:87 */     if (condition.startsWith("(")) {
/* 53:89 */       return evaluate(StringUtils.substringBetween(condition, "(", ")"), browserVersion);
/* 54:   */     }
/* 55:92 */     return false;
/* 56:   */   }
/* 57:   */   
/* 58:   */   private static float parseVersion(String s)
/* 59:   */   {
/* 60:97 */     return Float.parseFloat(s);
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.IEConditionalCommentExpressionEvaluator
 * JD-Core Version:    0.7.0.1
 */