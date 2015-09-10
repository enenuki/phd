/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ public class PluginArray
/*  4:   */   extends SimpleArray
/*  5:   */ {
/*  6:   */   public void jsxFunction_refresh(boolean reloadDocuments) {}
/*  7:   */   
/*  8:   */   protected String getItemName(Object element)
/*  9:   */   {
/* 10:48 */     return ((Plugin)element).jsxGet_name();
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.PluginArray
 * JD-Core Version:    0.7.0.1
 */