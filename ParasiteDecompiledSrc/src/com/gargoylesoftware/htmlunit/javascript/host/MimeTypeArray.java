/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ public class MimeTypeArray
/*  4:   */   extends SimpleArray
/*  5:   */ {
/*  6:   */   protected String getItemName(Object element)
/*  7:   */   {
/*  8:40 */     return ((MimeType)element).jsxGet_type();
/*  9:   */   }
/* 10:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.MimeTypeArray
 * JD-Core Version:    0.7.0.1
 */