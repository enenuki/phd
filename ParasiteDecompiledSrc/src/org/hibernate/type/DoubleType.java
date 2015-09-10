/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ 
/*  6:   */ public class DoubleType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<Double>
/*  8:   */   implements PrimitiveType<Double>
/*  9:   */ {
/* 10:38 */   public static final DoubleType INSTANCE = new DoubleType();
/* 11:41 */   public static final Double ZERO = Double.valueOf(0.0D);
/* 12:   */   
/* 13:   */   public DoubleType()
/* 14:   */   {
/* 15:44 */     super(org.hibernate.type.descriptor.sql.DoubleTypeDescriptor.INSTANCE, org.hibernate.type.descriptor.java.DoubleTypeDescriptor.INSTANCE);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:48 */     return "double";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String[] getRegistrationKeys()
/* 24:   */   {
/* 25:53 */     return new String[] { getName(), Double.TYPE.getName(), Double.class.getName() };
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Serializable getDefaultValue()
/* 29:   */   {
/* 30:57 */     return ZERO;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Class getPrimitiveClass()
/* 34:   */   {
/* 35:61 */     return Double.TYPE;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String objectToSQLString(Double value, Dialect dialect)
/* 39:   */     throws Exception
/* 40:   */   {
/* 41:65 */     return toString(value);
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.DoubleType
 * JD-Core Version:    0.7.0.1
 */