/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.dom4j.Element;
/*  11:    */ import org.dom4j.Node;
/*  12:    */ import org.hibernate.EntityMode;
/*  13:    */ import org.hibernate.FetchMode;
/*  14:    */ import org.hibernate.HibernateException;
/*  15:    */ import org.hibernate.MappingException;
/*  16:    */ import org.hibernate.PropertyNotFoundException;
/*  17:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  18:    */ import org.hibernate.engine.spi.Mapping;
/*  19:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  20:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  21:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  22:    */ import org.hibernate.internal.util.StringHelper;
/*  23:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  24:    */ import org.hibernate.metamodel.relational.Size;
/*  25:    */ import org.hibernate.tuple.StandardProperty;
/*  26:    */ import org.hibernate.tuple.component.ComponentMetamodel;
/*  27:    */ import org.hibernate.tuple.component.ComponentTuplizer;
/*  28:    */ 
/*  29:    */ public class ComponentType
/*  30:    */   extends AbstractType
/*  31:    */   implements CompositeType
/*  32:    */ {
/*  33:    */   private final TypeFactory.TypeScope typeScope;
/*  34:    */   private final String[] propertyNames;
/*  35:    */   private final Type[] propertyTypes;
/*  36:    */   private final boolean[] propertyNullability;
/*  37:    */   protected final int propertySpan;
/*  38:    */   private final CascadeStyle[] cascade;
/*  39:    */   private final FetchMode[] joinedFetch;
/*  40:    */   private final boolean isKey;
/*  41:    */   protected final EntityMode entityMode;
/*  42:    */   protected final ComponentTuplizer componentTuplizer;
/*  43:    */   
/*  44:    */   public ComponentType(TypeFactory.TypeScope typeScope, ComponentMetamodel metamodel)
/*  45:    */   {
/*  46: 73 */     this.typeScope = typeScope;
/*  47:    */     
/*  48: 75 */     this.isKey = metamodel.isKey();
/*  49: 76 */     this.propertySpan = metamodel.getPropertySpan();
/*  50: 77 */     this.propertyNames = new String[this.propertySpan];
/*  51: 78 */     this.propertyTypes = new Type[this.propertySpan];
/*  52: 79 */     this.propertyNullability = new boolean[this.propertySpan];
/*  53: 80 */     this.cascade = new CascadeStyle[this.propertySpan];
/*  54: 81 */     this.joinedFetch = new FetchMode[this.propertySpan];
/*  55: 83 */     for (int i = 0; i < this.propertySpan; i++)
/*  56:    */     {
/*  57: 84 */       StandardProperty prop = metamodel.getProperty(i);
/*  58: 85 */       this.propertyNames[i] = prop.getName();
/*  59: 86 */       this.propertyTypes[i] = prop.getType();
/*  60: 87 */       this.propertyNullability[i] = prop.isNullable();
/*  61: 88 */       this.cascade[i] = prop.getCascadeStyle();
/*  62: 89 */       this.joinedFetch[i] = prop.getFetchMode();
/*  63:    */     }
/*  64: 92 */     this.entityMode = metamodel.getEntityMode();
/*  65: 93 */     this.componentTuplizer = metamodel.getComponentTuplizer();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isKey()
/*  69:    */   {
/*  70: 97 */     return this.isKey;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public EntityMode getEntityMode()
/*  74:    */   {
/*  75:101 */     return this.entityMode;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public ComponentTuplizer getComponentTuplizer()
/*  79:    */   {
/*  80:105 */     return this.componentTuplizer;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getColumnSpan(Mapping mapping)
/*  84:    */     throws MappingException
/*  85:    */   {
/*  86:109 */     int span = 0;
/*  87:110 */     for (int i = 0; i < this.propertySpan; i++) {
/*  88:111 */       span += this.propertyTypes[i].getColumnSpan(mapping);
/*  89:    */     }
/*  90:113 */     return span;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int[] sqlTypes(Mapping mapping)
/*  94:    */     throws MappingException
/*  95:    */   {
/*  96:118 */     int[] sqlTypes = new int[getColumnSpan(mapping)];
/*  97:119 */     int n = 0;
/*  98:120 */     for (int i = 0; i < this.propertySpan; i++)
/*  99:    */     {
/* 100:121 */       int[] subtypes = this.propertyTypes[i].sqlTypes(mapping);
/* 101:122 */       for (int j = 0; j < subtypes.length; j++) {
/* 102:123 */         sqlTypes[(n++)] = subtypes[j];
/* 103:    */       }
/* 104:    */     }
/* 105:126 */     return sqlTypes;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Size[] dictatedSizes(Mapping mapping)
/* 109:    */     throws MappingException
/* 110:    */   {
/* 111:132 */     Size[] sizes = new Size[getColumnSpan(mapping)];
/* 112:133 */     int soFar = 0;
/* 113:134 */     for (Type propertyType : this.propertyTypes)
/* 114:    */     {
/* 115:135 */       Size[] propertySizes = propertyType.dictatedSizes(mapping);
/* 116:136 */       System.arraycopy(propertySizes, 0, sizes, soFar, propertySizes.length);
/* 117:137 */       soFar += propertySizes.length;
/* 118:    */     }
/* 119:139 */     return sizes;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Size[] defaultSizes(Mapping mapping)
/* 123:    */     throws MappingException
/* 124:    */   {
/* 125:145 */     Size[] sizes = new Size[getColumnSpan(mapping)];
/* 126:146 */     int soFar = 0;
/* 127:147 */     for (Type propertyType : this.propertyTypes)
/* 128:    */     {
/* 129:148 */       Size[] propertySizes = propertyType.defaultSizes(mapping);
/* 130:149 */       System.arraycopy(propertySizes, 0, sizes, soFar, propertySizes.length);
/* 131:150 */       soFar += propertySizes.length;
/* 132:    */     }
/* 133:152 */     return sizes;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public final boolean isComponentType()
/* 137:    */   {
/* 138:158 */     return true;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Class getReturnedClass()
/* 142:    */   {
/* 143:162 */     return this.componentTuplizer.getMappedClass();
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isSame(Object x, Object y)
/* 147:    */     throws HibernateException
/* 148:    */   {
/* 149:167 */     if (x == y) {
/* 150:168 */       return true;
/* 151:    */     }
/* 152:170 */     if ((x == null) || (y == null)) {
/* 153:171 */       return false;
/* 154:    */     }
/* 155:173 */     Object[] xvalues = getPropertyValues(x, this.entityMode);
/* 156:174 */     Object[] yvalues = getPropertyValues(y, this.entityMode);
/* 157:175 */     for (int i = 0; i < this.propertySpan; i++) {
/* 158:176 */       if (!this.propertyTypes[i].isSame(xvalues[i], yvalues[i])) {
/* 159:177 */         return false;
/* 160:    */       }
/* 161:    */     }
/* 162:180 */     return true;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean isEqual(Object x, Object y)
/* 166:    */     throws HibernateException
/* 167:    */   {
/* 168:186 */     if (x == y) {
/* 169:187 */       return true;
/* 170:    */     }
/* 171:189 */     if ((x == null) || (y == null)) {
/* 172:190 */       return false;
/* 173:    */     }
/* 174:192 */     Object[] xvalues = getPropertyValues(x, this.entityMode);
/* 175:193 */     Object[] yvalues = getPropertyValues(y, this.entityMode);
/* 176:194 */     for (int i = 0; i < this.propertySpan; i++) {
/* 177:195 */       if (!this.propertyTypes[i].isEqual(xvalues[i], yvalues[i])) {
/* 178:196 */         return false;
/* 179:    */       }
/* 180:    */     }
/* 181:199 */     return true;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory)
/* 185:    */     throws HibernateException
/* 186:    */   {
/* 187:205 */     if (x == y) {
/* 188:206 */       return true;
/* 189:    */     }
/* 190:208 */     if ((x == null) || (y == null)) {
/* 191:209 */       return false;
/* 192:    */     }
/* 193:211 */     Object[] xvalues = getPropertyValues(x, this.entityMode);
/* 194:212 */     Object[] yvalues = getPropertyValues(y, this.entityMode);
/* 195:213 */     for (int i = 0; i < this.propertySpan; i++) {
/* 196:214 */       if (!this.propertyTypes[i].isEqual(xvalues[i], yvalues[i], factory)) {
/* 197:215 */         return false;
/* 198:    */       }
/* 199:    */     }
/* 200:218 */     return true;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public int compare(Object x, Object y)
/* 204:    */   {
/* 205:223 */     if (x == y) {
/* 206:224 */       return 0;
/* 207:    */     }
/* 208:226 */     Object[] xvalues = getPropertyValues(x, this.entityMode);
/* 209:227 */     Object[] yvalues = getPropertyValues(y, this.entityMode);
/* 210:228 */     for (int i = 0; i < this.propertySpan; i++)
/* 211:    */     {
/* 212:229 */       int propertyCompare = this.propertyTypes[i].compare(xvalues[i], yvalues[i]);
/* 213:230 */       if (propertyCompare != 0) {
/* 214:231 */         return propertyCompare;
/* 215:    */       }
/* 216:    */     }
/* 217:234 */     return 0;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean isMethodOf(Method method)
/* 221:    */   {
/* 222:238 */     return false;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int getHashCode(Object x)
/* 226:    */   {
/* 227:243 */     int result = 17;
/* 228:244 */     Object[] values = getPropertyValues(x, this.entityMode);
/* 229:245 */     for (int i = 0; i < this.propertySpan; i++)
/* 230:    */     {
/* 231:246 */       Object y = values[i];
/* 232:247 */       result *= 37;
/* 233:248 */       if (y != null) {
/* 234:249 */         result += this.propertyTypes[i].getHashCode(y);
/* 235:    */       }
/* 236:    */     }
/* 237:252 */     return result;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public int getHashCode(Object x, SessionFactoryImplementor factory)
/* 241:    */   {
/* 242:257 */     int result = 17;
/* 243:258 */     Object[] values = getPropertyValues(x, this.entityMode);
/* 244:259 */     for (int i = 0; i < this.propertySpan; i++)
/* 245:    */     {
/* 246:260 */       Object y = values[i];
/* 247:261 */       result *= 37;
/* 248:262 */       if (y != null) {
/* 249:263 */         result += this.propertyTypes[i].getHashCode(y, factory);
/* 250:    */       }
/* 251:    */     }
/* 252:266 */     return result;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public boolean isDirty(Object x, Object y, SessionImplementor session)
/* 256:    */     throws HibernateException
/* 257:    */   {
/* 258:272 */     if (x == y) {
/* 259:273 */       return false;
/* 260:    */     }
/* 261:275 */     if ((x == null) || (y == null)) {
/* 262:276 */       return true;
/* 263:    */     }
/* 264:278 */     Object[] xvalues = getPropertyValues(x, this.entityMode);
/* 265:279 */     Object[] yvalues = getPropertyValues(y, this.entityMode);
/* 266:280 */     for (int i = 0; i < xvalues.length; i++) {
/* 267:281 */       if (this.propertyTypes[i].isDirty(xvalues[i], yvalues[i], session)) {
/* 268:282 */         return true;
/* 269:    */       }
/* 270:    */     }
/* 271:285 */     return false;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean isDirty(Object x, Object y, boolean[] checkable, SessionImplementor session)
/* 275:    */     throws HibernateException
/* 276:    */   {
/* 277:290 */     if (x == y) {
/* 278:291 */       return false;
/* 279:    */     }
/* 280:293 */     if ((x == null) || (y == null)) {
/* 281:294 */       return true;
/* 282:    */     }
/* 283:296 */     Object[] xvalues = getPropertyValues(x, this.entityMode);
/* 284:297 */     Object[] yvalues = getPropertyValues(y, this.entityMode);
/* 285:298 */     int loc = 0;
/* 286:299 */     for (int i = 0; i < xvalues.length; i++)
/* 287:    */     {
/* 288:300 */       int len = this.propertyTypes[i].getColumnSpan(session.getFactory());
/* 289:301 */       if (len <= 1)
/* 290:    */       {
/* 291:302 */         boolean dirty = ((len == 0) || (checkable[loc] != 0)) && (this.propertyTypes[i].isDirty(xvalues[i], yvalues[i], session));
/* 292:304 */         if (dirty) {
/* 293:305 */           return true;
/* 294:    */         }
/* 295:    */       }
/* 296:    */       else
/* 297:    */       {
/* 298:309 */         boolean[] subcheckable = new boolean[len];
/* 299:310 */         System.arraycopy(checkable, loc, subcheckable, 0, len);
/* 300:311 */         boolean dirty = this.propertyTypes[i].isDirty(xvalues[i], yvalues[i], subcheckable, session);
/* 301:312 */         if (dirty) {
/* 302:313 */           return true;
/* 303:    */         }
/* 304:    */       }
/* 305:316 */       loc += len;
/* 306:    */     }
/* 307:318 */     return false;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean isModified(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 311:    */     throws HibernateException
/* 312:    */   {
/* 313:325 */     if (current == null) {
/* 314:326 */       return old != null;
/* 315:    */     }
/* 316:328 */     if (old == null) {
/* 317:329 */       return current != null;
/* 318:    */     }
/* 319:331 */     Object[] currentValues = getPropertyValues(current, session);
/* 320:332 */     Object[] oldValues = (Object[])old;
/* 321:333 */     int loc = 0;
/* 322:334 */     for (int i = 0; i < currentValues.length; i++)
/* 323:    */     {
/* 324:335 */       int len = this.propertyTypes[i].getColumnSpan(session.getFactory());
/* 325:336 */       boolean[] subcheckable = new boolean[len];
/* 326:337 */       System.arraycopy(checkable, loc, subcheckable, 0, len);
/* 327:338 */       if (this.propertyTypes[i].isModified(oldValues[i], currentValues[i], subcheckable, session)) {
/* 328:339 */         return true;
/* 329:    */       }
/* 330:341 */       loc += len;
/* 331:    */     }
/* 332:343 */     return false;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 336:    */     throws HibernateException, SQLException
/* 337:    */   {
/* 338:349 */     return resolve(hydrate(rs, names, session, owner), session, owner);
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void nullSafeSet(PreparedStatement st, Object value, int begin, SessionImplementor session)
/* 342:    */     throws HibernateException, SQLException
/* 343:    */   {
/* 344:355 */     Object[] subvalues = nullSafeGetValues(value, this.entityMode);
/* 345:357 */     for (int i = 0; i < this.propertySpan; i++)
/* 346:    */     {
/* 347:358 */       this.propertyTypes[i].nullSafeSet(st, subvalues[i], begin, session);
/* 348:359 */       begin += this.propertyTypes[i].getColumnSpan(session.getFactory());
/* 349:    */     }
/* 350:    */   }
/* 351:    */   
/* 352:    */   public void nullSafeSet(PreparedStatement st, Object value, int begin, boolean[] settable, SessionImplementor session)
/* 353:    */     throws HibernateException, SQLException
/* 354:    */   {
/* 355:371 */     Object[] subvalues = nullSafeGetValues(value, this.entityMode);
/* 356:    */     
/* 357:373 */     int loc = 0;
/* 358:374 */     for (int i = 0; i < this.propertySpan; i++)
/* 359:    */     {
/* 360:375 */       int len = this.propertyTypes[i].getColumnSpan(session.getFactory());
/* 361:376 */       if (len != 0) {
/* 362:379 */         if (len == 1)
/* 363:    */         {
/* 364:380 */           if (settable[loc] != 0)
/* 365:    */           {
/* 366:381 */             this.propertyTypes[i].nullSafeSet(st, subvalues[i], begin, session);
/* 367:382 */             begin++;
/* 368:    */           }
/* 369:    */         }
/* 370:    */         else
/* 371:    */         {
/* 372:386 */           boolean[] subsettable = new boolean[len];
/* 373:387 */           System.arraycopy(settable, loc, subsettable, 0, len);
/* 374:388 */           this.propertyTypes[i].nullSafeSet(st, subvalues[i], begin, subsettable, session);
/* 375:389 */           begin += ArrayHelper.countTrue(subsettable);
/* 376:    */         }
/* 377:    */       }
/* 378:391 */       loc += len;
/* 379:    */     }
/* 380:    */   }
/* 381:    */   
/* 382:    */   private Object[] nullSafeGetValues(Object value, EntityMode entityMode)
/* 383:    */     throws HibernateException
/* 384:    */   {
/* 385:396 */     if (value == null) {
/* 386:397 */       return new Object[this.propertySpan];
/* 387:    */     }
/* 388:400 */     return getPropertyValues(value, entityMode);
/* 389:    */   }
/* 390:    */   
/* 391:    */   public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/* 392:    */     throws HibernateException, SQLException
/* 393:    */   {
/* 394:407 */     return nullSafeGet(rs, new String[] { name }, session, owner);
/* 395:    */   }
/* 396:    */   
/* 397:    */   public Object getPropertyValue(Object component, int i, SessionImplementor session)
/* 398:    */     throws HibernateException
/* 399:    */   {
/* 400:412 */     return getPropertyValue(component, i, this.entityMode);
/* 401:    */   }
/* 402:    */   
/* 403:    */   public Object getPropertyValue(Object component, int i, EntityMode entityMode)
/* 404:    */     throws HibernateException
/* 405:    */   {
/* 406:417 */     return this.componentTuplizer.getPropertyValue(component, i);
/* 407:    */   }
/* 408:    */   
/* 409:    */   public Object[] getPropertyValues(Object component, SessionImplementor session)
/* 410:    */     throws HibernateException
/* 411:    */   {
/* 412:422 */     return getPropertyValues(component, this.entityMode);
/* 413:    */   }
/* 414:    */   
/* 415:    */   public Object[] getPropertyValues(Object component, EntityMode entityMode)
/* 416:    */     throws HibernateException
/* 417:    */   {
/* 418:427 */     return this.componentTuplizer.getPropertyValues(component);
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void setPropertyValues(Object component, Object[] values, EntityMode entityMode)
/* 422:    */     throws HibernateException
/* 423:    */   {
/* 424:432 */     this.componentTuplizer.setPropertyValues(component, values);
/* 425:    */   }
/* 426:    */   
/* 427:    */   public Type[] getSubtypes()
/* 428:    */   {
/* 429:436 */     return this.propertyTypes;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public String getName()
/* 433:    */   {
/* 434:440 */     return "component" + ArrayHelper.toString(this.propertyNames);
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 438:    */     throws HibernateException
/* 439:    */   {
/* 440:445 */     if (value == null) {
/* 441:446 */       return "null";
/* 442:    */     }
/* 443:448 */     Map result = new HashMap();
/* 444:449 */     if (this.entityMode == null) {
/* 445:450 */       throw new ClassCastException(value.getClass().getName());
/* 446:    */     }
/* 447:452 */     Object[] values = getPropertyValues(value, this.entityMode);
/* 448:453 */     for (int i = 0; i < this.propertyTypes.length; i++) {
/* 449:454 */       result.put(this.propertyNames[i], this.propertyTypes[i].toLoggableString(values[i], factory));
/* 450:    */     }
/* 451:456 */     return StringHelper.unqualify(getName()) + result.toString();
/* 452:    */   }
/* 453:    */   
/* 454:    */   public String[] getPropertyNames()
/* 455:    */   {
/* 456:460 */     return this.propertyNames;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public Object deepCopy(Object component, SessionFactoryImplementor factory)
/* 460:    */     throws HibernateException
/* 461:    */   {
/* 462:465 */     if (component == null) {
/* 463:466 */       return null;
/* 464:    */     }
/* 465:469 */     Object[] values = getPropertyValues(component, this.entityMode);
/* 466:470 */     for (int i = 0; i < this.propertySpan; i++) {
/* 467:471 */       values[i] = this.propertyTypes[i].deepCopy(values[i], factory);
/* 468:    */     }
/* 469:474 */     Object result = instantiate(this.entityMode);
/* 470:475 */     setPropertyValues(result, values, this.entityMode);
/* 471:479 */     if (this.componentTuplizer.hasParentProperty()) {
/* 472:480 */       this.componentTuplizer.setParent(result, this.componentTuplizer.getParent(component), factory);
/* 473:    */     }
/* 474:483 */     return result;
/* 475:    */   }
/* 476:    */   
/* 477:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 478:    */     throws HibernateException
/* 479:    */   {
/* 480:494 */     if (original == null) {
/* 481:495 */       return null;
/* 482:    */     }
/* 483:499 */     Object result = target == null ? instantiate(owner, session) : target;
/* 484:    */     
/* 485:    */ 
/* 486:    */ 
/* 487:503 */     Object[] values = TypeHelper.replace(getPropertyValues(original, this.entityMode), getPropertyValues(result, this.entityMode), this.propertyTypes, session, owner, copyCache);
/* 488:    */     
/* 489:    */ 
/* 490:    */ 
/* 491:    */ 
/* 492:    */ 
/* 493:    */ 
/* 494:    */ 
/* 495:    */ 
/* 496:512 */     setPropertyValues(result, values, this.entityMode);
/* 497:513 */     return result;
/* 498:    */   }
/* 499:    */   
/* 500:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection)
/* 501:    */     throws HibernateException
/* 502:    */   {
/* 503:526 */     if (original == null) {
/* 504:527 */       return null;
/* 505:    */     }
/* 506:531 */     Object result = target == null ? instantiate(owner, session) : target;
/* 507:    */     
/* 508:    */ 
/* 509:    */ 
/* 510:535 */     Object[] values = TypeHelper.replace(getPropertyValues(original, this.entityMode), getPropertyValues(result, this.entityMode), this.propertyTypes, session, owner, copyCache, foreignKeyDirection);
/* 511:    */     
/* 512:    */ 
/* 513:    */ 
/* 514:    */ 
/* 515:    */ 
/* 516:    */ 
/* 517:    */ 
/* 518:    */ 
/* 519:    */ 
/* 520:545 */     setPropertyValues(result, values, this.entityMode);
/* 521:546 */     return result;
/* 522:    */   }
/* 523:    */   
/* 524:    */   public Object instantiate(EntityMode entityMode)
/* 525:    */     throws HibernateException
/* 526:    */   {
/* 527:553 */     return this.componentTuplizer.instantiate();
/* 528:    */   }
/* 529:    */   
/* 530:    */   public Object instantiate(Object parent, SessionImplementor session)
/* 531:    */     throws HibernateException
/* 532:    */   {
/* 533:559 */     Object result = instantiate(this.entityMode);
/* 534:561 */     if ((this.componentTuplizer.hasParentProperty()) && (parent != null)) {
/* 535:562 */       this.componentTuplizer.setParent(result, session.getPersistenceContext().proxyFor(parent), session.getFactory());
/* 536:    */     }
/* 537:569 */     return result;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public CascadeStyle getCascadeStyle(int i)
/* 541:    */   {
/* 542:573 */     return this.cascade[i];
/* 543:    */   }
/* 544:    */   
/* 545:    */   public boolean isMutable()
/* 546:    */   {
/* 547:577 */     return true;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/* 551:    */     throws HibernateException
/* 552:    */   {
/* 553:584 */     if (value == null) {
/* 554:585 */       return null;
/* 555:    */     }
/* 556:588 */     Object[] values = getPropertyValues(value, this.entityMode);
/* 557:589 */     for (int i = 0; i < this.propertyTypes.length; i++) {
/* 558:590 */       values[i] = this.propertyTypes[i].disassemble(values[i], session, owner);
/* 559:    */     }
/* 560:592 */     return values;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public Object assemble(Serializable object, SessionImplementor session, Object owner)
/* 564:    */     throws HibernateException
/* 565:    */   {
/* 566:600 */     if (object == null) {
/* 567:601 */       return null;
/* 568:    */     }
/* 569:604 */     Object[] values = (Object[])object;
/* 570:605 */     Object[] assembled = new Object[values.length];
/* 571:606 */     for (int i = 0; i < this.propertyTypes.length; i++) {
/* 572:607 */       assembled[i] = this.propertyTypes[i].assemble((Serializable)values[i], session, owner);
/* 573:    */     }
/* 574:609 */     Object result = instantiate(owner, session);
/* 575:610 */     setPropertyValues(result, assembled, this.entityMode);
/* 576:611 */     return result;
/* 577:    */   }
/* 578:    */   
/* 579:    */   public FetchMode getFetchMode(int i)
/* 580:    */   {
/* 581:616 */     return this.joinedFetch[i];
/* 582:    */   }
/* 583:    */   
/* 584:    */   public Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 585:    */     throws HibernateException, SQLException
/* 586:    */   {
/* 587:627 */     int begin = 0;
/* 588:628 */     boolean notNull = false;
/* 589:629 */     Object[] values = new Object[this.propertySpan];
/* 590:630 */     for (int i = 0; i < this.propertySpan; i++)
/* 591:    */     {
/* 592:631 */       int length = this.propertyTypes[i].getColumnSpan(session.getFactory());
/* 593:632 */       String[] range = ArrayHelper.slice(names, begin, length);
/* 594:633 */       Object val = this.propertyTypes[i].hydrate(rs, range, session, owner);
/* 595:634 */       if (val == null)
/* 596:    */       {
/* 597:635 */         if (this.isKey) {
/* 598:636 */           return null;
/* 599:    */         }
/* 600:    */       }
/* 601:    */       else {
/* 602:640 */         notNull = true;
/* 603:    */       }
/* 604:642 */       values[i] = val;
/* 605:643 */       begin += length;
/* 606:    */     }
/* 607:646 */     return notNull ? values : null;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public Object resolve(Object value, SessionImplementor session, Object owner)
/* 611:    */     throws HibernateException
/* 612:    */   {
/* 613:653 */     if (value != null)
/* 614:    */     {
/* 615:654 */       Object result = instantiate(owner, session);
/* 616:655 */       Object[] values = (Object[])value;
/* 617:656 */       Object[] resolvedValues = new Object[values.length];
/* 618:657 */       for (int i = 0; i < values.length; i++) {
/* 619:658 */         resolvedValues[i] = this.propertyTypes[i].resolve(values[i], session, owner);
/* 620:    */       }
/* 621:660 */       setPropertyValues(result, resolvedValues, this.entityMode);
/* 622:661 */       return result;
/* 623:    */     }
/* 624:664 */     return null;
/* 625:    */   }
/* 626:    */   
/* 627:    */   public Object semiResolve(Object value, SessionImplementor session, Object owner)
/* 628:    */     throws HibernateException
/* 629:    */   {
/* 630:673 */     return resolve(value, session, owner);
/* 631:    */   }
/* 632:    */   
/* 633:    */   public boolean[] getPropertyNullability()
/* 634:    */   {
/* 635:677 */     return this.propertyNullability;
/* 636:    */   }
/* 637:    */   
/* 638:    */   public boolean isXMLElement()
/* 639:    */   {
/* 640:682 */     return true;
/* 641:    */   }
/* 642:    */   
/* 643:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 644:    */     throws HibernateException
/* 645:    */   {
/* 646:686 */     return xml;
/* 647:    */   }
/* 648:    */   
/* 649:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 650:    */     throws HibernateException
/* 651:    */   {
/* 652:690 */     replaceNode(node, (Element)value);
/* 653:    */   }
/* 654:    */   
/* 655:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 656:    */   {
/* 657:694 */     boolean[] result = new boolean[getColumnSpan(mapping)];
/* 658:695 */     if (value == null) {
/* 659:696 */       return result;
/* 660:    */     }
/* 661:698 */     Object[] values = getPropertyValues(value, EntityMode.POJO);
/* 662:699 */     int loc = 0;
/* 663:700 */     for (int i = 0; i < this.propertyTypes.length; i++)
/* 664:    */     {
/* 665:701 */       boolean[] propertyNullness = this.propertyTypes[i].toColumnNullness(values[i], mapping);
/* 666:702 */       System.arraycopy(propertyNullness, 0, result, loc, propertyNullness.length);
/* 667:703 */       loc += propertyNullness.length;
/* 668:    */     }
/* 669:705 */     return result;
/* 670:    */   }
/* 671:    */   
/* 672:    */   public boolean isEmbedded()
/* 673:    */   {
/* 674:709 */     return false;
/* 675:    */   }
/* 676:    */   
/* 677:    */   public int getPropertyIndex(String name)
/* 678:    */   {
/* 679:713 */     String[] names = getPropertyNames();
/* 680:714 */     int i = 0;
/* 681:714 */     for (int max = names.length; i < max; i++) {
/* 682:715 */       if (names[i].equals(name)) {
/* 683:716 */         return i;
/* 684:    */       }
/* 685:    */     }
/* 686:719 */     throw new PropertyNotFoundException("Unable to locate property named " + name + " on " + getReturnedClass().getName());
/* 687:    */   }
/* 688:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ComponentType
 * JD-Core Version:    0.7.0.1
 */