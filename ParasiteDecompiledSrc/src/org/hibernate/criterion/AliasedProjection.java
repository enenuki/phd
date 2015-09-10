/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class AliasedProjection
/*  8:   */   implements EnhancedProjection
/*  9:   */ {
/* 10:   */   private final Projection projection;
/* 11:   */   private final String alias;
/* 12:   */   
/* 13:   */   public String toString()
/* 14:   */   {
/* 15:39 */     return this.projection.toString() + " as " + this.alias;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected AliasedProjection(Projection projection, String alias)
/* 19:   */   {
/* 20:43 */     this.projection = projection;
/* 21:44 */     this.alias = alias;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery)
/* 25:   */     throws HibernateException
/* 26:   */   {
/* 27:49 */     return this.projection.toSqlString(criteria, position, criteriaQuery);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 31:   */     throws HibernateException
/* 32:   */   {
/* 33:54 */     return this.projection.toGroupSqlString(criteria, criteriaQuery);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
/* 37:   */     throws HibernateException
/* 38:   */   {
/* 39:59 */     return this.projection.getTypes(criteria, criteriaQuery);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String[] getColumnAliases(int loc)
/* 43:   */   {
/* 44:63 */     return this.projection.getColumnAliases(loc);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String[] getColumnAliases(int loc, Criteria criteria, CriteriaQuery criteriaQuery)
/* 48:   */   {
/* 49:67 */     return (this.projection instanceof EnhancedProjection) ? ((EnhancedProjection)this.projection).getColumnAliases(loc, criteria, criteriaQuery) : getColumnAliases(loc);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Type[] getTypes(String alias, Criteria criteria, CriteriaQuery criteriaQuery)
/* 53:   */     throws HibernateException
/* 54:   */   {
/* 55:74 */     return this.alias.equals(alias) ? getTypes(criteria, criteriaQuery) : null;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String[] getColumnAliases(String alias, int loc)
/* 59:   */   {
/* 60:80 */     return this.alias.equals(alias) ? getColumnAliases(loc) : null;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String[] getColumnAliases(String alias, int loc, Criteria criteria, CriteriaQuery criteriaQuery)
/* 64:   */   {
/* 65:86 */     return this.alias.equals(alias) ? getColumnAliases(loc, criteria, criteriaQuery) : null;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String[] getAliases()
/* 69:   */   {
/* 70:92 */     return new String[] { this.alias };
/* 71:   */   }
/* 72:   */   
/* 73:   */   public boolean isGrouped()
/* 74:   */   {
/* 75:96 */     return this.projection.isGrouped();
/* 76:   */   }
/* 77:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.AliasedProjection
 * JD-Core Version:    0.7.0.1
 */