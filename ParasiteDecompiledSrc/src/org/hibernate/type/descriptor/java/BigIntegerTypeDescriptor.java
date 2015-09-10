/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*   6:    */ 
/*   7:    */ public class BigIntegerTypeDescriptor
/*   8:    */   extends AbstractTypeDescriptor<BigInteger>
/*   9:    */ {
/*  10: 37 */   public static final BigIntegerTypeDescriptor INSTANCE = new BigIntegerTypeDescriptor();
/*  11:    */   
/*  12:    */   public BigIntegerTypeDescriptor()
/*  13:    */   {
/*  14: 40 */     super(BigInteger.class);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String toString(BigInteger value)
/*  18:    */   {
/*  19: 44 */     return value.toString();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public BigInteger fromString(String string)
/*  23:    */   {
/*  24: 48 */     return new BigInteger(string);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int extractHashCode(BigInteger value)
/*  28:    */   {
/*  29: 53 */     return value.intValue();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public boolean areEqual(BigInteger one, BigInteger another)
/*  33:    */   {
/*  34: 58 */     return (one == another) || ((one != null) && (another != null) && (one.compareTo(another) == 0));
/*  35:    */   }
/*  36:    */   
/*  37:    */   public <X> X unwrap(BigInteger value, Class<X> type, WrapperOptions options)
/*  38:    */   {
/*  39: 66 */     if (value == null) {
/*  40: 67 */       return null;
/*  41:    */     }
/*  42: 69 */     if (BigInteger.class.isAssignableFrom(type)) {
/*  43: 70 */       return value;
/*  44:    */     }
/*  45: 72 */     if (BigDecimal.class.isAssignableFrom(type)) {
/*  46: 73 */       return new BigDecimal(value);
/*  47:    */     }
/*  48: 75 */     if (Byte.class.isAssignableFrom(type)) {
/*  49: 76 */       return Byte.valueOf(value.byteValue());
/*  50:    */     }
/*  51: 78 */     if (Short.class.isAssignableFrom(type)) {
/*  52: 79 */       return Short.valueOf(value.shortValue());
/*  53:    */     }
/*  54: 81 */     if (Integer.class.isAssignableFrom(type)) {
/*  55: 82 */       return Integer.valueOf(value.intValue());
/*  56:    */     }
/*  57: 84 */     if (Long.class.isAssignableFrom(type)) {
/*  58: 85 */       return Long.valueOf(value.longValue());
/*  59:    */     }
/*  60: 87 */     if (Double.class.isAssignableFrom(type)) {
/*  61: 88 */       return Double.valueOf(value.doubleValue());
/*  62:    */     }
/*  63: 90 */     if (Float.class.isAssignableFrom(type)) {
/*  64: 91 */       return Float.valueOf(value.floatValue());
/*  65:    */     }
/*  66: 93 */     throw unknownUnwrap(type);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public <X> BigInteger wrap(X value, WrapperOptions options)
/*  70:    */   {
/*  71:100 */     if (value == null) {
/*  72:101 */       return null;
/*  73:    */     }
/*  74:103 */     if (BigInteger.class.isInstance(value)) {
/*  75:104 */       return (BigInteger)value;
/*  76:    */     }
/*  77:106 */     if (BigDecimal.class.isInstance(value)) {
/*  78:107 */       return ((BigDecimal)value).toBigIntegerExact();
/*  79:    */     }
/*  80:109 */     if (Number.class.isInstance(value)) {
/*  81:110 */       return BigInteger.valueOf(((Number)value).longValue());
/*  82:    */     }
/*  83:112 */     throw unknownWrap(value.getClass());
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.BigIntegerTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */