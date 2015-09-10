/*   1:    */ package org.apache.xalan.xsltc.dom;
/*   2:    */ 
/*   3:    */ import org.apache.xalan.xsltc.DOM;
/*   4:    */ import org.apache.xalan.xsltc.DOMEnhancedForDTM;
/*   5:    */ import org.apache.xalan.xsltc.StripFilter;
/*   6:    */ import org.apache.xalan.xsltc.TransletException;
/*   7:    */ import org.apache.xalan.xsltc.runtime.Hashtable;
/*   8:    */ import org.apache.xml.dtm.DTMAxisIterator;
/*   9:    */ import org.apache.xml.serializer.SerializationHandler;
/*  10:    */ import org.w3c.dom.Node;
/*  11:    */ import org.w3c.dom.NodeList;
/*  12:    */ 
/*  13:    */ public final class DOMAdapter
/*  14:    */   implements DOM
/*  15:    */ {
/*  16:    */   private DOMEnhancedForDTM _enhancedDOM;
/*  17:    */   private DOM _dom;
/*  18:    */   private String[] _namesArray;
/*  19:    */   private String[] _urisArray;
/*  20:    */   private int[] _typesArray;
/*  21:    */   private String[] _namespaceArray;
/*  22: 53 */   private short[] _mapping = null;
/*  23: 54 */   private int[] _reverse = null;
/*  24: 55 */   private short[] _NSmapping = null;
/*  25: 56 */   private short[] _NSreverse = null;
/*  26: 58 */   private StripFilter _filter = null;
/*  27:    */   private int _multiDOMMask;
/*  28:    */   
/*  29:    */   public DOMAdapter(DOM dom, String[] namesArray, String[] urisArray, int[] typesArray, String[] namespaceArray)
/*  30:    */   {
/*  31: 67 */     if ((dom instanceof DOMEnhancedForDTM)) {
/*  32: 68 */       this._enhancedDOM = ((DOMEnhancedForDTM)dom);
/*  33:    */     }
/*  34: 71 */     this._dom = dom;
/*  35: 72 */     this._namesArray = namesArray;
/*  36: 73 */     this._urisArray = urisArray;
/*  37: 74 */     this._typesArray = typesArray;
/*  38: 75 */     this._namespaceArray = namespaceArray;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setupMapping(String[] names, String[] urisArray, int[] typesArray, String[] namespaces)
/*  42:    */   {
/*  43: 80 */     this._namesArray = names;
/*  44: 81 */     this._urisArray = urisArray;
/*  45: 82 */     this._typesArray = typesArray;
/*  46: 83 */     this._namespaceArray = namespaces;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String[] getNamesArray()
/*  50:    */   {
/*  51: 87 */     return this._namesArray;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String[] getUrisArray()
/*  55:    */   {
/*  56: 91 */     return this._urisArray;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int[] getTypesArray()
/*  60:    */   {
/*  61: 95 */     return this._typesArray;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String[] getNamespaceArray()
/*  65:    */   {
/*  66: 99 */     return this._namespaceArray;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public DOM getDOMImpl()
/*  70:    */   {
/*  71:103 */     return this._dom;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private short[] getMapping()
/*  75:    */   {
/*  76:107 */     if ((this._mapping == null) && 
/*  77:108 */       (this._enhancedDOM != null)) {
/*  78:109 */       this._mapping = this._enhancedDOM.getMapping(this._namesArray, this._urisArray, this._typesArray);
/*  79:    */     }
/*  80:113 */     return this._mapping;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private int[] getReverse()
/*  84:    */   {
/*  85:117 */     if ((this._reverse == null) && 
/*  86:118 */       (this._enhancedDOM != null)) {
/*  87:119 */       this._reverse = this._enhancedDOM.getReverseMapping(this._namesArray, this._urisArray, this._typesArray);
/*  88:    */     }
/*  89:124 */     return this._reverse;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private short[] getNSMapping()
/*  93:    */   {
/*  94:128 */     if ((this._NSmapping == null) && 
/*  95:129 */       (this._enhancedDOM != null)) {
/*  96:130 */       this._NSmapping = this._enhancedDOM.getNamespaceMapping(this._namespaceArray);
/*  97:    */     }
/*  98:133 */     return this._NSmapping;
/*  99:    */   }
/* 100:    */   
/* 101:    */   private short[] getNSReverse()
/* 102:    */   {
/* 103:137 */     if ((this._NSreverse == null) && 
/* 104:138 */       (this._enhancedDOM != null)) {
/* 105:139 */       this._NSreverse = this._enhancedDOM.getReverseNamespaceMapping(this._namespaceArray);
/* 106:    */     }
/* 107:143 */     return this._NSreverse;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public DTMAxisIterator getIterator()
/* 111:    */   {
/* 112:150 */     return this._dom.getIterator();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getStringValue()
/* 116:    */   {
/* 117:154 */     return this._dom.getStringValue();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public DTMAxisIterator getChildren(int node)
/* 121:    */   {
/* 122:158 */     if (this._enhancedDOM != null) {
/* 123:159 */       return this._enhancedDOM.getChildren(node);
/* 124:    */     }
/* 125:162 */     DTMAxisIterator iterator = this._dom.getChildren(node);
/* 126:163 */     return iterator.setStartNode(node);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setFilter(StripFilter filter)
/* 130:    */   {
/* 131:168 */     this._filter = filter;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public DTMAxisIterator getTypedChildren(int type)
/* 135:    */   {
/* 136:172 */     int[] reverse = getReverse();
/* 137:174 */     if (this._enhancedDOM != null) {
/* 138:175 */       return this._enhancedDOM.getTypedChildren(reverse[type]);
/* 139:    */     }
/* 140:178 */     return this._dom.getTypedChildren(type);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns)
/* 144:    */   {
/* 145:184 */     return this._dom.getNamespaceAxisIterator(axis, getNSReverse()[ns]);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public DTMAxisIterator getAxisIterator(int axis)
/* 149:    */   {
/* 150:188 */     if (this._enhancedDOM != null) {
/* 151:189 */       return this._enhancedDOM.getAxisIterator(axis);
/* 152:    */     }
/* 153:192 */     return this._dom.getAxisIterator(axis);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public DTMAxisIterator getTypedAxisIterator(int axis, int type)
/* 157:    */   {
/* 158:198 */     int[] reverse = getReverse();
/* 159:199 */     if (this._enhancedDOM != null) {
/* 160:200 */       return this._enhancedDOM.getTypedAxisIterator(axis, reverse[type]);
/* 161:    */     }
/* 162:202 */     return this._dom.getTypedAxisIterator(axis, type);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public int getMultiDOMMask()
/* 166:    */   {
/* 167:207 */     return this._multiDOMMask;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setMultiDOMMask(int mask)
/* 171:    */   {
/* 172:211 */     this._multiDOMMask = mask;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public DTMAxisIterator getNthDescendant(int type, int n, boolean includeself)
/* 176:    */   {
/* 177:216 */     return this._dom.getNthDescendant(getReverse()[type], n, includeself);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iterator, int type, String value, boolean op)
/* 181:    */   {
/* 182:222 */     return this._dom.getNodeValueIterator(iterator, type, value, op);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public DTMAxisIterator orderNodes(DTMAxisIterator source, int node)
/* 186:    */   {
/* 187:226 */     return this._dom.orderNodes(source, node);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getExpandedTypeID(int node)
/* 191:    */   {
/* 192:230 */     short[] mapping = getMapping();
/* 193:    */     int type;
/* 194:232 */     if (this._enhancedDOM != null) {
/* 195:233 */       type = mapping[this._enhancedDOM.getExpandedTypeID2(node)];
/* 196:236 */     } else if (null != mapping) {
/* 197:238 */       type = mapping[this._dom.getExpandedTypeID(node)];
/* 198:    */     } else {
/* 199:242 */       type = this._dom.getExpandedTypeID(node);
/* 200:    */     }
/* 201:245 */     return type;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int getNamespaceType(int node)
/* 205:    */   {
/* 206:249 */     return getNSMapping()[this._dom.getNSType(node)];
/* 207:    */   }
/* 208:    */   
/* 209:    */   public int getNSType(int node)
/* 210:    */   {
/* 211:253 */     return this._dom.getNSType(node);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public int getParent(int node)
/* 215:    */   {
/* 216:257 */     return this._dom.getParent(node);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public int getAttributeNode(int type, int element)
/* 220:    */   {
/* 221:261 */     return this._dom.getAttributeNode(getReverse()[type], element);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getNodeName(int node)
/* 225:    */   {
/* 226:265 */     if (node == -1) {
/* 227:266 */       return "";
/* 228:    */     }
/* 229:268 */     return this._dom.getNodeName(node);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String getNodeNameX(int node)
/* 233:    */   {
/* 234:273 */     if (node == -1) {
/* 235:274 */       return "";
/* 236:    */     }
/* 237:276 */     return this._dom.getNodeNameX(node);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String getNamespaceName(int node)
/* 241:    */   {
/* 242:281 */     if (node == -1) {
/* 243:282 */       return "";
/* 244:    */     }
/* 245:284 */     return this._dom.getNamespaceName(node);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getStringValueX(int node)
/* 249:    */   {
/* 250:289 */     if (this._enhancedDOM != null) {
/* 251:290 */       return this._enhancedDOM.getStringValueX(node);
/* 252:    */     }
/* 253:293 */     if (node == -1) {
/* 254:294 */       return "";
/* 255:    */     }
/* 256:296 */     return this._dom.getStringValueX(node);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void copy(int node, SerializationHandler handler)
/* 260:    */     throws TransletException
/* 261:    */   {
/* 262:303 */     this._dom.copy(node, handler);
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void copy(DTMAxisIterator nodes, SerializationHandler handler)
/* 266:    */     throws TransletException
/* 267:    */   {
/* 268:309 */     this._dom.copy(nodes, handler);
/* 269:    */   }
/* 270:    */   
/* 271:    */   public String shallowCopy(int node, SerializationHandler handler)
/* 272:    */     throws TransletException
/* 273:    */   {
/* 274:315 */     if (this._enhancedDOM != null) {
/* 275:316 */       return this._enhancedDOM.shallowCopy(node, handler);
/* 276:    */     }
/* 277:319 */     return this._dom.shallowCopy(node, handler);
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean lessThan(int node1, int node2)
/* 281:    */   {
/* 282:325 */     return this._dom.lessThan(node1, node2);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void characters(int textNode, SerializationHandler handler)
/* 286:    */     throws TransletException
/* 287:    */   {
/* 288:331 */     if (this._enhancedDOM != null) {
/* 289:332 */       this._enhancedDOM.characters(textNode, handler);
/* 290:    */     } else {
/* 291:335 */       this._dom.characters(textNode, handler);
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Node makeNode(int index)
/* 296:    */   {
/* 297:341 */     return this._dom.makeNode(index);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Node makeNode(DTMAxisIterator iter)
/* 301:    */   {
/* 302:346 */     return this._dom.makeNode(iter);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public NodeList makeNodeList(int index)
/* 306:    */   {
/* 307:351 */     return this._dom.makeNodeList(index);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public NodeList makeNodeList(DTMAxisIterator iter)
/* 311:    */   {
/* 312:356 */     return this._dom.makeNodeList(iter);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public String getLanguage(int node)
/* 316:    */   {
/* 317:361 */     return this._dom.getLanguage(node);
/* 318:    */   }
/* 319:    */   
/* 320:    */   public int getSize()
/* 321:    */   {
/* 322:366 */     return this._dom.getSize();
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setDocumentURI(String uri)
/* 326:    */   {
/* 327:371 */     if (this._enhancedDOM != null) {
/* 328:372 */       this._enhancedDOM.setDocumentURI(uri);
/* 329:    */     }
/* 330:    */   }
/* 331:    */   
/* 332:    */   public String getDocumentURI()
/* 333:    */   {
/* 334:378 */     if (this._enhancedDOM != null) {
/* 335:379 */       return this._enhancedDOM.getDocumentURI();
/* 336:    */     }
/* 337:382 */     return "";
/* 338:    */   }
/* 339:    */   
/* 340:    */   public String getDocumentURI(int node)
/* 341:    */   {
/* 342:388 */     return this._dom.getDocumentURI(node);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public int getDocument()
/* 346:    */   {
/* 347:393 */     return this._dom.getDocument();
/* 348:    */   }
/* 349:    */   
/* 350:    */   public boolean isElement(int node)
/* 351:    */   {
/* 352:398 */     return this._dom.isElement(node);
/* 353:    */   }
/* 354:    */   
/* 355:    */   public boolean isAttribute(int node)
/* 356:    */   {
/* 357:403 */     return this._dom.isAttribute(node);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public int getNodeIdent(int nodeHandle)
/* 361:    */   {
/* 362:408 */     return this._dom.getNodeIdent(nodeHandle);
/* 363:    */   }
/* 364:    */   
/* 365:    */   public int getNodeHandle(int nodeId)
/* 366:    */   {
/* 367:413 */     return this._dom.getNodeHandle(nodeId);
/* 368:    */   }
/* 369:    */   
/* 370:    */   public DOM getResultTreeFrag(int initSize, int rtfType)
/* 371:    */   {
/* 372:421 */     if (this._enhancedDOM != null) {
/* 373:422 */       return this._enhancedDOM.getResultTreeFrag(initSize, rtfType);
/* 374:    */     }
/* 375:425 */     return this._dom.getResultTreeFrag(initSize, rtfType);
/* 376:    */   }
/* 377:    */   
/* 378:    */   public DOM getResultTreeFrag(int initSize, int rtfType, boolean addToManager)
/* 379:    */   {
/* 380:435 */     if (this._enhancedDOM != null) {
/* 381:436 */       return this._enhancedDOM.getResultTreeFrag(initSize, rtfType, addToManager);
/* 382:    */     }
/* 383:440 */     return this._dom.getResultTreeFrag(initSize, rtfType, addToManager);
/* 384:    */   }
/* 385:    */   
/* 386:    */   public SerializationHandler getOutputDomBuilder()
/* 387:    */   {
/* 388:450 */     return this._dom.getOutputDomBuilder();
/* 389:    */   }
/* 390:    */   
/* 391:    */   public String lookupNamespace(int node, String prefix)
/* 392:    */     throws TransletException
/* 393:    */   {
/* 394:456 */     return this._dom.lookupNamespace(node, prefix);
/* 395:    */   }
/* 396:    */   
/* 397:    */   public String getUnparsedEntityURI(String entity)
/* 398:    */   {
/* 399:460 */     return this._dom.getUnparsedEntityURI(entity);
/* 400:    */   }
/* 401:    */   
/* 402:    */   public Hashtable getElementsWithIDs()
/* 403:    */   {
/* 404:464 */     return this._dom.getElementsWithIDs();
/* 405:    */   }
/* 406:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.DOMAdapter
 * JD-Core Version:    0.7.0.1
 */