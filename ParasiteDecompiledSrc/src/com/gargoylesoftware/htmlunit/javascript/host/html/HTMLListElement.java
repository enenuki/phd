/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  7:   */ 
/*  8:   */ public class HTMLListElement
/*  9:   */   extends HTMLElement
/* 10:   */ {
/* 11:   */   public boolean jsxGet_compact()
/* 12:   */   {
/* 13:34 */     return getDomNodeOrDie().hasAttribute("compact");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void jsxSet_compact(Object compact)
/* 17:   */   {
/* 18:42 */     if (Context.toBoolean(compact)) {
/* 19:43 */       getDomNodeOrDie().setAttribute("compact", "");
/* 20:   */     } else {
/* 21:46 */       getDomNodeOrDie().removeAttribute("compact");
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Object jsxFunction_getAttribute(String attributeName, Integer flags)
/* 26:   */   {
/* 27:55 */     if (("compact".equals(attributeName)) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_85))) {
/* 28:56 */       return Boolean.valueOf(jsxGet_compact());
/* 29:   */     }
/* 30:58 */     return super.jsxFunction_getAttribute(attributeName, flags);
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLListElement
 * JD-Core Version:    0.7.0.1
 */