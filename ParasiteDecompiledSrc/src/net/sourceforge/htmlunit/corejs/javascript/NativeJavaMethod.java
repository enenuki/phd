/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.lang.reflect.Member;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ 
/*   7:    */ public class NativeJavaMethod
/*   8:    */   extends BaseFunction
/*   9:    */ {
/*  10:    */   static final long serialVersionUID = -3440381785576412928L;
/*  11:    */   private static final int PREFERENCE_EQUAL = 0;
/*  12:    */   private static final int PREFERENCE_FIRST_ARG = 1;
/*  13:    */   private static final int PREFERENCE_SECOND_ARG = 2;
/*  14:    */   private static final int PREFERENCE_AMBIGUOUS = 3;
/*  15:    */   private static final boolean debug = false;
/*  16:    */   MemberBox[] methods;
/*  17:    */   private String functionName;
/*  18:    */   
/*  19:    */   NativeJavaMethod(MemberBox[] methods)
/*  20:    */   {
/*  21: 62 */     this.functionName = methods[0].getName();
/*  22: 63 */     this.methods = methods;
/*  23:    */   }
/*  24:    */   
/*  25:    */   NativeJavaMethod(MemberBox method, String name)
/*  26:    */   {
/*  27: 68 */     this.functionName = name;
/*  28: 69 */     this.methods = new MemberBox[] { method };
/*  29:    */   }
/*  30:    */   
/*  31:    */   public NativeJavaMethod(Method method, String name)
/*  32:    */   {
/*  33: 74 */     this(new MemberBox(method), name);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getFunctionName()
/*  37:    */   {
/*  38: 80 */     return this.functionName;
/*  39:    */   }
/*  40:    */   
/*  41:    */   static String scriptSignature(Object[] values)
/*  42:    */   {
/*  43: 85 */     StringBuffer sig = new StringBuffer();
/*  44: 86 */     for (int i = 0; i != values.length; i++)
/*  45:    */     {
/*  46: 87 */       Object value = values[i];
/*  47:    */       String s;
/*  48:    */       String s;
/*  49: 90 */       if (value == null)
/*  50:    */       {
/*  51: 91 */         s = "null";
/*  52:    */       }
/*  53:    */       else
/*  54:    */       {
/*  55:    */         String s;
/*  56: 92 */         if ((value instanceof Boolean))
/*  57:    */         {
/*  58: 93 */           s = "boolean";
/*  59:    */         }
/*  60:    */         else
/*  61:    */         {
/*  62:    */           String s;
/*  63: 94 */           if ((value instanceof String))
/*  64:    */           {
/*  65: 95 */             s = "string";
/*  66:    */           }
/*  67:    */           else
/*  68:    */           {
/*  69:    */             String s;
/*  70: 96 */             if ((value instanceof Number))
/*  71:    */             {
/*  72: 97 */               s = "number";
/*  73:    */             }
/*  74:    */             else
/*  75:    */             {
/*  76:    */               String s;
/*  77: 98 */               if ((value instanceof Scriptable))
/*  78:    */               {
/*  79:    */                 String s;
/*  80: 99 */                 if ((value instanceof Undefined))
/*  81:    */                 {
/*  82:100 */                   s = "undefined";
/*  83:    */                 }
/*  84:    */                 else
/*  85:    */                 {
/*  86:    */                   String s;
/*  87:101 */                   if ((value instanceof Wrapper))
/*  88:    */                   {
/*  89:102 */                     Object wrapped = ((Wrapper)value).unwrap();
/*  90:103 */                     s = wrapped.getClass().getName();
/*  91:    */                   }
/*  92:    */                   else
/*  93:    */                   {
/*  94:    */                     String s;
/*  95:104 */                     if ((value instanceof Function)) {
/*  96:105 */                       s = "function";
/*  97:    */                     } else {
/*  98:107 */                       s = "object";
/*  99:    */                     }
/* 100:    */                   }
/* 101:    */                 }
/* 102:    */               }
/* 103:    */               else
/* 104:    */               {
/* 105:110 */                 s = JavaMembers.javaSignature(value.getClass());
/* 106:    */               }
/* 107:    */             }
/* 108:    */           }
/* 109:    */         }
/* 110:    */       }
/* 111:113 */       if (i != 0) {
/* 112:114 */         sig.append(',');
/* 113:    */       }
/* 114:116 */       sig.append(s);
/* 115:    */     }
/* 116:118 */     return sig.toString();
/* 117:    */   }
/* 118:    */   
/* 119:    */   String decompile(int indent, int flags)
/* 120:    */   {
/* 121:124 */     StringBuffer sb = new StringBuffer();
/* 122:125 */     boolean justbody = 0 != (flags & 0x1);
/* 123:126 */     if (!justbody)
/* 124:    */     {
/* 125:127 */       sb.append("function ");
/* 126:128 */       sb.append(getFunctionName());
/* 127:129 */       sb.append("() {");
/* 128:    */     }
/* 129:131 */     sb.append("/*\n");
/* 130:132 */     sb.append(toString());
/* 131:133 */     sb.append(justbody ? "*/\n" : "*/}\n");
/* 132:134 */     return sb.toString();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String toString()
/* 136:    */   {
/* 137:140 */     StringBuffer sb = new StringBuffer();
/* 138:141 */     int i = 0;
/* 139:141 */     for (int N = this.methods.length; i != N; i++)
/* 140:    */     {
/* 141:142 */       Method method = this.methods[i].method();
/* 142:143 */       sb.append(JavaMembers.javaSignature(method.getReturnType()));
/* 143:144 */       sb.append(' ');
/* 144:145 */       sb.append(method.getName());
/* 145:146 */       sb.append(JavaMembers.liveConnectSignature(this.methods[i].argTypes));
/* 146:147 */       sb.append('\n');
/* 147:    */     }
/* 148:149 */     return sb.toString();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/* 152:    */   {
/* 153:157 */     if (this.methods.length == 0) {
/* 154:158 */       throw new RuntimeException("No methods defined for call");
/* 155:    */     }
/* 156:161 */     int index = findFunction(cx, this.methods, args);
/* 157:162 */     if (index < 0)
/* 158:    */     {
/* 159:163 */       Class<?> c = this.methods[0].method().getDeclaringClass();
/* 160:164 */       String sig = c.getName() + '.' + getFunctionName() + '(' + scriptSignature(args) + ')';
/* 161:    */       
/* 162:166 */       throw Context.reportRuntimeError1("msg.java.no_such_method", sig);
/* 163:    */     }
/* 164:169 */     MemberBox meth = this.methods[index];
/* 165:170 */     Class<?>[] argTypes = meth.argTypes;
/* 166:172 */     if (meth.vararg)
/* 167:    */     {
/* 168:174 */       Object[] newArgs = new Object[argTypes.length];
/* 169:175 */       for (int i = 0; i < argTypes.length - 1; i++) {
/* 170:176 */         newArgs[i] = Context.jsToJava(args[i], argTypes[i]);
/* 171:    */       }
/* 172:    */       Object varArgs;
/* 173:    */       Object varArgs;
/* 174:183 */       if ((args.length == argTypes.length) && ((args[(args.length - 1)] == null) || ((args[(args.length - 1)] instanceof NativeArray)) || ((args[(args.length - 1)] instanceof NativeJavaArray))))
/* 175:    */       {
/* 176:189 */         varArgs = Context.jsToJava(args[(args.length - 1)], argTypes[(argTypes.length - 1)]);
/* 177:    */       }
/* 178:    */       else
/* 179:    */       {
/* 180:193 */         Class<?> componentType = argTypes[(argTypes.length - 1)].getComponentType();
/* 181:    */         
/* 182:195 */         varArgs = Array.newInstance(componentType, args.length - argTypes.length + 1);
/* 183:197 */         for (int i = 0; i < Array.getLength(varArgs); i++)
/* 184:    */         {
/* 185:198 */           Object value = Context.jsToJava(args[(argTypes.length - 1 + i)], componentType);
/* 186:    */           
/* 187:200 */           Array.set(varArgs, i, value);
/* 188:    */         }
/* 189:    */       }
/* 190:205 */       newArgs[(argTypes.length - 1)] = varArgs;
/* 191:    */       
/* 192:207 */       args = newArgs;
/* 193:    */     }
/* 194:    */     else
/* 195:    */     {
/* 196:210 */       Object[] origArgs = args;
/* 197:211 */       for (int i = 0; i < args.length; i++)
/* 198:    */       {
/* 199:212 */         Object arg = args[i];
/* 200:213 */         Object coerced = Context.jsToJava(arg, argTypes[i]);
/* 201:214 */         if (coerced != arg)
/* 202:    */         {
/* 203:215 */           if (origArgs == args) {
/* 204:216 */             args = (Object[])args.clone();
/* 205:    */           }
/* 206:218 */           args[i] = coerced;
/* 207:    */         }
/* 208:    */       }
/* 209:    */     }
/* 210:    */     Object javaObject;
/* 211:223 */     if (meth.isStatic())
/* 212:    */     {
/* 213:224 */       javaObject = null;
/* 214:    */     }
/* 215:    */     else
/* 216:    */     {
/* 217:226 */       Scriptable o = thisObj;
/* 218:227 */       Class<?> c = meth.getDeclaringClass();
/* 219:    */       for (;;)
/* 220:    */       {
/* 221:229 */         if (o == null) {
/* 222:230 */           throw Context.reportRuntimeError3("msg.nonjava.method", getFunctionName(), ScriptRuntime.toString(thisObj), c.getName());
/* 223:    */         }
/* 224:234 */         if ((o instanceof Wrapper))
/* 225:    */         {
/* 226:235 */           Object javaObject = ((Wrapper)o).unwrap();
/* 227:236 */           if (c.isInstance(javaObject)) {
/* 228:    */             break;
/* 229:    */           }
/* 230:    */         }
/* 231:240 */         o = o.getPrototype();
/* 232:    */       }
/* 233:    */     }
/* 234:    */     Object javaObject;
/* 235:247 */     Object retval = meth.invoke(javaObject, args);
/* 236:248 */     Class<?> staticType = meth.method().getReturnType();
/* 237:    */     
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:258 */     Object wrapped = cx.getWrapFactory().wrap(cx, scope, retval, staticType);
/* 247:267 */     if ((wrapped == null) && (staticType == Void.TYPE)) {
/* 248:268 */       wrapped = Undefined.instance;
/* 249:    */     }
/* 250:270 */     return wrapped;
/* 251:    */   }
/* 252:    */   
/* 253:    */   static int findFunction(Context cx, MemberBox[] methodsOrCtors, Object[] args)
/* 254:    */   {
/* 255:281 */     if (methodsOrCtors.length == 0) {
/* 256:282 */       return -1;
/* 257:    */     }
/* 258:283 */     if (methodsOrCtors.length == 1)
/* 259:    */     {
/* 260:284 */       MemberBox member = methodsOrCtors[0];
/* 261:285 */       Class<?>[] argTypes = member.argTypes;
/* 262:286 */       int alength = argTypes.length;
/* 263:288 */       if (member.vararg)
/* 264:    */       {
/* 265:289 */         alength--;
/* 266:290 */         if (alength > args.length) {
/* 267:291 */           return -1;
/* 268:    */         }
/* 269:    */       }
/* 270:294 */       else if (alength != args.length)
/* 271:    */       {
/* 272:295 */         return -1;
/* 273:    */       }
/* 274:298 */       for (int j = 0; j != alength; j++) {
/* 275:299 */         if (!NativeJavaObject.canConvert(args[j], argTypes[j])) {
/* 276:302 */           return -1;
/* 277:    */         }
/* 278:    */       }
/* 279:306 */       return 0;
/* 280:    */     }
/* 281:309 */     int firstBestFit = -1;
/* 282:310 */     int[] extraBestFits = null;
/* 283:311 */     int extraBestFitsCount = 0;
/* 284:    */     label476:
/* 285:314 */     for (int i = 0; i < methodsOrCtors.length; i++)
/* 286:    */     {
/* 287:315 */       MemberBox member = methodsOrCtors[i];
/* 288:316 */       Class<?>[] argTypes = member.argTypes;
/* 289:317 */       int alength = argTypes.length;
/* 290:318 */       if (member.vararg)
/* 291:    */       {
/* 292:319 */         alength--;
/* 293:320 */         if (alength > args.length) {
/* 294:    */           continue;
/* 295:    */         }
/* 296:    */       }
/* 297:    */       else
/* 298:    */       {
/* 299:324 */         if (alength != args.length) {
/* 300:    */           continue;
/* 301:    */         }
/* 302:    */       }
/* 303:328 */       for (int j = 0; j < alength; j++) {
/* 304:329 */         if (!NativeJavaObject.canConvert(args[j], argTypes[j])) {
/* 305:    */           break label476;
/* 306:    */         }
/* 307:    */       }
/* 308:335 */       if (firstBestFit < 0)
/* 309:    */       {
/* 310:337 */         firstBestFit = i;
/* 311:    */       }
/* 312:    */       else
/* 313:    */       {
/* 314:343 */         int betterCount = 0;
/* 315:    */         
/* 316:345 */         int worseCount = 0;
/* 317:347 */         for (int j = -1; j != extraBestFitsCount; j++)
/* 318:    */         {
/* 319:    */           int bestFitIndex;
/* 320:    */           int bestFitIndex;
/* 321:349 */           if (j == -1) {
/* 322:350 */             bestFitIndex = firstBestFit;
/* 323:    */           } else {
/* 324:352 */             bestFitIndex = extraBestFits[j];
/* 325:    */           }
/* 326:354 */           MemberBox bestFit = methodsOrCtors[bestFitIndex];
/* 327:355 */           if ((cx.hasFeature(13)) && ((bestFit.member().getModifiers() & 0x1) != (member.member().getModifiers() & 0x1)))
/* 328:    */           {
/* 329:362 */             if ((bestFit.member().getModifiers() & 0x1) == 0) {
/* 330:363 */               betterCount++;
/* 331:    */             } else {
/* 332:365 */               worseCount++;
/* 333:    */             }
/* 334:    */           }
/* 335:    */           else
/* 336:    */           {
/* 337:367 */             int preference = preferSignature(args, argTypes, member.vararg, bestFit.argTypes, bestFit.vararg);
/* 338:371 */             if (preference == 3) {
/* 339:    */               break;
/* 340:    */             }
/* 341:373 */             if (preference == 1)
/* 342:    */             {
/* 343:374 */               betterCount++;
/* 344:    */             }
/* 345:375 */             else if (preference == 2)
/* 346:    */             {
/* 347:376 */               worseCount++;
/* 348:    */             }
/* 349:    */             else
/* 350:    */             {
/* 351:378 */               if (preference != 0) {
/* 352:378 */                 Kit.codeBug();
/* 353:    */               }
/* 354:384 */               if ((!bestFit.isStatic()) || (!bestFit.getDeclaringClass().isAssignableFrom(member.getDeclaringClass()))) {
/* 355:    */                 break label476;
/* 356:    */               }
/* 357:395 */               if (j == -1)
/* 358:    */               {
/* 359:396 */                 firstBestFit = i;
/* 360:    */                 break label476;
/* 361:    */               }
/* 362:398 */               extraBestFits[j] = i;
/* 363:    */               break label476;
/* 364:    */             }
/* 365:    */           }
/* 366:    */         }
/* 367:409 */         if (betterCount == 1 + extraBestFitsCount)
/* 368:    */         {
/* 369:413 */           firstBestFit = i;
/* 370:414 */           extraBestFitsCount = 0;
/* 371:    */         }
/* 372:415 */         else if (worseCount != 1 + extraBestFitsCount)
/* 373:    */         {
/* 374:423 */           if (extraBestFits == null) {
/* 375:425 */             extraBestFits = new int[methodsOrCtors.length - 1];
/* 376:    */           }
/* 377:427 */           extraBestFits[extraBestFitsCount] = i;
/* 378:428 */           extraBestFitsCount++;
/* 379:    */         }
/* 380:    */       }
/* 381:    */     }
/* 382:433 */     if (firstBestFit < 0) {
/* 383:435 */       return -1;
/* 384:    */     }
/* 385:436 */     if (extraBestFitsCount == 0) {
/* 386:438 */       return firstBestFit;
/* 387:    */     }
/* 388:442 */     StringBuffer buf = new StringBuffer();
/* 389:443 */     for (int j = -1; j != extraBestFitsCount; j++)
/* 390:    */     {
/* 391:    */       int bestFitIndex;
/* 392:    */       int bestFitIndex;
/* 393:445 */       if (j == -1) {
/* 394:446 */         bestFitIndex = firstBestFit;
/* 395:    */       } else {
/* 396:448 */         bestFitIndex = extraBestFits[j];
/* 397:    */       }
/* 398:450 */       buf.append("\n    ");
/* 399:451 */       buf.append(methodsOrCtors[bestFitIndex].toJavaDeclaration());
/* 400:    */     }
/* 401:454 */     MemberBox firstFitMember = methodsOrCtors[firstBestFit];
/* 402:455 */     String memberName = firstFitMember.getName();
/* 403:456 */     String memberClass = firstFitMember.getDeclaringClass().getName();
/* 404:458 */     if (methodsOrCtors[0].isMethod()) {
/* 405:459 */       throw Context.reportRuntimeError3("msg.constructor.ambiguous", memberName, scriptSignature(args), buf.toString());
/* 406:    */     }
/* 407:463 */     throw Context.reportRuntimeError4("msg.method.ambiguous", memberClass, memberName, scriptSignature(args), buf.toString());
/* 408:    */   }
/* 409:    */   
/* 410:    */   private static int preferSignature(Object[] args, Class<?>[] sig1, boolean vararg1, Class<?>[] sig2, boolean vararg2)
/* 411:    */   {
/* 412:487 */     int totalPreference = 0;
/* 413:488 */     for (int j = 0; j < args.length; j++)
/* 414:    */     {
/* 415:489 */       Class<?> type1 = (vararg1) && (j >= sig1.length) ? sig1[(sig1.length - 1)] : sig1[j];
/* 416:490 */       Class<?> type2 = (vararg2) && (j >= sig2.length) ? sig2[(sig2.length - 1)] : sig2[j];
/* 417:491 */       if (type1 != type2)
/* 418:    */       {
/* 419:494 */         Object arg = args[j];
/* 420:    */         
/* 421:    */ 
/* 422:    */ 
/* 423:498 */         int rank1 = NativeJavaObject.getConversionWeight(arg, type1);
/* 424:499 */         int rank2 = NativeJavaObject.getConversionWeight(arg, type2);
/* 425:    */         int preference;
/* 426:    */         int preference;
/* 427:502 */         if (rank1 < rank2)
/* 428:    */         {
/* 429:503 */           preference = 1;
/* 430:    */         }
/* 431:    */         else
/* 432:    */         {
/* 433:    */           int preference;
/* 434:504 */           if (rank1 > rank2)
/* 435:    */           {
/* 436:505 */             preference = 2;
/* 437:    */           }
/* 438:    */           else
/* 439:    */           {
/* 440:    */             int preference;
/* 441:508 */             if (rank1 == 0)
/* 442:    */             {
/* 443:    */               int preference;
/* 444:509 */               if (type1.isAssignableFrom(type2))
/* 445:    */               {
/* 446:510 */                 preference = 2;
/* 447:    */               }
/* 448:    */               else
/* 449:    */               {
/* 450:    */                 int preference;
/* 451:511 */                 if (type2.isAssignableFrom(type1)) {
/* 452:512 */                   preference = 1;
/* 453:    */                 } else {
/* 454:514 */                   preference = 3;
/* 455:    */                 }
/* 456:    */               }
/* 457:    */             }
/* 458:    */             else
/* 459:    */             {
/* 460:517 */               preference = 3;
/* 461:    */             }
/* 462:    */           }
/* 463:    */         }
/* 464:521 */         totalPreference |= preference;
/* 465:523 */         if (totalPreference == 3) {
/* 466:    */           break;
/* 467:    */         }
/* 468:    */       }
/* 469:    */     }
/* 470:527 */     return totalPreference;
/* 471:    */   }
/* 472:    */   
/* 473:    */   private static void printDebug(String msg, MemberBox member, Object[] args) {}
/* 474:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.NativeJavaMethod
 * JD-Core Version:    0.7.0.1
 */