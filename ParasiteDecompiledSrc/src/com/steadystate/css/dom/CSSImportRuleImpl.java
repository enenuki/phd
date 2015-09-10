/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import org.w3c.css.sac.CSSException;
/*   8:    */ import org.w3c.css.sac.InputSource;
/*   9:    */ import org.w3c.dom.DOMException;
/*  10:    */ import org.w3c.dom.css.CSSImportRule;
/*  11:    */ import org.w3c.dom.css.CSSRule;
/*  12:    */ import org.w3c.dom.css.CSSStyleSheet;
/*  13:    */ import org.w3c.dom.stylesheets.MediaList;
/*  14:    */ 
/*  15:    */ public class CSSImportRuleImpl
/*  16:    */   extends AbstractCSSRuleImpl
/*  17:    */   implements CSSImportRule, Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 7807829682009179339L;
/*  20: 59 */   String href = null;
/*  21: 60 */   MediaList media = null;
/*  22:    */   
/*  23:    */   public void setHref(String href)
/*  24:    */   {
/*  25: 64 */     this.href = href;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setMedia(MediaList media)
/*  29:    */   {
/*  30: 69 */     this.media = media;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CSSImportRuleImpl(CSSStyleSheetImpl parentStyleSheet, CSSRule parentRule, String href, MediaList media)
/*  34:    */   {
/*  35: 78 */     super(parentStyleSheet, parentRule);
/*  36: 79 */     this.href = href;
/*  37: 80 */     this.media = media;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public CSSImportRuleImpl() {}
/*  41:    */   
/*  42:    */   public short getType()
/*  43:    */   {
/*  44: 89 */     return 3;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getCssText()
/*  48:    */   {
/*  49: 93 */     StringBuilder sb = new StringBuilder();
/*  50: 94 */     sb.append("@import url(").append(getHref()).append(")");
/*  51: 97 */     if (getMedia().getLength() > 0) {
/*  52: 98 */       sb.append(" ").append(getMedia().toString());
/*  53:    */     }
/*  54:100 */     sb.append(";");
/*  55:101 */     return sb.toString();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setCssText(String cssText)
/*  59:    */     throws DOMException
/*  60:    */   {
/*  61:105 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/*  62:106 */       throw new DOMExceptionImpl((short)7, 2);
/*  63:    */     }
/*  64:    */     try
/*  65:    */     {
/*  66:112 */       InputSource is = new InputSource(new StringReader(cssText));
/*  67:113 */       CSSOMParser parser = new CSSOMParser();
/*  68:114 */       CSSRule r = parser.parseRule(is);
/*  69:117 */       if (r.getType() == 3)
/*  70:    */       {
/*  71:118 */         this.href = ((CSSImportRuleImpl)r).href;
/*  72:119 */         this.media = ((CSSImportRuleImpl)r).media;
/*  73:    */       }
/*  74:    */       else
/*  75:    */       {
/*  76:121 */         throw new DOMExceptionImpl((short)13, 6);
/*  77:    */       }
/*  78:    */     }
/*  79:    */     catch (CSSException e)
/*  80:    */     {
/*  81:126 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  82:    */     }
/*  83:    */     catch (IOException e)
/*  84:    */     {
/*  85:131 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getHref()
/*  90:    */   {
/*  91:139 */     return this.href;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public MediaList getMedia()
/*  95:    */   {
/*  96:143 */     return this.media;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public CSSStyleSheet getStyleSheet()
/* 100:    */   {
/* 101:147 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String toString()
/* 105:    */   {
/* 106:151 */     return getCssText();
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSImportRuleImpl
 * JD-Core Version:    0.7.0.1
 */