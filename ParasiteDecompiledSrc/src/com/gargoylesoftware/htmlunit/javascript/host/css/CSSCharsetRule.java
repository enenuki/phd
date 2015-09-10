/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*  2:   */ 
/*  3:   */ public class CSSCharsetRule
/*  4:   */   extends CSSRule
/*  5:   */ {
/*  6:   */   @Deprecated
/*  7:   */   public CSSCharsetRule() {}
/*  8:   */   
/*  9:   */   protected CSSCharsetRule(CSSStyleSheet stylesheet, org.w3c.dom.css.CSSCharsetRule rule)
/* 10:   */   {
/* 11:39 */     super(stylesheet, rule);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String jsxGet_encoding()
/* 15:   */   {
/* 16:47 */     return getCharsetRule().getEncoding();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void jsxSet_encoding(String encoding)
/* 20:   */   {
/* 21:55 */     getCharsetRule().setEncoding(encoding);
/* 22:   */   }
/* 23:   */   
/* 24:   */   private org.w3c.dom.css.CSSCharsetRule getCharsetRule()
/* 25:   */   {
/* 26:63 */     return (org.w3c.dom.css.CSSCharsetRule)getRule();
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSCharsetRule
 * JD-Core Version:    0.7.0.1
 */