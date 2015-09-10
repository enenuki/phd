/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ 
/*   5:    */ public class CSSRule
/*   6:    */   extends SimpleScriptable
/*   7:    */ {
/*   8:    */   public static final short UNKNOWN_RULE = 0;
/*   9:    */   public static final short STYLE_RULE = 1;
/*  10:    */   public static final short CHARSET_RULE = 2;
/*  11:    */   public static final short IMPORT_RULE = 3;
/*  12:    */   public static final short MEDIA_RULE = 4;
/*  13:    */   public static final short FONT_FACE_RULE = 5;
/*  14:    */   public static final short PAGE_RULE = 6;
/*  15:    */   private final CSSStyleSheet stylesheet_;
/*  16:    */   private final org.w3c.dom.css.CSSRule rule_;
/*  17:    */   
/*  18:    */   @Deprecated
/*  19:    */   public CSSRule()
/*  20:    */   {
/*  21: 65 */     this.stylesheet_ = null;
/*  22: 66 */     this.rule_ = null;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static CSSRule create(CSSStyleSheet stylesheet, org.w3c.dom.css.CSSRule rule)
/*  26:    */   {
/*  27: 76 */     switch (rule.getType())
/*  28:    */     {
/*  29:    */     case 1: 
/*  30: 78 */       return new CSSStyleRule(stylesheet, rule);
/*  31:    */     case 3: 
/*  32: 80 */       return new CSSImportRule(stylesheet, rule);
/*  33:    */     case 2: 
/*  34: 82 */       return new CSSCharsetRule(stylesheet, (org.w3c.dom.css.CSSCharsetRule)rule);
/*  35:    */     case 4: 
/*  36: 84 */       return new CSSMediaRule(stylesheet, (org.w3c.dom.css.CSSMediaRule)rule);
/*  37:    */     }
/*  38: 86 */     throw new UnsupportedOperationException("CSSRule " + rule.getClass().getName() + " is not yet supported.");
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected CSSRule(CSSStyleSheet stylesheet, org.w3c.dom.css.CSSRule rule)
/*  42:    */   {
/*  43: 97 */     this.stylesheet_ = stylesheet;
/*  44: 98 */     this.rule_ = rule;
/*  45: 99 */     setParentScope(stylesheet);
/*  46:100 */     setPrototype(getPrototype(getClass()));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public short jsxGet_type()
/*  50:    */   {
/*  51:108 */     return this.rule_.getType();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String jsxGet_cssText()
/*  55:    */   {
/*  56:117 */     return this.rule_.getCssText();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void jsxSet_cssText(String cssText)
/*  60:    */   {
/*  61:125 */     this.rule_.setCssText(cssText);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public CSSStyleSheet jsxGet_parentStyleSheet()
/*  65:    */   {
/*  66:133 */     return this.stylesheet_;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CSSRule jsxGet_parentRule()
/*  70:    */   {
/*  71:142 */     org.w3c.dom.css.CSSRule parentRule = this.rule_.getParentRule();
/*  72:143 */     if (parentRule != null) {
/*  73:144 */       return create(this.stylesheet_, parentRule);
/*  74:    */     }
/*  75:146 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected org.w3c.dom.css.CSSRule getRule()
/*  79:    */   {
/*  80:154 */     return this.rule_;
/*  81:    */   }
/*  82:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSRule
 * JD-Core Version:    0.7.0.1
 */