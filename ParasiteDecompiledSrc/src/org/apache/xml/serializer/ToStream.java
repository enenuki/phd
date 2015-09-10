/*    1:     */ package org.apache.xml.serializer;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.OutputStream;
/*    5:     */ import java.io.OutputStreamWriter;
/*    6:     */ import java.io.PrintStream;
/*    7:     */ import java.io.UnsupportedEncodingException;
/*    8:     */ import java.io.Writer;
/*    9:     */ import java.util.Enumeration;
/*   10:     */ import java.util.Hashtable;
/*   11:     */ import java.util.Iterator;
/*   12:     */ import java.util.Properties;
/*   13:     */ import java.util.Set;
/*   14:     */ import java.util.StringTokenizer;
/*   15:     */ import java.util.Vector;
/*   16:     */ import javax.xml.transform.ErrorListener;
/*   17:     */ import javax.xml.transform.Transformer;
/*   18:     */ import javax.xml.transform.TransformerException;
/*   19:     */ import org.apache.xml.serializer.utils.Messages;
/*   20:     */ import org.apache.xml.serializer.utils.Utils;
/*   21:     */ import org.apache.xml.serializer.utils.WrappedRuntimeException;
/*   22:     */ import org.w3c.dom.Node;
/*   23:     */ import org.xml.sax.Attributes;
/*   24:     */ import org.xml.sax.ContentHandler;
/*   25:     */ import org.xml.sax.SAXException;
/*   26:     */ import org.xml.sax.helpers.AttributesImpl;
/*   27:     */ 
/*   28:     */ public abstract class ToStream
/*   29:     */   extends SerializerBase
/*   30:     */ {
/*   31:     */   private static final String COMMENT_BEGIN = "<!--";
/*   32:     */   private static final String COMMENT_END = "-->";
/*   33:  62 */   protected BoolStack m_disableOutputEscapingStates = new BoolStack();
/*   34:  76 */   EncodingInfo m_encodingInfo = new EncodingInfo(null, null, '\000');
/*   35:  87 */   protected BoolStack m_preserves = new BoolStack();
/*   36:  97 */   protected boolean m_ispreserve = false;
/*   37: 107 */   protected boolean m_isprevtext = false;
/*   38:     */   private static final char[] s_systemLineSep;
/*   39:     */   
/*   40:     */   static
/*   41:     */   {
/*   42: 111 */     SecuritySupport ss = SecuritySupport.getInstance();
/*   43: 112 */     s_systemLineSep = ss.getSystemProperty("line.separator").toCharArray();
/*   44:     */   }
/*   45:     */   
/*   46: 121 */   protected char[] m_lineSep = s_systemLineSep;
/*   47: 127 */   protected boolean m_lineSepUse = true;
/*   48: 133 */   protected int m_lineSepLen = this.m_lineSep.length;
/*   49:     */   protected CharInfo m_charInfo;
/*   50: 142 */   boolean m_shouldFlush = true;
/*   51: 147 */   protected boolean m_spaceBeforeClose = false;
/*   52:     */   boolean m_startNewLine;
/*   53: 160 */   protected boolean m_inDoctype = false;
/*   54: 165 */   boolean m_isUTF8 = false;
/*   55: 171 */   protected boolean m_cdataStartCalled = false;
/*   56: 177 */   private boolean m_expandDTDEntities = true;
/*   57:     */   
/*   58:     */   protected void closeCDATA()
/*   59:     */     throws SAXException
/*   60:     */   {
/*   61:     */     try
/*   62:     */     {
/*   63: 196 */       this.m_writer.write("]]>");
/*   64:     */       
/*   65: 198 */       this.m_cdataTagOpen = false;
/*   66:     */     }
/*   67:     */     catch (IOException e)
/*   68:     */     {
/*   69: 202 */       throw new SAXException(e);
/*   70:     */     }
/*   71:     */   }
/*   72:     */   
/*   73:     */   public void serialize(Node node)
/*   74:     */     throws IOException
/*   75:     */   {
/*   76:     */     try
/*   77:     */     {
/*   78: 218 */       TreeWalker walker = new TreeWalker(this);
/*   79:     */       
/*   80:     */ 
/*   81: 221 */       walker.traverse(node);
/*   82:     */     }
/*   83:     */     catch (SAXException se)
/*   84:     */     {
/*   85: 225 */       throw new WrappedRuntimeException(se);
/*   86:     */     }
/*   87:     */   }
/*   88:     */   
/*   89: 232 */   protected boolean m_escaping = true;
/*   90:     */   OutputStream m_outputStream;
/*   91:     */   private boolean m_writer_set_by_user;
/*   92:     */   
/*   93:     */   protected final void flushWriter()
/*   94:     */     throws SAXException
/*   95:     */   {
/*   96: 241 */     Writer writer = this.m_writer;
/*   97: 242 */     if (null != writer) {
/*   98:     */       try
/*   99:     */       {
/*  100: 246 */         if ((writer instanceof WriterToUTF8Buffered)) {
/*  101: 248 */           if (this.m_shouldFlush) {
/*  102: 249 */             ((WriterToUTF8Buffered)writer).flush();
/*  103:     */           } else {
/*  104: 251 */             ((WriterToUTF8Buffered)writer).flushBuffer();
/*  105:     */           }
/*  106:     */         }
/*  107: 253 */         if ((writer instanceof WriterToASCI))
/*  108:     */         {
/*  109: 255 */           if (this.m_shouldFlush) {
/*  110: 256 */             writer.flush();
/*  111:     */           }
/*  112:     */         }
/*  113:     */         else {
/*  114: 263 */           writer.flush();
/*  115:     */         }
/*  116:     */       }
/*  117:     */       catch (IOException ioe)
/*  118:     */       {
/*  119: 268 */         throw new SAXException(ioe);
/*  120:     */       }
/*  121:     */     }
/*  122:     */   }
/*  123:     */   
/*  124:     */   public OutputStream getOutputStream()
/*  125:     */   {
/*  126: 282 */     return this.m_outputStream;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public void elementDecl(String name, String model)
/*  130:     */     throws SAXException
/*  131:     */   {
/*  132: 303 */     if (this.m_inExternalDTD) {
/*  133: 304 */       return;
/*  134:     */     }
/*  135:     */     try
/*  136:     */     {
/*  137: 307 */       Writer writer = this.m_writer;
/*  138: 308 */       DTDprolog();
/*  139:     */       
/*  140: 310 */       writer.write("<!ELEMENT ");
/*  141: 311 */       writer.write(name);
/*  142: 312 */       writer.write(32);
/*  143: 313 */       writer.write(model);
/*  144: 314 */       writer.write(62);
/*  145: 315 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  146:     */     }
/*  147:     */     catch (IOException e)
/*  148:     */     {
/*  149: 319 */       throw new SAXException(e);
/*  150:     */     }
/*  151:     */   }
/*  152:     */   
/*  153:     */   public void internalEntityDecl(String name, String value)
/*  154:     */     throws SAXException
/*  155:     */   {
/*  156: 341 */     if (this.m_inExternalDTD) {
/*  157: 342 */       return;
/*  158:     */     }
/*  159:     */     try
/*  160:     */     {
/*  161: 345 */       DTDprolog();
/*  162: 346 */       outputEntityDecl(name, value);
/*  163:     */     }
/*  164:     */     catch (IOException e)
/*  165:     */     {
/*  166: 350 */       throw new SAXException(e);
/*  167:     */     }
/*  168:     */   }
/*  169:     */   
/*  170:     */   void outputEntityDecl(String name, String value)
/*  171:     */     throws IOException
/*  172:     */   {
/*  173: 365 */     Writer writer = this.m_writer;
/*  174: 366 */     writer.write("<!ENTITY ");
/*  175: 367 */     writer.write(name);
/*  176: 368 */     writer.write(" \"");
/*  177: 369 */     writer.write(value);
/*  178: 370 */     writer.write("\">");
/*  179: 371 */     writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  180:     */   }
/*  181:     */   
/*  182:     */   protected final void outputLineSep()
/*  183:     */     throws IOException
/*  184:     */   {
/*  185: 382 */     this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  186:     */   }
/*  187:     */   
/*  188:     */   void setProp(String name, String val, boolean defaultVal)
/*  189:     */   {
/*  190: 386 */     if (val != null)
/*  191:     */     {
/*  192: 389 */       char first = SerializerBase.getFirstCharLocName(name);
/*  193: 390 */       switch (first)
/*  194:     */       {
/*  195:     */       case 'c': 
/*  196: 392 */         if ("cdata-section-elements".equals(name))
/*  197:     */         {
/*  198: 393 */           String cdataSectionNames = val;
/*  199: 394 */           addCdataSectionElements(cdataSectionNames);
/*  200:     */         }
/*  201:     */         break;
/*  202:     */       case 'd': 
/*  203: 398 */         if ("doctype-system".equals(name))
/*  204:     */         {
/*  205: 399 */           this.m_doctypeSystem = val;
/*  206:     */         }
/*  207: 400 */         else if ("doctype-public".equals(name))
/*  208:     */         {
/*  209: 401 */           this.m_doctypePublic = val;
/*  210: 402 */           if (val.startsWith("-//W3C//DTD XHTML")) {
/*  211: 403 */             this.m_spaceBeforeClose = true;
/*  212:     */           }
/*  213:     */         }
/*  214:     */         break;
/*  215:     */       case 'e': 
/*  216: 407 */         String newEncoding = val;
/*  217: 408 */         if ("encoding".equals(name))
/*  218:     */         {
/*  219: 409 */           String possible_encoding = Encodings.getMimeEncoding(val);
/*  220: 410 */           if (possible_encoding != null) {
/*  221: 414 */             super.setProp("mime-name", possible_encoding, defaultVal);
/*  222:     */           }
/*  223: 417 */           String oldExplicitEncoding = getOutputPropertyNonDefault("encoding");
/*  224: 418 */           String oldDefaultEncoding = getOutputPropertyDefault("encoding");
/*  225: 419 */           if (((defaultVal) && ((oldDefaultEncoding == null) || (!oldDefaultEncoding.equalsIgnoreCase(newEncoding)))) || ((!defaultVal) && ((oldExplicitEncoding == null) || (!oldExplicitEncoding.equalsIgnoreCase(newEncoding)))))
/*  226:     */           {
/*  227: 424 */             EncodingInfo encodingInfo = Encodings.getEncodingInfo(newEncoding);
/*  228: 425 */             if ((newEncoding != null) && (encodingInfo.name == null))
/*  229:     */             {
/*  230: 429 */               String msg = Utils.messages.createMessage("ER_ENCODING_NOT_SUPPORTED", new Object[] { newEncoding });
/*  231:     */               
/*  232:     */ 
/*  233: 432 */               String msg2 = "Warning: encoding \"" + newEncoding + "\" not supported, using " + "UTF-8";
/*  234:     */               try
/*  235:     */               {
/*  236: 437 */                 Transformer tran = super.getTransformer();
/*  237: 438 */                 if (tran != null)
/*  238:     */                 {
/*  239: 439 */                   ErrorListener errHandler = tran.getErrorListener();
/*  240: 442 */                   if ((null != errHandler) && (this.m_sourceLocator != null))
/*  241:     */                   {
/*  242: 444 */                     errHandler.warning(new TransformerException(msg, this.m_sourceLocator));
/*  243:     */                     
/*  244:     */ 
/*  245: 447 */                     errHandler.warning(new TransformerException(msg2, this.m_sourceLocator));
/*  246:     */                   }
/*  247:     */                   else
/*  248:     */                   {
/*  249: 451 */                     System.out.println(msg);
/*  250: 452 */                     System.out.println(msg2);
/*  251:     */                   }
/*  252:     */                 }
/*  253:     */                 else
/*  254:     */                 {
/*  255: 455 */                   System.out.println(msg);
/*  256: 456 */                   System.out.println(msg2);
/*  257:     */                 }
/*  258:     */               }
/*  259:     */               catch (Exception e) {}
/*  260: 462 */               newEncoding = "UTF-8";
/*  261: 463 */               val = "UTF-8";
/*  262: 464 */               encodingInfo = Encodings.getEncodingInfo(newEncoding);
/*  263:     */             }
/*  264: 473 */             if ((!defaultVal) || (oldExplicitEncoding == null))
/*  265:     */             {
/*  266: 474 */               this.m_encodingInfo = encodingInfo;
/*  267: 475 */               if (newEncoding != null) {
/*  268: 476 */                 this.m_isUTF8 = newEncoding.equals("UTF-8");
/*  269:     */               }
/*  270: 479 */               OutputStream os = getOutputStream();
/*  271: 480 */               if (os != null)
/*  272:     */               {
/*  273: 481 */                 Writer w = getWriter();
/*  274:     */                 
/*  275:     */ 
/*  276:     */ 
/*  277:     */ 
/*  278: 486 */                 String oldEncoding = getOutputProperty("encoding");
/*  279: 487 */                 if (((w == null) || (!this.m_writer_set_by_user)) && (!newEncoding.equalsIgnoreCase(oldEncoding)))
/*  280:     */                 {
/*  281: 493 */                   super.setProp(name, val, defaultVal);
/*  282: 494 */                   setOutputStreamInternal(os, false);
/*  283:     */                 }
/*  284:     */               }
/*  285:     */             }
/*  286:     */           }
/*  287:     */         }
/*  288:     */         break;
/*  289:     */       case 'i': 
/*  290: 502 */         if ("{http://xml.apache.org/xalan}indent-amount".equals(name))
/*  291:     */         {
/*  292: 503 */           setIndentAmount(Integer.parseInt(val));
/*  293:     */         }
/*  294: 504 */         else if ("indent".equals(name))
/*  295:     */         {
/*  296: 505 */           boolean b = "yes".equals(val);
/*  297: 506 */           this.m_doIndent = b;
/*  298:     */         }
/*  299:     */         break;
/*  300:     */       case 'l': 
/*  301: 511 */         if ("{http://xml.apache.org/xalan}line-separator".equals(name))
/*  302:     */         {
/*  303: 512 */           this.m_lineSep = val.toCharArray();
/*  304: 513 */           this.m_lineSepLen = this.m_lineSep.length;
/*  305:     */         }
/*  306:     */         break;
/*  307:     */       case 'm': 
/*  308: 518 */         if ("media-type".equals(name)) {
/*  309: 519 */           this.m_mediatype = val;
/*  310:     */         }
/*  311:     */         break;
/*  312:     */       case 'o': 
/*  313: 523 */         if ("omit-xml-declaration".equals(name))
/*  314:     */         {
/*  315: 524 */           boolean b = "yes".equals(val);
/*  316: 525 */           this.m_shouldNotWriteXMLHeader = b;
/*  317:     */         }
/*  318:     */         break;
/*  319:     */       case 's': 
/*  320: 530 */         if ("standalone".equals(name)) {
/*  321: 531 */           if (defaultVal)
/*  322:     */           {
/*  323: 532 */             setStandaloneInternal(val);
/*  324:     */           }
/*  325:     */           else
/*  326:     */           {
/*  327: 534 */             this.m_standaloneWasSpecified = true;
/*  328: 535 */             setStandaloneInternal(val);
/*  329:     */           }
/*  330:     */         }
/*  331:     */         break;
/*  332:     */       case 'v': 
/*  333: 541 */         if ("version".equals(name)) {
/*  334: 542 */           this.m_version = val;
/*  335:     */         }
/*  336:     */         break;
/*  337:     */       }
/*  338: 549 */       super.setProp(name, val, defaultVal);
/*  339:     */     }
/*  340:     */   }
/*  341:     */   
/*  342:     */   public void setOutputFormat(Properties format)
/*  343:     */   {
/*  344: 564 */     boolean shouldFlush = this.m_shouldFlush;
/*  345: 566 */     if (format != null)
/*  346:     */     {
/*  347: 573 */       Enumeration propNames = format.propertyNames();
/*  348: 574 */       while (propNames.hasMoreElements())
/*  349:     */       {
/*  350: 576 */         String key = (String)propNames.nextElement();
/*  351:     */         
/*  352: 578 */         String value = format.getProperty(key);
/*  353:     */         
/*  354: 580 */         String explicitValue = (String)format.get(key);
/*  355: 581 */         if ((explicitValue == null) && (value != null)) {
/*  356: 583 */           setOutputPropertyDefault(key, value);
/*  357:     */         }
/*  358: 585 */         if (explicitValue != null) {
/*  359: 587 */           setOutputProperty(key, explicitValue);
/*  360:     */         }
/*  361:     */       }
/*  362:     */     }
/*  363: 594 */     String entitiesFileName = (String)format.get("{http://xml.apache.org/xalan}entities");
/*  364: 597 */     if (null != entitiesFileName)
/*  365:     */     {
/*  366: 600 */       String method = (String)format.get("method");
/*  367:     */       
/*  368:     */ 
/*  369: 603 */       this.m_charInfo = CharInfo.getCharInfo(entitiesFileName, method);
/*  370:     */     }
/*  371: 609 */     this.m_shouldFlush = shouldFlush;
/*  372:     */   }
/*  373:     */   
/*  374:     */   public Properties getOutputFormat()
/*  375:     */   {
/*  376: 618 */     Properties def = new Properties();
/*  377:     */     
/*  378: 620 */     Set s = getOutputPropDefaultKeys();
/*  379: 621 */     Iterator i = s.iterator();
/*  380: 622 */     while (i.hasNext())
/*  381:     */     {
/*  382: 623 */       String key = (String)i.next();
/*  383: 624 */       String val = getOutputPropertyDefault(key);
/*  384: 625 */       def.put(key, val);
/*  385:     */     }
/*  386: 629 */     Properties props = new Properties(def);
/*  387:     */     
/*  388: 631 */     Set s = getOutputPropKeys();
/*  389: 632 */     Iterator i = s.iterator();
/*  390: 633 */     while (i.hasNext())
/*  391:     */     {
/*  392: 634 */       String key = (String)i.next();
/*  393: 635 */       String val = getOutputPropertyNonDefault(key);
/*  394: 636 */       if (val != null) {
/*  395: 637 */         props.put(key, val);
/*  396:     */       }
/*  397:     */     }
/*  398: 640 */     return props;
/*  399:     */   }
/*  400:     */   
/*  401:     */   public void setWriter(Writer writer)
/*  402:     */   {
/*  403: 652 */     setWriterInternal(writer, true);
/*  404:     */   }
/*  405:     */   
/*  406:     */   private void setWriterInternal(Writer writer, boolean setByUser)
/*  407:     */   {
/*  408: 658 */     this.m_writer_set_by_user = setByUser;
/*  409: 659 */     this.m_writer = writer;
/*  410: 662 */     if (this.m_tracer != null)
/*  411:     */     {
/*  412: 663 */       boolean noTracerYet = true;
/*  413: 664 */       Writer w2 = this.m_writer;
/*  414: 665 */       while ((w2 instanceof WriterChain))
/*  415:     */       {
/*  416: 666 */         if ((w2 instanceof SerializerTraceWriter))
/*  417:     */         {
/*  418: 667 */           noTracerYet = false;
/*  419: 668 */           break;
/*  420:     */         }
/*  421: 670 */         w2 = ((WriterChain)w2).getWriter();
/*  422:     */       }
/*  423: 672 */       if (noTracerYet) {
/*  424: 673 */         this.m_writer = new SerializerTraceWriter(this.m_writer, this.m_tracer);
/*  425:     */       }
/*  426:     */     }
/*  427:     */   }
/*  428:     */   
/*  429:     */   public boolean setLineSepUse(boolean use_sytem_line_break)
/*  430:     */   {
/*  431: 691 */     boolean oldValue = this.m_lineSepUse;
/*  432: 692 */     this.m_lineSepUse = use_sytem_line_break;
/*  433: 693 */     return oldValue;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public void setOutputStream(OutputStream output)
/*  437:     */   {
/*  438: 709 */     setOutputStreamInternal(output, true);
/*  439:     */   }
/*  440:     */   
/*  441:     */   private void setOutputStreamInternal(OutputStream output, boolean setByUser)
/*  442:     */   {
/*  443: 714 */     this.m_outputStream = output;
/*  444: 715 */     String encoding = getOutputProperty("encoding");
/*  445: 716 */     if ("UTF-8".equalsIgnoreCase(encoding))
/*  446:     */     {
/*  447: 720 */       setWriterInternal(new WriterToUTF8Buffered(output), false);
/*  448:     */     }
/*  449: 721 */     else if (("WINDOWS-1250".equals(encoding)) || ("US-ASCII".equals(encoding)) || ("ASCII".equals(encoding)))
/*  450:     */     {
/*  451: 726 */       setWriterInternal(new WriterToASCI(output), false);
/*  452:     */     }
/*  453: 727 */     else if (encoding != null)
/*  454:     */     {
/*  455: 728 */       Writer osw = null;
/*  456:     */       try
/*  457:     */       {
/*  458: 731 */         osw = Encodings.getWriter(output, encoding);
/*  459:     */       }
/*  460:     */       catch (UnsupportedEncodingException uee)
/*  461:     */       {
/*  462: 735 */         osw = null;
/*  463:     */       }
/*  464: 739 */       if (osw == null)
/*  465:     */       {
/*  466: 740 */         System.out.println("Warning: encoding \"" + encoding + "\" not supported" + ", using " + "UTF-8");
/*  467:     */         
/*  468:     */ 
/*  469:     */ 
/*  470:     */ 
/*  471:     */ 
/*  472:     */ 
/*  473: 747 */         encoding = "UTF-8";
/*  474: 748 */         setEncoding(encoding);
/*  475:     */         try
/*  476:     */         {
/*  477: 750 */           osw = Encodings.getWriter(output, encoding);
/*  478:     */         }
/*  479:     */         catch (UnsupportedEncodingException e)
/*  480:     */         {
/*  481: 754 */           e.printStackTrace();
/*  482:     */         }
/*  483:     */       }
/*  484: 757 */       setWriterInternal(osw, false);
/*  485:     */     }
/*  486:     */     else
/*  487:     */     {
/*  488: 761 */       Writer osw = new OutputStreamWriter(output);
/*  489: 762 */       setWriterInternal(osw, false);
/*  490:     */     }
/*  491:     */   }
/*  492:     */   
/*  493:     */   public boolean setEscaping(boolean escape)
/*  494:     */   {
/*  495: 771 */     boolean temp = this.m_escaping;
/*  496: 772 */     this.m_escaping = escape;
/*  497: 773 */     return temp;
/*  498:     */   }
/*  499:     */   
/*  500:     */   protected void indent(int depth)
/*  501:     */     throws IOException
/*  502:     */   {
/*  503: 789 */     if (this.m_startNewLine) {
/*  504: 790 */       outputLineSep();
/*  505:     */     }
/*  506: 795 */     if (this.m_indentAmount > 0) {
/*  507: 796 */       printSpace(depth * this.m_indentAmount);
/*  508:     */     }
/*  509:     */   }
/*  510:     */   
/*  511:     */   protected void indent()
/*  512:     */     throws IOException
/*  513:     */   {
/*  514: 806 */     indent(this.m_elemContext.m_currentElemDepth);
/*  515:     */   }
/*  516:     */   
/*  517:     */   private void printSpace(int n)
/*  518:     */     throws IOException
/*  519:     */   {
/*  520: 816 */     Writer writer = this.m_writer;
/*  521: 817 */     for (int i = 0; i < n; i++) {
/*  522: 819 */       writer.write(32);
/*  523:     */     }
/*  524:     */   }
/*  525:     */   
/*  526:     */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value)
/*  527:     */     throws SAXException
/*  528:     */   {
/*  529: 852 */     if (this.m_inExternalDTD) {
/*  530: 853 */       return;
/*  531:     */     }
/*  532:     */     try
/*  533:     */     {
/*  534: 856 */       Writer writer = this.m_writer;
/*  535: 857 */       DTDprolog();
/*  536:     */       
/*  537: 859 */       writer.write("<!ATTLIST ");
/*  538: 860 */       writer.write(eName);
/*  539: 861 */       writer.write(32);
/*  540:     */       
/*  541: 863 */       writer.write(aName);
/*  542: 864 */       writer.write(32);
/*  543: 865 */       writer.write(type);
/*  544: 866 */       if (valueDefault != null)
/*  545:     */       {
/*  546: 868 */         writer.write(32);
/*  547: 869 */         writer.write(valueDefault);
/*  548:     */       }
/*  549: 874 */       writer.write(62);
/*  550: 875 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  551:     */     }
/*  552:     */     catch (IOException e)
/*  553:     */     {
/*  554: 879 */       throw new SAXException(e);
/*  555:     */     }
/*  556:     */   }
/*  557:     */   
/*  558:     */   public Writer getWriter()
/*  559:     */   {
/*  560: 890 */     return this.m_writer;
/*  561:     */   }
/*  562:     */   
/*  563:     */   public void externalEntityDecl(String name, String publicId, String systemId)
/*  564:     */     throws SAXException
/*  565:     */   {
/*  566:     */     try
/*  567:     */     {
/*  568: 915 */       DTDprolog();
/*  569:     */       
/*  570: 917 */       this.m_writer.write("<!ENTITY ");
/*  571: 918 */       this.m_writer.write(name);
/*  572: 919 */       if (publicId != null)
/*  573:     */       {
/*  574: 920 */         this.m_writer.write(" PUBLIC \"");
/*  575: 921 */         this.m_writer.write(publicId);
/*  576:     */       }
/*  577:     */       else
/*  578:     */       {
/*  579: 925 */         this.m_writer.write(" SYSTEM \"");
/*  580: 926 */         this.m_writer.write(systemId);
/*  581:     */       }
/*  582: 928 */       this.m_writer.write("\" >");
/*  583: 929 */       this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  584:     */     }
/*  585:     */     catch (IOException e)
/*  586:     */     {
/*  587: 932 */       e.printStackTrace();
/*  588:     */     }
/*  589:     */   }
/*  590:     */   
/*  591:     */   protected boolean escapingNotNeeded(char ch)
/*  592:     */   {
/*  593:     */     boolean ret;
/*  594: 943 */     if (ch < '')
/*  595:     */     {
/*  596: 947 */       if ((ch >= ' ') || ('\n' == ch) || ('\r' == ch) || ('\t' == ch)) {
/*  597: 949 */         ret = true;
/*  598:     */       } else {
/*  599: 951 */         ret = false;
/*  600:     */       }
/*  601:     */     }
/*  602:     */     else {
/*  603: 954 */       ret = this.m_encodingInfo.isInEncoding(ch);
/*  604:     */     }
/*  605: 956 */     return ret;
/*  606:     */   }
/*  607:     */   
/*  608:     */   protected int writeUTF16Surrogate(char c, char[] ch, int i, int end)
/*  609:     */     throws IOException
/*  610:     */   {
/*  611: 986 */     int codePoint = 0;
/*  612: 987 */     if (i + 1 >= end) {
/*  613: 989 */       throw new IOException(Utils.messages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[] { Integer.toHexString(c) }));
/*  614:     */     }
/*  615: 995 */     char high = c;
/*  616: 996 */     char low = ch[(i + 1)];
/*  617: 997 */     if (!Encodings.isLowUTF16Surrogate(low)) {
/*  618: 998 */       throw new IOException(Utils.messages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[] { Integer.toHexString(c) + " " + Integer.toHexString(low) }));
/*  619:     */     }
/*  620:1007 */     Writer writer = this.m_writer;
/*  621:1010 */     if (this.m_encodingInfo.isInEncoding(c, low))
/*  622:     */     {
/*  623:1013 */       writer.write(ch, i, 2);
/*  624:     */     }
/*  625:     */     else
/*  626:     */     {
/*  627:1019 */       String encoding = getEncoding();
/*  628:1020 */       if (encoding != null)
/*  629:     */       {
/*  630:1024 */         codePoint = Encodings.toCodePoint(high, low);
/*  631:     */         
/*  632:1026 */         writer.write(38);
/*  633:1027 */         writer.write(35);
/*  634:1028 */         writer.write(Integer.toString(codePoint));
/*  635:1029 */         writer.write(59);
/*  636:     */       }
/*  637:     */       else
/*  638:     */       {
/*  639:1034 */         writer.write(ch, i, 2);
/*  640:     */       }
/*  641:     */     }
/*  642:1038 */     return codePoint;
/*  643:     */   }
/*  644:     */   
/*  645:     */   int accumDefaultEntity(Writer writer, char ch, int i, char[] chars, int len, boolean fromTextNode, boolean escLF)
/*  646:     */     throws IOException
/*  647:     */   {
/*  648:1068 */     if ((!escLF) && ('\n' == ch))
/*  649:     */     {
/*  650:1070 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  651:     */     }
/*  652:1076 */     else if (((fromTextNode) && (this.m_charInfo.shouldMapTextChar(ch))) || ((!fromTextNode) && (this.m_charInfo.shouldMapAttrChar(ch))))
/*  653:     */     {
/*  654:1078 */       String outputStringForChar = this.m_charInfo.getOutputStringForChar(ch);
/*  655:1080 */       if (null != outputStringForChar) {
/*  656:1082 */         writer.write(outputStringForChar);
/*  657:     */       } else {
/*  658:1085 */         return i;
/*  659:     */       }
/*  660:     */     }
/*  661:     */     else
/*  662:     */     {
/*  663:1088 */       return i;
/*  664:     */     }
/*  665:1091 */     return i + 1;
/*  666:     */   }
/*  667:     */   
/*  668:     */   void writeNormalizedChars(char[] ch, int start, int length, boolean isCData, boolean useSystemLineSeparator)
/*  669:     */     throws IOException, SAXException
/*  670:     */   {
/*  671:1115 */     Writer writer = this.m_writer;
/*  672:1116 */     int end = start + length;
/*  673:1118 */     for (int i = start; i < end; i++)
/*  674:     */     {
/*  675:1120 */       char c = ch[i];
/*  676:1122 */       if (('\n' == c) && (useSystemLineSeparator))
/*  677:     */       {
/*  678:1124 */         writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  679:     */       }
/*  680:1126 */       else if ((isCData) && (!escapingNotNeeded(c)))
/*  681:     */       {
/*  682:1129 */         if (this.m_cdataTagOpen) {
/*  683:1130 */           closeCDATA();
/*  684:     */         }
/*  685:1133 */         if (Encodings.isHighUTF16Surrogate(c))
/*  686:     */         {
/*  687:1135 */           writeUTF16Surrogate(c, ch, i, end);
/*  688:1136 */           i++;
/*  689:     */         }
/*  690:     */         else
/*  691:     */         {
/*  692:1140 */           writer.write("&#");
/*  693:     */           
/*  694:1142 */           String intStr = Integer.toString(c);
/*  695:     */           
/*  696:1144 */           writer.write(intStr);
/*  697:1145 */           writer.write(59);
/*  698:     */         }
/*  699:     */       }
/*  700:1155 */       else if ((isCData) && (i < end - 2) && (']' == c) && (']' == ch[(i + 1)]) && ('>' == ch[(i + 2)]))
/*  701:     */       {
/*  702:1162 */         writer.write("]]]]><![CDATA[>");
/*  703:     */         
/*  704:1164 */         i += 2;
/*  705:     */       }
/*  706:1168 */       else if (escapingNotNeeded(c))
/*  707:     */       {
/*  708:1170 */         if ((isCData) && (!this.m_cdataTagOpen))
/*  709:     */         {
/*  710:1172 */           writer.write("<![CDATA[");
/*  711:1173 */           this.m_cdataTagOpen = true;
/*  712:     */         }
/*  713:1175 */         writer.write(c);
/*  714:     */       }
/*  715:1179 */       else if (Encodings.isHighUTF16Surrogate(c))
/*  716:     */       {
/*  717:1181 */         if (this.m_cdataTagOpen) {
/*  718:1182 */           closeCDATA();
/*  719:     */         }
/*  720:1183 */         writeUTF16Surrogate(c, ch, i, end);
/*  721:1184 */         i++;
/*  722:     */       }
/*  723:     */       else
/*  724:     */       {
/*  725:1188 */         if (this.m_cdataTagOpen) {
/*  726:1189 */           closeCDATA();
/*  727:     */         }
/*  728:1190 */         writer.write("&#");
/*  729:     */         
/*  730:1192 */         String intStr = Integer.toString(c);
/*  731:     */         
/*  732:1194 */         writer.write(intStr);
/*  733:1195 */         writer.write(59);
/*  734:     */       }
/*  735:     */     }
/*  736:     */   }
/*  737:     */   
/*  738:     */   public void endNonEscaping()
/*  739:     */     throws SAXException
/*  740:     */   {
/*  741:1211 */     this.m_disableOutputEscapingStates.pop();
/*  742:     */   }
/*  743:     */   
/*  744:     */   public void startNonEscaping()
/*  745:     */     throws SAXException
/*  746:     */   {
/*  747:1226 */     this.m_disableOutputEscapingStates.push(true);
/*  748:     */   }
/*  749:     */   
/*  750:     */   protected void cdata(char[] ch, int start, int length)
/*  751:     */     throws SAXException
/*  752:     */   {
/*  753:     */     try
/*  754:     */     {
/*  755:1262 */       int old_start = start;
/*  756:1263 */       if (this.m_elemContext.m_startTagOpen)
/*  757:     */       {
/*  758:1265 */         closeStartTag();
/*  759:1266 */         this.m_elemContext.m_startTagOpen = false;
/*  760:     */       }
/*  761:1268 */       this.m_ispreserve = true;
/*  762:1270 */       if (shouldIndent()) {
/*  763:1271 */         indent();
/*  764:     */       }
/*  765:1273 */       boolean writeCDataBrackets = (length >= 1) && (escapingNotNeeded(ch[start]));
/*  766:1280 */       if ((writeCDataBrackets) && (!this.m_cdataTagOpen))
/*  767:     */       {
/*  768:1282 */         this.m_writer.write("<![CDATA[");
/*  769:1283 */         this.m_cdataTagOpen = true;
/*  770:     */       }
/*  771:1287 */       if (isEscapingDisabled()) {
/*  772:1289 */         charactersRaw(ch, start, length);
/*  773:     */       } else {
/*  774:1292 */         writeNormalizedChars(ch, start, length, true, this.m_lineSepUse);
/*  775:     */       }
/*  776:1298 */       if (writeCDataBrackets) {
/*  777:1305 */         if (ch[(start + length - 1)] == ']') {
/*  778:1306 */           closeCDATA();
/*  779:     */         }
/*  780:     */       }
/*  781:1310 */       if (this.m_tracer != null) {
/*  782:1311 */         super.fireCDATAEvent(ch, old_start, length);
/*  783:     */       }
/*  784:     */     }
/*  785:     */     catch (IOException ioe)
/*  786:     */     {
/*  787:1315 */       throw new SAXException(Utils.messages.createMessage("ER_OIERROR", null), ioe);
/*  788:     */     }
/*  789:     */   }
/*  790:     */   
/*  791:     */   private boolean isEscapingDisabled()
/*  792:     */   {
/*  793:1331 */     return this.m_disableOutputEscapingStates.peekOrFalse();
/*  794:     */   }
/*  795:     */   
/*  796:     */   protected void charactersRaw(char[] ch, int start, int length)
/*  797:     */     throws SAXException
/*  798:     */   {
/*  799:1348 */     if (this.m_inEntityRef) {
/*  800:1349 */       return;
/*  801:     */     }
/*  802:     */     try
/*  803:     */     {
/*  804:1352 */       if (this.m_elemContext.m_startTagOpen)
/*  805:     */       {
/*  806:1354 */         closeStartTag();
/*  807:1355 */         this.m_elemContext.m_startTagOpen = false;
/*  808:     */       }
/*  809:1358 */       this.m_ispreserve = true;
/*  810:     */       
/*  811:1360 */       this.m_writer.write(ch, start, length);
/*  812:     */     }
/*  813:     */     catch (IOException e)
/*  814:     */     {
/*  815:1364 */       throw new SAXException(e);
/*  816:     */     }
/*  817:     */   }
/*  818:     */   
/*  819:     */   public void characters(char[] chars, int start, int length)
/*  820:     */     throws SAXException
/*  821:     */   {
/*  822:1403 */     if ((length == 0) || ((this.m_inEntityRef) && (!this.m_expandDTDEntities))) {
/*  823:1404 */       return;
/*  824:     */     }
/*  825:1406 */     this.m_docIsEmpty = false;
/*  826:1408 */     if (this.m_elemContext.m_startTagOpen)
/*  827:     */     {
/*  828:1410 */       closeStartTag();
/*  829:1411 */       this.m_elemContext.m_startTagOpen = false;
/*  830:     */     }
/*  831:1413 */     else if (this.m_needToCallStartDocument)
/*  832:     */     {
/*  833:1415 */       startDocumentInternal();
/*  834:     */     }
/*  835:1418 */     if ((this.m_cdataStartCalled) || (this.m_elemContext.m_isCdataSection))
/*  836:     */     {
/*  837:1423 */       cdata(chars, start, length);
/*  838:     */       
/*  839:1425 */       return;
/*  840:     */     }
/*  841:1428 */     if (this.m_cdataTagOpen) {
/*  842:1429 */       closeCDATA();
/*  843:     */     }
/*  844:1431 */     if ((this.m_disableOutputEscapingStates.peekOrFalse()) || (!this.m_escaping))
/*  845:     */     {
/*  846:1433 */       charactersRaw(chars, start, length);
/*  847:1436 */       if (this.m_tracer != null) {
/*  848:1437 */         super.fireCharEvent(chars, start, length);
/*  849:     */       }
/*  850:1439 */       return;
/*  851:     */     }
/*  852:1442 */     if (this.m_elemContext.m_startTagOpen)
/*  853:     */     {
/*  854:1444 */       closeStartTag();
/*  855:1445 */       this.m_elemContext.m_startTagOpen = false;
/*  856:     */     }
/*  857:     */     try
/*  858:     */     {
/*  859:1457 */       int end = start + length;
/*  860:1458 */       int lastDirtyCharProcessed = start - 1;
/*  861:     */       
/*  862:1460 */       Writer writer = this.m_writer;
/*  863:1461 */       boolean isAllWhitespace = true;
/*  864:     */       
/*  865:     */ 
/*  866:1464 */       int i = start;
/*  867:1465 */       while ((i < end) && (isAllWhitespace))
/*  868:     */       {
/*  869:1466 */         char ch1 = chars[i];
/*  870:1468 */         if (this.m_charInfo.shouldMapTextChar(ch1))
/*  871:     */         {
/*  872:1473 */           writeOutCleanChars(chars, i, lastDirtyCharProcessed);
/*  873:1474 */           String outputStringForChar = this.m_charInfo.getOutputStringForChar(ch1);
/*  874:     */           
/*  875:1476 */           writer.write(outputStringForChar);
/*  876:     */           
/*  877:     */ 
/*  878:1479 */           isAllWhitespace = false;
/*  879:1480 */           lastDirtyCharProcessed = i;
/*  880:     */           
/*  881:1482 */           i++;
/*  882:     */         }
/*  883:     */         else
/*  884:     */         {
/*  885:1485 */           switch (ch1)
/*  886:     */           {
/*  887:     */           case ' ': 
/*  888:1489 */             i++;
/*  889:1490 */             break;
/*  890:     */           case '\n': 
/*  891:1492 */             lastDirtyCharProcessed = processLineFeed(chars, i, lastDirtyCharProcessed, writer);
/*  892:     */             
/*  893:1494 */             i++;
/*  894:1495 */             break;
/*  895:     */           case '\r': 
/*  896:1497 */             writeOutCleanChars(chars, i, lastDirtyCharProcessed);
/*  897:1498 */             writer.write("&#13;");
/*  898:1499 */             lastDirtyCharProcessed = i;
/*  899:1500 */             i++;
/*  900:1501 */             break;
/*  901:     */           case '\t': 
/*  902:1504 */             i++;
/*  903:1505 */             break;
/*  904:     */           default: 
/*  905:1510 */             isAllWhitespace = false;
/*  906:     */           }
/*  907:     */         }
/*  908:     */       }
/*  909:1519 */       if ((i < end) || (!isAllWhitespace)) {
/*  910:1520 */         this.m_ispreserve = true;
/*  911:     */       }
/*  912:1523 */       for (; i < end; i++)
/*  913:     */       {
/*  914:1525 */         char ch = chars[i];
/*  915:1527 */         if (this.m_charInfo.shouldMapTextChar(ch))
/*  916:     */         {
/*  917:1531 */           writeOutCleanChars(chars, i, lastDirtyCharProcessed);
/*  918:1532 */           String outputStringForChar = this.m_charInfo.getOutputStringForChar(ch);
/*  919:1533 */           writer.write(outputStringForChar);
/*  920:1534 */           lastDirtyCharProcessed = i;
/*  921:     */         }
/*  922:1537 */         else if (ch <= '\037')
/*  923:     */         {
/*  924:1553 */           switch (ch)
/*  925:     */           {
/*  926:     */           case '\t': 
/*  927:     */             break;
/*  928:     */           case '\n': 
/*  929:1559 */             lastDirtyCharProcessed = processLineFeed(chars, i, lastDirtyCharProcessed, writer);
/*  930:1560 */             break;
/*  931:     */           case '\r': 
/*  932:1562 */             writeOutCleanChars(chars, i, lastDirtyCharProcessed);
/*  933:1563 */             writer.write("&#13;");
/*  934:1564 */             lastDirtyCharProcessed = i;
/*  935:     */             
/*  936:1566 */             break;
/*  937:     */           case '\013': 
/*  938:     */           case '\f': 
/*  939:     */           default: 
/*  940:1568 */             writeOutCleanChars(chars, i, lastDirtyCharProcessed);
/*  941:1569 */             writer.write("&#");
/*  942:1570 */             writer.write(Integer.toString(ch));
/*  943:1571 */             writer.write(59);
/*  944:1572 */             lastDirtyCharProcessed = i;
/*  945:1573 */             break;
/*  946:     */           }
/*  947:     */         }
/*  948:1577 */         else if (ch >= '')
/*  949:     */         {
/*  950:1583 */           if (ch <= '')
/*  951:     */           {
/*  952:1586 */             writeOutCleanChars(chars, i, lastDirtyCharProcessed);
/*  953:1587 */             writer.write("&#");
/*  954:1588 */             writer.write(Integer.toString(ch));
/*  955:1589 */             writer.write(59);
/*  956:1590 */             lastDirtyCharProcessed = i;
/*  957:     */           }
/*  958:1592 */           else if (ch == ' ')
/*  959:     */           {
/*  960:1594 */             writeOutCleanChars(chars, i, lastDirtyCharProcessed);
/*  961:1595 */             writer.write("&#8232;");
/*  962:1596 */             lastDirtyCharProcessed = i;
/*  963:     */           }
/*  964:1598 */           else if (!this.m_encodingInfo.isInEncoding(ch))
/*  965:     */           {
/*  966:1609 */             writeOutCleanChars(chars, i, lastDirtyCharProcessed);
/*  967:1610 */             writer.write("&#");
/*  968:1611 */             writer.write(Integer.toString(ch));
/*  969:1612 */             writer.write(59);
/*  970:1613 */             lastDirtyCharProcessed = i;
/*  971:     */           }
/*  972:     */         }
/*  973:     */       }
/*  974:1620 */       int startClean = lastDirtyCharProcessed + 1;
/*  975:1621 */       if (i > startClean)
/*  976:     */       {
/*  977:1623 */         int lengthClean = i - startClean;
/*  978:1624 */         this.m_writer.write(chars, startClean, lengthClean);
/*  979:     */       }
/*  980:1628 */       this.m_isprevtext = true;
/*  981:     */     }
/*  982:     */     catch (IOException e)
/*  983:     */     {
/*  984:1632 */       throw new SAXException(e);
/*  985:     */     }
/*  986:1636 */     if (this.m_tracer != null) {
/*  987:1637 */       super.fireCharEvent(chars, start, length);
/*  988:     */     }
/*  989:     */   }
/*  990:     */   
/*  991:     */   private int processLineFeed(char[] chars, int i, int lastProcessed, Writer writer)
/*  992:     */     throws IOException
/*  993:     */   {
/*  994:1641 */     if ((this.m_lineSepUse) && ((this.m_lineSepLen != 1) || (this.m_lineSep[0] != '\n')))
/*  995:     */     {
/*  996:1648 */       writeOutCleanChars(chars, i, lastProcessed);
/*  997:1649 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/*  998:1650 */       lastProcessed = i;
/*  999:     */     }
/* 1000:1652 */     return lastProcessed;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   private void writeOutCleanChars(char[] chars, int i, int lastProcessed)
/* 1004:     */     throws IOException
/* 1005:     */   {
/* 1006:1657 */     int startClean = lastProcessed + 1;
/* 1007:1658 */     if (startClean < i)
/* 1008:     */     {
/* 1009:1660 */       int lengthClean = i - startClean;
/* 1010:1661 */       this.m_writer.write(chars, startClean, lengthClean);
/* 1011:     */     }
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   private static boolean isCharacterInC0orC1Range(char ch)
/* 1015:     */   {
/* 1016:1676 */     if ((ch == '\t') || (ch == '\n') || (ch == '\r')) {
/* 1017:1677 */       return false;
/* 1018:     */     }
/* 1019:1679 */     return ((ch >= '') && (ch <= '')) || ((ch >= '\001') && (ch <= '\037'));
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   private static boolean isNELorLSEPCharacter(char ch)
/* 1023:     */   {
/* 1024:1691 */     return (ch == '') || (ch == ' ');
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   private int processDirty(char[] chars, int end, int i, char ch, int lastDirty, boolean fromTextNode)
/* 1028:     */     throws IOException
/* 1029:     */   {
/* 1030:1714 */     int startClean = lastDirty + 1;
/* 1031:1717 */     if (i > startClean)
/* 1032:     */     {
/* 1033:1719 */       int lengthClean = i - startClean;
/* 1034:1720 */       this.m_writer.write(chars, startClean, lengthClean);
/* 1035:     */     }
/* 1036:1724 */     if (('\n' == ch) && (fromTextNode))
/* 1037:     */     {
/* 1038:1726 */       this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 1039:     */     }
/* 1040:     */     else
/* 1041:     */     {
/* 1042:1730 */       startClean = accumDefaultEscape(this.m_writer, ch, i, chars, end, fromTextNode, false);
/* 1043:     */       
/* 1044:     */ 
/* 1045:     */ 
/* 1046:     */ 
/* 1047:     */ 
/* 1048:     */ 
/* 1049:     */ 
/* 1050:     */ 
/* 1051:1739 */       i = startClean - 1;
/* 1052:     */     }
/* 1053:1743 */     return i;
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public void characters(String s)
/* 1057:     */     throws SAXException
/* 1058:     */   {
/* 1059:1755 */     if ((this.m_inEntityRef) && (!this.m_expandDTDEntities)) {
/* 1060:1756 */       return;
/* 1061:     */     }
/* 1062:1757 */     int length = s.length();
/* 1063:1758 */     if (length > this.m_charsBuff.length) {
/* 1064:1760 */       this.m_charsBuff = new char[length * 2 + 1];
/* 1065:     */     }
/* 1066:1762 */     s.getChars(0, length, this.m_charsBuff, 0);
/* 1067:1763 */     characters(this.m_charsBuff, 0, length);
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   private int accumDefaultEscape(Writer writer, char ch, int i, char[] chars, int len, boolean fromTextNode, boolean escLF)
/* 1071:     */     throws IOException
/* 1072:     */   {
/* 1073:1794 */     int pos = accumDefaultEntity(writer, ch, i, chars, len, fromTextNode, escLF);
/* 1074:1796 */     if (i == pos) {
/* 1075:1798 */       if (Encodings.isHighUTF16Surrogate(ch))
/* 1076:     */       {
/* 1077:1804 */         int codePoint = 0;
/* 1078:1806 */         if (i + 1 >= len) {
/* 1079:1808 */           throw new IOException(Utils.messages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[] { Integer.toHexString(ch) }));
/* 1080:     */         }
/* 1081:1818 */         char next = chars[(++i)];
/* 1082:1820 */         if (!Encodings.isLowUTF16Surrogate(next)) {
/* 1083:1821 */           throw new IOException(Utils.messages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[] { Integer.toHexString(ch) + " " + Integer.toHexString(next) }));
/* 1084:     */         }
/* 1085:1832 */         codePoint = Encodings.toCodePoint(ch, next);
/* 1086:     */         
/* 1087:     */ 
/* 1088:1835 */         writer.write("&#");
/* 1089:1836 */         writer.write(Integer.toString(codePoint));
/* 1090:1837 */         writer.write(59);
/* 1091:1838 */         pos += 2;
/* 1092:     */       }
/* 1093:     */       else
/* 1094:     */       {
/* 1095:1847 */         if ((isCharacterInC0orC1Range(ch)) || (isNELorLSEPCharacter(ch)))
/* 1096:     */         {
/* 1097:1849 */           writer.write("&#");
/* 1098:1850 */           writer.write(Integer.toString(ch));
/* 1099:1851 */           writer.write(59);
/* 1100:     */         }
/* 1101:1853 */         else if (((!escapingNotNeeded(ch)) || ((fromTextNode) && (this.m_charInfo.shouldMapTextChar(ch))) || ((!fromTextNode) && (this.m_charInfo.shouldMapAttrChar(ch)))) && (this.m_elemContext.m_currentElemDepth > 0))
/* 1102:     */         {
/* 1103:1858 */           writer.write("&#");
/* 1104:1859 */           writer.write(Integer.toString(ch));
/* 1105:1860 */           writer.write(59);
/* 1106:     */         }
/* 1107:     */         else
/* 1108:     */         {
/* 1109:1864 */           writer.write(ch);
/* 1110:     */         }
/* 1111:1866 */         pos++;
/* 1112:     */       }
/* 1113:     */     }
/* 1114:1870 */     return pos;
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public void startElement(String namespaceURI, String localName, String name, Attributes atts)
/* 1118:     */     throws SAXException
/* 1119:     */   {
/* 1120:1902 */     if (this.m_inEntityRef) {
/* 1121:1903 */       return;
/* 1122:     */     }
/* 1123:1905 */     if (this.m_needToCallStartDocument)
/* 1124:     */     {
/* 1125:1907 */       startDocumentInternal();
/* 1126:1908 */       this.m_needToCallStartDocument = false;
/* 1127:1909 */       this.m_docIsEmpty = false;
/* 1128:     */     }
/* 1129:1911 */     else if (this.m_cdataTagOpen)
/* 1130:     */     {
/* 1131:1912 */       closeCDATA();
/* 1132:     */     }
/* 1133:     */     try
/* 1134:     */     {
/* 1135:1915 */       if (this.m_needToOutputDocTypeDecl)
/* 1136:     */       {
/* 1137:1916 */         if (null != getDoctypeSystem()) {
/* 1138:1917 */           outputDocTypeDecl(name, true);
/* 1139:     */         }
/* 1140:1919 */         this.m_needToOutputDocTypeDecl = false;
/* 1141:     */       }
/* 1142:1925 */       if (this.m_elemContext.m_startTagOpen)
/* 1143:     */       {
/* 1144:1927 */         closeStartTag();
/* 1145:1928 */         this.m_elemContext.m_startTagOpen = false;
/* 1146:     */       }
/* 1147:1931 */       if (namespaceURI != null) {
/* 1148:1932 */         ensurePrefixIsDeclared(namespaceURI, name);
/* 1149:     */       }
/* 1150:1934 */       this.m_ispreserve = false;
/* 1151:1936 */       if ((shouldIndent()) && (this.m_startNewLine)) {
/* 1152:1938 */         indent();
/* 1153:     */       }
/* 1154:1941 */       this.m_startNewLine = true;
/* 1155:     */       
/* 1156:1943 */       Writer writer = this.m_writer;
/* 1157:1944 */       writer.write(60);
/* 1158:1945 */       writer.write(name);
/* 1159:     */     }
/* 1160:     */     catch (IOException e)
/* 1161:     */     {
/* 1162:1949 */       throw new SAXException(e);
/* 1163:     */     }
/* 1164:1953 */     if (atts != null) {
/* 1165:1954 */       addAttributes(atts);
/* 1166:     */     }
/* 1167:1956 */     this.m_elemContext = this.m_elemContext.push(namespaceURI, localName, name);
/* 1168:1957 */     this.m_isprevtext = false;
/* 1169:1959 */     if (this.m_tracer != null) {
/* 1170:1960 */       firePseudoAttributes();
/* 1171:     */     }
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public void startElement(String elementNamespaceURI, String elementLocalName, String elementName)
/* 1175:     */     throws SAXException
/* 1176:     */   {
/* 1177:1990 */     startElement(elementNamespaceURI, elementLocalName, elementName, null);
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   public void startElement(String elementName)
/* 1181:     */     throws SAXException
/* 1182:     */   {
/* 1183:1995 */     startElement(null, null, elementName, null);
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   void outputDocTypeDecl(String name, boolean closeDecl)
/* 1187:     */     throws SAXException
/* 1188:     */   {
/* 1189:2008 */     if (this.m_cdataTagOpen) {
/* 1190:2009 */       closeCDATA();
/* 1191:     */     }
/* 1192:     */     try
/* 1193:     */     {
/* 1194:2012 */       Writer writer = this.m_writer;
/* 1195:2013 */       writer.write("<!DOCTYPE ");
/* 1196:2014 */       writer.write(name);
/* 1197:     */       
/* 1198:2016 */       String doctypePublic = getDoctypePublic();
/* 1199:2017 */       if (null != doctypePublic)
/* 1200:     */       {
/* 1201:2019 */         writer.write(" PUBLIC \"");
/* 1202:2020 */         writer.write(doctypePublic);
/* 1203:2021 */         writer.write(34);
/* 1204:     */       }
/* 1205:2024 */       String doctypeSystem = getDoctypeSystem();
/* 1206:2025 */       if (null != doctypeSystem)
/* 1207:     */       {
/* 1208:2027 */         if (null == doctypePublic) {
/* 1209:2028 */           writer.write(" SYSTEM \"");
/* 1210:     */         } else {
/* 1211:2030 */           writer.write(" \"");
/* 1212:     */         }
/* 1213:2032 */         writer.write(doctypeSystem);
/* 1214:2034 */         if (closeDecl)
/* 1215:     */         {
/* 1216:2036 */           writer.write("\">");
/* 1217:2037 */           writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 1218:2038 */           closeDecl = false;
/* 1219:     */         }
/* 1220:     */         else
/* 1221:     */         {
/* 1222:2041 */           writer.write(34);
/* 1223:     */         }
/* 1224:     */       }
/* 1225:     */     }
/* 1226:     */     catch (IOException e)
/* 1227:     */     {
/* 1228:2046 */       throw new SAXException(e);
/* 1229:     */     }
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public void processAttributes(Writer writer, int nAttrs)
/* 1233:     */     throws IOException, SAXException
/* 1234:     */   {
/* 1235:2070 */     String encoding = getEncoding();
/* 1236:2071 */     for (int i = 0; i < nAttrs; i++)
/* 1237:     */     {
/* 1238:2074 */       String name = this.m_attributes.getQName(i);
/* 1239:2075 */       String value = this.m_attributes.getValue(i);
/* 1240:2076 */       writer.write(32);
/* 1241:2077 */       writer.write(name);
/* 1242:2078 */       writer.write("=\"");
/* 1243:2079 */       writeAttrString(writer, value, encoding);
/* 1244:2080 */       writer.write(34);
/* 1245:     */     }
/* 1246:     */   }
/* 1247:     */   
/* 1248:     */   public void writeAttrString(Writer writer, String string, String encoding)
/* 1249:     */     throws IOException
/* 1250:     */   {
/* 1251:2099 */     int len = string.length();
/* 1252:2100 */     if (len > this.m_attrBuff.length) {
/* 1253:2102 */       this.m_attrBuff = new char[len * 2 + 1];
/* 1254:     */     }
/* 1255:2104 */     string.getChars(0, len, this.m_attrBuff, 0);
/* 1256:2105 */     char[] stringChars = this.m_attrBuff;
/* 1257:2107 */     for (int i = 0; i < len; i++)
/* 1258:     */     {
/* 1259:2109 */       char ch = stringChars[i];
/* 1260:2111 */       if (this.m_charInfo.shouldMapAttrChar(ch))
/* 1261:     */       {
/* 1262:2115 */         accumDefaultEscape(writer, ch, i, stringChars, len, false, true);
/* 1263:     */       }
/* 1264:     */       else
/* 1265:     */       {
/* 1266:2118 */         if (('\000' <= ch) && (ch <= '\037')) {}
/* 1267:2133 */         switch (ch)
/* 1268:     */         {
/* 1269:     */         case '\t': 
/* 1270:2136 */           writer.write("&#9;");
/* 1271:2137 */           break;
/* 1272:     */         case '\n': 
/* 1273:2139 */           writer.write("&#10;");
/* 1274:2140 */           break;
/* 1275:     */         case '\r': 
/* 1276:2142 */           writer.write("&#13;");
/* 1277:2143 */           break;
/* 1278:     */         case '\013': 
/* 1279:     */         case '\f': 
/* 1280:     */         default: 
/* 1281:2145 */           writer.write("&#");
/* 1282:2146 */           writer.write(Integer.toString(ch));
/* 1283:2147 */           writer.write(59);
/* 1284:2148 */           continue;
/* 1285:2152 */           if (ch < '')
/* 1286:     */           {
/* 1287:2155 */             writer.write(ch);
/* 1288:     */           }
/* 1289:2157 */           else if (ch <= '')
/* 1290:     */           {
/* 1291:2160 */             writer.write("&#");
/* 1292:2161 */             writer.write(Integer.toString(ch));
/* 1293:2162 */             writer.write(59);
/* 1294:     */           }
/* 1295:2164 */           else if (ch == ' ')
/* 1296:     */           {
/* 1297:2166 */             writer.write("&#8232;");
/* 1298:     */           }
/* 1299:2168 */           else if (this.m_encodingInfo.isInEncoding(ch))
/* 1300:     */           {
/* 1301:2172 */             writer.write(ch);
/* 1302:     */           }
/* 1303:     */           else
/* 1304:     */           {
/* 1305:2179 */             writer.write("&#");
/* 1306:2180 */             writer.write(Integer.toString(ch));
/* 1307:2181 */             writer.write(59);
/* 1308:     */           }
/* 1309:     */           break;
/* 1310:     */         }
/* 1311:     */       }
/* 1312:     */     }
/* 1313:     */   }
/* 1314:     */   
/* 1315:     */   public void endElement(String namespaceURI, String localName, String name)
/* 1316:     */     throws SAXException
/* 1317:     */   {
/* 1318:2207 */     if (this.m_inEntityRef) {
/* 1319:2208 */       return;
/* 1320:     */     }
/* 1321:2212 */     this.m_prefixMap.popNamespaces(this.m_elemContext.m_currentElemDepth, null);
/* 1322:     */     try
/* 1323:     */     {
/* 1324:2216 */       Writer writer = this.m_writer;
/* 1325:2217 */       if (this.m_elemContext.m_startTagOpen)
/* 1326:     */       {
/* 1327:2219 */         if (this.m_tracer != null) {
/* 1328:2220 */           super.fireStartElem(this.m_elemContext.m_elementName);
/* 1329:     */         }
/* 1330:2221 */         int nAttrs = this.m_attributes.getLength();
/* 1331:2222 */         if (nAttrs > 0)
/* 1332:     */         {
/* 1333:2224 */           processAttributes(this.m_writer, nAttrs);
/* 1334:     */           
/* 1335:2226 */           this.m_attributes.clear();
/* 1336:     */         }
/* 1337:2228 */         if (this.m_spaceBeforeClose) {
/* 1338:2229 */           writer.write(" />");
/* 1339:     */         } else {
/* 1340:2231 */           writer.write("/>");
/* 1341:     */         }
/* 1342:     */       }
/* 1343:     */       else
/* 1344:     */       {
/* 1345:2240 */         if (this.m_cdataTagOpen) {
/* 1346:2241 */           closeCDATA();
/* 1347:     */         }
/* 1348:2243 */         if (shouldIndent()) {
/* 1349:2244 */           indent(this.m_elemContext.m_currentElemDepth - 1);
/* 1350:     */         }
/* 1351:2245 */         writer.write(60);
/* 1352:2246 */         writer.write(47);
/* 1353:2247 */         writer.write(name);
/* 1354:2248 */         writer.write(62);
/* 1355:     */       }
/* 1356:     */     }
/* 1357:     */     catch (IOException e)
/* 1358:     */     {
/* 1359:2253 */       throw new SAXException(e);
/* 1360:     */     }
/* 1361:2256 */     if ((!this.m_elemContext.m_startTagOpen) && (this.m_doIndent)) {
/* 1362:2258 */       this.m_ispreserve = (this.m_preserves.isEmpty() ? false : this.m_preserves.pop());
/* 1363:     */     }
/* 1364:2261 */     this.m_isprevtext = false;
/* 1365:2264 */     if (this.m_tracer != null) {
/* 1366:2265 */       super.fireEndElem(name);
/* 1367:     */     }
/* 1368:2266 */     this.m_elemContext = this.m_elemContext.m_prev;
/* 1369:     */   }
/* 1370:     */   
/* 1371:     */   public void endElement(String name)
/* 1372:     */     throws SAXException
/* 1373:     */   {
/* 1374:2277 */     endElement(null, null, name);
/* 1375:     */   }
/* 1376:     */   
/* 1377:     */   public void startPrefixMapping(String prefix, String uri)
/* 1378:     */     throws SAXException
/* 1379:     */   {
/* 1380:2299 */     startPrefixMapping(prefix, uri, true);
/* 1381:     */   }
/* 1382:     */   
/* 1383:     */   public boolean startPrefixMapping(String prefix, String uri, boolean shouldFlush)
/* 1384:     */     throws SAXException
/* 1385:     */   {
/* 1386:     */     int pushDepth;
/* 1387:2338 */     if (shouldFlush)
/* 1388:     */     {
/* 1389:2340 */       flushPending();
/* 1390:     */       
/* 1391:2342 */       pushDepth = this.m_elemContext.m_currentElemDepth + 1;
/* 1392:     */     }
/* 1393:     */     else
/* 1394:     */     {
/* 1395:2347 */       pushDepth = this.m_elemContext.m_currentElemDepth;
/* 1396:     */     }
/* 1397:2349 */     boolean pushed = this.m_prefixMap.pushNamespace(prefix, uri, pushDepth);
/* 1398:2351 */     if (pushed)
/* 1399:     */     {
/* 1400:     */       String name;
/* 1401:2359 */       if ("".equals(prefix))
/* 1402:     */       {
/* 1403:2361 */         name = "xmlns";
/* 1404:2362 */         addAttributeAlways("http://www.w3.org/2000/xmlns/", name, name, "CDATA", uri, false);
/* 1405:     */       }
/* 1406:2366 */       else if (!"".equals(uri))
/* 1407:     */       {
/* 1408:2369 */         name = "xmlns:" + prefix;
/* 1409:     */         
/* 1410:     */ 
/* 1411:     */ 
/* 1412:     */ 
/* 1413:     */ 
/* 1414:2375 */         addAttributeAlways("http://www.w3.org/2000/xmlns/", prefix, name, "CDATA", uri, false);
/* 1415:     */       }
/* 1416:     */     }
/* 1417:2379 */     return pushed;
/* 1418:     */   }
/* 1419:     */   
/* 1420:     */   public void comment(char[] ch, int start, int length)
/* 1421:     */     throws SAXException
/* 1422:     */   {
/* 1423:2395 */     int start_old = start;
/* 1424:2396 */     if (this.m_inEntityRef) {
/* 1425:2397 */       return;
/* 1426:     */     }
/* 1427:2398 */     if (this.m_elemContext.m_startTagOpen)
/* 1428:     */     {
/* 1429:2400 */       closeStartTag();
/* 1430:2401 */       this.m_elemContext.m_startTagOpen = false;
/* 1431:     */     }
/* 1432:2403 */     else if (this.m_needToCallStartDocument)
/* 1433:     */     {
/* 1434:2405 */       startDocumentInternal();
/* 1435:2406 */       this.m_needToCallStartDocument = false;
/* 1436:     */     }
/* 1437:     */     try
/* 1438:     */     {
/* 1439:2411 */       int limit = start + length;
/* 1440:2412 */       boolean wasDash = false;
/* 1441:2413 */       if (this.m_cdataTagOpen) {
/* 1442:2414 */         closeCDATA();
/* 1443:     */       }
/* 1444:2416 */       if (shouldIndent()) {
/* 1445:2417 */         indent();
/* 1446:     */       }
/* 1447:2419 */       Writer writer = this.m_writer;
/* 1448:2420 */       writer.write("<!--");
/* 1449:2422 */       for (int i = start; i < limit; i++)
/* 1450:     */       {
/* 1451:2424 */         if ((wasDash) && (ch[i] == '-'))
/* 1452:     */         {
/* 1453:2426 */           writer.write(ch, start, i - start);
/* 1454:2427 */           writer.write(" -");
/* 1455:2428 */           start = i + 1;
/* 1456:     */         }
/* 1457:2430 */         wasDash = ch[i] == '-';
/* 1458:     */       }
/* 1459:2434 */       if (length > 0)
/* 1460:     */       {
/* 1461:2437 */         int remainingChars = limit - start;
/* 1462:2438 */         if (remainingChars > 0) {
/* 1463:2439 */           writer.write(ch, start, remainingChars);
/* 1464:     */         }
/* 1465:2441 */         if (ch[(limit - 1)] == '-') {
/* 1466:2442 */           writer.write(32);
/* 1467:     */         }
/* 1468:     */       }
/* 1469:2444 */       writer.write("-->");
/* 1470:     */     }
/* 1471:     */     catch (IOException e)
/* 1472:     */     {
/* 1473:2448 */       throw new SAXException(e);
/* 1474:     */     }
/* 1475:2460 */     this.m_startNewLine = true;
/* 1476:2462 */     if (this.m_tracer != null) {
/* 1477:2463 */       super.fireCommentEvent(ch, start_old, length);
/* 1478:     */     }
/* 1479:     */   }
/* 1480:     */   
/* 1481:     */   public void endCDATA()
/* 1482:     */     throws SAXException
/* 1483:     */   {
/* 1484:2474 */     if (this.m_cdataTagOpen) {
/* 1485:2475 */       closeCDATA();
/* 1486:     */     }
/* 1487:2476 */     this.m_cdataStartCalled = false;
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   public void endDTD()
/* 1491:     */     throws SAXException
/* 1492:     */   {
/* 1493:     */     try
/* 1494:     */     {
/* 1495:2488 */       if (this.m_needToOutputDocTypeDecl)
/* 1496:     */       {
/* 1497:2490 */         outputDocTypeDecl(this.m_elemContext.m_elementName, false);
/* 1498:2491 */         this.m_needToOutputDocTypeDecl = false;
/* 1499:     */       }
/* 1500:2493 */       Writer writer = this.m_writer;
/* 1501:2494 */       if (!this.m_inDoctype) {
/* 1502:2495 */         writer.write("]>");
/* 1503:     */       } else {
/* 1504:2498 */         writer.write(62);
/* 1505:     */       }
/* 1506:2501 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 1507:     */     }
/* 1508:     */     catch (IOException e)
/* 1509:     */     {
/* 1510:2505 */       throw new SAXException(e);
/* 1511:     */     }
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   public void ignorableWhitespace(char[] ch, int start, int length)
/* 1515:     */     throws SAXException
/* 1516:     */   {
/* 1517:2540 */     if (0 == length) {
/* 1518:2541 */       return;
/* 1519:     */     }
/* 1520:2542 */     characters(ch, start, length);
/* 1521:     */   }
/* 1522:     */   
/* 1523:     */   public void startCDATA()
/* 1524:     */     throws SAXException
/* 1525:     */   {
/* 1526:2568 */     this.m_cdataStartCalled = true;
/* 1527:     */   }
/* 1528:     */   
/* 1529:     */   public void startEntity(String name)
/* 1530:     */     throws SAXException
/* 1531:     */   {
/* 1532:2588 */     if (name.equals("[dtd]")) {
/* 1533:2589 */       this.m_inExternalDTD = true;
/* 1534:     */     }
/* 1535:2591 */     if ((!this.m_expandDTDEntities) && (!this.m_inExternalDTD))
/* 1536:     */     {
/* 1537:2596 */       startNonEscaping();
/* 1538:2597 */       characters("&" + name + ';');
/* 1539:2598 */       endNonEscaping();
/* 1540:     */     }
/* 1541:2601 */     this.m_inEntityRef = true;
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   protected void closeStartTag()
/* 1545:     */     throws SAXException
/* 1546:     */   {
/* 1547:2613 */     if (this.m_elemContext.m_startTagOpen)
/* 1548:     */     {
/* 1549:     */       try
/* 1550:     */       {
/* 1551:2618 */         if (this.m_tracer != null) {
/* 1552:2619 */           super.fireStartElem(this.m_elemContext.m_elementName);
/* 1553:     */         }
/* 1554:2620 */         int nAttrs = this.m_attributes.getLength();
/* 1555:2621 */         if (nAttrs > 0)
/* 1556:     */         {
/* 1557:2623 */           processAttributes(this.m_writer, nAttrs);
/* 1558:     */           
/* 1559:2625 */           this.m_attributes.clear();
/* 1560:     */         }
/* 1561:2627 */         this.m_writer.write(62);
/* 1562:     */       }
/* 1563:     */       catch (IOException e)
/* 1564:     */       {
/* 1565:2631 */         throw new SAXException(e);
/* 1566:     */       }
/* 1567:2638 */       if (this.m_CdataElems != null) {
/* 1568:2639 */         this.m_elemContext.m_isCdataSection = isCdataSection();
/* 1569:     */       }
/* 1570:2641 */       if (this.m_doIndent)
/* 1571:     */       {
/* 1572:2643 */         this.m_isprevtext = false;
/* 1573:2644 */         this.m_preserves.push(this.m_ispreserve);
/* 1574:     */       }
/* 1575:     */     }
/* 1576:     */   }
/* 1577:     */   
/* 1578:     */   public void startDTD(String name, String publicId, String systemId)
/* 1579:     */     throws SAXException
/* 1580:     */   {
/* 1581:2669 */     setDoctypeSystem(systemId);
/* 1582:2670 */     setDoctypePublic(publicId);
/* 1583:     */     
/* 1584:2672 */     this.m_elemContext.m_elementName = name;
/* 1585:2673 */     this.m_inDoctype = true;
/* 1586:     */   }
/* 1587:     */   
/* 1588:     */   public int getIndentAmount()
/* 1589:     */   {
/* 1590:2682 */     return this.m_indentAmount;
/* 1591:     */   }
/* 1592:     */   
/* 1593:     */   public void setIndentAmount(int m_indentAmount)
/* 1594:     */   {
/* 1595:2692 */     this.m_indentAmount = m_indentAmount;
/* 1596:     */   }
/* 1597:     */   
/* 1598:     */   protected boolean shouldIndent()
/* 1599:     */   {
/* 1600:2703 */     return (this.m_doIndent) && (!this.m_ispreserve) && (!this.m_isprevtext) && (this.m_elemContext.m_currentElemDepth > 0);
/* 1601:     */   }
/* 1602:     */   
/* 1603:     */   private void setCdataSectionElements(String key, Properties props)
/* 1604:     */   {
/* 1605:2725 */     String s = props.getProperty(key);
/* 1606:2727 */     if (null != s)
/* 1607:     */     {
/* 1608:2730 */       Vector v = new Vector();
/* 1609:2731 */       int l = s.length();
/* 1610:2732 */       boolean inCurly = false;
/* 1611:2733 */       StringBuffer buf = new StringBuffer();
/* 1612:2738 */       for (int i = 0; i < l; i++)
/* 1613:     */       {
/* 1614:2740 */         char c = s.charAt(i);
/* 1615:2742 */         if (Character.isWhitespace(c))
/* 1616:     */         {
/* 1617:2744 */           if (!inCurly)
/* 1618:     */           {
/* 1619:2746 */             if (buf.length() <= 0) {
/* 1620:     */               continue;
/* 1621:     */             }
/* 1622:2748 */             addCdataSectionElement(buf.toString(), v);
/* 1623:2749 */             buf.setLength(0); continue;
/* 1624:     */           }
/* 1625:     */         }
/* 1626:2754 */         else if ('{' == c) {
/* 1627:2755 */           inCurly = true;
/* 1628:2756 */         } else if ('}' == c) {
/* 1629:2757 */           inCurly = false;
/* 1630:     */         }
/* 1631:2759 */         buf.append(c);
/* 1632:     */       }
/* 1633:2762 */       if (buf.length() > 0)
/* 1634:     */       {
/* 1635:2764 */         addCdataSectionElement(buf.toString(), v);
/* 1636:2765 */         buf.setLength(0);
/* 1637:     */       }
/* 1638:2768 */       setCdataSectionElements(v);
/* 1639:     */     }
/* 1640:     */   }
/* 1641:     */   
/* 1642:     */   private void addCdataSectionElement(String URI_and_localName, Vector v)
/* 1643:     */   {
/* 1644:2783 */     StringTokenizer tokenizer = new StringTokenizer(URI_and_localName, "{}", false);
/* 1645:     */     
/* 1646:2785 */     String s1 = tokenizer.nextToken();
/* 1647:2786 */     String s2 = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
/* 1648:2788 */     if (null == s2)
/* 1649:     */     {
/* 1650:2791 */       v.addElement(null);
/* 1651:2792 */       v.addElement(s1);
/* 1652:     */     }
/* 1653:     */     else
/* 1654:     */     {
/* 1655:2797 */       v.addElement(s1);
/* 1656:2798 */       v.addElement(s2);
/* 1657:     */     }
/* 1658:     */   }
/* 1659:     */   
/* 1660:     */   public void setCdataSectionElements(Vector URI_and_localNames)
/* 1661:     */   {
/* 1662:2812 */     if (URI_and_localNames != null)
/* 1663:     */     {
/* 1664:2814 */       int len = URI_and_localNames.size() - 1;
/* 1665:2815 */       if (len > 0)
/* 1666:     */       {
/* 1667:2817 */         StringBuffer sb = new StringBuffer();
/* 1668:2818 */         for (int i = 0; i < len; i += 2)
/* 1669:     */         {
/* 1670:2821 */           if (i != 0) {
/* 1671:2822 */             sb.append(' ');
/* 1672:     */           }
/* 1673:2823 */           String uri = (String)URI_and_localNames.elementAt(i);
/* 1674:2824 */           String localName = (String)URI_and_localNames.elementAt(i + 1);
/* 1675:2826 */           if (uri != null)
/* 1676:     */           {
/* 1677:2829 */             sb.append('{');
/* 1678:2830 */             sb.append(uri);
/* 1679:2831 */             sb.append('}');
/* 1680:     */           }
/* 1681:2833 */           sb.append(localName);
/* 1682:     */         }
/* 1683:2835 */         this.m_StringOfCDATASections = sb.toString();
/* 1684:     */       }
/* 1685:     */     }
/* 1686:2838 */     initCdataElems(this.m_StringOfCDATASections);
/* 1687:     */   }
/* 1688:     */   
/* 1689:     */   protected String ensureAttributesNamespaceIsDeclared(String ns, String localName, String rawName)
/* 1690:     */     throws SAXException
/* 1691:     */   {
/* 1692:2857 */     if ((ns != null) && (ns.length() > 0))
/* 1693:     */     {
/* 1694:2861 */       int index = 0;
/* 1695:2862 */       String prefixFromRawName = (index = rawName.indexOf(":")) < 0 ? "" : rawName.substring(0, index);
/* 1696:2867 */       if (index > 0)
/* 1697:     */       {
/* 1698:2870 */         String uri = this.m_prefixMap.lookupNamespace(prefixFromRawName);
/* 1699:2871 */         if ((uri != null) && (uri.equals(ns))) {
/* 1700:2875 */           return null;
/* 1701:     */         }
/* 1702:2881 */         startPrefixMapping(prefixFromRawName, ns, false);
/* 1703:2882 */         addAttribute("http://www.w3.org/2000/xmlns/", prefixFromRawName, "xmlns:" + prefixFromRawName, "CDATA", ns, false);
/* 1704:     */         
/* 1705:     */ 
/* 1706:     */ 
/* 1707:     */ 
/* 1708:     */ 
/* 1709:2888 */         return prefixFromRawName;
/* 1710:     */       }
/* 1711:2895 */       String prefix = this.m_prefixMap.lookupPrefix(ns);
/* 1712:2896 */       if (prefix == null)
/* 1713:     */       {
/* 1714:2900 */         prefix = this.m_prefixMap.generateNextPrefix();
/* 1715:2901 */         startPrefixMapping(prefix, ns, false);
/* 1716:2902 */         addAttribute("http://www.w3.org/2000/xmlns/", prefix, "xmlns:" + prefix, "CDATA", ns, false);
/* 1717:     */       }
/* 1718:2910 */       return prefix;
/* 1719:     */     }
/* 1720:2914 */     return null;
/* 1721:     */   }
/* 1722:     */   
/* 1723:     */   void ensurePrefixIsDeclared(String ns, String rawName)
/* 1724:     */     throws SAXException
/* 1725:     */   {
/* 1726:2921 */     if ((ns != null) && (ns.length() > 0))
/* 1727:     */     {
/* 1728:     */       int index;
/* 1729:2924 */       boolean no_prefix = (index = rawName.indexOf(":")) < 0;
/* 1730:2925 */       String prefix = no_prefix ? "" : rawName.substring(0, index);
/* 1731:2927 */       if (null != prefix)
/* 1732:     */       {
/* 1733:2929 */         String foundURI = this.m_prefixMap.lookupNamespace(prefix);
/* 1734:2931 */         if ((null == foundURI) || (!foundURI.equals(ns)))
/* 1735:     */         {
/* 1736:2933 */           startPrefixMapping(prefix, ns);
/* 1737:     */           
/* 1738:     */ 
/* 1739:     */ 
/* 1740:     */ 
/* 1741:2938 */           addAttributeAlways("http://www.w3.org/2000/xmlns/", no_prefix ? "xmlns" : prefix, "xmlns:" + prefix, "CDATA", ns, false);
/* 1742:     */         }
/* 1743:     */       }
/* 1744:     */     }
/* 1745:     */   }
/* 1746:     */   
/* 1747:     */   public void flushPending()
/* 1748:     */     throws SAXException
/* 1749:     */   {
/* 1750:2957 */     if (this.m_needToCallStartDocument)
/* 1751:     */     {
/* 1752:2959 */       startDocumentInternal();
/* 1753:2960 */       this.m_needToCallStartDocument = false;
/* 1754:     */     }
/* 1755:2962 */     if (this.m_elemContext.m_startTagOpen)
/* 1756:     */     {
/* 1757:2964 */       closeStartTag();
/* 1758:2965 */       this.m_elemContext.m_startTagOpen = false;
/* 1759:     */     }
/* 1760:2968 */     if (this.m_cdataTagOpen)
/* 1761:     */     {
/* 1762:2970 */       closeCDATA();
/* 1763:2971 */       this.m_cdataTagOpen = false;
/* 1764:     */     }
/* 1765:2973 */     if (this.m_writer != null) {
/* 1766:     */       try
/* 1767:     */       {
/* 1768:2975 */         this.m_writer.flush();
/* 1769:     */       }
/* 1770:     */       catch (IOException e) {}
/* 1771:     */     }
/* 1772:     */   }
/* 1773:     */   
/* 1774:     */   public boolean addAttributeAlways(String uri, String localName, String rawName, String type, String value, boolean xslAttribute)
/* 1775:     */   {
/* 1776:     */     int index;
/* 1777:3019 */     if ((uri == null) || (localName == null) || (uri.length() == 0)) {
/* 1778:3020 */       index = this.m_attributes.getIndex(rawName);
/* 1779:     */     } else {
/* 1780:3022 */       index = this.m_attributes.getIndex(uri, localName);
/* 1781:     */     }
/* 1782:     */     boolean was_added;
/* 1783:3025 */     if (index >= 0)
/* 1784:     */     {
/* 1785:3027 */       String old_value = null;
/* 1786:3028 */       if (this.m_tracer != null)
/* 1787:     */       {
/* 1788:3030 */         old_value = this.m_attributes.getValue(index);
/* 1789:3031 */         if (value.equals(old_value)) {
/* 1790:3032 */           old_value = null;
/* 1791:     */         }
/* 1792:     */       }
/* 1793:3039 */       this.m_attributes.setValue(index, value);
/* 1794:3040 */       was_added = false;
/* 1795:3041 */       if (old_value != null) {
/* 1796:3042 */         firePseudoAttributes();
/* 1797:     */       }
/* 1798:     */     }
/* 1799:     */     else
/* 1800:     */     {
/* 1801:3048 */       if (xslAttribute)
/* 1802:     */       {
/* 1803:3063 */         int colonIndex = rawName.indexOf(':');
/* 1804:3064 */         if (colonIndex > 0)
/* 1805:     */         {
/* 1806:3066 */           String prefix = rawName.substring(0, colonIndex);
/* 1807:3067 */           NamespaceMappings.MappingRecord existing_mapping = this.m_prefixMap.getMappingFromPrefix(prefix);
/* 1808:3072 */           if ((existing_mapping != null) && (existing_mapping.m_declarationDepth == this.m_elemContext.m_currentElemDepth) && (!existing_mapping.m_uri.equals(uri)))
/* 1809:     */           {
/* 1810:3086 */             prefix = this.m_prefixMap.lookupPrefix(uri);
/* 1811:3087 */             if (prefix == null) {
/* 1812:3098 */               prefix = this.m_prefixMap.generateNextPrefix();
/* 1813:     */             }
/* 1814:3101 */             rawName = prefix + ':' + localName;
/* 1815:     */           }
/* 1816:     */         }
/* 1817:     */         try
/* 1818:     */         {
/* 1819:3112 */           prefixUsed = ensureAttributesNamespaceIsDeclared(uri, localName, rawName);
/* 1820:     */         }
/* 1821:     */         catch (SAXException e)
/* 1822:     */         {
/* 1823:     */           String prefixUsed;
/* 1824:3121 */           e.printStackTrace();
/* 1825:     */         }
/* 1826:     */       }
/* 1827:3124 */       this.m_attributes.addAttribute(uri, localName, rawName, type, value);
/* 1828:3125 */       was_added = true;
/* 1829:3126 */       if (this.m_tracer != null) {
/* 1830:3127 */         firePseudoAttributes();
/* 1831:     */       }
/* 1832:     */     }
/* 1833:3129 */     return was_added;
/* 1834:     */   }
/* 1835:     */   
/* 1836:     */   protected void firePseudoAttributes()
/* 1837:     */   {
/* 1838:3140 */     if (this.m_tracer != null) {
/* 1839:     */       try
/* 1840:     */       {
/* 1841:3145 */         this.m_writer.flush();
/* 1842:     */         
/* 1843:     */ 
/* 1844:3148 */         StringBuffer sb = new StringBuffer();
/* 1845:3149 */         int nAttrs = this.m_attributes.getLength();
/* 1846:3150 */         if (nAttrs > 0)
/* 1847:     */         {
/* 1848:3154 */           Writer writer = new WritertoStringBuffer(sb);
/* 1849:     */           
/* 1850:     */ 
/* 1851:3157 */           processAttributes(writer, nAttrs);
/* 1852:     */         }
/* 1853:3162 */         sb.append('>');
/* 1854:     */         
/* 1855:     */ 
/* 1856:     */ 
/* 1857:3166 */         char[] ch = sb.toString().toCharArray();
/* 1858:3167 */         this.m_tracer.fireGenerateEvent(11, ch, 0, ch.length);
/* 1859:     */       }
/* 1860:     */       catch (IOException ioe) {}catch (SAXException se) {}
/* 1861:     */     }
/* 1862:     */   }
/* 1863:     */   
/* 1864:     */   private class WritertoStringBuffer
/* 1865:     */     extends Writer
/* 1866:     */   {
/* 1867:     */     private final StringBuffer m_stringbuf;
/* 1868:     */     
/* 1869:     */     WritertoStringBuffer(StringBuffer sb)
/* 1870:     */     {
/* 1871:3198 */       this.m_stringbuf = sb;
/* 1872:     */     }
/* 1873:     */     
/* 1874:     */     public void write(char[] arg0, int arg1, int arg2)
/* 1875:     */       throws IOException
/* 1876:     */     {
/* 1877:3203 */       this.m_stringbuf.append(arg0, arg1, arg2);
/* 1878:     */     }
/* 1879:     */     
/* 1880:     */     public void flush()
/* 1881:     */       throws IOException
/* 1882:     */     {}
/* 1883:     */     
/* 1884:     */     public void close()
/* 1885:     */       throws IOException
/* 1886:     */     {}
/* 1887:     */     
/* 1888:     */     public void write(int i)
/* 1889:     */     {
/* 1890:3220 */       this.m_stringbuf.append((char)i);
/* 1891:     */     }
/* 1892:     */     
/* 1893:     */     public void write(String s)
/* 1894:     */     {
/* 1895:3225 */       this.m_stringbuf.append(s);
/* 1896:     */     }
/* 1897:     */   }
/* 1898:     */   
/* 1899:     */   public void setTransformer(Transformer transformer)
/* 1900:     */   {
/* 1901:3233 */     super.setTransformer(transformer);
/* 1902:3234 */     if ((this.m_tracer != null) && (!(this.m_writer instanceof SerializerTraceWriter))) {
/* 1903:3236 */       setWriterInternal(new SerializerTraceWriter(this.m_writer, this.m_tracer), false);
/* 1904:     */     }
/* 1905:     */   }
/* 1906:     */   
/* 1907:     */   public boolean reset()
/* 1908:     */   {
/* 1909:3249 */     boolean wasReset = false;
/* 1910:3250 */     if (super.reset())
/* 1911:     */     {
/* 1912:3252 */       resetToStream();
/* 1913:3253 */       wasReset = true;
/* 1914:     */     }
/* 1915:3255 */     return wasReset;
/* 1916:     */   }
/* 1917:     */   
/* 1918:     */   private void resetToStream()
/* 1919:     */   {
/* 1920:3264 */     this.m_cdataStartCalled = false;
/* 1921:     */     
/* 1922:     */ 
/* 1923:     */ 
/* 1924:     */ 
/* 1925:     */ 
/* 1926:     */ 
/* 1927:     */ 
/* 1928:3272 */     this.m_disableOutputEscapingStates.clear();
/* 1929:     */     
/* 1930:     */ 
/* 1931:3275 */     this.m_escaping = true;
/* 1932:     */     
/* 1933:     */ 
/* 1934:3278 */     this.m_expandDTDEntities = true;
/* 1935:3279 */     this.m_inDoctype = false;
/* 1936:3280 */     this.m_ispreserve = false;
/* 1937:3281 */     this.m_isprevtext = false;
/* 1938:3282 */     this.m_isUTF8 = false;
/* 1939:3283 */     this.m_lineSep = s_systemLineSep;
/* 1940:3284 */     this.m_lineSepLen = s_systemLineSep.length;
/* 1941:3285 */     this.m_lineSepUse = true;
/* 1942:     */     
/* 1943:3287 */     this.m_preserves.clear();
/* 1944:3288 */     this.m_shouldFlush = true;
/* 1945:3289 */     this.m_spaceBeforeClose = false;
/* 1946:3290 */     this.m_startNewLine = false;
/* 1947:3291 */     this.m_writer_set_by_user = false;
/* 1948:     */   }
/* 1949:     */   
/* 1950:     */   public void setEncoding(String encoding)
/* 1951:     */   {
/* 1952:3300 */     setOutputProperty("encoding", encoding);
/* 1953:     */   }
/* 1954:     */   
/* 1955:     */   static final class BoolStack
/* 1956:     */   {
/* 1957:     */     private boolean[] m_values;
/* 1958:     */     private int m_allocatedSize;
/* 1959:     */     private int m_index;
/* 1960:     */     
/* 1961:     */     public BoolStack()
/* 1962:     */     {
/* 1963:3331 */       this(32);
/* 1964:     */     }
/* 1965:     */     
/* 1966:     */     public BoolStack(int size)
/* 1967:     */     {
/* 1968:3342 */       this.m_allocatedSize = size;
/* 1969:3343 */       this.m_values = new boolean[size];
/* 1970:3344 */       this.m_index = -1;
/* 1971:     */     }
/* 1972:     */     
/* 1973:     */     public final int size()
/* 1974:     */     {
/* 1975:3354 */       return this.m_index + 1;
/* 1976:     */     }
/* 1977:     */     
/* 1978:     */     public final void clear()
/* 1979:     */     {
/* 1980:3363 */       this.m_index = -1;
/* 1981:     */     }
/* 1982:     */     
/* 1983:     */     public final boolean push(boolean val)
/* 1984:     */     {
/* 1985:3376 */       if (this.m_index == this.m_allocatedSize - 1) {
/* 1986:3377 */         grow();
/* 1987:     */       }
/* 1988:3379 */       return this.m_values[(++this.m_index)] = val;
/* 1989:     */     }
/* 1990:     */     
/* 1991:     */     public final boolean pop()
/* 1992:     */     {
/* 1993:3391 */       return this.m_values[(this.m_index--)];
/* 1994:     */     }
/* 1995:     */     
/* 1996:     */     public final boolean popAndTop()
/* 1997:     */     {
/* 1998:3404 */       this.m_index -= 1;
/* 1999:     */       
/* 2000:3406 */       return this.m_index >= 0 ? this.m_values[this.m_index] : false;
/* 2001:     */     }
/* 2002:     */     
/* 2003:     */     public final void setTop(boolean b)
/* 2004:     */     {
/* 2005:3417 */       this.m_values[this.m_index] = b;
/* 2006:     */     }
/* 2007:     */     
/* 2008:     */     public final boolean peek()
/* 2009:     */     {
/* 2010:3429 */       return this.m_values[this.m_index];
/* 2011:     */     }
/* 2012:     */     
/* 2013:     */     public final boolean peekOrFalse()
/* 2014:     */     {
/* 2015:3440 */       return this.m_index > -1 ? this.m_values[this.m_index] : false;
/* 2016:     */     }
/* 2017:     */     
/* 2018:     */     public final boolean peekOrTrue()
/* 2019:     */     {
/* 2020:3451 */       return this.m_index > -1 ? this.m_values[this.m_index] : true;
/* 2021:     */     }
/* 2022:     */     
/* 2023:     */     public boolean isEmpty()
/* 2024:     */     {
/* 2025:3462 */       return this.m_index == -1;
/* 2026:     */     }
/* 2027:     */     
/* 2028:     */     private void grow()
/* 2029:     */     {
/* 2030:3472 */       this.m_allocatedSize *= 2;
/* 2031:     */       
/* 2032:3474 */       boolean[] newVector = new boolean[this.m_allocatedSize];
/* 2033:     */       
/* 2034:3476 */       System.arraycopy(this.m_values, 0, newVector, 0, this.m_index + 1);
/* 2035:     */       
/* 2036:3478 */       this.m_values = newVector;
/* 2037:     */     }
/* 2038:     */   }
/* 2039:     */   
/* 2040:     */   public void notationDecl(String name, String pubID, String sysID)
/* 2041:     */     throws SAXException
/* 2042:     */   {
/* 2043:     */     try
/* 2044:     */     {
/* 2045:3492 */       DTDprolog();
/* 2046:     */       
/* 2047:3494 */       this.m_writer.write("<!NOTATION ");
/* 2048:3495 */       this.m_writer.write(name);
/* 2049:3496 */       if (pubID != null)
/* 2050:     */       {
/* 2051:3497 */         this.m_writer.write(" PUBLIC \"");
/* 2052:3498 */         this.m_writer.write(pubID);
/* 2053:     */       }
/* 2054:     */       else
/* 2055:     */       {
/* 2056:3502 */         this.m_writer.write(" SYSTEM \"");
/* 2057:3503 */         this.m_writer.write(sysID);
/* 2058:     */       }
/* 2059:3505 */       this.m_writer.write("\" >");
/* 2060:3506 */       this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 2061:     */     }
/* 2062:     */     catch (IOException e)
/* 2063:     */     {
/* 2064:3509 */       e.printStackTrace();
/* 2065:     */     }
/* 2066:     */   }
/* 2067:     */   
/* 2068:     */   public void unparsedEntityDecl(String name, String pubID, String sysID, String notationName)
/* 2069:     */     throws SAXException
/* 2070:     */   {
/* 2071:     */     try
/* 2072:     */     {
/* 2073:3522 */       DTDprolog();
/* 2074:     */       
/* 2075:3524 */       this.m_writer.write("<!ENTITY ");
/* 2076:3525 */       this.m_writer.write(name);
/* 2077:3526 */       if (pubID != null)
/* 2078:     */       {
/* 2079:3527 */         this.m_writer.write(" PUBLIC \"");
/* 2080:3528 */         this.m_writer.write(pubID);
/* 2081:     */       }
/* 2082:     */       else
/* 2083:     */       {
/* 2084:3532 */         this.m_writer.write(" SYSTEM \"");
/* 2085:3533 */         this.m_writer.write(sysID);
/* 2086:     */       }
/* 2087:3535 */       this.m_writer.write("\" NDATA ");
/* 2088:3536 */       this.m_writer.write(notationName);
/* 2089:3537 */       this.m_writer.write(" >");
/* 2090:3538 */       this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 2091:     */     }
/* 2092:     */     catch (IOException e)
/* 2093:     */     {
/* 2094:3541 */       e.printStackTrace();
/* 2095:     */     }
/* 2096:     */   }
/* 2097:     */   
/* 2098:     */   private void DTDprolog()
/* 2099:     */     throws SAXException, IOException
/* 2100:     */   {
/* 2101:3551 */     Writer writer = this.m_writer;
/* 2102:3552 */     if (this.m_needToOutputDocTypeDecl)
/* 2103:     */     {
/* 2104:3554 */       outputDocTypeDecl(this.m_elemContext.m_elementName, false);
/* 2105:3555 */       this.m_needToOutputDocTypeDecl = false;
/* 2106:     */     }
/* 2107:3557 */     if (this.m_inDoctype)
/* 2108:     */     {
/* 2109:3559 */       writer.write(" [");
/* 2110:3560 */       writer.write(this.m_lineSep, 0, this.m_lineSepLen);
/* 2111:3561 */       this.m_inDoctype = false;
/* 2112:     */     }
/* 2113:     */   }
/* 2114:     */   
/* 2115:     */   public void setDTDEntityExpansion(boolean expand)
/* 2116:     */   {
/* 2117:3570 */     this.m_expandDTDEntities = expand;
/* 2118:     */   }
/* 2119:     */   
/* 2120:     */   public void setNewLine(char[] eolChars)
/* 2121:     */   {
/* 2122:3578 */     this.m_lineSep = eolChars;
/* 2123:3579 */     this.m_lineSepLen = eolChars.length;
/* 2124:     */   }
/* 2125:     */   
/* 2126:     */   public void addCdataSectionElements(String URI_and_localNames)
/* 2127:     */   {
/* 2128:3594 */     if (URI_and_localNames != null) {
/* 2129:3595 */       initCdataElems(URI_and_localNames);
/* 2130:     */     }
/* 2131:3596 */     if (this.m_StringOfCDATASections == null) {
/* 2132:3597 */       this.m_StringOfCDATASections = URI_and_localNames;
/* 2133:     */     } else {
/* 2134:3599 */       this.m_StringOfCDATASections = (this.m_StringOfCDATASections + " " + URI_and_localNames);
/* 2135:     */     }
/* 2136:     */   }
/* 2137:     */   
/* 2138:     */   public void endPrefixMapping(String prefix)
/* 2139:     */     throws SAXException
/* 2140:     */   {}
/* 2141:     */   
/* 2142:     */   public void skippedEntity(String name)
/* 2143:     */     throws SAXException
/* 2144:     */   {}
/* 2145:     */   
/* 2146:     */   public void setContentHandler(ContentHandler ch) {}
/* 2147:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToStream
 * JD-Core Version:    0.7.0.1
 */