/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.parser.CSSOMParser;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import org.w3c.css.sac.CSSException;
/*   8:    */ import org.w3c.css.sac.InputSource;
/*   9:    */ import org.w3c.dom.DOMException;
/*  10:    */ import org.w3c.dom.css.CSSMediaRule;
/*  11:    */ import org.w3c.dom.css.CSSRule;
/*  12:    */ import org.w3c.dom.css.CSSRuleList;
/*  13:    */ import org.w3c.dom.stylesheets.MediaList;
/*  14:    */ 
/*  15:    */ public class CSSMediaRuleImpl
/*  16:    */   extends AbstractCSSRuleImpl
/*  17:    */   implements CSSMediaRule, Serializable
/*  18:    */ {
/*  19:    */   private static final long serialVersionUID = 6603734096445214651L;
/*  20: 57 */   private MediaList media = null;
/*  21: 58 */   private CSSRuleList cssRules = null;
/*  22:    */   
/*  23:    */   public void setMedia(MediaList media)
/*  24:    */   {
/*  25: 62 */     this.media = media;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setCssRules(CSSRuleList cssRules)
/*  29:    */   {
/*  30: 67 */     this.cssRules = cssRules;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public CSSMediaRuleImpl(CSSStyleSheetImpl parentStyleSheet, CSSRule parentRule, MediaList media)
/*  34:    */   {
/*  35: 75 */     super(parentStyleSheet, parentRule);
/*  36: 76 */     this.media = media;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public CSSMediaRuleImpl() {}
/*  40:    */   
/*  41:    */   public short getType()
/*  42:    */   {
/*  43: 85 */     return 4;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getCssText()
/*  47:    */   {
/*  48: 89 */     StringBuffer sb = new StringBuffer("@media ");
/*  49: 90 */     sb.append(getMedia().toString()).append(" {");
/*  50: 91 */     for (int i = 0; i < getCssRules().getLength(); i++)
/*  51:    */     {
/*  52: 92 */       CSSRule rule = getCssRules().item(i);
/*  53: 93 */       sb.append(rule.getCssText()).append(" ");
/*  54:    */     }
/*  55: 95 */     sb.append("}");
/*  56: 96 */     return sb.toString();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setCssText(String cssText)
/*  60:    */     throws DOMException
/*  61:    */   {
/*  62:100 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/*  63:101 */       throw new DOMExceptionImpl((short)7, 2);
/*  64:    */     }
/*  65:    */     try
/*  66:    */     {
/*  67:107 */       InputSource is = new InputSource(new StringReader(cssText));
/*  68:108 */       CSSOMParser parser = new CSSOMParser();
/*  69:109 */       CSSRule r = parser.parseRule(is);
/*  70:112 */       if (r.getType() == 4)
/*  71:    */       {
/*  72:113 */         this.media = ((CSSMediaRuleImpl)r).media;
/*  73:114 */         this.cssRules = ((CSSMediaRuleImpl)r).cssRules;
/*  74:    */       }
/*  75:    */       else
/*  76:    */       {
/*  77:116 */         throw new DOMExceptionImpl((short)13, 7);
/*  78:    */       }
/*  79:    */     }
/*  80:    */     catch (CSSException e)
/*  81:    */     {
/*  82:121 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  83:    */     }
/*  84:    */     catch (IOException e)
/*  85:    */     {
/*  86:126 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public MediaList getMedia()
/*  91:    */   {
/*  92:134 */     return this.media;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public CSSRuleList getCssRules()
/*  96:    */   {
/*  97:138 */     if (this.cssRules == null) {
/*  98:140 */       this.cssRules = new CSSRuleListImpl();
/*  99:    */     }
/* 100:142 */     return this.cssRules;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int insertRule(String rule, int index)
/* 104:    */     throws DOMException
/* 105:    */   {
/* 106:146 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/* 107:147 */       throw new DOMExceptionImpl((short)7, 2);
/* 108:    */     }
/* 109:    */     try
/* 110:    */     {
/* 111:153 */       InputSource is = new InputSource(new StringReader(rule));
/* 112:154 */       CSSOMParser parser = new CSSOMParser();
/* 113:155 */       parser.setParentStyleSheet(this.parentStyleSheet);
/* 114:    */       
/* 115:    */ 
/* 116:158 */       CSSRule r = parser.parseRule(is);
/* 117:    */       
/* 118:    */ 
/* 119:161 */       ((CSSRuleListImpl)getCssRules()).insert(r, index);
/* 120:    */     }
/* 121:    */     catch (ArrayIndexOutOfBoundsException e)
/* 122:    */     {
/* 123:164 */       throw new DOMExceptionImpl((short)1, 1, e.getMessage());
/* 124:    */     }
/* 125:    */     catch (CSSException e)
/* 126:    */     {
/* 127:169 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/* 128:    */     }
/* 129:    */     catch (IOException e)
/* 130:    */     {
/* 131:174 */       throw new DOMExceptionImpl((short)12, 0, e.getMessage());
/* 132:    */     }
/* 133:179 */     return index;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void deleteRule(int index)
/* 137:    */     throws DOMException
/* 138:    */   {
/* 139:183 */     if ((this.parentStyleSheet != null) && (this.parentStyleSheet.isReadOnly())) {
/* 140:184 */       throw new DOMExceptionImpl((short)7, 2);
/* 141:    */     }
/* 142:    */     try
/* 143:    */     {
/* 144:189 */       ((CSSRuleListImpl)getCssRules()).delete(index);
/* 145:    */     }
/* 146:    */     catch (ArrayIndexOutOfBoundsException e)
/* 147:    */     {
/* 148:191 */       throw new DOMExceptionImpl((short)1, 1, e.getMessage());
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setRuleList(CSSRuleListImpl rules)
/* 153:    */   {
/* 154:199 */     this.cssRules = rules;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String toString()
/* 158:    */   {
/* 159:203 */     return getCssText();
/* 160:    */   }
/* 161:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSMediaRuleImpl
 * JD-Core Version:    0.7.0.1
 */