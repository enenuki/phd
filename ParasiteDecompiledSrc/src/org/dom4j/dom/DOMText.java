/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.tree.DefaultText;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.Document;
/*   7:    */ import org.w3c.dom.NamedNodeMap;
/*   8:    */ import org.w3c.dom.Node;
/*   9:    */ import org.w3c.dom.NodeList;
/*  10:    */ 
/*  11:    */ public class DOMText
/*  12:    */   extends DefaultText
/*  13:    */   implements org.w3c.dom.Text
/*  14:    */ {
/*  15:    */   public DOMText(String text)
/*  16:    */   {
/*  17: 29 */     super(text);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public DOMText(Element parent, String text)
/*  21:    */   {
/*  22: 33 */     super(parent, text);
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
/*  35:    */   public String getPrefix()
/*  36:    */   {
/*  37: 47 */     return DOMNodeHelper.getPrefix(this);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setPrefix(String prefix)
/*  41:    */     throws DOMException
/*  42:    */   {
/*  43: 51 */     DOMNodeHelper.setPrefix(this, prefix);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getLocalName()
/*  47:    */   {
/*  48: 55 */     return DOMNodeHelper.getLocalName(this);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String getNodeName()
/*  52:    */   {
/*  53: 59 */     return "#text";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getNodeValue()
/*  57:    */     throws DOMException
/*  58:    */   {
/*  59: 66 */     return DOMNodeHelper.getNodeValue(this);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setNodeValue(String nodeValue)
/*  63:    */     throws DOMException
/*  64:    */   {
/*  65: 70 */     DOMNodeHelper.setNodeValue(this, nodeValue);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Node getParentNode()
/*  69:    */   {
/*  70: 74 */     return DOMNodeHelper.getParentNode(this);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public NodeList getChildNodes()
/*  74:    */   {
/*  75: 78 */     return DOMNodeHelper.getChildNodes(this);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Node getFirstChild()
/*  79:    */   {
/*  80: 82 */     return DOMNodeHelper.getFirstChild(this);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Node getLastChild()
/*  84:    */   {
/*  85: 86 */     return DOMNodeHelper.getLastChild(this);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Node getPreviousSibling()
/*  89:    */   {
/*  90: 90 */     return DOMNodeHelper.getPreviousSibling(this);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Node getNextSibling()
/*  94:    */   {
/*  95: 94 */     return DOMNodeHelper.getNextSibling(this);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public NamedNodeMap getAttributes()
/*  99:    */   {
/* 100: 98 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Document getOwnerDocument()
/* 104:    */   {
/* 105:102 */     return DOMNodeHelper.getOwnerDocument(this);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Node insertBefore(Node newChild, Node refChild)
/* 109:    */     throws DOMException
/* 110:    */   {
/* 111:107 */     checkNewChildNode(newChild);
/* 112:    */     
/* 113:109 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 117:    */     throws DOMException
/* 118:    */   {
/* 119:114 */     checkNewChildNode(newChild);
/* 120:    */     
/* 121:116 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Node removeChild(Node oldChild)
/* 125:    */     throws DOMException
/* 126:    */   {
/* 127:121 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Node appendChild(Node newChild)
/* 131:    */     throws DOMException
/* 132:    */   {
/* 133:126 */     checkNewChildNode(newChild);
/* 134:    */     
/* 135:128 */     return DOMNodeHelper.appendChild(this, newChild);
/* 136:    */   }
/* 137:    */   
/* 138:    */   private void checkNewChildNode(Node newChild)
/* 139:    */     throws DOMException
/* 140:    */   {
/* 141:133 */     throw new DOMException((short)3, "Text nodes cannot have children");
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean hasChildNodes()
/* 145:    */   {
/* 146:138 */     return DOMNodeHelper.hasChildNodes(this);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Node cloneNode(boolean deep)
/* 150:    */   {
/* 151:142 */     return DOMNodeHelper.cloneNode(this, deep);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void normalize()
/* 155:    */   {
/* 156:146 */     DOMNodeHelper.normalize(this);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean isSupported(String feature, String version)
/* 160:    */   {
/* 161:150 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean hasAttributes()
/* 165:    */   {
/* 166:154 */     return DOMNodeHelper.hasAttributes(this);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getData()
/* 170:    */     throws DOMException
/* 171:    */   {
/* 172:160 */     return DOMNodeHelper.getData(this);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setData(String data)
/* 176:    */     throws DOMException
/* 177:    */   {
/* 178:164 */     DOMNodeHelper.setData(this, data);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getLength()
/* 182:    */   {
/* 183:168 */     return DOMNodeHelper.getLength(this);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String substringData(int offset, int count)
/* 187:    */     throws DOMException
/* 188:    */   {
/* 189:172 */     return DOMNodeHelper.substringData(this, offset, count);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void appendData(String arg)
/* 193:    */     throws DOMException
/* 194:    */   {
/* 195:176 */     DOMNodeHelper.appendData(this, arg);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void insertData(int offset, String arg)
/* 199:    */     throws DOMException
/* 200:    */   {
/* 201:180 */     DOMNodeHelper.insertData(this, offset, arg);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void deleteData(int offset, int count)
/* 205:    */     throws DOMException
/* 206:    */   {
/* 207:184 */     DOMNodeHelper.deleteData(this, offset, count);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void replaceData(int offset, int count, String arg)
/* 211:    */     throws DOMException
/* 212:    */   {
/* 213:189 */     DOMNodeHelper.replaceData(this, offset, count, arg);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public org.w3c.dom.Text splitText(int offset)
/* 217:    */     throws DOMException
/* 218:    */   {
/* 219:195 */     if (isReadOnly()) {
/* 220:196 */       throw new DOMException((short)7, "CharacterData node is read only: " + this);
/* 221:    */     }
/* 222:199 */     String text = getText();
/* 223:200 */     int length = text != null ? text.length() : 0;
/* 224:202 */     if ((offset < 0) || (offset >= length)) {
/* 225:203 */       throw new DOMException((short)1, "No text at offset: " + offset);
/* 226:    */     }
/* 227:206 */     String start = text.substring(0, offset);
/* 228:207 */     String rest = text.substring(offset);
/* 229:208 */     setText(start);
/* 230:    */     
/* 231:210 */     Element parent = getParent();
/* 232:211 */     org.dom4j.Text newText = createText(rest);
/* 233:213 */     if (parent != null) {
/* 234:214 */       parent.add(newText);
/* 235:    */     }
/* 236:217 */     return DOMNodeHelper.asDOMText(newText);
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected org.dom4j.Text createText(String text)
/* 240:    */   {
/* 241:225 */     return new DOMText(text);
/* 242:    */   }
/* 243:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMText
 * JD-Core Version:    0.7.0.1
 */