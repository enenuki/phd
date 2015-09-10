/*  1:   */ package org.cyberneko.html.xercesbridge;
/*  2:   */ 
/*  3:   */ import org.apache.xerces.impl.Version;
/*  4:   */ import org.apache.xerces.xni.Augmentations;
/*  5:   */ import org.apache.xerces.xni.NamespaceContext;
/*  6:   */ import org.apache.xerces.xni.XMLDocumentHandler;
/*  7:   */ import org.apache.xerces.xni.XMLLocator;
/*  8:   */ 
/*  9:   */ public class XercesBridge_2_0
/* 10:   */   extends XercesBridge
/* 11:   */ {
/* 12:   */   public String getVersion()
/* 13:   */   {
/* 14:39 */     return Version.fVersion;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void XMLDocumentHandler_startPrefixMapping(XMLDocumentHandler documentHandler, String prefix, String uri, Augmentations augs)
/* 18:   */   {
/* 19:45 */     documentHandler.startPrefixMapping(prefix, uri, augs);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void XMLDocumentHandler_endPrefixMapping(XMLDocumentHandler documentHandler, String prefix, Augmentations augs)
/* 23:   */   {
/* 24:51 */     documentHandler.endPrefixMapping(prefix, augs);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void XMLDocumentHandler_startDocument(XMLDocumentHandler documentHandler, XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/* 28:   */   {
/* 29:57 */     documentHandler.startDocument(locator, encoding, augs);
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.xercesbridge.XercesBridge_2_0
 * JD-Core Version:    0.7.0.1
 */