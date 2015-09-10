/*  1:   */ package org.cyberneko.html.xercesbridge;
/*  2:   */ 
/*  3:   */ import org.apache.xerces.xni.NamespaceContext;
/*  4:   */ 
/*  5:   */ public class XercesBridge_2_3
/*  6:   */   extends XercesBridge_2_2
/*  7:   */ {
/*  8:   */   public XercesBridge_2_3()
/*  9:   */     throws InstantiationException
/* 10:   */   {
/* 11:   */     try
/* 12:   */     {
/* 13:32 */       Class[] args = { String.class, String.class };
/* 14:33 */       NamespaceContext.class.getMethod("declarePrefix", args);
/* 15:   */     }
/* 16:   */     catch (NoSuchMethodException e)
/* 17:   */     {
/* 18:37 */       throw new InstantiationException(e.getMessage());
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void NamespaceContext_declarePrefix(NamespaceContext namespaceContext, String ns, String avalue)
/* 23:   */   {
/* 24:43 */     namespaceContext.declarePrefix(ns, avalue);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.xercesbridge.XercesBridge_2_3
 * JD-Core Version:    0.7.0.1
 */