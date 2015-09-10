/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.dom4j.Element;
/*  10:    */ import org.dom4j.Node;
/*  11:    */ import org.hibernate.EntityMode;
/*  12:    */ import org.hibernate.FetchMode;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.MappingException;
/*  15:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  16:    */ import org.hibernate.engine.spi.Mapping;
/*  17:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  18:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  19:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  20:    */ import org.hibernate.metamodel.relational.Size;
/*  21:    */ import org.hibernate.usertype.CompositeUserType;
/*  22:    */ import org.hibernate.usertype.LoggableUserType;
/*  23:    */ 
/*  24:    */ public class CompositeCustomType
/*  25:    */   extends AbstractType
/*  26:    */   implements CompositeType, BasicType
/*  27:    */ {
/*  28:    */   private final CompositeUserType userType;
/*  29:    */   private final String[] registrationKeys;
/*  30:    */   private final String name;
/*  31:    */   private final boolean customLogging;
/*  32:    */   
/*  33:    */   public CompositeCustomType(CompositeUserType userType)
/*  34:    */   {
/*  35: 62 */     this(userType, ArrayHelper.EMPTY_STRING_ARRAY);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public CompositeCustomType(CompositeUserType userType, String[] registrationKeys)
/*  39:    */   {
/*  40: 66 */     this.userType = userType;
/*  41: 67 */     this.name = userType.getClass().getName();
/*  42: 68 */     this.customLogging = LoggableUserType.class.isInstance(userType);
/*  43: 69 */     this.registrationKeys = registrationKeys;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String[] getRegistrationKeys()
/*  47:    */   {
/*  48: 73 */     return this.registrationKeys;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public CompositeUserType getUserType()
/*  52:    */   {
/*  53: 77 */     return this.userType;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean isMethodOf(Method method)
/*  57:    */   {
/*  58: 81 */     return false;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Type[] getSubtypes()
/*  62:    */   {
/*  63: 85 */     return this.userType.getPropertyTypes();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String[] getPropertyNames()
/*  67:    */   {
/*  68: 89 */     return this.userType.getPropertyNames();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object[] getPropertyValues(Object component, SessionImplementor session)
/*  72:    */     throws HibernateException
/*  73:    */   {
/*  74: 93 */     return getPropertyValues(component, EntityMode.POJO);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Object[] getPropertyValues(Object component, EntityMode entityMode)
/*  78:    */     throws HibernateException
/*  79:    */   {
/*  80: 98 */     int len = getSubtypes().length;
/*  81: 99 */     Object[] result = new Object[len];
/*  82:100 */     for (int i = 0; i < len; i++) {
/*  83:101 */       result[i] = getPropertyValue(component, i);
/*  84:    */     }
/*  85:103 */     return result;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setPropertyValues(Object component, Object[] values, EntityMode entityMode)
/*  89:    */     throws HibernateException
/*  90:    */   {
/*  91:109 */     for (int i = 0; i < values.length; i++) {
/*  92:110 */       this.userType.setPropertyValue(component, i, values[i]);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Object getPropertyValue(Object component, int i, SessionImplementor session)
/*  97:    */     throws HibernateException
/*  98:    */   {
/*  99:116 */     return getPropertyValue(component, i);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object getPropertyValue(Object component, int i)
/* 103:    */     throws HibernateException
/* 104:    */   {
/* 105:120 */     return this.userType.getPropertyValue(component, i);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public CascadeStyle getCascadeStyle(int i)
/* 109:    */   {
/* 110:124 */     return CascadeStyle.NONE;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public FetchMode getFetchMode(int i)
/* 114:    */   {
/* 115:128 */     return FetchMode.DEFAULT;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isComponentType()
/* 119:    */   {
/* 120:132 */     return true;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Object deepCopy(Object value, SessionFactoryImplementor factory)
/* 124:    */     throws HibernateException
/* 125:    */   {
/* 126:137 */     return this.userType.deepCopy(value);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Object assemble(Serializable cached, SessionImplementor session, Object owner)
/* 130:    */     throws HibernateException
/* 131:    */   {
/* 132:146 */     return this.userType.assemble(cached, session, owner);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Serializable disassemble(Object value, SessionImplementor session, Object owner)
/* 136:    */     throws HibernateException
/* 137:    */   {
/* 138:151 */     return this.userType.disassemble(value, session);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Object replace(Object original, Object target, SessionImplementor session, Object owner, Map copyCache)
/* 142:    */     throws HibernateException
/* 143:    */   {
/* 144:161 */     return this.userType.replace(original, target, session, owner);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public boolean isEqual(Object x, Object y)
/* 148:    */     throws HibernateException
/* 149:    */   {
/* 150:166 */     return this.userType.equals(x, y);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public int getHashCode(Object x)
/* 154:    */   {
/* 155:170 */     return this.userType.hashCode(x);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public int getColumnSpan(Mapping mapping)
/* 159:    */     throws MappingException
/* 160:    */   {
/* 161:174 */     Type[] types = this.userType.getPropertyTypes();
/* 162:175 */     int n = 0;
/* 163:176 */     for (Type type : types) {
/* 164:177 */       n += type.getColumnSpan(mapping);
/* 165:    */     }
/* 166:179 */     return n;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getName()
/* 170:    */   {
/* 171:183 */     return this.name;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Class getReturnedClass()
/* 175:    */   {
/* 176:187 */     return this.userType.returnedClass();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean isMutable()
/* 180:    */   {
/* 181:191 */     return this.userType.isMutable();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Object nullSafeGet(ResultSet rs, String columnName, SessionImplementor session, Object owner)
/* 185:    */     throws HibernateException, SQLException
/* 186:    */   {
/* 187:201 */     return this.userType.nullSafeGet(rs, new String[] { columnName }, session, owner);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
/* 191:    */     throws HibernateException, SQLException
/* 192:    */   {
/* 193:211 */     return this.userType.nullSafeGet(rs, names, session, owner);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
/* 197:    */     throws HibernateException, SQLException
/* 198:    */   {
/* 199:221 */     this.userType.nullSafeSet(st, value, index, session);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void nullSafeSet(PreparedStatement st, Object value, int index, boolean[] settable, SessionImplementor session)
/* 203:    */     throws HibernateException, SQLException
/* 204:    */   {
/* 205:233 */     this.userType.nullSafeSet(st, value, index, session);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public int[] sqlTypes(Mapping mapping)
/* 209:    */     throws MappingException
/* 210:    */   {
/* 211:237 */     int[] result = new int[getColumnSpan(mapping)];
/* 212:238 */     int n = 0;
/* 213:239 */     for (Type type : this.userType.getPropertyTypes()) {
/* 214:240 */       for (int sqlType : type.sqlTypes(mapping)) {
/* 215:241 */         result[(n++)] = sqlType;
/* 216:    */       }
/* 217:    */     }
/* 218:244 */     return result;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Size[] dictatedSizes(Mapping mapping)
/* 222:    */     throws MappingException
/* 223:    */   {
/* 224:250 */     Size[] sizes = new Size[getColumnSpan(mapping)];
/* 225:251 */     int soFar = 0;
/* 226:252 */     for (Type propertyType : this.userType.getPropertyTypes())
/* 227:    */     {
/* 228:253 */       Size[] propertySizes = propertyType.dictatedSizes(mapping);
/* 229:254 */       System.arraycopy(propertySizes, 0, sizes, soFar, propertySizes.length);
/* 230:255 */       soFar += propertySizes.length;
/* 231:    */     }
/* 232:257 */     return sizes;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Size[] defaultSizes(Mapping mapping)
/* 236:    */     throws MappingException
/* 237:    */   {
/* 238:263 */     Size[] sizes = new Size[getColumnSpan(mapping)];
/* 239:264 */     int soFar = 0;
/* 240:265 */     for (Type propertyType : this.userType.getPropertyTypes())
/* 241:    */     {
/* 242:266 */       Size[] propertySizes = propertyType.defaultSizes(mapping);
/* 243:267 */       System.arraycopy(propertySizes, 0, sizes, soFar, propertySizes.length);
/* 244:268 */       soFar += propertySizes.length;
/* 245:    */     }
/* 246:270 */     return sizes;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String toLoggableString(Object value, SessionFactoryImplementor factory)
/* 250:    */     throws HibernateException
/* 251:    */   {
/* 252:274 */     if (value == null) {
/* 253:275 */       return "null";
/* 254:    */     }
/* 255:277 */     if (this.customLogging) {
/* 256:278 */       return ((LoggableUserType)this.userType).toLoggableString(value, factory);
/* 257:    */     }
/* 258:281 */     return value.toString();
/* 259:    */   }
/* 260:    */   
/* 261:    */   public boolean[] getPropertyNullability()
/* 262:    */   {
/* 263:286 */     return null;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Object fromXMLNode(Node xml, Mapping factory)
/* 267:    */     throws HibernateException
/* 268:    */   {
/* 269:290 */     return xml;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setToXMLNode(Node node, Object value, SessionFactoryImplementor factory)
/* 273:    */     throws HibernateException
/* 274:    */   {
/* 275:295 */     replaceNode(node, (Element)value);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public boolean[] toColumnNullness(Object value, Mapping mapping)
/* 279:    */   {
/* 280:299 */     boolean[] result = new boolean[getColumnSpan(mapping)];
/* 281:300 */     if (value == null) {
/* 282:300 */       return result;
/* 283:    */     }
/* 284:301 */     Object[] values = getPropertyValues(value, EntityMode.POJO);
/* 285:302 */     int loc = 0;
/* 286:303 */     Type[] propertyTypes = getSubtypes();
/* 287:304 */     for (int i = 0; i < propertyTypes.length; i++)
/* 288:    */     {
/* 289:305 */       boolean[] propertyNullness = propertyTypes[i].toColumnNullness(values[i], mapping);
/* 290:306 */       System.arraycopy(propertyNullness, 0, result, loc, propertyNullness.length);
/* 291:307 */       loc += propertyNullness.length;
/* 292:    */     }
/* 293:309 */     return result;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public boolean isDirty(Object old, Object current, boolean[] checkable, SessionImplementor session)
/* 297:    */     throws HibernateException
/* 298:    */   {
/* 299:313 */     return isDirty(old, current, session);
/* 300:    */   }
/* 301:    */   
/* 302:    */   public boolean isEmbedded()
/* 303:    */   {
/* 304:317 */     return false;
/* 305:    */   }
/* 306:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CompositeCustomType
 * JD-Core Version:    0.7.0.1
 */