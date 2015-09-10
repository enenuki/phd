/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  6:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  7:   */ 
/*  8:   */ public class HTMLTableColElement
/*  9:   */   extends HTMLTableComponent
/* 10:   */ {
/* 11:   */   public int jsxGet_span()
/* 12:   */   {
/* 13:41 */     String span = getDomNodeOrDie().getAttribute("span");
/* 14:   */     int i;
/* 15:   */     try
/* 16:   */     {
/* 17:44 */       i = Integer.parseInt(span);
/* 18:45 */       if (i < 1) {
/* 19:46 */         i = 1;
/* 20:   */       }
/* 21:   */     }
/* 22:   */     catch (NumberFormatException e)
/* 23:   */     {
/* 24:50 */       i = 1;
/* 25:   */     }
/* 26:52 */     return i;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void jsxSet_span(Object span)
/* 30:   */   {
/* 31:60 */     double d = Context.toNumber(span);
/* 32:61 */     int i = (int)d;
/* 33:62 */     if (i < 1) {
/* 34:63 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_102))
/* 35:   */       {
/* 36:64 */         Exception e = new Exception("Cannot set the span property to invalid value: " + span);
/* 37:65 */         Context.throwAsScriptRuntimeEx(e);
/* 38:   */       }
/* 39:   */       else
/* 40:   */       {
/* 41:68 */         i = 1;
/* 42:   */       }
/* 43:   */     }
/* 44:71 */     getDomNodeOrDie().setAttribute("span", Integer.toString(i));
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String jsxGet_width()
/* 48:   */   {
/* 49:79 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_103);
/* 50:80 */     Boolean returnNegativeValues = ie ? Boolean.FALSE : null;
/* 51:81 */     return getWidthOrHeight("width", returnNegativeValues);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void jsxSet_width(Object width)
/* 55:   */   {
/* 56:89 */     setWidthOrHeight("width", width == null ? "" : Context.toString(width), Boolean.FALSE);
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTableColElement
 * JD-Core Version:    0.7.0.1
 */