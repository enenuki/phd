/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host.html;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.host.FormChild;
/*  4:   */ 
/*  5:   */ public class HTMLFieldSetElement
/*  6:   */   extends FormChild
/*  7:   */ {
/*  8:   */   public String jsxGet_align()
/*  9:   */   {
/* 10:39 */     return getAlign(false);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public void jsxSet_align(String align)
/* 14:   */   {
/* 15:47 */     setAlign(align, false);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Object getWithFallback(String name)
/* 19:   */   {
/* 20:55 */     if ("align".equals(name)) {
/* 21:56 */       return NOT_FOUND;
/* 22:   */     }
/* 23:58 */     return super.getWithFallback(name);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.html.HTMLFieldSetElement
 * JD-Core Version:    0.7.0.1
 */