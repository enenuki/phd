/*   1:    */ package org.dom4j.dom;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.List;
/*   5:    */ import org.dom4j.Branch;
/*   6:    */ import org.dom4j.CharacterData;
/*   7:    */ import org.w3c.dom.Attr;
/*   8:    */ import org.w3c.dom.DOMException;
/*   9:    */ import org.w3c.dom.NamedNodeMap;
/*  10:    */ import org.w3c.dom.NodeList;
/*  11:    */ import org.w3c.dom.Text;
/*  12:    */ 
/*  13:    */ public class DOMNodeHelper
/*  14:    */ {
/*  15: 33 */   public static final NodeList EMPTY_NODE_LIST = new EmptyNodeList();
/*  16:    */   
/*  17:    */   public static boolean supports(org.dom4j.Node node, String feature, String version)
/*  18:    */   {
/*  19: 41 */     return false;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static String getNamespaceURI(org.dom4j.Node node)
/*  23:    */   {
/*  24: 45 */     return null;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static String getPrefix(org.dom4j.Node node)
/*  28:    */   {
/*  29: 49 */     return null;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static String getLocalName(org.dom4j.Node node)
/*  33:    */   {
/*  34: 53 */     return null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static void setPrefix(org.dom4j.Node node, String prefix)
/*  38:    */     throws DOMException
/*  39:    */   {}
/*  40:    */   
/*  41:    */   public static String getNodeValue(org.dom4j.Node node)
/*  42:    */     throws DOMException
/*  43:    */   {
/*  44: 61 */     return node.getText();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void setNodeValue(org.dom4j.Node node, String nodeValue)
/*  48:    */     throws DOMException
/*  49:    */   {
/*  50: 66 */     node.setText(nodeValue);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static org.w3c.dom.Node getParentNode(org.dom4j.Node node)
/*  54:    */   {
/*  55: 70 */     return asDOMNode(node.getParent());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static NodeList getChildNodes(org.dom4j.Node node)
/*  59:    */   {
/*  60: 74 */     return EMPTY_NODE_LIST;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static org.w3c.dom.Node getFirstChild(org.dom4j.Node node)
/*  64:    */   {
/*  65: 78 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static org.w3c.dom.Node getLastChild(org.dom4j.Node node)
/*  69:    */   {
/*  70: 82 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static org.w3c.dom.Node getPreviousSibling(org.dom4j.Node node)
/*  74:    */   {
/*  75: 86 */     org.dom4j.Element parent = node.getParent();
/*  76: 88 */     if (parent != null)
/*  77:    */     {
/*  78: 89 */       int index = parent.indexOf(node);
/*  79: 91 */       if (index > 0)
/*  80:    */       {
/*  81: 92 */         org.dom4j.Node previous = parent.node(index - 1);
/*  82:    */         
/*  83: 94 */         return asDOMNode(previous);
/*  84:    */       }
/*  85:    */     }
/*  86: 98 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static org.w3c.dom.Node getNextSibling(org.dom4j.Node node)
/*  90:    */   {
/*  91:102 */     org.dom4j.Element parent = node.getParent();
/*  92:104 */     if (parent != null)
/*  93:    */     {
/*  94:105 */       int index = parent.indexOf(node);
/*  95:107 */       if (index >= 0)
/*  96:    */       {
/*  97:108 */         index++;
/*  98:108 */         if (index < parent.nodeCount())
/*  99:    */         {
/* 100:109 */           org.dom4j.Node next = parent.node(index);
/* 101:    */           
/* 102:111 */           return asDOMNode(next);
/* 103:    */         }
/* 104:    */       }
/* 105:    */     }
/* 106:116 */     return null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static NamedNodeMap getAttributes(org.dom4j.Node node)
/* 110:    */   {
/* 111:120 */     return null;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static org.w3c.dom.Document getOwnerDocument(org.dom4j.Node node)
/* 115:    */   {
/* 116:124 */     return asDOMDocument(node.getDocument());
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static org.w3c.dom.Node insertBefore(org.dom4j.Node node, org.w3c.dom.Node newChild, org.w3c.dom.Node refChild)
/* 120:    */     throws DOMException
/* 121:    */   {
/* 122:130 */     if ((node instanceof Branch))
/* 123:    */     {
/* 124:131 */       Branch branch = (Branch)node;
/* 125:132 */       List list = branch.content();
/* 126:133 */       int index = list.indexOf(refChild);
/* 127:135 */       if (index < 0) {
/* 128:136 */         branch.add((org.dom4j.Node)newChild);
/* 129:    */       } else {
/* 130:138 */         list.add(index, newChild);
/* 131:    */       }
/* 132:141 */       return newChild;
/* 133:    */     }
/* 134:143 */     throw new DOMException((short)3, "Children not allowed for this node: " + node);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static org.w3c.dom.Node replaceChild(org.dom4j.Node node, org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild)
/* 138:    */     throws DOMException
/* 139:    */   {
/* 140:151 */     if ((node instanceof Branch))
/* 141:    */     {
/* 142:152 */       Branch branch = (Branch)node;
/* 143:153 */       List list = branch.content();
/* 144:154 */       int index = list.indexOf(oldChild);
/* 145:156 */       if (index < 0) {
/* 146:157 */         throw new DOMException((short)8, "Tried to replace a non existing child for node: " + node);
/* 147:    */       }
/* 148:162 */       list.set(index, newChild);
/* 149:    */       
/* 150:164 */       return oldChild;
/* 151:    */     }
/* 152:166 */     throw new DOMException((short)3, "Children not allowed for this node: " + node);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static org.w3c.dom.Node removeChild(org.dom4j.Node node, org.w3c.dom.Node oldChild)
/* 156:    */     throws DOMException
/* 157:    */   {
/* 158:173 */     if ((node instanceof Branch))
/* 159:    */     {
/* 160:174 */       Branch branch = (Branch)node;
/* 161:175 */       branch.remove((org.dom4j.Node)oldChild);
/* 162:    */       
/* 163:177 */       return oldChild;
/* 164:    */     }
/* 165:180 */     throw new DOMException((short)3, "Children not allowed for this node: " + node);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public static org.w3c.dom.Node appendChild(org.dom4j.Node node, org.w3c.dom.Node newChild)
/* 169:    */     throws DOMException
/* 170:    */   {
/* 171:186 */     if ((node instanceof Branch))
/* 172:    */     {
/* 173:187 */       Branch branch = (Branch)node;
/* 174:188 */       org.w3c.dom.Node previousParent = newChild.getParentNode();
/* 175:190 */       if (previousParent != null) {
/* 176:191 */         previousParent.removeChild(newChild);
/* 177:    */       }
/* 178:194 */       branch.add((org.dom4j.Node)newChild);
/* 179:    */       
/* 180:196 */       return newChild;
/* 181:    */     }
/* 182:199 */     throw new DOMException((short)3, "Children not allowed for this node: " + node);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static boolean hasChildNodes(org.dom4j.Node node)
/* 186:    */   {
/* 187:204 */     return false;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static org.w3c.dom.Node cloneNode(org.dom4j.Node node, boolean deep)
/* 191:    */   {
/* 192:208 */     return asDOMNode((org.dom4j.Node)node.clone());
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static void normalize(org.dom4j.Node node) {}
/* 196:    */   
/* 197:    */   public static boolean isSupported(org.dom4j.Node n, String feature, String version)
/* 198:    */   {
/* 199:216 */     return false;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public static boolean hasAttributes(org.dom4j.Node node)
/* 203:    */   {
/* 204:220 */     if ((node != null) && ((node instanceof org.dom4j.Element))) {
/* 205:221 */       return ((org.dom4j.Element)node).attributeCount() > 0;
/* 206:    */     }
/* 207:223 */     return false;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public static String getData(CharacterData charData)
/* 211:    */     throws DOMException
/* 212:    */   {
/* 213:230 */     return charData.getText();
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static void setData(CharacterData charData, String data)
/* 217:    */     throws DOMException
/* 218:    */   {
/* 219:235 */     charData.setText(data);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static int getLength(CharacterData charData)
/* 223:    */   {
/* 224:239 */     String text = charData.getText();
/* 225:    */     
/* 226:241 */     return text != null ? text.length() : 0;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public static String substringData(CharacterData charData, int offset, int count)
/* 230:    */     throws DOMException
/* 231:    */   {
/* 232:246 */     if (count < 0) {
/* 233:247 */       throw new DOMException((short)1, "Illegal value for count: " + count);
/* 234:    */     }
/* 235:251 */     String text = charData.getText();
/* 236:252 */     int length = text != null ? text.length() : 0;
/* 237:254 */     if ((offset < 0) || (offset >= length)) {
/* 238:255 */       throw new DOMException((short)1, "No text at offset: " + offset);
/* 239:    */     }
/* 240:259 */     if (offset + count > length) {
/* 241:260 */       return text.substring(offset);
/* 242:    */     }
/* 243:263 */     return text.substring(offset, offset + count);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public static void appendData(CharacterData charData, String arg)
/* 247:    */     throws DOMException
/* 248:    */   {
/* 249:268 */     if (charData.isReadOnly()) {
/* 250:269 */       throw new DOMException((short)7, "CharacterData node is read only: " + charData);
/* 251:    */     }
/* 252:272 */     String text = charData.getText();
/* 253:274 */     if (text == null) {
/* 254:275 */       charData.setText(text);
/* 255:    */     } else {
/* 256:277 */       charData.setText(text + arg);
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public static void insertData(CharacterData data, int offset, String arg)
/* 261:    */     throws DOMException
/* 262:    */   {
/* 263:284 */     if (data.isReadOnly()) {
/* 264:285 */       throw new DOMException((short)7, "CharacterData node is read only: " + data);
/* 265:    */     }
/* 266:288 */     String text = data.getText();
/* 267:290 */     if (text == null)
/* 268:    */     {
/* 269:291 */       data.setText(arg);
/* 270:    */     }
/* 271:    */     else
/* 272:    */     {
/* 273:293 */       int length = text.length();
/* 274:295 */       if ((offset < 0) || (offset > length)) {
/* 275:296 */         throw new DOMException((short)1, "No text at offset: " + offset);
/* 276:    */       }
/* 277:299 */       StringBuffer buffer = new StringBuffer(text);
/* 278:300 */       buffer.insert(offset, arg);
/* 279:301 */       data.setText(buffer.toString());
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   public static void deleteData(CharacterData charData, int offset, int count)
/* 284:    */     throws DOMException
/* 285:    */   {
/* 286:309 */     if (charData.isReadOnly()) {
/* 287:310 */       throw new DOMException((short)7, "CharacterData node is read only: " + charData);
/* 288:    */     }
/* 289:313 */     if (count < 0) {
/* 290:314 */       throw new DOMException((short)1, "Illegal value for count: " + count);
/* 291:    */     }
/* 292:318 */     String text = charData.getText();
/* 293:320 */     if (text != null)
/* 294:    */     {
/* 295:321 */       int length = text.length();
/* 296:323 */       if ((offset < 0) || (offset >= length)) {
/* 297:324 */         throw new DOMException((short)1, "No text at offset: " + offset);
/* 298:    */       }
/* 299:327 */       StringBuffer buffer = new StringBuffer(text);
/* 300:328 */       buffer.delete(offset, offset + count);
/* 301:329 */       charData.setText(buffer.toString());
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static void replaceData(CharacterData charData, int offset, int count, String arg)
/* 306:    */     throws DOMException
/* 307:    */   {
/* 308:337 */     if (charData.isReadOnly()) {
/* 309:338 */       throw new DOMException((short)7, "CharacterData node is read only: " + charData);
/* 310:    */     }
/* 311:341 */     if (count < 0) {
/* 312:342 */       throw new DOMException((short)1, "Illegal value for count: " + count);
/* 313:    */     }
/* 314:346 */     String text = charData.getText();
/* 315:348 */     if (text != null)
/* 316:    */     {
/* 317:349 */       int length = text.length();
/* 318:351 */       if ((offset < 0) || (offset >= length)) {
/* 319:352 */         throw new DOMException((short)1, "No text at offset: " + offset);
/* 320:    */       }
/* 321:355 */       StringBuffer buffer = new StringBuffer(text);
/* 322:356 */       buffer.replace(offset, offset + count, arg);
/* 323:357 */       charData.setText(buffer.toString());
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   public static void appendElementsByTagName(List list, Branch parent, String name)
/* 328:    */   {
/* 329:367 */     boolean isStar = "*".equals(name);
/* 330:    */     
/* 331:369 */     int i = 0;
/* 332:369 */     for (int size = parent.nodeCount(); i < size; i++)
/* 333:    */     {
/* 334:370 */       org.dom4j.Node node = parent.node(i);
/* 335:372 */       if ((node instanceof org.dom4j.Element))
/* 336:    */       {
/* 337:373 */         org.dom4j.Element element = (org.dom4j.Element)node;
/* 338:375 */         if ((isStar) || (name.equals(element.getName()))) {
/* 339:376 */           list.add(element);
/* 340:    */         }
/* 341:379 */         appendElementsByTagName(list, element, name);
/* 342:    */       }
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public static void appendElementsByTagNameNS(List list, Branch parent, String namespace, String localName)
/* 347:    */   {
/* 348:386 */     boolean isStarNS = "*".equals(namespace);
/* 349:387 */     boolean isStar = "*".equals(localName);
/* 350:    */     
/* 351:389 */     int i = 0;
/* 352:389 */     for (int size = parent.nodeCount(); i < size; i++)
/* 353:    */     {
/* 354:390 */       org.dom4j.Node node = parent.node(i);
/* 355:392 */       if ((node instanceof org.dom4j.Element))
/* 356:    */       {
/* 357:393 */         org.dom4j.Element element = (org.dom4j.Element)node;
/* 358:395 */         if (((isStarNS) || (((namespace != null) && (namespace.length() != 0)) || ((element.getNamespaceURI() == null) || (element.getNamespaceURI().length() == 0) || ((namespace != null) && (namespace.equals(element.getNamespaceURI())))))) && ((isStar) || (localName.equals(element.getName())))) {
/* 359:403 */           list.add(element);
/* 360:    */         }
/* 361:406 */         appendElementsByTagNameNS(list, element, namespace, localName);
/* 362:    */       }
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public static NodeList createNodeList(List list)
/* 367:    */   {
/* 368:414 */     new NodeList()
/* 369:    */     {
/* 370:    */       private final List val$list;
/* 371:    */       
/* 372:    */       public org.w3c.dom.Node item(int index)
/* 373:    */       {
/* 374:416 */         if (index >= getLength()) {
/* 375:422 */           return null;
/* 376:    */         }
/* 377:424 */         return DOMNodeHelper.asDOMNode((org.dom4j.Node)this.val$list.get(index));
/* 378:    */       }
/* 379:    */       
/* 380:    */       public int getLength()
/* 381:    */       {
/* 382:429 */         return this.val$list.size();
/* 383:    */       }
/* 384:    */     };
/* 385:    */   }
/* 386:    */   
/* 387:    */   public static org.w3c.dom.Node asDOMNode(org.dom4j.Node node)
/* 388:    */   {
/* 389:435 */     if (node == null) {
/* 390:436 */       return null;
/* 391:    */     }
/* 392:439 */     if ((node instanceof org.w3c.dom.Node)) {
/* 393:440 */       return (org.w3c.dom.Node)node;
/* 394:    */     }
/* 395:443 */     System.out.println("Cannot convert: " + node + " into a W3C DOM Node");
/* 396:    */     
/* 397:445 */     notSupported();
/* 398:    */     
/* 399:447 */     return null;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public static org.w3c.dom.Document asDOMDocument(org.dom4j.Document document)
/* 403:    */   {
/* 404:452 */     if (document == null) {
/* 405:453 */       return null;
/* 406:    */     }
/* 407:456 */     if ((document instanceof org.w3c.dom.Document)) {
/* 408:457 */       return (org.w3c.dom.Document)document;
/* 409:    */     }
/* 410:460 */     notSupported();
/* 411:    */     
/* 412:462 */     return null;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public static org.w3c.dom.DocumentType asDOMDocumentType(org.dom4j.DocumentType dt)
/* 416:    */   {
/* 417:467 */     if (dt == null) {
/* 418:468 */       return null;
/* 419:    */     }
/* 420:471 */     if ((dt instanceof org.w3c.dom.DocumentType)) {
/* 421:472 */       return (org.w3c.dom.DocumentType)dt;
/* 422:    */     }
/* 423:475 */     notSupported();
/* 424:    */     
/* 425:477 */     return null;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public static Text asDOMText(CharacterData text)
/* 429:    */   {
/* 430:482 */     if (text == null) {
/* 431:483 */       return null;
/* 432:    */     }
/* 433:486 */     if ((text instanceof Text)) {
/* 434:487 */       return (Text)text;
/* 435:    */     }
/* 436:490 */     notSupported();
/* 437:    */     
/* 438:492 */     return null;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public static org.w3c.dom.Element asDOMElement(org.dom4j.Node element)
/* 442:    */   {
/* 443:497 */     if (element == null) {
/* 444:498 */       return null;
/* 445:    */     }
/* 446:501 */     if ((element instanceof org.w3c.dom.Element)) {
/* 447:502 */       return (org.w3c.dom.Element)element;
/* 448:    */     }
/* 449:505 */     notSupported();
/* 450:    */     
/* 451:507 */     return null;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public static Attr asDOMAttr(org.dom4j.Node attribute)
/* 455:    */   {
/* 456:512 */     if (attribute == null) {
/* 457:513 */       return null;
/* 458:    */     }
/* 459:516 */     if ((attribute instanceof Attr)) {
/* 460:517 */       return (Attr)attribute;
/* 461:    */     }
/* 462:520 */     notSupported();
/* 463:    */     
/* 464:522 */     return null;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public static void notSupported()
/* 468:    */   {
/* 469:533 */     throw new DOMException((short)9, "Not supported yet");
/* 470:    */   }
/* 471:    */   
/* 472:    */   public static class EmptyNodeList
/* 473:    */     implements NodeList
/* 474:    */   {
/* 475:    */     public org.w3c.dom.Node item(int index)
/* 476:    */     {
/* 477:539 */       return null;
/* 478:    */     }
/* 479:    */     
/* 480:    */     public int getLength()
/* 481:    */     {
/* 482:543 */       return 0;
/* 483:    */     }
/* 484:    */   }
/* 485:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.dom.DOMNodeHelper
 * JD-Core Version:    0.7.0.1
 */