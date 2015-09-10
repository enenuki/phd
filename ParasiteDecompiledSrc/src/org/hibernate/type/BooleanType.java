/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*  6:   */ 
/*  7:   */ public class BooleanType
/*  8:   */   extends AbstractSingleColumnStandardBasicType<Boolean>
/*  9:   */   implements PrimitiveType<Boolean>, DiscriminatorType<Boolean>
/* 10:   */ {
/* 11:41 */   public static final BooleanType INSTANCE = new BooleanType();
/* 12:   */   
/* 13:   */   public BooleanType()
/* 14:   */   {
/* 15:44 */     this(org.hibernate.type.descriptor.sql.BooleanTypeDescriptor.INSTANCE, org.hibernate.type.descriptor.java.BooleanTypeDescriptor.INSTANCE);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected BooleanType(SqlTypeDescriptor sqlTypeDescriptor, org.hibernate.type.descriptor.java.BooleanTypeDescriptor javaTypeDescriptor)
/* 19:   */   {
/* 20:48 */     super(sqlTypeDescriptor, javaTypeDescriptor);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:52 */     return "boolean";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String[] getRegistrationKeys()
/* 29:   */   {
/* 30:57 */     return new String[] { getName(), Boolean.TYPE.getName(), Boolean.class.getName() };
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Class getPrimitiveClass()
/* 34:   */   {
/* 35:61 */     return Boolean.TYPE;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Serializable getDefaultValue()
/* 39:   */   {
/* 40:65 */     return Boolean.FALSE;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Boolean stringToObject(String string)
/* 44:   */   {
/* 45:69 */     return (Boolean)fromString(string);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String objectToSQLString(Boolean value, Dialect dialect)
/* 49:   */   {
/* 50:74 */     return dialect.toBooleanValueString(value.booleanValue());
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.BooleanType
 * JD-Core Version:    0.7.0.1
 */