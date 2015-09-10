/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.type.Type;
/*  6:   */ 
/*  7:   */ public abstract class SimpleProjection
/*  8:   */   implements EnhancedProjection
/*  9:   */ {
/* 10:   */   public Projection as(String alias)
/* 11:   */   {
/* 12:38 */     return Projections.alias(this, alias);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String[] getColumnAliases(String alias, int loc)
/* 16:   */   {
/* 17:42 */     return null;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String[] getColumnAliases(String alias, int loc, Criteria criteria, CriteriaQuery criteriaQuery)
/* 21:   */   {
/* 22:46 */     return getColumnAliases(alias, loc);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Type[] getTypes(String alias, Criteria criteria, CriteriaQuery criteriaQuery)
/* 26:   */     throws HibernateException
/* 27:   */   {
/* 28:51 */     return null;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String[] getColumnAliases(int loc)
/* 32:   */   {
/* 33:55 */     return new String[] { "y" + loc + "_" };
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int getColumnCount(Criteria criteria, CriteriaQuery criteriaQuery)
/* 37:   */   {
/* 38:59 */     Type[] types = getTypes(criteria, criteriaQuery);
/* 39:60 */     int count = 0;
/* 40:61 */     for (int i = 0; i < types.length; i++) {
/* 41:62 */       count += types[i].getColumnSpan(criteriaQuery.getFactory());
/* 42:   */     }
/* 43:64 */     return count;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public String[] getColumnAliases(int loc, Criteria criteria, CriteriaQuery criteriaQuery)
/* 47:   */   {
/* 48:68 */     int numColumns = getColumnCount(criteria, criteriaQuery);
/* 49:69 */     String[] aliases = new String[numColumns];
/* 50:70 */     for (int i = 0; i < numColumns; i++)
/* 51:   */     {
/* 52:71 */       aliases[i] = ("y" + loc + "_");
/* 53:72 */       loc++;
/* 54:   */     }
/* 55:74 */     return aliases;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String[] getAliases()
/* 59:   */   {
/* 60:78 */     return new String[1];
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 64:   */     throws HibernateException
/* 65:   */   {
/* 66:83 */     throw new UnsupportedOperationException("not a grouping projection");
/* 67:   */   }
/* 68:   */   
/* 69:   */   public boolean isGrouped()
/* 70:   */   {
/* 71:87 */     return false;
/* 72:   */   }
/* 73:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.SimpleProjection
 * JD-Core Version:    0.7.0.1
 */