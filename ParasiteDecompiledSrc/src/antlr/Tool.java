/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import antlr.collections.impl.Vector;
/*   5:    */ import java.io.BufferedReader;
/*   6:    */ import java.io.BufferedWriter;
/*   7:    */ import java.io.DataInputStream;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.FileReader;
/*  10:    */ import java.io.FileWriter;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.InputStreamReader;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.io.PrintWriter;
/*  15:    */ import java.io.Reader;
/*  16:    */ import java.io.Writer;
/*  17:    */ import java.util.StringTokenizer;
/*  18:    */ 
/*  19:    */ public class Tool
/*  20:    */ {
/*  21: 26 */   public static String version = "";
/*  22:    */   ToolErrorHandler errorHandler;
/*  23: 32 */   protected boolean hasError = false;
/*  24: 35 */   boolean genDiagnostics = false;
/*  25: 38 */   boolean genDocBook = false;
/*  26: 41 */   boolean genHTML = false;
/*  27: 44 */   protected String outputDir = ".";
/*  28:    */   protected String grammarFile;
/*  29: 48 */   transient Reader f = new InputStreamReader(System.in);
/*  30: 52 */   protected String literalsPrefix = "LITERAL_";
/*  31: 53 */   protected boolean upperCaseMangledLiterals = false;
/*  32: 56 */   protected NameSpace nameSpace = null;
/*  33: 57 */   protected String namespaceAntlr = null;
/*  34: 58 */   protected String namespaceStd = null;
/*  35: 59 */   protected boolean genHashLines = true;
/*  36: 60 */   protected boolean noConstructors = false;
/*  37: 62 */   private BitSet cmdLineArgValid = new BitSet();
/*  38:    */   
/*  39:    */   public Tool()
/*  40:    */   {
/*  41: 66 */     this.errorHandler = new DefaultToolErrorHandler(this);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String getGrammarFile()
/*  45:    */   {
/*  46: 70 */     return this.grammarFile;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean hasError()
/*  50:    */   {
/*  51: 74 */     return this.hasError;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public NameSpace getNameSpace()
/*  55:    */   {
/*  56: 78 */     return this.nameSpace;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String getNamespaceStd()
/*  60:    */   {
/*  61: 82 */     return this.namespaceStd;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getNamespaceAntlr()
/*  65:    */   {
/*  66: 86 */     return this.namespaceAntlr;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean getGenHashLines()
/*  70:    */   {
/*  71: 90 */     return this.genHashLines;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getLiteralsPrefix()
/*  75:    */   {
/*  76: 94 */     return this.literalsPrefix;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean getUpperCaseMangledLiterals()
/*  80:    */   {
/*  81: 98 */     return this.upperCaseMangledLiterals;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setFileLineFormatter(FileLineFormatter paramFileLineFormatter)
/*  85:    */   {
/*  86:102 */     FileLineFormatter.setFormatter(paramFileLineFormatter);
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void checkForInvalidArguments(String[] paramArrayOfString, BitSet paramBitSet)
/*  90:    */   {
/*  91:107 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  92:108 */       if (!paramBitSet.member(i)) {
/*  93:109 */         warning("invalid command-line argument: " + paramArrayOfString[i] + "; ignored");
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void copyFile(String paramString1, String paramString2)
/*  99:    */     throws IOException
/* 100:    */   {
/* 101:121 */     File localFile1 = new File(paramString1);
/* 102:122 */     File localFile2 = new File(paramString2);
/* 103:123 */     BufferedReader localBufferedReader = null;
/* 104:124 */     BufferedWriter localBufferedWriter = null;
/* 105:    */     try
/* 106:    */     {
/* 107:131 */       if ((!localFile1.exists()) || (!localFile1.isFile())) {
/* 108:132 */         throw new FileCopyException("FileCopy: no such source file: " + paramString1);
/* 109:    */       }
/* 110:134 */       if (!localFile1.canRead()) {
/* 111:135 */         throw new FileCopyException("FileCopy: source file is unreadable: " + paramString1);
/* 112:    */       }
/* 113:    */       Object localObject1;
/* 114:141 */       if (localFile2.exists())
/* 115:    */       {
/* 116:142 */         if (localFile2.isFile())
/* 117:    */         {
/* 118:143 */           localObject1 = new DataInputStream(System.in);
/* 119:146 */           if (!localFile2.canWrite()) {
/* 120:147 */             throw new FileCopyException("FileCopy: destination file is unwriteable: " + paramString2);
/* 121:    */           }
/* 122:    */         }
/* 123:    */         else
/* 124:    */         {
/* 125:159 */           throw new FileCopyException("FileCopy: destination is not a file: " + paramString2);
/* 126:    */         }
/* 127:    */       }
/* 128:    */       else
/* 129:    */       {
/* 130:164 */         localObject1 = parent(localFile2);
/* 131:165 */         if (!((File)localObject1).exists()) {
/* 132:166 */           throw new FileCopyException("FileCopy: destination directory doesn't exist: " + paramString2);
/* 133:    */         }
/* 134:168 */         if (!((File)localObject1).canWrite()) {
/* 135:169 */           throw new FileCopyException("FileCopy: destination directory is unwriteable: " + paramString2);
/* 136:    */         }
/* 137:    */       }
/* 138:175 */       localBufferedReader = new BufferedReader(new FileReader(localFile1));
/* 139:176 */       localBufferedWriter = new BufferedWriter(new FileWriter(localFile2));
/* 140:    */       
/* 141:178 */       char[] arrayOfChar = new char[1024];
/* 142:    */       for (;;)
/* 143:    */       {
/* 144:180 */         int i = localBufferedReader.read(arrayOfChar, 0, 1024);
/* 145:181 */         if (i == -1) {
/* 146:    */           break;
/* 147:    */         }
/* 148:182 */         localBufferedWriter.write(arrayOfChar, 0, i);
/* 149:    */       }
/* 150:    */     }
/* 151:    */     finally
/* 152:    */     {
/* 153:187 */       if (localBufferedReader != null) {
/* 154:    */         try
/* 155:    */         {
/* 156:189 */           localBufferedReader.close();
/* 157:    */         }
/* 158:    */         catch (IOException localIOException1) {}
/* 159:    */       }
/* 160:195 */       if (localBufferedWriter != null) {
/* 161:    */         try
/* 162:    */         {
/* 163:197 */           localBufferedWriter.close();
/* 164:    */         }
/* 165:    */         catch (IOException localIOException2) {}
/* 166:    */       }
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void doEverythingWrapper(String[] paramArrayOfString)
/* 171:    */   {
/* 172:211 */     int i = doEverything(paramArrayOfString);
/* 173:212 */     System.exit(i);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int doEverything(String[] paramArrayOfString)
/* 177:    */   {
/* 178:223 */     antlr.preprocessor.Tool localTool = new antlr.preprocessor.Tool(this, paramArrayOfString);
/* 179:    */     
/* 180:225 */     boolean bool = localTool.preprocess();
/* 181:226 */     String[] arrayOfString = localTool.preprocessedArgList();
/* 182:    */     
/* 183:    */ 
/* 184:229 */     processArguments(arrayOfString);
/* 185:230 */     if (!bool) {
/* 186:231 */       return 1;
/* 187:    */     }
/* 188:234 */     this.f = getGrammarReader();
/* 189:    */     
/* 190:236 */     ANTLRLexer localANTLRLexer = new ANTLRLexer(this.f);
/* 191:237 */     TokenBuffer localTokenBuffer = new TokenBuffer(localANTLRLexer);
/* 192:238 */     LLkAnalyzer localLLkAnalyzer = new LLkAnalyzer(this);
/* 193:239 */     MakeGrammar localMakeGrammar = new MakeGrammar(this, paramArrayOfString, localLLkAnalyzer);
/* 194:    */     try
/* 195:    */     {
/* 196:242 */       ANTLRParser localANTLRParser = new ANTLRParser(localTokenBuffer, localMakeGrammar, this);
/* 197:243 */       localANTLRParser.setFilename(this.grammarFile);
/* 198:244 */       localANTLRParser.grammar();
/* 199:245 */       if (hasError()) {
/* 200:246 */         fatalError("Exiting due to errors.");
/* 201:    */       }
/* 202:248 */       checkForInvalidArguments(arrayOfString, this.cmdLineArgValid);
/* 203:    */       
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:255 */       String str = "antlr." + getLanguage(localMakeGrammar) + "CodeGenerator";
/* 210:    */       try
/* 211:    */       {
/* 212:257 */         CodeGenerator localCodeGenerator = (CodeGenerator)Utils.createInstanceOf(str);
/* 213:258 */         localCodeGenerator.setBehavior(localMakeGrammar);
/* 214:259 */         localCodeGenerator.setAnalyzer(localLLkAnalyzer);
/* 215:260 */         localCodeGenerator.setTool(this);
/* 216:261 */         localCodeGenerator.gen();
/* 217:    */       }
/* 218:    */       catch (ClassNotFoundException localClassNotFoundException)
/* 219:    */       {
/* 220:264 */         panic("Cannot instantiate code-generator: " + str);
/* 221:    */       }
/* 222:    */       catch (InstantiationException localInstantiationException)
/* 223:    */       {
/* 224:267 */         panic("Cannot instantiate code-generator: " + str);
/* 225:    */       }
/* 226:    */       catch (IllegalArgumentException localIllegalArgumentException)
/* 227:    */       {
/* 228:270 */         panic("Cannot instantiate code-generator: " + str);
/* 229:    */       }
/* 230:    */       catch (IllegalAccessException localIllegalAccessException)
/* 231:    */       {
/* 232:273 */         panic("code-generator class '" + str + "' is not accessible");
/* 233:    */       }
/* 234:    */     }
/* 235:    */     catch (RecognitionException localRecognitionException)
/* 236:    */     {
/* 237:277 */       fatalError("Unhandled parser error: " + localRecognitionException.getMessage());
/* 238:    */     }
/* 239:    */     catch (TokenStreamException localTokenStreamException)
/* 240:    */     {
/* 241:280 */       fatalError("TokenStreamException: " + localTokenStreamException.getMessage());
/* 242:    */     }
/* 243:282 */     return 0;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void error(String paramString)
/* 247:    */   {
/* 248:289 */     this.hasError = true;
/* 249:290 */     System.err.println("error: " + paramString);
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void error(String paramString1, String paramString2, int paramInt1, int paramInt2)
/* 253:    */   {
/* 254:300 */     this.hasError = true;
/* 255:301 */     System.err.println(FileLineFormatter.getFormatter().getFormatString(paramString2, paramInt1, paramInt2) + paramString1);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String fileMinusPath(String paramString)
/* 259:    */   {
/* 260:306 */     String str = System.getProperty("file.separator");
/* 261:307 */     int i = paramString.lastIndexOf(str);
/* 262:308 */     if (i == -1) {
/* 263:309 */       return paramString;
/* 264:    */     }
/* 265:311 */     return paramString.substring(i + 1);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public String getLanguage(MakeGrammar paramMakeGrammar)
/* 269:    */   {
/* 270:318 */     if (this.genDiagnostics) {
/* 271:319 */       return "Diagnostic";
/* 272:    */     }
/* 273:321 */     if (this.genHTML) {
/* 274:322 */       return "HTML";
/* 275:    */     }
/* 276:324 */     if (this.genDocBook) {
/* 277:325 */       return "DocBook";
/* 278:    */     }
/* 279:327 */     return paramMakeGrammar.language;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public String getOutputDirectory()
/* 283:    */   {
/* 284:331 */     return this.outputDir;
/* 285:    */   }
/* 286:    */   
/* 287:    */   private static void help()
/* 288:    */   {
/* 289:335 */     System.err.println("usage: java antlr.Tool [args] file.g");
/* 290:336 */     System.err.println("  -o outputDir       specify output directory where all output generated.");
/* 291:337 */     System.err.println("  -glib superGrammar specify location of supergrammar file.");
/* 292:338 */     System.err.println("  -debug             launch the ParseView debugger upon parser invocation.");
/* 293:339 */     System.err.println("  -html              generate a html file from your grammar.");
/* 294:340 */     System.err.println("  -docbook           generate a docbook sgml file from your grammar.");
/* 295:341 */     System.err.println("  -diagnostic        generate a textfile with diagnostics.");
/* 296:342 */     System.err.println("  -trace             have all rules call traceIn/traceOut.");
/* 297:343 */     System.err.println("  -traceLexer        have lexer rules call traceIn/traceOut.");
/* 298:344 */     System.err.println("  -traceParser       have parser rules call traceIn/traceOut.");
/* 299:345 */     System.err.println("  -traceTreeParser   have tree parser rules call traceIn/traceOut.");
/* 300:346 */     System.err.println("  -h|-help|--help    this message");
/* 301:    */   }
/* 302:    */   
/* 303:    */   public static void main(String[] paramArrayOfString)
/* 304:    */   {
/* 305:350 */     System.err.println("ANTLR Parser Generator   Version 2.7.7 (20060906)   1989-2005");
/* 306:    */     
/* 307:352 */     version = "2.7.7 (20060906)";
/* 308:    */     try
/* 309:    */     {
/* 310:355 */       int i = 0;
/* 311:357 */       if (paramArrayOfString.length == 0) {
/* 312:358 */         i = 1;
/* 313:    */       } else {
/* 314:361 */         for (int j = 0; j < paramArrayOfString.length; j++) {
/* 315:362 */           if ((paramArrayOfString[j].equals("-h")) || (paramArrayOfString[j].equals("-help")) || (paramArrayOfString[j].equals("--help")))
/* 316:    */           {
/* 317:366 */             i = 1;
/* 318:367 */             break;
/* 319:    */           }
/* 320:    */         }
/* 321:    */       }
/* 322:372 */       if (i != 0)
/* 323:    */       {
/* 324:373 */         help();
/* 325:    */       }
/* 326:    */       else
/* 327:    */       {
/* 328:376 */         Tool localTool = new Tool();
/* 329:377 */         localTool.doEverything(paramArrayOfString);
/* 330:378 */         localTool = null;
/* 331:    */       }
/* 332:    */     }
/* 333:    */     catch (Exception localException)
/* 334:    */     {
/* 335:382 */       System.err.println(System.getProperty("line.separator") + System.getProperty("line.separator"));
/* 336:    */       
/* 337:384 */       System.err.println("#$%%*&@# internal error: " + localException.toString());
/* 338:385 */       System.err.println("[complain to nearest government official");
/* 339:386 */       System.err.println(" or send hate-mail to parrt@antlr.org;");
/* 340:387 */       System.err.println(" please send stack trace with report.]" + System.getProperty("line.separator"));
/* 341:    */       
/* 342:389 */       localException.printStackTrace();
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public PrintWriter openOutputFile(String paramString)
/* 347:    */     throws IOException
/* 348:    */   {
/* 349:397 */     if (this.outputDir != ".")
/* 350:    */     {
/* 351:398 */       File localFile = new File(this.outputDir);
/* 352:399 */       if (!localFile.exists()) {
/* 353:400 */         localFile.mkdirs();
/* 354:    */       }
/* 355:    */     }
/* 356:402 */     return new PrintWriter(new PreservingFileWriter(this.outputDir + System.getProperty("file.separator") + paramString));
/* 357:    */   }
/* 358:    */   
/* 359:    */   public Reader getGrammarReader()
/* 360:    */   {
/* 361:406 */     BufferedReader localBufferedReader = null;
/* 362:    */     try
/* 363:    */     {
/* 364:408 */       if (this.grammarFile != null) {
/* 365:409 */         localBufferedReader = new BufferedReader(new FileReader(this.grammarFile));
/* 366:    */       }
/* 367:    */     }
/* 368:    */     catch (IOException localIOException)
/* 369:    */     {
/* 370:413 */       fatalError("cannot open grammar file " + this.grammarFile);
/* 371:    */     }
/* 372:415 */     return localBufferedReader;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void reportException(Exception paramException, String paramString)
/* 376:    */   {
/* 377:421 */     System.err.println(paramString + ": " + paramException.getMessage());
/* 378:    */   }
/* 379:    */   
/* 380:    */   public void reportProgress(String paramString)
/* 381:    */   {
/* 382:428 */     System.out.println(paramString);
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void fatalError(String paramString)
/* 386:    */   {
/* 387:444 */     System.err.println(paramString);
/* 388:445 */     Utils.error(paramString);
/* 389:    */   }
/* 390:    */   
/* 391:    */   /**
/* 392:    */    * @deprecated
/* 393:    */    */
/* 394:    */   public void panic()
/* 395:    */   {
/* 396:455 */     fatalError("panic");
/* 397:    */   }
/* 398:    */   
/* 399:    */   /**
/* 400:    */    * @deprecated
/* 401:    */    */
/* 402:    */   public void panic(String paramString)
/* 403:    */   {
/* 404:466 */     fatalError("panic: " + paramString);
/* 405:    */   }
/* 406:    */   
/* 407:    */   public File parent(File paramFile)
/* 408:    */   {
/* 409:473 */     String str = paramFile.getParent();
/* 410:474 */     if (str == null)
/* 411:    */     {
/* 412:475 */       if (paramFile.isAbsolute()) {
/* 413:476 */         return new File(File.separator);
/* 414:    */       }
/* 415:478 */       return new File(System.getProperty("user.dir"));
/* 416:    */     }
/* 417:480 */     return new File(str);
/* 418:    */   }
/* 419:    */   
/* 420:    */   public static Vector parseSeparatedList(String paramString, char paramChar)
/* 421:    */   {
/* 422:487 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, String.valueOf(paramChar));
/* 423:    */     
/* 424:489 */     Vector localVector = new Vector(10);
/* 425:490 */     while (localStringTokenizer.hasMoreTokens()) {
/* 426:491 */       localVector.appendElement(localStringTokenizer.nextToken());
/* 427:    */     }
/* 428:493 */     if (localVector.size() == 0) {
/* 429:493 */       return null;
/* 430:    */     }
/* 431:494 */     return localVector;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public String pathToFile(String paramString)
/* 435:    */   {
/* 436:501 */     String str = System.getProperty("file.separator");
/* 437:502 */     int i = paramString.lastIndexOf(str);
/* 438:503 */     if (i == -1) {
/* 439:505 */       return "." + System.getProperty("file.separator");
/* 440:    */     }
/* 441:507 */     return paramString.substring(0, i + 1);
/* 442:    */   }
/* 443:    */   
/* 444:    */   protected void processArguments(String[] paramArrayOfString)
/* 445:    */   {
/* 446:516 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 447:517 */       if (paramArrayOfString[i].equals("-diagnostic"))
/* 448:    */       {
/* 449:518 */         this.genDiagnostics = true;
/* 450:519 */         this.genHTML = false;
/* 451:520 */         setArgOK(i);
/* 452:    */       }
/* 453:522 */       else if (paramArrayOfString[i].equals("-o"))
/* 454:    */       {
/* 455:523 */         setArgOK(i);
/* 456:524 */         if (i + 1 >= paramArrayOfString.length)
/* 457:    */         {
/* 458:525 */           error("missing output directory with -o option; ignoring");
/* 459:    */         }
/* 460:    */         else
/* 461:    */         {
/* 462:528 */           i++;
/* 463:529 */           setOutputDirectory(paramArrayOfString[i]);
/* 464:530 */           setArgOK(i);
/* 465:    */         }
/* 466:    */       }
/* 467:533 */       else if (paramArrayOfString[i].equals("-html"))
/* 468:    */       {
/* 469:534 */         this.genHTML = true;
/* 470:535 */         this.genDiagnostics = false;
/* 471:536 */         setArgOK(i);
/* 472:    */       }
/* 473:538 */       else if (paramArrayOfString[i].equals("-docbook"))
/* 474:    */       {
/* 475:539 */         this.genDocBook = true;
/* 476:540 */         this.genDiagnostics = false;
/* 477:541 */         setArgOK(i);
/* 478:    */       }
/* 479:544 */       else if (paramArrayOfString[i].charAt(0) != '-')
/* 480:    */       {
/* 481:546 */         this.grammarFile = paramArrayOfString[i];
/* 482:547 */         setArgOK(i);
/* 483:    */       }
/* 484:    */     }
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void setArgOK(int paramInt)
/* 488:    */   {
/* 489:554 */     this.cmdLineArgValid.add(paramInt);
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setOutputDirectory(String paramString)
/* 493:    */   {
/* 494:558 */     this.outputDir = paramString;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void toolError(String paramString)
/* 498:    */   {
/* 499:565 */     System.err.println("error: " + paramString);
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void warning(String paramString)
/* 503:    */   {
/* 504:572 */     System.err.println("warning: " + paramString);
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void warning(String paramString1, String paramString2, int paramInt1, int paramInt2)
/* 508:    */   {
/* 509:582 */     System.err.println(FileLineFormatter.getFormatter().getFormatString(paramString2, paramInt1, paramInt2) + "warning:" + paramString1);
/* 510:    */   }
/* 511:    */   
/* 512:    */   public void warning(String[] paramArrayOfString, String paramString, int paramInt1, int paramInt2)
/* 513:    */   {
/* 514:592 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/* 515:593 */       panic("bad multi-line message to Tool.warning");
/* 516:    */     }
/* 517:595 */     System.err.println(FileLineFormatter.getFormatter().getFormatString(paramString, paramInt1, paramInt2) + "warning:" + paramArrayOfString[0]);
/* 518:597 */     for (int i = 1; i < paramArrayOfString.length; i++) {
/* 519:598 */       System.err.println(FileLineFormatter.getFormatter().getFormatString(paramString, paramInt1, paramInt2) + "    " + paramArrayOfString[i]);
/* 520:    */     }
/* 521:    */   }
/* 522:    */   
/* 523:    */   public void setNameSpace(String paramString)
/* 524:    */   {
/* 525:610 */     if (null == this.nameSpace) {
/* 526:611 */       this.nameSpace = new NameSpace(StringUtils.stripFrontBack(paramString, "\"", "\""));
/* 527:    */     }
/* 528:    */   }
/* 529:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.Tool
 * JD-Core Version:    0.7.0.1
 */