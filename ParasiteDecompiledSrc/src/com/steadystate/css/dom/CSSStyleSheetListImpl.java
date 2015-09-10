/*   1:    */ package com.steadystate.css.dom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.w3c.dom.css.CSSStyleSheet;
/*   7:    */ import org.w3c.dom.stylesheets.StyleSheet;
/*   8:    */ import org.w3c.dom.stylesheets.StyleSheetList;
/*   9:    */ 
/*  10:    */ public class CSSStyleSheetListImpl
/*  11:    */   implements StyleSheetList
/*  12:    */ {
/*  13:    */   private List<CSSStyleSheet> cssStyleSheets;
/*  14:    */   
/*  15:    */   public List<CSSStyleSheet> getCSSStyleSheets()
/*  16:    */   {
/*  17: 51 */     if (this.cssStyleSheets == null) {
/*  18: 53 */       this.cssStyleSheets = new ArrayList();
/*  19:    */     }
/*  20: 55 */     return this.cssStyleSheets;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setCSSStyleSheets(List<CSSStyleSheet> cssStyleSheets)
/*  24:    */   {
/*  25: 60 */     this.cssStyleSheets = cssStyleSheets;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getLength()
/*  29:    */   {
/*  30: 71 */     return getCSSStyleSheets().size();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public StyleSheet item(int index)
/*  34:    */   {
/*  35: 76 */     return (StyleSheet)getCSSStyleSheets().get(index);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void add(CSSStyleSheet cssStyleSheet)
/*  39:    */   {
/*  40: 86 */     getCSSStyleSheets().add(cssStyleSheet);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public StyleSheet merge()
/*  44:    */   {
/*  45: 97 */     CSSStyleSheetImpl merged = new CSSStyleSheetImpl();
/*  46: 98 */     CSSRuleListImpl cssRuleList = new CSSRuleListImpl();
/*  47: 99 */     Iterator it = getCSSStyleSheets().iterator();
/*  48:100 */     while (it.hasNext())
/*  49:    */     {
/*  50:102 */       CSSStyleSheetImpl cssStyleSheet = (CSSStyleSheetImpl)it.next();
/*  51:103 */       CSSMediaRuleImpl cssMediaRule = new CSSMediaRuleImpl(merged, null, cssStyleSheet.getMedia());
/*  52:    */       
/*  53:105 */       cssMediaRule.setRuleList((CSSRuleListImpl)cssStyleSheet.getCssRules());
/*  54:    */       
/*  55:107 */       cssRuleList.add(cssMediaRule);
/*  56:    */     }
/*  57:109 */     merged.setCssRules(cssRuleList);
/*  58:110 */     merged.setMediaText("all");
/*  59:111 */     return merged;
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.dom.CSSStyleSheetListImpl
 * JD-Core Version:    0.7.0.1
 */