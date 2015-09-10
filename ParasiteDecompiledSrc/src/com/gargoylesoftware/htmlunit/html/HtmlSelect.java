/*   1:    */ package com.gargoylesoftware.htmlunit.html;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.BrowserVersion;
/*   4:    */ import com.gargoylesoftware.htmlunit.BrowserVersionFeatures;
/*   5:    */ import com.gargoylesoftware.htmlunit.ElementNotFoundException;
/*   6:    */ import com.gargoylesoftware.htmlunit.Page;
/*   7:    */ import com.gargoylesoftware.htmlunit.SgmlPage;
/*   8:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   9:    */ import com.gargoylesoftware.htmlunit.WebClient;
/*  10:    */ import com.gargoylesoftware.htmlunit.WebWindow;
/*  11:    */ import com.gargoylesoftware.htmlunit.util.NameValuePair;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.Collection;
/*  14:    */ import java.util.Collections;
/*  15:    */ import java.util.HashSet;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import org.w3c.dom.Node;
/*  20:    */ 
/*  21:    */ public class HtmlSelect
/*  22:    */   extends HtmlElement
/*  23:    */   implements DisabledElement, SubmittableElement, FormFieldWithNameHistory
/*  24:    */ {
/*  25:    */   public static final String TAG_NAME = "select";
/*  26:    */   private String originalName_;
/*  27: 53 */   private Collection<String> previousNames_ = Collections.emptySet();
/*  28:    */   
/*  29:    */   HtmlSelect(String namespaceURI, String qualifiedName, SgmlPage page, Map<String, DomAttr> attributes)
/*  30:    */   {
/*  31: 65 */     super(namespaceURI, qualifiedName, page, attributes);
/*  32: 66 */     this.originalName_ = getNameAttribute();
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected void onAllChildrenAddedToPage(boolean postponed)
/*  36:    */   {
/*  37:    */     int size;
/*  38:    */     try
/*  39:    */     {
/*  40: 80 */       size = Integer.parseInt(getSizeAttribute());
/*  41: 81 */       if (size < 0)
/*  42:    */       {
/*  43: 82 */         removeAttribute("size");
/*  44: 83 */         size = 0;
/*  45:    */       }
/*  46:    */     }
/*  47:    */     catch (NumberFormatException e)
/*  48:    */     {
/*  49: 87 */       removeAttribute("size");
/*  50: 88 */       size = 0;
/*  51:    */     }
/*  52: 92 */     if ((getSelectedOptions().isEmpty()) && (size <= 1) && (!isMultipleSelectEnabled()))
/*  53:    */     {
/*  54: 93 */       List<HtmlOption> options = getOptions();
/*  55: 94 */       if (!options.isEmpty())
/*  56:    */       {
/*  57: 95 */         HtmlOption first = (HtmlOption)options.get(0);
/*  58: 96 */         first.setSelectedInternal(true);
/*  59:    */       }
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<HtmlOption> getSelectedOptions()
/*  64:    */   {
/*  65:    */     List<HtmlOption> result;
/*  66:    */     List<HtmlOption> result;
/*  67:113 */     if (isMultipleSelectEnabled())
/*  68:    */     {
/*  69:115 */       result = new ArrayList();
/*  70:116 */       for (HtmlElement element : getHtmlElementDescendants()) {
/*  71:117 */         if (((element instanceof HtmlOption)) && (((HtmlOption)element).isSelected())) {
/*  72:118 */           result.add((HtmlOption)element);
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78:124 */       result = new ArrayList(1);
/*  79:125 */       HtmlOption lastSelected = null;
/*  80:126 */       for (HtmlElement element : getHtmlElementDescendants()) {
/*  81:127 */         if ((element instanceof HtmlOption))
/*  82:    */         {
/*  83:128 */           HtmlOption option = (HtmlOption)element;
/*  84:129 */           if (option.isSelected()) {
/*  85:130 */             lastSelected = option;
/*  86:    */           }
/*  87:    */         }
/*  88:    */       }
/*  89:134 */       if (lastSelected != null) {
/*  90:135 */         result.add(lastSelected);
/*  91:    */       }
/*  92:    */     }
/*  93:138 */     return Collections.unmodifiableList(result);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public List<HtmlOption> getOptions()
/*  97:    */   {
/*  98:146 */     return Collections.unmodifiableList(getHtmlElementsByTagName("option"));
/*  99:    */   }
/* 100:    */   
/* 101:    */   public HtmlOption getOption(int index)
/* 102:    */   {
/* 103:156 */     return (HtmlOption)getHtmlElementsByTagName("option").get(index);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int getOptionSize()
/* 107:    */   {
/* 108:164 */     return getHtmlElementsByTagName("option").size();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setOptionSize(int newLength)
/* 112:    */   {
/* 113:173 */     List<HtmlElement> elementList = getHtmlElementsByTagName("option");
/* 114:175 */     for (int i = elementList.size() - 1; i >= newLength; i--) {
/* 115:176 */       ((HtmlElement)elementList.get(i)).remove();
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void removeOption(int index)
/* 120:    */   {
/* 121:185 */     HtmlElement.ChildElementsIterator iterator = new HtmlElement.ChildElementsIterator(this);
/* 122:186 */     for (int i = 0; iterator.hasNext();)
/* 123:    */     {
/* 124:187 */       HtmlElement element = iterator.nextElement();
/* 125:188 */       if ((element instanceof HtmlOption))
/* 126:    */       {
/* 127:189 */         if (i == index)
/* 128:    */         {
/* 129:190 */           element.remove();
/* 130:191 */           return;
/* 131:    */         }
/* 132:193 */         i++;
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void replaceOption(int index, HtmlOption newOption)
/* 138:    */   {
/* 139:204 */     HtmlElement.ChildElementsIterator iterator = new HtmlElement.ChildElementsIterator(this);
/* 140:205 */     for (int i = 0; iterator.hasNext();)
/* 141:    */     {
/* 142:206 */       HtmlElement element = iterator.nextElement();
/* 143:207 */       if ((element instanceof HtmlOption))
/* 144:    */       {
/* 145:208 */         if (i == index)
/* 146:    */         {
/* 147:209 */           element.replace(newOption);
/* 148:210 */           return;
/* 149:    */         }
/* 150:212 */         i++;
/* 151:    */       }
/* 152:    */     }
/* 153:216 */     if (newOption.isSelected()) {
/* 154:217 */       setSelectedAttribute(newOption, true);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void appendOption(HtmlOption newOption)
/* 159:    */   {
/* 160:226 */     appendChild(newOption);
/* 161:    */   }
/* 162:    */   
/* 163:    */   public DomNode appendChild(Node node)
/* 164:    */   {
/* 165:234 */     DomNode response = super.appendChild(node);
/* 166:235 */     if ((node instanceof HtmlOption))
/* 167:    */     {
/* 168:236 */       HtmlOption option = (HtmlOption)node;
/* 169:237 */       if (option.isSelected()) {
/* 170:238 */         doSelectOption(option, true);
/* 171:    */       }
/* 172:    */     }
/* 173:241 */     return response;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public <P extends Page> P setSelectedAttribute(String optionValue, boolean isSelected)
/* 177:    */   {
/* 178:    */     try
/* 179:    */     {
/* 180:259 */       return setSelectedAttribute(getOptionByValue(optionValue), isSelected);
/* 181:    */     }
/* 182:    */     catch (ElementNotFoundException e)
/* 183:    */     {
/* 184:262 */       if (getPage().getWebClient().getBrowserVersion().hasFeature(BrowserVersionFeatures.SELECT_DESELECT_ALL_IF_SWITCHING_UNKNOWN)) {
/* 185:264 */         for (HtmlOption o : getSelectedOptions()) {
/* 186:265 */           o.setSelected(false);
/* 187:    */         }
/* 188:    */       }
/* 189:    */     }
/* 190:268 */     return getPage();
/* 191:    */   }
/* 192:    */   
/* 193:    */   public <P extends Page> P setSelectedAttribute(HtmlOption selectedOption, boolean isSelected)
/* 194:    */   {
/* 195:286 */     return setSelectedAttribute(selectedOption, isSelected, true);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public <P extends Page> P setSelectedAttribute(HtmlOption selectedOption, boolean isSelected, boolean invokeOnFocus)
/* 199:    */   {
/* 200:307 */     if ((isSelected) && (invokeOnFocus)) {
/* 201:308 */       ((HtmlPage)getPage()).setFocusedElement(this);
/* 202:    */     }
/* 203:311 */     boolean changeSelectedState = selectedOption.isSelected() != isSelected;
/* 204:313 */     if (changeSelectedState)
/* 205:    */     {
/* 206:314 */       doSelectOption(selectedOption, isSelected);
/* 207:315 */       HtmlInput.executeOnChangeHandlerIfAppropriate(this);
/* 208:    */     }
/* 209:318 */     return getPage().getWebClient().getCurrentWindow().getEnclosedPage();
/* 210:    */   }
/* 211:    */   
/* 212:    */   private void doSelectOption(HtmlOption selectedOption, boolean isSelected)
/* 213:    */   {
/* 214:325 */     if (isMultipleSelectEnabled()) {
/* 215:326 */       selectedOption.setSelectedInternal(isSelected);
/* 216:    */     } else {
/* 217:329 */       for (HtmlOption option : getOptions()) {
/* 218:330 */         option.setSelectedInternal((option == selectedOption) && (isSelected));
/* 219:    */       }
/* 220:    */     }
/* 221:    */   }
/* 222:    */   
/* 223:    */   public NameValuePair[] getSubmitKeyValuePairs()
/* 224:    */   {
/* 225:339 */     String name = getNameAttribute();
/* 226:    */     
/* 227:341 */     List<HtmlOption> selectedOptions = getSelectedOptions();
/* 228:    */     
/* 229:343 */     NameValuePair[] pairs = new NameValuePair[selectedOptions.size()];
/* 230:    */     
/* 231:345 */     int i = 0;
/* 232:346 */     for (HtmlOption option : selectedOptions) {
/* 233:347 */       pairs[(i++)] = new NameValuePair(name, option.getValueAttribute());
/* 234:    */     }
/* 235:349 */     return pairs;
/* 236:    */   }
/* 237:    */   
/* 238:    */   boolean isValidForSubmission()
/* 239:    */   {
/* 240:357 */     return getOptionSize() > 0;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void reset()
/* 244:    */   {
/* 245:364 */     for (HtmlOption option : getOptions()) {
/* 246:365 */       option.reset();
/* 247:    */     }
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setDefaultValue(String defaultValue)
/* 251:    */   {
/* 252:374 */     setSelectedAttribute(defaultValue, true);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public String getDefaultValue()
/* 256:    */   {
/* 257:382 */     List<HtmlOption> options = getSelectedOptions();
/* 258:383 */     if (options.size() > 0) {
/* 259:384 */       return ((HtmlOption)options.get(0)).getValueAttribute();
/* 260:    */     }
/* 261:386 */     return "";
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setDefaultChecked(boolean defaultChecked) {}
/* 265:    */   
/* 266:    */   public boolean isDefaultChecked()
/* 267:    */   {
/* 268:410 */     return false;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public boolean isMultipleSelectEnabled()
/* 272:    */   {
/* 273:418 */     return getAttribute("multiple") != ATTRIBUTE_NOT_DEFINED;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public HtmlOption getOptionByValue(String value)
/* 277:    */     throws ElementNotFoundException
/* 278:    */   {
/* 279:429 */     WebAssert.notNull("value", value);
/* 280:430 */     for (HtmlOption option : getOptions()) {
/* 281:431 */       if (option.getValueAttribute().equals(value)) {
/* 282:432 */         return option;
/* 283:    */       }
/* 284:    */     }
/* 285:435 */     throw new ElementNotFoundException("option", "value", value);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public HtmlOption getOptionByText(String text)
/* 289:    */     throws ElementNotFoundException
/* 290:    */   {
/* 291:446 */     WebAssert.notNull("text", text);
/* 292:447 */     for (HtmlOption option : getOptions()) {
/* 293:448 */       if (option.getText().equals(text)) {
/* 294:449 */         return option;
/* 295:    */       }
/* 296:    */     }
/* 297:452 */     throw new ElementNotFoundException("option", "text", text);
/* 298:    */   }
/* 299:    */   
/* 300:    */   public String asText()
/* 301:    */   {
/* 302:    */     List<HtmlOption> options;
/* 303:    */     List<HtmlOption> options;
/* 304:467 */     if (isMultipleSelectEnabled()) {
/* 305:468 */       options = getOptions();
/* 306:    */     } else {
/* 307:471 */       options = getSelectedOptions();
/* 308:    */     }
/* 309:474 */     StringBuilder buffer = new StringBuilder();
/* 310:475 */     for (Iterator<HtmlOption> i = options.iterator(); i.hasNext();)
/* 311:    */     {
/* 312:476 */       HtmlOption currentOption = (HtmlOption)i.next();
/* 313:477 */       if (currentOption != null) {
/* 314:478 */         buffer.append(currentOption.asText());
/* 315:    */       }
/* 316:480 */       if (i.hasNext()) {
/* 317:481 */         buffer.append("\n");
/* 318:    */       }
/* 319:    */     }
/* 320:485 */     return buffer.toString();
/* 321:    */   }
/* 322:    */   
/* 323:    */   public final String getNameAttribute()
/* 324:    */   {
/* 325:495 */     return getAttribute("name");
/* 326:    */   }
/* 327:    */   
/* 328:    */   public final String getSizeAttribute()
/* 329:    */   {
/* 330:506 */     return getAttribute("size");
/* 331:    */   }
/* 332:    */   
/* 333:    */   public final String getMultipleAttribute()
/* 334:    */   {
/* 335:516 */     return getAttribute("multiple");
/* 336:    */   }
/* 337:    */   
/* 338:    */   public final String getDisabledAttribute()
/* 339:    */   {
/* 340:523 */     return getAttribute("disabled");
/* 341:    */   }
/* 342:    */   
/* 343:    */   public final boolean isDisabled()
/* 344:    */   {
/* 345:530 */     return hasAttribute("disabled");
/* 346:    */   }
/* 347:    */   
/* 348:    */   public final String getTabIndexAttribute()
/* 349:    */   {
/* 350:540 */     return getAttribute("tabindex");
/* 351:    */   }
/* 352:    */   
/* 353:    */   public final String getOnFocusAttribute()
/* 354:    */   {
/* 355:550 */     return getAttribute("onfocus");
/* 356:    */   }
/* 357:    */   
/* 358:    */   public final String getOnBlurAttribute()
/* 359:    */   {
/* 360:560 */     return getAttribute("onblur");
/* 361:    */   }
/* 362:    */   
/* 363:    */   public final String getOnChangeAttribute()
/* 364:    */   {
/* 365:570 */     return getAttribute("onchange");
/* 366:    */   }
/* 367:    */   
/* 368:    */   public void setAttributeNS(String namespaceURI, String qualifiedName, String attributeValue)
/* 369:    */   {
/* 370:578 */     if ("name".equals(qualifiedName))
/* 371:    */     {
/* 372:579 */       if (this.previousNames_.isEmpty()) {
/* 373:580 */         this.previousNames_ = new HashSet();
/* 374:    */       }
/* 375:582 */       this.previousNames_.add(attributeValue);
/* 376:    */     }
/* 377:584 */     super.setAttributeNS(namespaceURI, qualifiedName, attributeValue);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public String getOriginalName()
/* 381:    */   {
/* 382:591 */     return this.originalName_;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public Collection<String> getPreviousNames()
/* 386:    */   {
/* 387:598 */     return this.previousNames_;
/* 388:    */   }
/* 389:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.HtmlSelect
 * JD-Core Version:    0.7.0.1
 */