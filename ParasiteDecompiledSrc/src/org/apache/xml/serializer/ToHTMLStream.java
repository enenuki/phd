/*    1:     */ package org.apache.xml.serializer;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.Writer;
/*    5:     */ import java.util.Properties;
/*    6:     */ import org.apache.xml.serializer.utils.Messages;
/*    7:     */ import org.apache.xml.serializer.utils.Utils;
/*    8:     */ import org.xml.sax.Attributes;
/*    9:     */ import org.xml.sax.SAXException;
/*   10:     */ import org.xml.sax.helpers.AttributesImpl;
/*   11:     */ 
/*   12:     */ public class ToHTMLStream
/*   13:     */   extends ToStream
/*   14:     */ {
/*   15:  47 */   protected boolean m_inDTD = false;
/*   16:  51 */   private boolean m_inBlockElem = false;
/*   17:  57 */   private final CharInfo m_htmlcharInfo = CharInfo.getCharInfo(CharInfo.HTML_ENTITIES_RESOURCE, "html");
/*   18:  62 */   static final Trie m_elementFlags = new Trie();
/*   19:     */   
/*   20:     */   static
/*   21:     */   {
/*   22:  65 */     initTagReference(m_elementFlags);
/*   23:     */   }
/*   24:     */   
/*   25:     */   static void initTagReference(Trie m_elementFlags)
/*   26:     */   {
/*   27:  70 */     m_elementFlags.put("BASEFONT", new ElemDesc(2));
/*   28:  71 */     m_elementFlags.put("FRAME", new ElemDesc(10));
/*   29:     */     
/*   30:     */ 
/*   31:  74 */     m_elementFlags.put("FRAMESET", new ElemDesc(8));
/*   32:  75 */     m_elementFlags.put("NOFRAMES", new ElemDesc(8));
/*   33:  76 */     m_elementFlags.put("ISINDEX", new ElemDesc(10));
/*   34:     */     
/*   35:     */ 
/*   36:  79 */     m_elementFlags.put("APPLET", new ElemDesc(2097152));
/*   37:     */     
/*   38:     */ 
/*   39:  82 */     m_elementFlags.put("CENTER", new ElemDesc(8));
/*   40:  83 */     m_elementFlags.put("DIR", new ElemDesc(8));
/*   41:  84 */     m_elementFlags.put("MENU", new ElemDesc(8));
/*   42:     */     
/*   43:     */ 
/*   44:  87 */     m_elementFlags.put("TT", new ElemDesc(4096));
/*   45:  88 */     m_elementFlags.put("I", new ElemDesc(4096));
/*   46:  89 */     m_elementFlags.put("B", new ElemDesc(4096));
/*   47:  90 */     m_elementFlags.put("BIG", new ElemDesc(4096));
/*   48:  91 */     m_elementFlags.put("SMALL", new ElemDesc(4096));
/*   49:  92 */     m_elementFlags.put("EM", new ElemDesc(8192));
/*   50:  93 */     m_elementFlags.put("STRONG", new ElemDesc(8192));
/*   51:  94 */     m_elementFlags.put("DFN", new ElemDesc(8192));
/*   52:  95 */     m_elementFlags.put("CODE", new ElemDesc(8192));
/*   53:  96 */     m_elementFlags.put("SAMP", new ElemDesc(8192));
/*   54:  97 */     m_elementFlags.put("KBD", new ElemDesc(8192));
/*   55:  98 */     m_elementFlags.put("VAR", new ElemDesc(8192));
/*   56:  99 */     m_elementFlags.put("CITE", new ElemDesc(8192));
/*   57: 100 */     m_elementFlags.put("ABBR", new ElemDesc(8192));
/*   58: 101 */     m_elementFlags.put("ACRONYM", new ElemDesc(8192));
/*   59: 102 */     m_elementFlags.put("SUP", new ElemDesc(98304));
/*   60:     */     
/*   61:     */ 
/*   62: 105 */     m_elementFlags.put("SUB", new ElemDesc(98304));
/*   63:     */     
/*   64:     */ 
/*   65: 108 */     m_elementFlags.put("SPAN", new ElemDesc(98304));
/*   66:     */     
/*   67:     */ 
/*   68: 111 */     m_elementFlags.put("BDO", new ElemDesc(98304));
/*   69:     */     
/*   70:     */ 
/*   71: 114 */     m_elementFlags.put("BR", new ElemDesc(98314));
/*   72:     */     
/*   73:     */ 
/*   74:     */ 
/*   75:     */ 
/*   76:     */ 
/*   77:     */ 
/*   78:     */ 
/*   79: 122 */     m_elementFlags.put("BODY", new ElemDesc(8));
/*   80: 123 */     m_elementFlags.put("ADDRESS", new ElemDesc(56));
/*   81:     */     
/*   82:     */ 
/*   83:     */ 
/*   84:     */ 
/*   85:     */ 
/*   86:     */ 
/*   87: 130 */     m_elementFlags.put("DIV", new ElemDesc(56));
/*   88:     */     
/*   89:     */ 
/*   90:     */ 
/*   91:     */ 
/*   92:     */ 
/*   93:     */ 
/*   94: 137 */     m_elementFlags.put("A", new ElemDesc(32768));
/*   95: 138 */     m_elementFlags.put("MAP", new ElemDesc(98312));
/*   96:     */     
/*   97:     */ 
/*   98:     */ 
/*   99: 142 */     m_elementFlags.put("AREA", new ElemDesc(10));
/*  100:     */     
/*  101:     */ 
/*  102: 145 */     m_elementFlags.put("LINK", new ElemDesc(131082));
/*  103:     */     
/*  104:     */ 
/*  105:     */ 
/*  106: 149 */     m_elementFlags.put("IMG", new ElemDesc(2195458));
/*  107:     */     
/*  108:     */ 
/*  109:     */ 
/*  110:     */ 
/*  111:     */ 
/*  112:     */ 
/*  113:     */ 
/*  114: 157 */     m_elementFlags.put("OBJECT", new ElemDesc(2326528));
/*  115:     */     
/*  116:     */ 
/*  117:     */ 
/*  118:     */ 
/*  119:     */ 
/*  120:     */ 
/*  121:     */ 
/*  122: 165 */     m_elementFlags.put("PARAM", new ElemDesc(2));
/*  123: 166 */     m_elementFlags.put("HR", new ElemDesc(58));
/*  124:     */     
/*  125:     */ 
/*  126:     */ 
/*  127:     */ 
/*  128:     */ 
/*  129:     */ 
/*  130:     */ 
/*  131: 174 */     m_elementFlags.put("P", new ElemDesc(56));
/*  132:     */     
/*  133:     */ 
/*  134:     */ 
/*  135:     */ 
/*  136:     */ 
/*  137:     */ 
/*  138: 181 */     m_elementFlags.put("H1", new ElemDesc(262152));
/*  139:     */     
/*  140:     */ 
/*  141: 184 */     m_elementFlags.put("H2", new ElemDesc(262152));
/*  142:     */     
/*  143:     */ 
/*  144: 187 */     m_elementFlags.put("H3", new ElemDesc(262152));
/*  145:     */     
/*  146:     */ 
/*  147: 190 */     m_elementFlags.put("H4", new ElemDesc(262152));
/*  148:     */     
/*  149:     */ 
/*  150: 193 */     m_elementFlags.put("H5", new ElemDesc(262152));
/*  151:     */     
/*  152:     */ 
/*  153: 196 */     m_elementFlags.put("H6", new ElemDesc(262152));
/*  154:     */     
/*  155:     */ 
/*  156: 199 */     m_elementFlags.put("PRE", new ElemDesc(1048584));
/*  157:     */     
/*  158:     */ 
/*  159: 202 */     m_elementFlags.put("Q", new ElemDesc(98304));
/*  160:     */     
/*  161:     */ 
/*  162: 205 */     m_elementFlags.put("BLOCKQUOTE", new ElemDesc(56));
/*  163:     */     
/*  164:     */ 
/*  165:     */ 
/*  166:     */ 
/*  167:     */ 
/*  168:     */ 
/*  169: 212 */     m_elementFlags.put("INS", new ElemDesc(0));
/*  170: 213 */     m_elementFlags.put("DEL", new ElemDesc(0));
/*  171: 214 */     m_elementFlags.put("DL", new ElemDesc(56));
/*  172:     */     
/*  173:     */ 
/*  174:     */ 
/*  175:     */ 
/*  176:     */ 
/*  177:     */ 
/*  178: 221 */     m_elementFlags.put("DT", new ElemDesc(8));
/*  179: 222 */     m_elementFlags.put("DD", new ElemDesc(8));
/*  180: 223 */     m_elementFlags.put("OL", new ElemDesc(524296));
/*  181:     */     
/*  182:     */ 
/*  183: 226 */     m_elementFlags.put("UL", new ElemDesc(524296));
/*  184:     */     
/*  185:     */ 
/*  186: 229 */     m_elementFlags.put("LI", new ElemDesc(8));
/*  187: 230 */     m_elementFlags.put("FORM", new ElemDesc(8));
/*  188: 231 */     m_elementFlags.put("LABEL", new ElemDesc(16384));
/*  189: 232 */     m_elementFlags.put("INPUT", new ElemDesc(18434));
/*  190:     */     
/*  191:     */ 
/*  192:     */ 
/*  193: 236 */     m_elementFlags.put("SELECT", new ElemDesc(18432));
/*  194:     */     
/*  195:     */ 
/*  196: 239 */     m_elementFlags.put("OPTGROUP", new ElemDesc(0));
/*  197: 240 */     m_elementFlags.put("OPTION", new ElemDesc(0));
/*  198: 241 */     m_elementFlags.put("TEXTAREA", new ElemDesc(18432));
/*  199:     */     
/*  200:     */ 
/*  201: 244 */     m_elementFlags.put("FIELDSET", new ElemDesc(24));
/*  202:     */     
/*  203:     */ 
/*  204: 247 */     m_elementFlags.put("LEGEND", new ElemDesc(0));
/*  205: 248 */     m_elementFlags.put("BUTTON", new ElemDesc(18432));
/*  206:     */     
/*  207:     */ 
/*  208: 251 */     m_elementFlags.put("TABLE", new ElemDesc(56));
/*  209:     */     
/*  210:     */ 
/*  211:     */ 
/*  212:     */ 
/*  213:     */ 
/*  214:     */ 
/*  215: 258 */     m_elementFlags.put("CAPTION", new ElemDesc(8));
/*  216: 259 */     m_elementFlags.put("THEAD", new ElemDesc(8));
/*  217: 260 */     m_elementFlags.put("TFOOT", new ElemDesc(8));
/*  218: 261 */     m_elementFlags.put("TBODY", new ElemDesc(8));
/*  219: 262 */     m_elementFlags.put("COLGROUP", new ElemDesc(8));
/*  220: 263 */     m_elementFlags.put("COL", new ElemDesc(10));
/*  221:     */     
/*  222:     */ 
/*  223: 266 */     m_elementFlags.put("TR", new ElemDesc(8));
/*  224: 267 */     m_elementFlags.put("TH", new ElemDesc(0));
/*  225: 268 */     m_elementFlags.put("TD", new ElemDesc(0));
/*  226: 269 */     m_elementFlags.put("HEAD", new ElemDesc(4194312));
/*  227:     */     
/*  228:     */ 
/*  229: 272 */     m_elementFlags.put("TITLE", new ElemDesc(8));
/*  230: 273 */     m_elementFlags.put("BASE", new ElemDesc(10));
/*  231:     */     
/*  232:     */ 
/*  233: 276 */     m_elementFlags.put("META", new ElemDesc(131082));
/*  234:     */     
/*  235:     */ 
/*  236:     */ 
/*  237: 280 */     m_elementFlags.put("STYLE", new ElemDesc(131336));
/*  238:     */     
/*  239:     */ 
/*  240:     */ 
/*  241: 284 */     m_elementFlags.put("SCRIPT", new ElemDesc(229632));
/*  242:     */     
/*  243:     */ 
/*  244:     */ 
/*  245:     */ 
/*  246:     */ 
/*  247:     */ 
/*  248:     */ 
/*  249: 292 */     m_elementFlags.put("NOSCRIPT", new ElemDesc(56));
/*  250:     */     
/*  251:     */ 
/*  252:     */ 
/*  253:     */ 
/*  254:     */ 
/*  255:     */ 
/*  256: 299 */     m_elementFlags.put("HTML", new ElemDesc(8388616));
/*  257:     */     
/*  258:     */ 
/*  259:     */ 
/*  260:     */ 
/*  261: 304 */     m_elementFlags.put("FONT", new ElemDesc(4096));
/*  262:     */     
/*  263:     */ 
/*  264: 307 */     m_elementFlags.put("S", new ElemDesc(4096));
/*  265: 308 */     m_elementFlags.put("STRIKE", new ElemDesc(4096));
/*  266:     */     
/*  267:     */ 
/*  268: 311 */     m_elementFlags.put("U", new ElemDesc(4096));
/*  269:     */     
/*  270:     */ 
/*  271: 314 */     m_elementFlags.put("NOBR", new ElemDesc(4096));
/*  272:     */     
/*  273:     */ 
/*  274: 317 */     m_elementFlags.put("IFRAME", new ElemDesc(56));
/*  275:     */     
/*  276:     */ 
/*  277:     */ 
/*  278:     */ 
/*  279:     */ 
/*  280:     */ 
/*  281:     */ 
/*  282:     */ 
/*  283: 326 */     m_elementFlags.put("LAYER", new ElemDesc(56));
/*  284:     */     
/*  285:     */ 
/*  286:     */ 
/*  287:     */ 
/*  288:     */ 
/*  289:     */ 
/*  290:     */ 
/*  291: 334 */     m_elementFlags.put("ILAYER", new ElemDesc(56));
/*  292:     */     
/*  293:     */ 
/*  294:     */ 
/*  295:     */ 
/*  296:     */ 
/*  297:     */ 
/*  298:     */ 
/*  299:     */ 
/*  300:     */ 
/*  301:     */ 
/*  302:     */ 
/*  303:     */ 
/*  304: 347 */     ElemDesc elemDesc = (ElemDesc)m_elementFlags.get("a");
/*  305: 348 */     elemDesc.setAttr("HREF", 2);
/*  306: 349 */     elemDesc.setAttr("NAME", 2);
/*  307:     */     
/*  308:     */ 
/*  309: 352 */     elemDesc = (ElemDesc)m_elementFlags.get("area");
/*  310:     */     
/*  311: 354 */     elemDesc.setAttr("HREF", 2);
/*  312: 355 */     elemDesc.setAttr("NOHREF", 4);
/*  313:     */     
/*  314:     */ 
/*  315: 358 */     elemDesc = (ElemDesc)m_elementFlags.get("base");
/*  316:     */     
/*  317: 360 */     elemDesc.setAttr("HREF", 2);
/*  318:     */     
/*  319:     */ 
/*  320: 363 */     elemDesc = (ElemDesc)m_elementFlags.get("button");
/*  321: 364 */     elemDesc.setAttr("DISABLED", 4);
/*  322:     */     
/*  323:     */ 
/*  324: 367 */     elemDesc = (ElemDesc)m_elementFlags.get("blockquote");
/*  325:     */     
/*  326: 369 */     elemDesc.setAttr("CITE", 2);
/*  327:     */     
/*  328:     */ 
/*  329: 372 */     elemDesc = (ElemDesc)m_elementFlags.get("del");
/*  330: 373 */     elemDesc.setAttr("CITE", 2);
/*  331:     */     
/*  332:     */ 
/*  333: 376 */     elemDesc = (ElemDesc)m_elementFlags.get("dir");
/*  334: 377 */     elemDesc.setAttr("COMPACT", 4);
/*  335:     */     
/*  336:     */ 
/*  337:     */ 
/*  338: 381 */     elemDesc = (ElemDesc)m_elementFlags.get("div");
/*  339: 382 */     elemDesc.setAttr("SRC", 2);
/*  340: 383 */     elemDesc.setAttr("NOWRAP", 4);
/*  341:     */     
/*  342:     */ 
/*  343: 386 */     elemDesc = (ElemDesc)m_elementFlags.get("dl");
/*  344: 387 */     elemDesc.setAttr("COMPACT", 4);
/*  345:     */     
/*  346:     */ 
/*  347: 390 */     elemDesc = (ElemDesc)m_elementFlags.get("form");
/*  348: 391 */     elemDesc.setAttr("ACTION", 2);
/*  349:     */     
/*  350:     */ 
/*  351:     */ 
/*  352: 395 */     elemDesc = (ElemDesc)m_elementFlags.get("frame");
/*  353: 396 */     elemDesc.setAttr("SRC", 2);
/*  354: 397 */     elemDesc.setAttr("LONGDESC", 2);
/*  355: 398 */     elemDesc.setAttr("NORESIZE", 4);
/*  356:     */     
/*  357:     */ 
/*  358: 401 */     elemDesc = (ElemDesc)m_elementFlags.get("head");
/*  359: 402 */     elemDesc.setAttr("PROFILE", 2);
/*  360:     */     
/*  361:     */ 
/*  362: 405 */     elemDesc = (ElemDesc)m_elementFlags.get("hr");
/*  363: 406 */     elemDesc.setAttr("NOSHADE", 4);
/*  364:     */     
/*  365:     */ 
/*  366:     */ 
/*  367: 410 */     elemDesc = (ElemDesc)m_elementFlags.get("iframe");
/*  368: 411 */     elemDesc.setAttr("SRC", 2);
/*  369: 412 */     elemDesc.setAttr("LONGDESC", 2);
/*  370:     */     
/*  371:     */ 
/*  372:     */ 
/*  373: 416 */     elemDesc = (ElemDesc)m_elementFlags.get("ilayer");
/*  374: 417 */     elemDesc.setAttr("SRC", 2);
/*  375:     */     
/*  376:     */ 
/*  377: 420 */     elemDesc = (ElemDesc)m_elementFlags.get("img");
/*  378: 421 */     elemDesc.setAttr("SRC", 2);
/*  379: 422 */     elemDesc.setAttr("LONGDESC", 2);
/*  380: 423 */     elemDesc.setAttr("USEMAP", 2);
/*  381: 424 */     elemDesc.setAttr("ISMAP", 4);
/*  382:     */     
/*  383:     */ 
/*  384: 427 */     elemDesc = (ElemDesc)m_elementFlags.get("input");
/*  385:     */     
/*  386: 429 */     elemDesc.setAttr("SRC", 2);
/*  387: 430 */     elemDesc.setAttr("USEMAP", 2);
/*  388: 431 */     elemDesc.setAttr("CHECKED", 4);
/*  389: 432 */     elemDesc.setAttr("DISABLED", 4);
/*  390: 433 */     elemDesc.setAttr("ISMAP", 4);
/*  391: 434 */     elemDesc.setAttr("READONLY", 4);
/*  392:     */     
/*  393:     */ 
/*  394: 437 */     elemDesc = (ElemDesc)m_elementFlags.get("ins");
/*  395: 438 */     elemDesc.setAttr("CITE", 2);
/*  396:     */     
/*  397:     */ 
/*  398:     */ 
/*  399: 442 */     elemDesc = (ElemDesc)m_elementFlags.get("layer");
/*  400: 443 */     elemDesc.setAttr("SRC", 2);
/*  401:     */     
/*  402:     */ 
/*  403: 446 */     elemDesc = (ElemDesc)m_elementFlags.get("link");
/*  404: 447 */     elemDesc.setAttr("HREF", 2);
/*  405:     */     
/*  406:     */ 
/*  407: 450 */     elemDesc = (ElemDesc)m_elementFlags.get("menu");
/*  408: 451 */     elemDesc.setAttr("COMPACT", 4);
/*  409:     */     
/*  410:     */ 
/*  411: 454 */     elemDesc = (ElemDesc)m_elementFlags.get("object");
/*  412:     */     
/*  413: 456 */     elemDesc.setAttr("CLASSID", 2);
/*  414: 457 */     elemDesc.setAttr("CODEBASE", 2);
/*  415: 458 */     elemDesc.setAttr("DATA", 2);
/*  416: 459 */     elemDesc.setAttr("ARCHIVE", 2);
/*  417: 460 */     elemDesc.setAttr("USEMAP", 2);
/*  418: 461 */     elemDesc.setAttr("DECLARE", 4);
/*  419:     */     
/*  420:     */ 
/*  421: 464 */     elemDesc = (ElemDesc)m_elementFlags.get("ol");
/*  422: 465 */     elemDesc.setAttr("COMPACT", 4);
/*  423:     */     
/*  424:     */ 
/*  425: 468 */     elemDesc = (ElemDesc)m_elementFlags.get("optgroup");
/*  426: 469 */     elemDesc.setAttr("DISABLED", 4);
/*  427:     */     
/*  428:     */ 
/*  429: 472 */     elemDesc = (ElemDesc)m_elementFlags.get("option");
/*  430: 473 */     elemDesc.setAttr("SELECTED", 4);
/*  431: 474 */     elemDesc.setAttr("DISABLED", 4);
/*  432:     */     
/*  433:     */ 
/*  434: 477 */     elemDesc = (ElemDesc)m_elementFlags.get("q");
/*  435: 478 */     elemDesc.setAttr("CITE", 2);
/*  436:     */     
/*  437:     */ 
/*  438: 481 */     elemDesc = (ElemDesc)m_elementFlags.get("script");
/*  439: 482 */     elemDesc.setAttr("SRC", 2);
/*  440: 483 */     elemDesc.setAttr("FOR", 2);
/*  441: 484 */     elemDesc.setAttr("DEFER", 4);
/*  442:     */     
/*  443:     */ 
/*  444: 487 */     elemDesc = (ElemDesc)m_elementFlags.get("select");
/*  445: 488 */     elemDesc.setAttr("DISABLED", 4);
/*  446: 489 */     elemDesc.setAttr("MULTIPLE", 4);
/*  447:     */     
/*  448:     */ 
/*  449: 492 */     elemDesc = (ElemDesc)m_elementFlags.get("table");
/*  450: 493 */     elemDesc.setAttr("NOWRAP", 4);
/*  451:     */     
/*  452:     */ 
/*  453: 496 */     elemDesc = (ElemDesc)m_elementFlags.get("td");
/*  454: 497 */     elemDesc.setAttr("NOWRAP", 4);
/*  455:     */     
/*  456:     */ 
/*  457: 500 */     elemDesc = (ElemDesc)m_elementFlags.get("textarea");
/*  458: 501 */     elemDesc.setAttr("DISABLED", 4);
/*  459: 502 */     elemDesc.setAttr("READONLY", 4);
/*  460:     */     
/*  461:     */ 
/*  462: 505 */     elemDesc = (ElemDesc)m_elementFlags.get("th");
/*  463: 506 */     elemDesc.setAttr("NOWRAP", 4);
/*  464:     */     
/*  465:     */ 
/*  466:     */ 
/*  467:     */ 
/*  468: 511 */     elemDesc = (ElemDesc)m_elementFlags.get("tr");
/*  469: 512 */     elemDesc.setAttr("NOWRAP", 4);
/*  470:     */     
/*  471:     */ 
/*  472: 515 */     elemDesc = (ElemDesc)m_elementFlags.get("ul");
/*  473: 516 */     elemDesc.setAttr("COMPACT", 4);
/*  474:     */   }
/*  475:     */   
/*  476: 522 */   private static final ElemDesc m_dummy = new ElemDesc(8);
/*  477: 525 */   private boolean m_specialEscapeURLs = true;
/*  478: 528 */   private boolean m_omitMetaTag = false;
/*  479:     */   
/*  480:     */   public void setSpecialEscapeURLs(boolean bool)
/*  481:     */   {
/*  482: 537 */     this.m_specialEscapeURLs = bool;
/*  483:     */   }
/*  484:     */   
/*  485:     */   public void setOmitMetaTag(boolean bool)
/*  486:     */   {
/*  487: 547 */     this.m_omitMetaTag = bool;
/*  488:     */   }
/*  489:     */   
/*  490:     */   public void setOutputFormat(Properties format)
/*  491:     */   {
/*  492: 574 */     String value = format.getProperty("{http://xml.apache.org/xalan}use-url-escaping");
/*  493: 575 */     if (value != null) {
/*  494: 576 */       this.m_specialEscapeURLs = OutputPropertyUtils.getBooleanProperty("{http://xml.apache.org/xalan}use-url-escaping", format);
/*  495:     */     }
/*  496: 587 */     value = format.getProperty("{http://xml.apache.org/xalan}omit-meta-tag");
/*  497: 588 */     if (value != null) {
/*  498: 589 */       this.m_omitMetaTag = OutputPropertyUtils.getBooleanProperty("{http://xml.apache.org/xalan}omit-meta-tag", format);
/*  499:     */     }
/*  500: 595 */     super.setOutputFormat(format);
/*  501:     */   }
/*  502:     */   
/*  503:     */   private final boolean getSpecialEscapeURLs()
/*  504:     */   {
/*  505: 605 */     return this.m_specialEscapeURLs;
/*  506:     */   }
/*  507:     */   
/*  508:     */   private final boolean getOmitMetaTag()
/*  509:     */   {
/*  510: 615 */     return this.m_omitMetaTag;
/*  511:     */   }
/*  512:     */   
/*  513:     */   public static final ElemDesc getElemDesc(String name)
/*  514:     */   {
/*  515: 631 */     Object obj = m_elementFlags.get(name);
/*  516: 632 */     if (null != obj) {
/*  517: 633 */       return (ElemDesc)obj;
/*  518:     */     }
/*  519: 634 */     return m_dummy;
/*  520:     */   }
/*  521:     */   
/*  522: 643 */   private Trie m_htmlInfo = new Trie(m_elementFlags);
/*  523:     */   
/*  524:     */   private ElemDesc getElemDesc2(String name)
/*  525:     */   {
/*  526: 650 */     Object obj = this.m_htmlInfo.get2(name);
/*  527: 651 */     if (null != obj) {
/*  528: 652 */       return (ElemDesc)obj;
/*  529:     */     }
/*  530: 653 */     return m_dummy;
/*  531:     */   }
/*  532:     */   
/*  533:     */   public ToHTMLStream()
/*  534:     */   {
/*  535: 666 */     this.m_doIndent = true;
/*  536: 667 */     this.m_charInfo = this.m_htmlcharInfo;
/*  537:     */     
/*  538: 669 */     this.m_prefixMap = new NamespaceMappings();
/*  539:     */   }
/*  540:     */   
/*  541:     */   protected void startDocumentInternal()
/*  542:     */     throws SAXException
/*  543:     */   {
/*  544: 686 */     super.startDocumentInternal();
/*  545:     */     
/*  546: 688 */     this.m_needToCallStartDocument = false;
/*  547: 689 */     this.m_needToOutputDocTypeDecl = true;
/*  548: 690 */     this.m_startNewLine = false;
/*  549: 691 */     setOmitXMLDeclaration(true);
/*  550:     */   }
/*  551:     */   
/*  552:     */   private void outputDocTypeDecl(String name)
/*  553:     */     throws SAXException
/*  554:     */   {
/*  555: 701 */     if (true == this.m_needToOutputDocTypeDecl)
/*  556:     */     {
/*  557: 703 */       String doctypeSystem = getDoctypeSystem();
/*  558: 704 */       String doctypePublic = getDoctypePublic();
/*  559: 705 */       if ((null != doctypeSystem) || (null != doctypePublic))
/*  560:     */       {
/*  561: 707 */         Writer writer = this.m_writer;
/*  562:     */         try
/*  563:     */         {
/*  564: 710 */           writer.write("<!DOCTYPE ");
/*  565: 711 */           writer.write(name);
/*  566: 713 */           if (null != doctypePublic)
/*  567:     */           {
/*  568: 715 */             writer.write(" PUBLIC \"");
/*  569: 716 */             writer.write(doctypePublic);
/*  570: 717 */             writer.write(34);
/*  571:     */           }
/*  572: 720 */           if (null != doctypeSystem)
/*  573:     */           {
/*  574: 722 */             if (null == doctypePublic) {
/*  575: 723 */               writer.write(" SYSTEM \"");
/*  576:     */             } else {
/*  577: 725 */               writer.write(" \"");
/*  578:     */             }
/*  579: 727 */             writer.write(doctypeSystem);
/*  580: 728 */             writer.write(34);
/*  581:     */           }
/*  582: 731 */           writer.write(62);
/*  583: 732 */           outputLineSep();
/*  584:     */         }
/*  585:     */         catch (IOException e)
/*  586:     */         {
/*  587: 736 */           throw new SAXException(e);
/*  588:     */         }
/*  589:     */       }
/*  590:     */     }
/*  591: 741 */     this.m_needToOutputDocTypeDecl = false;
/*  592:     */   }
/*  593:     */   
/*  594:     */   public final void endDocument()
/*  595:     */     throws SAXException
/*  596:     */   {
/*  597: 755 */     flushPending();
/*  598: 756 */     if ((this.m_doIndent) && (!this.m_isprevtext)) {
/*  599:     */       try
/*  600:     */       {
/*  601: 760 */         outputLineSep();
/*  602:     */       }
/*  603:     */       catch (IOException e)
/*  604:     */       {
/*  605: 764 */         throw new SAXException(e);
/*  606:     */       }
/*  607:     */     }
/*  608: 768 */     flushWriter();
/*  609: 769 */     if (this.m_tracer != null) {
/*  610: 770 */       super.fireEndDoc();
/*  611:     */     }
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void startElement(String namespaceURI, String localName, String name, Attributes atts)
/*  615:     */     throws SAXException
/*  616:     */   {
/*  617: 794 */     ElemContext elemContext = this.m_elemContext;
/*  618: 797 */     if (elemContext.m_startTagOpen)
/*  619:     */     {
/*  620: 799 */       closeStartTag();
/*  621: 800 */       elemContext.m_startTagOpen = false;
/*  622:     */     }
/*  623: 802 */     else if (this.m_cdataTagOpen)
/*  624:     */     {
/*  625: 804 */       closeCDATA();
/*  626: 805 */       this.m_cdataTagOpen = false;
/*  627:     */     }
/*  628: 807 */     else if (this.m_needToCallStartDocument)
/*  629:     */     {
/*  630: 809 */       startDocumentInternal();
/*  631: 810 */       this.m_needToCallStartDocument = false;
/*  632:     */     }
/*  633: 813 */     if (this.m_needToOutputDocTypeDecl)
/*  634:     */     {
/*  635: 814 */       String n = name;
/*  636: 815 */       if ((n == null) || (n.length() == 0)) {
/*  637: 818 */         n = localName;
/*  638:     */       }
/*  639: 820 */       outputDocTypeDecl(n);
/*  640:     */     }
/*  641: 825 */     if ((null != namespaceURI) && (namespaceURI.length() > 0))
/*  642:     */     {
/*  643: 827 */       super.startElement(namespaceURI, localName, name, atts);
/*  644:     */       
/*  645: 829 */       return;
/*  646:     */     }
/*  647:     */     try
/*  648:     */     {
/*  649: 835 */       ElemDesc elemDesc = getElemDesc2(name);
/*  650: 836 */       int elemFlags = elemDesc.getFlags();
/*  651: 839 */       if (this.m_doIndent)
/*  652:     */       {
/*  653: 842 */         boolean isBlockElement = (elemFlags & 0x8) != 0;
/*  654: 843 */         if (this.m_ispreserve)
/*  655:     */         {
/*  656: 844 */           this.m_ispreserve = false;
/*  657:     */         }
/*  658: 845 */         else if ((null != elemContext.m_elementName) && ((!this.m_inBlockElem) || (isBlockElement)))
/*  659:     */         {
/*  660: 851 */           this.m_startNewLine = true;
/*  661:     */           
/*  662: 853 */           indent();
/*  663:     */         }
/*  664: 856 */         this.m_inBlockElem = (!isBlockElement);
/*  665:     */       }
/*  666: 860 */       if (atts != null) {
/*  667: 861 */         addAttributes(atts);
/*  668:     */       }
/*  669: 863 */       this.m_isprevtext = false;
/*  670: 864 */       Writer writer = this.m_writer;
/*  671: 865 */       writer.write(60);
/*  672: 866 */       writer.write(name);
/*  673: 870 */       if (this.m_tracer != null) {
/*  674: 871 */         firePseudoAttributes();
/*  675:     */       }
/*  676: 873 */       if ((elemFlags & 0x2) != 0)
/*  677:     */       {
/*  678: 877 */         this.m_elemContext = elemContext.push();
/*  679:     */         
/*  680:     */ 
/*  681:     */ 
/*  682: 881 */         this.m_elemContext.m_elementName = name;
/*  683: 882 */         this.m_elemContext.m_elementDesc = elemDesc;
/*  684: 883 */         return;
/*  685:     */       }
/*  686: 887 */       elemContext = elemContext.push(namespaceURI, localName, name);
/*  687: 888 */       this.m_elemContext = elemContext;
/*  688: 889 */       elemContext.m_elementDesc = elemDesc;
/*  689: 890 */       elemContext.m_isRaw = ((elemFlags & 0x100) != 0);
/*  690: 894 */       if ((elemFlags & 0x400000) != 0)
/*  691:     */       {
/*  692: 897 */         closeStartTag();
/*  693: 898 */         elemContext.m_startTagOpen = false;
/*  694: 899 */         if (!this.m_omitMetaTag)
/*  695:     */         {
/*  696: 901 */           if (this.m_doIndent) {
/*  697: 902 */             indent();
/*  698:     */           }
/*  699: 903 */           writer.write("<META http-equiv=\"Content-Type\" content=\"text/html; charset=");
/*  700:     */           
/*  701: 905 */           String encoding = getEncoding();
/*  702: 906 */           String encode = Encodings.getMimeEncoding(encoding);
/*  703: 907 */           writer.write(encode);
/*  704: 908 */           writer.write("\">");
/*  705:     */         }
/*  706:     */       }
/*  707:     */     }
/*  708:     */     catch (IOException e)
/*  709:     */     {
/*  710: 914 */       throw new SAXException(e);
/*  711:     */     }
/*  712:     */   }
/*  713:     */   
/*  714:     */   public final void endElement(String namespaceURI, String localName, String name)
/*  715:     */     throws SAXException
/*  716:     */   {
/*  717: 935 */     if (this.m_cdataTagOpen) {
/*  718: 936 */       closeCDATA();
/*  719:     */     }
/*  720: 939 */     if ((null != namespaceURI) && (namespaceURI.length() > 0))
/*  721:     */     {
/*  722: 941 */       super.endElement(namespaceURI, localName, name);
/*  723:     */       
/*  724: 943 */       return;
/*  725:     */     }
/*  726:     */     try
/*  727:     */     {
/*  728: 949 */       ElemContext elemContext = this.m_elemContext;
/*  729: 950 */       ElemDesc elemDesc = elemContext.m_elementDesc;
/*  730: 951 */       int elemFlags = elemDesc.getFlags();
/*  731: 952 */       boolean elemEmpty = (elemFlags & 0x2) != 0;
/*  732: 955 */       if (this.m_doIndent)
/*  733:     */       {
/*  734: 957 */         boolean isBlockElement = (elemFlags & 0x8) != 0;
/*  735: 958 */         boolean shouldIndent = false;
/*  736: 960 */         if (this.m_ispreserve)
/*  737:     */         {
/*  738: 962 */           this.m_ispreserve = false;
/*  739:     */         }
/*  740: 964 */         else if ((this.m_doIndent) && ((!this.m_inBlockElem) || (isBlockElement)))
/*  741:     */         {
/*  742: 966 */           this.m_startNewLine = true;
/*  743: 967 */           shouldIndent = true;
/*  744:     */         }
/*  745: 969 */         if ((!elemContext.m_startTagOpen) && (shouldIndent)) {
/*  746: 970 */           indent(elemContext.m_currentElemDepth - 1);
/*  747:     */         }
/*  748: 971 */         this.m_inBlockElem = (!isBlockElement);
/*  749:     */       }
/*  750: 974 */       Writer writer = this.m_writer;
/*  751: 975 */       if (!elemContext.m_startTagOpen)
/*  752:     */       {
/*  753: 977 */         writer.write("</");
/*  754: 978 */         writer.write(name);
/*  755: 979 */         writer.write(62);
/*  756:     */       }
/*  757:     */       else
/*  758:     */       {
/*  759: 986 */         if (this.m_tracer != null) {
/*  760: 987 */           super.fireStartElem(name);
/*  761:     */         }
/*  762: 991 */         int nAttrs = this.m_attributes.getLength();
/*  763: 992 */         if (nAttrs > 0)
/*  764:     */         {
/*  765: 994 */           processAttributes(this.m_writer, nAttrs);
/*  766:     */           
/*  767: 996 */           this.m_attributes.clear();
/*  768:     */         }
/*  769: 998 */         if (!elemEmpty)
/*  770:     */         {
/*  771:1005 */           writer.write("></");
/*  772:1006 */           writer.write(name);
/*  773:1007 */           writer.write(62);
/*  774:     */         }
/*  775:     */         else
/*  776:     */         {
/*  777:1011 */           writer.write(62);
/*  778:     */         }
/*  779:     */       }
/*  780:1016 */       if ((elemFlags & 0x200000) != 0) {
/*  781:1017 */         this.m_ispreserve = true;
/*  782:     */       }
/*  783:1018 */       this.m_isprevtext = false;
/*  784:1021 */       if (this.m_tracer != null) {
/*  785:1022 */         super.fireEndElem(name);
/*  786:     */       }
/*  787:1025 */       if (elemEmpty)
/*  788:     */       {
/*  789:1030 */         this.m_elemContext = elemContext.m_prev;
/*  790:1031 */         return;
/*  791:     */       }
/*  792:1035 */       if (!elemContext.m_startTagOpen) {
/*  793:1037 */         if ((this.m_doIndent) && (!this.m_preserves.isEmpty())) {
/*  794:1038 */           this.m_preserves.pop();
/*  795:     */         }
/*  796:     */       }
/*  797:1040 */       this.m_elemContext = elemContext.m_prev;
/*  798:     */     }
/*  799:     */     catch (IOException e)
/*  800:     */     {
/*  801:1045 */       throw new SAXException(e);
/*  802:     */     }
/*  803:     */   }
/*  804:     */   
/*  805:     */   protected void processAttribute(Writer writer, String name, String value, ElemDesc elemDesc)
/*  806:     */     throws IOException
/*  807:     */   {
/*  808:1066 */     writer.write(32);
/*  809:1068 */     if (((value.length() == 0) || (value.equalsIgnoreCase(name))) && (elemDesc != null) && (elemDesc.isAttrFlagSet(name, 4)))
/*  810:     */     {
/*  811:1072 */       writer.write(name);
/*  812:     */     }
/*  813:     */     else
/*  814:     */     {
/*  815:1079 */       writer.write(name);
/*  816:1080 */       writer.write("=\"");
/*  817:1081 */       if ((elemDesc != null) && (elemDesc.isAttrFlagSet(name, 2))) {
/*  818:1083 */         writeAttrURI(writer, value, this.m_specialEscapeURLs);
/*  819:     */       } else {
/*  820:1085 */         writeAttrString(writer, value, getEncoding());
/*  821:     */       }
/*  822:1086 */       writer.write(34);
/*  823:     */     }
/*  824:     */   }
/*  825:     */   
/*  826:     */   private boolean isASCIIDigit(char c)
/*  827:     */   {
/*  828:1096 */     return (c >= '0') && (c <= '9');
/*  829:     */   }
/*  830:     */   
/*  831:     */   private static String makeHHString(int i)
/*  832:     */   {
/*  833:1110 */     String s = Integer.toHexString(i).toUpperCase();
/*  834:1111 */     if (s.length() == 1) {
/*  835:1113 */       s = "0" + s;
/*  836:     */     }
/*  837:1115 */     return s;
/*  838:     */   }
/*  839:     */   
/*  840:     */   private boolean isHHSign(String str)
/*  841:     */   {
/*  842:1126 */     boolean sign = true;
/*  843:     */     try
/*  844:     */     {
/*  845:1129 */       r = (char)Integer.parseInt(str, 16);
/*  846:     */     }
/*  847:     */     catch (NumberFormatException e)
/*  848:     */     {
/*  849:     */       char r;
/*  850:1133 */       sign = false;
/*  851:     */     }
/*  852:1135 */     return sign;
/*  853:     */   }
/*  854:     */   
/*  855:     */   public void writeAttrURI(Writer writer, String string, boolean doURLEscaping)
/*  856:     */     throws IOException
/*  857:     */   {
/*  858:1167 */     int end = string.length();
/*  859:1168 */     if (end > this.m_attrBuff.length) {
/*  860:1170 */       this.m_attrBuff = new char[end * 2 + 1];
/*  861:     */     }
/*  862:1172 */     string.getChars(0, end, this.m_attrBuff, 0);
/*  863:1173 */     char[] chars = this.m_attrBuff;
/*  864:     */     
/*  865:1175 */     int cleanStart = 0;
/*  866:1176 */     int cleanLength = 0;
/*  867:     */     
/*  868:     */ 
/*  869:1179 */     char ch = '\000';
/*  870:1180 */     for (int i = 0; i < end; i++)
/*  871:     */     {
/*  872:1182 */       ch = chars[i];
/*  873:1184 */       if ((ch < ' ') || (ch > '~'))
/*  874:     */       {
/*  875:1186 */         if (cleanLength > 0)
/*  876:     */         {
/*  877:1188 */           writer.write(chars, cleanStart, cleanLength);
/*  878:1189 */           cleanLength = 0;
/*  879:     */         }
/*  880:1191 */         if (doURLEscaping)
/*  881:     */         {
/*  882:1203 */           if (ch <= '')
/*  883:     */           {
/*  884:1205 */             writer.write(37);
/*  885:1206 */             writer.write(makeHHString(ch));
/*  886:     */           }
/*  887:1208 */           else if (ch <= '߿')
/*  888:     */           {
/*  889:1212 */             int high = ch >> '\006' | 0xC0;
/*  890:1213 */             int low = ch & 0x3F | 0x80;
/*  891:     */             
/*  892:1215 */             writer.write(37);
/*  893:1216 */             writer.write(makeHHString(high));
/*  894:1217 */             writer.write(37);
/*  895:1218 */             writer.write(makeHHString(low));
/*  896:     */           }
/*  897:1220 */           else if (Encodings.isHighUTF16Surrogate(ch))
/*  898:     */           {
/*  899:1230 */             int highSurrogate = ch & 0x3FF;
/*  900:     */             
/*  901:     */ 
/*  902:     */ 
/*  903:     */ 
/*  904:     */ 
/*  905:1236 */             int wwww = (highSurrogate & 0x3C0) >> 6;
/*  906:1237 */             int uuuuu = wwww + 1;
/*  907:     */             
/*  908:     */ 
/*  909:1240 */             int zzzz = (highSurrogate & 0x3C) >> 2;
/*  910:     */             
/*  911:     */ 
/*  912:1243 */             int yyyyyy = (highSurrogate & 0x3) << 4 & 0x30;
/*  913:     */             
/*  914:     */ 
/*  915:1246 */             ch = chars[(++i)];
/*  916:     */             
/*  917:     */ 
/*  918:1249 */             int lowSurrogate = ch & 0x3FF;
/*  919:     */             
/*  920:     */ 
/*  921:1252 */             yyyyyy |= (lowSurrogate & 0x3C0) >> 6;
/*  922:     */             
/*  923:     */ 
/*  924:1255 */             int xxxxxx = lowSurrogate & 0x3F;
/*  925:     */             
/*  926:1257 */             int byte1 = 0xF0 | uuuuu >> 2;
/*  927:1258 */             int byte2 = 0x80 | (uuuuu & 0x3) << 4 & 0x30 | zzzz;
/*  928:     */             
/*  929:1260 */             int byte3 = 0x80 | yyyyyy;
/*  930:1261 */             int byte4 = 0x80 | xxxxxx;
/*  931:     */             
/*  932:1263 */             writer.write(37);
/*  933:1264 */             writer.write(makeHHString(byte1));
/*  934:1265 */             writer.write(37);
/*  935:1266 */             writer.write(makeHHString(byte2));
/*  936:1267 */             writer.write(37);
/*  937:1268 */             writer.write(makeHHString(byte3));
/*  938:1269 */             writer.write(37);
/*  939:1270 */             writer.write(makeHHString(byte4));
/*  940:     */           }
/*  941:     */           else
/*  942:     */           {
/*  943:1274 */             int high = ch >> '\f' | 0xE0;
/*  944:1275 */             int middle = (ch & 0xFC0) >> '\006' | 0x80;
/*  945:     */             
/*  946:1277 */             int low = ch & 0x3F | 0x80;
/*  947:     */             
/*  948:1279 */             writer.write(37);
/*  949:1280 */             writer.write(makeHHString(high));
/*  950:1281 */             writer.write(37);
/*  951:1282 */             writer.write(makeHHString(middle));
/*  952:1283 */             writer.write(37);
/*  953:1284 */             writer.write(makeHHString(low));
/*  954:     */           }
/*  955:     */         }
/*  956:1288 */         else if (escapingNotNeeded(ch))
/*  957:     */         {
/*  958:1290 */           writer.write(ch);
/*  959:     */         }
/*  960:     */         else
/*  961:     */         {
/*  962:1294 */           writer.write("&#");
/*  963:1295 */           writer.write(Integer.toString(ch));
/*  964:1296 */           writer.write(59);
/*  965:     */         }
/*  966:1302 */         cleanStart = i + 1;
/*  967:     */       }
/*  968:1307 */       else if (ch == '"')
/*  969:     */       {
/*  970:1319 */         if (cleanLength > 0)
/*  971:     */         {
/*  972:1321 */           writer.write(chars, cleanStart, cleanLength);
/*  973:1322 */           cleanLength = 0;
/*  974:     */         }
/*  975:1327 */         if (doURLEscaping) {
/*  976:1328 */           writer.write("%22");
/*  977:     */         } else {
/*  978:1330 */           writer.write("&quot;");
/*  979:     */         }
/*  980:1334 */         cleanStart = i + 1;
/*  981:     */       }
/*  982:1336 */       else if (ch == '&')
/*  983:     */       {
/*  984:1341 */         if (cleanLength > 0)
/*  985:     */         {
/*  986:1343 */           writer.write(chars, cleanStart, cleanLength);
/*  987:1344 */           cleanLength = 0;
/*  988:     */         }
/*  989:1346 */         writer.write("&amp;");
/*  990:1347 */         cleanStart = i + 1;
/*  991:     */       }
/*  992:     */       else
/*  993:     */       {
/*  994:1353 */         cleanLength++;
/*  995:     */       }
/*  996:     */     }
/*  997:1359 */     if (cleanLength > 1)
/*  998:     */     {
/*  999:1364 */       if (cleanStart == 0) {
/* 1000:1365 */         writer.write(string);
/* 1001:     */       } else {
/* 1002:1367 */         writer.write(chars, cleanStart, cleanLength);
/* 1003:     */       }
/* 1004:     */     }
/* 1005:1369 */     else if (cleanLength == 1) {
/* 1006:1373 */       writer.write(ch);
/* 1007:     */     }
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public void writeAttrString(Writer writer, String string, String encoding)
/* 1011:     */     throws IOException
/* 1012:     */   {
/* 1013:1390 */     int end = string.length();
/* 1014:1391 */     if (end > this.m_attrBuff.length) {
/* 1015:1393 */       this.m_attrBuff = new char[end * 2 + 1];
/* 1016:     */     }
/* 1017:1395 */     string.getChars(0, end, this.m_attrBuff, 0);
/* 1018:1396 */     char[] chars = this.m_attrBuff;
/* 1019:     */     
/* 1020:     */ 
/* 1021:     */ 
/* 1022:1400 */     int cleanStart = 0;
/* 1023:1401 */     int cleanLength = 0;
/* 1024:     */     
/* 1025:1403 */     char ch = '\000';
/* 1026:1404 */     for (int i = 0; i < end; i++)
/* 1027:     */     {
/* 1028:1406 */       ch = chars[i];
/* 1029:1412 */       if ((escapingNotNeeded(ch)) && (!this.m_charInfo.shouldMapAttrChar(ch)))
/* 1030:     */       {
/* 1031:1414 */         cleanLength++;
/* 1032:     */       }
/* 1033:1416 */       else if (('<' == ch) || ('>' == ch))
/* 1034:     */       {
/* 1035:1418 */         cleanLength++;
/* 1036:     */       }
/* 1037:1420 */       else if (('&' == ch) && (i + 1 < end) && ('{' == chars[(i + 1)]))
/* 1038:     */       {
/* 1039:1423 */         cleanLength++;
/* 1040:     */       }
/* 1041:     */       else
/* 1042:     */       {
/* 1043:1427 */         if (cleanLength > 0)
/* 1044:     */         {
/* 1045:1429 */           writer.write(chars, cleanStart, cleanLength);
/* 1046:1430 */           cleanLength = 0;
/* 1047:     */         }
/* 1048:1432 */         int pos = accumDefaultEntity(writer, ch, i, chars, end, false, true);
/* 1049:1434 */         if (i != pos)
/* 1050:     */         {
/* 1051:1436 */           i = pos - 1;
/* 1052:     */         }
/* 1053:     */         else
/* 1054:     */         {
/* 1055:1440 */           if (Encodings.isHighUTF16Surrogate(ch))
/* 1056:     */           {
/* 1057:1443 */             writeUTF16Surrogate(ch, chars, i, end);
/* 1058:1444 */             i++;
/* 1059:     */           }
/* 1060:1460 */           String outputStringForChar = this.m_charInfo.getOutputStringForChar(ch);
/* 1061:1461 */           if (null != outputStringForChar)
/* 1062:     */           {
/* 1063:1463 */             writer.write(outputStringForChar);
/* 1064:     */           }
/* 1065:1465 */           else if (escapingNotNeeded(ch))
/* 1066:     */           {
/* 1067:1467 */             writer.write(ch);
/* 1068:     */           }
/* 1069:     */           else
/* 1070:     */           {
/* 1071:1471 */             writer.write("&#");
/* 1072:1472 */             writer.write(Integer.toString(ch));
/* 1073:1473 */             writer.write(59);
/* 1074:     */           }
/* 1075:     */         }
/* 1076:1476 */         cleanStart = i + 1;
/* 1077:     */       }
/* 1078:     */     }
/* 1079:1482 */     if (cleanLength > 1)
/* 1080:     */     {
/* 1081:1487 */       if (cleanStart == 0) {
/* 1082:1488 */         writer.write(string);
/* 1083:     */       } else {
/* 1084:1490 */         writer.write(chars, cleanStart, cleanLength);
/* 1085:     */       }
/* 1086:     */     }
/* 1087:1492 */     else if (cleanLength == 1) {
/* 1088:1496 */       writer.write(ch);
/* 1089:     */     }
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public final void characters(char[] chars, int start, int length)
/* 1093:     */     throws SAXException
/* 1094:     */   {
/* 1095:1533 */     if (this.m_elemContext.m_isRaw) {
/* 1096:     */       try
/* 1097:     */       {
/* 1098:1538 */         if (this.m_elemContext.m_startTagOpen)
/* 1099:     */         {
/* 1100:1540 */           closeStartTag();
/* 1101:1541 */           this.m_elemContext.m_startTagOpen = false;
/* 1102:     */         }
/* 1103:1544 */         this.m_ispreserve = true;
/* 1104:     */         
/* 1105:1546 */         writeNormalizedChars(chars, start, length, false, this.m_lineSepUse);
/* 1106:1549 */         if (this.m_tracer != null) {
/* 1107:1550 */           super.fireCharEvent(chars, start, length);
/* 1108:     */         }
/* 1109:1552 */         return;
/* 1110:     */       }
/* 1111:     */       catch (IOException ioe)
/* 1112:     */       {
/* 1113:1556 */         throw new SAXException(Utils.messages.createMessage("ER_OIERROR", null), ioe);
/* 1114:     */       }
/* 1115:     */     }
/* 1116:1562 */     super.characters(chars, start, length);
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public final void cdata(char[] ch, int start, int length)
/* 1120:     */     throws SAXException
/* 1121:     */   {
/* 1122:1597 */     if ((null != this.m_elemContext.m_elementName) && ((this.m_elemContext.m_elementName.equalsIgnoreCase("SCRIPT")) || (this.m_elemContext.m_elementName.equalsIgnoreCase("STYLE")))) {
/* 1123:     */       try
/* 1124:     */       {
/* 1125:1603 */         if (this.m_elemContext.m_startTagOpen)
/* 1126:     */         {
/* 1127:1605 */           closeStartTag();
/* 1128:1606 */           this.m_elemContext.m_startTagOpen = false;
/* 1129:     */         }
/* 1130:1609 */         this.m_ispreserve = true;
/* 1131:1611 */         if (shouldIndent()) {
/* 1132:1612 */           indent();
/* 1133:     */         }
/* 1134:1615 */         writeNormalizedChars(ch, start, length, true, this.m_lineSepUse);
/* 1135:     */       }
/* 1136:     */       catch (IOException ioe)
/* 1137:     */       {
/* 1138:1619 */         throw new SAXException(Utils.messages.createMessage("ER_OIERROR", null), ioe);
/* 1139:     */       }
/* 1140:     */     } else {
/* 1141:1629 */       super.cdata(ch, start, length);
/* 1142:     */     }
/* 1143:     */   }
/* 1144:     */   
/* 1145:     */   public void processingInstruction(String target, String data)
/* 1146:     */     throws SAXException
/* 1147:     */   {
/* 1148:1649 */     flushPending();
/* 1149:1653 */     if (target.equals("javax.xml.transform.disable-output-escaping")) {
/* 1150:1655 */       startNonEscaping();
/* 1151:1657 */     } else if (target.equals("javax.xml.transform.enable-output-escaping")) {
/* 1152:1659 */       endNonEscaping();
/* 1153:     */     } else {
/* 1154:     */       try
/* 1155:     */       {
/* 1156:1666 */         if (this.m_elemContext.m_startTagOpen)
/* 1157:     */         {
/* 1158:1668 */           closeStartTag();
/* 1159:1669 */           this.m_elemContext.m_startTagOpen = false;
/* 1160:     */         }
/* 1161:1671 */         else if (this.m_cdataTagOpen)
/* 1162:     */         {
/* 1163:1673 */           closeCDATA();
/* 1164:     */         }
/* 1165:1675 */         else if (this.m_needToCallStartDocument)
/* 1166:     */         {
/* 1167:1677 */           startDocumentInternal();
/* 1168:     */         }
/* 1169:1687 */         if (true == this.m_needToOutputDocTypeDecl) {
/* 1170:1688 */           outputDocTypeDecl("html");
/* 1171:     */         }
/* 1172:1691 */         if (shouldIndent()) {
/* 1173:1692 */           indent();
/* 1174:     */         }
/* 1175:1694 */         Writer writer = this.m_writer;
/* 1176:     */         
/* 1177:1696 */         writer.write("<?");
/* 1178:1697 */         writer.write(target);
/* 1179:1699 */         if ((data.length() > 0) && (!Character.isSpaceChar(data.charAt(0)))) {
/* 1180:1700 */           writer.write(32);
/* 1181:     */         }
/* 1182:1703 */         writer.write(data);
/* 1183:1704 */         writer.write(62);
/* 1184:1709 */         if (this.m_elemContext.m_currentElemDepth <= 0) {
/* 1185:1710 */           outputLineSep();
/* 1186:     */         }
/* 1187:1712 */         this.m_startNewLine = true;
/* 1188:     */       }
/* 1189:     */       catch (IOException e)
/* 1190:     */       {
/* 1191:1716 */         throw new SAXException(e);
/* 1192:     */       }
/* 1193:     */     }
/* 1194:1721 */     if (this.m_tracer != null) {
/* 1195:1722 */       super.fireEscapingEvent(target, data);
/* 1196:     */     }
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public final void entityReference(String name)
/* 1200:     */     throws SAXException
/* 1201:     */   {
/* 1202:     */     try
/* 1203:     */     {
/* 1204:1738 */       Writer writer = this.m_writer;
/* 1205:1739 */       writer.write(38);
/* 1206:1740 */       writer.write(name);
/* 1207:1741 */       writer.write(59);
/* 1208:     */     }
/* 1209:     */     catch (IOException e)
/* 1210:     */     {
/* 1211:1745 */       throw new SAXException(e);
/* 1212:     */     }
/* 1213:     */   }
/* 1214:     */   
/* 1215:     */   public final void endElement(String elemName)
/* 1216:     */     throws SAXException
/* 1217:     */   {
/* 1218:1753 */     endElement(null, null, elemName);
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   public void processAttributes(Writer writer, int nAttrs)
/* 1222:     */     throws IOException, SAXException
/* 1223:     */   {
/* 1224:1773 */     for (int i = 0; i < nAttrs; i++) {
/* 1225:1775 */       processAttribute(writer, this.m_attributes.getQName(i), this.m_attributes.getValue(i), this.m_elemContext.m_elementDesc);
/* 1226:     */     }
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   protected void closeStartTag()
/* 1230:     */     throws SAXException
/* 1231:     */   {
/* 1232:     */     try
/* 1233:     */     {
/* 1234:1796 */       if (this.m_tracer != null) {
/* 1235:1797 */         super.fireStartElem(this.m_elemContext.m_elementName);
/* 1236:     */       }
/* 1237:1799 */       int nAttrs = this.m_attributes.getLength();
/* 1238:1800 */       if (nAttrs > 0)
/* 1239:     */       {
/* 1240:1802 */         processAttributes(this.m_writer, nAttrs);
/* 1241:     */         
/* 1242:1804 */         this.m_attributes.clear();
/* 1243:     */       }
/* 1244:1807 */       this.m_writer.write(62);
/* 1245:1813 */       if (this.m_CdataElems != null) {
/* 1246:1814 */         this.m_elemContext.m_isCdataSection = isCdataSection();
/* 1247:     */       }
/* 1248:1815 */       if (this.m_doIndent)
/* 1249:     */       {
/* 1250:1817 */         this.m_isprevtext = false;
/* 1251:1818 */         this.m_preserves.push(this.m_ispreserve);
/* 1252:     */       }
/* 1253:     */     }
/* 1254:     */     catch (IOException e)
/* 1255:     */     {
/* 1256:1824 */       throw new SAXException(e);
/* 1257:     */     }
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   public void namespaceAfterStartElement(String prefix, String uri)
/* 1261:     */     throws SAXException
/* 1262:     */   {
/* 1263:1845 */     if (this.m_elemContext.m_elementURI == null)
/* 1264:     */     {
/* 1265:1847 */       String prefix1 = SerializerBase.getPrefixPart(this.m_elemContext.m_elementName);
/* 1266:1848 */       if ((prefix1 == null) && ("".equals(prefix))) {
/* 1267:1854 */         this.m_elemContext.m_elementURI = uri;
/* 1268:     */       }
/* 1269:     */     }
/* 1270:1857 */     startPrefixMapping(prefix, uri, false);
/* 1271:     */   }
/* 1272:     */   
/* 1273:     */   public void startDTD(String name, String publicId, String systemId)
/* 1274:     */     throws SAXException
/* 1275:     */   {
/* 1276:1863 */     this.m_inDTD = true;
/* 1277:1864 */     super.startDTD(name, publicId, systemId);
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   public void endDTD()
/* 1281:     */     throws SAXException
/* 1282:     */   {
/* 1283:1874 */     this.m_inDTD = false;
/* 1284:     */   }
/* 1285:     */   
/* 1286:     */   public void addUniqueAttribute(String name, String value, int flags)
/* 1287:     */     throws SAXException
/* 1288:     */   {
/* 1289:     */     try
/* 1290:     */     {
/* 1291:1935 */       Writer writer = this.m_writer;
/* 1292:1936 */       if (((flags & 0x1) > 0) && (this.m_htmlcharInfo.onlyQuotAmpLtGt))
/* 1293:     */       {
/* 1294:1943 */         writer.write(32);
/* 1295:1944 */         writer.write(name);
/* 1296:1945 */         writer.write("=\"");
/* 1297:1946 */         writer.write(value);
/* 1298:1947 */         writer.write(34);
/* 1299:     */       }
/* 1300:1949 */       else if (((flags & 0x2) > 0) && ((value.length() == 0) || (value.equalsIgnoreCase(name))))
/* 1301:     */       {
/* 1302:1953 */         writer.write(32);
/* 1303:1954 */         writer.write(name);
/* 1304:     */       }
/* 1305:     */       else
/* 1306:     */       {
/* 1307:1958 */         writer.write(32);
/* 1308:1959 */         writer.write(name);
/* 1309:1960 */         writer.write("=\"");
/* 1310:1961 */         if ((flags & 0x4) > 0) {
/* 1311:1963 */           writeAttrURI(writer, value, this.m_specialEscapeURLs);
/* 1312:     */         } else {
/* 1313:1967 */           writeAttrString(writer, value, getEncoding());
/* 1314:     */         }
/* 1315:1969 */         writer.write(34);
/* 1316:     */       }
/* 1317:     */     }
/* 1318:     */     catch (IOException e)
/* 1319:     */     {
/* 1320:1972 */       throw new SAXException(e);
/* 1321:     */     }
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public void comment(char[] ch, int start, int length)
/* 1325:     */     throws SAXException
/* 1326:     */   {
/* 1327:1980 */     if (this.m_inDTD) {
/* 1328:1981 */       return;
/* 1329:     */     }
/* 1330:1987 */     if (this.m_elemContext.m_startTagOpen)
/* 1331:     */     {
/* 1332:1989 */       closeStartTag();
/* 1333:1990 */       this.m_elemContext.m_startTagOpen = false;
/* 1334:     */     }
/* 1335:1992 */     else if (this.m_cdataTagOpen)
/* 1336:     */     {
/* 1337:1994 */       closeCDATA();
/* 1338:     */     }
/* 1339:1996 */     else if (this.m_needToCallStartDocument)
/* 1340:     */     {
/* 1341:1998 */       startDocumentInternal();
/* 1342:     */     }
/* 1343:2006 */     if (this.m_needToOutputDocTypeDecl) {
/* 1344:2007 */       outputDocTypeDecl("html");
/* 1345:     */     }
/* 1346:2009 */     super.comment(ch, start, length);
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   public boolean reset()
/* 1350:     */   {
/* 1351:2014 */     boolean ret = super.reset();
/* 1352:2015 */     if (!ret) {
/* 1353:2016 */       return false;
/* 1354:     */     }
/* 1355:2017 */     resetToHTMLStream();
/* 1356:2018 */     return true;
/* 1357:     */   }
/* 1358:     */   
/* 1359:     */   private void resetToHTMLStream()
/* 1360:     */   {
/* 1361:2025 */     this.m_inBlockElem = false;
/* 1362:2026 */     this.m_inDTD = false;
/* 1363:2027 */     this.m_omitMetaTag = false;
/* 1364:2028 */     this.m_specialEscapeURLs = true;
/* 1365:     */   }
/* 1366:     */   
/* 1367:     */   public void attributeDecl(String eName, String aName, String type, String valueDefault, String value)
/* 1368:     */     throws SAXException
/* 1369:     */   {}
/* 1370:     */   
/* 1371:     */   public void elementDecl(String name, String model)
/* 1372:     */     throws SAXException
/* 1373:     */   {}
/* 1374:     */   
/* 1375:     */   public void internalEntityDecl(String name, String value)
/* 1376:     */     throws SAXException
/* 1377:     */   {}
/* 1378:     */   
/* 1379:     */   public void externalEntityDecl(String name, String publicId, String systemId)
/* 1380:     */     throws SAXException
/* 1381:     */   {}
/* 1382:     */   
/* 1383:     */   static class Trie
/* 1384:     */   {
/* 1385:     */     public static final int ALPHA_SIZE = 128;
/* 1386:     */     final Node m_Root;
/* 1387:2057 */     private char[] m_charBuffer = new char[0];
/* 1388:     */     private final boolean m_lowerCaseOnly;
/* 1389:     */     
/* 1390:     */     public Trie()
/* 1391:     */     {
/* 1392:2067 */       this.m_Root = new Node();
/* 1393:2068 */       this.m_lowerCaseOnly = false;
/* 1394:     */     }
/* 1395:     */     
/* 1396:     */     public Trie(boolean lowerCaseOnly)
/* 1397:     */     {
/* 1398:2078 */       this.m_Root = new Node();
/* 1399:2079 */       this.m_lowerCaseOnly = lowerCaseOnly;
/* 1400:     */     }
/* 1401:     */     
/* 1402:     */     public Object put(String key, Object value)
/* 1403:     */     {
/* 1404:2093 */       int len = key.length();
/* 1405:2094 */       if (len > this.m_charBuffer.length) {
/* 1406:2097 */         this.m_charBuffer = new char[len];
/* 1407:     */       }
/* 1408:2100 */       Node node = this.m_Root;
/* 1409:2102 */       for (int i = 0; i < len; i++)
/* 1410:     */       {
/* 1411:2104 */         Node nextNode = node.m_nextChar[Character.toLowerCase(key.charAt(i))];
/* 1412:2107 */         if (nextNode != null)
/* 1413:     */         {
/* 1414:2109 */           node = nextNode;
/* 1415:     */         }
/* 1416:     */         else
/* 1417:     */         {
/* 1418:2113 */           for (; i < len; i++)
/* 1419:     */           {
/* 1420:2115 */             Node newNode = new Node();
/* 1421:2116 */             if (this.m_lowerCaseOnly)
/* 1422:     */             {
/* 1423:2119 */               node.m_nextChar[Character.toLowerCase(key.charAt(i))] = newNode;
/* 1424:     */             }
/* 1425:     */             else
/* 1426:     */             {
/* 1427:2126 */               node.m_nextChar[Character.toUpperCase(key.charAt(i))] = newNode;
/* 1428:     */               
/* 1429:     */ 
/* 1430:2129 */               node.m_nextChar[Character.toLowerCase(key.charAt(i))] = newNode;
/* 1431:     */             }
/* 1432:2133 */             node = newNode;
/* 1433:     */           }
/* 1434:2135 */           break;
/* 1435:     */         }
/* 1436:     */       }
/* 1437:2139 */       Object ret = node.m_Value;
/* 1438:     */       
/* 1439:2141 */       node.m_Value = value;
/* 1440:     */       
/* 1441:2143 */       return ret;
/* 1442:     */     }
/* 1443:     */     
/* 1444:     */     public Object get(String key)
/* 1445:     */     {
/* 1446:2156 */       int len = key.length();
/* 1447:2161 */       if (this.m_charBuffer.length < len) {
/* 1448:2162 */         return null;
/* 1449:     */       }
/* 1450:2164 */       Node node = this.m_Root;
/* 1451:2165 */       switch (len)
/* 1452:     */       {
/* 1453:     */       case 0: 
/* 1454:2172 */         return null;
/* 1455:     */       case 1: 
/* 1456:2177 */         char ch = key.charAt(0);
/* 1457:2178 */         if (ch < '')
/* 1458:     */         {
/* 1459:2180 */           node = node.m_nextChar[ch];
/* 1460:2181 */           if (node != null) {
/* 1461:2182 */             return node.m_Value;
/* 1462:     */           }
/* 1463:     */         }
/* 1464:2184 */         return null;
/* 1465:     */       }
/* 1466:2209 */       for (int i = 0; i < len; i++)
/* 1467:     */       {
/* 1468:2212 */         char ch = key.charAt(i);
/* 1469:2213 */         if ('' <= ch) {
/* 1470:2216 */           return null;
/* 1471:     */         }
/* 1472:2219 */         node = node.m_nextChar[ch];
/* 1473:2220 */         if (node == null) {
/* 1474:2221 */           return null;
/* 1475:     */         }
/* 1476:     */       }
/* 1477:2224 */       return node.m_Value;
/* 1478:     */     }
/* 1479:     */     
/* 1480:     */     private class Node
/* 1481:     */     {
/* 1482:     */       final Node[] m_nextChar;
/* 1483:     */       Object m_Value;
/* 1484:     */       
/* 1485:     */       Node()
/* 1486:     */       {
/* 1487:2241 */         this.m_nextChar = new Node[''];
/* 1488:2242 */         this.m_Value = null;
/* 1489:     */       }
/* 1490:     */     }
/* 1491:     */     
/* 1492:     */     public Trie(Trie existingTrie)
/* 1493:     */     {
/* 1494:2262 */       this.m_Root = existingTrie.m_Root;
/* 1495:2263 */       this.m_lowerCaseOnly = existingTrie.m_lowerCaseOnly;
/* 1496:     */       
/* 1497:     */ 
/* 1498:2266 */       int max = existingTrie.getLongestKeyLength();
/* 1499:2267 */       this.m_charBuffer = new char[max];
/* 1500:     */     }
/* 1501:     */     
/* 1502:     */     public Object get2(String key)
/* 1503:     */     {
/* 1504:2281 */       int len = key.length();
/* 1505:2286 */       if (this.m_charBuffer.length < len) {
/* 1506:2287 */         return null;
/* 1507:     */       }
/* 1508:2289 */       Node node = this.m_Root;
/* 1509:2290 */       switch (len)
/* 1510:     */       {
/* 1511:     */       case 0: 
/* 1512:2297 */         return null;
/* 1513:     */       case 1: 
/* 1514:2302 */         char ch = key.charAt(0);
/* 1515:2303 */         if (ch < '')
/* 1516:     */         {
/* 1517:2305 */           node = node.m_nextChar[ch];
/* 1518:2306 */           if (node != null) {
/* 1519:2307 */             return node.m_Value;
/* 1520:     */           }
/* 1521:     */         }
/* 1522:2309 */         return null;
/* 1523:     */       }
/* 1524:2321 */       key.getChars(0, len, this.m_charBuffer, 0);
/* 1525:2323 */       for (int i = 0; i < len; i++)
/* 1526:     */       {
/* 1527:2325 */         char ch = this.m_charBuffer[i];
/* 1528:2326 */         if ('' <= ch) {
/* 1529:2329 */           return null;
/* 1530:     */         }
/* 1531:2332 */         node = node.m_nextChar[ch];
/* 1532:2333 */         if (node == null) {
/* 1533:2334 */           return null;
/* 1534:     */         }
/* 1535:     */       }
/* 1536:2337 */       return node.m_Value;
/* 1537:     */     }
/* 1538:     */     
/* 1539:     */     public int getLongestKeyLength()
/* 1540:     */     {
/* 1541:2347 */       return this.m_charBuffer.length;
/* 1542:     */     }
/* 1543:     */   }
/* 1544:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ToHTMLStream
 * JD-Core Version:    0.7.0.1
 */