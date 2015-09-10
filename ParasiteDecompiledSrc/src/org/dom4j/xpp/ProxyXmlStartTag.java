/*   1:    */ package org.dom4j.xpp;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.dom4j.Attribute;
/*   6:    */ import org.dom4j.DocumentFactory;
/*   7:    */ import org.dom4j.Element;
/*   8:    */ import org.dom4j.QName;
/*   9:    */ import org.dom4j.tree.AbstractElement;
/*  10:    */ import org.gjt.xpp.XmlPullParserException;
/*  11:    */ import org.gjt.xpp.XmlStartTag;
/*  12:    */ 
/*  13:    */ public class ProxyXmlStartTag
/*  14:    */   implements XmlStartTag
/*  15:    */ {
/*  16:    */   private Element element;
/*  17: 35 */   private DocumentFactory factory = DocumentFactory.getInstance();
/*  18:    */   
/*  19:    */   public ProxyXmlStartTag() {}
/*  20:    */   
/*  21:    */   public ProxyXmlStartTag(Element element)
/*  22:    */   {
/*  23: 41 */     this.element = element;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void resetStartTag()
/*  27:    */   {
/*  28: 47 */     this.element = null;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getAttributeCount()
/*  32:    */   {
/*  33: 51 */     return this.element != null ? this.element.attributeCount() : 0;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getAttributeNamespaceUri(int index)
/*  37:    */   {
/*  38: 55 */     if (this.element != null)
/*  39:    */     {
/*  40: 56 */       Attribute attribute = this.element.attribute(index);
/*  41: 58 */       if (attribute != null) {
/*  42: 59 */         return attribute.getNamespaceURI();
/*  43:    */       }
/*  44:    */     }
/*  45: 63 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getAttributeLocalName(int index)
/*  49:    */   {
/*  50: 67 */     if (this.element != null)
/*  51:    */     {
/*  52: 68 */       Attribute attribute = this.element.attribute(index);
/*  53: 70 */       if (attribute != null) {
/*  54: 71 */         return attribute.getName();
/*  55:    */       }
/*  56:    */     }
/*  57: 75 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String getAttributePrefix(int index)
/*  61:    */   {
/*  62: 79 */     if (this.element != null)
/*  63:    */     {
/*  64: 80 */       Attribute attribute = this.element.attribute(index);
/*  65: 82 */       if (attribute != null)
/*  66:    */       {
/*  67: 83 */         String prefix = attribute.getNamespacePrefix();
/*  68: 85 */         if ((prefix != null) && (prefix.length() > 0)) {
/*  69: 86 */           return prefix;
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73: 91 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getAttributeRawName(int index)
/*  77:    */   {
/*  78: 95 */     if (this.element != null)
/*  79:    */     {
/*  80: 96 */       Attribute attribute = this.element.attribute(index);
/*  81: 98 */       if (attribute != null) {
/*  82: 99 */         return attribute.getQualifiedName();
/*  83:    */       }
/*  84:    */     }
/*  85:103 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getAttributeValue(int index)
/*  89:    */   {
/*  90:107 */     if (this.element != null)
/*  91:    */     {
/*  92:108 */       Attribute attribute = this.element.attribute(index);
/*  93:110 */       if (attribute != null) {
/*  94:111 */         return attribute.getValue();
/*  95:    */       }
/*  96:    */     }
/*  97:115 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getAttributeValueFromRawName(String rawName)
/* 101:    */   {
/* 102:    */     Iterator iter;
/* 103:119 */     if (this.element != null) {
/* 104:120 */       for (iter = this.element.attributeIterator(); iter.hasNext();)
/* 105:    */       {
/* 106:121 */         Attribute attribute = (Attribute)iter.next();
/* 107:123 */         if (rawName.equals(attribute.getQualifiedName())) {
/* 108:124 */           return attribute.getValue();
/* 109:    */         }
/* 110:    */       }
/* 111:    */     }
/* 112:129 */     return null;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getAttributeValueFromName(String namespaceURI, String localName)
/* 116:    */   {
/* 117:    */     Iterator iter;
/* 118:134 */     if (this.element != null) {
/* 119:135 */       for (iter = this.element.attributeIterator(); iter.hasNext();)
/* 120:    */       {
/* 121:136 */         Attribute attribute = (Attribute)iter.next();
/* 122:138 */         if ((namespaceURI.equals(attribute.getNamespaceURI())) && (localName.equals(attribute.getName()))) {
/* 123:140 */           return attribute.getValue();
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:145 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isAttributeNamespaceDeclaration(int index)
/* 131:    */   {
/* 132:149 */     if (this.element != null)
/* 133:    */     {
/* 134:150 */       Attribute attribute = this.element.attribute(index);
/* 135:152 */       if (attribute != null) {
/* 136:153 */         return "xmlns".equals(attribute.getNamespacePrefix());
/* 137:    */       }
/* 138:    */     }
/* 139:157 */     return false;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void addAttribute(String namespaceURI, String localName, String rawName, String value)
/* 143:    */     throws XmlPullParserException
/* 144:    */   {
/* 145:172 */     QName qname = QName.get(rawName, namespaceURI);
/* 146:173 */     this.element.addAttribute(qname, value);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void addAttribute(String namespaceURI, String localName, String rawName, String value, boolean isNamespaceDeclaration)
/* 150:    */     throws XmlPullParserException
/* 151:    */   {
/* 152:179 */     if (isNamespaceDeclaration)
/* 153:    */     {
/* 154:180 */       String prefix = "";
/* 155:181 */       int idx = rawName.indexOf(':');
/* 156:183 */       if (idx > 0) {
/* 157:184 */         prefix = rawName.substring(0, idx);
/* 158:    */       }
/* 159:187 */       this.element.addNamespace(prefix, namespaceURI);
/* 160:    */     }
/* 161:    */     else
/* 162:    */     {
/* 163:189 */       QName qname = QName.get(rawName, namespaceURI);
/* 164:190 */       this.element.addAttribute(qname, value);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void ensureAttributesCapacity(int minCapacity)
/* 169:    */     throws XmlPullParserException
/* 170:    */   {
/* 171:196 */     if ((this.element instanceof AbstractElement))
/* 172:    */     {
/* 173:197 */       AbstractElement elementImpl = (AbstractElement)this.element;
/* 174:198 */       elementImpl.ensureAttributesCapacity(minCapacity);
/* 175:    */     }
/* 176:    */   }
/* 177:    */   
/* 178:    */   /**
/* 179:    */    * @deprecated
/* 180:    */    */
/* 181:    */   public void removeAtttributes()
/* 182:    */     throws XmlPullParserException
/* 183:    */   {
/* 184:208 */     removeAttributes();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void removeAttributes()
/* 188:    */     throws XmlPullParserException
/* 189:    */   {
/* 190:212 */     if (this.element != null) {
/* 191:213 */       this.element.setAttributes(new ArrayList());
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getLocalName()
/* 196:    */   {
/* 197:222 */     return this.element.getName();
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getNamespaceUri()
/* 201:    */   {
/* 202:226 */     return this.element.getNamespaceURI();
/* 203:    */   }
/* 204:    */   
/* 205:    */   public String getPrefix()
/* 206:    */   {
/* 207:230 */     return this.element.getNamespacePrefix();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getRawName()
/* 211:    */   {
/* 212:234 */     return this.element.getQualifiedName();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void modifyTag(String namespaceURI, String lName, String rawName)
/* 216:    */   {
/* 217:238 */     this.element = this.factory.createElement(rawName, namespaceURI);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void resetTag()
/* 221:    */   {
/* 222:242 */     this.element = null;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean removeAttributeByName(String namespaceURI, String localName)
/* 226:    */     throws XmlPullParserException
/* 227:    */   {
/* 228:247 */     if (this.element != null)
/* 229:    */     {
/* 230:248 */       QName qname = QName.get(localName, namespaceURI);
/* 231:249 */       Attribute attribute = this.element.attribute(qname);
/* 232:250 */       return this.element.remove(attribute);
/* 233:    */     }
/* 234:252 */     return false;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public boolean removeAttributeByRawName(String rawName)
/* 238:    */     throws XmlPullParserException
/* 239:    */   {
/* 240:257 */     if (this.element != null)
/* 241:    */     {
/* 242:258 */       Attribute attribute = null;
/* 243:259 */       Iterator it = this.element.attributeIterator();
/* 244:260 */       while (it.hasNext())
/* 245:    */       {
/* 246:261 */         Attribute current = (Attribute)it.next();
/* 247:262 */         if (current.getQualifiedName().equals(rawName))
/* 248:    */         {
/* 249:263 */           attribute = current;
/* 250:264 */           break;
/* 251:    */         }
/* 252:    */       }
/* 253:267 */       return this.element.remove(attribute);
/* 254:    */     }
/* 255:269 */     return false;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public DocumentFactory getDocumentFactory()
/* 259:    */   {
/* 260:275 */     return this.factory;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setDocumentFactory(DocumentFactory documentFactory)
/* 264:    */   {
/* 265:279 */     this.factory = documentFactory;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Element getElement()
/* 269:    */   {
/* 270:283 */     return this.element;
/* 271:    */   }
/* 272:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.xpp.ProxyXmlStartTag
 * JD-Core Version:    0.7.0.1
 */