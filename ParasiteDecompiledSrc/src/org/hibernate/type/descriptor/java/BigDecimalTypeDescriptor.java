/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*   6:    */ 
/*   7:    */ public class BigDecimalTypeDescriptor
/*   8:    */   extends AbstractTypeDescriptor<BigDecimal>
/*   9:    */ {
/*  10: 37 */   public static final BigDecimalTypeDescriptor INSTANCE = new BigDecimalTypeDescriptor();
/*  11:    */   
/*  12:    */   public BigDecimalTypeDescriptor()
/*  13:    */   {
/*  14: 40 */     super(BigDecimal.class);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String toString(BigDecimal value)
/*  18:    */   {
/*  19: 44 */     return value.toString();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public BigDecimal fromString(String string)
/*  23:    */   {
/*  24: 48 */     return new BigDecimal(string);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean areEqual(BigDecimal one, BigDecimal another)
/*  28:    */   {
/*  29: 53 */     return (one == another) || ((one != null) && (another != null) && (one.compareTo(another) == 0));
/*  30:    */   }
/*  31:    */   
/*  32:    */   public int extractHashCode(BigDecimal value)
/*  33:    */   {
/*  34: 58 */     return value.intValue();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public <X> X unwrap(BigDecimal value, Class<X> type, WrapperOptions options)
/*  38:    */   {
/*  39: 63 */     if (value == null) {
/*  40: 64 */       return null;
/*  41:    */     }
/*  42: 66 */     if (BigDecimal.class.isAssignableFrom(type)) {
/*  43: 67 */       return value;
/*  44:    */     }
/*  45: 69 */     if (BigInteger.class.isAssignableFrom(type)) {
/*  46: 70 */       return value.toBigIntegerExact();
/*  47:    */     }
/*  48: 72 */     if (Byte.class.isAssignableFrom(type)) {
/*  49: 73 */       return Byte.valueOf(value.byteValue());
/*  50:    */     }
/*  51: 75 */     if (Short.class.isAssignableFrom(type)) {
/*  52: 76 */       return Short.valueOf(value.shortValue());
/*  53:    */     }
/*  54: 78 */     if (Integer.class.isAssignableFrom(type)) {
/*  55: 79 */       return Integer.valueOf(value.intValue());
/*  56:    */     }
/*  57: 81 */     if (Long.class.isAssignableFrom(type)) {
/*  58: 82 */       return Long.valueOf(value.longValue());
/*  59:    */     }
/*  60: 84 */     if (Double.class.isAssignableFrom(type)) {
/*  61: 85 */       return Double.valueOf(value.doubleValue());
/*  62:    */     }
/*  63: 87 */     if (Float.class.isAssignableFrom(type)) {
/*  64: 88 */       return Float.valueOf(value.floatValue());
/*  65:    */     }
/*  66: 90 */     throw unknownUnwrap(type);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public <X> BigDecimal wrap(X value, WrapperOptions options)
/*  70:    */   {
/*  71: 94 */     if (value == null) {
/*  72: 95 */       return null;
/*  73:    */     }
/*  74: 97 */     if (BigDecimal.class.isInstance(value)) {
/*  75: 98 */       return (BigDecimal)value;
/*  76:    */     }
/*  77:100 */     if (BigInteger.class.isInstance(value)) {
/*  78:101 */       return new BigDecimal((BigInteger)value);
/*  79:    */     }
/*  80:103 */     if (Number.class.isInstance(value)) {
/*  81:104 */       return BigDecimal.valueOf(((Number)value).doubleValue());
/*  82:    */     }
/*  83:106 */     throw unknownWrap(value.getClass());
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.BigDecimalTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */