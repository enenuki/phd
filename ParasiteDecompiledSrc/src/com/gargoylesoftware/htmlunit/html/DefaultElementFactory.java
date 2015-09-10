/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   6:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.xml.sax.Attributes;
/*  11:    */ 
/*  12:    */ class DefaultElementFactory
/*  13:    */   implements IElementFactory
/*  14:    */ {
/*  15: 42 */   static final List<String> SUPPORTED_TAGS_ = Arrays.asList(new String[] { "abbr", "acronym", "a", "address", "applet", "area", "audio", "bgsound", "base", "basefont", "bdo", "big", "blink", "blockquote", "body", "b", "br", "button", "canvas", "caption", "center", "cite", "code", "dfn", "dd", "del", "dir", "div", "dl", "dt", "embed", "em", "fieldset", "font", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "hr", "html", "iframe", "q", "img", "ins", "isindex", "i", "kbd", "label", "legend", "listing", "li", "link", "map", "marquee", "menu", "meta", "multicol", "nobr", "noembed", "noframes", "noscript", "object", "ol", "optgroup", "option", "p", "param", "plaintext", "pre", "s", "samp", "script", "select", "small", "source", "spacer", "span", "strike", "strong", "style", "sub", "sup", "table", "col", "colgroup", "tbody", "td", "th", "tr", "textarea", "tfoot", "thead", "tt", "title", "u", "ul", "var", "video", "wbr", "xmp" });
/*  16:    */   
/*  17:    */   public HtmlElement createElement(SgmlPage page, String tagName, Attributes attributes)
/*  18:    */   {
/*  19: 91 */     return createElementNS(page, null, tagName, attributes);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public HtmlElement createElementNS(SgmlPage page, String namespaceURI, String qualifiedName, Attributes attributes)
/*  23:    */   {
/*  24:103 */     Map<String, DomAttr> attributeMap = setAttributes(page, attributes);
/*  25:    */     
/*  26:    */ 
/*  27:    */ 
/*  28:107 */     int colonIndex = qualifiedName.indexOf(':');
/*  29:    */     String tagName;
/*  30:    */     String tagName;
/*  31:108 */     if (colonIndex == -1) {
/*  32:109 */       tagName = qualifiedName.toLowerCase();
/*  33:    */     } else {
/*  34:112 */       tagName = qualifiedName.substring(colonIndex + 1).toLowerCase();
/*  35:    */     }
/*  36:    */     HtmlElement element;
/*  37:127 */     if (tagName.equals("abbr"))
/*  38:    */     {
/*  39:128 */       element = new HtmlAbbreviated(namespaceURI, qualifiedName, page, attributeMap);
/*  40:    */     }
/*  41:    */     else
/*  42:    */     {
/*  43:    */       HtmlElement element;
/*  44:130 */       if (tagName.equals("acronym"))
/*  45:    */       {
/*  46:131 */         element = new HtmlAcronym(namespaceURI, qualifiedName, page, attributeMap);
/*  47:    */       }
/*  48:    */       else
/*  49:    */       {
/*  50:    */         HtmlElement element;
/*  51:133 */         if (tagName.equals("address"))
/*  52:    */         {
/*  53:134 */           element = new HtmlAddress(namespaceURI, qualifiedName, page, attributeMap);
/*  54:    */         }
/*  55:    */         else
/*  56:    */         {
/*  57:    */           HtmlElement element;
/*  58:136 */           if (tagName.equals("a"))
/*  59:    */           {
/*  60:137 */             element = new HtmlAnchor(namespaceURI, qualifiedName, page, attributeMap);
/*  61:    */           }
/*  62:    */           else
/*  63:    */           {
/*  64:    */             HtmlElement element;
/*  65:139 */             if (tagName.equals("applet"))
/*  66:    */             {
/*  67:140 */               element = new HtmlApplet(namespaceURI, qualifiedName, page, attributeMap);
/*  68:    */             }
/*  69:    */             else
/*  70:    */             {
/*  71:    */               HtmlElement element;
/*  72:142 */               if (tagName.equals("area"))
/*  73:    */               {
/*  74:143 */                 element = new HtmlArea(namespaceURI, qualifiedName, page, attributeMap);
/*  75:    */               }
/*  76:145 */               else if (tagName.equals("audio"))
/*  77:    */               {
/*  78:    */                 HtmlElement element;
/*  79:146 */                 if (page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTML5_TAGS)) {
/*  80:147 */                   element = new HtmlAudio(namespaceURI, qualifiedName, page, attributeMap);
/*  81:    */                 } else {
/*  82:150 */                   return UnknownElementFactory.instance.createElementNS(page, namespaceURI, qualifiedName, attributes);
/*  83:    */                 }
/*  84:    */               }
/*  85:    */               else
/*  86:    */               {
/*  87:    */                 HtmlElement element;
/*  88:153 */                 if (tagName.equals("bgsound"))
/*  89:    */                 {
/*  90:154 */                   element = new HtmlBackgroundSound(namespaceURI, qualifiedName, page, attributeMap);
/*  91:    */                 }
/*  92:    */                 else
/*  93:    */                 {
/*  94:    */                   HtmlElement element;
/*  95:156 */                   if (tagName.equals("base"))
/*  96:    */                   {
/*  97:157 */                     element = new HtmlBase(namespaceURI, qualifiedName, page, attributeMap);
/*  98:    */                   }
/*  99:    */                   else
/* 100:    */                   {
/* 101:    */                     HtmlElement element;
/* 102:159 */                     if (tagName.equals("basefont"))
/* 103:    */                     {
/* 104:160 */                       element = new HtmlBaseFont(namespaceURI, qualifiedName, page, attributeMap);
/* 105:    */                     }
/* 106:    */                     else
/* 107:    */                     {
/* 108:    */                       HtmlElement element;
/* 109:162 */                       if (tagName.equals("bdo"))
/* 110:    */                       {
/* 111:163 */                         element = new HtmlBidirectionalOverride(namespaceURI, qualifiedName, page, attributeMap);
/* 112:    */                       }
/* 113:    */                       else
/* 114:    */                       {
/* 115:    */                         HtmlElement element;
/* 116:165 */                         if (tagName.equals("big"))
/* 117:    */                         {
/* 118:166 */                           element = new HtmlBig(namespaceURI, qualifiedName, page, attributeMap);
/* 119:    */                         }
/* 120:    */                         else
/* 121:    */                         {
/* 122:    */                           HtmlElement element;
/* 123:168 */                           if (tagName.equals("blink"))
/* 124:    */                           {
/* 125:169 */                             element = new HtmlBlink(namespaceURI, qualifiedName, page, attributeMap);
/* 126:    */                           }
/* 127:    */                           else
/* 128:    */                           {
/* 129:    */                             HtmlElement element;
/* 130:171 */                             if (tagName.equals("blockquote"))
/* 131:    */                             {
/* 132:172 */                               element = new HtmlBlockQuote(namespaceURI, qualifiedName, page, attributeMap);
/* 133:    */                             }
/* 134:    */                             else
/* 135:    */                             {
/* 136:    */                               HtmlElement element;
/* 137:174 */                               if (tagName.equals("body"))
/* 138:    */                               {
/* 139:175 */                                 element = new HtmlBody(namespaceURI, qualifiedName, page, attributeMap, false);
/* 140:    */                               }
/* 141:    */                               else
/* 142:    */                               {
/* 143:    */                                 HtmlElement element;
/* 144:177 */                                 if (tagName.equals("b"))
/* 145:    */                                 {
/* 146:178 */                                   element = new HtmlBold(namespaceURI, qualifiedName, page, attributeMap);
/* 147:    */                                 }
/* 148:    */                                 else
/* 149:    */                                 {
/* 150:    */                                   HtmlElement element;
/* 151:180 */                                   if (tagName.equals("br"))
/* 152:    */                                   {
/* 153:181 */                                     element = new HtmlBreak(namespaceURI, qualifiedName, page, attributeMap);
/* 154:    */                                   }
/* 155:    */                                   else
/* 156:    */                                   {
/* 157:    */                                     HtmlElement element;
/* 158:183 */                                     if (tagName.equals("button"))
/* 159:    */                                     {
/* 160:184 */                                       element = new HtmlButton(namespaceURI, qualifiedName, page, attributeMap);
/* 161:    */                                     }
/* 162:186 */                                     else if (tagName.equals("canvas"))
/* 163:    */                                     {
/* 164:    */                                       HtmlElement element;
/* 165:187 */                                       if (page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.CANVAS)) {
/* 166:188 */                                         element = new HtmlCanvas(namespaceURI, qualifiedName, page, attributeMap);
/* 167:    */                                       } else {
/* 168:191 */                                         return UnknownElementFactory.instance.createElementNS(page, namespaceURI, qualifiedName, attributes);
/* 169:    */                                       }
/* 170:    */                                     }
/* 171:    */                                     else
/* 172:    */                                     {
/* 173:    */                                       HtmlElement element;
/* 174:194 */                                       if (tagName.equals("caption"))
/* 175:    */                                       {
/* 176:195 */                                         element = new HtmlCaption(namespaceURI, qualifiedName, page, attributeMap);
/* 177:    */                                       }
/* 178:    */                                       else
/* 179:    */                                       {
/* 180:    */                                         HtmlElement element;
/* 181:197 */                                         if (tagName.equals("center"))
/* 182:    */                                         {
/* 183:198 */                                           element = new HtmlCenter(namespaceURI, qualifiedName, page, attributeMap);
/* 184:    */                                         }
/* 185:    */                                         else
/* 186:    */                                         {
/* 187:    */                                           HtmlElement element;
/* 188:200 */                                           if (tagName.equals("cite"))
/* 189:    */                                           {
/* 190:201 */                                             element = new HtmlCitation(namespaceURI, qualifiedName, page, attributeMap);
/* 191:    */                                           }
/* 192:    */                                           else
/* 193:    */                                           {
/* 194:    */                                             HtmlElement element;
/* 195:203 */                                             if (tagName.equals("code"))
/* 196:    */                                             {
/* 197:204 */                                               element = new HtmlCode(namespaceURI, qualifiedName, page, attributeMap);
/* 198:    */                                             }
/* 199:    */                                             else
/* 200:    */                                             {
/* 201:    */                                               HtmlElement element;
/* 202:206 */                                               if (tagName.equals("dfn"))
/* 203:    */                                               {
/* 204:207 */                                                 element = new HtmlDefinition(namespaceURI, qualifiedName, page, attributeMap);
/* 205:    */                                               }
/* 206:    */                                               else
/* 207:    */                                               {
/* 208:    */                                                 HtmlElement element;
/* 209:209 */                                                 if (tagName.equals("dd"))
/* 210:    */                                                 {
/* 211:210 */                                                   element = new HtmlDefinitionDescription(namespaceURI, qualifiedName, page, attributeMap);
/* 212:    */                                                 }
/* 213:    */                                                 else
/* 214:    */                                                 {
/* 215:    */                                                   HtmlElement element;
/* 216:212 */                                                   if (tagName.equals("dl"))
/* 217:    */                                                   {
/* 218:213 */                                                     element = new HtmlDefinitionList(namespaceURI, qualifiedName, page, attributeMap);
/* 219:    */                                                   }
/* 220:    */                                                   else
/* 221:    */                                                   {
/* 222:    */                                                     HtmlElement element;
/* 223:215 */                                                     if (tagName.equals("dt"))
/* 224:    */                                                     {
/* 225:216 */                                                       element = new HtmlDefinitionTerm(namespaceURI, qualifiedName, page, attributeMap);
/* 226:    */                                                     }
/* 227:    */                                                     else
/* 228:    */                                                     {
/* 229:    */                                                       HtmlElement element;
/* 230:218 */                                                       if (tagName.equals("del"))
/* 231:    */                                                       {
/* 232:219 */                                                         element = new HtmlDeletedText(namespaceURI, qualifiedName, page, attributeMap);
/* 233:    */                                                       }
/* 234:    */                                                       else
/* 235:    */                                                       {
/* 236:    */                                                         HtmlElement element;
/* 237:221 */                                                         if (tagName.equals("div"))
/* 238:    */                                                         {
/* 239:222 */                                                           element = new HtmlDivision(namespaceURI, qualifiedName, page, attributeMap);
/* 240:    */                                                         }
/* 241:    */                                                         else
/* 242:    */                                                         {
/* 243:    */                                                           HtmlElement element;
/* 244:224 */                                                           if (tagName.equals("embed"))
/* 245:    */                                                           {
/* 246:225 */                                                             element = new HtmlEmbed(namespaceURI, qualifiedName, page, attributeMap);
/* 247:    */                                                           }
/* 248:    */                                                           else
/* 249:    */                                                           {
/* 250:    */                                                             HtmlElement element;
/* 251:227 */                                                             if (tagName.equals("em"))
/* 252:    */                                                             {
/* 253:228 */                                                               element = new HtmlEmphasis(namespaceURI, qualifiedName, page, attributeMap);
/* 254:    */                                                             }
/* 255:    */                                                             else
/* 256:    */                                                             {
/* 257:    */                                                               HtmlElement element;
/* 258:230 */                                                               if (tagName.equals("fieldset"))
/* 259:    */                                                               {
/* 260:231 */                                                                 element = new HtmlFieldSet(namespaceURI, qualifiedName, page, attributeMap);
/* 261:    */                                                               }
/* 262:    */                                                               else
/* 263:    */                                                               {
/* 264:    */                                                                 HtmlElement element;
/* 265:233 */                                                                 if (tagName.equals("font"))
/* 266:    */                                                                 {
/* 267:234 */                                                                   element = new HtmlFont(namespaceURI, qualifiedName, page, attributeMap);
/* 268:    */                                                                 }
/* 269:    */                                                                 else
/* 270:    */                                                                 {
/* 271:    */                                                                   HtmlElement element;
/* 272:236 */                                                                   if (tagName.equals("form"))
/* 273:    */                                                                   {
/* 274:237 */                                                                     element = new HtmlForm(namespaceURI, qualifiedName, page, attributeMap);
/* 275:    */                                                                   }
/* 276:    */                                                                   else
/* 277:    */                                                                   {
/* 278:    */                                                                     HtmlElement element;
/* 279:239 */                                                                     if (tagName.equals("frame"))
/* 280:    */                                                                     {
/* 281:240 */                                                                       if (attributeMap != null)
/* 282:    */                                                                       {
/* 283:241 */                                                                         DomAttr srcAttribute = (DomAttr)attributeMap.get("src");
/* 284:242 */                                                                         if (srcAttribute != null) {
/* 285:243 */                                                                           srcAttribute.setValue(srcAttribute.getValue().trim());
/* 286:    */                                                                         }
/* 287:    */                                                                       }
/* 288:246 */                                                                       element = new HtmlFrame(namespaceURI, qualifiedName, page, attributeMap);
/* 289:    */                                                                     }
/* 290:    */                                                                     else
/* 291:    */                                                                     {
/* 292:    */                                                                       HtmlElement element;
/* 293:248 */                                                                       if (tagName.equals("frameset"))
/* 294:    */                                                                       {
/* 295:249 */                                                                         element = new HtmlFrameSet(namespaceURI, qualifiedName, page, attributeMap);
/* 296:    */                                                                       }
/* 297:    */                                                                       else
/* 298:    */                                                                       {
/* 299:    */                                                                         HtmlElement element;
/* 300:251 */                                                                         if (tagName.equals("head"))
/* 301:    */                                                                         {
/* 302:252 */                                                                           element = new HtmlHead(namespaceURI, qualifiedName, page, attributeMap);
/* 303:    */                                                                         }
/* 304:    */                                                                         else
/* 305:    */                                                                         {
/* 306:    */                                                                           HtmlElement element;
/* 307:254 */                                                                           if (tagName.equals("h1"))
/* 308:    */                                                                           {
/* 309:255 */                                                                             element = new HtmlHeading1(namespaceURI, qualifiedName, page, attributeMap);
/* 310:    */                                                                           }
/* 311:    */                                                                           else
/* 312:    */                                                                           {
/* 313:    */                                                                             HtmlElement element;
/* 314:257 */                                                                             if (tagName.equals("h2"))
/* 315:    */                                                                             {
/* 316:258 */                                                                               element = new HtmlHeading2(namespaceURI, qualifiedName, page, attributeMap);
/* 317:    */                                                                             }
/* 318:    */                                                                             else
/* 319:    */                                                                             {
/* 320:    */                                                                               HtmlElement element;
/* 321:260 */                                                                               if (tagName.equals("h3"))
/* 322:    */                                                                               {
/* 323:261 */                                                                                 element = new HtmlHeading3(namespaceURI, qualifiedName, page, attributeMap);
/* 324:    */                                                                               }
/* 325:    */                                                                               else
/* 326:    */                                                                               {
/* 327:    */                                                                                 HtmlElement element;
/* 328:263 */                                                                                 if (tagName.equals("h4"))
/* 329:    */                                                                                 {
/* 330:264 */                                                                                   element = new HtmlHeading4(namespaceURI, qualifiedName, page, attributeMap);
/* 331:    */                                                                                 }
/* 332:    */                                                                                 else
/* 333:    */                                                                                 {
/* 334:    */                                                                                   HtmlElement element;
/* 335:266 */                                                                                   if (tagName.equals("h5"))
/* 336:    */                                                                                   {
/* 337:267 */                                                                                     element = new HtmlHeading5(namespaceURI, qualifiedName, page, attributeMap);
/* 338:    */                                                                                   }
/* 339:    */                                                                                   else
/* 340:    */                                                                                   {
/* 341:    */                                                                                     HtmlElement element;
/* 342:269 */                                                                                     if (tagName.equals("h6"))
/* 343:    */                                                                                     {
/* 344:270 */                                                                                       element = new HtmlHeading6(namespaceURI, qualifiedName, page, attributeMap);
/* 345:    */                                                                                     }
/* 346:    */                                                                                     else
/* 347:    */                                                                                     {
/* 348:    */                                                                                       HtmlElement element;
/* 349:272 */                                                                                       if (tagName.equals("hr"))
/* 350:    */                                                                                       {
/* 351:273 */                                                                                         element = new HtmlHorizontalRule(namespaceURI, qualifiedName, page, attributeMap);
/* 352:    */                                                                                       }
/* 353:    */                                                                                       else
/* 354:    */                                                                                       {
/* 355:    */                                                                                         HtmlElement element;
/* 356:275 */                                                                                         if (tagName.equals("html"))
/* 357:    */                                                                                         {
/* 358:276 */                                                                                           element = new HtmlHtml(namespaceURI, qualifiedName, page, attributeMap);
/* 359:    */                                                                                         }
/* 360:    */                                                                                         else
/* 361:    */                                                                                         {
/* 362:    */                                                                                           HtmlElement element;
/* 363:278 */                                                                                           if (tagName.equals("img"))
/* 364:    */                                                                                           {
/* 365:279 */                                                                                             element = new HtmlImage(namespaceURI, qualifiedName, page, attributeMap);
/* 366:    */                                                                                           }
/* 367:    */                                                                                           else
/* 368:    */                                                                                           {
/* 369:    */                                                                                             HtmlElement element;
/* 370:281 */                                                                                             if (tagName.equals("iframe"))
/* 371:    */                                                                                             {
/* 372:282 */                                                                                               if (attributeMap != null)
/* 373:    */                                                                                               {
/* 374:283 */                                                                                                 DomAttr srcAttribute = (DomAttr)attributeMap.get("src");
/* 375:284 */                                                                                                 if (srcAttribute != null) {
/* 376:285 */                                                                                                   srcAttribute.setValue(srcAttribute.getValue().trim());
/* 377:    */                                                                                                 }
/* 378:    */                                                                                               }
/* 379:288 */                                                                                               element = new HtmlInlineFrame(namespaceURI, qualifiedName, page, attributeMap);
/* 380:    */                                                                                             }
/* 381:    */                                                                                             else
/* 382:    */                                                                                             {
/* 383:    */                                                                                               HtmlElement element;
/* 384:290 */                                                                                               if (tagName.equals("q"))
/* 385:    */                                                                                               {
/* 386:291 */                                                                                                 element = new HtmlInlineQuotation(namespaceURI, qualifiedName, page, attributeMap);
/* 387:    */                                                                                               }
/* 388:    */                                                                                               else
/* 389:    */                                                                                               {
/* 390:    */                                                                                                 HtmlElement element;
/* 391:293 */                                                                                                 if (tagName.equals("ins"))
/* 392:    */                                                                                                 {
/* 393:294 */                                                                                                   element = new HtmlInsertedText(namespaceURI, qualifiedName, page, attributeMap);
/* 394:    */                                                                                                 }
/* 395:    */                                                                                                 else
/* 396:    */                                                                                                 {
/* 397:    */                                                                                                   HtmlElement element;
/* 398:296 */                                                                                                   if (tagName.equals("isindex"))
/* 399:    */                                                                                                   {
/* 400:297 */                                                                                                     element = new HtmlIsIndex(namespaceURI, qualifiedName, page, attributeMap);
/* 401:    */                                                                                                   }
/* 402:    */                                                                                                   else
/* 403:    */                                                                                                   {
/* 404:    */                                                                                                     HtmlElement element;
/* 405:299 */                                                                                                     if (tagName.equals("i"))
/* 406:    */                                                                                                     {
/* 407:300 */                                                                                                       element = new HtmlItalic(namespaceURI, qualifiedName, page, attributeMap);
/* 408:    */                                                                                                     }
/* 409:    */                                                                                                     else
/* 410:    */                                                                                                     {
/* 411:    */                                                                                                       HtmlElement element;
/* 412:302 */                                                                                                       if (tagName.equals("kbd"))
/* 413:    */                                                                                                       {
/* 414:303 */                                                                                                         element = new HtmlKeyboard(namespaceURI, qualifiedName, page, attributeMap);
/* 415:    */                                                                                                       }
/* 416:    */                                                                                                       else
/* 417:    */                                                                                                       {
/* 418:    */                                                                                                         HtmlElement element;
/* 419:305 */                                                                                                         if (tagName.equals("label"))
/* 420:    */                                                                                                         {
/* 421:306 */                                                                                                           element = new HtmlLabel(namespaceURI, qualifiedName, page, attributeMap);
/* 422:    */                                                                                                         }
/* 423:    */                                                                                                         else
/* 424:    */                                                                                                         {
/* 425:    */                                                                                                           HtmlElement element;
/* 426:308 */                                                                                                           if (tagName.equals("legend"))
/* 427:    */                                                                                                           {
/* 428:309 */                                                                                                             element = new HtmlLegend(namespaceURI, qualifiedName, page, attributeMap);
/* 429:    */                                                                                                           }
/* 430:    */                                                                                                           else
/* 431:    */                                                                                                           {
/* 432:    */                                                                                                             HtmlElement element;
/* 433:311 */                                                                                                             if (tagName.equals("link"))
/* 434:    */                                                                                                             {
/* 435:312 */                                                                                                               element = new HtmlLink(namespaceURI, qualifiedName, page, attributeMap);
/* 436:    */                                                                                                             }
/* 437:    */                                                                                                             else
/* 438:    */                                                                                                             {
/* 439:    */                                                                                                               HtmlElement element;
/* 440:314 */                                                                                                               if (tagName.equals("listing"))
/* 441:    */                                                                                                               {
/* 442:315 */                                                                                                                 element = new HtmlListing(namespaceURI, qualifiedName, page, attributeMap);
/* 443:    */                                                                                                               }
/* 444:    */                                                                                                               else
/* 445:    */                                                                                                               {
/* 446:    */                                                                                                                 HtmlElement element;
/* 447:317 */                                                                                                                 if (tagName.equals("li"))
/* 448:    */                                                                                                                 {
/* 449:318 */                                                                                                                   element = new HtmlListItem(namespaceURI, qualifiedName, page, attributeMap);
/* 450:    */                                                                                                                 }
/* 451:    */                                                                                                                 else
/* 452:    */                                                                                                                 {
/* 453:    */                                                                                                                   HtmlElement element;
/* 454:320 */                                                                                                                   if (tagName.equals("map"))
/* 455:    */                                                                                                                   {
/* 456:321 */                                                                                                                     element = new HtmlMap(namespaceURI, qualifiedName, page, attributeMap);
/* 457:    */                                                                                                                   }
/* 458:    */                                                                                                                   else
/* 459:    */                                                                                                                   {
/* 460:    */                                                                                                                     HtmlElement element;
/* 461:323 */                                                                                                                     if (tagName.equals("marquee"))
/* 462:    */                                                                                                                     {
/* 463:324 */                                                                                                                       element = new HtmlMarquee(namespaceURI, qualifiedName, page, attributeMap);
/* 464:    */                                                                                                                     }
/* 465:    */                                                                                                                     else
/* 466:    */                                                                                                                     {
/* 467:    */                                                                                                                       HtmlElement element;
/* 468:326 */                                                                                                                       if (tagName.equals("menu"))
/* 469:    */                                                                                                                       {
/* 470:327 */                                                                                                                         element = new HtmlMenu(namespaceURI, qualifiedName, page, attributeMap);
/* 471:    */                                                                                                                       }
/* 472:    */                                                                                                                       else
/* 473:    */                                                                                                                       {
/* 474:    */                                                                                                                         HtmlElement element;
/* 475:329 */                                                                                                                         if (tagName.equals("meta"))
/* 476:    */                                                                                                                         {
/* 477:330 */                                                                                                                           element = new HtmlMeta(namespaceURI, qualifiedName, page, attributeMap);
/* 478:    */                                                                                                                         }
/* 479:    */                                                                                                                         else
/* 480:    */                                                                                                                         {
/* 481:    */                                                                                                                           HtmlElement element;
/* 482:332 */                                                                                                                           if (tagName.equals("multicol"))
/* 483:    */                                                                                                                           {
/* 484:333 */                                                                                                                             element = new HtmlMultiColumn(namespaceURI, qualifiedName, page, attributeMap);
/* 485:    */                                                                                                                           }
/* 486:    */                                                                                                                           else
/* 487:    */                                                                                                                           {
/* 488:    */                                                                                                                             HtmlElement element;
/* 489:335 */                                                                                                                             if (tagName.equals("nobr"))
/* 490:    */                                                                                                                             {
/* 491:336 */                                                                                                                               element = new HtmlNoBreak(namespaceURI, qualifiedName, page, attributeMap);
/* 492:    */                                                                                                                             }
/* 493:    */                                                                                                                             else
/* 494:    */                                                                                                                             {
/* 495:    */                                                                                                                               HtmlElement element;
/* 496:338 */                                                                                                                               if (tagName.equals("noembed"))
/* 497:    */                                                                                                                               {
/* 498:339 */                                                                                                                                 element = new HtmlNoEmbed(namespaceURI, qualifiedName, page, attributeMap);
/* 499:    */                                                                                                                               }
/* 500:    */                                                                                                                               else
/* 501:    */                                                                                                                               {
/* 502:    */                                                                                                                                 HtmlElement element;
/* 503:341 */                                                                                                                                 if (tagName.equals("noframes"))
/* 504:    */                                                                                                                                 {
/* 505:342 */                                                                                                                                   element = new HtmlNoFrames(namespaceURI, qualifiedName, page, attributeMap);
/* 506:    */                                                                                                                                 }
/* 507:    */                                                                                                                                 else
/* 508:    */                                                                                                                                 {
/* 509:    */                                                                                                                                   HtmlElement element;
/* 510:344 */                                                                                                                                   if (tagName.equals("noscript"))
/* 511:    */                                                                                                                                   {
/* 512:345 */                                                                                                                                     element = new HtmlNoScript(namespaceURI, qualifiedName, page, attributeMap);
/* 513:    */                                                                                                                                   }
/* 514:    */                                                                                                                                   else
/* 515:    */                                                                                                                                   {
/* 516:    */                                                                                                                                     HtmlElement element;
/* 517:347 */                                                                                                                                     if (tagName.equals("object"))
/* 518:    */                                                                                                                                     {
/* 519:348 */                                                                                                                                       element = new HtmlObject(namespaceURI, qualifiedName, page, attributeMap);
/* 520:    */                                                                                                                                     }
/* 521:    */                                                                                                                                     else
/* 522:    */                                                                                                                                     {
/* 523:    */                                                                                                                                       HtmlElement element;
/* 524:350 */                                                                                                                                       if (tagName.equals("option"))
/* 525:    */                                                                                                                                       {
/* 526:351 */                                                                                                                                         element = new HtmlOption(namespaceURI, qualifiedName, page, attributeMap);
/* 527:    */                                                                                                                                       }
/* 528:    */                                                                                                                                       else
/* 529:    */                                                                                                                                       {
/* 530:    */                                                                                                                                         HtmlElement element;
/* 531:353 */                                                                                                                                         if (tagName.equals("optgroup"))
/* 532:    */                                                                                                                                         {
/* 533:354 */                                                                                                                                           element = new HtmlOptionGroup(namespaceURI, qualifiedName, page, attributeMap);
/* 534:    */                                                                                                                                         }
/* 535:    */                                                                                                                                         else
/* 536:    */                                                                                                                                         {
/* 537:    */                                                                                                                                           HtmlElement element;
/* 538:356 */                                                                                                                                           if (tagName.equals("ol"))
/* 539:    */                                                                                                                                           {
/* 540:357 */                                                                                                                                             element = new HtmlOrderedList(namespaceURI, qualifiedName, page, attributeMap);
/* 541:    */                                                                                                                                           }
/* 542:    */                                                                                                                                           else
/* 543:    */                                                                                                                                           {
/* 544:    */                                                                                                                                             HtmlElement element;
/* 545:359 */                                                                                                                                             if (tagName.equals("p"))
/* 546:    */                                                                                                                                             {
/* 547:360 */                                                                                                                                               element = new HtmlParagraph(namespaceURI, qualifiedName, page, attributeMap);
/* 548:    */                                                                                                                                             }
/* 549:    */                                                                                                                                             else
/* 550:    */                                                                                                                                             {
/* 551:    */                                                                                                                                               HtmlElement element;
/* 552:362 */                                                                                                                                               if (tagName.equals("param"))
/* 553:    */                                                                                                                                               {
/* 554:363 */                                                                                                                                                 element = new HtmlParameter(namespaceURI, qualifiedName, page, attributeMap);
/* 555:    */                                                                                                                                               }
/* 556:    */                                                                                                                                               else
/* 557:    */                                                                                                                                               {
/* 558:    */                                                                                                                                                 HtmlElement element;
/* 559:365 */                                                                                                                                                 if (tagName.equals("plaintext"))
/* 560:    */                                                                                                                                                 {
/* 561:366 */                                                                                                                                                   element = new HtmlPlainText(namespaceURI, qualifiedName, page, attributeMap);
/* 562:    */                                                                                                                                                 }
/* 563:    */                                                                                                                                                 else
/* 564:    */                                                                                                                                                 {
/* 565:    */                                                                                                                                                   HtmlElement element;
/* 566:368 */                                                                                                                                                   if (tagName.equals("pre"))
/* 567:    */                                                                                                                                                   {
/* 568:369 */                                                                                                                                                     element = new HtmlPreformattedText(namespaceURI, qualifiedName, page, attributeMap);
/* 569:    */                                                                                                                                                   }
/* 570:    */                                                                                                                                                   else
/* 571:    */                                                                                                                                                   {
/* 572:    */                                                                                                                                                     HtmlElement element;
/* 573:371 */                                                                                                                                                     if (tagName.equals("s"))
/* 574:    */                                                                                                                                                     {
/* 575:372 */                                                                                                                                                       element = new HtmlS(namespaceURI, qualifiedName, page, attributeMap);
/* 576:    */                                                                                                                                                     }
/* 577:    */                                                                                                                                                     else
/* 578:    */                                                                                                                                                     {
/* 579:    */                                                                                                                                                       HtmlElement element;
/* 580:374 */                                                                                                                                                       if (tagName.equals("samp"))
/* 581:    */                                                                                                                                                       {
/* 582:375 */                                                                                                                                                         element = new HtmlSample(namespaceURI, qualifiedName, page, attributeMap);
/* 583:    */                                                                                                                                                       }
/* 584:    */                                                                                                                                                       else
/* 585:    */                                                                                                                                                       {
/* 586:    */                                                                                                                                                         HtmlElement element;
/* 587:377 */                                                                                                                                                         if (tagName.equals("script"))
/* 588:    */                                                                                                                                                         {
/* 589:378 */                                                                                                                                                           element = new HtmlScript(namespaceURI, qualifiedName, page, attributeMap);
/* 590:    */                                                                                                                                                         }
/* 591:    */                                                                                                                                                         else
/* 592:    */                                                                                                                                                         {
/* 593:    */                                                                                                                                                           HtmlElement element;
/* 594:380 */                                                                                                                                                           if (tagName.equals("select"))
/* 595:    */                                                                                                                                                           {
/* 596:381 */                                                                                                                                                             element = new HtmlSelect(namespaceURI, qualifiedName, page, attributeMap);
/* 597:    */                                                                                                                                                           }
/* 598:    */                                                                                                                                                           else
/* 599:    */                                                                                                                                                           {
/* 600:    */                                                                                                                                                             HtmlElement element;
/* 601:383 */                                                                                                                                                             if (tagName.equals("small"))
/* 602:    */                                                                                                                                                             {
/* 603:384 */                                                                                                                                                               element = new HtmlSmall(namespaceURI, qualifiedName, page, attributeMap);
/* 604:    */                                                                                                                                                             }
/* 605:386 */                                                                                                                                                             else if (tagName.equals("source"))
/* 606:    */                                                                                                                                                             {
/* 607:    */                                                                                                                                                               HtmlElement element;
/* 608:387 */                                                                                                                                                               if (page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTML5_TAGS)) {
/* 609:388 */                                                                                                                                                                 element = new HtmlSource(namespaceURI, qualifiedName, page, attributeMap);
/* 610:    */                                                                                                                                                               } else {
/* 611:391 */                                                                                                                                                                 return UnknownElementFactory.instance.createElementNS(page, namespaceURI, qualifiedName, attributes);
/* 612:    */                                                                                                                                                               }
/* 613:    */                                                                                                                                                             }
/* 614:    */                                                                                                                                                             else
/* 615:    */                                                                                                                                                             {
/* 616:    */                                                                                                                                                               HtmlElement element;
/* 617:394 */                                                                                                                                                               if (tagName.equals("spacer"))
/* 618:    */                                                                                                                                                               {
/* 619:395 */                                                                                                                                                                 element = new HtmlSpacer(namespaceURI, qualifiedName, page, attributeMap);
/* 620:    */                                                                                                                                                               }
/* 621:    */                                                                                                                                                               else
/* 622:    */                                                                                                                                                               {
/* 623:    */                                                                                                                                                                 HtmlElement element;
/* 624:397 */                                                                                                                                                                 if (tagName.equals("span"))
/* 625:    */                                                                                                                                                                 {
/* 626:398 */                                                                                                                                                                   element = new HtmlSpan(namespaceURI, qualifiedName, page, attributeMap);
/* 627:    */                                                                                                                                                                 }
/* 628:    */                                                                                                                                                                 else
/* 629:    */                                                                                                                                                                 {
/* 630:    */                                                                                                                                                                   HtmlElement element;
/* 631:400 */                                                                                                                                                                   if (tagName.equals("strike"))
/* 632:    */                                                                                                                                                                   {
/* 633:401 */                                                                                                                                                                     element = new HtmlStrike(namespaceURI, qualifiedName, page, attributeMap);
/* 634:    */                                                                                                                                                                   }
/* 635:    */                                                                                                                                                                   else
/* 636:    */                                                                                                                                                                   {
/* 637:    */                                                                                                                                                                     HtmlElement element;
/* 638:403 */                                                                                                                                                                     if (tagName.equals("strong"))
/* 639:    */                                                                                                                                                                     {
/* 640:404 */                                                                                                                                                                       element = new HtmlStrong(namespaceURI, qualifiedName, page, attributeMap);
/* 641:    */                                                                                                                                                                     }
/* 642:    */                                                                                                                                                                     else
/* 643:    */                                                                                                                                                                     {
/* 644:    */                                                                                                                                                                       HtmlElement element;
/* 645:406 */                                                                                                                                                                       if (tagName.equals("style"))
/* 646:    */                                                                                                                                                                       {
/* 647:407 */                                                                                                                                                                         element = new HtmlStyle(namespaceURI, qualifiedName, page, attributeMap);
/* 648:    */                                                                                                                                                                       }
/* 649:    */                                                                                                                                                                       else
/* 650:    */                                                                                                                                                                       {
/* 651:    */                                                                                                                                                                         HtmlElement element;
/* 652:409 */                                                                                                                                                                         if (tagName.equals("sub"))
/* 653:    */                                                                                                                                                                         {
/* 654:410 */                                                                                                                                                                           element = new HtmlSubscript(namespaceURI, qualifiedName, page, attributeMap);
/* 655:    */                                                                                                                                                                         }
/* 656:    */                                                                                                                                                                         else
/* 657:    */                                                                                                                                                                         {
/* 658:    */                                                                                                                                                                           HtmlElement element;
/* 659:412 */                                                                                                                                                                           if (tagName.equals("sup"))
/* 660:    */                                                                                                                                                                           {
/* 661:413 */                                                                                                                                                                             element = new HtmlSuperscript(namespaceURI, qualifiedName, page, attributeMap);
/* 662:    */                                                                                                                                                                           }
/* 663:    */                                                                                                                                                                           else
/* 664:    */                                                                                                                                                                           {
/* 665:    */                                                                                                                                                                             HtmlElement element;
/* 666:415 */                                                                                                                                                                             if (tagName.equals("table"))
/* 667:    */                                                                                                                                                                             {
/* 668:416 */                                                                                                                                                                               element = new HtmlTable(namespaceURI, qualifiedName, page, attributeMap);
/* 669:    */                                                                                                                                                                             }
/* 670:    */                                                                                                                                                                             else
/* 671:    */                                                                                                                                                                             {
/* 672:    */                                                                                                                                                                               HtmlElement element;
/* 673:418 */                                                                                                                                                                               if (tagName.equals("tbody"))
/* 674:    */                                                                                                                                                                               {
/* 675:419 */                                                                                                                                                                                 element = new HtmlTableBody(namespaceURI, qualifiedName, page, attributeMap);
/* 676:    */                                                                                                                                                                               }
/* 677:    */                                                                                                                                                                               else
/* 678:    */                                                                                                                                                                               {
/* 679:    */                                                                                                                                                                                 HtmlElement element;
/* 680:421 */                                                                                                                                                                                 if (tagName.equals("col"))
/* 681:    */                                                                                                                                                                                 {
/* 682:422 */                                                                                                                                                                                   element = new HtmlTableColumn(namespaceURI, qualifiedName, page, attributeMap);
/* 683:    */                                                                                                                                                                                 }
/* 684:    */                                                                                                                                                                                 else
/* 685:    */                                                                                                                                                                                 {
/* 686:    */                                                                                                                                                                                   HtmlElement element;
/* 687:424 */                                                                                                                                                                                   if (tagName.equals("colgroup"))
/* 688:    */                                                                                                                                                                                   {
/* 689:425 */                                                                                                                                                                                     element = new HtmlTableColumnGroup(namespaceURI, qualifiedName, page, attributeMap);
/* 690:    */                                                                                                                                                                                   }
/* 691:    */                                                                                                                                                                                   else
/* 692:    */                                                                                                                                                                                   {
/* 693:    */                                                                                                                                                                                     HtmlElement element;
/* 694:427 */                                                                                                                                                                                     if (tagName.equals("td"))
/* 695:    */                                                                                                                                                                                     {
/* 696:428 */                                                                                                                                                                                       element = new HtmlTableDataCell(namespaceURI, qualifiedName, page, attributeMap);
/* 697:    */                                                                                                                                                                                     }
/* 698:    */                                                                                                                                                                                     else
/* 699:    */                                                                                                                                                                                     {
/* 700:    */                                                                                                                                                                                       HtmlElement element;
/* 701:430 */                                                                                                                                                                                       if (tagName.equals("tfoot"))
/* 702:    */                                                                                                                                                                                       {
/* 703:431 */                                                                                                                                                                                         element = new HtmlTableFooter(namespaceURI, qualifiedName, page, attributeMap);
/* 704:    */                                                                                                                                                                                       }
/* 705:    */                                                                                                                                                                                       else
/* 706:    */                                                                                                                                                                                       {
/* 707:    */                                                                                                                                                                                         HtmlElement element;
/* 708:433 */                                                                                                                                                                                         if (tagName.equals("thead"))
/* 709:    */                                                                                                                                                                                         {
/* 710:434 */                                                                                                                                                                                           element = new HtmlTableHeader(namespaceURI, qualifiedName, page, attributeMap);
/* 711:    */                                                                                                                                                                                         }
/* 712:    */                                                                                                                                                                                         else
/* 713:    */                                                                                                                                                                                         {
/* 714:    */                                                                                                                                                                                           HtmlElement element;
/* 715:436 */                                                                                                                                                                                           if (tagName.equals("th"))
/* 716:    */                                                                                                                                                                                           {
/* 717:437 */                                                                                                                                                                                             element = new HtmlTableHeaderCell(namespaceURI, qualifiedName, page, attributeMap);
/* 718:    */                                                                                                                                                                                           }
/* 719:    */                                                                                                                                                                                           else
/* 720:    */                                                                                                                                                                                           {
/* 721:    */                                                                                                                                                                                             HtmlElement element;
/* 722:439 */                                                                                                                                                                                             if (tagName.equals("tr"))
/* 723:    */                                                                                                                                                                                             {
/* 724:440 */                                                                                                                                                                                               element = new HtmlTableRow(namespaceURI, qualifiedName, page, attributeMap);
/* 725:    */                                                                                                                                                                                             }
/* 726:    */                                                                                                                                                                                             else
/* 727:    */                                                                                                                                                                                             {
/* 728:    */                                                                                                                                                                                               HtmlElement element;
/* 729:442 */                                                                                                                                                                                               if (tagName.equals("tt"))
/* 730:    */                                                                                                                                                                                               {
/* 731:443 */                                                                                                                                                                                                 element = new HtmlTeletype(namespaceURI, qualifiedName, page, attributeMap);
/* 732:    */                                                                                                                                                                                               }
/* 733:    */                                                                                                                                                                                               else
/* 734:    */                                                                                                                                                                                               {
/* 735:    */                                                                                                                                                                                                 HtmlElement element;
/* 736:445 */                                                                                                                                                                                                 if (tagName.equals("textarea"))
/* 737:    */                                                                                                                                                                                                 {
/* 738:446 */                                                                                                                                                                                                   element = new HtmlTextArea(namespaceURI, qualifiedName, page, attributeMap);
/* 739:    */                                                                                                                                                                                                 }
/* 740:    */                                                                                                                                                                                                 else
/* 741:    */                                                                                                                                                                                                 {
/* 742:    */                                                                                                                                                                                                   HtmlElement element;
/* 743:448 */                                                                                                                                                                                                   if (tagName.equals("dir"))
/* 744:    */                                                                                                                                                                                                   {
/* 745:449 */                                                                                                                                                                                                     element = new HtmlDirectory(namespaceURI, qualifiedName, page, attributeMap);
/* 746:    */                                                                                                                                                                                                   }
/* 747:    */                                                                                                                                                                                                   else
/* 748:    */                                                                                                                                                                                                   {
/* 749:    */                                                                                                                                                                                                     HtmlElement element;
/* 750:451 */                                                                                                                                                                                                     if (tagName.equals("title"))
/* 751:    */                                                                                                                                                                                                     {
/* 752:452 */                                                                                                                                                                                                       element = new HtmlTitle(namespaceURI, qualifiedName, page, attributeMap);
/* 753:    */                                                                                                                                                                                                     }
/* 754:    */                                                                                                                                                                                                     else
/* 755:    */                                                                                                                                                                                                     {
/* 756:    */                                                                                                                                                                                                       HtmlElement element;
/* 757:454 */                                                                                                                                                                                                       if (tagName.equals("u"))
/* 758:    */                                                                                                                                                                                                       {
/* 759:455 */                                                                                                                                                                                                         element = new HtmlUnderlined(namespaceURI, qualifiedName, page, attributeMap);
/* 760:    */                                                                                                                                                                                                       }
/* 761:    */                                                                                                                                                                                                       else
/* 762:    */                                                                                                                                                                                                       {
/* 763:    */                                                                                                                                                                                                         HtmlElement element;
/* 764:457 */                                                                                                                                                                                                         if (tagName.equals("ul"))
/* 765:    */                                                                                                                                                                                                         {
/* 766:458 */                                                                                                                                                                                                           element = new HtmlUnorderedList(namespaceURI, qualifiedName, page, attributeMap);
/* 767:    */                                                                                                                                                                                                         }
/* 768:    */                                                                                                                                                                                                         else
/* 769:    */                                                                                                                                                                                                         {
/* 770:    */                                                                                                                                                                                                           HtmlElement element;
/* 771:460 */                                                                                                                                                                                                           if (tagName.equals("var"))
/* 772:    */                                                                                                                                                                                                           {
/* 773:461 */                                                                                                                                                                                                             element = new HtmlVariable(namespaceURI, qualifiedName, page, attributeMap);
/* 774:    */                                                                                                                                                                                                           }
/* 775:463 */                                                                                                                                                                                                           else if (tagName.equals("video"))
/* 776:    */                                                                                                                                                                                                           {
/* 777:    */                                                                                                                                                                                                             HtmlElement element;
/* 778:464 */                                                                                                                                                                                                             if (page.getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.HTML5_TAGS)) {
/* 779:465 */                                                                                                                                                                                                               element = new HtmlVideo(namespaceURI, qualifiedName, page, attributeMap);
/* 780:    */                                                                                                                                                                                                             } else {
/* 781:468 */                                                                                                                                                                                                               return UnknownElementFactory.instance.createElementNS(page, namespaceURI, qualifiedName, attributes);
/* 782:    */                                                                                                                                                                                                             }
/* 783:    */                                                                                                                                                                                                           }
/* 784:    */                                                                                                                                                                                                           else
/* 785:    */                                                                                                                                                                                                           {
/* 786:    */                                                                                                                                                                                                             HtmlElement element;
/* 787:471 */                                                                                                                                                                                                             if (tagName.equals("wbr"))
/* 788:    */                                                                                                                                                                                                             {
/* 789:472 */                                                                                                                                                                                                               element = new HtmlWordBreak(namespaceURI, qualifiedName, page, attributeMap);
/* 790:    */                                                                                                                                                                                                             }
/* 791:    */                                                                                                                                                                                                             else
/* 792:    */                                                                                                                                                                                                             {
/* 793:    */                                                                                                                                                                                                               HtmlElement element;
/* 794:474 */                                                                                                                                                                                                               if (tagName.equals("xmp")) {
/* 795:475 */                                                                                                                                                                                                                 element = new HtmlExample(namespaceURI, qualifiedName, page, attributeMap);
/* 796:    */                                                                                                                                                                                                               } else {
/* 797:478 */                                                                                                                                                                                                                 throw new IllegalStateException("Cannot find HtmlElement for " + qualifiedName);
/* 798:    */                                                                                                                                                                                                               }
/* 799:    */                                                                                                                                                                                                             }
/* 800:    */                                                                                                                                                                                                           }
/* 801:    */                                                                                                                                                                                                         }
/* 802:    */                                                                                                                                                                                                       }
/* 803:    */                                                                                                                                                                                                     }
/* 804:    */                                                                                                                                                                                                   }
/* 805:    */                                                                                                                                                                                                 }
/* 806:    */                                                                                                                                                                                               }
/* 807:    */                                                                                                                                                                                             }
/* 808:    */                                                                                                                                                                                           }
/* 809:    */                                                                                                                                                                                         }
/* 810:    */                                                                                                                                                                                       }
/* 811:    */                                                                                                                                                                                     }
/* 812:    */                                                                                                                                                                                   }
/* 813:    */                                                                                                                                                                                 }
/* 814:    */                                                                                                                                                                               }
/* 815:    */                                                                                                                                                                             }
/* 816:    */                                                                                                                                                                           }
/* 817:    */                                                                                                                                                                         }
/* 818:    */                                                                                                                                                                       }
/* 819:    */                                                                                                                                                                     }
/* 820:    */                                                                                                                                                                   }
/* 821:    */                                                                                                                                                                 }
/* 822:    */                                                                                                                                                               }
/* 823:    */                                                                                                                                                             }
/* 824:    */                                                                                                                                                           }
/* 825:    */                                                                                                                                                         }
/* 826:    */                                                                                                                                                       }
/* 827:    */                                                                                                                                                     }
/* 828:    */                                                                                                                                                   }
/* 829:    */                                                                                                                                                 }
/* 830:    */                                                                                                                                               }
/* 831:    */                                                                                                                                             }
/* 832:    */                                                                                                                                           }
/* 833:    */                                                                                                                                         }
/* 834:    */                                                                                                                                       }
/* 835:    */                                                                                                                                     }
/* 836:    */                                                                                                                                   }
/* 837:    */                                                                                                                                 }
/* 838:    */                                                                                                                               }
/* 839:    */                                                                                                                             }
/* 840:    */                                                                                                                           }
/* 841:    */                                                                                                                         }
/* 842:    */                                                                                                                       }
/* 843:    */                                                                                                                     }
/* 844:    */                                                                                                                   }
/* 845:    */                                                                                                                 }
/* 846:    */                                                                                                               }
/* 847:    */                                                                                                             }
/* 848:    */                                                                                                           }
/* 849:    */                                                                                                         }
/* 850:    */                                                                                                       }
/* 851:    */                                                                                                     }
/* 852:    */                                                                                                   }
/* 853:    */                                                                                                 }
/* 854:    */                                                                                               }
/* 855:    */                                                                                             }
/* 856:    */                                                                                           }
/* 857:    */                                                                                         }
/* 858:    */                                                                                       }
/* 859:    */                                                                                     }
/* 860:    */                                                                                   }
/* 861:    */                                                                                 }
/* 862:    */                                                                               }
/* 863:    */                                                                             }
/* 864:    */                                                                           }
/* 865:    */                                                                         }
/* 866:    */                                                                       }
/* 867:    */                                                                     }
/* 868:    */                                                                   }
/* 869:    */                                                                 }
/* 870:    */                                                               }
/* 871:    */                                                             }
/* 872:    */                                                           }
/* 873:    */                                                         }
/* 874:    */                                                       }
/* 875:    */                                                     }
/* 876:    */                                                   }
/* 877:    */                                                 }
/* 878:    */                                               }
/* 879:    */                                             }
/* 880:    */                                           }
/* 881:    */                                         }
/* 882:    */                                       }
/* 883:    */                                     }
/* 884:    */                                   }
/* 885:    */                                 }
/* 886:    */                               }
/* 887:    */                             }
/* 888:    */                           }
/* 889:    */                         }
/* 890:    */                       }
/* 891:    */                     }
/* 892:    */                   }
/* 893:    */                 }
/* 894:    */               }
/* 895:    */             }
/* 896:    */           }
/* 897:    */         }
/* 898:    */       }
/* 899:    */     }
/* 900:    */     HtmlElement element;
/* 901:480 */     return element;
/* 902:    */   }
/* 903:    */   
/* 904:    */   static Map<String, DomAttr> setAttributes(SgmlPage page, Attributes attributes)
/* 905:    */   {
/* 906:491 */     Map<String, DomAttr> attributeMap = null;
/* 907:492 */     if (attributes != null)
/* 908:    */     {
/* 909:493 */       attributeMap = HtmlElement.createAttributeMap(attributes.getLength());
/* 910:494 */       for (int i = 0; i < attributes.getLength(); i++)
/* 911:    */       {
/* 912:495 */         String qName = attributes.getQName(i);
/* 913:497 */         if (!attributeMap.containsKey(qName))
/* 914:    */         {
/* 915:498 */           String namespaceURI = attributes.getURI(i);
/* 916:499 */           if ((namespaceURI != null) && (namespaceURI.length() == 0)) {
/* 917:500 */             namespaceURI = null;
/* 918:    */           }
/* 919:502 */           HtmlElement.addAttributeToMap(page, attributeMap, namespaceURI, qName, attributes.getValue(i));
/* 920:    */         }
/* 921:    */       }
/* 922:    */     }
/* 923:507 */     return attributeMap;
/* 924:    */   }
/* 925:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.DefaultElementFactory
 * JD-Core Version:    0.7.0.1
 */