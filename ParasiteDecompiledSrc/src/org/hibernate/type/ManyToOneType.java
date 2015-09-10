/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.MappingException;
/*  11:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  12:    */ import org.hibernate.engine.spi.BatchFetchQueue;
/*  13:    */ import org.hibernate.engine.spi.EntityKey;
/*  14:    */ import org.hibernate.engine.spi.Mapping;
/*  15:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  16:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  17:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  18:    */ import org.hibernate.metamodel.relational.Size;
/*  19:    */ import org.hibernate.persister.entity.EntityPersister;
/*  20:    */ 
/*  21:    */ public class ManyToOneType
/*  22:    */   extends EntityType
/*  23:    */ {
/*  24:    */   private final boolean ignoreNotFound;
/*  25:    */   private boolean isLogicalOneToOne;
/*  26:    */   
/*  27:    */   public ManyToOneType(TypeFactory.TypeScope scope, String referencedEntityName)
/*  28:    */   {
/*  29: 58 */     this(scope, referencedEntityName, false);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public ManyToOneType(TypeFactory.TypeScope scope, String referencedEntityName, boolean lazy)
/*  33:    */   {
/*  34: 70 */     this(scope, referencedEntityName, null, lazy, true, false, false, false);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public ManyToOneType(TypeFactory.TypeScope scope, String referencedEntityName, String uniqueKeyPropertyName, boolean lazy, boolean unwrapProxy, boolean isEmbeddedInXML, boolean ignoreNotFound, boolean isLogicalOneToOne)
/*  38:    */   {
/*  39: 82 */     super(scope, referencedEntityName, uniqueKeyPropertyName, !lazy, isEmbeddedInXML, unwrapProxy);
/*  40: 83 */     this.ignoreNotFound = ignoreNotFound;
/*  41: 84 */     this.isLogicalOneToOne = isLogicalOneToOne;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected boolean isNullable()
/*  45:    */   {
/*  46: 88 */     return this.ignoreNotFound;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean isAlwaysDirtyChecked()
/*  50:    */   {
/*  51: 96 */     return true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isOneToOne()
/*  55:    */   {
/*  56:100 */     return false;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isLogicalOneToOne()
/*  60:    */   {
/*  61:104 */     return this.isLogicalOneToOne;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getColumnSpan(Mapping mapping)
/*  65:    */     throws MappingException
/*  66:    */   {
/*  67:109 */     return getIdentifierOrUniqueKeyType(mapping).getColumnSpan(mapping);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int[] sqlTypes(Mapping mapping)
/*  71:    */     throws MappingException
/*  72:    */   {
/*  73:113 */     return getIdentifierOrUniqueKeyType(mapping).sqlTypes(mapping);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Size[] dictatedSizes(Mapping mapping)
/*  77:    */     throws MappingException
/*  78:    */   {
/*  79:118 */     return getIdentifierOrUniqueKeyType(mapping).dictatedSizes(mapping);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Size[] defaultSizes(Mapping mapping)
/*  83:    */     throws MappingException
/*  84:    */   {
/*  85:123 */     return getIdentifierOrUniqueKeyType(mapping).defaultSizes(mapping);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/*  89:    */     throws HibernateException, SQLException
/*  90:    */   {
/*  91:132 */     getIdentifierOrUniqueKeyType(session.getFactory()).nullSafeSet(st, getIdentifier(value, session), index, settable, session);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/*  95:    */     throws HibernateException, SQLException
/*  96:    */   {
/*  97:141 */     getIdentifierOrUniqueKeyType(session.getFactory()).nullSafeSet(st, getIdentifier(value, session), index, session);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public ForeignKeyDirection getForeignKeyDirection()
/* 101:    */   {
/* 102:146 */     return ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Object hydrate(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 106:    */     throws HibernateException, SQLException
/* 107:    */   {
/* 108:157 */     Serializable id = (Serializable)getIdentifierOrUniqueKeyType(session.getFactory()).nullSafeGet(rs, names, session, null);
/* 109:    */     
/* 110:159 */     scheduleBatchLoadIfNeeded(id, session);
/* 111:160 */     return id;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void scheduleBatchLoadIfNeeded(Serializable id, SessionImplementor session)
/* 115:    */     throws MappingException
/* 116:    */   {
/* 117:169 */     if ((this.uniqueKeyPropertyName == null) && (id != null))
/* 118:    */     {
/* 119:170 */       EntityPersister persister = session.getFactory().getEntityPersister(getAssociatedEntityName());
/* 120:171 */       EntityKey entityKey = session.generateEntityKey(id, persister);
/* 121:172 */       if (!session.getPersistenceContext().containsEntity(entityKey)) {
/* 122:173 */         session.getPersistenceContext().getBatchFetchQueue().addBatchLoadableEntityKey(entityKey);
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean useLHSPrimaryKey()
/* 128:    */   {
/* 129:179 */     return false;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public boolean isModified(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 133:    */     throws HibernateException
/* 134:    */   {
/* 135:187 */     if (current == null) {
/* 136:188 */       return old != null;
/* 137:    */     }
/* 138:190 */     if (old == null) {
/* 139:192 */       return true;
/* 140:    */     }
/* 141:195 */     return getIdentifierOrUniqueKeyType(session.getFactory()).isDirty(old, getIdentifier(current, session), session);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/* 145:    */     throws HibernateException
/* 146:    */   {
/* 147:204 */     if (isNotEmbedded(session)) {
/* 148:205 */       return getIdentifierType(session).disassemble(value, session, owner);
/* 149:    */     }
/* 150:208 */     if (value == null) {
/* 151:209 */       return null;
/* 152:    */     }
/* 153:214 */     Object id = ForeignKeys.getEntityIdentifierIfNotUnsaved(getAssociatedEntityName(), value, session);
/* 154:219 */     if (id == null) {
/* 155:220 */       throw new AssertionFailure("cannot cache a reference to an object with a null id: " + getAssociatedEntityName());
/* 156:    */     }
/* 157:225 */     return getIdentifierType(session).disassemble(id, session, owner);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Object assemble(Serializable oid, SessionImplementor session, Object owner)
/* 161:    */     throws HibernateException
/* 162:    */   {
/* 163:237 */     Serializable id = assembleId(oid, session);
/* 164:239 */     if (isNotEmbedded(session)) {
/* 165:240 */       return id;
/* 166:    */     }
/* 167:243 */     if (id == null) {
/* 168:244 */       return null;
/* 169:    */     }
/* 170:247 */     return resolveIdentifier(id, session);
/* 171:    */   }
/* 172:    */   
/* 173:    */   private Serializable assembleId(Serializable oid, SessionImplementor session)
/* 174:    */   {
/* 175:253 */     return (Serializable)getIdentifierType(session).assemble(oid, session, null);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void beforeAssemble(Serializable oid, SessionImplementor session)
/* 179:    */   {
/* 180:257 */     scheduleBatchLoadIfNeeded(assembleId(oid, session), session);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 184:    */   {
/* 185:261 */     boolean[] result = new boolean[getColumnSpan(mapping)];
/* 186:262 */     if (value != null) {
/* 187:263 */       Arrays.fill(result, true);
/* 188:    */     }
/* 189:265 */     return result;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean isDirty(Object old, Object current, SessionImplementor session)
/* 193:    */     throws HibernateException
/* 194:    */   {
/* 195:272 */     if (isSame(old, current)) {
/* 196:273 */       return false;
/* 197:    */     }
/* 198:275 */     Object oldid = getIdentifier(old, session);
/* 199:276 */     Object newid = getIdentifier(current, session);
/* 200:277 */     return getIdentifierType(session).isDirty(oldid, newid, session);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 204:    */     throws HibernateException
/* 205:    */   {
/* 206:285 */     if (isAlwaysDirtyChecked()) {
/* 207:286 */       return isDirty(old, current, session);
/* 208:    */     }
/* 209:289 */     if (isSame(old, current)) {
/* 210:290 */       return false;
/* 211:    */     }
/* 212:292 */     Object oldid = getIdentifier(old, session);
/* 213:293 */     Object newid = getIdentifier(current, session);
/* 214:294 */     return getIdentifierType(session).isDirty(oldid, newid, checkable, session);
/* 215:    */   }
/* 216:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ManyToOneType
 * JD-Core Version:    0.7.0.1
 */