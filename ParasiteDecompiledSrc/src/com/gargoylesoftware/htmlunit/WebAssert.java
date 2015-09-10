/*   1:    */ package com.gargoylesoftware.htmlunit;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
/*   4:    */ import com.gargoylesoftware.htmlunit.html.HtmlElement;
/*   5:    */ import com.gargoylesoftware.htmlunit.html.HtmlInput;
/*   6:    */ import com.gargoylesoftware.htmlunit.html.HtmlPage;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.List;
/*  10:    */ 
/*  11:    */ public final class WebAssert
/*  12:    */ {
/*  13:    */   public static void assertTitleEquals(HtmlPage page, String title)
/*  14:    */   {
/*  15: 50 */     String s = page.getTitleText();
/*  16: 51 */     if (!s.equals(title))
/*  17:    */     {
/*  18: 52 */       String msg = "Actual page title '" + s + "' does not match expected page title '" + title + "'.";
/*  19: 53 */       throw new AssertionError(msg);
/*  20:    */     }
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static void assertTitleContains(HtmlPage page, String titlePortion)
/*  24:    */   {
/*  25: 64 */     String s = page.getTitleText();
/*  26: 65 */     if (s.indexOf(titlePortion) == -1)
/*  27:    */     {
/*  28: 66 */       String msg = "Page title '" + s + "' does not contain the substring '" + titlePortion + "'.";
/*  29: 67 */       throw new AssertionError(msg);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static void assertTitleMatches(HtmlPage page, String regex)
/*  34:    */   {
/*  35: 78 */     String s = page.getTitleText();
/*  36: 79 */     if (!s.matches(regex))
/*  37:    */     {
/*  38: 80 */       String msg = "Page title '" + s + "' does not match the regular expression '" + regex + "'.";
/*  39: 81 */       throw new AssertionError(msg);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static void assertElementPresent(HtmlPage page, String id)
/*  44:    */   {
/*  45:    */     try
/*  46:    */     {
/*  47: 93 */       page.getHtmlElementById(id);
/*  48:    */     }
/*  49:    */     catch (ElementNotFoundException e)
/*  50:    */     {
/*  51: 96 */       String msg = "The page does not contain an element with ID '" + id + "'.";
/*  52: 97 */       throw new AssertionError(msg);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static void assertElementPresentByXPath(HtmlPage page, String xpath)
/*  57:    */   {
/*  58:108 */     List<?> elements = page.getByXPath(xpath);
/*  59:109 */     if (elements.isEmpty())
/*  60:    */     {
/*  61:110 */       String msg = "The page does not contain any elements matching the XPath expression '" + xpath + "'.";
/*  62:    */       
/*  63:112 */       throw new AssertionError(msg);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void assertElementNotPresent(HtmlPage page, String id)
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:124 */       page.getHtmlElementById(id);
/*  72:    */     }
/*  73:    */     catch (ElementNotFoundException e)
/*  74:    */     {
/*  75:127 */       return;
/*  76:    */     }
/*  77:129 */     String msg = "The page contains an element with ID '" + id + "'.";
/*  78:130 */     throw new AssertionError(msg);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static void assertElementNotPresentByXPath(HtmlPage page, String xpath)
/*  82:    */   {
/*  83:141 */     List<?> elements = page.getByXPath(xpath);
/*  84:142 */     if (!elements.isEmpty())
/*  85:    */     {
/*  86:143 */       String msg = "The page does not contain any elements matching the XPath expression '" + xpath + "'.";
/*  87:    */       
/*  88:145 */       throw new AssertionError(msg);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static void assertTextPresent(HtmlPage page, String text)
/*  93:    */   {
/*  94:156 */     if (page.asText().indexOf(text) == -1)
/*  95:    */     {
/*  96:157 */       String msg = "The page does not contain the text '" + text + "'.";
/*  97:158 */       throw new AssertionError(msg);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void assertTextPresentInElement(HtmlPage page, String text, String id)
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:172 */       HtmlElement element = page.getHtmlElementById(id);
/* 106:173 */       if (element.asText().indexOf(text) == -1)
/* 107:    */       {
/* 108:174 */         String msg = "The element with ID '" + id + "' does not contain the text '" + text + "'.";
/* 109:175 */         throw new AssertionError(msg);
/* 110:    */       }
/* 111:    */     }
/* 112:    */     catch (ElementNotFoundException e)
/* 113:    */     {
/* 114:179 */       String msg = "Unable to verify that the element with ID '" + id + "' contains the text '" + text + "' because the specified element does not exist.";
/* 115:    */       
/* 116:181 */       throw new AssertionError(msg);
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static void assertTextNotPresent(HtmlPage page, String text)
/* 121:    */   {
/* 122:192 */     if (page.asText().contains(text))
/* 123:    */     {
/* 124:193 */       String msg = "The page contains the text '" + text + "'.";
/* 125:194 */       throw new AssertionError(msg);
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public static void assertTextNotPresentInElement(HtmlPage page, String text, String id)
/* 130:    */   {
/* 131:    */     try
/* 132:    */     {
/* 133:208 */       HtmlElement element = page.getHtmlElementById(id);
/* 134:209 */       if (element.asText().contains(text))
/* 135:    */       {
/* 136:210 */         String msg = "The element with ID '" + id + "' contains the text '" + text + "'.";
/* 137:211 */         throw new AssertionError(msg);
/* 138:    */       }
/* 139:    */     }
/* 140:    */     catch (ElementNotFoundException e)
/* 141:    */     {
/* 142:215 */       String msg = "Unable to verify that the element with ID '" + id + "' does not contain the text '" + text + "' because the specified element does not exist.";
/* 143:    */       
/* 144:217 */       throw new AssertionError(msg);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void assertLinkPresent(HtmlPage page, String id)
/* 149:    */   {
/* 150:    */     try
/* 151:    */     {
/* 152:229 */       page.getDocumentElement().getOneHtmlElementByAttribute("a", "id", id);
/* 153:    */     }
/* 154:    */     catch (ElementNotFoundException e)
/* 155:    */     {
/* 156:232 */       String msg = "The page does not contain a link with ID '" + id + "'.";
/* 157:233 */       throw new AssertionError(msg);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static void assertLinkNotPresent(HtmlPage page, String id)
/* 162:    */   {
/* 163:    */     try
/* 164:    */     {
/* 165:245 */       page.getDocumentElement().getOneHtmlElementByAttribute("a", "id", id);
/* 166:    */       
/* 167:247 */       String msg = "The page contains a link with ID '" + id + "'.";
/* 168:248 */       throw new AssertionError(msg);
/* 169:    */     }
/* 170:    */     catch (ElementNotFoundException e) {}
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static void assertLinkPresentWithText(HtmlPage page, String text)
/* 174:    */   {
/* 175:263 */     boolean found = false;
/* 176:264 */     for (HtmlAnchor a : page.getAnchors()) {
/* 177:265 */       if (a.asText().contains(text))
/* 178:    */       {
/* 179:266 */         found = true;
/* 180:267 */         break;
/* 181:    */       }
/* 182:    */     }
/* 183:270 */     if (!found)
/* 184:    */     {
/* 185:271 */       String msg = "The page does not contain a link with text '" + text + "'.";
/* 186:272 */       throw new AssertionError(msg);
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static void assertLinkNotPresentWithText(HtmlPage page, String text)
/* 191:    */   {
/* 192:284 */     boolean found = false;
/* 193:285 */     for (HtmlAnchor a : page.getAnchors()) {
/* 194:286 */       if (a.asText().contains(text))
/* 195:    */       {
/* 196:287 */         found = true;
/* 197:288 */         break;
/* 198:    */       }
/* 199:    */     }
/* 200:291 */     if (found)
/* 201:    */     {
/* 202:292 */       String msg = "The page contains a link with text '" + text + "'.";
/* 203:293 */       throw new AssertionError(msg);
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static void assertFormPresent(HtmlPage page, String name)
/* 208:    */   {
/* 209:    */     try
/* 210:    */     {
/* 211:305 */       page.getFormByName(name);
/* 212:    */     }
/* 213:    */     catch (ElementNotFoundException e)
/* 214:    */     {
/* 215:308 */       String msg = "The page does not contain a form named '" + name + "'.";
/* 216:309 */       throw new AssertionError(msg);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public static void assertFormNotPresent(HtmlPage page, String name)
/* 221:    */   {
/* 222:    */     try
/* 223:    */     {
/* 224:321 */       page.getFormByName(name);
/* 225:    */     }
/* 226:    */     catch (ElementNotFoundException e)
/* 227:    */     {
/* 228:324 */       return;
/* 229:    */     }
/* 230:326 */     String msg = "The page contains a form named '" + name + "'.";
/* 231:327 */     throw new AssertionError(msg);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static void assertInputPresent(HtmlPage page, String name)
/* 235:    */   {
/* 236:337 */     String xpath = "//input[@name='" + name + "']";
/* 237:338 */     List<?> list = page.getByXPath(xpath);
/* 238:339 */     if (list.isEmpty()) {
/* 239:340 */       throw new AssertionError("Unable to find an input element named '" + name + "'.");
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public static void assertInputNotPresent(HtmlPage page, String name)
/* 244:    */   {
/* 245:351 */     String xpath = "//input[@name='" + name + "']";
/* 246:352 */     List<?> list = page.getByXPath(xpath);
/* 247:353 */     if (!list.isEmpty()) {
/* 248:354 */       throw new AssertionError("Unable to find an input element named '" + name + "'.");
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public static void assertInputContainsValue(HtmlPage page, String name, String value)
/* 253:    */   {
/* 254:367 */     String xpath = "//input[@name='" + name + "']";
/* 255:368 */     List<?> list = page.getByXPath(xpath);
/* 256:369 */     if (list.isEmpty()) {
/* 257:370 */       throw new AssertionError("Unable to find an input element named '" + name + "'.");
/* 258:    */     }
/* 259:372 */     HtmlInput input = (HtmlInput)list.get(0);
/* 260:373 */     String s = input.getValueAttribute();
/* 261:374 */     if (!s.equals(value)) {
/* 262:375 */       throw new AssertionError("The input element named '" + name + "' contains the value '" + s + "', not the expected value '" + value + "'.");
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public static void assertInputDoesNotContainValue(HtmlPage page, String name, String value)
/* 267:    */   {
/* 268:389 */     String xpath = "//input[@name='" + name + "']";
/* 269:390 */     List<?> list = page.getByXPath(xpath);
/* 270:391 */     if (list.isEmpty()) {
/* 271:392 */       throw new AssertionError("Unable to find an input element named '" + name + "'.");
/* 272:    */     }
/* 273:394 */     HtmlInput input = (HtmlInput)list.get(0);
/* 274:395 */     String s = input.getValueAttribute();
/* 275:396 */     if (s.equals(value)) {
/* 276:397 */       throw new AssertionError("The input element named '" + name + "' contains the value '" + s + "', not the expected value '" + value + "'.");
/* 277:    */     }
/* 278:    */   }
/* 279:    */   
/* 280:    */   public static void assertAllTabIndexAttributesSet(HtmlPage page)
/* 281:    */   {
/* 282:414 */     List<String> tags = Arrays.asList(new String[] { "a", "area", "button", "input", "object", "select", "textarea" });
/* 283:416 */     for (HtmlElement element : page.getDocumentElement().getHtmlElementsByTagNames(tags))
/* 284:    */     {
/* 285:417 */       Short tabIndex = element.getTabIndex();
/* 286:418 */       if ((tabIndex == null) || (tabIndex == HtmlElement.TAB_INDEX_OUT_OF_BOUNDS))
/* 287:    */       {
/* 288:419 */         String s = element.getAttribute("tabindex");
/* 289:420 */         throw new AssertionError("Illegal value for tab index: '" + s + "'.");
/* 290:    */       }
/* 291:    */     }
/* 292:    */   }
/* 293:    */   
/* 294:    */   public static void assertAllAccessKeyAttributesUnique(HtmlPage page)
/* 295:    */   {
/* 296:433 */     List<String> list = new ArrayList();
/* 297:434 */     for (HtmlElement element : page.getHtmlElementDescendants())
/* 298:    */     {
/* 299:435 */       String key = element.getAttribute("accesskey");
/* 300:436 */       if ((key != null) && (key.length() != 0))
/* 301:    */       {
/* 302:437 */         if (list.contains(key)) {
/* 303:438 */           throw new AssertionError("The access key '" + key + "' is not unique.");
/* 304:    */         }
/* 305:440 */         list.add(key);
/* 306:    */       }
/* 307:    */     }
/* 308:    */   }
/* 309:    */   
/* 310:    */   public static void assertAllIdAttributesUnique(HtmlPage page)
/* 311:    */   {
/* 312:451 */     List<String> list = new ArrayList();
/* 313:452 */     for (HtmlElement element : page.getHtmlElementDescendants())
/* 314:    */     {
/* 315:453 */       String id = element.getAttribute("id");
/* 316:454 */       if ((id != null) && (id.length() != 0))
/* 317:    */       {
/* 318:455 */         if (list.contains(id)) {
/* 319:456 */           throw new AssertionError("The element ID '" + id + "' is not unique.");
/* 320:    */         }
/* 321:458 */         list.add(id);
/* 322:    */       }
/* 323:    */     }
/* 324:    */   }
/* 325:    */   
/* 326:    */   public static void notNull(String description, Object object)
/* 327:    */   {
/* 328:470 */     if (object == null) {
/* 329:471 */       throw new NullPointerException(description);
/* 330:    */     }
/* 331:    */   }
/* 332:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.WebAssert
 * JD-Core Version:    0.7.0.1
 */