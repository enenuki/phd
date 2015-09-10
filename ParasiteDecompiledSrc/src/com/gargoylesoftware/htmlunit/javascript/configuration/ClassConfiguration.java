/*   1:    */ package com.gargoylesoftware.htmlunit.javascript.configuration;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.javascript.SimpleScriptable;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ 
/*  12:    */ public final class ClassConfiguration
/*  13:    */ {
/*  14:    */   private static final String GETTER_PREFIX = "jsxGet_";
/*  15:    */   private static final String SETTER_PREFIX = "jsxSet_";
/*  16:    */   private static final String FUNCTION_PREFIX = "jsxFunction_";
/*  17: 39 */   private Map<String, PropertyInfo> propertyMap_ = new HashMap();
/*  18: 40 */   private Map<String, FunctionInfo> functionMap_ = new HashMap();
/*  19: 41 */   private List<String> constants_ = new ArrayList();
/*  20:    */   private String extendedClassName_;
/*  21:    */   private final Class<? extends SimpleScriptable> hostClass_;
/*  22:    */   private final Method jsConstructor_;
/*  23:    */   private final String htmlClassName_;
/*  24:    */   private final boolean jsObject_;
/*  25:    */   
/*  26:    */   public ClassConfiguration(String hostClassName, String jsConstructor, String extendedClassName, String htmlClassName, boolean jsObject)
/*  27:    */     throws ClassNotFoundException
/*  28:    */   {
/*  29: 65 */     this.extendedClassName_ = extendedClassName;
/*  30: 66 */     this.hostClass_ = Class.forName(hostClassName);
/*  31: 67 */     if ((jsConstructor != null) && (jsConstructor.length() != 0))
/*  32:    */     {
/*  33: 68 */       Method foundCtor = null;
/*  34: 69 */       for (Method method : this.hostClass_.getMethods()) {
/*  35: 70 */         if (method.getName().equals(jsConstructor))
/*  36:    */         {
/*  37: 71 */           foundCtor = method;
/*  38: 72 */           break;
/*  39:    */         }
/*  40:    */       }
/*  41: 75 */       if (foundCtor == null) {
/*  42: 76 */         throw new IllegalStateException("Constructor method \"" + jsConstructor + "\" in class \"" + hostClassName + " is not found.");
/*  43:    */       }
/*  44: 79 */       this.jsConstructor_ = foundCtor;
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 82 */       this.jsConstructor_ = null;
/*  49:    */     }
/*  50: 84 */     this.jsObject_ = jsObject;
/*  51: 85 */     if ((htmlClassName != null) && (htmlClassName.length() != 0)) {
/*  52: 86 */       this.htmlClassName_ = htmlClassName;
/*  53:    */     } else {
/*  54: 89 */       this.htmlClassName_ = null;
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void addProperty(String name, boolean readable, boolean writable)
/*  59:    */   {
/*  60:100 */     PropertyInfo info = new PropertyInfo();
/*  61:101 */     info.setReadable(readable);
/*  62:102 */     info.setWritable(writable);
/*  63:    */     try
/*  64:    */     {
/*  65:104 */       if (readable) {
/*  66:105 */         info.setReadMethod(this.hostClass_.getMethod("jsxGet_" + name, (Class[])null));
/*  67:    */       }
/*  68:    */     }
/*  69:    */     catch (NoSuchMethodException e)
/*  70:    */     {
/*  71:109 */       throw new IllegalStateException("Method 'jsxGet_" + name + "' was not found for " + name + " property in " + this.hostClass_.getName());
/*  72:    */     }
/*  73:114 */     if (writable)
/*  74:    */     {
/*  75:115 */       String setMethodName = "jsxSet_" + name;
/*  76:116 */       for (Method method : this.hostClass_.getMethods()) {
/*  77:117 */         if ((method.getName().equals(setMethodName)) && (method.getParameterTypes().length == 1))
/*  78:    */         {
/*  79:118 */           info.setWriteMethod(method);
/*  80:119 */           break;
/*  81:    */         }
/*  82:    */       }
/*  83:122 */       if (info.getWriteMethod() == null) {
/*  84:123 */         throw new IllegalStateException("Method 'jsxSet_" + name + "' was not found for " + name + " property in " + this.hostClass_.getName());
/*  85:    */       }
/*  86:    */     }
/*  87:127 */     this.propertyMap_.put(name, info);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void addConstant(String name)
/*  91:    */   {
/*  92:135 */     this.constants_.add(name);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Set<String> propertyKeys()
/*  96:    */   {
/*  97:143 */     return this.propertyMap_.keySet();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Set<String> functionKeys()
/* 101:    */   {
/* 102:151 */     return this.functionMap_.keySet();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<String> constants()
/* 106:    */   {
/* 107:159 */     return this.constants_;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void addFunction(String name)
/* 111:    */   {
/* 112:167 */     FunctionInfo info = new FunctionInfo(null);
/* 113:168 */     String setMethodName = "jsxFunction_" + name;
/* 114:169 */     for (Method method : this.hostClass_.getMethods()) {
/* 115:170 */       if (method.getName().equals(setMethodName))
/* 116:    */       {
/* 117:171 */         info.setFunctionMethod(method);
/* 118:172 */         break;
/* 119:    */       }
/* 120:    */     }
/* 121:175 */     if (info.getFunctionMethod() == null) {
/* 122:176 */       throw new IllegalStateException("Method 'jsxFunction_" + name + "' was not found for " + name + " function in " + this.hostClass_.getName());
/* 123:    */     }
/* 124:179 */     this.functionMap_.put(name, info);
/* 125:    */   }
/* 126:    */   
/* 127:    */   void addAllDefinitions(ClassConfiguration virtualClassConfig)
/* 128:    */   {
/* 129:187 */     if (!virtualClassConfig.getHostClass().isAssignableFrom(getHostClass())) {
/* 130:188 */       throw new RuntimeException("Can't configure " + getHostClass() + " with info from " + virtualClassConfig.getHostClass());
/* 131:    */     }
/* 132:191 */     this.propertyMap_.putAll(virtualClassConfig.propertyMap_);
/* 133:192 */     this.functionMap_.putAll(virtualClassConfig.functionMap_);
/* 134:193 */     this.constants_.addAll(virtualClassConfig.constants_);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setBrowser(String propertyName, String browserName)
/* 138:    */     throws IllegalStateException
/* 139:    */   {
/* 140:204 */     PropertyInfo property = getPropertyInfo(propertyName);
/* 141:205 */     if (property == null) {
/* 142:206 */       throw new IllegalStateException("Property does not exist to set browser");
/* 143:    */     }
/* 144:208 */     property.setBrowser(new BrowserInfo(browserName, null));
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getExtendedClassName()
/* 148:    */   {
/* 149:215 */     return this.extendedClassName_;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setExtendedClassName(String extendedClass)
/* 153:    */   {
/* 154:222 */     this.extendedClassName_ = extendedClass;
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected PropertyInfo getPropertyInfo(String propertyName)
/* 158:    */   {
/* 159:231 */     return (PropertyInfo)this.propertyMap_.get(propertyName);
/* 160:    */   }
/* 161:    */   
/* 162:    */   private FunctionInfo getFunctionInfo(String functionName)
/* 163:    */   {
/* 164:235 */     return (FunctionInfo)this.functionMap_.get(functionName);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean equals(Object obj)
/* 168:    */   {
/* 169:247 */     if (!(obj instanceof ClassConfiguration)) {
/* 170:248 */       return false;
/* 171:    */     }
/* 172:250 */     ClassConfiguration config = (ClassConfiguration)obj;
/* 173:251 */     if (this.propertyMap_.size() != config.propertyMap_.size()) {
/* 174:252 */       return false;
/* 175:    */     }
/* 176:254 */     if (this.functionMap_.size() != config.functionMap_.size()) {
/* 177:255 */       return false;
/* 178:    */     }
/* 179:257 */     Set<String> keys = config.propertyMap_.keySet();
/* 180:258 */     for (String key : keys) {
/* 181:259 */       if (!((PropertyInfo)config.propertyMap_.get(key)).valueEquals(this.propertyMap_.get(key))) {
/* 182:260 */         return false;
/* 183:    */       }
/* 184:    */     }
/* 185:264 */     for (String key : config.functionMap_.keySet()) {
/* 186:265 */       if (!((FunctionInfo)config.functionMap_.get(key)).valueEquals(this.functionMap_.get(key))) {
/* 187:266 */         return false;
/* 188:    */       }
/* 189:    */     }
/* 190:269 */     return true;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public int hashCode()
/* 194:    */   {
/* 195:278 */     return this.hostClass_.getName().hashCode();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Method getPropertyReadMethod(String propertyName)
/* 199:    */   {
/* 200:288 */     PropertyInfo info = getPropertyInfo(propertyName);
/* 201:289 */     if (info == null) {
/* 202:290 */       return null;
/* 203:    */     }
/* 204:292 */     return info.getReadMethod();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Method getPropertyWriteMethod(String propertyName)
/* 208:    */   {
/* 209:302 */     PropertyInfo info = getPropertyInfo(propertyName);
/* 210:303 */     if (info == null) {
/* 211:304 */       return null;
/* 212:    */     }
/* 213:306 */     return info.getWriteMethod();
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Method getFunctionMethod(String functionName)
/* 217:    */   {
/* 218:316 */     FunctionInfo info = getFunctionInfo(functionName);
/* 219:317 */     if (info == null) {
/* 220:318 */       return null;
/* 221:    */     }
/* 222:320 */     return info.getFunctionMethod();
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Class<? extends SimpleScriptable> getHostClass()
/* 226:    */   {
/* 227:328 */     return this.hostClass_;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public Method getJsConstructor()
/* 231:    */   {
/* 232:336 */     return this.jsConstructor_;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String getHtmlClassname()
/* 236:    */   {
/* 237:343 */     return this.htmlClassName_;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean isJsObject()
/* 241:    */   {
/* 242:350 */     return this.jsObject_;
/* 243:    */   }
/* 244:    */   
/* 245:    */   protected static class PropertyInfo
/* 246:    */   {
/* 247:358 */     private boolean readable_ = false;
/* 248:359 */     private boolean writable_ = false;
/* 249:360 */     private boolean hasBrowsers_ = false;
/* 250:    */     private Map<String, ClassConfiguration.BrowserInfo> browserMap_;
/* 251:    */     private Method readMethod_;
/* 252:    */     private Method writeMethod_;
/* 253:    */     
/* 254:    */     public Method getReadMethod()
/* 255:    */     {
/* 256:369 */       return this.readMethod_;
/* 257:    */     }
/* 258:    */     
/* 259:    */     public void setReadMethod(Method readMethod)
/* 260:    */     {
/* 261:376 */       this.readMethod_ = readMethod;
/* 262:    */     }
/* 263:    */     
/* 264:    */     public Method getWriteMethod()
/* 265:    */     {
/* 266:383 */       return this.writeMethod_;
/* 267:    */     }
/* 268:    */     
/* 269:    */     public void setWriteMethod(Method writeMethod)
/* 270:    */     {
/* 271:390 */       this.writeMethod_ = writeMethod;
/* 272:    */     }
/* 273:    */     
/* 274:    */     private void setBrowser(ClassConfiguration.BrowserInfo browserInfo)
/* 275:    */     {
/* 276:394 */       if (this.browserMap_ == null)
/* 277:    */       {
/* 278:395 */         this.hasBrowsers_ = true;
/* 279:396 */         this.browserMap_ = new HashMap();
/* 280:    */       }
/* 281:399 */       this.browserMap_.put(browserInfo.getBrowserName(), browserInfo);
/* 282:    */     }
/* 283:    */     
/* 284:    */     private boolean valueEquals(Object obj)
/* 285:    */     {
/* 286:410 */       if (!(obj instanceof PropertyInfo)) {
/* 287:411 */         return false;
/* 288:    */       }
/* 289:413 */       PropertyInfo info = (PropertyInfo)obj;
/* 290:414 */       if (this.hasBrowsers_ != info.hasBrowsers_) {
/* 291:415 */         return false;
/* 292:    */       }
/* 293:417 */       if (this.hasBrowsers_)
/* 294:    */       {
/* 295:418 */         if (this.browserMap_.size() != info.browserMap_.size()) {
/* 296:419 */           return false;
/* 297:    */         }
/* 298:421 */         for (Map.Entry<String, ClassConfiguration.BrowserInfo> entry : this.browserMap_.entrySet()) {
/* 299:422 */           if (!((ClassConfiguration.BrowserInfo)entry.getValue()).valueEquals(info.browserMap_.get(entry.getKey()))) {
/* 300:423 */             return false;
/* 301:    */           }
/* 302:    */         }
/* 303:    */       }
/* 304:428 */       return (this.readable_ == info.readable_) && (this.writable_ == info.writable_);
/* 305:    */     }
/* 306:    */     
/* 307:    */     private void setReadable(boolean readable)
/* 308:    */     {
/* 309:436 */       this.readable_ = readable;
/* 310:    */     }
/* 311:    */     
/* 312:    */     private void setWritable(boolean writable)
/* 313:    */     {
/* 314:443 */       this.writable_ = writable;
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   private static class FunctionInfo
/* 319:    */   {
/* 320:448 */     private boolean hasBrowsers_ = false;
/* 321:    */     private Map<String, ClassConfiguration.BrowserInfo> browserMap_;
/* 322:    */     private Method functionMethod_;
/* 323:    */     
/* 324:    */     private boolean valueEquals(Object obj)
/* 325:    */     {
/* 326:460 */       if (!(obj instanceof FunctionInfo)) {
/* 327:461 */         return false;
/* 328:    */       }
/* 329:463 */       FunctionInfo info = (FunctionInfo)obj;
/* 330:464 */       if (this.hasBrowsers_ != info.hasBrowsers_) {
/* 331:465 */         return false;
/* 332:    */       }
/* 333:467 */       if (this.hasBrowsers_)
/* 334:    */       {
/* 335:468 */         if (this.browserMap_.size() != info.browserMap_.size()) {
/* 336:469 */           return false;
/* 337:    */         }
/* 338:471 */         for (Map.Entry<String, ClassConfiguration.BrowserInfo> entry : this.browserMap_.entrySet()) {
/* 339:472 */           if (((ClassConfiguration.BrowserInfo)entry.getValue()).valueEquals(info.browserMap_.get(entry.getKey()))) {
/* 340:473 */             return false;
/* 341:    */           }
/* 342:    */         }
/* 343:    */       }
/* 344:478 */       return true;
/* 345:    */     }
/* 346:    */     
/* 347:    */     public Method getFunctionMethod()
/* 348:    */     {
/* 349:485 */       return this.functionMethod_;
/* 350:    */     }
/* 351:    */     
/* 352:    */     public void setFunctionMethod(Method functionMethod)
/* 353:    */     {
/* 354:492 */       this.functionMethod_ = functionMethod;
/* 355:    */     }
/* 356:    */   }
/* 357:    */   
/* 358:    */   private static final class BrowserInfo
/* 359:    */   {
/* 360:    */     private String browserName_;
/* 361:    */     private String minVersion_;
/* 362:    */     private String maxVersion_;
/* 363:    */     private String lessThanVersion_;
/* 364:    */     
/* 365:    */     private boolean valueEquals(Object obj)
/* 366:    */     {
/* 367:510 */       if (!(obj instanceof BrowserInfo)) {
/* 368:511 */         return false;
/* 369:    */       }
/* 370:513 */       BrowserInfo info = (BrowserInfo)obj;
/* 371:514 */       if ((this.minVersion_ != null) && (!this.minVersion_.equals(info.minVersion_))) {
/* 372:515 */         return false;
/* 373:    */       }
/* 374:517 */       if ((this.maxVersion_ != null) && (!this.maxVersion_.equals(info.maxVersion_))) {
/* 375:518 */         return false;
/* 376:    */       }
/* 377:520 */       if ((this.lessThanVersion_ != null) && (!this.lessThanVersion_.equals(info.lessThanVersion_))) {
/* 378:521 */         return false;
/* 379:    */       }
/* 380:523 */       return this.browserName_ == info.browserName_;
/* 381:    */     }
/* 382:    */     
/* 383:    */     private BrowserInfo(String browserName)
/* 384:    */     {
/* 385:530 */       this.browserName_ = browserName;
/* 386:    */     }
/* 387:    */     
/* 388:    */     private String getBrowserName()
/* 389:    */     {
/* 390:537 */       return this.browserName_;
/* 391:    */     }
/* 392:    */   }
/* 393:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.javascript.configuration.ClassConfiguration
 * JD-Core Version:    0.7.0.1
 */