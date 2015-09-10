/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.BaseFrame;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   6:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*   7:    */ import com.gargoylesoftware.htmlunit.javascript.host.WindowProxy;
/*   8:    */ 
/*   9:    */ public class HTMLFrameElement
/*  10:    */   extends HTMLElement
/*  11:    */ {
/*  12:    */   public String jsxGet_src()
/*  13:    */   {
/*  14: 41 */     return getFrame().getSrcAttribute();
/*  15:    */   }
/*  16:    */   
/*  17:    */   public DocumentProxy jsxGet_contentDocument()
/*  18:    */   {
/*  19: 50 */     return ((Window)getFrame().getEnclosedWindow().getScriptObject()).jsxGet_document();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public WindowProxy jsxGet_contentWindow()
/*  23:    */   {
/*  24: 60 */     return Window.getProxy(getFrame().getEnclosedWindow());
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void jsxSet_src(String src)
/*  28:    */   {
/*  29: 68 */     getFrame().setSrcAttribute(src);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String jsxGet_name()
/*  33:    */   {
/*  34: 76 */     return getFrame().getNameAttribute();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void jsxSet_name(String name)
/*  38:    */   {
/*  39: 84 */     getFrame().setNameAttribute(name);
/*  40:    */   }
/*  41:    */   
/*  42:    */   private BaseFrame getFrame()
/*  43:    */   {
/*  44: 88 */     return (BaseFrame)getDomNodeOrDie();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void jsxSet_onload(Object eventHandler)
/*  48:    */   {
/*  49: 96 */     setEventHandlerProp("onload", eventHandler);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object jsxGet_onload()
/*  53:    */   {
/*  54:104 */     return getEventHandlerProp("onload");
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String jsxGet_border()
/*  58:    */   {
/*  59:112 */     String border = getDomNodeOrDie().getAttribute("border");
/*  60:113 */     return border;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void jsxSet_border(String border)
/*  64:    */   {
/*  65:121 */     getDomNodeOrDie().setAttribute("border", border);
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLFrameElement
 * JD-Core Version:    0.7.0.1
 */