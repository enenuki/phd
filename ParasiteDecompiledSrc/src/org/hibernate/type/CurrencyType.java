/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.Currency;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.CurrencyTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class CurrencyType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<Currency>
/* 10:   */   implements LiteralType<Currency>
/* 11:   */ {
/* 12:42 */   public static final CurrencyType INSTANCE = new CurrencyType();
/* 13:   */   
/* 14:   */   public CurrencyType()
/* 15:   */   {
/* 16:45 */     super(VarcharTypeDescriptor.INSTANCE, CurrencyTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:49 */     return "currency";
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean registerUnderJavaType()
/* 25:   */   {
/* 26:54 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String objectToSQLString(Currency value, Dialect dialect)
/* 30:   */     throws Exception
/* 31:   */   {
/* 32:58 */     return "'" + toString(value) + "'";
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CurrencyType
 * JD-Core Version:    0.7.0.1
 */