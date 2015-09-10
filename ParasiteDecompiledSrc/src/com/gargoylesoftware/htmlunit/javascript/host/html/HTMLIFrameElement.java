/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.BaseFrame;
/*   7:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   8:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   9:    */ 
/*  10:    */ public class HTMLIFrameElement
/*  11:    */   extends HTMLElement
/*  12:    */ {
/*  13:    */   public String jsxGet_src()
/*  14:    */   {
/*  15: 41 */     return getFrame().getSrcAttribute();
/*  16:    */   }
/*  17:    */   
/*  18:    */   public DocumentProxy jsxGet_contentDocument()
/*  19:    */   {
/*  20: 50 */     return ((Window)getFrame().getEnclosedWindow().getScriptObject()).jsxGet_document();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Window jsxGet_contentWindow()
/*  24:    */   {
/*  25: 61 */     return (Window)getFrame().getEnclosedWindow().getScriptObject();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void jsxSet_src(String src)
/*  29:    */   {
/*  30: 69 */     getFrame().setSrcAttribute(src);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String jsxGet_name()
/*  34:    */   {
/*  35: 77 */     return getFrame().getNameAttribute();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void jsxSet_name(String name)
/*  39:    */   {
/*  40: 85 */     getFrame().setNameAttribute(name);
/*  41:    */   }
/*  42:    */   
/*  43:    */   private BaseFrame getFrame()
/*  44:    */   {
/*  45: 89 */     return (BaseFrame)getDomNodeOrDie();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void jsxSet_onload(Object eventHandler)
/*  49:    */   {
/*  50: 97 */     setEventHandlerProp("onload", eventHandler);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object jsxGet_onload()
/*  54:    */   {
/*  55:105 */     return getEventHandlerProp("onload");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String jsxGet_border()
/*  59:    */   {
/*  60:113 */     String border = getDomNodeOrDie().getAttribute("border");
/*  61:114 */     return border;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void jsxSet_border(String border)
/*  65:    */   {
/*  66:122 */     getDomNodeOrDie().setAttribute("border", border);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String jsxGet_align()
/*  70:    */   {
/*  71:130 */     return getAlign(true);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void jsxSet_align(String align)
/*  75:    */   {
/*  76:138 */     setAlign(align, false);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String jsxGet_width()
/*  80:    */   {
/*  81:146 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_IFRAME_GET_WIDTH_NEGATIVE_VALUES);
/*  82:147 */     Boolean returnNegativeValues = ie ? Boolean.TRUE : null;
/*  83:148 */     return getWidthOrHeight("width", returnNegativeValues);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void jsxSet_width(String width)
/*  87:    */   {
/*  88:156 */     setWidthOrHeight("width", width, Boolean.TRUE);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String jsxGet_height()
/*  92:    */   {
/*  93:164 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.JS_IFRAME_GET_HEIGHT_NEGATIVE_VALUES);
/*  94:165 */     Boolean returnNegativeValues = ie ? Boolean.TRUE : null;
/*  95:166 */     return getWidthOrHeight("height", returnNegativeValues);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void jsxSet_height(String width)
/*  99:    */   {
/* 100:174 */     setWidthOrHeight("height", width, Boolean.TRUE);
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLIFrameElement
 * JD-Core Version:    0.7.0.1
 */