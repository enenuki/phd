/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ 
/*  6:   */ public class FloatType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<Float>
/*  8:   */   implements PrimitiveType<Float>
/*  9:   */ {
/* 10:38 */   public static final FloatType INSTANCE = new FloatType();
/* 11:41 */   public static final Float ZERO = Float.valueOf(0.0F);
/* 12:   */   
/* 13:   */   public FloatType()
/* 14:   */   {
/* 15:44 */     super(org.hibernate.type.descriptor.sql.FloatTypeDescriptor.INSTANCE, org.hibernate.type.descriptor.java.FloatTypeDescriptor.INSTANCE);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:48 */     return "float";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String[] getRegistrationKeys()
/* 24:   */   {
/* 25:53 */     return new String[] { getName(), Float.TYPE.getName(), Float.class.getName() };
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Serializable getDefaultValue()
/* 29:   */   {
/* 30:57 */     return ZERO;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Class getPrimitiveClass()
/* 34:   */   {
/* 35:61 */     return Float.TYPE;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String objectToSQLString(Float value, Dialect dialect)
/* 39:   */     throws Exception
/* 40:   */   {
/* 41:65 */     return toString(value);
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.FloatType
 * JD-Core Version:    0.7.0.1
 */