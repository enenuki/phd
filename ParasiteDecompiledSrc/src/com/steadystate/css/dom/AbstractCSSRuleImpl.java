/*  1:   */ package com.steadystate.css.dom;
/*  2:   */ 
/*  3:   */ import org.w3c.dom.css.CSSRule;
/*  4:   */ import org.w3c.dom.css.CSSStyleSheet;
/*  5:   */ 
/*  6:   */ public abstract class AbstractCSSRuleImpl
/*  7:   */   extends CSSOMObjectImpl
/*  8:   */ {
/*  9:16 */   protected CSSStyleSheetImpl parentStyleSheet = null;
/* 10:17 */   protected CSSRule parentRule = null;
/* 11:   */   
/* 12:   */   public void setParentStyleSheet(CSSStyleSheetImpl parentStyleSheet)
/* 13:   */   {
/* 14:21 */     this.parentStyleSheet = parentStyleSheet;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setParentRule(CSSRule parentRule)
/* 18:   */   {
/* 19:26 */     this.parentRule = parentRule;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public AbstractCSSRuleImpl(CSSStyleSheetImpl parentStyleSheet, CSSRule parentRule)
/* 23:   */   {
/* 24:34 */     this.parentStyleSheet = parentStyleSheet;
/* 25:35 */     this.parentRule = parentRule;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public AbstractCSSRuleImpl() {}
/* 29:   */   
/* 30:   */   public CSSStyleSheet getParentStyleSheet()
/* 31:   */   {
/* 32:44 */     return this.parentStyleSheet;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public CSSRule getParentRule()
/* 36:   */   {
/* 37:48 */     return this.parentRule;
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.AbstractCSSRuleImpl
 * JD-Core Version:    0.7.0.1
 */