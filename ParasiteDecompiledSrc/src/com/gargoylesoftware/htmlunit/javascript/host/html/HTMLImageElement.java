/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.Page;
/*   4:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HTMLParser;
/*   8:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.HtmlImage;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.IElementFactory;
/*  12:    */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*  13:    */ import com.gargoylesoftware.htmlunit.javascript.PostponedAction;
/*  14:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  15:    */ import java.net.MalformedURLException;
/*  16:    */ import java.net.URL;
/*  17:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  18:    */ import org.apache.xalan.xsltc.runtime.AttributeList;
/*  19:    */ 
/*  20:    */ public class HTMLImageElement
/*  21:    */   extends HTMLElement
/*  22:    */ {
/*  23: 43 */   private boolean instantiatedViaJavaScript_ = false;
/*  24:    */   
/*  25:    */   public void jsConstructor()
/*  26:    */   {
/*  27: 57 */     this.instantiatedViaJavaScript_ = true;
/*  28: 58 */     SgmlPage page = (SgmlPage)getWindow().getWebWindow().getEnclosedPage();
/*  29: 59 */     HtmlElement fake = HTMLParser.getFactory("img").createElement(page, "img", new AttributeList());
/*  30:    */     
/*  31: 61 */     setDomNode(fake);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void jsxSet_src(String src)
/*  35:    */   {
/*  36: 69 */     HtmlElement img = getDomNodeOrDie();
/*  37: 70 */     img.setAttribute("src", src);
/*  38: 71 */     getWindow().getWebWindow().getWebClient().getJavaScriptEngine().addPostponedAction(new ImageOnLoadAction(img.getPage()));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String jsxGet_src()
/*  42:    */   {
/*  43: 80 */     HtmlImage img = (HtmlImage)getDomNodeOrDie();
/*  44: 81 */     String src = img.getSrcAttribute();
/*  45: 82 */     if ((this.instantiatedViaJavaScript_) && ("".equals(src))) {
/*  46: 83 */       return src;
/*  47:    */     }
/*  48:    */     try
/*  49:    */     {
/*  50: 86 */       HtmlPage page = (HtmlPage)img.getPage();
/*  51: 87 */       return page.getFullyQualifiedUrl(src).toExternalForm();
/*  52:    */     }
/*  53:    */     catch (MalformedURLException e)
/*  54:    */     {
/*  55: 90 */       String msg = "Unable to create fully qualified URL for src attribute of image " + e.getMessage();
/*  56: 91 */       throw Context.reportRuntimeError(msg);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void jsxSet_onload(Object onloadHandler)
/*  61:    */   {
/*  62:100 */     setEventHandlerProp("onload", onloadHandler);
/*  63:101 */     getWindow().getWebWindow().getWebClient().getJavaScriptEngine().addPostponedAction(new ImageOnLoadAction(getDomNodeOrDie().getPage()));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object jsxGet_onload()
/*  67:    */   {
/*  68:110 */     return getEventHandlerProp("onload");
/*  69:    */   }
/*  70:    */   
/*  71:    */   private class ImageOnLoadAction
/*  72:    */     extends PostponedAction
/*  73:    */   {
/*  74:    */     public ImageOnLoadAction(Page page)
/*  75:    */     {
/*  76:118 */       super();
/*  77:    */     }
/*  78:    */     
/*  79:    */     public void execute()
/*  80:    */       throws Exception
/*  81:    */     {
/*  82:122 */       HtmlImage img = (HtmlImage)HTMLImageElement.this.getDomNodeOrNull();
/*  83:123 */       if (img != null) {
/*  84:124 */         img.doOnLoad();
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String jsxGet_alt()
/*  90:    */   {
/*  91:134 */     String alt = getDomNodeOrDie().getAttribute("alt");
/*  92:135 */     return alt;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void jsxSet_alt(String alt)
/*  96:    */   {
/*  97:143 */     getDomNodeOrDie().setAttribute("alt", alt);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String jsxGet_border()
/* 101:    */   {
/* 102:151 */     String border = getDomNodeOrDie().getAttribute("border");
/* 103:152 */     return border;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void jsxSet_border(String border)
/* 107:    */   {
/* 108:160 */     getDomNodeOrDie().setAttribute("border", border);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String jsxGet_align()
/* 112:    */   {
/* 113:168 */     return getAlign(true);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void jsxSet_align(String align)
/* 117:    */   {
/* 118:176 */     setAlign(align, false);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int jsxGet_width()
/* 122:    */   {
/* 123:184 */     HtmlImage img = (HtmlImage)getDomNodeOrDie();
/* 124:185 */     String width = img.getWidthAttribute();
/* 125:    */     try
/* 126:    */     {
/* 127:187 */       return Integer.parseInt(width);
/* 128:    */     }
/* 129:    */     catch (NumberFormatException e) {}
/* 130:190 */     return 24;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void jsxSet_width(String width)
/* 134:    */   {
/* 135:199 */     getDomNodeOrDie().setAttribute("width", width);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int jsxGet_height()
/* 139:    */   {
/* 140:207 */     HtmlImage img = (HtmlImage)getDomNodeOrDie();
/* 141:208 */     String height = img.getHeightAttribute();
/* 142:    */     try
/* 143:    */     {
/* 144:210 */       return Integer.parseInt(height);
/* 145:    */     }
/* 146:    */     catch (NumberFormatException e) {}
/* 147:213 */     return 24;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void jsxSet_height(String height)
/* 151:    */   {
/* 152:222 */     getDomNodeOrDie().setAttribute("height", height);
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLImageElement
 * JD-Core Version:    0.7.0.1
 */