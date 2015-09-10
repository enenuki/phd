/*   1:    */ package org.hibernate.loader.collection;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.FetchMode;
/*   7:    */ import org.hibernate.LockMode;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.cfg.Settings;
/*  10:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  11:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.internal.util.StringHelper;
/*  14:    */ import org.hibernate.loader.BasicLoader;
/*  15:    */ import org.hibernate.loader.OuterJoinableAssociation;
/*  16:    */ import org.hibernate.loader.PropertyPath;
/*  17:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  18:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  19:    */ import org.hibernate.sql.JoinFragment;
/*  20:    */ import org.hibernate.sql.JoinType;
/*  21:    */ import org.hibernate.sql.Select;
/*  22:    */ import org.hibernate.type.AssociationType;
/*  23:    */ 
/*  24:    */ public class BasicCollectionJoinWalker
/*  25:    */   extends CollectionJoinWalker
/*  26:    */ {
/*  27:    */   private final QueryableCollection collectionPersister;
/*  28:    */   
/*  29:    */   public BasicCollectionJoinWalker(QueryableCollection collectionPersister, int batchSize, String subquery, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  30:    */     throws MappingException
/*  31:    */   {
/*  32: 64 */     super(factory, loadQueryInfluencers);
/*  33:    */     
/*  34: 66 */     this.collectionPersister = collectionPersister;
/*  35:    */     
/*  36: 68 */     String alias = generateRootAlias(collectionPersister.getRole());
/*  37:    */     
/*  38: 70 */     walkCollectionTree(collectionPersister, alias);
/*  39:    */     
/*  40: 72 */     List allAssociations = new ArrayList();
/*  41: 73 */     allAssociations.addAll(this.associations);
/*  42: 74 */     allAssociations.add(OuterJoinableAssociation.createRoot(collectionPersister.getCollectionType(), alias, getFactory()));
/*  43: 75 */     initPersisters(allAssociations, LockMode.NONE);
/*  44: 76 */     initStatementString(alias, batchSize, subquery);
/*  45:    */   }
/*  46:    */   
/*  47:    */   private void initStatementString(String alias, int batchSize, String subquery)
/*  48:    */     throws MappingException
/*  49:    */   {
/*  50: 84 */     int joins = countEntityPersisters(this.associations);
/*  51: 85 */     int collectionJoins = countCollectionPersisters(this.associations) + 1;
/*  52:    */     
/*  53: 87 */     this.suffixes = BasicLoader.generateSuffixes(joins);
/*  54: 88 */     this.collectionSuffixes = BasicLoader.generateSuffixes(joins, collectionJoins);
/*  55:    */     
/*  56: 90 */     StringBuffer whereString = whereString(alias, this.collectionPersister.getKeyColumnNames(), subquery, batchSize);
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63: 97 */     String manyToManyOrderBy = "";
/*  64: 98 */     String filter = this.collectionPersister.filterFragment(alias, getLoadQueryInfluencers().getEnabledFilters());
/*  65: 99 */     if (this.collectionPersister.isManyToMany())
/*  66:    */     {
/*  67:105 */       Iterator itr = this.associations.iterator();
/*  68:106 */       AssociationType associationType = (AssociationType)this.collectionPersister.getElementType();
/*  69:107 */       while (itr.hasNext())
/*  70:    */       {
/*  71:108 */         OuterJoinableAssociation oja = (OuterJoinableAssociation)itr.next();
/*  72:109 */         if (oja.getJoinableType() == associationType)
/*  73:    */         {
/*  74:111 */           filter = filter + this.collectionPersister.getManyToManyFilterFragment(oja.getRHSAlias(), getLoadQueryInfluencers().getEnabledFilters());
/*  75:    */           
/*  76:    */ 
/*  77:    */ 
/*  78:115 */           manyToManyOrderBy = manyToManyOrderBy + this.collectionPersister.getManyToManyOrderByString(oja.getRHSAlias());
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82:119 */     whereString.insert(0, StringHelper.moveAndToBeginning(filter));
/*  83:    */     
/*  84:121 */     JoinFragment ojf = mergeOuterJoins(this.associations);
/*  85:122 */     Select select = new Select(getDialect()).setSelectClause(this.collectionPersister.selectFragment(alias, this.collectionSuffixes[0]) + selectString(this.associations)).setFromClause(this.collectionPersister.getTableName(), alias).setWhereClause(whereString.toString()).setOuterJoins(ojf.toFromFragmentString(), ojf.toWhereFragmentString());
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:134 */     select.setOrderByClause(orderBy(this.associations, mergeOrderings(this.collectionPersister.getSQLOrderByString(alias), manyToManyOrderBy)));
/*  98:136 */     if (getFactory().getSettings().isCommentsEnabled()) {
/*  99:137 */       select.setComment("load collection " + this.collectionPersister.getRole());
/* 100:    */     }
/* 101:140 */     this.sql = select.toStatementString();
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected JoinType getJoinType(OuterJoinLoadable persister, PropertyPath path, int propertyNumber, AssociationType associationType, FetchMode metadataFetchMode, CascadeStyle metadataCascadeStyle, String lhsTable, String[] lhsColumns, boolean nullable, int currentDepth)
/* 105:    */     throws MappingException
/* 106:    */   {
/* 107:154 */     JoinType joinType = super.getJoinType(persister, path, propertyNumber, associationType, metadataFetchMode, metadataCascadeStyle, lhsTable, lhsColumns, nullable, currentDepth);
/* 108:167 */     if ((joinType == JoinType.LEFT_OUTER_JOIN) && (path.isRoot())) {
/* 109:168 */       joinType = JoinType.INNER_JOIN;
/* 110:    */     }
/* 111:170 */     return joinType;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String toString()
/* 115:    */   {
/* 116:174 */     return getClass().getName() + '(' + this.collectionPersister.getRole() + ')';
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.collection.BasicCollectionJoinWalker
 * JD-Core Version:    0.7.0.1
 */