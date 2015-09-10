/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.io.Writer;
/*   6:    */ import javax.xml.transform.ErrorListener;
/*   7:    */ import javax.xml.transform.Transformer;
/*   8:    */ import javax.xml.transform.TransformerException;
/*   9:    */ import org.apache.xml.serializer.utils.Messages;
/*  10:    */ import org.apache.xml.serializer.utils.Utils;
/*  11:    */ import org.xml.sax.SAXException;
/*  12:    */ 
/*  13:    */ public class ToXMLStream
/*  14:    */   extends ToStream
/*  15:    */ {
/*  16: 50 */   private CharInfo m_xmlcharInfo = CharInfo.getCharInfo(CharInfo.XML_ENTITIES_RESOURCE, "xml");
/*  17:    */   
/*  18:    */   public ToXMLStream()
/*  19:    */   {
/*  20: 58 */     this.m_charInfo = this.m_xmlcharInfo;
/*  21:    */     
/*  22: 60 */     initCDATA();
/*  23:    */     
/*  24: 62 */     this.m_prefixMap = new NamespaceMappings();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void CopyFrom(ToXMLStream xmlListener)
/*  28:    */   {
/*  29: 74 */     setWriter(xmlListener.m_writer);
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33: 78 */     String encoding = xmlListener.getEncoding();
/*  34: 79 */     setEncoding(encoding);
/*  35:    */     
/*  36: 81 */     setOmitXMLDeclaration(xmlListener.getOmitXMLDeclaration());
/*  37:    */     
/*  38: 83 */     this.m_ispreserve = xmlListener.m_ispreserve;
/*  39: 84 */     this.m_preserves = xmlListener.m_preserves;
/*  40: 85 */     this.m_isprevtext = xmlListener.m_isprevtext;
/*  41: 86 */     this.m_doIndent = xmlListener.m_doIndent;
/*  42: 87 */     setIndentAmount(xmlListener.getIndentAmount());
/*  43: 88 */     this.m_startNewLine = xmlListener.m_startNewLine;
/*  44: 89 */     this.m_needToOutputDocTypeDecl = xmlListener.m_needToOutputDocTypeDecl;
/*  45: 90 */     setDoctypeSystem(xmlListener.getDoctypeSystem());
/*  46: 91 */     setDoctypePublic(xmlListener.getDoctypePublic());
/*  47: 92 */     setStandalone(xmlListener.getStandalone());
/*  48: 93 */     setMediaType(xmlListener.getMediaType());
/*  49: 94 */     this.m_encodingInfo = xmlListener.m_encodingInfo;
/*  50: 95 */     this.m_spaceBeforeClose = xmlListener.m_spaceBeforeClose;
/*  51: 96 */     this.m_cdataStartCalled = xmlListener.m_cdataStartCalled;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void startDocumentInternal()
/*  55:    */     throws SAXException
/*  56:    */   {
/*  57:111 */     if (this.m_needToCallStartDocument)
/*  58:    */     {
/*  59:113 */       super.startDocumentInternal();
/*  60:114 */       this.m_needToCallStartDocument = false;
/*  61:116 */       if (this.m_inEntityRef) {
/*  62:117 */         return;
/*  63:    */       }
/*  64:119 */       this.m_needToOutputDocTypeDecl = true;
/*  65:120 */       this.m_startNewLine = false;
/*  66:    */       
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:125 */       String version = getXMLVersion();
/*  71:126 */       if (!getOmitXMLDeclaration())
/*  72:    */       {
/*  73:128 */         String encoding = Encodings.getMimeEncoding(getEncoding());
/*  74:    */         String standalone;
/*  75:131 */         if (this.m_standaloneWasSpecified) {
/*  76:133 */           standalone = " standalone=\"" + getStandalone() + "\"";
/*  77:    */         } else {
/*  78:137 */           standalone = "";
/*  79:    */         }
/*  80:    */         try
/*  81:    */         {
/*  82:142 */           Writer writer = this.m_writer;
/*  83:143 */           writer.write("<?xml version=\"");
/*  84:144 */           writer.write(version);
/*  85:145 */           writer.write("\" encoding=\"");
/*  86:146 */           writer.write(encoding);
/*  87:147 */           writer.write(34);
/*  88:148 */           writer.write(standalone);
/*  89:149 */           writer.write("?>");
/*  90:150 */           if ((this.m_doIndent) && (
/*  91:151 */             (this.m_standaloneWasSpecified) || (getDoctypePublic() != null) || (getDoctypeSystem() != null))) {
/*  92:163 */             writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  93:    */           }
/*  94:    */         }
/*  95:    */         catch (IOException e)
/*  96:    */         {
/*  97:169 */           throw new SAXException(e);
/*  98:    */         }
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void endDocument()
/* 104:    */     throws SAXException
/* 105:    */   {
/* 106:186 */     flushPending();
/* 107:187 */     if ((this.m_doIndent) && (!this.m_isprevtext)) {
/* 108:    */       try
/* 109:    */       {
/* 110:191 */         outputLineSep();
/* 111:    */       }
/* 112:    */       catch (IOException e)
/* 113:    */       {
/* 114:195 */         throw new SAXException(e);
/* 115:    */       }
/* 116:    */     }
/* 117:199 */     flushWriter();
/* 118:201 */     if (this.m_tracer != null) {
/* 119:202 */       super.fireEndDoc();
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void startPreserving()
/* 124:    */     throws SAXException
/* 125:    */   {
/* 126:221 */     this.m_preserves.push(true);
/* 127:    */     
/* 128:223 */     this.m_ispreserve = true;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void endPreserving()
/* 132:    */     throws SAXException
/* 133:    */   {
/* 134:237 */     this.m_ispreserve = (this.m_preserves.isEmpty() ? false : this.m_preserves.pop());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void processingInstruction(String target, String data)
/* 138:    */     throws SAXException
/* 139:    */   {
/* 140:254 */     if (this.m_inEntityRef) {
/* 141:255 */       return;
/* 142:    */     }
/* 143:257 */     flushPending();
/* 144:259 */     if (target.equals("javax.xml.transform.disable-output-escaping")) {
/* 145:261 */       startNonEscaping();
/* 146:263 */     } else if (target.equals("javax.xml.transform.enable-output-escaping")) {
/* 147:265 */       endNonEscaping();
/* 148:    */     } else {
/* 149:    */       try
/* 150:    */       {
/* 151:271 */         if (this.m_elemContext.m_startTagOpen)
/* 152:    */         {
/* 153:273 */           closeStartTag();
/* 154:274 */           this.m_elemContext.m_startTagOpen = false;
/* 155:    */         }
/* 156:276 */         else if (this.m_needToCallStartDocument)
/* 157:    */         {
/* 158:277 */           startDocumentInternal();
/* 159:    */         }
/* 160:279 */         if (shouldIndent()) {
/* 161:280 */           indent();
/* 162:    */         }
/* 163:282 */         Writer writer = this.m_writer;
/* 164:283 */         writer.write("<?");
/* 165:284 */         writer.write(target);
/* 166:286 */         if ((data.length() > 0) && (!Character.isSpaceChar(data.charAt(0)))) {
/* 167:288 */           writer.write(32);
/* 168:    */         }
/* 169:290 */         int indexOfQLT = data.indexOf("?>");
/* 170:292 */         if (indexOfQLT >= 0)
/* 171:    */         {
/* 172:296 */           if (indexOfQLT > 0) {
/* 173:298 */             writer.write(data.substring(0, indexOfQLT));
/* 174:    */           }
/* 175:301 */           writer.write("? >");
/* 176:303 */           if (indexOfQLT + 2 < data.length()) {
/* 177:305 */             writer.write(data.substring(indexOfQLT + 2));
/* 178:    */           }
/* 179:    */         }
/* 180:    */         else
/* 181:    */         {
/* 182:310 */           writer.write(data);
/* 183:    */         }
/* 184:313 */         writer.write(63);
/* 185:314 */         writer.write(62);
/* 186:    */         
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:325 */         this.m_startNewLine = true;
/* 197:    */       }
/* 198:    */       catch (IOException e)
/* 199:    */       {
/* 200:329 */         throw new SAXException(e);
/* 201:    */       }
/* 202:    */     }
/* 203:333 */     if (this.m_tracer != null) {
/* 204:334 */       super.fireEscapingEvent(target, data);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void entityReference(String name)
/* 209:    */     throws SAXException
/* 210:    */   {
/* 211:346 */     if (this.m_elemContext.m_startTagOpen)
/* 212:    */     {
/* 213:348 */       closeStartTag();
/* 214:349 */       this.m_elemContext.m_startTagOpen = false;
/* 215:    */     }
/* 216:    */     try
/* 217:    */     {
/* 218:354 */       if (shouldIndent()) {
/* 219:355 */         indent();
/* 220:    */       }
/* 221:357 */       Writer writer = this.m_writer;
/* 222:358 */       writer.write(38);
/* 223:359 */       writer.write(name);
/* 224:360 */       writer.write(59);
/* 225:    */     }
/* 226:    */     catch (IOException e)
/* 227:    */     {
/* 228:364 */       throw new SAXException(e);
/* 229:    */     }
/* 230:367 */     if (this.m_tracer != null) {
/* 231:368 */       super.fireEntityReference(name);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void addUniqueAttribute(String name, String value, int flags)
/* 236:    */     throws SAXException
/* 237:    */   {
/* 238:384 */     if (this.m_elemContext.m_startTagOpen) {
/* 239:    */       try
/* 240:    */       {
/* 241:389 */         String patchedName = patchName(name);
/* 242:390 */         Writer writer = this.m_writer;
/* 243:391 */         if (((flags & 0x1) > 0) && (this.m_xmlcharInfo.onlyQuotAmpLtGt))
/* 244:    */         {
/* 245:399 */           writer.write(32);
/* 246:400 */           writer.write(patchedName);
/* 247:401 */           writer.write("=\"");
/* 248:402 */           writer.write(value);
/* 249:403 */           writer.write(34);
/* 250:    */         }
/* 251:    */         else
/* 252:    */         {
/* 253:407 */           writer.write(32);
/* 254:408 */           writer.write(patchedName);
/* 255:409 */           writer.write("=\"");
/* 256:410 */           writeAttrString(writer, value, getEncoding());
/* 257:411 */           writer.write(34);
/* 258:    */         }
/* 259:    */       }
/* 260:    */       catch (IOException e)
/* 261:    */       {
/* 262:414 */         throw new SAXException(e);
/* 263:    */       }
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void addAttribute(String uri, String localName, String rawName, String type, String value, boolean xslAttribute)
/* 268:    */     throws SAXException
/* 269:    */   {
/* 270:439 */     if (this.m_elemContext.m_startTagOpen)
/* 271:    */     {
/* 272:441 */       boolean was_added = addAttributeAlways(uri, localName, rawName, type, value, xslAttribute);
/* 273:451 */       if ((was_added) && (!xslAttribute) && (!rawName.startsWith("xmlns")))
/* 274:    */       {
/* 275:453 */         String prefixUsed = ensureAttributesNamespaceIsDeclared(uri, localName, rawName);
/* 276:458 */         if ((prefixUsed != null) && (rawName != null) && (!rawName.startsWith(prefixUsed))) {
/* 277:464 */           rawName = prefixUsed + ":" + localName;
/* 278:    */         }
/* 279:    */       }
/* 280:468 */       addAttributeAlways(uri, localName, rawName, type, value, xslAttribute);
/* 281:    */     }
/* 282:    */     else
/* 283:    */     {
/* 284:486 */       String msg = Utils.messages.createMessage("ER_ILLEGAL_ATTRIBUTE_POSITION", new Object[] { localName });
/* 285:    */       try
/* 286:    */       {
/* 287:491 */         Transformer tran = super.getTransformer();
/* 288:492 */         ErrorListener errHandler = tran.getErrorListener();
/* 289:496 */         if ((null != errHandler) && (this.m_sourceLocator != null)) {
/* 290:497 */           errHandler.warning(new TransformerException(msg, this.m_sourceLocator));
/* 291:    */         } else {
/* 292:499 */           System.out.println(msg);
/* 293:    */         }
/* 294:    */       }
/* 295:    */       catch (TransformerException e)
/* 296:    */       {
/* 297:507 */         SAXException se = new SAXException(e);
/* 298:508 */         throw se;
/* 299:    */       }
/* 300:    */     }
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void endElement(String elemName)
/* 304:    */     throws SAXException
/* 305:    */   {
/* 306:518 */     endElement(null, null, elemName);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void namespaceAfterStartElement(String prefix, String uri)
/* 310:    */     throws SAXException
/* 311:    */   {
/* 312:535 */     if (this.m_elemContext.m_elementURI == null)
/* 313:    */     {
/* 314:537 */       String prefix1 = SerializerBase.getPrefixPart(this.m_elemContext.m_elementName);
/* 315:538 */       if ((prefix1 == null) && ("".equals(prefix))) {
/* 316:544 */         this.m_elemContext.m_elementURI = uri;
/* 317:    */       }
/* 318:    */     }
/* 319:547 */     startPrefixMapping(prefix, uri, false);
/* 320:    */   }
/* 321:    */   
/* 322:    */   protected boolean pushNamespace(String prefix, String uri)
/* 323:    */   {
/* 324:    */     try
/* 325:    */     {
/* 326:561 */       if (this.m_prefixMap.pushNamespace(prefix, uri, this.m_elemContext.m_currentElemDepth))
/* 327:    */       {
/* 328:564 */         startPrefixMapping(prefix, uri);
/* 329:565 */         return true;
/* 330:    */       }
/* 331:    */     }
/* 332:    */     catch (SAXException e) {}
/* 333:572 */     return false;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public boolean reset()
/* 337:    */   {
/* 338:583 */     boolean wasReset = false;
/* 339:584 */     if (super.reset()) {
/* 340:589 */       wasReset = true;
/* 341:    */     }
/* 342:591 */     return wasReset;
/* 343:    */   }
/* 344:    */   
/* 345:    */   private void resetToXMLStream() {}
/* 346:    */   
/* 347:    */   private String getXMLVersion()
/* 348:    */   {
/* 349:617 */     String xmlVersion = getVersion();
/* 350:618 */     if ((xmlVersion == null) || (xmlVersion.equals("1.0")))
/* 351:    */     {
/* 352:620 */       xmlVersion = "1.0";
/* 353:    */     }
/* 354:622 */     else if (xmlVersion.equals("1.1"))
/* 355:    */     {
/* 356:624 */       xmlVersion = "1.1";
/* 357:    */     }
/* 358:    */     else
/* 359:    */     {
/* 360:628 */       String msg = Utils.messages.createMessage("ER_XML_VERSION_NOT_SUPPORTED", new Object[] { xmlVersion });
/* 361:    */       try
/* 362:    */       {
/* 363:633 */         Transformer tran = super.getTransformer();
/* 364:634 */         ErrorListener errHandler = tran.getErrorListener();
/* 365:636 */         if ((null != errHandler) && (this.m_sourceLocator != null)) {
/* 366:637 */           errHandler.warning(new TransformerException(msg, this.m_sourceLocator));
/* 367:    */         } else {
/* 368:639 */           System.out.println(msg);
/* 369:    */         }
/* 370:    */       }
/* 371:    */       catch (Exception e) {}
/* 372:642 */       xmlVersion = "1.0";
/* 373:    */     }
/* 374:644 */     return xmlVersion;
/* 375:    */   }
/* 376:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToXMLStream
 * JD-Core Version:    0.7.0.1
 */