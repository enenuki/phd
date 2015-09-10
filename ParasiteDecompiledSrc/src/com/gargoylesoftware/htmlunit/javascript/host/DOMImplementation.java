/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  6:   */ import com.gargoylesoftware.htmlunit.javascript.host.xml.XMLDocument;
/*  7:   */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*  8:   */ 
/*  9:   */ public class DOMImplementation
/* 10:   */   extends SimpleScriptable
/* 11:   */ {
/* 12:   */   public boolean jsxFunction_hasFeature(String feature, String version)
/* 13:   */   {
/* 14:40 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_35))
/* 15:   */     {
/* 16:41 */       if (("HTML".equals(feature)) && ("1.0".equals(version))) {
/* 17:42 */         return true;
/* 18:   */       }
/* 19:   */     }
/* 20:   */     else
/* 21:   */     {
/* 22:46 */       if (("HTML".equals(feature)) && (("1.0".equals(version)) || ("2.0".equals(version)))) {
/* 23:47 */         return true;
/* 24:   */       }
/* 25:49 */       if (("XML".equals(feature)) && (("1.0".equals(version)) || ("2.0".equals(version)))) {
/* 26:50 */         return true;
/* 27:   */       }
/* 28:52 */       if (("CSS2".equals(feature)) && ("2.0".equals(version))) {
/* 29:53 */         return true;
/* 30:   */       }
/* 31:55 */       if (("XPath".equals(feature)) && ("3.0".equals(version))) {
/* 32:56 */         return true;
/* 33:   */       }
/* 34:   */     }
/* 35:60 */     return false;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public XMLDocument jsxFunction_createDocument(String namespaceURI, String qualifiedName, Object doctype)
/* 39:   */   {
/* 40:74 */     XMLDocument document = new XMLDocument(getWindow().getWebWindow());
/* 41:75 */     document.setParentScope(getParentScope());
/* 42:76 */     document.setPrototype(getPrototype(document.getClass()));
/* 43:77 */     if ((qualifiedName != null) && (qualifiedName.length() != 0))
/* 44:   */     {
/* 45:78 */       XmlPage page = (XmlPage)document.getDomNodeOrDie();
/* 46:79 */       page.appendChild(page.createXmlElementNS(namespaceURI, qualifiedName));
/* 47:   */     }
/* 48:81 */     return document;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.DOMImplementation
 * JD-Core Version:    0.7.0.1
 */