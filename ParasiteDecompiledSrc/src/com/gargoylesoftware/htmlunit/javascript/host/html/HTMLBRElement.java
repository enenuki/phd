/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  7:   */ import org.apache.commons.lang.ArrayUtils;
/*  8:   */ 
/*  9:   */ public class HTMLBRElement
/* 10:   */   extends HTMLElement
/* 11:   */ {
/* 12:32 */   private static final String[] VALID_CLEAR_VALUES = { "left", "right", "all", "none" };
/* 13:   */   
/* 14:   */   public String jsxGet_clear()
/* 15:   */   {
/* 16:46 */     String clear = getDomNodeOrDie().getAttribute("clear");
/* 17:47 */     if ((!ArrayUtils.contains(VALID_CLEAR_VALUES, clear)) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_42))) {
/* 18:49 */       return "";
/* 19:   */     }
/* 20:51 */     return clear;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void jsxSet_clear(String clear)
/* 24:   */   {
/* 25:59 */     if ((!ArrayUtils.contains(VALID_CLEAR_VALUES, clear)) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_43))) {
/* 26:61 */       throw Context.reportRuntimeError("Invalid clear property value: '" + clear + "'.");
/* 27:   */     }
/* 28:63 */     getDomNodeOrDie().setAttribute("clear", clear);
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLBRElement
 * JD-Core Version:    0.7.0.1
 */