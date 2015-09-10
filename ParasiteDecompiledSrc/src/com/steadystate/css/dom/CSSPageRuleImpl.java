/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import org.w3c.css.sac.CSSException;
/*   8:    */ import org.w3c.css.sac.InputSource;
/*   9:    */ import org.w3c.dom.DOMException;
/*  10:    */ import org.w3c.dom.css.CSSPageRule;
/*  11:    */ import org.w3c.dom.css.CSSRule;
/*  12:    */ import org.w3c.dom.css.CSSStyleDeclaration;
/*  13:    */ 
/*  14:    */ public class CSSPageRuleImpl
/*  15:    */   extends AbstractCSSRuleImpl
/*  16:    */   implements CSSPageRule, Serializable
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -6007519872104320812L;
/*  19: 57 */   private String ident = null;
/*  20: 58 */   private String pseudoPage = null;
/*  21: 59 */   private CSSStyleDeclaration style = null;
/*  22:    */   
/*  23:    */   public CSSPageRuleImpl(CSSStyleSheetImpl parentStyleSheet, CSSRule parentRule, String ident, String pseudoPage)
/*  24:    */   {
/*  25: 66 */     super(parentStyleSheet, parentRule);
/*  26: 67 */     this.ident = ident;
/*  27: 68 */     this.pseudoPage = pseudoPage;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public CSSPageRuleImpl() {}
/*  31:    */   
/*  32:    */   public short getType()
/*  33:    */   {
/*  34: 77 */     return 6;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getCssText()
/*  38:    */   {
/*  39: 81 */     String sel = getSelectorText();
/*  40: 82 */     return "@page {" + sel + (sel.length() > 0 ? " " : "") + getStyle().getCssText() + "}";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setCssText(String cssText)
/*  44:    */     throws DOMException
/*  45:    */   {
/*  46: 89 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/*  47: 90 */       throw new DOMExceptionImpl((short)7, 2);
/*  48:    */     }
/*  49:    */     try
/*  50:    */     {
/*  51: 96 */       InputSource is = new InputSource(new StringReader(cssText));
/*  52: 97 */       CSSOMParser parser = new CSSOMParser();
/*  53: 98 */       CSSRule r = parser.parseRule(is);
/*  54:101 */       if (r.getType() == 6)
/*  55:    */       {
/*  56:102 */         this.ident = ((CSSPageRuleImpl)r).ident;
/*  57:103 */         this.pseudoPage = ((CSSPageRuleImpl)r).pseudoPage;
/*  58:104 */         this.style = ((CSSPageRuleImpl)r).style;
/*  59:    */       }
/*  60:    */       else
/*  61:    */       {
/*  62:106 */         throw new DOMExceptionImpl((short)13, 9);
/*  63:    */       }
/*  64:    */     }
/*  65:    */     catch (CSSException e)
/*  66:    */     {
/*  67:111 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  68:    */     }
/*  69:    */     catch (IOException e)
/*  70:    */     {
/*  71:116 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getSelectorText()
/*  76:    */   {
/*  77:124 */     return (this.ident != null ? this.ident : "") + (this.pseudoPage != null ? ":" + this.pseudoPage : "");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setSelectorText(String selectorText)
/*  81:    */     throws DOMException
/*  82:    */   {}
/*  83:    */   
/*  84:    */   public CSSStyleDeclaration getStyle()
/*  85:    */   {
/*  86:132 */     return this.style;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdent(String ident)
/*  90:    */   {
/*  91:136 */     this.ident = ident;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setPseudoPage(String pseudoPage)
/*  95:    */   {
/*  96:140 */     this.pseudoPage = pseudoPage;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setStyle(CSSStyleDeclarationImpl style)
/* 100:    */   {
/* 101:144 */     this.style = style;
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSPageRuleImpl
 * JD-Core Version:    0.7.0.1
 */