/*   1:    */ package org.hibernate.loader.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.LockMode;
/*   7:    */ import org.hibernate.LockOptions;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.engine.spi.BatchFetchQueue;
/*  10:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  11:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  14:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  15:    */ import org.hibernate.loader.Loader;
/*  16:    */ import org.hibernate.persister.entity.EntityPersister;
/*  17:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  18:    */ import org.hibernate.type.Type;
/*  19:    */ 
/*  20:    */ public class BatchingEntityLoader
/*  21:    */   implements UniqueEntityLoader
/*  22:    */ {
/*  23:    */   private final Loader[] loaders;
/*  24:    */   private final int[] batchSizes;
/*  25:    */   private final EntityPersister persister;
/*  26:    */   private final Type idType;
/*  27:    */   
/*  28:    */   public BatchingEntityLoader(EntityPersister persister, int[] batchSizes, Loader[] loaders)
/*  29:    */   {
/*  30: 58 */     this.batchSizes = batchSizes;
/*  31: 59 */     this.loaders = loaders;
/*  32: 60 */     this.persister = persister;
/*  33: 61 */     this.idType = persister.getIdentifierType();
/*  34:    */   }
/*  35:    */   
/*  36:    */   private Object getObjectFromList(List results, Serializable id, SessionImplementor session)
/*  37:    */   {
/*  38: 66 */     Iterator iter = results.iterator();
/*  39: 67 */     while (iter.hasNext())
/*  40:    */     {
/*  41: 68 */       Object obj = iter.next();
/*  42: 69 */       boolean equal = this.idType.isEqual(id, session.getContextEntityIdentifier(obj), session.getFactory());
/*  43: 74 */       if (equal) {
/*  44: 74 */         return obj;
/*  45:    */       }
/*  46:    */     }
/*  47: 76 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Object load(Serializable id, Object optionalObject, SessionImplementor session)
/*  51:    */   {
/*  52: 84 */     return load(id, optionalObject, session, LockOptions.NONE);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object load(Serializable id, Object optionalObject, SessionImplementor session, LockOptions lockOptions)
/*  56:    */   {
/*  57: 88 */     Serializable[] batch = session.getPersistenceContext().getBatchFetchQueue().getEntityBatch(this.persister, id, this.batchSizes[0], this.persister.getEntityMode());
/*  58: 92 */     for (int i = 0; i < this.batchSizes.length - 1; i++)
/*  59:    */     {
/*  60: 93 */       int smallBatchSize = this.batchSizes[i];
/*  61: 94 */       if (batch[(smallBatchSize - 1)] != null)
/*  62:    */       {
/*  63: 95 */         Serializable[] smallBatch = new Serializable[smallBatchSize];
/*  64: 96 */         System.arraycopy(batch, 0, smallBatch, 0, smallBatchSize);
/*  65: 97 */         List results = this.loaders[i].loadEntityBatch(session, smallBatch, this.idType, optionalObject, this.persister.getEntityName(), id, this.persister, lockOptions);
/*  66:    */         
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:107 */         return getObjectFromList(results, id, session);
/*  76:    */       }
/*  77:    */     }
/*  78:111 */     return ((UniqueEntityLoader)this.loaders[(this.batchSizes.length - 1)]).load(id, optionalObject, session);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static UniqueEntityLoader createBatchingEntityLoader(OuterJoinLoadable persister, int maxBatchSize, LockMode lockMode, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  82:    */     throws MappingException
/*  83:    */   {
/*  84:122 */     if (maxBatchSize > 1)
/*  85:    */     {
/*  86:123 */       int[] batchSizesToCreate = ArrayHelper.getBatchSizes(maxBatchSize);
/*  87:124 */       Loader[] loadersToCreate = new Loader[batchSizesToCreate.length];
/*  88:125 */       for (int i = 0; i < batchSizesToCreate.length; i++) {
/*  89:126 */         loadersToCreate[i] = new EntityLoader(persister, batchSizesToCreate[i], lockMode, factory, loadQueryInfluencers);
/*  90:    */       }
/*  91:128 */       return new BatchingEntityLoader(persister, batchSizesToCreate, loadersToCreate);
/*  92:    */     }
/*  93:131 */     return new EntityLoader(persister, lockMode, factory, loadQueryInfluencers);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static UniqueEntityLoader createBatchingEntityLoader(OuterJoinLoadable persister, int maxBatchSize, LockOptions lockOptions, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  97:    */     throws MappingException
/*  98:    */   {
/*  99:142 */     if (maxBatchSize > 1)
/* 100:    */     {
/* 101:143 */       int[] batchSizesToCreate = ArrayHelper.getBatchSizes(maxBatchSize);
/* 102:144 */       Loader[] loadersToCreate = new Loader[batchSizesToCreate.length];
/* 103:145 */       for (int i = 0; i < batchSizesToCreate.length; i++) {
/* 104:146 */         loadersToCreate[i] = new EntityLoader(persister, batchSizesToCreate[i], lockOptions, factory, loadQueryInfluencers);
/* 105:    */       }
/* 106:148 */       return new BatchingEntityLoader(persister, batchSizesToCreate, loadersToCreate);
/* 107:    */     }
/* 108:151 */     return new EntityLoader(persister, lockOptions, factory, loadQueryInfluencers);
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.entity.BatchingEntityLoader
 * JD-Core Version:    0.7.0.1
 */