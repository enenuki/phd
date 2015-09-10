/*   1:    */ package org.cyberneko.html;
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
/*  14:    */   private static final String DEFAULT_PROPERTIES_FILENAME = "xerces.properties";
/*  15:    */   private static final boolean DEBUG = false;
/*  16:    */   private static final int DEFAULT_LINE_LENGTH = 80;
/*  17: 63 */   private static Properties fXercesProperties = null;
/*  18: 70 */   private static long fLastModified = -1L;
/*  19:    */   
/*  20:    */   static Object createObject(String factoryId, String fallbackClassName)
/*  21:    */     throws ObjectFactory.ConfigurationError
/*  22:    */   {
/*  23: 96 */     return createObject(factoryId, null, fallbackClassName);
/*  24:    */   }
/*  25:    */   
/*  26:    */   static Object createObject(String factoryId, String propertiesFilename, String fallbackClassName)
/*  27:    */     throws ObjectFactory.ConfigurationError
/*  28:    */   {
/*  29:128 */     SecuritySupport ss = SecuritySupport.getInstance();
/*  30:129 */     ClassLoader cl = findClassLoader();
/*  31:    */     try
/*  32:    */     {
/*  33:133 */       String systemProp = ss.getSystemProperty(factoryId);
/*  34:134 */       if (systemProp != null) {
/*  35:136 */         return newInstance(systemProp, cl, true);
/*  36:    */       }
/*  37:    */     }
/*  38:    */     catch (SecurityException se) {}
/*  39:143 */     String factoryClassName = null;
/*  40:145 */     if (propertiesFilename == null)
/*  41:    */     {
/*  42:146 */       File propertiesFile = null;
/*  43:147 */       boolean propertiesFileExists = false;
/*  44:    */       try
/*  45:    */       {
/*  46:149 */         String javah = ss.getSystemProperty("java.home");
/*  47:150 */         propertiesFilename = javah + File.separator + "lib" + File.separator + "xerces.properties";
/*  48:    */         
/*  49:152 */         propertiesFile = new File(propertiesFilename);
/*  50:153 */         propertiesFileExists = ss.getFileExists(propertiesFile);
/*  51:    */       }
/*  52:    */       catch (SecurityException e)
/*  53:    */       {
/*  54:156 */         fLastModified = -1L;
/*  55:157 */         fXercesProperties = null;
/*  56:    */       }
/*  57:160 */       synchronized (ObjectFactory.class)
/*  58:    */       {
/*  59:161 */         boolean loadProperties = false;
/*  60:    */         try
/*  61:    */         {
/*  62:164 */           if (fLastModified >= 0L)
/*  63:    */           {
/*  64:165 */             if ((propertiesFileExists) && (fLastModified < (ObjectFactory.fLastModified = ss.getLastModified(propertiesFile))))
/*  65:    */             {
/*  66:167 */               loadProperties = true;
/*  67:    */             }
/*  68:170 */             else if (!propertiesFileExists)
/*  69:    */             {
/*  70:171 */               fLastModified = -1L;
/*  71:172 */               fXercesProperties = null;
/*  72:    */             }
/*  73:    */           }
/*  74:177 */           else if (propertiesFileExists)
/*  75:    */           {
/*  76:178 */             loadProperties = true;
/*  77:179 */             fLastModified = ss.getLastModified(propertiesFile);
/*  78:    */           }
/*  79:182 */           if (loadProperties)
/*  80:    */           {
/*  81:184 */             fXercesProperties = new Properties();
/*  82:185 */             FileInputStream fis = ss.getFileInputStream(propertiesFile);
/*  83:186 */             fXercesProperties.load(fis);
/*  84:187 */             fis.close();
/*  85:    */           }
/*  86:    */         }
/*  87:    */         catch (Exception x)
/*  88:    */         {
/*  89:190 */           fXercesProperties = null;
/*  90:191 */           fLastModified = -1L;
/*  91:    */         }
/*  92:    */       }
/*  93:197 */       if (fXercesProperties != null) {
/*  94:198 */         factoryClassName = fXercesProperties.getProperty(factoryId);
/*  95:    */       }
/*  96:    */     }
/*  97:    */     else
/*  98:    */     {
/*  99:    */       try
/* 100:    */       {
/* 101:202 */         FileInputStream fis = ss.getFileInputStream(new File(propertiesFilename));
/* 102:203 */         Properties props = new Properties();
/* 103:204 */         props.load(fis);
/* 104:205 */         fis.close();
/* 105:206 */         factoryClassName = props.getProperty(factoryId);
/* 106:    */       }
/* 107:    */       catch (Exception x) {}
/* 108:    */     }
/* 109:213 */     if (factoryClassName != null) {
/* 110:215 */       return newInstance(factoryClassName, cl, true);
/* 111:    */     }
/* 112:219 */     Object provider = findJarServiceProvider(factoryId);
/* 113:220 */     if (provider != null) {
/* 114:221 */       return provider;
/* 115:    */     }
/* 116:224 */     if (fallbackClassName == null) {
/* 117:225 */       throw new ConfigurationError("Provider for " + factoryId + " cannot be found", null);
/* 118:    */     }
/* 119:230 */     return newInstance(fallbackClassName, cl, true);
/* 120:    */   }
/* 121:    */   
/* 122:    */   private static void debugPrintln(String msg) {}
/* 123:    */   
/* 124:    */   static ClassLoader findClassLoader()
/* 125:    */     throws ObjectFactory.ConfigurationError
/* 126:    */   {
/* 127:251 */     SecuritySupport ss = SecuritySupport.getInstance();
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:255 */     ClassLoader context = ss.getContextClassLoader();
/* 132:256 */     ClassLoader system = ss.getSystemClassLoader();
/* 133:    */     
/* 134:258 */     ClassLoader chain = system;
/* 135:    */     for (;;)
/* 136:    */     {
/* 137:260 */       if (context == chain)
/* 138:    */       {
/* 139:269 */         ClassLoader current = ObjectFactory.class.getClassLoader();
/* 140:    */         
/* 141:271 */         chain = system;
/* 142:    */         for (;;)
/* 143:    */         {
/* 144:273 */           if (current == chain) {
/* 145:276 */             return system;
/* 146:    */           }
/* 147:278 */           if (chain == null) {
/* 148:    */             break;
/* 149:    */           }
/* 150:281 */           chain = ss.getParentClassLoader(chain);
/* 151:    */         }
/* 152:286 */         return current;
/* 153:    */       }
/* 154:289 */       if (chain == null) {
/* 155:    */         break;
/* 156:    */       }
/* 157:296 */       chain = ss.getParentClassLoader(chain);
/* 158:    */     }
/* 159:301 */     return context;
/* 160:    */   }
/* 161:    */   
/* 162:    */   static Object newInstance(String className, ClassLoader cl, boolean doFallback)
/* 163:    */     throws ObjectFactory.ConfigurationError
/* 164:    */   {
/* 165:    */     try
/* 166:    */     {
/* 167:313 */       Class providerClass = findProviderClass(className, cl, doFallback);
/* 168:314 */       return providerClass.newInstance();
/* 169:    */     }
/* 170:    */     catch (ClassNotFoundException x)
/* 171:    */     {
/* 172:319 */       throw new ConfigurationError("Provider " + className + " not found", x);
/* 173:    */     }
/* 174:    */     catch (Exception x)
/* 175:    */     {
/* 176:322 */       throw new ConfigurationError("Provider " + className + " could not be instantiated: " + x, x);
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   static Class findProviderClass(String className, ClassLoader cl, boolean doFallback)
/* 181:    */     throws ClassNotFoundException, ObjectFactory.ConfigurationError
/* 182:    */   {
/* 183:337 */     SecurityManager security = System.getSecurityManager();
/* 184:    */     try
/* 185:    */     {
/* 186:339 */       if (security != null)
/* 187:    */       {
/* 188:340 */         int lastDot = className.lastIndexOf(".");
/* 189:341 */         String packageName = className;
/* 190:342 */         if (lastDot != -1) {
/* 191:342 */           packageName = className.substring(0, lastDot);
/* 192:    */         }
/* 193:343 */         security.checkPackageAccess(packageName);
/* 194:    */       }
/* 195:    */     }
/* 196:    */     catch (SecurityException e)
/* 197:    */     {
/* 198:346 */       throw e;
/* 199:    */     }
/* 200:    */     Class providerClass;
/* 201:349 */     if (cl == null) {
/* 202:359 */       providerClass = Class.forName(className);
/* 203:    */     } else {
/* 204:    */       try
/* 205:    */       {
/* 206:362 */         providerClass = cl.loadClass(className);
/* 207:    */       }
/* 208:    */       catch (ClassNotFoundException x)
/* 209:    */       {
/* 210:    */         Class providerClass;
/* 211:    */         Class providerClass;
/* 212:364 */         if (doFallback)
/* 213:    */         {
/* 214:366 */           ClassLoader current = ObjectFactory.class.getClassLoader();
/* 215:367 */           if (current == null)
/* 216:    */           {
/* 217:368 */             providerClass = Class.forName(className);
/* 218:    */           }
/* 219:    */           else
/* 220:    */           {
/* 221:    */             Class providerClass;
/* 222:369 */             if (cl != current)
/* 223:    */             {
/* 224:370 */               cl = current;
/* 225:371 */               providerClass = cl.loadClass(className);
/* 226:    */             }
/* 227:    */             else
/* 228:    */             {
/* 229:373 */               throw x;
/* 230:    */             }
/* 231:    */           }
/* 232:    */         }
/* 233:    */         else
/* 234:    */         {
/* 235:376 */           throw x;
/* 236:    */         }
/* 237:    */       }
/* 238:    */     }
/* 239:    */     Class providerClass;
/* 240:381 */     return providerClass;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private static Object findJarServiceProvider(String factoryId)
/* 244:    */     throws ObjectFactory.ConfigurationError
/* 245:    */   {
/* 246:392 */     SecuritySupport ss = SecuritySupport.getInstance();
/* 247:393 */     String serviceId = "META-INF/services/" + factoryId;
/* 248:394 */     InputStream is = null;
/* 249:    */     
/* 250:    */ 
/* 251:397 */     ClassLoader cl = findClassLoader();
/* 252:    */     
/* 253:399 */     is = ss.getResourceAsStream(cl, serviceId);
/* 254:402 */     if (is == null)
/* 255:    */     {
/* 256:403 */       ClassLoader current = ObjectFactory.class.getClassLoader();
/* 257:404 */       if (cl != current)
/* 258:    */       {
/* 259:405 */         cl = current;
/* 260:406 */         is = ss.getResourceAsStream(cl, serviceId);
/* 261:    */       }
/* 262:    */     }
/* 263:410 */     if (is == null) {
/* 264:412 */       return null;
/* 265:    */     }
/* 266:    */     BufferedReader rd;
/* 267:    */     try
/* 268:    */     {
/* 269:436 */       rd = new BufferedReader(new InputStreamReader(is, "UTF-8"), 80);
/* 270:    */     }
/* 271:    */     catch (UnsupportedEncodingException e)
/* 272:    */     {
/* 273:438 */       rd = new BufferedReader(new InputStreamReader(is), 80);
/* 274:    */     }
/* 275:441 */     String factoryClassName = null;
/* 276:    */     try
/* 277:    */     {
/* 278:445 */       factoryClassName = rd.readLine();
/* 279:446 */       rd.close();
/* 280:    */     }
/* 281:    */     catch (IOException x)
/* 282:    */     {
/* 283:449 */       return null;
/* 284:    */     }
/* 285:452 */     if ((factoryClassName != null) && (!"".equals(factoryClassName))) {
/* 286:461 */       return newInstance(factoryClassName, cl, false);
/* 287:    */     }
/* 288:465 */     return null;
/* 289:    */   }
/* 290:    */   
/* 291:    */   static class ConfigurationError
/* 292:    */     extends Error
/* 293:    */   {
/* 294:    */     private Exception exception;
/* 295:    */     
/* 296:    */     ConfigurationError(String msg, Exception x)
/* 297:    */     {
/* 298:494 */       super();
/* 299:495 */       this.exception = x;
/* 300:    */     }
/* 301:    */     
/* 302:    */     Exception getException()
/* 303:    */     {
/* 304:504 */       return this.exception;
/* 305:    */     }
/* 306:    */   }
/* 307:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.cyberneko.html.ObjectFactory
 * JD-Core Version:    0.7.0.1
 */