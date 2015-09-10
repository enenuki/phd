/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.Criteria;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.dialect.Dialect;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.type.Type;
/*   9:    */ 
/*  10:    */ public class Order
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   private boolean ascending;
/*  14:    */   private boolean ignoreCase;
/*  15:    */   private String propertyName;
/*  16:    */   
/*  17:    */   public String toString()
/*  18:    */   {
/*  19: 45 */     return this.propertyName + ' ' + (this.ascending ? "asc" : "desc");
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Order ignoreCase()
/*  23:    */   {
/*  24: 49 */     this.ignoreCase = true;
/*  25: 50 */     return this;
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected Order(String propertyName, boolean ascending)
/*  29:    */   {
/*  30: 57 */     this.propertyName = propertyName;
/*  31: 58 */     this.ascending = ascending;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/*  35:    */     throws HibernateException
/*  36:    */   {
/*  37: 67 */     String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, this.propertyName);
/*  38: 68 */     Type type = criteriaQuery.getTypeUsingProjection(criteria, this.propertyName);
/*  39: 69 */     StringBuffer fragment = new StringBuffer();
/*  40: 70 */     for (int i = 0; i < columns.length; i++)
/*  41:    */     {
/*  42: 71 */       SessionFactoryImplementor factory = criteriaQuery.getFactory();
/*  43: 72 */       boolean lower = (this.ignoreCase) && (type.sqlTypes(factory)[i] == 12);
/*  44: 73 */       if (lower) {
/*  45: 74 */         fragment.append(factory.getDialect().getLowercaseFunction()).append('(');
/*  46:    */       }
/*  47: 77 */       fragment.append(columns[i]);
/*  48: 78 */       if (lower) {
/*  49: 78 */         fragment.append(')');
/*  50:    */       }
/*  51: 79 */       fragment.append(this.ascending ? " asc" : " desc");
/*  52: 80 */       if (i < columns.length - 1) {
/*  53: 80 */         fragment.append(", ");
/*  54:    */       }
/*  55:    */     }
/*  56: 82 */     return fragment.toString();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Order asc(String propertyName)
/*  60:    */   {
/*  61: 92 */     return new Order(propertyName, true);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Order desc(String propertyName)
/*  65:    */   {
/*  66:102 */     return new Order(propertyName, false);
/*  67:    */   }
/*  68:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Order
 * JD-Core Version:    0.7.0.1
 */