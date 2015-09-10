/*  1:   */ package com.gargoylesoftware.htmlunit.javascript.host;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*  4:   */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.DomDocumentType;
/*  6:   */ 
/*  7:   */ public class DocumentType
/*  8:   */   extends Node
/*  9:   */ {
/* 10:   */   public String jsxGet_name()
/* 11:   */   {
/* 12:35 */     String name = ((DomDocumentType)getDomNodeOrDie()).getName();
/* 13:36 */     if (("html".equals(name)) && ("FF3".equals(getBrowserVersion().getNickname()))) {
/* 14:37 */       return "HTML";
/* 15:   */     }
/* 16:39 */     return name;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String jsxGet_nodeName()
/* 20:   */   {
/* 21:47 */     return jsxGet_name();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String jsxGet_publicId()
/* 25:   */   {
/* 26:55 */     return ((DomDocumentType)getDomNodeOrDie()).getPublicId();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String jsxGet_systemId()
/* 30:   */   {
/* 31:63 */     return ((DomDocumentType)getDomNodeOrDie()).getSystemId();
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String jsxGet_internalSubset()
/* 35:   */   {
/* 36:71 */     return ((DomDocumentType)getDomNodeOrDie()).getInternalSubset();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String jsxGet_entities()
/* 40:   */   {
/* 41:79 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_33)) {
/* 42:80 */       return "";
/* 43:   */     }
/* 44:82 */     return null;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String jsxGet_notations()
/* 48:   */   {
/* 49:90 */     if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_34)) {
/* 50:91 */       return "";
/* 51:   */     }
/* 52:93 */     return null;
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.DocumentType
 * JD-Core Version:    0.7.0.1
 */