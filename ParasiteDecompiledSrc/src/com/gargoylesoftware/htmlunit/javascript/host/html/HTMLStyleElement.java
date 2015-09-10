/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.Cache;
/*  4:   */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  5:   */ import com.gargoylesoftware.htmlunit.WebClient;
/*  6:   */ import com.gargoylesoftware.htmlunit.WebRequest;
/*  7:   */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  8:   */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  9:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/* 10:   */ import com.gargoylesoftware.htmlunit.html.HtmlStyle;
/* 11:   */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/* 12:   */ import java.io.StringReader;
/* 13:   */ import java.net.URL;
/* 14:   */ import org.w3c.css.sac.InputSource;
/* 15:   */ 
/* 16:   */ public class HTMLStyleElement
/* 17:   */   extends HTMLElement
/* 18:   */ {
/* 19:   */   private com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet sheet_;
/* 20:   */   
/* 21:   */   public com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet jsxGet_sheet()
/* 22:   */   {
/* 23:49 */     if (this.sheet_ != null) {
/* 24:50 */       return this.sheet_;
/* 25:   */     }
/* 26:53 */     HtmlStyle style = (HtmlStyle)getDomNodeOrDie();
/* 27:54 */     String css = style.getTextContent();
/* 28:   */     
/* 29:56 */     Cache cache = getWindow().getWebWindow().getWebClient().getCache();
/* 30:57 */     org.w3c.dom.css.CSSStyleSheet cached = cache.getCachedStyleSheet(css);
/* 31:58 */     String uri = getDomNodeOrDie().getPage().getWebResponse().getWebRequest().getUrl().toExternalForm();
/* 32:60 */     if (cached != null)
/* 33:   */     {
/* 34:61 */       this.sheet_ = new com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet(this, cached, uri);
/* 35:   */     }
/* 36:   */     else
/* 37:   */     {
/* 38:64 */       InputSource source = new InputSource(new StringReader(css));
/* 39:65 */       this.sheet_ = new com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet(this, source, uri);
/* 40:66 */       cache.cache(css, this.sheet_.getWrappedSheet());
/* 41:   */     }
/* 42:69 */     return this.sheet_;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleSheet jsxGet_styleSheet()
/* 46:   */   {
/* 47:77 */     return jsxGet_sheet();
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLStyleElement
 * JD-Core Version:    0.7.0.1
 */