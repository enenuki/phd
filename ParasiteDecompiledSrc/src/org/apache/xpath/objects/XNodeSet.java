/*   1:    */ package org.apache.xpath.objects;
/*   2:    */ 
/*   3:    */ import java.util.Vector;
/*   4:    */ import javax.xml.transform.TransformerException;
/*   5:    */ import org.apache.xml.dtm.DTM;
/*   6:    */ import org.apache.xml.dtm.DTMIterator;
/*   7:    */ import org.apache.xml.dtm.DTMManager;
/*   8:    */ import org.apache.xml.dtm.ref.DTMNodeIterator;
/*   9:    */ import org.apache.xml.dtm.ref.DTMNodeList;
/*  10:    */ import org.apache.xml.utils.FastStringBuffer;
/*  11:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  12:    */ import org.apache.xml.utils.XMLString;
/*  13:    */ import org.apache.xpath.NodeSetDTM;
/*  14:    */ import org.apache.xpath.axes.NodeSequence;
/*  15:    */ import org.w3c.dom.NodeList;
/*  16:    */ import org.w3c.dom.traversal.NodeIterator;
/*  17:    */ import org.xml.sax.ContentHandler;
/*  18:    */ import org.xml.sax.SAXException;
/*  19:    */ 
/*  20:    */ public class XNodeSet
/*  21:    */   extends NodeSequence
/*  22:    */ {
/*  23:    */   static final long serialVersionUID = 1916026368035639667L;
/*  24:    */   
/*  25:    */   protected XNodeSet() {}
/*  26:    */   
/*  27:    */   public XNodeSet(DTMIterator val)
/*  28:    */   {
/*  29: 56 */     if ((val instanceof XNodeSet))
/*  30:    */     {
/*  31: 58 */       XNodeSet nodeSet = (XNodeSet)val;
/*  32: 59 */       setIter(nodeSet.m_iter);
/*  33: 60 */       this.m_dtmMgr = nodeSet.m_dtmMgr;
/*  34: 61 */       this.m_last = nodeSet.m_last;
/*  35: 64 */       if (!nodeSet.hasCache()) {
/*  36: 65 */         nodeSet.setShouldCacheNodes(true);
/*  37:    */       }
/*  38: 68 */       setObject(nodeSet.getIteratorCache());
/*  39:    */     }
/*  40:    */     else
/*  41:    */     {
/*  42: 71 */       setIter(val);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public XNodeSet(XNodeSet val)
/*  47:    */   {
/*  48: 82 */     setIter(val.m_iter);
/*  49: 83 */     this.m_dtmMgr = val.m_dtmMgr;
/*  50: 84 */     this.m_last = val.m_last;
/*  51: 85 */     if (!val.hasCache()) {
/*  52: 86 */       val.setShouldCacheNodes(true);
/*  53:    */     }
/*  54: 87 */     setObject(val.m_obj);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public XNodeSet(DTMManager dtmMgr)
/*  58:    */   {
/*  59: 97 */     this(-1, dtmMgr);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public XNodeSet(int n, DTMManager dtmMgr)
/*  63:    */   {
/*  64:108 */     super(new NodeSetDTM(dtmMgr));
/*  65:109 */     this.m_dtmMgr = dtmMgr;
/*  66:111 */     if (-1 != n)
/*  67:    */     {
/*  68:113 */       ((NodeSetDTM)this.m_obj).addNode(n);
/*  69:114 */       this.m_last = 1;
/*  70:    */     }
/*  71:    */     else
/*  72:    */     {
/*  73:117 */       this.m_last = 0;
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getType()
/*  78:    */   {
/*  79:127 */     return 4;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getTypeString()
/*  83:    */   {
/*  84:138 */     return "#NODESET";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public double getNumberFromNode(int n)
/*  88:    */   {
/*  89:150 */     XMLString xstr = this.m_dtmMgr.getDTM(n).getStringValue(n);
/*  90:151 */     return xstr.toDouble();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public double num()
/*  94:    */   {
/*  95:163 */     int node = item(0);
/*  96:164 */     return node != -1 ? getNumberFromNode(node) : (0.0D / 0.0D);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public double numWithSideEffects()
/* 100:    */   {
/* 101:176 */     int node = nextNode();
/* 102:    */     
/* 103:178 */     return node != -1 ? getNumberFromNode(node) : (0.0D / 0.0D);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public boolean bool()
/* 107:    */   {
/* 108:189 */     return item(0) != -1;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean boolWithSideEffects()
/* 112:    */   {
/* 113:200 */     return nextNode() != -1;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public XMLString getStringFromNode(int n)
/* 117:    */   {
/* 118:215 */     if (-1 != n) {
/* 119:217 */       return this.m_dtmMgr.getDTM(n).getStringValue(n);
/* 120:    */     }
/* 121:221 */     return XString.EMPTYSTRING;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void dispatchCharactersEvents(ContentHandler ch)
/* 125:    */     throws SAXException
/* 126:    */   {
/* 127:239 */     int node = item(0);
/* 128:241 */     if (node != -1) {
/* 129:243 */       this.m_dtmMgr.getDTM(node).dispatchCharactersEvents(node, ch, false);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   public XMLString xstr()
/* 134:    */   {
/* 135:255 */     int node = item(0);
/* 136:256 */     return node != -1 ? getStringFromNode(node) : XString.EMPTYSTRING;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void appendToFsb(FastStringBuffer fsb)
/* 140:    */   {
/* 141:266 */     XString xstring = (XString)xstr();
/* 142:267 */     xstring.appendToFsb(fsb);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String str()
/* 146:    */   {
/* 147:279 */     int node = item(0);
/* 148:280 */     return node != -1 ? getStringFromNode(node).toString() : "";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Object object()
/* 152:    */   {
/* 153:291 */     if (null == this.m_obj) {
/* 154:292 */       return this;
/* 155:    */     }
/* 156:294 */     return this.m_obj;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public NodeIterator nodeset()
/* 160:    */     throws TransformerException
/* 161:    */   {
/* 162:334 */     return new DTMNodeIterator(iter());
/* 163:    */   }
/* 164:    */   
/* 165:    */   public NodeList nodelist()
/* 166:    */     throws TransformerException
/* 167:    */   {
/* 168:346 */     DTMNodeList nodelist = new DTMNodeList(this);
/* 169:    */     
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:351 */     XNodeSet clone = (XNodeSet)nodelist.getDTMIterator();
/* 174:352 */     SetVector(clone.getVector());
/* 175:353 */     return nodelist;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public DTMIterator iterRaw()
/* 179:    */   {
/* 180:373 */     return this;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void release(DTMIterator iter) {}
/* 184:    */   
/* 185:    */   public DTMIterator iter()
/* 186:    */   {
/* 187:    */     try
/* 188:    */     {
/* 189:389 */       if (hasCache()) {
/* 190:390 */         return cloneWithReset();
/* 191:    */       }
/* 192:392 */       return this;
/* 193:    */     }
/* 194:    */     catch (CloneNotSupportedException cnse)
/* 195:    */     {
/* 196:396 */       throw new RuntimeException(cnse.getMessage());
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public XObject getFresh()
/* 201:    */   {
/* 202:    */     try
/* 203:    */     {
/* 204:409 */       if (hasCache()) {
/* 205:410 */         return (XObject)cloneWithReset();
/* 206:    */       }
/* 207:412 */       return this;
/* 208:    */     }
/* 209:    */     catch (CloneNotSupportedException cnse)
/* 210:    */     {
/* 211:416 */       throw new RuntimeException(cnse.getMessage());
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public NodeSetDTM mutableNodeset()
/* 216:    */   {
/* 217:    */     NodeSetDTM mnl;
/* 218:429 */     if ((this.m_obj instanceof NodeSetDTM))
/* 219:    */     {
/* 220:431 */       mnl = (NodeSetDTM)this.m_obj;
/* 221:    */     }
/* 222:    */     else
/* 223:    */     {
/* 224:435 */       mnl = new NodeSetDTM(iter());
/* 225:436 */       setObject(mnl);
/* 226:437 */       setCurrentPos(0);
/* 227:    */     }
/* 228:440 */     return mnl;
/* 229:    */   }
/* 230:    */   
/* 231:444 */   static final LessThanComparator S_LT = new LessThanComparator();
/* 232:447 */   static final LessThanOrEqualComparator S_LTE = new LessThanOrEqualComparator();
/* 233:450 */   static final GreaterThanComparator S_GT = new GreaterThanComparator();
/* 234:453 */   static final GreaterThanOrEqualComparator S_GTE = new GreaterThanOrEqualComparator();
/* 235:457 */   static final EqualComparator S_EQ = new EqualComparator();
/* 236:460 */   static final NotEqualComparator S_NEQ = new NotEqualComparator();
/* 237:    */   
/* 238:    */   public boolean compare(XObject obj2, Comparator comparator)
/* 239:    */     throws TransformerException
/* 240:    */   {
/* 241:476 */     boolean result = false;
/* 242:477 */     int type = obj2.getType();
/* 243:    */     Vector node2Strings;
/* 244:479 */     if (4 == type)
/* 245:    */     {
/* 246:494 */       DTMIterator list1 = iterRaw();
/* 247:495 */       DTMIterator list2 = ((XNodeSet)obj2).iterRaw();
/* 248:    */       
/* 249:497 */       node2Strings = null;
/* 250:    */       int node1;
/* 251:499 */       while (-1 != (node1 = list1.nextNode()))
/* 252:    */       {
/* 253:    */         int i;
/* 254:501 */         XMLString s1 = getStringFromNode(i);
/* 255:503 */         if (null == node2Strings)
/* 256:    */         {
/* 257:    */           int node2;
/* 258:507 */           while (-1 != (node2 = list2.nextNode()))
/* 259:    */           {
/* 260:    */             int j;
/* 261:509 */             XMLString s2 = getStringFromNode(j);
/* 262:511 */             if (comparator.compareStrings(s1, s2))
/* 263:    */             {
/* 264:513 */               result = true;
/* 265:    */               
/* 266:515 */               break;
/* 267:    */             }
/* 268:518 */             if (null == node2Strings) {
/* 269:519 */               node2Strings = new Vector();
/* 270:    */             }
/* 271:521 */             node2Strings.addElement(s2);
/* 272:    */           }
/* 273:    */         }
/* 274:    */         else
/* 275:    */         {
/* 276:526 */           int n = node2Strings.size();
/* 277:528 */           for (int i = 0; i < n; i++) {
/* 278:530 */             if (comparator.compareStrings(s1, (XMLString)node2Strings.elementAt(i)))
/* 279:    */             {
/* 280:532 */               result = true;
/* 281:    */               
/* 282:534 */               break;
/* 283:    */             }
/* 284:    */           }
/* 285:    */         }
/* 286:    */       }
/* 287:539 */       list1.reset();
/* 288:540 */       list2.reset();
/* 289:    */     }
/* 290:    */     else
/* 291:    */     {
/* 292:    */       double num2;
/* 293:542 */       if (1 == type)
/* 294:    */       {
/* 295:551 */         double num1 = bool() ? 1.0D : 0.0D;
/* 296:552 */         num2 = obj2.num();
/* 297:    */         
/* 298:554 */         result = comparator.compareNumbers(num1, num2);
/* 299:    */       }
/* 300:556 */       else if (2 == type)
/* 301:    */       {
/* 302:566 */         DTMIterator list1 = iterRaw();
/* 303:567 */         double num2 = obj2.num();
/* 304:    */         int node;
/* 305:570 */         while (-1 != (node = list1.nextNode()))
/* 306:    */         {
/* 307:572 */           double num1 = getNumberFromNode(node2Strings);
/* 308:574 */           if (comparator.compareNumbers(num1, num2))
/* 309:    */           {
/* 310:576 */             result = true;
/* 311:    */             
/* 312:578 */             break;
/* 313:    */           }
/* 314:    */         }
/* 315:581 */         list1.reset();
/* 316:    */       }
/* 317:    */       else
/* 318:    */       {
/* 319:    */         int node;
/* 320:583 */         if (5 == type)
/* 321:    */         {
/* 322:585 */           XMLString s2 = obj2.xstr();
/* 323:586 */           DTMIterator list1 = iterRaw();
/* 324:589 */           while (-1 != (node = list1.nextNode()))
/* 325:    */           {
/* 326:591 */             XMLString s1 = getStringFromNode(num2);
/* 327:593 */             if (comparator.compareStrings(s1, s2))
/* 328:    */             {
/* 329:595 */               result = true;
/* 330:    */               
/* 331:597 */               break;
/* 332:    */             }
/* 333:    */           }
/* 334:600 */           list1.reset();
/* 335:    */         }
/* 336:602 */         else if (3 == type)
/* 337:    */         {
/* 338:611 */           XMLString s2 = obj2.xstr();
/* 339:612 */           DTMIterator list1 = iterRaw();
/* 340:    */           int node;
/* 341:615 */           while (-1 != (node = list1.nextNode()))
/* 342:    */           {
/* 343:617 */             XMLString s1 = getStringFromNode(node);
/* 344:618 */             if (comparator.compareStrings(s1, s2))
/* 345:    */             {
/* 346:620 */               result = true;
/* 347:    */               
/* 348:622 */               break;
/* 349:    */             }
/* 350:    */           }
/* 351:625 */           list1.reset();
/* 352:    */         }
/* 353:    */         else
/* 354:    */         {
/* 355:629 */           result = comparator.compareNumbers(num(), obj2.num());
/* 356:    */         }
/* 357:    */       }
/* 358:    */     }
/* 359:632 */     return result;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public boolean lessThan(XObject obj2)
/* 363:    */     throws TransformerException
/* 364:    */   {
/* 365:646 */     return compare(obj2, S_LT);
/* 366:    */   }
/* 367:    */   
/* 368:    */   public boolean lessThanOrEqual(XObject obj2)
/* 369:    */     throws TransformerException
/* 370:    */   {
/* 371:660 */     return compare(obj2, S_LTE);
/* 372:    */   }
/* 373:    */   
/* 374:    */   public boolean greaterThan(XObject obj2)
/* 375:    */     throws TransformerException
/* 376:    */   {
/* 377:674 */     return compare(obj2, S_GT);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public boolean greaterThanOrEqual(XObject obj2)
/* 381:    */     throws TransformerException
/* 382:    */   {
/* 383:689 */     return compare(obj2, S_GTE);
/* 384:    */   }
/* 385:    */   
/* 386:    */   public boolean equals(XObject obj2)
/* 387:    */   {
/* 388:    */     try
/* 389:    */     {
/* 390:705 */       return compare(obj2, S_EQ);
/* 391:    */     }
/* 392:    */     catch (TransformerException te)
/* 393:    */     {
/* 394:709 */       throw new WrappedRuntimeException(te);
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   public boolean notEquals(XObject obj2)
/* 399:    */     throws TransformerException
/* 400:    */   {
/* 401:724 */     return compare(obj2, S_NEQ);
/* 402:    */   }
/* 403:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XNodeSet
 * JD-Core Version:    0.7.0.1
 */