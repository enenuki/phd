/*   1:    */ package org.hibernate.tuple.entity;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import org.hibernate.EntityMode;
/*   5:    */ import org.hibernate.EntityNameResolver;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.internal.CoreMessageLogger;
/*   9:    */ import org.hibernate.mapping.PersistentClass;
/*  10:    */ import org.hibernate.mapping.Property;
/*  11:    */ import org.hibernate.metamodel.binding.AttributeBinding;
/*  12:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  13:    */ import org.hibernate.metamodel.domain.Attribute;
/*  14:    */ import org.hibernate.property.Getter;
/*  15:    */ import org.hibernate.property.PropertyAccessor;
/*  16:    */ import org.hibernate.property.PropertyAccessorFactory;
/*  17:    */ import org.hibernate.property.Setter;
/*  18:    */ import org.hibernate.proxy.ProxyFactory;
/*  19:    */ import org.hibernate.proxy.map.MapProxyFactory;
/*  20:    */ import org.hibernate.tuple.DynamicMapInstantiator;
/*  21:    */ import org.hibernate.tuple.Instantiator;
/*  22:    */ import org.jboss.logging.Logger;
/*  23:    */ 
/*  24:    */ public class DynamicMapEntityTuplizer
/*  25:    */   extends AbstractEntityTuplizer
/*  26:    */ {
/*  27: 55 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DynamicMapEntityTuplizer.class.getName());
/*  28:    */   
/*  29:    */   DynamicMapEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity)
/*  30:    */   {
/*  31: 59 */     super(entityMetamodel, mappedEntity);
/*  32:    */   }
/*  33:    */   
/*  34:    */   DynamicMapEntityTuplizer(EntityMetamodel entityMetamodel, EntityBinding mappedEntity)
/*  35:    */   {
/*  36: 63 */     super(entityMetamodel, mappedEntity);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public EntityMode getEntityMode()
/*  40:    */   {
/*  41: 70 */     return EntityMode.MAP;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private PropertyAccessor buildPropertyAccessor(Property mappedProperty)
/*  45:    */   {
/*  46: 74 */     if (mappedProperty.isBackRef()) {
/*  47: 75 */       return mappedProperty.getPropertyAccessor(null);
/*  48:    */     }
/*  49: 78 */     return PropertyAccessorFactory.getDynamicMapPropertyAccessor();
/*  50:    */   }
/*  51:    */   
/*  52:    */   protected Getter buildPropertyGetter(Property mappedProperty, PersistentClass mappedEntity)
/*  53:    */   {
/*  54: 87 */     return buildPropertyAccessor(mappedProperty).getGetter(null, mappedProperty.getName());
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected Setter buildPropertySetter(Property mappedProperty, PersistentClass mappedEntity)
/*  58:    */   {
/*  59: 95 */     return buildPropertyAccessor(mappedProperty).getSetter(null, mappedProperty.getName());
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Instantiator buildInstantiator(PersistentClass mappingInfo)
/*  63:    */   {
/*  64:103 */     return new DynamicMapInstantiator(mappingInfo);
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected ProxyFactory buildProxyFactory(PersistentClass mappingInfo, Getter idGetter, Setter idSetter)
/*  68:    */   {
/*  69:112 */     ProxyFactory pf = new MapProxyFactory();
/*  70:    */     try
/*  71:    */     {
/*  72:115 */       pf.postInstantiate(getEntityName(), null, null, null, null, null);
/*  73:    */     }
/*  74:    */     catch (HibernateException he)
/*  75:    */     {
/*  76:125 */       LOG.unableToCreateProxyFactory(getEntityName(), he);
/*  77:126 */       pf = null;
/*  78:    */     }
/*  79:128 */     return pf;
/*  80:    */   }
/*  81:    */   
/*  82:    */   private PropertyAccessor buildPropertyAccessor(AttributeBinding mappedProperty)
/*  83:    */   {
/*  84:137 */     return PropertyAccessorFactory.getDynamicMapPropertyAccessor();
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected Getter buildPropertyGetter(AttributeBinding mappedProperty)
/*  88:    */   {
/*  89:146 */     return buildPropertyAccessor(mappedProperty).getGetter(null, mappedProperty.getAttribute().getName());
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected Setter buildPropertySetter(AttributeBinding mappedProperty)
/*  93:    */   {
/*  94:154 */     return buildPropertyAccessor(mappedProperty).getSetter(null, mappedProperty.getAttribute().getName());
/*  95:    */   }
/*  96:    */   
/*  97:    */   protected Instantiator buildInstantiator(EntityBinding mappingInfo)
/*  98:    */   {
/*  99:162 */     return new DynamicMapInstantiator(mappingInfo);
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected ProxyFactory buildProxyFactory(EntityBinding mappingInfo, Getter idGetter, Setter idSetter)
/* 103:    */   {
/* 104:171 */     ProxyFactory pf = new MapProxyFactory();
/* 105:    */     try
/* 106:    */     {
/* 107:174 */       pf.postInstantiate(getEntityName(), null, null, null, null, null);
/* 108:    */     }
/* 109:    */     catch (HibernateException he)
/* 110:    */     {
/* 111:184 */       LOG.unableToCreateProxyFactory(getEntityName(), he);
/* 112:185 */       pf = null;
/* 113:    */     }
/* 114:187 */     return pf;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Class getMappedClass()
/* 118:    */   {
/* 119:194 */     return Map.class;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Class getConcreteProxyClass()
/* 123:    */   {
/* 124:201 */     return Map.class;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isInstrumented()
/* 128:    */   {
/* 129:208 */     return false;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public EntityNameResolver[] getEntityNameResolvers()
/* 133:    */   {
/* 134:215 */     return new EntityNameResolver[] { BasicEntityNameResolver.INSTANCE };
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory)
/* 138:    */   {
/* 139:222 */     return extractEmbeddedEntityName((Map)entityInstance);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static String extractEmbeddedEntityName(Map entity)
/* 143:    */   {
/* 144:226 */     return (String)entity.get("$type$");
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static class BasicEntityNameResolver
/* 148:    */     implements EntityNameResolver
/* 149:    */   {
/* 150:230 */     public static final BasicEntityNameResolver INSTANCE = new BasicEntityNameResolver();
/* 151:    */     
/* 152:    */     public String resolveEntityName(Object entity)
/* 153:    */     {
/* 154:236 */       if (!Map.class.isInstance(entity)) {
/* 155:237 */         return null;
/* 156:    */       }
/* 157:239 */       String entityName = DynamicMapEntityTuplizer.extractEmbeddedEntityName((Map)entity);
/* 158:240 */       if (entityName == null) {
/* 159:241 */         throw new HibernateException("Could not determine type of dynamic map entity");
/* 160:    */       }
/* 161:243 */       return entityName;
/* 162:    */     }
/* 163:    */     
/* 164:    */     public boolean equals(Object obj)
/* 165:    */     {
/* 166:251 */       return getClass().equals(obj.getClass());
/* 167:    */     }
/* 168:    */     
/* 169:    */     public int hashCode()
/* 170:    */     {
/* 171:259 */       return getClass().hashCode();
/* 172:    */     }
/* 173:    */   }
/* 174:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.entity.DynamicMapEntityTuplizer
 * JD-Core Version:    0.7.0.1
 */