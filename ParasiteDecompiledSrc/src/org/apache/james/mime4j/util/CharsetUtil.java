/*    1:     */ package org.apache.james.mime4j.util;
/*    2:     */ 
/*    3:     */ import java.io.UnsupportedEncodingException;
/*    4:     */ import java.nio.charset.Charset;
/*    5:     */ import java.nio.charset.IllegalCharsetNameException;
/*    6:     */ import java.nio.charset.UnsupportedCharsetException;
/*    7:     */ import java.util.HashMap;
/*    8:     */ import java.util.Map;
/*    9:     */ import java.util.SortedSet;
/*   10:     */ import java.util.TreeSet;
/*   11:     */ import org.apache.commons.logging.Log;
/*   12:     */ import org.apache.commons.logging.LogFactory;
/*   13:     */ 
/*   14:     */ public class CharsetUtil
/*   15:     */ {
/*   16: 791 */   private static Log log = LogFactory.getLog(CharsetUtil.class);
/*   17:     */   
/*   18:     */   private static class Charset
/*   19:     */     implements Comparable<Charset>
/*   20:     */   {
/*   21: 794 */     private String canonical = null;
/*   22: 795 */     private String mime = null;
/*   23: 796 */     private String[] aliases = null;
/*   24:     */     
/*   25:     */     private Charset(String canonical, String mime, String[] aliases)
/*   26:     */     {
/*   27: 799 */       this.canonical = canonical;
/*   28: 800 */       this.mime = mime;
/*   29: 801 */       this.aliases = aliases;
/*   30:     */     }
/*   31:     */     
/*   32:     */     public int compareTo(Charset c)
/*   33:     */     {
/*   34: 805 */       return this.canonical.compareTo(c.canonical);
/*   35:     */     }
/*   36:     */   }
/*   37:     */   
/*   38: 809 */   private static Charset[] JAVA_CHARSETS = { new Charset("ISO8859_1", "ISO-8859-1", new String[] { "ISO_8859-1:1987", "iso-ir-100", "ISO_8859-1", "latin1", "l1", "IBM819", "CP819", "csISOLatin1", "8859_1", "819", "IBM-819", "ISO8859-1", "ISO_8859_1" }, null), new Charset("ISO8859_2", "ISO-8859-2", new String[] { "ISO_8859-2:1987", "iso-ir-101", "ISO_8859-2", "latin2", "l2", "csISOLatin2", "8859_2", "iso8859_2" }, null), new Charset("ISO8859_3", "ISO-8859-3", new String[] { "ISO_8859-3:1988", "iso-ir-109", "ISO_8859-3", "latin3", "l3", "csISOLatin3", "8859_3" }, null), new Charset("ISO8859_4", "ISO-8859-4", new String[] { "ISO_8859-4:1988", "iso-ir-110", "ISO_8859-4", "latin4", "l4", "csISOLatin4", "8859_4" }, null), new Charset("ISO8859_5", "ISO-8859-5", new String[] { "ISO_8859-5:1988", "iso-ir-144", "ISO_8859-5", "cyrillic", "csISOLatinCyrillic", "8859_5" }, null), new Charset("ISO8859_6", "ISO-8859-6", new String[] { "ISO_8859-6:1987", "iso-ir-127", "ISO_8859-6", "ECMA-114", "ASMO-708", "arabic", "csISOLatinArabic", "8859_6" }, null), new Charset("ISO8859_7", "ISO-8859-7", new String[] { "ISO_8859-7:1987", "iso-ir-126", "ISO_8859-7", "ELOT_928", "ECMA-118", "greek", "greek8", "csISOLatinGreek", "8859_7", "sun_eu_greek" }, null), new Charset("ISO8859_8", "ISO-8859-8", new String[] { "ISO_8859-8:1988", "iso-ir-138", "ISO_8859-8", "hebrew", "csISOLatinHebrew", "8859_8" }, null), new Charset("ISO8859_9", "ISO-8859-9", new String[] { "ISO_8859-9:1989", "iso-ir-148", "ISO_8859-9", "latin5", "l5", "csISOLatin5", "8859_9" }, null), new Charset("ISO8859_13", "ISO-8859-13", new String[0], null), new Charset("ISO8859_15", "ISO-8859-15", new String[] { "ISO_8859-15", "Latin-9", "8859_15", "csISOlatin9", "IBM923", "cp923", "923", "L9", "IBM-923", "ISO8859-15", "LATIN9", "LATIN0", "csISOlatin0", "ISO8859_15_FDIS" }, null), new Charset("KOI8_R", "KOI8-R", new String[] { "csKOI8R", "koi8" }, null), new Charset("ASCII", "US-ASCII", new String[] { "ANSI_X3.4-1968", "iso-ir-6", "ANSI_X3.4-1986", "ISO_646.irv:1991", "ISO646-US", "us", "IBM367", "cp367", "csASCII", "ascii7", "646", "iso_646.irv:1983" }, null), new Charset("UTF8", "UTF-8", new String[0], null), new Charset("UTF-16", "UTF-16", new String[] { "UTF_16" }, null), new Charset("UnicodeBigUnmarked", "UTF-16BE", new String[] { "X-UTF-16BE", "UTF_16BE", "ISO-10646-UCS-2" }, null), new Charset("UnicodeLittleUnmarked", "UTF-16LE", new String[] { "UTF_16LE", "X-UTF-16LE" }, null), new Charset("Big5", "Big5", new String[] { "csBig5", "CN-Big5", "BIG-FIVE", "BIGFIVE" }, null), new Charset("Big5_HKSCS", "Big5-HKSCS", new String[] { "big5hkscs" }, null), new Charset("EUC_JP", "EUC-JP", new String[] { "csEUCPkdFmtJapanese", "Extended_UNIX_Code_Packed_Format_for_Japanese", "eucjis", "x-eucjp", "eucjp", "x-euc-jp" }, null), new Charset("EUC_KR", "EUC-KR", new String[] { "csEUCKR", "ksc5601", "5601", "ksc5601_1987", "ksc_5601", "ksc5601-1987", "ks_c_5601-1987", "euckr" }, null), new Charset("GB18030", "GB18030", new String[] { "gb18030-2000" }, null), new Charset("EUC_CN", "GB2312", new String[] { "x-EUC-CN", "csGB2312", "euccn", "euc-cn", "gb2312-80", "gb2312-1980", "CN-GB", "CN-GB-ISOIR165" }, null), new Charset("GBK", "windows-936", new String[] { "CP936", "MS936", "ms_936", "x-mswin-936" }, null), new Charset("Cp037", "IBM037", new String[] { "ebcdic-cp-us", "ebcdic-cp-ca", "ebcdic-cp-wt", "ebcdic-cp-nl", "csIBM037" }, null), new Charset("Cp273", "IBM273", new String[] { "csIBM273" }, null), new Charset("Cp277", "IBM277", new String[] { "EBCDIC-CP-DK", "EBCDIC-CP-NO", "csIBM277" }, null), new Charset("Cp278", "IBM278", new String[] { "CP278", "ebcdic-cp-fi", "ebcdic-cp-se", "csIBM278" }, null), new Charset("Cp280", "IBM280", new String[] { "ebcdic-cp-it", "csIBM280" }, null), new Charset("Cp284", "IBM284", new String[] { "ebcdic-cp-es", "csIBM284" }, null), new Charset("Cp285", "IBM285", new String[] { "ebcdic-cp-gb", "csIBM285" }, null), new Charset("Cp297", "IBM297", new String[] { "ebcdic-cp-fr", "csIBM297" }, null), new Charset("Cp420", "IBM420", new String[] { "ebcdic-cp-ar1", "csIBM420" }, null), new Charset("Cp424", "IBM424", new String[] { "ebcdic-cp-he", "csIBM424" }, null), new Charset("Cp437", "IBM437", new String[] { "437", "csPC8CodePage437" }, null), new Charset("Cp500", "IBM500", new String[] { "ebcdic-cp-be", "ebcdic-cp-ch", "csIBM500" }, null), new Charset("Cp775", "IBM775", new String[] { "csPC775Baltic" }, null), new Charset("Cp838", "IBM-Thai", new String[0], null), new Charset("Cp850", "IBM850", new String[] { "850", "csPC850Multilingual" }, null), new Charset("Cp852", "IBM852", new String[] { "852", "csPCp852" }, null), new Charset("Cp855", "IBM855", new String[] { "855", "csIBM855" }, null), new Charset("Cp857", "IBM857", new String[] { "857", "csIBM857" }, null), new Charset("Cp858", "IBM00858", new String[] { "CCSID00858", "CP00858", "PC-Multilingual-850+euro" }, null), new Charset("Cp860", "IBM860", new String[] { "860", "csIBM860" }, null), new Charset("Cp861", "IBM861", new String[] { "861", "cp-is", "csIBM861" }, null), new Charset("Cp862", "IBM862", new String[] { "862", "csPC862LatinHebrew" }, null), new Charset("Cp863", "IBM863", new String[] { "863", "csIBM863" }, null), new Charset("Cp864", "IBM864", new String[] { "cp864", "csIBM864" }, null), new Charset("Cp865", "IBM865", new String[] { "865", "csIBM865" }, null), new Charset("Cp866", "IBM866", new String[] { "866", "csIBM866" }, null), new Charset("Cp868", "IBM868", new String[] { "cp-ar", "csIBM868" }, null), new Charset("Cp869", "IBM869", new String[] { "cp-gr", "csIBM869" }, null), new Charset("Cp870", "IBM870", new String[] { "ebcdic-cp-roece", "ebcdic-cp-yu", "csIBM870" }, null), new Charset("Cp871", "IBM871", new String[] { "ebcdic-cp-is", "csIBM871" }, null), new Charset("Cp918", "IBM918", new String[] { "ebcdic-cp-ar2", "csIBM918" }, null), new Charset("Cp1026", "IBM1026", new String[] { "csIBM1026" }, null), new Charset("Cp1047", "IBM1047", new String[] { "IBM-1047" }, null), new Charset("Cp1140", "IBM01140", new String[] { "CCSID01140", "CP01140", "ebcdic-us-37+euro" }, null), new Charset("Cp1141", "IBM01141", new String[] { "CCSID01141", "CP01141", "ebcdic-de-273+euro" }, null), new Charset("Cp1142", "IBM01142", new String[] { "CCSID01142", "CP01142", "ebcdic-dk-277+euro", "ebcdic-no-277+euro" }, null), new Charset("Cp1143", "IBM01143", new String[] { "CCSID01143", "CP01143", "ebcdic-fi-278+euro", "ebcdic-se-278+euro" }, null), new Charset("Cp1144", "IBM01144", new String[] { "CCSID01144", "CP01144", "ebcdic-it-280+euro" }, null), new Charset("Cp1145", "IBM01145", new String[] { "CCSID01145", "CP01145", "ebcdic-es-284+euro" }, null), new Charset("Cp1146", "IBM01146", new String[] { "CCSID01146", "CP01146", "ebcdic-gb-285+euro" }, null), new Charset("Cp1147", "IBM01147", new String[] { "CCSID01147", "CP01147", "ebcdic-fr-297+euro" }, null), new Charset("Cp1148", "IBM01148", new String[] { "CCSID01148", "CP01148", "ebcdic-international-500+euro" }, null), new Charset("Cp1149", "IBM01149", new String[] { "CCSID01149", "CP01149", "ebcdic-is-871+euro" }, null), new Charset("Cp1250", "windows-1250", new String[0], null), new Charset("Cp1251", "windows-1251", new String[0], null), new Charset("Cp1252", "windows-1252", new String[0], null), new Charset("Cp1253", "windows-1253", new String[0], null), new Charset("Cp1254", "windows-1254", new String[0], null), new Charset("Cp1255", "windows-1255", new String[0], null), new Charset("Cp1256", "windows-1256", new String[0], null), new Charset("Cp1257", "windows-1257", new String[0], null), new Charset("Cp1258", "windows-1258", new String[0], null), new Charset("ISO2022CN", "ISO-2022-CN", new String[0], null), new Charset("ISO2022JP", "ISO-2022-JP", new String[] { "csISO2022JP", "JIS", "jis_encoding", "csjisencoding" }, null), new Charset("ISO2022KR", "ISO-2022-KR", new String[] { "csISO2022KR" }, null), new Charset("JIS_X0201", "JIS_X0201", new String[] { "X0201", "JIS0201", "csHalfWidthKatakana" }, null), new Charset("JIS_X0212-1990", "JIS_X0212-1990", new String[] { "iso-ir-159", "x0212", "JIS0212", "csISO159JISX02121990" }, null), new Charset("JIS_C6626-1983", "JIS_C6626-1983", new String[] { "x-JIS0208", "JIS0208", "csISO87JISX0208", "x0208", "JIS_X0208-1983", "iso-ir-87" }, null), new Charset("SJIS", "Shift_JIS", new String[] { "MS_Kanji", "csShiftJIS", "shift-jis", "x-sjis", "pck" }, null), new Charset("TIS620", "TIS-620", new String[0], null), new Charset("MS932", "Windows-31J", new String[] { "windows-932", "csWindows31J", "x-ms-cp932" }, null), new Charset("EUC_TW", "EUC-TW", new String[] { "x-EUC-TW", "cns11643", "euctw" }, null), new Charset("x-Johab", "johab", new String[] { "johab", "cp1361", "ms1361", "ksc5601-1992", "ksc5601_1992" }, null), new Charset("MS950_HKSCS", "", new String[0], null), new Charset("MS874", "windows-874", new String[] { "cp874" }, null), new Charset("MS949", "windows-949", new String[] { "windows949", "ms_949", "x-windows-949" }, null), new Charset("MS950", "windows-950", new String[] { "x-windows-950" }, null), new Charset("Cp737", null, new String[0], null), new Charset("Cp856", null, new String[0], null), new Charset("Cp875", null, new String[0], null), new Charset("Cp921", null, new String[0], null), new Charset("Cp922", null, new String[0], null), new Charset("Cp930", null, new String[0], null), new Charset("Cp933", null, new String[0], null), new Charset("Cp935", null, new String[0], null), new Charset("Cp937", null, new String[0], null), new Charset("Cp939", null, new String[0], null), new Charset("Cp942", null, new String[0], null), new Charset("Cp942C", null, new String[0], null), new Charset("Cp943", null, new String[0], null), new Charset("Cp943C", null, new String[0], null), new Charset("Cp948", null, new String[0], null), new Charset("Cp949", null, new String[0], null), new Charset("Cp949C", null, new String[0], null), new Charset("Cp950", null, new String[0], null), new Charset("Cp964", null, new String[0], null), new Charset("Cp970", null, new String[0], null), new Charset("Cp1006", null, new String[0], null), new Charset("Cp1025", null, new String[0], null), new Charset("Cp1046", null, new String[0], null), new Charset("Cp1097", null, new String[0], null), new Charset("Cp1098", null, new String[0], null), new Charset("Cp1112", null, new String[0], null), new Charset("Cp1122", null, new String[0], null), new Charset("Cp1123", null, new String[0], null), new Charset("Cp1124", null, new String[0], null), new Charset("Cp1381", null, new String[0], null), new Charset("Cp1383", null, new String[0], null), new Charset("Cp33722", null, new String[0], null), new Charset("Big5_Solaris", null, new String[0], null), new Charset("EUC_JP_LINUX", null, new String[0], null), new Charset("EUC_JP_Solaris", null, new String[0], null), new Charset("ISCII91", null, new String[] { "x-ISCII91", "iscii" }, null), new Charset("ISO2022_CN_CNS", null, new String[0], null), new Charset("ISO2022_CN_GB", null, new String[0], null), new Charset("x-iso-8859-11", null, new String[0], null), new Charset("JISAutoDetect", null, new String[0], null), new Charset("MacArabic", null, new String[0], null), new Charset("MacCentralEurope", null, new String[0], null), new Charset("MacCroatian", null, new String[0], null), new Charset("MacCyrillic", null, new String[0], null), new Charset("MacDingbat", null, new String[0], null), new Charset("MacGreek", "MacGreek", new String[0], null), new Charset("MacHebrew", null, new String[0], null), new Charset("MacIceland", null, new String[0], null), new Charset("MacRoman", "MacRoman", new String[] { "Macintosh", "MAC", "csMacintosh" }, null), new Charset("MacRomania", null, new String[0], null), new Charset("MacSymbol", null, new String[0], null), new Charset("MacThai", null, new String[0], null), new Charset("MacTurkish", null, new String[0], null), new Charset("MacUkraine", null, new String[0], null), new Charset("UnicodeBig", null, new String[0], null), new Charset("UnicodeLittle", null, new String[0], null) };
/*   39:1002 */   private static SortedSet<String> decodingSupported = null;
/*   40:1008 */   private static SortedSet<String> encodingSupported = null;
/*   41:1014 */   private static Map<String, Charset> charsetMap = null;
/*   42:     */   public static final String CRLF = "\r\n";
/*   43:     */   public static final int CR = 13;
/*   44:     */   public static final int LF = 10;
/*   45:     */   public static final int SP = 32;
/*   46:     */   public static final int HT = 9;
/*   47:     */   
/*   48:     */   static
/*   49:     */   {
/*   50:1017 */     decodingSupported = new TreeSet();
/*   51:1018 */     encodingSupported = new TreeSet();
/*   52:1019 */     byte[] dummy = { 100, 117, 109, 109, 121 };
/*   53:1020 */     for (Charset c : JAVA_CHARSETS)
/*   54:     */     {
/*   55:     */       try
/*   56:     */       {
/*   57:1022 */         new String(dummy, c.canonical);
/*   58:1023 */         decodingSupported.add(c.canonical.toLowerCase());
/*   59:     */       }
/*   60:     */       catch (UnsupportedOperationException e) {}catch (UnsupportedEncodingException e) {}
/*   61:     */       try
/*   62:     */       {
/*   63:1028 */         "dummy".getBytes(c.canonical);
/*   64:1029 */         encodingSupported.add(c.canonical.toLowerCase());
/*   65:     */       }
/*   66:     */       catch (UnsupportedOperationException e) {}catch (UnsupportedEncodingException e) {}
/*   67:     */     }
/*   68:1035 */     charsetMap = new HashMap();
/*   69:1036 */     for (Charset c : JAVA_CHARSETS)
/*   70:     */     {
/*   71:1037 */       charsetMap.put(c.canonical.toLowerCase(), c);
/*   72:1038 */       if (c.mime != null) {
/*   73:1039 */         charsetMap.put(c.mime.toLowerCase(), c);
/*   74:     */       }
/*   75:1041 */       if (c.aliases != null) {
/*   76:1042 */         for (String str : c.aliases) {
/*   77:1043 */           charsetMap.put(str.toLowerCase(), c);
/*   78:     */         }
/*   79:     */       }
/*   80:     */     }
/*   81:1048 */     if (log.isDebugEnabled())
/*   82:     */     {
/*   83:1049 */       log.debug("Character sets which support decoding: " + decodingSupported);
/*   84:     */       
/*   85:1051 */       log.debug("Character sets which support encoding: " + encodingSupported);
/*   86:     */     }
/*   87:     */   }
/*   88:     */   
/*   89:1071 */   public static final Charset US_ASCII = Charset.forName("US-ASCII");
/*   90:1074 */   public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
/*   91:1077 */   public static final Charset UTF_8 = Charset.forName("UTF-8");
/*   92:1080 */   public static final Charset DEFAULT_CHARSET = US_ASCII;
/*   93:     */   
/*   94:     */   public static boolean isASCII(char ch)
/*   95:     */   {
/*   96:1092 */     return (0xFF80 & ch) == 0;
/*   97:     */   }
/*   98:     */   
/*   99:     */   public static boolean isASCII(String s)
/*  100:     */   {
/*  101:1105 */     if (s == null) {
/*  102:1106 */       throw new IllegalArgumentException("String may not be null");
/*  103:     */     }
/*  104:1108 */     int len = s.length();
/*  105:1109 */     for (int i = 0; i < len; i++) {
/*  106:1110 */       if (!isASCII(s.charAt(i))) {
/*  107:1111 */         return false;
/*  108:     */       }
/*  109:     */     }
/*  110:1114 */     return true;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public static boolean isWhitespace(char ch)
/*  114:     */   {
/*  115:1127 */     return (ch == ' ') || (ch == '\t') || (ch == '\r') || (ch == '\n');
/*  116:     */   }
/*  117:     */   
/*  118:     */   public static boolean isWhitespace(String s)
/*  119:     */   {
/*  120:1140 */     if (s == null) {
/*  121:1141 */       throw new IllegalArgumentException("String may not be null");
/*  122:     */     }
/*  123:1143 */     int len = s.length();
/*  124:1144 */     for (int i = 0; i < len; i++) {
/*  125:1145 */       if (!isWhitespace(s.charAt(i))) {
/*  126:1146 */         return false;
/*  127:     */       }
/*  128:     */     }
/*  129:1149 */     return true;
/*  130:     */   }
/*  131:     */   
/*  132:     */   public static boolean isEncodingSupported(String charsetName)
/*  133:     */   {
/*  134:1164 */     return encodingSupported.contains(charsetName.toLowerCase());
/*  135:     */   }
/*  136:     */   
/*  137:     */   public static boolean isDecodingSupported(String charsetName)
/*  138:     */   {
/*  139:1179 */     return decodingSupported.contains(charsetName.toLowerCase());
/*  140:     */   }
/*  141:     */   
/*  142:     */   public static String toMimeCharset(String charsetName)
/*  143:     */   {
/*  144:1190 */     Charset c = (Charset)charsetMap.get(charsetName.toLowerCase());
/*  145:1191 */     if (c != null) {
/*  146:1192 */       return c.mime;
/*  147:     */     }
/*  148:1194 */     return null;
/*  149:     */   }
/*  150:     */   
/*  151:     */   public static String toJavaCharset(String charsetName)
/*  152:     */   {
/*  153:1209 */     Charset c = (Charset)charsetMap.get(charsetName.toLowerCase());
/*  154:1210 */     if (c != null) {
/*  155:1211 */       return c.canonical;
/*  156:     */     }
/*  157:1213 */     return null;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public static Charset getCharset(String charsetName)
/*  161:     */   {
/*  162:1217 */     String defaultCharset = "ISO-8859-1";
/*  163:1220 */     if (charsetName == null) {
/*  164:1220 */       charsetName = defaultCharset;
/*  165:     */     }
/*  166:     */     try
/*  167:     */     {
/*  168:1223 */       return Charset.forName(charsetName);
/*  169:     */     }
/*  170:     */     catch (IllegalCharsetNameException e)
/*  171:     */     {
/*  172:1225 */       log.info("Illegal charset " + charsetName + ", fallback to " + defaultCharset + ": " + e);
/*  173:     */       
/*  174:1227 */       return Charset.forName(defaultCharset);
/*  175:     */     }
/*  176:     */     catch (UnsupportedCharsetException ex)
/*  177:     */     {
/*  178:1229 */       log.info("Unsupported charset " + charsetName + ", fallback to " + defaultCharset + ": " + ex);
/*  179:     */     }
/*  180:1231 */     return Charset.forName(defaultCharset);
/*  181:     */   }
/*  182:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.util.CharsetUtil
 * JD-Core Version:    0.7.0.1
 */