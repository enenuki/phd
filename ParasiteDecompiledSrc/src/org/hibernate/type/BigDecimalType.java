/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.math.BigDecimal;
/*  4:   */ import org.hibernate.type.descriptor.java.BigDecimalTypeDescriptor;
/*  5:   */ import org.hibernate.type.descriptor.sql.NumericTypeDescriptor;
/*  6:   */ 
/*  7:   */ public class BigDecimalType
/*  8:   */   extends AbstractSingleColumnStandardBasicType<BigDecimal>
/*  9:   */ {
/* 10:39 */   public static final BigDecimalType INSTANCE = new BigDecimalType();
/* 11:   */   
/* 12:   */   public BigDecimalType()
/* 13:   */   {
/* 14:42 */     super(NumericTypeDescriptor.INSTANCE, BigDecimalTypeDescriptor.INSTANCE);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:49 */     return "big_decimal";
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected boolean registerUnderJavaType()
/* 23:   */   {
/* 24:54 */     return true;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.BigDecimalType
 * JD-Core Version:    0.7.0.1
 */