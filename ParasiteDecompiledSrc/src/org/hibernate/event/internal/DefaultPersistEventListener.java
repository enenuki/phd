/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.util.IdentityHashMap;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.ObjectDeletedException;
/*   7:    */ import org.hibernate.PersistentObjectException;
/*   8:    */ import org.hibernate.engine.spi.CascadingAction;
/*   9:    */ import org.hibernate.engine.spi.EntityEntry;
/*  10:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  11:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.event.spi.EventSource;
/*  14:    */ import org.hibernate.event.spi.PersistEvent;
/*  15:    */ import org.hibernate.event.spi.PersistEventListener;
/*  16:    */ import org.hibernate.id.ForeignGenerator;
/*  17:    */ import org.hibernate.internal.CoreMessageLogger;
/*  18:    */ import org.hibernate.persister.entity.EntityPersister;
/*  19:    */ import org.hibernate.proxy.HibernateProxy;
/*  20:    */ import org.hibernate.proxy.LazyInitializer;
/*  21:    */ import org.jboss.logging.Logger;
/*  22:    */ 
/*  23:    */ public class DefaultPersistEventListener
/*  24:    */   extends AbstractSaveEventListener
/*  25:    */   implements PersistEventListener
/*  26:    */ {
/*  27: 55 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultPersistEventListener.class.getName());
/*  28:    */   
/*  29:    */   public void onPersist(PersistEvent event)
/*  30:    */     throws HibernateException
/*  31:    */   {
/*  32: 65 */     onPersist(event, new IdentityHashMap(10));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void onPersist(PersistEvent event, Map createCache)
/*  36:    */     throws HibernateException
/*  37:    */   {
/*  38: 76 */     SessionImplementor source = event.getSession();
/*  39: 77 */     Object object = event.getObject();
/*  40:    */     Object entity;
/*  41:    */     Object entity;
/*  42: 80 */     if ((object instanceof HibernateProxy))
/*  43:    */     {
/*  44: 81 */       LazyInitializer li = ((HibernateProxy)object).getHibernateLazyInitializer();
/*  45: 82 */       if (li.isUninitialized())
/*  46:    */       {
/*  47: 83 */         if (li.getSession() == source) {
/*  48: 84 */           return;
/*  49:    */         }
/*  50: 87 */         throw new PersistentObjectException("uninitialized proxy passed to persist()");
/*  51:    */       }
/*  52: 90 */       entity = li.getImplementation();
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 93 */       entity = object;
/*  57:    */     }
/*  58:    */     String entityName;
/*  59:    */     String entityName;
/*  60: 97 */     if (event.getEntityName() != null)
/*  61:    */     {
/*  62: 98 */       entityName = event.getEntityName();
/*  63:    */     }
/*  64:    */     else
/*  65:    */     {
/*  66:101 */       entityName = source.bestGuessEntityName(entity);
/*  67:102 */       event.setEntityName(entityName);
/*  68:    */     }
/*  69:105 */     EntityEntry entityEntry = source.getPersistenceContext().getEntry(entity);
/*  70:106 */     AbstractSaveEventListener.EntityState entityState = getEntityState(entity, entityName, entityEntry, source);
/*  71:107 */     if (entityState == AbstractSaveEventListener.EntityState.DETACHED)
/*  72:    */     {
/*  73:117 */       EntityPersister persister = source.getFactory().getEntityPersister(entityName);
/*  74:118 */       if (ForeignGenerator.class.isInstance(persister.getIdentifierGenerator()))
/*  75:    */       {
/*  76:119 */         if ((LOG.isDebugEnabled()) && (persister.getIdentifier(entity, source) != null)) {
/*  77:120 */           LOG.debug("Resetting entity id attribute to null for foreign generator");
/*  78:    */         }
/*  79:122 */         persister.setIdentifier(entity, null, source);
/*  80:123 */         entityState = getEntityState(entity, entityName, entityEntry, source);
/*  81:    */       }
/*  82:    */     }
/*  83:127 */     switch (1.$SwitchMap$org$hibernate$event$internal$AbstractSaveEventListener$EntityState[entityState.ordinal()])
/*  84:    */     {
/*  85:    */     case 1: 
/*  86:129 */       throw new PersistentObjectException("detached entity passed to persist: " + getLoggableName(event.getEntityName(), entity));
/*  87:    */     case 2: 
/*  88:134 */       entityIsPersistent(event, createCache);
/*  89:135 */       break;
/*  90:    */     case 3: 
/*  91:137 */       entityIsTransient(event, createCache);
/*  92:138 */       break;
/*  93:    */     default: 
/*  94:140 */       throw new ObjectDeletedException("deleted entity passed to persist", null, getLoggableName(event.getEntityName(), entity));
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected void entityIsPersistent(PersistEvent event, Map createCache)
/*  99:    */   {
/* 100:150 */     LOG.trace("Ignoring persistent instance");
/* 101:151 */     EventSource source = event.getSession();
/* 102:    */     
/* 103:    */ 
/* 104:    */ 
/* 105:155 */     Object entity = source.getPersistenceContext().unproxy(event.getObject());
/* 106:156 */     EntityPersister persister = source.getEntityPersister(event.getEntityName(), entity);
/* 107:158 */     if (createCache.put(entity, entity) == null)
/* 108:    */     {
/* 109:160 */       cascadeBeforeSave(source, persister, entity, createCache);
/* 110:161 */       cascadeAfterSave(source, persister, entity, createCache);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected void entityIsTransient(PersistEvent event, Map createCache)
/* 115:    */     throws HibernateException
/* 116:    */   {
/* 117:174 */     LOG.trace("Saving transient instance");
/* 118:    */     
/* 119:176 */     EventSource source = event.getSession();
/* 120:    */     
/* 121:178 */     Object entity = source.getPersistenceContext().unproxy(event.getObject());
/* 122:180 */     if (createCache.put(entity, entity) == null) {
/* 123:181 */       saveWithGeneratedId(entity, event.getEntityName(), createCache, source, false);
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected CascadingAction getCascadeAction()
/* 128:    */   {
/* 129:188 */     return CascadingAction.PERSIST;
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected Boolean getAssumedUnsaved()
/* 133:    */   {
/* 134:193 */     return Boolean.TRUE;
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultPersistEventListener
 * JD-Core Version:    0.7.0.1
 */