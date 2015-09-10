/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.HtmlFrameSet;
/*  5:   */ 
/*  6:   */ public class HTMLFrameSetElement
/*  7:   */   extends HTMLElement
/*  8:   */ {
/*  9:   */   public void jsxSet_rows(String rows)
/* 10:   */   {
/* 11:41 */     HtmlFrameSet htmlFrameSet = (HtmlFrameSet)getDomNodeOrNull();
/* 12:42 */     if (htmlFrameSet != null) {
/* 13:43 */       htmlFrameSet.setAttribute("rows", rows);
/* 14:   */     }
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String jsxGet_rows()
/* 18:   */   {
/* 19:54 */     HtmlFrameSet htmlFrameSet = (HtmlFrameSet)getDomNodeOrNull();
/* 20:55 */     return htmlFrameSet.getRowsAttribute();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void jsxSet_cols(String cols)
/* 24:   */   {
/* 25:64 */     HtmlFrameSet htmlFrameSet = (HtmlFrameSet)getDomNodeOrNull();
/* 26:65 */     if (htmlFrameSet != null) {
/* 27:66 */       htmlFrameSet.setAttribute("cols", cols);
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String jsxGet_cols()
/* 32:   */   {
/* 33:76 */     HtmlFrameSet htmlFrameSet = (HtmlFrameSet)getDomNodeOrNull();
/* 34:77 */     return htmlFrameSet.getColsAttribute();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String jsxGet_border()
/* 38:   */   {
/* 39:85 */     String border = getDomNodeOrDie().getAttribute("border");
/* 40:86 */     return border;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void jsxSet_border(String border)
/* 44:   */   {
/* 45:94 */     getDomNodeOrDie().setAttribute("border", border);
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLFrameSetElement
 * JD-Core Version:    0.7.0.1
 */