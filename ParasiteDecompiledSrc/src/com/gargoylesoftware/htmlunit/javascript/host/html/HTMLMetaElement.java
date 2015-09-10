/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   4:    */ 
/*   5:    */ public class HTMLMetaElement
/*   6:    */   extends HTMLElement
/*   7:    */ {
/*   8:    */   public String jsxGet_charset()
/*   9:    */   {
/*  10: 37 */     return "";
/*  11:    */   }
/*  12:    */   
/*  13:    */   public void jsxSet_charset(String charset) {}
/*  14:    */   
/*  15:    */   public String jsxGet_content()
/*  16:    */   {
/*  17: 53 */     return getDomNodeOrDie().getAttribute("content");
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void jsxSet_content(String content)
/*  21:    */   {
/*  22: 61 */     getDomNodeOrDie().setAttribute("content", content);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public String jsxGet_httpEquiv()
/*  26:    */   {
/*  27: 69 */     return getDomNodeOrDie().getAttribute("http-equiv");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void jsxSet_httpEquiv(String httpEquiv)
/*  31:    */   {
/*  32: 77 */     getDomNodeOrDie().setAttribute("http-equiv", httpEquiv);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String jsxGet_name()
/*  36:    */   {
/*  37: 85 */     return getDomNodeOrDie().getAttribute("name");
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void jsxSet_name(String name)
/*  41:    */   {
/*  42: 93 */     getDomNodeOrDie().setAttribute("name", name);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String jsxGet_scheme()
/*  46:    */   {
/*  47:101 */     return getDomNodeOrDie().getAttribute("scheme");
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void jsxSet_scheme(String scheme)
/*  51:    */   {
/*  52:109 */     getDomNodeOrDie().setAttribute("scheme", scheme);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String jsxGet_url()
/*  56:    */   {
/*  57:117 */     return "";
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void jsxSet_url(String url) {}
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLMetaElement
 * JD-Core Version:    0.7.0.1
 */