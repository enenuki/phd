/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.dom4j.Node;
/*  11:    */ import org.hibernate.EntityMode;
/*  12:    */ import org.hibernate.FetchMode;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.MappingException;
/*  15:    */ import org.hibernate.TransientObjectException;
/*  16:    */ import org.hibernate.TypeHelper;
/*  17:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  18:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  19:    */ import org.hibernate.engine.spi.Mapping;
/*  20:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  21:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  22:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  23:    */ import org.hibernate.metamodel.relational.Size;
/*  24:    */ import org.hibernate.persister.entity.Joinable;
/*  25:    */ import org.hibernate.proxy.HibernateProxyHelper;
/*  26:    */ 
/*  27:    */ public class AnyType
/*  28:    */   extends AbstractType
/*  29:    */   implements CompositeType, AssociationType
/*  30:    */ {
/*  31:    */   private final Type identifierType;
/*  32:    */   private final Type metaType;
/*  33:    */   
/*  34:    */   public AnyType(Type metaType, Type identifierType)
/*  35:    */   {
/*  36: 61 */     this.identifierType = identifierType;
/*  37: 62 */     this.metaType = metaType;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/*  41:    */     throws HibernateException
/*  42:    */   {
/*  43: 67 */     return value;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean isMethodOf(Method method)
/*  47:    */   {
/*  48: 71 */     return false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean isSame(Object x, Object y)
/*  52:    */     throws HibernateException
/*  53:    */   {
/*  54: 75 */     return x == y;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int compare(Object x, Object y)
/*  58:    */   {
/*  59: 79 */     return 0;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getColumnSpan(Mapping session)
/*  63:    */     throws MappingException
/*  64:    */   {
/*  65: 84 */     return 2;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getName()
/*  69:    */   {
/*  70: 88 */     return "object";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean isMutable()
/*  74:    */   {
/*  75: 92 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/*  79:    */     throws HibernateException, SQLException
/*  80:    */   {
/*  81: 98 */     throw new UnsupportedOperationException("object is a multicolumn type");
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/*  85:    */     throws HibernateException, SQLException
/*  86:    */   {
/*  87:103 */     return resolveAny((String)this.metaType.nullSafeGet(rs, names[0], session, owner), (Serializable)this.identifierType.nullSafeGet(rs, names[1], session, owner), session);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/*  91:    */     throws HibernateException, SQLException
/*  92:    */   {
/*  93:112 */     String entityName = (String)this.metaType.nullSafeGet(rs, names[0], session, owner);
/*  94:113 */     Serializable id = (Serializable)this.identifierType.nullSafeGet(rs, names[1], session, owner);
/*  95:114 */     return new ObjectTypeCacheEntry(entityName, id);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Object resolve(Object value, SessionImplementor session, Object owner)
/*  99:    */     throws HibernateException
/* 100:    */   {
/* 101:119 */     ObjectTypeCacheEntry holder = (ObjectTypeCacheEntry)value;
/* 102:120 */     return resolveAny(holder.entityName, holder.id, session);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Object semiResolve(Object value, SessionImplementor session, Object owner)
/* 106:    */     throws HibernateException
/* 107:    */   {
/* 108:125 */     throw new UnsupportedOperationException("any mappings may not form part of a property-ref");
/* 109:    */   }
/* 110:    */   
/* 111:    */   private Object resolveAny(String entityName, Serializable id, SessionImplementor session)
/* 112:    */     throws HibernateException
/* 113:    */   {
/* 114:130 */     return (entityName == null) || (id == null) ? null : session.internalLoad(entityName, id, false, false);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/* 118:    */     throws HibernateException, SQLException
/* 119:    */   {
/* 120:136 */     nullSafeSet(st, value, index, null, session);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/* 124:    */     throws HibernateException, SQLException
/* 125:    */   {
/* 126:    */     String entityName;
/* 127:    */     String entityName;
/* 128:    */     Serializable id;
/* 129:144 */     if (value == null)
/* 130:    */     {
/* 131:145 */       Serializable id = null;
/* 132:146 */       entityName = null;
/* 133:    */     }
/* 134:    */     else
/* 135:    */     {
/* 136:149 */       entityName = session.bestGuessEntityName(value);
/* 137:150 */       id = ForeignKeys.getEntityIdentifierIfNotUnsaved(entityName, value, session);
/* 138:    */     }
/* 139:154 */     if ((settable == null) || (settable[0] != 0)) {
/* 140:155 */       this.metaType.nullSafeSet(st, entityName, index, session);
/* 141:    */     }
/* 142:157 */     if (settable == null)
/* 143:    */     {
/* 144:158 */       this.identifierType.nullSafeSet(st, id, index + 1, session);
/* 145:    */     }
/* 146:    */     else
/* 147:    */     {
/* 148:161 */       boolean[] idsettable = new boolean[settable.length - 1];
/* 149:162 */       System.arraycopy(settable, 1, idsettable, 0, idsettable.length);
/* 150:163 */       this.identifierType.nullSafeSet(st, id, index + 1, idsettable, session);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Class getReturnedClass()
/* 155:    */   {
/* 156:168 */     return Object.class;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int[] sqlTypes(Mapping mapping)
/* 160:    */     throws MappingException
/* 161:    */   {
/* 162:172 */     return ArrayHelper.join(this.metaType.sqlTypes(mapping), this.identifierType.sqlTypes(mapping));
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Size[] dictatedSizes(Mapping mapping)
/* 166:    */     throws MappingException
/* 167:    */   {
/* 168:180 */     return (Size[])ArrayHelper.join(this.metaType.dictatedSizes(mapping), this.identifierType.dictatedSizes(mapping));
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Size[] defaultSizes(Mapping mapping)
/* 172:    */     throws MappingException
/* 173:    */   {
/* 174:188 */     return (Size[])ArrayHelper.join(this.metaType.defaultSizes(mapping), this.identifierType.defaultSizes(mapping));
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setToXMLNode(Node xml, Object value, SessionFactoryImplementor factory)
/* 178:    */   {
/* 179:195 */     throw new UnsupportedOperationException("any types cannot be stringified");
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 183:    */     throws HibernateException
/* 184:    */   {
/* 185:201 */     return value == null ? "null" : factory.getTypeHelper().entity(HibernateProxyHelper.getClassWithoutInitializingProxy(value)).toLoggableString(value, factory);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 189:    */     throws HibernateException
/* 190:    */   {
/* 191:209 */     throw new UnsupportedOperationException();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static final class ObjectTypeCacheEntry
/* 195:    */     implements Serializable
/* 196:    */   {
/* 197:    */     String entityName;
/* 198:    */     Serializable id;
/* 199:    */     
/* 200:    */     ObjectTypeCacheEntry(String entityName, Serializable id)
/* 201:    */     {
/* 202:216 */       this.entityName = entityName;
/* 203:217 */       this.id = id;
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Object assemble(Serializable cached, SessionImplementor session, Object owner)
/* 208:    */     throws HibernateException
/* 209:    */   {
/* 210:227 */     ObjectTypeCacheEntry e = (ObjectTypeCacheEntry)cached;
/* 211:228 */     return e == null ? null : session.internalLoad(e.entityName, e.id, false, false);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/* 215:    */     throws HibernateException
/* 216:    */   {
/* 217:233 */     return value == null ? null : new ObjectTypeCacheEntry(session.bestGuessEntityName(value), ForeignKeys.getEntityIdentifierIfNotUnsaved(session.bestGuessEntityName(value), value, session));
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean isAnyType()
/* 221:    */   {
/* 222:244 */     return true;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 226:    */     throws HibernateException
/* 227:    */   {
/* 228:254 */     if (original == null) {
/* 229:255 */       return null;
/* 230:    */     }
/* 231:258 */     String entityName = session.bestGuessEntityName(original);
/* 232:259 */     Serializable id = ForeignKeys.getEntityIdentifierIfNotUnsaved(entityName, original, session);
/* 233:    */     
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:264 */     return session.internalLoad(entityName, id, false, false);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public CascadeStyle getCascadeStyle(int i)
/* 241:    */   {
/* 242:273 */     return CascadeStyle.NONE;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public FetchMode getFetchMode(int i)
/* 246:    */   {
/* 247:277 */     return FetchMode.SELECT;
/* 248:    */   }
/* 249:    */   
/* 250:280 */   private static final String[] PROPERTY_NAMES = { "class", "id" };
/* 251:    */   
/* 252:    */   public String[] getPropertyNames()
/* 253:    */   {
/* 254:283 */     return PROPERTY_NAMES;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Object getPropertyValue(Object component, int i, SessionImplementor session)
/* 258:    */     throws HibernateException
/* 259:    */   {
/* 260:289 */     return i == 0 ? session.bestGuessEntityName(component) : getIdentifier(component, session);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Object[] getPropertyValues(Object component, SessionImplementor session)
/* 264:    */     throws HibernateException
/* 265:    */   {
/* 266:297 */     return new Object[] { session.bestGuessEntityName(component), getIdentifier(component, session) };
/* 267:    */   }
/* 268:    */   
/* 269:    */   private Serializable getIdentifier(Object value, SessionImplementor session)
/* 270:    */     throws HibernateException
/* 271:    */   {
/* 272:    */     try
/* 273:    */     {
/* 274:302 */       return ForeignKeys.getEntityIdentifierIfNotUnsaved(session.bestGuessEntityName(value), value, session);
/* 275:    */     }
/* 276:    */     catch (TransientObjectException toe) {}
/* 277:305 */     return null;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public Type[] getSubtypes()
/* 281:    */   {
/* 282:310 */     return new Type[] { this.metaType, this.identifierType };
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setPropertyValues(Object component, Object[] values, EntityMode entityMode)
/* 286:    */     throws HibernateException
/* 287:    */   {
/* 288:316 */     throw new UnsupportedOperationException();
/* 289:    */   }
/* 290:    */   
/* 291:    */   public Object[] getPropertyValues(Object component, EntityMode entityMode)
/* 292:    */   {
/* 293:321 */     throw new UnsupportedOperationException();
/* 294:    */   }
/* 295:    */   
/* 296:    */   public boolean isComponentType()
/* 297:    */   {
/* 298:325 */     return true;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public ForeignKeyDirection getForeignKeyDirection()
/* 302:    */   {
/* 303:330 */     return ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public boolean isAssociationType()
/* 307:    */   {
/* 308:334 */     return true;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public boolean useLHSPrimaryKey()
/* 312:    */   {
/* 313:338 */     return false;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public Joinable getAssociatedJoinable(SessionFactoryImplementor factory)
/* 317:    */   {
/* 318:342 */     throw new UnsupportedOperationException("any types do not have a unique referenced persister");
/* 319:    */   }
/* 320:    */   
/* 321:    */   public boolean isModified(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 322:    */     throws HibernateException
/* 323:    */   {
/* 324:347 */     if (current == null) {
/* 325:347 */       return old != null;
/* 326:    */     }
/* 327:348 */     if (old == null) {
/* 328:348 */       return current != null;
/* 329:    */     }
/* 330:349 */     ObjectTypeCacheEntry holder = (ObjectTypeCacheEntry)old;
/* 331:350 */     boolean[] idcheckable = new boolean[checkable.length - 1];
/* 332:351 */     System.arraycopy(checkable, 1, idcheckable, 0, idcheckable.length);
/* 333:352 */     return ((checkable[0] != 0) && (!holder.entityName.equals(session.bestGuessEntityName(current)))) || (this.identifierType.isModified(holder.id, getIdentifier(current, session), idcheckable, session));
/* 334:    */   }
/* 335:    */   
/* 336:    */   public String getAssociatedEntityName(SessionFactoryImplementor factory)
/* 337:    */     throws MappingException
/* 338:    */   {
/* 339:358 */     throw new UnsupportedOperationException("any types do not have a unique referenced persister");
/* 340:    */   }
/* 341:    */   
/* 342:    */   public boolean[] getPropertyNullability()
/* 343:    */   {
/* 344:362 */     return null;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public String getOnCondition(String alias, SessionFactoryImplementor factory, Map enabledFilters)
/* 348:    */     throws MappingException
/* 349:    */   {
/* 350:367 */     throw new UnsupportedOperationException();
/* 351:    */   }
/* 352:    */   
/* 353:    */   public boolean isReferenceToPrimaryKey()
/* 354:    */   {
/* 355:371 */     return true;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public String getRHSUniqueKeyPropertyName()
/* 359:    */   {
/* 360:375 */     return null;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public String getLHSPropertyName()
/* 364:    */   {
/* 365:379 */     return null;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public boolean isAlwaysDirtyChecked()
/* 369:    */   {
/* 370:383 */     return false;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public boolean isEmbeddedInXML()
/* 374:    */   {
/* 375:387 */     return false;
/* 376:    */   }
/* 377:    */   
/* 378:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 379:    */   {
/* 380:391 */     boolean[] result = new boolean[getColumnSpan(mapping)];
/* 381:392 */     if (value != null) {
/* 382:392 */       Arrays.fill(result, true);
/* 383:    */     }
/* 384:393 */     return result;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 388:    */     throws HibernateException
/* 389:    */   {
/* 390:399 */     return isDirty(old, current, session);
/* 391:    */   }
/* 392:    */   
/* 393:    */   public boolean isEmbedded()
/* 394:    */   {
/* 395:403 */     return false;
/* 396:    */   }
/* 397:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AnyType
 * JD-Core Version:    0.7.0.1
 */