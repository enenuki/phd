/*   1:    */ package org.hibernate.engine.jdbc;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.lang.reflect.InvocationHandler;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Proxy;
/*   8:    */ import java.sql.Blob;
/*   9:    */ import java.sql.SQLException;
/*  10:    */ import org.hibernate.type.descriptor.java.BinaryStreamImpl;
/*  11:    */ import org.hibernate.type.descriptor.java.DataHelper;
/*  12:    */ 
/*  13:    */ public class BlobProxy
/*  14:    */   implements InvocationHandler
/*  15:    */ {
/*  16: 45 */   private static final Class[] PROXY_INTERFACES = { Blob.class, BlobImplementer.class };
/*  17:    */   private InputStream stream;
/*  18:    */   private long length;
/*  19: 49 */   private boolean needsReset = false;
/*  20:    */   
/*  21:    */   private BlobProxy(byte[] bytes)
/*  22:    */   {
/*  23: 58 */     this.stream = new BinaryStreamImpl(bytes);
/*  24: 59 */     this.length = bytes.length;
/*  25:    */   }
/*  26:    */   
/*  27:    */   private BlobProxy(InputStream stream, long length)
/*  28:    */   {
/*  29: 70 */     this.stream = stream;
/*  30: 71 */     this.length = length;
/*  31:    */   }
/*  32:    */   
/*  33:    */   private long getLength()
/*  34:    */   {
/*  35: 75 */     return this.length;
/*  36:    */   }
/*  37:    */   
/*  38:    */   private InputStream getStream()
/*  39:    */     throws SQLException
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 80 */       if (this.needsReset) {
/*  44: 81 */         this.stream.reset();
/*  45:    */       }
/*  46:    */     }
/*  47:    */     catch (IOException ioe)
/*  48:    */     {
/*  49: 85 */       throw new SQLException("could not reset reader");
/*  50:    */     }
/*  51: 87 */     this.needsReset = true;
/*  52: 88 */     return this.stream;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Object invoke(Object proxy, Method method, Object[] args)
/*  56:    */     throws Throwable
/*  57:    */   {
/*  58: 99 */     String methodName = method.getName();
/*  59:100 */     int argCount = method.getParameterTypes().length;
/*  60:102 */     if (("length".equals(methodName)) && (argCount == 0)) {
/*  61:103 */       return Long.valueOf(getLength());
/*  62:    */     }
/*  63:105 */     if ("getBinaryStream".equals(methodName))
/*  64:    */     {
/*  65:106 */       if (argCount == 0) {
/*  66:107 */         return getStream();
/*  67:    */       }
/*  68:109 */       if (argCount == 2)
/*  69:    */       {
/*  70:110 */         long start = ((Long)args[0]).longValue();
/*  71:111 */         if (start < 1L) {
/*  72:112 */           throw new SQLException("Start position 1-based; must be 1 or more.");
/*  73:    */         }
/*  74:114 */         if (start > getLength()) {
/*  75:115 */           throw new SQLException("Start position [" + start + "] cannot exceed overall CLOB length [" + getLength() + "]");
/*  76:    */         }
/*  77:117 */         int length = ((Integer)args[1]).intValue();
/*  78:118 */         if (length < 0) {
/*  79:121 */           throw new SQLException("Length must be great-than-or-equal to zero.");
/*  80:    */         }
/*  81:123 */         return DataHelper.subStream(getStream(), start - 1L, length);
/*  82:    */       }
/*  83:    */     }
/*  84:126 */     if (("getBytes".equals(methodName)) && 
/*  85:127 */       (argCount == 2))
/*  86:    */     {
/*  87:128 */       long start = ((Long)args[0]).longValue();
/*  88:129 */       if (start < 1L) {
/*  89:130 */         throw new SQLException("Start position 1-based; must be 1 or more.");
/*  90:    */       }
/*  91:132 */       int length = ((Integer)args[1]).intValue();
/*  92:133 */       if (length < 0) {
/*  93:134 */         throw new SQLException("Length must be great-than-or-equal to zero.");
/*  94:    */       }
/*  95:136 */       return DataHelper.extractBytes(getStream(), start - 1L, length);
/*  96:    */     }
/*  97:139 */     if (("free".equals(methodName)) && (argCount == 0))
/*  98:    */     {
/*  99:140 */       this.stream.close();
/* 100:141 */       return null;
/* 101:    */     }
/* 102:143 */     if (("toString".equals(methodName)) && (argCount == 0)) {
/* 103:144 */       return toString();
/* 104:    */     }
/* 105:146 */     if (("equals".equals(methodName)) && (argCount == 1)) {
/* 106:147 */       return Boolean.valueOf(proxy == args[0]);
/* 107:    */     }
/* 108:149 */     if (("hashCode".equals(methodName)) && (argCount == 0)) {
/* 109:150 */       return Integer.valueOf(hashCode());
/* 110:    */     }
/* 111:153 */     throw new UnsupportedOperationException("Blob may not be manipulated from creating session");
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static Blob generateProxy(byte[] bytes)
/* 115:    */   {
/* 116:164 */     return (Blob)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new BlobProxy(bytes));
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static Blob generateProxy(InputStream stream, long length)
/* 120:    */   {
/* 121:180 */     return (Blob)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new BlobProxy(stream, length));
/* 122:    */   }
/* 123:    */   
/* 124:    */   private static ClassLoader getProxyClassLoader()
/* 125:    */   {
/* 126:194 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 127:195 */     if (cl == null) {
/* 128:196 */       cl = BlobImplementer.class.getClassLoader();
/* 129:    */     }
/* 130:198 */     return cl;
/* 131:    */   }
/* 132:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.BlobProxy
 * JD-Core Version:    0.7.0.1
 */