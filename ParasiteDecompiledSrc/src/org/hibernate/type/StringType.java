/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.Dialect;
/*  4:   */ import org.hibernate.type.descriptor.java.StringTypeDescriptor;
/*  5:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  6:   */ 
/*  7:   */ public class StringType
/*  8:   */   extends AbstractSingleColumnStandardBasicType<String>
/*  9:   */   implements DiscriminatorType<String>
/* 10:   */ {
/* 11:39 */   public static final StringType INSTANCE = new StringType();
/* 12:   */   
/* 13:   */   public StringType()
/* 14:   */   {
/* 15:42 */     super(VarcharTypeDescriptor.INSTANCE, StringTypeDescriptor.INSTANCE);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:46 */     return "string";
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected boolean registerUnderJavaType()
/* 24:   */   {
/* 25:51 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String objectToSQLString(String value, Dialect dialect)
/* 29:   */     throws Exception
/* 30:   */   {
/* 31:55 */     return '\'' + value + '\'';
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String stringToObject(String xml)
/* 35:   */     throws Exception
/* 36:   */   {
/* 37:59 */     return xml;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String toString(String value)
/* 41:   */   {
/* 42:63 */     return value;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.StringType
 * JD-Core Version:    0.7.0.1
 */