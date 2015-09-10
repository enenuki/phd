/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.DomText;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  6:   */ 
/*  7:   */ public class HTMLTitleElement
/*  8:   */   extends HTMLElement
/*  9:   */ {
/* 10:   */   public String jsxGet_text()
/* 11:   */   {
/* 12:42 */     DomNode firstChild = getDomNodeOrDie().getFirstChild();
/* 13:43 */     if (firstChild != null) {
/* 14:44 */       return firstChild.getNodeValue();
/* 15:   */     }
/* 16:46 */     return "";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void jsxSet_text(String text)
/* 20:   */   {
/* 21:54 */     DomNode htmlElement = getDomNodeOrDie();
/* 22:55 */     DomNode firstChild = htmlElement.getFirstChild();
/* 23:56 */     if (firstChild == null)
/* 24:   */     {
/* 25:57 */       firstChild = new DomText(htmlElement.getPage(), text);
/* 26:58 */       htmlElement.appendChild(firstChild);
/* 27:   */     }
/* 28:   */     else
/* 29:   */     {
/* 30:61 */       firstChild.setNodeValue(text);
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLTitleElement
 * JD-Core Version:    0.7.0.1
 */