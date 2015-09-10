/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.EntityMode;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  7:   */ import org.hibernate.engine.spi.TypedValue;
/*  8:   */ import org.hibernate.persister.collection.QueryableCollection;
/*  9:   */ import org.hibernate.persister.entity.Loadable;
/* 10:   */ import org.hibernate.sql.ConditionFragment;
/* 11:   */ import org.hibernate.type.StandardBasicTypes;
/* 12:   */ 
/* 13:   */ public class SizeExpression
/* 14:   */   implements Criterion
/* 15:   */ {
/* 16:   */   private final String propertyName;
/* 17:   */   private final int size;
/* 18:   */   private final String op;
/* 19:   */   
/* 20:   */   protected SizeExpression(String propertyName, int size, String op)
/* 21:   */   {
/* 22:45 */     this.propertyName = propertyName;
/* 23:46 */     this.size = size;
/* 24:47 */     this.op = op;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String toString()
/* 28:   */   {
/* 29:51 */     return this.propertyName + ".size" + this.op + this.size;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 33:   */     throws HibernateException
/* 34:   */   {
/* 35:56 */     String role = criteriaQuery.getEntityName(criteria, this.propertyName) + '.' + criteriaQuery.getPropertyName(this.propertyName);
/* 36:   */     
/* 37:   */ 
/* 38:59 */     QueryableCollection cp = (QueryableCollection)criteriaQuery.getFactory().getCollectionPersister(role);
/* 39:   */     
/* 40:   */ 
/* 41:62 */     String[] fk = cp.getKeyColumnNames();
/* 42:63 */     String[] pk = ((Loadable)cp.getOwnerEntityPersister()).getIdentifierColumnNames();
/* 43:64 */     return "? " + this.op + " (select count(*) from " + cp.getTableName() + " where " + new ConditionFragment().setTableAlias(criteriaQuery.getSQLAlias(criteria, this.propertyName)).setCondition(pk, fk).toFragmentString() + ")";
/* 44:   */   }
/* 45:   */   
/* 46:   */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 47:   */     throws HibernateException
/* 48:   */   {
/* 49:79 */     return new TypedValue[] { new TypedValue(StandardBasicTypes.INTEGER, Integer.valueOf(this.size), EntityMode.POJO) };
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.SizeExpression
 * JD-Core Version:    0.7.0.1
 */