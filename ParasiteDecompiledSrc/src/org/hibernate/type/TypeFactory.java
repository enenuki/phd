/*   1:    */ package org.hibernate.type;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.Properties;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.MappingException;
/*   8:    */ import org.hibernate.classic.Lifecycle;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.internal.util.ReflectHelper;
/*  12:    */ import org.hibernate.tuple.component.ComponentMetamodel;
/*  13:    */ import org.hibernate.usertype.CompositeUserType;
/*  14:    */ import org.hibernate.usertype.ParameterizedType;
/*  15:    */ import org.hibernate.usertype.UserType;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public final class TypeFactory
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21: 56 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TypeFactory.class.getName());
/*  22:    */   private final TypeScopeImpl typeScope;
/*  23:    */   
/*  24:    */   public TypeFactory()
/*  25:    */   {
/*  26: 58 */     this.typeScope = new TypeScopeImpl(null);
/*  27:    */   }
/*  28:    */   
/*  29:    */   private static class TypeScopeImpl
/*  30:    */     implements TypeFactory.TypeScope
/*  31:    */   {
/*  32:    */     private SessionFactoryImplementor factory;
/*  33:    */     
/*  34:    */     public void injectSessionFactory(SessionFactoryImplementor factory)
/*  35:    */     {
/*  36: 68 */       if (this.factory != null) {
/*  37: 69 */         TypeFactory.LOG.scopingTypesToSessionFactoryAfterAlreadyScoped(this.factory, factory);
/*  38:    */       } else {
/*  39: 72 */         TypeFactory.LOG.tracev("Scoping types to session factory {0}", factory);
/*  40:    */       }
/*  41: 74 */       this.factory = factory;
/*  42:    */     }
/*  43:    */     
/*  44:    */     public SessionFactoryImplementor resolveFactory()
/*  45:    */     {
/*  46: 78 */       if (this.factory == null) {
/*  47: 79 */         throw new HibernateException("SessionFactory for type scoping not yet known");
/*  48:    */       }
/*  49: 81 */       return this.factory;
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void injectSessionFactory(SessionFactoryImplementor factory)
/*  54:    */   {
/*  55: 86 */     this.typeScope.injectSessionFactory(factory);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public SessionFactoryImplementor resolveSessionFactory()
/*  59:    */   {
/*  60: 90 */     return this.typeScope.resolveFactory();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Type byClass(Class clazz, Properties parameters)
/*  64:    */   {
/*  65: 94 */     if (Type.class.isAssignableFrom(clazz)) {
/*  66: 95 */       return type(clazz, parameters);
/*  67:    */     }
/*  68: 98 */     if (CompositeUserType.class.isAssignableFrom(clazz)) {
/*  69: 99 */       return customComponent(clazz, parameters);
/*  70:    */     }
/*  71:102 */     if (UserType.class.isAssignableFrom(clazz)) {
/*  72:103 */       return custom(clazz, parameters);
/*  73:    */     }
/*  74:106 */     if (Lifecycle.class.isAssignableFrom(clazz)) {
/*  75:108 */       return manyToOne(clazz.getName());
/*  76:    */     }
/*  77:111 */     if (Serializable.class.isAssignableFrom(clazz)) {
/*  78:112 */       return serializable(clazz);
/*  79:    */     }
/*  80:115 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Type type(Class<Type> typeClass, Properties parameters)
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:120 */       Type type = (Type)typeClass.newInstance();
/*  88:121 */       injectParameters(type, parameters);
/*  89:122 */       return type;
/*  90:    */     }
/*  91:    */     catch (Exception e)
/*  92:    */     {
/*  93:125 */       throw new MappingException("Could not instantiate Type: " + typeClass.getName(), e);
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static void injectParameters(Object type, Properties parameters)
/*  98:    */   {
/*  99:130 */     if (ParameterizedType.class.isInstance(type)) {
/* 100:131 */       ((ParameterizedType)type).setParameterValues(parameters);
/* 101:133 */     } else if ((parameters != null) && (!parameters.isEmpty())) {
/* 102:134 */       throw new MappingException("type is not parameterized: " + type.getClass().getName());
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public CompositeCustomType customComponent(Class<CompositeUserType> typeClass, Properties parameters)
/* 107:    */   {
/* 108:139 */     return customComponent(typeClass, parameters, this.typeScope);
/* 109:    */   }
/* 110:    */   
/* 111:    */   @Deprecated
/* 112:    */   public static CompositeCustomType customComponent(Class<CompositeUserType> typeClass, Properties parameters, TypeScope scope)
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:149 */       CompositeUserType userType = (CompositeUserType)typeClass.newInstance();
/* 117:150 */       injectParameters(userType, parameters);
/* 118:151 */       return new CompositeCustomType(userType);
/* 119:    */     }
/* 120:    */     catch (Exception e)
/* 121:    */     {
/* 122:154 */       throw new MappingException("Unable to instantiate custom type: " + typeClass.getName(), e);
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public CollectionType customCollection(String typeName, Properties typeParameters, String role, String propertyRef, boolean embedded)
/* 127:    */   {
/* 128:    */     Class typeClass;
/* 129:    */     try
/* 130:    */     {
/* 131:166 */       typeClass = ReflectHelper.classForName(typeName);
/* 132:    */     }
/* 133:    */     catch (ClassNotFoundException cnfe)
/* 134:    */     {
/* 135:169 */       throw new MappingException("user collection type class not found: " + typeName, cnfe);
/* 136:    */     }
/* 137:171 */     CustomCollectionType result = new CustomCollectionType(this.typeScope, typeClass, role, propertyRef, embedded);
/* 138:172 */     if (typeParameters != null) {
/* 139:173 */       injectParameters(result.getUserType(), typeParameters);
/* 140:    */     }
/* 141:175 */     return result;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public CustomType custom(Class<UserType> typeClass, Properties parameters)
/* 145:    */   {
/* 146:179 */     return custom(typeClass, parameters, this.typeScope);
/* 147:    */   }
/* 148:    */   
/* 149:    */   @Deprecated
/* 150:    */   public static CustomType custom(Class<UserType> typeClass, Properties parameters, TypeScope scope)
/* 151:    */   {
/* 152:    */     try
/* 153:    */     {
/* 154:188 */       UserType userType = (UserType)typeClass.newInstance();
/* 155:189 */       injectParameters(userType, parameters);
/* 156:190 */       return new CustomType(userType);
/* 157:    */     }
/* 158:    */     catch (Exception e)
/* 159:    */     {
/* 160:193 */       throw new MappingException("Unable to instantiate custom type: " + typeClass.getName(), e);
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static <T extends Serializable> SerializableType<T> serializable(Class<T> serializableClass)
/* 165:    */   {
/* 166:206 */     return new SerializableType(serializableClass);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public EntityType oneToOne(String persistentClass, ForeignKeyDirection foreignKeyType, String uniqueKeyPropertyName, boolean lazy, boolean unwrapProxy, boolean isEmbeddedInXML, String entityName, String propertyName)
/* 170:    */   {
/* 171:221 */     return new OneToOneType(this.typeScope, persistentClass, foreignKeyType, uniqueKeyPropertyName, lazy, unwrapProxy, isEmbeddedInXML, entityName, propertyName);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public EntityType specialOneToOne(String persistentClass, ForeignKeyDirection foreignKeyType, String uniqueKeyPropertyName, boolean lazy, boolean unwrapProxy, String entityName, String propertyName)
/* 175:    */   {
/* 176:233 */     return new SpecialOneToOneType(this.typeScope, persistentClass, foreignKeyType, uniqueKeyPropertyName, lazy, unwrapProxy, entityName, propertyName);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public EntityType manyToOne(String persistentClass)
/* 180:    */   {
/* 181:241 */     return new ManyToOneType(this.typeScope, persistentClass);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public EntityType manyToOne(String persistentClass, boolean lazy)
/* 185:    */   {
/* 186:245 */     return new ManyToOneType(this.typeScope, persistentClass, lazy);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public EntityType manyToOne(String persistentClass, String uniqueKeyPropertyName, boolean lazy, boolean unwrapProxy, boolean isEmbeddedInXML, boolean ignoreNotFound, boolean isLogicalOneToOne)
/* 190:    */   {
/* 191:256 */     return new ManyToOneType(this.typeScope, persistentClass, uniqueKeyPropertyName, lazy, unwrapProxy, isEmbeddedInXML, ignoreNotFound, isLogicalOneToOne);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public CollectionType array(String role, String propertyRef, boolean embedded, Class elementClass)
/* 195:    */   {
/* 196:272 */     return new ArrayType(this.typeScope, role, propertyRef, elementClass, embedded);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public CollectionType list(String role, String propertyRef, boolean embedded)
/* 200:    */   {
/* 201:276 */     return new ListType(this.typeScope, role, propertyRef, embedded);
/* 202:    */   }
/* 203:    */   
/* 204:    */   public CollectionType bag(String role, String propertyRef, boolean embedded)
/* 205:    */   {
/* 206:280 */     return new BagType(this.typeScope, role, propertyRef, embedded);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public CollectionType idbag(String role, String propertyRef, boolean embedded)
/* 210:    */   {
/* 211:284 */     return new IdentifierBagType(this.typeScope, role, propertyRef, embedded);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public CollectionType map(String role, String propertyRef, boolean embedded)
/* 215:    */   {
/* 216:288 */     return new MapType(this.typeScope, role, propertyRef, embedded);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public CollectionType orderedMap(String role, String propertyRef, boolean embedded)
/* 220:    */   {
/* 221:292 */     return new OrderedMapType(this.typeScope, role, propertyRef, embedded);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public CollectionType sortedMap(String role, String propertyRef, boolean embedded, Comparator comparator)
/* 225:    */   {
/* 226:296 */     return new SortedMapType(this.typeScope, role, propertyRef, comparator, embedded);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public CollectionType set(String role, String propertyRef, boolean embedded)
/* 230:    */   {
/* 231:300 */     return new SetType(this.typeScope, role, propertyRef, embedded);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public CollectionType orderedSet(String role, String propertyRef, boolean embedded)
/* 235:    */   {
/* 236:304 */     return new OrderedSetType(this.typeScope, role, propertyRef, embedded);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public CollectionType sortedSet(String role, String propertyRef, boolean embedded, Comparator comparator)
/* 240:    */   {
/* 241:308 */     return new SortedSetType(this.typeScope, role, propertyRef, comparator, embedded);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public ComponentType component(ComponentMetamodel metamodel)
/* 245:    */   {
/* 246:315 */     return new ComponentType(this.typeScope, metamodel);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public EmbeddedComponentType embeddedComponent(ComponentMetamodel metamodel)
/* 250:    */   {
/* 251:319 */     return new EmbeddedComponentType(this.typeScope, metamodel);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public Type any(Type metaType, Type identifierType)
/* 255:    */   {
/* 256:326 */     return new AnyType(metaType, identifierType);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public static abstract interface TypeScope
/* 260:    */     extends Serializable
/* 261:    */   {
/* 262:    */     public abstract SessionFactoryImplementor resolveFactory();
/* 263:    */   }
/* 264:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.TypeFactory
 * JD-Core Version:    0.7.0.1
 */