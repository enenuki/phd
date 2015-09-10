/*   1:    */ package org.apache.xpath.domapi;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Attr;
/*   4:    */ import org.w3c.dom.DOMException;
/*   5:    */ import org.w3c.dom.Document;
/*   6:    */ import org.w3c.dom.Element;
/*   7:    */ import org.w3c.dom.NamedNodeMap;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.NodeList;
/*  10:    */ import org.w3c.dom.UserDataHandler;
/*  11:    */ import org.w3c.dom.xpath.XPathNamespace;
/*  12:    */ 
/*  13:    */ class XPathNamespaceImpl
/*  14:    */   implements XPathNamespace
/*  15:    */ {
/*  16:    */   private final Node m_attributeNode;
/*  17:    */   private String textContent;
/*  18:    */   
/*  19:    */   XPathNamespaceImpl(Node node)
/*  20:    */   {
/*  21: 83 */     this.m_attributeNode = node;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Element getOwnerElement()
/*  25:    */   {
/*  26: 90 */     return ((Attr)this.m_attributeNode).getOwnerElement();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getNodeName()
/*  30:    */   {
/*  31: 97 */     return "#namespace";
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String getNodeValue()
/*  35:    */     throws DOMException
/*  36:    */   {
/*  37:104 */     return this.m_attributeNode.getNodeValue();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setNodeValue(String arg0)
/*  41:    */     throws DOMException
/*  42:    */   {}
/*  43:    */   
/*  44:    */   public short getNodeType()
/*  45:    */   {
/*  46:117 */     return 13;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Node getParentNode()
/*  50:    */   {
/*  51:124 */     return this.m_attributeNode.getParentNode();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public NodeList getChildNodes()
/*  55:    */   {
/*  56:131 */     return this.m_attributeNode.getChildNodes();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Node getFirstChild()
/*  60:    */   {
/*  61:138 */     return this.m_attributeNode.getFirstChild();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Node getLastChild()
/*  65:    */   {
/*  66:145 */     return this.m_attributeNode.getLastChild();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Node getPreviousSibling()
/*  70:    */   {
/*  71:152 */     return this.m_attributeNode.getPreviousSibling();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Node getNextSibling()
/*  75:    */   {
/*  76:159 */     return this.m_attributeNode.getNextSibling();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public NamedNodeMap getAttributes()
/*  80:    */   {
/*  81:166 */     return this.m_attributeNode.getAttributes();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Document getOwnerDocument()
/*  85:    */   {
/*  86:173 */     return this.m_attributeNode.getOwnerDocument();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Node insertBefore(Node arg0, Node arg1)
/*  90:    */     throws DOMException
/*  91:    */   {
/*  92:180 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Node replaceChild(Node arg0, Node arg1)
/*  96:    */     throws DOMException
/*  97:    */   {
/*  98:187 */     return null;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Node removeChild(Node arg0)
/* 102:    */     throws DOMException
/* 103:    */   {
/* 104:194 */     return null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Node appendChild(Node arg0)
/* 108:    */     throws DOMException
/* 109:    */   {
/* 110:201 */     return null;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean hasChildNodes()
/* 114:    */   {
/* 115:208 */     return false;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Node cloneNode(boolean arg0)
/* 119:    */   {
/* 120:215 */     throw new DOMException((short)9, null);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void normalize()
/* 124:    */   {
/* 125:222 */     this.m_attributeNode.normalize();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isSupported(String arg0, String arg1)
/* 129:    */   {
/* 130:229 */     return this.m_attributeNode.isSupported(arg0, arg1);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getNamespaceURI()
/* 134:    */   {
/* 135:239 */     return this.m_attributeNode.getNodeValue();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getPrefix()
/* 139:    */   {
/* 140:246 */     return this.m_attributeNode.getPrefix();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPrefix(String arg0)
/* 144:    */     throws DOMException
/* 145:    */   {}
/* 146:    */   
/* 147:    */   public String getLocalName()
/* 148:    */   {
/* 149:261 */     return this.m_attributeNode.getPrefix();
/* 150:    */   }
/* 151:    */   
/* 152:    */   public boolean hasAttributes()
/* 153:    */   {
/* 154:268 */     return this.m_attributeNode.hasAttributes();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getBaseURI()
/* 158:    */   {
/* 159:272 */     return null;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public short compareDocumentPosition(Node other)
/* 163:    */     throws DOMException
/* 164:    */   {
/* 165:276 */     return 0;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getTextContent()
/* 169:    */     throws DOMException
/* 170:    */   {
/* 171:282 */     return this.textContent;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setTextContent(String textContent)
/* 175:    */     throws DOMException
/* 176:    */   {
/* 177:286 */     this.textContent = textContent;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isSameNode(Node other)
/* 181:    */   {
/* 182:290 */     return false;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String lookupPrefix(String namespaceURI)
/* 186:    */   {
/* 187:294 */     return "";
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isDefaultNamespace(String namespaceURI)
/* 191:    */   {
/* 192:298 */     return false;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String lookupNamespaceURI(String prefix)
/* 196:    */   {
/* 197:302 */     return null;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isEqualNode(Node arg)
/* 201:    */   {
/* 202:306 */     return false;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public Object getFeature(String feature, String version)
/* 206:    */   {
/* 207:310 */     return null;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Object setUserData(String key, Object data, UserDataHandler handler)
/* 211:    */   {
/* 212:316 */     return null;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Object getUserData(String key)
/* 216:    */   {
/* 217:320 */     return null;
/* 218:    */   }
/* 219:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.domapi.XPathNamespaceImpl
 * JD-Core Version:    0.7.0.1
 */