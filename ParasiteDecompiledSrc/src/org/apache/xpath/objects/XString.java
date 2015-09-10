/*    1:     */ package org.apache.xpath.objects;
/*    2:     */ 
/*    3:     */ import java.util.Locale;
/*    4:     */ import javax.xml.transform.TransformerException;
/*    5:     */ import org.apache.xml.dtm.DTM;
/*    6:     */ import org.apache.xml.utils.WrappedRuntimeException;
/*    7:     */ import org.apache.xml.utils.XMLCharacterRecognizer;
/*    8:     */ import org.apache.xml.utils.XMLString;
/*    9:     */ import org.apache.xml.utils.XMLStringFactory;
/*   10:     */ import org.apache.xpath.ExpressionOwner;
/*   11:     */ import org.apache.xpath.XPathContext;
/*   12:     */ import org.apache.xpath.XPathVisitor;
/*   13:     */ import org.xml.sax.ContentHandler;
/*   14:     */ import org.xml.sax.SAXException;
/*   15:     */ import org.xml.sax.ext.LexicalHandler;
/*   16:     */ 
/*   17:     */ public class XString
/*   18:     */   extends XObject
/*   19:     */   implements XMLString
/*   20:     */ {
/*   21:     */   static final long serialVersionUID = 2020470518395094525L;
/*   22:  43 */   public static final XString EMPTYSTRING = new XString("");
/*   23:     */   
/*   24:     */   protected XString(Object val)
/*   25:     */   {
/*   26:  52 */     super(val);
/*   27:     */   }
/*   28:     */   
/*   29:     */   public XString(String val)
/*   30:     */   {
/*   31:  62 */     super(val);
/*   32:     */   }
/*   33:     */   
/*   34:     */   public int getType()
/*   35:     */   {
/*   36:  72 */     return 3;
/*   37:     */   }
/*   38:     */   
/*   39:     */   public String getTypeString()
/*   40:     */   {
/*   41:  83 */     return "#STRING";
/*   42:     */   }
/*   43:     */   
/*   44:     */   public boolean hasString()
/*   45:     */   {
/*   46:  93 */     return true;
/*   47:     */   }
/*   48:     */   
/*   49:     */   public double num()
/*   50:     */   {
/*   51: 104 */     return toDouble();
/*   52:     */   }
/*   53:     */   
/*   54:     */   public double toDouble()
/*   55:     */   {
/*   56: 122 */     XMLString s = trim();
/*   57: 123 */     double result = (0.0D / 0.0D);
/*   58: 124 */     for (int i = 0; i < s.length(); i++)
/*   59:     */     {
/*   60: 126 */       char c = s.charAt(i);
/*   61: 127 */       if ((c != '-') && (c != '.') && ((c < '0') || (c > '9'))) {
/*   62: 130 */         return result;
/*   63:     */       }
/*   64:     */     }
/*   65:     */     try
/*   66:     */     {
/*   67: 135 */       result = Double.parseDouble(s.toString());
/*   68:     */     }
/*   69:     */     catch (NumberFormatException e) {}
/*   70: 138 */     return result;
/*   71:     */   }
/*   72:     */   
/*   73:     */   public boolean bool()
/*   74:     */   {
/*   75: 149 */     return str().length() > 0;
/*   76:     */   }
/*   77:     */   
/*   78:     */   public XMLString xstr()
/*   79:     */   {
/*   80: 159 */     return this;
/*   81:     */   }
/*   82:     */   
/*   83:     */   public String str()
/*   84:     */   {
/*   85: 169 */     return null != this.m_obj ? (String)this.m_obj : "";
/*   86:     */   }
/*   87:     */   
/*   88:     */   public int rtf(XPathContext support)
/*   89:     */   {
/*   90: 182 */     DTM frag = support.createDocumentFragment();
/*   91:     */     
/*   92: 184 */     frag.appendTextChild(str());
/*   93:     */     
/*   94: 186 */     return frag.getDocument();
/*   95:     */   }
/*   96:     */   
/*   97:     */   public void dispatchCharactersEvents(ContentHandler ch)
/*   98:     */     throws SAXException
/*   99:     */   {
/*  100: 204 */     String str = str();
/*  101:     */     
/*  102: 206 */     ch.characters(str.toCharArray(), 0, str.length());
/*  103:     */   }
/*  104:     */   
/*  105:     */   public void dispatchAsComment(LexicalHandler lh)
/*  106:     */     throws SAXException
/*  107:     */   {
/*  108: 222 */     String str = str();
/*  109:     */     
/*  110: 224 */     lh.comment(str.toCharArray(), 0, str.length());
/*  111:     */   }
/*  112:     */   
/*  113:     */   public int length()
/*  114:     */   {
/*  115: 235 */     return str().length();
/*  116:     */   }
/*  117:     */   
/*  118:     */   public char charAt(int index)
/*  119:     */   {
/*  120: 253 */     return str().charAt(index);
/*  121:     */   }
/*  122:     */   
/*  123:     */   public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
/*  124:     */   {
/*  125: 279 */     str().getChars(srcBegin, srcEnd, dst, dstBegin);
/*  126:     */   }
/*  127:     */   
/*  128:     */   public boolean equals(XObject obj2)
/*  129:     */   {
/*  130: 297 */     int t = obj2.getType();
/*  131:     */     try
/*  132:     */     {
/*  133: 300 */       if (4 == t) {
/*  134: 301 */         return obj2.equals(this);
/*  135:     */       }
/*  136: 305 */       if (1 == t) {
/*  137: 306 */         return obj2.bool() == bool();
/*  138:     */       }
/*  139: 309 */       if (2 == t) {
/*  140: 310 */         return obj2.num() == num();
/*  141:     */       }
/*  142:     */     }
/*  143:     */     catch (TransformerException te)
/*  144:     */     {
/*  145: 314 */       throw new WrappedRuntimeException(te);
/*  146:     */     }
/*  147: 319 */     return xstr().equals(obj2.xstr());
/*  148:     */   }
/*  149:     */   
/*  150:     */   public boolean equals(String obj2)
/*  151:     */   {
/*  152: 335 */     return str().equals(obj2);
/*  153:     */   }
/*  154:     */   
/*  155:     */   public boolean equals(XMLString obj2)
/*  156:     */   {
/*  157: 353 */     if (obj2 != null)
/*  158:     */     {
/*  159: 354 */       if (!obj2.hasString()) {
/*  160: 355 */         return obj2.equals(str());
/*  161:     */       }
/*  162: 357 */       return str().equals(obj2.toString());
/*  163:     */     }
/*  164: 360 */     return false;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public boolean equals(Object obj2)
/*  168:     */   {
/*  169: 379 */     if (null == obj2) {
/*  170: 380 */       return false;
/*  171:     */     }
/*  172: 385 */     if ((obj2 instanceof XNodeSet)) {
/*  173: 386 */       return obj2.equals(this);
/*  174:     */     }
/*  175: 387 */     if ((obj2 instanceof XNumber)) {
/*  176: 388 */       return obj2.equals(this);
/*  177:     */     }
/*  178: 390 */     return str().equals(obj2.toString());
/*  179:     */   }
/*  180:     */   
/*  181:     */   public boolean equalsIgnoreCase(String anotherString)
/*  182:     */   {
/*  183: 410 */     return str().equalsIgnoreCase(anotherString);
/*  184:     */   }
/*  185:     */   
/*  186:     */   public int compareTo(XMLString xstr)
/*  187:     */   {
/*  188: 429 */     int len1 = length();
/*  189: 430 */     int len2 = xstr.length();
/*  190: 431 */     int n = Math.min(len1, len2);
/*  191: 432 */     int i = 0;
/*  192: 433 */     int j = 0;
/*  193: 435 */     while (n-- != 0)
/*  194:     */     {
/*  195: 437 */       char c1 = charAt(i);
/*  196: 438 */       char c2 = xstr.charAt(j);
/*  197: 440 */       if (c1 != c2) {
/*  198: 442 */         return c1 - c2;
/*  199:     */       }
/*  200: 445 */       i++;
/*  201: 446 */       j++;
/*  202:     */     }
/*  203: 449 */     return len1 - len2;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public int compareToIgnoreCase(XMLString str)
/*  207:     */   {
/*  208: 480 */     throw new WrappedRuntimeException(new NoSuchMethodException("Java 1.2 method, not yet implemented"));
/*  209:     */   }
/*  210:     */   
/*  211:     */   public boolean startsWith(String prefix, int toffset)
/*  212:     */   {
/*  213: 506 */     return str().startsWith(prefix, toffset);
/*  214:     */   }
/*  215:     */   
/*  216:     */   public boolean startsWith(String prefix)
/*  217:     */   {
/*  218: 525 */     return startsWith(prefix, 0);
/*  219:     */   }
/*  220:     */   
/*  221:     */   public boolean startsWith(XMLString prefix, int toffset)
/*  222:     */   {
/*  223: 550 */     int to = toffset;
/*  224: 551 */     int tlim = length();
/*  225: 552 */     int po = 0;
/*  226: 553 */     int pc = prefix.length();
/*  227: 556 */     if ((toffset < 0) || (toffset > tlim - pc)) {
/*  228: 558 */       return false;
/*  229:     */     }
/*  230:     */     do
/*  231:     */     {
/*  232: 563 */       if (charAt(to) != prefix.charAt(po)) {
/*  233: 565 */         return false;
/*  234:     */       }
/*  235: 568 */       to++;
/*  236: 569 */       po++;pc--;
/*  237: 561 */     } while (pc >= 0);
/*  238: 572 */     return true;
/*  239:     */   }
/*  240:     */   
/*  241:     */   public boolean startsWith(XMLString prefix)
/*  242:     */   {
/*  243: 591 */     return startsWith(prefix, 0);
/*  244:     */   }
/*  245:     */   
/*  246:     */   public boolean endsWith(String suffix)
/*  247:     */   {
/*  248: 609 */     return str().endsWith(suffix);
/*  249:     */   }
/*  250:     */   
/*  251:     */   public int hashCode()
/*  252:     */   {
/*  253: 627 */     return str().hashCode();
/*  254:     */   }
/*  255:     */   
/*  256:     */   public int indexOf(int ch)
/*  257:     */   {
/*  258: 649 */     return str().indexOf(ch);
/*  259:     */   }
/*  260:     */   
/*  261:     */   public int indexOf(int ch, int fromIndex)
/*  262:     */   {
/*  263: 682 */     return str().indexOf(ch, fromIndex);
/*  264:     */   }
/*  265:     */   
/*  266:     */   public int lastIndexOf(int ch)
/*  267:     */   {
/*  268: 702 */     return str().lastIndexOf(ch);
/*  269:     */   }
/*  270:     */   
/*  271:     */   public int lastIndexOf(int ch, int fromIndex)
/*  272:     */   {
/*  273: 730 */     return str().lastIndexOf(ch, fromIndex);
/*  274:     */   }
/*  275:     */   
/*  276:     */   public int indexOf(String str)
/*  277:     */   {
/*  278: 752 */     return str().indexOf(str);
/*  279:     */   }
/*  280:     */   
/*  281:     */   public int indexOf(XMLString str)
/*  282:     */   {
/*  283: 774 */     return str().indexOf(str.toString());
/*  284:     */   }
/*  285:     */   
/*  286:     */   public int indexOf(String str, int fromIndex)
/*  287:     */   {
/*  288: 805 */     return str().indexOf(str, fromIndex);
/*  289:     */   }
/*  290:     */   
/*  291:     */   public int lastIndexOf(String str)
/*  292:     */   {
/*  293: 828 */     return str().lastIndexOf(str);
/*  294:     */   }
/*  295:     */   
/*  296:     */   public int lastIndexOf(String str, int fromIndex)
/*  297:     */   {
/*  298: 853 */     return str().lastIndexOf(str, fromIndex);
/*  299:     */   }
/*  300:     */   
/*  301:     */   public XMLString substring(int beginIndex)
/*  302:     */   {
/*  303: 875 */     return new XString(str().substring(beginIndex));
/*  304:     */   }
/*  305:     */   
/*  306:     */   public XMLString substring(int beginIndex, int endIndex)
/*  307:     */   {
/*  308: 896 */     return new XString(str().substring(beginIndex, endIndex));
/*  309:     */   }
/*  310:     */   
/*  311:     */   public XMLString concat(String str)
/*  312:     */   {
/*  313: 913 */     return new XString(str().concat(str));
/*  314:     */   }
/*  315:     */   
/*  316:     */   public XMLString toLowerCase(Locale locale)
/*  317:     */   {
/*  318: 927 */     return new XString(str().toLowerCase(locale));
/*  319:     */   }
/*  320:     */   
/*  321:     */   public XMLString toLowerCase()
/*  322:     */   {
/*  323: 942 */     return new XString(str().toLowerCase());
/*  324:     */   }
/*  325:     */   
/*  326:     */   public XMLString toUpperCase(Locale locale)
/*  327:     */   {
/*  328: 955 */     return new XString(str().toUpperCase(locale));
/*  329:     */   }
/*  330:     */   
/*  331:     */   public XMLString toUpperCase()
/*  332:     */   {
/*  333: 986 */     return new XString(str().toUpperCase());
/*  334:     */   }
/*  335:     */   
/*  336:     */   public XMLString trim()
/*  337:     */   {
/*  338: 996 */     return new XString(str().trim());
/*  339:     */   }
/*  340:     */   
/*  341:     */   private static boolean isSpace(char ch)
/*  342:     */   {
/*  343:1008 */     return XMLCharacterRecognizer.isWhiteSpace(ch);
/*  344:     */   }
/*  345:     */   
/*  346:     */   public XMLString fixWhiteSpace(boolean trimHead, boolean trimTail, boolean doublePunctuationSpaces)
/*  347:     */   {
/*  348:1030 */     int len = length();
/*  349:1031 */     char[] buf = new char[len];
/*  350:     */     
/*  351:1033 */     getChars(0, len, buf, 0);
/*  352:     */     
/*  353:1035 */     boolean edit = false;
/*  354:1038 */     for (int s = 0; s < len; s++) {
/*  355:1040 */       if (isSpace(buf[s])) {
/*  356:     */         break;
/*  357:     */       }
/*  358:     */     }
/*  359:1047 */     int d = s;
/*  360:1048 */     boolean pres = false;
/*  361:1050 */     for (; s < len; s++)
/*  362:     */     {
/*  363:1052 */       char c = buf[s];
/*  364:1054 */       if (isSpace(c))
/*  365:     */       {
/*  366:1056 */         if (!pres)
/*  367:     */         {
/*  368:1058 */           if (' ' != c) {
/*  369:1060 */             edit = true;
/*  370:     */           }
/*  371:1063 */           buf[(d++)] = ' ';
/*  372:1065 */           if ((doublePunctuationSpaces) && (s != 0))
/*  373:     */           {
/*  374:1067 */             char prevChar = buf[(s - 1)];
/*  375:1069 */             if ((prevChar != '.') && (prevChar != '!') && (prevChar != '?')) {
/*  376:1072 */               pres = true;
/*  377:     */             }
/*  378:     */           }
/*  379:     */           else
/*  380:     */           {
/*  381:1077 */             pres = true;
/*  382:     */           }
/*  383:     */         }
/*  384:     */         else
/*  385:     */         {
/*  386:1082 */           edit = true;
/*  387:1083 */           pres = true;
/*  388:     */         }
/*  389:     */       }
/*  390:     */       else
/*  391:     */       {
/*  392:1088 */         buf[(d++)] = c;
/*  393:1089 */         pres = false;
/*  394:     */       }
/*  395:     */     }
/*  396:1093 */     if ((trimTail) && (1 <= d) && (' ' == buf[(d - 1)]))
/*  397:     */     {
/*  398:1095 */       edit = true;
/*  399:     */       
/*  400:1097 */       d--;
/*  401:     */     }
/*  402:1100 */     int start = 0;
/*  403:1102 */     if ((trimHead) && (0 < d) && (' ' == buf[0]))
/*  404:     */     {
/*  405:1104 */       edit = true;
/*  406:     */       
/*  407:1106 */       start++;
/*  408:     */     }
/*  409:1109 */     XMLStringFactory xsf = XMLStringFactoryImpl.getFactory();
/*  410:     */     
/*  411:1111 */     return edit ? xsf.newstr(new String(buf, start, d - start)) : this;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/*  415:     */   {
/*  416:1119 */     visitor.visitStringLiteral(owner, this);
/*  417:     */   }
/*  418:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.XString
 * JD-Core Version:    0.7.0.1
 */