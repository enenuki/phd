/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import org.w3c.css.sac.CSSException;
/*   8:    */ import org.w3c.css.sac.InputSource;
/*   9:    */ import org.w3c.dom.DOMException;
/*  10:    */ import org.w3c.dom.css.CSSFontFaceRule;
/*  11:    */ import org.w3c.dom.css.CSSRule;
/*  12:    */ import org.w3c.dom.css.CSSStyleDeclaration;
/*  13:    */ 
/*  14:    */ public class CSSFontFaceRuleImpl
/*  15:    */   extends AbstractCSSRuleImpl
/*  16:    */   implements CSSFontFaceRule, Serializable
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -3604191834588759088L;
/*  19: 55 */   private CSSStyleDeclarationImpl style = null;
/*  20:    */   
/*  21:    */   public CSSFontFaceRuleImpl(CSSStyleSheetImpl parentStyleSheet, CSSRule parentRule)
/*  22:    */   {
/*  23: 58 */     super(parentStyleSheet, parentRule);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public CSSFontFaceRuleImpl() {}
/*  27:    */   
/*  28:    */   public short getType()
/*  29:    */   {
/*  30: 67 */     return 5;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getCssText()
/*  34:    */   {
/*  35: 71 */     return "@font-face {" + getStyle().getCssText() + "}";
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setCssText(String cssText)
/*  39:    */     throws DOMException
/*  40:    */   {
/*  41: 75 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/*  42: 76 */       throw new DOMExceptionImpl((short)7, 2);
/*  43:    */     }
/*  44:    */     try
/*  45:    */     {
/*  46: 82 */       InputSource is = new InputSource(new StringReader(cssText));
/*  47: 83 */       CSSOMParser parser = new CSSOMParser();
/*  48: 84 */       CSSRule r = parser.parseRule(is);
/*  49: 87 */       if (r.getType() == 5) {
/*  50: 88 */         this.style = ((CSSFontFaceRuleImpl)r).style;
/*  51:    */       } else {
/*  52: 90 */         throw new DOMExceptionImpl((short)13, 8);
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (CSSException e)
/*  56:    */     {
/*  57: 95 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  58:    */     }
/*  59:    */     catch (IOException e)
/*  60:    */     {
/*  61:100 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public CSSStyleDeclaration getStyle()
/*  66:    */   {
/*  67:108 */     return this.style;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setStyle(CSSStyleDeclarationImpl style)
/*  71:    */   {
/*  72:112 */     this.style = style;
/*  73:    */   }
/*  74:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSFontFaceRuleImpl
 * JD-Core Version:    0.7.0.1
 */