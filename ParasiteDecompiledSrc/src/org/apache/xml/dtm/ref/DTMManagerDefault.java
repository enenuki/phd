/*   1:    */ package org.apache.xml.dtm.ref;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import javax.xml.parsers.DocumentBuilder;
/*   5:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*   6:    */ import javax.xml.transform.Source;
/*   7:    */ import javax.xml.transform.dom.DOMSource;
/*   8:    */ import javax.xml.transform.sax.SAXSource;
/*   9:    */ import javax.xml.transform.stream.StreamSource;
/*  10:    */ import org.apache.xml.dtm.DTM;
/*  11:    */ import org.apache.xml.dtm.DTMException;
/*  12:    */ import org.apache.xml.dtm.DTMFilter;
/*  13:    */ import org.apache.xml.dtm.DTMIterator;
/*  14:    */ import org.apache.xml.dtm.DTMManager;
/*  15:    */ import org.apache.xml.dtm.DTMWSFilter;
/*  16:    */ import org.apache.xml.dtm.ref.dom2dtm.DOM2DTM;
/*  17:    */ import org.apache.xml.dtm.ref.dom2dtm.DOM2DTMdefaultNamespaceDeclarationNode;
/*  18:    */ import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM;
/*  19:    */ import org.apache.xml.dtm.ref.sax2dtm.SAX2RTFDTM;
/*  20:    */ import org.apache.xml.res.XMLMessages;
/*  21:    */ import org.apache.xml.utils.PrefixResolver;
/*  22:    */ import org.apache.xml.utils.SuballocatedIntVector;
/*  23:    */ import org.apache.xml.utils.SystemIDResolver;
/*  24:    */ import org.apache.xml.utils.WrappedRuntimeException;
/*  25:    */ import org.apache.xml.utils.XMLReaderManager;
/*  26:    */ import org.apache.xml.utils.XMLStringFactory;
/*  27:    */ import org.w3c.dom.Attr;
/*  28:    */ import org.w3c.dom.Document;
/*  29:    */ import org.w3c.dom.Node;
/*  30:    */ import org.xml.sax.InputSource;
/*  31:    */ import org.xml.sax.SAXException;
/*  32:    */ import org.xml.sax.SAXNotRecognizedException;
/*  33:    */ import org.xml.sax.SAXNotSupportedException;
/*  34:    */ import org.xml.sax.XMLReader;
/*  35:    */ import org.xml.sax.helpers.DefaultHandler;
/*  36:    */ 
/*  37:    */ public class DTMManagerDefault
/*  38:    */   extends DTMManager
/*  39:    */ {
/*  40:    */   private static final boolean DUMPTREE = false;
/*  41:    */   private static final boolean DEBUG = false;
/*  42: 94 */   protected DTM[] m_dtms = new DTM[256];
/*  43:109 */   int[] m_dtm_offsets = new int[256];
/*  44:115 */   protected XMLReaderManager m_readerManager = null;
/*  45:120 */   protected DefaultHandler m_defaultHandler = new DefaultHandler();
/*  46:    */   
/*  47:    */   public synchronized void addDTM(DTM dtm, int id)
/*  48:    */   {
/*  49:130 */     addDTM(dtm, id, 0);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public synchronized void addDTM(DTM dtm, int id, int offset)
/*  53:    */   {
/*  54:145 */     if (id >= 65536) {
/*  55:148 */       throw new DTMException(XMLMessages.createXMLMessage("ER_NO_DTMIDS_AVAIL", null));
/*  56:    */     }
/*  57:156 */     int oldlen = this.m_dtms.length;
/*  58:157 */     if (oldlen <= id)
/*  59:    */     {
/*  60:164 */       int newlen = Math.min(id + 256, 65536);
/*  61:    */       
/*  62:166 */       DTM[] new_m_dtms = new DTM[newlen];
/*  63:167 */       System.arraycopy(this.m_dtms, 0, new_m_dtms, 0, oldlen);
/*  64:168 */       this.m_dtms = new_m_dtms;
/*  65:169 */       int[] new_m_dtm_offsets = new int[newlen];
/*  66:170 */       System.arraycopy(this.m_dtm_offsets, 0, new_m_dtm_offsets, 0, oldlen);
/*  67:171 */       this.m_dtm_offsets = new_m_dtm_offsets;
/*  68:    */     }
/*  69:174 */     this.m_dtms[id] = dtm;
/*  70:175 */     this.m_dtm_offsets[id] = offset;
/*  71:176 */     dtm.documentRegistration();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public synchronized int getFirstFreeDTMID()
/*  75:    */   {
/*  76:186 */     int n = this.m_dtms.length;
/*  77:187 */     for (int i = 1; i < n; i++) {
/*  78:189 */       if (null == this.m_dtms[i]) {
/*  79:191 */         return i;
/*  80:    */       }
/*  81:    */     }
/*  82:194 */     return n;
/*  83:    */   }
/*  84:    */   
/*  85:200 */   private ExpandedNameTable m_expandedNameTable = new ExpandedNameTable();
/*  86:    */   
/*  87:    */   public synchronized DTM getDTM(Source source, boolean unique, DTMWSFilter whiteSpaceFilter, boolean incremental, boolean doIndexing)
/*  88:    */   {
/*  89:247 */     XMLStringFactory xstringFactory = this.m_xsf;
/*  90:248 */     int dtmPos = getFirstFreeDTMID();
/*  91:249 */     int documentID = dtmPos << 16;
/*  92:251 */     if ((null != source) && ((source instanceof DOMSource)))
/*  93:    */     {
/*  94:253 */       DOM2DTM dtm = new DOM2DTM(this, (DOMSource)source, documentID, whiteSpaceFilter, xstringFactory, doIndexing);
/*  95:    */       
/*  96:    */ 
/*  97:256 */       addDTM(dtm, dtmPos, 0);
/*  98:    */       
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:263 */       return dtm;
/* 105:    */     }
/* 106:267 */     boolean isSAXSource = null != source ? source instanceof SAXSource : true;
/* 107:    */     
/* 108:269 */     boolean isStreamSource = null != source ? source instanceof StreamSource : false;
/* 109:272 */     if ((isSAXSource) || (isStreamSource))
/* 110:    */     {
/* 111:273 */       XMLReader reader = null;
/* 112:    */       try
/* 113:    */       {
/* 114:    */         InputSource xmlSource;
/* 115:279 */         if (null == source)
/* 116:    */         {
/* 117:280 */           xmlSource = null;
/* 118:    */         }
/* 119:    */         else
/* 120:    */         {
/* 121:282 */           reader = getXMLReader(source);
/* 122:283 */           xmlSource = SAXSource.sourceToInputSource(source);
/* 123:    */           
/* 124:285 */           String urlOfSource = xmlSource.getSystemId();
/* 125:287 */           if (null != urlOfSource)
/* 126:    */           {
/* 127:    */             try
/* 128:    */             {
/* 129:289 */               urlOfSource = SystemIDResolver.getAbsoluteURI(urlOfSource);
/* 130:    */             }
/* 131:    */             catch (Exception e)
/* 132:    */             {
/* 133:292 */               System.err.println("Can not absolutize URL: " + urlOfSource);
/* 134:    */             }
/* 135:295 */             xmlSource.setSystemId(urlOfSource);
/* 136:    */           }
/* 137:    */         }
/* 138:    */         SAX2DTM dtm;
/* 139:299 */         if ((source == null) && (unique) && (!incremental) && (!doIndexing)) {
/* 140:307 */           dtm = new SAX2RTFDTM(this, source, documentID, whiteSpaceFilter, xstringFactory, doIndexing);
/* 141:    */         } else {
/* 142:319 */           dtm = new SAX2DTM(this, source, documentID, whiteSpaceFilter, xstringFactory, doIndexing);
/* 143:    */         }
/* 144:326 */         addDTM(dtm, dtmPos, 0);
/* 145:    */         
/* 146:    */ 
/* 147:329 */         boolean haveXercesParser = (null != reader) && (reader.getClass().getName().equals("org.apache.xerces.parsers.SAXParser"));
/* 148:335 */         if (haveXercesParser) {
/* 149:336 */           incremental = true;
/* 150:    */         }
/* 151:    */         IncrementalSAXSource coParser;
/* 152:341 */         if ((this.m_incremental) && (incremental))
/* 153:    */         {
/* 154:343 */           coParser = null;
/* 155:345 */           if (haveXercesParser) {
/* 156:    */             try
/* 157:    */             {
/* 158:348 */               coParser = (IncrementalSAXSource)Class.forName("org.apache.xml.dtm.ref.IncrementalSAXSource_Xerces").newInstance();
/* 159:    */             }
/* 160:    */             catch (Exception ex)
/* 161:    */             {
/* 162:351 */               ex.printStackTrace();
/* 163:352 */               coParser = null;
/* 164:    */             }
/* 165:    */           }
/* 166:    */           IncrementalSAXSource_Filter filter;
/* 167:356 */           if (coParser == null) {
/* 168:358 */             if (null == reader)
/* 169:    */             {
/* 170:359 */               coParser = new IncrementalSAXSource_Filter();
/* 171:    */             }
/* 172:    */             else
/* 173:    */             {
/* 174:361 */               filter = new IncrementalSAXSource_Filter();
/* 175:    */               
/* 176:363 */               filter.setXMLReader(reader);
/* 177:364 */               coParser = filter;
/* 178:    */             }
/* 179:    */           }
/* 180:388 */           dtm.setIncrementalSAXSource(coParser);
/* 181:390 */           if (null == xmlSource) {
/* 182:393 */             return dtm;
/* 183:    */           }
/* 184:396 */           if (null == reader.getErrorHandler()) {
/* 185:397 */             reader.setErrorHandler(dtm);
/* 186:    */           }
/* 187:399 */           reader.setDTDHandler(dtm);
/* 188:    */           try
/* 189:    */           {
/* 190:405 */             coParser.startParse(xmlSource);
/* 191:    */           }
/* 192:    */           catch (RuntimeException re)
/* 193:    */           {
/* 194:408 */             dtm.clearCoRoutine();
/* 195:    */             
/* 196:410 */             throw re;
/* 197:    */           }
/* 198:    */           catch (Exception e)
/* 199:    */           {
/* 200:413 */             dtm.clearCoRoutine();
/* 201:    */             
/* 202:415 */             throw new WrappedRuntimeException(e);
/* 203:    */           }
/* 204:    */         }
/* 205:    */         else
/* 206:    */         {
/* 207:418 */           if (null == reader) {
/* 208:421 */             return dtm;
/* 209:    */           }
/* 210:425 */           reader.setContentHandler(dtm);
/* 211:426 */           reader.setDTDHandler(dtm);
/* 212:427 */           if (null == reader.getErrorHandler()) {
/* 213:428 */             reader.setErrorHandler(dtm);
/* 214:    */           }
/* 215:    */           try
/* 216:    */           {
/* 217:432 */             reader.setProperty("http://xml.org/sax/properties/lexical-handler", dtm);
/* 218:    */           }
/* 219:    */           catch (SAXNotRecognizedException e) {}catch (SAXNotSupportedException e) {}
/* 220:    */           try
/* 221:    */           {
/* 222:439 */             reader.parse(xmlSource);
/* 223:    */           }
/* 224:    */           catch (RuntimeException re)
/* 225:    */           {
/* 226:441 */             dtm.clearCoRoutine();
/* 227:    */             
/* 228:443 */             throw re;
/* 229:    */           }
/* 230:    */           catch (Exception e)
/* 231:    */           {
/* 232:445 */             dtm.clearCoRoutine();
/* 233:    */             
/* 234:447 */             throw new WrappedRuntimeException(e);
/* 235:    */           }
/* 236:    */         }
/* 237:456 */         return dtm;
/* 238:    */       }
/* 239:    */       finally
/* 240:    */       {
/* 241:460 */         if ((reader != null) && ((!this.m_incremental) || (!incremental)))
/* 242:    */         {
/* 243:461 */           reader.setContentHandler(this.m_defaultHandler);
/* 244:462 */           reader.setDTDHandler(this.m_defaultHandler);
/* 245:463 */           reader.setErrorHandler(this.m_defaultHandler);
/* 246:    */           try
/* 247:    */           {
/* 248:467 */             reader.setProperty("http://xml.org/sax/properties/lexical-handler", null);
/* 249:    */           }
/* 250:    */           catch (Exception e) {}
/* 251:    */         }
/* 252:471 */         releaseXMLReader(reader);
/* 253:    */       }
/* 254:    */     }
/* 255:477 */     throw new DTMException(XMLMessages.createXMLMessage("ER_NOT_SUPPORTED", new Object[] { source }));
/* 256:    */   }
/* 257:    */   
/* 258:    */   public synchronized int getDTMHandleFromNode(Node node)
/* 259:    */   {
/* 260:493 */     if (null == node) {
/* 261:494 */       throw new IllegalArgumentException(XMLMessages.createXMLMessage("ER_NODE_NON_NULL", null));
/* 262:    */     }
/* 263:496 */     if ((node instanceof DTMNodeProxy)) {
/* 264:497 */       return ((DTMNodeProxy)node).getDTMNodeNumber();
/* 265:    */     }
/* 266:522 */     int max = this.m_dtms.length;
/* 267:523 */     for (int i = 0; i < max; i++)
/* 268:    */     {
/* 269:525 */       DTM thisDTM = this.m_dtms[i];
/* 270:526 */       if ((null != thisDTM) && ((thisDTM instanceof DOM2DTM)))
/* 271:    */       {
/* 272:528 */         int handle = ((DOM2DTM)thisDTM).getHandleOfNode(node);
/* 273:529 */         if (handle != -1) {
/* 274:529 */           return handle;
/* 275:    */         }
/* 276:    */       }
/* 277:    */     }
/* 278:552 */     Node root = node;
/* 279:553 */     for (Node p = root.getNodeType() == 2 ? ((Attr)root).getOwnerElement() : root.getParentNode(); p != null; p = p.getParentNode()) {
/* 280:556 */       root = p;
/* 281:    */     }
/* 282:559 */     DOM2DTM dtm = (DOM2DTM)getDTM(new DOMSource(root), false, null, true, true);
/* 283:    */     int handle;
/* 284:564 */     if ((node instanceof DOM2DTMdefaultNamespaceDeclarationNode))
/* 285:    */     {
/* 286:569 */       handle = dtm.getHandleOfNode(((Attr)node).getOwnerElement());
/* 287:570 */       handle = dtm.getAttributeNode(handle, node.getNamespaceURI(), node.getLocalName());
/* 288:    */     }
/* 289:    */     else
/* 290:    */     {
/* 291:573 */       handle = dtm.getHandleOfNode(node);
/* 292:    */     }
/* 293:575 */     if (-1 == handle) {
/* 294:576 */       throw new RuntimeException(XMLMessages.createXMLMessage("ER_COULD_NOT_RESOLVE_NODE", null));
/* 295:    */     }
/* 296:578 */     return handle;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public synchronized XMLReader getXMLReader(Source inputSource)
/* 300:    */   {
/* 301:    */     try
/* 302:    */     {
/* 303:601 */       XMLReader reader = (inputSource instanceof SAXSource) ? ((SAXSource)inputSource).getXMLReader() : null;
/* 304:605 */       if (null == reader) {
/* 305:606 */         if (this.m_readerManager == null) {
/* 306:607 */           this.m_readerManager = XMLReaderManager.getInstance();
/* 307:    */         }
/* 308:    */       }
/* 309:610 */       return this.m_readerManager.getXMLReader();
/* 310:    */     }
/* 311:    */     catch (SAXException se)
/* 312:    */     {
/* 313:616 */       throw new DTMException(se.getMessage(), se);
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   public synchronized void releaseXMLReader(XMLReader reader)
/* 318:    */   {
/* 319:631 */     if (this.m_readerManager != null) {
/* 320:632 */       this.m_readerManager.releaseXMLReader(reader);
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public synchronized DTM getDTM(int nodeHandle)
/* 325:    */   {
/* 326:    */     try
/* 327:    */     {
/* 328:648 */       return this.m_dtms[(nodeHandle >>> 16)];
/* 329:    */     }
/* 330:    */     catch (ArrayIndexOutOfBoundsException e)
/* 331:    */     {
/* 332:652 */       if (nodeHandle == -1) {
/* 333:653 */         return null;
/* 334:    */       }
/* 335:655 */       throw e;
/* 336:    */     }
/* 337:    */   }
/* 338:    */   
/* 339:    */   public synchronized int getDTMIdentity(DTM dtm)
/* 340:    */   {
/* 341:673 */     if ((dtm instanceof DTMDefaultBase))
/* 342:    */     {
/* 343:675 */       DTMDefaultBase dtmdb = (DTMDefaultBase)dtm;
/* 344:676 */       if (dtmdb.getManager() == this) {
/* 345:677 */         return dtmdb.getDTMIDs().elementAt(0);
/* 346:    */       }
/* 347:679 */       return -1;
/* 348:    */     }
/* 349:682 */     int n = this.m_dtms.length;
/* 350:684 */     for (int i = 0; i < n; i++)
/* 351:    */     {
/* 352:686 */       DTM tdtm = this.m_dtms[i];
/* 353:688 */       if ((tdtm == dtm) && (this.m_dtm_offsets[i] == 0)) {
/* 354:689 */         return i << 16;
/* 355:    */       }
/* 356:    */     }
/* 357:692 */     return -1;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public synchronized boolean release(DTM dtm, boolean shouldHardDelete)
/* 361:    */   {
/* 362:723 */     if ((dtm instanceof SAX2DTM)) {
/* 363:725 */       ((SAX2DTM)dtm).clearCoRoutine();
/* 364:    */     }
/* 365:736 */     if ((dtm instanceof DTMDefaultBase))
/* 366:    */     {
/* 367:738 */       SuballocatedIntVector ids = ((DTMDefaultBase)dtm).getDTMIDs();
/* 368:739 */       for (int i = ids.size() - 1; i >= 0; i--) {
/* 369:740 */         this.m_dtms[(ids.elementAt(i) >>> 16)] = null;
/* 370:    */       }
/* 371:    */     }
/* 372:    */     else
/* 373:    */     {
/* 374:744 */       int i = getDTMIdentity(dtm);
/* 375:745 */       if (i >= 0) {
/* 376:747 */         this.m_dtms[(i >>> 16)] = null;
/* 377:    */       }
/* 378:    */     }
/* 379:751 */     dtm.documentRelease();
/* 380:752 */     return true;
/* 381:    */   }
/* 382:    */   
/* 383:    */   public synchronized DTM createDocumentFragment()
/* 384:    */   {
/* 385:    */     try
/* 386:    */     {
/* 387:766 */       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 388:    */       
/* 389:768 */       dbf.setNamespaceAware(true);
/* 390:    */       
/* 391:770 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 392:771 */       Document doc = db.newDocument();
/* 393:772 */       Node df = doc.createDocumentFragment();
/* 394:    */       
/* 395:774 */       return getDTM(new DOMSource(df), true, null, false, false);
/* 396:    */     }
/* 397:    */     catch (Exception e)
/* 398:    */     {
/* 399:778 */       throw new DTMException(e);
/* 400:    */     }
/* 401:    */   }
/* 402:    */   
/* 403:    */   public synchronized DTMIterator createDTMIterator(int whatToShow, DTMFilter filter, boolean entityReferenceExpansion)
/* 404:    */   {
/* 405:797 */     return null;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public synchronized DTMIterator createDTMIterator(String xpathString, PrefixResolver presolver)
/* 409:    */   {
/* 410:814 */     return null;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public synchronized DTMIterator createDTMIterator(int node)
/* 414:    */   {
/* 415:829 */     return null;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public synchronized DTMIterator createDTMIterator(Object xpathCompiler, int pos)
/* 419:    */   {
/* 420:845 */     return null;
/* 421:    */   }
/* 422:    */   
/* 423:    */   public ExpandedNameTable getExpandedNameTable(DTM dtm)
/* 424:    */   {
/* 425:857 */     return this.m_expandedNameTable;
/* 426:    */   }
/* 427:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.DTMManagerDefault
 * JD-Core Version:    0.7.0.1
 */