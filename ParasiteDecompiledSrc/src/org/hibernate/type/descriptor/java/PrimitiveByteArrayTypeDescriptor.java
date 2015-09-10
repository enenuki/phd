/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.sql.Blob;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  10:    */ import org.hibernate.type.descriptor.BinaryStream;
/*  11:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  12:    */ 
/*  13:    */ public class PrimitiveByteArrayTypeDescriptor
/*  14:    */   extends AbstractTypeDescriptor<byte[]>
/*  15:    */ {
/*  16: 42 */   public static final PrimitiveByteArrayTypeDescriptor INSTANCE = new PrimitiveByteArrayTypeDescriptor();
/*  17:    */   
/*  18:    */   public PrimitiveByteArrayTypeDescriptor()
/*  19:    */   {
/*  20: 46 */     super([B.class, ArrayMutabilityPlan.INSTANCE);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean areEqual(byte[] one, byte[] another)
/*  24:    */   {
/*  25: 51 */     return (one == another) || ((one != null) && (another != null) && (Arrays.equals(one, another)));
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int extractHashCode(byte[] bytes)
/*  29:    */   {
/*  30: 57 */     int hashCode = 1;
/*  31: 58 */     for (byte aByte : bytes) {
/*  32: 59 */       hashCode = 31 * hashCode + aByte;
/*  33:    */     }
/*  34: 61 */     return hashCode;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String toString(byte[] bytes)
/*  38:    */   {
/*  39: 65 */     StringBuffer buf = new StringBuffer();
/*  40: 66 */     for (byte aByte : bytes)
/*  41:    */     {
/*  42: 67 */       String hexStr = Integer.toHexString(aByte - -128);
/*  43: 68 */       if (hexStr.length() == 1) {
/*  44: 69 */         buf.append('0');
/*  45:    */       }
/*  46: 71 */       buf.append(hexStr);
/*  47:    */     }
/*  48: 73 */     return buf.toString();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public byte[] fromString(String string)
/*  52:    */   {
/*  53: 77 */     if (string == null) {
/*  54: 78 */       return null;
/*  55:    */     }
/*  56: 80 */     if (string.length() % 2 != 0) {
/*  57: 81 */       throw new IllegalArgumentException("The string is not a valid string representation of a binary content.");
/*  58:    */     }
/*  59: 83 */     byte[] bytes = new byte[string.length() / 2];
/*  60: 84 */     for (int i = 0; i < bytes.length; i++)
/*  61:    */     {
/*  62: 85 */       String hexStr = string.substring(i * 2, (i + 1) * 2);
/*  63: 86 */       bytes[i] = ((byte)(Integer.parseInt(hexStr, 16) + -128));
/*  64:    */     }
/*  65: 88 */     return bytes;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public <X> X unwrap(byte[] value, Class<X> type, WrapperOptions options)
/*  69:    */   {
/*  70: 93 */     if (value == null) {
/*  71: 94 */       return null;
/*  72:    */     }
/*  73: 96 */     if ([B.class.isAssignableFrom(type)) {
/*  74: 97 */       return value;
/*  75:    */     }
/*  76: 99 */     if (InputStream.class.isAssignableFrom(type)) {
/*  77:100 */       return new ByteArrayInputStream(value);
/*  78:    */     }
/*  79:102 */     if (BinaryStream.class.isAssignableFrom(type)) {
/*  80:103 */       return new BinaryStreamImpl(value);
/*  81:    */     }
/*  82:105 */     if (Blob.class.isAssignableFrom(type)) {
/*  83:106 */       return options.getLobCreator().createBlob(value);
/*  84:    */     }
/*  85:109 */     throw unknownUnwrap(type);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public <X> byte[] wrap(X value, WrapperOptions options)
/*  89:    */   {
/*  90:113 */     if (value == null) {
/*  91:114 */       return null;
/*  92:    */     }
/*  93:116 */     if ([B.class.isInstance(value)) {
/*  94:117 */       return (byte[])value;
/*  95:    */     }
/*  96:119 */     if (InputStream.class.isInstance(value)) {
/*  97:120 */       return DataHelper.extractBytes((InputStream)value);
/*  98:    */     }
/*  99:122 */     if ((Blob.class.isInstance(value)) || (DataHelper.isNClob(value.getClass()))) {
/* 100:    */       try
/* 101:    */       {
/* 102:124 */         return DataHelper.extractBytes(((Blob)value).getBinaryStream());
/* 103:    */       }
/* 104:    */       catch (SQLException e)
/* 105:    */       {
/* 106:127 */         throw new HibernateException("Unable to access lob stream", e);
/* 107:    */       }
/* 108:    */     }
/* 109:131 */     throw unknownWrap(value.getClass());
/* 110:    */   }
/* 111:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.PrimitiveByteArrayTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */