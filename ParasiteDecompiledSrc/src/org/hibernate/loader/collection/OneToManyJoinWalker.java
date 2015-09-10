/*   1:    */ package org.hibernate.loader.collection;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.LockMode;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.cfg.Settings;
/*   9:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.internal.util.StringHelper;
/*  12:    */ import org.hibernate.loader.BasicLoader;
/*  13:    */ import org.hibernate.loader.OuterJoinableAssociation;
/*  14:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  15:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  16:    */ import org.hibernate.sql.JoinFragment;
/*  17:    */ import org.hibernate.sql.Select;
/*  18:    */ 
/*  19:    */ public class OneToManyJoinWalker
/*  20:    */   extends CollectionJoinWalker
/*  21:    */ {
/*  22:    */   private final QueryableCollection oneToManyPersister;
/*  23:    */   
/*  24:    */   protected boolean isDuplicateAssociation(String foreignKeyTable, String[] foreignKeyColumns)
/*  25:    */   {
/*  26: 59 */     boolean isSameJoin = (this.oneToManyPersister.getTableName().equals(foreignKeyTable)) && (Arrays.equals(foreignKeyColumns, this.oneToManyPersister.getKeyColumnNames()));
/*  27:    */     
/*  28: 61 */     return (isSameJoin) || (super.isDuplicateAssociation(foreignKeyTable, foreignKeyColumns));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public OneToManyJoinWalker(QueryableCollection oneToManyPersister, int batchSize, String subquery, SessionFactoryImplementor factory, LoadQueryInfluencers loadQueryInfluencers)
/*  32:    */     throws MappingException
/*  33:    */   {
/*  34: 71 */     super(factory, loadQueryInfluencers);
/*  35:    */     
/*  36: 73 */     this.oneToManyPersister = oneToManyPersister;
/*  37:    */     
/*  38: 75 */     OuterJoinLoadable elementPersister = (OuterJoinLoadable)oneToManyPersister.getElementPersister();
/*  39: 76 */     String alias = generateRootAlias(oneToManyPersister.getRole());
/*  40:    */     
/*  41: 78 */     walkEntityTree(elementPersister, alias);
/*  42:    */     
/*  43: 80 */     List allAssociations = new ArrayList();
/*  44: 81 */     allAssociations.addAll(this.associations);
/*  45: 82 */     allAssociations.add(OuterJoinableAssociation.createRoot(oneToManyPersister.getCollectionType(), alias, getFactory()));
/*  46: 83 */     initPersisters(allAssociations, LockMode.NONE);
/*  47: 84 */     initStatementString(elementPersister, alias, batchSize, subquery);
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void initStatementString(OuterJoinLoadable elementPersister, String alias, int batchSize, String subquery)
/*  51:    */     throws MappingException
/*  52:    */   {
/*  53: 94 */     int joins = countEntityPersisters(this.associations);
/*  54: 95 */     this.suffixes = BasicLoader.generateSuffixes(joins + 1);
/*  55:    */     
/*  56: 97 */     int collectionJoins = countCollectionPersisters(this.associations) + 1;
/*  57: 98 */     this.collectionSuffixes = BasicLoader.generateSuffixes(joins + 1, collectionJoins);
/*  58:    */     
/*  59:100 */     StringBuffer whereString = whereString(alias, this.oneToManyPersister.getKeyColumnNames(), subquery, batchSize);
/*  60:    */     
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:106 */     String filter = this.oneToManyPersister.filterFragment(alias, getLoadQueryInfluencers().getEnabledFilters());
/*  66:107 */     whereString.insert(0, StringHelper.moveAndToBeginning(filter));
/*  67:    */     
/*  68:109 */     JoinFragment ojf = mergeOuterJoins(this.associations);
/*  69:110 */     Select select = new Select(getDialect()).setSelectClause(this.oneToManyPersister.selectFragment(null, null, alias, this.suffixes[joins], this.collectionSuffixes[0], true) + selectString(this.associations)).setFromClause(elementPersister.fromTableFragment(alias) + elementPersister.fromJoinFragment(alias, true, true)).setWhereClause(whereString.toString()).setOuterJoins(ojf.toFromFragmentString(), ojf.toWhereFragmentString() + elementPersister.whereJoinFragment(alias, true, true));
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:126 */     select.setOrderByClause(orderBy(this.associations, this.oneToManyPersister.getSQLOrderByString(alias)));
/*  86:128 */     if (getFactory().getSettings().isCommentsEnabled()) {
/*  87:129 */       select.setComment("load one-to-many " + this.oneToManyPersister.getRole());
/*  88:    */     }
/*  89:132 */     this.sql = select.toStatementString();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String toString()
/*  93:    */   {
/*  94:137 */     return getClass().getName() + '(' + this.oneToManyPersister.getRole() + ')';
/*  95:    */   }
/*  96:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.collection.OneToManyJoinWalker
 * JD-Core Version:    0.7.0.1
 */