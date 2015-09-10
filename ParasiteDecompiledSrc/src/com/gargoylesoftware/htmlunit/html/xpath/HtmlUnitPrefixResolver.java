/*  1:   */ package com.gargoylesoftware.htmlunit.html.xpath;
/*  2:   */ 
/*  3:   */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*  4:   */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*  5:   */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  6:   */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*  7:   */ import java.util.Map;
/*  8:   */ import org.apache.xml.utils.PrefixResolverDefault;
/*  9:   */ import org.w3c.dom.Node;
/* 10:   */ 
/* 11:   */ final class HtmlUnitPrefixResolver
/* 12:   */   extends PrefixResolverDefault
/* 13:   */ {
/* 14:   */   HtmlUnitPrefixResolver(Node xpathExpressionContext)
/* 15:   */   {
/* 16:40 */     super(xpathExpressionContext);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getNamespaceForPrefix(String prefix, Node namespaceContext)
/* 20:   */   {
/* 21:48 */     String namespace = super.getNamespaceForPrefix(prefix, namespaceContext);
/* 22:49 */     if (namespace == null) {
/* 23:50 */       if ((namespaceContext instanceof XmlPage))
/* 24:   */       {
/* 25:51 */         DomElement documentElement = ((XmlPage)namespaceContext).getDocumentElement();
/* 26:52 */         if (documentElement != null) {
/* 27:53 */           namespace = getNamespace(documentElement, prefix);
/* 28:   */         }
/* 29:   */       }
/* 30:56 */       else if ((namespaceContext instanceof DomElement))
/* 31:   */       {
/* 32:57 */         namespace = getNamespace((DomElement)namespaceContext, prefix);
/* 33:   */       }
/* 34:   */     }
/* 35:60 */     return namespace;
/* 36:   */   }
/* 37:   */   
/* 38:   */   private String getNamespace(DomElement element, String prefix)
/* 39:   */   {
/* 40:64 */     Map<String, DomAttr> attributes = element.getAttributesMap();
/* 41:65 */     for (String name : attributes.keySet()) {
/* 42:66 */       if ((name.startsWith("xmlns:")) && 
/* 43:67 */         (name.substring("xmlns:".length()).equals(prefix))) {
/* 44:68 */         return ((DomAttr)attributes.get(name)).getValue();
/* 45:   */       }
/* 46:   */     }
/* 47:72 */     for (DomNode child : element.getChildren()) {
/* 48:73 */       if ((child instanceof DomElement))
/* 49:   */       {
/* 50:74 */         String namespace = getNamespace((DomElement)child, prefix);
/* 51:75 */         if (namespace != null) {
/* 52:76 */           return namespace;
/* 53:   */         }
/* 54:   */       }
/* 55:   */     }
/* 56:80 */     return null;
/* 57:   */   }
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.xpath.HtmlUnitPrefixResolver
 * JD-Core Version:    0.7.0.1
 */