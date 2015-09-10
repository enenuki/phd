/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  4:   */ 
/*  5:   */ public class ByteTypeDescriptor
/*  6:   */   extends AbstractTypeDescriptor<Byte>
/*  7:   */ {
/*  8:34 */   public static final ByteTypeDescriptor INSTANCE = new ByteTypeDescriptor();
/*  9:   */   
/* 10:   */   public ByteTypeDescriptor()
/* 11:   */   {
/* 12:37 */     super(Byte.class);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String toString(Byte value)
/* 16:   */   {
/* 17:41 */     return value == null ? null : value.toString();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Byte fromString(String string)
/* 21:   */   {
/* 22:45 */     return Byte.valueOf(string);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public <X> X unwrap(Byte value, Class<X> type, WrapperOptions options)
/* 26:   */   {
/* 27:50 */     if (value == null) {
/* 28:51 */       return null;
/* 29:   */     }
/* 30:53 */     if (Byte.class.isAssignableFrom(type)) {
/* 31:54 */       return value;
/* 32:   */     }
/* 33:56 */     if (Short.class.isAssignableFrom(type)) {
/* 34:57 */       return Short.valueOf(value.shortValue());
/* 35:   */     }
/* 36:59 */     if (Integer.class.isAssignableFrom(type)) {
/* 37:60 */       return Integer.valueOf(value.intValue());
/* 38:   */     }
/* 39:62 */     if (Long.class.isAssignableFrom(type)) {
/* 40:63 */       return Long.valueOf(value.longValue());
/* 41:   */     }
/* 42:65 */     if (Double.class.isAssignableFrom(type)) {
/* 43:66 */       return Double.valueOf(value.doubleValue());
/* 44:   */     }
/* 45:68 */     if (Float.class.isAssignableFrom(type)) {
/* 46:69 */       return Float.valueOf(value.floatValue());
/* 47:   */     }
/* 48:71 */     if (String.class.isAssignableFrom(type)) {
/* 49:72 */       return value.toString();
/* 50:   */     }
/* 51:74 */     throw unknownUnwrap(type);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public <X> Byte wrap(X value, WrapperOptions options)
/* 55:   */   {
/* 56:79 */     if (value == null) {
/* 57:80 */       return null;
/* 58:   */     }
/* 59:82 */     if (Byte.class.isInstance(value)) {
/* 60:83 */       return (Byte)value;
/* 61:   */     }
/* 62:85 */     if (Number.class.isInstance(value)) {
/* 63:86 */       return Byte.valueOf(((Number)value).byteValue());
/* 64:   */     }
/* 65:88 */     if (String.class.isInstance(value)) {
/* 66:89 */       return Byte.valueOf((String)value);
/* 67:   */     }
/* 68:91 */     throw unknownWrap(value.getClass());
/* 69:   */   }
/* 70:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.ByteTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */