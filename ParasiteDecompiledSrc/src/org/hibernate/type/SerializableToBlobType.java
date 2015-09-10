/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayInputStream;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.sql.Blob;
/*   6:    */ import java.sql.PreparedStatement;
/*   7:    */ import java.sql.ResultSet;
/*   8:    */ import java.sql.SQLException;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Properties;
/*  11:    */ import org.dom4j.Node;
/*  12:    */ import org.hibernate.Hibernate;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.MappingException;
/*  15:    */ import org.hibernate.dialect.Dialect;
/*  16:    */ import org.hibernate.engine.jdbc.LobCreator;
/*  17:    */ import org.hibernate.engine.spi.Mapping;
/*  18:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  19:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  20:    */ import org.hibernate.internal.util.ReflectHelper;
/*  21:    */ import org.hibernate.internal.util.SerializationHelper;
/*  22:    */ import org.hibernate.usertype.ParameterizedType;
/*  23:    */ 
/*  24:    */ public class SerializableToBlobType
/*  25:    */   extends AbstractLobType
/*  26:    */   implements ParameterizedType
/*  27:    */ {
/*  28:    */   public static final String CLASS_NAME = "classname";
/*  29:    */   private Class serializableClass;
/*  30:    */   private SerializableType type;
/*  31:    */   
/*  32:    */   public int[] sqlTypes(Mapping mapping)
/*  33:    */     throws MappingException
/*  34:    */   {
/*  35: 60 */     return new int[] { 2004 };
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Class getReturnedClass()
/*  39:    */   {
/*  40: 64 */     return this.serializableClass;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean isEqual(Object x, Object y, SessionFactoryImplementor factory)
/*  44:    */   {
/*  45: 69 */     return this.type.isEqual(x, y);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getHashCode(Object x, SessionFactoryImplementor session)
/*  49:    */   {
/*  50: 75 */     return this.type.getHashCode(x);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Object get(ResultSet rs, String name)
/*  54:    */     throws SQLException
/*  55:    */   {
/*  56: 79 */     Blob blob = rs.getBlob(name);
/*  57: 80 */     if (rs.wasNull()) {
/*  58: 80 */       return null;
/*  59:    */     }
/*  60: 81 */     int length = (int)blob.length();
/*  61: 82 */     byte[] primaryResult = blob.getBytes(1L, length);
/*  62: 83 */     return fromBytes(primaryResult);
/*  63:    */   }
/*  64:    */   
/*  65:    */   private static byte[] toBytes(Object object)
/*  66:    */     throws SerializationException
/*  67:    */   {
/*  68: 87 */     return SerializationHelper.serialize((Serializable)object);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private Object fromBytes(byte[] bytes)
/*  72:    */     throws SerializationException
/*  73:    */   {
/*  74: 91 */     return SerializationHelper.deserialize(bytes, getReturnedClass().getClassLoader());
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void set(PreparedStatement st, Object value, int index, SessionImplementor session)
/*  78:    */     throws SQLException
/*  79:    */   {
/*  80: 95 */     if (value != null)
/*  81:    */     {
/*  82: 97 */       byte[] toSet = toBytes(value);
/*  83: 98 */       if (session.getFactory().getDialect().useInputStreamToInsertBlob()) {
/*  84: 99 */         st.setBinaryStream(index, new ByteArrayInputStream(toSet), toSet.length);
/*  85:    */       } else {
/*  86:102 */         st.setBlob(index, Hibernate.getLobCreator(session).createBlob(toSet));
/*  87:    */       }
/*  88:    */     }
/*  89:    */     else
/*  90:    */     {
/*  91:106 */       st.setNull(index, sqlTypes(null)[0]);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/*  96:    */     throws HibernateException
/*  97:    */   {
/*  98:111 */     this.type.setToXMLNode(node, value, factory);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 102:    */     throws HibernateException
/* 103:    */   {
/* 104:115 */     return this.type.toLoggableString(value, factory);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 108:    */     throws HibernateException
/* 109:    */   {
/* 110:119 */     return this.type.fromXMLNode(xml, factory);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 114:    */     throws HibernateException
/* 115:    */   {
/* 116:124 */     return this.type.deepCopy(value, null);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean isMutable()
/* 120:    */   {
/* 121:128 */     return this.type.isMutable();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 125:    */     throws HibernateException
/* 126:    */   {
/* 127:133 */     return this.type.replace(original, target, session, owner, copyCache);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 131:    */   {
/* 132:137 */     return this.type.toColumnNullness(value, mapping);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setParameterValues(Properties parameters)
/* 136:    */   {
/* 137:141 */     if (parameters != null)
/* 138:    */     {
/* 139:142 */       String className = parameters.getProperty("classname");
/* 140:143 */       if (className == null) {
/* 141:144 */         throw new MappingException("No class name defined for type: " + SerializableToBlobType.class.getName());
/* 142:    */       }
/* 143:    */       try
/* 144:    */       {
/* 145:149 */         this.serializableClass = ReflectHelper.classForName(className);
/* 146:    */       }
/* 147:    */       catch (ClassNotFoundException e)
/* 148:    */       {
/* 149:152 */         throw new MappingException("Unable to load class from classname parameter", e);
/* 150:    */       }
/* 151:    */     }
/* 152:155 */     this.type = new SerializableType(this.serializableClass);
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.SerializableToBlobType
 * JD-Core Version:    0.7.0.1
 */