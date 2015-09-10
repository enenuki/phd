/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import org.hibernate.Criteria;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.MappingException;
/*   6:    */ import org.hibernate.QueryException;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.engine.spi.TypedValue;
/*   9:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  10:    */ import org.hibernate.persister.entity.Loadable;
/*  11:    */ import org.hibernate.persister.entity.PropertyMapping;
/*  12:    */ import org.hibernate.sql.ConditionFragment;
/*  13:    */ import org.hibernate.type.CollectionType;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ 
/*  16:    */ public abstract class AbstractEmptinessExpression
/*  17:    */   implements Criterion
/*  18:    */ {
/*  19: 46 */   private static final TypedValue[] NO_VALUES = new TypedValue[0];
/*  20:    */   protected final String propertyName;
/*  21:    */   
/*  22:    */   protected AbstractEmptinessExpression(String propertyName)
/*  23:    */   {
/*  24: 51 */     this.propertyName = propertyName;
/*  25:    */   }
/*  26:    */   
/*  27:    */   protected abstract boolean excludeEmpty();
/*  28:    */   
/*  29:    */   public final String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/*  30:    */     throws HibernateException
/*  31:    */   {
/*  32: 57 */     String entityName = criteriaQuery.getEntityName(criteria, this.propertyName);
/*  33: 58 */     String actualPropertyName = criteriaQuery.getPropertyName(this.propertyName);
/*  34: 59 */     String sqlAlias = criteriaQuery.getSQLAlias(criteria, this.propertyName);
/*  35:    */     
/*  36: 61 */     SessionFactoryImplementor factory = criteriaQuery.getFactory();
/*  37: 62 */     QueryableCollection collectionPersister = getQueryableCollection(entityName, actualPropertyName, factory);
/*  38:    */     
/*  39: 64 */     String[] collectionKeys = collectionPersister.getKeyColumnNames();
/*  40: 65 */     String[] ownerKeys = ((Loadable)factory.getEntityPersister(entityName)).getIdentifierColumnNames();
/*  41:    */     
/*  42: 67 */     String innerSelect = "(select 1 from " + collectionPersister.getTableName() + " where " + new ConditionFragment().setTableAlias(sqlAlias).setCondition(ownerKeys, collectionKeys).toFragmentString() + ")";
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 72 */     return "not exists " + innerSelect;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected QueryableCollection getQueryableCollection(String entityName, String propertyName, SessionFactoryImplementor factory)
/*  51:    */     throws HibernateException
/*  52:    */   {
/*  53: 80 */     PropertyMapping ownerMapping = (PropertyMapping)factory.getEntityPersister(entityName);
/*  54: 81 */     Type type = ownerMapping.toType(propertyName);
/*  55: 82 */     if (!type.isCollectionType()) {
/*  56: 83 */       throw new MappingException("Property path [" + entityName + "." + propertyName + "] does not reference a collection");
/*  57:    */     }
/*  58: 88 */     String role = ((CollectionType)type).getRole();
/*  59:    */     try
/*  60:    */     {
/*  61: 90 */       return (QueryableCollection)factory.getCollectionPersister(role);
/*  62:    */     }
/*  63:    */     catch (ClassCastException cce)
/*  64:    */     {
/*  65: 93 */       throw new QueryException("collection role is not queryable: " + role);
/*  66:    */     }
/*  67:    */     catch (Exception e)
/*  68:    */     {
/*  69: 96 */       throw new QueryException("collection role not found: " + role);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/*  74:    */     throws HibernateException
/*  75:    */   {
/*  76:102 */     return NO_VALUES;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public final String toString()
/*  80:    */   {
/*  81:106 */     return this.propertyName + (excludeEmpty() ? " is not empty" : " is empty");
/*  82:    */   }
/*  83:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.AbstractEmptinessExpression
 * JD-Core Version:    0.7.0.1
 */