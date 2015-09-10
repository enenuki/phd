/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Comparator;
/*   9:    */ import java.util.Map;
/*  10:    */ import org.dom4j.Node;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.MappingException;
/*  13:    */ import org.hibernate.dialect.Dialect;
/*  14:    */ import org.hibernate.engine.spi.Mapping;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  18:    */ import org.hibernate.metamodel.relational.Size;
/*  19:    */ import org.hibernate.usertype.EnhancedUserType;
/*  20:    */ import org.hibernate.usertype.LoggableUserType;
/*  21:    */ import org.hibernate.usertype.Sized;
/*  22:    */ import org.hibernate.usertype.UserType;
/*  23:    */ import org.hibernate.usertype.UserVersionType;
/*  24:    */ 
/*  25:    */ public class CustomType
/*  26:    */   extends AbstractType
/*  27:    */   implements IdentifierType, DiscriminatorType, VersionType, BasicType
/*  28:    */ {
/*  29:    */   private final UserType userType;
/*  30:    */   private final String name;
/*  31:    */   private final int[] types;
/*  32:    */   private final Size[] dictatedSizes;
/*  33:    */   private final Size[] defaultSizes;
/*  34:    */   private final boolean customLogging;
/*  35:    */   private final String[] registrationKeys;
/*  36:    */   
/*  37:    */   public CustomType(UserType userType)
/*  38:    */     throws MappingException
/*  39:    */   {
/*  40: 67 */     this(userType, ArrayHelper.EMPTY_STRING_ARRAY);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public CustomType(UserType userType, String[] registrationKeys)
/*  44:    */     throws MappingException
/*  45:    */   {
/*  46: 71 */     this.userType = userType;
/*  47: 72 */     this.name = userType.getClass().getName();
/*  48: 73 */     this.types = userType.sqlTypes();
/*  49: 74 */     this.dictatedSizes = (Sized.class.isInstance(userType) ? ((Sized)userType).dictatedSizes() : new Size[this.types.length]);
/*  50:    */     
/*  51:    */ 
/*  52: 77 */     this.defaultSizes = (Sized.class.isInstance(userType) ? ((Sized)userType).defaultSizes() : new Size[this.types.length]);
/*  53:    */     
/*  54:    */ 
/*  55: 80 */     this.customLogging = LoggableUserType.class.isInstance(userType);
/*  56: 81 */     this.registrationKeys = registrationKeys;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public UserType getUserType()
/*  60:    */   {
/*  61: 85 */     return this.userType;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String[] getRegistrationKeys()
/*  65:    */   {
/*  66: 89 */     return this.registrationKeys;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int[] sqlTypes(Mapping pi)
/*  70:    */   {
/*  71: 93 */     return this.types;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Size[] dictatedSizes(Mapping mapping)
/*  75:    */     throws MappingException
/*  76:    */   {
/*  77: 98 */     return this.dictatedSizes;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Size[] defaultSizes(Mapping mapping)
/*  81:    */     throws MappingException
/*  82:    */   {
/*  83:103 */     return this.defaultSizes;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getColumnSpan(Mapping session)
/*  87:    */   {
/*  88:107 */     return this.types.length;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Class getReturnedClass()
/*  92:    */   {
/*  93:111 */     return this.userType.returnedClass();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public boolean isEqual(Object x, Object y)
/*  97:    */     throws HibernateException
/*  98:    */   {
/*  99:115 */     return this.userType.equals(x, y);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getHashCode(Object x)
/* 103:    */   {
/* 104:119 */     return this.userType.hashCode(x);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 108:    */     throws HibernateException, SQLException
/* 109:    */   {
/* 110:124 */     return this.userType.nullSafeGet(rs, names, session, owner);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Object nullSafeGet(ResultSet rs, String columnName, SessionImplementor session, Object owner)
/* 114:    */     throws HibernateException, SQLException
/* 115:    */   {
/* 116:129 */     return nullSafeGet(rs, new String[] { columnName }, session, owner);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public Object assemble(Serializable cached, SessionImplementor session, Object owner)
/* 120:    */     throws HibernateException
/* 121:    */   {
/* 122:135 */     return this.userType.assemble(cached, owner);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/* 126:    */     throws HibernateException
/* 127:    */   {
/* 128:140 */     return this.userType.disassemble(value);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 132:    */     throws HibernateException
/* 133:    */   {
/* 134:149 */     return this.userType.replace(original, target, owner);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/* 138:    */     throws HibernateException, SQLException
/* 139:    */   {
/* 140:154 */     if (settable[0] != 0) {
/* 141:155 */       this.userType.nullSafeSet(st, value, index, session);
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/* 146:    */     throws HibernateException, SQLException
/* 147:    */   {
/* 148:161 */     this.userType.nullSafeSet(st, value, index, session);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String toXMLString(Object value, SessionFactoryImplementor factory)
/* 152:    */   {
/* 153:166 */     if (value == null) {
/* 154:167 */       return null;
/* 155:    */     }
/* 156:169 */     if ((this.userType instanceof EnhancedUserType)) {
/* 157:170 */       return ((EnhancedUserType)this.userType).toXMLString(value);
/* 158:    */     }
/* 159:173 */     return value.toString();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Object fromXMLString(String xml, Mapping factory)
/* 163:    */   {
/* 164:179 */     return ((EnhancedUserType)this.userType).fromXMLString(xml);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getName()
/* 168:    */   {
/* 169:183 */     return this.name;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 173:    */     throws HibernateException
/* 174:    */   {
/* 175:188 */     return this.userType.deepCopy(value);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean isMutable()
/* 179:    */   {
/* 180:192 */     return this.userType.isMutable();
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Object stringToObject(String xml)
/* 184:    */   {
/* 185:196 */     return ((EnhancedUserType)this.userType).fromXMLString(xml);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String objectToSQLString(Object value, Dialect dialect)
/* 189:    */     throws Exception
/* 190:    */   {
/* 191:200 */     return ((EnhancedUserType)this.userType).objectToSQLString(value);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Comparator getComparator()
/* 195:    */   {
/* 196:204 */     return (Comparator)this.userType;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public Object next(Object current, SessionImplementor session)
/* 200:    */   {
/* 201:208 */     return ((UserVersionType)this.userType).next(current, session);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public Object seed(SessionImplementor session)
/* 205:    */   {
/* 206:212 */     return ((UserVersionType)this.userType).seed(session);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 210:    */     throws HibernateException
/* 211:    */   {
/* 212:216 */     return fromXMLString(xml.getText(), factory);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 216:    */     throws HibernateException
/* 217:    */   {
/* 218:221 */     node.setText(toXMLString(value, factory));
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 222:    */     throws HibernateException
/* 223:    */   {
/* 224:226 */     if (value == null) {
/* 225:227 */       return "null";
/* 226:    */     }
/* 227:229 */     if (this.customLogging) {
/* 228:230 */       return ((LoggableUserType)this.userType).toLoggableString(value, factory);
/* 229:    */     }
/* 230:233 */     return toXMLString(value, factory);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 234:    */   {
/* 235:238 */     boolean[] result = new boolean[getColumnSpan(mapping)];
/* 236:239 */     if (value != null) {
/* 237:240 */       Arrays.fill(result, true);
/* 238:    */     }
/* 239:242 */     return result;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 243:    */     throws HibernateException
/* 244:    */   {
/* 245:247 */     return (checkable[0] != 0) && (isDirty(old, current, session));
/* 246:    */   }
/* 247:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CustomType
 * JD-Core Version:    0.7.0.1
 */