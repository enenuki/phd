/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import org.hibernate.internal.util.SerializationHelper;
/*   7:    */ import org.hibernate.type.descriptor.BinaryStream;
/*   8:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*   9:    */ 
/*  10:    */ public class SerializableTypeDescriptor<T extends Serializable>
/*  11:    */   extends AbstractTypeDescriptor<T>
/*  12:    */ {
/*  13:    */   public static class SerializableMutabilityPlan<S extends Serializable>
/*  14:    */     extends MutableMutabilityPlan<S>
/*  15:    */   {
/*  16:    */     private final Class<S> type;
/*  17: 45 */     public static final SerializableMutabilityPlan<Serializable> INSTANCE = new SerializableMutabilityPlan(Serializable.class);
/*  18:    */     
/*  19:    */     public SerializableMutabilityPlan(Class<S> type)
/*  20:    */     {
/*  21: 49 */       this.type = type;
/*  22:    */     }
/*  23:    */     
/*  24:    */     public S deepCopyNotNull(S value)
/*  25:    */     {
/*  26: 55 */       return (Serializable)SerializationHelper.clone(value);
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public SerializableTypeDescriptor(Class<T> type)
/*  31:    */   {
/*  32: 62 */     super(type, Serializable.class.equals(type) ? SerializableMutabilityPlan.INSTANCE : new SerializableMutabilityPlan(type));
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String toString(T value)
/*  36:    */   {
/*  37: 71 */     return PrimitiveByteArrayTypeDescriptor.INSTANCE.toString(toBytes(value));
/*  38:    */   }
/*  39:    */   
/*  40:    */   public T fromString(String string)
/*  41:    */   {
/*  42: 75 */     return fromBytes(PrimitiveByteArrayTypeDescriptor.INSTANCE.fromString(string));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean areEqual(T one, T another)
/*  46:    */   {
/*  47: 80 */     if (one == another) {
/*  48: 81 */       return true;
/*  49:    */     }
/*  50: 83 */     if ((one == null) || (another == null)) {
/*  51: 84 */       return false;
/*  52:    */     }
/*  53: 86 */     return (one.equals(another)) || (PrimitiveByteArrayTypeDescriptor.INSTANCE.areEqual(toBytes(one), toBytes(another)));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int extractHashCode(T value)
/*  57:    */   {
/*  58: 92 */     return PrimitiveByteArrayTypeDescriptor.INSTANCE.extractHashCode(toBytes(value));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public <X> X unwrap(T value, Class<X> type, WrapperOptions options)
/*  62:    */   {
/*  63: 97 */     if (value == null) {
/*  64: 98 */       return null;
/*  65:    */     }
/*  66:100 */     if ([B.class.isAssignableFrom(type)) {
/*  67:101 */       return toBytes(value);
/*  68:    */     }
/*  69:103 */     if (InputStream.class.isAssignableFrom(type)) {
/*  70:104 */       return new ByteArrayInputStream(toBytes(value));
/*  71:    */     }
/*  72:106 */     if (BinaryStream.class.isAssignableFrom(type)) {
/*  73:107 */       return new BinaryStreamImpl(toBytes(value));
/*  74:    */     }
/*  75:109 */     throw unknownUnwrap(type);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public <X> T wrap(X value, WrapperOptions options)
/*  79:    */   {
/*  80:113 */     if (value == null) {
/*  81:114 */       return null;
/*  82:    */     }
/*  83:116 */     if ([B.class.isInstance(value)) {
/*  84:117 */       return fromBytes((byte[])value);
/*  85:    */     }
/*  86:119 */     if (InputStream.class.isInstance(value)) {
/*  87:120 */       return fromBytes(DataHelper.extractBytes((InputStream)value));
/*  88:    */     }
/*  89:122 */     throw unknownWrap(value.getClass());
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected byte[] toBytes(T value)
/*  93:    */   {
/*  94:126 */     return SerializationHelper.serialize(value);
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected T fromBytes(byte[] bytes)
/*  98:    */   {
/*  99:131 */     return (Serializable)SerializationHelper.deserialize(bytes, getJavaTypeClass().getClassLoader());
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.SerializableTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */