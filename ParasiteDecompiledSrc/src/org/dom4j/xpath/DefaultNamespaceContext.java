/*  1:   */ package org.dom4j.xpath;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.dom4j.Document;
/*  5:   */ import org.dom4j.Element;
/*  6:   */ import org.dom4j.Namespace;
/*  7:   */ import org.dom4j.Node;
/*  8:   */ import org.jaxen.NamespaceContext;
/*  9:   */ 
/* 10:   */ public class DefaultNamespaceContext
/* 11:   */   implements NamespaceContext, Serializable
/* 12:   */ {
/* 13:   */   private final Element element;
/* 14:   */   
/* 15:   */   public DefaultNamespaceContext(Element element)
/* 16:   */   {
/* 17:32 */     this.element = element;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static DefaultNamespaceContext create(Object node)
/* 21:   */   {
/* 22:36 */     Element element = null;
/* 23:38 */     if ((node instanceof Element))
/* 24:   */     {
/* 25:39 */       element = (Element)node;
/* 26:   */     }
/* 27:40 */     else if ((node instanceof Document))
/* 28:   */     {
/* 29:41 */       Document doc = (Document)node;
/* 30:42 */       element = doc.getRootElement();
/* 31:   */     }
/* 32:43 */     else if ((node instanceof Node))
/* 33:   */     {
/* 34:44 */       element = ((Node)node).getParent();
/* 35:   */     }
/* 36:47 */     if (element != null) {
/* 37:48 */       return new DefaultNamespaceContext(element);
/* 38:   */     }
/* 39:51 */     return null;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String translateNamespacePrefixToUri(String prefix)
/* 43:   */   {
/* 44:55 */     if ((prefix != null) && (prefix.length() > 0))
/* 45:   */     {
/* 46:56 */       Namespace ns = this.element.getNamespaceForPrefix(prefix);
/* 47:58 */       if (ns != null) {
/* 48:59 */         return ns.getURI();
/* 49:   */       }
/* 50:   */     }
/* 51:63 */     return null;
/* 52:   */   }
/* 53:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.xpath.DefaultNamespaceContext
 * JD-Core Version:    0.7.0.1
 */