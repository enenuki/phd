/*  1:   */ package com.gargoylesoftware.htmlunit.util;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.Page;
/*  4:   */ import com.gargoylesoftware.htmlunit.WebClient;
/*  5:   */ import com.gargoylesoftware.htmlunit.WebRequest;
/*  6:   */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  7:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  8:   */ import com.gargoylesoftware.htmlunit.javascript.HtmlUnitContextFactory;
/*  9:   */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/* 10:   */ import java.net.URL;
/* 11:   */ import net.sourceforge.htmlunit.corejs.javascript.debug.DebuggableScript;
/* 12:   */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;
/* 13:   */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.ScopeProvider;
/* 14:   */ import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.SourceProvider;
/* 15:   */ import org.apache.commons.lang.StringUtils;
/* 16:   */ 
/* 17:   */ public final class WebClientUtils
/* 18:   */ {
/* 19:   */   public static void attachVisualDebugger(WebClient client)
/* 20:   */   {
/* 21:50 */     ScopeProvider sp = null;
/* 22:51 */     HtmlUnitContextFactory cf = client.getJavaScriptEngine().getContextFactory();
/* 23:52 */     Main main = Main.mainEmbedded(cf, sp, "HtmlUnit JavaScript Debugger");
/* 24:   */     
/* 25:54 */     SourceProvider sourceProvider = new SourceProvider()
/* 26:   */     {
/* 27:   */       public String getSource(DebuggableScript script)
/* 28:   */       {
/* 29:56 */         String sourceName = script.getSourceName();
/* 30:57 */         if (sourceName.endsWith("(eval)")) {
/* 31:58 */           return null;
/* 32:   */         }
/* 33:60 */         if (sourceName.startsWith("script in "))
/* 34:   */         {
/* 35:61 */           sourceName = StringUtils.substringBetween(sourceName, "script in ", " from");
/* 36:62 */           for (WebWindow ww : this.val$client.getWebWindows())
/* 37:   */           {
/* 38:63 */             WebResponse wr = ww.getEnclosedPage().getWebResponse();
/* 39:64 */             if (sourceName.equals(wr.getWebRequest().getUrl().toString())) {
/* 40:65 */               return wr.getContentAsString();
/* 41:   */             }
/* 42:   */           }
/* 43:   */         }
/* 44:69 */         return null;
/* 45:   */       }
/* 46:71 */     };
/* 47:72 */     main.setSourceProvider(sourceProvider);
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.WebClientUtils
 * JD-Core Version:    0.7.0.1
 */