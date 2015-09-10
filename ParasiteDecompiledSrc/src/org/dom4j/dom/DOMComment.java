/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import org.dom4j.Element;
/*   4:    */ import org.dom4j.tree.DefaultComment;
/*   5:    */ import org.w3c.dom.Comment;
/*   6:    */ import org.w3c.dom.DOMException;
/*   7:    */ import org.w3c.dom.Document;
/*   8:    */ import org.w3c.dom.NamedNodeMap;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.w3c.dom.NodeList;
/*  11:    */ 
/*  12:    */ public class DOMComment
/*  13:    */   extends DefaultComment
/*  14:    */   implements Comment
/*  15:    */ {
/*  16:    */   public DOMComment(String text)
/*  17:    */   {
/*  18: 28 */     super(text);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public DOMComment(Element parent, String text)
/*  22:    */   {
/*  23: 32 */     super(parent, text);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean supports(String feature, String version)
/*  27:    */   {
/*  28: 38 */     return DOMNodeHelper.supports(this, feature, version);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public String getNamespaceURI()
/*  32:    */   {
/*  33: 42 */     return DOMNodeHelper.getNamespaceURI(this);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getPrefix()
/*  37:    */   {
/*  38: 46 */     return DOMNodeHelper.getPrefix(this);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setPrefix(String prefix)
/*  42:    */     throws DOMException
/*  43:    */   {
/*  44: 50 */     DOMNodeHelper.setPrefix(this, prefix);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getLocalName()
/*  48:    */   {
/*  49: 54 */     return DOMNodeHelper.getLocalName(this);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getNodeName()
/*  53:    */   {
/*  54: 58 */     return "#comment";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getNodeValue()
/*  58:    */     throws DOMException
/*  59:    */   {
/*  60: 65 */     return DOMNodeHelper.getNodeValue(this);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setNodeValue(String nodeValue)
/*  64:    */     throws DOMException
/*  65:    */   {
/*  66: 69 */     DOMNodeHelper.setNodeValue(this, nodeValue);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Node getParentNode()
/*  70:    */   {
/*  71: 73 */     return DOMNodeHelper.getParentNode(this);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public NodeList getChildNodes()
/*  75:    */   {
/*  76: 77 */     return DOMNodeHelper.getChildNodes(this);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Node getFirstChild()
/*  80:    */   {
/*  81: 81 */     return DOMNodeHelper.getFirstChild(this);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Node getLastChild()
/*  85:    */   {
/*  86: 85 */     return DOMNodeHelper.getLastChild(this);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Node getPreviousSibling()
/*  90:    */   {
/*  91: 89 */     return DOMNodeHelper.getPreviousSibling(this);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Node getNextSibling()
/*  95:    */   {
/*  96: 93 */     return DOMNodeHelper.getNextSibling(this);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public NamedNodeMap getAttributes()
/* 100:    */   {
/* 101: 97 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Document getOwnerDocument()
/* 105:    */   {
/* 106:101 */     return DOMNodeHelper.getOwnerDocument(this);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Node insertBefore(Node newChild, Node refChild)
/* 110:    */     throws DOMException
/* 111:    */   {
/* 112:106 */     checkNewChildNode(newChild);
/* 113:    */     
/* 114:108 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 118:    */     throws DOMException
/* 119:    */   {
/* 120:113 */     checkNewChildNode(newChild);
/* 121:    */     
/* 122:115 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Node removeChild(Node oldChild)
/* 126:    */     throws DOMException
/* 127:    */   {
/* 128:120 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Node appendChild(Node newChild)
/* 132:    */     throws DOMException
/* 133:    */   {
/* 134:125 */     checkNewChildNode(newChild);
/* 135:    */     
/* 136:127 */     return DOMNodeHelper.appendChild(this, newChild);
/* 137:    */   }
/* 138:    */   
/* 139:    */   private void checkNewChildNode(Node newChild)
/* 140:    */     throws DOMException
/* 141:    */   {
/* 142:132 */     throw new DOMException((short)3, "Comment nodes cannot have children");
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean hasChildNodes()
/* 146:    */   {
/* 147:137 */     return DOMNodeHelper.hasChildNodes(this);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Node cloneNode(boolean deep)
/* 151:    */   {
/* 152:141 */     return DOMNodeHelper.cloneNode(this, deep);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void normalize()
/* 156:    */   {
/* 157:145 */     DOMNodeHelper.normalize(this);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isSupported(String feature, String version)
/* 161:    */   {
/* 162:149 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean hasAttributes()
/* 166:    */   {
/* 167:153 */     return DOMNodeHelper.hasAttributes(this);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getData()
/* 171:    */     throws DOMException
/* 172:    */   {
/* 173:159 */     return DOMNodeHelper.getData(this);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setData(String data)
/* 177:    */     throws DOMException
/* 178:    */   {
/* 179:163 */     DOMNodeHelper.setData(this, data);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public int getLength()
/* 183:    */   {
/* 184:167 */     return DOMNodeHelper.getLength(this);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String substringData(int offset, int count)
/* 188:    */     throws DOMException
/* 189:    */   {
/* 190:171 */     return DOMNodeHelper.substringData(this, offset, count);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void appendData(String arg)
/* 194:    */     throws DOMException
/* 195:    */   {
/* 196:175 */     DOMNodeHelper.appendData(this, arg);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void insertData(int offset, String arg)
/* 200:    */     throws DOMException
/* 201:    */   {
/* 202:179 */     DOMNodeHelper.insertData(this, offset, arg);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void deleteData(int offset, int count)
/* 206:    */     throws DOMException
/* 207:    */   {
/* 208:183 */     DOMNodeHelper.deleteData(this, offset, count);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void replaceData(int offset, int count, String arg)
/* 212:    */     throws DOMException
/* 213:    */   {
/* 214:188 */     DOMNodeHelper.replaceData(this, offset, count, arg);
/* 215:    */   }
/* 216:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMComment
 * JD-Core Version:    0.7.0.1
 */