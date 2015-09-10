/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.io.Reader;
/*  4:   */ import java.io.StringReader;
/*  5:   */ import java.sql.Clob;
/*  6:   */ import java.sql.SQLException;
/*  7:   */ import org.hibernate.HibernateException;
/*  8:   */ import org.hibernate.engine.jdbc.LobCreator;
/*  9:   */ import org.hibernate.type.descriptor.CharacterStream;
/* 10:   */ import org.hibernate.type.descriptor.WrapperOptions;
/* 11:   */ 
/* 12:   */ public class StringTypeDescriptor
/* 13:   */   extends AbstractTypeDescriptor<String>
/* 14:   */ {
/* 15:41 */   public static final StringTypeDescriptor INSTANCE = new StringTypeDescriptor();
/* 16:   */   
/* 17:   */   public StringTypeDescriptor()
/* 18:   */   {
/* 19:44 */     super(String.class);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String toString(String value)
/* 23:   */   {
/* 24:48 */     return value;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String fromString(String string)
/* 28:   */   {
/* 29:52 */     return string;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public <X> X unwrap(String value, Class<X> type, WrapperOptions options)
/* 33:   */   {
/* 34:57 */     if (value == null) {
/* 35:58 */       return null;
/* 36:   */     }
/* 37:60 */     if (String.class.isAssignableFrom(type)) {
/* 38:61 */       return value;
/* 39:   */     }
/* 40:63 */     if (Reader.class.isAssignableFrom(type)) {
/* 41:64 */       return new StringReader(value);
/* 42:   */     }
/* 43:66 */     if (CharacterStream.class.isAssignableFrom(type)) {
/* 44:67 */       return new CharacterStreamImpl(value);
/* 45:   */     }
/* 46:69 */     if (Clob.class.isAssignableFrom(type)) {
/* 47:70 */       return options.getLobCreator().createClob(value);
/* 48:   */     }
/* 49:72 */     if (DataHelper.isNClob(type)) {
/* 50:73 */       return options.getLobCreator().createNClob(value);
/* 51:   */     }
/* 52:76 */     throw unknownUnwrap(type);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public <X> String wrap(X value, WrapperOptions options)
/* 56:   */   {
/* 57:80 */     if (value == null) {
/* 58:81 */       return null;
/* 59:   */     }
/* 60:83 */     if (String.class.isInstance(value)) {
/* 61:84 */       return (String)value;
/* 62:   */     }
/* 63:86 */     if (Reader.class.isInstance(value)) {
/* 64:87 */       return DataHelper.extractString((Reader)value);
/* 65:   */     }
/* 66:89 */     if ((Clob.class.isInstance(value)) || (DataHelper.isNClob(value.getClass()))) {
/* 67:   */       try
/* 68:   */       {
/* 69:91 */         return DataHelper.extractString(((Clob)value).getCharacterStream());
/* 70:   */       }
/* 71:   */       catch (SQLException e)
/* 72:   */       {
/* 73:94 */         throw new HibernateException("Unable to access lob stream", e);
/* 74:   */       }
/* 75:   */     }
/* 76:98 */     throw unknownWrap(value.getClass());
/* 77:   */   }
/* 78:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.StringTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */