/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*   6:    */ 
/*   7:    */ public class DoubleTypeDescriptor
/*   8:    */   extends AbstractTypeDescriptor<Double>
/*   9:    */ {
/*  10: 37 */   public static final DoubleTypeDescriptor INSTANCE = new DoubleTypeDescriptor();
/*  11:    */   
/*  12:    */   public DoubleTypeDescriptor()
/*  13:    */   {
/*  14: 40 */     super(Double.class);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public String toString(Double value)
/*  18:    */   {
/*  19: 44 */     return value == null ? null : value.toString();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Double fromString(String string)
/*  23:    */   {
/*  24: 48 */     return Double.valueOf(string);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public <X> X unwrap(Double value, Class<X> type, WrapperOptions options)
/*  28:    */   {
/*  29: 53 */     if (value == null) {
/*  30: 54 */       return null;
/*  31:    */     }
/*  32: 56 */     if (Double.class.isAssignableFrom(type)) {
/*  33: 57 */       return value;
/*  34:    */     }
/*  35: 59 */     if (Byte.class.isAssignableFrom(type)) {
/*  36: 60 */       return Byte.valueOf(value.byteValue());
/*  37:    */     }
/*  38: 62 */     if (Short.class.isAssignableFrom(type)) {
/*  39: 63 */       return Short.valueOf(value.shortValue());
/*  40:    */     }
/*  41: 65 */     if (Integer.class.isAssignableFrom(type)) {
/*  42: 66 */       return Integer.valueOf(value.intValue());
/*  43:    */     }
/*  44: 68 */     if (Long.class.isAssignableFrom(type)) {
/*  45: 69 */       return Long.valueOf(value.longValue());
/*  46:    */     }
/*  47: 71 */     if (Float.class.isAssignableFrom(type)) {
/*  48: 72 */       return Float.valueOf(value.floatValue());
/*  49:    */     }
/*  50: 74 */     if (BigInteger.class.isAssignableFrom(type)) {
/*  51: 75 */       return BigInteger.valueOf(value.longValue());
/*  52:    */     }
/*  53: 77 */     if (BigDecimal.class.isAssignableFrom(type)) {
/*  54: 78 */       return BigDecimal.valueOf(value.doubleValue());
/*  55:    */     }
/*  56: 80 */     if (String.class.isAssignableFrom(type)) {
/*  57: 81 */       return value.toString();
/*  58:    */     }
/*  59: 83 */     throw unknownUnwrap(type);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public <X> Double wrap(X value, WrapperOptions options)
/*  63:    */   {
/*  64: 88 */     if (value == null) {
/*  65: 89 */       return null;
/*  66:    */     }
/*  67: 91 */     if (Double.class.isInstance(value)) {
/*  68: 92 */       return (Double)value;
/*  69:    */     }
/*  70: 94 */     if (Number.class.isInstance(value)) {
/*  71: 95 */       return Double.valueOf(((Number)value).doubleValue());
/*  72:    */     }
/*  73: 97 */     if (String.class.isInstance(value)) {
/*  74: 98 */       return Double.valueOf((String)value);
/*  75:    */     }
/*  76:100 */     throw unknownWrap(value.getClass());
/*  77:    */   }
/*  78:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.DoubleTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */