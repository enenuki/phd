/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public class PropertyProjection
/*  9:   */   extends SimpleProjection
/* 10:   */ {
/* 11:   */   private String propertyName;
/* 12:   */   private boolean grouped;
/* 13:   */   
/* 14:   */   protected PropertyProjection(String prop, boolean grouped)
/* 15:   */   {
/* 16:41 */     this.propertyName = prop;
/* 17:42 */     this.grouped = grouped;
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected PropertyProjection(String prop)
/* 21:   */   {
/* 22:46 */     this(prop, false);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getPropertyName()
/* 26:   */   {
/* 27:50 */     return this.propertyName;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String toString()
/* 31:   */   {
/* 32:54 */     return this.propertyName;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
/* 36:   */     throws HibernateException
/* 37:   */   {
/* 38:59 */     return new Type[] { criteriaQuery.getType(criteria, this.propertyName) };
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery)
/* 42:   */     throws HibernateException
/* 43:   */   {
/* 44:64 */     StringBuffer buf = new StringBuffer();
/* 45:65 */     String[] cols = criteriaQuery.getColumns(this.propertyName, criteria);
/* 46:66 */     for (int i = 0; i < cols.length; i++)
/* 47:   */     {
/* 48:67 */       buf.append(cols[i]).append(" as y").append(position + i).append('_');
/* 49:71 */       if (i < cols.length - 1) {
/* 50:72 */         buf.append(", ");
/* 51:   */       }
/* 52:   */     }
/* 53:74 */     return buf.toString();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public boolean isGrouped()
/* 57:   */   {
/* 58:78 */     return this.grouped;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 62:   */     throws HibernateException
/* 63:   */   {
/* 64:83 */     if (!this.grouped) {
/* 65:84 */       return super.toGroupSqlString(criteria, criteriaQuery);
/* 66:   */     }
/* 67:87 */     return StringHelper.join(", ", criteriaQuery.getColumns(this.propertyName, criteria));
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.PropertyProjection
 * JD-Core Version:    0.7.0.1
 */