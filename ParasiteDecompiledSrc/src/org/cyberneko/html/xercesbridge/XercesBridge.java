/*  1:   */ package org.cyberneko.html.xercesbridge;
/*  2:   */ 
/*  3:   */ import org.apache.xerces.xni.Augmentations;
/*  4:   */ import org.apache.xerces.xni.NamespaceContext;
/*  5:   */ import org.apache.xerces.xni.XMLDocumentHandler;
/*  6:   */ import org.apache.xerces.xni.XMLLocator;
/*  7:   */ import org.apache.xerces.xni.parser.XMLDocumentFilter;
/*  8:   */ import org.apache.xerces.xni.parser.XMLDocumentSource;
/*  9:   */ 
/* 10:   */ public abstract class XercesBridge
/* 11:   */ {
/* 12:32 */   private static final XercesBridge instance = ;
/* 13:   */   
/* 14:   */   public static XercesBridge getInstance()
/* 15:   */   {
/* 16:40 */     return instance;
/* 17:   */   }
/* 18:   */   
/* 19:   */   private static XercesBridge makeInstance()
/* 20:   */   {
/* 21:45 */     String[] classNames = { "org.cyberneko.html.xercesbridge.XercesBridge_2_3", "org.cyberneko.html.xercesbridge.XercesBridge_2_2", "org.cyberneko.html.xercesbridge.XercesBridge_2_1", "org.cyberneko.html.xercesbridge.XercesBridge_2_0" };
/* 22:52 */     for (int i = 0; i != classNames.length; i++)
/* 23:   */     {
/* 24:53 */       String className = classNames[i];
/* 25:54 */       XercesBridge bridge = newInstanceOrNull(className);
/* 26:55 */       if (bridge != null) {
/* 27:56 */         return bridge;
/* 28:   */       }
/* 29:   */     }
/* 30:59 */     throw new IllegalStateException("Failed to create XercesBridge instance");
/* 31:   */   }
/* 32:   */   
/* 33:   */   private static XercesBridge newInstanceOrNull(String className)
/* 34:   */   {
/* 35:   */     try
/* 36:   */     {
/* 37:64 */       return (XercesBridge)Class.forName(className).newInstance();
/* 38:   */     }
/* 39:   */     catch (ClassNotFoundException ex) {}catch (SecurityException ex) {}catch (LinkageError ex) {}catch (IllegalArgumentException e) {}catch (IllegalAccessException e) {}catch (InstantiationException e) {}
/* 40:73 */     return null;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void NamespaceContext_declarePrefix(NamespaceContext namespaceContext, String ns, String avalue) {}
/* 44:   */   
/* 45:   */   public abstract String getVersion();
/* 46:   */   
/* 47:   */   public abstract void XMLDocumentHandler_startDocument(XMLDocumentHandler paramXMLDocumentHandler, XMLLocator paramXMLLocator, String paramString, NamespaceContext paramNamespaceContext, Augmentations paramAugmentations);
/* 48:   */   
/* 49:   */   public void XMLDocumentHandler_startPrefixMapping(XMLDocumentHandler documentHandler, String prefix, String uri, Augmentations augs) {}
/* 50:   */   
/* 51:   */   public void XMLDocumentHandler_endPrefixMapping(XMLDocumentHandler documentHandler, String prefix, Augmentations augs) {}
/* 52:   */   
/* 53:   */   public void XMLDocumentFilter_setDocumentSource(XMLDocumentFilter filter, XMLDocumentSource lastSource) {}
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.xercesbridge.XercesBridge
 * JD-Core Version:    0.7.0.1
 */