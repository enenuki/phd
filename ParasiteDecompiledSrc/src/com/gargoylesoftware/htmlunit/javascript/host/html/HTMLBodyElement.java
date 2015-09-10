/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.host.css.ComputedCSSStyleDeclaration;
/*   9:    */ import java.net.MalformedURLException;
/*  10:    */ import java.net.URL;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  12:    */ 
/*  13:    */ public class HTMLBodyElement
/*  14:    */   extends HTMLElement
/*  15:    */ {
/*  16:    */   public void createEventHandlerFromAttribute(String attributeName, String value)
/*  17:    */   {
/*  18: 53 */     if (attributeName.toLowerCase().startsWith("on")) {
/*  19: 54 */       createEventHandler(attributeName, value);
/*  20:    */     }
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setDefaults(ComputedCSSStyleDeclaration style)
/*  24:    */   {
/*  25: 63 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_41))
/*  26:    */     {
/*  27: 64 */       style.setDefaultLocalStyleAttribute("margin", "15px 10px");
/*  28: 65 */       style.setDefaultLocalStyleAttribute("padding", "0px");
/*  29:    */     }
/*  30:    */     else
/*  31:    */     {
/*  32: 68 */       style.setDefaultLocalStyleAttribute("margin-left", "8px");
/*  33: 69 */       style.setDefaultLocalStyleAttribute("margin-right", "8px");
/*  34: 70 */       style.setDefaultLocalStyleAttribute("margin-top", "8px");
/*  35: 71 */       style.setDefaultLocalStyleAttribute("margin-bottom", "8px");
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public HTMLElement jsxGet_offsetParent()
/*  40:    */   {
/*  41: 80 */     return null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String jsxGet_aLink()
/*  45:    */   {
/*  46: 89 */     String aLink = getDomNodeOrDie().getAttribute("aLink");
/*  47: 90 */     if ((aLink == DomElement.ATTRIBUTE_NOT_DEFINED) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.HTML_BODY_COLOR))) {
/*  48: 92 */       aLink = "#ee0000";
/*  49:    */     }
/*  50: 94 */     return aLink;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void jsxSet_aLink(String aLink)
/*  54:    */   {
/*  55:103 */     setColorAttribute("aLink", aLink);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String jsxGet_background()
/*  59:    */   {
/*  60:112 */     HtmlElement node = getDomNodeOrDie();
/*  61:113 */     String background = node.getAttribute("background");
/*  62:114 */     if ((background != DomElement.ATTRIBUTE_NOT_DEFINED) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_158))) {
/*  63:    */       try
/*  64:    */       {
/*  65:117 */         HtmlPage page = (HtmlPage)node.getPage();
/*  66:118 */         background = page.getFullyQualifiedUrl(background).toExternalForm();
/*  67:    */       }
/*  68:    */       catch (MalformedURLException e)
/*  69:    */       {
/*  70:121 */         Context.throwAsScriptRuntimeEx(e);
/*  71:    */       }
/*  72:    */     }
/*  73:124 */     return background;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void jsxSet_background(String background)
/*  77:    */   {
/*  78:133 */     getDomNodeOrDie().setAttribute("background", background);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String jsxGet_bgColor()
/*  82:    */   {
/*  83:142 */     String bgColor = getDomNodeOrDie().getAttribute("bgColor");
/*  84:143 */     if ((bgColor == DomElement.ATTRIBUTE_NOT_DEFINED) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.HTML_BODY_COLOR))) {
/*  85:145 */       bgColor = "#ffffff";
/*  86:    */     }
/*  87:147 */     return bgColor;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void jsxSet_bgColor(String bgColor)
/*  91:    */   {
/*  92:156 */     setColorAttribute("bgColor", bgColor);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String jsxGet_link()
/*  96:    */   {
/*  97:165 */     String link = getDomNodeOrDie().getAttribute("link");
/*  98:166 */     if ((link == DomElement.ATTRIBUTE_NOT_DEFINED) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.HTML_BODY_COLOR))) {
/*  99:168 */       link = "#0000ee";
/* 100:    */     }
/* 101:170 */     return link;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void jsxSet_link(String link)
/* 105:    */   {
/* 106:179 */     setColorAttribute("link", link);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String jsxGet_text()
/* 110:    */   {
/* 111:189 */     String text = getDomNodeOrDie().getAttribute("text");
/* 112:190 */     if ((text == DomElement.ATTRIBUTE_NOT_DEFINED) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.HTML_BODY_COLOR))) {
/* 113:192 */       text = "#000000";
/* 114:    */     }
/* 115:194 */     return text;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void jsxSet_text(String text)
/* 119:    */   {
/* 120:203 */     setColorAttribute("text", text);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String jsxGet_vLink()
/* 124:    */   {
/* 125:212 */     String vLink = getDomNodeOrDie().getAttribute("vLink");
/* 126:213 */     if ((vLink == DomElement.ATTRIBUTE_NOT_DEFINED) && (getBrowserVersion().hasFeature(BrowserVersionFeatures.HTML_BODY_COLOR))) {
/* 127:215 */       vLink = "#551a8b";
/* 128:    */     }
/* 129:217 */     return vLink;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void jsxSet_vLink(String vLink)
/* 133:    */   {
/* 134:226 */     setColorAttribute("vLink", vLink);
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLBodyElement
 * JD-Core Version:    0.7.0.1
 */