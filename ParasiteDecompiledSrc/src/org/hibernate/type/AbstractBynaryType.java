/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.InputStream;
/*   7:    */ import java.sql.PreparedStatement;
/*   8:    */ import java.sql.ResultSet;
/*   9:    */ import java.sql.SQLException;
/*  10:    */ import java.util.Arrays;
/*  11:    */ import java.util.Comparator;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.cfg.Environment;
/*  14:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  15:    */ 
/*  16:    */ /**
/*  17:    */  * @deprecated
/*  18:    */  */
/*  19:    */ public abstract class AbstractBynaryType
/*  20:    */   extends MutableType
/*  21:    */   implements VersionType, Comparator
/*  22:    */ {
/*  23:    */   protected abstract Object toExternalFormat(byte[] paramArrayOfByte);
/*  24:    */   
/*  25:    */   protected abstract byte[] toInternalFormat(Object paramObject);
/*  26:    */   
/*  27:    */   public void set(PreparedStatement st, Object value, int index)
/*  28:    */     throws HibernateException, SQLException
/*  29:    */   {
/*  30: 60 */     byte[] internalValue = toInternalFormat(value);
/*  31: 61 */     if (Environment.useStreamsForBinary()) {
/*  32: 62 */       st.setBinaryStream(index, new ByteArrayInputStream(internalValue), internalValue.length);
/*  33:    */     } else {
/*  34: 65 */       st.setBytes(index, internalValue);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Object get(ResultSet rs, String name)
/*  39:    */     throws HibernateException, SQLException
/*  40:    */   {
/*  41: 71 */     if (Environment.useStreamsForBinary())
/*  42:    */     {
/*  43: 73 */       InputStream inputStream = rs.getBinaryStream(name);
/*  44: 75 */       if (inputStream == null) {
/*  45: 75 */         return toExternalFormat(null);
/*  46:    */       }
/*  47: 77 */       ByteArrayOutputStream outputStream = new ByteArrayOutputStream(2048);
/*  48: 78 */       byte[] buffer = new byte[2048];
/*  49:    */       try
/*  50:    */       {
/*  51:    */         for (;;)
/*  52:    */         {
/*  53: 82 */           int amountRead = inputStream.read(buffer);
/*  54: 83 */           if (amountRead == -1) {
/*  55:    */             break;
/*  56:    */           }
/*  57: 86 */           outputStream.write(buffer, 0, amountRead);
/*  58:    */         }
/*  59: 89 */         inputStream.close();
/*  60: 90 */         outputStream.close();
/*  61:    */       }
/*  62:    */       catch (IOException ioe)
/*  63:    */       {
/*  64: 93 */         throw new HibernateException("IOException occurred reading a binary value", ioe);
/*  65:    */       }
/*  66: 96 */       return toExternalFormat(outputStream.toByteArray());
/*  67:    */     }
/*  68:100 */     return toExternalFormat(rs.getBytes(name));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int sqlType()
/*  72:    */   {
/*  73:105 */     return -3;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object seed(SessionImplementor session)
/*  77:    */   {
/*  78:114 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Object next(Object current, SessionImplementor session)
/*  82:    */   {
/*  83:118 */     return current;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Comparator getComparator()
/*  87:    */   {
/*  88:122 */     return this;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isEqual(Object x, Object y)
/*  92:    */   {
/*  93:129 */     return (x == y) || ((x != null) && (y != null) && (Arrays.equals(toInternalFormat(x), toInternalFormat(y))));
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getHashCode(Object x)
/*  97:    */   {
/*  98:133 */     byte[] bytes = toInternalFormat(x);
/*  99:134 */     int hashCode = 1;
/* 100:135 */     for (int j = 0; j < bytes.length; j++) {
/* 101:136 */       hashCode = 31 * hashCode + bytes[j];
/* 102:    */     }
/* 103:138 */     return hashCode;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public int compare(Object x, Object y)
/* 107:    */   {
/* 108:142 */     byte[] xbytes = toInternalFormat(x);
/* 109:143 */     byte[] ybytes = toInternalFormat(y);
/* 110:144 */     if (xbytes.length < ybytes.length) {
/* 111:144 */       return -1;
/* 112:    */     }
/* 113:145 */     if (xbytes.length > ybytes.length) {
/* 114:145 */       return 1;
/* 115:    */     }
/* 116:146 */     for (int i = 0; i < xbytes.length; i++)
/* 117:    */     {
/* 118:147 */       if (xbytes[i] < ybytes[i]) {
/* 119:147 */         return -1;
/* 120:    */       }
/* 121:148 */       if (xbytes[i] > ybytes[i]) {
/* 122:148 */         return 1;
/* 123:    */       }
/* 124:    */     }
/* 125:150 */     return 0;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public abstract String getName();
/* 129:    */   
/* 130:    */   public String toString(Object val)
/* 131:    */   {
/* 132:156 */     byte[] bytes = toInternalFormat(val);
/* 133:157 */     StringBuffer buf = new StringBuffer();
/* 134:158 */     for (int i = 0; i < bytes.length; i++)
/* 135:    */     {
/* 136:159 */       String hexStr = Integer.toHexString(bytes[i] - -128);
/* 137:160 */       if (hexStr.length() == 1) {
/* 138:160 */         buf.append('0');
/* 139:    */       }
/* 140:161 */       buf.append(hexStr);
/* 141:    */     }
/* 142:163 */     return buf.toString();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Object deepCopyNotNull(Object value)
/* 146:    */   {
/* 147:167 */     byte[] bytes = toInternalFormat(value);
/* 148:168 */     byte[] result = new byte[bytes.length];
/* 149:169 */     System.arraycopy(bytes, 0, result, 0, bytes.length);
/* 150:170 */     return toExternalFormat(result);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Object fromStringValue(String xml)
/* 154:    */     throws HibernateException
/* 155:    */   {
/* 156:174 */     if (xml == null) {
/* 157:175 */       return null;
/* 158:    */     }
/* 159:176 */     if (xml.length() % 2 != 0) {
/* 160:177 */       throw new IllegalArgumentException("The string is not a valid xml representation of a binary content.");
/* 161:    */     }
/* 162:178 */     byte[] bytes = new byte[xml.length() / 2];
/* 163:179 */     for (int i = 0; i < bytes.length; i++)
/* 164:    */     {
/* 165:180 */       String hexStr = xml.substring(i * 2, (i + 1) * 2);
/* 166:181 */       bytes[i] = ((byte)(Integer.parseInt(hexStr, 16) + -128));
/* 167:    */     }
/* 168:183 */     return toExternalFormat(bytes);
/* 169:    */   }
/* 170:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AbstractBynaryType
 * JD-Core Version:    0.7.0.1
 */