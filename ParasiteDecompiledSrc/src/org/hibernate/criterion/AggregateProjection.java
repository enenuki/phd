/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import java.util.Collections;
/*   4:    */ import java.util.List;
/*   5:    */ import org.hibernate.Criteria;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.dialect.function.SQLFunction;
/*   8:    */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.type.Type;
/*  11:    */ 
/*  12:    */ public class AggregateProjection
/*  13:    */   extends SimpleProjection
/*  14:    */ {
/*  15:    */   protected final String propertyName;
/*  16:    */   private final String functionName;
/*  17:    */   
/*  18:    */   protected AggregateProjection(String functionName, String propertyName)
/*  19:    */   {
/*  20: 43 */     this.functionName = functionName;
/*  21: 44 */     this.propertyName = propertyName;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getFunctionName()
/*  25:    */   {
/*  26: 48 */     return this.functionName;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getPropertyName()
/*  30:    */   {
/*  31: 52 */     return this.propertyName;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toString()
/*  35:    */   {
/*  36: 56 */     return this.functionName + "(" + this.propertyName + ')';
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
/*  40:    */     throws HibernateException
/*  41:    */   {
/*  42: 63 */     return new Type[] { getFunction(criteriaQuery).getReturnType(criteriaQuery.getType(criteria, getPropertyName()), criteriaQuery.getFactory()) };
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery)
/*  46:    */     throws HibernateException
/*  47:    */   {
/*  48: 75 */     String functionFragment = getFunction(criteriaQuery).render(criteriaQuery.getType(criteria, getPropertyName()), buildFunctionParameterList(criteria, criteriaQuery), criteriaQuery.getFactory());
/*  49:    */     
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 80 */     return functionFragment + " as y" + loc + '_';
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected SQLFunction getFunction(CriteriaQuery criteriaQuery)
/*  57:    */   {
/*  58: 84 */     return getFunction(getFunctionName(), criteriaQuery);
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected SQLFunction getFunction(String functionName, CriteriaQuery criteriaQuery)
/*  62:    */   {
/*  63: 88 */     SQLFunction function = criteriaQuery.getFactory().getSqlFunctionRegistry().findSQLFunction(functionName);
/*  64: 91 */     if (function == null) {
/*  65: 92 */       throw new HibernateException("Unable to locate mapping for function named [" + functionName + "]");
/*  66:    */     }
/*  67: 94 */     return function;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected List buildFunctionParameterList(Criteria criteria, CriteriaQuery criteriaQuery)
/*  71:    */   {
/*  72: 98 */     return buildFunctionParameterList(criteriaQuery.getColumn(criteria, getPropertyName()));
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected List buildFunctionParameterList(String column)
/*  76:    */   {
/*  77:102 */     return Collections.singletonList(column);
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.AggregateProjection
 * JD-Core Version:    0.7.0.1
 */