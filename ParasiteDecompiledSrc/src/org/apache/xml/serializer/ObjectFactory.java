/*   1:    */ package org.apache.xml.serializer;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileInputStream;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.io.InputStreamReader;
/*   9:    */ import java.io.UnsupportedEncodingException;
/*  10:    */ import java.util.Properties;
/*  11:    */ 
/*  12:    */ class ObjectFactory
/*  13:    */ {
/*  14:    */   private static final String DEFAULT_PROPERTIES_FILENAME = "xalan.properties";
/*  15:    */   private static final String SERVICES_PATH = "META-INF/services/";
/*  16:    */   private static final boolean DEBUG = false;
/*  17: 69 */   private static Properties fXalanProperties = null;
/*  18: 76 */   private static long fLastModified = -1L;
/*  19:    */   
/*  20:    */   static Object createObject(String factoryId, String fallbackClassName)
/*  21:    */     throws ObjectFactory.ConfigurationError
/*  22:    */   {
/*  23:102 */     return createObject(factoryId, null, fallbackClassName);
/*  24:    */   }
/*  25:    */   
/*  26:    */   static Object createObject(String factoryId, String propertiesFilename, String fallbackClassName)
/*  27:    */     throws ObjectFactory.ConfigurationError
/*  28:    */   {
/*  29:132 */     Class factoryClass = lookUpFactoryClass(factoryId, propertiesFilename, fallbackClassName);
/*  30:136 */     if (factoryClass == null) {
/*  31:137 */       throw new ConfigurationError("Provider for " + factoryId + " cannot be found", null);
/*  32:    */     }
/*  33:    */     try
/*  34:    */     {
/*  35:142 */       Object instance = factoryClass.newInstance();
/*  36:143 */       debugPrintln("created new instance of factory " + factoryId);
/*  37:144 */       return instance;
/*  38:    */     }
/*  39:    */     catch (Exception x)
/*  40:    */     {
/*  41:146 */       throw new ConfigurationError("Provider for factory " + factoryId + " could not be instantiated: " + x, x);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   static Class lookUpFactoryClass(String factoryId)
/*  46:    */     throws ObjectFactory.ConfigurationError
/*  47:    */   {
/*  48:177 */     return lookUpFactoryClass(factoryId, null, null);
/*  49:    */   }
/*  50:    */   
/*  51:    */   static Class lookUpFactoryClass(String factoryId, String propertiesFilename, String fallbackClassName)
/*  52:    */     throws ObjectFactory.ConfigurationError
/*  53:    */   {
/*  54:207 */     String factoryClassName = lookUpFactoryClassName(factoryId, propertiesFilename, fallbackClassName);
/*  55:    */     
/*  56:    */ 
/*  57:210 */     ClassLoader cl = findClassLoader();
/*  58:212 */     if (factoryClassName == null) {
/*  59:213 */       factoryClassName = fallbackClassName;
/*  60:    */     }
/*  61:    */     try
/*  62:    */     {
/*  63:218 */       Class providerClass = findProviderClass(factoryClassName, cl, true);
/*  64:    */       
/*  65:    */ 
/*  66:221 */       debugPrintln("created new instance of " + providerClass + " using ClassLoader: " + cl);
/*  67:    */       
/*  68:223 */       return providerClass;
/*  69:    */     }
/*  70:    */     catch (ClassNotFoundException x)
/*  71:    */     {
/*  72:225 */       throw new ConfigurationError("Provider " + factoryClassName + " not found", x);
/*  73:    */     }
/*  74:    */     catch (Exception x)
/*  75:    */     {
/*  76:228 */       throw new ConfigurationError("Provider " + factoryClassName + " could not be instantiated: " + x, x);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   static String lookUpFactoryClassName(String factoryId, String propertiesFilename, String fallbackClassName)
/*  81:    */   {
/*  82:260 */     SecuritySupport ss = SecuritySupport.getInstance();
/*  83:    */     try
/*  84:    */     {
/*  85:264 */       String systemProp = ss.getSystemProperty(factoryId);
/*  86:265 */       if (systemProp != null)
/*  87:    */       {
/*  88:266 */         debugPrintln("found system property, value=" + systemProp);
/*  89:267 */         return systemProp;
/*  90:    */       }
/*  91:    */     }
/*  92:    */     catch (SecurityException se) {}
/*  93:275 */     String factoryClassName = null;
/*  94:278 */     if (propertiesFilename == null)
/*  95:    */     {
/*  96:279 */       File propertiesFile = null;
/*  97:280 */       boolean propertiesFileExists = false;
/*  98:    */       try
/*  99:    */       {
/* 100:282 */         String javah = ss.getSystemProperty("java.home");
/* 101:283 */         propertiesFilename = javah + File.separator + "lib" + File.separator + "xalan.properties";
/* 102:    */         
/* 103:285 */         propertiesFile = new File(propertiesFilename);
/* 104:286 */         propertiesFileExists = ss.getFileExists(propertiesFile);
/* 105:    */       }
/* 106:    */       catch (SecurityException javah)
/* 107:    */       {
/* 108:289 */         fLastModified = -1L;
/* 109:290 */         fXalanProperties = null;
/* 110:    */       }
/* 111:293 */       synchronized (ObjectFactory.class)
/* 112:    */       {
/* 113:294 */         boolean loadProperties = false;
/* 114:295 */         FileInputStream fis = null;
/* 115:    */         try
/* 116:    */         {
/* 117:298 */           if (fLastModified >= 0L)
/* 118:    */           {
/* 119:299 */             if ((propertiesFileExists) && (fLastModified < (ObjectFactory.fLastModified = ss.getLastModified(propertiesFile))))
/* 120:    */             {
/* 121:301 */               loadProperties = true;
/* 122:    */             }
/* 123:304 */             else if (!propertiesFileExists)
/* 124:    */             {
/* 125:305 */               fLastModified = -1L;
/* 126:306 */               fXalanProperties = null;
/* 127:    */             }
/* 128:    */           }
/* 129:311 */           else if (propertiesFileExists)
/* 130:    */           {
/* 131:312 */             loadProperties = true;
/* 132:313 */             fLastModified = ss.getLastModified(propertiesFile);
/* 133:    */           }
/* 134:316 */           if (loadProperties)
/* 135:    */           {
/* 136:319 */             fXalanProperties = new Properties();
/* 137:320 */             fis = ss.getFileInputStream(propertiesFile);
/* 138:321 */             fXalanProperties.load(fis);
/* 139:    */           }
/* 140:    */         }
/* 141:    */         catch (Exception x)
/* 142:    */         {
/* 143:324 */           fXalanProperties = null;
/* 144:325 */           fLastModified = -1L;
/* 145:    */         }
/* 146:    */         finally
/* 147:    */         {
/* 148:332 */           if (fis != null) {
/* 149:    */             try
/* 150:    */             {
/* 151:334 */               fis.close();
/* 152:    */             }
/* 153:    */             catch (IOException exc) {}
/* 154:    */           }
/* 155:    */         }
/* 156:    */       }
/* 157:341 */       if (fXalanProperties != null) {
/* 158:342 */         factoryClassName = fXalanProperties.getProperty(factoryId);
/* 159:    */       }
/* 160:    */     }
/* 161:    */     else
/* 162:    */     {
/* 163:345 */       FileInputStream fis = null;
/* 164:    */       try
/* 165:    */       {
/* 166:347 */         fis = ss.getFileInputStream(new File(propertiesFilename));
/* 167:348 */         Properties props = new Properties();
/* 168:349 */         props.load(fis);
/* 169:350 */         factoryClassName = props.getProperty(factoryId);
/* 170:    */       }
/* 171:    */       catch (Exception x) {}finally
/* 172:    */       {
/* 173:358 */         if (fis != null) {
/* 174:    */           try
/* 175:    */           {
/* 176:360 */             fis.close();
/* 177:    */           }
/* 178:    */           catch (IOException exc) {}
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:367 */     if (factoryClassName != null)
/* 183:    */     {
/* 184:368 */       debugPrintln("found in " + propertiesFilename + ", value=" + factoryClassName);
/* 185:    */       
/* 186:370 */       return factoryClassName;
/* 187:    */     }
/* 188:374 */     return findJarServiceProviderName(factoryId);
/* 189:    */   }
/* 190:    */   
/* 191:    */   private static void debugPrintln(String msg) {}
/* 192:    */   
/* 193:    */   static ClassLoader findClassLoader()
/* 194:    */     throws ObjectFactory.ConfigurationError
/* 195:    */   {
/* 196:395 */     SecuritySupport ss = SecuritySupport.getInstance();
/* 197:    */     
/* 198:    */ 
/* 199:    */ 
/* 200:399 */     ClassLoader context = ss.getContextClassLoader();
/* 201:400 */     ClassLoader system = ss.getSystemClassLoader();
/* 202:    */     
/* 203:402 */     ClassLoader chain = system;
/* 204:    */     for (;;)
/* 205:    */     {
/* 206:404 */       if (context == chain)
/* 207:    */       {
/* 208:413 */         ClassLoader current = ObjectFactory.class.getClassLoader();
/* 209:    */         
/* 210:415 */         chain = system;
/* 211:    */         for (;;)
/* 212:    */         {
/* 213:417 */           if (current == chain) {
/* 214:420 */             return system;
/* 215:    */           }
/* 216:422 */           if (chain == null) {
/* 217:    */             break;
/* 218:    */           }
/* 219:425 */           chain = ss.getParentClassLoader(chain);
/* 220:    */         }
/* 221:430 */         return current;
/* 222:    */       }
/* 223:433 */       if (chain == null) {
/* 224:    */         break;
/* 225:    */       }
/* 226:440 */       chain = ss.getParentClassLoader(chain);
/* 227:    */     }
/* 228:445 */     return context;
/* 229:    */   }
/* 230:    */   
/* 231:    */   static Object newInstance(String className, ClassLoader cl, boolean doFallback)
/* 232:    */     throws ObjectFactory.ConfigurationError
/* 233:    */   {
/* 234:    */     try
/* 235:    */     {
/* 236:457 */       Class providerClass = findProviderClass(className, cl, doFallback);
/* 237:458 */       Object instance = providerClass.newInstance();
/* 238:459 */       debugPrintln("created new instance of " + providerClass + " using ClassLoader: " + cl);
/* 239:    */       
/* 240:461 */       return instance;
/* 241:    */     }
/* 242:    */     catch (ClassNotFoundException x)
/* 243:    */     {
/* 244:463 */       throw new ConfigurationError("Provider " + className + " not found", x);
/* 245:    */     }
/* 246:    */     catch (Exception x)
/* 247:    */     {
/* 248:466 */       throw new ConfigurationError("Provider " + className + " could not be instantiated: " + x, x);
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   static Class findProviderClass(String className, ClassLoader cl, boolean doFallback)
/* 253:    */     throws ClassNotFoundException, ObjectFactory.ConfigurationError
/* 254:    */   {
/* 255:481 */     SecurityManager security = System.getSecurityManager();
/* 256:    */     try
/* 257:    */     {
/* 258:483 */       if (security != null)
/* 259:    */       {
/* 260:484 */         int lastDot = className.lastIndexOf(".");
/* 261:485 */         String packageName = className;
/* 262:486 */         if (lastDot != -1) {
/* 263:486 */           packageName = className.substring(0, lastDot);
/* 264:    */         }
/* 265:487 */         security.checkPackageAccess(packageName);
/* 266:    */       }
/* 267:    */     }
/* 268:    */     catch (SecurityException e)
/* 269:    */     {
/* 270:490 */       throw e;
/* 271:    */     }
/* 272:    */     Class providerClass;
/* 273:494 */     if (cl == null) {
/* 274:504 */       providerClass = Class.forName(className);
/* 275:    */     } else {
/* 276:    */       try
/* 277:    */       {
/* 278:507 */         providerClass = cl.loadClass(className);
/* 279:    */       }
/* 280:    */       catch (ClassNotFoundException x)
/* 281:    */       {
/* 282:509 */         if (doFallback)
/* 283:    */         {
/* 284:511 */           ClassLoader current = ObjectFactory.class.getClassLoader();
/* 285:512 */           if (current == null)
/* 286:    */           {
/* 287:513 */             providerClass = Class.forName(className);
/* 288:    */           }
/* 289:514 */           else if (cl != current)
/* 290:    */           {
/* 291:515 */             cl = current;
/* 292:516 */             providerClass = cl.loadClass(className);
/* 293:    */           }
/* 294:    */           else
/* 295:    */           {
/* 296:518 */             throw x;
/* 297:    */           }
/* 298:    */         }
/* 299:    */         else
/* 300:    */         {
/* 301:521 */           throw x;
/* 302:    */         }
/* 303:    */       }
/* 304:    */     }
/* 305:526 */     return providerClass;
/* 306:    */   }
/* 307:    */   
/* 308:    */   private static String findJarServiceProviderName(String factoryId)
/* 309:    */   {
/* 310:536 */     SecuritySupport ss = SecuritySupport.getInstance();
/* 311:537 */     String serviceId = "META-INF/services/" + factoryId;
/* 312:538 */     InputStream is = null;
/* 313:    */     
/* 314:    */ 
/* 315:541 */     ClassLoader cl = findClassLoader();
/* 316:    */     
/* 317:543 */     is = ss.getResourceAsStream(cl, serviceId);
/* 318:546 */     if (is == null)
/* 319:    */     {
/* 320:547 */       ClassLoader current = ObjectFactory.class.getClassLoader();
/* 321:548 */       if (cl != current)
/* 322:    */       {
/* 323:549 */         cl = current;
/* 324:550 */         is = ss.getResourceAsStream(cl, serviceId);
/* 325:    */       }
/* 326:    */     }
/* 327:554 */     if (is == null) {
/* 328:556 */       return null;
/* 329:    */     }
/* 330:559 */     debugPrintln("found jar resource=" + serviceId + " using ClassLoader: " + cl);
/* 331:    */     BufferedReader rd;
/* 332:    */     try
/* 333:    */     {
/* 334:580 */       rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/* 335:    */     }
/* 336:    */     catch (UnsupportedEncodingException e)
/* 337:    */     {
/* 338:582 */       rd = new BufferedReader(new InputStreamReader(is));
/* 339:    */     }
/* 340:585 */     String factoryClassName = null;
/* 341:    */     try
/* 342:    */     {
/* 343:589 */       factoryClassName = rd.readLine();
/* 344:    */     }
/* 345:    */     catch (IOException x)
/* 346:    */     {
/* 347:592 */       return null;
/* 348:    */     }
/* 349:    */     finally
/* 350:    */     {
/* 351:    */       try
/* 352:    */       {
/* 353:597 */         rd.close();
/* 354:    */       }
/* 355:    */       catch (IOException exc) {}
/* 356:    */     }
/* 357:603 */     if ((factoryClassName != null) && (!"".equals(factoryClassName)))
/* 358:    */     {
/* 359:605 */       debugPrintln("found in resource, value=" + factoryClassName);
/* 360:    */       
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:612 */       return factoryClassName;
/* 367:    */     }
/* 368:616 */     return null;
/* 369:    */   }
/* 370:    */   
/* 371:    */   static class ConfigurationError
/* 372:    */     extends Error
/* 373:    */   {
/* 374:    */     static final long serialVersionUID = 8859254254255146542L;
/* 375:    */     private Exception exception;
/* 376:    */     
/* 377:    */     ConfigurationError(String msg, Exception x)
/* 378:    */     {
/* 379:645 */       super();
/* 380:646 */       this.exception = x;
/* 381:    */     }
/* 382:    */     
/* 383:    */     Exception getException()
/* 384:    */     {
/* 385:655 */       return this.exception;
/* 386:    */     }
/* 387:    */   }
/* 388:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ObjectFactory
 * JD-Core Version:    0.7.0.1
 */