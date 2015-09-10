/*   1:    */ package org.hibernate.loader.collection;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.engine.spi.BatchFetchQueue;
/*   7:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   8:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  11:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  12:    */ import org.hibernate.loader.Loader;
/*  13:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  14:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  15:    */ 
/*  16:    */ public class BatchingCollectionInitializer
/*  17:    */   implements CollectionInitializer
/*  18:    */ {
/*  19:    */   private final Loader[] loaders;
/*  20:    */   private final int[] batchSizes;
/*  21:    */   private final CollectionPersister collectionPersister;
/*  22:    */   
/*  23:    */   public BatchingCollectionInitializer(CollectionPersister collPersister, int[] batchSizes, Loader[] loaders)
/*  24:    */   {
/*  25: 52 */     this.loaders = loaders;
/*  26: 53 */     this.batchSizes = batchSizes;
/*  27: 54 */     this.collectionPersister = collPersister;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public CollectionPersister getCollectionPersister()
/*  31:    */   {
/*  32: 58 */     return this.collectionPersister;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Loader[] getLoaders()
/*  36:    */   {
/*  37: 62 */     return this.loaders;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int[] getBatchSizes()
/*  41:    */   {
/*  42: 66 */     return this.batchSizes;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void initialize(Serializable id, SessionImplementor session)
/*  46:    */     throws HibernateException
/*  47:    */   {
/*  48: 72 */     Serializable[] batch = session.getPersistenceContext().getBatchFetchQueue().getCollectionBatch(this.collectionPersister, id, this.batchSizes[0]);
/*  49: 75 */     for (int i = 0; i < this.batchSizes.length - 1; i++)
/*  50:    */     {
/*  51: 76 */       int smallBatchSize = this.batchSizes[i];
/*  52: 77 */       if (batch[(smallBatchSize - 1)] != null)
/*  53:    */       {
/*  54: 78 */         Serializable[] smallBatch = new Serializable[smallBatchSize];
/*  55: 79 */         System.arraycopy(batch, 0, smallBatch, 0, smallBatchSize);
/*  56: 80 */         this.loaders[i].loadCollectionBatch(session, smallBatch, this.collectionPersister.getKeyType());
/*  57: 81 */         return;
/*  58:    */       }
/*  59:    */     }
/*  60: 85 */     this.loaders[(this.batchSizes.length - 1)].loadCollection(session, id, this.collectionPersister.getKeyType());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static CollectionInitializer createBatchingOneToManyInitializer(QueryableCollection persister, int maxBatchSize, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  64:    */     throws MappingException
/*  65:    */   {
/*  66: 94 */     if (maxBatchSize > 1)
/*  67:    */     {
/*  68: 95 */       int[] batchSizesToCreate = ArrayHelper.getBatchSizes(maxBatchSize);
/*  69: 96 */       Loader[] loadersToCreate = new Loader[batchSizesToCreate.length];
/*  70: 97 */       for (int i = 0; i < batchSizesToCreate.length; i++) {
/*  71: 98 */         loadersToCreate[i] = new OneToManyLoader(persister, batchSizesToCreate[i], factory, loadQueryInfluencers);
/*  72:    */       }
/*  73:100 */       return new BatchingCollectionInitializer(persister, batchSizesToCreate, loadersToCreate);
/*  74:    */     }
/*  75:103 */     return new OneToManyLoader(persister, factory, loadQueryInfluencers);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static CollectionInitializer createBatchingCollectionInitializer(QueryableCollection persister, int maxBatchSize, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  79:    */     throws MappingException
/*  80:    */   {
/*  81:112 */     if (maxBatchSize > 1)
/*  82:    */     {
/*  83:113 */       int[] batchSizesToCreate = ArrayHelper.getBatchSizes(maxBatchSize);
/*  84:114 */       Loader[] loadersToCreate = new Loader[batchSizesToCreate.length];
/*  85:115 */       for (int i = 0; i < batchSizesToCreate.length; i++) {
/*  86:116 */         loadersToCreate[i] = new BasicCollectionLoader(persister, batchSizesToCreate[i], factory, loadQueryInfluencers);
/*  87:    */       }
/*  88:118 */       return new BatchingCollectionInitializer(persister, batchSizesToCreate, loadersToCreate);
/*  89:    */     }
/*  90:121 */     return new BasicCollectionLoader(persister, factory, loadQueryInfluencers);
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.collection.BatchingCollectionInitializer
 * JD-Core Version:    0.7.0.1
 */