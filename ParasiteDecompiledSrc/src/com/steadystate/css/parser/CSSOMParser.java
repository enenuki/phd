/*   1:    */ package com.steadystate.css.parser;
/*   2:    */ 
/*   3:    */ import com.steadystate.css.dom.CSSCharsetRuleImpl;
/*   4:    */ import com.steadystate.css.dom.CSSFontFaceRuleImpl;
/*   5:    */ import com.steadystate.css.dom.CSSImportRuleImpl;
/*   6:    */ import com.steadystate.css.dom.CSSMediaRuleImpl;
/*   7:    */ import com.steadystate.css.dom.CSSOMObject;
/*   8:    */ import com.steadystate.css.dom.CSSPageRuleImpl;
/*   9:    */ import com.steadystate.css.dom.CSSRuleListImpl;
/*  10:    */ import com.steadystate.css.dom.CSSStyleDeclarationImpl;
/*  11:    */ import com.steadystate.css.dom.CSSStyleRuleImpl;
/*  12:    */ import com.steadystate.css.dom.CSSStyleSheetImpl;
/*  13:    */ import com.steadystate.css.dom.CSSUnknownRuleImpl;
/*  14:    */ import com.steadystate.css.dom.CSSValueImpl;
/*  15:    */ import com.steadystate.css.dom.MediaListImpl;
/*  16:    */ import com.steadystate.css.dom.Property;
/*  17:    */ import com.steadystate.css.sac.DocumentHandlerExt;
/*  18:    */ import com.steadystate.css.userdata.UserDataConstants;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.io.PrintStream;
/*  21:    */ import java.util.Properties;
/*  22:    */ import java.util.Stack;
/*  23:    */ import org.w3c.css.sac.CSSException;
/*  24:    */ import org.w3c.css.sac.ErrorHandler;
/*  25:    */ import org.w3c.css.sac.InputSource;
/*  26:    */ import org.w3c.css.sac.LexicalUnit;
/*  27:    */ import org.w3c.css.sac.Parser;
/*  28:    */ import org.w3c.css.sac.SACMediaList;
/*  29:    */ import org.w3c.css.sac.SelectorList;
/*  30:    */ import org.w3c.css.sac.helpers.ParserFactory;
/*  31:    */ import org.w3c.dom.DOMException;
/*  32:    */ import org.w3c.dom.Node;
/*  33:    */ import org.w3c.dom.css.CSSRule;
/*  34:    */ import org.w3c.dom.css.CSSStyleDeclaration;
/*  35:    */ import org.w3c.dom.css.CSSStyleSheet;
/*  36:    */ import org.w3c.dom.css.CSSValue;
/*  37:    */ 
/*  38:    */ public class CSSOMParser
/*  39:    */ {
/*  40:    */   private static final String PARSER = "com.steadystate.css.parser.SACParserCSS2";
/*  41: 78 */   private static boolean use_internal = false;
/*  42: 80 */   private Parser _parser = null;
/*  43: 81 */   private CSSStyleSheetImpl _parentStyleSheet = null;
/*  44:    */   
/*  45:    */   public CSSOMParser()
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49: 89 */       if (use_internal)
/*  50:    */       {
/*  51: 90 */         this._parser = new SACParserCSS2();
/*  52:    */       }
/*  53:    */       else
/*  54:    */       {
/*  55: 92 */         setProperty("org.w3c.css.sac.parser", "com.steadystate.css.parser.SACParserCSS2");
/*  56: 93 */         ParserFactory factory = new ParserFactory();
/*  57: 94 */         this._parser = factory.makeParser();
/*  58:    */       }
/*  59:    */     }
/*  60:    */     catch (Exception e)
/*  61:    */     {
/*  62: 97 */       use_internal = true;
/*  63: 98 */       System.err.println(e.getMessage());
/*  64: 99 */       e.printStackTrace();
/*  65:100 */       System.err.println("using the default parser instead");
/*  66:101 */       this._parser = new SACParserCSS2();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public CSSOMParser(Parser parser)
/*  71:    */   {
/*  72:112 */     this._parser = parser;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setErrorHandler(ErrorHandler eh)
/*  76:    */   {
/*  77:118 */     this._parser.setErrorHandler(eh);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public CSSStyleSheet parseStyleSheet(InputSource source, Node ownerNode, String href)
/*  81:    */     throws IOException
/*  82:    */   {
/*  83:134 */     CSSOMHandler handler = new CSSOMHandler();
/*  84:135 */     handler.setOwnerNode(ownerNode);
/*  85:136 */     handler.setHref(href);
/*  86:137 */     this._parser.setDocumentHandler(handler);
/*  87:138 */     this._parser.parseStyleSheet(source);
/*  88:139 */     Object o = handler.getRoot();
/*  89:140 */     if ((o instanceof CSSStyleSheet)) {
/*  90:142 */       return (CSSStyleSheet)handler.getRoot();
/*  91:    */     }
/*  92:144 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public CSSStyleDeclaration parseStyleDeclaration(InputSource source)
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:156 */     CSSStyleDeclarationImpl sd = new CSSStyleDeclarationImpl(null);
/*  99:157 */     parseStyleDeclaration(sd, source);
/* 100:158 */     return sd;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void parseStyleDeclaration(CSSStyleDeclaration sd, InputSource source)
/* 104:    */     throws IOException
/* 105:    */   {
/* 106:163 */     Stack nodeStack = new Stack();
/* 107:164 */     nodeStack.push(sd);
/* 108:165 */     CSSOMHandler handler = new CSSOMHandler(nodeStack);
/* 109:166 */     this._parser.setDocumentHandler(handler);
/* 110:167 */     this._parser.parseStyleDeclaration(source);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public CSSValue parsePropertyValue(InputSource source)
/* 114:    */     throws IOException
/* 115:    */   {
/* 116:171 */     CSSOMHandler handler = new CSSOMHandler();
/* 117:172 */     this._parser.setDocumentHandler(handler);
/* 118:173 */     return new CSSValueImpl(this._parser.parsePropertyValue(source));
/* 119:    */   }
/* 120:    */   
/* 121:    */   public CSSRule parseRule(InputSource source)
/* 122:    */     throws IOException
/* 123:    */   {
/* 124:177 */     CSSOMHandler handler = new CSSOMHandler();
/* 125:178 */     this._parser.setDocumentHandler(handler);
/* 126:179 */     this._parser.parseRule(source);
/* 127:180 */     return (CSSRule)handler.getRoot();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public SelectorList parseSelectors(InputSource source)
/* 131:    */     throws IOException
/* 132:    */   {
/* 133:184 */     HandlerBase handler = new HandlerBase();
/* 134:185 */     this._parser.setDocumentHandler(handler);
/* 135:186 */     return this._parser.parseSelectors(source);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setParentStyleSheet(CSSStyleSheetImpl parentStyleSheet)
/* 139:    */   {
/* 140:190 */     this._parentStyleSheet = parentStyleSheet;
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected CSSStyleSheetImpl getParentStyleSheet()
/* 144:    */   {
/* 145:195 */     return this._parentStyleSheet;
/* 146:    */   }
/* 147:    */   
/* 148:    */   class CSSOMHandler
/* 149:    */     implements DocumentHandlerExt
/* 150:    */   {
/* 151:    */     private Stack _nodeStack;
/* 152:207 */     private Object _root = null;
/* 153:    */     private Node ownerNode;
/* 154:    */     private String href;
/* 155:    */     
/* 156:    */     private Node getOwnerNode()
/* 157:    */     {
/* 158:213 */       return this.ownerNode;
/* 159:    */     }
/* 160:    */     
/* 161:    */     private void setOwnerNode(Node ownerNode)
/* 162:    */     {
/* 163:218 */       this.ownerNode = ownerNode;
/* 164:    */     }
/* 165:    */     
/* 166:    */     private String getHref()
/* 167:    */     {
/* 168:223 */       return this.href;
/* 169:    */     }
/* 170:    */     
/* 171:    */     private void setHref(String href)
/* 172:    */     {
/* 173:228 */       this.href = href;
/* 174:    */     }
/* 175:    */     
/* 176:    */     public CSSOMHandler(Stack nodeStack)
/* 177:    */     {
/* 178:232 */       this._nodeStack = nodeStack;
/* 179:    */     }
/* 180:    */     
/* 181:    */     public CSSOMHandler()
/* 182:    */     {
/* 183:236 */       this._nodeStack = new Stack();
/* 184:    */     }
/* 185:    */     
/* 186:    */     public Object getRoot()
/* 187:    */     {
/* 188:240 */       return this._root;
/* 189:    */     }
/* 190:    */     
/* 191:    */     public void startDocument(InputSource source)
/* 192:    */       throws CSSException
/* 193:    */     {
/* 194:244 */       if (this._nodeStack.empty())
/* 195:    */       {
/* 196:245 */         CSSStyleSheetImpl ss = new CSSStyleSheetImpl();
/* 197:246 */         CSSOMParser.this.setParentStyleSheet(ss);
/* 198:247 */         ss.setOwnerNode(getOwnerNode());
/* 199:248 */         ss.setBaseUri(source.getURI());
/* 200:249 */         ss.setHref(getHref());
/* 201:250 */         ss.setMediaText(source.getMedia());
/* 202:251 */         ss.setTitle(source.getTitle());
/* 203:    */         
/* 204:253 */         CSSRuleListImpl rules = new CSSRuleListImpl();
/* 205:254 */         ss.setCssRules(rules);
/* 206:255 */         this._nodeStack.push(ss);
/* 207:256 */         this._nodeStack.push(rules);
/* 208:    */       }
/* 209:    */     }
/* 210:    */     
/* 211:    */     public void endDocument(InputSource source)
/* 212:    */       throws CSSException
/* 213:    */     {
/* 214:265 */       this._nodeStack.pop();
/* 215:266 */       this._root = this._nodeStack.pop();
/* 216:    */     }
/* 217:    */     
/* 218:    */     public void comment(String text)
/* 219:    */       throws CSSException
/* 220:    */     {}
/* 221:    */     
/* 222:    */     public void ignorableAtRule(String atRule)
/* 223:    */       throws CSSException
/* 224:    */     {
/* 225:275 */       CSSUnknownRuleImpl ir = new CSSUnknownRuleImpl(CSSOMParser.this.getParentStyleSheet(), getParentRule(), atRule);
/* 226:    */       
/* 227:    */ 
/* 228:    */ 
/* 229:279 */       addLocator(ir);
/* 230:280 */       if (!this._nodeStack.empty()) {
/* 231:281 */         ((CSSRuleListImpl)this._nodeStack.peek()).add(ir);
/* 232:    */       } else {
/* 233:284 */         this._root = ir;
/* 234:    */       }
/* 235:    */     }
/* 236:    */     
/* 237:    */     public void namespaceDeclaration(String prefix, String uri)
/* 238:    */       throws CSSException
/* 239:    */     {}
/* 240:    */     
/* 241:    */     public void charset(String characterEncoding)
/* 242:    */       throws CSSException
/* 243:    */     {
/* 244:294 */       CSSCharsetRuleImpl cr = new CSSCharsetRuleImpl(CSSOMParser.this.getParentStyleSheet(), getParentRule(), characterEncoding);
/* 245:    */       
/* 246:    */ 
/* 247:297 */       addLocator(cr);
/* 248:298 */       if (!this._nodeStack.empty()) {
/* 249:300 */         ((CSSRuleListImpl)this._nodeStack.peek()).add(cr);
/* 250:    */       } else {
/* 251:304 */         this._root = cr;
/* 252:    */       }
/* 253:    */     }
/* 254:    */     
/* 255:    */     public void importStyle(String uri, SACMediaList media, String defaultNamespaceURI)
/* 256:    */       throws CSSException
/* 257:    */     {
/* 258:313 */       CSSImportRuleImpl ir = new CSSImportRuleImpl(CSSOMParser.this.getParentStyleSheet(), getParentRule(), uri, new MediaListImpl(media));
/* 259:    */       
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:318 */       addLocator(ir);
/* 264:319 */       if (!this._nodeStack.empty()) {
/* 265:320 */         ((CSSRuleListImpl)this._nodeStack.peek()).add(ir);
/* 266:    */       } else {
/* 267:323 */         this._root = ir;
/* 268:    */       }
/* 269:    */     }
/* 270:    */     
/* 271:    */     public void startMedia(SACMediaList media)
/* 272:    */       throws CSSException
/* 273:    */     {
/* 274:329 */       MediaListImpl ml = new MediaListImpl(media);
/* 275:330 */       addLocator(ml);
/* 276:    */       
/* 277:332 */       CSSMediaRuleImpl mr = new CSSMediaRuleImpl(CSSOMParser.this.getParentStyleSheet(), getParentRule(), ml);
/* 278:    */       
/* 279:    */ 
/* 280:    */ 
/* 281:336 */       addLocator(mr);
/* 282:337 */       if (!this._nodeStack.empty()) {
/* 283:338 */         ((CSSRuleListImpl)this._nodeStack.peek()).add(mr);
/* 284:    */       }
/* 285:342 */       CSSRuleListImpl rules = new CSSRuleListImpl();
/* 286:343 */       mr.setRuleList(rules);
/* 287:344 */       this._nodeStack.push(mr);
/* 288:345 */       this._nodeStack.push(rules);
/* 289:    */     }
/* 290:    */     
/* 291:    */     public void endMedia(SACMediaList media)
/* 292:    */       throws CSSException
/* 293:    */     {
/* 294:351 */       this._nodeStack.pop();
/* 295:352 */       this._root = this._nodeStack.pop();
/* 296:    */     }
/* 297:    */     
/* 298:    */     public void startPage(String name, String pseudo_page)
/* 299:    */       throws CSSException
/* 300:    */     {
/* 301:358 */       CSSPageRuleImpl pr = new CSSPageRuleImpl(CSSOMParser.this.getParentStyleSheet(), getParentRule(), name, pseudo_page);
/* 302:    */       
/* 303:    */ 
/* 304:361 */       addLocator(pr);
/* 305:362 */       if (!this._nodeStack.empty()) {
/* 306:363 */         ((CSSRuleListImpl)this._nodeStack.peek()).add(pr);
/* 307:    */       }
/* 308:367 */       CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(pr);
/* 309:368 */       pr.setStyle(decl);
/* 310:369 */       this._nodeStack.push(pr);
/* 311:370 */       this._nodeStack.push(decl);
/* 312:    */     }
/* 313:    */     
/* 314:    */     public void endPage(String name, String pseudo_page)
/* 315:    */       throws CSSException
/* 316:    */     {
/* 317:376 */       this._nodeStack.pop();
/* 318:377 */       this._root = this._nodeStack.pop();
/* 319:    */     }
/* 320:    */     
/* 321:    */     public void startFontFace()
/* 322:    */       throws CSSException
/* 323:    */     {
/* 324:383 */       CSSFontFaceRuleImpl ffr = new CSSFontFaceRuleImpl(CSSOMParser.this.getParentStyleSheet(), getParentRule());
/* 325:    */       
/* 326:    */ 
/* 327:386 */       addLocator(ffr);
/* 328:387 */       if (!this._nodeStack.empty()) {
/* 329:388 */         ((CSSRuleListImpl)this._nodeStack.peek()).add(ffr);
/* 330:    */       }
/* 331:392 */       CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(ffr);
/* 332:393 */       ffr.setStyle(decl);
/* 333:394 */       this._nodeStack.push(ffr);
/* 334:395 */       this._nodeStack.push(decl);
/* 335:    */     }
/* 336:    */     
/* 337:    */     public void endFontFace()
/* 338:    */       throws CSSException
/* 339:    */     {
/* 340:401 */       this._nodeStack.pop();
/* 341:402 */       this._root = this._nodeStack.pop();
/* 342:    */     }
/* 343:    */     
/* 344:    */     public void startSelector(SelectorList selectors)
/* 345:    */       throws CSSException
/* 346:    */     {
/* 347:408 */       CSSStyleRuleImpl sr = new CSSStyleRuleImpl(CSSOMParser.this.getParentStyleSheet(), getParentRule(), selectors);
/* 348:    */       
/* 349:    */ 
/* 350:411 */       addLocator(sr);
/* 351:412 */       if (!this._nodeStack.empty())
/* 352:    */       {
/* 353:413 */         Object o = this._nodeStack.peek();
/* 354:414 */         ((CSSRuleListImpl)o).add(sr);
/* 355:    */       }
/* 356:418 */       CSSStyleDeclarationImpl decl = new CSSStyleDeclarationImpl(sr);
/* 357:419 */       sr.setStyle(decl);
/* 358:420 */       this._nodeStack.push(sr);
/* 359:421 */       this._nodeStack.push(decl);
/* 360:    */     }
/* 361:    */     
/* 362:    */     public void endSelector(SelectorList selectors)
/* 363:    */       throws CSSException
/* 364:    */     {
/* 365:427 */       this._nodeStack.pop();
/* 366:428 */       this._root = this._nodeStack.pop();
/* 367:    */     }
/* 368:    */     
/* 369:    */     public void property(String name, LexicalUnit value, boolean important)
/* 370:    */       throws CSSException
/* 371:    */     {
/* 372:433 */       CSSStyleDeclarationImpl decl = (CSSStyleDeclarationImpl)this._nodeStack.peek();
/* 373:    */       try
/* 374:    */       {
/* 375:437 */         Property property = new Property(name, new CSSValueImpl(value), important);
/* 376:    */         
/* 377:439 */         addLocator(property);
/* 378:440 */         decl.addProperty(property);
/* 379:    */       }
/* 380:    */       catch (DOMException e) {}
/* 381:    */     }
/* 382:    */     
/* 383:    */     private CSSRule getParentRule()
/* 384:    */     {
/* 385:450 */       if ((!this._nodeStack.empty()) && (this._nodeStack.size() > 1))
/* 386:    */       {
/* 387:452 */         Object node = this._nodeStack.get(this._nodeStack.size() - 2);
/* 388:453 */         if ((node instanceof CSSRule)) {
/* 389:455 */           return (CSSRule)node;
/* 390:    */         }
/* 391:    */       }
/* 392:458 */       return null;
/* 393:    */     }
/* 394:    */     
/* 395:    */     private void addLocator(CSSOMObject cssomObject)
/* 396:    */     {
/* 397:463 */       cssomObject.setUserData(UserDataConstants.KEY_LOCATOR, ((AbstractSACParser)CSSOMParser.this._parser).getLocator());
/* 398:    */     }
/* 399:    */   }
/* 400:    */   
/* 401:    */   public static void setProperty(String key, String val)
/* 402:    */   {
/* 403:469 */     Properties props = System.getProperties();
/* 404:470 */     props.put(key, val);
/* 405:471 */     System.setProperties(props);
/* 406:    */   }
/* 407:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.steadystate.css.parser.CSSOMParser
 * JD-Core Version:    0.7.0.1
 */