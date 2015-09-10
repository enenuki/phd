/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.EntityMode;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.Interceptor;
/*   7:    */ import org.hibernate.TransientObjectException;
/*   8:    */ import org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer;
/*   9:    */ import org.hibernate.dialect.Dialect;
/*  10:    */ import org.hibernate.engine.spi.EntityEntry;
/*  11:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  14:    */ import org.hibernate.persister.entity.EntityPersister;
/*  15:    */ import org.hibernate.proxy.HibernateProxy;
/*  16:    */ import org.hibernate.proxy.LazyInitializer;
/*  17:    */ import org.hibernate.type.CompositeType;
/*  18:    */ import org.hibernate.type.EntityType;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ 
/*  21:    */ public final class ForeignKeys
/*  22:    */ {
/*  23:    */   public static class Nullifier
/*  24:    */   {
/*  25:    */     private final boolean isDelete;
/*  26:    */     private final boolean isEarlyInsert;
/*  27:    */     private final SessionImplementor session;
/*  28:    */     private final Object self;
/*  29:    */     
/*  30:    */     public Nullifier(Object self, boolean isDelete, boolean isEarlyInsert, SessionImplementor session)
/*  31:    */     {
/*  32: 58 */       this.isDelete = isDelete;
/*  33: 59 */       this.isEarlyInsert = isEarlyInsert;
/*  34: 60 */       this.session = session;
/*  35: 61 */       this.self = self;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public void nullifyTransientReferences(Object[] values, Type[] types)
/*  39:    */       throws HibernateException
/*  40:    */     {
/*  41: 71 */       for (int i = 0; i < types.length; i++) {
/*  42: 72 */         values[i] = nullifyTransientReferences(values[i], types[i]);
/*  43:    */       }
/*  44:    */     }
/*  45:    */     
/*  46:    */     private Object nullifyTransientReferences(Object value, Type type)
/*  47:    */       throws HibernateException
/*  48:    */     {
/*  49: 84 */       if (value == null) {
/*  50: 85 */         return null;
/*  51:    */       }
/*  52: 87 */       if (type.isEntityType())
/*  53:    */       {
/*  54: 88 */         EntityType entityType = (EntityType)type;
/*  55: 89 */         if (entityType.isOneToOne()) {
/*  56: 90 */           return value;
/*  57:    */         }
/*  58: 93 */         String entityName = entityType.getAssociatedEntityName();
/*  59: 94 */         return isNullifiable(entityName, value) ? null : value;
/*  60:    */       }
/*  61: 97 */       if (type.isAnyType()) {
/*  62: 98 */         return isNullifiable(null, value) ? null : value;
/*  63:    */       }
/*  64:100 */       if (type.isComponentType())
/*  65:    */       {
/*  66:101 */         CompositeType actype = (CompositeType)type;
/*  67:102 */         Object[] subvalues = actype.getPropertyValues(value, this.session);
/*  68:103 */         Type[] subtypes = actype.getSubtypes();
/*  69:104 */         boolean substitute = false;
/*  70:105 */         for (int i = 0; i < subvalues.length; i++)
/*  71:    */         {
/*  72:106 */           Object replacement = nullifyTransientReferences(subvalues[i], subtypes[i]);
/*  73:107 */           if (replacement != subvalues[i])
/*  74:    */           {
/*  75:108 */             substitute = true;
/*  76:109 */             subvalues[i] = replacement;
/*  77:    */           }
/*  78:    */         }
/*  79:112 */         if (substitute) {
/*  80:114 */           actype.setPropertyValues(value, subvalues, EntityMode.POJO);
/*  81:    */         }
/*  82:116 */         return value;
/*  83:    */       }
/*  84:119 */       return value;
/*  85:    */     }
/*  86:    */     
/*  87:    */     private boolean isNullifiable(String entityName, Object object)
/*  88:    */       throws HibernateException
/*  89:    */     {
/*  90:130 */       if (object == LazyPropertyInitializer.UNFETCHED_PROPERTY) {
/*  91:130 */         return false;
/*  92:    */       }
/*  93:132 */       if ((object instanceof HibernateProxy))
/*  94:    */       {
/*  95:134 */         LazyInitializer li = ((HibernateProxy)object).getHibernateLazyInitializer();
/*  96:135 */         if (li.getImplementation(this.session) == null) {
/*  97:136 */           return false;
/*  98:    */         }
/*  99:142 */         object = li.getImplementation();
/* 100:    */       }
/* 101:149 */       if (object == this.self) {
/* 102:150 */         return (this.isEarlyInsert) || ((this.isDelete) && (this.session.getFactory().getDialect().hasSelfReferentialForeignKeyBug()));
/* 103:    */       }
/* 104:163 */       EntityEntry entityEntry = this.session.getPersistenceContext().getEntry(object);
/* 105:164 */       if (entityEntry == null) {
/* 106:165 */         return ForeignKeys.isTransient(entityName, object, null, this.session);
/* 107:    */       }
/* 108:168 */       return entityEntry.isNullifiable(this.isEarlyInsert, this.session);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static boolean isNotTransient(String entityName, Object entity, Boolean assumed, SessionImplementor session)
/* 113:    */     throws HibernateException
/* 114:    */   {
/* 115:183 */     if ((entity instanceof HibernateProxy)) {
/* 116:183 */       return true;
/* 117:    */     }
/* 118:184 */     if (session.getPersistenceContext().isEntryFor(entity)) {
/* 119:184 */       return true;
/* 120:    */     }
/* 121:185 */     return !isTransient(entityName, entity, assumed, session);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static boolean isTransient(String entityName, Object entity, Boolean assumed, SessionImplementor session)
/* 125:    */     throws HibernateException
/* 126:    */   {
/* 127:197 */     if (entity == LazyPropertyInitializer.UNFETCHED_PROPERTY) {
/* 128:200 */       return false;
/* 129:    */     }
/* 130:204 */     Boolean isUnsaved = session.getInterceptor().isTransient(entity);
/* 131:205 */     if (isUnsaved != null) {
/* 132:205 */       return isUnsaved.booleanValue();
/* 133:    */     }
/* 134:208 */     EntityPersister persister = session.getEntityPersister(entityName, entity);
/* 135:209 */     isUnsaved = persister.isTransient(entity, session);
/* 136:210 */     if (isUnsaved != null) {
/* 137:210 */       return isUnsaved.booleanValue();
/* 138:    */     }
/* 139:214 */     if (assumed != null) {
/* 140:214 */       return assumed.booleanValue();
/* 141:    */     }
/* 142:217 */     Object[] snapshot = session.getPersistenceContext().getDatabaseSnapshot(persister.getIdentifier(entity, session), persister);
/* 143:    */     
/* 144:    */ 
/* 145:    */ 
/* 146:221 */     return snapshot == null;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static Serializable getEntityIdentifierIfNotUnsaved(String entityName, Object object, SessionImplementor session)
/* 150:    */     throws HibernateException
/* 151:    */   {
/* 152:239 */     if (object == null) {
/* 153:240 */       return null;
/* 154:    */     }
/* 155:243 */     Serializable id = session.getContextEntityIdentifier(object);
/* 156:244 */     if (id == null)
/* 157:    */     {
/* 158:248 */       if (isTransient(entityName, object, Boolean.FALSE, session)) {
/* 159:249 */         throw new TransientObjectException("object references an unsaved transient instance - save the transient instance before flushing: " + (entityName == null ? session.guessEntityName(object) : entityName));
/* 160:    */       }
/* 161:254 */       id = session.getEntityPersister(entityName, object).getIdentifier(object, session);
/* 162:    */     }
/* 163:256 */     return id;
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.ForeignKeys
 * JD-Core Version:    0.7.0.1
 */