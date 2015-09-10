/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   6:    */ 
/*   7:    */ public class HTMLScriptElement
/*   8:    */   extends HTMLElement
/*   9:    */ {
/*  10:    */   public String jsxGet_src()
/*  11:    */   {
/*  12: 42 */     return getDomNodeOrDie().getAttribute("src");
/*  13:    */   }
/*  14:    */   
/*  15:    */   public void jsxSet_src(String src)
/*  16:    */   {
/*  17: 50 */     getDomNodeOrDie().setAttribute("src", src);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String jsxGet_text()
/*  21:    */   {
/*  22: 59 */     DomNode firstChild = getDomNodeOrDie().getFirstChild();
/*  23: 60 */     if (firstChild != null) {
/*  24: 61 */       return firstChild.getNodeValue();
/*  25:    */     }
/*  26: 63 */     return "";
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void jsxSet_text(String text)
/*  30:    */   {
/*  31: 71 */     DomNode htmlElement = getDomNodeOrDie();
/*  32: 72 */     DomNode firstChild = htmlElement.getFirstChild();
/*  33: 73 */     if (firstChild == null)
/*  34:    */     {
/*  35: 74 */       firstChild = new DomText(htmlElement.getPage(), text);
/*  36: 75 */       htmlElement.appendChild(firstChild);
/*  37:    */     }
/*  38:    */     else
/*  39:    */     {
/*  40: 78 */       firstChild.setNodeValue(text);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String jsxGet_type()
/*  45:    */   {
/*  46: 87 */     return getDomNodeOrDie().getAttribute("type");
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void jsxSet_type(String type)
/*  50:    */   {
/*  51: 95 */     getDomNodeOrDie().setAttribute("type", type);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object jsxGet_onreadystatechange()
/*  55:    */   {
/*  56:103 */     return getEventHandlerProp("onreadystatechange");
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void jsxSet_onreadystatechange(Object handler)
/*  60:    */   {
/*  61:111 */     setEventHandlerProp("onreadystatechange", handler);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object jsxGet_onload()
/*  65:    */   {
/*  66:119 */     return getEventHandlerProp("onload");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void jsxSet_onload(Object handler)
/*  70:    */   {
/*  71:127 */     setEventHandlerProp("onload", handler);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String jsxGet_readyState()
/*  75:    */   {
/*  76:140 */     DomNode node = getDomNodeOrDie();
/*  77:141 */     return node.getReadyState();
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLScriptElement
 * JD-Core Version:    0.7.0.1
 */