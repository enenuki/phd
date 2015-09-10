/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.Dialect;
/*  4:   */ import org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor;
/*  5:   */ import org.hibernate.type.descriptor.sql.DateTypeDescriptor;
/*  6:   */ 
/*  7:   */ public class DateType
/*  8:   */   extends AbstractSingleColumnStandardBasicType<java.util.Date>
/*  9:   */   implements IdentifierType<java.util.Date>, LiteralType<java.util.Date>
/* 10:   */ {
/* 11:41 */   public static final DateType INSTANCE = new DateType();
/* 12:   */   
/* 13:   */   public DateType()
/* 14:   */   {
/* 15:44 */     super(DateTypeDescriptor.INSTANCE, JdbcDateTypeDescriptor.INSTANCE);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:48 */     return "date";
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String[] getRegistrationKeys()
/* 24:   */   {
/* 25:53 */     return new String[] { getName(), java.sql.Date.class.getName() };
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String objectToSQLString(java.util.Date value, Dialect dialect)
/* 29:   */     throws Exception
/* 30:   */   {
/* 31:65 */     java.sql.Date jdbcDate = java.sql.Date.class.isInstance(value) ? (java.sql.Date)value : new java.sql.Date(value.getTime());
/* 32:   */     
/* 33:   */ 
/* 34:   */ 
/* 35:69 */     return StringType.INSTANCE.objectToSQLString(jdbcDate.toString(), dialect);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public java.util.Date stringToObject(String xml)
/* 39:   */   {
/* 40:73 */     return (java.util.Date)fromString(xml);
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.DateType
 * JD-Core Version:    0.7.0.1
 */