/*   1:    */ package org.hibernate.action.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.LinkedHashSet;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.action.spi.AfterTransactionCompletionProcess;
/*  12:    */ import org.hibernate.action.spi.BeforeTransactionCompletionProcess;
/*  13:    */ import org.hibernate.action.spi.Executable;
/*  14:    */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*  15:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  16:    */ import org.hibernate.cache.spi.access.SoftLock;
/*  17:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  18:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  19:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  20:    */ import org.hibernate.persister.entity.EntityPersister;
/*  21:    */ import org.hibernate.persister.entity.Queryable;
/*  22:    */ 
/*  23:    */ public class BulkOperationCleanupAction
/*  24:    */   implements Executable, Serializable
/*  25:    */ {
/*  26:    */   private final Serializable[] affectedTableSpaces;
/*  27: 61 */   private final Set<EntityCleanup> entityCleanups = new HashSet();
/*  28: 62 */   private final Set<CollectionCleanup> collectionCleanups = new HashSet();
/*  29:    */   
/*  30:    */   public BulkOperationCleanupAction(SessionImplementor session, Queryable[] affectedQueryables)
/*  31:    */   {
/*  32: 75 */     SessionFactoryImplementor factory = session.getFactory();
/*  33: 76 */     LinkedHashSet<String> spacesList = new LinkedHashSet();
/*  34: 77 */     for (Queryable persister : affectedQueryables)
/*  35:    */     {
/*  36: 78 */       spacesList.addAll(Arrays.asList((String[])persister.getQuerySpaces()));
/*  37: 80 */       if (persister.hasCache()) {
/*  38: 81 */         this.entityCleanups.add(new EntityCleanup(persister.getCacheAccessStrategy(), null));
/*  39:    */       }
/*  40: 84 */       Set<String> roles = factory.getCollectionRolesByEntityParticipant(persister.getEntityName());
/*  41: 85 */       if (roles != null) {
/*  42: 86 */         for (String role : roles)
/*  43:    */         {
/*  44: 87 */           CollectionPersister collectionPersister = factory.getCollectionPersister(role);
/*  45: 88 */           if (collectionPersister.hasCache()) {
/*  46: 89 */             this.collectionCleanups.add(new CollectionCleanup(collectionPersister.getCacheAccessStrategy(), null));
/*  47:    */           }
/*  48:    */         }
/*  49:    */       }
/*  50:    */     }
/*  51: 95 */     this.affectedTableSpaces = ((Serializable[])spacesList.toArray(new String[spacesList.size()]));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public BulkOperationCleanupAction(SessionImplementor session, Set tableSpaces)
/*  55:    */   {
/*  56:112 */     LinkedHashSet<String> spacesList = new LinkedHashSet();
/*  57:113 */     spacesList.addAll(tableSpaces);
/*  58:    */     
/*  59:115 */     SessionFactoryImplementor factory = session.getFactory();
/*  60:116 */     for (String entityName : factory.getAllClassMetadata().keySet())
/*  61:    */     {
/*  62:117 */       EntityPersister persister = factory.getEntityPersister(entityName);
/*  63:118 */       String[] entitySpaces = (String[])persister.getQuerySpaces();
/*  64:119 */       if (affectedEntity(tableSpaces, entitySpaces))
/*  65:    */       {
/*  66:120 */         spacesList.addAll(Arrays.asList(entitySpaces));
/*  67:122 */         if (persister.hasCache()) {
/*  68:123 */           this.entityCleanups.add(new EntityCleanup(persister.getCacheAccessStrategy(), null));
/*  69:    */         }
/*  70:125 */         Set<String> roles = session.getFactory().getCollectionRolesByEntityParticipant(persister.getEntityName());
/*  71:126 */         if (roles != null) {
/*  72:127 */           for (String role : roles)
/*  73:    */           {
/*  74:128 */             CollectionPersister collectionPersister = factory.getCollectionPersister(role);
/*  75:129 */             if (collectionPersister.hasCache()) {
/*  76:130 */               this.collectionCleanups.add(new CollectionCleanup(collectionPersister.getCacheAccessStrategy(), null));
/*  77:    */             }
/*  78:    */           }
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82:139 */     this.affectedTableSpaces = ((Serializable[])spacesList.toArray(new String[spacesList.size()]));
/*  83:    */   }
/*  84:    */   
/*  85:    */   private boolean affectedEntity(Set affectedTableSpaces, Serializable[] checkTableSpaces)
/*  86:    */   {
/*  87:156 */     if ((affectedTableSpaces == null) || (affectedTableSpaces.isEmpty())) {
/*  88:157 */       return true;
/*  89:    */     }
/*  90:160 */     for (Serializable checkTableSpace : checkTableSpaces) {
/*  91:161 */       if (affectedTableSpaces.contains(checkTableSpace)) {
/*  92:162 */         return true;
/*  93:    */       }
/*  94:    */     }
/*  95:165 */     return false;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Serializable[] getPropertySpaces()
/*  99:    */   {
/* 100:170 */     return this.affectedTableSpaces;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public BeforeTransactionCompletionProcess getBeforeTransactionCompletionProcess()
/* 104:    */   {
/* 105:175 */     return null;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public AfterTransactionCompletionProcess getAfterTransactionCompletionProcess()
/* 109:    */   {
/* 110:180 */     new AfterTransactionCompletionProcess()
/* 111:    */     {
/* 112:    */       public void doAfterTransactionCompletion(boolean success, SessionImplementor session)
/* 113:    */       {
/* 114:183 */         Iterator itr = BulkOperationCleanupAction.this.entityCleanups.iterator();
/* 115:184 */         while (itr.hasNext())
/* 116:    */         {
/* 117:185 */           BulkOperationCleanupAction.EntityCleanup cleanup = (BulkOperationCleanupAction.EntityCleanup)itr.next();
/* 118:186 */           cleanup.release();
/* 119:    */         }
/* 120:189 */         itr = BulkOperationCleanupAction.this.collectionCleanups.iterator();
/* 121:190 */         while (itr.hasNext())
/* 122:    */         {
/* 123:191 */           BulkOperationCleanupAction.CollectionCleanup cleanup = (BulkOperationCleanupAction.CollectionCleanup)itr.next();
/* 124:192 */           cleanup.release();
/* 125:    */         }
/* 126:    */       }
/* 127:    */     };
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void beforeExecutions()
/* 131:    */     throws HibernateException
/* 132:    */   {}
/* 133:    */   
/* 134:    */   public void execute()
/* 135:    */     throws HibernateException
/* 136:    */   {}
/* 137:    */   
/* 138:    */   private static class EntityCleanup
/* 139:    */   {
/* 140:    */     private final EntityRegionAccessStrategy cacheAccess;
/* 141:    */     private final SoftLock cacheLock;
/* 142:    */     
/* 143:    */     private EntityCleanup(EntityRegionAccessStrategy cacheAccess)
/* 144:    */     {
/* 145:213 */       this.cacheAccess = cacheAccess;
/* 146:214 */       this.cacheLock = cacheAccess.lockRegion();
/* 147:215 */       cacheAccess.removeAll();
/* 148:    */     }
/* 149:    */     
/* 150:    */     private void release()
/* 151:    */     {
/* 152:219 */       this.cacheAccess.unlockRegion(this.cacheLock);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private static class CollectionCleanup
/* 157:    */   {
/* 158:    */     private final CollectionRegionAccessStrategy cacheAccess;
/* 159:    */     private final SoftLock cacheLock;
/* 160:    */     
/* 161:    */     private CollectionCleanup(CollectionRegionAccessStrategy cacheAccess)
/* 162:    */     {
/* 163:228 */       this.cacheAccess = cacheAccess;
/* 164:229 */       this.cacheLock = cacheAccess.lockRegion();
/* 165:230 */       cacheAccess.removeAll();
/* 166:    */     }
/* 167:    */     
/* 168:    */     private void release()
/* 169:    */     {
/* 170:234 */       this.cacheAccess.unlockRegion(this.cacheLock);
/* 171:    */     }
/* 172:    */   }
/* 173:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.BulkOperationCleanupAction
 * JD-Core Version:    0.7.0.1
 */