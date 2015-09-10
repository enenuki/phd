/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*   6:    */ import com.gargoylesoftware.htmlunit.FormEncodingType;
/*   7:    */ import com.gargoylesoftware.htmlunit.HttpMethod;
/*   8:    */ import com.gargoylesoftware.htmlunit.Page;
/*   9:    */ import com.gargoylesoftware.htmlunit.ScriptResult;
/*  10:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*  11:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*  12:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*  13:    */ import com.gargoylesoftware.htmlunit.WebRequest;
/*  14:    */ import com.gargoylesoftware.htmlunit.WebResponse;
/*  15:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  16:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*  17:    */ import com.gargoylesoftware.htmlunit.util.UrlUtils;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.net.MalformedURLException;
/*  20:    */ import java.net.URL;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Arrays;
/*  23:    */ import java.util.Collection;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import java.util.regex.Matcher;
/*  27:    */ import java.util.regex.Pattern;
/*  28:    */ import org.apache.commons.lang.StringUtils;
/*  29:    */ import org.apache.http.client.utils.URLEncodedUtils;
/*  30:    */ 
/*  31:    */ public class HtmlForm
/*  32:    */   extends HtmlElement
/*  33:    */ {
/*  34:    */   public static final String TAG_NAME = "form";
/*  35: 67 */   private static final Collection<String> SUBMITTABLE_ELEMENT_NAMES = Arrays.asList(new String[] { "input", "button", "select", "textarea", "isindex" });
/*  36: 70 */   private static final Pattern SUBMIT_CHARSET_PATTERN = Pattern.compile("[ ,].*");
/*  37: 72 */   private final List<HtmlElement> lostChildren_ = new ArrayList();
/*  38:    */   private boolean isPreventDefault_;
/*  39:    */   
/*  40:    */   HtmlForm(String namespaceURI, String qualifiedName, SgmlPage htmlPage, Map<String, DomAttr> attributes)
/*  41:    */   {
/*  42: 86 */     super(namespaceURI, qualifiedName, htmlPage, attributes);
/*  43:    */   }
/*  44:    */   
/*  45:    */   Page submit(SubmittableElement submitElement)
/*  46:    */     throws IOException
/*  47:    */   {
/*  48:105 */     HtmlPage htmlPage = (HtmlPage)getPage();
/*  49:106 */     WebClient webClient = htmlPage.getWebClient();
/*  50:107 */     if (webClient.isJavaScriptEnabled())
/*  51:    */     {
/*  52:108 */       if (submitElement != null)
/*  53:    */       {
/*  54:109 */         this.isPreventDefault_ = false;
/*  55:110 */         ScriptResult scriptResult = fireEvent("submit");
/*  56:111 */         if (this.isPreventDefault_) {
/*  57:112 */           return scriptResult.getNewPage();
/*  58:    */         }
/*  59:    */       }
/*  60:116 */       String action = getActionAttribute().trim();
/*  61:117 */       if (StringUtils.startsWithIgnoreCase(action, "javascript:")) {
/*  62:118 */         return htmlPage.executeJavaScriptIfPossible(action, "Form action", getStartLineNumber()).getNewPage();
/*  63:    */       }
/*  64:    */     }
/*  65:122 */     else if (StringUtils.startsWithIgnoreCase(getActionAttribute(), "javascript:"))
/*  66:    */     {
/*  67:125 */       return htmlPage;
/*  68:    */     }
/*  69:129 */     WebRequest request = getWebRequest(submitElement);
/*  70:130 */     String target = htmlPage.getResolvedTarget(getTargetAttribute());
/*  71:    */     
/*  72:132 */     WebWindow webWindow = htmlPage.getEnclosingWindow();
/*  73:133 */     String action = getActionAttribute();
/*  74:134 */     boolean isHashJump = (HttpMethod.GET.equals(request.getHttpMethod())) && (action.endsWith("#"));
/*  75:135 */     webClient.download(webWindow, target, request, isHashJump, "JS form.submit()");
/*  76:136 */     return htmlPage;
/*  77:    */   }
/*  78:    */   
/*  79:    */   @Deprecated
/*  80:    */   public WebRequest getWebRequestSettings(SubmittableElement submitElement)
/*  81:    */   {
/*  82:149 */     return getWebRequest(submitElement);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public WebRequest getWebRequest(SubmittableElement submitElement)
/*  86:    */   {
/*  87:160 */     HtmlPage htmlPage = (HtmlPage)getPage();
/*  88:161 */     List<NameValuePair> parameters = getParameterListForSubmit(submitElement);
/*  89:    */     
/*  90:163 */     String methodAttribute = getMethodAttribute();
/*  91:    */     HttpMethod method;
/*  92:    */     HttpMethod method;
/*  93:164 */     if ("post".equalsIgnoreCase(methodAttribute))
/*  94:    */     {
/*  95:165 */       method = HttpMethod.POST;
/*  96:    */     }
/*  97:    */     else
/*  98:    */     {
/*  99:168 */       if ((!"get".equalsIgnoreCase(methodAttribute)) && (StringUtils.isNotBlank(methodAttribute))) {
/* 100:169 */         notifyIncorrectness("Incorrect submit method >" + getMethodAttribute() + "<. Using >GET<.");
/* 101:    */       }
/* 102:171 */       method = HttpMethod.GET;
/* 103:    */     }
/* 104:174 */     BrowserVersion browser = getPage().getWebClient().getBrowserVersion();
/* 105:175 */     String actionUrl = getActionAttribute();
/* 106:176 */     if (HttpMethod.GET == method)
/* 107:    */     {
/* 108:177 */       String anchor = StringUtils.substringAfter(actionUrl, "#");
/* 109:178 */       String enc = getPage().getPageEncoding();
/* 110:179 */       String queryFromFields = URLEncodedUtils.format(Arrays.asList(NameValuePair.toHttpClient(parameters)), enc);
/* 111:    */       
/* 112:    */ 
/* 113:    */ 
/* 114:183 */       actionUrl = StringUtils.substringBefore(actionUrl, "#");
/* 115:184 */       actionUrl = StringUtils.substringBefore(actionUrl, "?");
/* 116:185 */       if ((browser.hasFeature(BrowserVersionFeatures.FORM_SUBMISSION_URL_END_WITH_QUESTIONMARK)) || (queryFromFields.length() > 0)) {
/* 117:187 */         actionUrl = actionUrl + "?" + queryFromFields;
/* 118:    */       }
/* 119:189 */       if ((anchor.length() > 0) && (!browser.hasFeature(BrowserVersionFeatures.FORM_SUBMISSION_URL_WITHOUT_HASH))) {
/* 120:191 */         actionUrl = actionUrl + "#" + anchor;
/* 121:    */       }
/* 122:193 */       parameters.clear();
/* 123:    */     }
/* 124:    */     URL url;
/* 125:    */     try
/* 126:    */     {
/* 127:197 */       if (actionUrl.length() == 0)
/* 128:    */       {
/* 129:198 */         URL url = htmlPage.getWebResponse().getWebRequest().getUrl();
/* 130:199 */         if (browser.hasFeature(BrowserVersionFeatures.FORM_SUBMISSION_URL_WITHOUT_HASH)) {
/* 131:200 */           url = UrlUtils.getUrlWithNewRef(url, null);
/* 132:    */         }
/* 133:    */       }
/* 134:    */       else
/* 135:    */       {
/* 136:    */         URL url;
/* 137:203 */         if (actionUrl.startsWith("?"))
/* 138:    */         {
/* 139:204 */           String urlString = htmlPage.getWebResponse().getWebRequest().getUrl().toExternalForm();
/* 140:205 */           if (urlString.indexOf('?') != -1) {
/* 141:206 */             urlString = urlString.substring(0, urlString.indexOf('?'));
/* 142:208 */           } else if ((urlString.indexOf('#') != -1) && (browser.hasFeature(BrowserVersionFeatures.FORM_SUBMISSION_URL_WITHOUT_HASH))) {
/* 143:210 */             urlString = urlString.substring(0, urlString.indexOf('#'));
/* 144:    */           }
/* 145:212 */           url = new URL(urlString + actionUrl);
/* 146:    */         }
/* 147:    */         else
/* 148:    */         {
/* 149:215 */           url = htmlPage.getFullyQualifiedUrl(actionUrl);
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:    */     catch (MalformedURLException e)
/* 154:    */     {
/* 155:219 */       throw new IllegalArgumentException("Not a valid url: " + actionUrl);
/* 156:    */     }
/* 157:222 */     WebRequest request = new WebRequest(url, method);
/* 158:223 */     request.setRequestParameters(parameters);
/* 159:224 */     if (HttpMethod.POST == method) {
/* 160:225 */       request.setEncodingType(FormEncodingType.getInstance(getEnctypeAttribute()));
/* 161:    */     }
/* 162:227 */     request.setCharset(getSubmitCharset());
/* 163:228 */     request.setAdditionalHeader("Referer", htmlPage.getWebResponse().getWebRequest().getUrl().toExternalForm());
/* 164:    */     
/* 165:230 */     return request;
/* 166:    */   }
/* 167:    */   
/* 168:    */   private String getSubmitCharset()
/* 169:    */   {
/* 170:240 */     if (getAcceptCharsetAttribute().length() > 0) {
/* 171:241 */       return SUBMIT_CHARSET_PATTERN.matcher(getAcceptCharsetAttribute().trim()).replaceAll("");
/* 172:    */     }
/* 173:243 */     return getPage().getPageEncoding();
/* 174:    */   }
/* 175:    */   
/* 176:    */   private List<NameValuePair> getParameterListForSubmit(SubmittableElement submitElement)
/* 177:    */   {
/* 178:257 */     Collection<SubmittableElement> submittableElements = getSubmittableElements(submitElement);
/* 179:    */     
/* 180:259 */     List<NameValuePair> parameterList = new ArrayList(submittableElements.size());
/* 181:260 */     for (SubmittableElement element : submittableElements) {
/* 182:261 */       for (NameValuePair pair : element.getSubmitKeyValuePairs()) {
/* 183:262 */         parameterList.add(pair);
/* 184:    */       }
/* 185:    */     }
/* 186:266 */     return parameterList;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Page reset()
/* 190:    */   {
/* 191:277 */     SgmlPage htmlPage = getPage();
/* 192:278 */     ScriptResult scriptResult = fireEvent("reset");
/* 193:279 */     if (ScriptResult.isFalse(scriptResult)) {
/* 194:280 */       return scriptResult.getNewPage();
/* 195:    */     }
/* 196:283 */     for (HtmlElement next : getHtmlElementDescendants()) {
/* 197:284 */       if ((next instanceof SubmittableElement)) {
/* 198:285 */         ((SubmittableElement)next).reset();
/* 199:    */       }
/* 200:    */     }
/* 201:289 */     return htmlPage;
/* 202:    */   }
/* 203:    */   
/* 204:    */   Collection<SubmittableElement> getSubmittableElements(SubmittableElement submitElement)
/* 205:    */   {
/* 206:301 */     List<SubmittableElement> submittableElements = new ArrayList();
/* 207:303 */     for (HtmlElement element : getHtmlElementDescendants()) {
/* 208:304 */       if (isSubmittable(element, submitElement)) {
/* 209:305 */         submittableElements.add((SubmittableElement)element);
/* 210:    */       }
/* 211:    */     }
/* 212:309 */     for (HtmlElement element : this.lostChildren_) {
/* 213:310 */       if (isSubmittable(element, submitElement)) {
/* 214:311 */         submittableElements.add((SubmittableElement)element);
/* 215:    */       }
/* 216:    */     }
/* 217:315 */     return submittableElements;
/* 218:    */   }
/* 219:    */   
/* 220:    */   private boolean isValidForSubmission(HtmlElement element, SubmittableElement submitElement)
/* 221:    */   {
/* 222:319 */     String tagName = element.getTagName();
/* 223:320 */     if (!SUBMITTABLE_ELEMENT_NAMES.contains(tagName)) {
/* 224:321 */       return false;
/* 225:    */     }
/* 226:323 */     if (element.hasAttribute("disabled")) {
/* 227:324 */       return false;
/* 228:    */     }
/* 229:327 */     if ((element == submitElement) && ((element instanceof HtmlImageInput))) {
/* 230:328 */       return true;
/* 231:    */     }
/* 232:331 */     if ((!"isindex".equals(tagName)) && (!element.hasAttribute("name"))) {
/* 233:332 */       return false;
/* 234:    */     }
/* 235:335 */     if ((!"isindex".equals(tagName)) && ("".equals(element.getAttribute("name")))) {
/* 236:336 */       return false;
/* 237:    */     }
/* 238:339 */     if ((element instanceof HtmlInput))
/* 239:    */     {
/* 240:340 */       String type = element.getAttribute("type").toLowerCase();
/* 241:341 */       if (("radio".equals(type)) || ("checkbox".equals(type))) {
/* 242:342 */         return element.hasAttribute("checked");
/* 243:    */       }
/* 244:    */     }
/* 245:345 */     if ("select".equals(tagName)) {
/* 246:346 */       return ((HtmlSelect)element).isValidForSubmission();
/* 247:    */     }
/* 248:348 */     return true;
/* 249:    */   }
/* 250:    */   
/* 251:    */   private boolean isSubmittable(HtmlElement element, SubmittableElement submitElement)
/* 252:    */   {
/* 253:361 */     String tagName = element.getTagName();
/* 254:362 */     if (!isValidForSubmission(element, submitElement)) {
/* 255:363 */       return false;
/* 256:    */     }
/* 257:367 */     if (element == submitElement) {
/* 258:368 */       return true;
/* 259:    */     }
/* 260:370 */     if ((element instanceof HtmlInput))
/* 261:    */     {
/* 262:371 */       HtmlInput input = (HtmlInput)element;
/* 263:372 */       String type = input.getTypeAttribute().toLowerCase();
/* 264:373 */       if (("submit".equals(type)) || ("image".equals(type)) || ("reset".equals(type)) || ("button".equals(type))) {
/* 265:374 */         return false;
/* 266:    */       }
/* 267:    */     }
/* 268:377 */     if ("button".equals(tagName)) {
/* 269:378 */       return false;
/* 270:    */     }
/* 271:381 */     return true;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<HtmlInput> getInputsByName(String name)
/* 275:    */   {
/* 276:391 */     List<HtmlInput> list = getElementsByAttribute("input", "name", name);
/* 277:394 */     for (HtmlElement elt : getLostChildren()) {
/* 278:395 */       if (((elt instanceof HtmlInput)) && (name.equals(elt.getAttribute("name")))) {
/* 279:396 */         list.add((HtmlInput)elt);
/* 280:    */       }
/* 281:    */     }
/* 282:399 */     return list;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public final <I extends HtmlInput> I getInputByName(String name)
/* 286:    */     throws ElementNotFoundException
/* 287:    */   {
/* 288:412 */     List<HtmlInput> inputs = getInputsByName(name);
/* 289:414 */     if (inputs.isEmpty()) {
/* 290:415 */       throw new ElementNotFoundException("input", "name", name);
/* 291:    */     }
/* 292:417 */     return (HtmlInput)inputs.get(0);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public List<HtmlSelect> getSelectsByName(String name)
/* 296:    */   {
/* 297:427 */     List<HtmlSelect> list = getElementsByAttribute("select", "name", name);
/* 298:430 */     for (HtmlElement elt : getLostChildren()) {
/* 299:431 */       if (((elt instanceof HtmlSelect)) && (name.equals(elt.getAttribute("name")))) {
/* 300:432 */         list.add((HtmlSelect)elt);
/* 301:    */       }
/* 302:    */     }
/* 303:435 */     return list;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public HtmlSelect getSelectByName(String name)
/* 307:    */     throws ElementNotFoundException
/* 308:    */   {
/* 309:447 */     List<HtmlSelect> list = getSelectsByName(name);
/* 310:448 */     if (list.isEmpty()) {
/* 311:449 */       throw new ElementNotFoundException("select", "name", name);
/* 312:    */     }
/* 313:451 */     return (HtmlSelect)list.get(0);
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List<HtmlButton> getButtonsByName(String name)
/* 317:    */   {
/* 318:461 */     List<HtmlButton> list = getElementsByAttribute("button", "name", name);
/* 319:464 */     for (HtmlElement elt : getLostChildren()) {
/* 320:465 */       if (((elt instanceof HtmlButton)) && (name.equals(elt.getAttribute("name")))) {
/* 321:466 */         list.add((HtmlButton)elt);
/* 322:    */       }
/* 323:    */     }
/* 324:469 */     return list;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public HtmlButton getButtonByName(String name)
/* 328:    */     throws ElementNotFoundException
/* 329:    */   {
/* 330:481 */     List<HtmlButton> list = getButtonsByName(name);
/* 331:482 */     if (list.isEmpty()) {
/* 332:483 */       throw new ElementNotFoundException("button", "name", name);
/* 333:    */     }
/* 334:485 */     return (HtmlButton)list.get(0);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public List<HtmlTextArea> getTextAreasByName(String name)
/* 338:    */   {
/* 339:495 */     List<HtmlTextArea> list = getElementsByAttribute("textarea", "name", name);
/* 340:498 */     for (HtmlElement elt : getLostChildren()) {
/* 341:499 */       if (((elt instanceof HtmlTextArea)) && (name.equals(elt.getAttribute("name")))) {
/* 342:500 */         list.add((HtmlTextArea)elt);
/* 343:    */       }
/* 344:    */     }
/* 345:503 */     return list;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public HtmlTextArea getTextAreaByName(String name)
/* 349:    */     throws ElementNotFoundException
/* 350:    */   {
/* 351:515 */     List<HtmlTextArea> list = getTextAreasByName(name);
/* 352:516 */     if (list.isEmpty()) {
/* 353:517 */       throw new ElementNotFoundException("textarea", "name", name);
/* 354:    */     }
/* 355:519 */     return (HtmlTextArea)list.get(0);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public List<HtmlRadioButtonInput> getRadioButtonsByName(String name)
/* 359:    */   {
/* 360:529 */     WebAssert.notNull("name", name);
/* 361:    */     
/* 362:531 */     List<HtmlRadioButtonInput> results = new ArrayList();
/* 363:533 */     for (HtmlElement element : getInputsByName(name)) {
/* 364:534 */       if ((element instanceof HtmlRadioButtonInput)) {
/* 365:535 */         results.add((HtmlRadioButtonInput)element);
/* 366:    */       }
/* 367:    */     }
/* 368:539 */     return results;
/* 369:    */   }
/* 370:    */   
/* 371:    */   void setCheckedRadioButton(HtmlRadioButtonInput radioButtonInput)
/* 372:    */   {
/* 373:549 */     if ((!isAncestorOf(radioButtonInput)) && (!this.lostChildren_.contains(radioButtonInput))) {
/* 374:550 */       throw new IllegalArgumentException("HtmlRadioButtonInput is not child of this HtmlForm");
/* 375:    */     }
/* 376:552 */     List<HtmlRadioButtonInput> radios = getRadioButtonsByName(radioButtonInput.getNameAttribute());
/* 377:554 */     for (HtmlRadioButtonInput input : radios) {
/* 378:555 */       if (input == radioButtonInput) {
/* 379:556 */         input.setAttribute("checked", "checked");
/* 380:    */       } else {
/* 381:559 */         input.removeAttribute("checked");
/* 382:    */       }
/* 383:    */     }
/* 384:    */   }
/* 385:    */   
/* 386:    */   public HtmlRadioButtonInput getCheckedRadioButton(String name)
/* 387:    */   {
/* 388:572 */     WebAssert.notNull("name", name);
/* 389:574 */     for (HtmlRadioButtonInput input : getRadioButtonsByName(name)) {
/* 390:575 */       if (input.isChecked()) {
/* 391:576 */         return input;
/* 392:    */       }
/* 393:    */     }
/* 394:579 */     return null;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public final String getActionAttribute()
/* 398:    */   {
/* 399:590 */     return getAttribute("action");
/* 400:    */   }
/* 401:    */   
/* 402:    */   public final void setActionAttribute(String action)
/* 403:    */   {
/* 404:601 */     setAttribute("action", action);
/* 405:    */   }
/* 406:    */   
/* 407:    */   public final String getMethodAttribute()
/* 408:    */   {
/* 409:612 */     return getAttribute("method");
/* 410:    */   }
/* 411:    */   
/* 412:    */   public final void setMethodAttribute(String method)
/* 413:    */   {
/* 414:623 */     setAttribute("method", method);
/* 415:    */   }
/* 416:    */   
/* 417:    */   public final String getNameAttribute()
/* 418:    */   {
/* 419:634 */     return getAttribute("name");
/* 420:    */   }
/* 421:    */   
/* 422:    */   public final void setNameAttribute(String name)
/* 423:    */   {
/* 424:645 */     setAttribute("name", name);
/* 425:    */   }
/* 426:    */   
/* 427:    */   public final String getEnctypeAttribute()
/* 428:    */   {
/* 429:657 */     return getAttribute("enctype");
/* 430:    */   }
/* 431:    */   
/* 432:    */   public final void setEnctypeAttribute(String encoding)
/* 433:    */   {
/* 434:669 */     setAttribute("enctype", encoding);
/* 435:    */   }
/* 436:    */   
/* 437:    */   public final String getOnSubmitAttribute()
/* 438:    */   {
/* 439:680 */     return getAttribute("onsubmit");
/* 440:    */   }
/* 441:    */   
/* 442:    */   public final String getOnResetAttribute()
/* 443:    */   {
/* 444:691 */     return getAttribute("onreset");
/* 445:    */   }
/* 446:    */   
/* 447:    */   public final String getAcceptAttribute()
/* 448:    */   {
/* 449:702 */     return getAttribute("accept");
/* 450:    */   }
/* 451:    */   
/* 452:    */   public final String getAcceptCharsetAttribute()
/* 453:    */   {
/* 454:713 */     return getAttribute("accept-charset");
/* 455:    */   }
/* 456:    */   
/* 457:    */   public final String getTargetAttribute()
/* 458:    */   {
/* 459:724 */     return getAttribute("target");
/* 460:    */   }
/* 461:    */   
/* 462:    */   public final void setTargetAttribute(String target)
/* 463:    */   {
/* 464:735 */     setAttribute("target", target);
/* 465:    */   }
/* 466:    */   
/* 467:    */   public <I extends HtmlInput> I getInputByValue(String value)
/* 468:    */     throws ElementNotFoundException
/* 469:    */   {
/* 470:747 */     List<HtmlInput> list = getInputsByValue(value);
/* 471:748 */     if (list.isEmpty()) {
/* 472:749 */       throw new ElementNotFoundException("input", "value", value);
/* 473:    */     }
/* 474:751 */     return (HtmlInput)list.get(0);
/* 475:    */   }
/* 476:    */   
/* 477:    */   public List<HtmlInput> getInputsByValue(String value)
/* 478:    */   {
/* 479:760 */     List<HtmlInput> results = getElementsByAttribute("input", "value", value);
/* 480:762 */     for (HtmlElement element : getLostChildren()) {
/* 481:763 */       if (((element instanceof HtmlInput)) && (value.equals(element.getAttribute("value")))) {
/* 482:764 */         results.add((HtmlInput)element);
/* 483:    */       }
/* 484:    */     }
/* 485:768 */     return results;
/* 486:    */   }
/* 487:    */   
/* 488:    */   void addLostChild(HtmlElement field)
/* 489:    */   {
/* 490:777 */     this.lostChildren_.add(field);
/* 491:778 */     field.setOwningForm(this);
/* 492:    */   }
/* 493:    */   
/* 494:    */   public List<HtmlElement> getLostChildren()
/* 495:    */   {
/* 496:787 */     return this.lostChildren_;
/* 497:    */   }
/* 498:    */   
/* 499:    */   protected void preventDefault()
/* 500:    */   {
/* 501:795 */     this.isPreventDefault_ = true;
/* 502:    */   }
/* 503:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlForm
 * JD-Core Version:    0.7.0.1
 */