/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.net.URL;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.UrlTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class UrlType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<URL>
/* 10:   */   implements DiscriminatorType<URL>
/* 11:   */ {
/* 12:38 */   public static final UrlType INSTANCE = new UrlType();
/* 13:   */   
/* 14:   */   public UrlType()
/* 15:   */   {
/* 16:41 */     super(VarcharTypeDescriptor.INSTANCE, UrlTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:45 */     return "url";
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean registerUnderJavaType()
/* 25:   */   {
/* 26:50 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String toString(URL value)
/* 30:   */   {
/* 31:55 */     return UrlTypeDescriptor.INSTANCE.toString(value);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public String objectToSQLString(URL value, Dialect dialect)
/* 35:   */     throws Exception
/* 36:   */   {
/* 37:59 */     return StringType.INSTANCE.objectToSQLString(toString(value), dialect);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public URL stringToObject(String xml)
/* 41:   */     throws Exception
/* 42:   */   {
/* 43:63 */     return UrlTypeDescriptor.INSTANCE.fromString(xml);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.UrlType
 * JD-Core Version:    0.7.0.1
 */