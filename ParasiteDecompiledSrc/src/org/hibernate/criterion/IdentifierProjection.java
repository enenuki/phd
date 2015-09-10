/*  1:   */ package org.hibernate.criterion;
/*  2:   */ 
/*  3:   */ import org.hibernate.Criteria;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.hibernate.type.Type;
/*  7:   */ 
/*  8:   */ public class IdentifierProjection
/*  9:   */   extends SimpleProjection
/* 10:   */ {
/* 11:   */   private boolean grouped;
/* 12:   */   
/* 13:   */   protected IdentifierProjection(boolean grouped)
/* 14:   */   {
/* 15:40 */     this.grouped = grouped;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected IdentifierProjection()
/* 19:   */   {
/* 20:44 */     this(false);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String toString()
/* 24:   */   {
/* 25:48 */     return "id";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
/* 29:   */     throws HibernateException
/* 30:   */   {
/* 31:53 */     return new Type[] { criteriaQuery.getIdentifierType(criteria) };
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery)
/* 35:   */     throws HibernateException
/* 36:   */   {
/* 37:58 */     StringBuffer buf = new StringBuffer();
/* 38:59 */     String[] cols = criteriaQuery.getIdentifierColumns(criteria);
/* 39:60 */     for (int i = 0; i < cols.length; i++)
/* 40:   */     {
/* 41:61 */       buf.append(cols[i]).append(" as y").append(position + i).append('_');
/* 42:65 */       if (i < cols.length - 1) {
/* 43:66 */         buf.append(", ");
/* 44:   */       }
/* 45:   */     }
/* 46:68 */     return buf.toString();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean isGrouped()
/* 50:   */   {
/* 51:72 */     return this.grouped;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public String toGroupSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 55:   */     throws HibernateException
/* 56:   */   {
/* 57:77 */     if (!this.grouped) {
/* 58:78 */       return super.toGroupSqlString(criteria, criteriaQuery);
/* 59:   */     }
/* 60:81 */     return StringHelper.join(", ", criteriaQuery.getIdentifierColumns(criteria));
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.IdentifierProjection
 * JD-Core Version:    0.7.0.1
 */