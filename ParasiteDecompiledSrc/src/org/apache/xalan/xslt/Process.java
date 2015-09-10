/*    1:     */ package org.apache.xalan.xslt;
/*    2:     */ 
/*    3:     */ import java.io.FileOutputStream;
/*    4:     */ import java.io.FileWriter;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.io.OutputStream;
/*    8:     */ import java.io.PrintStream;
/*    9:     */ import java.io.PrintWriter;
/*   10:     */ import java.io.StringReader;
/*   11:     */ import java.io.Writer;
/*   12:     */ import java.util.Hashtable;
/*   13:     */ import java.util.Properties;
/*   14:     */ import java.util.ResourceBundle;
/*   15:     */ import java.util.Vector;
/*   16:     */ import javax.xml.parsers.DocumentBuilder;
/*   17:     */ import javax.xml.parsers.DocumentBuilderFactory;
/*   18:     */ import javax.xml.parsers.FactoryConfigurationError;
/*   19:     */ import javax.xml.parsers.ParserConfigurationException;
/*   20:     */ import javax.xml.parsers.SAXParser;
/*   21:     */ import javax.xml.parsers.SAXParserFactory;
/*   22:     */ import javax.xml.transform.Source;
/*   23:     */ import javax.xml.transform.Templates;
/*   24:     */ import javax.xml.transform.Transformer;
/*   25:     */ import javax.xml.transform.TransformerConfigurationException;
/*   26:     */ import javax.xml.transform.TransformerException;
/*   27:     */ import javax.xml.transform.TransformerFactory;
/*   28:     */ import javax.xml.transform.TransformerFactoryConfigurationError;
/*   29:     */ import javax.xml.transform.URIResolver;
/*   30:     */ import javax.xml.transform.dom.DOMResult;
/*   31:     */ import javax.xml.transform.dom.DOMSource;
/*   32:     */ import javax.xml.transform.sax.SAXResult;
/*   33:     */ import javax.xml.transform.sax.SAXSource;
/*   34:     */ import javax.xml.transform.sax.SAXTransformerFactory;
/*   35:     */ import javax.xml.transform.sax.TransformerHandler;
/*   36:     */ import javax.xml.transform.stream.StreamResult;
/*   37:     */ import javax.xml.transform.stream.StreamSource;
/*   38:     */ import org.apache.xalan.Version;
/*   39:     */ import org.apache.xalan.res.XSLMessages;
/*   40:     */ import org.apache.xalan.trace.PrintTraceListener;
/*   41:     */ import org.apache.xalan.trace.TraceManager;
/*   42:     */ import org.apache.xalan.transformer.TransformerImpl;
/*   43:     */ import org.apache.xml.res.XMLMessages;
/*   44:     */ import org.apache.xml.utils.DefaultErrorHandler;
/*   45:     */ import org.apache.xml.utils.WrappedRuntimeException;
/*   46:     */ import org.w3c.dom.Document;
/*   47:     */ import org.w3c.dom.DocumentFragment;
/*   48:     */ import org.w3c.dom.Node;
/*   49:     */ import org.xml.sax.ContentHandler;
/*   50:     */ import org.xml.sax.EntityResolver;
/*   51:     */ import org.xml.sax.ErrorHandler;
/*   52:     */ import org.xml.sax.InputSource;
/*   53:     */ import org.xml.sax.SAXException;
/*   54:     */ import org.xml.sax.SAXNotRecognizedException;
/*   55:     */ import org.xml.sax.SAXNotSupportedException;
/*   56:     */ import org.xml.sax.XMLReader;
/*   57:     */ import org.xml.sax.helpers.XMLReaderFactory;
/*   58:     */ 
/*   59:     */ public class Process
/*   60:     */ {
/*   61:     */   protected static void printArgOptions(ResourceBundle resbundle)
/*   62:     */   {
/*   63:  83 */     System.out.println(resbundle.getString("xslProc_option"));
/*   64:  84 */     System.out.println("\n\t\t\t" + resbundle.getString("xslProc_common_options") + "\n");
/*   65:  85 */     System.out.println(resbundle.getString("optionXSLTC"));
/*   66:  86 */     System.out.println(resbundle.getString("optionIN"));
/*   67:  87 */     System.out.println(resbundle.getString("optionXSL"));
/*   68:  88 */     System.out.println(resbundle.getString("optionOUT"));
/*   69:     */     
/*   70:     */ 
/*   71:  91 */     System.out.println(resbundle.getString("optionV"));
/*   72:     */     
/*   73:     */ 
/*   74:  94 */     System.out.println(resbundle.getString("optionEDUMP"));
/*   75:  95 */     System.out.println(resbundle.getString("optionXML"));
/*   76:  96 */     System.out.println(resbundle.getString("optionTEXT"));
/*   77:  97 */     System.out.println(resbundle.getString("optionHTML"));
/*   78:  98 */     System.out.println(resbundle.getString("optionPARAM"));
/*   79:     */     
/*   80: 100 */     System.out.println(resbundle.getString("optionMEDIA"));
/*   81: 101 */     System.out.println(resbundle.getString("optionFLAVOR"));
/*   82: 102 */     System.out.println(resbundle.getString("optionDIAG"));
/*   83: 103 */     System.out.println(resbundle.getString("optionURIRESOLVER"));
/*   84: 104 */     System.out.println(resbundle.getString("optionENTITYRESOLVER"));
/*   85: 105 */     waitForReturnKey(resbundle);
/*   86: 106 */     System.out.println(resbundle.getString("optionCONTENTHANDLER"));
/*   87: 107 */     System.out.println(resbundle.getString("optionSECUREPROCESSING"));
/*   88:     */     
/*   89: 109 */     System.out.println("\n\t\t\t" + resbundle.getString("xslProc_xalan_options") + "\n");
/*   90:     */     
/*   91: 111 */     System.out.println(resbundle.getString("optionQC"));
/*   92:     */     
/*   93:     */ 
/*   94: 114 */     System.out.println(resbundle.getString("optionTT"));
/*   95: 115 */     System.out.println(resbundle.getString("optionTG"));
/*   96: 116 */     System.out.println(resbundle.getString("optionTS"));
/*   97: 117 */     System.out.println(resbundle.getString("optionTTC"));
/*   98: 118 */     System.out.println(resbundle.getString("optionTCLASS"));
/*   99: 119 */     System.out.println(resbundle.getString("optionLINENUMBERS"));
/*  100: 120 */     System.out.println(resbundle.getString("optionINCREMENTAL"));
/*  101: 121 */     System.out.println(resbundle.getString("optionNOOPTIMIMIZE"));
/*  102: 122 */     System.out.println(resbundle.getString("optionRL"));
/*  103:     */     
/*  104: 124 */     System.out.println("\n\t\t\t" + resbundle.getString("xslProc_xsltc_options") + "\n");
/*  105: 125 */     System.out.println(resbundle.getString("optionXO"));
/*  106: 126 */     waitForReturnKey(resbundle);
/*  107: 127 */     System.out.println(resbundle.getString("optionXD"));
/*  108: 128 */     System.out.println(resbundle.getString("optionXJ"));
/*  109: 129 */     System.out.println(resbundle.getString("optionXP"));
/*  110: 130 */     System.out.println(resbundle.getString("optionXN"));
/*  111: 131 */     System.out.println(resbundle.getString("optionXX"));
/*  112: 132 */     System.out.println(resbundle.getString("optionXT"));
/*  113:     */   }
/*  114:     */   
/*  115:     */   public static void main(String[] argv)
/*  116:     */   {
/*  117: 154 */     boolean doStackDumpOnError = false;
/*  118: 155 */     boolean setQuietMode = false;
/*  119: 156 */     boolean doDiag = false;
/*  120: 157 */     String msg = null;
/*  121: 158 */     boolean isSecureProcessing = false;
/*  122:     */     
/*  123:     */ 
/*  124:     */ 
/*  125:     */ 
/*  126:     */ 
/*  127:     */ 
/*  128:     */ 
/*  129: 166 */     PrintWriter diagnosticsWriter = new PrintWriter(System.err, true);
/*  130: 167 */     PrintWriter dumpWriter = diagnosticsWriter;
/*  131: 168 */     ResourceBundle resbundle = XMLMessages.loadResourceBundle("org.apache.xalan.res.XSLTErrorResources");
/*  132:     */     
/*  133:     */ 
/*  134: 171 */     String flavor = "s2s";
/*  135: 173 */     if (argv.length < 1)
/*  136:     */     {
/*  137: 175 */       printArgOptions(resbundle);
/*  138:     */     }
/*  139:     */     else
/*  140:     */     {
/*  141: 179 */       boolean useXSLTC = false;
/*  142: 180 */       for (int i = 0; i < argv.length; i++) {
/*  143: 182 */         if ("-XSLTC".equalsIgnoreCase(argv[i])) {
/*  144: 184 */           useXSLTC = true;
/*  145:     */         }
/*  146:     */       }
/*  147: 189 */       if (useXSLTC)
/*  148:     */       {
/*  149: 191 */         String key = "javax.xml.transform.TransformerFactory";
/*  150: 192 */         String value = "org.apache.xalan.xsltc.trax.TransformerFactoryImpl";
/*  151: 193 */         Properties props = System.getProperties();
/*  152: 194 */         props.put(key, value);
/*  153: 195 */         System.setProperties(props);
/*  154:     */       }
/*  155:     */       TransformerFactory tfactory;
/*  156:     */       try
/*  157:     */       {
/*  158: 200 */         tfactory = TransformerFactory.newInstance();
/*  159: 201 */         tfactory.setErrorListener(new DefaultErrorHandler(false));
/*  160:     */       }
/*  161:     */       catch (TransformerFactoryConfigurationError pfe)
/*  162:     */       {
/*  163: 205 */         pfe.printStackTrace(dumpWriter);
/*  164:     */         
/*  165: 207 */         msg = XSLMessages.createMessage("ER_NOT_SUCCESSFUL", null);
/*  166:     */         
/*  167: 209 */         diagnosticsWriter.println(msg);
/*  168:     */         
/*  169: 211 */         tfactory = null;
/*  170:     */         
/*  171: 213 */         doExit(msg);
/*  172:     */       }
/*  173: 216 */       boolean formatOutput = false;
/*  174: 217 */       boolean useSourceLocation = false;
/*  175: 218 */       String inFileName = null;
/*  176: 219 */       String outFileName = null;
/*  177: 220 */       String dumpFileName = null;
/*  178: 221 */       String xslFileName = null;
/*  179: 222 */       String treedumpFileName = null;
/*  180: 223 */       PrintTraceListener tracer = null;
/*  181: 224 */       String outputType = null;
/*  182: 225 */       String media = null;
/*  183: 226 */       Vector params = new Vector();
/*  184: 227 */       boolean quietConflictWarnings = false;
/*  185: 228 */       URIResolver uriResolver = null;
/*  186: 229 */       EntityResolver entityResolver = null;
/*  187: 230 */       ContentHandler contentHandler = null;
/*  188: 231 */       int recursionLimit = -1;
/*  189: 233 */       for (int i = 0; i < argv.length; i++) {
/*  190: 235 */         if (!"-XSLTC".equalsIgnoreCase(argv[i])) {
/*  191: 239 */           if ("-TT".equalsIgnoreCase(argv[i]))
/*  192:     */           {
/*  193: 241 */             if (!useXSLTC)
/*  194:     */             {
/*  195: 243 */               if (null == tracer) {
/*  196: 244 */                 tracer = new PrintTraceListener(diagnosticsWriter);
/*  197:     */               }
/*  198: 246 */               tracer.m_traceTemplates = true;
/*  199:     */             }
/*  200:     */             else
/*  201:     */             {
/*  202: 249 */               printInvalidXSLTCOption("-TT");
/*  203:     */             }
/*  204:     */           }
/*  205: 253 */           else if ("-TG".equalsIgnoreCase(argv[i]))
/*  206:     */           {
/*  207: 255 */             if (!useXSLTC)
/*  208:     */             {
/*  209: 257 */               if (null == tracer) {
/*  210: 258 */                 tracer = new PrintTraceListener(diagnosticsWriter);
/*  211:     */               }
/*  212: 260 */               tracer.m_traceGeneration = true;
/*  213:     */             }
/*  214:     */             else
/*  215:     */             {
/*  216: 263 */               printInvalidXSLTCOption("-TG");
/*  217:     */             }
/*  218:     */           }
/*  219: 267 */           else if ("-TS".equalsIgnoreCase(argv[i]))
/*  220:     */           {
/*  221: 269 */             if (!useXSLTC)
/*  222:     */             {
/*  223: 271 */               if (null == tracer) {
/*  224: 272 */                 tracer = new PrintTraceListener(diagnosticsWriter);
/*  225:     */               }
/*  226: 274 */               tracer.m_traceSelection = true;
/*  227:     */             }
/*  228:     */             else
/*  229:     */             {
/*  230: 277 */               printInvalidXSLTCOption("-TS");
/*  231:     */             }
/*  232:     */           }
/*  233: 281 */           else if ("-TTC".equalsIgnoreCase(argv[i]))
/*  234:     */           {
/*  235: 283 */             if (!useXSLTC)
/*  236:     */             {
/*  237: 285 */               if (null == tracer) {
/*  238: 286 */                 tracer = new PrintTraceListener(diagnosticsWriter);
/*  239:     */               }
/*  240: 288 */               tracer.m_traceElements = true;
/*  241:     */             }
/*  242:     */             else
/*  243:     */             {
/*  244: 291 */               printInvalidXSLTCOption("-TTC");
/*  245:     */             }
/*  246:     */           }
/*  247: 295 */           else if ("-INDENT".equalsIgnoreCase(argv[i]))
/*  248:     */           {
/*  249:     */             int indentAmount;
/*  250: 299 */             if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  251: 301 */               indentAmount = Integer.parseInt(argv[(++i)]);
/*  252:     */             } else {
/*  253: 305 */               indentAmount = 0;
/*  254:     */             }
/*  255:     */           }
/*  256: 311 */           else if ("-IN".equalsIgnoreCase(argv[i]))
/*  257:     */           {
/*  258: 313 */             if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  259: 314 */               inFileName = argv[(++i)];
/*  260:     */             } else {
/*  261: 316 */               System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-IN" }));
/*  262:     */             }
/*  263:     */           }
/*  264: 321 */           else if ("-MEDIA".equalsIgnoreCase(argv[i]))
/*  265:     */           {
/*  266: 323 */             if (i + 1 < argv.length) {
/*  267: 324 */               media = argv[(++i)];
/*  268:     */             } else {
/*  269: 326 */               System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-MEDIA" }));
/*  270:     */             }
/*  271:     */           }
/*  272: 331 */           else if ("-OUT".equalsIgnoreCase(argv[i]))
/*  273:     */           {
/*  274: 333 */             if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  275: 334 */               outFileName = argv[(++i)];
/*  276:     */             } else {
/*  277: 336 */               System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-OUT" }));
/*  278:     */             }
/*  279:     */           }
/*  280: 341 */           else if ("-XSL".equalsIgnoreCase(argv[i]))
/*  281:     */           {
/*  282: 343 */             if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  283: 344 */               xslFileName = argv[(++i)];
/*  284:     */             } else {
/*  285: 346 */               System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-XSL" }));
/*  286:     */             }
/*  287:     */           }
/*  288: 351 */           else if ("-FLAVOR".equalsIgnoreCase(argv[i]))
/*  289:     */           {
/*  290: 353 */             if (i + 1 < argv.length) {
/*  291: 355 */               flavor = argv[(++i)];
/*  292:     */             } else {
/*  293: 358 */               System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-FLAVOR" }));
/*  294:     */             }
/*  295:     */           }
/*  296: 363 */           else if ("-PARAM".equalsIgnoreCase(argv[i]))
/*  297:     */           {
/*  298: 365 */             if (i + 2 < argv.length)
/*  299:     */             {
/*  300: 367 */               String name = argv[(++i)];
/*  301:     */               
/*  302: 369 */               params.addElement(name);
/*  303:     */               
/*  304: 371 */               String expression = argv[(++i)];
/*  305:     */               
/*  306: 373 */               params.addElement(expression);
/*  307:     */             }
/*  308:     */             else
/*  309:     */             {
/*  310: 376 */               System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-PARAM" }));
/*  311:     */             }
/*  312:     */           }
/*  313: 381 */           else if (!"-E".equalsIgnoreCase(argv[i]))
/*  314:     */           {
/*  315: 387 */             if ("-V".equalsIgnoreCase(argv[i]))
/*  316:     */             {
/*  317: 389 */               diagnosticsWriter.println(resbundle.getString("version") + Version.getVersion() + ", " + resbundle.getString("version2"));
/*  318:     */             }
/*  319: 395 */             else if ("-QC".equalsIgnoreCase(argv[i]))
/*  320:     */             {
/*  321: 397 */               if (!useXSLTC) {
/*  322: 398 */                 quietConflictWarnings = true;
/*  323:     */               } else {
/*  324: 400 */                 printInvalidXSLTCOption("-QC");
/*  325:     */               }
/*  326:     */             }
/*  327: 402 */             else if ("-Q".equalsIgnoreCase(argv[i]))
/*  328:     */             {
/*  329: 404 */               setQuietMode = true;
/*  330:     */             }
/*  331: 406 */             else if ("-DIAG".equalsIgnoreCase(argv[i]))
/*  332:     */             {
/*  333: 408 */               doDiag = true;
/*  334:     */             }
/*  335: 410 */             else if ("-XML".equalsIgnoreCase(argv[i]))
/*  336:     */             {
/*  337: 412 */               outputType = "xml";
/*  338:     */             }
/*  339: 414 */             else if ("-TEXT".equalsIgnoreCase(argv[i]))
/*  340:     */             {
/*  341: 416 */               outputType = "text";
/*  342:     */             }
/*  343: 418 */             else if ("-HTML".equalsIgnoreCase(argv[i]))
/*  344:     */             {
/*  345: 420 */               outputType = "html";
/*  346:     */             }
/*  347: 422 */             else if ("-EDUMP".equalsIgnoreCase(argv[i]))
/*  348:     */             {
/*  349: 424 */               doStackDumpOnError = true;
/*  350: 426 */               if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  351: 428 */                 dumpFileName = argv[(++i)];
/*  352:     */               }
/*  353:     */             }
/*  354: 431 */             else if ("-URIRESOLVER".equalsIgnoreCase(argv[i]))
/*  355:     */             {
/*  356: 433 */               if (i + 1 < argv.length)
/*  357:     */               {
/*  358:     */                 try
/*  359:     */                 {
/*  360: 437 */                   uriResolver = (URIResolver)ObjectFactory.newInstance(argv[(++i)], ObjectFactory.findClassLoader(), true);
/*  361:     */                   
/*  362:     */ 
/*  363: 440 */                   tfactory.setURIResolver(uriResolver);
/*  364:     */                 }
/*  365:     */                 catch (ObjectFactory.ConfigurationError cnfe)
/*  366:     */                 {
/*  367: 444 */                   msg = XSLMessages.createMessage("ER_CLASS_NOT_FOUND_FOR_OPTION", new Object[] { "-URIResolver" });
/*  368:     */                   
/*  369:     */ 
/*  370: 447 */                   System.err.println(msg);
/*  371: 448 */                   doExit(msg);
/*  372:     */                 }
/*  373:     */               }
/*  374:     */               else
/*  375:     */               {
/*  376: 453 */                 msg = XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-URIResolver" });
/*  377:     */                 
/*  378:     */ 
/*  379: 456 */                 System.err.println(msg);
/*  380: 457 */                 doExit(msg);
/*  381:     */               }
/*  382:     */             }
/*  383: 460 */             else if ("-ENTITYRESOLVER".equalsIgnoreCase(argv[i]))
/*  384:     */             {
/*  385: 462 */               if (i + 1 < argv.length)
/*  386:     */               {
/*  387:     */                 try
/*  388:     */                 {
/*  389: 466 */                   entityResolver = (EntityResolver)ObjectFactory.newInstance(argv[(++i)], ObjectFactory.findClassLoader(), true);
/*  390:     */                 }
/*  391:     */                 catch (ObjectFactory.ConfigurationError cnfe)
/*  392:     */                 {
/*  393: 471 */                   msg = XSLMessages.createMessage("ER_CLASS_NOT_FOUND_FOR_OPTION", new Object[] { "-EntityResolver" });
/*  394:     */                   
/*  395:     */ 
/*  396: 474 */                   System.err.println(msg);
/*  397: 475 */                   doExit(msg);
/*  398:     */                 }
/*  399:     */               }
/*  400:     */               else
/*  401:     */               {
/*  402: 481 */                 msg = XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-EntityResolver" });
/*  403:     */                 
/*  404:     */ 
/*  405: 484 */                 System.err.println(msg);
/*  406: 485 */                 doExit(msg);
/*  407:     */               }
/*  408:     */             }
/*  409: 488 */             else if ("-CONTENTHANDLER".equalsIgnoreCase(argv[i]))
/*  410:     */             {
/*  411: 490 */               if (i + 1 < argv.length)
/*  412:     */               {
/*  413:     */                 try
/*  414:     */                 {
/*  415: 494 */                   contentHandler = (ContentHandler)ObjectFactory.newInstance(argv[(++i)], ObjectFactory.findClassLoader(), true);
/*  416:     */                 }
/*  417:     */                 catch (ObjectFactory.ConfigurationError cnfe)
/*  418:     */                 {
/*  419: 499 */                   msg = XSLMessages.createMessage("ER_CLASS_NOT_FOUND_FOR_OPTION", new Object[] { "-ContentHandler" });
/*  420:     */                   
/*  421:     */ 
/*  422: 502 */                   System.err.println(msg);
/*  423: 503 */                   doExit(msg);
/*  424:     */                 }
/*  425:     */               }
/*  426:     */               else
/*  427:     */               {
/*  428: 509 */                 msg = XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-ContentHandler" });
/*  429:     */                 
/*  430:     */ 
/*  431: 512 */                 System.err.println(msg);
/*  432: 513 */                 doExit(msg);
/*  433:     */               }
/*  434:     */             }
/*  435: 516 */             else if ("-L".equalsIgnoreCase(argv[i]))
/*  436:     */             {
/*  437: 518 */               if (!useXSLTC) {
/*  438: 519 */                 tfactory.setAttribute("http://xml.apache.org/xalan/properties/source-location", Boolean.TRUE);
/*  439:     */               } else {
/*  440: 521 */                 printInvalidXSLTCOption("-L");
/*  441:     */               }
/*  442:     */             }
/*  443: 523 */             else if ("-INCREMENTAL".equalsIgnoreCase(argv[i]))
/*  444:     */             {
/*  445: 525 */               if (!useXSLTC) {
/*  446: 526 */                 tfactory.setAttribute("http://xml.apache.org/xalan/features/incremental", Boolean.TRUE);
/*  447:     */               } else {
/*  448: 530 */                 printInvalidXSLTCOption("-INCREMENTAL");
/*  449:     */               }
/*  450:     */             }
/*  451: 532 */             else if ("-NOOPTIMIZE".equalsIgnoreCase(argv[i]))
/*  452:     */             {
/*  453: 539 */               if (!useXSLTC) {
/*  454: 540 */                 tfactory.setAttribute("http://xml.apache.org/xalan/features/optimize", Boolean.FALSE);
/*  455:     */               } else {
/*  456: 544 */                 printInvalidXSLTCOption("-NOOPTIMIZE");
/*  457:     */               }
/*  458:     */             }
/*  459: 546 */             else if ("-RL".equalsIgnoreCase(argv[i]))
/*  460:     */             {
/*  461: 548 */               if (!useXSLTC)
/*  462:     */               {
/*  463: 550 */                 if (i + 1 < argv.length) {
/*  464: 551 */                   recursionLimit = Integer.parseInt(argv[(++i)]);
/*  465:     */                 } else {
/*  466: 553 */                   System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-rl" }));
/*  467:     */                 }
/*  468:     */               }
/*  469:     */               else
/*  470:     */               {
/*  471: 560 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  472: 561 */                   i++;
/*  473:     */                 }
/*  474: 563 */                 printInvalidXSLTCOption("-RL");
/*  475:     */               }
/*  476:     */             }
/*  477: 568 */             else if ("-XO".equalsIgnoreCase(argv[i]))
/*  478:     */             {
/*  479: 570 */               if (useXSLTC)
/*  480:     */               {
/*  481: 572 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-'))
/*  482:     */                 {
/*  483: 574 */                   tfactory.setAttribute("generate-translet", "true");
/*  484: 575 */                   tfactory.setAttribute("translet-name", argv[(++i)]);
/*  485:     */                 }
/*  486:     */                 else
/*  487:     */                 {
/*  488: 578 */                   tfactory.setAttribute("generate-translet", "true");
/*  489:     */                 }
/*  490:     */               }
/*  491:     */               else
/*  492:     */               {
/*  493: 582 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  494: 583 */                   i++;
/*  495:     */                 }
/*  496: 584 */                 printInvalidXalanOption("-XO");
/*  497:     */               }
/*  498:     */             }
/*  499: 588 */             else if ("-XD".equalsIgnoreCase(argv[i]))
/*  500:     */             {
/*  501: 590 */               if (useXSLTC)
/*  502:     */               {
/*  503: 592 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  504: 593 */                   tfactory.setAttribute("destination-directory", argv[(++i)]);
/*  505:     */                 } else {
/*  506: 595 */                   System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-XD" }));
/*  507:     */                 }
/*  508:     */               }
/*  509:     */               else
/*  510:     */               {
/*  511: 603 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  512: 604 */                   i++;
/*  513:     */                 }
/*  514: 606 */                 printInvalidXalanOption("-XD");
/*  515:     */               }
/*  516:     */             }
/*  517: 610 */             else if ("-XJ".equalsIgnoreCase(argv[i]))
/*  518:     */             {
/*  519: 612 */               if (useXSLTC)
/*  520:     */               {
/*  521: 614 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-'))
/*  522:     */                 {
/*  523: 616 */                   tfactory.setAttribute("generate-translet", "true");
/*  524: 617 */                   tfactory.setAttribute("jar-name", argv[(++i)]);
/*  525:     */                 }
/*  526:     */                 else
/*  527:     */                 {
/*  528: 620 */                   System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-XJ" }));
/*  529:     */                 }
/*  530:     */               }
/*  531:     */               else
/*  532:     */               {
/*  533: 627 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  534: 628 */                   i++;
/*  535:     */                 }
/*  536: 630 */                 printInvalidXalanOption("-XJ");
/*  537:     */               }
/*  538:     */             }
/*  539: 635 */             else if ("-XP".equalsIgnoreCase(argv[i]))
/*  540:     */             {
/*  541: 637 */               if (useXSLTC)
/*  542:     */               {
/*  543: 639 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  544: 640 */                   tfactory.setAttribute("package-name", argv[(++i)]);
/*  545:     */                 } else {
/*  546: 642 */                   System.err.println(XSLMessages.createMessage("ER_MISSING_ARG_FOR_OPTION", new Object[] { "-XP" }));
/*  547:     */                 }
/*  548:     */               }
/*  549:     */               else
/*  550:     */               {
/*  551: 649 */                 if ((i + 1 < argv.length) && (argv[(i + 1)].charAt(0) != '-')) {
/*  552: 650 */                   i++;
/*  553:     */                 }
/*  554: 652 */                 printInvalidXalanOption("-XP");
/*  555:     */               }
/*  556:     */             }
/*  557: 657 */             else if ("-XN".equalsIgnoreCase(argv[i]))
/*  558:     */             {
/*  559: 659 */               if (useXSLTC) {
/*  560: 661 */                 tfactory.setAttribute("enable-inlining", "true");
/*  561:     */               } else {
/*  562: 664 */                 printInvalidXalanOption("-XN");
/*  563:     */               }
/*  564:     */             }
/*  565: 667 */             else if ("-XX".equalsIgnoreCase(argv[i]))
/*  566:     */             {
/*  567: 669 */               if (useXSLTC) {
/*  568: 671 */                 tfactory.setAttribute("debug", "true");
/*  569:     */               } else {
/*  570: 674 */                 printInvalidXalanOption("-XX");
/*  571:     */               }
/*  572:     */             }
/*  573: 678 */             else if ("-XT".equalsIgnoreCase(argv[i]))
/*  574:     */             {
/*  575: 680 */               if (useXSLTC) {
/*  576: 682 */                 tfactory.setAttribute("auto-translet", "true");
/*  577:     */               } else {
/*  578: 685 */                 printInvalidXalanOption("-XT");
/*  579:     */               }
/*  580:     */             }
/*  581: 687 */             else if ("-SECURE".equalsIgnoreCase(argv[i]))
/*  582:     */             {
/*  583: 689 */               isSecureProcessing = true;
/*  584:     */               try
/*  585:     */               {
/*  586: 692 */                 tfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  587:     */               }
/*  588:     */               catch (TransformerConfigurationException e) {}
/*  589:     */             }
/*  590:     */             else
/*  591:     */             {
/*  592: 697 */               System.err.println(XSLMessages.createMessage("ER_INVALID_OPTION", new Object[] { argv[i] }));
/*  593:     */             }
/*  594:     */           }
/*  595:     */         }
/*  596:     */       }
/*  597: 703 */       if ((inFileName == null) && (xslFileName == null))
/*  598:     */       {
/*  599: 705 */         msg = resbundle.getString("xslProc_no_input");
/*  600: 706 */         System.err.println(msg);
/*  601: 707 */         doExit(msg);
/*  602:     */       }
/*  603:     */       try
/*  604:     */       {
/*  605: 714 */         long start = System.currentTimeMillis();
/*  606: 716 */         if (null != dumpFileName) {
/*  607: 718 */           dumpWriter = new PrintWriter(new FileWriter(dumpFileName));
/*  608:     */         }
/*  609: 721 */         Templates stylesheet = null;
/*  610: 723 */         if (null != xslFileName) {
/*  611: 725 */           if (flavor.equals("d2d"))
/*  612:     */           {
/*  613: 729 */             DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
/*  614:     */             
/*  615:     */ 
/*  616: 732 */             dfactory.setNamespaceAware(true);
/*  617: 734 */             if (isSecureProcessing) {
/*  618:     */               try
/*  619:     */               {
/*  620: 738 */                 dfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  621:     */               }
/*  622:     */               catch (ParserConfigurationException pce) {}
/*  623:     */             }
/*  624: 743 */             DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
/*  625: 744 */             Node xslDOM = docBuilder.parse(new InputSource(xslFileName));
/*  626:     */             
/*  627: 746 */             stylesheet = tfactory.newTemplates(new DOMSource(xslDOM, xslFileName));
/*  628:     */           }
/*  629:     */           else
/*  630:     */           {
/*  631: 752 */             stylesheet = tfactory.newTemplates(new StreamSource(xslFileName));
/*  632:     */           }
/*  633:     */         }
/*  634:     */         StreamResult strResult;
/*  635: 760 */         if (null != outFileName)
/*  636:     */         {
/*  637: 762 */           strResult = new StreamResult(new FileOutputStream(outFileName));
/*  638:     */           
/*  639:     */ 
/*  640:     */ 
/*  641:     */ 
/*  642: 767 */           strResult.setSystemId(outFileName);
/*  643:     */         }
/*  644:     */         else
/*  645:     */         {
/*  646: 771 */           strResult = new StreamResult(System.out);
/*  647:     */         }
/*  648: 778 */         SAXTransformerFactory stf = (SAXTransformerFactory)tfactory;
/*  649: 781 */         if ((!useXSLTC) && (useSourceLocation)) {
/*  650: 782 */           stf.setAttribute("http://xml.apache.org/xalan/properties/source-location", Boolean.TRUE);
/*  651:     */         }
/*  652: 786 */         if (null == stylesheet)
/*  653:     */         {
/*  654: 788 */           Source source = stf.getAssociatedStylesheet(new StreamSource(inFileName), media, null, null);
/*  655: 792 */           if (null != source)
/*  656:     */           {
/*  657: 793 */             stylesheet = tfactory.newTemplates(source);
/*  658:     */           }
/*  659:     */           else
/*  660:     */           {
/*  661: 796 */             if (null != media) {
/*  662: 797 */               throw new TransformerException(XSLMessages.createMessage("ER_NO_STYLESHEET_IN_MEDIA", new Object[] { inFileName, media }));
/*  663:     */             }
/*  664: 801 */             throw new TransformerException(XSLMessages.createMessage("ER_NO_STYLESHEET_PI", new Object[] { inFileName }));
/*  665:     */           }
/*  666:     */         }
/*  667: 806 */         if (null != stylesheet)
/*  668:     */         {
/*  669: 808 */           Transformer transformer = flavor.equals("th") ? null : stylesheet.newTransformer();
/*  670: 809 */           transformer.setErrorListener(new DefaultErrorHandler(false));
/*  671: 812 */           if (null != outputType) {
/*  672: 814 */             transformer.setOutputProperty("method", outputType);
/*  673:     */           }
/*  674: 817 */           if ((transformer instanceof TransformerImpl))
/*  675:     */           {
/*  676: 819 */             TransformerImpl impl = (TransformerImpl)transformer;
/*  677: 820 */             TraceManager tm = impl.getTraceManager();
/*  678: 822 */             if (null != tracer) {
/*  679: 823 */               tm.addTraceListener(tracer);
/*  680:     */             }
/*  681: 825 */             impl.setQuietConflictWarnings(quietConflictWarnings);
/*  682: 828 */             if (useSourceLocation) {
/*  683: 829 */               impl.setProperty("http://xml.apache.org/xalan/properties/source-location", Boolean.TRUE);
/*  684:     */             }
/*  685: 831 */             if (recursionLimit > 0) {
/*  686: 832 */               impl.setRecursionLimit(recursionLimit);
/*  687:     */             }
/*  688:     */           }
/*  689: 838 */           int nParams = params.size();
/*  690: 840 */           for (int i = 0; i < nParams; i += 2) {
/*  691: 842 */             transformer.setParameter((String)params.elementAt(i), (String)params.elementAt(i + 1));
/*  692:     */           }
/*  693: 846 */           if (uriResolver != null) {
/*  694: 847 */             transformer.setURIResolver(uriResolver);
/*  695:     */           }
/*  696: 849 */           if (null != inFileName)
/*  697:     */           {
/*  698: 851 */             if (flavor.equals("d2d"))
/*  699:     */             {
/*  700: 855 */               DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
/*  701:     */               
/*  702:     */ 
/*  703: 858 */               dfactory.setCoalescing(true);
/*  704: 859 */               dfactory.setNamespaceAware(true);
/*  705: 861 */               if (isSecureProcessing) {
/*  706:     */                 try
/*  707:     */                 {
/*  708: 865 */                   dfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  709:     */                 }
/*  710:     */                 catch (ParserConfigurationException pce) {}
/*  711:     */               }
/*  712: 870 */               DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
/*  713: 872 */               if (entityResolver != null) {
/*  714: 873 */                 docBuilder.setEntityResolver(entityResolver);
/*  715:     */               }
/*  716: 875 */               Node xmlDoc = docBuilder.parse(new InputSource(inFileName));
/*  717: 876 */               Document doc = docBuilder.newDocument();
/*  718: 877 */               DocumentFragment outNode = doc.createDocumentFragment();
/*  719:     */               
/*  720:     */ 
/*  721: 880 */               transformer.transform(new DOMSource(xmlDoc, inFileName), new DOMResult(outNode));
/*  722:     */               
/*  723:     */ 
/*  724:     */ 
/*  725: 884 */               Transformer serializer = stf.newTransformer();
/*  726: 885 */               serializer.setErrorListener(new DefaultErrorHandler(false));
/*  727:     */               
/*  728: 887 */               Properties serializationProps = stylesheet.getOutputProperties();
/*  729:     */               
/*  730:     */ 
/*  731: 890 */               serializer.setOutputProperties(serializationProps);
/*  732: 892 */               if (contentHandler != null)
/*  733:     */               {
/*  734: 894 */                 SAXResult result = new SAXResult(contentHandler);
/*  735:     */                 
/*  736: 896 */                 serializer.transform(new DOMSource(outNode), result);
/*  737:     */               }
/*  738:     */               else
/*  739:     */               {
/*  740: 899 */                 serializer.transform(new DOMSource(outNode), strResult);
/*  741:     */               }
/*  742:     */             }
/*  743: 901 */             else if (flavor.equals("th"))
/*  744:     */             {
/*  745: 903 */               for (int i = 0; i < 1; i++)
/*  746:     */               {
/*  747: 908 */                 XMLReader reader = null;
/*  748:     */                 try
/*  749:     */                 {
/*  750: 913 */                   SAXParserFactory factory = SAXParserFactory.newInstance();
/*  751:     */                   
/*  752:     */ 
/*  753: 916 */                   factory.setNamespaceAware(true);
/*  754: 918 */                   if (isSecureProcessing) {
/*  755:     */                     try
/*  756:     */                     {
/*  757: 922 */                       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  758:     */                     }
/*  759:     */                     catch (SAXException se) {}
/*  760:     */                   }
/*  761: 927 */                   SAXParser jaxpParser = factory.newSAXParser();
/*  762:     */                   
/*  763:     */ 
/*  764: 930 */                   reader = jaxpParser.getXMLReader();
/*  765:     */                 }
/*  766:     */                 catch (ParserConfigurationException ex)
/*  767:     */                 {
/*  768: 934 */                   throw new SAXException(ex);
/*  769:     */                 }
/*  770:     */                 catch (FactoryConfigurationError ex1)
/*  771:     */                 {
/*  772: 938 */                   throw new SAXException(ex1.toString());
/*  773:     */                 }
/*  774:     */                 catch (NoSuchMethodError ex2) {}catch (AbstractMethodError ame) {}
/*  775: 943 */                 if (null == reader) {
/*  776: 945 */                   reader = XMLReaderFactory.createXMLReader();
/*  777:     */                 }
/*  778: 948 */                 if (!useXSLTC) {
/*  779: 949 */                   stf.setAttribute("http://xml.apache.org/xalan/features/incremental", Boolean.TRUE);
/*  780:     */                 }
/*  781: 952 */                 TransformerHandler th = stf.newTransformerHandler(stylesheet);
/*  782:     */                 
/*  783: 954 */                 reader.setContentHandler(th);
/*  784: 955 */                 reader.setDTDHandler(th);
/*  785: 957 */                 if ((th instanceof ErrorHandler)) {
/*  786: 958 */                   reader.setErrorHandler((ErrorHandler)th);
/*  787:     */                 }
/*  788:     */                 try
/*  789:     */                 {
/*  790: 962 */                   reader.setProperty("http://xml.org/sax/properties/lexical-handler", th);
/*  791:     */                 }
/*  792:     */                 catch (SAXNotRecognizedException e) {}catch (SAXNotSupportedException e) {}
/*  793:     */                 try
/*  794:     */                 {
/*  795: 969 */                   reader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
/*  796:     */                 }
/*  797:     */                 catch (SAXException se) {}
/*  798: 973 */                 th.setResult(strResult);
/*  799:     */                 
/*  800: 975 */                 reader.parse(new InputSource(inFileName));
/*  801:     */               }
/*  802:     */             }
/*  803: 980 */             else if (entityResolver != null)
/*  804:     */             {
/*  805: 982 */               XMLReader reader = null;
/*  806:     */               try
/*  807:     */               {
/*  808: 987 */                 SAXParserFactory factory = SAXParserFactory.newInstance();
/*  809:     */                 
/*  810:     */ 
/*  811: 990 */                 factory.setNamespaceAware(true);
/*  812: 992 */                 if (isSecureProcessing) {
/*  813:     */                   try
/*  814:     */                   {
/*  815: 996 */                     factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
/*  816:     */                   }
/*  817:     */                   catch (SAXException se) {}
/*  818:     */                 }
/*  819:1001 */                 SAXParser jaxpParser = factory.newSAXParser();
/*  820:     */                 
/*  821:     */ 
/*  822:1004 */                 reader = jaxpParser.getXMLReader();
/*  823:     */               }
/*  824:     */               catch (ParserConfigurationException ex)
/*  825:     */               {
/*  826:1008 */                 throw new SAXException(ex);
/*  827:     */               }
/*  828:     */               catch (FactoryConfigurationError ex1)
/*  829:     */               {
/*  830:1012 */                 throw new SAXException(ex1.toString());
/*  831:     */               }
/*  832:     */               catch (NoSuchMethodError ex2) {}catch (AbstractMethodError ame) {}
/*  833:1017 */               if (null == reader) {
/*  834:1019 */                 reader = XMLReaderFactory.createXMLReader();
/*  835:     */               }
/*  836:1022 */               reader.setEntityResolver(entityResolver);
/*  837:1024 */               if (contentHandler != null)
/*  838:     */               {
/*  839:1026 */                 SAXResult result = new SAXResult(contentHandler);
/*  840:     */                 
/*  841:1028 */                 transformer.transform(new SAXSource(reader, new InputSource(inFileName)), result);
/*  842:     */               }
/*  843:     */               else
/*  844:     */               {
/*  845:1034 */                 transformer.transform(new SAXSource(reader, new InputSource(inFileName)), strResult);
/*  846:     */               }
/*  847:     */             }
/*  848:1039 */             else if (contentHandler != null)
/*  849:     */             {
/*  850:1041 */               SAXResult result = new SAXResult(contentHandler);
/*  851:     */               
/*  852:1043 */               transformer.transform(new StreamSource(inFileName), result);
/*  853:     */             }
/*  854:     */             else
/*  855:     */             {
/*  856:1048 */               transformer.transform(new StreamSource(inFileName), strResult);
/*  857:     */             }
/*  858:     */           }
/*  859:     */           else
/*  860:     */           {
/*  861:1056 */             StringReader reader = new StringReader("<?xml version=\"1.0\"?> <doc/>");
/*  862:     */             
/*  863:     */ 
/*  864:1059 */             transformer.transform(new StreamSource(reader), strResult);
/*  865:     */           }
/*  866:     */         }
/*  867:     */         else
/*  868:     */         {
/*  869:1065 */           msg = XSLMessages.createMessage("ER_NOT_SUCCESSFUL", null);
/*  870:     */           
/*  871:1067 */           diagnosticsWriter.println(msg);
/*  872:1068 */           doExit(msg);
/*  873:     */         }
/*  874:1072 */         if ((null != outFileName) && (strResult != null))
/*  875:     */         {
/*  876:1074 */           OutputStream out = strResult.getOutputStream();
/*  877:1075 */           Writer writer = strResult.getWriter();
/*  878:     */           try
/*  879:     */           {
/*  880:1078 */             if (out != null) {
/*  881:1078 */               out.close();
/*  882:     */             }
/*  883:1079 */             if (writer != null) {
/*  884:1079 */               writer.close();
/*  885:     */             }
/*  886:     */           }
/*  887:     */           catch (IOException ie) {}
/*  888:     */         }
/*  889:1084 */         long stop = System.currentTimeMillis();
/*  890:1085 */         long millisecondsDuration = stop - start;
/*  891:1087 */         if (doDiag)
/*  892:     */         {
/*  893:1089 */           Object[] msgArgs = { inFileName, xslFileName, new Long(millisecondsDuration) };
/*  894:1090 */           msg = XSLMessages.createMessage("diagTiming", msgArgs);
/*  895:1091 */           diagnosticsWriter.println('\n');
/*  896:1092 */           diagnosticsWriter.println(msg);
/*  897:     */         }
/*  898:     */       }
/*  899:     */       catch (Throwable throwable)
/*  900:     */       {
/*  901:1099 */         while ((throwable instanceof WrappedRuntimeException)) {
/*  902:1101 */           throwable = ((WrappedRuntimeException)throwable).getException();
/*  903:     */         }
/*  904:1105 */         if (((throwable instanceof NullPointerException)) || ((throwable instanceof ClassCastException))) {
/*  905:1107 */           doStackDumpOnError = true;
/*  906:     */         }
/*  907:1109 */         diagnosticsWriter.println();
/*  908:1111 */         if (doStackDumpOnError)
/*  909:     */         {
/*  910:1112 */           throwable.printStackTrace(dumpWriter);
/*  911:     */         }
/*  912:     */         else
/*  913:     */         {
/*  914:1115 */           DefaultErrorHandler.printLocation(diagnosticsWriter, throwable);
/*  915:1116 */           diagnosticsWriter.println(XSLMessages.createMessage("ER_XSLT_ERROR", null) + " (" + throwable.getClass().getName() + "): " + throwable.getMessage());
/*  916:     */         }
/*  917:1123 */         if (null != dumpFileName) {
/*  918:1125 */           dumpWriter.close();
/*  919:     */         }
/*  920:1128 */         doExit(throwable.getMessage());
/*  921:     */       }
/*  922:1131 */       if (null != dumpFileName) {
/*  923:1133 */         dumpWriter.close();
/*  924:     */       }
/*  925:1136 */       if (null == diagnosticsWriter) {}
/*  926:     */     }
/*  927:     */   }
/*  928:     */   
/*  929:     */   static void doExit(String msg)
/*  930:     */   {
/*  931:1155 */     throw new RuntimeException(msg);
/*  932:     */   }
/*  933:     */   
/*  934:     */   private static void waitForReturnKey(ResourceBundle resbundle)
/*  935:     */   {
/*  936:1165 */     System.out.println(resbundle.getString("xslProc_return_to_continue"));
/*  937:     */     try
/*  938:     */     {
/*  939:1168 */       while (System.in.read() != 10) {}
/*  940:     */     }
/*  941:     */     catch (IOException e) {}
/*  942:     */   }
/*  943:     */   
/*  944:     */   private static void printInvalidXSLTCOption(String option)
/*  945:     */   {
/*  946:1180 */     System.err.println(XSLMessages.createMessage("xslProc_invalid_xsltc_option", new Object[] { option }));
/*  947:     */   }
/*  948:     */   
/*  949:     */   private static void printInvalidXalanOption(String option)
/*  950:     */   {
/*  951:1190 */     System.err.println(XSLMessages.createMessage("xslProc_invalid_xalan_option", new Object[] { option }));
/*  952:     */   }
/*  953:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xslt.Process
 * JD-Core Version:    0.7.0.1
 */