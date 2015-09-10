/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   4:    */ import com.gargoylesoftware.htmlunit.util.StringUtils;
/*   5:    */ import java.io.PrintWriter;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import org.w3c.dom.Attr;
/*  10:    */ import org.w3c.dom.Element;
/*  11:    */ import org.w3c.dom.NamedNodeMap;
/*  12:    */ import org.w3c.dom.TypeInfo;
/*  13:    */ 
/*  14:    */ public class DomElement
/*  15:    */   extends DomNamespaceNode
/*  16:    */   implements Element
/*  17:    */ {
/*  18: 45 */   public static final String ATTRIBUTE_NOT_DEFINED = new String("");
/*  19: 48 */   public static final String ATTRIBUTE_VALUE_EMPTY = new String();
/*  20: 51 */   private NamedAttrNodeMapImpl attributes_ = new NamedAttrNodeMapImpl(this, isAttributeCaseSensitive());
/*  21: 54 */   private Map<String, String> namespaces_ = new HashMap();
/*  22:    */   
/*  23:    */   public DomElement(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  24:    */   {
/*  25: 67 */     super(namespaceURI, qualifiedName, page);
/*  26: 68 */     if ((attributes != null) && (!attributes.isEmpty()))
/*  27:    */     {
/*  28: 69 */       this.attributes_ = new NamedAttrNodeMapImpl(this, isAttributeCaseSensitive(), attributes);
/*  29: 70 */       for (DomAttr entry : this.attributes_.values())
/*  30:    */       {
/*  31: 71 */         entry.setParentNode(this);
/*  32: 72 */         String attrNamespaceURI = entry.getNamespaceURI();
/*  33: 73 */         if (attrNamespaceURI != null) {
/*  34: 74 */           this.namespaces_.put(attrNamespaceURI, entry.getPrefix());
/*  35:    */         }
/*  36:    */       }
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String getNodeName()
/*  41:    */   {
/*  42: 85 */     return getQualifiedName();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final short getNodeType()
/*  46:    */   {
/*  47: 93 */     return 1;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected Map<String, String> namespaces()
/*  51:    */   {
/*  52:102 */     return this.namespaces_;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final String getTagName()
/*  56:    */   {
/*  57:110 */     return getNodeName();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final boolean hasAttributes()
/*  61:    */   {
/*  62:118 */     return !this.attributes_.isEmpty();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean hasAttribute(String attributeName)
/*  66:    */   {
/*  67:129 */     return this.attributes_.containsKey(attributeName);
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected void printOpeningTagContentAsXml(PrintWriter printWriter)
/*  71:    */   {
/*  72:138 */     printWriter.print(getTagName());
/*  73:139 */     for (Map.Entry<String, DomAttr> entry : this.attributes_.entrySet())
/*  74:    */     {
/*  75:140 */       printWriter.print(" ");
/*  76:141 */       printWriter.print((String)entry.getKey());
/*  77:142 */       printWriter.print("=\"");
/*  78:143 */       printWriter.print(StringUtils.escapeXmlAttributeValue(((DomAttr)entry.getValue()).getNodeValue()));
/*  79:144 */       printWriter.print("\"");
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected void printXml(String indent, PrintWriter printWriter)
/*  84:    */   {
/*  85:156 */     boolean hasChildren = getFirstChild() != null;
/*  86:157 */     printWriter.print(indent + "<");
/*  87:158 */     printOpeningTagContentAsXml(printWriter);
/*  88:160 */     if ((!hasChildren) && (!isEmptyXmlTagExpanded()))
/*  89:    */     {
/*  90:161 */       printWriter.println("/>");
/*  91:    */     }
/*  92:    */     else
/*  93:    */     {
/*  94:164 */       printWriter.println(">");
/*  95:165 */       printChildrenAsXml(indent, printWriter);
/*  96:166 */       printWriter.println(indent + "</" + getTagName() + ">");
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected boolean isEmptyXmlTagExpanded()
/* 101:    */   {
/* 102:176 */     return false;
/* 103:    */   }
/* 104:    */   
/* 105:    */   String getQualifiedName(String namespaceURI, String localName)
/* 106:    */   {
/* 107:    */     String qualifiedName;
/* 108:    */     String qualifiedName;
/* 109:189 */     if (namespaceURI != null)
/* 110:    */     {
/* 111:190 */       String prefix = (String)namespaces().get(namespaceURI);
/* 112:    */       String qualifiedName;
/* 113:191 */       if (prefix != null) {
/* 114:192 */         qualifiedName = prefix + ':' + localName;
/* 115:    */       } else {
/* 116:195 */         qualifiedName = null;
/* 117:    */       }
/* 118:    */     }
/* 119:    */     else
/* 120:    */     {
/* 121:199 */       qualifiedName = localName;
/* 122:    */     }
/* 123:201 */     return qualifiedName;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getAttribute(String attributeName)
/* 127:    */   {
/* 128:214 */     DomAttr attr = this.attributes_.get(attributeName);
/* 129:215 */     if (attr != null) {
/* 130:216 */       return attr.getNodeValue();
/* 131:    */     }
/* 132:218 */     return ATTRIBUTE_NOT_DEFINED;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void removeAttribute(String attributeName)
/* 136:    */   {
/* 137:226 */     this.attributes_.remove(attributeName.toLowerCase());
/* 138:    */   }
/* 139:    */   
/* 140:    */   public final void removeAttributeNS(String namespaceURI, String localName)
/* 141:    */   {
/* 142:235 */     String qualifiedName = getQualifiedName(namespaceURI, localName);
/* 143:236 */     if (qualifiedName != null) {
/* 144:237 */       removeAttribute(qualifiedName);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public final Attr removeAttributeNode(Attr attribute)
/* 149:    */   {
/* 150:246 */     throw new UnsupportedOperationException("DomElement.removeAttributeNode is not yet implemented.");
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final boolean hasAttributeNS(String namespaceURI, String localName)
/* 154:    */   {
/* 155:258 */     String qualifiedName = getQualifiedName(namespaceURI, localName);
/* 156:259 */     if (qualifiedName != null) {
/* 157:260 */       return this.attributes_.get(qualifiedName) != null;
/* 158:    */     }
/* 159:262 */     return false;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public final Map<String, DomAttr> getAttributesMap()
/* 163:    */   {
/* 164:270 */     return this.attributes_;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public NamedNodeMap getAttributes()
/* 168:    */   {
/* 169:278 */     return this.attributes_;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public final void setAttribute(String attributeName, String attributeValue)
/* 173:    */   {
/* 174:288 */     setAttributeNS(null, attributeName, attributeValue);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/* 178:    */   {
/* 179:300 */     String value = attributeValue;
/* 180:301 */     DomAttr newAttr = new DomAttr(getPage(), namespaceURI, qualifiedName, value, true);
/* 181:302 */     newAttr.setParentNode(this);
/* 182:303 */     this.attributes_.put(qualifiedName, newAttr);
/* 183:305 */     if (namespaceURI != null) {
/* 184:306 */       namespaces().put(namespaceURI, newAttr.getPrefix());
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   protected boolean isAttributeCaseSensitive()
/* 189:    */   {
/* 190:315 */     return true;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public final String getAttributeNS(String namespaceURI, String localName)
/* 194:    */   {
/* 195:329 */     String qualifiedName = getQualifiedName(namespaceURI, localName);
/* 196:330 */     if (qualifiedName != null) {
/* 197:331 */       return getAttribute(qualifiedName);
/* 198:    */     }
/* 199:333 */     return ATTRIBUTE_NOT_DEFINED;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public DomAttr getAttributeNode(String name)
/* 203:    */   {
/* 204:340 */     return this.attributes_.get(name);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public DomAttr getAttributeNodeNS(String namespaceURI, String localName)
/* 208:    */   {
/* 209:347 */     String qualifiedName = getQualifiedName(namespaceURI, localName);
/* 210:348 */     if (qualifiedName != null) {
/* 211:349 */       return this.attributes_.get(qualifiedName);
/* 212:    */     }
/* 213:351 */     return null;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public DomNodeList<HtmlElement> getElementsByTagName(String tagName)
/* 217:    */   {
/* 218:358 */     return new XPathDomNodeList(this, ".//*[local-name()='" + tagName + "']");
/* 219:    */   }
/* 220:    */   
/* 221:    */   public DomNodeList<HtmlElement> getElementsByTagNameNS(String namespace, String localName)
/* 222:    */   {
/* 223:366 */     throw new UnsupportedOperationException("DomElement.getElementsByTagNameNS is not yet implemented.");
/* 224:    */   }
/* 225:    */   
/* 226:    */   public TypeInfo getSchemaTypeInfo()
/* 227:    */   {
/* 228:374 */     throw new UnsupportedOperationException("DomElement.getSchemaTypeInfo is not yet implemented.");
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setIdAttribute(String name, boolean isId)
/* 232:    */   {
/* 233:382 */     throw new UnsupportedOperationException("DomElement.setIdAttribute is not yet implemented.");
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIdAttributeNS(String namespaceURI, String localName, boolean isId)
/* 237:    */   {
/* 238:390 */     throw new UnsupportedOperationException("DomElement.setIdAttributeNS is not yet implemented.");
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Attr setAttributeNode(Attr attribute)
/* 242:    */   {
/* 243:397 */     this.attributes_.setNamedItem(attribute);
/* 244:398 */     return null;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Attr setAttributeNodeNS(Attr attribute)
/* 248:    */   {
/* 249:406 */     throw new UnsupportedOperationException("DomElement.setAttributeNodeNS is not yet implemented.");
/* 250:    */   }
/* 251:    */   
/* 252:    */   public final void setIdAttributeNode(Attr idAttr, boolean isId)
/* 253:    */   {
/* 254:414 */     throw new UnsupportedOperationException("DomElement.setIdAttributeNode is not yet implemented.");
/* 255:    */   }
/* 256:    */   
/* 257:    */   public DomNode cloneNode(boolean deep)
/* 258:    */   {
/* 259:422 */     DomElement clone = (DomElement)super.cloneNode(deep);
/* 260:423 */     clone.attributes_ = new NamedAttrNodeMapImpl(clone, isAttributeCaseSensitive());
/* 261:424 */     clone.attributes_.putAll(this.attributes_);
/* 262:425 */     return clone;
/* 263:    */   }
/* 264:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DomElement
 * JD-Core Version:    0.7.0.1
 */