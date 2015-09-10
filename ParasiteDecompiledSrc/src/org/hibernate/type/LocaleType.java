/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.Locale;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.LocaleTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class LocaleType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<Locale>
/* 10:   */   implements LiteralType<Locale>
/* 11:   */ {
/* 12:41 */   public static final LocaleType INSTANCE = new LocaleType();
/* 13:   */   
/* 14:   */   public LocaleType()
/* 15:   */   {
/* 16:44 */     super(VarcharTypeDescriptor.INSTANCE, LocaleTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:48 */     return "locale";
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean registerUnderJavaType()
/* 25:   */   {
/* 26:53 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String objectToSQLString(Locale value, Dialect dialect)
/* 30:   */     throws Exception
/* 31:   */   {
/* 32:57 */     return StringType.INSTANCE.objectToSQLString(toString(value), dialect);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.LocaleType
 * JD-Core Version:    0.7.0.1
 */