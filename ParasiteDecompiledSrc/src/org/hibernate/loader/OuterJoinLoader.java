/*   1:    */ package org.hibernate.loader;
/*   2:    */ 
/*   3:    */ import org.hibernate.LockMode;
/*   4:    */ import org.hibernate.LockOptions;
/*   5:    */ import org.hibernate.dialect.Dialect;
/*   6:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.persister.collection.CollectionPersister;
/*   9:    */ import org.hibernate.persister.entity.Loadable;
/*  10:    */ import org.hibernate.type.EntityType;
/*  11:    */ 
/*  12:    */ public abstract class OuterJoinLoader
/*  13:    */   extends BasicLoader
/*  14:    */ {
/*  15:    */   protected Loadable[] persisters;
/*  16:    */   protected CollectionPersister[] collectionPersisters;
/*  17:    */   protected int[] collectionOwners;
/*  18:    */   protected String[] aliases;
/*  19:    */   private LockOptions lockOptions;
/*  20:    */   protected LockMode[] lockModeArray;
/*  21:    */   protected int[] owners;
/*  22:    */   protected EntityType[] ownerAssociationTypes;
/*  23:    */   protected String sql;
/*  24:    */   protected String[] suffixes;
/*  25:    */   protected String[] collectionSuffixes;
/*  26:    */   private LoadQueryInfluencers loadQueryInfluencers;
/*  27:    */   
/*  28:    */   protected final Dialect getDialect()
/*  29:    */   {
/*  30: 60 */     return getFactory().getDialect();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public OuterJoinLoader(SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  34:    */   {
/*  35: 66 */     super(factory);
/*  36: 67 */     this.loadQueryInfluencers = loadQueryInfluencers;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected String[] getSuffixes()
/*  40:    */   {
/*  41: 71 */     return this.suffixes;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected String[] getCollectionSuffixes()
/*  45:    */   {
/*  46: 75 */     return this.collectionSuffixes;
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected final String getSQLString()
/*  50:    */   {
/*  51: 79 */     return this.sql;
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected final Loadable[] getEntityPersisters()
/*  55:    */   {
/*  56: 83 */     return this.persisters;
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected int[] getOwners()
/*  60:    */   {
/*  61: 87 */     return this.owners;
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected EntityType[] getOwnerAssociationTypes()
/*  65:    */   {
/*  66: 91 */     return this.ownerAssociationTypes;
/*  67:    */   }
/*  68:    */   
/*  69:    */   protected LockMode[] getLockModes(LockOptions lockOptions)
/*  70:    */   {
/*  71: 95 */     return this.lockModeArray;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected LockOptions getLockOptions()
/*  75:    */   {
/*  76: 99 */     return this.lockOptions;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public LoadQueryInfluencers getLoadQueryInfluencers()
/*  80:    */   {
/*  81:103 */     return this.loadQueryInfluencers;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected final String[] getAliases()
/*  85:    */   {
/*  86:107 */     return this.aliases;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected final CollectionPersister[] getCollectionPersisters()
/*  90:    */   {
/*  91:111 */     return this.collectionPersisters;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected final int[] getCollectionOwners()
/*  95:    */   {
/*  96:115 */     return this.collectionOwners;
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected void initFromWalker(JoinWalker walker)
/* 100:    */   {
/* 101:119 */     this.persisters = walker.getPersisters();
/* 102:120 */     this.collectionPersisters = walker.getCollectionPersisters();
/* 103:121 */     this.ownerAssociationTypes = walker.getOwnerAssociationTypes();
/* 104:122 */     this.lockOptions = walker.getLockModeOptions();
/* 105:123 */     this.lockModeArray = walker.getLockModeArray();
/* 106:124 */     this.suffixes = walker.getSuffixes();
/* 107:125 */     this.collectionSuffixes = walker.getCollectionSuffixes();
/* 108:126 */     this.owners = walker.getOwners();
/* 109:127 */     this.collectionOwners = walker.getCollectionOwners();
/* 110:128 */     this.sql = walker.getSQLString();
/* 111:129 */     this.aliases = walker.getAliases();
/* 112:    */   }
/* 113:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.OuterJoinLoader
 * JD-Core Version:    0.7.0.1
 */