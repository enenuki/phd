/*   1:    */ package org.hibernate.engine.jdbc;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.Reader;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import java.lang.reflect.InvocationHandler;
/*   8:    */ import java.lang.reflect.Method;
/*   9:    */ import java.lang.reflect.Proxy;
/*  10:    */ import java.sql.Clob;
/*  11:    */ import java.sql.SQLException;
/*  12:    */ import org.hibernate.type.descriptor.java.DataHelper;
/*  13:    */ 
/*  14:    */ public class ClobProxy
/*  15:    */   implements InvocationHandler
/*  16:    */ {
/*  17: 47 */   private static final Class[] PROXY_INTERFACES = { Clob.class, ClobImplementer.class };
/*  18:    */   private String string;
/*  19:    */   private Reader reader;
/*  20:    */   private long length;
/*  21: 52 */   private boolean needsReset = false;
/*  22:    */   
/*  23:    */   protected ClobProxy(String string)
/*  24:    */   {
/*  25: 62 */     this.string = string;
/*  26: 63 */     this.reader = new StringReader(string);
/*  27: 64 */     this.length = string.length();
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected ClobProxy(Reader reader, long length)
/*  31:    */   {
/*  32: 75 */     this.reader = reader;
/*  33: 76 */     this.length = length;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected long getLength()
/*  37:    */   {
/*  38: 80 */     return this.length;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected InputStream getAsciiStream()
/*  42:    */     throws SQLException
/*  43:    */   {
/*  44: 84 */     resetIfNeeded();
/*  45: 85 */     return new ReaderInputStream(this.reader);
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected Reader getCharacterStream()
/*  49:    */     throws SQLException
/*  50:    */   {
/*  51: 89 */     resetIfNeeded();
/*  52: 90 */     return this.reader;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected String getSubString(long start, int length)
/*  56:    */   {
/*  57: 94 */     if (this.string == null) {
/*  58: 95 */       throw new UnsupportedOperationException("Clob was not created from string; cannot substring");
/*  59:    */     }
/*  60: 98 */     int endIndex = Math.min((int)start + length, this.string.length());
/*  61: 99 */     return this.string.substring((int)start, endIndex);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Object invoke(Object proxy, Method method, Object[] args)
/*  65:    */     throws Throwable
/*  66:    */   {
/*  67:110 */     String methodName = method.getName();
/*  68:111 */     int argCount = method.getParameterTypes().length;
/*  69:113 */     if (("length".equals(methodName)) && (argCount == 0)) {
/*  70:114 */       return Long.valueOf(getLength());
/*  71:    */     }
/*  72:116 */     if (("getAsciiStream".equals(methodName)) && (argCount == 0)) {
/*  73:117 */       return getAsciiStream();
/*  74:    */     }
/*  75:119 */     if ("getCharacterStream".equals(methodName))
/*  76:    */     {
/*  77:120 */       if (argCount == 0) {
/*  78:121 */         return getCharacterStream();
/*  79:    */       }
/*  80:123 */       if (argCount == 2)
/*  81:    */       {
/*  82:124 */         long start = ((Long)args[0]).longValue();
/*  83:125 */         if (start < 1L) {
/*  84:126 */           throw new SQLException("Start position 1-based; must be 1 or more.");
/*  85:    */         }
/*  86:128 */         if (start > getLength()) {
/*  87:129 */           throw new SQLException("Start position [" + start + "] cannot exceed overall CLOB length [" + getLength() + "]");
/*  88:    */         }
/*  89:131 */         int length = ((Integer)args[1]).intValue();
/*  90:132 */         if (length < 0) {
/*  91:135 */           throw new SQLException("Length must be great-than-or-equal to zero.");
/*  92:    */         }
/*  93:137 */         return DataHelper.subStream(getCharacterStream(), start - 1L, length);
/*  94:    */       }
/*  95:    */     }
/*  96:140 */     if (("getSubString".equals(methodName)) && (argCount == 2))
/*  97:    */     {
/*  98:141 */       long start = ((Long)args[0]).longValue();
/*  99:142 */       if (start < 1L) {
/* 100:143 */         throw new SQLException("Start position 1-based; must be 1 or more.");
/* 101:    */       }
/* 102:145 */       if (start > getLength()) {
/* 103:146 */         throw new SQLException("Start position [" + start + "] cannot exceed overall CLOB length [" + getLength() + "]");
/* 104:    */       }
/* 105:148 */       int length = ((Integer)args[1]).intValue();
/* 106:149 */       if (length < 0) {
/* 107:150 */         throw new SQLException("Length must be great-than-or-equal to zero.");
/* 108:    */       }
/* 109:152 */       return getSubString(start - 1L, length);
/* 110:    */     }
/* 111:154 */     if (("free".equals(methodName)) && (argCount == 0))
/* 112:    */     {
/* 113:155 */       this.reader.close();
/* 114:156 */       return null;
/* 115:    */     }
/* 116:158 */     if (("toString".equals(methodName)) && (argCount == 0)) {
/* 117:159 */       return toString();
/* 118:    */     }
/* 119:161 */     if (("equals".equals(methodName)) && (argCount == 1)) {
/* 120:162 */       return Boolean.valueOf(proxy == args[0]);
/* 121:    */     }
/* 122:164 */     if (("hashCode".equals(methodName)) && (argCount == 0)) {
/* 123:165 */       return Integer.valueOf(hashCode());
/* 124:    */     }
/* 125:168 */     throw new UnsupportedOperationException("Clob may not be manipulated from creating session");
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected void resetIfNeeded()
/* 129:    */     throws SQLException
/* 130:    */   {
/* 131:    */     try
/* 132:    */     {
/* 133:173 */       if (this.needsReset) {
/* 134:174 */         this.reader.reset();
/* 135:    */       }
/* 136:    */     }
/* 137:    */     catch (IOException ioe)
/* 138:    */     {
/* 139:178 */       throw new SQLException("could not reset reader", ioe);
/* 140:    */     }
/* 141:180 */     this.needsReset = true;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static Clob generateProxy(String string)
/* 145:    */   {
/* 146:191 */     return (Clob)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new ClobProxy(string));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static Clob generateProxy(Reader reader, long length)
/* 150:    */   {
/* 151:207 */     return (Clob)Proxy.newProxyInstance(getProxyClassLoader(), PROXY_INTERFACES, new ClobProxy(reader, length));
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected static ClassLoader getProxyClassLoader()
/* 155:    */   {
/* 156:221 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 157:222 */     if (cl == null) {
/* 158:223 */       cl = ClobImplementer.class.getClassLoader();
/* 159:    */     }
/* 160:225 */     return cl;
/* 161:    */   }
/* 162:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.ClobProxy
 * JD-Core Version:    0.7.0.1
 */