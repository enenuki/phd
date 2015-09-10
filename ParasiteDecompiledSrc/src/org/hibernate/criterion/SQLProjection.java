/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public class SQLProjection
/*  9:   */   implements Projection
/* 10:   */ {
/* 11:   */   private final String sql;
/* 12:   */   private final String groupBy;
/* 13:   */   private final Type[] types;
/* 14:   */   private String[] aliases;
/* 15:   */   private String[] columnAliases;
/* 16:   */   private boolean grouped;
/* 17:   */   
/* 18:   */   public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery)
/* 19:   */     throws HibernateException
/* 20:   */   {
/* 21:49 */     return StringHelper.replace(this.sql, "{alias}", criteriaQuery.getSQLAlias(criteria));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 25:   */     throws HibernateException
/* 26:   */   {
/* 27:54 */     return StringHelper.replace(this.groupBy, "{alias}", criteriaQuery.getSQLAlias(criteria));
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Type[] getTypes(Criteria crit, CriteriaQuery criteriaQuery)
/* 31:   */     throws HibernateException
/* 32:   */   {
/* 33:59 */     return this.types;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public String toString()
/* 37:   */   {
/* 38:63 */     return this.sql;
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected SQLProjection(String sql, String[] columnAliases, Type[] types)
/* 42:   */   {
/* 43:67 */     this(sql, null, columnAliases, types);
/* 44:   */   }
/* 45:   */   
/* 46:   */   protected SQLProjection(String sql, String groupBy, String[] columnAliases, Type[] types)
/* 47:   */   {
/* 48:71 */     this.sql = sql;
/* 49:72 */     this.types = types;
/* 50:73 */     this.aliases = columnAliases;
/* 51:74 */     this.columnAliases = columnAliases;
/* 52:75 */     this.grouped = (groupBy != null);
/* 53:76 */     this.groupBy = groupBy;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public String[] getAliases()
/* 57:   */   {
/* 58:80 */     return this.aliases;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String[] getColumnAliases(int loc)
/* 62:   */   {
/* 63:84 */     return this.columnAliases;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean isGrouped()
/* 67:   */   {
/* 68:88 */     return this.grouped;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public Type[] getTypes(String alias, Criteria crit, CriteriaQuery criteriaQuery)
/* 72:   */   {
/* 73:92 */     return null;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public String[] getColumnAliases(String alias, int loc)
/* 77:   */   {
/* 78:96 */     return null;
/* 79:   */   }
/* 80:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.SQLProjection
 * JD-Core Version:    0.7.0.1
 */