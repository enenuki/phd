/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.beans.BeanInfo;
/*   4:    */ import java.beans.IntrospectionException;
/*   5:    */ import java.beans.Introspector;
/*   6:    */ import java.beans.PropertyDescriptor;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.lang.reflect.Constructor;
/*   9:    */ import java.lang.reflect.InvocationTargetException;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import java.util.AbstractMap;
/*  12:    */ import java.util.AbstractSet;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Collection;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.Set;
/*  18:    */ import org.apache.commons.collections.keyvalue.AbstractMapEntry;
/*  19:    */ import org.apache.commons.collections.list.UnmodifiableList;
/*  20:    */ import org.apache.commons.collections.set.UnmodifiableSet;
/*  21:    */ 
/*  22:    */ /**
/*  23:    */  * @deprecated
/*  24:    */  */
/*  25:    */ public class BeanMap
/*  26:    */   extends AbstractMap
/*  27:    */   implements Cloneable
/*  28:    */ {
/*  29:    */   private transient Object bean;
/*  30: 59 */   private transient HashMap readMethods = new HashMap();
/*  31: 60 */   private transient HashMap writeMethods = new HashMap();
/*  32: 61 */   private transient HashMap types = new HashMap();
/*  33: 66 */   public static final Object[] NULL_ARGUMENTS = new Object[0];
/*  34: 72 */   public static HashMap defaultTransformers = new HashMap();
/*  35:    */   
/*  36:    */   static
/*  37:    */   {
/*  38: 75 */     defaultTransformers.put(Boolean.TYPE, new Transformer()
/*  39:    */     {
/*  40:    */       public Object transform(Object input)
/*  41:    */       {
/*  42: 79 */         return Boolean.valueOf(input.toString());
/*  43:    */       }
/*  44: 82 */     });
/*  45: 83 */     defaultTransformers.put(Character.TYPE, new Transformer()
/*  46:    */     {
/*  47:    */       public Object transform(Object input)
/*  48:    */       {
/*  49: 87 */         return new Character(input.toString().charAt(0));
/*  50:    */       }
/*  51: 90 */     });
/*  52: 91 */     defaultTransformers.put(Byte.TYPE, new Transformer()
/*  53:    */     {
/*  54:    */       public Object transform(Object input)
/*  55:    */       {
/*  56: 95 */         return Byte.valueOf(input.toString());
/*  57:    */       }
/*  58: 98 */     });
/*  59: 99 */     defaultTransformers.put(Short.TYPE, new Transformer()
/*  60:    */     {
/*  61:    */       public Object transform(Object input)
/*  62:    */       {
/*  63:103 */         return Short.valueOf(input.toString());
/*  64:    */       }
/*  65:106 */     });
/*  66:107 */     defaultTransformers.put(Integer.TYPE, new Transformer()
/*  67:    */     {
/*  68:    */       public Object transform(Object input)
/*  69:    */       {
/*  70:111 */         return Integer.valueOf(input.toString());
/*  71:    */       }
/*  72:114 */     });
/*  73:115 */     defaultTransformers.put(Long.TYPE, new Transformer()
/*  74:    */     {
/*  75:    */       public Object transform(Object input)
/*  76:    */       {
/*  77:119 */         return Long.valueOf(input.toString());
/*  78:    */       }
/*  79:122 */     });
/*  80:123 */     defaultTransformers.put(Float.TYPE, new Transformer()
/*  81:    */     {
/*  82:    */       public Object transform(Object input)
/*  83:    */       {
/*  84:127 */         return Float.valueOf(input.toString());
/*  85:    */       }
/*  86:130 */     });
/*  87:131 */     defaultTransformers.put(Double.TYPE, new Transformer()
/*  88:    */     {
/*  89:    */       public Object transform(Object input)
/*  90:    */       {
/*  91:135 */         return Double.valueOf(input.toString());
/*  92:    */       }
/*  93:    */     });
/*  94:    */   }
/*  95:    */   
/*  96:    */   public BeanMap(Object bean)
/*  97:    */   {
/*  98:159 */     this.bean = bean;
/*  99:160 */     initialise();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String toString()
/* 103:    */   {
/* 104:167 */     return "BeanMap<" + String.valueOf(this.bean) + ">";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Object clone()
/* 108:    */     throws CloneNotSupportedException
/* 109:    */   {
/* 110:194 */     BeanMap newMap = (BeanMap)super.clone();
/* 111:196 */     if (this.bean == null) {
/* 112:199 */       return newMap;
/* 113:    */     }
/* 114:202 */     Object newBean = null;
/* 115:203 */     Class beanClass = null;
/* 116:    */     try
/* 117:    */     {
/* 118:205 */       beanClass = this.bean.getClass();
/* 119:206 */       newBean = beanClass.newInstance();
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:209 */       throw new CloneNotSupportedException("Unable to instantiate the underlying bean \"" + beanClass.getName() + "\": " + e);
/* 124:    */     }
/* 125:    */     try
/* 126:    */     {
/* 127:215 */       newMap.setBean(newBean);
/* 128:    */     }
/* 129:    */     catch (Exception exception)
/* 130:    */     {
/* 131:217 */       throw new CloneNotSupportedException("Unable to set bean in the cloned bean map: " + exception);
/* 132:    */     }
/* 133:    */     try
/* 134:    */     {
/* 135:226 */       Iterator readableKeys = this.readMethods.keySet().iterator();
/* 136:227 */       while (readableKeys.hasNext())
/* 137:    */       {
/* 138:228 */         Object key = readableKeys.next();
/* 139:229 */         if (getWriteMethod(key) != null) {
/* 140:230 */           newMap.put(key, get(key));
/* 141:    */         }
/* 142:    */       }
/* 143:    */     }
/* 144:    */     catch (Exception exception)
/* 145:    */     {
/* 146:234 */       throw new CloneNotSupportedException("Unable to copy bean values to cloned bean map: " + exception);
/* 147:    */     }
/* 148:239 */     return newMap;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void putAllWriteable(BeanMap map)
/* 152:    */   {
/* 153:249 */     Iterator readableKeys = map.readMethods.keySet().iterator();
/* 154:250 */     while (readableKeys.hasNext())
/* 155:    */     {
/* 156:251 */       Object key = readableKeys.next();
/* 157:252 */       if (getWriteMethod(key) != null) {
/* 158:253 */         put(key, map.get(key));
/* 159:    */       }
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void clear()
/* 164:    */   {
/* 165:268 */     if (this.bean == null) {
/* 166:268 */       return;
/* 167:    */     }
/* 168:270 */     Class beanClass = null;
/* 169:    */     try
/* 170:    */     {
/* 171:272 */       beanClass = this.bean.getClass();
/* 172:273 */       this.bean = beanClass.newInstance();
/* 173:    */     }
/* 174:    */     catch (Exception e)
/* 175:    */     {
/* 176:276 */       throw new UnsupportedOperationException("Could not create new instance of class: " + beanClass);
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean containsKey(Object name)
/* 181:    */   {
/* 182:296 */     Method method = getReadMethod(name);
/* 183:297 */     return method != null;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean containsValue(Object value)
/* 187:    */   {
/* 188:310 */     return super.containsValue(value);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Object get(Object name)
/* 192:    */   {
/* 193:329 */     if (this.bean != null)
/* 194:    */     {
/* 195:330 */       Method method = getReadMethod(name);
/* 196:331 */       if (method != null) {
/* 197:    */         try
/* 198:    */         {
/* 199:333 */           return method.invoke(this.bean, NULL_ARGUMENTS);
/* 200:    */         }
/* 201:    */         catch (IllegalAccessException e)
/* 202:    */         {
/* 203:336 */           logWarn(e);
/* 204:    */         }
/* 205:    */         catch (IllegalArgumentException e)
/* 206:    */         {
/* 207:339 */           logWarn(e);
/* 208:    */         }
/* 209:    */         catch (InvocationTargetException e)
/* 210:    */         {
/* 211:342 */           logWarn(e);
/* 212:    */         }
/* 213:    */         catch (NullPointerException e)
/* 214:    */         {
/* 215:345 */           logWarn(e);
/* 216:    */         }
/* 217:    */       }
/* 218:    */     }
/* 219:349 */     return null;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public Object put(Object name, Object value)
/* 223:    */     throws IllegalArgumentException, ClassCastException
/* 224:    */   {
/* 225:364 */     if (this.bean != null)
/* 226:    */     {
/* 227:365 */       Object oldValue = get(name);
/* 228:366 */       Method method = getWriteMethod(name);
/* 229:367 */       if (method == null) {
/* 230:368 */         throw new IllegalArgumentException("The bean of type: " + this.bean.getClass().getName() + " has no property called: " + name);
/* 231:    */       }
/* 232:    */       try
/* 233:    */       {
/* 234:371 */         Object[] arguments = createWriteMethodArguments(method, value);
/* 235:372 */         method.invoke(this.bean, arguments);
/* 236:    */         
/* 237:374 */         Object newValue = get(name);
/* 238:375 */         firePropertyChange(name, oldValue, newValue);
/* 239:    */       }
/* 240:    */       catch (InvocationTargetException e)
/* 241:    */       {
/* 242:378 */         logInfo(e);
/* 243:379 */         throw new IllegalArgumentException(e.getMessage());
/* 244:    */       }
/* 245:    */       catch (IllegalAccessException e)
/* 246:    */       {
/* 247:382 */         logInfo(e);
/* 248:383 */         throw new IllegalArgumentException(e.getMessage());
/* 249:    */       }
/* 250:385 */       return oldValue;
/* 251:    */     }
/* 252:387 */     return null;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public int size()
/* 256:    */   {
/* 257:396 */     return this.readMethods.size();
/* 258:    */   }
/* 259:    */   
/* 260:    */   public Set keySet()
/* 261:    */   {
/* 262:411 */     return UnmodifiableSet.decorate(this.readMethods.keySet());
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Set entrySet()
/* 266:    */   {
/* 267:422 */     UnmodifiableSet.decorate(new AbstractSet()
/* 268:    */     {
/* 269:    */       public Iterator iterator()
/* 270:    */       {
/* 271:424 */         return BeanMap.this.entryIterator();
/* 272:    */       }
/* 273:    */       
/* 274:    */       public int size()
/* 275:    */       {
/* 276:427 */         return BeanMap.this.readMethods.size();
/* 277:    */       }
/* 278:    */     });
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Collection values()
/* 282:    */   {
/* 283:439 */     ArrayList answer = new ArrayList(this.readMethods.size());
/* 284:440 */     for (Iterator iter = valueIterator(); iter.hasNext();) {
/* 285:441 */       answer.add(iter.next());
/* 286:    */     }
/* 287:443 */     return UnmodifiableList.decorate(answer);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public Class getType(String name)
/* 291:    */   {
/* 292:458 */     return (Class)this.types.get(name);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public Iterator keyIterator()
/* 296:    */   {
/* 297:469 */     return this.readMethods.keySet().iterator();
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Iterator valueIterator()
/* 301:    */   {
/* 302:478 */     Iterator iter = keyIterator();
/* 303:479 */     new Iterator()
/* 304:    */     {
/* 305:    */       private final Iterator val$iter;
/* 306:    */       
/* 307:    */       public boolean hasNext()
/* 308:    */       {
/* 309:481 */         return this.val$iter.hasNext();
/* 310:    */       }
/* 311:    */       
/* 312:    */       public Object next()
/* 313:    */       {
/* 314:484 */         Object key = this.val$iter.next();
/* 315:485 */         return BeanMap.this.get(key);
/* 316:    */       }
/* 317:    */       
/* 318:    */       public void remove()
/* 319:    */       {
/* 320:488 */         throw new UnsupportedOperationException("remove() not supported for BeanMap");
/* 321:    */       }
/* 322:    */     };
/* 323:    */   }
/* 324:    */   
/* 325:    */   public Iterator entryIterator()
/* 326:    */   {
/* 327:499 */     Iterator iter = keyIterator();
/* 328:500 */     new Iterator()
/* 329:    */     {
/* 330:    */       private final Iterator val$iter;
/* 331:    */       
/* 332:    */       public boolean hasNext()
/* 333:    */       {
/* 334:502 */         return this.val$iter.hasNext();
/* 335:    */       }
/* 336:    */       
/* 337:    */       public Object next()
/* 338:    */       {
/* 339:505 */         Object key = this.val$iter.next();
/* 340:506 */         Object value = BeanMap.this.get(key);
/* 341:507 */         return new BeanMap.MyMapEntry(BeanMap.this, key, value);
/* 342:    */       }
/* 343:    */       
/* 344:    */       public void remove()
/* 345:    */       {
/* 346:510 */         throw new UnsupportedOperationException("remove() not supported for BeanMap");
/* 347:    */       }
/* 348:    */     };
/* 349:    */   }
/* 350:    */   
/* 351:    */   public Object getBean()
/* 352:    */   {
/* 353:526 */     return this.bean;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public void setBean(Object newBean)
/* 357:    */   {
/* 358:536 */     this.bean = newBean;
/* 359:537 */     reinitialise();
/* 360:    */   }
/* 361:    */   
/* 362:    */   public Method getReadMethod(String name)
/* 363:    */   {
/* 364:547 */     return (Method)this.readMethods.get(name);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public Method getWriteMethod(String name)
/* 368:    */   {
/* 369:557 */     return (Method)this.writeMethods.get(name);
/* 370:    */   }
/* 371:    */   
/* 372:    */   protected Method getReadMethod(Object name)
/* 373:    */   {
/* 374:573 */     return (Method)this.readMethods.get(name);
/* 375:    */   }
/* 376:    */   
/* 377:    */   protected Method getWriteMethod(Object name)
/* 378:    */   {
/* 379:585 */     return (Method)this.writeMethods.get(name);
/* 380:    */   }
/* 381:    */   
/* 382:    */   protected void reinitialise()
/* 383:    */   {
/* 384:593 */     this.readMethods.clear();
/* 385:594 */     this.writeMethods.clear();
/* 386:595 */     this.types.clear();
/* 387:596 */     initialise();
/* 388:    */   }
/* 389:    */   
/* 390:    */   private void initialise()
/* 391:    */   {
/* 392:600 */     if (getBean() == null) {
/* 393:600 */       return;
/* 394:    */     }
/* 395:602 */     Class beanClass = getBean().getClass();
/* 396:    */     try
/* 397:    */     {
/* 398:605 */       BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
/* 399:606 */       PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
/* 400:607 */       if (propertyDescriptors != null) {
/* 401:608 */         for (int i = 0; i < propertyDescriptors.length; i++)
/* 402:    */         {
/* 403:609 */           PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
/* 404:610 */           if (propertyDescriptor != null)
/* 405:    */           {
/* 406:611 */             String name = propertyDescriptor.getName();
/* 407:612 */             Method readMethod = propertyDescriptor.getReadMethod();
/* 408:613 */             Method writeMethod = propertyDescriptor.getWriteMethod();
/* 409:614 */             Class aType = propertyDescriptor.getPropertyType();
/* 410:616 */             if (readMethod != null) {
/* 411:617 */               this.readMethods.put(name, readMethod);
/* 412:    */             }
/* 413:619 */             if (writeMethod != null) {
/* 414:620 */               this.writeMethods.put(name, writeMethod);
/* 415:    */             }
/* 416:622 */             this.types.put(name, aType);
/* 417:    */           }
/* 418:    */         }
/* 419:    */       }
/* 420:    */     }
/* 421:    */     catch (IntrospectionException e)
/* 422:    */     {
/* 423:628 */       logWarn(e);
/* 424:    */     }
/* 425:    */   }
/* 426:    */   
/* 427:    */   protected static class MyMapEntry
/* 428:    */     extends AbstractMapEntry
/* 429:    */   {
/* 430:    */     private BeanMap owner;
/* 431:    */     
/* 432:    */     protected MyMapEntry(BeanMap owner, Object key, Object value)
/* 433:    */     {
/* 434:661 */       super(value);
/* 435:662 */       this.owner = owner;
/* 436:    */     }
/* 437:    */     
/* 438:    */     public Object setValue(Object value)
/* 439:    */     {
/* 440:672 */       Object key = getKey();
/* 441:673 */       Object oldValue = this.owner.get(key);
/* 442:    */       
/* 443:675 */       this.owner.put(key, value);
/* 444:676 */       Object newValue = this.owner.get(key);
/* 445:677 */       super.setValue(newValue);
/* 446:678 */       return oldValue;
/* 447:    */     }
/* 448:    */   }
/* 449:    */   
/* 450:    */   protected Object[] createWriteMethodArguments(Method method, Object value)
/* 451:    */     throws IllegalAccessException, ClassCastException
/* 452:    */   {
/* 453:    */     try
/* 454:    */     {
/* 455:698 */       if (value != null)
/* 456:    */       {
/* 457:699 */         Class[] types = method.getParameterTypes();
/* 458:700 */         if ((types != null) && (types.length > 0))
/* 459:    */         {
/* 460:701 */           Class paramType = types[0];
/* 461:702 */           if (!paramType.isAssignableFrom(value.getClass())) {
/* 462:703 */             value = convertType(paramType, value);
/* 463:    */           }
/* 464:    */         }
/* 465:    */       }
/* 466:707 */       return new Object[] { value };
/* 467:    */     }
/* 468:    */     catch (InvocationTargetException e)
/* 469:    */     {
/* 470:711 */       logInfo(e);
/* 471:712 */       throw new IllegalArgumentException(e.getMessage());
/* 472:    */     }
/* 473:    */     catch (InstantiationException e)
/* 474:    */     {
/* 475:715 */       logInfo(e);
/* 476:716 */       throw new IllegalArgumentException(e.getMessage());
/* 477:    */     }
/* 478:    */   }
/* 479:    */   
/* 480:    */   protected Object convertType(Class newType, Object value)
/* 481:    */     throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
/* 482:    */   {
/* 483:755 */     Class[] types = { value.getClass() };
/* 484:    */     try
/* 485:    */     {
/* 486:757 */       Constructor constructor = newType.getConstructor(types);
/* 487:758 */       Object[] arguments = { value };
/* 488:759 */       return constructor.newInstance(arguments);
/* 489:    */     }
/* 490:    */     catch (NoSuchMethodException e)
/* 491:    */     {
/* 492:763 */       Transformer transformer = getTypeTransformer(newType);
/* 493:764 */       if (transformer != null) {
/* 494:765 */         return transformer.transform(value);
/* 495:    */       }
/* 496:    */     }
/* 497:767 */     return value;
/* 498:    */   }
/* 499:    */   
/* 500:    */   protected Transformer getTypeTransformer(Class aType)
/* 501:    */   {
/* 502:779 */     return (Transformer)defaultTransformers.get(aType);
/* 503:    */   }
/* 504:    */   
/* 505:    */   protected void logInfo(Exception ex)
/* 506:    */   {
/* 507:790 */     System.out.println("INFO: Exception: " + ex);
/* 508:    */   }
/* 509:    */   
/* 510:    */   protected void logWarn(Exception ex)
/* 511:    */   {
/* 512:801 */     System.out.println("WARN: Exception: " + ex);
/* 513:802 */     ex.printStackTrace();
/* 514:    */   }
/* 515:    */   
/* 516:    */   public BeanMap() {}
/* 517:    */   
/* 518:    */   protected void firePropertyChange(Object key, Object oldValue, Object newValue) {}
/* 519:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BeanMap
 * JD-Core Version:    0.7.0.1
 */