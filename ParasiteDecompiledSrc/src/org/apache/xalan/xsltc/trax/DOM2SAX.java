/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Hashtable;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Stack;
/*   8:    */ import java.util.Vector;
/*   9:    */ import org.apache.xalan.xsltc.dom.SAXImpl;
/*  10:    */ import org.w3c.dom.NamedNodeMap;
/*  11:    */ import org.w3c.dom.Node;
/*  12:    */ import org.xml.sax.ContentHandler;
/*  13:    */ import org.xml.sax.DTDHandler;
/*  14:    */ import org.xml.sax.EntityResolver;
/*  15:    */ import org.xml.sax.ErrorHandler;
/*  16:    */ import org.xml.sax.InputSource;
/*  17:    */ import org.xml.sax.Locator;
/*  18:    */ import org.xml.sax.SAXException;
/*  19:    */ import org.xml.sax.SAXNotRecognizedException;
/*  20:    */ import org.xml.sax.SAXNotSupportedException;
/*  21:    */ import org.xml.sax.XMLReader;
/*  22:    */ import org.xml.sax.ext.LexicalHandler;
/*  23:    */ import org.xml.sax.helpers.AttributesImpl;
/*  24:    */ 
/*  25:    */ public class DOM2SAX
/*  26:    */   implements XMLReader, Locator
/*  27:    */ {
/*  28:    */   private static final String EMPTYSTRING = "";
/*  29:    */   private static final String XMLNS_PREFIX = "xmlns";
/*  30: 55 */   private Node _dom = null;
/*  31: 56 */   private ContentHandler _sax = null;
/*  32: 57 */   private LexicalHandler _lex = null;
/*  33: 58 */   private SAXImpl _saxImpl = null;
/*  34: 59 */   private Hashtable _nsPrefixes = new Hashtable();
/*  35:    */   
/*  36:    */   public DOM2SAX(Node root)
/*  37:    */   {
/*  38: 62 */     this._dom = root;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ContentHandler getContentHandler()
/*  42:    */   {
/*  43: 66 */     return this._sax;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setContentHandler(ContentHandler handler)
/*  47:    */     throws NullPointerException
/*  48:    */   {
/*  49: 72 */     this._sax = handler;
/*  50: 73 */     if ((handler instanceof LexicalHandler)) {
/*  51: 74 */       this._lex = ((LexicalHandler)handler);
/*  52:    */     }
/*  53: 77 */     if ((handler instanceof SAXImpl)) {
/*  54: 78 */       this._saxImpl = ((SAXImpl)handler);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   private boolean startPrefixMapping(String prefix, String uri)
/*  59:    */     throws SAXException
/*  60:    */   {
/*  61: 90 */     boolean pushed = true;
/*  62: 91 */     Stack uriStack = (Stack)this._nsPrefixes.get(prefix);
/*  63: 93 */     if (uriStack != null)
/*  64:    */     {
/*  65: 94 */       if (uriStack.isEmpty())
/*  66:    */       {
/*  67: 95 */         this._sax.startPrefixMapping(prefix, uri);
/*  68: 96 */         uriStack.push(uri);
/*  69:    */       }
/*  70:    */       else
/*  71:    */       {
/*  72: 99 */         String lastUri = (String)uriStack.peek();
/*  73:100 */         if (!lastUri.equals(uri))
/*  74:    */         {
/*  75:101 */           this._sax.startPrefixMapping(prefix, uri);
/*  76:102 */           uriStack.push(uri);
/*  77:    */         }
/*  78:    */         else
/*  79:    */         {
/*  80:105 */           pushed = false;
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:110 */       this._sax.startPrefixMapping(prefix, uri);
/*  87:111 */       this._nsPrefixes.put(prefix, uriStack = new Stack());
/*  88:112 */       uriStack.push(uri);
/*  89:    */     }
/*  90:114 */     return pushed;
/*  91:    */   }
/*  92:    */   
/*  93:    */   private void endPrefixMapping(String prefix)
/*  94:    */     throws SAXException
/*  95:    */   {
/*  96:124 */     Stack uriStack = (Stack)this._nsPrefixes.get(prefix);
/*  97:126 */     if (uriStack != null)
/*  98:    */     {
/*  99:127 */       this._sax.endPrefixMapping(prefix);
/* 100:128 */       uriStack.pop();
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private static String getLocalName(Node node)
/* 105:    */   {
/* 106:138 */     String localName = node.getLocalName();
/* 107:140 */     if (localName == null)
/* 108:    */     {
/* 109:141 */       String qname = node.getNodeName();
/* 110:142 */       int col = qname.lastIndexOf(':');
/* 111:143 */       return col > 0 ? qname.substring(col + 1) : qname;
/* 112:    */     }
/* 113:145 */     return localName;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void parse(InputSource unused)
/* 117:    */     throws IOException, SAXException
/* 118:    */   {
/* 119:149 */     parse(this._dom);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void parse()
/* 123:    */     throws IOException, SAXException
/* 124:    */   {
/* 125:153 */     if (this._dom != null)
/* 126:    */     {
/* 127:154 */       boolean isIncomplete = this._dom.getNodeType() != 9;
/* 128:157 */       if (isIncomplete)
/* 129:    */       {
/* 130:158 */         this._sax.startDocument();
/* 131:159 */         parse(this._dom);
/* 132:160 */         this._sax.endDocument();
/* 133:    */       }
/* 134:    */       else
/* 135:    */       {
/* 136:163 */         parse(this._dom);
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private void parse(Node node)
/* 142:    */     throws IOException, SAXException
/* 143:    */   {
/* 144:174 */     Node first = null;
/* 145:175 */     if (node == null) {
/* 146:    */       return;
/* 147:    */     }
/* 148:    */     Node next;
/* 149:177 */     switch (node.getNodeType())
/* 150:    */     {
/* 151:    */     case 2: 
/* 152:    */     case 5: 
/* 153:    */     case 6: 
/* 154:    */     case 10: 
/* 155:    */     case 11: 
/* 156:    */     case 12: 
/* 157:    */       break;
/* 158:    */     case 4: 
/* 159:187 */       String cdata = node.getNodeValue();
/* 160:188 */       if (this._lex != null)
/* 161:    */       {
/* 162:189 */         this._lex.startCDATA();
/* 163:190 */         this._sax.characters(cdata.toCharArray(), 0, cdata.length());
/* 164:191 */         this._lex.endCDATA();
/* 165:    */       }
/* 166:    */       else
/* 167:    */       {
/* 168:196 */         this._sax.characters(cdata.toCharArray(), 0, cdata.length());
/* 169:    */       }
/* 170:198 */       break;
/* 171:    */     case 8: 
/* 172:201 */       if (this._lex != null)
/* 173:    */       {
/* 174:202 */         String value = node.getNodeValue();
/* 175:203 */         this._lex.comment(value.toCharArray(), 0, value.length());
/* 176:    */       }
/* 177:    */       break;
/* 178:    */     case 9: 
/* 179:207 */       this._sax.setDocumentLocator(this);
/* 180:    */       
/* 181:209 */       this._sax.startDocument();
/* 182:210 */       next = node.getFirstChild();
/* 183:211 */       while (next != null)
/* 184:    */       {
/* 185:212 */         parse(next);
/* 186:213 */         next = next.getNextSibling();
/* 187:    */       }
/* 188:215 */       this._sax.endDocument();
/* 189:216 */       break;
/* 190:    */     case 1: 
/* 191:220 */       List pushedPrefixes = new ArrayList();
/* 192:221 */       AttributesImpl attrs = new AttributesImpl();
/* 193:222 */       NamedNodeMap map = node.getAttributes();
/* 194:223 */       int length = map.getLength();
/* 195:    */       String prefix;
/* 196:226 */       for (int i = 0; i < length; i++)
/* 197:    */       {
/* 198:227 */         Node attr = map.item(i);
/* 199:228 */         String qnameAttr = attr.getNodeName();
/* 200:231 */         if (qnameAttr.startsWith("xmlns"))
/* 201:    */         {
/* 202:232 */           String uriAttr = attr.getNodeValue();
/* 203:233 */           int colon = qnameAttr.lastIndexOf(':');
/* 204:234 */           prefix = colon > 0 ? qnameAttr.substring(colon + 1) : "";
/* 205:235 */           if (startPrefixMapping(prefix, uriAttr)) {
/* 206:236 */             pushedPrefixes.add(prefix);
/* 207:    */           }
/* 208:    */         }
/* 209:    */       }
/* 210:242 */       for (int i = 0; i < length; i++)
/* 211:    */       {
/* 212:243 */         Node attr = map.item(i);
/* 213:244 */         String qnameAttr = attr.getNodeName();
/* 214:247 */         if (!qnameAttr.startsWith("xmlns"))
/* 215:    */         {
/* 216:248 */           String uriAttr = attr.getNamespaceURI();
/* 217:249 */           String localNameAttr = getLocalName(attr);
/* 218:252 */           if (uriAttr != null)
/* 219:    */           {
/* 220:253 */             int colon = qnameAttr.lastIndexOf(':');
/* 221:254 */             prefix = colon > 0 ? qnameAttr.substring(0, colon) : "";
/* 222:255 */             if (startPrefixMapping(prefix, uriAttr)) {
/* 223:256 */               pushedPrefixes.add(prefix);
/* 224:    */             }
/* 225:    */           }
/* 226:261 */           attrs.addAttribute(attr.getNamespaceURI(), getLocalName(attr), qnameAttr, "CDATA", attr.getNodeValue());
/* 227:    */         }
/* 228:    */       }
/* 229:267 */       String qname = node.getNodeName();
/* 230:268 */       String uri = node.getNamespaceURI();
/* 231:269 */       String localName = getLocalName(node);
/* 232:272 */       if (uri != null)
/* 233:    */       {
/* 234:273 */         int colon = qname.lastIndexOf(':');
/* 235:274 */         prefix = colon > 0 ? qname.substring(0, colon) : "";
/* 236:275 */         if (startPrefixMapping(prefix, uri)) {
/* 237:276 */           pushedPrefixes.add(prefix);
/* 238:    */         }
/* 239:    */       }
/* 240:281 */       if (this._saxImpl != null) {
/* 241:282 */         this._saxImpl.startElement(uri, localName, qname, attrs, node);
/* 242:    */       } else {
/* 243:285 */         this._sax.startElement(uri, localName, qname, attrs);
/* 244:    */       }
/* 245:289 */       next = node.getFirstChild();
/* 246:290 */       while (next != null)
/* 247:    */       {
/* 248:291 */         parse(next);
/* 249:292 */         next = next.getNextSibling();
/* 250:    */       }
/* 251:296 */       this._sax.endElement(uri, localName, qname);
/* 252:    */       
/* 253:    */ 
/* 254:299 */       int nPushedPrefixes = pushedPrefixes.size();
/* 255:300 */       for (int i = 0; i < nPushedPrefixes; i++) {
/* 256:301 */         endPrefixMapping((String)pushedPrefixes.get(i));
/* 257:    */       }
/* 258:303 */       break;
/* 259:    */     case 7: 
/* 260:306 */       this._sax.processingInstruction(node.getNodeName(), node.getNodeValue());
/* 261:    */       
/* 262:308 */       break;
/* 263:    */     case 3: 
/* 264:311 */       String data = node.getNodeValue();
/* 265:312 */       this._sax.characters(data.toCharArray(), 0, data.length());
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public DTDHandler getDTDHandler()
/* 270:    */   {
/* 271:322 */     return null;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public ErrorHandler getErrorHandler()
/* 275:    */   {
/* 276:330 */     return null;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public boolean getFeature(String name)
/* 280:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 281:    */   {
/* 282:340 */     return false;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setFeature(String name, boolean value)
/* 286:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 287:    */   {}
/* 288:    */   
/* 289:    */   public void parse(String sysId)
/* 290:    */     throws IOException, SAXException
/* 291:    */   {
/* 292:357 */     throw new IOException("This method is not yet implemented.");
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setDTDHandler(DTDHandler handler)
/* 296:    */     throws NullPointerException
/* 297:    */   {}
/* 298:    */   
/* 299:    */   public void setEntityResolver(EntityResolver resolver)
/* 300:    */     throws NullPointerException
/* 301:    */   {}
/* 302:    */   
/* 303:    */   public EntityResolver getEntityResolver()
/* 304:    */   {
/* 305:381 */     return null;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setErrorHandler(ErrorHandler handler)
/* 309:    */     throws NullPointerException
/* 310:    */   {}
/* 311:    */   
/* 312:    */   public void setProperty(String name, Object value)
/* 313:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 314:    */   {}
/* 315:    */   
/* 316:    */   public Object getProperty(String name)
/* 317:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 318:    */   {
/* 319:408 */     return null;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public int getColumnNumber()
/* 323:    */   {
/* 324:416 */     return 0;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public int getLineNumber()
/* 328:    */   {
/* 329:424 */     return 0;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public String getPublicId()
/* 333:    */   {
/* 334:432 */     return null;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public String getSystemId()
/* 338:    */   {
/* 339:440 */     return null;
/* 340:    */   }
/* 341:    */   
/* 342:    */   private String getNodeTypeFromCode(short code)
/* 343:    */   {
/* 344:445 */     String retval = null;
/* 345:446 */     switch (code)
/* 346:    */     {
/* 347:    */     case 2: 
/* 348:448 */       retval = "ATTRIBUTE_NODE"; break;
/* 349:    */     case 4: 
/* 350:450 */       retval = "CDATA_SECTION_NODE"; break;
/* 351:    */     case 8: 
/* 352:452 */       retval = "COMMENT_NODE"; break;
/* 353:    */     case 11: 
/* 354:454 */       retval = "DOCUMENT_FRAGMENT_NODE"; break;
/* 355:    */     case 9: 
/* 356:456 */       retval = "DOCUMENT_NODE"; break;
/* 357:    */     case 10: 
/* 358:458 */       retval = "DOCUMENT_TYPE_NODE"; break;
/* 359:    */     case 1: 
/* 360:460 */       retval = "ELEMENT_NODE"; break;
/* 361:    */     case 6: 
/* 362:462 */       retval = "ENTITY_NODE"; break;
/* 363:    */     case 5: 
/* 364:464 */       retval = "ENTITY_REFERENCE_NODE"; break;
/* 365:    */     case 12: 
/* 366:466 */       retval = "NOTATION_NODE"; break;
/* 367:    */     case 7: 
/* 368:468 */       retval = "PROCESSING_INSTRUCTION_NODE"; break;
/* 369:    */     case 3: 
/* 370:470 */       retval = "TEXT_NODE";
/* 371:    */     }
/* 372:472 */     return retval;
/* 373:    */   }
/* 374:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.DOM2SAX
 * JD-Core Version:    0.7.0.1
 */