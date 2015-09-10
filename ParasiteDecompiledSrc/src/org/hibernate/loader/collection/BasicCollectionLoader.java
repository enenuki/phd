/*  1:   */ package org.hibernate.loader.collection;
/*  2:   */ 
/*  3:   */ import org.hibernate.MappingException;
/*  4:   */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  5:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  6:   */ import org.hibernate.internal.CoreMessageLogger;
/*  7:   */ import org.hibernate.loader.JoinWalker;
/*  8:   */ import org.hibernate.persister.collection.QueryableCollection;
/*  9:   */ import org.jboss.logging.Logger;
/* 10:   */ 
/* 11:   */ public class BasicCollectionLoader
/* 12:   */   extends CollectionLoader
/* 13:   */ {
/* 14:46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BasicCollectionLoader.class.getName());
/* 15:   */   
/* 16:   */   public BasicCollectionLoader(QueryableCollection collectionPersister, SessionFactoryImplementor session, LoadQueryInfluencers loadQueryInfluencers)
/* 17:   */     throws MappingException
/* 18:   */   {
/* 19:52 */     this(collectionPersister, 1, session, loadQueryInfluencers);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public BasicCollectionLoader(QueryableCollection collectionPersister, int batchSize, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/* 23:   */     throws MappingException
/* 24:   */   {
/* 25:60 */     this(collectionPersister, batchSize, null, factory, loadQueryInfluencers);
/* 26:   */   }
/* 27:   */   
/* 28:   */   protected BasicCollectionLoader(QueryableCollection collectionPersister, int batchSize, String subquery, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/* 29:   */     throws MappingException
/* 30:   */   {
/* 31:69 */     super(collectionPersister, factory, loadQueryInfluencers);
/* 32:   */     
/* 33:71 */     JoinWalker walker = new BasicCollectionJoinWalker(collectionPersister, batchSize, subquery, factory, loadQueryInfluencers);
/* 34:   */     
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:78 */     initFromWalker(walker);
/* 41:   */     
/* 42:80 */     postInstantiate();
/* 43:82 */     if (LOG.isDebugEnabled()) {
/* 44:83 */       LOG.debugf("Static select for collection %s: %s", collectionPersister.getRole(), getSQLString());
/* 45:   */     }
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.collection.BasicCollectionLoader
 * JD-Core Version:    0.7.0.1
 */