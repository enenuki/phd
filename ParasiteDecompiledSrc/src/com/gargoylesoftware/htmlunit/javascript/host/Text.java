/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.DomText;
/*  4:   */ 
/*  5:   */ public class Text
/*  6:   */   extends CharacterDataImpl
/*  7:   */ {
/*  8:   */   public void initialize() {}
/*  9:   */   
/* 10:   */   public Object jsxFunction_splitText(int offset)
/* 11:   */   {
/* 12:47 */     DomText domText = (DomText)getDomNodeOrDie();
/* 13:48 */     return getScriptableFor(domText.splitText(offset));
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Text
 * JD-Core Version:    0.7.0.1
 */