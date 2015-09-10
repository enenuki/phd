/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.UUID;
/*   5:    */ import org.hibernate.internal.util.BytesHelper;
/*   6:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*   7:    */ 
/*   8:    */ public class UUIDTypeDescriptor
/*   9:    */   extends AbstractTypeDescriptor<UUID>
/*  10:    */ {
/*  11: 38 */   public static final UUIDTypeDescriptor INSTANCE = new UUIDTypeDescriptor();
/*  12:    */   
/*  13:    */   public UUIDTypeDescriptor()
/*  14:    */   {
/*  15: 41 */     super(UUID.class);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public String toString(UUID value)
/*  19:    */   {
/*  20: 45 */     return ToStringTransformer.INSTANCE.transform(value);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public UUID fromString(String string)
/*  24:    */   {
/*  25: 49 */     return ToStringTransformer.INSTANCE.parse(string);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public <X> X unwrap(UUID value, Class<X> type, WrapperOptions options)
/*  29:    */   {
/*  30: 54 */     if (value == null) {
/*  31: 55 */       return null;
/*  32:    */     }
/*  33: 57 */     if (UUID.class.isAssignableFrom(type)) {
/*  34: 58 */       return PassThroughTransformer.INSTANCE.transform(value);
/*  35:    */     }
/*  36: 60 */     if (String.class.isAssignableFrom(type)) {
/*  37: 61 */       return ToStringTransformer.INSTANCE.transform(value);
/*  38:    */     }
/*  39: 63 */     if ([B.class.isAssignableFrom(type)) {
/*  40: 64 */       return ToBytesTransformer.INSTANCE.transform(value);
/*  41:    */     }
/*  42: 66 */     throw unknownUnwrap(type);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public <X> UUID wrap(X value, WrapperOptions options)
/*  46:    */   {
/*  47: 70 */     if (value == null) {
/*  48: 71 */       return null;
/*  49:    */     }
/*  50: 73 */     if (UUID.class.isInstance(value)) {
/*  51: 74 */       return PassThroughTransformer.INSTANCE.parse(value);
/*  52:    */     }
/*  53: 76 */     if (String.class.isInstance(value)) {
/*  54: 77 */       return ToStringTransformer.INSTANCE.parse(value);
/*  55:    */     }
/*  56: 79 */     if ([B.class.isInstance(value)) {
/*  57: 80 */       return ToBytesTransformer.INSTANCE.parse(value);
/*  58:    */     }
/*  59: 82 */     throw unknownWrap(value.getClass());
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static abstract interface ValueTransformer
/*  63:    */   {
/*  64:    */     public abstract Serializable transform(UUID paramUUID);
/*  65:    */     
/*  66:    */     public abstract UUID parse(Object paramObject);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static class PassThroughTransformer
/*  70:    */     implements UUIDTypeDescriptor.ValueTransformer
/*  71:    */   {
/*  72: 91 */     public static final PassThroughTransformer INSTANCE = new PassThroughTransformer();
/*  73:    */     
/*  74:    */     public UUID transform(UUID uuid)
/*  75:    */     {
/*  76: 94 */       return uuid;
/*  77:    */     }
/*  78:    */     
/*  79:    */     public UUID parse(Object value)
/*  80:    */     {
/*  81: 98 */       return (UUID)value;
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static class ToStringTransformer
/*  86:    */     implements UUIDTypeDescriptor.ValueTransformer
/*  87:    */   {
/*  88:103 */     public static final ToStringTransformer INSTANCE = new ToStringTransformer();
/*  89:    */     
/*  90:    */     public String transform(UUID uuid)
/*  91:    */     {
/*  92:106 */       return uuid.toString();
/*  93:    */     }
/*  94:    */     
/*  95:    */     public UUID parse(Object value)
/*  96:    */     {
/*  97:110 */       return UUID.fromString((String)value);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static class ToBytesTransformer
/* 102:    */     implements UUIDTypeDescriptor.ValueTransformer
/* 103:    */   {
/* 104:115 */     public static final ToBytesTransformer INSTANCE = new ToBytesTransformer();
/* 105:    */     
/* 106:    */     public byte[] transform(UUID uuid)
/* 107:    */     {
/* 108:118 */       byte[] bytes = new byte[16];
/* 109:119 */       System.arraycopy(BytesHelper.fromLong(uuid.getMostSignificantBits()), 0, bytes, 0, 8);
/* 110:120 */       System.arraycopy(BytesHelper.fromLong(uuid.getLeastSignificantBits()), 0, bytes, 8, 8);
/* 111:121 */       return bytes;
/* 112:    */     }
/* 113:    */     
/* 114:    */     public UUID parse(Object value)
/* 115:    */     {
/* 116:125 */       byte[] msb = new byte[8];
/* 117:126 */       byte[] lsb = new byte[8];
/* 118:127 */       System.arraycopy(value, 0, msb, 0, 8);
/* 119:128 */       System.arraycopy(value, 8, lsb, 0, 8);
/* 120:129 */       return new UUID(BytesHelper.asLong(msb), BytesHelper.asLong(lsb));
/* 121:    */     }
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.UUIDTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */