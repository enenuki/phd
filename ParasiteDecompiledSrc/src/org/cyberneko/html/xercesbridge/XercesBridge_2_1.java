/*  1:   */ package org.cyberneko.html.xercesbridge;
/*  2:   */ 
/*  3:   */ import org.apache.xerces.impl.Version;
/*  4:   */ import org.apache.xerces.xni.Augmentations;
/*  5:   */ import org.apache.xerces.xni.NamespaceContext;
/*  6:   */ import org.apache.xerces.xni.XMLDocumentHandler;
/*  7:   */ import org.apache.xerces.xni.XMLLocator;
/*  8:   */ import org.apache.xerces.xni.parser.XMLDocumentFilter;
/*  9:   */ import org.apache.xerces.xni.parser.XMLDocumentSource;
/* 10:   */ 
/* 11:   */ public class XercesBridge_2_1
/* 12:   */   extends XercesBridge
/* 13:   */ {
/* 14:   */   public XercesBridge_2_1()
/* 15:   */     throws InstantiationException
/* 16:   */   {
/* 17:   */     try
/* 18:   */     {
/* 19:40 */       getVersion();
/* 20:   */     }
/* 21:   */     catch (Error e)
/* 22:   */     {
/* 23:42 */       throw new InstantiationException(e.getMessage());
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getVersion()
/* 28:   */   {
/* 29:47 */     return new Version().getVersion();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void XMLDocumentHandler_startDocument(XMLDocumentHandler documentHandler, XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/* 33:   */   {
/* 34:53 */     documentHandler.startDocument(locator, encoding, augs);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void XMLDocumentFilter_setDocumentSource(XMLDocumentFilter filter, XMLDocumentSource lastSource)
/* 38:   */   {
/* 39:58 */     filter.setDocumentSource(lastSource);
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.xercesbridge.XercesBridge_2_1
 * JD-Core Version:    0.7.0.1
 */