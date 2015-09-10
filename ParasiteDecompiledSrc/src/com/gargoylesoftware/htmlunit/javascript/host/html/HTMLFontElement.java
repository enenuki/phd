/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  4:   */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  5:   */ 
/*  6:   */ public class HTMLFontElement
/*  7:   */   extends HTMLElement
/*  8:   */ {
/*  9:   */   public String jsxGet_color()
/* 10:   */   {
/* 11:39 */     return getDomNodeOrDie().getAttribute("color");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void jsxSet_color(String color)
/* 15:   */   {
/* 16:47 */     getDomNodeOrDie().setAttribute("color", color);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String jsxGet_face()
/* 20:   */   {
/* 21:55 */     return getDomNodeOrDie().getAttribute("face");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void jsxSet_face(String face)
/* 25:   */   {
/* 26:63 */     getDomNodeOrDie().setAttribute("face", face);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int jsxGet_size()
/* 30:   */   {
/* 31:71 */     return (int)Context.toNumber(getDomNodeOrDie().getAttribute("size"));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void jsxSet_size(int size)
/* 35:   */   {
/* 36:79 */     getDomNodeOrDie().setAttribute("size", Context.toString(Integer.valueOf(size)));
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLFontElement
 * JD-Core Version:    0.7.0.1
 */