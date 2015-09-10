/*    1:     */ package org.apache.commons.lang;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.StringWriter;
/*    5:     */ import java.io.Writer;
/*    6:     */ import java.util.HashMap;
/*    7:     */ import java.util.Map;
/*    8:     */ import java.util.TreeMap;
/*    9:     */ 
/*   10:     */ class Entities
/*   11:     */ {
/*   12:  45 */   private static final String[][] BASIC_ARRAY = { { "quot", "34" }, { "amp", "38" }, { "lt", "60" }, { "gt", "62" } };
/*   13:  51 */   private static final String[][] APOS_ARRAY = { { "apos", "39" } };
/*   14:  55 */   static final String[][] ISO8859_1_ARRAY = { { "nbsp", "160" }, { "iexcl", "161" }, { "cent", "162" }, { "pound", "163" }, { "curren", "164" }, { "yen", "165" }, { "brvbar", "166" }, { "sect", "167" }, { "uml", "168" }, { "copy", "169" }, { "ordf", "170" }, { "laquo", "171" }, { "not", "172" }, { "shy", "173" }, { "reg", "174" }, { "macr", "175" }, { "deg", "176" }, { "plusmn", "177" }, { "sup2", "178" }, { "sup3", "179" }, { "acute", "180" }, { "micro", "181" }, { "para", "182" }, { "middot", "183" }, { "cedil", "184" }, { "sup1", "185" }, { "ordm", "186" }, { "raquo", "187" }, { "frac14", "188" }, { "frac12", "189" }, { "frac34", "190" }, { "iquest", "191" }, { "Agrave", "192" }, { "Aacute", "193" }, { "Acirc", "194" }, { "Atilde", "195" }, { "Auml", "196" }, { "Aring", "197" }, { "AElig", "198" }, { "Ccedil", "199" }, { "Egrave", "200" }, { "Eacute", "201" }, { "Ecirc", "202" }, { "Euml", "203" }, { "Igrave", "204" }, { "Iacute", "205" }, { "Icirc", "206" }, { "Iuml", "207" }, { "ETH", "208" }, { "Ntilde", "209" }, { "Ograve", "210" }, { "Oacute", "211" }, { "Ocirc", "212" }, { "Otilde", "213" }, { "Ouml", "214" }, { "times", "215" }, { "Oslash", "216" }, { "Ugrave", "217" }, { "Uacute", "218" }, { "Ucirc", "219" }, { "Uuml", "220" }, { "Yacute", "221" }, { "THORN", "222" }, { "szlig", "223" }, { "agrave", "224" }, { "aacute", "225" }, { "acirc", "226" }, { "atilde", "227" }, { "auml", "228" }, { "aring", "229" }, { "aelig", "230" }, { "ccedil", "231" }, { "egrave", "232" }, { "eacute", "233" }, { "ecirc", "234" }, { "euml", "235" }, { "igrave", "236" }, { "iacute", "237" }, { "icirc", "238" }, { "iuml", "239" }, { "eth", "240" }, { "ntilde", "241" }, { "ograve", "242" }, { "oacute", "243" }, { "ocirc", "244" }, { "otilde", "245" }, { "ouml", "246" }, { "divide", "247" }, { "oslash", "248" }, { "ugrave", "249" }, { "uacute", "250" }, { "ucirc", "251" }, { "uuml", "252" }, { "yacute", "253" }, { "thorn", "254" }, { "yuml", "255" } };
/*   15: 155 */   static final String[][] HTML40_ARRAY = { { "fnof", "402" }, { "Alpha", "913" }, { "Beta", "914" }, { "Gamma", "915" }, { "Delta", "916" }, { "Epsilon", "917" }, { "Zeta", "918" }, { "Eta", "919" }, { "Theta", "920" }, { "Iota", "921" }, { "Kappa", "922" }, { "Lambda", "923" }, { "Mu", "924" }, { "Nu", "925" }, { "Xi", "926" }, { "Omicron", "927" }, { "Pi", "928" }, { "Rho", "929" }, { "Sigma", "931" }, { "Tau", "932" }, { "Upsilon", "933" }, { "Phi", "934" }, { "Chi", "935" }, { "Psi", "936" }, { "Omega", "937" }, { "alpha", "945" }, { "beta", "946" }, { "gamma", "947" }, { "delta", "948" }, { "epsilon", "949" }, { "zeta", "950" }, { "eta", "951" }, { "theta", "952" }, { "iota", "953" }, { "kappa", "954" }, { "lambda", "955" }, { "mu", "956" }, { "nu", "957" }, { "xi", "958" }, { "omicron", "959" }, { "pi", "960" }, { "rho", "961" }, { "sigmaf", "962" }, { "sigma", "963" }, { "tau", "964" }, { "upsilon", "965" }, { "phi", "966" }, { "chi", "967" }, { "psi", "968" }, { "omega", "969" }, { "thetasym", "977" }, { "upsih", "978" }, { "piv", "982" }, { "bull", "8226" }, { "hellip", "8230" }, { "prime", "8242" }, { "Prime", "8243" }, { "oline", "8254" }, { "frasl", "8260" }, { "weierp", "8472" }, { "image", "8465" }, { "real", "8476" }, { "trade", "8482" }, { "alefsym", "8501" }, { "larr", "8592" }, { "uarr", "8593" }, { "rarr", "8594" }, { "darr", "8595" }, { "harr", "8596" }, { "crarr", "8629" }, { "lArr", "8656" }, { "uArr", "8657" }, { "rArr", "8658" }, { "dArr", "8659" }, { "hArr", "8660" }, { "forall", "8704" }, { "part", "8706" }, { "exist", "8707" }, { "empty", "8709" }, { "nabla", "8711" }, { "isin", "8712" }, { "notin", "8713" }, { "ni", "8715" }, { "prod", "8719" }, { "sum", "8721" }, { "minus", "8722" }, { "lowast", "8727" }, { "radic", "8730" }, { "prop", "8733" }, { "infin", "8734" }, { "ang", "8736" }, { "and", "8743" }, { "or", "8744" }, { "cap", "8745" }, { "cup", "8746" }, { "int", "8747" }, { "there4", "8756" }, { "sim", "8764" }, { "cong", "8773" }, { "asymp", "8776" }, { "ne", "8800" }, { "equiv", "8801" }, { "le", "8804" }, { "ge", "8805" }, { "sub", "8834" }, { "sup", "8835" }, { "sube", "8838" }, { "supe", "8839" }, { "oplus", "8853" }, { "otimes", "8855" }, { "perp", "8869" }, { "sdot", "8901" }, { "lceil", "8968" }, { "rceil", "8969" }, { "lfloor", "8970" }, { "rfloor", "8971" }, { "lang", "9001" }, { "rang", "9002" }, { "loz", "9674" }, { "spades", "9824" }, { "clubs", "9827" }, { "hearts", "9829" }, { "diams", "9830" }, { "OElig", "338" }, { "oelig", "339" }, { "Scaron", "352" }, { "scaron", "353" }, { "Yuml", "376" }, { "circ", "710" }, { "tilde", "732" }, { "ensp", "8194" }, { "emsp", "8195" }, { "thinsp", "8201" }, { "zwnj", "8204" }, { "zwj", "8205" }, { "lrm", "8206" }, { "rlm", "8207" }, { "ndash", "8211" }, { "mdash", "8212" }, { "lsquo", "8216" }, { "rsquo", "8217" }, { "sbquo", "8218" }, { "ldquo", "8220" }, { "rdquo", "8221" }, { "bdquo", "8222" }, { "dagger", "8224" }, { "Dagger", "8225" }, { "permil", "8240" }, { "lsaquo", "8249" }, { "rsaquo", "8250" }, { "euro", "8364" } };
/*   16:     */   public static final Entities XML;
/*   17:     */   public static final Entities HTML32;
/*   18:     */   public static final Entities HTML40;
/*   19:     */   private final EntityMap map;
/*   20:     */   
/*   21:     */   static
/*   22:     */   {
/*   23: 374 */     Entities xml = new Entities();
/*   24: 375 */     xml.addEntities(BASIC_ARRAY);
/*   25: 376 */     xml.addEntities(APOS_ARRAY);
/*   26: 377 */     XML = xml;
/*   27:     */     
/*   28:     */ 
/*   29:     */ 
/*   30: 381 */     Entities html32 = new Entities();
/*   31: 382 */     html32.addEntities(BASIC_ARRAY);
/*   32: 383 */     html32.addEntities(ISO8859_1_ARRAY);
/*   33: 384 */     HTML32 = html32;
/*   34:     */     
/*   35:     */ 
/*   36:     */ 
/*   37: 388 */     Entities html40 = new Entities();
/*   38: 389 */     fillWithHtml40Entities(html40);
/*   39: 390 */     HTML40 = html40;
/*   40:     */   }
/*   41:     */   
/*   42:     */   static void fillWithHtml40Entities(Entities entities)
/*   43:     */   {
/*   44: 402 */     entities.addEntities(BASIC_ARRAY);
/*   45: 403 */     entities.addEntities(ISO8859_1_ARRAY);
/*   46: 404 */     entities.addEntities(HTML40_ARRAY);
/*   47:     */   }
/*   48:     */   
/*   49:     */   static abstract interface EntityMap
/*   50:     */   {
/*   51:     */     public abstract void add(String paramString, int paramInt);
/*   52:     */     
/*   53:     */     public abstract String name(int paramInt);
/*   54:     */     
/*   55:     */     public abstract int value(String paramString);
/*   56:     */   }
/*   57:     */   
/*   58:     */   static class PrimitiveEntityMap
/*   59:     */     implements Entities.EntityMap
/*   60:     */   {
/*   61: 444 */     private final Map mapNameToValue = new HashMap();
/*   62: 446 */     private final IntHashMap mapValueToName = new IntHashMap();
/*   63:     */     
/*   64:     */     public void add(String name, int value)
/*   65:     */     {
/*   66: 453 */       this.mapNameToValue.put(name, new Integer(value));
/*   67: 454 */       this.mapValueToName.put(value, name);
/*   68:     */     }
/*   69:     */     
/*   70:     */     public String name(int value)
/*   71:     */     {
/*   72: 461 */       return (String)this.mapValueToName.get(value);
/*   73:     */     }
/*   74:     */     
/*   75:     */     public int value(String name)
/*   76:     */     {
/*   77: 468 */       Object value = this.mapNameToValue.get(name);
/*   78: 469 */       if (value == null) {
/*   79: 470 */         return -1;
/*   80:     */       }
/*   81: 472 */       return ((Integer)value).intValue();
/*   82:     */     }
/*   83:     */   }
/*   84:     */   
/*   85:     */   static abstract class MapIntMap
/*   86:     */     implements Entities.EntityMap
/*   87:     */   {
/*   88:     */     protected final Map mapNameToValue;
/*   89:     */     protected final Map mapValueToName;
/*   90:     */     
/*   91:     */     MapIntMap(Map nameToValue, Map valueToName)
/*   92:     */     {
/*   93: 488 */       this.mapNameToValue = nameToValue;
/*   94: 489 */       this.mapValueToName = valueToName;
/*   95:     */     }
/*   96:     */     
/*   97:     */     public void add(String name, int value)
/*   98:     */     {
/*   99: 496 */       this.mapNameToValue.put(name, new Integer(value));
/*  100: 497 */       this.mapValueToName.put(new Integer(value), name);
/*  101:     */     }
/*  102:     */     
/*  103:     */     public String name(int value)
/*  104:     */     {
/*  105: 504 */       return (String)this.mapValueToName.get(new Integer(value));
/*  106:     */     }
/*  107:     */     
/*  108:     */     public int value(String name)
/*  109:     */     {
/*  110: 511 */       Object value = this.mapNameToValue.get(name);
/*  111: 512 */       if (value == null) {
/*  112: 513 */         return -1;
/*  113:     */       }
/*  114: 515 */       return ((Integer)value).intValue();
/*  115:     */     }
/*  116:     */   }
/*  117:     */   
/*  118:     */   static class HashEntityMap
/*  119:     */     extends Entities.MapIntMap
/*  120:     */   {
/*  121:     */     public HashEntityMap()
/*  122:     */     {
/*  123: 524 */       super(new HashMap());
/*  124:     */     }
/*  125:     */   }
/*  126:     */   
/*  127:     */   static class TreeEntityMap
/*  128:     */     extends Entities.MapIntMap
/*  129:     */   {
/*  130:     */     public TreeEntityMap()
/*  131:     */     {
/*  132: 533 */       super(new TreeMap());
/*  133:     */     }
/*  134:     */   }
/*  135:     */   
/*  136:     */   static class LookupEntityMap
/*  137:     */     extends Entities.PrimitiveEntityMap
/*  138:     */   {
/*  139:     */     private String[] lookupTable;
/*  140:     */     private static final int LOOKUP_TABLE_SIZE = 256;
/*  141:     */     
/*  142:     */     public String name(int value)
/*  143:     */     {
/*  144: 547 */       if (value < 256) {
/*  145: 548 */         return lookupTable()[value];
/*  146:     */       }
/*  147: 550 */       return super.name(value);
/*  148:     */     }
/*  149:     */     
/*  150:     */     private String[] lookupTable()
/*  151:     */     {
/*  152: 561 */       if (this.lookupTable == null) {
/*  153: 562 */         createLookupTable();
/*  154:     */       }
/*  155: 564 */       return this.lookupTable;
/*  156:     */     }
/*  157:     */     
/*  158:     */     private void createLookupTable()
/*  159:     */     {
/*  160: 573 */       this.lookupTable = new String[256];
/*  161: 574 */       for (int i = 0; i < 256; i++) {
/*  162: 575 */         this.lookupTable[i] = super.name(i);
/*  163:     */       }
/*  164:     */     }
/*  165:     */   }
/*  166:     */   
/*  167:     */   static class ArrayEntityMap
/*  168:     */     implements Entities.EntityMap
/*  169:     */   {
/*  170:     */     protected final int growBy;
/*  171: 584 */     protected int size = 0;
/*  172:     */     protected String[] names;
/*  173:     */     protected int[] values;
/*  174:     */     
/*  175:     */     public ArrayEntityMap()
/*  176:     */     {
/*  177: 594 */       this.growBy = 100;
/*  178: 595 */       this.names = new String[this.growBy];
/*  179: 596 */       this.values = new int[this.growBy];
/*  180:     */     }
/*  181:     */     
/*  182:     */     public ArrayEntityMap(int growBy)
/*  183:     */     {
/*  184: 607 */       this.growBy = growBy;
/*  185: 608 */       this.names = new String[growBy];
/*  186: 609 */       this.values = new int[growBy];
/*  187:     */     }
/*  188:     */     
/*  189:     */     public void add(String name, int value)
/*  190:     */     {
/*  191: 616 */       ensureCapacity(this.size + 1);
/*  192: 617 */       this.names[this.size] = name;
/*  193: 618 */       this.values[this.size] = value;
/*  194: 619 */       this.size += 1;
/*  195:     */     }
/*  196:     */     
/*  197:     */     protected void ensureCapacity(int capacity)
/*  198:     */     {
/*  199: 629 */       if (capacity > this.names.length)
/*  200:     */       {
/*  201: 630 */         int newSize = Math.max(capacity, this.size + this.growBy);
/*  202: 631 */         String[] newNames = new String[newSize];
/*  203: 632 */         System.arraycopy(this.names, 0, newNames, 0, this.size);
/*  204: 633 */         this.names = newNames;
/*  205: 634 */         int[] newValues = new int[newSize];
/*  206: 635 */         System.arraycopy(this.values, 0, newValues, 0, this.size);
/*  207: 636 */         this.values = newValues;
/*  208:     */       }
/*  209:     */     }
/*  210:     */     
/*  211:     */     public String name(int value)
/*  212:     */     {
/*  213: 644 */       for (int i = 0; i < this.size; i++) {
/*  214: 645 */         if (this.values[i] == value) {
/*  215: 646 */           return this.names[i];
/*  216:     */         }
/*  217:     */       }
/*  218: 649 */       return null;
/*  219:     */     }
/*  220:     */     
/*  221:     */     public int value(String name)
/*  222:     */     {
/*  223: 656 */       for (int i = 0; i < this.size; i++) {
/*  224: 657 */         if (this.names[i].equals(name)) {
/*  225: 658 */           return this.values[i];
/*  226:     */         }
/*  227:     */       }
/*  228: 661 */       return -1;
/*  229:     */     }
/*  230:     */   }
/*  231:     */   
/*  232:     */   static class BinaryEntityMap
/*  233:     */     extends Entities.ArrayEntityMap
/*  234:     */   {
/*  235:     */     public BinaryEntityMap() {}
/*  236:     */     
/*  237:     */     public BinaryEntityMap(int growBy)
/*  238:     */     {
/*  239: 684 */       super();
/*  240:     */     }
/*  241:     */     
/*  242:     */     private int binarySearch(int key)
/*  243:     */     {
/*  244: 696 */       int low = 0;
/*  245: 697 */       int high = this.size - 1;
/*  246: 699 */       while (low <= high)
/*  247:     */       {
/*  248: 700 */         int mid = low + high >>> 1;
/*  249: 701 */         int midVal = this.values[mid];
/*  250: 703 */         if (midVal < key) {
/*  251: 704 */           low = mid + 1;
/*  252: 705 */         } else if (midVal > key) {
/*  253: 706 */           high = mid - 1;
/*  254:     */         } else {
/*  255: 708 */           return mid;
/*  256:     */         }
/*  257:     */       }
/*  258: 711 */       return -(low + 1);
/*  259:     */     }
/*  260:     */     
/*  261:     */     public void add(String name, int value)
/*  262:     */     {
/*  263: 718 */       ensureCapacity(this.size + 1);
/*  264: 719 */       int insertAt = binarySearch(value);
/*  265: 720 */       if (insertAt > 0) {
/*  266: 721 */         return;
/*  267:     */       }
/*  268: 723 */       insertAt = -(insertAt + 1);
/*  269: 724 */       System.arraycopy(this.values, insertAt, this.values, insertAt + 1, this.size - insertAt);
/*  270: 725 */       this.values[insertAt] = value;
/*  271: 726 */       System.arraycopy(this.names, insertAt, this.names, insertAt + 1, this.size - insertAt);
/*  272: 727 */       this.names[insertAt] = name;
/*  273: 728 */       this.size += 1;
/*  274:     */     }
/*  275:     */     
/*  276:     */     public String name(int value)
/*  277:     */     {
/*  278: 735 */       int index = binarySearch(value);
/*  279: 736 */       if (index < 0) {
/*  280: 737 */         return null;
/*  281:     */       }
/*  282: 739 */       return this.names[index];
/*  283:     */     }
/*  284:     */   }
/*  285:     */   
/*  286:     */   public Entities()
/*  287:     */   {
/*  288: 749 */     this.map = new LookupEntityMap();
/*  289:     */   }
/*  290:     */   
/*  291:     */   Entities(EntityMap emap)
/*  292:     */   {
/*  293: 758 */     this.map = emap;
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void addEntities(String[][] entityArray)
/*  297:     */   {
/*  298: 770 */     for (int i = 0; i < entityArray.length; i++) {
/*  299: 771 */       addEntity(entityArray[i][0], Integer.parseInt(entityArray[i][1]));
/*  300:     */     }
/*  301:     */   }
/*  302:     */   
/*  303:     */   public void addEntity(String name, int value)
/*  304:     */   {
/*  305: 786 */     this.map.add(name, value);
/*  306:     */   }
/*  307:     */   
/*  308:     */   public String entityName(int value)
/*  309:     */   {
/*  310: 799 */     return this.map.name(value);
/*  311:     */   }
/*  312:     */   
/*  313:     */   public int entityValue(String name)
/*  314:     */   {
/*  315: 812 */     return this.map.value(name);
/*  316:     */   }
/*  317:     */   
/*  318:     */   public String escape(String str)
/*  319:     */   {
/*  320: 830 */     StringWriter stringWriter = createStringWriter(str);
/*  321:     */     try
/*  322:     */     {
/*  323: 832 */       escape(stringWriter, str);
/*  324:     */     }
/*  325:     */     catch (IOException e)
/*  326:     */     {
/*  327: 836 */       throw new UnhandledException(e);
/*  328:     */     }
/*  329: 838 */     return stringWriter.toString();
/*  330:     */   }
/*  331:     */   
/*  332:     */   public void escape(Writer writer, String str)
/*  333:     */     throws IOException
/*  334:     */   {
/*  335: 859 */     int len = str.length();
/*  336: 860 */     for (int i = 0; i < len; i++)
/*  337:     */     {
/*  338: 861 */       char c = str.charAt(i);
/*  339: 862 */       String entityName = entityName(c);
/*  340: 863 */       if (entityName == null)
/*  341:     */       {
/*  342: 864 */         if (c > '')
/*  343:     */         {
/*  344: 865 */           writer.write("&#");
/*  345: 866 */           writer.write(Integer.toString(c, 10));
/*  346: 867 */           writer.write(59);
/*  347:     */         }
/*  348:     */         else
/*  349:     */         {
/*  350: 869 */           writer.write(c);
/*  351:     */         }
/*  352:     */       }
/*  353:     */       else
/*  354:     */       {
/*  355: 872 */         writer.write(38);
/*  356: 873 */         writer.write(entityName);
/*  357: 874 */         writer.write(59);
/*  358:     */       }
/*  359:     */     }
/*  360:     */   }
/*  361:     */   
/*  362:     */   public String unescape(String str)
/*  363:     */   {
/*  364: 894 */     int firstAmp = str.indexOf('&');
/*  365: 895 */     if (firstAmp < 0) {
/*  366: 896 */       return str;
/*  367:     */     }
/*  368: 898 */     StringWriter stringWriter = createStringWriter(str);
/*  369:     */     try
/*  370:     */     {
/*  371: 900 */       doUnescape(stringWriter, str, firstAmp);
/*  372:     */     }
/*  373:     */     catch (IOException e)
/*  374:     */     {
/*  375: 904 */       throw new UnhandledException(e);
/*  376:     */     }
/*  377: 906 */     return stringWriter.toString();
/*  378:     */   }
/*  379:     */   
/*  380:     */   private StringWriter createStringWriter(String str)
/*  381:     */   {
/*  382: 917 */     return new StringWriter((int)(str.length() + str.length() * 0.1D));
/*  383:     */   }
/*  384:     */   
/*  385:     */   public void unescape(Writer writer, String str)
/*  386:     */     throws IOException
/*  387:     */   {
/*  388: 938 */     int firstAmp = str.indexOf('&');
/*  389: 939 */     if (firstAmp < 0)
/*  390:     */     {
/*  391: 940 */       writer.write(str);
/*  392: 941 */       return;
/*  393:     */     }
/*  394: 943 */     doUnescape(writer, str, firstAmp);
/*  395:     */   }
/*  396:     */   
/*  397:     */   private void doUnescape(Writer writer, String str, int firstAmp)
/*  398:     */     throws IOException
/*  399:     */   {
/*  400: 961 */     writer.write(str, 0, firstAmp);
/*  401: 962 */     int len = str.length();
/*  402: 963 */     for (int i = firstAmp; i < len; i++)
/*  403:     */     {
/*  404: 964 */       char c = str.charAt(i);
/*  405: 965 */       if (c == '&')
/*  406:     */       {
/*  407: 966 */         int nextIdx = i + 1;
/*  408: 967 */         int semiColonIdx = str.indexOf(';', nextIdx);
/*  409: 968 */         if (semiColonIdx == -1)
/*  410:     */         {
/*  411: 969 */           writer.write(c);
/*  412:     */         }
/*  413:     */         else
/*  414:     */         {
/*  415: 972 */           int amphersandIdx = str.indexOf('&', i + 1);
/*  416: 973 */           if ((amphersandIdx != -1) && (amphersandIdx < semiColonIdx))
/*  417:     */           {
/*  418: 975 */             writer.write(c);
/*  419:     */           }
/*  420:     */           else
/*  421:     */           {
/*  422: 978 */             String entityContent = str.substring(nextIdx, semiColonIdx);
/*  423: 979 */             int entityValue = -1;
/*  424: 980 */             int entityContentLen = entityContent.length();
/*  425: 981 */             if (entityContentLen > 0) {
/*  426: 982 */               if (entityContent.charAt(0) == '#')
/*  427:     */               {
/*  428: 984 */                 if (entityContentLen > 1)
/*  429:     */                 {
/*  430: 985 */                   char isHexChar = entityContent.charAt(1);
/*  431:     */                   try
/*  432:     */                   {
/*  433: 987 */                     switch (isHexChar)
/*  434:     */                     {
/*  435:     */                     case 'X': 
/*  436:     */                     case 'x': 
/*  437: 990 */                       entityValue = Integer.parseInt(entityContent.substring(2), 16);
/*  438: 991 */                       break;
/*  439:     */                     default: 
/*  440: 994 */                       entityValue = Integer.parseInt(entityContent.substring(1), 10);
/*  441:     */                     }
/*  442: 997 */                     if (entityValue > 65535) {
/*  443: 998 */                       entityValue = -1;
/*  444:     */                     }
/*  445:     */                   }
/*  446:     */                   catch (NumberFormatException e)
/*  447:     */                   {
/*  448:1001 */                     entityValue = -1;
/*  449:     */                   }
/*  450:     */                 }
/*  451:     */               }
/*  452:     */               else {
/*  453:1005 */                 entityValue = entityValue(entityContent);
/*  454:     */               }
/*  455:     */             }
/*  456:1009 */             if (entityValue == -1)
/*  457:     */             {
/*  458:1010 */               writer.write(38);
/*  459:1011 */               writer.write(entityContent);
/*  460:1012 */               writer.write(59);
/*  461:     */             }
/*  462:     */             else
/*  463:     */             {
/*  464:1014 */               writer.write(entityValue);
/*  465:     */             }
/*  466:1016 */             i = semiColonIdx;
/*  467:     */           }
/*  468:     */         }
/*  469:     */       }
/*  470:     */       else
/*  471:     */       {
/*  472:1018 */         writer.write(c);
/*  473:     */       }
/*  474:     */     }
/*  475:     */   }
/*  476:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.Entities
 * JD-Core Version:    0.7.0.1
 */