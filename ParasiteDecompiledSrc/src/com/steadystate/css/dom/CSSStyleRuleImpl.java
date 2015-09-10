/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import org.w3c.css.sac.CSSException;
/*   8:    */ import org.w3c.css.sac.InputSource;
/*   9:    */ import org.w3c.css.sac.SelectorList;
/*  10:    */ import org.w3c.dom.DOMException;
/*  11:    */ import org.w3c.dom.css.CSSRule;
/*  12:    */ import org.w3c.dom.css.CSSStyleDeclaration;
/*  13:    */ import org.w3c.dom.css.CSSStyleRule;
/*  14:    */ 
/*  15:    */ public class CSSStyleRuleImpl
/*  16:    */   extends AbstractCSSRuleImpl
/*  17:    */   implements CSSStyleRule, Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = -697009251364657426L;
/*  20: 57 */   private SelectorList selectors = null;
/*  21: 58 */   private CSSStyleDeclaration style = null;
/*  22:    */   
/*  23:    */   public SelectorList getSelectors()
/*  24:    */   {
/*  25: 62 */     return this.selectors;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setSelectors(SelectorList selectors)
/*  29:    */   {
/*  30: 67 */     this.selectors = selectors;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CSSStyleRuleImpl(CSSStyleSheetImpl parentStyleSheet, CSSRule parentRule, SelectorList selectors)
/*  34:    */   {
/*  35: 73 */     super(parentStyleSheet, parentRule);
/*  36: 74 */     this.selectors = selectors;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public CSSStyleRuleImpl() {}
/*  40:    */   
/*  41:    */   public short getType()
/*  42:    */   {
/*  43: 83 */     return 1;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getCssText()
/*  47:    */   {
/*  48: 87 */     return getSelectorText() + " { " + getStyle().getCssText() + " }";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setCssText(String cssText)
/*  52:    */     throws DOMException
/*  53:    */   {
/*  54: 91 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/*  55: 92 */       throw new DOMExceptionImpl((short)7, 2);
/*  56:    */     }
/*  57:    */     try
/*  58:    */     {
/*  59: 98 */       InputSource is = new InputSource(new StringReader(cssText));
/*  60: 99 */       CSSOMParser parser = new CSSOMParser();
/*  61:100 */       CSSRule r = parser.parseRule(is);
/*  62:103 */       if (r.getType() == 1)
/*  63:    */       {
/*  64:104 */         this.selectors = ((CSSStyleRuleImpl)r).selectors;
/*  65:105 */         this.style = ((CSSStyleRuleImpl)r).style;
/*  66:    */       }
/*  67:    */       else
/*  68:    */       {
/*  69:107 */         throw new DOMExceptionImpl((short)13, 4);
/*  70:    */       }
/*  71:    */     }
/*  72:    */     catch (CSSException e)
/*  73:    */     {
/*  74:112 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  75:    */     }
/*  76:    */     catch (IOException e)
/*  77:    */     {
/*  78:117 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getSelectorText()
/*  83:    */   {
/*  84:125 */     return this.selectors.toString();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setSelectorText(String selectorText)
/*  88:    */     throws DOMException
/*  89:    */   {
/*  90:129 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/*  91:130 */       throw new DOMExceptionImpl((short)7, 2);
/*  92:    */     }
/*  93:    */     try
/*  94:    */     {
/*  95:136 */       InputSource is = new InputSource(new StringReader(selectorText));
/*  96:137 */       CSSOMParser parser = new CSSOMParser();
/*  97:138 */       this.selectors = parser.parseSelectors(is);
/*  98:    */     }
/*  99:    */     catch (CSSException e)
/* 100:    */     {
/* 101:140 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/* 102:    */     }
/* 103:    */     catch (IOException e)
/* 104:    */     {
/* 105:145 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public CSSStyleDeclaration getStyle()
/* 110:    */   {
/* 111:153 */     return this.style;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setStyle(CSSStyleDeclaration style)
/* 115:    */   {
/* 116:157 */     this.style = style;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String toString()
/* 120:    */   {
/* 121:161 */     return getCssText();
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSStyleRuleImpl
 * JD-Core Version:    0.7.0.1
 */