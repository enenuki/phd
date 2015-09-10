/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.Reader;
/*  5:   */ import java.io.StringReader;
/*  6:   */ import java.sql.PreparedStatement;
/*  7:   */ import java.sql.ResultSet;
/*  8:   */ import java.sql.SQLException;
/*  9:   */ import org.hibernate.HibernateException;
/* 10:   */ 
/* 11:   */ /**
/* 12:   */  * @deprecated
/* 13:   */  */
/* 14:   */ public abstract class AbstractLongStringType
/* 15:   */   extends ImmutableType
/* 16:   */ {
/* 17:   */   public void set(PreparedStatement st, Object value, int index)
/* 18:   */     throws HibernateException, SQLException
/* 19:   */   {
/* 20:44 */     String str = (String)value;
/* 21:45 */     st.setCharacterStream(index, new StringReader(str), str.length());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Object get(ResultSet rs, String name)
/* 25:   */     throws HibernateException, SQLException
/* 26:   */   {
/* 27:52 */     Reader charReader = rs.getCharacterStream(name);
/* 28:55 */     if (charReader == null) {
/* 29:55 */       return null;
/* 30:   */     }
/* 31:58 */     sb = new StringBuffer();
/* 32:   */     try
/* 33:   */     {
/* 34:60 */       char[] buffer = new char[2048];
/* 35:   */       for (;;)
/* 36:   */       {
/* 37:62 */         int amountRead = charReader.read(buffer, 0, buffer.length);
/* 38:63 */         if (amountRead == -1) {
/* 39:   */           break;
/* 40:   */         }
/* 41:64 */         sb.append(buffer, 0, amountRead);
/* 42:   */       }
/* 43:80 */       return sb.toString();
/* 44:   */     }
/* 45:   */     catch (IOException ioe)
/* 46:   */     {
/* 47:68 */       throw new HibernateException("IOException occurred reading text", ioe);
/* 48:   */     }
/* 49:   */     finally
/* 50:   */     {
/* 51:   */       try
/* 52:   */       {
/* 53:72 */         charReader.close();
/* 54:   */       }
/* 55:   */       catch (IOException e)
/* 56:   */       {
/* 57:75 */         throw new HibernateException("IOException occurred closing stream", e);
/* 58:   */       }
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public Class getReturnedClass()
/* 63:   */   {
/* 64:84 */     return String.class;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String toString(Object val)
/* 68:   */   {
/* 69:88 */     return (String)val;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public Object fromStringValue(String xml)
/* 73:   */   {
/* 74:91 */     return xml;
/* 75:   */   }
/* 76:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AbstractLongStringType
 * JD-Core Version:    0.7.0.1
 */