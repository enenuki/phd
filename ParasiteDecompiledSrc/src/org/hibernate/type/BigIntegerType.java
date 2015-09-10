/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.math.BigInteger;
/*  4:   */ import org.hibernate.dialect.Dialect;
/*  5:   */ import org.hibernate.type.descriptor.java.BigIntegerTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.NumericTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class BigIntegerType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<BigInteger>
/* 10:   */   implements DiscriminatorType<BigInteger>
/* 11:   */ {
/* 12:43 */   public static final BigIntegerType INSTANCE = new BigIntegerType();
/* 13:   */   
/* 14:   */   public BigIntegerType()
/* 15:   */   {
/* 16:46 */     super(NumericTypeDescriptor.INSTANCE, BigIntegerTypeDescriptor.INSTANCE);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String getName()
/* 20:   */   {
/* 21:53 */     return "big_integer";
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected boolean registerUnderJavaType()
/* 25:   */   {
/* 26:58 */     return true;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String objectToSQLString(BigInteger value, Dialect dialect)
/* 30:   */   {
/* 31:65 */     return BigIntegerTypeDescriptor.INSTANCE.toString(value);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public BigInteger stringToObject(String string)
/* 35:   */   {
/* 36:72 */     return BigIntegerTypeDescriptor.INSTANCE.fromString(string);
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.BigIntegerType
 * JD-Core Version:    0.7.0.1
 */