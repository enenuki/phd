/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import org.hibernate.Criteria;
/*   4:    */ import org.hibernate.EntityMode;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.engine.spi.QueryParameters;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   9:    */ import org.hibernate.engine.spi.TypedValue;
/*  10:    */ import org.hibernate.internal.CriteriaImpl;
/*  11:    */ import org.hibernate.internal.CriteriaImpl.Subcriteria;
/*  12:    */ import org.hibernate.loader.criteria.CriteriaJoinWalker;
/*  13:    */ import org.hibernate.loader.criteria.CriteriaQueryTranslator;
/*  14:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ 
/*  17:    */ public abstract class SubqueryExpression
/*  18:    */   implements Criterion
/*  19:    */ {
/*  20:    */   private CriteriaImpl criteriaImpl;
/*  21:    */   private String quantifier;
/*  22:    */   private String op;
/*  23:    */   private QueryParameters params;
/*  24:    */   private Type[] types;
/*  25:    */   private CriteriaQueryTranslator innerQuery;
/*  26:    */   
/*  27:    */   protected Type[] getTypes()
/*  28:    */   {
/*  29: 52 */     return this.types;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected SubqueryExpression(String op, String quantifier, DetachedCriteria dc)
/*  33:    */   {
/*  34: 56 */     this.criteriaImpl = dc.getCriteriaImpl();
/*  35: 57 */     this.quantifier = quantifier;
/*  36: 58 */     this.op = op;
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected abstract String toLeftSqlString(Criteria paramCriteria, CriteriaQuery paramCriteriaQuery);
/*  40:    */   
/*  41:    */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/*  42:    */     throws HibernateException
/*  43:    */   {
/*  44: 64 */     SessionFactoryImplementor factory = criteriaQuery.getFactory();
/*  45: 65 */     OuterJoinLoadable persister = (OuterJoinLoadable)factory.getEntityPersister(this.criteriaImpl.getEntityOrClassName());
/*  46:    */     
/*  47:    */ 
/*  48: 68 */     createAndSetInnerQuery(criteriaQuery, factory);
/*  49: 69 */     this.criteriaImpl.setSession(deriveRootSession(criteria));
/*  50:    */     
/*  51: 71 */     CriteriaJoinWalker walker = new CriteriaJoinWalker(persister, this.innerQuery, factory, this.criteriaImpl, this.criteriaImpl.getEntityOrClassName(), this.criteriaImpl.getSession().getLoadQueryInfluencers(), this.innerQuery.getRootSQLALias());
/*  52:    */     
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61: 81 */     String sql = walker.getSQLString();
/*  62:    */     
/*  63: 83 */     StringBuffer buf = new StringBuffer(toLeftSqlString(criteria, criteriaQuery));
/*  64: 84 */     if (this.op != null) {
/*  65: 85 */       buf.append(' ').append(this.op).append(' ');
/*  66:    */     }
/*  67: 87 */     if (this.quantifier != null) {
/*  68: 88 */       buf.append(this.quantifier).append(' ');
/*  69:    */     }
/*  70: 90 */     return '(' + sql + ')';
/*  71:    */   }
/*  72:    */   
/*  73:    */   private SessionImplementor deriveRootSession(Criteria criteria)
/*  74:    */   {
/*  75: 95 */     if ((criteria instanceof CriteriaImpl)) {
/*  76: 96 */       return ((CriteriaImpl)criteria).getSession();
/*  77:    */     }
/*  78: 98 */     if ((criteria instanceof CriteriaImpl.Subcriteria)) {
/*  79: 99 */       return deriveRootSession(((CriteriaImpl.Subcriteria)criteria).getParent());
/*  80:    */     }
/*  81:104 */     return null;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/*  85:    */     throws HibernateException
/*  86:    */   {
/*  87:112 */     SessionFactoryImplementor factory = criteriaQuery.getFactory();
/*  88:113 */     createAndSetInnerQuery(criteriaQuery, factory);
/*  89:    */     
/*  90:115 */     Type[] ppTypes = this.params.getPositionalParameterTypes();
/*  91:116 */     Object[] ppValues = this.params.getPositionalParameterValues();
/*  92:117 */     TypedValue[] tv = new TypedValue[ppTypes.length];
/*  93:118 */     for (int i = 0; i < ppTypes.length; i++) {
/*  94:119 */       tv[i] = new TypedValue(ppTypes[i], ppValues[i], EntityMode.POJO);
/*  95:    */     }
/*  96:121 */     return tv;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void createAndSetInnerQuery(CriteriaQuery criteriaQuery, SessionFactoryImplementor factory)
/* 100:    */   {
/* 101:131 */     if (this.innerQuery == null)
/* 102:    */     {
/* 103:    */       String alias;
/* 104:    */       String alias;
/* 105:136 */       if (this.criteriaImpl.getAlias() == null) {
/* 106:137 */         alias = criteriaQuery.generateSQLAlias();
/* 107:    */       } else {
/* 108:140 */         alias = this.criteriaImpl.getAlias() + "_";
/* 109:    */       }
/* 110:143 */       this.innerQuery = new CriteriaQueryTranslator(factory, this.criteriaImpl, this.criteriaImpl.getEntityOrClassName(), alias, criteriaQuery);
/* 111:    */       
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:151 */       this.params = this.innerQuery.getQueryParameters();
/* 119:152 */       this.types = this.innerQuery.getProjectedTypes();
/* 120:    */     }
/* 121:    */   }
/* 122:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.SubqueryExpression
 * JD-Core Version:    0.7.0.1
 */