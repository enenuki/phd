/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.w3c.dom.DOMException;
/*   5:    */ import org.w3c.dom.css.CSSRule;
/*   6:    */ import org.w3c.dom.css.CSSStyleSheet;
/*   7:    */ import org.w3c.dom.css.CSSUnknownRule;
/*   8:    */ 
/*   9:    */ public class CSSUnknownRuleImpl
/*  10:    */   extends AbstractCSSRuleImpl
/*  11:    */   implements CSSUnknownRule, Serializable
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -268104019127675990L;
/*  14: 50 */   String text = null;
/*  15:    */   
/*  16:    */   public String getText()
/*  17:    */   {
/*  18: 54 */     return this.text;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void setText(String text)
/*  22:    */   {
/*  23: 59 */     this.text = text;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public CSSUnknownRuleImpl(CSSStyleSheetImpl parentStyleSheet, CSSRule parentRule, String text)
/*  27:    */   {
/*  28: 67 */     super(parentStyleSheet, parentRule);
/*  29: 68 */     this.text = text;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public CSSUnknownRuleImpl() {}
/*  33:    */   
/*  34:    */   public short getType()
/*  35:    */   {
/*  36: 77 */     return 0;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getCssText()
/*  40:    */   {
/*  41: 81 */     return this.text;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setCssText(String cssText)
/*  45:    */     throws DOMException
/*  46:    */   {}
/*  47:    */   
/*  48:    */   public CSSStyleSheet getParentStyleSheet()
/*  49:    */   {
/*  50:127 */     return this.parentStyleSheet;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public CSSRule getParentRule()
/*  54:    */   {
/*  55:131 */     return this.parentRule;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String toString()
/*  59:    */   {
/*  60:135 */     return getCssText();
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSUnknownRuleImpl
 * JD-Core Version:    0.7.0.1
 */