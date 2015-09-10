/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*  2:   */ 
/*  3:   */ public class CSSMediaRule
/*  4:   */   extends CSSRule
/*  5:   */ {
/*  6:   */   private com.gargoylesoftware.htmlunit.javascript.host.MediaList media_;
/*  7:   */   
/*  8:   */   @Deprecated
/*  9:   */   public CSSMediaRule() {}
/* 10:   */   
/* 11:   */   protected CSSMediaRule(CSSStyleSheet stylesheet, org.w3c.dom.css.CSSMediaRule rule)
/* 12:   */   {
/* 13:43 */     super(stylesheet, rule);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public com.gargoylesoftware.htmlunit.javascript.host.MediaList jsxGet_media()
/* 17:   */   {
/* 18:51 */     if (this.media_ == null)
/* 19:   */     {
/* 20:52 */       CSSStyleSheet parent = jsxGet_parentStyleSheet();
/* 21:53 */       org.w3c.dom.stylesheets.MediaList ml = getMediaRule().getMedia();
/* 22:54 */       this.media_ = new com.gargoylesoftware.htmlunit.javascript.host.MediaList(parent, ml);
/* 23:   */     }
/* 24:56 */     return this.media_;
/* 25:   */   }
/* 26:   */   
/* 27:   */   private org.w3c.dom.css.CSSMediaRule getMediaRule()
/* 28:   */   {
/* 29:64 */     return (org.w3c.dom.css.CSSMediaRule)getRule();
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSMediaRule
 * JD-Core Version:    0.7.0.1
 */