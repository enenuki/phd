/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.BooleanTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.CharTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class YesNoType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<Boolean>
/* 10:   */   implements PrimitiveType<Boolean>, DiscriminatorType<Boolean>
/* 11:   */ {
/* 12:42 */   public static final YesNoType INSTANCE = new YesNoType();
/* 13:   */   
/* 14:   */   public YesNoType()
/* 15:   */   {
/* 16:45 */     super(CharTypeDescriptor.INSTANCE, BooleanTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:49 */     return "yes_no";
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Class getPrimitiveClass()
/* 25:   */   {
/* 26:53 */     return Boolean.TYPE;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public Boolean stringToObject(String xml)
/* 30:   */     throws Exception
/* 31:   */   {
/* 32:57 */     return (Boolean)fromString(xml);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Serializable getDefaultValue()
/* 36:   */   {
/* 37:61 */     return Boolean.FALSE;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String objectToSQLString(Boolean value, Dialect dialect)
/* 41:   */     throws Exception
/* 42:   */   {
/* 43:66 */     return StringType.INSTANCE.objectToSQLString(value.booleanValue() ? "Y" : "N", dialect);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.YesNoType
 * JD-Core Version:    0.7.0.1
 */