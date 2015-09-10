/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Comparator;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.dom4j.Node;
/*  10:    */ import org.hibernate.Hibernate;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.MappingException;
/*  13:    */ import org.hibernate.cfg.Environment;
/*  14:    */ import org.hibernate.dialect.Dialect;
/*  15:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  16:    */ import org.hibernate.engine.spi.Mapping;
/*  17:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  18:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  19:    */ import org.hibernate.internal.util.StringHelper;
/*  20:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  21:    */ import org.hibernate.metamodel.relational.Size;
/*  22:    */ import org.hibernate.metamodel.relational.Size.LobMultiplier;
/*  23:    */ import org.hibernate.type.descriptor.ValueBinder;
/*  24:    */ import org.hibernate.type.descriptor.ValueExtractor;
/*  25:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  26:    */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  27:    */ import org.hibernate.type.descriptor.java.MutabilityPlan;
/*  28:    */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*  29:    */ 
/*  30:    */ public abstract class AbstractStandardBasicType<T>
/*  31:    */   implements BasicType, StringRepresentableType<T>, XmlRepresentableType<T>
/*  32:    */ {
/*  33: 58 */   private static final Size DEFAULT_SIZE = new Size(19, 2, 255L, Size.LobMultiplier.NONE);
/*  34: 59 */   private final Size dictatedSize = new Size();
/*  35:    */   private final SqlTypeDescriptor sqlTypeDescriptor;
/*  36:    */   private final JavaTypeDescriptor<T> javaTypeDescriptor;
/*  37:    */   
/*  38:    */   public AbstractStandardBasicType(SqlTypeDescriptor sqlTypeDescriptor, JavaTypeDescriptor<T> javaTypeDescriptor)
/*  39:    */   {
/*  40: 65 */     this.sqlTypeDescriptor = sqlTypeDescriptor;
/*  41: 66 */     this.javaTypeDescriptor = javaTypeDescriptor;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public T fromString(String string)
/*  45:    */   {
/*  46: 70 */     return this.javaTypeDescriptor.fromString(string);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String toString(T value)
/*  50:    */   {
/*  51: 74 */     return this.javaTypeDescriptor.toString(value);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public T fromStringValue(String xml)
/*  55:    */     throws HibernateException
/*  56:    */   {
/*  57: 78 */     return fromString(xml);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String toXMLString(T value, SessionFactoryImplementor factory)
/*  61:    */     throws HibernateException
/*  62:    */   {
/*  63: 82 */     return toString(value);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public T fromXMLString(String xml, Mapping factory)
/*  67:    */     throws HibernateException
/*  68:    */   {
/*  69: 86 */     return StringHelper.isEmpty(xml) ? null : fromStringValue(xml);
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected MutabilityPlan<T> getMutabilityPlan()
/*  73:    */   {
/*  74: 90 */     return this.javaTypeDescriptor.getMutabilityPlan();
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected T getReplacement(T original, T target, SessionImplementor session)
/*  78:    */   {
/*  79: 94 */     if (!isMutable()) {
/*  80: 95 */       return original;
/*  81:    */     }
/*  82: 97 */     if (isEqual(original, target)) {
/*  83: 98 */       return original;
/*  84:    */     }
/*  85:101 */     return deepCopy(original);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/*  89:    */   {
/*  90:106 */     return value == null ? ArrayHelper.FALSE : ArrayHelper.TRUE;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String[] getRegistrationKeys()
/*  94:    */   {
/*  95:110 */     return new String[] { registerUnderJavaType() ? new String[] { getName(), this.javaTypeDescriptor.getJavaTypeClass().getName() } : getName() };
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected boolean registerUnderJavaType()
/*  99:    */   {
/* 100:116 */     return false;
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected static Size getDefaultSize()
/* 104:    */   {
/* 105:120 */     return DEFAULT_SIZE;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected Size getDictatedSize()
/* 109:    */   {
/* 110:124 */     return this.dictatedSize;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public final JavaTypeDescriptor<T> getJavaTypeDescriptor()
/* 114:    */   {
/* 115:131 */     return this.javaTypeDescriptor;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public final SqlTypeDescriptor getSqlTypeDescriptor()
/* 119:    */   {
/* 120:135 */     return this.sqlTypeDescriptor;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public final Class getReturnedClass()
/* 124:    */   {
/* 125:139 */     return this.javaTypeDescriptor.getJavaTypeClass();
/* 126:    */   }
/* 127:    */   
/* 128:    */   public final int getColumnSpan(Mapping mapping)
/* 129:    */     throws MappingException
/* 130:    */   {
/* 131:143 */     return sqlTypes(mapping).length;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public final int[] sqlTypes(Mapping mapping)
/* 135:    */     throws MappingException
/* 136:    */   {
/* 137:147 */     return new int[] { this.sqlTypeDescriptor.getSqlType() };
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Size[] dictatedSizes(Mapping mapping)
/* 141:    */     throws MappingException
/* 142:    */   {
/* 143:152 */     return new Size[] { getDictatedSize() };
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Size[] defaultSizes(Mapping mapping)
/* 147:    */     throws MappingException
/* 148:    */   {
/* 149:157 */     return new Size[] { getDefaultSize() };
/* 150:    */   }
/* 151:    */   
/* 152:    */   public final boolean isAssociationType()
/* 153:    */   {
/* 154:161 */     return false;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public final boolean isCollectionType()
/* 158:    */   {
/* 159:165 */     return false;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public final boolean isComponentType()
/* 163:    */   {
/* 164:169 */     return false;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public final boolean isEntityType()
/* 168:    */   {
/* 169:173 */     return false;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public final boolean isAnyType()
/* 173:    */   {
/* 174:177 */     return false;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public final boolean isXMLElement()
/* 178:    */   {
/* 179:181 */     return false;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public final boolean isSame(Object x, Object y)
/* 183:    */   {
/* 184:186 */     return isEqual(x, y);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public final boolean isEqual(Object x, Object y, SessionFactoryImplementor factory)
/* 188:    */   {
/* 189:191 */     return isEqual(x, y);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public final boolean isEqual(Object one, Object another)
/* 193:    */   {
/* 194:196 */     return this.javaTypeDescriptor.areEqual(one, another);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public final int getHashCode(Object x)
/* 198:    */   {
/* 199:201 */     return this.javaTypeDescriptor.extractHashCode(x);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public final int getHashCode(Object x, SessionFactoryImplementor factory)
/* 203:    */   {
/* 204:205 */     return getHashCode(x);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public final int compare(Object x, Object y)
/* 208:    */   {
/* 209:210 */     return this.javaTypeDescriptor.getComparator().compare(x, y);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public final boolean isDirty(Object old, Object current, SessionImplementor session)
/* 213:    */   {
/* 214:214 */     return isDirty(old, current);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public final boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 218:    */   {
/* 219:218 */     return (checkable[0] != 0) && (isDirty(old, current));
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected final boolean isDirty(Object old, Object current)
/* 223:    */   {
/* 224:222 */     return !isSame(old, current);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public final boolean isModified(Object oldHydratedState, Object currentState, boolean[] checkable, SessionImplementor session)
/* 228:    */   {
/* 229:230 */     return isDirty(oldHydratedState, currentState);
/* 230:    */   }
/* 231:    */   
/* 232:    */   public final Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 233:    */     throws SQLException
/* 234:    */   {
/* 235:238 */     return nullSafeGet(rs, names[0], session);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public final Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/* 239:    */     throws SQLException
/* 240:    */   {
/* 241:243 */     return nullSafeGet(rs, name, session);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public final T nullSafeGet(ResultSet rs, String name, final SessionImplementor session)
/* 245:    */     throws SQLException
/* 246:    */   {
/* 247:248 */     WrapperOptions options = new WrapperOptions()
/* 248:    */     {
/* 249:    */       public boolean useStreamForLobBinding()
/* 250:    */       {
/* 251:250 */         return Environment.useStreamsForBinary();
/* 252:    */       }
/* 253:    */       
/* 254:    */       public LobCreator getLobCreator()
/* 255:    */       {
/* 256:254 */         return Hibernate.getLobCreator(session);
/* 257:    */       }
/* 258:    */       
/* 259:    */       public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor)
/* 260:    */       {
/* 261:258 */         SqlTypeDescriptor remapped = sqlTypeDescriptor.canBeRemapped() ? session.getFactory().getDialect().remapSqlTypeDescriptor(sqlTypeDescriptor) : sqlTypeDescriptor;
/* 262:    */         
/* 263:    */ 
/* 264:261 */         return remapped == null ? sqlTypeDescriptor : remapped;
/* 265:    */       }
/* 266:264 */     };
/* 267:265 */     return nullSafeGet(rs, name, options);
/* 268:    */   }
/* 269:    */   
/* 270:    */   protected final T nullSafeGet(ResultSet rs, String name, WrapperOptions options)
/* 271:    */     throws SQLException
/* 272:    */   {
/* 273:269 */     return remapSqlTypeDescriptor(options).getExtractor(this.javaTypeDescriptor).extract(rs, name, options);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public Object get(ResultSet rs, String name, SessionImplementor session)
/* 277:    */     throws HibernateException, SQLException
/* 278:    */   {
/* 279:273 */     return nullSafeGet(rs, name, session);
/* 280:    */   }
/* 281:    */   
/* 282:    */   public final void nullSafeSet(PreparedStatement st, Object value, int index, final SessionImplementor session)
/* 283:    */     throws SQLException
/* 284:    */   {
/* 285:283 */     WrapperOptions options = new WrapperOptions()
/* 286:    */     {
/* 287:    */       public boolean useStreamForLobBinding()
/* 288:    */       {
/* 289:285 */         return Environment.useStreamsForBinary();
/* 290:    */       }
/* 291:    */       
/* 292:    */       public LobCreator getLobCreator()
/* 293:    */       {
/* 294:289 */         return Hibernate.getLobCreator(session);
/* 295:    */       }
/* 296:    */       
/* 297:    */       public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor)
/* 298:    */       {
/* 299:293 */         SqlTypeDescriptor remapped = sqlTypeDescriptor.canBeRemapped() ? session.getFactory().getDialect().remapSqlTypeDescriptor(sqlTypeDescriptor) : sqlTypeDescriptor;
/* 300:    */         
/* 301:    */ 
/* 302:296 */         return remapped == null ? sqlTypeDescriptor : remapped;
/* 303:    */       }
/* 304:299 */     };
/* 305:300 */     nullSafeSet(st, value, index, options);
/* 306:    */   }
/* 307:    */   
/* 308:    */   protected final void nullSafeSet(PreparedStatement st, Object value, int index, WrapperOptions options)
/* 309:    */     throws SQLException
/* 310:    */   {
/* 311:305 */     remapSqlTypeDescriptor(options).getBinder(this.javaTypeDescriptor).bind(st, value, index, options);
/* 312:    */   }
/* 313:    */   
/* 314:    */   protected SqlTypeDescriptor remapSqlTypeDescriptor(WrapperOptions options)
/* 315:    */   {
/* 316:309 */     return options.remapSqlTypeDescriptor(this.sqlTypeDescriptor);
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void set(PreparedStatement st, T value, int index, SessionImplementor session)
/* 320:    */     throws HibernateException, SQLException
/* 321:    */   {
/* 322:313 */     nullSafeSet(st, value, index, session);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public final String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 326:    */   {
/* 327:318 */     return this.javaTypeDescriptor.extractLoggableRepresentation(value);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public final void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 331:    */   {
/* 332:323 */     node.setText(toString(value));
/* 333:    */   }
/* 334:    */   
/* 335:    */   public final Object fromXMLNode(Node xml, Mapping factory)
/* 336:    */   {
/* 337:327 */     return fromString(xml.getText());
/* 338:    */   }
/* 339:    */   
/* 340:    */   public final boolean isMutable()
/* 341:    */   {
/* 342:331 */     return getMutabilityPlan().isMutable();
/* 343:    */   }
/* 344:    */   
/* 345:    */   public final Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 346:    */   {
/* 347:336 */     return deepCopy(value);
/* 348:    */   }
/* 349:    */   
/* 350:    */   protected final T deepCopy(T value)
/* 351:    */   {
/* 352:340 */     return getMutabilityPlan().deepCopy(value);
/* 353:    */   }
/* 354:    */   
/* 355:    */   public final Serializable disassemble(Object value, SessionImplementor session, Object owner)
/* 356:    */     throws HibernateException
/* 357:    */   {
/* 358:345 */     return getMutabilityPlan().disassemble(value);
/* 359:    */   }
/* 360:    */   
/* 361:    */   public final Object assemble(Serializable cached, SessionImplementor session, Object owner)
/* 362:    */     throws HibernateException
/* 363:    */   {
/* 364:349 */     return getMutabilityPlan().assemble(cached);
/* 365:    */   }
/* 366:    */   
/* 367:    */   public final void beforeAssemble(Serializable cached, SessionImplementor session) {}
/* 368:    */   
/* 369:    */   public final Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 370:    */     throws HibernateException, SQLException
/* 371:    */   {
/* 372:357 */     return nullSafeGet(rs, names, session, owner);
/* 373:    */   }
/* 374:    */   
/* 375:    */   public final Object resolve(Object value, SessionImplementor session, Object owner)
/* 376:    */     throws HibernateException
/* 377:    */   {
/* 378:361 */     return value;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public final Object semiResolve(Object value, SessionImplementor session, Object owner)
/* 382:    */     throws HibernateException
/* 383:    */   {
/* 384:365 */     return value;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public final Type getSemiResolvedType(SessionFactoryImplementor factory)
/* 388:    */   {
/* 389:369 */     return this;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public final Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 393:    */   {
/* 394:374 */     return getReplacement(original, target, session);
/* 395:    */   }
/* 396:    */   
/* 397:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache, ForeignKeyDirection foreignKeyDirection)
/* 398:    */   {
/* 399:385 */     return ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT == foreignKeyDirection ? getReplacement(original, target, session) : target;
/* 400:    */   }
/* 401:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AbstractStandardBasicType
 * JD-Core Version:    0.7.0.1
 */