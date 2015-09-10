/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.dom4j.Attribute;
/*   6:    */ import org.dom4j.DocumentFactory;
/*   7:    */ import org.dom4j.Namespace;
/*   8:    */ import org.dom4j.QName;
/*   9:    */ import org.dom4j.tree.DefaultElement;
/*  10:    */ import org.w3c.dom.Attr;
/*  11:    */ import org.w3c.dom.DOMException;
/*  12:    */ import org.w3c.dom.Document;
/*  13:    */ import org.w3c.dom.Element;
/*  14:    */ import org.w3c.dom.NamedNodeMap;
/*  15:    */ import org.w3c.dom.Node;
/*  16:    */ import org.w3c.dom.NodeList;
/*  17:    */ 
/*  18:    */ public class DOMElement
/*  19:    */   extends DefaultElement
/*  20:    */   implements Element
/*  21:    */ {
/*  22: 36 */   private static final DocumentFactory DOCUMENT_FACTORY = ;
/*  23:    */   
/*  24:    */   public DOMElement(String name)
/*  25:    */   {
/*  26: 40 */     super(name);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public DOMElement(QName qname)
/*  30:    */   {
/*  31: 44 */     super(qname);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DOMElement(QName qname, int attributeCount)
/*  35:    */   {
/*  36: 48 */     super(qname, attributeCount);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public DOMElement(String name, Namespace namespace)
/*  40:    */   {
/*  41: 52 */     super(name, namespace);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean supports(String feature, String version)
/*  45:    */   {
/*  46: 58 */     return DOMNodeHelper.supports(this, feature, version);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getNamespaceURI()
/*  50:    */   {
/*  51: 62 */     return getQName().getNamespaceURI();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getPrefix()
/*  55:    */   {
/*  56: 66 */     return getQName().getNamespacePrefix();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setPrefix(String prefix)
/*  60:    */     throws DOMException
/*  61:    */   {
/*  62: 70 */     DOMNodeHelper.setPrefix(this, prefix);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getLocalName()
/*  66:    */   {
/*  67: 74 */     return getQName().getName();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getNodeName()
/*  71:    */   {
/*  72: 78 */     return getName();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getNodeValue()
/*  76:    */     throws DOMException
/*  77:    */   {
/*  78: 85 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setNodeValue(String nodeValue)
/*  82:    */     throws DOMException
/*  83:    */   {}
/*  84:    */   
/*  85:    */   public Node getParentNode()
/*  86:    */   {
/*  87: 92 */     return DOMNodeHelper.getParentNode(this);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public NodeList getChildNodes()
/*  91:    */   {
/*  92: 96 */     return DOMNodeHelper.createNodeList(content());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Node getFirstChild()
/*  96:    */   {
/*  97:100 */     return DOMNodeHelper.asDOMNode(node(0));
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Node getLastChild()
/* 101:    */   {
/* 102:104 */     return DOMNodeHelper.asDOMNode(node(nodeCount() - 1));
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Node getPreviousSibling()
/* 106:    */   {
/* 107:108 */     return DOMNodeHelper.getPreviousSibling(this);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Node getNextSibling()
/* 111:    */   {
/* 112:112 */     return DOMNodeHelper.getNextSibling(this);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public NamedNodeMap getAttributes()
/* 116:    */   {
/* 117:116 */     return new DOMAttributeNodeMap(this);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Document getOwnerDocument()
/* 121:    */   {
/* 122:120 */     return DOMNodeHelper.getOwnerDocument(this);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Node insertBefore(Node newChild, Node refChild)
/* 126:    */     throws DOMException
/* 127:    */   {
/* 128:125 */     checkNewChildNode(newChild);
/* 129:    */     
/* 130:127 */     return DOMNodeHelper.insertBefore(this, newChild, refChild);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Node replaceChild(Node newChild, Node oldChild)
/* 134:    */     throws DOMException
/* 135:    */   {
/* 136:132 */     checkNewChildNode(newChild);
/* 137:    */     
/* 138:134 */     return DOMNodeHelper.replaceChild(this, newChild, oldChild);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Node removeChild(Node oldChild)
/* 142:    */     throws DOMException
/* 143:    */   {
/* 144:139 */     return DOMNodeHelper.removeChild(this, oldChild);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Node appendChild(Node newChild)
/* 148:    */     throws DOMException
/* 149:    */   {
/* 150:144 */     checkNewChildNode(newChild);
/* 151:    */     
/* 152:146 */     return DOMNodeHelper.appendChild(this, newChild);
/* 153:    */   }
/* 154:    */   
/* 155:    */   private void checkNewChildNode(Node newChild)
/* 156:    */     throws DOMException
/* 157:    */   {
/* 158:151 */     int nodeType = newChild.getNodeType();
/* 159:153 */     if ((nodeType != 1) && (nodeType != 3) && (nodeType != 8) && (nodeType != 7) && (nodeType != 4) && (nodeType != 5)) {
/* 160:158 */       throw new DOMException((short)3, "Given node cannot be a child of element");
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean hasChildNodes()
/* 165:    */   {
/* 166:164 */     return nodeCount() > 0;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Node cloneNode(boolean deep)
/* 170:    */   {
/* 171:168 */     return DOMNodeHelper.cloneNode(this, deep);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public boolean isSupported(String feature, String version)
/* 175:    */   {
/* 176:172 */     return DOMNodeHelper.isSupported(this, feature, version);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean hasAttributes()
/* 180:    */   {
/* 181:176 */     return DOMNodeHelper.hasAttributes(this);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String getTagName()
/* 185:    */   {
/* 186:182 */     return getName();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getAttribute(String name)
/* 190:    */   {
/* 191:186 */     String answer = attributeValue(name);
/* 192:    */     
/* 193:188 */     return answer != null ? answer : "";
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setAttribute(String name, String value)
/* 197:    */     throws DOMException
/* 198:    */   {
/* 199:192 */     addAttribute(name, value);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void removeAttribute(String name)
/* 203:    */     throws DOMException
/* 204:    */   {
/* 205:196 */     Attribute attribute = attribute(name);
/* 206:198 */     if (attribute != null) {
/* 207:199 */       remove(attribute);
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public Attr getAttributeNode(String name)
/* 212:    */   {
/* 213:204 */     return DOMNodeHelper.asDOMAttr(attribute(name));
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Attr setAttributeNode(Attr newAttr)
/* 217:    */     throws DOMException
/* 218:    */   {
/* 219:209 */     if (isReadOnly()) {
/* 220:210 */       throw new DOMException((short)7, "No modification allowed");
/* 221:    */     }
/* 222:214 */     Attribute attribute = attribute(newAttr);
/* 223:216 */     if (attribute != newAttr)
/* 224:    */     {
/* 225:217 */       if (newAttr.getOwnerElement() != null) {
/* 226:218 */         throw new DOMException((short)10, "Attribute is already in use");
/* 227:    */       }
/* 228:222 */       Attribute newAttribute = createAttribute(newAttr);
/* 229:224 */       if (attribute != null) {
/* 230:225 */         attribute.detach();
/* 231:    */       }
/* 232:228 */       add(newAttribute);
/* 233:    */     }
/* 234:231 */     return DOMNodeHelper.asDOMAttr(attribute);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Attr removeAttributeNode(Attr oldAttr)
/* 238:    */     throws DOMException
/* 239:    */   {
/* 240:236 */     Attribute attribute = attribute(oldAttr);
/* 241:238 */     if (attribute != null)
/* 242:    */     {
/* 243:239 */       attribute.detach();
/* 244:    */       
/* 245:241 */       return DOMNodeHelper.asDOMAttr(attribute);
/* 246:    */     }
/* 247:243 */     throw new DOMException((short)8, "No such attribute");
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String getAttributeNS(String namespaceURI, String localName)
/* 251:    */   {
/* 252:249 */     Attribute attribute = attribute(namespaceURI, localName);
/* 253:251 */     if (attribute != null)
/* 254:    */     {
/* 255:252 */       String answer = attribute.getValue();
/* 256:254 */       if (answer != null) {
/* 257:255 */         return answer;
/* 258:    */       }
/* 259:    */     }
/* 260:259 */     return "";
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String value)
/* 264:    */     throws DOMException
/* 265:    */   {
/* 266:264 */     Attribute attribute = attribute(namespaceURI, qualifiedName);
/* 267:266 */     if (attribute != null)
/* 268:    */     {
/* 269:267 */       attribute.setValue(value);
/* 270:    */     }
/* 271:    */     else
/* 272:    */     {
/* 273:269 */       QName qname = getQName(namespaceURI, qualifiedName);
/* 274:270 */       addAttribute(qname, value);
/* 275:    */     }
/* 276:    */   }
/* 277:    */   
/* 278:    */   public void removeAttributeNS(String namespaceURI, String localName)
/* 279:    */     throws DOMException
/* 280:    */   {
/* 281:276 */     Attribute attribute = attribute(namespaceURI, localName);
/* 282:278 */     if (attribute != null) {
/* 283:279 */       remove(attribute);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Attr getAttributeNodeNS(String namespaceURI, String localName)
/* 288:    */   {
/* 289:285 */     Attribute attribute = attribute(namespaceURI, localName);
/* 290:287 */     if (attribute != null) {
/* 291:288 */       DOMNodeHelper.asDOMAttr(attribute);
/* 292:    */     }
/* 293:291 */     return null;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public Attr setAttributeNodeNS(Attr newAttr)
/* 297:    */     throws DOMException
/* 298:    */   {
/* 299:296 */     Attribute attribute = attribute(newAttr.getNamespaceURI(), newAttr.getLocalName());
/* 300:299 */     if (attribute != null)
/* 301:    */     {
/* 302:300 */       attribute.setValue(newAttr.getValue());
/* 303:    */     }
/* 304:    */     else
/* 305:    */     {
/* 306:302 */       attribute = createAttribute(newAttr);
/* 307:303 */       add(attribute);
/* 308:    */     }
/* 309:306 */     return DOMNodeHelper.asDOMAttr(attribute);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public NodeList getElementsByTagName(String name)
/* 313:    */   {
/* 314:310 */     ArrayList list = new ArrayList();
/* 315:311 */     DOMNodeHelper.appendElementsByTagName(list, this, name);
/* 316:    */     
/* 317:313 */     return DOMNodeHelper.createNodeList(list);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public NodeList getElementsByTagNameNS(String namespace, String lName)
/* 321:    */   {
/* 322:317 */     ArrayList list = new ArrayList();
/* 323:318 */     DOMNodeHelper.appendElementsByTagNameNS(list, this, namespace, lName);
/* 324:    */     
/* 325:320 */     return DOMNodeHelper.createNodeList(list);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public boolean hasAttribute(String name)
/* 329:    */   {
/* 330:324 */     return attribute(name) != null;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public boolean hasAttributeNS(String namespaceURI, String localName)
/* 334:    */   {
/* 335:328 */     return attribute(namespaceURI, localName) != null;
/* 336:    */   }
/* 337:    */   
/* 338:    */   protected DocumentFactory getDocumentFactory()
/* 339:    */   {
/* 340:334 */     DocumentFactory factory = getQName().getDocumentFactory();
/* 341:    */     
/* 342:336 */     return factory != null ? factory : DOCUMENT_FACTORY;
/* 343:    */   }
/* 344:    */   
/* 345:    */   protected Attribute attribute(Attr attr)
/* 346:    */   {
/* 347:340 */     return attribute(DOCUMENT_FACTORY.createQName(attr.getLocalName(), attr.getPrefix(), attr.getNamespaceURI()));
/* 348:    */   }
/* 349:    */   
/* 350:    */   protected Attribute attribute(String namespaceURI, String localName)
/* 351:    */   {
/* 352:345 */     List attributes = attributeList();
/* 353:346 */     int size = attributes.size();
/* 354:348 */     for (int i = 0; i < size; i++)
/* 355:    */     {
/* 356:349 */       Attribute attribute = (Attribute)attributes.get(i);
/* 357:351 */       if ((localName.equals(attribute.getName())) && (((namespaceURI != null) && (namespaceURI.length() != 0)) || ((attribute.getNamespaceURI() == null) || (attribute.getNamespaceURI().length() == 0) || ((namespaceURI != null) && (namespaceURI.equals(attribute.getNamespaceURI())))))) {
/* 358:357 */         return attribute;
/* 359:    */       }
/* 360:    */     }
/* 361:361 */     return null;
/* 362:    */   }
/* 363:    */   
/* 364:    */   protected Attribute createAttribute(Attr newAttr)
/* 365:    */   {
/* 366:365 */     QName qname = null;
/* 367:366 */     String name = newAttr.getLocalName();
/* 368:368 */     if (name != null)
/* 369:    */     {
/* 370:369 */       String prefix = newAttr.getPrefix();
/* 371:370 */       String uri = newAttr.getNamespaceURI();
/* 372:371 */       qname = getDocumentFactory().createQName(name, prefix, uri);
/* 373:    */     }
/* 374:    */     else
/* 375:    */     {
/* 376:373 */       name = newAttr.getName();
/* 377:374 */       qname = getDocumentFactory().createQName(name);
/* 378:    */     }
/* 379:377 */     return new DOMAttribute(qname, newAttr.getValue());
/* 380:    */   }
/* 381:    */   
/* 382:    */   protected QName getQName(String namespace, String qualifiedName)
/* 383:    */   {
/* 384:381 */     int index = qualifiedName.indexOf(':');
/* 385:382 */     String prefix = "";
/* 386:383 */     String localName = qualifiedName;
/* 387:385 */     if (index >= 0)
/* 388:    */     {
/* 389:386 */       prefix = qualifiedName.substring(0, index);
/* 390:387 */       localName = qualifiedName.substring(index + 1);
/* 391:    */     }
/* 392:390 */     return getDocumentFactory().createQName(localName, prefix, namespace);
/* 393:    */   }
/* 394:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMElement
 * JD-Core Version:    0.7.0.1
 */