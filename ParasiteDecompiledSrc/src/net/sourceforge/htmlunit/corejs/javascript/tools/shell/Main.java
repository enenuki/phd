/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.tools.shell;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.io.InputStreamReader;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.UnsupportedEncodingException;
/*   9:    */ import java.lang.ref.ReferenceQueue;
/*  10:    */ import java.lang.ref.SoftReference;
/*  11:    */ import java.lang.reflect.UndeclaredThrowableException;
/*  12:    */ import java.security.MessageDigest;
/*  13:    */ import java.security.NoSuchAlgorithmException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Arrays;
/*  16:    */ import java.util.LinkedHashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map.Entry;
/*  19:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  20:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextAction;
/*  21:    */ import net.sourceforge.htmlunit.corejs.javascript.EvaluatorException;
/*  22:    */ import net.sourceforge.htmlunit.corejs.javascript.Function;
/*  23:    */ import net.sourceforge.htmlunit.corejs.javascript.GeneratedClassLoader;
/*  24:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*  25:    */ import net.sourceforge.htmlunit.corejs.javascript.NativeArray;
/*  26:    */ import net.sourceforge.htmlunit.corejs.javascript.RhinoException;
/*  27:    */ import net.sourceforge.htmlunit.corejs.javascript.Script;
/*  28:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  29:    */ import net.sourceforge.htmlunit.corejs.javascript.SecurityController;
/*  30:    */ import net.sourceforge.htmlunit.corejs.javascript.commonjs.module.Require;
/*  31:    */ import net.sourceforge.htmlunit.corejs.javascript.tools.SourceReader;
/*  32:    */ import net.sourceforge.htmlunit.corejs.javascript.tools.ToolErrorReporter;
/*  33:    */ 
/*  34:    */ public class Main
/*  35:    */ {
/*  36: 90 */   public static ShellContextFactory shellContextFactory = new ShellContextFactory();
/*  37: 92 */   public static Global global = new Global();
/*  38:    */   protected static ToolErrorReporter errorReporter;
/*  39: 94 */   protected static int exitCode = 0;
/*  40:    */   private static final int EXITCODE_RUNTIME_ERROR = 3;
/*  41:    */   private static final int EXITCODE_FILE_NOT_FOUND = 4;
/*  42: 97 */   static boolean processStdin = true;
/*  43: 98 */   static List<String> fileList = new ArrayList();
/*  44:    */   static List<String> modulePath;
/*  45:    */   static String mainModule;
/*  46:101 */   static boolean sandboxed = false;
/*  47:    */   static Require require;
/*  48:    */   private static SecurityProxy securityImpl;
/*  49:104 */   private static final ScriptCache scriptCache = new ScriptCache(32);
/*  50:    */   
/*  51:    */   static
/*  52:    */   {
/*  53:107 */     global.initQuitAction(new IProxy(3));
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static class IProxy
/*  57:    */     implements ContextAction, QuitAction
/*  58:    */   {
/*  59:    */     private static final int PROCESS_FILES = 1;
/*  60:    */     private static final int EVAL_INLINE_SCRIPT = 2;
/*  61:    */     private static final int SYSTEM_EXIT = 3;
/*  62:    */     private int type;
/*  63:    */     String[] args;
/*  64:    */     String scriptText;
/*  65:    */     
/*  66:    */     IProxy(int type)
/*  67:    */     {
/*  68:125 */       this.type = type;
/*  69:    */     }
/*  70:    */     
/*  71:    */     public Object run(Context cx)
/*  72:    */     {
/*  73:130 */       if ((Main.modulePath != null) || (Main.mainModule != null)) {
/*  74:131 */         Main.require = Main.global.installRequire(cx, Main.modulePath, Main.sandboxed);
/*  75:    */       }
/*  76:133 */       if (this.type == 1)
/*  77:    */       {
/*  78:134 */         Main.processFiles(cx, this.args);
/*  79:    */       }
/*  80:135 */       else if (this.type == 2)
/*  81:    */       {
/*  82:136 */         Script script = Main.loadScriptFromSource(cx, this.scriptText, "<command>", 1, null);
/*  83:138 */         if (script != null) {
/*  84:139 */           Main.evaluateScript(script, cx, Main.getGlobal());
/*  85:    */         }
/*  86:    */       }
/*  87:    */       else
/*  88:    */       {
/*  89:142 */         throw Kit.codeBug();
/*  90:    */       }
/*  91:144 */       return null;
/*  92:    */     }
/*  93:    */     
/*  94:    */     public void quit(Context cx, int exitCode)
/*  95:    */     {
/*  96:149 */       if (this.type == 3)
/*  97:    */       {
/*  98:150 */         System.exit(exitCode);
/*  99:151 */         return;
/* 100:    */       }
/* 101:153 */       throw Kit.codeBug();
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static void main(String[] args)
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:167 */       if (Boolean.getBoolean("rhino.use_java_policy_security")) {
/* 110:168 */         initJavaPolicySecuritySupport();
/* 111:    */       }
/* 112:    */     }
/* 113:    */     catch (SecurityException ex)
/* 114:    */     {
/* 115:171 */       ex.printStackTrace(System.err);
/* 116:    */     }
/* 117:174 */     int result = exec(args);
/* 118:175 */     if (result != 0) {
/* 119:176 */       System.exit(result);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static int exec(String[] origArgs)
/* 124:    */   {
/* 125:185 */     errorReporter = new ToolErrorReporter(false, global.getErr());
/* 126:186 */     shellContextFactory.setErrorReporter(errorReporter);
/* 127:187 */     String[] args = processOptions(origArgs);
/* 128:188 */     if ((mainModule != null) && (!fileList.contains(mainModule))) {
/* 129:189 */       fileList.add(mainModule);
/* 130:    */     }
/* 131:190 */     if (processStdin) {
/* 132:191 */       fileList.add(null);
/* 133:    */     }
/* 134:193 */     if (!global.initialized) {
/* 135:194 */       global.init(shellContextFactory);
/* 136:    */     }
/* 137:196 */     IProxy iproxy = new IProxy(1);
/* 138:197 */     iproxy.args = args;
/* 139:198 */     shellContextFactory.call(iproxy);
/* 140:    */     
/* 141:200 */     return exitCode;
/* 142:    */   }
/* 143:    */   
/* 144:    */   static void processFiles(Context cx, String[] args)
/* 145:    */   {
/* 146:208 */     Object[] array = new Object[args.length];
/* 147:209 */     System.arraycopy(args, 0, array, 0, args.length);
/* 148:210 */     Scriptable argsObj = cx.newArray(global, array);
/* 149:211 */     global.defineProperty("arguments", argsObj, 2);
/* 150:214 */     for (String file : fileList) {
/* 151:215 */       processSource(cx, file);
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static Global getGlobal()
/* 156:    */   {
/* 157:221 */     return global;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static String[] processOptions(String[] args)
/* 161:    */   {
/* 162:230 */     for (int i = 0;; i++)
/* 163:    */     {
/* 164:231 */       if (i == args.length) {
/* 165:232 */         return new String[0];
/* 166:    */       }
/* 167:234 */       String arg = args[i];
/* 168:235 */       if (!arg.startsWith("-"))
/* 169:    */       {
/* 170:236 */         processStdin = false;
/* 171:237 */         fileList.add(arg);
/* 172:238 */         String[] result = new String[args.length - i - 1];
/* 173:239 */         System.arraycopy(args, i + 1, result, 0, args.length - i - 1);
/* 174:240 */         return result;
/* 175:    */       }
/* 176:242 */       if (arg.equals("-version"))
/* 177:    */       {
/* 178:243 */         i++;
/* 179:243 */         if (i == args.length)
/* 180:    */         {
/* 181:244 */           String usageError = arg;
/* 182:245 */           break;
/* 183:    */         }
/* 184:    */         int version;
/* 185:    */         try
/* 186:    */         {
/* 187:249 */           version = Integer.parseInt(args[i]);
/* 188:    */         }
/* 189:    */         catch (NumberFormatException ex)
/* 190:    */         {
/* 191:251 */           String usageError = args[i];
/* 192:252 */           break;
/* 193:    */         }
/* 194:254 */         if (!Context.isValidLanguageVersion(version))
/* 195:    */         {
/* 196:255 */           String usageError = args[i];
/* 197:256 */           break;
/* 198:    */         }
/* 199:258 */         shellContextFactory.setLanguageVersion(version);
/* 200:    */       }
/* 201:261 */       else if ((arg.equals("-opt")) || (arg.equals("-O")))
/* 202:    */       {
/* 203:262 */         i++;
/* 204:262 */         if (i == args.length)
/* 205:    */         {
/* 206:263 */           String usageError = arg;
/* 207:264 */           break;
/* 208:    */         }
/* 209:    */         int opt;
/* 210:    */         try
/* 211:    */         {
/* 212:268 */           opt = Integer.parseInt(args[i]);
/* 213:    */         }
/* 214:    */         catch (NumberFormatException ex)
/* 215:    */         {
/* 216:270 */           String usageError = args[i];
/* 217:271 */           break;
/* 218:    */         }
/* 219:273 */         if (opt == -2)
/* 220:    */         {
/* 221:275 */           opt = -1;
/* 222:    */         }
/* 223:276 */         else if (!Context.isValidOptimizationLevel(opt))
/* 224:    */         {
/* 225:277 */           String usageError = args[i];
/* 226:278 */           break;
/* 227:    */         }
/* 228:280 */         shellContextFactory.setOptimizationLevel(opt);
/* 229:    */       }
/* 230:283 */       else if (arg.equals("-encoding"))
/* 231:    */       {
/* 232:284 */         i++;
/* 233:284 */         if (i == args.length)
/* 234:    */         {
/* 235:285 */           String usageError = arg;
/* 236:286 */           break;
/* 237:    */         }
/* 238:288 */         String enc = args[i];
/* 239:289 */         shellContextFactory.setCharacterEncoding(enc);
/* 240:    */       }
/* 241:292 */       else if (arg.equals("-strict"))
/* 242:    */       {
/* 243:293 */         shellContextFactory.setStrictMode(true);
/* 244:294 */         shellContextFactory.setAllowReservedKeywords(false);
/* 245:295 */         errorReporter.setIsReportingWarnings(true);
/* 246:    */       }
/* 247:298 */       else if (arg.equals("-fatal-warnings"))
/* 248:    */       {
/* 249:299 */         shellContextFactory.setWarningAsError(true);
/* 250:    */       }
/* 251:302 */       else if (arg.equals("-e"))
/* 252:    */       {
/* 253:303 */         processStdin = false;
/* 254:304 */         i++;
/* 255:304 */         if (i == args.length)
/* 256:    */         {
/* 257:305 */           String usageError = arg;
/* 258:306 */           break;
/* 259:    */         }
/* 260:308 */         if (!global.initialized) {
/* 261:309 */           global.init(shellContextFactory);
/* 262:    */         }
/* 263:311 */         IProxy iproxy = new IProxy(2);
/* 264:312 */         iproxy.scriptText = args[i];
/* 265:313 */         shellContextFactory.call(iproxy);
/* 266:    */       }
/* 267:316 */       else if (arg.equals("-modules"))
/* 268:    */       {
/* 269:317 */         i++;
/* 270:317 */         if (i == args.length)
/* 271:    */         {
/* 272:318 */           String usageError = arg;
/* 273:319 */           break;
/* 274:    */         }
/* 275:321 */         if (modulePath == null) {
/* 276:322 */           modulePath = new ArrayList();
/* 277:    */         }
/* 278:324 */         modulePath.add(args[i]);
/* 279:    */       }
/* 280:327 */       else if (arg.equals("-main"))
/* 281:    */       {
/* 282:328 */         i++;
/* 283:328 */         if (i == args.length)
/* 284:    */         {
/* 285:329 */           String usageError = arg;
/* 286:330 */           break;
/* 287:    */         }
/* 288:332 */         mainModule = args[i];
/* 289:333 */         processStdin = false;
/* 290:    */       }
/* 291:336 */       else if (arg.equals("-sandbox"))
/* 292:    */       {
/* 293:337 */         sandboxed = true;
/* 294:    */       }
/* 295:340 */       else if (arg.equals("-w"))
/* 296:    */       {
/* 297:341 */         errorReporter.setIsReportingWarnings(true);
/* 298:    */       }
/* 299:344 */       else if (arg.equals("-f"))
/* 300:    */       {
/* 301:345 */         processStdin = false;
/* 302:346 */         i++;
/* 303:346 */         if (i == args.length)
/* 304:    */         {
/* 305:347 */           String usageError = arg;
/* 306:348 */           break;
/* 307:    */         }
/* 308:350 */         fileList.add(args[i].equals("-") ? null : args[i]);
/* 309:    */       }
/* 310:353 */       else if (arg.equals("-sealedlib"))
/* 311:    */       {
/* 312:354 */         global.setSealedStdLib(true);
/* 313:    */       }
/* 314:357 */       else if (arg.equals("-debug"))
/* 315:    */       {
/* 316:358 */         shellContextFactory.setGeneratingDebug(true);
/* 317:    */       }
/* 318:    */       else
/* 319:    */       {
/* 320:361 */         if ((arg.equals("-?")) || (arg.equals("-help")))
/* 321:    */         {
/* 322:364 */           global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
/* 323:    */           
/* 324:366 */           System.exit(1);
/* 325:    */         }
/* 326:368 */         String usageError = arg;
/* 327:369 */         break;
/* 328:    */       }
/* 329:    */     }
/* 330:    */     String usageError;
/* 331:372 */     global.getOut().println(ToolErrorReporter.getMessage("msg.shell.invalid", usageError));
/* 332:    */     
/* 333:374 */     global.getOut().println(ToolErrorReporter.getMessage("msg.shell.usage", Main.class.getName()));
/* 334:    */     
/* 335:376 */     System.exit(1);
/* 336:377 */     return null;
/* 337:    */   }
/* 338:    */   
/* 339:    */   private static void initJavaPolicySecuritySupport()
/* 340:    */   {
/* 341:    */     Throwable exObj;
/* 342:    */     try
/* 343:    */     {
/* 344:384 */       Class<?> cl = Class.forName("net.sourceforge.htmlunit.corejs.javascript.tools.shell.JavaPolicySecurity");
/* 345:    */       
/* 346:386 */       securityImpl = (SecurityProxy)cl.newInstance();
/* 347:387 */       SecurityController.initGlobal(securityImpl);
/* 348:388 */       return;
/* 349:    */     }
/* 350:    */     catch (ClassNotFoundException ex)
/* 351:    */     {
/* 352:390 */       exObj = ex;
/* 353:    */     }
/* 354:    */     catch (IllegalAccessException ex)
/* 355:    */     {
/* 356:392 */       exObj = ex;
/* 357:    */     }
/* 358:    */     catch (InstantiationException ex)
/* 359:    */     {
/* 360:394 */       exObj = ex;
/* 361:    */     }
/* 362:    */     catch (LinkageError ex)
/* 363:    */     {
/* 364:396 */       exObj = ex;
/* 365:    */     }
/* 366:398 */     throw Kit.initCause(new IllegalStateException("Can not load security support: " + exObj), exObj);
/* 367:    */   }
/* 368:    */   
/* 369:    */   public static void processSource(Context cx, String filename)
/* 370:    */   {
/* 371:411 */     if ((filename == null) || (filename.equals("-")))
/* 372:    */     {
/* 373:412 */       PrintStream ps = global.getErr();
/* 374:413 */       if (filename == null) {
/* 375:415 */         ps.println(cx.getImplementationVersion());
/* 376:    */       }
/* 377:418 */       String charEnc = shellContextFactory.getCharacterEncoding();
/* 378:419 */       if (charEnc == null) {
/* 379:421 */         charEnc = System.getProperty("file.encoding");
/* 380:    */       }
/* 381:    */       BufferedReader in;
/* 382:    */       try
/* 383:    */       {
/* 384:426 */         in = new BufferedReader(new InputStreamReader(global.getIn(), charEnc));
/* 385:    */       }
/* 386:    */       catch (UnsupportedEncodingException e)
/* 387:    */       {
/* 388:431 */         throw new UndeclaredThrowableException(e);
/* 389:    */       }
/* 390:433 */       int lineno = 1;
/* 391:434 */       boolean hitEOF = false;
/* 392:435 */       while (!hitEOF)
/* 393:    */       {
/* 394:436 */         String[] prompts = global.getPrompts(cx);
/* 395:437 */         if (filename == null) {
/* 396:438 */           ps.print(prompts[0]);
/* 397:    */         }
/* 398:439 */         ps.flush();
/* 399:440 */         String source = "";
/* 400:    */         for (;;)
/* 401:    */         {
/* 402:    */           String newline;
/* 403:    */           try
/* 404:    */           {
/* 405:446 */             newline = in.readLine();
/* 406:    */           }
/* 407:    */           catch (IOException ioe)
/* 408:    */           {
/* 409:449 */             ps.println(ioe.toString());
/* 410:450 */             break;
/* 411:    */           }
/* 412:452 */           if (newline == null)
/* 413:    */           {
/* 414:453 */             hitEOF = true;
/* 415:454 */             break;
/* 416:    */           }
/* 417:456 */           source = source + newline + "\n";
/* 418:457 */           lineno++;
/* 419:458 */           if (cx.stringIsCompilableUnit(source)) {
/* 420:    */             break;
/* 421:    */           }
/* 422:460 */           ps.print(prompts[1]);
/* 423:    */         }
/* 424:462 */         Script script = loadScriptFromSource(cx, source, "<stdin>", lineno, null);
/* 425:464 */         if (script != null)
/* 426:    */         {
/* 427:465 */           Object result = evaluateScript(script, cx, global);
/* 428:467 */           if ((result != Context.getUndefinedValue()) && ((!(result instanceof Function)) || (!source.trim().startsWith("function")))) {
/* 429:    */             try
/* 430:    */             {
/* 431:472 */               ps.println(Context.toString(result));
/* 432:    */             }
/* 433:    */             catch (RhinoException rex)
/* 434:    */             {
/* 435:474 */               ToolErrorReporter.reportException(cx.getErrorReporter(), rex);
/* 436:    */             }
/* 437:    */           }
/* 438:478 */           NativeArray h = global.history;
/* 439:479 */           h.put((int)h.getLength(), h, source);
/* 440:    */         }
/* 441:    */       }
/* 442:482 */       ps.println();
/* 443:    */     }
/* 444:483 */     else if (filename.equals(mainModule))
/* 445:    */     {
/* 446:    */       try
/* 447:    */       {
/* 448:485 */         require.requireMain(cx, filename);
/* 449:    */       }
/* 450:    */       catch (RhinoException rex)
/* 451:    */       {
/* 452:487 */         ToolErrorReporter.reportException(cx.getErrorReporter(), rex);
/* 453:    */         
/* 454:489 */         exitCode = 3;
/* 455:    */       }
/* 456:    */       catch (VirtualMachineError ex)
/* 457:    */       {
/* 458:492 */         ex.printStackTrace();
/* 459:493 */         String msg = ToolErrorReporter.getMessage("msg.uncaughtJSException", ex.toString());
/* 460:    */         
/* 461:495 */         exitCode = 3;
/* 462:496 */         Context.reportError(msg);
/* 463:    */       }
/* 464:    */     }
/* 465:    */     else
/* 466:    */     {
/* 467:499 */       processFile(cx, global, filename);
/* 468:    */     }
/* 469:    */   }
/* 470:    */   
/* 471:    */   public static void processFile(Context cx, Scriptable scope, String filename)
/* 472:    */   {
/* 473:506 */     if (securityImpl == null) {
/* 474:507 */       processFileSecure(cx, scope, filename, null);
/* 475:    */     } else {
/* 476:509 */       securityImpl.callProcessFileSecure(cx, scope, filename);
/* 477:    */     }
/* 478:    */   }
/* 479:    */   
/* 480:    */   static void processFileSecure(Context cx, Scriptable scope, String path, Object securityDomain)
/* 481:    */   {
/* 482:516 */     boolean isClass = path.endsWith(".class");
/* 483:517 */     Object source = readFileOrUrl(path, !isClass);
/* 484:519 */     if (source == null)
/* 485:    */     {
/* 486:520 */       exitCode = 4;
/* 487:521 */       return;
/* 488:    */     }
/* 489:524 */     byte[] digest = getDigest(source);
/* 490:525 */     String key = path + "_" + cx.getOptimizationLevel();
/* 491:526 */     ScriptReference ref = scriptCache.get(key, digest);
/* 492:527 */     Script script = ref != null ? (Script)ref.get() : null;
/* 493:529 */     if (script == null)
/* 494:    */     {
/* 495:530 */       if (isClass)
/* 496:    */       {
/* 497:531 */         script = loadCompiledScript(cx, path, (byte[])source, securityDomain);
/* 498:    */       }
/* 499:    */       else
/* 500:    */       {
/* 501:533 */         String strSrc = (String)source;
/* 502:537 */         if ((strSrc.length() > 0) && (strSrc.charAt(0) == '#')) {
/* 503:538 */           for (int i = 1; i != strSrc.length(); i++)
/* 504:    */           {
/* 505:539 */             int c = strSrc.charAt(i);
/* 506:540 */             if ((c == 10) || (c == 13))
/* 507:    */             {
/* 508:541 */               strSrc = strSrc.substring(i);
/* 509:542 */               break;
/* 510:    */             }
/* 511:    */           }
/* 512:    */         }
/* 513:546 */         script = loadScriptFromSource(cx, strSrc, path, 1, securityDomain);
/* 514:    */       }
/* 515:548 */       scriptCache.put(key, digest, script);
/* 516:    */     }
/* 517:551 */     if (script != null) {
/* 518:552 */       evaluateScript(script, cx, scope);
/* 519:    */     }
/* 520:    */   }
/* 521:    */   
/* 522:    */   public static Script loadScriptFromSource(Context cx, String scriptSource, String path, int lineno, Object securityDomain)
/* 523:    */   {
/* 524:    */     try
/* 525:    */     {
/* 526:561 */       return cx.compileString(scriptSource, path, lineno, securityDomain);
/* 527:    */     }
/* 528:    */     catch (EvaluatorException ee)
/* 529:    */     {
/* 530:565 */       exitCode = 3;
/* 531:    */     }
/* 532:    */     catch (RhinoException rex)
/* 533:    */     {
/* 534:567 */       ToolErrorReporter.reportException(cx.getErrorReporter(), rex);
/* 535:    */       
/* 536:569 */       exitCode = 3;
/* 537:    */     }
/* 538:    */     catch (VirtualMachineError ex)
/* 539:    */     {
/* 540:572 */       ex.printStackTrace();
/* 541:573 */       String msg = ToolErrorReporter.getMessage("msg.uncaughtJSException", ex.toString());
/* 542:    */       
/* 543:575 */       exitCode = 3;
/* 544:576 */       Context.reportError(msg);
/* 545:    */     }
/* 546:578 */     return null;
/* 547:    */   }
/* 548:    */   
/* 549:    */   private static byte[] getDigest(Object source)
/* 550:    */   {
/* 551:582 */     byte[] digest = null;
/* 552:584 */     if (source != null)
/* 553:    */     {
/* 554:    */       byte[] bytes;
/* 555:585 */       if ((source instanceof String)) {
/* 556:    */         try
/* 557:    */         {
/* 558:587 */           bytes = ((String)source).getBytes("UTF-8");
/* 559:    */         }
/* 560:    */         catch (UnsupportedEncodingException ue)
/* 561:    */         {
/* 562:589 */           byte[] bytes = ((String)source).getBytes();
/* 563:    */         }
/* 564:    */       } else {
/* 565:592 */         bytes = (byte[])source;
/* 566:    */       }
/* 567:    */       try
/* 568:    */       {
/* 569:595 */         MessageDigest md = MessageDigest.getInstance("MD5");
/* 570:596 */         digest = md.digest(bytes);
/* 571:    */       }
/* 572:    */       catch (NoSuchAlgorithmException nsa)
/* 573:    */       {
/* 574:599 */         throw new RuntimeException(nsa);
/* 575:    */       }
/* 576:    */     }
/* 577:603 */     return digest;
/* 578:    */   }
/* 579:    */   
/* 580:    */   private static Script loadCompiledScript(Context cx, String path, byte[] data, Object securityDomain)
/* 581:    */   {
/* 582:609 */     if (data == null)
/* 583:    */     {
/* 584:610 */       exitCode = 4;
/* 585:611 */       return null;
/* 586:    */     }
/* 587:615 */     int nameStart = path.lastIndexOf('/');
/* 588:616 */     if (nameStart < 0) {
/* 589:617 */       nameStart = 0;
/* 590:    */     } else {
/* 591:619 */       nameStart++;
/* 592:    */     }
/* 593:621 */     int nameEnd = path.lastIndexOf('.');
/* 594:622 */     if (nameEnd < nameStart) {
/* 595:625 */       nameEnd = path.length();
/* 596:    */     }
/* 597:627 */     String name = path.substring(nameStart, nameEnd);
/* 598:    */     try
/* 599:    */     {
/* 600:629 */       GeneratedClassLoader loader = SecurityController.createLoader(cx.getApplicationClassLoader(), securityDomain);
/* 601:630 */       Class<?> clazz = loader.defineClass(name, data);
/* 602:631 */       loader.linkClass(clazz);
/* 603:632 */       if (!Script.class.isAssignableFrom(clazz)) {
/* 604:633 */         throw Context.reportRuntimeError("msg.must.implement.Script");
/* 605:    */       }
/* 606:635 */       return (Script)clazz.newInstance();
/* 607:    */     }
/* 608:    */     catch (RhinoException rex)
/* 609:    */     {
/* 610:637 */       ToolErrorReporter.reportException(cx.getErrorReporter(), rex);
/* 611:    */       
/* 612:639 */       exitCode = 3;
/* 613:    */     }
/* 614:    */     catch (IllegalAccessException iaex)
/* 615:    */     {
/* 616:641 */       exitCode = 3;
/* 617:642 */       Context.reportError(iaex.toString());
/* 618:    */     }
/* 619:    */     catch (InstantiationException inex)
/* 620:    */     {
/* 621:644 */       exitCode = 3;
/* 622:645 */       Context.reportError(inex.toString());
/* 623:    */     }
/* 624:647 */     return null;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public static Object evaluateScript(Script script, Context cx, Scriptable scope)
/* 628:    */   {
/* 629:    */     try
/* 630:    */     {
/* 631:654 */       return script.exec(cx, scope);
/* 632:    */     }
/* 633:    */     catch (RhinoException rex)
/* 634:    */     {
/* 635:656 */       ToolErrorReporter.reportException(cx.getErrorReporter(), rex);
/* 636:    */       
/* 637:658 */       exitCode = 3;
/* 638:    */     }
/* 639:    */     catch (VirtualMachineError ex)
/* 640:    */     {
/* 641:661 */       ex.printStackTrace();
/* 642:662 */       String msg = ToolErrorReporter.getMessage("msg.uncaughtJSException", ex.toString());
/* 643:    */       
/* 644:664 */       exitCode = 3;
/* 645:665 */       Context.reportError(msg);
/* 646:    */     }
/* 647:667 */     return Context.getUndefinedValue();
/* 648:    */   }
/* 649:    */   
/* 650:    */   public static InputStream getIn()
/* 651:    */   {
/* 652:671 */     return getGlobal().getIn();
/* 653:    */   }
/* 654:    */   
/* 655:    */   public static void setIn(InputStream in)
/* 656:    */   {
/* 657:675 */     getGlobal().setIn(in);
/* 658:    */   }
/* 659:    */   
/* 660:    */   public static PrintStream getOut()
/* 661:    */   {
/* 662:679 */     return getGlobal().getOut();
/* 663:    */   }
/* 664:    */   
/* 665:    */   public static void setOut(PrintStream out)
/* 666:    */   {
/* 667:683 */     getGlobal().setOut(out);
/* 668:    */   }
/* 669:    */   
/* 670:    */   public static PrintStream getErr()
/* 671:    */   {
/* 672:687 */     return getGlobal().getErr();
/* 673:    */   }
/* 674:    */   
/* 675:    */   public static void setErr(PrintStream err)
/* 676:    */   {
/* 677:691 */     getGlobal().setErr(err);
/* 678:    */   }
/* 679:    */   
/* 680:    */   private static Object readFileOrUrl(String path, boolean convertToString)
/* 681:    */   {
/* 682:    */     try
/* 683:    */     {
/* 684:702 */       return SourceReader.readFileOrUrl(path, convertToString, shellContextFactory.getCharacterEncoding());
/* 685:    */     }
/* 686:    */     catch (IOException ex)
/* 687:    */     {
/* 688:705 */       Context.reportError(ToolErrorReporter.getMessage("msg.couldnt.read.source", path, ex.getMessage()));
/* 689:    */     }
/* 690:707 */     return null;
/* 691:    */   }
/* 692:    */   
/* 693:    */   static class ScriptReference
/* 694:    */     extends SoftReference<Script>
/* 695:    */   {
/* 696:    */     String path;
/* 697:    */     byte[] digest;
/* 698:    */     
/* 699:    */     ScriptReference(String path, byte[] digest, Script script, ReferenceQueue<Script> queue)
/* 700:    */     {
/* 701:717 */       super(queue);
/* 702:718 */       this.path = path;
/* 703:719 */       this.digest = digest;
/* 704:    */     }
/* 705:    */   }
/* 706:    */   
/* 707:    */   static class ScriptCache
/* 708:    */     extends LinkedHashMap<String, Main.ScriptReference>
/* 709:    */   {
/* 710:    */     ReferenceQueue<Script> queue;
/* 711:    */     int capacity;
/* 712:    */     
/* 713:    */     ScriptCache(int capacity)
/* 714:    */     {
/* 715:728 */       super(2.0F, true);
/* 716:729 */       this.capacity = capacity;
/* 717:730 */       this.queue = new ReferenceQueue();
/* 718:    */     }
/* 719:    */     
/* 720:    */     protected boolean removeEldestEntry(Map.Entry<String, Main.ScriptReference> eldest)
/* 721:    */     {
/* 722:735 */       return size() > this.capacity;
/* 723:    */     }
/* 724:    */     
/* 725:    */     Main.ScriptReference get(String path, byte[] digest)
/* 726:    */     {
/* 727:740 */       while ((ref = (Main.ScriptReference)this.queue.poll()) != null) {
/* 728:741 */         remove(ref.path);
/* 729:    */       }
/* 730:743 */       Main.ScriptReference ref = (Main.ScriptReference)get(path);
/* 731:744 */       if ((ref != null) && (!Arrays.equals(digest, ref.digest)))
/* 732:    */       {
/* 733:745 */         remove(ref.path);
/* 734:746 */         ref = null;
/* 735:    */       }
/* 736:748 */       return ref;
/* 737:    */     }
/* 738:    */     
/* 739:    */     void put(String path, byte[] digest, Script script)
/* 740:    */     {
/* 741:752 */       put(path, new Main.ScriptReference(path, digest, script, this.queue));
/* 742:    */     }
/* 743:    */   }
/* 744:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.tools.shell.Main
 * JD-Core Version:    0.7.0.1
 */