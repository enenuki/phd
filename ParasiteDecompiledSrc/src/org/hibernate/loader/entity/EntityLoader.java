/*   1:    */ package org.hibernate.loader.entity;
/*   2:    */ 
/*   3:    */ import org.hibernate.LockMode;
/*   4:    */ import org.hibernate.LockOptions;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   9:    */ import org.hibernate.internal.CoreMessageLogger;
/*  10:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  11:    */ import org.hibernate.type.Type;
/*  12:    */ 
/*  13:    */ public class EntityLoader
/*  14:    */   extends AbstractEntityLoader
/*  15:    */ {
/*  16:    */   private final boolean batchLoader;
/*  17:    */   private final int[][] compositeKeyManyToOneTargetIndices;
/*  18:    */   
/*  19:    */   public EntityLoader(OuterJoinLoadable persister, LockMode lockMode, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  20:    */     throws MappingException
/*  21:    */   {
/*  22: 53 */     this(persister, 1, lockMode, factory, loadQueryInfluencers);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public EntityLoader(OuterJoinLoadable persister, LockOptions lockOptions, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  26:    */     throws MappingException
/*  27:    */   {
/*  28: 61 */     this(persister, 1, lockOptions, factory, loadQueryInfluencers);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public EntityLoader(OuterJoinLoadable persister, int batchSize, LockMode lockMode, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  32:    */     throws MappingException
/*  33:    */   {
/*  34: 70 */     this(persister, persister.getIdentifierColumnNames(), persister.getIdentifierType(), batchSize, lockMode, factory, loadQueryInfluencers);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public EntityLoader(OuterJoinLoadable persister, int batchSize, LockOptions lockOptions, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  38:    */     throws MappingException
/*  39:    */   {
/*  40: 87 */     this(persister, persister.getIdentifierColumnNames(), persister.getIdentifierType(), batchSize, lockOptions, factory, loadQueryInfluencers);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public EntityLoader(OuterJoinLoadable persister, String[] uniqueKey, Type uniqueKeyType, int batchSize, LockMode lockMode, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  44:    */     throws MappingException
/*  45:    */   {
/*  46:106 */     super(persister, uniqueKeyType, factory, loadQueryInfluencers);
/*  47:    */     
/*  48:108 */     EntityJoinWalker walker = new EntityJoinWalker(persister, uniqueKey, batchSize, lockMode, factory, loadQueryInfluencers);
/*  49:    */     
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:116 */     initFromWalker(walker);
/*  57:117 */     this.compositeKeyManyToOneTargetIndices = walker.getCompositeKeyManyToOneTargetIndices();
/*  58:118 */     postInstantiate();
/*  59:    */     
/*  60:120 */     this.batchLoader = (batchSize > 1);
/*  61:122 */     if (LOG.isDebugEnabled()) {
/*  62:123 */       LOG.debugf("Static select for entity %s [%s]: %s", this.entityName, lockMode, getSQLString());
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public EntityLoader(OuterJoinLoadable persister, String[] uniqueKey, Type uniqueKeyType, int batchSize, LockOptions lockOptions, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  67:    */     throws MappingException
/*  68:    */   {
/*  69:135 */     super(persister, uniqueKeyType, factory, loadQueryInfluencers);
/*  70:    */     
/*  71:137 */     EntityJoinWalker walker = new EntityJoinWalker(persister, uniqueKey, batchSize, lockOptions, factory, loadQueryInfluencers);
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:145 */     initFromWalker(walker);
/*  80:146 */     this.compositeKeyManyToOneTargetIndices = walker.getCompositeKeyManyToOneTargetIndices();
/*  81:147 */     postInstantiate();
/*  82:    */     
/*  83:149 */     this.batchLoader = (batchSize > 1);
/*  84:151 */     if (LOG.isDebugEnabled()) {
/*  85:152 */       LOG.debugf("Static select for entity %s [%s:%s]: %s", new Object[] { this.entityName, lockOptions.getLockMode(), Integer.valueOf(lockOptions.getTimeOut()), getSQLString() });
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Object loadByUniqueKey(SessionImplementor session, Object key)
/*  90:    */   {
/*  91:161 */     return load(session, key, null, null, LockOptions.NONE);
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected boolean isSingleRowLoader()
/*  95:    */   {
/*  96:166 */     return !this.batchLoader;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int[][] getCompositeKeyManyToOneTargetIndices()
/* 100:    */   {
/* 101:171 */     return this.compositeKeyManyToOneTargetIndices;
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.entity.EntityLoader
 * JD-Core Version:    0.7.0.1
 */