/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import org.dom4j.tree.DefaultDocumentType;
/*   4:    */ import org.w3c.dom.DOMException;
/*   5:    */ import org.w3c.dom.Document;
/*   6:    */ import org.w3c.dom.DocumentType;
/*   7:    */ import org.w3c.dom.NamedNodeMap;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.NodeList;
/*  10:    */ 
/*  11:    */ public class DOMDocumentType
/*  12:    */   extends DefaultDocumentType
/*  13:    */   implements DocumentType
/*  14:    */ {
/*  15:    */   public DOMDocumentType() {}
/*  16:    */   
/*  17:    */   public DOMDocumentType(String elementName, String systemID)
/*  18:    */   {
/*  19: 32 */     super(elementName, systemID);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public DOMDocumentType(String name, String publicID, String systemID)
/*  23:    */   {
/*  24: 36 */     super(name, publicID, systemID);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean supports(String feature, String version)
/*  28:    */   {
/*  29: 42 */     return DOMNodeHelper.supports(this, feature, version);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public String getNamespaceURI()
/*  33:    */   {
/*  34: 46 */     return DOMNodeHelper.getNamespaceURI(this);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getPrefix()
/*  38:    */   {
/*  39: 50 */     return DOMNodeHelper.getPrefix(this);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setPrefix(String prefix)
/*  43:    */     throws DOMException
/*  44:    */   {
/*  45: 54 */     DOMNodeHelper.setPrefix(this, prefix);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getLocalName()
/*  49:    */   {
/*  50: 58 */     return DOMNodeHelper.getLocalName(this);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getNodeName()
/*  54:    */   {
/*  55: 62 */     return getName();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getNodeValue()
/*  59:    */     throws DOMException
/*  60:    */   {
/*  61: 69 */     return null;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setNodeValue(String nodeValue)
/*  65:    */     throws DOMException
/*  66:    */   {}
/*  67:    */   
/*  68:    */   public Node getParentNode()
/*  69:    */   {
/*  70: 76 */     return DOMNodeHelper.getParentNode(this);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public NodeList getChildNodes()
/*  74:    */   {
/*  75: 80 */     return DOMNodeHelper.getChildNodes(this);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Node getFirstChild()
/*  79:    */   {
/*  80: 84 */     return DOMNodeHelper.getFirstChild(this);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Node getLastChild()
/*  84:    */   {
/*  85: 88 */     return DOMNodeHelper.getLastChild(this);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Node getPreviousSibling()
/*  89:    */   {
/*  90: 92 */     return DOMNodeHelper.getPreviousSibling(this);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Node getNextSibling()
/*  94:    */   {
/*  95: 96 */     return DOMNodeHelper.getNextSibling(this);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public NamedNodeMap getAttributes()
/*  99:    */   {
/* 100:100 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Document getOwnerDocument()
/* 104:    */   {
/* 105:104 */     return DOMNodeHelper.getOwnerDocument(this);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Node insertBefore(Node newChild, Node refChild)
/* 109:    */     throws DOMException
/* 110:    */   {
/* 111:109 */     checkNewChildNode(newChild);
/* 112:    */     
/* 113:111 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 117:    */     throws DOMException
/* 118:    */   {
/* 119:116 */     checkNewChildNode(newChild);
/* 120:    */     
/* 121:118 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Node removeChild(Node oldChild)
/* 125:    */     throws DOMException
/* 126:    */   {
/* 127:123 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Node appendChild(Node newChild)
/* 131:    */     throws DOMException
/* 132:    */   {
/* 133:128 */     checkNewChildNode(newChild);
/* 134:    */     
/* 135:130 */     return DOMNodeHelper.appendChild(this, newChild);
/* 136:    */   }
/* 137:    */   
/* 138:    */   private void checkNewChildNode(Node newChild)
/* 139:    */     throws DOMException
/* 140:    */   {
/* 141:135 */     throw new DOMException((short)3, "DocumentType nodes cannot have children");
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean hasChildNodes()
/* 145:    */   {
/* 146:140 */     return DOMNodeHelper.hasChildNodes(this);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Node cloneNode(boolean deep)
/* 150:    */   {
/* 151:144 */     return DOMNodeHelper.cloneNode(this, deep);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void normalize()
/* 155:    */   {
/* 156:148 */     DOMNodeHelper.normalize(this);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isSupported(String feature, String version)
/* 160:    */   {
/* 161:152 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean hasAttributes()
/* 165:    */   {
/* 166:156 */     return DOMNodeHelper.hasAttributes(this);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public NamedNodeMap getEntities()
/* 170:    */   {
/* 171:162 */     return null;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public NamedNodeMap getNotations()
/* 175:    */   {
/* 176:166 */     return null;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getPublicId()
/* 180:    */   {
/* 181:170 */     return getPublicID();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String getSystemId()
/* 185:    */   {
/* 186:174 */     return getSystemID();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getInternalSubset()
/* 190:    */   {
/* 191:178 */     return getElementName();
/* 192:    */   }
/* 193:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMDocumentType
 * JD-Core Version:    0.7.0.1
 */