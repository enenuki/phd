/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.text.DateFormat;
/*    4:     */ import java.text.ParseException;
/*    5:     */ import java.text.SimpleDateFormat;
/*    6:     */ import java.util.Date;
/*    7:     */ import java.util.SimpleTimeZone;
/*    8:     */ import java.util.TimeZone;
/*    9:     */ 
/*   10:     */ final class NativeDate
/*   11:     */   extends IdScriptableObject
/*   12:     */ {
/*   13:     */   static final long serialVersionUID = -8307438915861678966L;
/*   14:  61 */   private static final Object DATE_TAG = "Date";
/*   15:     */   private static final String js_NaN_date_str = "Invalid Date";
/*   16:  67 */   private static final DateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
/*   17:     */   private static final double HalfTimeDomain = 8640000000000000.0D;
/*   18:     */   private static final double HoursPerDay = 24.0D;
/*   19:     */   private static final double MinutesPerHour = 60.0D;
/*   20:     */   private static final double SecondsPerMinute = 60.0D;
/*   21:     */   private static final double msPerSecond = 1000.0D;
/*   22:     */   private static final double MinutesPerDay = 1440.0D;
/*   23:     */   private static final double SecondsPerDay = 86400.0D;
/*   24:     */   private static final double SecondsPerHour = 3600.0D;
/*   25:     */   private static final double msPerDay = 86400000.0D;
/*   26:     */   private static final double msPerHour = 3600000.0D;
/*   27:     */   private static final double msPerMinute = 60000.0D;
/*   28:     */   private static final boolean TZO_WORKAROUND = false;
/*   29:     */   private static final int MAXARGS = 7;
/*   30:     */   private static final int ConstructorId_now = -3;
/*   31:     */   private static final int ConstructorId_parse = -2;
/*   32:     */   private static final int ConstructorId_UTC = -1;
/*   33:     */   private static final int Id_constructor = 1;
/*   34:     */   private static final int Id_toString = 2;
/*   35:     */   private static final int Id_toTimeString = 3;
/*   36:     */   private static final int Id_toDateString = 4;
/*   37:     */   private static final int Id_toLocaleString = 5;
/*   38:     */   private static final int Id_toLocaleTimeString = 6;
/*   39:     */   private static final int Id_toLocaleDateString = 7;
/*   40:     */   private static final int Id_toUTCString = 8;
/*   41:     */   private static final int Id_toSource = 9;
/*   42:     */   private static final int Id_valueOf = 10;
/*   43:     */   private static final int Id_getTime = 11;
/*   44:     */   private static final int Id_getYear = 12;
/*   45:     */   private static final int Id_getFullYear = 13;
/*   46:     */   private static final int Id_getUTCFullYear = 14;
/*   47:     */   private static final int Id_getMonth = 15;
/*   48:     */   private static final int Id_getUTCMonth = 16;
/*   49:     */   private static final int Id_getDate = 17;
/*   50:     */   private static final int Id_getUTCDate = 18;
/*   51:     */   private static final int Id_getDay = 19;
/*   52:     */   private static final int Id_getUTCDay = 20;
/*   53:     */   private static final int Id_getHours = 21;
/*   54:     */   private static final int Id_getUTCHours = 22;
/*   55:     */   private static final int Id_getMinutes = 23;
/*   56:     */   private static final int Id_getUTCMinutes = 24;
/*   57:     */   private static final int Id_getSeconds = 25;
/*   58:     */   private static final int Id_getUTCSeconds = 26;
/*   59:     */   private static final int Id_getMilliseconds = 27;
/*   60:     */   private static final int Id_getUTCMilliseconds = 28;
/*   61:     */   private static final int Id_getTimezoneOffset = 29;
/*   62:     */   private static final int Id_setTime = 30;
/*   63:     */   private static final int Id_setMilliseconds = 31;
/*   64:     */   private static final int Id_setUTCMilliseconds = 32;
/*   65:     */   private static final int Id_setSeconds = 33;
/*   66:     */   private static final int Id_setUTCSeconds = 34;
/*   67:     */   private static final int Id_setMinutes = 35;
/*   68:     */   private static final int Id_setUTCMinutes = 36;
/*   69:     */   private static final int Id_setHours = 37;
/*   70:     */   private static final int Id_setUTCHours = 38;
/*   71:     */   private static final int Id_setDate = 39;
/*   72:     */   private static final int Id_setUTCDate = 40;
/*   73:     */   private static final int Id_setMonth = 41;
/*   74:     */   private static final int Id_setUTCMonth = 42;
/*   75:     */   private static final int Id_setFullYear = 43;
/*   76:     */   private static final int Id_setUTCFullYear = 44;
/*   77:     */   private static final int Id_setYear = 45;
/*   78:     */   private static final int Id_toISOString = 46;
/*   79:     */   private static final int Id_toJSON = 47;
/*   80:     */   private static final int MAX_PROTOTYPE_ID = 47;
/*   81:     */   private static final int Id_toGMTString = 8;
/*   82:     */   private static TimeZone thisTimeZone;
/*   83:     */   private static double LocalTZA;
/*   84:     */   private static DateFormat timeZoneFormatter;
/*   85:     */   private static DateFormat localeDateTimeFormatter;
/*   86:     */   private static DateFormat localeDateFormatter;
/*   87:     */   private static DateFormat localeTimeFormatter;
/*   88:     */   private double date;
/*   89:     */   
/*   90:     */   static
/*   91:     */   {
/*   92:  68 */     isoFormat.setTimeZone(new SimpleTimeZone(0, "UTC"));
/*   93:  69 */     isoFormat.setLenient(false);
/*   94:     */   }
/*   95:     */   
/*   96:     */   static void init(Scriptable scope, boolean sealed)
/*   97:     */   {
/*   98:  74 */     NativeDate obj = new NativeDate();
/*   99:     */     
/*  100:  76 */     obj.date = ScriptRuntime.NaN;
/*  101:  77 */     obj.exportAsJSClass(47, scope, sealed);
/*  102:     */   }
/*  103:     */   
/*  104:     */   private NativeDate()
/*  105:     */   {
/*  106:  82 */     if (thisTimeZone == null)
/*  107:     */     {
/*  108:  85 */       thisTimeZone = TimeZone.getDefault();
/*  109:  86 */       LocalTZA = thisTimeZone.getRawOffset();
/*  110:     */     }
/*  111:     */   }
/*  112:     */   
/*  113:     */   public String getClassName()
/*  114:     */   {
/*  115:  93 */     return "Date";
/*  116:     */   }
/*  117:     */   
/*  118:     */   public Object getDefaultValue(Class<?> typeHint)
/*  119:     */   {
/*  120:  99 */     if (typeHint == null) {
/*  121: 100 */       typeHint = ScriptRuntime.StringClass;
/*  122:     */     }
/*  123: 101 */     return super.getDefaultValue(typeHint);
/*  124:     */   }
/*  125:     */   
/*  126:     */   double getJSTimeValue()
/*  127:     */   {
/*  128: 106 */     return this.date;
/*  129:     */   }
/*  130:     */   
/*  131:     */   protected void fillConstructorProperties(IdFunctionObject ctor)
/*  132:     */   {
/*  133: 112 */     addIdFunctionProperty(ctor, DATE_TAG, -3, "now", 0);
/*  134:     */     
/*  135: 114 */     addIdFunctionProperty(ctor, DATE_TAG, -2, "parse", 1);
/*  136:     */     
/*  137: 116 */     addIdFunctionProperty(ctor, DATE_TAG, -1, "UTC", 1);
/*  138:     */     
/*  139: 118 */     super.fillConstructorProperties(ctor);
/*  140:     */   }
/*  141:     */   
/*  142:     */   protected void initPrototypeId(int id)
/*  143:     */   {
/*  144:     */     int arity;
/*  145:     */     String s;
/*  146: 126 */     switch (id)
/*  147:     */     {
/*  148:     */     case 1: 
/*  149: 127 */       arity = 1;s = "constructor"; break;
/*  150:     */     case 2: 
/*  151: 128 */       arity = 0;s = "toString"; break;
/*  152:     */     case 3: 
/*  153: 129 */       arity = 0;s = "toTimeString"; break;
/*  154:     */     case 4: 
/*  155: 130 */       arity = 0;s = "toDateString"; break;
/*  156:     */     case 5: 
/*  157: 131 */       arity = 0;s = "toLocaleString"; break;
/*  158:     */     case 6: 
/*  159: 132 */       arity = 0;s = "toLocaleTimeString"; break;
/*  160:     */     case 7: 
/*  161: 133 */       arity = 0;s = "toLocaleDateString"; break;
/*  162:     */     case 8: 
/*  163: 134 */       arity = 0;s = "toUTCString"; break;
/*  164:     */     case 9: 
/*  165: 135 */       arity = 0;s = "toSource"; break;
/*  166:     */     case 10: 
/*  167: 136 */       arity = 0;s = "valueOf"; break;
/*  168:     */     case 11: 
/*  169: 137 */       arity = 0;s = "getTime"; break;
/*  170:     */     case 12: 
/*  171: 138 */       arity = 0;s = "getYear"; break;
/*  172:     */     case 13: 
/*  173: 139 */       arity = 0;s = "getFullYear"; break;
/*  174:     */     case 14: 
/*  175: 140 */       arity = 0;s = "getUTCFullYear"; break;
/*  176:     */     case 15: 
/*  177: 141 */       arity = 0;s = "getMonth"; break;
/*  178:     */     case 16: 
/*  179: 142 */       arity = 0;s = "getUTCMonth"; break;
/*  180:     */     case 17: 
/*  181: 143 */       arity = 0;s = "getDate"; break;
/*  182:     */     case 18: 
/*  183: 144 */       arity = 0;s = "getUTCDate"; break;
/*  184:     */     case 19: 
/*  185: 145 */       arity = 0;s = "getDay"; break;
/*  186:     */     case 20: 
/*  187: 146 */       arity = 0;s = "getUTCDay"; break;
/*  188:     */     case 21: 
/*  189: 147 */       arity = 0;s = "getHours"; break;
/*  190:     */     case 22: 
/*  191: 148 */       arity = 0;s = "getUTCHours"; break;
/*  192:     */     case 23: 
/*  193: 149 */       arity = 0;s = "getMinutes"; break;
/*  194:     */     case 24: 
/*  195: 150 */       arity = 0;s = "getUTCMinutes"; break;
/*  196:     */     case 25: 
/*  197: 151 */       arity = 0;s = "getSeconds"; break;
/*  198:     */     case 26: 
/*  199: 152 */       arity = 0;s = "getUTCSeconds"; break;
/*  200:     */     case 27: 
/*  201: 153 */       arity = 0;s = "getMilliseconds"; break;
/*  202:     */     case 28: 
/*  203: 154 */       arity = 0;s = "getUTCMilliseconds"; break;
/*  204:     */     case 29: 
/*  205: 155 */       arity = 0;s = "getTimezoneOffset"; break;
/*  206:     */     case 30: 
/*  207: 156 */       arity = 1;s = "setTime"; break;
/*  208:     */     case 31: 
/*  209: 157 */       arity = 1;s = "setMilliseconds"; break;
/*  210:     */     case 32: 
/*  211: 158 */       arity = 1;s = "setUTCMilliseconds"; break;
/*  212:     */     case 33: 
/*  213: 159 */       arity = 2;s = "setSeconds"; break;
/*  214:     */     case 34: 
/*  215: 160 */       arity = 2;s = "setUTCSeconds"; break;
/*  216:     */     case 35: 
/*  217: 161 */       arity = 3;s = "setMinutes"; break;
/*  218:     */     case 36: 
/*  219: 162 */       arity = 3;s = "setUTCMinutes"; break;
/*  220:     */     case 37: 
/*  221: 163 */       arity = 4;s = "setHours"; break;
/*  222:     */     case 38: 
/*  223: 164 */       arity = 4;s = "setUTCHours"; break;
/*  224:     */     case 39: 
/*  225: 165 */       arity = 1;s = "setDate"; break;
/*  226:     */     case 40: 
/*  227: 166 */       arity = 1;s = "setUTCDate"; break;
/*  228:     */     case 41: 
/*  229: 167 */       arity = 2;s = "setMonth"; break;
/*  230:     */     case 42: 
/*  231: 168 */       arity = 2;s = "setUTCMonth"; break;
/*  232:     */     case 43: 
/*  233: 169 */       arity = 3;s = "setFullYear"; break;
/*  234:     */     case 44: 
/*  235: 170 */       arity = 3;s = "setUTCFullYear"; break;
/*  236:     */     case 45: 
/*  237: 171 */       arity = 1;s = "setYear"; break;
/*  238:     */     case 46: 
/*  239: 172 */       arity = 0;s = "toISOString"; break;
/*  240:     */     case 47: 
/*  241: 173 */       arity = 1;s = "toJSON"; break;
/*  242:     */     default: 
/*  243: 174 */       throw new IllegalArgumentException(String.valueOf(id));
/*  244:     */     }
/*  245: 176 */     initPrototypeMethod(DATE_TAG, id, s, arity);
/*  246:     */   }
/*  247:     */   
/*  248:     */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  249:     */   {
/*  250: 183 */     if (!f.hasTag(DATE_TAG)) {
/*  251: 184 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  252:     */     }
/*  253: 186 */     int id = f.methodId();
/*  254: 187 */     switch (id)
/*  255:     */     {
/*  256:     */     case -3: 
/*  257: 189 */       return ScriptRuntime.wrapNumber(now());
/*  258:     */     case -2: 
/*  259: 193 */       String dataStr = ScriptRuntime.toString(args, 0);
/*  260: 194 */       return ScriptRuntime.wrapNumber(date_parseString(dataStr));
/*  261:     */     case -1: 
/*  262: 198 */       return ScriptRuntime.wrapNumber(jsStaticFunction_UTC(args));
/*  263:     */     case 1: 
/*  264: 204 */       if (thisObj != null) {
/*  265: 205 */         return date_format(now(), 2);
/*  266:     */       }
/*  267: 206 */       return jsConstructor(args);
/*  268:     */     case 47: 
/*  269: 211 */       if ((thisObj instanceof NativeDate)) {
/*  270: 212 */         return ((NativeDate)thisObj).toISOString();
/*  271:     */       }
/*  272: 215 */       String toISOString = "toISOString";
/*  273:     */       
/*  274: 217 */       Scriptable o = ScriptRuntime.toObject(cx, scope, thisObj);
/*  275: 218 */       Object tv = ScriptRuntime.toPrimitive(o, ScriptRuntime.NumberClass);
/*  276: 219 */       if ((tv instanceof Number))
/*  277:     */       {
/*  278: 220 */         double d = ((Number)tv).doubleValue();
/*  279: 221 */         if ((d != d) || (Double.isInfinite(d))) {
/*  280: 222 */           return null;
/*  281:     */         }
/*  282:     */       }
/*  283: 225 */       Object toISO = o.get("toISOString", o);
/*  284: 226 */       if (toISO == NOT_FOUND) {
/*  285: 227 */         throw ScriptRuntime.typeError2("msg.function.not.found.in", "toISOString", ScriptRuntime.toString(o));
/*  286:     */       }
/*  287: 231 */       if (!(toISO instanceof Callable)) {
/*  288: 232 */         throw ScriptRuntime.typeError3("msg.isnt.function.in", "toISOString", ScriptRuntime.toString(o), ScriptRuntime.toString(toISO));
/*  289:     */       }
/*  290: 237 */       Object result = ((Callable)toISO).call(cx, scope, o, ScriptRuntime.emptyArgs);
/*  291: 239 */       if (!ScriptRuntime.isPrimitive(result)) {
/*  292: 240 */         throw ScriptRuntime.typeError1("msg.toisostring.must.return.primitive", ScriptRuntime.toString(result));
/*  293:     */       }
/*  294: 243 */       return result;
/*  295:     */     }
/*  296: 250 */     if (!(thisObj instanceof NativeDate)) {
/*  297: 251 */       throw incompatibleCallError(f);
/*  298:     */     }
/*  299: 252 */     NativeDate realThis = (NativeDate)thisObj;
/*  300: 253 */     double t = realThis.date;
/*  301: 255 */     switch (id)
/*  302:     */     {
/*  303:     */     case 2: 
/*  304:     */     case 3: 
/*  305:     */     case 4: 
/*  306: 260 */       if (t == t) {
/*  307: 261 */         return date_format(t, id);
/*  308:     */       }
/*  309: 263 */       return "Invalid Date";
/*  310:     */     case 5: 
/*  311:     */     case 6: 
/*  312:     */     case 7: 
/*  313: 268 */       if (t == t) {
/*  314: 269 */         return toLocale_helper(t, id);
/*  315:     */       }
/*  316: 271 */       return "Invalid Date";
/*  317:     */     case 8: 
/*  318: 274 */       if (t == t) {
/*  319: 275 */         return js_toUTCString(t);
/*  320:     */       }
/*  321: 277 */       return "Invalid Date";
/*  322:     */     case 9: 
/*  323: 280 */       return "(new Date(" + ScriptRuntime.toString(t) + "))";
/*  324:     */     case 10: 
/*  325:     */     case 11: 
/*  326: 284 */       return ScriptRuntime.wrapNumber(t);
/*  327:     */     case 12: 
/*  328:     */     case 13: 
/*  329:     */     case 14: 
/*  330: 289 */       if (t == t)
/*  331:     */       {
/*  332: 290 */         if (id != 14) {
/*  333: 290 */           t = LocalTime(t);
/*  334:     */         }
/*  335: 291 */         t = YearFromTime(t);
/*  336: 292 */         if (id == 12) {
/*  337: 293 */           if (cx.hasFeature(1))
/*  338:     */           {
/*  339: 294 */             if ((1900.0D <= t) && (t < 2000.0D)) {
/*  340: 295 */               t -= 1900.0D;
/*  341:     */             }
/*  342:     */           }
/*  343:     */           else {
/*  344: 298 */             t -= 1900.0D;
/*  345:     */           }
/*  346:     */         }
/*  347:     */       }
/*  348: 302 */       return ScriptRuntime.wrapNumber(t);
/*  349:     */     case 15: 
/*  350:     */     case 16: 
/*  351: 306 */       if (t == t)
/*  352:     */       {
/*  353: 307 */         if (id == 15) {
/*  354: 307 */           t = LocalTime(t);
/*  355:     */         }
/*  356: 308 */         t = MonthFromTime(t);
/*  357:     */       }
/*  358: 310 */       return ScriptRuntime.wrapNumber(t);
/*  359:     */     case 17: 
/*  360:     */     case 18: 
/*  361: 314 */       if (t == t)
/*  362:     */       {
/*  363: 315 */         if (id == 17) {
/*  364: 315 */           t = LocalTime(t);
/*  365:     */         }
/*  366: 316 */         t = DateFromTime(t);
/*  367:     */       }
/*  368: 318 */       return ScriptRuntime.wrapNumber(t);
/*  369:     */     case 19: 
/*  370:     */     case 20: 
/*  371: 322 */       if (t == t)
/*  372:     */       {
/*  373: 323 */         if (id == 19) {
/*  374: 323 */           t = LocalTime(t);
/*  375:     */         }
/*  376: 324 */         t = WeekDay(t);
/*  377:     */       }
/*  378: 326 */       return ScriptRuntime.wrapNumber(t);
/*  379:     */     case 21: 
/*  380:     */     case 22: 
/*  381: 330 */       if (t == t)
/*  382:     */       {
/*  383: 331 */         if (id == 21) {
/*  384: 331 */           t = LocalTime(t);
/*  385:     */         }
/*  386: 332 */         t = HourFromTime(t);
/*  387:     */       }
/*  388: 334 */       return ScriptRuntime.wrapNumber(t);
/*  389:     */     case 23: 
/*  390:     */     case 24: 
/*  391: 338 */       if (t == t)
/*  392:     */       {
/*  393: 339 */         if (id == 23) {
/*  394: 339 */           t = LocalTime(t);
/*  395:     */         }
/*  396: 340 */         t = MinFromTime(t);
/*  397:     */       }
/*  398: 342 */       return ScriptRuntime.wrapNumber(t);
/*  399:     */     case 25: 
/*  400:     */     case 26: 
/*  401: 346 */       if (t == t)
/*  402:     */       {
/*  403: 347 */         if (id == 25) {
/*  404: 347 */           t = LocalTime(t);
/*  405:     */         }
/*  406: 348 */         t = SecFromTime(t);
/*  407:     */       }
/*  408: 350 */       return ScriptRuntime.wrapNumber(t);
/*  409:     */     case 27: 
/*  410:     */     case 28: 
/*  411: 354 */       if (t == t)
/*  412:     */       {
/*  413: 355 */         if (id == 27) {
/*  414: 355 */           t = LocalTime(t);
/*  415:     */         }
/*  416: 356 */         t = msFromTime(t);
/*  417:     */       }
/*  418: 358 */       return ScriptRuntime.wrapNumber(t);
/*  419:     */     case 29: 
/*  420: 361 */       if (t == t) {
/*  421: 362 */         t = (t - LocalTime(t)) / 60000.0D;
/*  422:     */       }
/*  423: 364 */       return ScriptRuntime.wrapNumber(t);
/*  424:     */     case 30: 
/*  425: 367 */       t = TimeClip(ScriptRuntime.toNumber(args, 0));
/*  426: 368 */       realThis.date = t;
/*  427: 369 */       return ScriptRuntime.wrapNumber(t);
/*  428:     */     case 31: 
/*  429:     */     case 32: 
/*  430:     */     case 33: 
/*  431:     */     case 34: 
/*  432:     */     case 35: 
/*  433:     */     case 36: 
/*  434:     */     case 37: 
/*  435:     */     case 38: 
/*  436: 379 */       t = makeTime(t, args, id);
/*  437: 380 */       realThis.date = t;
/*  438: 381 */       return ScriptRuntime.wrapNumber(t);
/*  439:     */     case 39: 
/*  440:     */     case 40: 
/*  441:     */     case 41: 
/*  442:     */     case 42: 
/*  443:     */     case 43: 
/*  444:     */     case 44: 
/*  445: 389 */       t = makeDate(t, args, id);
/*  446: 390 */       realThis.date = t;
/*  447: 391 */       return ScriptRuntime.wrapNumber(t);
/*  448:     */     case 45: 
/*  449: 395 */       double year = ScriptRuntime.toNumber(args, 0);
/*  450: 397 */       if ((year != year) || (Double.isInfinite(year)))
/*  451:     */       {
/*  452: 398 */         t = ScriptRuntime.NaN;
/*  453:     */       }
/*  454:     */       else
/*  455:     */       {
/*  456: 400 */         if (t != t) {
/*  457: 401 */           t = 0.0D;
/*  458:     */         } else {
/*  459: 403 */           t = LocalTime(t);
/*  460:     */         }
/*  461: 406 */         if ((year >= 0.0D) && (year <= 99.0D)) {
/*  462: 407 */           year += 1900.0D;
/*  463:     */         }
/*  464: 409 */         double day = MakeDay(year, MonthFromTime(t), DateFromTime(t));
/*  465:     */         
/*  466: 411 */         t = MakeDate(day, TimeWithinDay(t));
/*  467: 412 */         t = internalUTC(t);
/*  468: 413 */         t = TimeClip(t);
/*  469:     */       }
/*  470: 416 */       realThis.date = t;
/*  471: 417 */       return ScriptRuntime.wrapNumber(t);
/*  472:     */     case 46: 
/*  473: 420 */       return realThis.toISOString();
/*  474:     */     }
/*  475: 422 */     throw new IllegalArgumentException(String.valueOf(id));
/*  476:     */   }
/*  477:     */   
/*  478:     */   private String toISOString()
/*  479:     */   {
/*  480: 428 */     if (this.date == this.date) {
/*  481: 429 */       synchronized (isoFormat)
/*  482:     */       {
/*  483: 430 */         return isoFormat.format(new Date(this.date));
/*  484:     */       }
/*  485:     */     }
/*  486: 433 */     String msg = ScriptRuntime.getMessage0("msg.invalid.date");
/*  487: 434 */     throw ScriptRuntime.constructError("RangeError", msg);
/*  488:     */   }
/*  489:     */   
/*  490:     */   private static double Day(double t)
/*  491:     */   {
/*  492: 453 */     return Math.floor(t / 86400000.0D);
/*  493:     */   }
/*  494:     */   
/*  495:     */   private static double TimeWithinDay(double t)
/*  496:     */   {
/*  497: 459 */     double result = t % 86400000.0D;
/*  498: 460 */     if (result < 0.0D) {
/*  499: 461 */       result += 86400000.0D;
/*  500:     */     }
/*  501: 462 */     return result;
/*  502:     */   }
/*  503:     */   
/*  504:     */   private static boolean IsLeapYear(int year)
/*  505:     */   {
/*  506: 467 */     return (year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0));
/*  507:     */   }
/*  508:     */   
/*  509:     */   private static double DayFromYear(double y)
/*  510:     */   {
/*  511: 475 */     return 365.0D * (y - 1970.0D) + Math.floor((y - 1969.0D) / 4.0D) - Math.floor((y - 1901.0D) / 100.0D) + Math.floor((y - 1601.0D) / 400.0D);
/*  512:     */   }
/*  513:     */   
/*  514:     */   private static double TimeFromYear(double y)
/*  515:     */   {
/*  516: 481 */     return DayFromYear(y) * 86400000.0D;
/*  517:     */   }
/*  518:     */   
/*  519:     */   private static int YearFromTime(double t)
/*  520:     */   {
/*  521: 486 */     int lo = (int)Math.floor(t / 86400000.0D / 366.0D) + 1970;
/*  522: 487 */     int hi = (int)Math.floor(t / 86400000.0D / 365.0D) + 1970;
/*  523: 491 */     if (hi < lo)
/*  524:     */     {
/*  525: 492 */       int temp = lo;
/*  526: 493 */       lo = hi;
/*  527: 494 */       hi = temp;
/*  528:     */     }
/*  529: 503 */     while (hi > lo)
/*  530:     */     {
/*  531: 504 */       int mid = (hi + lo) / 2;
/*  532: 505 */       if (TimeFromYear(mid) > t)
/*  533:     */       {
/*  534: 506 */         hi = mid - 1;
/*  535:     */       }
/*  536:     */       else
/*  537:     */       {
/*  538: 508 */         lo = mid + 1;
/*  539: 509 */         if (TimeFromYear(lo) > t) {
/*  540: 510 */           return mid;
/*  541:     */         }
/*  542:     */       }
/*  543:     */     }
/*  544: 514 */     return lo;
/*  545:     */   }
/*  546:     */   
/*  547:     */   private static double DayFromMonth(int m, int year)
/*  548:     */   {
/*  549: 519 */     int day = m * 30;
/*  550: 521 */     if (m >= 7) {
/*  551: 521 */       day += m / 2 - 1;
/*  552: 522 */     } else if (m >= 2) {
/*  553: 522 */       day += (m - 1) / 2 - 1;
/*  554:     */     } else {
/*  555: 523 */       day += m;
/*  556:     */     }
/*  557: 525 */     if ((m >= 2) && (IsLeapYear(year))) {
/*  558: 525 */       day++;
/*  559:     */     }
/*  560: 527 */     return day;
/*  561:     */   }
/*  562:     */   
/*  563:     */   private static int MonthFromTime(double t)
/*  564:     */   {
/*  565: 532 */     int year = YearFromTime(t);
/*  566: 533 */     int d = (int)(Day(t) - DayFromYear(year));
/*  567:     */     
/*  568: 535 */     d -= 59;
/*  569: 536 */     if (d < 0) {
/*  570: 537 */       return d < -28 ? 0 : 1;
/*  571:     */     }
/*  572: 540 */     if (IsLeapYear(year))
/*  573:     */     {
/*  574: 541 */       if (d == 0) {
/*  575: 542 */         return 1;
/*  576:     */       }
/*  577: 543 */       d--;
/*  578:     */     }
/*  579: 547 */     int estimate = d / 30;
/*  580:     */     int mstart;
/*  581: 549 */     switch (estimate)
/*  582:     */     {
/*  583:     */     case 0: 
/*  584: 550 */       return 2;
/*  585:     */     case 1: 
/*  586: 551 */       mstart = 31; break;
/*  587:     */     case 2: 
/*  588: 552 */       mstart = 61; break;
/*  589:     */     case 3: 
/*  590: 553 */       mstart = 92; break;
/*  591:     */     case 4: 
/*  592: 554 */       mstart = 122; break;
/*  593:     */     case 5: 
/*  594: 555 */       mstart = 153; break;
/*  595:     */     case 6: 
/*  596: 556 */       mstart = 184; break;
/*  597:     */     case 7: 
/*  598: 557 */       mstart = 214; break;
/*  599:     */     case 8: 
/*  600: 558 */       mstart = 245; break;
/*  601:     */     case 9: 
/*  602: 559 */       mstart = 275; break;
/*  603:     */     case 10: 
/*  604: 560 */       return 11;
/*  605:     */     default: 
/*  606: 561 */       throw Kit.codeBug();
/*  607:     */     }
/*  608: 564 */     return d >= mstart ? estimate + 2 : estimate + 1;
/*  609:     */   }
/*  610:     */   
/*  611:     */   private static int DateFromTime(double t)
/*  612:     */   {
/*  613: 569 */     int year = YearFromTime(t);
/*  614: 570 */     int d = (int)(Day(t) - DayFromYear(year));
/*  615:     */     
/*  616: 572 */     d -= 59;
/*  617: 573 */     if (d < 0) {
/*  618: 574 */       return d < -28 ? d + 31 + 28 + 1 : d + 28 + 1;
/*  619:     */     }
/*  620: 577 */     if (IsLeapYear(year))
/*  621:     */     {
/*  622: 578 */       if (d == 0) {
/*  623: 579 */         return 29;
/*  624:     */       }
/*  625: 580 */       d--;
/*  626:     */     }
/*  627:     */     int mdays;
/*  628:     */     int mstart;
/*  629: 585 */     switch (d / 30)
/*  630:     */     {
/*  631:     */     case 0: 
/*  632: 586 */       return d + 1;
/*  633:     */     case 1: 
/*  634: 587 */       mdays = 31;mstart = 31; break;
/*  635:     */     case 2: 
/*  636: 588 */       mdays = 30;mstart = 61; break;
/*  637:     */     case 3: 
/*  638: 589 */       mdays = 31;mstart = 92; break;
/*  639:     */     case 4: 
/*  640: 590 */       mdays = 30;mstart = 122; break;
/*  641:     */     case 5: 
/*  642: 591 */       mdays = 31;mstart = 153; break;
/*  643:     */     case 6: 
/*  644: 592 */       mdays = 31;mstart = 184; break;
/*  645:     */     case 7: 
/*  646: 593 */       mdays = 30;mstart = 214; break;
/*  647:     */     case 8: 
/*  648: 594 */       mdays = 31;mstart = 245; break;
/*  649:     */     case 9: 
/*  650: 595 */       mdays = 30;mstart = 275; break;
/*  651:     */     case 10: 
/*  652: 597 */       return d - 275 + 1;
/*  653:     */     default: 
/*  654: 598 */       throw Kit.codeBug();
/*  655:     */     }
/*  656: 600 */     d -= mstart;
/*  657: 601 */     if (d < 0) {
/*  658: 603 */       d += mdays;
/*  659:     */     }
/*  660: 605 */     return d + 1;
/*  661:     */   }
/*  662:     */   
/*  663:     */   private static int WeekDay(double t)
/*  664:     */   {
/*  665: 611 */     double result = Day(t) + 4.0D;
/*  666: 612 */     result %= 7.0D;
/*  667: 613 */     if (result < 0.0D) {
/*  668: 614 */       result += 7.0D;
/*  669:     */     }
/*  670: 615 */     return (int)result;
/*  671:     */   }
/*  672:     */   
/*  673:     */   private static double now()
/*  674:     */   {
/*  675: 620 */     return System.currentTimeMillis();
/*  676:     */   }
/*  677:     */   
/*  678:     */   private static double DaylightSavingTA(double t)
/*  679:     */   {
/*  680: 637 */     if ((t < 0.0D) || (t > 2145916800000.0D))
/*  681:     */     {
/*  682: 638 */       int year = EquivalentYear(YearFromTime(t));
/*  683: 639 */       double day = MakeDay(year, MonthFromTime(t), DateFromTime(t));
/*  684: 640 */       t = MakeDate(day, TimeWithinDay(t));
/*  685:     */     }
/*  686: 643 */     Date date = new Date(t);
/*  687: 644 */     if (thisTimeZone.inDaylightTime(date)) {
/*  688: 645 */       return 3600000.0D;
/*  689:     */     }
/*  690: 647 */     return 0.0D;
/*  691:     */   }
/*  692:     */   
/*  693:     */   private static int EquivalentYear(int year)
/*  694:     */   {
/*  695: 685 */     int day = (int)DayFromYear(year) + 4;
/*  696: 686 */     day %= 7;
/*  697: 687 */     if (day < 0) {
/*  698: 688 */       day += 7;
/*  699:     */     }
/*  700: 690 */     if (IsLeapYear(year)) {
/*  701: 691 */       switch (day)
/*  702:     */       {
/*  703:     */       case 0: 
/*  704: 692 */         return 1984;
/*  705:     */       case 1: 
/*  706: 693 */         return 1996;
/*  707:     */       case 2: 
/*  708: 694 */         return 1980;
/*  709:     */       case 3: 
/*  710: 695 */         return 1992;
/*  711:     */       case 4: 
/*  712: 696 */         return 1976;
/*  713:     */       case 5: 
/*  714: 697 */         return 1988;
/*  715:     */       case 6: 
/*  716: 698 */         return 1972;
/*  717:     */       }
/*  718:     */     } else {
/*  719: 701 */       switch (day)
/*  720:     */       {
/*  721:     */       case 0: 
/*  722: 702 */         return 1978;
/*  723:     */       case 1: 
/*  724: 703 */         return 1973;
/*  725:     */       case 2: 
/*  726: 704 */         return 1974;
/*  727:     */       case 3: 
/*  728: 705 */         return 1975;
/*  729:     */       case 4: 
/*  730: 706 */         return 1981;
/*  731:     */       case 5: 
/*  732: 707 */         return 1971;
/*  733:     */       case 6: 
/*  734: 708 */         return 1977;
/*  735:     */       }
/*  736:     */     }
/*  737: 712 */     throw Kit.codeBug();
/*  738:     */   }
/*  739:     */   
/*  740:     */   private static double LocalTime(double t)
/*  741:     */   {
/*  742: 717 */     return t + LocalTZA + DaylightSavingTA(t);
/*  743:     */   }
/*  744:     */   
/*  745:     */   private static double internalUTC(double t)
/*  746:     */   {
/*  747: 722 */     return t - LocalTZA - DaylightSavingTA(t - LocalTZA);
/*  748:     */   }
/*  749:     */   
/*  750:     */   private static int HourFromTime(double t)
/*  751:     */   {
/*  752: 728 */     double result = Math.floor(t / 3600000.0D) % 24.0D;
/*  753: 729 */     if (result < 0.0D) {
/*  754: 730 */       result += 24.0D;
/*  755:     */     }
/*  756: 731 */     return (int)result;
/*  757:     */   }
/*  758:     */   
/*  759:     */   private static int MinFromTime(double t)
/*  760:     */   {
/*  761: 737 */     double result = Math.floor(t / 60000.0D) % 60.0D;
/*  762: 738 */     if (result < 0.0D) {
/*  763: 739 */       result += 60.0D;
/*  764:     */     }
/*  765: 740 */     return (int)result;
/*  766:     */   }
/*  767:     */   
/*  768:     */   private static int SecFromTime(double t)
/*  769:     */   {
/*  770: 746 */     double result = Math.floor(t / 1000.0D) % 60.0D;
/*  771: 747 */     if (result < 0.0D) {
/*  772: 748 */       result += 60.0D;
/*  773:     */     }
/*  774: 749 */     return (int)result;
/*  775:     */   }
/*  776:     */   
/*  777:     */   private static int msFromTime(double t)
/*  778:     */   {
/*  779: 755 */     double result = t % 1000.0D;
/*  780: 756 */     if (result < 0.0D) {
/*  781: 757 */       result += 1000.0D;
/*  782:     */     }
/*  783: 758 */     return (int)result;
/*  784:     */   }
/*  785:     */   
/*  786:     */   private static double MakeTime(double hour, double min, double sec, double ms)
/*  787:     */   {
/*  788: 764 */     return ((hour * 60.0D + min) * 60.0D + sec) * 1000.0D + ms;
/*  789:     */   }
/*  790:     */   
/*  791:     */   private static double MakeDay(double year, double month, double date)
/*  792:     */   {
/*  793: 770 */     year += Math.floor(month / 12.0D);
/*  794:     */     
/*  795: 772 */     month %= 12.0D;
/*  796: 773 */     if (month < 0.0D) {
/*  797: 774 */       month += 12.0D;
/*  798:     */     }
/*  799: 776 */     double yearday = Math.floor(TimeFromYear(year) / 86400000.0D);
/*  800: 777 */     double monthday = DayFromMonth((int)month, (int)year);
/*  801:     */     
/*  802: 779 */     return yearday + monthday + date - 1.0D;
/*  803:     */   }
/*  804:     */   
/*  805:     */   private static double MakeDate(double day, double time)
/*  806:     */   {
/*  807: 784 */     return day * 86400000.0D + time;
/*  808:     */   }
/*  809:     */   
/*  810:     */   private static double TimeClip(double d)
/*  811:     */   {
/*  812: 789 */     if ((d != d) || (d == (1.0D / 0.0D)) || (d == (-1.0D / 0.0D)) || (Math.abs(d) > 8640000000000000.0D)) {
/*  813: 794 */       return ScriptRuntime.NaN;
/*  814:     */     }
/*  815: 796 */     if (d > 0.0D) {
/*  816: 797 */       return Math.floor(d + 0.0D);
/*  817:     */     }
/*  818: 799 */     return Math.ceil(d + 0.0D);
/*  819:     */   }
/*  820:     */   
/*  821:     */   private static double date_msecFromDate(double year, double mon, double mday, double hour, double min, double sec, double msec)
/*  822:     */   {
/*  823: 814 */     double day = MakeDay(year, mon, mday);
/*  824: 815 */     double time = MakeTime(hour, min, sec, msec);
/*  825: 816 */     double result = MakeDate(day, time);
/*  826: 817 */     return result;
/*  827:     */   }
/*  828:     */   
/*  829:     */   private static double date_msecFromArgs(Object[] args)
/*  830:     */   {
/*  831: 824 */     double[] array = new double[7];
/*  832: 828 */     for (int loop = 0; loop < 7; loop++) {
/*  833: 829 */       if (loop < args.length)
/*  834:     */       {
/*  835: 830 */         double d = ScriptRuntime.toNumber(args[loop]);
/*  836: 831 */         if ((d != d) || (Double.isInfinite(d))) {
/*  837: 832 */           return ScriptRuntime.NaN;
/*  838:     */         }
/*  839: 834 */         array[loop] = ScriptRuntime.toInteger(args[loop]);
/*  840:     */       }
/*  841: 836 */       else if (loop == 2)
/*  842:     */       {
/*  843: 837 */         array[loop] = 1.0D;
/*  844:     */       }
/*  845:     */       else
/*  846:     */       {
/*  847: 839 */         array[loop] = 0.0D;
/*  848:     */       }
/*  849:     */     }
/*  850: 845 */     if ((array[0] >= 0.0D) && (array[0] <= 99.0D)) {
/*  851: 846 */       array[0] += 1900.0D;
/*  852:     */     }
/*  853: 848 */     return date_msecFromDate(array[0], array[1], array[2], array[3], array[4], array[5], array[6]);
/*  854:     */   }
/*  855:     */   
/*  856:     */   private static double jsStaticFunction_UTC(Object[] args)
/*  857:     */   {
/*  858: 854 */     return TimeClip(date_msecFromArgs(args));
/*  859:     */   }
/*  860:     */   
/*  861:     */   private static double date_parseString(String s)
/*  862:     */   {
/*  863:     */     try
/*  864:     */     {
/*  865: 860 */       if (s.length() == 24)
/*  866:     */       {
/*  867:     */         Date d;
/*  868: 862 */         synchronized (isoFormat)
/*  869:     */         {
/*  870: 863 */           d = isoFormat.parse(s);
/*  871:     */         }
/*  872: 865 */         return d.getTime();
/*  873:     */       }
/*  874:     */     }
/*  875:     */     catch (ParseException ex) {}
/*  876: 869 */     int year = -1;
/*  877: 870 */     int mon = -1;
/*  878: 871 */     int mday = -1;
/*  879: 872 */     int hour = -1;
/*  880: 873 */     int min = -1;
/*  881: 874 */     int sec = -1;
/*  882: 875 */     char c = '\000';
/*  883: 876 */     char si = '\000';
/*  884: 877 */     int i = 0;
/*  885: 878 */     int n = -1;
/*  886: 879 */     double tzoffset = -1.0D;
/*  887: 880 */     char prevc = '\000';
/*  888: 881 */     int limit = 0;
/*  889: 882 */     boolean seenplusminus = false;
/*  890:     */     
/*  891: 884 */     limit = s.length();
/*  892:     */     for (;;)
/*  893:     */     {
/*  894: 885 */       if (i >= limit) {
/*  895:     */         break label1085;
/*  896:     */       }
/*  897: 886 */       c = s.charAt(i);
/*  898: 887 */       i++;
/*  899: 888 */       if ((c <= ' ') || (c == ',') || (c == '-'))
/*  900:     */       {
/*  901: 889 */         if (i < limit)
/*  902:     */         {
/*  903: 890 */           si = s.charAt(i);
/*  904: 891 */           if ((c == '-') && ('0' <= si) && (si <= '9')) {
/*  905: 892 */             prevc = c;
/*  906:     */           }
/*  907:     */         }
/*  908:     */       }
/*  909:     */       else
/*  910:     */       {
/*  911: 897 */         if (c == '(')
/*  912:     */         {
/*  913: 898 */           int depth = 1;
/*  914: 899 */           while (i < limit)
/*  915:     */           {
/*  916: 900 */             c = s.charAt(i);
/*  917: 901 */             i++;
/*  918: 902 */             if (c != '(') {
/*  919:     */               break label213;
/*  920:     */             }
/*  921: 903 */             depth++;
/*  922:     */           }
/*  923:     */           continue;
/*  924:     */           label213:
/*  925: 904 */           if (c != ')') {
/*  926:     */             break;
/*  927:     */           }
/*  928: 905 */           depth--;
/*  929: 905 */           if (depth > 0) {
/*  930:     */             break;
/*  931:     */           }
/*  932: 906 */           continue;
/*  933:     */         }
/*  934: 910 */         if (('0' <= c) && (c <= '9'))
/*  935:     */         {
/*  936: 911 */           n = c - '0';
/*  937: 912 */           while ((i < limit) && ('0' <= (c = s.charAt(i))) && (c <= '9'))
/*  938:     */           {
/*  939: 913 */             n = n * 10 + c - 48;
/*  940: 914 */             i++;
/*  941:     */           }
/*  942: 924 */           if ((prevc == '+') || (prevc == '-'))
/*  943:     */           {
/*  944: 926 */             seenplusminus = true;
/*  945: 929 */             if (n < 24) {
/*  946: 930 */               n *= 60;
/*  947:     */             } else {
/*  948: 932 */               n = n % 100 + n / 100 * 60;
/*  949:     */             }
/*  950: 933 */             if (prevc == '+') {
/*  951: 934 */               n = -n;
/*  952:     */             }
/*  953: 935 */             if ((tzoffset != 0.0D) && (tzoffset != -1.0D)) {
/*  954: 936 */               return ScriptRuntime.NaN;
/*  955:     */             }
/*  956: 937 */             tzoffset = n;
/*  957:     */           }
/*  958: 938 */           else if ((n >= 70) || ((prevc == '/') && (mon >= 0) && (mday >= 0) && (year < 0)))
/*  959:     */           {
/*  960: 942 */             if (year >= 0) {
/*  961: 943 */               return ScriptRuntime.NaN;
/*  962:     */             }
/*  963: 944 */             if ((c <= ' ') || (c == ',') || (c == '/') || (i >= limit)) {
/*  964: 945 */               year = n < 100 ? n + 1900 : n;
/*  965:     */             } else {
/*  966: 947 */               return ScriptRuntime.NaN;
/*  967:     */             }
/*  968:     */           }
/*  969: 948 */           else if (c == ':')
/*  970:     */           {
/*  971: 949 */             if (hour < 0) {
/*  972: 950 */               hour = n;
/*  973: 951 */             } else if (min < 0) {
/*  974: 952 */               min = n;
/*  975:     */             } else {
/*  976: 954 */               return ScriptRuntime.NaN;
/*  977:     */             }
/*  978:     */           }
/*  979: 955 */           else if (c == '/')
/*  980:     */           {
/*  981: 956 */             if (mon < 0) {
/*  982: 957 */               mon = n - 1;
/*  983: 958 */             } else if (mday < 0) {
/*  984: 959 */               mday = n;
/*  985:     */             } else {
/*  986: 961 */               return ScriptRuntime.NaN;
/*  987:     */             }
/*  988:     */           }
/*  989:     */           else
/*  990:     */           {
/*  991: 962 */             if ((i < limit) && (c != ',') && (c > ' ') && (c != '-')) {
/*  992: 963 */               return ScriptRuntime.NaN;
/*  993:     */             }
/*  994: 964 */             if ((seenplusminus) && (n < 60))
/*  995:     */             {
/*  996: 965 */               if (tzoffset < 0.0D) {
/*  997: 966 */                 tzoffset -= n;
/*  998:     */               } else {
/*  999: 968 */                 tzoffset += n;
/* 1000:     */               }
/* 1001:     */             }
/* 1002: 969 */             else if ((hour >= 0) && (min < 0)) {
/* 1003: 970 */               min = n;
/* 1004: 971 */             } else if ((min >= 0) && (sec < 0)) {
/* 1005: 972 */               sec = n;
/* 1006: 973 */             } else if (mday < 0) {
/* 1007: 974 */               mday = n;
/* 1008:     */             } else {
/* 1009: 976 */               return ScriptRuntime.NaN;
/* 1010:     */             }
/* 1011:     */           }
/* 1012: 978 */           prevc = '\000';
/* 1013:     */         }
/* 1014: 979 */         else if ((c == '/') || (c == ':') || (c == '+') || (c == '-'))
/* 1015:     */         {
/* 1016: 980 */           prevc = c;
/* 1017:     */         }
/* 1018:     */         else
/* 1019:     */         {
/* 1020: 982 */           int st = i - 1;
/* 1021: 983 */           while (i < limit)
/* 1022:     */           {
/* 1023: 984 */             c = s.charAt(i);
/* 1024: 985 */             if ((('A' > c) || (c > 'Z')) && (('a' > c) || (c > 'z'))) {
/* 1025:     */               break;
/* 1026:     */             }
/* 1027: 987 */             i++;
/* 1028:     */           }
/* 1029: 989 */           int letterCount = i - st;
/* 1030: 990 */           if (letterCount < 2) {
/* 1031: 991 */             return ScriptRuntime.NaN;
/* 1032:     */           }
/* 1033: 997 */           String wtb = "am;pm;monday;tuesday;wednesday;thursday;friday;saturday;sunday;january;february;march;april;may;june;july;august;september;october;november;december;gmt;ut;utc;est;edt;cst;cdt;mst;mdt;pst;pdt;";
/* 1034:     */           
/* 1035:     */ 
/* 1036:     */ 
/* 1037:     */ 
/* 1038:     */ 
/* 1039:1003 */           int index = 0;
/* 1040:1004 */           int wtbOffset = 0;
/* 1041:     */           for (;;)
/* 1042:     */           {
/* 1043:1005 */             int wtbNext = wtb.indexOf(';', wtbOffset);
/* 1044:1006 */             if (wtbNext < 0) {
/* 1045:1007 */               return ScriptRuntime.NaN;
/* 1046:     */             }
/* 1047:1008 */             if (wtb.regionMatches(true, wtbOffset, s, st, letterCount)) {
/* 1048:     */               break;
/* 1049:     */             }
/* 1050:1010 */             wtbOffset = wtbNext + 1;
/* 1051:1011 */             index++;
/* 1052:     */           }
/* 1053:1013 */           if (index < 2)
/* 1054:     */           {
/* 1055:1018 */             if ((hour > 12) || (hour < 0)) {
/* 1056:1019 */               return ScriptRuntime.NaN;
/* 1057:     */             }
/* 1058:1020 */             if (index == 0)
/* 1059:     */             {
/* 1060:1022 */               if (hour == 12) {
/* 1061:1023 */                 hour = 0;
/* 1062:     */               }
/* 1063:     */             }
/* 1064:1026 */             else if (hour != 12) {
/* 1065:1027 */               hour += 12;
/* 1066:     */             }
/* 1067:     */           }
/* 1068:     */           else
/* 1069:     */           {
/* 1070:1029 */             index -= 2;
/* 1071:1029 */             if (index >= 7)
/* 1072:     */             {
/* 1073:1031 */               index -= 7;
/* 1074:1031 */               if (index < 12)
/* 1075:     */               {
/* 1076:1033 */                 if (mon < 0) {
/* 1077:1034 */                   mon = index;
/* 1078:     */                 } else {
/* 1079:1036 */                   return ScriptRuntime.NaN;
/* 1080:     */                 }
/* 1081:     */               }
/* 1082:     */               else
/* 1083:     */               {
/* 1084:1039 */                 index -= 12;
/* 1085:1041 */                 switch (index)
/* 1086:     */                 {
/* 1087:     */                 case 0: 
/* 1088:1042 */                   tzoffset = 0.0D; break;
/* 1089:     */                 case 1: 
/* 1090:1043 */                   tzoffset = 0.0D; break;
/* 1091:     */                 case 2: 
/* 1092:1044 */                   tzoffset = 0.0D; break;
/* 1093:     */                 case 3: 
/* 1094:1045 */                   tzoffset = 300.0D; break;
/* 1095:     */                 case 4: 
/* 1096:1046 */                   tzoffset = 240.0D; break;
/* 1097:     */                 case 5: 
/* 1098:1047 */                   tzoffset = 360.0D; break;
/* 1099:     */                 case 6: 
/* 1100:1048 */                   tzoffset = 300.0D; break;
/* 1101:     */                 case 7: 
/* 1102:1049 */                   tzoffset = 420.0D; break;
/* 1103:     */                 case 8: 
/* 1104:1050 */                   tzoffset = 360.0D; break;
/* 1105:     */                 case 9: 
/* 1106:1051 */                   tzoffset = 480.0D; break;
/* 1107:     */                 case 10: 
/* 1108:1052 */                   tzoffset = 420.0D; break;
/* 1109:     */                 default: 
/* 1110:1053 */                   Kit.codeBug();
/* 1111:     */                 }
/* 1112:     */               }
/* 1113:     */             }
/* 1114:     */           }
/* 1115:     */         }
/* 1116:     */       }
/* 1117:     */     }
/* 1118:     */     label1085:
/* 1119:1058 */     if ((year < 0) || (mon < 0) || (mday < 0)) {
/* 1120:1059 */       return ScriptRuntime.NaN;
/* 1121:     */     }
/* 1122:1060 */     if (sec < 0) {
/* 1123:1061 */       sec = 0;
/* 1124:     */     }
/* 1125:1062 */     if (min < 0) {
/* 1126:1063 */       min = 0;
/* 1127:     */     }
/* 1128:1064 */     if (hour < 0) {
/* 1129:1065 */       hour = 0;
/* 1130:     */     }
/* 1131:1067 */     double msec = date_msecFromDate(year, mon, mday, hour, min, sec, 0.0D);
/* 1132:1068 */     if (tzoffset == -1.0D) {
/* 1133:1069 */       return internalUTC(msec);
/* 1134:     */     }
/* 1135:1071 */     return msec + tzoffset * 60000.0D;
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   private static String date_format(double t, int methodId)
/* 1139:     */   {
/* 1140:1077 */     StringBuffer result = new StringBuffer(60);
/* 1141:1078 */     double local = LocalTime(t);
/* 1142:1084 */     if (methodId != 3)
/* 1143:     */     {
/* 1144:1085 */       appendWeekDayName(result, WeekDay(local));
/* 1145:1086 */       result.append(' ');
/* 1146:1087 */       appendMonthName(result, MonthFromTime(local));
/* 1147:1088 */       result.append(' ');
/* 1148:1089 */       append0PaddedUint(result, DateFromTime(local), 2);
/* 1149:1090 */       result.append(' ');
/* 1150:1091 */       int year = YearFromTime(local);
/* 1151:1092 */       if (year < 0)
/* 1152:     */       {
/* 1153:1093 */         result.append('-');
/* 1154:1094 */         year = -year;
/* 1155:     */       }
/* 1156:1096 */       append0PaddedUint(result, year, 4);
/* 1157:1097 */       if (methodId != 4) {
/* 1158:1098 */         result.append(' ');
/* 1159:     */       }
/* 1160:     */     }
/* 1161:1101 */     if (methodId != 4)
/* 1162:     */     {
/* 1163:1102 */       append0PaddedUint(result, HourFromTime(local), 2);
/* 1164:1103 */       result.append(':');
/* 1165:1104 */       append0PaddedUint(result, MinFromTime(local), 2);
/* 1166:1105 */       result.append(':');
/* 1167:1106 */       append0PaddedUint(result, SecFromTime(local), 2);
/* 1168:     */       
/* 1169:     */ 
/* 1170:     */ 
/* 1171:1110 */       int minutes = (int)Math.floor((LocalTZA + DaylightSavingTA(t)) / 60000.0D);
/* 1172:     */       
/* 1173:     */ 
/* 1174:1113 */       int offset = minutes / 60 * 100 + minutes % 60;
/* 1175:1114 */       if (offset > 0)
/* 1176:     */       {
/* 1177:1115 */         result.append(" GMT+");
/* 1178:     */       }
/* 1179:     */       else
/* 1180:     */       {
/* 1181:1117 */         result.append(" GMT-");
/* 1182:1118 */         offset = -offset;
/* 1183:     */       }
/* 1184:1120 */       append0PaddedUint(result, offset, 4);
/* 1185:1122 */       if (timeZoneFormatter == null) {
/* 1186:1123 */         timeZoneFormatter = new SimpleDateFormat("zzz");
/* 1187:     */       }
/* 1188:1127 */       if ((t < 0.0D) || (t > 2145916800000.0D))
/* 1189:     */       {
/* 1190:1128 */         int equiv = EquivalentYear(YearFromTime(local));
/* 1191:1129 */         double day = MakeDay(equiv, MonthFromTime(t), DateFromTime(t));
/* 1192:1130 */         t = MakeDate(day, TimeWithinDay(t));
/* 1193:     */       }
/* 1194:1132 */       result.append(" (");
/* 1195:1133 */       Date date = new Date(t);
/* 1196:1134 */       synchronized (timeZoneFormatter)
/* 1197:     */       {
/* 1198:1135 */         result.append(timeZoneFormatter.format(date));
/* 1199:     */       }
/* 1200:1137 */       result.append(')');
/* 1201:     */     }
/* 1202:1139 */     return result.toString();
/* 1203:     */   }
/* 1204:     */   
/* 1205:     */   private static Object jsConstructor(Object[] args)
/* 1206:     */   {
/* 1207:1145 */     NativeDate obj = new NativeDate();
/* 1208:1149 */     if (args.length == 0)
/* 1209:     */     {
/* 1210:1150 */       obj.date = now();
/* 1211:1151 */       return obj;
/* 1212:     */     }
/* 1213:1155 */     if (args.length == 1)
/* 1214:     */     {
/* 1215:1156 */       Object arg0 = args[0];
/* 1216:1157 */       if ((arg0 instanceof Scriptable)) {
/* 1217:1158 */         arg0 = ((Scriptable)arg0).getDefaultValue(null);
/* 1218:     */       }
/* 1219:     */       double date;
/* 1220:     */       double date;
/* 1221:1160 */       if ((arg0 instanceof String)) {
/* 1222:1162 */         date = date_parseString((String)arg0);
/* 1223:     */       } else {
/* 1224:1165 */         date = ScriptRuntime.toNumber(arg0);
/* 1225:     */       }
/* 1226:1167 */       obj.date = TimeClip(date);
/* 1227:1168 */       return obj;
/* 1228:     */     }
/* 1229:1171 */     double time = date_msecFromArgs(args);
/* 1230:1173 */     if ((!Double.isNaN(time)) && (!Double.isInfinite(time))) {
/* 1231:1174 */       time = TimeClip(internalUTC(time));
/* 1232:     */     }
/* 1233:1176 */     obj.date = time;
/* 1234:     */     
/* 1235:1178 */     return obj;
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   private static String toLocale_helper(double t, int methodId)
/* 1239:     */   {
/* 1240:     */     DateFormat formatter;
/* 1241:1184 */     switch (methodId)
/* 1242:     */     {
/* 1243:     */     case 5: 
/* 1244:1186 */       if (localeDateTimeFormatter == null) {
/* 1245:1187 */         localeDateTimeFormatter = DateFormat.getDateTimeInstance(1, 1);
/* 1246:     */       }
/* 1247:1191 */       formatter = localeDateTimeFormatter;
/* 1248:1192 */       break;
/* 1249:     */     case 6: 
/* 1250:1194 */       if (localeTimeFormatter == null) {
/* 1251:1195 */         localeTimeFormatter = DateFormat.getTimeInstance(1);
/* 1252:     */       }
/* 1253:1198 */       formatter = localeTimeFormatter;
/* 1254:1199 */       break;
/* 1255:     */     case 7: 
/* 1256:1201 */       if (localeDateFormatter == null) {
/* 1257:1202 */         localeDateFormatter = DateFormat.getDateInstance(1);
/* 1258:     */       }
/* 1259:1205 */       formatter = localeDateFormatter;
/* 1260:1206 */       break;
/* 1261:     */     default: 
/* 1262:1207 */       throw new AssertionError();
/* 1263:     */     }
/* 1264:1210 */     synchronized (formatter)
/* 1265:     */     {
/* 1266:1211 */       return formatter.format(new Date(t));
/* 1267:     */     }
/* 1268:     */   }
/* 1269:     */   
/* 1270:     */   private static String js_toUTCString(double date)
/* 1271:     */   {
/* 1272:1217 */     StringBuffer result = new StringBuffer(60);
/* 1273:     */     
/* 1274:1219 */     appendWeekDayName(result, WeekDay(date));
/* 1275:1220 */     result.append(", ");
/* 1276:1221 */     append0PaddedUint(result, DateFromTime(date), 2);
/* 1277:1222 */     result.append(' ');
/* 1278:1223 */     appendMonthName(result, MonthFromTime(date));
/* 1279:1224 */     result.append(' ');
/* 1280:1225 */     int year = YearFromTime(date);
/* 1281:1226 */     if (year < 0)
/* 1282:     */     {
/* 1283:1227 */       result.append('-');year = -year;
/* 1284:     */     }
/* 1285:1229 */     append0PaddedUint(result, year, 4);
/* 1286:1230 */     result.append(' ');
/* 1287:1231 */     append0PaddedUint(result, HourFromTime(date), 2);
/* 1288:1232 */     result.append(':');
/* 1289:1233 */     append0PaddedUint(result, MinFromTime(date), 2);
/* 1290:1234 */     result.append(':');
/* 1291:1235 */     append0PaddedUint(result, SecFromTime(date), 2);
/* 1292:1236 */     result.append(" GMT");
/* 1293:1237 */     return result.toString();
/* 1294:     */   }
/* 1295:     */   
/* 1296:     */   private static void append0PaddedUint(StringBuffer sb, int i, int minWidth)
/* 1297:     */   {
/* 1298:1242 */     if (i < 0) {
/* 1299:1242 */       Kit.codeBug();
/* 1300:     */     }
/* 1301:1243 */     int scale = 1;
/* 1302:1244 */     minWidth--;
/* 1303:1245 */     if (i >= 10)
/* 1304:     */     {
/* 1305:1246 */       if (i < 1000000000) {
/* 1306:     */         for (;;)
/* 1307:     */         {
/* 1308:1248 */           int newScale = scale * 10;
/* 1309:1249 */           if (i < newScale) {
/* 1310:     */             break;
/* 1311:     */           }
/* 1312:1250 */           minWidth--;
/* 1313:1251 */           scale = newScale;
/* 1314:     */         }
/* 1315:     */       }
/* 1316:1255 */       minWidth -= 9;
/* 1317:1256 */       scale = 1000000000;
/* 1318:     */     }
/* 1319:1259 */     while (minWidth > 0)
/* 1320:     */     {
/* 1321:1260 */       sb.append('0');
/* 1322:1261 */       minWidth--;
/* 1323:     */     }
/* 1324:1263 */     while (scale != 1)
/* 1325:     */     {
/* 1326:1264 */       sb.append((char)(48 + i / scale));
/* 1327:1265 */       i %= scale;
/* 1328:1266 */       scale /= 10;
/* 1329:     */     }
/* 1330:1268 */     sb.append((char)(48 + i));
/* 1331:     */   }
/* 1332:     */   
/* 1333:     */   private static void appendMonthName(StringBuffer sb, int index)
/* 1334:     */   {
/* 1335:1276 */     String months = "JanFebMarAprMayJunJulAugSepOctNovDec";
/* 1336:     */     
/* 1337:1278 */     index *= 3;
/* 1338:1279 */     for (int i = 0; i != 3; i++) {
/* 1339:1280 */       sb.append(months.charAt(index + i));
/* 1340:     */     }
/* 1341:     */   }
/* 1342:     */   
/* 1343:     */   private static void appendWeekDayName(StringBuffer sb, int index)
/* 1344:     */   {
/* 1345:1286 */     String days = "SunMonTueWedThuFriSat";
/* 1346:1287 */     index *= 3;
/* 1347:1288 */     for (int i = 0; i != 3; i++) {
/* 1348:1289 */       sb.append(days.charAt(index + i));
/* 1349:     */     }
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   private static double makeTime(double date, Object[] args, int methodId)
/* 1353:     */   {
/* 1354:1296 */     boolean local = true;
/* 1355:     */     int maxargs;
/* 1356:1297 */     switch (methodId)
/* 1357:     */     {
/* 1358:     */     case 32: 
/* 1359:1299 */       local = false;
/* 1360:     */     case 31: 
/* 1361:1302 */       maxargs = 1;
/* 1362:1303 */       break;
/* 1363:     */     case 34: 
/* 1364:1306 */       local = false;
/* 1365:     */     case 33: 
/* 1366:1309 */       maxargs = 2;
/* 1367:1310 */       break;
/* 1368:     */     case 36: 
/* 1369:1313 */       local = false;
/* 1370:     */     case 35: 
/* 1371:1316 */       maxargs = 3;
/* 1372:1317 */       break;
/* 1373:     */     case 38: 
/* 1374:1320 */       local = false;
/* 1375:     */     case 37: 
/* 1376:1323 */       maxargs = 4;
/* 1377:1324 */       break;
/* 1378:     */     default: 
/* 1379:1327 */       Kit.codeBug();
/* 1380:1328 */       maxargs = 0;
/* 1381:     */     }
/* 1382:1332 */     double[] conv = new double[4];
/* 1383:1340 */     if (date != date) {
/* 1384:1341 */       return date;
/* 1385:     */     }
/* 1386:1351 */     if (args.length == 0) {
/* 1387:1352 */       args = ScriptRuntime.padArguments(args, 1);
/* 1388:     */     }
/* 1389:1354 */     for (int i = 0; (i < args.length) && (i < maxargs); i++)
/* 1390:     */     {
/* 1391:1355 */       conv[i] = ScriptRuntime.toNumber(args[i]);
/* 1392:1358 */       if ((conv[i] != conv[i]) || (Double.isInfinite(conv[i]))) {
/* 1393:1359 */         return ScriptRuntime.NaN;
/* 1394:     */       }
/* 1395:1361 */       conv[i] = ScriptRuntime.toInteger(conv[i]);
/* 1396:     */     }
/* 1397:     */     double lorutime;
/* 1398:     */     double lorutime;
/* 1399:1364 */     if (local) {
/* 1400:1365 */       lorutime = LocalTime(date);
/* 1401:     */     } else {
/* 1402:1367 */       lorutime = date;
/* 1403:     */     }
/* 1404:1369 */     i = 0;
/* 1405:1370 */     int stop = args.length;
/* 1406:     */     double hour;
/* 1407:     */     double hour;
/* 1408:1372 */     if ((maxargs >= 4) && (i < stop)) {
/* 1409:1373 */       hour = conv[(i++)];
/* 1410:     */     } else {
/* 1411:1375 */       hour = HourFromTime(lorutime);
/* 1412:     */     }
/* 1413:     */     double min;
/* 1414:     */     double min;
/* 1415:1377 */     if ((maxargs >= 3) && (i < stop)) {
/* 1416:1378 */       min = conv[(i++)];
/* 1417:     */     } else {
/* 1418:1380 */       min = MinFromTime(lorutime);
/* 1419:     */     }
/* 1420:     */     double sec;
/* 1421:     */     double sec;
/* 1422:1382 */     if ((maxargs >= 2) && (i < stop)) {
/* 1423:1383 */       sec = conv[(i++)];
/* 1424:     */     } else {
/* 1425:1385 */       sec = SecFromTime(lorutime);
/* 1426:     */     }
/* 1427:     */     double msec;
/* 1428:     */     double msec;
/* 1429:1387 */     if ((maxargs >= 1) && (i < stop)) {
/* 1430:1388 */       msec = conv[(i++)];
/* 1431:     */     } else {
/* 1432:1390 */       msec = msFromTime(lorutime);
/* 1433:     */     }
/* 1434:1392 */     double time = MakeTime(hour, min, sec, msec);
/* 1435:1393 */     double result = MakeDate(Day(lorutime), time);
/* 1436:1395 */     if (local) {
/* 1437:1396 */       result = internalUTC(result);
/* 1438:     */     }
/* 1439:1397 */     date = TimeClip(result);
/* 1440:     */     
/* 1441:1399 */     return date;
/* 1442:     */   }
/* 1443:     */   
/* 1444:     */   private static double makeDate(double date, Object[] args, int methodId)
/* 1445:     */   {
/* 1446:1405 */     boolean local = true;
/* 1447:     */     int maxargs;
/* 1448:1406 */     switch (methodId)
/* 1449:     */     {
/* 1450:     */     case 40: 
/* 1451:1408 */       local = false;
/* 1452:     */     case 39: 
/* 1453:1411 */       maxargs = 1;
/* 1454:1412 */       break;
/* 1455:     */     case 42: 
/* 1456:1415 */       local = false;
/* 1457:     */     case 41: 
/* 1458:1418 */       maxargs = 2;
/* 1459:1419 */       break;
/* 1460:     */     case 44: 
/* 1461:1422 */       local = false;
/* 1462:     */     case 43: 
/* 1463:1425 */       maxargs = 3;
/* 1464:1426 */       break;
/* 1465:     */     default: 
/* 1466:1429 */       Kit.codeBug();
/* 1467:1430 */       maxargs = 0;
/* 1468:     */     }
/* 1469:1434 */     double[] conv = new double[3];
/* 1470:1440 */     if (args.length == 0) {
/* 1471:1441 */       args = ScriptRuntime.padArguments(args, 1);
/* 1472:     */     }
/* 1473:1443 */     for (int i = 0; (i < args.length) && (i < maxargs); i++)
/* 1474:     */     {
/* 1475:1444 */       conv[i] = ScriptRuntime.toNumber(args[i]);
/* 1476:1447 */       if ((conv[i] != conv[i]) || (Double.isInfinite(conv[i]))) {
/* 1477:1448 */         return ScriptRuntime.NaN;
/* 1478:     */       }
/* 1479:1450 */       conv[i] = ScriptRuntime.toInteger(conv[i]);
/* 1480:     */     }
/* 1481:     */     double lorutime;
/* 1482:     */     double lorutime;
/* 1483:1455 */     if (date != date)
/* 1484:     */     {
/* 1485:1456 */       if (args.length < 3) {
/* 1486:1457 */         return ScriptRuntime.NaN;
/* 1487:     */       }
/* 1488:1459 */       lorutime = 0.0D;
/* 1489:     */     }
/* 1490:     */     else
/* 1491:     */     {
/* 1492:     */       double lorutime;
/* 1493:1462 */       if (local) {
/* 1494:1463 */         lorutime = LocalTime(date);
/* 1495:     */       } else {
/* 1496:1465 */         lorutime = date;
/* 1497:     */       }
/* 1498:     */     }
/* 1499:1468 */     i = 0;
/* 1500:1469 */     int stop = args.length;
/* 1501:     */     double year;
/* 1502:     */     double year;
/* 1503:1471 */     if ((maxargs >= 3) && (i < stop)) {
/* 1504:1472 */       year = conv[(i++)];
/* 1505:     */     } else {
/* 1506:1474 */       year = YearFromTime(lorutime);
/* 1507:     */     }
/* 1508:     */     double month;
/* 1509:     */     double month;
/* 1510:1476 */     if ((maxargs >= 2) && (i < stop)) {
/* 1511:1477 */       month = conv[(i++)];
/* 1512:     */     } else {
/* 1513:1479 */       month = MonthFromTime(lorutime);
/* 1514:     */     }
/* 1515:     */     double day;
/* 1516:1481 */     if ((maxargs >= 1) && (i < stop)) {
/* 1517:1482 */       day = conv[(i++)];
/* 1518:     */     } else {
/* 1519:1484 */       day = DateFromTime(lorutime);
/* 1520:     */     }
/* 1521:1486 */     double day = MakeDay(year, month, day);
/* 1522:1487 */     double result = MakeDate(day, TimeWithinDay(lorutime));
/* 1523:1489 */     if (local) {
/* 1524:1490 */       result = internalUTC(result);
/* 1525:     */     }
/* 1526:1492 */     date = TimeClip(result);
/* 1527:     */     
/* 1528:1494 */     return date;
/* 1529:     */   }
/* 1530:     */   
/* 1531:     */   protected int findPrototypeId(String s)
/* 1532:     */   {
/* 1533:1504 */     int id = 0;String X = null;
/* 1534:     */     int c;
/* 1535:1505 */     switch (s.length())
/* 1536:     */     {
/* 1537:     */     case 6: 
/* 1538:1506 */       c = s.charAt(0);
/* 1539:1507 */       if (c == 103)
/* 1540:     */       {
/* 1541:1507 */         X = "getDay";id = 19;
/* 1542:     */       }
/* 1543:1508 */       else if (c == 116)
/* 1544:     */       {
/* 1545:1508 */         X = "toJSON";id = 47;
/* 1546:     */       }
/* 1547:     */       break;
/* 1548:     */     case 7: 
/* 1549:1510 */       switch (s.charAt(3))
/* 1550:     */       {
/* 1551:     */       case 'D': 
/* 1552:1511 */         c = s.charAt(0);
/* 1553:1512 */         if (c == 103)
/* 1554:     */         {
/* 1555:1512 */           X = "getDate";id = 17;
/* 1556:     */         }
/* 1557:1513 */         else if (c == 115)
/* 1558:     */         {
/* 1559:1513 */           X = "setDate";id = 39;
/* 1560:     */         }
/* 1561:     */         break;
/* 1562:     */       case 'T': 
/* 1563:1515 */         c = s.charAt(0);
/* 1564:1516 */         if (c == 103)
/* 1565:     */         {
/* 1566:1516 */           X = "getTime";id = 11;
/* 1567:     */         }
/* 1568:1517 */         else if (c == 115)
/* 1569:     */         {
/* 1570:1517 */           X = "setTime";id = 30;
/* 1571:     */         }
/* 1572:     */         break;
/* 1573:     */       case 'Y': 
/* 1574:1519 */         c = s.charAt(0);
/* 1575:1520 */         if (c == 103)
/* 1576:     */         {
/* 1577:1520 */           X = "getYear";id = 12;
/* 1578:     */         }
/* 1579:1521 */         else if (c == 115)
/* 1580:     */         {
/* 1581:1521 */           X = "setYear";id = 45;
/* 1582:     */         }
/* 1583:     */         break;
/* 1584:     */       case 'u': 
/* 1585:1523 */         X = "valueOf";id = 10;
/* 1586:     */       }
/* 1587:1524 */       break;
/* 1588:     */     case 8: 
/* 1589:1525 */       switch (s.charAt(3))
/* 1590:     */       {
/* 1591:     */       case 'H': 
/* 1592:1526 */         c = s.charAt(0);
/* 1593:1527 */         if (c == 103)
/* 1594:     */         {
/* 1595:1527 */           X = "getHours";id = 21;
/* 1596:     */         }
/* 1597:1528 */         else if (c == 115)
/* 1598:     */         {
/* 1599:1528 */           X = "setHours";id = 37;
/* 1600:     */         }
/* 1601:     */         break;
/* 1602:     */       case 'M': 
/* 1603:1530 */         c = s.charAt(0);
/* 1604:1531 */         if (c == 103)
/* 1605:     */         {
/* 1606:1531 */           X = "getMonth";id = 15;
/* 1607:     */         }
/* 1608:1532 */         else if (c == 115)
/* 1609:     */         {
/* 1610:1532 */           X = "setMonth";id = 41;
/* 1611:     */         }
/* 1612:     */         break;
/* 1613:     */       case 'o': 
/* 1614:1534 */         X = "toSource";id = 9; break;
/* 1615:     */       case 't': 
/* 1616:1535 */         X = "toString";id = 2;
/* 1617:     */       }
/* 1618:1536 */       break;
/* 1619:     */     case 9: 
/* 1620:1537 */       X = "getUTCDay";id = 20; break;
/* 1621:     */     case 10: 
/* 1622:1538 */       c = s.charAt(3);
/* 1623:1539 */       if (c == 77)
/* 1624:     */       {
/* 1625:1540 */         c = s.charAt(0);
/* 1626:1541 */         if (c == 103)
/* 1627:     */         {
/* 1628:1541 */           X = "getMinutes";id = 23;
/* 1629:     */         }
/* 1630:1542 */         else if (c == 115)
/* 1631:     */         {
/* 1632:1542 */           X = "setMinutes";id = 35;
/* 1633:     */         }
/* 1634:     */       }
/* 1635:1544 */       else if (c == 83)
/* 1636:     */       {
/* 1637:1545 */         c = s.charAt(0);
/* 1638:1546 */         if (c == 103)
/* 1639:     */         {
/* 1640:1546 */           X = "getSeconds";id = 25;
/* 1641:     */         }
/* 1642:1547 */         else if (c == 115)
/* 1643:     */         {
/* 1644:1547 */           X = "setSeconds";id = 33;
/* 1645:     */         }
/* 1646:     */       }
/* 1647:1549 */       else if (c == 85)
/* 1648:     */       {
/* 1649:1550 */         c = s.charAt(0);
/* 1650:1551 */         if (c == 103)
/* 1651:     */         {
/* 1652:1551 */           X = "getUTCDate";id = 18;
/* 1653:     */         }
/* 1654:1552 */         else if (c == 115)
/* 1655:     */         {
/* 1656:1552 */           X = "setUTCDate";id = 40;
/* 1657:     */         }
/* 1658:     */       }
/* 1659:     */       break;
/* 1660:     */     case 11: 
/* 1661:1555 */       switch (s.charAt(3))
/* 1662:     */       {
/* 1663:     */       case 'F': 
/* 1664:1556 */         c = s.charAt(0);
/* 1665:1557 */         if (c == 103)
/* 1666:     */         {
/* 1667:1557 */           X = "getFullYear";id = 13;
/* 1668:     */         }
/* 1669:1558 */         else if (c == 115)
/* 1670:     */         {
/* 1671:1558 */           X = "setFullYear";id = 43;
/* 1672:     */         }
/* 1673:     */         break;
/* 1674:     */       case 'M': 
/* 1675:1560 */         X = "toGMTString";id = 8; break;
/* 1676:     */       case 'S': 
/* 1677:1561 */         X = "toISOString";id = 46; break;
/* 1678:     */       case 'T': 
/* 1679:1562 */         X = "toUTCString";id = 8; break;
/* 1680:     */       case 'U': 
/* 1681:1563 */         c = s.charAt(0);
/* 1682:1564 */         if (c == 103)
/* 1683:     */         {
/* 1684:1565 */           c = s.charAt(9);
/* 1685:1566 */           if (c == 114)
/* 1686:     */           {
/* 1687:1566 */             X = "getUTCHours";id = 22;
/* 1688:     */           }
/* 1689:1567 */           else if (c == 116)
/* 1690:     */           {
/* 1691:1567 */             X = "getUTCMonth";id = 16;
/* 1692:     */           }
/* 1693:     */         }
/* 1694:1569 */         else if (c == 115)
/* 1695:     */         {
/* 1696:1570 */           c = s.charAt(9);
/* 1697:1571 */           if (c == 114)
/* 1698:     */           {
/* 1699:1571 */             X = "setUTCHours";id = 38;
/* 1700:     */           }
/* 1701:1572 */           else if (c == 116)
/* 1702:     */           {
/* 1703:1572 */             X = "setUTCMonth";id = 42;
/* 1704:     */           }
/* 1705:     */         }
/* 1706:     */         break;
/* 1707:     */       case 's': 
/* 1708:1575 */         X = "constructor";id = 1;
/* 1709:     */       }
/* 1710:1576 */       break;
/* 1711:     */     case 12: 
/* 1712:1577 */       c = s.charAt(2);
/* 1713:1578 */       if (c == 68)
/* 1714:     */       {
/* 1715:1578 */         X = "toDateString";id = 4;
/* 1716:     */       }
/* 1717:1579 */       else if (c == 84)
/* 1718:     */       {
/* 1719:1579 */         X = "toTimeString";id = 3;
/* 1720:     */       }
/* 1721:     */       break;
/* 1722:     */     case 13: 
/* 1723:1581 */       c = s.charAt(0);
/* 1724:1582 */       if (c == 103)
/* 1725:     */       {
/* 1726:1583 */         c = s.charAt(6);
/* 1727:1584 */         if (c == 77)
/* 1728:     */         {
/* 1729:1584 */           X = "getUTCMinutes";id = 24;
/* 1730:     */         }
/* 1731:1585 */         else if (c == 83)
/* 1732:     */         {
/* 1733:1585 */           X = "getUTCSeconds";id = 26;
/* 1734:     */         }
/* 1735:     */       }
/* 1736:1587 */       else if (c == 115)
/* 1737:     */       {
/* 1738:1588 */         c = s.charAt(6);
/* 1739:1589 */         if (c == 77)
/* 1740:     */         {
/* 1741:1589 */           X = "setUTCMinutes";id = 36;
/* 1742:     */         }
/* 1743:1590 */         else if (c == 83)
/* 1744:     */         {
/* 1745:1590 */           X = "setUTCSeconds";id = 34;
/* 1746:     */         }
/* 1747:     */       }
/* 1748:     */       break;
/* 1749:     */     case 14: 
/* 1750:1593 */       c = s.charAt(0);
/* 1751:1594 */       if (c == 103)
/* 1752:     */       {
/* 1753:1594 */         X = "getUTCFullYear";id = 14;
/* 1754:     */       }
/* 1755:1595 */       else if (c == 115)
/* 1756:     */       {
/* 1757:1595 */         X = "setUTCFullYear";id = 44;
/* 1758:     */       }
/* 1759:1596 */       else if (c == 116)
/* 1760:     */       {
/* 1761:1596 */         X = "toLocaleString";id = 5;
/* 1762:     */       }
/* 1763:     */       break;
/* 1764:     */     case 15: 
/* 1765:1598 */       c = s.charAt(0);
/* 1766:1599 */       if (c == 103)
/* 1767:     */       {
/* 1768:1599 */         X = "getMilliseconds";id = 27;
/* 1769:     */       }
/* 1770:1600 */       else if (c == 115)
/* 1771:     */       {
/* 1772:1600 */         X = "setMilliseconds";id = 31;
/* 1773:     */       }
/* 1774:     */       break;
/* 1775:     */     case 17: 
/* 1776:1602 */       X = "getTimezoneOffset";id = 29; break;
/* 1777:     */     case 18: 
/* 1778:1603 */       c = s.charAt(0);
/* 1779:1604 */       if (c == 103)
/* 1780:     */       {
/* 1781:1604 */         X = "getUTCMilliseconds";id = 28;
/* 1782:     */       }
/* 1783:1605 */       else if (c == 115)
/* 1784:     */       {
/* 1785:1605 */         X = "setUTCMilliseconds";id = 32;
/* 1786:     */       }
/* 1787:1606 */       else if (c == 116)
/* 1788:     */       {
/* 1789:1607 */         c = s.charAt(8);
/* 1790:1608 */         if (c == 68)
/* 1791:     */         {
/* 1792:1608 */           X = "toLocaleDateString";id = 7;
/* 1793:     */         }
/* 1794:1609 */         else if (c == 84)
/* 1795:     */         {
/* 1796:1609 */           X = "toLocaleTimeString";id = 6;
/* 1797:     */         }
/* 1798:     */       }
/* 1799:     */       break;
/* 1800:     */     }
/* 1801:1613 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 1802:1613 */       id = 0;
/* 1803:     */     }
/* 1804:1617 */     return id;
/* 1805:     */   }
/* 1806:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeDate
 * JD-Core Version:    0.7.0.1
 */