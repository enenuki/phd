/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public class Distinct
/*  8:   */   implements EnhancedProjection
/*  9:   */ {
/* 10:   */   private final Projection projection;
/* 11:   */   
/* 12:   */   public Distinct(Projection proj)
/* 13:   */   {
/* 14:38 */     this.projection = proj;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery)
/* 18:   */     throws HibernateException
/* 19:   */   {
/* 20:43 */     return "distinct " + this.projection.toSqlString(criteria, position, criteriaQuery);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 24:   */     throws HibernateException
/* 25:   */   {
/* 26:48 */     return this.projection.toGroupSqlString(criteria, criteriaQuery);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
/* 30:   */     throws HibernateException
/* 31:   */   {
/* 32:53 */     return this.projection.getTypes(criteria, criteriaQuery);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Type[] getTypes(String alias, Criteria criteria, CriteriaQuery criteriaQuery)
/* 36:   */     throws HibernateException
/* 37:   */   {
/* 38:58 */     return this.projection.getTypes(alias, criteria, criteriaQuery);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String[] getColumnAliases(int loc)
/* 42:   */   {
/* 43:62 */     return this.projection.getColumnAliases(loc);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String[] getColumnAliases(int loc, Criteria criteria, CriteriaQuery criteriaQuery)
/* 47:   */   {
/* 48:66 */     return (this.projection instanceof EnhancedProjection) ? ((EnhancedProjection)this.projection).getColumnAliases(loc, criteria, criteriaQuery) : getColumnAliases(loc);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String[] getColumnAliases(String alias, int loc)
/* 52:   */   {
/* 53:72 */     return this.projection.getColumnAliases(alias, loc);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String[] getColumnAliases(String alias, int loc, Criteria criteria, CriteriaQuery criteriaQuery)
/* 57:   */   {
/* 58:76 */     return (this.projection instanceof EnhancedProjection) ? ((EnhancedProjection)this.projection).getColumnAliases(alias, loc, criteria, criteriaQuery) : getColumnAliases(alias, loc);
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String[] getAliases()
/* 62:   */   {
/* 63:82 */     return this.projection.getAliases();
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean isGrouped()
/* 67:   */   {
/* 68:86 */     return this.projection.isGrouped();
/* 69:   */   }
/* 70:   */   
/* 71:   */   public String toString()
/* 72:   */   {
/* 73:90 */     return "distinct " + this.projection.toString();
/* 74:   */   }
/* 75:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Distinct
 * JD-Core Version:    0.7.0.1
 */