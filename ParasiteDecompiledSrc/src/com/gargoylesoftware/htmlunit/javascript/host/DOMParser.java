/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  4:   */ import com.gargoylesoftware.htmlunit.javascript.host.xml.XMLDocument;
/*  5:   */ 
/*  6:   */ public class DOMParser
/*  7:   */   extends SimpleScriptable
/*  8:   */ {
/*  9:   */   public void jsConstructor() {}
/* 10:   */   
/* 11:   */   public XMLDocument jsxFunction_parseFromString(String str, String contentType)
/* 12:   */   {
/* 13:45 */     XMLDocument document = new XMLDocument();
/* 14:46 */     document.setParentScope(getParentScope());
/* 15:47 */     document.setPrototype(getPrototype(XMLDocument.class));
/* 16:48 */     document.jsxFunction_loadXML(str);
/* 17:49 */     return document;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.DOMParser
 * JD-Core Version:    0.7.0.1
 */