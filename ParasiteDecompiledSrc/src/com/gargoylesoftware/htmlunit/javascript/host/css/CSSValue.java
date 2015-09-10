/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*  5:   */ 
/*  6:   */ public class CSSValue
/*  7:   */   extends SimpleScriptable
/*  8:   */ {
/*  9:   */   public static final short CSS_INHERIT = 0;
/* 10:   */   public static final short CSS_PRIMITIVE_VALUE = 1;
/* 11:   */   public static final short CSS_VALUE_LIST = 2;
/* 12:   */   public static final short CSS_CUSTOM = 3;
/* 13:   */   private org.w3c.dom.css.CSSValue wrappedCssValue_;
/* 14:   */   
/* 15:   */   public CSSValue() {}
/* 16:   */   
/* 17:   */   CSSValue(HTMLElement element, org.w3c.dom.css.CSSValue cssValue)
/* 18:   */   {
/* 19:72 */     setParentScope(element.getParentScope());
/* 20:73 */     setPrototype(getPrototype(getClass()));
/* 21:74 */     setDomNode(element.getDomNodeOrNull(), false);
/* 22:75 */     this.wrappedCssValue_ = cssValue;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String jsxGet_cssText()
/* 26:   */   {
/* 27:83 */     return this.wrappedCssValue_.getCssText();
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSValue
 * JD-Core Version:    0.7.0.1
 */