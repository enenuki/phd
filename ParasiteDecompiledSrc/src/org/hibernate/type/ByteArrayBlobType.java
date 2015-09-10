/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.sql.Blob;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.dom4j.Node;
/*  10:    */ import org.hibernate.Hibernate;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.dialect.Dialect;
/*  13:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  14:    */ import org.hibernate.engine.spi.Mapping;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  18:    */ 
/*  19:    */ @Deprecated
/*  20:    */ public class ByteArrayBlobType
/*  21:    */   extends AbstractLobType
/*  22:    */ {
/*  23: 52 */   private static final int[] TYPES = { 2004 };
/*  24:    */   
/*  25:    */   public int[] sqlTypes(Mapping mapping)
/*  26:    */   {
/*  27: 55 */     return TYPES;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory)
/*  31:    */   {
/*  32: 60 */     if (x == y) {
/*  33: 60 */       return true;
/*  34:    */     }
/*  35: 61 */     if ((x == null) || (y == null)) {
/*  36: 61 */       return false;
/*  37:    */     }
/*  38: 62 */     if ((x instanceof Byte[]))
/*  39:    */     {
/*  40: 63 */       Object[] o1 = (Object[])x;
/*  41: 64 */       Object[] o2 = (Object[])y;
/*  42: 65 */       return ArrayHelper.isEquals(o1, o2);
/*  43:    */     }
/*  44: 68 */     byte[] c1 = (byte[])x;
/*  45: 69 */     byte[] c2 = (byte[])y;
/*  46: 70 */     return ArrayHelper.isEquals(c1, c2);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public int getHashCode(Object x, SessionFactoryImplementor factory)
/*  50:    */   {
/*  51: 75 */     if ((x instanceof Character[]))
/*  52:    */     {
/*  53: 76 */       Object[] o = (Object[])x;
/*  54: 77 */       return ArrayHelper.hash(o);
/*  55:    */     }
/*  56: 80 */     byte[] c = (byte[])x;
/*  57: 81 */     return ArrayHelper.hash(c);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/*  61:    */     throws HibernateException
/*  62:    */   {
/*  63: 87 */     if (value == null) {
/*  64: 87 */       return null;
/*  65:    */     }
/*  66: 88 */     if ((value instanceof Byte[]))
/*  67:    */     {
/*  68: 89 */       Byte[] array = (Byte[])value;
/*  69: 90 */       int length = array.length;
/*  70: 91 */       Byte[] copy = new Byte[length];
/*  71: 92 */       for (int index = 0; index < length; index++) {
/*  72: 93 */         copy[index] = Byte.valueOf(array[index].byteValue());
/*  73:    */       }
/*  74: 95 */       return copy;
/*  75:    */     }
/*  76: 98 */     byte[] array = (byte[])value;
/*  77: 99 */     int length = array.length;
/*  78:100 */     byte[] copy = new byte[length];
/*  79:101 */     System.arraycopy(array, 0, copy, 0, length);
/*  80:102 */     return copy;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Class getReturnedClass()
/*  84:    */   {
/*  85:107 */     return [Ljava.lang.Byte.class;
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected Object get(ResultSet rs, String name)
/*  89:    */     throws SQLException
/*  90:    */   {
/*  91:111 */     Blob blob = rs.getBlob(name);
/*  92:112 */     if (rs.wasNull()) {
/*  93:112 */       return null;
/*  94:    */     }
/*  95:113 */     int length = (int)blob.length();
/*  96:114 */     byte[] primaryResult = blob.getBytes(1L, length);
/*  97:115 */     return wrap(primaryResult);
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected void set(PreparedStatement st, Object value, int index, SessionImplementor session)
/* 101:    */     throws SQLException
/* 102:    */   {
/* 103:119 */     if (value == null)
/* 104:    */     {
/* 105:120 */       st.setNull(index, sqlTypes(null)[0]);
/* 106:    */     }
/* 107:    */     else
/* 108:    */     {
/* 109:123 */       byte[] toSet = unWrap(value);
/* 110:124 */       boolean useInputStream = session.getFactory().getDialect().useInputStreamToInsertBlob();
/* 111:126 */       if (useInputStream) {
/* 112:127 */         st.setBinaryStream(index, new ByteArrayInputStream(toSet), toSet.length);
/* 113:    */       } else {
/* 114:130 */         st.setBlob(index, Hibernate.getLobCreator(session).createBlob(toSet));
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 120:    */     throws HibernateException
/* 121:    */   {
/* 122:136 */     node.setText(toString(value));
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String toString(Object val)
/* 126:    */   {
/* 127:140 */     byte[] bytes = unWrap(val);
/* 128:141 */     StringBuilder buf = new StringBuilder(2 * bytes.length);
/* 129:142 */     for (int i = 0; i < bytes.length; i++)
/* 130:    */     {
/* 131:143 */       String hexStr = Integer.toHexString(bytes[i] - -128);
/* 132:144 */       if (hexStr.length() == 1) {
/* 133:144 */         buf.append('0');
/* 134:    */       }
/* 135:145 */       buf.append(hexStr);
/* 136:    */     }
/* 137:147 */     return buf.toString();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 141:    */   {
/* 142:151 */     return value == null ? "null" : toString(value);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 146:    */     throws HibernateException
/* 147:    */   {
/* 148:155 */     String xmlText = xml.getText();
/* 149:156 */     return (xmlText == null) || (xmlText.length() == 0) ? null : fromString(xmlText);
/* 150:    */   }
/* 151:    */   
/* 152:    */   private Object fromString(String xmlText)
/* 153:    */   {
/* 154:160 */     if (xmlText == null) {
/* 155:161 */       return null;
/* 156:    */     }
/* 157:163 */     if (xmlText.length() % 2 != 0) {
/* 158:164 */       throw new IllegalArgumentException("The string is not a valid xml representation of a binary content.");
/* 159:    */     }
/* 160:166 */     byte[] bytes = new byte[xmlText.length() / 2];
/* 161:167 */     for (int i = 0; i < bytes.length; i++)
/* 162:    */     {
/* 163:168 */       String hexStr = xmlText.substring(i * 2, (i + 1) * 2);
/* 164:169 */       bytes[i] = ((byte)(Integer.parseInt(hexStr, 16) + -128));
/* 165:    */     }
/* 166:171 */     return wrap(bytes);
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected Object wrap(byte[] bytes)
/* 170:    */   {
/* 171:175 */     return wrapPrimitive(bytes);
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected byte[] unWrap(Object bytes)
/* 175:    */   {
/* 176:179 */     return unwrapNonPrimitive((Byte[])bytes);
/* 177:    */   }
/* 178:    */   
/* 179:    */   private byte[] unwrapNonPrimitive(Byte[] bytes)
/* 180:    */   {
/* 181:183 */     int length = bytes.length;
/* 182:184 */     byte[] result = new byte[length];
/* 183:185 */     for (int i = 0; i < length; i++) {
/* 184:186 */       result[i] = bytes[i].byteValue();
/* 185:    */     }
/* 186:188 */     return result;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private Byte[] wrapPrimitive(byte[] bytes)
/* 190:    */   {
/* 191:192 */     int length = bytes.length;
/* 192:193 */     Byte[] result = new Byte[length];
/* 193:194 */     for (int index = 0; index < length; index++) {
/* 194:195 */       result[index] = Byte.valueOf(bytes[index]);
/* 195:    */     }
/* 196:197 */     return result;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean isMutable()
/* 200:    */   {
/* 201:201 */     return true;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 205:    */     throws HibernateException
/* 206:    */   {
/* 207:212 */     if (isEqual(original, target)) {
/* 208:212 */       return original;
/* 209:    */     }
/* 210:213 */     return deepCopy(original, session.getFactory());
/* 211:    */   }
/* 212:    */   
/* 213:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 214:    */   {
/* 215:217 */     return value == null ? ArrayHelper.FALSE : ArrayHelper.TRUE;
/* 216:    */   }
/* 217:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ByteArrayBlobType
 * JD-Core Version:    0.7.0.1
 */