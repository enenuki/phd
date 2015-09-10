/*   1:    */ package junit.runner;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.FileOutputStream;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.io.PrintWriter;
/*  11:    */ import java.io.StringReader;
/*  12:    */ import java.io.StringWriter;
/*  13:    */ import java.lang.reflect.InvocationTargetException;
/*  14:    */ import java.lang.reflect.Method;
/*  15:    */ import java.lang.reflect.Modifier;
/*  16:    */ import java.text.NumberFormat;
/*  17:    */ import java.util.Properties;
/*  18:    */ import junit.framework.AssertionFailedError;
/*  19:    */ import junit.framework.Test;
/*  20:    */ import junit.framework.TestListener;
/*  21:    */ import junit.framework.TestSuite;
/*  22:    */ 
/*  23:    */ public abstract class BaseTestRunner
/*  24:    */   implements TestListener
/*  25:    */ {
/*  26: 32 */   static boolean fgFilterStack = true;
/*  27: 33 */   boolean fLoading = true;
/*  28:    */   
/*  29:    */   public synchronized void startTest(Test test)
/*  30:    */   {
/*  31: 39 */     testStarted(test.toString());
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected static void setPreferences(Properties preferences)
/*  35:    */   {
/*  36: 43 */     fPreferences = preferences;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected static Properties getPreferences()
/*  40:    */   {
/*  41: 47 */     if (fPreferences == null)
/*  42:    */     {
/*  43: 48 */       fPreferences = new Properties();
/*  44: 49 */       fPreferences.put("loading", "true");
/*  45: 50 */       fPreferences.put("filterstack", "true");
/*  46: 51 */       readPreferences();
/*  47:    */     }
/*  48: 53 */     return fPreferences;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static void savePreferences()
/*  52:    */     throws IOException
/*  53:    */   {
/*  54: 57 */     FileOutputStream fos = new FileOutputStream(getPreferencesFile());
/*  55:    */     try
/*  56:    */     {
/*  57: 59 */       getPreferences().store(fos, "");
/*  58:    */     }
/*  59:    */     finally
/*  60:    */     {
/*  61: 61 */       fos.close();
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static void setPreference(String key, String value)
/*  66:    */   {
/*  67: 66 */     getPreferences().put(key, value);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public synchronized void endTest(Test test)
/*  71:    */   {
/*  72: 70 */     testEnded(test.toString());
/*  73:    */   }
/*  74:    */   
/*  75:    */   public synchronized void addError(Test test, Throwable t)
/*  76:    */   {
/*  77: 74 */     testFailed(1, test, t);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public synchronized void addFailure(Test test, AssertionFailedError t)
/*  81:    */   {
/*  82: 78 */     testFailed(2, test, t);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public abstract void testStarted(String paramString);
/*  86:    */   
/*  87:    */   public abstract void testEnded(String paramString);
/*  88:    */   
/*  89:    */   public abstract void testFailed(int paramInt, Test paramTest, Throwable paramThrowable);
/*  90:    */   
/*  91:    */   public Test getTest(String suiteClassName)
/*  92:    */   {
/*  93: 94 */     if (suiteClassName.length() <= 0)
/*  94:    */     {
/*  95: 95 */       clearStatus();
/*  96: 96 */       return null;
/*  97:    */     }
/*  98: 98 */     Class<?> testClass = null;
/*  99:    */     try
/* 100:    */     {
/* 101:100 */       testClass = loadSuiteClass(suiteClassName);
/* 102:    */     }
/* 103:    */     catch (ClassNotFoundException e)
/* 104:    */     {
/* 105:102 */       String clazz = e.getMessage();
/* 106:103 */       if (clazz == null) {
/* 107:104 */         clazz = suiteClassName;
/* 108:    */       }
/* 109:105 */       runFailed("Class not found \"" + clazz + "\"");
/* 110:106 */       return null;
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:108 */       runFailed("Error: " + e.toString());
/* 115:109 */       return null;
/* 116:    */     }
/* 117:111 */     Method suiteMethod = null;
/* 118:    */     try
/* 119:    */     {
/* 120:113 */       suiteMethod = testClass.getMethod("suite", new Class[0]);
/* 121:    */     }
/* 122:    */     catch (Exception e)
/* 123:    */     {
/* 124:116 */       clearStatus();
/* 125:117 */       return new TestSuite(testClass);
/* 126:    */     }
/* 127:119 */     if (!Modifier.isStatic(suiteMethod.getModifiers()))
/* 128:    */     {
/* 129:120 */       runFailed("Suite() method must be static");
/* 130:121 */       return null;
/* 131:    */     }
/* 132:123 */     Test test = null;
/* 133:    */     try
/* 134:    */     {
/* 135:125 */       test = (Test)suiteMethod.invoke(null, (Object[])new Class[0]);
/* 136:126 */       if (test == null) {
/* 137:127 */         return test;
/* 138:    */       }
/* 139:    */     }
/* 140:    */     catch (InvocationTargetException e)
/* 141:    */     {
/* 142:130 */       runFailed("Failed to invoke suite():" + e.getTargetException().toString());
/* 143:131 */       return null;
/* 144:    */     }
/* 145:    */     catch (IllegalAccessException e)
/* 146:    */     {
/* 147:134 */       runFailed("Failed to invoke suite():" + e.toString());
/* 148:135 */       return null;
/* 149:    */     }
/* 150:138 */     clearStatus();
/* 151:139 */     return test;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String elapsedTimeAsString(long runTime)
/* 155:    */   {
/* 156:146 */     return NumberFormat.getInstance().format(runTime / 1000.0D);
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected String processArguments(String[] args)
/* 160:    */   {
/* 161:154 */     String suiteName = null;
/* 162:155 */     for (int i = 0; i < args.length; i++) {
/* 163:156 */       if (args[i].equals("-noloading"))
/* 164:    */       {
/* 165:157 */         setLoading(false);
/* 166:    */       }
/* 167:158 */       else if (args[i].equals("-nofilterstack"))
/* 168:    */       {
/* 169:159 */         fgFilterStack = false;
/* 170:    */       }
/* 171:160 */       else if (args[i].equals("-c"))
/* 172:    */       {
/* 173:161 */         if (args.length > i + 1) {
/* 174:162 */           suiteName = extractClassName(args[(i + 1)]);
/* 175:    */         } else {
/* 176:164 */           System.out.println("Missing Test class name");
/* 177:    */         }
/* 178:165 */         i++;
/* 179:    */       }
/* 180:    */       else
/* 181:    */       {
/* 182:167 */         suiteName = args[i];
/* 183:    */       }
/* 184:    */     }
/* 185:170 */     return suiteName;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setLoading(boolean enable)
/* 189:    */   {
/* 190:177 */     this.fLoading = enable;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String extractClassName(String className)
/* 194:    */   {
/* 195:183 */     if (className.startsWith("Default package for")) {
/* 196:184 */       return className.substring(className.lastIndexOf(".") + 1);
/* 197:    */     }
/* 198:185 */     return className;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static String truncate(String s)
/* 202:    */   {
/* 203:192 */     if ((fgMaxMessageLength != -1) && (s.length() > fgMaxMessageLength)) {
/* 204:193 */       s = s.substring(0, fgMaxMessageLength) + "...";
/* 205:    */     }
/* 206:194 */     return s;
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected abstract void runFailed(String paramString);
/* 210:    */   
/* 211:    */   protected Class<?> loadSuiteClass(String suiteClassName)
/* 212:    */     throws ClassNotFoundException
/* 213:    */   {
/* 214:207 */     return Class.forName(suiteClassName);
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected void clearStatus() {}
/* 218:    */   
/* 219:    */   protected boolean useReloadingTestSuiteLoader()
/* 220:    */   {
/* 221:217 */     return (getPreference("loading").equals("true")) && (this.fLoading);
/* 222:    */   }
/* 223:    */   
/* 224:    */   private static File getPreferencesFile()
/* 225:    */   {
/* 226:221 */     String home = System.getProperty("user.home");
/* 227:222 */     return new File(home, "junit.properties");
/* 228:    */   }
/* 229:    */   
/* 230:    */   private static void readPreferences()
/* 231:    */   {
/* 232:226 */     InputStream is = null;
/* 233:    */     try
/* 234:    */     {
/* 235:228 */       is = new FileInputStream(getPreferencesFile());
/* 236:229 */       setPreferences(new Properties(getPreferences()));
/* 237:230 */       getPreferences().load(is);
/* 238:    */     }
/* 239:    */     catch (IOException e)
/* 240:    */     {
/* 241:    */       try
/* 242:    */       {
/* 243:233 */         if (is != null) {
/* 244:234 */           is.close();
/* 245:    */         }
/* 246:    */       }
/* 247:    */       catch (IOException e1) {}
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public static String getPreference(String key)
/* 252:    */   {
/* 253:241 */     return getPreferences().getProperty(key);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public static int getPreference(String key, int dflt)
/* 257:    */   {
/* 258:245 */     String value = getPreference(key);
/* 259:246 */     int intValue = dflt;
/* 260:247 */     if (value == null) {
/* 261:248 */       return intValue;
/* 262:    */     }
/* 263:    */     try
/* 264:    */     {
/* 265:250 */       intValue = Integer.parseInt(value);
/* 266:    */     }
/* 267:    */     catch (NumberFormatException ne) {}
/* 268:253 */     return intValue;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public static String getFilteredTrace(Throwable t)
/* 272:    */   {
/* 273:260 */     StringWriter stringWriter = new StringWriter();
/* 274:261 */     PrintWriter writer = new PrintWriter(stringWriter);
/* 275:262 */     t.printStackTrace(writer);
/* 276:263 */     StringBuffer buffer = stringWriter.getBuffer();
/* 277:264 */     String trace = buffer.toString();
/* 278:265 */     return getFilteredTrace(trace);
/* 279:    */   }
/* 280:    */   
/* 281:    */   public static String getFilteredTrace(String stack)
/* 282:    */   {
/* 283:272 */     if (showStackRaw()) {
/* 284:273 */       return stack;
/* 285:    */     }
/* 286:275 */     StringWriter sw = new StringWriter();
/* 287:276 */     PrintWriter pw = new PrintWriter(sw);
/* 288:277 */     StringReader sr = new StringReader(stack);
/* 289:278 */     BufferedReader br = new BufferedReader(sr);
/* 290:    */     try
/* 291:    */     {
/* 292:    */       String line;
/* 293:282 */       while ((line = br.readLine()) != null) {
/* 294:283 */         if (!filterLine(line)) {
/* 295:284 */           pw.println(line);
/* 296:    */         }
/* 297:    */       }
/* 298:    */     }
/* 299:    */     catch (Exception IOException)
/* 300:    */     {
/* 301:287 */       return stack;
/* 302:    */     }
/* 303:289 */     return sw.toString();
/* 304:    */   }
/* 305:    */   
/* 306:    */   protected static boolean showStackRaw()
/* 307:    */   {
/* 308:293 */     return (!getPreference("filterstack").equals("true")) || (!fgFilterStack);
/* 309:    */   }
/* 310:    */   
/* 311:    */   static boolean filterLine(String line)
/* 312:    */   {
/* 313:297 */     String[] patterns = { "junit.framework.TestCase", "junit.framework.TestResult", "junit.framework.TestSuite", "junit.framework.Assert.", "junit.swingui.TestRunner", "junit.awtui.TestRunner", "junit.textui.TestRunner", "java.lang.reflect.Method.invoke(" };
/* 314:307 */     for (int i = 0; i < patterns.length; i++) {
/* 315:308 */       if (line.indexOf(patterns[i]) > 0) {
/* 316:309 */         return true;
/* 317:    */       }
/* 318:    */     }
/* 319:311 */     return false;
/* 320:    */   }
/* 321:    */   
/* 322:315 */   static int fgMaxMessageLength = getPreference("maxmessage", fgMaxMessageLength);
/* 323:    */   public static final String SUITE_METHODNAME = "suite";
/* 324:    */   private static Properties fPreferences;
/* 325:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     junit.runner.BaseTestRunner
 * JD-Core Version:    0.7.0.1
 */