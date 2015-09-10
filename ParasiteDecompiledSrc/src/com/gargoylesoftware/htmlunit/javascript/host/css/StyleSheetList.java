/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.css;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   4:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlAttributeChangeEvent;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlLink;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlStyle;
/*  10:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  11:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*  13:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection.EffectOnCache;
/*  14:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument;
/*  15:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*  16:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLLinkElement;
/*  17:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLStyleElement;
/*  18:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  19:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  20:    */ 
/*  21:    */ public class StyleSheetList
/*  22:    */   extends SimpleScriptable
/*  23:    */ {
/*  24:    */   private HTMLCollection nodes_;
/*  25:    */   
/*  26:    */   public StyleSheetList() {}
/*  27:    */   
/*  28:    */   public StyleSheetList(HTMLDocument document)
/*  29:    */   {
/*  30: 67 */     setParentScope(document);
/*  31: 68 */     setPrototype(getPrototype(getClass()));
/*  32:    */     
/*  33: 70 */     boolean cssEnabled = getWindow().getWebWindow().getWebClient().isCssEnabled();
/*  34: 71 */     if (cssEnabled) {
/*  35: 72 */       this.nodes_ = new HTMLCollection(document.getDomNodeOrDie(), true, "stylesheets")
/*  36:    */       {
/*  37:    */         protected boolean isMatching(DomNode node)
/*  38:    */         {
/*  39: 74 */           return ((node instanceof HtmlStyle)) || (((node instanceof HtmlLink)) && ("stylesheet".equalsIgnoreCase(((HtmlLink)node).getAttribute("rel"))));
/*  40:    */         }
/*  41:    */         
/*  42:    */         protected HTMLCollection.EffectOnCache getEffectOnCache(HtmlAttributeChangeEvent event)
/*  43:    */         {
/*  44: 81 */           HtmlElement node = event.getHtmlElement();
/*  45: 82 */           if (((node instanceof HtmlLink)) && ("rel".equalsIgnoreCase(event.getName()))) {
/*  46: 83 */             return HTMLCollection.EffectOnCache.RESET;
/*  47:    */           }
/*  48: 85 */           return HTMLCollection.EffectOnCache.NONE;
/*  49:    */         }
/*  50:    */       };
/*  51:    */     } else {
/*  52: 90 */       this.nodes_ = HTMLCollection.emptyCollection(getWindow());
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int jsxGet_length()
/*  57:    */   {
/*  58:100 */     return this.nodes_.jsxGet_length();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Object jsxFunction_item(int index)
/*  62:    */   {
/*  63:110 */     if (index < 0) {
/*  64:111 */       throw Context.reportRuntimeError("Invalid negative index: " + index);
/*  65:    */     }
/*  66:113 */     if (index >= this.nodes_.jsxGet_length()) {
/*  67:114 */       return Context.getUndefinedValue();
/*  68:    */     }
/*  69:117 */     HTMLElement element = (HTMLElement)this.nodes_.jsxFunction_item(Integer.valueOf(index));
/*  70:    */     CSSStyleSheet sheet;
/*  71:    */     CSSStyleSheet sheet;
/*  72:121 */     if ((element instanceof HTMLStyleElement)) {
/*  73:122 */       sheet = ((HTMLStyleElement)element).jsxGet_sheet();
/*  74:    */     } else {
/*  75:126 */       sheet = ((HTMLLinkElement)element).getSheet();
/*  76:    */     }
/*  77:129 */     return sheet;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Object get(int index, Scriptable start)
/*  81:    */   {
/*  82:137 */     if (this == start) {
/*  83:138 */       return jsxFunction_item(index);
/*  84:    */     }
/*  85:140 */     return super.get(index, start);
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.css.StyleSheetList
 * JD-Core Version:    0.7.0.1
 */