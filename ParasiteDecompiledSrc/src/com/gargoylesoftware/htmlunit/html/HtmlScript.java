/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*   6:    */ import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
/*   7:    */ import com.gargoylesoftware.htmlunit.Page;
/*   8:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*  11:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  12:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  13:    */ import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
/*  14:    */ import com.gargoylesoftware.htmlunit.javascript.PostponedAction;
/*  15:    */ import com.gargoylesoftware.htmlunit.javascript.host.Event;
/*  16:    */ import com.gargoylesoftware.htmlunit.javascript.host.EventHandler;
/*  17:    */ import com.gargoylesoftware.htmlunit.javascript.host.Window;
/*  18:    */ import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLScriptElement;
/*  19:    */ import com.gargoylesoftware.htmlunit.xml.XmlPage;
/*  20:    */ import java.io.PrintWriter;
/*  21:    */ import java.net.URL;
/*  22:    */ import java.util.Map;
/*  23:    */ import net.sourceforge.htmlunit.corejs.javascript.BaseFunction;
/*  24:    */ import org.apache.commons.lang.StringUtils;
/*  25:    */ import org.apache.commons.logging.Log;
/*  26:    */ import org.apache.commons.logging.LogFactory;
/*  27:    */ 
/*  28:    */ public class HtmlScript
/*  29:    */   extends HtmlElement
/*  30:    */ {
/*  31: 65 */   private static final Log LOG = LogFactory.getLog(HtmlScript.class);
/*  32:    */   public static final String TAG_NAME = "script";
/*  33:    */   private static final String SLASH_SLASH_COLON = "//:";
/*  34:    */   
/*  35:    */   HtmlScript(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  36:    */   {
/*  37: 83 */     super(namespaceURI, qualifiedName, page, attributes);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public final String getCharsetAttribute()
/*  41:    */   {
/*  42: 95 */     return getAttribute("charset");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public final String getTypeAttribute()
/*  46:    */   {
/*  47:107 */     return getAttribute("type");
/*  48:    */   }
/*  49:    */   
/*  50:    */   public final String getLanguageAttribute()
/*  51:    */   {
/*  52:119 */     return getAttribute("language");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public final String getSrcAttribute()
/*  56:    */   {
/*  57:131 */     return getAttribute("src");
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final String getEventAttribute()
/*  61:    */   {
/*  62:139 */     return getAttribute("event");
/*  63:    */   }
/*  64:    */   
/*  65:    */   public final String getHtmlForAttribute()
/*  66:    */   {
/*  67:147 */     return getAttribute("for");
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final String getDeferAttribute()
/*  71:    */   {
/*  72:159 */     return getAttribute("defer");
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected boolean isDeferred()
/*  76:    */   {
/*  77:167 */     return getDeferAttribute() != ATTRIBUTE_NOT_DEFINED;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean mayBeDisplayed()
/*  81:    */   {
/*  82:175 */     return false;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/*  86:    */   {
/*  87:184 */     String oldValue = getAttributeNS(namespaceURI, qualifiedName);
/*  88:185 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/*  89:187 */     if ((namespaceURI == null) && ("src".equals(qualifiedName)))
/*  90:    */     {
/*  91:188 */       boolean ie = getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_5);
/*  92:190 */       if ((ie) || ((oldValue.length() == 0) && (getFirstChild() == null))) {
/*  93:194 */         executeScriptIfNeeded();
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected void onAllChildrenAddedToPage(boolean postponed)
/*  99:    */   {
/* 100:205 */     if ((getOwnerDocument() instanceof XmlPage)) {
/* 101:206 */       return;
/* 102:    */     }
/* 103:208 */     if (LOG.isDebugEnabled()) {
/* 104:209 */       LOG.debug("Script node added: " + asXml());
/* 105:    */     }
/* 106:211 */     PostponedAction action = new PostponedAction(getPage())
/* 107:    */     {
/* 108:    */       public void execute()
/* 109:    */       {
/* 110:214 */         boolean ie = HtmlScript.this.getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_6);
/* 111:216 */         if (ie)
/* 112:    */         {
/* 113:217 */           if (!HtmlScript.this.isDeferred()) {
/* 114:218 */             if (!"//:".equals(HtmlScript.this.getSrcAttribute()))
/* 115:    */             {
/* 116:219 */               HtmlScript.this.setAndExecuteReadyState("loading");
/* 117:220 */               HtmlScript.this.executeScriptIfNeeded();
/* 118:221 */               HtmlScript.this.setAndExecuteReadyState("loaded");
/* 119:    */             }
/* 120:    */             else
/* 121:    */             {
/* 122:224 */               HtmlScript.this.setAndExecuteReadyState("complete");
/* 123:225 */               HtmlScript.this.executeScriptIfNeeded();
/* 124:    */             }
/* 125:    */           }
/* 126:    */         }
/* 127:    */         else {
/* 128:230 */           HtmlScript.this.executeScriptIfNeeded();
/* 129:    */         }
/* 130:    */       }
/* 131:    */     };
/* 132:234 */     if ((postponed) && (StringUtils.isBlank(getTextContent())))
/* 133:    */     {
/* 134:235 */       JavaScriptEngine engine = getPage().getWebClient().getJavaScriptEngine();
/* 135:236 */       engine.addPostponedAction(action);
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:    */       try
/* 140:    */       {
/* 141:240 */         action.execute();
/* 142:    */       }
/* 143:    */       catch (Exception e)
/* 144:    */       {
/* 145:243 */         if ((e instanceof RuntimeException)) {
/* 146:244 */           throw ((RuntimeException)e);
/* 147:    */         }
/* 148:246 */         throw new RuntimeException(e);
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void executeInlineScriptIfNeeded()
/* 154:    */   {
/* 155:257 */     if (!isExecutionNeeded()) {
/* 156:258 */       return;
/* 157:    */     }
/* 158:261 */     String src = getSrcAttribute();
/* 159:262 */     if (src != DomElement.ATTRIBUTE_NOT_DEFINED) {
/* 160:263 */       return;
/* 161:    */     }
/* 162:266 */     DomCharacterData textNode = (DomCharacterData)getFirstChild();
/* 163:267 */     String forr = getHtmlForAttribute();
/* 164:268 */     String event = getEventAttribute();
/* 165:270 */     if (event.endsWith("()")) {
/* 166:271 */       event = event.substring(0, event.length() - 2);
/* 167:    */     }
/* 168:274 */     boolean ie = getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.GENERATED_7);
/* 169:275 */     String scriptCode = textNode.getData();
/* 170:276 */     if ((ie) && (event != ATTRIBUTE_NOT_DEFINED) && (forr != ATTRIBUTE_NOT_DEFINED))
/* 171:    */     {
/* 172:277 */       if ("window".equals(forr))
/* 173:    */       {
/* 174:279 */         Window window = (Window)getPage().getEnclosingWindow().getScriptObject();
/* 175:280 */         BaseFunction function = new EventHandler(this, event, scriptCode);
/* 176:281 */         window.jsxFunction_attachEvent(event, function);
/* 177:    */       }
/* 178:    */       else
/* 179:    */       {
/* 180:    */         try
/* 181:    */         {
/* 182:285 */           HtmlElement elt = ((HtmlPage)getPage()).getHtmlElementById(forr);
/* 183:286 */           elt.setEventHandler(event, scriptCode);
/* 184:    */         }
/* 185:    */         catch (ElementNotFoundException e)
/* 186:    */         {
/* 187:289 */           LOG.warn("<script for='" + forr + "' ...>: no element found with id \"" + forr + "\". Ignoring.");
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:294 */     else if ((forr == ATTRIBUTE_NOT_DEFINED) || ("onload".equals(event)))
/* 192:    */     {
/* 193:295 */       String url = getPage().getWebResponse().getWebRequest().getUrl().toExternalForm();
/* 194:296 */       int line1 = getStartLineNumber();
/* 195:297 */       int line2 = getEndLineNumber();
/* 196:298 */       int col1 = getStartColumnNumber();
/* 197:299 */       int col2 = getEndColumnNumber();
/* 198:300 */       String desc = "script in " + url + " from (" + line1 + ", " + col1 + ") to (" + line2 + ", " + col2 + ")";
/* 199:    */       
/* 200:302 */       ((HtmlPage)getPage()).executeJavaScriptIfPossible(scriptCode, desc, line1);
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   void executeScriptIfNeeded()
/* 205:    */   {
/* 206:313 */     if (!isExecutionNeeded()) {
/* 207:314 */       return;
/* 208:    */     }
/* 209:317 */     HtmlPage page = (HtmlPage)getPage();
/* 210:318 */     BrowserVersion browser = page.getWebClient().getBrowserVersion();
/* 211:    */     
/* 212:320 */     String src = getSrcAttribute();
/* 213:321 */     if (src.equals("//:")) {
/* 214:322 */       return;
/* 215:    */     }
/* 216:325 */     if (src != ATTRIBUTE_NOT_DEFINED)
/* 217:    */     {
/* 218:326 */       if (src.startsWith("javascript:"))
/* 219:    */       {
/* 220:328 */         if (browser.hasFeature(BrowserVersionFeatures.HTMLSCRIPT_SRC_JAVASCRIPT))
/* 221:    */         {
/* 222:329 */           String code = StringUtils.removeStart(src, "javascript:").trim();
/* 223:330 */           int len = code.length();
/* 224:331 */           if ((len > 2) && (
/* 225:332 */             ((code.charAt(0) == '\'') && (code.charAt(len - 1) == '\'')) || ((code.charAt(0) == '"') && (code.charAt(len - 1) == '"'))))
/* 226:    */           {
/* 227:334 */             code = code.substring(1, len - 1);
/* 228:335 */             if (LOG.isDebugEnabled()) {
/* 229:336 */               LOG.debug("Executing JavaScript: " + code);
/* 230:    */             }
/* 231:338 */             page.executeJavaScriptIfPossible(code, code, getStartLineNumber());
/* 232:    */           }
/* 233:    */         }
/* 234:    */       }
/* 235:    */       else
/* 236:    */       {
/* 237:345 */         if (LOG.isDebugEnabled()) {
/* 238:346 */           LOG.debug("Loading external JavaScript: " + src);
/* 239:    */         }
/* 240:    */         try
/* 241:    */         {
/* 242:349 */           HtmlPage.JavaScriptLoadResult result = page.loadExternalJavaScriptFile(src, getCharsetAttribute());
/* 243:350 */           if (result == HtmlPage.JavaScriptLoadResult.SUCCESS) {
/* 244:351 */             executeEventIfBrowserHasFeature("load", BrowserVersionFeatures.EVENT_ONLOAD_EXTERNAL_JAVASCRIPT);
/* 245:354 */           } else if (result == HtmlPage.JavaScriptLoadResult.DOWNLOAD_ERROR) {
/* 246:355 */             executeEventIfBrowserHasFeature("error", BrowserVersionFeatures.EVENT_ONERROR_EXTERNAL_JAVASCRIPT);
/* 247:    */           }
/* 248:    */         }
/* 249:    */         catch (FailingHttpStatusCodeException e)
/* 250:    */         {
/* 251:360 */           executeEventIfBrowserHasFeature("error", BrowserVersionFeatures.EVENT_ONERROR_EXTERNAL_JAVASCRIPT);
/* 252:    */           
/* 253:362 */           throw e;
/* 254:    */         }
/* 255:    */       }
/* 256:    */     }
/* 257:366 */     else if (getFirstChild() != null) {
/* 258:368 */       executeInlineScriptIfNeeded();
/* 259:    */     }
/* 260:    */   }
/* 261:    */   
/* 262:    */   private void executeEventIfBrowserHasFeature(String type, BrowserVersionFeatures feature)
/* 263:    */   {
/* 264:373 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(feature))
/* 265:    */     {
/* 266:374 */       HTMLScriptElement script = (HTMLScriptElement)getScriptObject();
/* 267:375 */       Event event = new Event(this, type);
/* 268:376 */       script.executeEvent(event);
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   private boolean isExecutionNeeded()
/* 273:    */   {
/* 274:386 */     SgmlPage page = getPage();
/* 275:388 */     if (!isDirectlyAttachedToPage()) {
/* 276:389 */       return false;
/* 277:    */     }
/* 278:393 */     if (!page.getWebClient().isJavaScriptEnabled()) {
/* 279:394 */       return false;
/* 280:    */     }
/* 281:398 */     if (((page instanceof HtmlPage)) && (((HtmlPage)page).isParsingHtmlSnippet())) {
/* 282:399 */       return false;
/* 283:    */     }
/* 284:403 */     for (DomNode o = this; o != null; o = o.getParentNode()) {
/* 285:404 */       if (((o instanceof HtmlInlineFrame)) || ((o instanceof HtmlNoFrames))) {
/* 286:405 */         return false;
/* 287:    */       }
/* 288:    */     }
/* 289:411 */     if ((page.getEnclosingWindow() != null) && (page.getEnclosingWindow().getEnclosedPage() != page)) {
/* 290:412 */       return false;
/* 291:    */     }
/* 292:416 */     if (!isJavaScript(getTypeAttribute(), getLanguageAttribute()))
/* 293:    */     {
/* 294:417 */       String t = getTypeAttribute();
/* 295:418 */       String l = getLanguageAttribute();
/* 296:419 */       LOG.warn("Script is not JavaScript (type: " + t + ", language: " + l + "). Skipping execution.");
/* 297:420 */       return false;
/* 298:    */     }
/* 299:425 */     DomNode root = this;
/* 300:426 */     while (root.getParentNode() != null) {
/* 301:427 */       root = root.getParentNode();
/* 302:    */     }
/* 303:429 */     if (root != getPage()) {
/* 304:430 */       return false;
/* 305:    */     }
/* 306:433 */     return true;
/* 307:    */   }
/* 308:    */   
/* 309:    */   boolean isJavaScript(String typeAttribute, String languageAttribute)
/* 310:    */   {
/* 311:447 */     if (StringUtils.isNotEmpty(typeAttribute))
/* 312:    */     {
/* 313:448 */       if (("text/javascript".equalsIgnoreCase(typeAttribute)) || ("text/ecmascript".equalsIgnoreCase(typeAttribute))) {
/* 314:450 */         return true;
/* 315:    */       }
/* 316:453 */       boolean appJavascriptSupported = getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTMLSCRIPT_APPLICATION_JAVASCRIPT);
/* 317:455 */       if ((appJavascriptSupported) && (("application/javascript".equalsIgnoreCase(typeAttribute)) || ("application/ecmascript".equalsIgnoreCase(typeAttribute)) || ("application/x-javascript".equalsIgnoreCase(typeAttribute)))) {
/* 318:459 */         return true;
/* 319:    */       }
/* 320:461 */       return false;
/* 321:    */     }
/* 322:464 */     if (StringUtils.isNotEmpty(languageAttribute)) {
/* 323:465 */       return StringUtils.startsWithIgnoreCase(languageAttribute, "javascript");
/* 324:    */     }
/* 325:467 */     return true;
/* 326:    */   }
/* 327:    */   
/* 328:    */   protected void setAndExecuteReadyState(String state)
/* 329:    */   {
/* 330:476 */     if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.EVENT_ONREADY_STATE_CHANGE))
/* 331:    */     {
/* 332:478 */       setReadyState(state);
/* 333:479 */       HTMLScriptElement script = (HTMLScriptElement)getScriptObject();
/* 334:480 */       Event event = new Event(this, "readystatechange");
/* 335:481 */       script.executeEvent(event);
/* 336:    */     }
/* 337:    */   }
/* 338:    */   
/* 339:    */   public String asText()
/* 340:    */   {
/* 341:492 */     return "";
/* 342:    */   }
/* 343:    */   
/* 344:    */   protected boolean isEmptyXmlTagExpanded()
/* 345:    */   {
/* 346:502 */     return true;
/* 347:    */   }
/* 348:    */   
/* 349:    */   protected void printChildrenAsXml(String indent, PrintWriter printWriter)
/* 350:    */   {
/* 351:510 */     DomCharacterData textNode = (DomCharacterData)getFirstChild();
/* 352:511 */     if (textNode != null)
/* 353:    */     {
/* 354:512 */       printWriter.println("//<![CDATA[");
/* 355:513 */       printWriter.println(textNode.getData());
/* 356:514 */       printWriter.println("//]]>");
/* 357:    */     }
/* 358:    */   }
/* 359:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlScript
 * JD-Core Version:    0.7.0.1
 */