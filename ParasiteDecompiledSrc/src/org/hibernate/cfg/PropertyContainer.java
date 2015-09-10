/*   1:    */ package org.hibernate.cfg;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.TreeMap;
/*   8:    */ import javax.persistence.Access;
/*   9:    */ import javax.persistence.ManyToMany;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.OneToMany;
/*  12:    */ import javax.persistence.OneToOne;
/*  13:    */ import javax.persistence.Transient;
/*  14:    */ import org.hibernate.AnnotationException;
/*  15:    */ import org.hibernate.MappingException;
/*  16:    */ import org.hibernate.annotations.Any;
/*  17:    */ import org.hibernate.annotations.ManyToAny;
/*  18:    */ import org.hibernate.annotations.Target;
/*  19:    */ import org.hibernate.annotations.Type;
/*  20:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  21:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  22:    */ import org.hibernate.internal.CoreMessageLogger;
/*  23:    */ import org.hibernate.internal.util.StringHelper;
/*  24:    */ import org.jboss.logging.Logger;
/*  25:    */ 
/*  26:    */ class PropertyContainer
/*  27:    */ {
/*  28:    */   static
/*  29:    */   {
/*  30: 61 */     System.setProperty("jboss.i18n.generate-proxies", "true");
/*  31:    */   }
/*  32:    */   
/*  33: 64 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PropertyContainer.class.getName());
/*  34:    */   private final AccessType explicitClassDefinedAccessType;
/*  35:    */   private final TreeMap<String, XProperty> fieldAccessMap;
/*  36:    */   private final TreeMap<String, XProperty> propertyAccessMap;
/*  37:    */   private final XClass xClass;
/*  38:    */   private final XClass entityAtStake;
/*  39:    */   
/*  40:    */   PropertyContainer(XClass clazz, XClass entityAtStake)
/*  41:    */   {
/*  42: 89 */     this.xClass = clazz;
/*  43: 90 */     this.entityAtStake = entityAtStake;
/*  44:    */     
/*  45: 92 */     this.explicitClassDefinedAccessType = determineClassDefinedAccessStrategy();
/*  46:    */     
/*  47:    */ 
/*  48: 95 */     this.fieldAccessMap = initProperties(AccessType.FIELD);
/*  49: 96 */     this.propertyAccessMap = initProperties(AccessType.PROPERTY);
/*  50:    */     
/*  51: 98 */     considerExplicitFieldAndPropertyAccess();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public XClass getEntityAtStake()
/*  55:    */   {
/*  56:102 */     return this.entityAtStake;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public XClass getDeclaringClass()
/*  60:    */   {
/*  61:106 */     return this.xClass;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public AccessType getExplicitAccessStrategy()
/*  65:    */   {
/*  66:110 */     return this.explicitClassDefinedAccessType;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean hasExplicitAccessStrategy()
/*  70:    */   {
/*  71:114 */     return !this.explicitClassDefinedAccessType.equals(AccessType.DEFAULT);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Collection<XProperty> getProperties(AccessType accessType)
/*  75:    */   {
/*  76:118 */     assertTypesAreResolvable(accessType);
/*  77:119 */     if ((AccessType.DEFAULT == accessType) || (AccessType.PROPERTY == accessType)) {
/*  78:120 */       return Collections.unmodifiableCollection(this.propertyAccessMap.values());
/*  79:    */     }
/*  80:123 */     return Collections.unmodifiableCollection(this.fieldAccessMap.values());
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void assertTypesAreResolvable(AccessType access)
/*  84:    */   {
/*  85:    */     Map<String, XProperty> xprops;
/*  86:    */     Map<String, XProperty> xprops;
/*  87:129 */     if ((AccessType.PROPERTY.equals(access)) || (AccessType.DEFAULT.equals(access))) {
/*  88:130 */       xprops = this.propertyAccessMap;
/*  89:    */     } else {
/*  90:133 */       xprops = this.fieldAccessMap;
/*  91:    */     }
/*  92:135 */     for (XProperty property : xprops.values()) {
/*  93:136 */       if ((!property.isTypeResolved()) && (!discoverTypeWithoutReflection(property)))
/*  94:    */       {
/*  95:137 */         String msg = "Property " + StringHelper.qualify(this.xClass.getName(), property.getName()) + " has an unbound type and no explicit target entity. Resolve this Generic usage issue" + " or set an explicit target attribute (eg @OneToMany(target=) or use an explicit @Type";
/*  96:    */         
/*  97:    */ 
/*  98:140 */         throw new AnnotationException(msg);
/*  99:    */       }
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void considerExplicitFieldAndPropertyAccess()
/* 104:    */   {
/* 105:146 */     for (XProperty property : this.fieldAccessMap.values())
/* 106:    */     {
/* 107:147 */       Access access = (Access)property.getAnnotation(Access.class);
/* 108:148 */       if (access != null)
/* 109:    */       {
/* 110:155 */         AccessType accessType = AccessType.getAccessStrategy(access.value());
/* 111:156 */         if (accessType == AccessType.FIELD) {
/* 112:157 */           this.propertyAccessMap.put(property.getName(), property);
/* 113:    */         } else {
/* 114:160 */           LOG.debug("Placing @Access(AccessType.FIELD) on a field does not have any effect.");
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:164 */     for (XProperty property : this.propertyAccessMap.values())
/* 119:    */     {
/* 120:165 */       Access access = (Access)property.getAnnotation(Access.class);
/* 121:166 */       if (access != null)
/* 122:    */       {
/* 123:170 */         AccessType accessType = AccessType.getAccessStrategy(access.value());
/* 124:175 */         if (accessType == AccessType.PROPERTY) {
/* 125:176 */           this.fieldAccessMap.put(property.getName(), property);
/* 126:    */         } else {
/* 127:179 */           LOG.debug("Placing @Access(AccessType.PROPERTY) on a field does not have any effect.");
/* 128:    */         }
/* 129:    */       }
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   private TreeMap<String, XProperty> initProperties(AccessType access)
/* 134:    */   {
/* 135:193 */     if ((!AccessType.PROPERTY.equals(access)) && (!AccessType.FIELD.equals(access))) {
/* 136:194 */       throw new IllegalArgumentException("Access type has to be AccessType.FIELD or AccessType.Property");
/* 137:    */     }
/* 138:198 */     TreeMap<String, XProperty> propertiesMap = new TreeMap();
/* 139:199 */     List<XProperty> properties = this.xClass.getDeclaredProperties(access.getType());
/* 140:200 */     for (XProperty property : properties) {
/* 141:201 */       if (!mustBeSkipped(property)) {
/* 142:204 */         propertiesMap.put(property.getName(), property);
/* 143:    */       }
/* 144:    */     }
/* 145:206 */     return propertiesMap;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private AccessType determineClassDefinedAccessStrategy()
/* 149:    */   {
/* 150:212 */     AccessType hibernateDefinedAccessType = AccessType.DEFAULT;
/* 151:213 */     AccessType jpaDefinedAccessType = AccessType.DEFAULT;
/* 152:    */     
/* 153:215 */     org.hibernate.annotations.AccessType accessType = (org.hibernate.annotations.AccessType)this.xClass.getAnnotation(org.hibernate.annotations.AccessType.class);
/* 154:216 */     if (accessType != null) {
/* 155:217 */       hibernateDefinedAccessType = AccessType.getAccessStrategy(accessType.value());
/* 156:    */     }
/* 157:220 */     Access access = (Access)this.xClass.getAnnotation(Access.class);
/* 158:221 */     if (access != null) {
/* 159:222 */       jpaDefinedAccessType = AccessType.getAccessStrategy(access.value());
/* 160:    */     }
/* 161:225 */     if ((hibernateDefinedAccessType != AccessType.DEFAULT) && (jpaDefinedAccessType != AccessType.DEFAULT) && (hibernateDefinedAccessType != jpaDefinedAccessType)) {
/* 162:228 */       throw new MappingException("@AccessType and @Access specified with contradicting values. Use of @Access only is recommended. ");
/* 163:    */     }
/* 164:    */     AccessType classDefinedAccessType;
/* 165:    */     AccessType classDefinedAccessType;
/* 166:233 */     if (hibernateDefinedAccessType != AccessType.DEFAULT) {
/* 167:234 */       classDefinedAccessType = hibernateDefinedAccessType;
/* 168:    */     } else {
/* 169:237 */       classDefinedAccessType = jpaDefinedAccessType;
/* 170:    */     }
/* 171:239 */     return classDefinedAccessType;
/* 172:    */   }
/* 173:    */   
/* 174:    */   private static boolean discoverTypeWithoutReflection(XProperty p)
/* 175:    */   {
/* 176:243 */     if ((p.isAnnotationPresent(OneToOne.class)) && (!((OneToOne)p.getAnnotation(OneToOne.class)).targetEntity().equals(Void.TYPE))) {
/* 177:246 */       return true;
/* 178:    */     }
/* 179:248 */     if ((p.isAnnotationPresent(OneToMany.class)) && (!((OneToMany)p.getAnnotation(OneToMany.class)).targetEntity().equals(Void.TYPE))) {
/* 180:251 */       return true;
/* 181:    */     }
/* 182:253 */     if ((p.isAnnotationPresent(ManyToOne.class)) && (!((ManyToOne)p.getAnnotation(ManyToOne.class)).targetEntity().equals(Void.TYPE))) {
/* 183:256 */       return true;
/* 184:    */     }
/* 185:258 */     if ((p.isAnnotationPresent(ManyToMany.class)) && (!((ManyToMany)p.getAnnotation(ManyToMany.class)).targetEntity().equals(Void.TYPE))) {
/* 186:261 */       return true;
/* 187:    */     }
/* 188:263 */     if (p.isAnnotationPresent(Any.class)) {
/* 189:264 */       return true;
/* 190:    */     }
/* 191:266 */     if (p.isAnnotationPresent(ManyToAny.class))
/* 192:    */     {
/* 193:267 */       if ((!p.isCollection()) && (!p.isArray())) {
/* 194:268 */         throw new AnnotationException("@ManyToAny used on a non collection non array property: " + p.getName());
/* 195:    */       }
/* 196:270 */       return true;
/* 197:    */     }
/* 198:272 */     if (p.isAnnotationPresent(Type.class)) {
/* 199:273 */       return true;
/* 200:    */     }
/* 201:275 */     if (p.isAnnotationPresent(Target.class)) {
/* 202:276 */       return true;
/* 203:    */     }
/* 204:278 */     return false;
/* 205:    */   }
/* 206:    */   
/* 207:    */   private static boolean mustBeSkipped(XProperty property)
/* 208:    */   {
/* 209:283 */     return (property.isAnnotationPresent(Transient.class)) || ("net.sf.cglib.transform.impl.InterceptFieldCallback".equals(property.getType().getName())) || ("org.hibernate.bytecode.internal.javassist.FieldHandler".equals(property.getType().getName()));
/* 210:    */   }
/* 211:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.PropertyContainer
 * JD-Core Version:    0.7.0.1
 */