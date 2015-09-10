/*   1:    */ package org.hibernate.loader.entity;
/*   2:    */ 
/*   3:    */ import java.sql.ResultSet;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.LockMode;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  14:    */ import org.hibernate.loader.JoinWalker;
/*  15:    */ import org.hibernate.loader.OuterJoinLoader;
/*  16:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  17:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  18:    */ import org.hibernate.transform.ResultTransformer;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ import org.jboss.logging.Logger;
/*  21:    */ 
/*  22:    */ public class CollectionElementLoader
/*  23:    */   extends OuterJoinLoader
/*  24:    */ {
/*  25: 54 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, CollectionElementLoader.class.getName());
/*  26:    */   private final OuterJoinLoadable persister;
/*  27:    */   private final Type keyType;
/*  28:    */   private final Type indexType;
/*  29:    */   private final String entityName;
/*  30:    */   
/*  31:    */   public CollectionElementLoader(QueryableCollection collectionPersister, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  32:    */     throws MappingException
/*  33:    */   {
/*  34: 65 */     super(factory, loadQueryInfluencers);
/*  35:    */     
/*  36: 67 */     this.keyType = collectionPersister.getKeyType();
/*  37: 68 */     this.indexType = collectionPersister.getIndexType();
/*  38: 69 */     this.persister = ((OuterJoinLoadable)collectionPersister.getElementPersister());
/*  39: 70 */     this.entityName = this.persister.getEntityName();
/*  40:    */     
/*  41: 72 */     JoinWalker walker = new EntityJoinWalker(this.persister, ArrayHelper.join(collectionPersister.getKeyColumnNames(), collectionPersister.getIndexColumnNames()), 1, LockMode.NONE, factory, loadQueryInfluencers);
/*  42:    */     
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52: 83 */     initFromWalker(walker);
/*  53:    */     
/*  54: 85 */     postInstantiate();
/*  55: 87 */     if (LOG.isDebugEnabled()) {
/*  56: 88 */       LOG.debugf("Static select for entity %s: %s", this.entityName, getSQLString());
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Object loadElement(SessionImplementor session, Object key, Object index)
/*  61:    */     throws HibernateException
/*  62:    */   {
/*  63: 96 */     List list = loadEntity(session, key, index, this.keyType, this.indexType, this.persister);
/*  64:105 */     if (list.size() == 1) {
/*  65:106 */       return list.get(0);
/*  66:    */     }
/*  67:108 */     if (list.size() == 0) {
/*  68:109 */       return null;
/*  69:    */     }
/*  70:112 */     if (getCollectionOwners() != null) {
/*  71:113 */       return list.get(0);
/*  72:    */     }
/*  73:116 */     throw new HibernateException("More than one row was found");
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected Object getResultColumnOrRow(Object[] row, ResultTransformer transformer, ResultSet rs, SessionImplementor session)
/*  77:    */     throws SQLException, HibernateException
/*  78:    */   {
/*  79:128 */     return row[(row.length - 1)];
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected boolean isSingleRowLoader()
/*  83:    */   {
/*  84:133 */     return true;
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.entity.CollectionElementLoader
 * JD-Core Version:    0.7.0.1
 */