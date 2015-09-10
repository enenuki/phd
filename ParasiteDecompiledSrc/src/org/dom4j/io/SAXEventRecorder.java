/*   1:    */ package org.dom4j.io;
/*   2:    */ 
/*   3:    */ import java.io.Externalizable;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.ObjectInput;
/*   6:    */ import java.io.ObjectOutput;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import org.dom4j.Namespace;
/*  13:    */ import org.dom4j.QName;
/*  14:    */ import org.xml.sax.Attributes;
/*  15:    */ import org.xml.sax.ContentHandler;
/*  16:    */ import org.xml.sax.DTDHandler;
/*  17:    */ import org.xml.sax.SAXException;
/*  18:    */ import org.xml.sax.ext.DeclHandler;
/*  19:    */ import org.xml.sax.ext.LexicalHandler;
/*  20:    */ import org.xml.sax.helpers.AttributesImpl;
/*  21:    */ import org.xml.sax.helpers.DefaultHandler;
/*  22:    */ 
/*  23:    */ public class SAXEventRecorder
/*  24:    */   extends DefaultHandler
/*  25:    */   implements LexicalHandler, DeclHandler, DTDHandler, Externalizable
/*  26:    */ {
/*  27:    */   public static final long serialVersionUID = 1L;
/*  28:    */   private static final byte STRING = 0;
/*  29:    */   private static final byte OBJECT = 1;
/*  30:    */   private static final byte NULL = 2;
/*  31: 70 */   private List events = new ArrayList();
/*  32: 72 */   private Map prefixMappings = new HashMap();
/*  33:    */   private static final String XMLNS = "xmlns";
/*  34:    */   private static final String EMPTY_STRING = "";
/*  35:    */   
/*  36:    */   public void replay(ContentHandler handler)
/*  37:    */     throws SAXException
/*  38:    */   {
/*  39: 83 */     Iterator itr = this.events.iterator();
/*  40: 85 */     while (itr.hasNext())
/*  41:    */     {
/*  42: 86 */       SAXEvent saxEvent = (SAXEvent)itr.next();
/*  43: 88 */       switch (saxEvent.event)
/*  44:    */       {
/*  45:    */       case 1: 
/*  46: 91 */         handler.processingInstruction((String)saxEvent.getParm(0), (String)saxEvent.getParm(1));
/*  47:    */         
/*  48:    */ 
/*  49: 94 */         break;
/*  50:    */       case 2: 
/*  51: 97 */         handler.startPrefixMapping((String)saxEvent.getParm(0), (String)saxEvent.getParm(1));
/*  52:    */         
/*  53:    */ 
/*  54:100 */         break;
/*  55:    */       case 3: 
/*  56:103 */         handler.endPrefixMapping((String)saxEvent.getParm(0));
/*  57:    */         
/*  58:105 */         break;
/*  59:    */       case 4: 
/*  60:108 */         handler.startDocument();
/*  61:    */         
/*  62:110 */         break;
/*  63:    */       case 5: 
/*  64:113 */         handler.endDocument();
/*  65:    */         
/*  66:115 */         break;
/*  67:    */       case 6: 
/*  68:119 */         AttributesImpl attributes = new AttributesImpl();
/*  69:120 */         List attParmList = (List)saxEvent.getParm(3);
/*  70:122 */         if (attParmList != null)
/*  71:    */         {
/*  72:123 */           Iterator attsItr = attParmList.iterator();
/*  73:125 */           while (attsItr.hasNext())
/*  74:    */           {
/*  75:126 */             String[] attParms = (String[])attsItr.next();
/*  76:127 */             attributes.addAttribute(attParms[0], attParms[1], attParms[2], attParms[3], attParms[4]);
/*  77:    */           }
/*  78:    */         }
/*  79:132 */         handler.startElement((String)saxEvent.getParm(0), (String)saxEvent.getParm(1), (String)saxEvent.getParm(2), attributes);
/*  80:    */         
/*  81:    */ 
/*  82:    */ 
/*  83:136 */         break;
/*  84:    */       case 7: 
/*  85:139 */         handler.endElement((String)saxEvent.getParm(0), (String)saxEvent.getParm(1), (String)saxEvent.getParm(2));
/*  86:    */         
/*  87:    */ 
/*  88:    */ 
/*  89:143 */         break;
/*  90:    */       case 8: 
/*  91:147 */         char[] chars = (char[])saxEvent.getParm(0);
/*  92:148 */         int start = ((Integer)saxEvent.getParm(1)).intValue();
/*  93:149 */         int end = ((Integer)saxEvent.getParm(2)).intValue();
/*  94:150 */         handler.characters(chars, start, end);
/*  95:    */         
/*  96:152 */         break;
/*  97:    */       case 9: 
/*  98:156 */         ((LexicalHandler)handler).startDTD((String)saxEvent.getParm(0), (String)saxEvent.getParm(1), (String)saxEvent.getParm(2));
/*  99:    */         
/* 100:    */ 
/* 101:    */ 
/* 102:160 */         break;
/* 103:    */       case 10: 
/* 104:163 */         ((LexicalHandler)handler).endDTD();
/* 105:    */         
/* 106:165 */         break;
/* 107:    */       case 11: 
/* 108:168 */         ((LexicalHandler)handler).startEntity((String)saxEvent.getParm(0));
/* 109:    */         
/* 110:    */ 
/* 111:171 */         break;
/* 112:    */       case 12: 
/* 113:174 */         ((LexicalHandler)handler).endEntity((String)saxEvent.getParm(0));
/* 114:    */         
/* 115:    */ 
/* 116:177 */         break;
/* 117:    */       case 13: 
/* 118:180 */         ((LexicalHandler)handler).startCDATA();
/* 119:    */         
/* 120:182 */         break;
/* 121:    */       case 14: 
/* 122:185 */         ((LexicalHandler)handler).endCDATA();
/* 123:    */         
/* 124:187 */         break;
/* 125:    */       case 15: 
/* 126:191 */         char[] cchars = (char[])saxEvent.getParm(0);
/* 127:192 */         int cstart = ((Integer)saxEvent.getParm(1)).intValue();
/* 128:193 */         int cend = ((Integer)saxEvent.getParm(2)).intValue();
/* 129:194 */         ((LexicalHandler)handler).comment(cchars, cstart, cend);
/* 130:    */         
/* 131:196 */         break;
/* 132:    */       case 16: 
/* 133:200 */         ((DeclHandler)handler).elementDecl((String)saxEvent.getParm(0), (String)saxEvent.getParm(1));
/* 134:    */         
/* 135:    */ 
/* 136:203 */         break;
/* 137:    */       case 17: 
/* 138:206 */         ((DeclHandler)handler).attributeDecl((String)saxEvent.getParm(0), (String)saxEvent.getParm(1), (String)saxEvent.getParm(2), (String)saxEvent.getParm(3), (String)saxEvent.getParm(4));
/* 139:    */         
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:211 */         break;
/* 144:    */       case 18: 
/* 145:214 */         ((DeclHandler)handler).internalEntityDecl((String)saxEvent.getParm(0), (String)saxEvent.getParm(1));
/* 146:    */         
/* 147:    */ 
/* 148:    */ 
/* 149:218 */         break;
/* 150:    */       case 19: 
/* 151:221 */         ((DeclHandler)handler).externalEntityDecl((String)saxEvent.getParm(0), (String)saxEvent.getParm(1), (String)saxEvent.getParm(2));
/* 152:    */         
/* 153:    */ 
/* 154:    */ 
/* 155:225 */         break;
/* 156:    */       default: 
/* 157:228 */         throw new SAXException("Unrecognized event: " + saxEvent.event);
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void processingInstruction(String target, String data)
/* 163:    */     throws SAXException
/* 164:    */   {
/* 165:238 */     SAXEvent saxEvent = new SAXEvent((byte)1);
/* 166:239 */     saxEvent.addParm(target);
/* 167:240 */     saxEvent.addParm(data);
/* 168:241 */     this.events.add(saxEvent);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void startPrefixMapping(String prefix, String uri)
/* 172:    */     throws SAXException
/* 173:    */   {
/* 174:246 */     SAXEvent saxEvent = new SAXEvent((byte)2);
/* 175:247 */     saxEvent.addParm(prefix);
/* 176:248 */     saxEvent.addParm(uri);
/* 177:249 */     this.events.add(saxEvent);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void endPrefixMapping(String prefix)
/* 181:    */     throws SAXException
/* 182:    */   {
/* 183:253 */     SAXEvent saxEvent = new SAXEvent((byte)3);
/* 184:254 */     saxEvent.addParm(prefix);
/* 185:255 */     this.events.add(saxEvent);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void startDocument()
/* 189:    */     throws SAXException
/* 190:    */   {
/* 191:259 */     SAXEvent saxEvent = new SAXEvent((byte)4);
/* 192:260 */     this.events.add(saxEvent);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void endDocument()
/* 196:    */     throws SAXException
/* 197:    */   {
/* 198:264 */     SAXEvent saxEvent = new SAXEvent((byte)5);
/* 199:265 */     this.events.add(saxEvent);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attributes)
/* 203:    */     throws SAXException
/* 204:    */   {
/* 205:270 */     SAXEvent saxEvent = new SAXEvent((byte)6);
/* 206:271 */     saxEvent.addParm(namespaceURI);
/* 207:272 */     saxEvent.addParm(localName);
/* 208:273 */     saxEvent.addParm(qualifiedName);
/* 209:    */     
/* 210:275 */     QName qName = null;
/* 211:276 */     if (namespaceURI != null) {
/* 212:277 */       qName = new QName(localName, Namespace.get(namespaceURI));
/* 213:    */     } else {
/* 214:279 */       qName = new QName(localName);
/* 215:    */     }
/* 216:282 */     if ((attributes != null) && (attributes.getLength() > 0))
/* 217:    */     {
/* 218:283 */       List attParmList = new ArrayList(attributes.getLength());
/* 219:284 */       String[] attParms = null;
/* 220:286 */       for (int i = 0; i < attributes.getLength(); i++)
/* 221:    */       {
/* 222:288 */         String attLocalName = attributes.getLocalName(i);
/* 223:290 */         if (attLocalName.startsWith("xmlns"))
/* 224:    */         {
/* 225:295 */           String prefix = null;
/* 226:296 */           if (attLocalName.length() > 5) {
/* 227:297 */             prefix = attLocalName.substring(6);
/* 228:    */           } else {
/* 229:299 */             prefix = "";
/* 230:    */           }
/* 231:302 */           SAXEvent prefixEvent = new SAXEvent((byte)2);
/* 232:    */           
/* 233:304 */           prefixEvent.addParm(prefix);
/* 234:305 */           prefixEvent.addParm(attributes.getValue(i));
/* 235:306 */           this.events.add(prefixEvent);
/* 236:    */           
/* 237:    */ 
/* 238:    */ 
/* 239:310 */           List prefixes = (List)this.prefixMappings.get(qName);
/* 240:311 */           if (prefixes == null)
/* 241:    */           {
/* 242:312 */             prefixes = new ArrayList();
/* 243:313 */             this.prefixMappings.put(qName, prefixes);
/* 244:    */           }
/* 245:315 */           prefixes.add(prefix);
/* 246:    */         }
/* 247:    */         else
/* 248:    */         {
/* 249:319 */           attParms = new String[5];
/* 250:320 */           attParms[0] = attributes.getURI(i);
/* 251:321 */           attParms[1] = attLocalName;
/* 252:322 */           attParms[2] = attributes.getQName(i);
/* 253:323 */           attParms[3] = attributes.getType(i);
/* 254:324 */           attParms[4] = attributes.getValue(i);
/* 255:325 */           attParmList.add(attParms);
/* 256:    */         }
/* 257:    */       }
/* 258:331 */       saxEvent.addParm(attParmList);
/* 259:    */     }
/* 260:334 */     this.events.add(saxEvent);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void endElement(String namespaceURI, String localName, String qName)
/* 264:    */     throws SAXException
/* 265:    */   {
/* 266:340 */     SAXEvent saxEvent = new SAXEvent((byte)7);
/* 267:341 */     saxEvent.addParm(namespaceURI);
/* 268:342 */     saxEvent.addParm(localName);
/* 269:343 */     saxEvent.addParm(qName);
/* 270:344 */     this.events.add(saxEvent);
/* 271:    */     
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:349 */     QName elementName = null;
/* 276:350 */     if (namespaceURI != null) {
/* 277:351 */       elementName = new QName(localName, Namespace.get(namespaceURI));
/* 278:    */     } else {
/* 279:353 */       elementName = new QName(localName);
/* 280:    */     }
/* 281:356 */     List prefixes = (List)this.prefixMappings.get(elementName);
/* 282:357 */     if (prefixes != null)
/* 283:    */     {
/* 284:358 */       Iterator itr = prefixes.iterator();
/* 285:359 */       while (itr.hasNext())
/* 286:    */       {
/* 287:360 */         SAXEvent prefixEvent = new SAXEvent((byte)3);
/* 288:    */         
/* 289:362 */         prefixEvent.addParm(itr.next());
/* 290:363 */         this.events.add(prefixEvent);
/* 291:    */       }
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void characters(char[] ch, int start, int end)
/* 296:    */     throws SAXException
/* 297:    */   {
/* 298:370 */     SAXEvent saxEvent = new SAXEvent((byte)8);
/* 299:371 */     saxEvent.addParm(ch);
/* 300:372 */     saxEvent.addParm(new Integer(start));
/* 301:373 */     saxEvent.addParm(new Integer(end));
/* 302:374 */     this.events.add(saxEvent);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void startDTD(String name, String publicId, String systemId)
/* 306:    */     throws SAXException
/* 307:    */   {
/* 308:381 */     SAXEvent saxEvent = new SAXEvent((byte)9);
/* 309:382 */     saxEvent.addParm(name);
/* 310:383 */     saxEvent.addParm(publicId);
/* 311:384 */     saxEvent.addParm(systemId);
/* 312:385 */     this.events.add(saxEvent);
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void endDTD()
/* 316:    */     throws SAXException
/* 317:    */   {
/* 318:389 */     SAXEvent saxEvent = new SAXEvent((byte)10);
/* 319:390 */     this.events.add(saxEvent);
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void startEntity(String name)
/* 323:    */     throws SAXException
/* 324:    */   {
/* 325:394 */     SAXEvent saxEvent = new SAXEvent((byte)11);
/* 326:395 */     saxEvent.addParm(name);
/* 327:396 */     this.events.add(saxEvent);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void endEntity(String name)
/* 331:    */     throws SAXException
/* 332:    */   {
/* 333:400 */     SAXEvent saxEvent = new SAXEvent((byte)12);
/* 334:401 */     saxEvent.addParm(name);
/* 335:402 */     this.events.add(saxEvent);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void startCDATA()
/* 339:    */     throws SAXException
/* 340:    */   {
/* 341:406 */     SAXEvent saxEvent = new SAXEvent((byte)13);
/* 342:407 */     this.events.add(saxEvent);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void endCDATA()
/* 346:    */     throws SAXException
/* 347:    */   {
/* 348:411 */     SAXEvent saxEvent = new SAXEvent((byte)14);
/* 349:412 */     this.events.add(saxEvent);
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void comment(char[] ch, int start, int end)
/* 353:    */     throws SAXException
/* 354:    */   {
/* 355:416 */     SAXEvent saxEvent = new SAXEvent((byte)15);
/* 356:417 */     saxEvent.addParm(ch);
/* 357:418 */     saxEvent.addParm(new Integer(start));
/* 358:419 */     saxEvent.addParm(new Integer(end));
/* 359:420 */     this.events.add(saxEvent);
/* 360:    */   }
/* 361:    */   
/* 362:    */   public void elementDecl(String name, String model)
/* 363:    */     throws SAXException
/* 364:    */   {
/* 365:426 */     SAXEvent saxEvent = new SAXEvent((byte)16);
/* 366:427 */     saxEvent.addParm(name);
/* 367:428 */     saxEvent.addParm(model);
/* 368:429 */     this.events.add(saxEvent);
/* 369:    */   }
/* 370:    */   
/* 371:    */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value)
/* 372:    */     throws SAXException
/* 373:    */   {
/* 374:434 */     SAXEvent saxEvent = new SAXEvent((byte)17);
/* 375:435 */     saxEvent.addParm(eName);
/* 376:436 */     saxEvent.addParm(aName);
/* 377:437 */     saxEvent.addParm(type);
/* 378:438 */     saxEvent.addParm(valueDefault);
/* 379:439 */     saxEvent.addParm(value);
/* 380:440 */     this.events.add(saxEvent);
/* 381:    */   }
/* 382:    */   
/* 383:    */   public void internalEntityDecl(String name, String value)
/* 384:    */     throws SAXException
/* 385:    */   {
/* 386:445 */     SAXEvent saxEvent = new SAXEvent((byte)18);
/* 387:446 */     saxEvent.addParm(name);
/* 388:447 */     saxEvent.addParm(value);
/* 389:448 */     this.events.add(saxEvent);
/* 390:    */   }
/* 391:    */   
/* 392:    */   public void externalEntityDecl(String name, String publicId, String sysId)
/* 393:    */     throws SAXException
/* 394:    */   {
/* 395:453 */     SAXEvent saxEvent = new SAXEvent((byte)19);
/* 396:454 */     saxEvent.addParm(name);
/* 397:455 */     saxEvent.addParm(publicId);
/* 398:456 */     saxEvent.addParm(sysId);
/* 399:457 */     this.events.add(saxEvent);
/* 400:    */   }
/* 401:    */   
/* 402:    */   public void writeExternal(ObjectOutput out)
/* 403:    */     throws IOException
/* 404:    */   {
/* 405:461 */     if (this.events == null)
/* 406:    */     {
/* 407:462 */       out.writeByte(2);
/* 408:    */     }
/* 409:    */     else
/* 410:    */     {
/* 411:464 */       out.writeByte(1);
/* 412:465 */       out.writeObject(this.events);
/* 413:    */     }
/* 414:    */   }
/* 415:    */   
/* 416:    */   public void readExternal(ObjectInput in)
/* 417:    */     throws ClassNotFoundException, IOException
/* 418:    */   {
/* 419:471 */     if (in.readByte() != 2) {
/* 420:472 */       this.events = ((List)in.readObject());
/* 421:    */     }
/* 422:    */   }
/* 423:    */   
/* 424:    */   static class SAXEvent
/* 425:    */     implements Externalizable
/* 426:    */   {
/* 427:    */     public static final long serialVersionUID = 1L;
/* 428:    */     static final byte PROCESSING_INSTRUCTION = 1;
/* 429:    */     static final byte START_PREFIX_MAPPING = 2;
/* 430:    */     static final byte END_PREFIX_MAPPING = 3;
/* 431:    */     static final byte START_DOCUMENT = 4;
/* 432:    */     static final byte END_DOCUMENT = 5;
/* 433:    */     static final byte START_ELEMENT = 6;
/* 434:    */     static final byte END_ELEMENT = 7;
/* 435:    */     static final byte CHARACTERS = 8;
/* 436:    */     static final byte START_DTD = 9;
/* 437:    */     static final byte END_DTD = 10;
/* 438:    */     static final byte START_ENTITY = 11;
/* 439:    */     static final byte END_ENTITY = 12;
/* 440:    */     static final byte START_CDATA = 13;
/* 441:    */     static final byte END_CDATA = 14;
/* 442:    */     static final byte COMMENT = 15;
/* 443:    */     static final byte ELEMENT_DECL = 16;
/* 444:    */     static final byte ATTRIBUTE_DECL = 17;
/* 445:    */     static final byte INTERNAL_ENTITY_DECL = 18;
/* 446:    */     static final byte EXTERNAL_ENTITY_DECL = 19;
/* 447:    */     protected byte event;
/* 448:    */     protected List parms;
/* 449:    */     
/* 450:    */     public SAXEvent() {}
/* 451:    */     
/* 452:    */     SAXEvent(byte event)
/* 453:    */     {
/* 454:527 */       this.event = event;
/* 455:    */     }
/* 456:    */     
/* 457:    */     void addParm(Object parm)
/* 458:    */     {
/* 459:531 */       if (this.parms == null) {
/* 460:532 */         this.parms = new ArrayList(3);
/* 461:    */       }
/* 462:535 */       this.parms.add(parm);
/* 463:    */     }
/* 464:    */     
/* 465:    */     Object getParm(int index)
/* 466:    */     {
/* 467:539 */       if ((this.parms != null) && (index < this.parms.size())) {
/* 468:540 */         return this.parms.get(index);
/* 469:    */       }
/* 470:542 */       return null;
/* 471:    */     }
/* 472:    */     
/* 473:    */     public void writeExternal(ObjectOutput out)
/* 474:    */       throws IOException
/* 475:    */     {
/* 476:547 */       out.writeByte(this.event);
/* 477:549 */       if (this.parms == null)
/* 478:    */       {
/* 479:550 */         out.writeByte(2);
/* 480:    */       }
/* 481:    */       else
/* 482:    */       {
/* 483:552 */         out.writeByte(1);
/* 484:553 */         out.writeObject(this.parms);
/* 485:    */       }
/* 486:    */     }
/* 487:    */     
/* 488:    */     public void readExternal(ObjectInput in)
/* 489:    */       throws ClassNotFoundException, IOException
/* 490:    */     {
/* 491:559 */       this.event = in.readByte();
/* 492:561 */       if (in.readByte() != 2) {
/* 493:562 */         this.parms = ((List)in.readObject());
/* 494:    */       }
/* 495:    */     }
/* 496:    */   }
/* 497:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.io.SAXEventRecorder
 * JD-Core Version:    0.7.0.1
 */