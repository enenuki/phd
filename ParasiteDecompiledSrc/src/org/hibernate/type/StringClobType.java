/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.io.StringReader;
/*   7:    */ import java.sql.PreparedStatement;
/*   8:    */ import java.sql.ResultSet;
/*   9:    */ import java.sql.SQLException;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.usertype.UserType;
/*  13:    */ 
/*  14:    */ @Deprecated
/*  15:    */ public class StringClobType
/*  16:    */   implements UserType, Serializable
/*  17:    */ {
/*  18:    */   public int[] sqlTypes()
/*  19:    */   {
/*  20: 48 */     return new int[] { 2005 };
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Class returnedClass()
/*  24:    */   {
/*  25: 52 */     return String.class;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean equals(Object x, Object y)
/*  29:    */     throws HibernateException
/*  30:    */   {
/*  31: 56 */     return (x == y) || ((x != null) && (x.equals(y)));
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int hashCode(Object x)
/*  35:    */     throws HibernateException
/*  36:    */   {
/*  37: 60 */     return x.hashCode();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/*  41:    */     throws HibernateException, SQLException
/*  42:    */   {
/*  43: 64 */     Reader reader = rs.getCharacterStream(names[0]);
/*  44: 65 */     if (reader == null) {
/*  45: 65 */       return null;
/*  46:    */     }
/*  47: 66 */     StringBuilder result = new StringBuilder(4096);
/*  48:    */     try
/*  49:    */     {
/*  50: 68 */       char[] charbuf = new char[4096];
/*  51: 69 */       for (int i = reader.read(charbuf); i > 0; i = reader.read(charbuf)) {
/*  52: 70 */         result.append(charbuf, 0, i);
/*  53:    */       }
/*  54:    */     }
/*  55:    */     catch (IOException e)
/*  56:    */     {
/*  57: 74 */       throw new SQLException(e.getMessage());
/*  58:    */     }
/*  59: 76 */     return result.toString();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/*  63:    */     throws HibernateException, SQLException
/*  64:    */   {
/*  65: 80 */     if (value != null)
/*  66:    */     {
/*  67: 81 */       String string = (String)value;
/*  68: 82 */       StringReader reader = new StringReader(string);
/*  69: 83 */       st.setCharacterStream(index, reader, string.length());
/*  70:    */     }
/*  71:    */     else
/*  72:    */     {
/*  73: 86 */       st.setNull(index, sqlTypes()[0]);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Object deepCopy(Object value)
/*  78:    */     throws HibernateException
/*  79:    */   {
/*  80: 92 */     return value;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isMutable()
/*  84:    */   {
/*  85: 96 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Serializable disassemble(Object value)
/*  89:    */     throws HibernateException
/*  90:    */   {
/*  91:100 */     return (Serializable)value;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Object assemble(Serializable cached, Object owner)
/*  95:    */     throws HibernateException
/*  96:    */   {
/*  97:104 */     return cached;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Object replace(Object original, Object target, Object owner)
/* 101:    */     throws HibernateException
/* 102:    */   {
/* 103:108 */     return original;
/* 104:    */   }
/* 105:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.StringClobType
 * JD-Core Version:    0.7.0.1
 */