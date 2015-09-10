/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import org.dom4j.CDATA;
/*   4:    */ import org.dom4j.Element;
/*   5:    */ import org.dom4j.tree.DefaultCDATA;
/*   6:    */ import org.w3c.dom.CDATASection;
/*   7:    */ import org.w3c.dom.DOMException;
/*   8:    */ import org.w3c.dom.Document;
/*   9:    */ import org.w3c.dom.NamedNodeMap;
/*  10:    */ import org.w3c.dom.Node;
/*  11:    */ import org.w3c.dom.NodeList;
/*  12:    */ import org.w3c.dom.Text;
/*  13:    */ 
/*  14:    */ public class DOMCDATA
/*  15:    */   extends DefaultCDATA
/*  16:    */   implements CDATASection
/*  17:    */ {
/*  18:    */   public DOMCDATA(String text)
/*  19:    */   {
/*  20: 30 */     super(text);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public DOMCDATA(Element parent, String text)
/*  24:    */   {
/*  25: 34 */     super(parent, text);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean supports(String feature, String version)
/*  29:    */   {
/*  30: 40 */     return DOMNodeHelper.supports(this, feature, version);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getNamespaceURI()
/*  34:    */   {
/*  35: 44 */     return DOMNodeHelper.getNamespaceURI(this);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getPrefix()
/*  39:    */   {
/*  40: 48 */     return DOMNodeHelper.getPrefix(this);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setPrefix(String prefix)
/*  44:    */     throws DOMException
/*  45:    */   {
/*  46: 52 */     DOMNodeHelper.setPrefix(this, prefix);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getLocalName()
/*  50:    */   {
/*  51: 56 */     return DOMNodeHelper.getLocalName(this);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getNodeName()
/*  55:    */   {
/*  56: 60 */     return "#cdata-section";
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getNodeValue()
/*  60:    */     throws DOMException
/*  61:    */   {
/*  62: 67 */     return DOMNodeHelper.getNodeValue(this);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setNodeValue(String nodeValue)
/*  66:    */     throws DOMException
/*  67:    */   {
/*  68: 71 */     DOMNodeHelper.setNodeValue(this, nodeValue);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Node getParentNode()
/*  72:    */   {
/*  73: 75 */     return DOMNodeHelper.getParentNode(this);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public NodeList getChildNodes()
/*  77:    */   {
/*  78: 79 */     return DOMNodeHelper.getChildNodes(this);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Node getFirstChild()
/*  82:    */   {
/*  83: 83 */     return DOMNodeHelper.getFirstChild(this);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Node getLastChild()
/*  87:    */   {
/*  88: 87 */     return DOMNodeHelper.getLastChild(this);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Node getPreviousSibling()
/*  92:    */   {
/*  93: 91 */     return DOMNodeHelper.getPreviousSibling(this);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Node getNextSibling()
/*  97:    */   {
/*  98: 95 */     return DOMNodeHelper.getNextSibling(this);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public NamedNodeMap getAttributes()
/* 102:    */   {
/* 103: 99 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Document getOwnerDocument()
/* 107:    */   {
/* 108:103 */     return DOMNodeHelper.getOwnerDocument(this);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Node insertBefore(Node newChild, Node refChild)
/* 112:    */     throws DOMException
/* 113:    */   {
/* 114:108 */     checkNewChildNode(newChild);
/* 115:    */     
/* 116:110 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 120:    */     throws DOMException
/* 121:    */   {
/* 122:115 */     checkNewChildNode(newChild);
/* 123:    */     
/* 124:117 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Node removeChild(Node oldChild)
/* 128:    */     throws DOMException
/* 129:    */   {
/* 130:122 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Node appendChild(Node newChild)
/* 134:    */     throws DOMException
/* 135:    */   {
/* 136:127 */     checkNewChildNode(newChild);
/* 137:    */     
/* 138:129 */     return DOMNodeHelper.appendChild(this, newChild);
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void checkNewChildNode(Node newChild)
/* 142:    */     throws DOMException
/* 143:    */   {
/* 144:134 */     throw new DOMException((short)3, "CDATASection nodes cannot have children");
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean hasChildNodes()
/* 148:    */   {
/* 149:139 */     return DOMNodeHelper.hasChildNodes(this);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Node cloneNode(boolean deep)
/* 153:    */   {
/* 154:143 */     return DOMNodeHelper.cloneNode(this, deep);
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void normalize()
/* 158:    */   {
/* 159:147 */     DOMNodeHelper.normalize(this);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public boolean isSupported(String feature, String version)
/* 163:    */   {
/* 164:151 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean hasAttributes()
/* 168:    */   {
/* 169:155 */     return DOMNodeHelper.hasAttributes(this);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getData()
/* 173:    */     throws DOMException
/* 174:    */   {
/* 175:161 */     return DOMNodeHelper.getData(this);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setData(String data)
/* 179:    */     throws DOMException
/* 180:    */   {
/* 181:165 */     DOMNodeHelper.setData(this, data);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public int getLength()
/* 185:    */   {
/* 186:169 */     return DOMNodeHelper.getLength(this);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String substringData(int offset, int count)
/* 190:    */     throws DOMException
/* 191:    */   {
/* 192:173 */     return DOMNodeHelper.substringData(this, offset, count);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void appendData(String arg)
/* 196:    */     throws DOMException
/* 197:    */   {
/* 198:177 */     DOMNodeHelper.appendData(this, arg);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void insertData(int offset, String arg)
/* 202:    */     throws DOMException
/* 203:    */   {
/* 204:181 */     DOMNodeHelper.insertData(this, offset, arg);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void deleteData(int offset, int count)
/* 208:    */     throws DOMException
/* 209:    */   {
/* 210:185 */     DOMNodeHelper.deleteData(this, offset, count);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void replaceData(int offset, int count, String arg)
/* 214:    */     throws DOMException
/* 215:    */   {
/* 216:190 */     DOMNodeHelper.replaceData(this, offset, count, arg);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Text splitText(int offset)
/* 220:    */     throws DOMException
/* 221:    */   {
/* 222:196 */     if (isReadOnly()) {
/* 223:197 */       throw new DOMException((short)7, "CharacterData node is read only: " + this);
/* 224:    */     }
/* 225:200 */     String text = getText();
/* 226:201 */     int length = text != null ? text.length() : 0;
/* 227:203 */     if ((offset < 0) || (offset >= length)) {
/* 228:204 */       throw new DOMException((short)1, "No text at offset: " + offset);
/* 229:    */     }
/* 230:207 */     String start = text.substring(0, offset);
/* 231:208 */     String rest = text.substring(offset);
/* 232:209 */     setText(start);
/* 233:    */     
/* 234:211 */     Element parent = getParent();
/* 235:212 */     CDATA newText = createCDATA(rest);
/* 236:214 */     if (parent != null) {
/* 237:215 */       parent.add(newText);
/* 238:    */     }
/* 239:218 */     return DOMNodeHelper.asDOMText(newText);
/* 240:    */   }
/* 241:    */   
/* 242:    */   protected CDATA createCDATA(String text)
/* 243:    */   {
/* 244:226 */     return new DOMCDATA(text);
/* 245:    */   }
/* 246:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMCDATA
 * JD-Core Version:    0.7.0.1
 */