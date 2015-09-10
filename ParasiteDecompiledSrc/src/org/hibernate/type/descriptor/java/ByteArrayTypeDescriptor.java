/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.sql.Blob;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.engine.jdbc.LobCreator;
/*   9:    */ import org.hibernate.type.descriptor.BinaryStream;
/*  10:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  11:    */ 
/*  12:    */ public class ByteArrayTypeDescriptor
/*  13:    */   extends AbstractTypeDescriptor<Byte[]>
/*  14:    */ {
/*  15: 41 */   public static final ByteArrayTypeDescriptor INSTANCE = new ByteArrayTypeDescriptor();
/*  16:    */   
/*  17:    */   public ByteArrayTypeDescriptor()
/*  18:    */   {
/*  19: 45 */     super([Ljava.lang.Byte.class, ArrayMutabilityPlan.INSTANCE);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String toString(Byte[] bytes)
/*  23:    */   {
/*  24: 50 */     StringBuffer buf = new StringBuffer();
/*  25: 51 */     for (Byte aByte : bytes)
/*  26:    */     {
/*  27: 52 */       String hexStr = Integer.toHexString(aByte.byteValue() - -128);
/*  28: 53 */       if (hexStr.length() == 1) {
/*  29: 54 */         buf.append('0');
/*  30:    */       }
/*  31: 56 */       buf.append(hexStr);
/*  32:    */     }
/*  33: 58 */     return buf.toString();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Byte[] fromString(String string)
/*  37:    */   {
/*  38: 63 */     if (string == null) {
/*  39: 64 */       return null;
/*  40:    */     }
/*  41: 66 */     if (string.length() % 2 != 0) {
/*  42: 67 */       throw new IllegalArgumentException("The string is not a valid string representation of a binary content.");
/*  43:    */     }
/*  44: 69 */     Byte[] bytes = new Byte[string.length() / 2];
/*  45: 70 */     for (int i = 0; i < bytes.length; i++)
/*  46:    */     {
/*  47: 71 */       String hexStr = string.substring(i * 2, (i + 1) * 2);
/*  48: 72 */       bytes[i] = Byte.valueOf((byte)(Integer.parseInt(hexStr, 16) + -128));
/*  49:    */     }
/*  50: 74 */     return bytes;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public <X> X unwrap(Byte[] value, Class<X> type, WrapperOptions options)
/*  54:    */   {
/*  55: 79 */     if (value == null) {
/*  56: 80 */       return null;
/*  57:    */     }
/*  58: 82 */     if ([Ljava.lang.Byte.class.isAssignableFrom(type)) {
/*  59: 83 */       return value;
/*  60:    */     }
/*  61: 85 */     if ([B.class.isAssignableFrom(type)) {
/*  62: 86 */       return unwrapBytes(value);
/*  63:    */     }
/*  64: 88 */     if (InputStream.class.isAssignableFrom(type)) {
/*  65: 89 */       return new ByteArrayInputStream(unwrapBytes(value));
/*  66:    */     }
/*  67: 91 */     if (BinaryStream.class.isAssignableFrom(type)) {
/*  68: 92 */       return new BinaryStreamImpl(unwrapBytes(value));
/*  69:    */     }
/*  70: 94 */     if (Blob.class.isAssignableFrom(type)) {
/*  71: 95 */       return options.getLobCreator().createBlob(unwrapBytes(value));
/*  72:    */     }
/*  73: 98 */     throw unknownUnwrap(type);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public <X> Byte[] wrap(X value, WrapperOptions options)
/*  77:    */   {
/*  78:102 */     if (value == null) {
/*  79:103 */       return null;
/*  80:    */     }
/*  81:105 */     if ([Ljava.lang.Byte.class.isInstance(value)) {
/*  82:106 */       return (Byte[])value;
/*  83:    */     }
/*  84:108 */     if ([B.class.isInstance(value)) {
/*  85:109 */       return wrapBytes((byte[])value);
/*  86:    */     }
/*  87:111 */     if (InputStream.class.isInstance(value)) {
/*  88:112 */       return wrapBytes(DataHelper.extractBytes((InputStream)value));
/*  89:    */     }
/*  90:114 */     if ((Blob.class.isInstance(value)) || (DataHelper.isNClob(value.getClass()))) {
/*  91:    */       try
/*  92:    */       {
/*  93:116 */         return wrapBytes(DataHelper.extractBytes(((Blob)value).getBinaryStream()));
/*  94:    */       }
/*  95:    */       catch (SQLException e)
/*  96:    */       {
/*  97:119 */         throw new HibernateException("Unable to access lob stream", e);
/*  98:    */       }
/*  99:    */     }
/* 100:123 */     throw unknownWrap(value.getClass());
/* 101:    */   }
/* 102:    */   
/* 103:    */   private Byte[] wrapBytes(byte[] bytes)
/* 104:    */   {
/* 105:128 */     if (bytes == null) {
/* 106:129 */       return null;
/* 107:    */     }
/* 108:131 */     Byte[] result = new Byte[bytes.length];
/* 109:132 */     for (int i = 0; i < bytes.length; i++) {
/* 110:133 */       result[i] = Byte.valueOf(bytes[i]);
/* 111:    */     }
/* 112:135 */     return result;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private byte[] unwrapBytes(Byte[] bytes)
/* 116:    */   {
/* 117:140 */     if (bytes == null) {
/* 118:141 */       return null;
/* 119:    */     }
/* 120:143 */     byte[] result = new byte[bytes.length];
/* 121:144 */     for (int i = 0; i < bytes.length; i++) {
/* 122:145 */       result[i] = bytes[i].byteValue();
/* 123:    */     }
/* 124:147 */     return result;
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.ByteArrayTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */