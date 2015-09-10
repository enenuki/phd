/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Properties;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.internal.util.ReflectHelper;
/*  12:    */ import org.hibernate.usertype.EnhancedUserType;
/*  13:    */ import org.hibernate.usertype.ParameterizedType;
/*  14:    */ import org.jboss.logging.Logger;
/*  15:    */ 
/*  16:    */ public class EnumType
/*  17:    */   implements EnhancedUserType, ParameterizedType, Serializable
/*  18:    */ {
/*  19: 54 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EnumType.class.getName());
/*  20:    */   public static final String ENUM = "enumClass";
/*  21:    */   public static final String SCHEMA = "schema";
/*  22:    */   public static final String CATALOG = "catalog";
/*  23:    */   public static final String TABLE = "table";
/*  24:    */   public static final String COLUMN = "column";
/*  25:    */   public static final String TYPE = "type";
/*  26:    */   private Class<? extends Enum> enumClass;
/*  27:    */   private transient Object[] enumValues;
/*  28: 65 */   private int sqlType = 4;
/*  29:    */   
/*  30:    */   public int[] sqlTypes()
/*  31:    */   {
/*  32: 68 */     return new int[] { this.sqlType };
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Class<? extends Enum> returnedClass()
/*  36:    */   {
/*  37: 72 */     return this.enumClass;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean equals(Object x, Object y)
/*  41:    */     throws HibernateException
/*  42:    */   {
/*  43: 76 */     return x == y;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public int hashCode(Object x)
/*  47:    */     throws HibernateException
/*  48:    */   {
/*  49: 80 */     return x == null ? 0 : x.hashCode();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/*  53:    */     throws HibernateException, SQLException
/*  54:    */   {
/*  55: 85 */     Object object = rs.getObject(names[0]);
/*  56: 86 */     if (rs.wasNull())
/*  57:    */     {
/*  58: 87 */       if (LOG.isTraceEnabled()) {
/*  59: 87 */         LOG.tracev("Returning null as column {0}", names[0]);
/*  60:    */       }
/*  61: 88 */       return null;
/*  62:    */     }
/*  63: 90 */     if ((object instanceof Number))
/*  64:    */     {
/*  65: 91 */       initEnumValues();
/*  66: 92 */       int ordinal = ((Number)object).intValue();
/*  67: 93 */       if ((ordinal < 0) || (ordinal >= this.enumValues.length)) {
/*  68: 93 */         throw new IllegalArgumentException("Unknown ordinal value for enum " + this.enumClass + ": " + ordinal);
/*  69:    */       }
/*  70: 95 */       if (LOG.isTraceEnabled()) {
/*  71: 95 */         LOG.tracev("Returning '{0}' as column {1}", Integer.valueOf(ordinal), names[0]);
/*  72:    */       }
/*  73: 96 */       return this.enumValues[ordinal];
/*  74:    */     }
/*  75: 99 */     String name = (String)object;
/*  76:100 */     if (LOG.isTraceEnabled()) {
/*  77:100 */       LOG.tracev("Returning '{0}' as column {1}", name, names[0]);
/*  78:    */     }
/*  79:    */     try
/*  80:    */     {
/*  81:102 */       return Enum.valueOf(this.enumClass, name);
/*  82:    */     }
/*  83:    */     catch (IllegalArgumentException iae)
/*  84:    */     {
/*  85:105 */       throw new IllegalArgumentException("Unknown name value for enum " + this.enumClass + ": " + name, iae);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/*  90:    */     throws HibernateException, SQLException
/*  91:    */   {
/*  92:111 */     if (value == null)
/*  93:    */     {
/*  94:112 */       if (LOG.isTraceEnabled()) {
/*  95:112 */         LOG.tracev("Binding null to parameter: {0}", Integer.valueOf(index));
/*  96:    */       }
/*  97:113 */       st.setNull(index, this.sqlType);
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:116 */       boolean isOrdinal = isOrdinal(this.sqlType);
/* 102:117 */       if (isOrdinal)
/* 103:    */       {
/* 104:118 */         int ordinal = ((Enum)value).ordinal();
/* 105:119 */         if (LOG.isTraceEnabled()) {
/* 106:119 */           LOG.tracev("Binding '{0}' to parameter: '{1}", Integer.valueOf(ordinal), Integer.valueOf(index));
/* 107:    */         }
/* 108:120 */         st.setObject(index, Integer.valueOf(ordinal), this.sqlType);
/* 109:    */       }
/* 110:    */       else
/* 111:    */       {
/* 112:123 */         String enumString = ((Enum)value).name();
/* 113:124 */         if (LOG.isTraceEnabled()) {
/* 114:124 */           LOG.tracev("Binding '{0}' to parameter: {1}", enumString, Integer.valueOf(index));
/* 115:    */         }
/* 116:125 */         st.setObject(index, enumString, this.sqlType);
/* 117:    */       }
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   private boolean isOrdinal(int paramType)
/* 122:    */   {
/* 123:131 */     switch (paramType)
/* 124:    */     {
/* 125:    */     case -6: 
/* 126:    */     case -5: 
/* 127:    */     case 2: 
/* 128:    */     case 3: 
/* 129:    */     case 4: 
/* 130:    */     case 5: 
/* 131:    */     case 6: 
/* 132:    */     case 8: 
/* 133:140 */       return true;
/* 134:    */     case -1: 
/* 135:    */     case 1: 
/* 136:    */     case 12: 
/* 137:144 */       return false;
/* 138:    */     }
/* 139:146 */     throw new HibernateException("Unable to persist an Enum in a column of SQL Type: " + paramType);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Object deepCopy(Object value)
/* 143:    */     throws HibernateException
/* 144:    */   {
/* 145:151 */     return value;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean isMutable()
/* 149:    */   {
/* 150:155 */     return false;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Serializable disassemble(Object value)
/* 154:    */     throws HibernateException
/* 155:    */   {
/* 156:159 */     return (Serializable)value;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Object assemble(Serializable cached, Object owner)
/* 160:    */     throws HibernateException
/* 161:    */   {
/* 162:163 */     return cached;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Object replace(Object original, Object target, Object owner)
/* 166:    */     throws HibernateException
/* 167:    */   {
/* 168:167 */     return original;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setParameterValues(Properties parameters)
/* 172:    */   {
/* 173:171 */     String enumClassName = parameters.getProperty("enumClass");
/* 174:    */     try
/* 175:    */     {
/* 176:173 */       this.enumClass = ReflectHelper.classForName(enumClassName, getClass()).asSubclass(Enum.class);
/* 177:    */     }
/* 178:    */     catch (ClassNotFoundException exception)
/* 179:    */     {
/* 180:176 */       throw new HibernateException("Enum class not found", exception);
/* 181:    */     }
/* 182:179 */     String type = parameters.getProperty("type");
/* 183:180 */     if (type != null) {
/* 184:181 */       this.sqlType = Integer.decode(type).intValue();
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   private void initEnumValues()
/* 189:    */   {
/* 190:189 */     if (this.enumValues == null)
/* 191:    */     {
/* 192:190 */       this.enumValues = this.enumClass.getEnumConstants();
/* 193:191 */       if (this.enumValues == null) {
/* 194:192 */         throw new NullPointerException("Failed to init enumValues");
/* 195:    */       }
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String objectToSQLString(Object value)
/* 200:    */   {
/* 201:198 */     boolean isOrdinal = isOrdinal(this.sqlType);
/* 202:199 */     if (isOrdinal)
/* 203:    */     {
/* 204:200 */       int ordinal = ((Enum)value).ordinal();
/* 205:201 */       return Integer.toString(ordinal);
/* 206:    */     }
/* 207:204 */     return '\'' + ((Enum)value).name() + '\'';
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String toXMLString(Object value)
/* 211:    */   {
/* 212:209 */     boolean isOrdinal = isOrdinal(this.sqlType);
/* 213:210 */     if (isOrdinal)
/* 214:    */     {
/* 215:211 */       int ordinal = ((Enum)value).ordinal();
/* 216:212 */       return Integer.toString(ordinal);
/* 217:    */     }
/* 218:215 */     return ((Enum)value).name();
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Object fromXMLString(String xmlValue)
/* 222:    */   {
/* 223:    */     try
/* 224:    */     {
/* 225:221 */       int ordinal = Integer.parseInt(xmlValue);
/* 226:222 */       initEnumValues();
/* 227:223 */       if ((ordinal < 0) || (ordinal >= this.enumValues.length)) {
/* 228:224 */         throw new IllegalArgumentException("Unknown ordinal value for enum " + this.enumClass + ": " + ordinal);
/* 229:    */       }
/* 230:226 */       return this.enumValues[ordinal];
/* 231:    */     }
/* 232:    */     catch (NumberFormatException e)
/* 233:    */     {
/* 234:    */       try
/* 235:    */       {
/* 236:230 */         return Enum.valueOf(this.enumClass, xmlValue);
/* 237:    */       }
/* 238:    */       catch (IllegalArgumentException iae)
/* 239:    */       {
/* 240:233 */         throw new IllegalArgumentException("Unknown name value for enum " + this.enumClass + ": " + xmlValue, iae);
/* 241:    */       }
/* 242:    */     }
/* 243:    */   }
/* 244:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.EnumType
 * JD-Core Version:    0.7.0.1
 */