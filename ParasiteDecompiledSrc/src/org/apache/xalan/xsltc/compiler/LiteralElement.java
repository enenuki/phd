/*   1:    */ package org.apache.xalan.xsltc.compiler;
/*   2:    */ 
/*   3:    */ import java.util.Enumeration;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import java.util.Vector;
/*   6:    */ import org.apache.bcel.generic.ClassGen;
/*   7:    */ import org.apache.bcel.generic.ConstantPoolGen;
/*   8:    */ import org.apache.bcel.generic.InstructionConstants;
/*   9:    */ import org.apache.bcel.generic.InstructionList;
/*  10:    */ import org.apache.bcel.generic.MethodGen;
/*  11:    */ import org.apache.bcel.generic.PUSH;
/*  12:    */ import org.apache.xalan.xsltc.compiler.util.ClassGenerator;
/*  13:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  14:    */ import org.apache.xalan.xsltc.compiler.util.MethodGenerator;
/*  15:    */ import org.apache.xalan.xsltc.compiler.util.Type;
/*  16:    */ import org.apache.xalan.xsltc.compiler.util.TypeCheckError;
/*  17:    */ import org.apache.xalan.xsltc.compiler.util.Util;
/*  18:    */ import org.apache.xalan.xsltc.runtime.AttributeList;
/*  19:    */ import org.apache.xml.serializer.ElemDesc;
/*  20:    */ import org.apache.xml.serializer.ToHTMLStream;
/*  21:    */ 
/*  22:    */ final class LiteralElement
/*  23:    */   extends Instruction
/*  24:    */ {
/*  25:    */   private String _name;
/*  26: 49 */   private LiteralElement _literalElemParent = null;
/*  27: 50 */   private Vector _attributeElements = null;
/*  28: 51 */   private Hashtable _accessedPrefixes = null;
/*  29: 56 */   private boolean _allAttributesUnique = false;
/*  30:    */   private static final String XMLNS_STRING = "xmlns";
/*  31:    */   
/*  32:    */   public QName getName()
/*  33:    */   {
/*  34: 64 */     return this._qname;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void display(int indent)
/*  38:    */   {
/*  39: 71 */     indent(indent);
/*  40: 72 */     Util.println("LiteralElement name = " + this._name);
/*  41: 73 */     displayContents(indent + 4);
/*  42:    */   }
/*  43:    */   
/*  44:    */   private String accessedNamespace(String prefix)
/*  45:    */   {
/*  46: 80 */     if (this._literalElemParent != null)
/*  47:    */     {
/*  48: 81 */       String result = this._literalElemParent.accessedNamespace(prefix);
/*  49: 82 */       if (result != null) {
/*  50: 83 */         return result;
/*  51:    */       }
/*  52:    */     }
/*  53: 86 */     return this._accessedPrefixes != null ? (String)this._accessedPrefixes.get(prefix) : null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void registerNamespace(String prefix, String uri, SymbolTable stable, boolean declared)
/*  57:    */   {
/*  58: 99 */     if (this._literalElemParent != null)
/*  59:    */     {
/*  60:100 */       String parentUri = this._literalElemParent.accessedNamespace(prefix);
/*  61:101 */       if ((parentUri != null) && (parentUri.equals(uri))) {
/*  62:102 */         return;
/*  63:    */       }
/*  64:    */     }
/*  65:107 */     if (this._accessedPrefixes == null)
/*  66:    */     {
/*  67:108 */       this._accessedPrefixes = new Hashtable();
/*  68:    */     }
/*  69:111 */     else if (!declared)
/*  70:    */     {
/*  71:113 */       String old = (String)this._accessedPrefixes.get(prefix);
/*  72:114 */       if (old != null)
/*  73:    */       {
/*  74:115 */         if (old.equals(uri)) {
/*  75:116 */           return;
/*  76:    */         }
/*  77:118 */         prefix = stable.generateNamespacePrefix();
/*  78:    */       }
/*  79:    */     }
/*  80:123 */     if (!prefix.equals("xml")) {
/*  81:124 */       this._accessedPrefixes.put(prefix, uri);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   private String translateQName(QName qname, SymbolTable stable)
/*  86:    */   {
/*  87:135 */     String localname = qname.getLocalPart();
/*  88:136 */     String prefix = qname.getPrefix();
/*  89:139 */     if (prefix == null) {
/*  90:140 */       prefix = "";
/*  91:141 */     } else if (prefix.equals("xmlns")) {
/*  92:142 */       return "xmlns";
/*  93:    */     }
/*  94:145 */     String alternative = stable.lookupPrefixAlias(prefix);
/*  95:146 */     if (alternative != null)
/*  96:    */     {
/*  97:147 */       stable.excludeNamespaces(prefix);
/*  98:148 */       prefix = alternative;
/*  99:    */     }
/* 100:152 */     String uri = lookupNamespace(prefix);
/* 101:153 */     if (uri == null) {
/* 102:153 */       return localname;
/* 103:    */     }
/* 104:156 */     registerNamespace(prefix, uri, stable, false);
/* 105:159 */     if (prefix != "") {
/* 106:160 */       return prefix + ":" + localname;
/* 107:    */     }
/* 108:162 */     return localname;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void addAttribute(SyntaxTreeNode attribute)
/* 112:    */   {
/* 113:169 */     if (this._attributeElements == null) {
/* 114:170 */       this._attributeElements = new Vector(2);
/* 115:    */     }
/* 116:172 */     this._attributeElements.add(attribute);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setFirstAttribute(SyntaxTreeNode attribute)
/* 120:    */   {
/* 121:179 */     if (this._attributeElements == null) {
/* 122:180 */       this._attributeElements = new Vector(2);
/* 123:    */     }
/* 124:182 */     this._attributeElements.insertElementAt(attribute, 0);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Type typeCheck(SymbolTable stable)
/* 128:    */     throws TypeCheckError
/* 129:    */   {
/* 130:191 */     if (this._attributeElements != null)
/* 131:    */     {
/* 132:192 */       int count = this._attributeElements.size();
/* 133:193 */       for (int i = 0; i < count; i++)
/* 134:    */       {
/* 135:194 */         SyntaxTreeNode node = (SyntaxTreeNode)this._attributeElements.elementAt(i);
/* 136:    */         
/* 137:196 */         node.typeCheck(stable);
/* 138:    */       }
/* 139:    */     }
/* 140:199 */     typeCheckContents(stable);
/* 141:200 */     return Type.Void;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Enumeration getNamespaceScope(SyntaxTreeNode node)
/* 145:    */   {
/* 146:209 */     Hashtable all = new Hashtable();
/* 147:211 */     while (node != null)
/* 148:    */     {
/* 149:212 */       Hashtable mapping = node.getPrefixMapping();
/* 150:213 */       if (mapping != null)
/* 151:    */       {
/* 152:214 */         Enumeration prefixes = mapping.keys();
/* 153:215 */         while (prefixes.hasMoreElements())
/* 154:    */         {
/* 155:216 */           String prefix = (String)prefixes.nextElement();
/* 156:217 */           if (!all.containsKey(prefix)) {
/* 157:218 */             all.put(prefix, mapping.get(prefix));
/* 158:    */           }
/* 159:    */         }
/* 160:    */       }
/* 161:222 */       node = node.getParent();
/* 162:    */     }
/* 163:224 */     return all.keys();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void parseContents(Parser parser)
/* 167:    */   {
/* 168:232 */     SymbolTable stable = parser.getSymbolTable();
/* 169:233 */     stable.setCurrentNode(this);
/* 170:    */     
/* 171:    */ 
/* 172:236 */     SyntaxTreeNode parent = getParent();
/* 173:237 */     if ((parent != null) && ((parent instanceof LiteralElement))) {
/* 174:238 */       this._literalElemParent = ((LiteralElement)parent);
/* 175:    */     }
/* 176:241 */     this._name = translateQName(this._qname, stable);
/* 177:    */     
/* 178:    */ 
/* 179:244 */     int count = this._attributes.getLength();
/* 180:245 */     for (int i = 0; i < count; i++)
/* 181:    */     {
/* 182:246 */       QName qname = parser.getQName(this._attributes.getQName(i));
/* 183:247 */       String uri = qname.getNamespace();
/* 184:248 */       String val = this._attributes.getValue(i);
/* 185:253 */       if (qname == parser.getUseAttributeSets())
/* 186:    */       {
/* 187:254 */         if (!Util.isValidQNames(val))
/* 188:    */         {
/* 189:255 */           ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", val, this);
/* 190:256 */           parser.reportError(3, err);
/* 191:    */         }
/* 192:258 */         setFirstAttribute(new UseAttributeSets(val, parser));
/* 193:    */       }
/* 194:261 */       else if (qname == parser.getExtensionElementPrefixes())
/* 195:    */       {
/* 196:262 */         stable.excludeNamespaces(val);
/* 197:    */       }
/* 198:265 */       else if (qname == parser.getExcludeResultPrefixes())
/* 199:    */       {
/* 200:266 */         stable.excludeNamespaces(val);
/* 201:    */       }
/* 202:    */       else
/* 203:    */       {
/* 204:270 */         String prefix = qname.getPrefix();
/* 205:271 */         if (((prefix == null) || (!prefix.equals("xmlns"))) && ((prefix != null) || (!qname.getLocalPart().equals("xmlns"))) && ((uri == null) || (!uri.equals("http://www.w3.org/1999/XSL/Transform"))))
/* 206:    */         {
/* 207:279 */           String name = translateQName(qname, stable);
/* 208:280 */           LiteralAttribute attr = new LiteralAttribute(name, val, parser, this);
/* 209:281 */           addAttribute(attr);
/* 210:282 */           attr.setParent(this);
/* 211:283 */           attr.parseContents(parser);
/* 212:    */         }
/* 213:    */       }
/* 214:    */     }
/* 215:289 */     Enumeration include = getNamespaceScope(this);
/* 216:290 */     while (include.hasMoreElements())
/* 217:    */     {
/* 218:291 */       String prefix = (String)include.nextElement();
/* 219:292 */       if (!prefix.equals("xml"))
/* 220:    */       {
/* 221:293 */         String uri = lookupNamespace(prefix);
/* 222:294 */         if ((uri != null) && (!stable.isExcludedNamespace(uri))) {
/* 223:295 */           registerNamespace(prefix, uri, stable, true);
/* 224:    */         }
/* 225:    */       }
/* 226:    */     }
/* 227:300 */     parseChildren(parser);
/* 228:303 */     for (int i = 0; i < count; i++)
/* 229:    */     {
/* 230:304 */       QName qname = parser.getQName(this._attributes.getQName(i));
/* 231:305 */       String val = this._attributes.getValue(i);
/* 232:308 */       if (qname == parser.getExtensionElementPrefixes()) {
/* 233:309 */         stable.unExcludeNamespaces(val);
/* 234:312 */       } else if (qname == parser.getExcludeResultPrefixes()) {
/* 235:313 */         stable.unExcludeNamespaces(val);
/* 236:    */       }
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   protected boolean contextDependent()
/* 241:    */   {
/* 242:319 */     return dependentContents();
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void translate(ClassGenerator classGen, MethodGenerator methodGen)
/* 246:    */   {
/* 247:331 */     ConstantPoolGen cpg = classGen.getConstantPool();
/* 248:332 */     InstructionList il = methodGen.getInstructionList();
/* 249:    */     
/* 250:    */ 
/* 251:335 */     this._allAttributesUnique = checkAttributesUnique();
/* 252:    */     
/* 253:    */ 
/* 254:338 */     il.append(methodGen.loadHandler());
/* 255:    */     
/* 256:340 */     il.append(new PUSH(cpg, this._name));
/* 257:341 */     il.append(InstructionConstants.DUP2);
/* 258:342 */     il.append(methodGen.startElement());
/* 259:    */     
/* 260:    */ 
/* 261:345 */     int j = 0;
/* 262:346 */     while (j < elementCount())
/* 263:    */     {
/* 264:347 */       SyntaxTreeNode item = (SyntaxTreeNode)elementAt(j);
/* 265:348 */       if ((item instanceof Variable)) {
/* 266:349 */         item.translate(classGen, methodGen);
/* 267:    */       }
/* 268:351 */       j++;
/* 269:    */     }
/* 270:355 */     if (this._accessedPrefixes != null)
/* 271:    */     {
/* 272:356 */       boolean declaresDefaultNS = false;
/* 273:357 */       Enumeration e = this._accessedPrefixes.keys();
/* 274:359 */       while (e.hasMoreElements())
/* 275:    */       {
/* 276:360 */         String prefix = (String)e.nextElement();
/* 277:361 */         String uri = (String)this._accessedPrefixes.get(prefix);
/* 278:363 */         if ((uri != "") || (prefix != ""))
/* 279:    */         {
/* 280:366 */           if (prefix == "") {
/* 281:367 */             declaresDefaultNS = true;
/* 282:    */           }
/* 283:369 */           il.append(methodGen.loadHandler());
/* 284:370 */           il.append(new PUSH(cpg, prefix));
/* 285:371 */           il.append(new PUSH(cpg, uri));
/* 286:372 */           il.append(methodGen.namespace());
/* 287:    */         }
/* 288:    */       }
/* 289:380 */       if ((!declaresDefaultNS) && ((this._parent instanceof XslElement)) && (((XslElement)this._parent).declaresDefaultNS()))
/* 290:    */       {
/* 291:383 */         il.append(methodGen.loadHandler());
/* 292:384 */         il.append(new PUSH(cpg, ""));
/* 293:385 */         il.append(new PUSH(cpg, ""));
/* 294:386 */         il.append(methodGen.namespace());
/* 295:    */       }
/* 296:    */     }
/* 297:391 */     if (this._attributeElements != null)
/* 298:    */     {
/* 299:392 */       int count = this._attributeElements.size();
/* 300:393 */       for (int i = 0; i < count; i++)
/* 301:    */       {
/* 302:394 */         SyntaxTreeNode node = (SyntaxTreeNode)this._attributeElements.elementAt(i);
/* 303:396 */         if (!(node instanceof XslAttribute)) {
/* 304:397 */           node.translate(classGen, methodGen);
/* 305:    */         }
/* 306:    */       }
/* 307:    */     }
/* 308:403 */     translateContents(classGen, methodGen);
/* 309:    */     
/* 310:    */ 
/* 311:406 */     il.append(methodGen.endElement());
/* 312:    */   }
/* 313:    */   
/* 314:    */   private boolean isHTMLOutput()
/* 315:    */   {
/* 316:413 */     return getStylesheet().getOutputMethod() == 2;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public ElemDesc getElemDesc()
/* 320:    */   {
/* 321:422 */     if (isHTMLOutput()) {
/* 322:423 */       return ToHTMLStream.getElemDesc(this._name);
/* 323:    */     }
/* 324:426 */     return null;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean allAttributesUnique()
/* 328:    */   {
/* 329:433 */     return this._allAttributesUnique;
/* 330:    */   }
/* 331:    */   
/* 332:    */   private boolean checkAttributesUnique()
/* 333:    */   {
/* 334:440 */     boolean hasHiddenXslAttribute = canProduceAttributeNodes(this, true);
/* 335:441 */     if (hasHiddenXslAttribute) {
/* 336:442 */       return false;
/* 337:    */     }
/* 338:444 */     if (this._attributeElements != null)
/* 339:    */     {
/* 340:445 */       int numAttrs = this._attributeElements.size();
/* 341:446 */       Hashtable attrsTable = null;
/* 342:447 */       for (int i = 0; i < numAttrs; i++)
/* 343:    */       {
/* 344:448 */         SyntaxTreeNode node = (SyntaxTreeNode)this._attributeElements.elementAt(i);
/* 345:450 */         if ((node instanceof UseAttributeSets)) {
/* 346:451 */           return false;
/* 347:    */         }
/* 348:453 */         if ((node instanceof XslAttribute))
/* 349:    */         {
/* 350:454 */           if (attrsTable == null)
/* 351:    */           {
/* 352:455 */             attrsTable = new Hashtable();
/* 353:456 */             for (int k = 0; k < i; k++)
/* 354:    */             {
/* 355:457 */               SyntaxTreeNode n = (SyntaxTreeNode)this._attributeElements.elementAt(k);
/* 356:458 */               if ((n instanceof LiteralAttribute))
/* 357:    */               {
/* 358:459 */                 LiteralAttribute literalAttr = (LiteralAttribute)n;
/* 359:460 */                 attrsTable.put(literalAttr.getName(), literalAttr);
/* 360:    */               }
/* 361:    */             }
/* 362:    */           }
/* 363:465 */           XslAttribute xslAttr = (XslAttribute)node;
/* 364:466 */           AttributeValue attrName = xslAttr.getName();
/* 365:467 */           if ((attrName instanceof AttributeValueTemplate)) {
/* 366:468 */             return false;
/* 367:    */           }
/* 368:470 */           if ((attrName instanceof SimpleAttributeValue))
/* 369:    */           {
/* 370:471 */             SimpleAttributeValue simpleAttr = (SimpleAttributeValue)attrName;
/* 371:472 */             String name = simpleAttr.toString();
/* 372:473 */             if ((name != null) && (attrsTable.get(name) != null)) {
/* 373:474 */               return false;
/* 374:    */             }
/* 375:475 */             if (name != null) {
/* 376:476 */               attrsTable.put(name, xslAttr);
/* 377:    */             }
/* 378:    */           }
/* 379:    */         }
/* 380:    */       }
/* 381:    */     }
/* 382:482 */     return true;
/* 383:    */   }
/* 384:    */   
/* 385:    */   private boolean canProduceAttributeNodes(SyntaxTreeNode node, boolean ignoreXslAttribute)
/* 386:    */   {
/* 387:492 */     Vector contents = node.getContents();
/* 388:493 */     int size = contents.size();
/* 389:494 */     for (int i = 0; i < size; i++)
/* 390:    */     {
/* 391:495 */       SyntaxTreeNode child = (SyntaxTreeNode)contents.elementAt(i);
/* 392:496 */       if ((child instanceof Text))
/* 393:    */       {
/* 394:497 */         Text text = (Text)child;
/* 395:498 */         if (!text.isIgnore()) {
/* 396:501 */           return false;
/* 397:    */         }
/* 398:    */       }
/* 399:    */       else
/* 400:    */       {
/* 401:505 */         if (((child instanceof LiteralElement)) || ((child instanceof ValueOf)) || ((child instanceof XslElement)) || ((child instanceof Comment)) || ((child instanceof Number)) || ((child instanceof ProcessingInstruction))) {
/* 402:511 */           return false;
/* 403:    */         }
/* 404:512 */         if ((child instanceof XslAttribute))
/* 405:    */         {
/* 406:513 */           if (!ignoreXslAttribute) {
/* 407:516 */             return true;
/* 408:    */           }
/* 409:    */         }
/* 410:    */         else
/* 411:    */         {
/* 412:522 */           if (((child instanceof CallTemplate)) || ((child instanceof ApplyTemplates)) || ((child instanceof Copy)) || ((child instanceof CopyOf))) {
/* 413:526 */             return true;
/* 414:    */           }
/* 415:527 */           if ((((child instanceof If)) || ((child instanceof ForEach))) && (canProduceAttributeNodes(child, false))) {
/* 416:530 */             return true;
/* 417:    */           }
/* 418:532 */           if ((child instanceof Choose))
/* 419:    */           {
/* 420:533 */             Vector chooseContents = child.getContents();
/* 421:534 */             int num = chooseContents.size();
/* 422:535 */             for (int k = 0; k < num; k++)
/* 423:    */             {
/* 424:536 */               SyntaxTreeNode chooseChild = (SyntaxTreeNode)chooseContents.elementAt(k);
/* 425:537 */               if ((((chooseChild instanceof When)) || ((chooseChild instanceof Otherwise))) && 
/* 426:538 */                 (canProduceAttributeNodes(chooseChild, false))) {
/* 427:539 */                 return true;
/* 428:    */               }
/* 429:    */             }
/* 430:    */           }
/* 431:    */         }
/* 432:    */       }
/* 433:    */     }
/* 434:544 */     return false;
/* 435:    */   }
/* 436:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.compiler.LiteralElement
 * JD-Core Version:    0.7.0.1
 */