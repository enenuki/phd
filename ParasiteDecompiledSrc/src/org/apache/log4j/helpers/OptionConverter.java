/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.io.InterruptedIOException;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.util.Properties;
/*   8:    */ import org.apache.log4j.Level;
/*   9:    */ import org.apache.log4j.PropertyConfigurator;
/*  10:    */ import org.apache.log4j.spi.Configurator;
/*  11:    */ import org.apache.log4j.spi.LoggerRepository;
/*  12:    */ 
/*  13:    */ public class OptionConverter
/*  14:    */ {
/*  15: 42 */   static String DELIM_START = "${";
/*  16: 43 */   static char DELIM_STOP = '}';
/*  17: 44 */   static int DELIM_START_LEN = 2;
/*  18: 45 */   static int DELIM_STOP_LEN = 1;
/*  19:    */   
/*  20:    */   public static String[] concatanateArrays(String[] l, String[] r)
/*  21:    */   {
/*  22: 53 */     int len = l.length + r.length;
/*  23: 54 */     String[] a = new String[len];
/*  24:    */     
/*  25: 56 */     System.arraycopy(l, 0, a, 0, l.length);
/*  26: 57 */     System.arraycopy(r, 0, a, l.length, r.length);
/*  27:    */     
/*  28: 59 */     return a;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static String convertSpecialChars(String s)
/*  32:    */   {
/*  33: 66 */     int len = s.length();
/*  34: 67 */     StringBuffer sbuf = new StringBuffer(len);
/*  35:    */     
/*  36: 69 */     int i = 0;
/*  37: 70 */     while (i < len)
/*  38:    */     {
/*  39: 71 */       char c = s.charAt(i++);
/*  40: 72 */       if (c == '\\')
/*  41:    */       {
/*  42: 73 */         c = s.charAt(i++);
/*  43: 74 */         if (c == 'n') {
/*  44: 74 */           c = '\n';
/*  45: 75 */         } else if (c == 'r') {
/*  46: 75 */           c = '\r';
/*  47: 76 */         } else if (c == 't') {
/*  48: 76 */           c = '\t';
/*  49: 77 */         } else if (c == 'f') {
/*  50: 77 */           c = '\f';
/*  51: 78 */         } else if (c == '\b') {
/*  52: 78 */           c = '\b';
/*  53: 79 */         } else if (c == '"') {
/*  54: 79 */           c = '"';
/*  55: 80 */         } else if (c == '\'') {
/*  56: 80 */           c = '\'';
/*  57: 81 */         } else if (c == '\\') {
/*  58: 81 */           c = '\\';
/*  59:    */         }
/*  60:    */       }
/*  61: 83 */       sbuf.append(c);
/*  62:    */     }
/*  63: 85 */     return sbuf.toString();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static String getSystemProperty(String key, String def)
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70:103 */       return System.getProperty(key, def);
/*  71:    */     }
/*  72:    */     catch (Throwable e)
/*  73:    */     {
/*  74:105 */       LogLog.debug("Was not allowed to read system property \"" + key + "\".");
/*  75:    */     }
/*  76:106 */     return def;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static Object instantiateByKey(Properties props, String key, Class superClass, Object defaultValue)
/*  80:    */   {
/*  81:117 */     String className = findAndSubst(key, props);
/*  82:118 */     if (className == null)
/*  83:    */     {
/*  84:119 */       LogLog.error("Could not find value for key " + key);
/*  85:120 */       return defaultValue;
/*  86:    */     }
/*  87:123 */     return instantiateByClassName(className.trim(), superClass, defaultValue);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static boolean toBoolean(String value, boolean dEfault)
/*  91:    */   {
/*  92:137 */     if (value == null) {
/*  93:138 */       return dEfault;
/*  94:    */     }
/*  95:139 */     String trimmedVal = value.trim();
/*  96:140 */     if ("true".equalsIgnoreCase(trimmedVal)) {
/*  97:141 */       return true;
/*  98:    */     }
/*  99:142 */     if ("false".equalsIgnoreCase(trimmedVal)) {
/* 100:143 */       return false;
/* 101:    */     }
/* 102:144 */     return dEfault;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static int toInt(String value, int dEfault)
/* 106:    */   {
/* 107:150 */     if (value != null)
/* 108:    */     {
/* 109:151 */       String s = value.trim();
/* 110:    */       try
/* 111:    */       {
/* 112:153 */         return Integer.valueOf(s).intValue();
/* 113:    */       }
/* 114:    */       catch (NumberFormatException e)
/* 115:    */       {
/* 116:156 */         LogLog.error("[" + s + "] is not in proper int form.");
/* 117:157 */         e.printStackTrace();
/* 118:    */       }
/* 119:    */     }
/* 120:160 */     return dEfault;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static Level toLevel(String value, Level defaultValue)
/* 124:    */   {
/* 125:186 */     if (value == null) {
/* 126:187 */       return defaultValue;
/* 127:    */     }
/* 128:189 */     value = value.trim();
/* 129:    */     
/* 130:191 */     int hashIndex = value.indexOf('#');
/* 131:192 */     if (hashIndex == -1)
/* 132:    */     {
/* 133:193 */       if ("NULL".equalsIgnoreCase(value)) {
/* 134:194 */         return null;
/* 135:    */       }
/* 136:197 */       return Level.toLevel(value, defaultValue);
/* 137:    */     }
/* 138:201 */     Level result = defaultValue;
/* 139:    */     
/* 140:203 */     String clazz = value.substring(hashIndex + 1);
/* 141:204 */     String levelName = value.substring(0, hashIndex);
/* 142:207 */     if ("NULL".equalsIgnoreCase(levelName)) {
/* 143:208 */       return null;
/* 144:    */     }
/* 145:211 */     LogLog.debug("toLevel:class=[" + clazz + "]" + ":pri=[" + levelName + "]");
/* 146:    */     try
/* 147:    */     {
/* 148:215 */       Class customLevel = Loader.loadClass(clazz);
/* 149:    */       
/* 150:    */ 
/* 151:    */ 
/* 152:219 */       Class[] paramTypes = { String.class, Level.class };
/* 153:    */       
/* 154:    */ 
/* 155:222 */       Method toLevelMethod = customLevel.getMethod("toLevel", paramTypes);
/* 156:    */       
/* 157:    */ 
/* 158:    */ 
/* 159:226 */       Object[] params = { levelName, defaultValue };
/* 160:227 */       Object o = toLevelMethod.invoke(null, params);
/* 161:    */       
/* 162:229 */       result = (Level)o;
/* 163:    */     }
/* 164:    */     catch (ClassNotFoundException e)
/* 165:    */     {
/* 166:231 */       LogLog.warn("custom level class [" + clazz + "] not found.");
/* 167:    */     }
/* 168:    */     catch (NoSuchMethodException e)
/* 169:    */     {
/* 170:233 */       LogLog.warn("custom level class [" + clazz + "]" + " does not have a class function toLevel(String, Level)", e);
/* 171:    */     }
/* 172:    */     catch (InvocationTargetException e)
/* 173:    */     {
/* 174:236 */       if (((e.getTargetException() instanceof InterruptedException)) || ((e.getTargetException() instanceof InterruptedIOException))) {
/* 175:238 */         Thread.currentThread().interrupt();
/* 176:    */       }
/* 177:240 */       LogLog.warn("custom level class [" + clazz + "]" + " could not be instantiated", e);
/* 178:    */     }
/* 179:    */     catch (ClassCastException e)
/* 180:    */     {
/* 181:243 */       LogLog.warn("class [" + clazz + "] is not a subclass of org.apache.log4j.Level", e);
/* 182:    */     }
/* 183:    */     catch (IllegalAccessException e)
/* 184:    */     {
/* 185:246 */       LogLog.warn("class [" + clazz + "] cannot be instantiated due to access restrictions", e);
/* 186:    */     }
/* 187:    */     catch (RuntimeException e)
/* 188:    */     {
/* 189:249 */       LogLog.warn("class [" + clazz + "], level [" + levelName + "] conversion failed.", e);
/* 190:    */     }
/* 191:252 */     return result;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static long toFileSize(String value, long dEfault)
/* 195:    */   {
/* 196:258 */     if (value == null) {
/* 197:259 */       return dEfault;
/* 198:    */     }
/* 199:261 */     String s = value.trim().toUpperCase();
/* 200:262 */     long multiplier = 1L;
/* 201:    */     int index;
/* 202:265 */     if ((index = s.indexOf("KB")) != -1)
/* 203:    */     {
/* 204:266 */       multiplier = 1024L;
/* 205:267 */       s = s.substring(0, index);
/* 206:    */     }
/* 207:269 */     else if ((index = s.indexOf("MB")) != -1)
/* 208:    */     {
/* 209:270 */       multiplier = 1048576L;
/* 210:271 */       s = s.substring(0, index);
/* 211:    */     }
/* 212:273 */     else if ((index = s.indexOf("GB")) != -1)
/* 213:    */     {
/* 214:274 */       multiplier = 1073741824L;
/* 215:275 */       s = s.substring(0, index);
/* 216:    */     }
/* 217:277 */     if (s != null) {
/* 218:    */       try
/* 219:    */       {
/* 220:279 */         return Long.valueOf(s).longValue() * multiplier;
/* 221:    */       }
/* 222:    */       catch (NumberFormatException e)
/* 223:    */       {
/* 224:282 */         LogLog.error("[" + s + "] is not in proper int form.");
/* 225:283 */         LogLog.error("[" + value + "] not in expected format.", e);
/* 226:    */       }
/* 227:    */     }
/* 228:286 */     return dEfault;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public static String findAndSubst(String key, Properties props)
/* 232:    */   {
/* 233:298 */     String value = props.getProperty(key);
/* 234:299 */     if (value == null) {
/* 235:300 */       return null;
/* 236:    */     }
/* 237:    */     try
/* 238:    */     {
/* 239:303 */       return substVars(value, props);
/* 240:    */     }
/* 241:    */     catch (IllegalArgumentException e)
/* 242:    */     {
/* 243:305 */       LogLog.error("Bad option value [" + value + "].", e);
/* 244:    */     }
/* 245:306 */     return value;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public static Object instantiateByClassName(String className, Class superClass, Object defaultValue)
/* 249:    */   {
/* 250:324 */     if (className != null) {
/* 251:    */       try
/* 252:    */       {
/* 253:326 */         Class classObj = Loader.loadClass(className);
/* 254:327 */         if (!superClass.isAssignableFrom(classObj))
/* 255:    */         {
/* 256:328 */           LogLog.error("A \"" + className + "\" object is not assignable to a \"" + superClass.getName() + "\" variable.");
/* 257:    */           
/* 258:330 */           LogLog.error("The class \"" + superClass.getName() + "\" was loaded by ");
/* 259:331 */           LogLog.error("[" + superClass.getClassLoader() + "] whereas object of type ");
/* 260:332 */           LogLog.error("\"" + classObj.getName() + "\" was loaded by [" + classObj.getClassLoader() + "].");
/* 261:    */           
/* 262:334 */           return defaultValue;
/* 263:    */         }
/* 264:336 */         return classObj.newInstance();
/* 265:    */       }
/* 266:    */       catch (ClassNotFoundException e)
/* 267:    */       {
/* 268:338 */         LogLog.error("Could not instantiate class [" + className + "].", e);
/* 269:    */       }
/* 270:    */       catch (IllegalAccessException e)
/* 271:    */       {
/* 272:340 */         LogLog.error("Could not instantiate class [" + className + "].", e);
/* 273:    */       }
/* 274:    */       catch (InstantiationException e)
/* 275:    */       {
/* 276:342 */         LogLog.error("Could not instantiate class [" + className + "].", e);
/* 277:    */       }
/* 278:    */       catch (RuntimeException e)
/* 279:    */       {
/* 280:344 */         LogLog.error("Could not instantiate class [" + className + "].", e);
/* 281:    */       }
/* 282:    */     }
/* 283:347 */     return defaultValue;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public static String substVars(String val, Properties props)
/* 287:    */     throws IllegalArgumentException
/* 288:    */   {
/* 289:391 */     StringBuffer sbuf = new StringBuffer();
/* 290:    */     
/* 291:393 */     int i = 0;
/* 292:    */     for (;;)
/* 293:    */     {
/* 294:397 */       int j = val.indexOf(DELIM_START, i);
/* 295:398 */       if (j == -1)
/* 296:    */       {
/* 297:400 */         if (i == 0) {
/* 298:401 */           return val;
/* 299:    */         }
/* 300:403 */         sbuf.append(val.substring(i, val.length()));
/* 301:404 */         return sbuf.toString();
/* 302:    */       }
/* 303:407 */       sbuf.append(val.substring(i, j));
/* 304:408 */       int k = val.indexOf(DELIM_STOP, j);
/* 305:409 */       if (k == -1) {
/* 306:410 */         throw new IllegalArgumentException('"' + val + "\" has no closing brace. Opening brace at position " + j + '.');
/* 307:    */       }
/* 308:414 */       j += DELIM_START_LEN;
/* 309:415 */       String key = val.substring(j, k);
/* 310:    */       
/* 311:417 */       String replacement = getSystemProperty(key, null);
/* 312:419 */       if ((replacement == null) && (props != null)) {
/* 313:420 */         replacement = props.getProperty(key);
/* 314:    */       }
/* 315:423 */       if (replacement != null)
/* 316:    */       {
/* 317:429 */         String recursiveReplacement = substVars(replacement, props);
/* 318:430 */         sbuf.append(recursiveReplacement);
/* 319:    */       }
/* 320:432 */       i = k + DELIM_STOP_LEN;
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public static void selectAndConfigure(URL url, String clazz, LoggerRepository hierarchy)
/* 325:    */   {
/* 326:463 */     Configurator configurator = null;
/* 327:464 */     String filename = url.getFile();
/* 328:466 */     if ((clazz == null) && (filename != null) && (filename.endsWith(".xml"))) {
/* 329:467 */       clazz = "org.apache.log4j.xml.DOMConfigurator";
/* 330:    */     }
/* 331:470 */     if (clazz != null)
/* 332:    */     {
/* 333:471 */       LogLog.debug("Preferred configurator class: " + clazz);
/* 334:472 */       configurator = (Configurator)instantiateByClassName(clazz, Configurator.class, null);
/* 335:475 */       if (configurator == null) {
/* 336:476 */         LogLog.error("Could not instantiate configurator [" + clazz + "].");
/* 337:    */       }
/* 338:    */     }
/* 339:    */     else
/* 340:    */     {
/* 341:480 */       configurator = new PropertyConfigurator();
/* 342:    */     }
/* 343:483 */     configurator.doConfigure(url, hierarchy);
/* 344:    */   }
/* 345:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.OptionConverter
 * JD-Core Version:    0.7.0.1
 */