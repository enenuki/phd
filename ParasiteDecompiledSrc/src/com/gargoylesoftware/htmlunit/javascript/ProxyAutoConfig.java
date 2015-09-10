/*   1:    */ package com.gargoylesoftware.htmlunit.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.net.InetAddress;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.text.SimpleDateFormat;
/*   7:    */ import java.util.Calendar;
/*   8:    */ import java.util.TimeZone;
/*   9:    */ import java.util.regex.Pattern;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.FunctionObject;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.NativeFunction;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.ScriptableObject;
/*  16:    */ import net.sourceforge.htmlunit.corejs.javascript.Undefined;
/*  17:    */ 
/*  18:    */ public final class ProxyAutoConfig
/*  19:    */ {
/*  20: 42 */   private static final Pattern DOT_SPLIT_PATTERN = Pattern.compile("\\.");
/*  21:    */   
/*  22:    */   public static String evaluate(String content, URL url)
/*  23:    */   {
/*  24: 54 */     Context cx = ContextFactory.getGlobal().enterContext();
/*  25:    */     try
/*  26:    */     {
/*  27: 56 */       ProxyAutoConfig config = new ProxyAutoConfig();
/*  28: 57 */       Scriptable scope = cx.initStandardObjects();
/*  29:    */       
/*  30: 59 */       config.defineMethod("isPlainHostName", scope);
/*  31: 60 */       config.defineMethod("dnsDomainIs", scope);
/*  32: 61 */       config.defineMethod("localHostOrDomainIs", scope);
/*  33: 62 */       config.defineMethod("isResolvable", scope);
/*  34: 63 */       config.defineMethod("isInNet", scope);
/*  35: 64 */       config.defineMethod("dnsResolve", scope);
/*  36: 65 */       config.defineMethod("myIpAddress", scope);
/*  37: 66 */       config.defineMethod("dnsDomainLevels", scope);
/*  38: 67 */       config.defineMethod("shExpMatch", scope);
/*  39: 68 */       config.defineMethod("weekdayRange", scope);
/*  40: 69 */       config.defineMethod("dateRange", scope);
/*  41: 70 */       config.defineMethod("timeRange", scope);
/*  42:    */       
/*  43: 72 */       cx.evaluateString(scope, "var ProxyConfig = function() {}; ProxyConfig.bindings = {}", "<init>", 1, null);
/*  44: 73 */       cx.evaluateString(scope, content, "<Proxy Auto-Config>", 1, null);
/*  45: 74 */       Object[] functionArgs = { url.toExternalForm(), url.getHost() };
/*  46: 75 */       Object fObj = scope.get("FindProxyForURL", scope);
/*  47:    */       
/*  48: 77 */       NativeFunction f = (NativeFunction)fObj;
/*  49: 78 */       Object result = f.call(cx, scope, scope, functionArgs);
/*  50: 79 */       return Context.toString(result);
/*  51:    */     }
/*  52:    */     finally
/*  53:    */     {
/*  54: 82 */       Context.exit();
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   private void defineMethod(String methodName, Scriptable scope)
/*  59:    */   {
/*  60: 87 */     for (Method method : getClass().getMethods()) {
/*  61: 88 */       if (method.getName().equals(methodName))
/*  62:    */       {
/*  63: 89 */         FunctionObject functionObject = new FunctionObject(methodName, method, scope);
/*  64: 90 */         ((ScriptableObject)scope).defineProperty(methodName, functionObject, 0);
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static boolean isPlainHostName(String host)
/*  70:    */   {
/*  71:101 */     return host.indexOf('.') == -1;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static boolean dnsDomainIs(String host, String domain)
/*  75:    */   {
/*  76:111 */     return host.endsWith(domain);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static boolean localHostOrDomainIs(String host, String hostdom)
/*  80:    */   {
/*  81:123 */     return ((host.length() > 1) && (host.equals(hostdom))) || ((host.indexOf('.') == -1) && (hostdom.startsWith(host)));
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static boolean isResolvable(String host)
/*  85:    */   {
/*  86:132 */     return dnsResolve(host) != null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static boolean isInNet(String host, String pattern, String mask)
/*  90:    */   {
/*  91:145 */     String[] hostTokens = DOT_SPLIT_PATTERN.split(dnsResolve(host));
/*  92:146 */     String[] patternTokens = DOT_SPLIT_PATTERN.split(pattern);
/*  93:147 */     String[] maskTokens = DOT_SPLIT_PATTERN.split(mask);
/*  94:148 */     for (int i = 0; i < hostTokens.length; i++) {
/*  95:149 */       if ((Integer.parseInt(maskTokens[i]) != 0) && (!hostTokens[i].equals(patternTokens[i]))) {
/*  96:150 */         return false;
/*  97:    */       }
/*  98:    */     }
/*  99:153 */     return true;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static String dnsResolve(String host)
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:163 */       return InetAddress.getByName(host).getHostAddress();
/* 107:    */     }
/* 108:    */     catch (Exception e) {}
/* 109:166 */     return null;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static String myIpAddress()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:176 */       return InetAddress.getLocalHost().getHostAddress();
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:179 */       throw Context.throwAsScriptRuntimeEx(e);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static int dnsDomainLevels(String host)
/* 125:    */   {
/* 126:189 */     int levels = 0;
/* 127:190 */     for (int i = host.length() - 1; i >= 0; i--) {
/* 128:191 */       if (host.charAt(i) == '.') {
/* 129:192 */         levels++;
/* 130:    */       }
/* 131:    */     }
/* 132:195 */     return levels;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static boolean shExpMatch(String str, String shexp)
/* 136:    */   {
/* 137:205 */     String regexp = shexp.replace(".", "\\.").replace("*", ".*").replace("?", ".");
/* 138:206 */     return str.matches(regexp);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static boolean weekdayRange(String wd1, Object wd2, Object gmt)
/* 142:    */   {
/* 143:217 */     TimeZone timezone = TimeZone.getDefault();
/* 144:218 */     if (("GMT".equals(Context.toString(gmt))) || ("GMT".equals(Context.toString(wd2)))) {
/* 145:219 */       timezone = TimeZone.getTimeZone("GMT");
/* 146:    */     }
/* 147:221 */     if ((wd2 == Undefined.instance) || ("GMT".equals(Context.toString(wd2)))) {
/* 148:222 */       wd2 = wd1;
/* 149:    */     }
/* 150:224 */     Calendar calendar = Calendar.getInstance(timezone);
/* 151:225 */     for (int i = 0; i < 7; i++)
/* 152:    */     {
/* 153:226 */       String day = new SimpleDateFormat("EEE").format(calendar.getTime()).toUpperCase();
/* 154:227 */       if (day.equals(wd2)) {
/* 155:228 */         return true;
/* 156:    */       }
/* 157:230 */       if (day.equals(wd1)) {
/* 158:231 */         return i == 0;
/* 159:    */       }
/* 160:233 */       calendar.add(7, 1);
/* 161:    */     }
/* 162:235 */     return false;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static boolean dateRange(String value1, Object value2, Object value3, Object value4, Object value5, Object value6, Object value7)
/* 166:    */   {
/* 167:251 */     Object[] values = { value1, value2, value3, value4, value5, value6, value7 };
/* 168:252 */     TimeZone timezone = TimeZone.getDefault();
/* 169:256 */     for (int length = values.length - 1; length >= 0; length--)
/* 170:    */     {
/* 171:257 */       if ("GMT".equals(Context.toString(values[length])))
/* 172:    */       {
/* 173:258 */         timezone = TimeZone.getTimeZone("GMT");
/* 174:259 */         break;
/* 175:    */       }
/* 176:261 */       if (values[length] != Undefined.instance)
/* 177:    */       {
/* 178:262 */         length++;
/* 179:263 */         break;
/* 180:    */       }
/* 181:    */     }
/* 182:    */     Calendar cal1;
/* 183:    */     Calendar cal2;
/* 184:    */     int day1;
/* 185:    */     int month1;
/* 186:    */     int year1;
/* 187:    */     int day2;
/* 188:    */     int month2;
/* 189:    */     int year2;
/* 190:    */     int month1;
/* 191:    */     int month2;
/* 192:    */     Calendar cal1;
/* 193:    */     Calendar cal2;
/* 194:270 */     switch (length)
/* 195:    */     {
/* 196:    */     case 1: 
/* 197:272 */       int day = getSmallInt(value1);
/* 198:273 */       int month = dateRange_getMonth(value1);
/* 199:274 */       int year = dateRange_getYear(value1);
/* 200:275 */       cal1 = dateRange_createCalendar(timezone, day, month, year);
/* 201:276 */       cal2 = (Calendar)cal1.clone();
/* 202:277 */       break;
/* 203:    */     case 2: 
/* 204:280 */       day1 = getSmallInt(value1);
/* 205:281 */       month1 = dateRange_getMonth(value1);
/* 206:282 */       year1 = dateRange_getYear(value1);
/* 207:283 */       cal1 = dateRange_createCalendar(timezone, day1, month1, year1);
/* 208:284 */       day2 = getSmallInt(value2);
/* 209:285 */       month2 = dateRange_getMonth(value2);
/* 210:286 */       year2 = dateRange_getYear(value2);
/* 211:287 */       cal2 = dateRange_createCalendar(timezone, day2, month2, year2);
/* 212:288 */       break;
/* 213:    */     case 4: 
/* 214:291 */       day1 = getSmallInt(value1);
/* 215:292 */       if (day1 != -1)
/* 216:    */       {
/* 217:293 */         month1 = dateRange_getMonth(value2);
/* 218:294 */         day2 = getSmallInt(value3);
/* 219:295 */         month2 = dateRange_getMonth(value4);
/* 220:296 */         cal1 = dateRange_createCalendar(timezone, day1, month1, -1);
/* 221:297 */         cal2 = dateRange_createCalendar(timezone, day2, month2, -1);
/* 222:    */       }
/* 223:    */       else
/* 224:    */       {
/* 225:300 */         month1 = dateRange_getMonth(value1);
/* 226:301 */         year1 = dateRange_getMonth(value2);
/* 227:302 */         month2 = getSmallInt(value3);
/* 228:303 */         year2 = dateRange_getMonth(value4);
/* 229:304 */         cal1 = dateRange_createCalendar(timezone, -1, month1, year1);
/* 230:305 */         cal2 = dateRange_createCalendar(timezone, -1, month2, year2);
/* 231:    */       }
/* 232:307 */       break;
/* 233:    */     case 3: 
/* 234:    */     default: 
/* 235:310 */       day1 = getSmallInt(value1);
/* 236:311 */       month1 = dateRange_getMonth(value2);
/* 237:312 */       year1 = dateRange_getYear(value3);
/* 238:313 */       int day2 = getSmallInt(value4);
/* 239:314 */       month2 = dateRange_getMonth(value5);
/* 240:315 */       year2 = dateRange_getYear(value6);
/* 241:316 */       cal1 = dateRange_createCalendar(timezone, day1, month1, year1);
/* 242:317 */       cal2 = dateRange_createCalendar(timezone, day2, month2, year2);
/* 243:    */     }
/* 244:320 */     Calendar today = Calendar.getInstance(timezone);
/* 245:321 */     today.set(14, 0);
/* 246:322 */     today.set(13, 0);
/* 247:323 */     cal1.set(14, 0);
/* 248:324 */     cal1.set(13, 0);
/* 249:325 */     cal2.set(14, 0);
/* 250:326 */     cal2.set(13, 0);
/* 251:327 */     return (today.equals(cal1)) || ((today.after(cal1)) && (today.before(cal2))) || (today.equals(cal2));
/* 252:    */   }
/* 253:    */   
/* 254:    */   private static Calendar dateRange_createCalendar(TimeZone timezone, int day, int month, int year)
/* 255:    */   {
/* 256:332 */     Calendar calendar = Calendar.getInstance(timezone);
/* 257:333 */     if (day != -1) {
/* 258:334 */       calendar.set(5, day);
/* 259:    */     }
/* 260:336 */     if (month != -1) {
/* 261:337 */       calendar.set(2, month);
/* 262:    */     }
/* 263:339 */     if (year != -1) {
/* 264:340 */       calendar.set(1, year);
/* 265:    */     }
/* 266:342 */     return calendar;
/* 267:    */   }
/* 268:    */   
/* 269:    */   private static int getSmallInt(Object object)
/* 270:    */   {
/* 271:346 */     String s = Context.toString(object);
/* 272:347 */     if (Character.isDigit(s.charAt(0)))
/* 273:    */     {
/* 274:348 */       int i = Integer.parseInt(s);
/* 275:349 */       if (i < 70) {
/* 276:350 */         return i;
/* 277:    */       }
/* 278:    */     }
/* 279:353 */     return -1;
/* 280:    */   }
/* 281:    */   
/* 282:    */   private static int dateRange_getMonth(Object object)
/* 283:    */   {
/* 284:357 */     String s = Context.toString(object);
/* 285:358 */     if (Character.isLetter(s.charAt(0))) {
/* 286:    */       try
/* 287:    */       {
/* 288:360 */         Calendar cal = Calendar.getInstance();
/* 289:361 */         cal.clear();
/* 290:362 */         cal.setTime(new SimpleDateFormat("MMM").parse(s));
/* 291:363 */         return cal.get(2);
/* 292:    */       }
/* 293:    */       catch (Exception e) {}
/* 294:    */     }
/* 295:369 */     return -1;
/* 296:    */   }
/* 297:    */   
/* 298:    */   private static int dateRange_getYear(Object object)
/* 299:    */   {
/* 300:373 */     String s = Context.toString(object);
/* 301:374 */     if (Character.isDigit(s.charAt(0)))
/* 302:    */     {
/* 303:375 */       int i = Integer.parseInt(s);
/* 304:376 */       if (i > 1000) {
/* 305:377 */         return i;
/* 306:    */       }
/* 307:    */     }
/* 308:380 */     return -1;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public static boolean timeRange(String value1, Object value2, Object value3, Object value4, Object value5, Object value6, Object value7)
/* 312:    */   {
/* 313:396 */     Object[] values = { value1, value2, value3, value4, value5, value6, value7 };
/* 314:397 */     TimeZone timezone = TimeZone.getDefault();
/* 315:401 */     for (int length = values.length - 1; length >= 0; length--)
/* 316:    */     {
/* 317:402 */       if ("GMT".equals(Context.toString(values[length])))
/* 318:    */       {
/* 319:403 */         timezone = TimeZone.getTimeZone("GMT");
/* 320:404 */         break;
/* 321:    */       }
/* 322:406 */       if (values[length] != Undefined.instance)
/* 323:    */       {
/* 324:407 */         length++;
/* 325:408 */         break;
/* 326:    */       }
/* 327:    */     }
/* 328:    */     int hour1;
/* 329:    */     Calendar cal1;
/* 330:    */     Calendar cal2;
/* 331:    */     int hour2;
/* 332:    */     int min1;
/* 333:    */     int min2;
/* 334:415 */     switch (length)
/* 335:    */     {
/* 336:    */     case 1: 
/* 337:417 */       hour1 = getSmallInt(value1);
/* 338:418 */       cal1 = timeRange_createCalendar(timezone, hour1, -1, -1);
/* 339:419 */       cal2 = (Calendar)cal1.clone();
/* 340:420 */       cal2.add(11, 1);
/* 341:421 */       break;
/* 342:    */     case 2: 
/* 343:424 */       hour1 = getSmallInt(value1);
/* 344:425 */       cal1 = timeRange_createCalendar(timezone, hour1, -1, -1);
/* 345:426 */       hour2 = getSmallInt(value2);
/* 346:427 */       cal2 = timeRange_createCalendar(timezone, hour2, -1, -1);
/* 347:428 */       break;
/* 348:    */     case 4: 
/* 349:431 */       hour1 = getSmallInt(value1);
/* 350:432 */       min1 = getSmallInt(value2);
/* 351:433 */       hour2 = getSmallInt(value3);
/* 352:434 */       min2 = getSmallInt(value4);
/* 353:435 */       cal1 = dateRange_createCalendar(timezone, hour1, min1, -1);
/* 354:436 */       cal2 = dateRange_createCalendar(timezone, hour2, min2, -1);
/* 355:437 */       break;
/* 356:    */     case 3: 
/* 357:    */     default: 
/* 358:440 */       hour1 = getSmallInt(value1);
/* 359:441 */       min1 = getSmallInt(value2);
/* 360:442 */       int second1 = getSmallInt(value3);
/* 361:443 */       hour2 = getSmallInt(value4);
/* 362:444 */       min2 = getSmallInt(value5);
/* 363:445 */       int second2 = getSmallInt(value6);
/* 364:446 */       cal1 = dateRange_createCalendar(timezone, hour1, min1, second1);
/* 365:447 */       cal2 = dateRange_createCalendar(timezone, hour2, min2, second2);
/* 366:    */     }
/* 367:450 */     Calendar now = Calendar.getInstance(timezone);
/* 368:451 */     return (now.equals(cal1)) || ((now.after(cal1)) && (now.before(cal2))) || (now.equals(cal2));
/* 369:    */   }
/* 370:    */   
/* 371:    */   private static Calendar timeRange_createCalendar(TimeZone timezone, int hour, int minute, int second)
/* 372:    */   {
/* 373:456 */     Calendar calendar = Calendar.getInstance(timezone);
/* 374:457 */     if (hour != -1) {
/* 375:458 */       calendar.set(11, hour);
/* 376:    */     }
/* 377:460 */     if (minute != -1) {
/* 378:461 */       calendar.set(12, minute);
/* 379:    */     }
/* 380:463 */     if (second != -1) {
/* 381:464 */       calendar.set(13, second);
/* 382:    */     }
/* 383:466 */     return calendar;
/* 384:    */   }
/* 385:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.ProxyAutoConfig
 * JD-Core Version:    0.7.0.1
 */