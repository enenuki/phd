/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.tree.DefaultNamespace;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.Document;
/*   7:    */ import org.w3c.dom.NamedNodeMap;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.NodeList;
/*  10:    */ 
/*  11:    */ public class DOMNamespace
/*  12:    */   extends DefaultNamespace
/*  13:    */   implements Node
/*  14:    */ {
/*  15:    */   public DOMNamespace(String prefix, String uri)
/*  16:    */   {
/*  17: 29 */     super(prefix, uri);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public DOMNamespace(Element parent, String prefix, String uri)
/*  21:    */   {
/*  22: 33 */     super(parent, prefix, uri);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public boolean supports(String feature, String version)
/*  26:    */   {
/*  27: 39 */     return DOMNodeHelper.supports(this, feature, version);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getNamespaceURI()
/*  31:    */   {
/*  32: 43 */     return DOMNodeHelper.getNamespaceURI(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void setPrefix(String prefix)
/*  36:    */     throws DOMException
/*  37:    */   {
/*  38: 50 */     DOMNodeHelper.setPrefix(this, prefix);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getLocalName()
/*  42:    */   {
/*  43: 54 */     return DOMNodeHelper.getLocalName(this);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getNodeName()
/*  47:    */   {
/*  48: 58 */     return getName();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getNodeValue()
/*  52:    */     throws DOMException
/*  53:    */   {
/*  54: 65 */     return DOMNodeHelper.getNodeValue(this);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setNodeValue(String nodeValue)
/*  58:    */     throws DOMException
/*  59:    */   {
/*  60: 69 */     DOMNodeHelper.setNodeValue(this, nodeValue);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Node getParentNode()
/*  64:    */   {
/*  65: 73 */     return DOMNodeHelper.getParentNode(this);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public NodeList getChildNodes()
/*  69:    */   {
/*  70: 77 */     return DOMNodeHelper.getChildNodes(this);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public Node getFirstChild()
/*  74:    */   {
/*  75: 81 */     return DOMNodeHelper.getFirstChild(this);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Node getLastChild()
/*  79:    */   {
/*  80: 85 */     return DOMNodeHelper.getLastChild(this);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Node getPreviousSibling()
/*  84:    */   {
/*  85: 89 */     return DOMNodeHelper.getPreviousSibling(this);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Node getNextSibling()
/*  89:    */   {
/*  90: 93 */     return DOMNodeHelper.getNextSibling(this);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public NamedNodeMap getAttributes()
/*  94:    */   {
/*  95: 97 */     return DOMNodeHelper.getAttributes(this);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Document getOwnerDocument()
/*  99:    */   {
/* 100:101 */     return DOMNodeHelper.getOwnerDocument(this);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Node insertBefore(Node newChild, Node refChild)
/* 104:    */     throws DOMException
/* 105:    */   {
/* 106:106 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 110:    */     throws DOMException
/* 111:    */   {
/* 112:111 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Node removeChild(Node oldChild)
/* 116:    */     throws DOMException
/* 117:    */   {
/* 118:116 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Node appendChild(Node newChild)
/* 122:    */     throws DOMException
/* 123:    */   {
/* 124:121 */     return DOMNodeHelper.appendChild(this, newChild);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean hasChildNodes()
/* 128:    */   {
/* 129:125 */     return DOMNodeHelper.hasChildNodes(this);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Node cloneNode(boolean deep)
/* 133:    */   {
/* 134:129 */     return DOMNodeHelper.cloneNode(this, deep);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void normalize()
/* 138:    */   {
/* 139:133 */     DOMNodeHelper.normalize(this);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public boolean isSupported(String feature, String version)
/* 143:    */   {
/* 144:137 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean hasAttributes()
/* 148:    */   {
/* 149:141 */     return DOMNodeHelper.hasAttributes(this);
/* 150:    */   }
/* 151:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMNamespace
 * JD-Core Version:    0.7.0.1
 */