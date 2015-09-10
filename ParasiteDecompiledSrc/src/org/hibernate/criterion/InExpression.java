/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.hibernate.Criteria;
/*   5:    */ import org.hibernate.EntityMode;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.dialect.Dialect;
/*   8:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   9:    */ import org.hibernate.engine.spi.TypedValue;
/*  10:    */ import org.hibernate.internal.util.StringHelper;
/*  11:    */ import org.hibernate.type.CompositeType;
/*  12:    */ import org.hibernate.type.Type;
/*  13:    */ 
/*  14:    */ public class InExpression
/*  15:    */   implements Criterion
/*  16:    */ {
/*  17:    */   private final String propertyName;
/*  18:    */   private final Object[] values;
/*  19:    */   
/*  20:    */   protected InExpression(String propertyName, Object[] values)
/*  21:    */   {
/*  22: 45 */     this.propertyName = propertyName;
/*  23: 46 */     this.values = values;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/*  27:    */     throws HibernateException
/*  28:    */   {
/*  29: 51 */     String[] columns = criteriaQuery.findColumns(this.propertyName, criteria);
/*  30: 52 */     if ((criteriaQuery.getFactory().getDialect().supportsRowValueConstructorSyntaxInInList()) || (columns.length <= 1))
/*  31:    */     {
/*  32: 55 */       String singleValueParam = StringHelper.repeat("?, ", columns.length - 1) + "?";
/*  33: 58 */       if (columns.length > 1) {
/*  34: 59 */         singleValueParam = '(' + singleValueParam + ')';
/*  35:    */       }
/*  36: 60 */       String params = this.values.length > 0 ? StringHelper.repeat(new StringBuilder().append(singleValueParam).append(", ").toString(), this.values.length - 1) + singleValueParam : "";
/*  37:    */       
/*  38:    */ 
/*  39: 63 */       String cols = StringHelper.join(", ", columns);
/*  40: 64 */       if (columns.length > 1) {
/*  41: 65 */         cols = '(' + cols + ')';
/*  42:    */       }
/*  43: 66 */       return cols + " in (" + params + ')';
/*  44:    */     }
/*  45: 68 */     String cols = " ( " + StringHelper.join(" = ? and ", columns) + "= ? ) ";
/*  46: 69 */     cols = this.values.length > 0 ? StringHelper.repeat(new StringBuilder().append(cols).append("or ").toString(), this.values.length - 1) + cols : "";
/*  47:    */     
/*  48:    */ 
/*  49: 72 */     cols = " ( " + cols + " ) ";
/*  50: 73 */     return cols;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/*  54:    */     throws HibernateException
/*  55:    */   {
/*  56: 79 */     ArrayList list = new ArrayList();
/*  57: 80 */     Type type = criteriaQuery.getTypeUsingProjection(criteria, this.propertyName);
/*  58: 81 */     if (type.isComponentType())
/*  59:    */     {
/*  60: 82 */       CompositeType actype = (CompositeType)type;
/*  61: 83 */       Type[] types = actype.getSubtypes();
/*  62: 84 */       for (int j = 0; j < this.values.length; j++) {
/*  63: 85 */         for (int i = 0; i < types.length; i++)
/*  64:    */         {
/*  65: 86 */           Object subval = this.values[j] == null ? null : actype.getPropertyValues(this.values[j], EntityMode.POJO)[i];
/*  66:    */           
/*  67:    */ 
/*  68: 89 */           list.add(new TypedValue(types[i], subval, EntityMode.POJO));
/*  69:    */         }
/*  70:    */       }
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 94 */       for (int j = 0; j < this.values.length; j++) {
/*  75: 95 */         list.add(new TypedValue(type, this.values[j], EntityMode.POJO));
/*  76:    */       }
/*  77:    */     }
/*  78: 98 */     return (TypedValue[])list.toArray(new TypedValue[list.size()]);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String toString()
/*  82:    */   {
/*  83:102 */     return this.propertyName + " in (" + StringHelper.toString(this.values) + ')';
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.InExpression
 * JD-Core Version:    0.7.0.1
 */