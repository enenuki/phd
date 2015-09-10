/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.host;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*   6:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   7:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*   9:    */ import com.gargoylesoftware.htmlunit.html.DomAttr;
/*  10:    */ import com.gargoylesoftware.htmlunit.html.DomComment;
/*  11:    */ import com.gargoylesoftware.htmlunit.html.DomDocumentFragment;
/*  12:    */ import com.gargoylesoftware.htmlunit.html.DomElement;
/*  13:    */ import com.gargoylesoftware.htmlunit.html.DomNode;
/*  14:    */ import com.gargoylesoftware.htmlunit.html.DomText;
/*  15:    */ import com.gargoylesoftware.htmlunit.html.FrameWindow;
/*  16:    */ import com.gargoylesoftware.htmlunit.html.HtmlDivision;
/*  17:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*  18:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*  19:    */ import com.gargoylesoftware.htmlunit.html.impl.SimpleRange;
/*  20:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*  21:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCollection;
/*  22:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLElement;
/*  23:    */ import com.gargoylesoftware.htmlunit.xml.XmlUtil;
/*  24:    */ import java.io.IOException;
/*  25:    */ import java.util.Map;
/*  26:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  27:    */ import org.apache.commons.logging.Log;
/*  28:    */ import org.apache.commons.logging.LogFactory;
/*  29:    */ 
/*  30:    */ public class Document
/*  31:    */   extends EventNode
/*  32:    */ {
/*  33: 63 */   private static final Log LOG = LogFactory.getLog(Document.class);
/*  34:    */   private Window window_;
/*  35:    */   private DOMImplementation implementation_;
/*  36:    */   private String designMode_;
/*  37:    */   
/*  38:    */   public void setWindow(Window window)
/*  39:    */   {
/*  40: 74 */     this.window_ = window;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Location jsxGet_location()
/*  44:    */   {
/*  45: 82 */     return this.window_.jsxGet_location();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void jsxSet_location(String location)
/*  49:    */     throws IOException
/*  50:    */   {
/*  51: 94 */     this.window_.jsxSet_location(location);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String jsxGet_referrer()
/*  55:    */   {
/*  56:102 */     String referrer = (String)getPage().getWebResponse().getWebRequest().getAdditionalHeaders().get("Referer");
/*  57:103 */     if (referrer == null) {
/*  58:104 */       return "";
/*  59:    */     }
/*  60:106 */     return referrer;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Element jsxGet_documentElement()
/*  64:    */   {
/*  65:114 */     Object documentElement = getPage().getDocumentElement();
/*  66:115 */     if (documentElement == null) {
/*  67:117 */       return null;
/*  68:    */     }
/*  69:119 */     return (Element)getScriptableFor(documentElement);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public SimpleScriptable jsxGet_doctype()
/*  73:    */   {
/*  74:127 */     Object documentType = getPage().getDoctype();
/*  75:128 */     if (documentType == null) {
/*  76:129 */       return null;
/*  77:    */     }
/*  78:131 */     return getScriptableFor(documentType);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String jsxGet_designMode()
/*  82:    */   {
/*  83:139 */     if (this.designMode_ == null) {
/*  84:140 */       if (getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_30))
/*  85:    */       {
/*  86:141 */         if ((getWindow().getWebWindow() instanceof FrameWindow)) {
/*  87:142 */           this.designMode_ = "Inherit";
/*  88:    */         } else {
/*  89:145 */           this.designMode_ = "Off";
/*  90:    */         }
/*  91:    */       }
/*  92:    */       else {
/*  93:149 */         this.designMode_ = "off";
/*  94:    */       }
/*  95:    */     }
/*  96:152 */     return this.designMode_;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void jsxSet_designMode(String mode)
/* 100:    */   {
/* 101:160 */     boolean ie = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_31);
/* 102:161 */     if (ie)
/* 103:    */     {
/* 104:162 */       if ((!"on".equalsIgnoreCase(mode)) && (!"off".equalsIgnoreCase(mode)) && (!"inherit".equalsIgnoreCase(mode))) {
/* 105:163 */         throw Context.reportRuntimeError("Invalid document.designMode value '" + mode + "'.");
/* 106:    */       }
/* 107:165 */       if (!(getWindow().getWebWindow() instanceof FrameWindow)) {
/* 108:167 */         return;
/* 109:    */       }
/* 110:169 */       if ("on".equalsIgnoreCase(mode)) {
/* 111:170 */         this.designMode_ = "On";
/* 112:172 */       } else if ("off".equalsIgnoreCase(mode)) {
/* 113:173 */         this.designMode_ = "Off";
/* 114:175 */       } else if ("inherit".equalsIgnoreCase(mode)) {
/* 115:176 */         this.designMode_ = "Inherit";
/* 116:    */       }
/* 117:    */     }
/* 118:180 */     else if ("on".equalsIgnoreCase(mode))
/* 119:    */     {
/* 120:181 */       this.designMode_ = "on";
/* 121:182 */       SgmlPage page = getPage();
/* 122:183 */       if ((page instanceof HtmlPage))
/* 123:    */       {
/* 124:184 */         HtmlPage htmlPage = (HtmlPage)page;
/* 125:185 */         DomNode child = htmlPage.getBody().getFirstChild();
/* 126:186 */         DomNode rangeNode = child != null ? child : htmlPage.getBody();
/* 127:187 */         htmlPage.setSelectionRange(new SimpleRange(rangeNode, 0));
/* 128:    */       }
/* 129:    */     }
/* 130:190 */     else if ("off".equalsIgnoreCase(mode))
/* 131:    */     {
/* 132:191 */       this.designMode_ = "off";
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected SgmlPage getPage()
/* 137:    */   {
/* 138:201 */     return (SgmlPage)getDomNodeOrDie();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Object jsxGet_defaultView()
/* 142:    */   {
/* 143:209 */     return getWindow();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Object jsxFunction_createDocumentFragment()
/* 147:    */   {
/* 148:217 */     DomDocumentFragment fragment = getDomNodeOrDie().getPage().createDomDocumentFragment();
/* 149:218 */     DocumentFragment node = new DocumentFragment();
/* 150:219 */     node.setParentScope(getParentScope());
/* 151:220 */     node.setPrototype(getPrototype(node.getClass()));
/* 152:221 */     node.setDomNode(fragment);
/* 153:222 */     return getScriptableFor(fragment);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public Attr jsxFunction_createAttribute(String attributeName)
/* 157:    */   {
/* 158:232 */     return (Attr)getPage().createAttribute(attributeName).getScriptObject();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public BoxObject jsxFunction_getBoxObjectFor(HTMLElement element)
/* 162:    */   {
/* 163:242 */     return element.getBoxObject();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Object jsxFunction_importNode(Node importedNode, boolean deep)
/* 167:    */   {
/* 168:255 */     return importedNode.getDomNodeOrDie().cloneNode(deep).getScriptObject();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public DOMImplementation jsxGet_implementation()
/* 172:    */   {
/* 173:263 */     if (this.implementation_ == null)
/* 174:    */     {
/* 175:264 */       this.implementation_ = new DOMImplementation();
/* 176:265 */       this.implementation_.setParentScope(getWindow());
/* 177:266 */       this.implementation_.setPrototype(getPrototype(this.implementation_.getClass()));
/* 178:    */     }
/* 179:268 */     return this.implementation_;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void jsxFunction_captureEvents(String type) {}
/* 183:    */   
/* 184:    */   public XPathNSResolver jsxFunction_createNSResolver(Node nodeResolver)
/* 185:    */   {
/* 186:288 */     XPathNSResolver resolver = new XPathNSResolver();
/* 187:289 */     resolver.setElement(nodeResolver);
/* 188:290 */     resolver.setParentScope(getWindow());
/* 189:291 */     resolver.setPrototype(getPrototype(resolver.getClass()));
/* 190:292 */     return resolver;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Object jsxFunction_createTextNode(String newData)
/* 194:    */   {
/* 195:302 */     Object result = NOT_FOUND;
/* 196:    */     try
/* 197:    */     {
/* 198:304 */       DomNode domNode = new DomText(getDomNodeOrDie().getPage(), newData);
/* 199:305 */       Object jsElement = getScriptableFor(domNode);
/* 200:307 */       if (jsElement == NOT_FOUND)
/* 201:    */       {
/* 202:308 */         if (LOG.isDebugEnabled()) {
/* 203:309 */           LOG.debug("createTextNode(" + newData + ") cannot return a result as there isn't a JavaScript object for the DOM node " + domNode.getClass().getName());
/* 204:    */         }
/* 205:    */       }
/* 206:    */       else {
/* 207:315 */         result = jsElement;
/* 208:    */       }
/* 209:    */     }
/* 210:    */     catch (ElementNotFoundException e) {}
/* 211:321 */     return result;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public Object jsxFunction_createComment(String comment)
/* 215:    */   {
/* 216:330 */     DomNode domNode = new DomComment(getDomNodeOrDie().getPage(), comment);
/* 217:331 */     return getScriptableFor(domNode);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public XPathResult jsxFunction_evaluate(String expression, Node contextNode, Object resolver, int type, Object result)
/* 221:    */   {
/* 222:346 */     XPathResult xPathResult = (XPathResult)result;
/* 223:347 */     if (xPathResult == null)
/* 224:    */     {
/* 225:348 */       xPathResult = new XPathResult();
/* 226:349 */       xPathResult.setParentScope(getParentScope());
/* 227:350 */       xPathResult.setPrototype(getPrototype(xPathResult.getClass()));
/* 228:    */     }
/* 229:352 */     xPathResult.init(contextNode.getDomNodeOrDie().getByXPath(expression), type);
/* 230:353 */     return xPathResult;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Object jsxFunction_createElement(String tagName)
/* 234:    */   {
/* 235:363 */     Object result = NOT_FOUND;
/* 236:    */     try
/* 237:    */     {
/* 238:365 */       BrowserVersion browserVersion = getBrowserVersion();
/* 239:367 */       if ((tagName.startsWith("<")) && (tagName.endsWith(">")) && (browserVersion.hasFeature(BrowserVersionFeatures.GENERATED_153)))
/* 240:    */       {
/* 241:369 */         tagName = tagName.substring(1, tagName.length() - 1);
/* 242:370 */         if (!tagName.matches("\\w+"))
/* 243:    */         {
/* 244:371 */           LOG.error("Unexpected exception occurred while parsing HTML snippet");
/* 245:372 */           throw Context.reportRuntimeError("Unexpected exception occurred while parsing HTML snippet: " + tagName);
/* 246:    */         }
/* 247:    */       }
/* 248:377 */       SgmlPage page = getPage();
/* 249:378 */       org.w3c.dom.Element element = page.createElement(tagName);
/* 250:379 */       Object jsElement = getScriptableFor(element);
/* 251:381 */       if (jsElement == NOT_FOUND)
/* 252:    */       {
/* 253:382 */         if (LOG.isDebugEnabled()) {
/* 254:383 */           LOG.debug("createElement(" + tagName + ") cannot return a result as there isn't a JavaScript object for the element " + element.getClass().getName());
/* 255:    */         }
/* 256:    */       }
/* 257:    */       else {
/* 258:389 */         result = jsElement;
/* 259:    */       }
/* 260:    */     }
/* 261:    */     catch (ElementNotFoundException e) {}
/* 262:395 */     return result;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Object jsxFunction_createElementNS(String namespaceURI, String qualifiedName)
/* 266:    */   {
/* 267:407 */     BrowserVersion browserVersion = getBrowserVersion();
/* 268:    */     org.w3c.dom.Element element;
/* 269:    */     org.w3c.dom.Element element;
/* 270:408 */     if ((browserVersion.hasFeature(BrowserVersionFeatures.XUL_SUPPORT)) && ("http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul".equals(namespaceURI)))
/* 271:    */     {
/* 272:411 */       element = new HtmlDivision(namespaceURI, qualifiedName, getPage(), null);
/* 273:    */     }
/* 274:    */     else
/* 275:    */     {
/* 276:    */       org.w3c.dom.Element element;
/* 277:413 */       if ("http://www.w3.org/1999/xhtml".equals(namespaceURI)) {
/* 278:414 */         element = getPage().createElementNS(namespaceURI, qualifiedName);
/* 279:    */       } else {
/* 280:417 */         element = new DomElement(namespaceURI, qualifiedName, getPage(), null);
/* 281:    */       }
/* 282:    */     }
/* 283:419 */     return getScriptableFor(element);
/* 284:    */   }
/* 285:    */   
/* 286:    */   public HTMLCollection jsxFunction_getElementsByTagName(String tagName)
/* 287:    */   {
/* 288:428 */     String description = "Document.getElementsByTagName('" + tagName + "')";
/* 289:    */     HTMLCollection collection;
/* 290:    */     HTMLCollection collection;
/* 291:431 */     if ("*".equals(tagName))
/* 292:    */     {
/* 293:432 */       collection = new HTMLCollection(getDomNodeOrDie(), false, description)
/* 294:    */       {
/* 295:    */         protected boolean isMatching(DomNode node)
/* 296:    */         {
/* 297:435 */           return true;
/* 298:    */         }
/* 299:    */       };
/* 300:    */     }
/* 301:    */     else
/* 302:    */     {
/* 303:440 */       final boolean useLocalName = getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_32);
/* 304:441 */       final String tagNameLC = tagName.toLowerCase();
/* 305:    */       
/* 306:443 */       collection = new HTMLCollection(getDomNodeOrDie(), false, description)
/* 307:    */       {
/* 308:    */         protected boolean isMatching(DomNode node)
/* 309:    */         {
/* 310:446 */           if (useLocalName) {
/* 311:447 */             return tagNameLC.equalsIgnoreCase(node.getLocalName());
/* 312:    */           }
/* 313:449 */           return tagNameLC.equalsIgnoreCase(node.getNodeName());
/* 314:    */         }
/* 315:    */       };
/* 316:    */     }
/* 317:454 */     return collection;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public Object jsxFunction_getElementsByTagNameNS(Object namespaceURI, final String localName)
/* 321:    */   {
/* 322:465 */     String description = "Document.getElementsByTagNameNS('" + namespaceURI + "', '" + localName + "')";
/* 323:466 */     DomElement domNode = getPage().getDocumentElement();
/* 324:    */     String prefix;
/* 325:    */     final String prefix;
/* 326:469 */     if ((namespaceURI != null) && (!"*".equals("*"))) {
/* 327:470 */       prefix = XmlUtil.lookupPrefix(domNode, Context.toString(namespaceURI));
/* 328:    */     } else {
/* 329:473 */       prefix = null;
/* 330:    */     }
/* 331:476 */     HTMLCollection collection = new HTMLCollection(domNode, false, description)
/* 332:    */     {
/* 333:    */       protected boolean isMatching(DomNode node)
/* 334:    */       {
/* 335:479 */         if (!localName.equals(node.getLocalName())) {
/* 336:480 */           return false;
/* 337:    */         }
/* 338:482 */         if (prefix == null) {
/* 339:483 */           return true;
/* 340:    */         }
/* 341:485 */         return true;
/* 342:    */       }
/* 343:488 */     };
/* 344:489 */     return collection;
/* 345:    */   }
/* 346:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.host.Document
 * JD-Core Version:    0.7.0.1
 */