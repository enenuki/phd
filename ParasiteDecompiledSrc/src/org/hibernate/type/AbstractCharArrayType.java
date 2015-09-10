/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.CharArrayReader;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.dialect.Dialect;
/*   8:    */ 
/*   9:    */ /**
/*  10:    */  * @deprecated
/*  11:    */  */
/*  12:    */ public abstract class AbstractCharArrayType
/*  13:    */   extends MutableType
/*  14:    */ {
/*  15:    */   protected abstract Object toExternalFormat(char[] paramArrayOfChar);
/*  16:    */   
/*  17:    */   protected abstract char[] toInternalFormat(Object paramObject);
/*  18:    */   
/*  19:    */   /* Error */
/*  20:    */   public Object get(java.sql.ResultSet rs, String name)
/*  21:    */     throws SQLException
/*  22:    */   {
/*  23:    */     // Byte code:
/*  24:    */     //   0: aload_1
/*  25:    */     //   1: aload_2
/*  26:    */     //   2: invokeinterface 2 2 0
/*  27:    */     //   7: astore_3
/*  28:    */     //   8: aload_3
/*  29:    */     //   9: ifnonnull +9 -> 18
/*  30:    */     //   12: aload_0
/*  31:    */     //   13: aconst_null
/*  32:    */     //   14: invokevirtual 3	org/hibernate/type/AbstractCharArrayType:toExternalFormat	([C)Ljava/lang/Object;
/*  33:    */     //   17: areturn
/*  34:    */     //   18: new 4	java/io/CharArrayWriter
/*  35:    */     //   21: dup
/*  36:    */     //   22: invokespecial 5	java/io/CharArrayWriter:<init>	()V
/*  37:    */     //   25: astore 4
/*  38:    */     //   27: aload_3
/*  39:    */     //   28: invokevirtual 6	java/io/Reader:read	()I
/*  40:    */     //   31: istore 5
/*  41:    */     //   33: iload 5
/*  42:    */     //   35: iconst_m1
/*  43:    */     //   36: if_icmpne +13 -> 49
/*  44:    */     //   39: aload_0
/*  45:    */     //   40: aload 4
/*  46:    */     //   42: invokevirtual 7	java/io/CharArrayWriter:toCharArray	()[C
/*  47:    */     //   45: invokevirtual 3	org/hibernate/type/AbstractCharArrayType:toExternalFormat	([C)Ljava/lang/Object;
/*  48:    */     //   48: areturn
/*  49:    */     //   49: aload 4
/*  50:    */     //   51: iload 5
/*  51:    */     //   53: invokevirtual 8	java/io/CharArrayWriter:write	(I)V
/*  52:    */     //   56: goto -29 -> 27
/*  53:    */     //   59: astore 5
/*  54:    */     //   61: new 10	org/hibernate/HibernateException
/*  55:    */     //   64: dup
/*  56:    */     //   65: ldc 11
/*  57:    */     //   67: invokespecial 12	org/hibernate/HibernateException:<init>	(Ljava/lang/String;)V
/*  58:    */     //   70: athrow
/*  59:    */     // Line number table:
/*  60:    */     //   Java source line #58	-> byte code offset #0
/*  61:    */     //   Java source line #59	-> byte code offset #8
/*  62:    */     //   Java source line #60	-> byte code offset #18
/*  63:    */     //   Java source line #63	-> byte code offset #27
/*  64:    */     //   Java source line #64	-> byte code offset #33
/*  65:    */     //   Java source line #65	-> byte code offset #49
/*  66:    */     //   Java source line #69	-> byte code offset #56
/*  67:    */     //   Java source line #67	-> byte code offset #59
/*  68:    */     //   Java source line #68	-> byte code offset #61
/*  69:    */     // Local variable table:
/*  70:    */     //   start	length	slot	name	signature
/*  71:    */     //   0	71	0	this	AbstractCharArrayType
/*  72:    */     //   0	71	1	rs	java.sql.ResultSet
/*  73:    */     //   0	71	2	name	String
/*  74:    */     //   7	21	3	stream	java.io.Reader
/*  75:    */     //   25	25	4	writer	java.io.CharArrayWriter
/*  76:    */     //   31	21	5	c	int
/*  77:    */     //   59	3	5	e	java.io.IOException
/*  78:    */     // Exception table:
/*  79:    */     //   from	to	target	type
/*  80:    */     //   27	48	59	java/io/IOException
/*  81:    */     //   49	56	59	java/io/IOException
/*  82:    */   }
/*  83:    */   
/*  84:    */   public abstract Class getReturnedClass();
/*  85:    */   
/*  86:    */   public void set(PreparedStatement st, Object value, int index)
/*  87:    */     throws SQLException
/*  88:    */   {
/*  89: 76 */     char[] chars = toInternalFormat(value);
/*  90: 77 */     st.setCharacterStream(index, new CharArrayReader(chars), chars.length);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int sqlType()
/*  94:    */   {
/*  95: 81 */     return 12;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String objectToSQLString(Object value, Dialect dialect)
/*  99:    */     throws Exception
/* 100:    */   {
/* 101: 86 */     return '\'' + new String(toInternalFormat(value)) + '\'';
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Object stringToObject(String xml)
/* 105:    */     throws Exception
/* 106:    */   {
/* 107: 90 */     if (xml == null) {
/* 108: 90 */       return toExternalFormat(null);
/* 109:    */     }
/* 110: 91 */     int length = xml.length();
/* 111: 92 */     char[] chars = new char[length];
/* 112: 93 */     for (int index = 0; index < length; index++) {
/* 113: 94 */       chars[index] = xml.charAt(index);
/* 114:    */     }
/* 115: 96 */     return toExternalFormat(chars);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String toString(Object value)
/* 119:    */   {
/* 120:100 */     if (value == null) {
/* 121:100 */       return null;
/* 122:    */     }
/* 123:101 */     return new String(toInternalFormat(value));
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Object fromStringValue(String xml)
/* 127:    */   {
/* 128:105 */     if (xml == null) {
/* 129:105 */       return null;
/* 130:    */     }
/* 131:106 */     int length = xml.length();
/* 132:107 */     char[] chars = new char[length];
/* 133:108 */     for (int index = 0; index < length; index++) {
/* 134:109 */       chars[index] = xml.charAt(index);
/* 135:    */     }
/* 136:111 */     return toExternalFormat(chars);
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected Object deepCopyNotNull(Object value)
/* 140:    */     throws HibernateException
/* 141:    */   {
/* 142:115 */     char[] chars = toInternalFormat(value);
/* 143:116 */     char[] result = new char[chars.length];
/* 144:117 */     System.arraycopy(chars, 0, result, 0, chars.length);
/* 145:118 */     return toExternalFormat(result);
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AbstractCharArrayType
 * JD-Core Version:    0.7.0.1
 */