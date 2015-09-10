/*   1:    */ package org.apache.xalan.xsltc.runtime;
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
/*  17: 70 */   private static Properties fXalanProperties = null;
/*  18: 77 */   private static long fLastModified = -1L;
/*  19:    */   
/*  20:    */   static Object createObject(String factoryId, String fallbackClassName)
/*  21:    */     throws ObjectFactory.ConfigurationError
/*  22:    */   {
/*  23:103 */     return createObject(factoryId, null, fallbackClassName);
/*  24:    */   }
/*  25:    */   
/*  26:    */   static Object createObject(String factoryId, String propertiesFilename, String fallbackClassName)
/*  27:    */     throws ObjectFactory.ConfigurationError
/*  28:    */   {
/*  29:133 */     Class factoryClass = lookUpFactoryClass(factoryId, propertiesFilename, fallbackClassName);
/*  30:137 */     if (factoryClass == null) {
/*  31:138 */       throw new ConfigurationError("Provider for " + factoryId + " cannot be found", null);
/*  32:    */     }
/*  33:    */     try
/*  34:    */     {
/*  35:143 */       Object instance = factoryClass.newInstance();
/*  36:144 */       debugPrintln("created new instance of factory " + factoryId);
/*  37:145 */       return instance;
/*  38:    */     }
/*  39:    */     catch (Exception x)
/*  40:    */     {
/*  41:147 */       throw new ConfigurationError("Provider for factory " + factoryId + " could not be instantiated: " + x, x);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   static Class lookUpFactoryClass(String factoryId)
/*  46:    */     throws ObjectFactory.ConfigurationError
/*  47:    */   {
/*  48:178 */     return lookUpFactoryClass(factoryId, null, null);
/*  49:    */   }
/*  50:    */   
/*  51:    */   static Class lookUpFactoryClass(String factoryId, String propertiesFilename, String fallbackClassName)
/*  52:    */     throws ObjectFactory.ConfigurationError
/*  53:    */   {
/*  54:208 */     String factoryClassName = lookUpFactoryClassName(factoryId, propertiesFilename, fallbackClassName);
/*  55:    */     
/*  56:    */ 
/*  57:211 */     ClassLoader cl = findClassLoader();
/*  58:213 */     if (factoryClassName == null) {
/*  59:214 */       factoryClassName = fallbackClassName;
/*  60:    */     }
/*  61:    */     try
/*  62:    */     {
/*  63:219 */       Class providerClass = findProviderClass(factoryClassName, cl, true);
/*  64:    */       
/*  65:    */ 
/*  66:222 */       debugPrintln("created new instance of " + providerClass + " using ClassLoader: " + cl);
/*  67:    */       
/*  68:224 */       return providerClass;
/*  69:    */     }
/*  70:    */     catch (ClassNotFoundException x)
/*  71:    */     {
/*  72:226 */       throw new ConfigurationError("Provider " + factoryClassName + " not found", x);
/*  73:    */     }
/*  74:    */     catch (Exception x)
/*  75:    */     {
/*  76:229 */       throw new ConfigurationError("Provider " + factoryClassName + " could not be instantiated: " + x, x);
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   static String lookUpFactoryClassName(String factoryId, String propertiesFilename, String fallbackClassName)
/*  81:    */   {
/*  82:261 */     SecuritySupport ss = SecuritySupport.getInstance();
/*  83:    */     try
/*  84:    */     {
/*  85:265 */       String systemProp = ss.getSystemProperty(factoryId);
/*  86:266 */       if (systemProp != null)
/*  87:    */       {
/*  88:267 */         debugPrintln("found system property, value=" + systemProp);
/*  89:268 */         return systemProp;
/*  90:    */       }
/*  91:    */     }
/*  92:    */     catch (SecurityException se) {}
/*  93:276 */     String factoryClassName = null;
/*  94:279 */     if (propertiesFilename == null)
/*  95:    */     {
/*  96:280 */       File propertiesFile = null;
/*  97:281 */       boolean propertiesFileExists = false;
/*  98:    */       try
/*  99:    */       {
/* 100:283 */         String javah = ss.getSystemProperty("java.home");
/* 101:284 */         propertiesFilename = javah + File.separator + "lib" + File.separator + "xalan.properties";
/* 102:    */         
/* 103:286 */         propertiesFile = new File(propertiesFilename);
/* 104:287 */         propertiesFileExists = ss.getFileExists(propertiesFile);
/* 105:    */       }
/* 106:    */       catch (SecurityException javah)
/* 107:    */       {
/* 108:290 */         fLastModified = -1L;
/* 109:291 */         fXalanProperties = null;
/* 110:    */       }
/* 111:294 */       synchronized (ObjectFactory.class)
/* 112:    */       {
/* 113:295 */         boolean loadProperties = false;
/* 114:296 */         FileInputStream fis = null;
/* 115:    */         try
/* 116:    */         {
/* 117:299 */           if (fLastModified >= 0L)
/* 118:    */           {
/* 119:300 */             if ((propertiesFileExists) && (fLastModified < (ObjectFactory.fLastModified = ss.getLastModified(propertiesFile))))
/* 120:    */             {
/* 121:302 */               loadProperties = true;
/* 122:    */             }
/* 123:305 */             else if (!propertiesFileExists)
/* 124:    */             {
/* 125:306 */               fLastModified = -1L;
/* 126:307 */               fXalanProperties = null;
/* 127:    */             }
/* 128:    */           }
/* 129:312 */           else if (propertiesFileExists)
/* 130:    */           {
/* 131:313 */             loadProperties = true;
/* 132:314 */             fLastModified = ss.getLastModified(propertiesFile);
/* 133:    */           }
/* 134:317 */           if (loadProperties)
/* 135:    */           {
/* 136:320 */             fXalanProperties = new Properties();
/* 137:321 */             fis = ss.getFileInputStream(propertiesFile);
/* 138:322 */             fXalanProperties.load(fis);
/* 139:    */           }
/* 140:    */         }
/* 141:    */         catch (Exception x)
/* 142:    */         {
/* 143:325 */           fXalanProperties = null;
/* 144:326 */           fLastModified = -1L;
/* 145:    */         }
/* 146:    */         finally
/* 147:    */         {
/* 148:333 */           if (fis != null) {
/* 149:    */             try
/* 150:    */             {
/* 151:335 */               fis.close();
/* 152:    */             }
/* 153:    */             catch (IOException exc) {}
/* 154:    */           }
/* 155:    */         }
/* 156:    */       }
/* 157:342 */       if (fXalanProperties != null) {
/* 158:343 */         factoryClassName = fXalanProperties.getProperty(factoryId);
/* 159:    */       }
/* 160:    */     }
/* 161:    */     else
/* 162:    */     {
/* 163:346 */       FileInputStream fis = null;
/* 164:    */       try
/* 165:    */       {
/* 166:348 */         fis = ss.getFileInputStream(new File(propertiesFilename));
/* 167:349 */         Properties props = new Properties();
/* 168:350 */         props.load(fis);
/* 169:351 */         factoryClassName = props.getProperty(factoryId);
/* 170:    */       }
/* 171:    */       catch (Exception x) {}finally
/* 172:    */       {
/* 173:359 */         if (fis != null) {
/* 174:    */           try
/* 175:    */           {
/* 176:361 */             fis.close();
/* 177:    */           }
/* 178:    */           catch (IOException exc) {}
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:368 */     if (factoryClassName != null)
/* 183:    */     {
/* 184:369 */       debugPrintln("found in " + propertiesFilename + ", value=" + factoryClassName);
/* 185:    */       
/* 186:371 */       return factoryClassName;
/* 187:    */     }
/* 188:375 */     return findJarServiceProviderName(factoryId);
/* 189:    */   }
/* 190:    */   
/* 191:    */   private static void debugPrintln(String msg) {}
/* 192:    */   
/* 193:    */   static ClassLoader findClassLoader()
/* 194:    */     throws ObjectFactory.ConfigurationError
/* 195:    */   {
/* 196:396 */     SecuritySupport ss = SecuritySupport.getInstance();
/* 197:    */     
/* 198:    */ 
/* 199:    */ 
/* 200:400 */     ClassLoader context = ss.getContextClassLoader();
/* 201:401 */     ClassLoader system = ss.getSystemClassLoader();
/* 202:    */     
/* 203:403 */     ClassLoader chain = system;
/* 204:    */     for (;;)
/* 205:    */     {
/* 206:405 */       if (context == chain)
/* 207:    */       {
/* 208:414 */         ClassLoader current = ObjectFactory.class.getClassLoader();
/* 209:    */         
/* 210:416 */         chain = system;
/* 211:    */         for (;;)
/* 212:    */         {
/* 213:418 */           if (current == chain) {
/* 214:421 */             return system;
/* 215:    */           }
/* 216:423 */           if (chain == null) {
/* 217:    */             break;
/* 218:    */           }
/* 219:426 */           chain = ss.getParentClassLoader(chain);
/* 220:    */         }
/* 221:431 */         return current;
/* 222:    */       }
/* 223:434 */       if (chain == null) {
/* 224:    */         break;
/* 225:    */       }
/* 226:441 */       chain = ss.getParentClassLoader(chain);
/* 227:    */     }
/* 228:446 */     return context;
/* 229:    */   }
/* 230:    */   
/* 231:    */   static Object newInstance(String className, ClassLoader cl, boolean doFallback)
/* 232:    */     throws ObjectFactory.ConfigurationError
/* 233:    */   {
/* 234:    */     try
/* 235:    */     {
/* 236:458 */       Class providerClass = findProviderClass(className, cl, doFallback);
/* 237:459 */       Object instance = providerClass.newInstance();
/* 238:460 */       debugPrintln("created new instance of " + providerClass + " using ClassLoader: " + cl);
/* 239:    */       
/* 240:462 */       return instance;
/* 241:    */     }
/* 242:    */     catch (ClassNotFoundException x)
/* 243:    */     {
/* 244:464 */       throw new ConfigurationError("Provider " + className + " not found", x);
/* 245:    */     }
/* 246:    */     catch (Exception x)
/* 247:    */     {
/* 248:467 */       throw new ConfigurationError("Provider " + className + " could not be instantiated: " + x, x);
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   static Class findProviderClass(String className, ClassLoader cl, boolean doFallback)
/* 253:    */     throws ClassNotFoundException, ObjectFactory.ConfigurationError
/* 254:    */   {
/* 255:482 */     SecurityManager security = System.getSecurityManager();
/* 256:    */     try
/* 257:    */     {
/* 258:484 */       if (security != null)
/* 259:    */       {
/* 260:485 */         int lastDot = className.lastIndexOf(".");
/* 261:486 */         String packageName = className;
/* 262:487 */         if (lastDot != -1) {
/* 263:487 */           packageName = className.substring(0, lastDot);
/* 264:    */         }
/* 265:488 */         security.checkPackageAccess(packageName);
/* 266:    */       }
/* 267:    */     }
/* 268:    */     catch (SecurityException e)
/* 269:    */     {
/* 270:491 */       throw e;
/* 271:    */     }
/* 272:    */     Class providerClass;
/* 273:495 */     if (cl == null) {
/* 274:505 */       providerClass = Class.forName(className);
/* 275:    */     } else {
/* 276:    */       try
/* 277:    */       {
/* 278:508 */         providerClass = cl.loadClass(className);
/* 279:    */       }
/* 280:    */       catch (ClassNotFoundException x)
/* 281:    */       {
/* 282:510 */         if (doFallback)
/* 283:    */         {
/* 284:512 */           ClassLoader current = ObjectFactory.class.getClassLoader();
/* 285:513 */           if (current == null)
/* 286:    */           {
/* 287:514 */             providerClass = Class.forName(className);
/* 288:    */           }
/* 289:515 */           else if (cl != current)
/* 290:    */           {
/* 291:516 */             cl = current;
/* 292:517 */             providerClass = cl.loadClass(className);
/* 293:    */           }
/* 294:    */           else
/* 295:    */           {
/* 296:519 */             throw x;
/* 297:    */           }
/* 298:    */         }
/* 299:    */         else
/* 300:    */         {
/* 301:522 */           throw x;
/* 302:    */         }
/* 303:    */       }
/* 304:    */     }
/* 305:527 */     return providerClass;
/* 306:    */   }
/* 307:    */   
/* 308:    */   private static String findJarServiceProviderName(String factoryId)
/* 309:    */   {
/* 310:537 */     SecuritySupport ss = SecuritySupport.getInstance();
/* 311:538 */     String serviceId = "META-INF/services/" + factoryId;
/* 312:539 */     InputStream is = null;
/* 313:    */     
/* 314:    */ 
/* 315:542 */     ClassLoader cl = findClassLoader();
/* 316:    */     
/* 317:544 */     is = ss.getResourceAsStream(cl, serviceId);
/* 318:547 */     if (is == null)
/* 319:    */     {
/* 320:548 */       ClassLoader current = ObjectFactory.class.getClassLoader();
/* 321:549 */       if (cl != current)
/* 322:    */       {
/* 323:550 */         cl = current;
/* 324:551 */         is = ss.getResourceAsStream(cl, serviceId);
/* 325:    */       }
/* 326:    */     }
/* 327:555 */     if (is == null) {
/* 328:557 */       return null;
/* 329:    */     }
/* 330:560 */     debugPrintln("found jar resource=" + serviceId + " using ClassLoader: " + cl);
/* 331:    */     BufferedReader rd;
/* 332:    */     try
/* 333:    */     {
/* 334:581 */       rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/* 335:    */     }
/* 336:    */     catch (UnsupportedEncodingException e)
/* 337:    */     {
/* 338:583 */       rd = new BufferedReader(new InputStreamReader(is));
/* 339:    */     }
/* 340:586 */     String factoryClassName = null;
/* 341:    */     try
/* 342:    */     {
/* 343:590 */       factoryClassName = rd.readLine();
/* 344:    */     }
/* 345:    */     catch (IOException x)
/* 346:    */     {
/* 347:593 */       return null;
/* 348:    */     }
/* 349:    */     finally
/* 350:    */     {
/* 351:    */       try
/* 352:    */       {
/* 353:598 */         rd.close();
/* 354:    */       }
/* 355:    */       catch (IOException exc) {}
/* 356:    */     }
/* 357:604 */     if ((factoryClassName != null) && (!"".equals(factoryClassName)))
/* 358:    */     {
/* 359:606 */       debugPrintln("found in resource, value=" + factoryClassName);
/* 360:    */       
/* 361:    */ 
/* 362:    */ 
/* 363:    */ 
/* 364:    */ 
/* 365:    */ 
/* 366:613 */       return factoryClassName;
/* 367:    */     }
/* 368:617 */     return null;
/* 369:    */   }
/* 370:    */   
/* 371:    */   static class ConfigurationError
/* 372:    */     extends Error
/* 373:    */   {
/* 374:    */     static final long serialVersionUID = -2293620736651286953L;
/* 375:    */     private Exception exception;
/* 376:    */     
/* 377:    */     ConfigurationError(String msg, Exception x)
/* 378:    */     {
/* 379:646 */       super();
/* 380:647 */       this.exception = x;
/* 381:    */     }
/* 382:    */     
/* 383:    */     Exception getException()
/* 384:    */     {
/* 385:656 */       return this.exception;
/* 386:    */     }
/* 387:    */   }
/* 388:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.runtime.ObjectFactory
 * JD-Core Version:    0.7.0.1
 */