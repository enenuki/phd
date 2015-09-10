/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.cfg.Settings;
/*   7:    */ import org.hibernate.collection.spi.PersistentCollection;
/*   8:    */ import org.hibernate.engine.spi.CollectionEntry;
/*   9:    */ import org.hibernate.engine.spi.EntityEntry;
/*  10:    */ import org.hibernate.engine.spi.EntityKey;
/*  11:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  14:    */ import org.hibernate.engine.spi.Status;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  17:    */ import org.hibernate.persister.entity.EntityPersister;
/*  18:    */ import org.hibernate.pretty.MessageHelper;
/*  19:    */ import org.hibernate.type.CollectionType;
/*  20:    */ import org.hibernate.type.Type;
/*  21:    */ import org.jboss.logging.Logger;
/*  22:    */ 
/*  23:    */ public final class Collections
/*  24:    */ {
/*  25: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Collections.class.getName());
/*  26:    */   
/*  27:    */   public static void processUnreachableCollection(PersistentCollection coll, SessionImplementor session)
/*  28:    */   {
/*  29: 63 */     if (coll.getOwner() == null) {
/*  30: 64 */       processNeverReferencedCollection(coll, session);
/*  31:    */     } else {
/*  32: 67 */       processDereferencedCollection(coll, session);
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   private static void processDereferencedCollection(PersistentCollection coll, SessionImplementor session)
/*  37:    */   {
/*  38: 72 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  39: 73 */     CollectionEntry entry = persistenceContext.getCollectionEntry(coll);
/*  40: 74 */     CollectionPersister loadedPersister = entry.getLoadedPersister();
/*  41: 76 */     if ((LOG.isDebugEnabled()) && (loadedPersister != null)) {
/*  42: 77 */       LOG.debugf("Collection dereferenced: %s", MessageHelper.collectionInfoString(loadedPersister, entry.getLoadedKey(), session.getFactory()));
/*  43:    */     }
/*  44: 88 */     boolean hasOrphanDelete = (loadedPersister != null) && (loadedPersister.hasOrphanDelete());
/*  45: 89 */     if (hasOrphanDelete)
/*  46:    */     {
/*  47: 90 */       Serializable ownerId = loadedPersister.getOwnerEntityPersister().getIdentifier(coll.getOwner(), session);
/*  48: 91 */       if (ownerId == null)
/*  49:    */       {
/*  50: 95 */         if (session.getFactory().getSettings().isIdentifierRollbackEnabled())
/*  51:    */         {
/*  52: 96 */           EntityEntry ownerEntry = persistenceContext.getEntry(coll.getOwner());
/*  53: 97 */           if (ownerEntry != null) {
/*  54: 98 */             ownerId = ownerEntry.getId();
/*  55:    */           }
/*  56:    */         }
/*  57:101 */         if (ownerId == null) {
/*  58:102 */           throw new AssertionFailure("Unable to determine collection owner identifier for orphan-delete processing");
/*  59:    */         }
/*  60:    */       }
/*  61:105 */       EntityKey key = session.generateEntityKey(ownerId, loadedPersister.getOwnerEntityPersister());
/*  62:106 */       Object owner = persistenceContext.getEntity(key);
/*  63:107 */       if (owner == null) {
/*  64:108 */         throw new AssertionFailure("collection owner not associated with session: " + loadedPersister.getRole());
/*  65:    */       }
/*  66:113 */       EntityEntry e = persistenceContext.getEntry(owner);
/*  67:115 */       if ((e != null) && (e.getStatus() != Status.DELETED) && (e.getStatus() != Status.GONE)) {
/*  68:116 */         throw new HibernateException("A collection with cascade=\"all-delete-orphan\" was no longer referenced by the owning entity instance: " + loadedPersister.getRole());
/*  69:    */       }
/*  70:    */     }
/*  71:124 */     entry.setCurrentPersister(null);
/*  72:125 */     entry.setCurrentKey(null);
/*  73:126 */     prepareCollectionForUpdate(coll, entry, session.getFactory());
/*  74:    */   }
/*  75:    */   
/*  76:    */   private static void processNeverReferencedCollection(PersistentCollection coll, SessionImplementor session)
/*  77:    */     throws HibernateException
/*  78:    */   {
/*  79:133 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  80:134 */     CollectionEntry entry = persistenceContext.getCollectionEntry(coll);
/*  81:136 */     if (LOG.isDebugEnabled()) {
/*  82:137 */       LOG.debugf("Found collection with unloaded owner: %s", MessageHelper.collectionInfoString(entry.getLoadedPersister(), entry.getLoadedKey(), session.getFactory()));
/*  83:    */     }
/*  84:141 */     entry.setCurrentPersister(entry.getLoadedPersister());
/*  85:142 */     entry.setCurrentKey(entry.getLoadedKey());
/*  86:    */     
/*  87:144 */     prepareCollectionForUpdate(coll, entry, session.getFactory());
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void processReachableCollection(PersistentCollection collection, CollectionType type, Object entity, SessionImplementor session)
/*  91:    */   {
/*  92:162 */     collection.setOwner(entity);
/*  93:    */     
/*  94:164 */     CollectionEntry ce = session.getPersistenceContext().getCollectionEntry(collection);
/*  95:166 */     if (ce == null) {
/*  96:168 */       throw new HibernateException("Found two representations of same collection: " + type.getRole());
/*  97:    */     }
/*  98:176 */     if (ce.isReached()) {
/*  99:178 */       throw new HibernateException("Found shared references to a collection: " + type.getRole());
/* 100:    */     }
/* 101:183 */     ce.setReached(true);
/* 102:    */     
/* 103:185 */     SessionFactoryImplementor factory = session.getFactory();
/* 104:186 */     CollectionPersister persister = factory.getCollectionPersister(type.getRole());
/* 105:187 */     ce.setCurrentPersister(persister);
/* 106:188 */     ce.setCurrentKey(type.getKeyOfOwner(entity, session));
/* 107:190 */     if (LOG.isDebugEnabled()) {
/* 108:191 */       if (collection.wasInitialized()) {
/* 109:191 */         LOG.debugf("Collection found: %s, was: %s (initialized)", MessageHelper.collectionInfoString(persister, ce.getCurrentKey(), factory), MessageHelper.collectionInfoString(ce.getLoadedPersister(), ce.getLoadedKey(), factory));
/* 110:    */       } else {
/* 111:196 */         LOG.debugf("Collection found: %s, was: %s (uninitialized)", MessageHelper.collectionInfoString(persister, ce.getCurrentKey(), factory), MessageHelper.collectionInfoString(ce.getLoadedPersister(), ce.getLoadedKey(), factory));
/* 112:    */       }
/* 113:    */     }
/* 114:201 */     prepareCollectionForUpdate(collection, ce, factory);
/* 115:    */   }
/* 116:    */   
/* 117:    */   private static void prepareCollectionForUpdate(PersistentCollection collection, CollectionEntry entry, SessionFactoryImplementor factory)
/* 118:    */   {
/* 119:216 */     if (entry.isProcessed()) {
/* 120:217 */       throw new AssertionFailure("collection was processed twice by flush()");
/* 121:    */     }
/* 122:219 */     entry.setProcessed(true);
/* 123:    */     
/* 124:221 */     CollectionPersister loadedPersister = entry.getLoadedPersister();
/* 125:222 */     CollectionPersister currentPersister = entry.getCurrentPersister();
/* 126:223 */     if ((loadedPersister != null) || (currentPersister != null))
/* 127:    */     {
/* 128:225 */       boolean ownerChanged = (loadedPersister != currentPersister) || (!currentPersister.getKeyType().isEqual(entry.getLoadedKey(), entry.getCurrentKey(), factory));
/* 129:233 */       if (ownerChanged)
/* 130:    */       {
/* 131:236 */         boolean orphanDeleteAndRoleChanged = (loadedPersister != null) && (currentPersister != null) && (loadedPersister.hasOrphanDelete());
/* 132:240 */         if (orphanDeleteAndRoleChanged) {
/* 133:241 */           throw new HibernateException("Don't change the reference to a collection with cascade=\"all-delete-orphan\": " + loadedPersister.getRole());
/* 134:    */         }
/* 135:248 */         if (currentPersister != null) {
/* 136:249 */           entry.setDorecreate(true);
/* 137:    */         }
/* 138:252 */         if (loadedPersister != null)
/* 139:    */         {
/* 140:253 */           entry.setDoremove(true);
/* 141:254 */           if (entry.isDorecreate())
/* 142:    */           {
/* 143:255 */             LOG.trace("Forcing collection initialization");
/* 144:256 */             collection.forceInitialization();
/* 145:    */           }
/* 146:    */         }
/* 147:    */       }
/* 148:260 */       else if (collection.isDirty())
/* 149:    */       {
/* 150:262 */         entry.setDoupdate(true);
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.Collections
 * JD-Core Version:    0.7.0.1
 */