/*  1:   */ package com.gargoylesoftware.htmlunit.javascript;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.ScriptPreProcessor;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  6:   */ import java.util.regex.Matcher;
/*  7:   */ import java.util.regex.Pattern;
/*  8:   */ 
/*  9:   */ public class IEWeirdSyntaxScriptPreProcessor
/* 10:   */   implements ScriptPreProcessor
/* 11:   */ {
/* 12:32 */   private static final IEWeirdSyntaxScriptPreProcessor instance_ = new IEWeirdSyntaxScriptPreProcessor();
/* 13:33 */   private static final Pattern patternFinally_ = Pattern.compile("(\\}(?:\\s*(?://.*\\n)?)*);((?:\\s*(?://.*\\n)?)*finally)");
/* 14:35 */   private static final Pattern patternCatch_ = Pattern.compile("(\\}(?:\\s*(?://.*\\n)?)*);((?:\\s*(?://.*\\n)?)*catch)");
/* 15:   */   
/* 16:   */   public static IEWeirdSyntaxScriptPreProcessor getInstance()
/* 17:   */   {
/* 18:43 */     return instance_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String preProcess(HtmlPage htmlPage, String sourceCode, String sourceName, int lineNumber, HtmlElement htmlElement)
/* 22:   */   {
/* 23:52 */     if (sourceCode.contains("catch")) {
/* 24:53 */       sourceCode = patternCatch_.matcher(sourceCode).replaceAll("$1 $2");
/* 25:   */     }
/* 26:55 */     if (sourceCode.contains("finally")) {
/* 27:56 */       sourceCode = patternFinally_.matcher(sourceCode).replaceAll("$1 $2");
/* 28:   */     }
/* 29:58 */     return sourceCode;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.IEWeirdSyntaxScriptPreProcessor
 * JD-Core Version:    0.7.0.1
 */