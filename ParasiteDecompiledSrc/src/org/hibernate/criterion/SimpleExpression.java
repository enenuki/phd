/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import org.hibernate.Criteria;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.dialect.Dialect;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.engine.spi.TypedValue;
/*   8:    */ import org.hibernate.type.Type;
/*   9:    */ 
/*  10:    */ public class SimpleExpression
/*  11:    */   implements Criterion
/*  12:    */ {
/*  13:    */   private final String propertyName;
/*  14:    */   private final Object value;
/*  15:    */   private boolean ignoreCase;
/*  16:    */   private final String op;
/*  17:    */   
/*  18:    */   protected SimpleExpression(String propertyName, Object value, String op)
/*  19:    */   {
/*  20: 46 */     this.propertyName = propertyName;
/*  21: 47 */     this.value = value;
/*  22: 48 */     this.op = op;
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected SimpleExpression(String propertyName, Object value, String op, boolean ignoreCase)
/*  26:    */   {
/*  27: 52 */     this.propertyName = propertyName;
/*  28: 53 */     this.value = value;
/*  29: 54 */     this.ignoreCase = ignoreCase;
/*  30: 55 */     this.op = op;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public SimpleExpression ignoreCase()
/*  34:    */   {
/*  35: 59 */     this.ignoreCase = true;
/*  36: 60 */     return this;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/*  40:    */     throws HibernateException
/*  41:    */   {
/*  42: 66 */     String[] columns = criteriaQuery.findColumns(this.propertyName, criteria);
/*  43: 67 */     Type type = criteriaQuery.getTypeUsingProjection(criteria, this.propertyName);
/*  44: 68 */     StringBuffer fragment = new StringBuffer();
/*  45: 69 */     if (columns.length > 1) {
/*  46: 69 */       fragment.append('(');
/*  47:    */     }
/*  48: 70 */     SessionFactoryImplementor factory = criteriaQuery.getFactory();
/*  49: 71 */     int[] sqlTypes = type.sqlTypes(factory);
/*  50: 72 */     for (int i = 0; i < columns.length; i++)
/*  51:    */     {
/*  52: 73 */       boolean lower = (this.ignoreCase) && ((sqlTypes[i] == 12) || (sqlTypes[i] == 1));
/*  53: 75 */       if (lower) {
/*  54: 76 */         fragment.append(factory.getDialect().getLowercaseFunction()).append('(');
/*  55:    */       }
/*  56: 79 */       fragment.append(columns[i]);
/*  57: 80 */       if (lower) {
/*  58: 80 */         fragment.append(')');
/*  59:    */       }
/*  60: 81 */       fragment.append(getOp()).append("?");
/*  61: 82 */       if (i < columns.length - 1) {
/*  62: 82 */         fragment.append(" and ");
/*  63:    */       }
/*  64:    */     }
/*  65: 84 */     if (columns.length > 1) {
/*  66: 84 */       fragment.append(')');
/*  67:    */     }
/*  68: 85 */     return fragment.toString();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/*  72:    */     throws HibernateException
/*  73:    */   {
/*  74: 91 */     Object icvalue = this.ignoreCase ? this.value.toString().toLowerCase() : this.value;
/*  75: 92 */     return new TypedValue[] { criteriaQuery.getTypedValue(criteria, this.propertyName, icvalue) };
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String toString()
/*  79:    */   {
/*  80: 96 */     return this.propertyName + getOp() + this.value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected final String getOp()
/*  84:    */   {
/*  85:100 */     return this.op;
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.SimpleExpression
 * JD-Core Version:    0.7.0.1
 */