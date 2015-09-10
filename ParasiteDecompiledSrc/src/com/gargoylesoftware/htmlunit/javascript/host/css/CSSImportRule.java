/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*  4:   */ 
/*  5:   */ public class CSSImportRule
/*  6:   */   extends CSSRule
/*  7:   */ {
/*  8:   */   private com.gargoylesoftware.htmlunit.javascript.host.MediaList media_;
/*  9:   */   private CSSStyleSheet importedStylesheet_;
/* 10:   */   
/* 11:   */   @Deprecated
/* 12:   */   public CSSImportRule() {}
/* 13:   */   
/* 14:   */   protected CSSImportRule(CSSStyleSheet stylesheet, org.w3c.dom.css.CSSRule rule)
/* 15:   */   {
/* 16:45 */     super(stylesheet, rule);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String jsxGet_href()
/* 20:   */   {
/* 21:53 */     return getImportRule().getHref();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public com.gargoylesoftware.htmlunit.javascript.host.MediaList jsxGet_media()
/* 25:   */   {
/* 26:61 */     if (this.media_ == null)
/* 27:   */     {
/* 28:62 */       CSSStyleSheet parent = jsxGet_parentStyleSheet();
/* 29:63 */       org.w3c.dom.stylesheets.MediaList ml = getImportRule().getMedia();
/* 30:64 */       this.media_ = new com.gargoylesoftware.htmlunit.javascript.host.MediaList(parent, ml);
/* 31:   */     }
/* 32:66 */     return this.media_;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public CSSStyleSheet jsxGet_styleSheet()
/* 36:   */   {
/* 37:74 */     if (this.importedStylesheet_ == null)
/* 38:   */     {
/* 39:75 */       CSSStyleSheet owningSheet = jsxGet_parentStyleSheet();
/* 40:76 */       HTMLElement ownerNode = owningSheet.jsxGet_ownerNode();
/* 41:77 */       org.w3c.dom.css.CSSStyleSheet importedStylesheet = getImportRule().getStyleSheet();
/* 42:78 */       this.importedStylesheet_ = new CSSStyleSheet(ownerNode, importedStylesheet, owningSheet.getUri());
/* 43:   */     }
/* 44:80 */     return this.importedStylesheet_;
/* 45:   */   }
/* 46:   */   
/* 47:   */   private org.w3c.dom.css.CSSImportRule getImportRule()
/* 48:   */   {
/* 49:88 */     return (org.w3c.dom.css.CSSImportRule)getRule();
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSImportRule
 * JD-Core Version:    0.7.0.1
 */