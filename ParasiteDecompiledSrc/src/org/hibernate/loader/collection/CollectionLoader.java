/*  1:   */ package org.hibernate.loader.collection;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.loader.OuterJoinLoader;
/*  9:   */ import org.hibernate.persister.collection.QueryableCollection;
/* 10:   */ import org.hibernate.type.Type;
/* 11:   */ 
/* 12:   */ public class CollectionLoader
/* 13:   */   extends OuterJoinLoader
/* 14:   */   implements CollectionInitializer
/* 15:   */ {
/* 16:   */   private final QueryableCollection collectionPersister;
/* 17:   */   
/* 18:   */   public CollectionLoader(QueryableCollection collectionPersister, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/* 19:   */   {
/* 20:51 */     super(factory, loadQueryInfluencers);
/* 21:52 */     this.collectionPersister = collectionPersister;
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean isSubselectLoadingEnabled()
/* 25:   */   {
/* 26:56 */     return hasSubselectLoadableCollections();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void initialize(Serializable id, SessionImplementor session)
/* 30:   */     throws HibernateException
/* 31:   */   {
/* 32:61 */     loadCollection(session, id, getKeyType());
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected Type getKeyType()
/* 36:   */   {
/* 37:65 */     return this.collectionPersister.getKeyType();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String toString()
/* 41:   */   {
/* 42:69 */     return getClass().getName() + '(' + this.collectionPersister.getRole() + ')';
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.collection.CollectionLoader
 * JD-Core Version:    0.7.0.1
 */