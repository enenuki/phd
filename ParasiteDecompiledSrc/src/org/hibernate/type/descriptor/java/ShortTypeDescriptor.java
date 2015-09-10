/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  4:   */ 
/*  5:   */ public class ShortTypeDescriptor
/*  6:   */   extends AbstractTypeDescriptor<Short>
/*  7:   */ {
/*  8:33 */   public static final ShortTypeDescriptor INSTANCE = new ShortTypeDescriptor();
/*  9:   */   
/* 10:   */   public ShortTypeDescriptor()
/* 11:   */   {
/* 12:36 */     super(Short.class);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String toString(Short value)
/* 16:   */   {
/* 17:40 */     return value == null ? null : value.toString();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Short fromString(String string)
/* 21:   */   {
/* 22:44 */     return Short.valueOf(string);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public <X> X unwrap(Short value, Class<X> type, WrapperOptions options)
/* 26:   */   {
/* 27:49 */     if (value == null) {
/* 28:50 */       return null;
/* 29:   */     }
/* 30:52 */     if (Short.class.isAssignableFrom(type)) {
/* 31:53 */       return value;
/* 32:   */     }
/* 33:55 */     if (Byte.class.isAssignableFrom(type)) {
/* 34:56 */       return Byte.valueOf(value.byteValue());
/* 35:   */     }
/* 36:58 */     if (Integer.class.isAssignableFrom(type)) {
/* 37:59 */       return Integer.valueOf(value.intValue());
/* 38:   */     }
/* 39:61 */     if (Long.class.isAssignableFrom(type)) {
/* 40:62 */       return Long.valueOf(value.longValue());
/* 41:   */     }
/* 42:64 */     if (Double.class.isAssignableFrom(type)) {
/* 43:65 */       return Double.valueOf(value.doubleValue());
/* 44:   */     }
/* 45:67 */     if (Float.class.isAssignableFrom(type)) {
/* 46:68 */       return Float.valueOf(value.floatValue());
/* 47:   */     }
/* 48:70 */     if (String.class.isAssignableFrom(type)) {
/* 49:71 */       return value.toString();
/* 50:   */     }
/* 51:73 */     throw unknownUnwrap(type);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public <X> Short wrap(X value, WrapperOptions options)
/* 55:   */   {
/* 56:78 */     if (value == null) {
/* 57:79 */       return null;
/* 58:   */     }
/* 59:81 */     if (Short.class.isInstance(value)) {
/* 60:82 */       return (Short)value;
/* 61:   */     }
/* 62:84 */     if (Number.class.isInstance(value)) {
/* 63:85 */       return Short.valueOf(((Number)value).shortValue());
/* 64:   */     }
/* 65:87 */     if (String.class.isInstance(value)) {
/* 66:88 */       return Short.valueOf((String)value);
/* 67:   */     }
/* 68:90 */     throw unknownWrap(value.getClass());
/* 69:   */   }
/* 70:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.ShortTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */