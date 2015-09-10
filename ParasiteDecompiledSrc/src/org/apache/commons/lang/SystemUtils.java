/*    1:     */ package org.apache.commons.lang;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ 
/*    6:     */ public class SystemUtils
/*    7:     */ {
/*    8:     */   private static final int JAVA_VERSION_TRIM_SIZE = 3;
/*    9:     */   private static final String OS_NAME_WINDOWS_PREFIX = "Windows";
/*   10:     */   private static final String USER_HOME_KEY = "user.home";
/*   11:     */   private static final String USER_DIR_KEY = "user.dir";
/*   12:     */   private static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";
/*   13:     */   private static final String JAVA_HOME_KEY = "java.home";
/*   14: 105 */   public static final String AWT_TOOLKIT = getSystemProperty("awt.toolkit");
/*   15: 129 */   public static final String FILE_ENCODING = getSystemProperty("file.encoding");
/*   16: 149 */   public static final String FILE_SEPARATOR = getSystemProperty("file.separator");
/*   17: 169 */   public static final String JAVA_AWT_FONTS = getSystemProperty("java.awt.fonts");
/*   18: 189 */   public static final String JAVA_AWT_GRAPHICSENV = getSystemProperty("java.awt.graphicsenv");
/*   19: 212 */   public static final String JAVA_AWT_HEADLESS = getSystemProperty("java.awt.headless");
/*   20: 232 */   public static final String JAVA_AWT_PRINTERJOB = getSystemProperty("java.awt.printerjob");
/*   21: 252 */   public static final String JAVA_CLASS_PATH = getSystemProperty("java.class.path");
/*   22: 272 */   public static final String JAVA_CLASS_VERSION = getSystemProperty("java.class.version");
/*   23: 293 */   public static final String JAVA_COMPILER = getSystemProperty("java.compiler");
/*   24: 313 */   public static final String JAVA_ENDORSED_DIRS = getSystemProperty("java.endorsed.dirs");
/*   25: 333 */   public static final String JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs");
/*   26: 353 */   public static final String JAVA_HOME = getSystemProperty("java.home");
/*   27: 373 */   public static final String JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir");
/*   28: 393 */   public static final String JAVA_LIBRARY_PATH = getSystemProperty("java.library.path");
/*   29: 414 */   public static final String JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name");
/*   30: 435 */   public static final String JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version");
/*   31: 455 */   public static final String JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name");
/*   32: 475 */   public static final String JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor");
/*   33: 495 */   public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");
/*   34: 516 */   public static final String JAVA_UTIL_PREFS_PREFERENCES_FACTORY = getSystemProperty("java.util.prefs.PreferencesFactory");
/*   35: 537 */   public static final String JAVA_VENDOR = getSystemProperty("java.vendor");
/*   36: 557 */   public static final String JAVA_VENDOR_URL = getSystemProperty("java.vendor.url");
/*   37: 577 */   public static final String JAVA_VERSION = getSystemProperty("java.version");
/*   38: 598 */   public static final String JAVA_VM_INFO = getSystemProperty("java.vm.info");
/*   39: 618 */   public static final String JAVA_VM_NAME = getSystemProperty("java.vm.name");
/*   40: 638 */   public static final String JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name");
/*   41: 658 */   public static final String JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor");
/*   42: 678 */   public static final String JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version");
/*   43: 698 */   public static final String JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor");
/*   44: 718 */   public static final String JAVA_VM_VERSION = getSystemProperty("java.vm.version");
/*   45: 738 */   public static final String LINE_SEPARATOR = getSystemProperty("line.separator");
/*   46: 758 */   public static final String OS_ARCH = getSystemProperty("os.arch");
/*   47: 778 */   public static final String OS_NAME = getSystemProperty("os.name");
/*   48: 798 */   public static final String OS_VERSION = getSystemProperty("os.version");
/*   49: 818 */   public static final String PATH_SEPARATOR = getSystemProperty("path.separator");
/*   50: 841 */   public static final String USER_COUNTRY = getSystemProperty("user.country") == null ? getSystemProperty("user.region") : getSystemProperty("user.country");
/*   51: 863 */   public static final String USER_DIR = getSystemProperty("user.dir");
/*   52: 883 */   public static final String USER_HOME = getSystemProperty("user.home");
/*   53: 904 */   public static final String USER_LANGUAGE = getSystemProperty("user.language");
/*   54: 924 */   public static final String USER_NAME = getSystemProperty("user.name");
/*   55: 944 */   public static final String USER_TIMEZONE = getSystemProperty("user.timezone");
/*   56: 962 */   public static final String JAVA_VERSION_TRIMMED = getJavaVersionTrimmed();
/*   57: 988 */   public static final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat();
/*   58:1009 */   public static final int JAVA_VERSION_INT = getJavaVersionAsInt();
/*   59:1025 */   public static final boolean IS_JAVA_1_1 = getJavaVersionMatches("1.1");
/*   60:1036 */   public static final boolean IS_JAVA_1_2 = getJavaVersionMatches("1.2");
/*   61:1047 */   public static final boolean IS_JAVA_1_3 = getJavaVersionMatches("1.3");
/*   62:1058 */   public static final boolean IS_JAVA_1_4 = getJavaVersionMatches("1.4");
/*   63:1069 */   public static final boolean IS_JAVA_1_5 = getJavaVersionMatches("1.5");
/*   64:1080 */   public static final boolean IS_JAVA_1_6 = getJavaVersionMatches("1.6");
/*   65:1093 */   public static final boolean IS_JAVA_1_7 = getJavaVersionMatches("1.7");
/*   66:1114 */   public static final boolean IS_OS_AIX = getOSMatchesName("AIX");
/*   67:1127 */   public static final boolean IS_OS_HP_UX = getOSMatchesName("HP-UX");
/*   68:1140 */   public static final boolean IS_OS_IRIX = getOSMatchesName("Irix");
/*   69:1153 */   public static final boolean IS_OS_LINUX = (getOSMatchesName("Linux")) || (getOSMatchesName("LINUX"));
/*   70:1166 */   public static final boolean IS_OS_MAC = getOSMatchesName("Mac");
/*   71:1179 */   public static final boolean IS_OS_MAC_OSX = getOSMatchesName("Mac OS X");
/*   72:1192 */   public static final boolean IS_OS_OS2 = getOSMatchesName("OS/2");
/*   73:1205 */   public static final boolean IS_OS_SOLARIS = getOSMatchesName("Solaris");
/*   74:1218 */   public static final boolean IS_OS_SUN_OS = getOSMatchesName("SunOS");
/*   75:1232 */   public static final boolean IS_OS_UNIX = (IS_OS_AIX) || (IS_OS_HP_UX) || (IS_OS_IRIX) || (IS_OS_LINUX) || (IS_OS_MAC_OSX) || (IS_OS_SOLARIS) || (IS_OS_SUN_OS);
/*   76:1247 */   public static final boolean IS_OS_WINDOWS = getOSMatchesName("Windows");
/*   77:1260 */   public static final boolean IS_OS_WINDOWS_2000 = getOSMatches("Windows", "5.0");
/*   78:1273 */   public static final boolean IS_OS_WINDOWS_95 = getOSMatches("Windows 9", "4.0");
/*   79:1287 */   public static final boolean IS_OS_WINDOWS_98 = getOSMatches("Windows 9", "4.1");
/*   80:1301 */   public static final boolean IS_OS_WINDOWS_ME = getOSMatches("Windows", "4.9");
/*   81:1315 */   public static final boolean IS_OS_WINDOWS_NT = getOSMatchesName("Windows NT");
/*   82:1329 */   public static final boolean IS_OS_WINDOWS_XP = getOSMatches("Windows", "5.1");
/*   83:1343 */   public static final boolean IS_OS_WINDOWS_VISTA = getOSMatches("Windows", "6.0");
/*   84:1356 */   public static final boolean IS_OS_WINDOWS_7 = getOSMatches("Windows", "6.1");
/*   85:     */   
/*   86:     */   public static File getJavaHome()
/*   87:     */   {
/*   88:1370 */     return new File(System.getProperty("java.home"));
/*   89:     */   }
/*   90:     */   
/*   91:     */   public static File getJavaIoTmpDir()
/*   92:     */   {
/*   93:1386 */     return new File(System.getProperty("java.io.tmpdir"));
/*   94:     */   }
/*   95:     */   
/*   96:     */   /**
/*   97:     */    * @deprecated
/*   98:     */    */
/*   99:     */   public static float getJavaVersion()
/*  100:     */   {
/*  101:1403 */     return JAVA_VERSION_FLOAT;
/*  102:     */   }
/*  103:     */   
/*  104:     */   private static float getJavaVersionAsFloat()
/*  105:     */   {
/*  106:1427 */     return toVersionFloat(toJavaVersionIntArray(JAVA_VERSION, 3));
/*  107:     */   }
/*  108:     */   
/*  109:     */   private static int getJavaVersionAsInt()
/*  110:     */   {
/*  111:1451 */     return toVersionInt(toJavaVersionIntArray(JAVA_VERSION, 3));
/*  112:     */   }
/*  113:     */   
/*  114:     */   private static boolean getJavaVersionMatches(String versionPrefix)
/*  115:     */   {
/*  116:1464 */     return isJavaVersionMatch(JAVA_VERSION_TRIMMED, versionPrefix);
/*  117:     */   }
/*  118:     */   
/*  119:     */   private static String getJavaVersionTrimmed()
/*  120:     */   {
/*  121:1473 */     if (JAVA_VERSION != null) {
/*  122:1474 */       for (int i = 0; i < JAVA_VERSION.length(); i++)
/*  123:     */       {
/*  124:1475 */         char ch = JAVA_VERSION.charAt(i);
/*  125:1476 */         if ((ch >= '0') && (ch <= '9')) {
/*  126:1477 */           return JAVA_VERSION.substring(i);
/*  127:     */         }
/*  128:     */       }
/*  129:     */     }
/*  130:1481 */     return null;
/*  131:     */   }
/*  132:     */   
/*  133:     */   private static boolean getOSMatches(String osNamePrefix, String osVersionPrefix)
/*  134:     */   {
/*  135:1494 */     return isOSMatch(OS_NAME, OS_VERSION, osNamePrefix, osVersionPrefix);
/*  136:     */   }
/*  137:     */   
/*  138:     */   private static boolean getOSMatchesName(String osNamePrefix)
/*  139:     */   {
/*  140:1505 */     return isOSNameMatch(OS_NAME, osNamePrefix);
/*  141:     */   }
/*  142:     */   
/*  143:     */   private static String getSystemProperty(String property)
/*  144:     */   {
/*  145:     */     try
/*  146:     */     {
/*  147:1525 */       return System.getProperty(property);
/*  148:     */     }
/*  149:     */     catch (SecurityException ex)
/*  150:     */     {
/*  151:1528 */       System.err.println("Caught a SecurityException reading the system property '" + property + "'; the SystemUtils property value will default to null.");
/*  152:     */     }
/*  153:1530 */     return null;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public static File getUserDir()
/*  157:     */   {
/*  158:1546 */     return new File(System.getProperty("user.dir"));
/*  159:     */   }
/*  160:     */   
/*  161:     */   public static File getUserHome()
/*  162:     */   {
/*  163:1561 */     return new File(System.getProperty("user.home"));
/*  164:     */   }
/*  165:     */   
/*  166:     */   public static boolean isJavaAwtHeadless()
/*  167:     */   {
/*  168:1574 */     return JAVA_AWT_HEADLESS != null ? JAVA_AWT_HEADLESS.equals(Boolean.TRUE.toString()) : false;
/*  169:     */   }
/*  170:     */   
/*  171:     */   public static boolean isJavaVersionAtLeast(float requiredVersion)
/*  172:     */   {
/*  173:1595 */     return JAVA_VERSION_FLOAT >= requiredVersion;
/*  174:     */   }
/*  175:     */   
/*  176:     */   public static boolean isJavaVersionAtLeast(int requiredVersion)
/*  177:     */   {
/*  178:1617 */     return JAVA_VERSION_INT >= requiredVersion;
/*  179:     */   }
/*  180:     */   
/*  181:     */   static boolean isJavaVersionMatch(String version, String versionPrefix)
/*  182:     */   {
/*  183:1635 */     if (version == null) {
/*  184:1636 */       return false;
/*  185:     */     }
/*  186:1638 */     return version.startsWith(versionPrefix);
/*  187:     */   }
/*  188:     */   
/*  189:     */   static boolean isOSMatch(String osName, String osVersion, String osNamePrefix, String osVersionPrefix)
/*  190:     */   {
/*  191:1658 */     if ((osName == null) || (osVersion == null)) {
/*  192:1659 */       return false;
/*  193:     */     }
/*  194:1661 */     return (osName.startsWith(osNamePrefix)) && (osVersion.startsWith(osVersionPrefix));
/*  195:     */   }
/*  196:     */   
/*  197:     */   static boolean isOSNameMatch(String osName, String osNamePrefix)
/*  198:     */   {
/*  199:1677 */     if (osName == null) {
/*  200:1678 */       return false;
/*  201:     */     }
/*  202:1680 */     return osName.startsWith(osNamePrefix);
/*  203:     */   }
/*  204:     */   
/*  205:     */   static float toJavaVersionFloat(String version)
/*  206:     */   {
/*  207:1708 */     return toVersionFloat(toJavaVersionIntArray(version, 3));
/*  208:     */   }
/*  209:     */   
/*  210:     */   static int toJavaVersionInt(String version)
/*  211:     */   {
/*  212:1736 */     return toVersionInt(toJavaVersionIntArray(version, 3));
/*  213:     */   }
/*  214:     */   
/*  215:     */   static int[] toJavaVersionIntArray(String version)
/*  216:     */   {
/*  217:1760 */     return toJavaVersionIntArray(version, 2147483647);
/*  218:     */   }
/*  219:     */   
/*  220:     */   private static int[] toJavaVersionIntArray(String version, int limit)
/*  221:     */   {
/*  222:1782 */     if (version == null) {
/*  223:1783 */       return ArrayUtils.EMPTY_INT_ARRAY;
/*  224:     */     }
/*  225:1785 */     String[] strings = StringUtils.split(version, "._- ");
/*  226:1786 */     int[] ints = new int[Math.min(limit, strings.length)];
/*  227:1787 */     int j = 0;
/*  228:1788 */     for (int i = 0; (i < strings.length) && (j < limit); i++)
/*  229:     */     {
/*  230:1789 */       String s = strings[i];
/*  231:1790 */       if (s.length() > 0) {
/*  232:     */         try
/*  233:     */         {
/*  234:1792 */           ints[j] = Integer.parseInt(s);
/*  235:1793 */           j++;
/*  236:     */         }
/*  237:     */         catch (Exception e) {}
/*  238:     */       }
/*  239:     */     }
/*  240:1798 */     if (ints.length > j)
/*  241:     */     {
/*  242:1799 */       int[] newInts = new int[j];
/*  243:1800 */       System.arraycopy(ints, 0, newInts, 0, j);
/*  244:1801 */       ints = newInts;
/*  245:     */     }
/*  246:1803 */     return ints;
/*  247:     */   }
/*  248:     */   
/*  249:     */   private static float toVersionFloat(int[] javaVersions)
/*  250:     */   {
/*  251:1828 */     if ((javaVersions == null) || (javaVersions.length == 0)) {
/*  252:1829 */       return 0.0F;
/*  253:     */     }
/*  254:1831 */     if (javaVersions.length == 1) {
/*  255:1832 */       return javaVersions[0];
/*  256:     */     }
/*  257:1834 */     StringBuffer builder = new StringBuffer();
/*  258:1835 */     builder.append(javaVersions[0]);
/*  259:1836 */     builder.append('.');
/*  260:1837 */     for (int i = 1; i < javaVersions.length; i++) {
/*  261:1838 */       builder.append(javaVersions[i]);
/*  262:     */     }
/*  263:     */     try
/*  264:     */     {
/*  265:1841 */       return Float.parseFloat(builder.toString());
/*  266:     */     }
/*  267:     */     catch (Exception ex) {}
/*  268:1843 */     return 0.0F;
/*  269:     */   }
/*  270:     */   
/*  271:     */   private static int toVersionInt(int[] javaVersions)
/*  272:     */   {
/*  273:1869 */     if (javaVersions == null) {
/*  274:1870 */       return 0;
/*  275:     */     }
/*  276:1872 */     int intVersion = 0;
/*  277:1873 */     int len = javaVersions.length;
/*  278:1874 */     if (len >= 1) {
/*  279:1875 */       intVersion = javaVersions[0] * 100;
/*  280:     */     }
/*  281:1877 */     if (len >= 2) {
/*  282:1878 */       intVersion += javaVersions[1] * 10;
/*  283:     */     }
/*  284:1880 */     if (len >= 3) {
/*  285:1881 */       intVersion += javaVersions[2];
/*  286:     */     }
/*  287:1883 */     return intVersion;
/*  288:     */   }
/*  289:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.SystemUtils
 * JD-Core Version:    0.7.0.1
 */