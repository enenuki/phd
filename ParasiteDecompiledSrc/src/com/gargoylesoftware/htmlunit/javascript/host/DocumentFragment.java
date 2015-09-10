/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ public class DocumentFragment
/*  4:   */   extends Node
/*  5:   */ {
/*  6:   */   public Object jsxGet_xml()
/*  7:   */   {
/*  8:33 */     Node node = jsxGet_firstChild();
/*  9:34 */     if (node != null) {
/* 10:35 */       return node.jsxGet_xml();
/* 11:   */     }
/* 12:37 */     return "";
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.DocumentFragment
 * JD-Core Version:    0.7.0.1
 */