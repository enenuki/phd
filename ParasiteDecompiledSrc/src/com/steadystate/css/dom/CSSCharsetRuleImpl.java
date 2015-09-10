/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import org.w3c.css.sac.CSSException;
/*   8:    */ import org.w3c.css.sac.InputSource;
/*   9:    */ import org.w3c.dom.DOMException;
/*  10:    */ import org.w3c.dom.css.CSSCharsetRule;
/*  11:    */ import org.w3c.dom.css.CSSRule;
/*  12:    */ 
/*  13:    */ public class CSSCharsetRuleImpl
/*  14:    */   extends AbstractCSSRuleImpl
/*  15:    */   implements CSSCharsetRule, Serializable
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = -2472209213089007127L;
/*  18: 54 */   String encoding = null;
/*  19:    */   
/*  20:    */   public CSSCharsetRuleImpl(CSSStyleSheetImpl parentStyleSheet, CSSRule parentRule, String encoding)
/*  21:    */   {
/*  22: 60 */     super(parentStyleSheet, parentRule);
/*  23: 61 */     this.encoding = encoding;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public CSSCharsetRuleImpl() {}
/*  27:    */   
/*  28:    */   public short getType()
/*  29:    */   {
/*  30: 70 */     return 2;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getCssText()
/*  34:    */   {
/*  35: 74 */     return "@charset \"" + getEncoding() + "\";";
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setCssText(String cssText)
/*  39:    */     throws DOMException
/*  40:    */   {
/*  41: 78 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/*  42: 79 */       throw new DOMExceptionImpl((short)7, 2);
/*  43:    */     }
/*  44:    */     try
/*  45:    */     {
/*  46: 85 */       InputSource is = new InputSource(new StringReader(cssText));
/*  47: 86 */       CSSOMParser parser = new CSSOMParser();
/*  48: 87 */       CSSRule r = parser.parseRule(is);
/*  49: 90 */       if (r.getType() == 2) {
/*  50: 91 */         this.encoding = ((CSSCharsetRuleImpl)r).encoding;
/*  51:    */       } else {
/*  52: 93 */         throw new DOMExceptionImpl((short)13, 5);
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (CSSException e)
/*  56:    */     {
/*  57: 98 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  58:    */     }
/*  59:    */     catch (IOException e)
/*  60:    */     {
/*  61:103 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getEncoding()
/*  66:    */   {
/*  67:111 */     return this.encoding;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setEncoding(String encoding)
/*  71:    */     throws DOMException
/*  72:    */   {
/*  73:115 */     this.encoding = encoding;
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSCharsetRuleImpl
 * JD-Core Version:    0.7.0.1
 */