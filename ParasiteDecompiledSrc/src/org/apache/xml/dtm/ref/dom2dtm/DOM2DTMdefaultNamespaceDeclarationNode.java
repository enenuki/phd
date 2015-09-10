/*   1:    */ package org.apache.xml.dtm.ref.dom2dtm;
/*   2:    */ 
/*   3:    */ import org.apache.xml.dtm.DTMException;
/*   4:    */ import org.w3c.dom.Attr;
/*   5:    */ import org.w3c.dom.DOMException;
/*   6:    */ import org.w3c.dom.Document;
/*   7:    */ import org.w3c.dom.Element;
/*   8:    */ import org.w3c.dom.NamedNodeMap;
/*   9:    */ import org.w3c.dom.Node;
/*  10:    */ import org.w3c.dom.NodeList;
/*  11:    */ import org.w3c.dom.TypeInfo;
/*  12:    */ import org.w3c.dom.UserDataHandler;
/*  13:    */ 
/*  14:    */ public class DOM2DTMdefaultNamespaceDeclarationNode
/*  15:    */   implements Attr, TypeInfo
/*  16:    */ {
/*  17: 55 */   final String NOT_SUPPORTED_ERR = "Unsupported operation on pseudonode";
/*  18:    */   Element pseudoparent;
/*  19:    */   String prefix;
/*  20:    */   String uri;
/*  21:    */   String nodename;
/*  22:    */   int handle;
/*  23:    */   
/*  24:    */   DOM2DTMdefaultNamespaceDeclarationNode(Element pseudoparent, String prefix, String uri, int handle)
/*  25:    */   {
/*  26: 62 */     this.pseudoparent = pseudoparent;
/*  27: 63 */     this.prefix = prefix;
/*  28: 64 */     this.uri = uri;
/*  29: 65 */     this.handle = handle;
/*  30: 66 */     this.nodename = ("xmlns:" + prefix);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getNodeName()
/*  34:    */   {
/*  35: 68 */     return this.nodename;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getName()
/*  39:    */   {
/*  40: 69 */     return this.nodename;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getNamespaceURI()
/*  44:    */   {
/*  45: 70 */     return "http://www.w3.org/2000/xmlns/";
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getPrefix()
/*  49:    */   {
/*  50: 71 */     return this.prefix;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public String getLocalName()
/*  54:    */   {
/*  55: 72 */     return this.prefix;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getNodeValue()
/*  59:    */   {
/*  60: 73 */     return this.uri;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getValue()
/*  64:    */   {
/*  65: 74 */     return this.uri;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Element getOwnerElement()
/*  69:    */   {
/*  70: 75 */     return this.pseudoparent;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isSupported(String feature, String version)
/*  74:    */   {
/*  75: 77 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean hasChildNodes()
/*  79:    */   {
/*  80: 78 */     return false;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean hasAttributes()
/*  84:    */   {
/*  85: 79 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Node getParentNode()
/*  89:    */   {
/*  90: 80 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Node getFirstChild()
/*  94:    */   {
/*  95: 81 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Node getLastChild()
/*  99:    */   {
/* 100: 82 */     return null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Node getPreviousSibling()
/* 104:    */   {
/* 105: 83 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Node getNextSibling()
/* 109:    */   {
/* 110: 84 */     return null;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean getSpecified()
/* 114:    */   {
/* 115: 85 */     return false;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void normalize() {}
/* 119:    */   
/* 120:    */   public NodeList getChildNodes()
/* 121:    */   {
/* 122: 87 */     return null;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public NamedNodeMap getAttributes()
/* 126:    */   {
/* 127: 88 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public short getNodeType()
/* 131:    */   {
/* 132: 89 */     return 2;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setNodeValue(String value)
/* 136:    */   {
/* 137: 90 */     throw new DTMException("Unsupported operation on pseudonode");
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setValue(String value)
/* 141:    */   {
/* 142: 91 */     throw new DTMException("Unsupported operation on pseudonode");
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setPrefix(String value)
/* 146:    */   {
/* 147: 92 */     throw new DTMException("Unsupported operation on pseudonode");
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Node insertBefore(Node a, Node b)
/* 151:    */   {
/* 152: 93 */     throw new DTMException("Unsupported operation on pseudonode");
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Node replaceChild(Node a, Node b)
/* 156:    */   {
/* 157: 94 */     throw new DTMException("Unsupported operation on pseudonode");
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Node appendChild(Node a)
/* 161:    */   {
/* 162: 95 */     throw new DTMException("Unsupported operation on pseudonode");
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Node removeChild(Node a)
/* 166:    */   {
/* 167: 96 */     throw new DTMException("Unsupported operation on pseudonode");
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Document getOwnerDocument()
/* 171:    */   {
/* 172: 97 */     return this.pseudoparent.getOwnerDocument();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Node cloneNode(boolean deep)
/* 176:    */   {
/* 177: 98 */     throw new DTMException("Unsupported operation on pseudonode");
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getHandleOfNode()
/* 181:    */   {
/* 182:107 */     return this.handle;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getTypeName()
/* 186:    */   {
/* 187:115 */     return null;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getTypeNamespace()
/* 191:    */   {
/* 192:120 */     return null;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean isDerivedFrom(String ns, String localName, int derivationMethod)
/* 196:    */   {
/* 197:126 */     return false;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public TypeInfo getSchemaTypeInfo()
/* 201:    */   {
/* 202:129 */     return this;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public boolean isId()
/* 206:    */   {
/* 207:131 */     return false;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public Object setUserData(String key, Object data, UserDataHandler handler)
/* 211:    */   {
/* 212:149 */     return getOwnerDocument().setUserData(key, data, handler);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Object getUserData(String key)
/* 216:    */   {
/* 217:162 */     return getOwnerDocument().getUserData(key);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Object getFeature(String feature, String version)
/* 221:    */   {
/* 222:188 */     return isSupported(feature, version) ? this : null;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean isEqualNode(Node arg)
/* 226:    */   {
/* 227:234 */     if (arg == this) {
/* 228:235 */       return true;
/* 229:    */     }
/* 230:237 */     if (arg.getNodeType() != getNodeType()) {
/* 231:238 */       return false;
/* 232:    */     }
/* 233:242 */     if (getNodeName() == null)
/* 234:    */     {
/* 235:243 */       if (arg.getNodeName() != null) {
/* 236:244 */         return false;
/* 237:    */       }
/* 238:    */     }
/* 239:247 */     else if (!getNodeName().equals(arg.getNodeName())) {
/* 240:248 */       return false;
/* 241:    */     }
/* 242:251 */     if (getLocalName() == null)
/* 243:    */     {
/* 244:252 */       if (arg.getLocalName() != null) {
/* 245:253 */         return false;
/* 246:    */       }
/* 247:    */     }
/* 248:256 */     else if (!getLocalName().equals(arg.getLocalName())) {
/* 249:257 */       return false;
/* 250:    */     }
/* 251:260 */     if (getNamespaceURI() == null)
/* 252:    */     {
/* 253:261 */       if (arg.getNamespaceURI() != null) {
/* 254:262 */         return false;
/* 255:    */       }
/* 256:    */     }
/* 257:265 */     else if (!getNamespaceURI().equals(arg.getNamespaceURI())) {
/* 258:266 */       return false;
/* 259:    */     }
/* 260:269 */     if (getPrefix() == null)
/* 261:    */     {
/* 262:270 */       if (arg.getPrefix() != null) {
/* 263:271 */         return false;
/* 264:    */       }
/* 265:    */     }
/* 266:274 */     else if (!getPrefix().equals(arg.getPrefix())) {
/* 267:275 */       return false;
/* 268:    */     }
/* 269:278 */     if (getNodeValue() == null)
/* 270:    */     {
/* 271:279 */       if (arg.getNodeValue() != null) {
/* 272:280 */         return false;
/* 273:    */       }
/* 274:    */     }
/* 275:283 */     else if (!getNodeValue().equals(arg.getNodeValue())) {
/* 276:284 */       return false;
/* 277:    */     }
/* 278:297 */     return true;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public String lookupNamespaceURI(String specifiedPrefix)
/* 282:    */   {
/* 283:310 */     short type = getNodeType();
/* 284:311 */     switch (type)
/* 285:    */     {
/* 286:    */     case 1: 
/* 287:314 */       String namespace = getNamespaceURI();
/* 288:315 */       String prefix = getPrefix();
/* 289:316 */       if (namespace != null)
/* 290:    */       {
/* 291:318 */         if ((specifiedPrefix == null) && (prefix == specifiedPrefix)) {
/* 292:320 */           return namespace;
/* 293:    */         }
/* 294:321 */         if ((prefix != null) && (prefix.equals(specifiedPrefix))) {
/* 295:323 */           return namespace;
/* 296:    */         }
/* 297:    */       }
/* 298:326 */       if (hasAttributes())
/* 299:    */       {
/* 300:327 */         NamedNodeMap map = getAttributes();
/* 301:328 */         int length = map.getLength();
/* 302:329 */         for (int i = 0; i < length; i++)
/* 303:    */         {
/* 304:330 */           Node attr = map.item(i);
/* 305:331 */           String attrPrefix = attr.getPrefix();
/* 306:332 */           String value = attr.getNodeValue();
/* 307:333 */           namespace = attr.getNamespaceURI();
/* 308:334 */           if ((namespace != null) && (namespace.equals("http://www.w3.org/2000/xmlns/")))
/* 309:    */           {
/* 310:336 */             if ((specifiedPrefix == null) && (attr.getNodeName().equals("xmlns"))) {
/* 311:339 */               return value;
/* 312:    */             }
/* 313:340 */             if ((attrPrefix != null) && (attrPrefix.equals("xmlns")) && (attr.getLocalName().equals(specifiedPrefix))) {
/* 314:344 */               return value;
/* 315:    */             }
/* 316:    */           }
/* 317:    */         }
/* 318:    */       }
/* 319:356 */       return null;
/* 320:    */     case 6: 
/* 321:    */     case 10: 
/* 322:    */     case 11: 
/* 323:    */     case 12: 
/* 324:370 */       return null;
/* 325:    */     case 2: 
/* 326:372 */       if (getOwnerElement().getNodeType() == 1) {
/* 327:373 */         return getOwnerElement().lookupNamespaceURI(specifiedPrefix);
/* 328:    */       }
/* 329:376 */       return null;
/* 330:    */     }
/* 331:385 */     return null;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public boolean isDefaultNamespace(String namespaceURI)
/* 335:    */   {
/* 336:461 */     return false;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public String lookupPrefix(String namespaceURI)
/* 340:    */   {
/* 341:478 */     if (namespaceURI == null) {
/* 342:479 */       return null;
/* 343:    */     }
/* 344:482 */     short type = getNodeType();
/* 345:484 */     switch (type)
/* 346:    */     {
/* 347:    */     case 6: 
/* 348:    */     case 10: 
/* 349:    */     case 11: 
/* 350:    */     case 12: 
/* 351:501 */       return null;
/* 352:    */     case 2: 
/* 353:503 */       if (getOwnerElement().getNodeType() == 1) {
/* 354:504 */         return getOwnerElement().lookupPrefix(namespaceURI);
/* 355:    */       }
/* 356:507 */       return null;
/* 357:    */     }
/* 358:516 */     return null;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public boolean isSameNode(Node other)
/* 362:    */   {
/* 363:537 */     return this == other;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void setTextContent(String textContent)
/* 367:    */     throws DOMException
/* 368:    */   {
/* 369:587 */     setNodeValue(textContent);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public String getTextContent()
/* 373:    */     throws DOMException
/* 374:    */   {
/* 375:636 */     return getNodeValue();
/* 376:    */   }
/* 377:    */   
/* 378:    */   public short compareDocumentPosition(Node other)
/* 379:    */     throws DOMException
/* 380:    */   {
/* 381:648 */     return 0;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public String getBaseURI()
/* 385:    */   {
/* 386:676 */     return null;
/* 387:    */   }
/* 388:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.dom2dtm.DOM2DTMdefaultNamespaceDeclarationNode
 * JD-Core Version:    0.7.0.1
 */