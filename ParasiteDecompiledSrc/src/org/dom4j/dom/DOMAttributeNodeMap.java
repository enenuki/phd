/*  1:   */ package org.dom4j.dom;
/*  2:   */ 
/*  3:   */ import org.w3c.dom.Attr;
/*  4:   */ import org.w3c.dom.DOMException;
/*  5:   */ import org.w3c.dom.NamedNodeMap;
/*  6:   */ import org.w3c.dom.Node;
/*  7:   */ 
/*  8:   */ public class DOMAttributeNodeMap
/*  9:   */   implements NamedNodeMap
/* 10:   */ {
/* 11:   */   private DOMElement element;
/* 12:   */   
/* 13:   */   public DOMAttributeNodeMap(DOMElement element)
/* 14:   */   {
/* 15:27 */     this.element = element;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void foo()
/* 19:   */     throws DOMException
/* 20:   */   {}
/* 21:   */   
/* 22:   */   public Node getNamedItem(String name)
/* 23:   */   {
/* 24:37 */     return this.element.getAttributeNode(name);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Node setNamedItem(Node arg)
/* 28:   */     throws DOMException
/* 29:   */   {
/* 30:41 */     if ((arg instanceof Attr)) {
/* 31:42 */       return this.element.setAttributeNode((Attr)arg);
/* 32:   */     }
/* 33:44 */     throw new DOMException((short)9, "Node is not an Attr: " + arg);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Node removeNamedItem(String name)
/* 37:   */     throws DOMException
/* 38:   */   {
/* 39:50 */     Attr attr = this.element.getAttributeNode(name);
/* 40:52 */     if (attr == null) {
/* 41:53 */       throw new DOMException((short)8, "No attribute named " + name);
/* 42:   */     }
/* 43:57 */     return this.element.removeAttributeNode(attr);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Node item(int index)
/* 47:   */   {
/* 48:61 */     return DOMNodeHelper.asDOMAttr(this.element.attribute(index));
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int getLength()
/* 52:   */   {
/* 53:65 */     return this.element.attributeCount();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public Node getNamedItemNS(String namespaceURI, String localName)
/* 57:   */   {
/* 58:69 */     return this.element.getAttributeNodeNS(namespaceURI, localName);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public Node setNamedItemNS(Node arg)
/* 62:   */     throws DOMException
/* 63:   */   {
/* 64:73 */     if ((arg instanceof Attr)) {
/* 65:74 */       return this.element.setAttributeNodeNS((Attr)arg);
/* 66:   */     }
/* 67:76 */     throw new DOMException((short)9, "Node is not an Attr: " + arg);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public Node removeNamedItemNS(String namespaceURI, String localName)
/* 71:   */     throws DOMException
/* 72:   */   {
/* 73:83 */     Attr attr = this.element.getAttributeNodeNS(namespaceURI, localName);
/* 74:86 */     if (attr != null) {
/* 75:87 */       return this.element.removeAttributeNode(attr);
/* 76:   */     }
/* 77:90 */     return attr;
/* 78:   */   }
/* 79:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMAttributeNodeMap
 * JD-Core Version:    0.7.0.1
 */