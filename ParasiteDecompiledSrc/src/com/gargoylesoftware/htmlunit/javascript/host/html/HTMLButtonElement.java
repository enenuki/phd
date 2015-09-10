/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlButton;
/*  6:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  7:   */ import com.gargoylesoftware.htmlunit.javascript.host.FormField;
/*  8:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  9:   */ 
/* 10:   */ public class HTMLButtonElement
/* 11:   */   extends FormField
/* 12:   */ {
/* 13:   */   public void jsxSet_type(String newType)
/* 14:   */   {
/* 15:46 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_44)) {
/* 16:47 */       throw Context.reportRuntimeError("Object doesn't support this action");
/* 17:   */     }
/* 18:49 */     getDomNodeOrDie().setAttribute("type", newType);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String jsxGet_type()
/* 22:   */   {
/* 23:57 */     return ((HtmlButton)getDomNodeOrDie()).getTypeAttribute();
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement
 * JD-Core Version:    0.7.0.1
 */