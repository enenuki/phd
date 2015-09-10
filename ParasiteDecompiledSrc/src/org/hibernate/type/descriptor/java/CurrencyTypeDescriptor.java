/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.util.Currency;
/*  4:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  5:   */ 
/*  6:   */ public class CurrencyTypeDescriptor
/*  7:   */   extends AbstractTypeDescriptor<Currency>
/*  8:   */ {
/*  9:36 */   public static final CurrencyTypeDescriptor INSTANCE = new CurrencyTypeDescriptor();
/* 10:   */   
/* 11:   */   public CurrencyTypeDescriptor()
/* 12:   */   {
/* 13:39 */     super(Currency.class);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String toString(Currency value)
/* 17:   */   {
/* 18:43 */     return value.getCurrencyCode();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Currency fromString(String string)
/* 22:   */   {
/* 23:47 */     return Currency.getInstance(string);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public <X> X unwrap(Currency value, Class<X> type, WrapperOptions options)
/* 27:   */   {
/* 28:52 */     if (value == null) {
/* 29:53 */       return null;
/* 30:   */     }
/* 31:55 */     if (String.class.isAssignableFrom(type)) {
/* 32:56 */       return value.getCurrencyCode();
/* 33:   */     }
/* 34:58 */     throw unknownUnwrap(type);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public <X> Currency wrap(X value, WrapperOptions options)
/* 38:   */   {
/* 39:62 */     if (value == null) {
/* 40:63 */       return null;
/* 41:   */     }
/* 42:65 */     if (String.class.isInstance(value)) {
/* 43:66 */       return Currency.getInstance((String)value);
/* 44:   */     }
/* 45:68 */     throw unknownWrap(value.getClass());
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.CurrencyTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */