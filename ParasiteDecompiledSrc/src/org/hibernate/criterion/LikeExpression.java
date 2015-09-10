/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import org.hibernate.Criteria;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.dialect.Dialect;
/*   6:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   7:    */ import org.hibernate.engine.spi.TypedValue;
/*   8:    */ 
/*   9:    */ public class LikeExpression
/*  10:    */   implements Criterion
/*  11:    */ {
/*  12:    */   private final String propertyName;
/*  13:    */   private final Object value;
/*  14:    */   private final Character escapeChar;
/*  15:    */   private final boolean ignoreCase;
/*  16:    */   
/*  17:    */   protected LikeExpression(String propertyName, String value, Character escapeChar, boolean ignoreCase)
/*  18:    */   {
/*  19: 48 */     this.propertyName = propertyName;
/*  20: 49 */     this.value = value;
/*  21: 50 */     this.escapeChar = escapeChar;
/*  22: 51 */     this.ignoreCase = ignoreCase;
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected LikeExpression(String propertyName, String value)
/*  26:    */   {
/*  27: 57 */     this(propertyName, value, null, false);
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected LikeExpression(String propertyName, String value, MatchMode matchMode)
/*  31:    */   {
/*  32: 64 */     this(propertyName, matchMode.toMatchString(value));
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected LikeExpression(String propertyName, String value, MatchMode matchMode, Character escapeChar, boolean ignoreCase)
/*  36:    */   {
/*  37: 73 */     this(propertyName, matchMode.toMatchString(value), escapeChar, ignoreCase);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/*  41:    */     throws HibernateException
/*  42:    */   {
/*  43: 79 */     Dialect dialect = criteriaQuery.getFactory().getDialect();
/*  44: 80 */     String[] columns = criteriaQuery.findColumns(this.propertyName, criteria);
/*  45: 81 */     if (columns.length != 1) {
/*  46: 82 */       throw new HibernateException("Like may only be used with single-column properties");
/*  47:    */     }
/*  48: 84 */     String escape = " escape '" + this.escapeChar + "'";
/*  49: 85 */     String column = columns[0];
/*  50: 86 */     if (this.ignoreCase)
/*  51:    */     {
/*  52: 87 */       if (dialect.supportsCaseInsensitiveLike()) {
/*  53: 88 */         return column + " " + dialect.getCaseInsensitiveLike() + " ?" + escape;
/*  54:    */       }
/*  55: 91 */       return dialect.getLowercaseFunction() + '(' + column + ')' + " like ?" + escape;
/*  56:    */     }
/*  57: 95 */     return column + " like ?" + escape;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/*  61:    */     throws HibernateException
/*  62:    */   {
/*  63:102 */     return new TypedValue[] { criteriaQuery.getTypedValue(criteria, this.propertyName, this.ignoreCase ? this.value.toString().toLowerCase() : this.value.toString()) };
/*  64:    */   }
/*  65:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.LikeExpression
 * JD-Core Version:    0.7.0.1
 */