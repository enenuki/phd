/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import org.dom4j.Node;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.engine.spi.Mapping;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  14:    */ import org.hibernate.internal.util.compare.EqualsHelper;
/*  15:    */ import org.hibernate.metamodel.relational.Size;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ @Deprecated
/*  19:    */ public abstract class NullableType
/*  20:    */   extends AbstractType
/*  21:    */   implements StringRepresentableType, XmlRepresentableType
/*  22:    */ {
/*  23: 52 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, NullableType.class.getName());
/*  24: 54 */   private final Size dictatedSize = new Size();
/*  25:    */   
/*  26:    */   public abstract int sqlType();
/*  27:    */   
/*  28:    */   public Size dictatedSize()
/*  29:    */   {
/*  30: 72 */     return this.dictatedSize;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Size defaultSize()
/*  34:    */   {
/*  35: 82 */     return LEGACY_DEFAULT_SIZE;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public abstract Object get(ResultSet paramResultSet, String paramString)
/*  39:    */     throws HibernateException, SQLException;
/*  40:    */   
/*  41:    */   public abstract void set(PreparedStatement paramPreparedStatement, Object paramObject, int paramInt)
/*  42:    */     throws HibernateException, SQLException;
/*  43:    */   
/*  44:    */   public String nullSafeToString(Object value)
/*  45:    */     throws HibernateException
/*  46:    */   {
/*  47:124 */     return value == null ? null : toString(value);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public abstract String toString(Object paramObject)
/*  51:    */     throws HibernateException;
/*  52:    */   
/*  53:    */   public abstract Object fromStringValue(String paramString)
/*  54:    */     throws HibernateException;
/*  55:    */   
/*  56:    */   public final void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/*  57:    */     throws HibernateException, SQLException
/*  58:    */   {
/*  59:138 */     if (settable[0] != 0) {
/*  60:138 */       nullSafeSet(st, value, index);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   public final void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/*  65:    */     throws HibernateException, SQLException
/*  66:    */   {
/*  67:143 */     nullSafeSet(st, value, index);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final void nullSafeSet(PreparedStatement st, Object value, int index)
/*  71:    */     throws HibernateException, SQLException
/*  72:    */   {
/*  73:    */     try
/*  74:    */     {
/*  75:149 */       if (value == null)
/*  76:    */       {
/*  77:150 */         LOG.tracev("Binding null to parameter: {0}", Integer.valueOf(index));
/*  78:    */         
/*  79:152 */         st.setNull(index, sqlType());
/*  80:    */       }
/*  81:    */       else
/*  82:    */       {
/*  83:155 */         if (LOG.isTraceEnabled()) {
/*  84:155 */           LOG.tracev("Binding '{0}' to parameter: {1}", toString(value), Integer.valueOf(index));
/*  85:    */         }
/*  86:157 */         set(st, value, index);
/*  87:    */       }
/*  88:    */     }
/*  89:    */     catch (RuntimeException re)
/*  90:    */     {
/*  91:161 */       LOG.unableToBindValueToParameter(nullSafeToString(value), index, re.getMessage());
/*  92:162 */       throw re;
/*  93:    */     }
/*  94:    */     catch (SQLException se)
/*  95:    */     {
/*  96:165 */       LOG.unableToBindValueToParameter(nullSafeToString(value), index, se.getMessage());
/*  97:166 */       throw se;
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public final Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 102:    */     throws HibernateException, SQLException
/* 103:    */   {
/* 104:176 */     return nullSafeGet(rs, names[0]);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final Object nullSafeGet(ResultSet rs, String[] names)
/* 108:    */     throws HibernateException, SQLException
/* 109:    */   {
/* 110:181 */     return nullSafeGet(rs, names[0]);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public final Object nullSafeGet(ResultSet rs, String name)
/* 114:    */     throws HibernateException, SQLException
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:187 */       Object value = get(rs, name);
/* 119:188 */       if ((value == null) || (rs.wasNull()))
/* 120:    */       {
/* 121:189 */         LOG.tracev("Returning null as column {0}", name);
/* 122:190 */         return null;
/* 123:    */       }
/* 124:192 */       if (LOG.isTraceEnabled()) {
/* 125:192 */         LOG.trace("Returning '" + toString(value) + "' as column " + name);
/* 126:    */       }
/* 127:193 */       return value;
/* 128:    */     }
/* 129:    */     catch (RuntimeException re)
/* 130:    */     {
/* 131:196 */       LOG.unableToReadColumnValueFromResultSet(name, re.getMessage());
/* 132:197 */       throw re;
/* 133:    */     }
/* 134:    */     catch (SQLException se)
/* 135:    */     {
/* 136:200 */       LOG.unableToReadColumnValueFromResultSet(name, se.getMessage());
/* 137:201 */       throw se;
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public final Object nullSafeGet(ResultSet rs, String name, SessionImplementor session, Object owner)
/* 142:    */     throws HibernateException, SQLException
/* 143:    */   {
/* 144:207 */     return nullSafeGet(rs, name);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public final String toXMLString(Object value, SessionFactoryImplementor pc)
/* 148:    */     throws HibernateException
/* 149:    */   {
/* 150:212 */     return toString(value);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public final Object fromXMLString(String xml, Mapping factory)
/* 154:    */     throws HibernateException
/* 155:    */   {
/* 156:216 */     return (xml == null) || (xml.length() == 0) ? null : fromStringValue(xml);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public final int getColumnSpan(Mapping session)
/* 160:    */   {
/* 161:220 */     return 1;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public final int[] sqlTypes(Mapping session)
/* 165:    */   {
/* 166:224 */     return new int[] { sqlType() };
/* 167:    */   }
/* 168:    */   
/* 169:    */   public Size[] dictatedSizes(Mapping mapping)
/* 170:    */     throws MappingException
/* 171:    */   {
/* 172:229 */     return new Size[] { dictatedSize() };
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Size[] defaultSizes(Mapping mapping)
/* 176:    */     throws MappingException
/* 177:    */   {
/* 178:234 */     return new Size[] { defaultSize() };
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean isEqual(Object x, Object y)
/* 182:    */   {
/* 183:239 */     return EqualsHelper.equals(x, y);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 187:    */   {
/* 188:243 */     return value == null ? "null" : toString(value);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 192:    */     throws HibernateException
/* 193:    */   {
/* 194:247 */     return fromXMLString(xml.getText(), factory);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setToXMLNode(Node xml, Object value, SessionFactoryImplementor factory)
/* 198:    */     throws HibernateException
/* 199:    */   {
/* 200:252 */     xml.setText(toXMLString(value, factory));
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 204:    */   {
/* 205:256 */     return value == null ? ArrayHelper.FALSE : ArrayHelper.TRUE;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 209:    */     throws HibernateException
/* 210:    */   {
/* 211:261 */     return (checkable[0] != 0) && (isDirty(old, current, session));
/* 212:    */   }
/* 213:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.NullableType
 * JD-Core Version:    0.7.0.1
 */