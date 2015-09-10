/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import java.util.regex.Matcher;
/*  6:   */ import java.util.regex.Pattern;
/*  7:   */ 
/*  8:   */ public class CSSStyleRule
/*  9:   */   extends CSSRule
/* 10:   */ {
/* 11:30 */   private static final Pattern SELECTOR_PARTS_PATTERN = Pattern.compile("[\\.#]?[a-zA-Z]+");
/* 12:31 */   private static final Pattern SELECTOR_REPLACE_PATTERN = Pattern.compile("\\*([\\.#])");
/* 13:   */   
/* 14:   */   @Deprecated
/* 15:   */   public CSSStyleRule() {}
/* 16:   */   
/* 17:   */   protected CSSStyleRule(CSSStyleSheet stylesheet, org.w3c.dom.css.CSSRule rule)
/* 18:   */   {
/* 19:46 */     super(stylesheet, rule);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String jsxGet_selectorText()
/* 23:   */   {
/* 24:54 */     String selectorText = ((org.w3c.dom.css.CSSStyleRule)getRule()).getSelectorText();
/* 25:55 */     Matcher m = SELECTOR_PARTS_PATTERN.matcher(selectorText);
/* 26:56 */     StringBuffer sb = new StringBuffer();
/* 27:57 */     while (m.find())
/* 28:   */     {
/* 29:58 */       String fixedName = m.group();
/* 30:60 */       if ((fixedName.length() <= 0) || (('.' != fixedName.charAt(0)) && ('#' != fixedName.charAt(0)))) {
/* 31:64 */         if (getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_SELECTOR_TEXT_UPPERCASE)) {
/* 32:65 */           fixedName = fixedName.toUpperCase();
/* 33:   */         } else {
/* 34:68 */           fixedName = fixedName.toLowerCase();
/* 35:   */         }
/* 36:   */       }
/* 37:70 */       m.appendReplacement(sb, fixedName);
/* 38:   */     }
/* 39:72 */     m.appendTail(sb);
/* 40:   */     
/* 41:   */ 
/* 42:75 */     selectorText = SELECTOR_REPLACE_PATTERN.matcher(sb.toString()).replaceAll("$1");
/* 43:76 */     return selectorText;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void jsxSet_selectorText(String selectorText)
/* 47:   */   {
/* 48:84 */     ((org.w3c.dom.css.CSSStyleRule)getRule()).setSelectorText(selectorText);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public CSSStyleDeclaration jsxGet_style()
/* 52:   */   {
/* 53:92 */     return new CSSStyleDeclaration(getParentScope(), ((org.w3c.dom.css.CSSStyleRule)getRule()).getStyle());
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.CSSStyleRule
 * JD-Core Version:    0.7.0.1
 */