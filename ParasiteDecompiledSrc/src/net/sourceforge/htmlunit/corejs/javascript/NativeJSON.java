/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Stack;
/*   9:    */ import net.sourceforge.htmlunit.corejs.javascript.json.JsonParser;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.json.JsonParser.ParseException;
/*  11:    */ 
/*  12:    */ final class NativeJSON
/*  13:    */   extends IdScriptableObject
/*  14:    */ {
/*  15:    */   static final long serialVersionUID = -4567599697595654984L;
/*  16: 61 */   private static final Object JSON_TAG = "JSON";
/*  17:    */   private static final int MAX_STRINGIFY_GAP_LENGTH = 10;
/*  18:    */   private static final int Id_toSource = 1;
/*  19:    */   private static final int Id_parse = 2;
/*  20:    */   private static final int Id_stringify = 3;
/*  21:    */   private static final int LAST_METHOD_ID = 3;
/*  22:    */   private static final int MAX_ID = 3;
/*  23:    */   
/*  24:    */   static void init(Scriptable scope, boolean sealed)
/*  25:    */   {
/*  26: 67 */     NativeJSON obj = new NativeJSON();
/*  27: 68 */     obj.activatePrototypeMap(3);
/*  28: 69 */     obj.setPrototype(getObjectPrototype(scope));
/*  29: 70 */     obj.setParentScope(scope);
/*  30: 71 */     if (sealed) {
/*  31: 71 */       obj.sealObject();
/*  32:    */     }
/*  33: 72 */     ScriptableObject.defineProperty(scope, "JSON", obj, 2);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getClassName()
/*  37:    */   {
/*  38: 81 */     return "JSON";
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void initPrototypeId(int id)
/*  42:    */   {
/*  43: 86 */     if (id <= 3)
/*  44:    */     {
/*  45:    */       int arity;
/*  46:    */       String name;
/*  47: 89 */       switch (id)
/*  48:    */       {
/*  49:    */       case 1: 
/*  50: 90 */         arity = 0;name = "toSource"; break;
/*  51:    */       case 2: 
/*  52: 91 */         arity = 2;name = "parse"; break;
/*  53:    */       case 3: 
/*  54: 92 */         arity = 3;name = "stringify"; break;
/*  55:    */       default: 
/*  56: 93 */         throw new IllegalStateException(String.valueOf(id));
/*  57:    */       }
/*  58: 95 */       initPrototypeMethod(JSON_TAG, id, name, arity);
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 97 */       throw new IllegalStateException(String.valueOf(id));
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  67:    */   {
/*  68:105 */     if (!f.hasTag(JSON_TAG)) {
/*  69:106 */       return super.execIdCall(f, cx, scope, thisObj, args);
/*  70:    */     }
/*  71:108 */     int methodId = f.methodId();
/*  72:109 */     switch (methodId)
/*  73:    */     {
/*  74:    */     case 1: 
/*  75:111 */       return "JSON";
/*  76:    */     case 2: 
/*  77:114 */       String jtext = ScriptRuntime.toString(args, 0);
/*  78:115 */       Object reviver = null;
/*  79:116 */       if (args.length > 1) {
/*  80:117 */         reviver = args[1];
/*  81:    */       }
/*  82:119 */       if ((reviver instanceof Callable)) {
/*  83:120 */         return parse(cx, scope, jtext, (Callable)reviver);
/*  84:    */       }
/*  85:122 */       return parse(cx, scope, jtext);
/*  86:    */     case 3: 
/*  87:127 */       Object value = null;Object replacer = null;Object space = null;
/*  88:128 */       switch (args.length)
/*  89:    */       {
/*  90:    */       case 3: 
/*  91:    */       default: 
/*  92:130 */         space = args[2];
/*  93:    */       case 2: 
/*  94:131 */         replacer = args[1];
/*  95:    */       case 1: 
/*  96:132 */         value = args[0];
/*  97:    */       }
/*  98:135 */       return stringify(cx, scope, value, replacer, space);
/*  99:    */     }
/* 100:138 */     throw new IllegalStateException(String.valueOf(methodId));
/* 101:    */   }
/* 102:    */   
/* 103:    */   private static Object parse(Context cx, Scriptable scope, String jtext)
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:144 */       return new JsonParser(cx, scope).parseValue(jtext);
/* 108:    */     }
/* 109:    */     catch (JsonParser.ParseException ex)
/* 110:    */     {
/* 111:146 */       throw ScriptRuntime.constructError("SyntaxError", ex.getMessage());
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static Object parse(Context cx, Scriptable scope, String jtext, Callable reviver)
/* 116:    */   {
/* 117:153 */     Object unfiltered = parse(cx, scope, jtext);
/* 118:154 */     Scriptable root = cx.newObject(scope);
/* 119:155 */     root.put("", root, unfiltered);
/* 120:156 */     return walk(cx, scope, reviver, root, "");
/* 121:    */   }
/* 122:    */   
/* 123:    */   private static Object walk(Context cx, Scriptable scope, Callable reviver, Scriptable holder, Object name)
/* 124:    */   {
/* 125:    */     Object property;
/* 126:    */     Object property;
/* 127:163 */     if ((name instanceof Number)) {
/* 128:164 */       property = holder.get(((Number)name).intValue(), holder);
/* 129:    */     } else {
/* 130:166 */       property = holder.get((String)name, holder);
/* 131:    */     }
/* 132:169 */     if ((property instanceof Scriptable))
/* 133:    */     {
/* 134:170 */       Scriptable val = (Scriptable)property;
/* 135:171 */       if ((val instanceof NativeArray))
/* 136:    */       {
/* 137:172 */         int len = (int)((NativeArray)val).getLength();
/* 138:173 */         for (int i = 0; i < len; i++)
/* 139:    */         {
/* 140:174 */           Object newElement = walk(cx, scope, reviver, val, Integer.valueOf(i));
/* 141:175 */           if (newElement == Undefined.instance) {
/* 142:176 */             val.delete(i);
/* 143:    */           } else {
/* 144:178 */             val.put(i, val, newElement);
/* 145:    */           }
/* 146:    */         }
/* 147:    */       }
/* 148:    */       else
/* 149:    */       {
/* 150:182 */         Object[] keys = val.getIds();
/* 151:183 */         for (Object p : keys)
/* 152:    */         {
/* 153:184 */           Object newElement = walk(cx, scope, reviver, val, p);
/* 154:185 */           if (newElement == Undefined.instance)
/* 155:    */           {
/* 156:186 */             if ((p instanceof Number)) {
/* 157:187 */               val.delete(((Number)p).intValue());
/* 158:    */             } else {
/* 159:189 */               val.delete((String)p);
/* 160:    */             }
/* 161:    */           }
/* 162:191 */           else if ((p instanceof Number)) {
/* 163:192 */             val.put(((Number)p).intValue(), val, newElement);
/* 164:    */           } else {
/* 165:194 */             val.put((String)p, val, newElement);
/* 166:    */           }
/* 167:    */         }
/* 168:    */       }
/* 169:    */     }
/* 170:200 */     return reviver.call(cx, scope, holder, new Object[] { name, property });
/* 171:    */   }
/* 172:    */   
/* 173:    */   private static String repeat(char c, int count)
/* 174:    */   {
/* 175:204 */     char[] chars = new char[count];
/* 176:205 */     Arrays.fill(chars, c);
/* 177:206 */     return new String(chars);
/* 178:    */   }
/* 179:    */   
/* 180:    */   private static class StringifyState
/* 181:    */   {
/* 182:    */     StringifyState(Context cx, Scriptable scope, String indent, String gap, Callable replacer, List<Object> propertyList, Object space)
/* 183:    */     {
/* 184:214 */       this.cx = cx;
/* 185:215 */       this.scope = scope;
/* 186:    */       
/* 187:217 */       this.indent = indent;
/* 188:218 */       this.gap = gap;
/* 189:219 */       this.replacer = replacer;
/* 190:220 */       this.propertyList = propertyList;
/* 191:221 */       this.space = space;
/* 192:    */     }
/* 193:    */     
/* 194:224 */     Stack<Scriptable> stack = new Stack();
/* 195:    */     String indent;
/* 196:    */     String gap;
/* 197:    */     Callable replacer;
/* 198:    */     List<Object> propertyList;
/* 199:    */     Object space;
/* 200:    */     Context cx;
/* 201:    */     Scriptable scope;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static Object stringify(Context cx, Scriptable scope, Object value, Object replacer, Object space)
/* 205:    */   {
/* 206:238 */     String indent = "";
/* 207:239 */     String gap = "";
/* 208:    */     
/* 209:241 */     List<Object> propertyList = null;
/* 210:242 */     Callable replacerFunction = null;
/* 211:244 */     if ((replacer instanceof Callable))
/* 212:    */     {
/* 213:245 */       replacerFunction = (Callable)replacer;
/* 214:    */     }
/* 215:246 */     else if ((replacer instanceof NativeArray))
/* 216:    */     {
/* 217:247 */       propertyList = new LinkedList();
/* 218:248 */       NativeArray replacerArray = (NativeArray)replacer;
/* 219:249 */       Integer[] arr$ = replacerArray.getIndexIds();int len$ = arr$.length;
/* 220:249 */       for (int i$ = 0; i$ < len$; i$++)
/* 221:    */       {
/* 222:249 */         int i = arr$[i$].intValue();
/* 223:250 */         Object v = replacerArray.get(i, replacerArray);
/* 224:251 */         if (((v instanceof String)) || ((v instanceof Number))) {
/* 225:252 */           propertyList.add(v);
/* 226:253 */         } else if (((v instanceof NativeString)) || ((v instanceof NativeNumber))) {
/* 227:254 */           propertyList.add(ScriptRuntime.toString(v));
/* 228:    */         }
/* 229:    */       }
/* 230:    */     }
/* 231:259 */     if ((space instanceof NativeNumber)) {
/* 232:260 */       space = Double.valueOf(ScriptRuntime.toNumber(space));
/* 233:261 */     } else if ((space instanceof NativeString)) {
/* 234:262 */       space = ScriptRuntime.toString(space);
/* 235:    */     }
/* 236:265 */     if ((space instanceof Number))
/* 237:    */     {
/* 238:266 */       int gapLength = (int)ScriptRuntime.toInteger(space);
/* 239:267 */       gapLength = Math.min(10, gapLength);
/* 240:268 */       gap = gapLength > 0 ? repeat(' ', gapLength) : "";
/* 241:269 */       space = Integer.valueOf(gapLength);
/* 242:    */     }
/* 243:270 */     else if ((space instanceof String))
/* 244:    */     {
/* 245:271 */       gap = (String)space;
/* 246:272 */       if (gap.length() > 10) {
/* 247:273 */         gap = gap.substring(0, 10);
/* 248:    */       }
/* 249:    */     }
/* 250:277 */     StringifyState state = new StringifyState(cx, scope, indent, gap, replacerFunction, propertyList, space);
/* 251:    */     
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:284 */     ScriptableObject wrapper = new NativeObject();
/* 258:285 */     wrapper.setParentScope(scope);
/* 259:286 */     wrapper.setPrototype(ScriptableObject.getObjectPrototype(scope));
/* 260:287 */     wrapper.defineProperty("", value, 0);
/* 261:288 */     return str("", wrapper, state);
/* 262:    */   }
/* 263:    */   
/* 264:    */   private static Object str(Object key, Scriptable holder, StringifyState state)
/* 265:    */   {
/* 266:294 */     Object value = null;
/* 267:295 */     if ((key instanceof String)) {
/* 268:296 */       value = getProperty(holder, (String)key);
/* 269:    */     } else {
/* 270:298 */       value = getProperty(holder, ((Number)key).intValue());
/* 271:    */     }
/* 272:301 */     if ((value instanceof Scriptable))
/* 273:    */     {
/* 274:302 */       Object toJSON = getProperty((Scriptable)value, "toJSON");
/* 275:303 */       if ((toJSON instanceof Callable)) {
/* 276:304 */         value = callMethod(state.cx, (Scriptable)value, "toJSON", new Object[] { key });
/* 277:    */       }
/* 278:    */     }
/* 279:309 */     if (state.replacer != null) {
/* 280:310 */       value = state.replacer.call(state.cx, state.scope, holder, new Object[] { key, value });
/* 281:    */     }
/* 282:315 */     if ((value instanceof NativeNumber)) {
/* 283:316 */       value = Double.valueOf(ScriptRuntime.toNumber(value));
/* 284:317 */     } else if ((value instanceof NativeString)) {
/* 285:318 */       value = ScriptRuntime.toString(value);
/* 286:319 */     } else if ((value instanceof NativeBoolean)) {
/* 287:320 */       value = ((NativeBoolean)value).getDefaultValue(ScriptRuntime.BooleanClass);
/* 288:    */     }
/* 289:323 */     if (value == null) {
/* 290:323 */       return "null";
/* 291:    */     }
/* 292:324 */     if (value.equals(Boolean.TRUE)) {
/* 293:324 */       return "true";
/* 294:    */     }
/* 295:325 */     if (value.equals(Boolean.FALSE)) {
/* 296:325 */       return "false";
/* 297:    */     }
/* 298:327 */     if ((value instanceof String)) {
/* 299:328 */       return quote((String)value);
/* 300:    */     }
/* 301:331 */     if ((value instanceof Number))
/* 302:    */     {
/* 303:332 */       double d = ((Number)value).doubleValue();
/* 304:333 */       if ((d == d) && (d != (1.0D / 0.0D)) && (d != (-1.0D / 0.0D))) {
/* 305:336 */         return ScriptRuntime.toString(value);
/* 306:    */       }
/* 307:338 */       return "null";
/* 308:    */     }
/* 309:342 */     if (((value instanceof Scriptable)) && (!(value instanceof Callable)))
/* 310:    */     {
/* 311:343 */       if ((value instanceof NativeArray)) {
/* 312:344 */         return ja((NativeArray)value, state);
/* 313:    */       }
/* 314:346 */       return jo((Scriptable)value, state);
/* 315:    */     }
/* 316:349 */     return Undefined.instance;
/* 317:    */   }
/* 318:    */   
/* 319:    */   private static String join(Collection<Object> objs, String delimiter)
/* 320:    */   {
/* 321:353 */     if ((objs == null) || (objs.isEmpty())) {
/* 322:354 */       return "";
/* 323:    */     }
/* 324:356 */     Iterator<Object> iter = objs.iterator();
/* 325:357 */     if (!iter.hasNext()) {
/* 326:357 */       return "";
/* 327:    */     }
/* 328:358 */     StringBuilder builder = new StringBuilder(iter.next().toString());
/* 329:359 */     while (iter.hasNext()) {
/* 330:360 */       builder.append(delimiter).append(iter.next().toString());
/* 331:    */     }
/* 332:362 */     return builder.toString();
/* 333:    */   }
/* 334:    */   
/* 335:    */   private static String jo(Scriptable value, StringifyState state)
/* 336:    */   {
/* 337:366 */     if (state.stack.search(value) != -1) {
/* 338:367 */       throw ScriptRuntime.typeError0("msg.cyclic.value");
/* 339:    */     }
/* 340:369 */     state.stack.push(value);
/* 341:    */     
/* 342:371 */     String stepback = state.indent;
/* 343:372 */     state.indent += state.gap;
/* 344:373 */     Object[] k = null;
/* 345:374 */     if (state.propertyList != null) {
/* 346:375 */       k = state.propertyList.toArray();
/* 347:    */     } else {
/* 348:377 */       k = value.getIds();
/* 349:    */     }
/* 350:380 */     List<Object> partial = new LinkedList();
/* 351:382 */     for (Object p : k)
/* 352:    */     {
/* 353:383 */       Object strP = str(p, value, state);
/* 354:384 */       if (strP != Undefined.instance)
/* 355:    */       {
/* 356:385 */         String member = quote(p.toString()) + ":";
/* 357:386 */         if (state.gap.length() > 0) {
/* 358:387 */           member = member + " ";
/* 359:    */         }
/* 360:389 */         member = member + strP;
/* 361:390 */         partial.add(member);
/* 362:    */       }
/* 363:    */     }
/* 364:    */     String finalValue;
/* 365:    */     String finalValue;
/* 366:396 */     if (partial.isEmpty())
/* 367:    */     {
/* 368:397 */       finalValue = "{}";
/* 369:    */     }
/* 370:    */     else
/* 371:    */     {
/* 372:    */       String finalValue;
/* 373:399 */       if (state.gap.length() == 0)
/* 374:    */       {
/* 375:400 */         finalValue = '{' + join(partial, ",") + '}';
/* 376:    */       }
/* 377:    */       else
/* 378:    */       {
/* 379:402 */         String separator = ",\n" + state.indent;
/* 380:403 */         String properties = join(partial, separator);
/* 381:404 */         finalValue = "{\n" + state.indent + properties + '\n' + stepback + '}';
/* 382:    */       }
/* 383:    */     }
/* 384:409 */     state.stack.pop();
/* 385:410 */     state.indent = stepback;
/* 386:411 */     return finalValue;
/* 387:    */   }
/* 388:    */   
/* 389:    */   private static String ja(NativeArray value, StringifyState state)
/* 390:    */   {
/* 391:415 */     if (state.stack.search(value) != -1) {
/* 392:416 */       throw ScriptRuntime.typeError0("msg.cyclic.value");
/* 393:    */     }
/* 394:418 */     state.stack.push(value);
/* 395:    */     
/* 396:420 */     String stepback = state.indent;
/* 397:421 */     state.indent += state.gap;
/* 398:422 */     List<Object> partial = new LinkedList();
/* 399:    */     
/* 400:424 */     int len = (int)value.getLength();
/* 401:425 */     for (int index = 0; index < len; index++)
/* 402:    */     {
/* 403:426 */       Object strP = str(Integer.valueOf(index), value, state);
/* 404:427 */       if (strP == Undefined.instance) {
/* 405:428 */         partial.add("null");
/* 406:    */       } else {
/* 407:430 */         partial.add(strP);
/* 408:    */       }
/* 409:    */     }
/* 410:    */     String finalValue;
/* 411:    */     String finalValue;
/* 412:436 */     if (partial.isEmpty())
/* 413:    */     {
/* 414:437 */       finalValue = "[]";
/* 415:    */     }
/* 416:    */     else
/* 417:    */     {
/* 418:    */       String finalValue;
/* 419:439 */       if (state.gap.length() == 0)
/* 420:    */       {
/* 421:440 */         finalValue = '[' + join(partial, ",") + ']';
/* 422:    */       }
/* 423:    */       else
/* 424:    */       {
/* 425:442 */         String separator = ",\n" + state.indent;
/* 426:443 */         String properties = join(partial, separator);
/* 427:444 */         finalValue = "[\n" + state.indent + properties + '\n' + stepback + ']';
/* 428:    */       }
/* 429:    */     }
/* 430:448 */     state.stack.pop();
/* 431:449 */     state.indent = stepback;
/* 432:450 */     return finalValue;
/* 433:    */   }
/* 434:    */   
/* 435:    */   private static String quote(String string)
/* 436:    */   {
/* 437:454 */     StringBuffer product = new StringBuffer(string.length() + 2);
/* 438:455 */     product.append('"');
/* 439:456 */     int length = string.length();
/* 440:457 */     for (int i = 0; i < length; i++)
/* 441:    */     {
/* 442:458 */       char c = string.charAt(i);
/* 443:459 */       switch (c)
/* 444:    */       {
/* 445:    */       case '"': 
/* 446:461 */         product.append("\\\"");
/* 447:462 */         break;
/* 448:    */       case '\\': 
/* 449:464 */         product.append("\\\\");
/* 450:465 */         break;
/* 451:    */       case '\b': 
/* 452:467 */         product.append("\\b");
/* 453:468 */         break;
/* 454:    */       case '\f': 
/* 455:470 */         product.append("\\f");
/* 456:471 */         break;
/* 457:    */       case '\n': 
/* 458:473 */         product.append("\\n");
/* 459:474 */         break;
/* 460:    */       case '\r': 
/* 461:476 */         product.append("\\r");
/* 462:477 */         break;
/* 463:    */       case '\t': 
/* 464:479 */         product.append("\\t");
/* 465:480 */         break;
/* 466:    */       default: 
/* 467:482 */         if (c < ' ')
/* 468:    */         {
/* 469:483 */           product.append("\\u");
/* 470:484 */           String hex = String.format("%04x", new Object[] { Integer.valueOf(c) });
/* 471:485 */           product.append(hex);
/* 472:    */         }
/* 473:    */         else
/* 474:    */         {
/* 475:488 */           product.append(c);
/* 476:    */         }
/* 477:    */         break;
/* 478:    */       }
/* 479:    */     }
/* 480:493 */     product.append('"');
/* 481:494 */     return product.toString();
/* 482:    */   }
/* 483:    */   
/* 484:    */   protected int findPrototypeId(String s)
/* 485:    */   {
/* 486:504 */     int id = 0;String X = null;
/* 487:505 */     switch (s.length())
/* 488:    */     {
/* 489:    */     case 5: 
/* 490:506 */       X = "parse";id = 2; break;
/* 491:    */     case 8: 
/* 492:507 */       X = "toSource";id = 1; break;
/* 493:    */     case 9: 
/* 494:508 */       X = "stringify";id = 3; break;
/* 495:    */     }
/* 496:510 */     if ((X != null) && (X != s) && (!X.equals(s))) {
/* 497:510 */       id = 0;
/* 498:    */     }
/* 499:513 */     return id;
/* 500:    */   }
/* 501:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeJSON
 * JD-Core Version:    0.7.0.1
 */