/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import org.hibernate.EntityMode;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.collection.spi.PersistentCollection;
/*   6:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   9:    */ import org.hibernate.event.spi.EventSource;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  12:    */ import org.hibernate.persister.entity.EntityPersister;
/*  13:    */ import org.hibernate.type.CollectionType;
/*  14:    */ import org.hibernate.type.CompositeType;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class WrapVisitor
/*  19:    */   extends ProxyVisitor
/*  20:    */ {
/*  21: 48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, WrapVisitor.class.getName());
/*  22: 50 */   boolean substitute = false;
/*  23:    */   
/*  24:    */   boolean isSubstitutionRequired()
/*  25:    */   {
/*  26: 53 */     return this.substitute;
/*  27:    */   }
/*  28:    */   
/*  29:    */   WrapVisitor(EventSource session)
/*  30:    */   {
/*  31: 57 */     super(session);
/*  32:    */   }
/*  33:    */   
/*  34:    */   Object processCollection(Object collection, CollectionType collectionType)
/*  35:    */     throws HibernateException
/*  36:    */   {
/*  37: 64 */     if ((collection != null) && ((collection instanceof PersistentCollection)))
/*  38:    */     {
/*  39: 66 */       SessionImplementor session = getSession();
/*  40: 67 */       PersistentCollection coll = (PersistentCollection)collection;
/*  41: 68 */       if (coll.setCurrentSession(session)) {
/*  42: 69 */         reattachCollection(coll, collectionType);
/*  43:    */       }
/*  44: 71 */       return null;
/*  45:    */     }
/*  46: 75 */     return processArrayOrNewCollection(collection, collectionType);
/*  47:    */   }
/*  48:    */   
/*  49:    */   final Object processArrayOrNewCollection(Object collection, CollectionType collectionType)
/*  50:    */     throws HibernateException
/*  51:    */   {
/*  52: 83 */     SessionImplementor session = getSession();
/*  53: 85 */     if (collection == null) {
/*  54: 87 */       return null;
/*  55:    */     }
/*  56: 90 */     CollectionPersister persister = session.getFactory().getCollectionPersister(collectionType.getRole());
/*  57:    */     
/*  58: 92 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  59: 94 */     if (collectionType.hasHolder())
/*  60:    */     {
/*  61: 96 */       if (collection == CollectionType.UNFETCHED_COLLECTION) {
/*  62: 96 */         return null;
/*  63:    */       }
/*  64: 98 */       PersistentCollection ah = persistenceContext.getCollectionHolder(collection);
/*  65: 99 */       if (ah == null)
/*  66:    */       {
/*  67:100 */         ah = collectionType.wrap(session, collection);
/*  68:101 */         persistenceContext.addNewCollection(persister, ah);
/*  69:102 */         persistenceContext.addCollectionHolder(ah);
/*  70:    */       }
/*  71:104 */       return null;
/*  72:    */     }
/*  73:108 */     PersistentCollection persistentCollection = collectionType.wrap(session, collection);
/*  74:109 */     persistenceContext.addNewCollection(persister, persistentCollection);
/*  75:111 */     if (LOG.isTraceEnabled()) {
/*  76:112 */       LOG.tracev("Wrapped collection in role: {0}", collectionType.getRole());
/*  77:    */     }
/*  78:115 */     return persistentCollection;
/*  79:    */   }
/*  80:    */   
/*  81:    */   void processValue(int i, Object[] values, Type[] types)
/*  82:    */   {
/*  83:125 */     Object result = processValue(values[i], types[i]);
/*  84:126 */     if (result != null)
/*  85:    */     {
/*  86:127 */       this.substitute = true;
/*  87:128 */       values[i] = result;
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   Object processComponent(Object component, CompositeType componentType)
/*  92:    */     throws HibernateException
/*  93:    */   {
/*  94:136 */     if (component != null)
/*  95:    */     {
/*  96:137 */       Object[] values = componentType.getPropertyValues(component, getSession());
/*  97:138 */       Type[] types = componentType.getSubtypes();
/*  98:139 */       boolean substituteComponent = false;
/*  99:140 */       for (int i = 0; i < types.length; i++)
/* 100:    */       {
/* 101:141 */         Object result = processValue(values[i], types[i]);
/* 102:142 */         if (result != null)
/* 103:    */         {
/* 104:143 */           values[i] = result;
/* 105:144 */           substituteComponent = true;
/* 106:    */         }
/* 107:    */       }
/* 108:147 */       if (substituteComponent) {
/* 109:148 */         componentType.setPropertyValues(component, values, EntityMode.POJO);
/* 110:    */       }
/* 111:    */     }
/* 112:152 */     return null;
/* 113:    */   }
/* 114:    */   
/* 115:    */   void process(Object object, EntityPersister persister)
/* 116:    */     throws HibernateException
/* 117:    */   {
/* 118:157 */     Object[] values = persister.getPropertyValues(object);
/* 119:158 */     Type[] types = persister.getPropertyTypes();
/* 120:159 */     processEntityPropertyValues(values, types);
/* 121:160 */     if (isSubstitutionRequired()) {
/* 122:161 */       persister.setPropertyValues(object, values);
/* 123:    */     }
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.WrapVisitor
 * JD-Core Version:    0.7.0.1
 */