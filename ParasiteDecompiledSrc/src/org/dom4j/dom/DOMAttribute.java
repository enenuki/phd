/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import org.dom4j.QName;
/*   4:    */ import org.dom4j.tree.DefaultAttribute;
/*   5:    */ import org.w3c.dom.Attr;
/*   6:    */ import org.w3c.dom.DOMException;
/*   7:    */ import org.w3c.dom.Document;
/*   8:    */ import org.w3c.dom.NamedNodeMap;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.w3c.dom.NodeList;
/*  11:    */ 
/*  12:    */ public class DOMAttribute
/*  13:    */   extends DefaultAttribute
/*  14:    */   implements Attr
/*  15:    */ {
/*  16:    */   public DOMAttribute(QName qname)
/*  17:    */   {
/*  18: 30 */     super(qname);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public DOMAttribute(QName qname, String value)
/*  22:    */   {
/*  23: 34 */     super(qname, value);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public DOMAttribute(org.dom4j.Element parent, QName qname, String value)
/*  27:    */   {
/*  28: 38 */     super(parent, qname, value);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean supports(String feature, String version)
/*  32:    */   {
/*  33: 44 */     return DOMNodeHelper.supports(this, feature, version);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getNamespaceURI()
/*  37:    */   {
/*  38: 48 */     return getQName().getNamespaceURI();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getPrefix()
/*  42:    */   {
/*  43: 52 */     return getQName().getNamespacePrefix();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setPrefix(String prefix)
/*  47:    */     throws DOMException
/*  48:    */   {
/*  49: 56 */     DOMNodeHelper.setPrefix(this, prefix);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getLocalName()
/*  53:    */   {
/*  54: 60 */     return getQName().getName();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getNodeName()
/*  58:    */   {
/*  59: 64 */     return getName();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getNodeValue()
/*  63:    */     throws DOMException
/*  64:    */   {
/*  65: 71 */     return DOMNodeHelper.getNodeValue(this);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setNodeValue(String nodeValue)
/*  69:    */     throws DOMException
/*  70:    */   {
/*  71: 75 */     DOMNodeHelper.setNodeValue(this, nodeValue);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Node getParentNode()
/*  75:    */   {
/*  76: 82 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public NodeList getChildNodes()
/*  80:    */   {
/*  81: 86 */     return DOMNodeHelper.getChildNodes(this);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Node getFirstChild()
/*  85:    */   {
/*  86: 90 */     return DOMNodeHelper.getFirstChild(this);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Node getLastChild()
/*  90:    */   {
/*  91: 94 */     return DOMNodeHelper.getLastChild(this);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Node getPreviousSibling()
/*  95:    */   {
/*  96: 98 */     return DOMNodeHelper.getPreviousSibling(this);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Node getNextSibling()
/* 100:    */   {
/* 101:102 */     return DOMNodeHelper.getNextSibling(this);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public NamedNodeMap getAttributes()
/* 105:    */   {
/* 106:106 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Document getOwnerDocument()
/* 110:    */   {
/* 111:110 */     return DOMNodeHelper.getOwnerDocument(this);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Node insertBefore(Node newChild, Node refChild)
/* 115:    */     throws DOMException
/* 116:    */   {
/* 117:115 */     checkNewChildNode(newChild);
/* 118:    */     
/* 119:117 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 123:    */     throws DOMException
/* 124:    */   {
/* 125:122 */     checkNewChildNode(newChild);
/* 126:    */     
/* 127:124 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Node removeChild(Node oldChild)
/* 131:    */     throws DOMException
/* 132:    */   {
/* 133:129 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Node appendChild(Node newChild)
/* 137:    */     throws DOMException
/* 138:    */   {
/* 139:134 */     checkNewChildNode(newChild);
/* 140:    */     
/* 141:136 */     return DOMNodeHelper.appendChild(this, newChild);
/* 142:    */   }
/* 143:    */   
/* 144:    */   private void checkNewChildNode(Node newChild)
/* 145:    */     throws DOMException
/* 146:    */   {
/* 147:141 */     int nodeType = newChild.getNodeType();
/* 148:143 */     if ((nodeType != 3) && (nodeType != 5)) {
/* 149:145 */       throw new DOMException((short)3, "The node cannot be a child of attribute");
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean hasChildNodes()
/* 154:    */   {
/* 155:151 */     return DOMNodeHelper.hasChildNodes(this);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Node cloneNode(boolean deep)
/* 159:    */   {
/* 160:155 */     return DOMNodeHelper.cloneNode(this, deep);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void normalize()
/* 164:    */   {
/* 165:159 */     DOMNodeHelper.normalize(this);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean isSupported(String feature, String version)
/* 169:    */   {
/* 170:163 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean hasAttributes()
/* 174:    */   {
/* 175:167 */     return DOMNodeHelper.hasAttributes(this);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean getSpecified()
/* 179:    */   {
/* 180:174 */     return true;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public org.w3c.dom.Element getOwnerElement()
/* 184:    */   {
/* 185:180 */     return DOMNodeHelper.asDOMElement(getParent());
/* 186:    */   }
/* 187:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMAttribute
 * JD-Core Version:    0.7.0.1
 */