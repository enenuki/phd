/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.BooleanTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.IntegerTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class NumericBooleanType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<Boolean>
/* 10:   */   implements PrimitiveType<Boolean>, DiscriminatorType<Boolean>
/* 11:   */ {
/* 12:41 */   public static final NumericBooleanType INSTANCE = new NumericBooleanType();
/* 13:   */   
/* 14:   */   public NumericBooleanType()
/* 15:   */   {
/* 16:44 */     super(IntegerTypeDescriptor.INSTANCE, BooleanTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:48 */     return "numeric_boolean";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Class getPrimitiveClass()
/* 25:   */   {
/* 26:52 */     return Boolean.TYPE;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Serializable getDefaultValue()
/* 30:   */   {
/* 31:56 */     return Boolean.FALSE;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Boolean stringToObject(String string)
/* 35:   */   {
/* 36:60 */     return (Boolean)fromString(string);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public String objectToSQLString(Boolean value, Dialect dialect)
/* 40:   */   {
/* 41:65 */     return value.booleanValue() ? "1" : "0";
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.NumericBooleanType
 * JD-Core Version:    0.7.0.1
 */