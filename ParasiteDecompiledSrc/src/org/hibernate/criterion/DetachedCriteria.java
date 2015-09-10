/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.Criteria;
/*   5:    */ import org.hibernate.FetchMode;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.LockMode;
/*   8:    */ import org.hibernate.Session;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ import org.hibernate.internal.CriteriaImpl;
/*  11:    */ import org.hibernate.sql.JoinType;
/*  12:    */ import org.hibernate.transform.ResultTransformer;
/*  13:    */ 
/*  14:    */ public class DetachedCriteria
/*  15:    */   implements CriteriaSpecification, Serializable
/*  16:    */ {
/*  17:    */   private final CriteriaImpl impl;
/*  18:    */   private final Criteria criteria;
/*  19:    */   
/*  20:    */   protected DetachedCriteria(String entityName)
/*  21:    */   {
/*  22: 56 */     this.impl = new CriteriaImpl(entityName, null);
/*  23: 57 */     this.criteria = this.impl;
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected DetachedCriteria(String entityName, String alias)
/*  27:    */   {
/*  28: 61 */     this.impl = new CriteriaImpl(entityName, alias, null);
/*  29: 62 */     this.criteria = this.impl;
/*  30:    */   }
/*  31:    */   
/*  32:    */   protected DetachedCriteria(CriteriaImpl impl, Criteria criteria)
/*  33:    */   {
/*  34: 66 */     this.impl = impl;
/*  35: 67 */     this.criteria = criteria;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Criteria getExecutableCriteria(Session session)
/*  39:    */   {
/*  40: 75 */     this.impl.setSession((SessionImplementor)session);
/*  41: 76 */     return this.impl;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static DetachedCriteria forEntityName(String entityName)
/*  45:    */   {
/*  46: 80 */     return new DetachedCriteria(entityName);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static DetachedCriteria forEntityName(String entityName, String alias)
/*  50:    */   {
/*  51: 84 */     return new DetachedCriteria(entityName, alias);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static DetachedCriteria forClass(Class clazz)
/*  55:    */   {
/*  56: 88 */     return new DetachedCriteria(clazz.getName());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static DetachedCriteria forClass(Class clazz, String alias)
/*  60:    */   {
/*  61: 92 */     return new DetachedCriteria(clazz.getName(), alias);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public DetachedCriteria add(Criterion criterion)
/*  65:    */   {
/*  66: 96 */     this.criteria.add(criterion);
/*  67: 97 */     return this;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public DetachedCriteria addOrder(Order order)
/*  71:    */   {
/*  72:101 */     this.criteria.addOrder(order);
/*  73:102 */     return this;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public DetachedCriteria createAlias(String associationPath, String alias)
/*  77:    */     throws HibernateException
/*  78:    */   {
/*  79:107 */     this.criteria.createAlias(associationPath, alias);
/*  80:108 */     return this;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public DetachedCriteria createCriteria(String associationPath, String alias)
/*  84:    */     throws HibernateException
/*  85:    */   {
/*  86:113 */     return new DetachedCriteria(this.impl, this.criteria.createCriteria(associationPath, alias));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public DetachedCriteria createCriteria(String associationPath)
/*  90:    */     throws HibernateException
/*  91:    */   {
/*  92:118 */     return new DetachedCriteria(this.impl, this.criteria.createCriteria(associationPath));
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getAlias()
/*  96:    */   {
/*  97:122 */     return this.criteria.getAlias();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public DetachedCriteria setFetchMode(String associationPath, FetchMode mode)
/* 101:    */     throws HibernateException
/* 102:    */   {
/* 103:127 */     this.criteria.setFetchMode(associationPath, mode);
/* 104:128 */     return this;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public DetachedCriteria setProjection(Projection projection)
/* 108:    */   {
/* 109:132 */     this.criteria.setProjection(projection);
/* 110:133 */     return this;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public DetachedCriteria setResultTransformer(ResultTransformer resultTransformer)
/* 114:    */   {
/* 115:137 */     this.criteria.setResultTransformer(resultTransformer);
/* 116:138 */     return this;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String toString()
/* 120:    */   {
/* 121:142 */     return "DetachableCriteria(" + this.criteria.toString() + ')';
/* 122:    */   }
/* 123:    */   
/* 124:    */   CriteriaImpl getCriteriaImpl()
/* 125:    */   {
/* 126:146 */     return this.impl;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public DetachedCriteria createAlias(String associationPath, String alias, JoinType joinType)
/* 130:    */     throws HibernateException
/* 131:    */   {
/* 132:150 */     this.criteria.createAlias(associationPath, alias, joinType);
/* 133:151 */     return this;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public DetachedCriteria createAlias(String associationPath, String alias, JoinType joinType, Criterion withClause)
/* 137:    */     throws HibernateException
/* 138:    */   {
/* 139:155 */     this.criteria.createAlias(associationPath, alias, joinType, withClause);
/* 140:156 */     return this;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public DetachedCriteria createCriteria(String associationPath, JoinType joinType)
/* 144:    */     throws HibernateException
/* 145:    */   {
/* 146:160 */     return new DetachedCriteria(this.impl, this.criteria.createCriteria(associationPath, joinType));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public DetachedCriteria createCriteria(String associationPath, String alias, JoinType joinType)
/* 150:    */     throws HibernateException
/* 151:    */   {
/* 152:164 */     return new DetachedCriteria(this.impl, this.criteria.createCriteria(associationPath, alias, joinType));
/* 153:    */   }
/* 154:    */   
/* 155:    */   public DetachedCriteria createCriteria(String associationPath, String alias, JoinType joinType, Criterion withClause)
/* 156:    */     throws HibernateException
/* 157:    */   {
/* 158:168 */     return new DetachedCriteria(this.impl, this.criteria.createCriteria(associationPath, alias, joinType, withClause));
/* 159:    */   }
/* 160:    */   
/* 161:    */   @Deprecated
/* 162:    */   public DetachedCriteria createAlias(String associationPath, String alias, int joinType)
/* 163:    */     throws HibernateException
/* 164:    */   {
/* 165:176 */     return createAlias(associationPath, alias, JoinType.parse(joinType));
/* 166:    */   }
/* 167:    */   
/* 168:    */   @Deprecated
/* 169:    */   public DetachedCriteria createAlias(String associationPath, String alias, int joinType, Criterion withClause)
/* 170:    */     throws HibernateException
/* 171:    */   {
/* 172:183 */     return createAlias(associationPath, alias, JoinType.parse(joinType), withClause);
/* 173:    */   }
/* 174:    */   
/* 175:    */   @Deprecated
/* 176:    */   public DetachedCriteria createCriteria(String associationPath, int joinType)
/* 177:    */     throws HibernateException
/* 178:    */   {
/* 179:190 */     return createCriteria(associationPath, JoinType.parse(joinType));
/* 180:    */   }
/* 181:    */   
/* 182:    */   @Deprecated
/* 183:    */   public DetachedCriteria createCriteria(String associationPath, String alias, int joinType)
/* 184:    */     throws HibernateException
/* 185:    */   {
/* 186:197 */     return createCriteria(associationPath, alias, JoinType.parse(joinType));
/* 187:    */   }
/* 188:    */   
/* 189:    */   @Deprecated
/* 190:    */   public DetachedCriteria createCriteria(String associationPath, String alias, int joinType, Criterion withClause)
/* 191:    */     throws HibernateException
/* 192:    */   {
/* 193:204 */     return createCriteria(associationPath, alias, JoinType.parse(joinType), withClause);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public DetachedCriteria setComment(String comment)
/* 197:    */   {
/* 198:208 */     this.criteria.setComment(comment);
/* 199:209 */     return this;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public DetachedCriteria setLockMode(LockMode lockMode)
/* 203:    */   {
/* 204:213 */     this.criteria.setLockMode(lockMode);
/* 205:214 */     return this;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public DetachedCriteria setLockMode(String alias, LockMode lockMode)
/* 209:    */   {
/* 210:218 */     this.criteria.setLockMode(alias, lockMode);
/* 211:219 */     return this;
/* 212:    */   }
/* 213:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.DetachedCriteria
 * JD-Core Version:    0.7.0.1
 */