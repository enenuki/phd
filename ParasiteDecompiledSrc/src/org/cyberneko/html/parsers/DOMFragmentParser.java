/*   1:    */ package org.cyberneko.html.parsers;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.Reader;
/*   6:    */ import org.apache.xerces.util.ErrorHandlerWrapper;
/*   7:    */ import org.apache.xerces.util.XMLChar;
/*   8:    */ import org.apache.xerces.xni.Augmentations;
/*   9:    */ import org.apache.xerces.xni.NamespaceContext;
/*  10:    */ import org.apache.xerces.xni.QName;
/*  11:    */ import org.apache.xerces.xni.XMLAttributes;
/*  12:    */ import org.apache.xerces.xni.XMLDocumentHandler;
/*  13:    */ import org.apache.xerces.xni.XMLLocator;
/*  14:    */ import org.apache.xerces.xni.XMLResourceIdentifier;
/*  15:    */ import org.apache.xerces.xni.XMLString;
/*  16:    */ import org.apache.xerces.xni.XNIException;
/*  17:    */ import org.apache.xerces.xni.parser.XMLConfigurationException;
/*  18:    */ import org.apache.xerces.xni.parser.XMLDocumentSource;
/*  19:    */ import org.apache.xerces.xni.parser.XMLErrorHandler;
/*  20:    */ import org.apache.xerces.xni.parser.XMLInputSource;
/*  21:    */ import org.apache.xerces.xni.parser.XMLParseException;
/*  22:    */ import org.apache.xerces.xni.parser.XMLParserConfiguration;
/*  23:    */ import org.cyberneko.html.HTMLConfiguration;
/*  24:    */ import org.w3c.dom.CDATASection;
/*  25:    */ import org.w3c.dom.Comment;
/*  26:    */ import org.w3c.dom.Document;
/*  27:    */ import org.w3c.dom.DocumentFragment;
/*  28:    */ import org.w3c.dom.Element;
/*  29:    */ import org.w3c.dom.EntityReference;
/*  30:    */ import org.w3c.dom.Node;
/*  31:    */ import org.w3c.dom.ProcessingInstruction;
/*  32:    */ import org.w3c.dom.Text;
/*  33:    */ import org.xml.sax.ErrorHandler;
/*  34:    */ import org.xml.sax.InputSource;
/*  35:    */ import org.xml.sax.SAXException;
/*  36:    */ import org.xml.sax.SAXNotRecognizedException;
/*  37:    */ import org.xml.sax.SAXNotSupportedException;
/*  38:    */ import org.xml.sax.SAXParseException;
/*  39:    */ 
/*  40:    */ public class DOMFragmentParser
/*  41:    */   implements XMLDocumentHandler
/*  42:    */ {
/*  43:    */   protected static final String DOCUMENT_FRAGMENT = "http://cyberneko.org/html/features/document-fragment";
/*  44: 82 */   protected static final String[] RECOGNIZED_FEATURES = { "http://cyberneko.org/html/features/document-fragment" };
/*  45:    */   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
/*  46:    */   protected static final String CURRENT_ELEMENT_NODE = "http://apache.org/xml/properties/dom/current-element-node";
/*  47: 97 */   protected static final String[] RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/dom/current-element-node" };
/*  48:    */   protected XMLParserConfiguration fParserConfiguration;
/*  49:    */   protected XMLDocumentSource fDocumentSource;
/*  50:    */   protected DocumentFragment fDocumentFragment;
/*  51:    */   protected Document fDocument;
/*  52:    */   protected Node fCurrentNode;
/*  53:    */   protected boolean fInCDATASection;
/*  54:    */   
/*  55:    */   public DOMFragmentParser()
/*  56:    */   {
/*  57:130 */     this.fParserConfiguration = new HTMLConfiguration();
/*  58:131 */     this.fParserConfiguration.addRecognizedFeatures(RECOGNIZED_FEATURES);
/*  59:132 */     this.fParserConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);
/*  60:133 */     this.fParserConfiguration.setFeature("http://cyberneko.org/html/features/document-fragment", true);
/*  61:134 */     this.fParserConfiguration.setDocumentHandler(this);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void parse(String systemId, DocumentFragment fragment)
/*  65:    */     throws SAXException, IOException
/*  66:    */   {
/*  67:144 */     parse(new InputSource(systemId), fragment);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void parse(InputSource source, DocumentFragment fragment)
/*  71:    */     throws SAXException, IOException
/*  72:    */   {
/*  73:151 */     this.fCurrentNode = (this.fDocumentFragment = fragment);
/*  74:152 */     this.fDocument = this.fDocumentFragment.getOwnerDocument();
/*  75:    */     try
/*  76:    */     {
/*  77:155 */       String pubid = source.getPublicId();
/*  78:156 */       String sysid = source.getSystemId();
/*  79:157 */       String encoding = source.getEncoding();
/*  80:158 */       InputStream stream = source.getByteStream();
/*  81:159 */       Reader reader = source.getCharacterStream();
/*  82:    */       
/*  83:161 */       XMLInputSource inputSource = new XMLInputSource(pubid, sysid, sysid);
/*  84:    */       
/*  85:163 */       inputSource.setEncoding(encoding);
/*  86:164 */       inputSource.setByteStream(stream);
/*  87:165 */       inputSource.setCharacterStream(reader);
/*  88:    */       
/*  89:167 */       this.fParserConfiguration.parse(inputSource);
/*  90:    */     }
/*  91:    */     catch (XMLParseException e)
/*  92:    */     {
/*  93:170 */       Exception ex = e.getException();
/*  94:171 */       if (ex != null) {
/*  95:172 */         throw new SAXParseException(e.getMessage(), null, ex);
/*  96:    */       }
/*  97:174 */       throw new SAXParseException(e.getMessage(), null);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setErrorHandler(ErrorHandler errorHandler)
/* 102:    */   {
/* 103:198 */     this.fParserConfiguration.setErrorHandler(new ErrorHandlerWrapper(errorHandler));
/* 104:    */   }
/* 105:    */   
/* 106:    */   public ErrorHandler getErrorHandler()
/* 107:    */   {
/* 108:210 */     ErrorHandler errorHandler = null;
/* 109:    */     try
/* 110:    */     {
/* 111:212 */       XMLErrorHandler xmlErrorHandler = (XMLErrorHandler)this.fParserConfiguration.getProperty("http://apache.org/xml/properties/internal/error-handler");
/* 112:214 */       if ((xmlErrorHandler != null) && ((xmlErrorHandler instanceof ErrorHandlerWrapper))) {
/* 113:216 */         errorHandler = ((ErrorHandlerWrapper)xmlErrorHandler).getErrorHandler();
/* 114:    */       }
/* 115:    */     }
/* 116:    */     catch (XMLConfigurationException e) {}
/* 117:222 */     return errorHandler;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setFeature(String featureId, boolean state)
/* 121:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:244 */       this.fParserConfiguration.setFeature(featureId, state);
/* 126:    */     }
/* 127:    */     catch (XMLConfigurationException e)
/* 128:    */     {
/* 129:247 */       String message = e.getMessage();
/* 130:248 */       if (e.getType() == 0) {
/* 131:249 */         throw new SAXNotRecognizedException(message);
/* 132:    */       }
/* 133:252 */       throw new SAXNotSupportedException(message);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean getFeature(String featureId)
/* 138:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 139:    */   {
/* 140:    */     try
/* 141:    */     {
/* 142:276 */       return this.fParserConfiguration.getFeature(featureId);
/* 143:    */     }
/* 144:    */     catch (XMLConfigurationException e)
/* 145:    */     {
/* 146:279 */       String message = e.getMessage();
/* 147:280 */       if (e.getType() == 0) {
/* 148:281 */         throw new SAXNotRecognizedException(message);
/* 149:    */       }
/* 150:284 */       throw new SAXNotSupportedException(message);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setProperty(String propertyId, Object value)
/* 155:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 156:    */   {
/* 157:    */     try
/* 158:    */     {
/* 159:309 */       this.fParserConfiguration.setProperty(propertyId, value);
/* 160:    */     }
/* 161:    */     catch (XMLConfigurationException e)
/* 162:    */     {
/* 163:312 */       String message = e.getMessage();
/* 164:313 */       if (e.getType() == 0) {
/* 165:314 */         throw new SAXNotRecognizedException(message);
/* 166:    */       }
/* 167:317 */       throw new SAXNotSupportedException(message);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Object getProperty(String propertyId)
/* 172:    */     throws SAXNotRecognizedException, SAXNotSupportedException
/* 173:    */   {
/* 174:340 */     if (propertyId.equals("http://apache.org/xml/properties/dom/current-element-node")) {
/* 175:341 */       return (this.fCurrentNode != null) && (this.fCurrentNode.getNodeType() == 1) ? this.fCurrentNode : null;
/* 176:    */     }
/* 177:    */     try
/* 178:    */     {
/* 179:346 */       return this.fParserConfiguration.getProperty(propertyId);
/* 180:    */     }
/* 181:    */     catch (XMLConfigurationException e)
/* 182:    */     {
/* 183:349 */       String message = e.getMessage();
/* 184:350 */       if (e.getType() == 0) {
/* 185:351 */         throw new SAXNotRecognizedException(message);
/* 186:    */       }
/* 187:354 */       throw new SAXNotSupportedException(message);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setDocumentSource(XMLDocumentSource source)
/* 192:    */   {
/* 193:366 */     this.fDocumentSource = source;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public XMLDocumentSource getDocumentSource()
/* 197:    */   {
/* 198:371 */     return this.fDocumentSource;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void startDocument(XMLLocator locator, String encoding, Augmentations augs)
/* 202:    */     throws XNIException
/* 203:    */   {
/* 204:377 */     startDocument(locator, encoding, null, augs);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void startDocument(XMLLocator locator, String encoding, NamespaceContext nscontext, Augmentations augs)
/* 208:    */     throws XNIException
/* 209:    */   {
/* 210:386 */     this.fInCDATASection = false;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void xmlDecl(String version, String encoding, String standalone, Augmentations augs)
/* 214:    */     throws XNIException
/* 215:    */   {}
/* 216:    */   
/* 217:    */   public void doctypeDecl(String root, String pubid, String sysid, Augmentations augs)
/* 218:    */     throws XNIException
/* 219:    */   {}
/* 220:    */   
/* 221:    */   public void processingInstruction(String target, XMLString data, Augmentations augs)
/* 222:    */     throws XNIException
/* 223:    */   {
/* 224:405 */     String s = data.toString();
/* 225:406 */     if (XMLChar.isValidName(s))
/* 226:    */     {
/* 227:407 */       ProcessingInstruction pi = this.fDocument.createProcessingInstruction(target, s);
/* 228:408 */       this.fCurrentNode.appendChild(pi);
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void comment(XMLString text, Augmentations augs)
/* 233:    */     throws XNIException
/* 234:    */   {
/* 235:415 */     Comment comment = this.fDocument.createComment(text.toString());
/* 236:416 */     this.fCurrentNode.appendChild(comment);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void startPrefixMapping(String prefix, String uri, Augmentations augs)
/* 240:    */     throws XNIException
/* 241:    */   {}
/* 242:    */   
/* 243:    */   public void endPrefixMapping(String prefix, Augmentations augs)
/* 244:    */     throws XNIException
/* 245:    */   {}
/* 246:    */   
/* 247:    */   public void startElement(QName element, XMLAttributes attrs, Augmentations augs)
/* 248:    */     throws XNIException
/* 249:    */   {
/* 250:432 */     Element elementNode = this.fDocument.createElement(element.rawname);
/* 251:433 */     int count = attrs != null ? attrs.getLength() : 0;
/* 252:434 */     for (int i = 0; i < count; i++)
/* 253:    */     {
/* 254:435 */       String aname = attrs.getQName(i);
/* 255:436 */       String avalue = attrs.getValue(i);
/* 256:437 */       if (XMLChar.isValidName(aname)) {
/* 257:438 */         elementNode.setAttribute(aname, avalue);
/* 258:    */       }
/* 259:    */     }
/* 260:441 */     this.fCurrentNode.appendChild(elementNode);
/* 261:442 */     this.fCurrentNode = elementNode;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void emptyElement(QName element, XMLAttributes attrs, Augmentations augs)
/* 265:    */     throws XNIException
/* 266:    */   {
/* 267:448 */     startElement(element, attrs, augs);
/* 268:449 */     endElement(element, augs);
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void characters(XMLString text, Augmentations augs)
/* 272:    */     throws XNIException
/* 273:    */   {
/* 274:456 */     if (this.fInCDATASection)
/* 275:    */     {
/* 276:457 */       Node node = this.fCurrentNode.getLastChild();
/* 277:458 */       if ((node != null) && (node.getNodeType() == 4))
/* 278:    */       {
/* 279:459 */         CDATASection cdata = (CDATASection)node;
/* 280:460 */         cdata.appendData(text.toString());
/* 281:    */       }
/* 282:    */       else
/* 283:    */       {
/* 284:463 */         CDATASection cdata = this.fDocument.createCDATASection(text.toString());
/* 285:464 */         this.fCurrentNode.appendChild(cdata);
/* 286:    */       }
/* 287:    */     }
/* 288:    */     else
/* 289:    */     {
/* 290:468 */       Node node = this.fCurrentNode.getLastChild();
/* 291:469 */       if ((node != null) && (node.getNodeType() == 3))
/* 292:    */       {
/* 293:470 */         Text textNode = (Text)node;
/* 294:471 */         textNode.appendData(text.toString());
/* 295:    */       }
/* 296:    */       else
/* 297:    */       {
/* 298:474 */         Text textNode = this.fDocument.createTextNode(text.toString());
/* 299:475 */         this.fCurrentNode.appendChild(textNode);
/* 300:    */       }
/* 301:    */     }
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void ignorableWhitespace(XMLString text, Augmentations augs)
/* 305:    */     throws XNIException
/* 306:    */   {
/* 307:484 */     characters(text, augs);
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void startGeneralEntity(String name, XMLResourceIdentifier id, String encoding, Augmentations augs)
/* 311:    */     throws XNIException
/* 312:    */   {
/* 313:491 */     EntityReference entityRef = this.fDocument.createEntityReference(name);
/* 314:492 */     this.fCurrentNode.appendChild(entityRef);
/* 315:493 */     this.fCurrentNode = entityRef;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void textDecl(String version, String encoding, Augmentations augs)
/* 319:    */     throws XNIException
/* 320:    */   {}
/* 321:    */   
/* 322:    */   public void endGeneralEntity(String name, Augmentations augs)
/* 323:    */     throws XNIException
/* 324:    */   {
/* 325:504 */     this.fCurrentNode = this.fCurrentNode.getParentNode();
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void startCDATA(Augmentations augs)
/* 329:    */     throws XNIException
/* 330:    */   {
/* 331:509 */     this.fInCDATASection = true;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void endCDATA(Augmentations augs)
/* 335:    */     throws XNIException
/* 336:    */   {
/* 337:514 */     this.fInCDATASection = false;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public void endElement(QName element, Augmentations augs)
/* 341:    */     throws XNIException
/* 342:    */   {
/* 343:520 */     this.fCurrentNode = this.fCurrentNode.getParentNode();
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void endDocument(Augmentations augs)
/* 347:    */     throws XNIException
/* 348:    */   {}
/* 349:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.parsers.DOMFragmentParser
 * JD-Core Version:    0.7.0.1
 */