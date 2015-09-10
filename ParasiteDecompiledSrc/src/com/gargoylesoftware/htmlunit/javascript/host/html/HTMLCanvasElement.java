/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.host.canvas.CanvasRenderingContext2D;
/*  5:   */ import com.gargoylesoftware.htmlunit.javascript.host.css.ComputedCSSStyleDeclaration;
/*  6:   */ 
/*  7:   */ public class HTMLCanvasElement
/*  8:   */   extends HTMLElement
/*  9:   */ {
/* 10:   */   public int jsxGet_width()
/* 11:   */   {
/* 12:38 */     return jsxGet_currentStyle().getCalculatedWidth(false, false);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void jsxSet_width(String width)
/* 16:   */   {
/* 17:46 */     getDomNodeOrDie().setAttribute("width", width);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int jsxGet_height()
/* 21:   */   {
/* 22:54 */     return jsxGet_currentStyle().getCalculatedHeight(false, false);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void jsxSet_height(String height)
/* 26:   */   {
/* 27:62 */     getDomNodeOrDie().setAttribute("height", height);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Object jsxFunction_getContext(String contextId)
/* 31:   */   {
/* 32:72 */     if ("2d".equals(contextId))
/* 33:   */     {
/* 34:73 */       CanvasRenderingContext2D context = new CanvasRenderingContext2D();
/* 35:74 */       context.setParentScope(getParentScope());
/* 36:75 */       context.setPrototype(getPrototype(context.getClass()));
/* 37:76 */       return context;
/* 38:   */     }
/* 39:78 */     return null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String jsxFunction_toDataURL(String type)
/* 43:   */   {
/* 44:88 */     return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAAxUlEQVR4nO3BMQEAAADCoPVPbQhfoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOA1v9QAATX68/0AAAAASUVORK5CYII=";
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCanvasElement
 * JD-Core Version:    0.7.0.1
 */