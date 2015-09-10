/*   1:    */ package org.apache.xalan.xsltc.trax;
/*   2:    */ 
/*   3:    */ import javax.xml.transform.Result;
/*   4:    */ import javax.xml.transform.Transformer;
/*   5:    */ import javax.xml.transform.TransformerException;
/*   6:    */ import javax.xml.transform.dom.DOMResult;
/*   7:    */ import javax.xml.transform.sax.TransformerHandler;
/*   8:    */ import org.apache.xalan.xsltc.StripFilter;
/*   9:    */ import org.apache.xalan.xsltc.compiler.util.ErrorMsg;
/*  10:    */ import org.apache.xalan.xsltc.dom.DOMWSFilter;
/*  11:    */ import org.apache.xalan.xsltc.dom.SAXImpl;
/*  12:    */ import org.apache.xalan.xsltc.dom.XSLTCDTMManager;
/*  13:    */ import org.apache.xalan.xsltc.runtime.AbstractTranslet;
/*  14:    */ import org.apache.xalan.xsltc.runtime.output.TransletOutputHandlerFactory;
/*  15:    */ import org.apache.xml.dtm.DTMWSFilter;
/*  16:    */ import org.apache.xml.serializer.SerializationHandler;
/*  17:    */ import org.xml.sax.Attributes;
/*  18:    */ import org.xml.sax.ContentHandler;
/*  19:    */ import org.xml.sax.DTDHandler;
/*  20:    */ import org.xml.sax.Locator;
/*  21:    */ import org.xml.sax.SAXException;
/*  22:    */ import org.xml.sax.ext.DeclHandler;
/*  23:    */ import org.xml.sax.ext.LexicalHandler;
/*  24:    */ import org.xml.sax.helpers.DefaultHandler;
/*  25:    */ 
/*  26:    */ public class TransformerHandlerImpl
/*  27:    */   implements TransformerHandler, DeclHandler
/*  28:    */ {
/*  29:    */   private TransformerImpl _transformer;
/*  30: 55 */   private AbstractTranslet _translet = null;
/*  31:    */   private String _systemId;
/*  32: 57 */   private SAXImpl _dom = null;
/*  33: 58 */   private ContentHandler _handler = null;
/*  34: 59 */   private LexicalHandler _lexHandler = null;
/*  35: 60 */   private DTDHandler _dtdHandler = null;
/*  36: 61 */   private DeclHandler _declHandler = null;
/*  37: 62 */   private Result _result = null;
/*  38: 63 */   private Locator _locator = null;
/*  39: 65 */   private boolean _done = false;
/*  40: 71 */   private boolean _isIdentity = false;
/*  41:    */   
/*  42:    */   public TransformerHandlerImpl(TransformerImpl transformer)
/*  43:    */   {
/*  44: 78 */     this._transformer = transformer;
/*  45: 80 */     if (transformer.isIdentity())
/*  46:    */     {
/*  47: 82 */       this._handler = new DefaultHandler();
/*  48: 83 */       this._isIdentity = true;
/*  49:    */     }
/*  50:    */     else
/*  51:    */     {
/*  52: 87 */       this._translet = this._transformer.getTranslet();
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getSystemId()
/*  57:    */   {
/*  58: 98 */     return this._systemId;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setSystemId(String id)
/*  62:    */   {
/*  63:108 */     this._systemId = id;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Transformer getTransformer()
/*  67:    */   {
/*  68:118 */     return this._transformer;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setResult(Result result)
/*  72:    */     throws IllegalArgumentException
/*  73:    */   {
/*  74:129 */     this._result = result;
/*  75:131 */     if (null == result)
/*  76:    */     {
/*  77:132 */       ErrorMsg err = new ErrorMsg("ER_RESULT_NULL");
/*  78:133 */       throw new IllegalArgumentException(err.toString());
/*  79:    */     }
/*  80:136 */     if (this._isIdentity) {
/*  81:    */       try
/*  82:    */       {
/*  83:139 */         SerializationHandler outputHandler = this._transformer.getOutputHandler(result);
/*  84:    */         
/*  85:141 */         this._transformer.transferOutputProperties(outputHandler);
/*  86:    */         
/*  87:143 */         this._handler = outputHandler;
/*  88:144 */         this._lexHandler = outputHandler;
/*  89:    */       }
/*  90:    */       catch (TransformerException e)
/*  91:    */       {
/*  92:147 */         this._result = null;
/*  93:    */       }
/*  94:150 */     } else if (this._done) {
/*  95:    */       try
/*  96:    */       {
/*  97:153 */         this._transformer.setDOM(this._dom);
/*  98:154 */         this._transformer.transform(null, this._result);
/*  99:    */       }
/* 100:    */       catch (TransformerException e)
/* 101:    */       {
/* 102:158 */         throw new IllegalArgumentException(e.getMessage());
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void characters(char[] ch, int start, int length)
/* 108:    */     throws SAXException
/* 109:    */   {
/* 110:170 */     this._handler.characters(ch, start, length);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void startDocument()
/* 114:    */     throws SAXException
/* 115:    */   {
/* 116:179 */     if (this._result == null)
/* 117:    */     {
/* 118:180 */       ErrorMsg err = new ErrorMsg("JAXP_SET_RESULT_ERR");
/* 119:181 */       throw new SAXException(err.toString());
/* 120:    */     }
/* 121:184 */     if (!this._isIdentity)
/* 122:    */     {
/* 123:185 */       boolean hasIdCall = this._translet != null ? this._translet.hasIdCall() : false;
/* 124:186 */       XSLTCDTMManager dtmManager = null;
/* 125:    */       try
/* 126:    */       {
/* 127:190 */         dtmManager = (XSLTCDTMManager)this._transformer.getTransformerFactory().getDTMManagerClass().newInstance();
/* 128:    */       }
/* 129:    */       catch (Exception e)
/* 130:    */       {
/* 131:195 */         throw new SAXException(e);
/* 132:    */       }
/* 133:    */       DTMWSFilter wsFilter;
/* 134:199 */       if ((this._translet != null) && ((this._translet instanceof StripFilter))) {
/* 135:200 */         wsFilter = new DOMWSFilter(this._translet);
/* 136:    */       } else {
/* 137:202 */         wsFilter = null;
/* 138:    */       }
/* 139:206 */       this._dom = ((SAXImpl)dtmManager.getDTM(null, false, wsFilter, true, false, hasIdCall));
/* 140:    */       
/* 141:    */ 
/* 142:209 */       this._handler = this._dom.getBuilder();
/* 143:210 */       this._lexHandler = ((LexicalHandler)this._handler);
/* 144:211 */       this._dtdHandler = ((DTDHandler)this._handler);
/* 145:212 */       this._declHandler = ((DeclHandler)this._handler);
/* 146:    */       
/* 147:    */ 
/* 148:    */ 
/* 149:216 */       this._dom.setDocumentURI(this._systemId);
/* 150:218 */       if (this._locator != null) {
/* 151:219 */         this._handler.setDocumentLocator(this._locator);
/* 152:    */       }
/* 153:    */     }
/* 154:224 */     this._handler.startDocument();
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void endDocument()
/* 158:    */     throws SAXException
/* 159:    */   {
/* 160:233 */     this._handler.endDocument();
/* 161:235 */     if (!this._isIdentity)
/* 162:    */     {
/* 163:237 */       if (this._result != null) {
/* 164:    */         try
/* 165:    */         {
/* 166:239 */           this._transformer.setDOM(this._dom);
/* 167:240 */           this._transformer.transform(null, this._result);
/* 168:    */         }
/* 169:    */         catch (TransformerException e)
/* 170:    */         {
/* 171:243 */           throw new SAXException(e);
/* 172:    */         }
/* 173:    */       }
/* 174:247 */       this._done = true;
/* 175:    */       
/* 176:    */ 
/* 177:250 */       this._transformer.setDOM(this._dom);
/* 178:    */     }
/* 179:252 */     if ((this._isIdentity) && ((this._result instanceof DOMResult))) {
/* 180:253 */       ((DOMResult)this._result).setNode(this._transformer.getTransletOutputHandlerFactory().getNode());
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void startElement(String uri, String localName, String qname, Attributes attributes)
/* 185:    */     throws SAXException
/* 186:    */   {
/* 187:265 */     this._handler.startElement(uri, localName, qname, attributes);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void endElement(String namespaceURI, String localName, String qname)
/* 191:    */     throws SAXException
/* 192:    */   {
/* 193:275 */     this._handler.endElement(namespaceURI, localName, qname);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void processingInstruction(String target, String data)
/* 197:    */     throws SAXException
/* 198:    */   {
/* 199:285 */     this._handler.processingInstruction(target, data);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void startCDATA()
/* 203:    */     throws SAXException
/* 204:    */   {
/* 205:292 */     if (this._lexHandler != null) {
/* 206:293 */       this._lexHandler.startCDATA();
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void endCDATA()
/* 211:    */     throws SAXException
/* 212:    */   {
/* 213:301 */     if (this._lexHandler != null) {
/* 214:302 */       this._lexHandler.endCDATA();
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void comment(char[] ch, int start, int length)
/* 219:    */     throws SAXException
/* 220:    */   {
/* 221:313 */     if (this._lexHandler != null) {
/* 222:314 */       this._lexHandler.comment(ch, start, length);
/* 223:    */     }
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void ignorableWhitespace(char[] ch, int start, int length)
/* 227:    */     throws SAXException
/* 228:    */   {
/* 229:326 */     this._handler.ignorableWhitespace(ch, start, length);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setDocumentLocator(Locator locator)
/* 233:    */   {
/* 234:334 */     this._locator = locator;
/* 235:336 */     if (this._handler != null) {
/* 236:337 */       this._handler.setDocumentLocator(locator);
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void skippedEntity(String name)
/* 241:    */     throws SAXException
/* 242:    */   {
/* 243:346 */     this._handler.skippedEntity(name);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void startPrefixMapping(String prefix, String uri)
/* 247:    */     throws SAXException
/* 248:    */   {
/* 249:355 */     this._handler.startPrefixMapping(prefix, uri);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void endPrefixMapping(String prefix)
/* 253:    */     throws SAXException
/* 254:    */   {
/* 255:363 */     this._handler.endPrefixMapping(prefix);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void startDTD(String name, String publicId, String systemId)
/* 259:    */     throws SAXException
/* 260:    */   {
/* 261:372 */     if (this._lexHandler != null) {
/* 262:373 */       this._lexHandler.startDTD(name, publicId, systemId);
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void endDTD()
/* 267:    */     throws SAXException
/* 268:    */   {
/* 269:381 */     if (this._lexHandler != null) {
/* 270:382 */       this._lexHandler.endDTD();
/* 271:    */     }
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void startEntity(String name)
/* 275:    */     throws SAXException
/* 276:    */   {
/* 277:390 */     if (this._lexHandler != null) {
/* 278:391 */       this._lexHandler.startEntity(name);
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void endEntity(String name)
/* 283:    */     throws SAXException
/* 284:    */   {
/* 285:399 */     if (this._lexHandler != null) {
/* 286:400 */       this._lexHandler.endEntity(name);
/* 287:    */     }
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName)
/* 291:    */     throws SAXException
/* 292:    */   {
/* 293:410 */     if (this._dtdHandler != null) {
/* 294:411 */       this._dtdHandler.unparsedEntityDecl(name, publicId, systemId, notationName);
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void notationDecl(String name, String publicId, String systemId)
/* 299:    */     throws SAXException
/* 300:    */   {
/* 301:422 */     if (this._dtdHandler != null) {
/* 302:423 */       this._dtdHandler.notationDecl(name, publicId, systemId);
/* 303:    */     }
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value)
/* 307:    */     throws SAXException
/* 308:    */   {
/* 309:433 */     if (this._declHandler != null) {
/* 310:434 */       this._declHandler.attributeDecl(eName, aName, type, valueDefault, value);
/* 311:    */     }
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void elementDecl(String name, String model)
/* 315:    */     throws SAXException
/* 316:    */   {
/* 317:444 */     if (this._declHandler != null) {
/* 318:445 */       this._declHandler.elementDecl(name, model);
/* 319:    */     }
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void externalEntityDecl(String name, String publicId, String systemId)
/* 323:    */     throws SAXException
/* 324:    */   {
/* 325:455 */     if (this._declHandler != null) {
/* 326:456 */       this._declHandler.externalEntityDecl(name, publicId, systemId);
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void internalEntityDecl(String name, String value)
/* 331:    */     throws SAXException
/* 332:    */   {
/* 333:466 */     if (this._declHandler != null) {
/* 334:467 */       this._declHandler.internalEntityDecl(name, value);
/* 335:    */     }
/* 336:    */   }
/* 337:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.trax.TransformerHandlerImpl
 * JD-Core Version:    0.7.0.1
 */