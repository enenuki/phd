/*   1:    */ package org.hibernate.tuple.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.concurrent.ConcurrentHashMap;
/*   7:    */ import org.hibernate.EntityMode;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.internal.util.ReflectHelper;
/*  10:    */ import org.hibernate.mapping.PersistentClass;
/*  11:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  12:    */ 
/*  13:    */ public class EntityTuplizerFactory
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16: 43 */   public static final Class[] ENTITY_TUP_CTOR_SIG = { EntityMetamodel.class, PersistentClass.class };
/*  17: 44 */   public static final Class[] ENTITY_TUP_CTOR_SIG_NEW = { EntityMetamodel.class, EntityBinding.class };
/*  18: 46 */   private Map<EntityMode, Class<? extends EntityTuplizer>> defaultImplClassByMode = buildBaseMapping();
/*  19:    */   
/*  20:    */   public void registerDefaultTuplizerClass(EntityMode entityMode, Class<? extends EntityTuplizer> tuplizerClass)
/*  21:    */   {
/*  22: 56 */     assert (isEntityTuplizerImplementor(tuplizerClass)) : ("Specified tuplizer class [" + tuplizerClass.getName() + "] does not implement " + EntityTuplizer.class.getName());
/*  23:    */     
/*  24:    */ 
/*  25: 59 */     assert (hasProperConstructor(tuplizerClass, ENTITY_TUP_CTOR_SIG)) : ("Specified tuplizer class [" + tuplizerClass.getName() + "] is not properly instantiatable");
/*  26:    */     
/*  27: 61 */     assert (hasProperConstructor(tuplizerClass, ENTITY_TUP_CTOR_SIG_NEW)) : ("Specified tuplizer class [" + tuplizerClass.getName() + "] is not properly instantiatable");
/*  28: 62 */     this.defaultImplClassByMode.put(entityMode, tuplizerClass);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public EntityTuplizer constructTuplizer(String tuplizerClassName, EntityMetamodel metamodel, PersistentClass persistentClass)
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 83 */       Class<? extends EntityTuplizer> tuplizerClass = ReflectHelper.classForName(tuplizerClassName);
/*  36: 84 */       return constructTuplizer(tuplizerClass, metamodel, persistentClass);
/*  37:    */     }
/*  38:    */     catch (ClassNotFoundException e)
/*  39:    */     {
/*  40: 87 */       throw new HibernateException("Could not locate specified tuplizer class [" + tuplizerClassName + "]");
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public EntityTuplizer constructTuplizer(String tuplizerClassName, EntityMetamodel metamodel, EntityBinding entityBinding)
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48:109 */       Class<? extends EntityTuplizer> tuplizerClass = ReflectHelper.classForName(tuplizerClassName);
/*  49:110 */       return constructTuplizer(tuplizerClass, metamodel, entityBinding);
/*  50:    */     }
/*  51:    */     catch (ClassNotFoundException e)
/*  52:    */     {
/*  53:113 */       throw new HibernateException("Could not locate specified tuplizer class [" + tuplizerClassName + "]");
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   public EntityTuplizer constructTuplizer(Class<? extends EntityTuplizer> tuplizerClass, EntityMetamodel metamodel, PersistentClass persistentClass)
/*  58:    */   {
/*  59:132 */     Constructor<? extends EntityTuplizer> constructor = getProperConstructor(tuplizerClass, ENTITY_TUP_CTOR_SIG);
/*  60:133 */     assert (constructor != null) : ("Unable to locate proper constructor for tuplizer [" + tuplizerClass.getName() + "]");
/*  61:    */     try
/*  62:    */     {
/*  63:135 */       return (EntityTuplizer)constructor.newInstance(new Object[] { metamodel, persistentClass });
/*  64:    */     }
/*  65:    */     catch (Throwable t)
/*  66:    */     {
/*  67:138 */       throw new HibernateException("Unable to instantiate default tuplizer [" + tuplizerClass.getName() + "]", t);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public EntityTuplizer constructTuplizer(Class<? extends EntityTuplizer> tuplizerClass, EntityMetamodel metamodel, EntityBinding entityBinding)
/*  72:    */   {
/*  73:157 */     Constructor<? extends EntityTuplizer> constructor = getProperConstructor(tuplizerClass, ENTITY_TUP_CTOR_SIG_NEW);
/*  74:158 */     assert (constructor != null) : ("Unable to locate proper constructor for tuplizer [" + tuplizerClass.getName() + "]");
/*  75:    */     try
/*  76:    */     {
/*  77:160 */       return (EntityTuplizer)constructor.newInstance(new Object[] { metamodel, entityBinding });
/*  78:    */     }
/*  79:    */     catch (Throwable t)
/*  80:    */     {
/*  81:163 */       throw new HibernateException("Unable to instantiate default tuplizer [" + tuplizerClass.getName() + "]", t);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public EntityTuplizer constructDefaultTuplizer(EntityMode entityMode, EntityMetamodel metamodel, PersistentClass persistentClass)
/*  86:    */   {
/*  87:183 */     Class<? extends EntityTuplizer> tuplizerClass = (Class)this.defaultImplClassByMode.get(entityMode);
/*  88:184 */     if (tuplizerClass == null) {
/*  89:185 */       throw new HibernateException("could not determine default tuplizer class to use [" + entityMode + "]");
/*  90:    */     }
/*  91:188 */     return constructTuplizer(tuplizerClass, metamodel, persistentClass);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public EntityTuplizer constructDefaultTuplizer(EntityMode entityMode, EntityMetamodel metamodel, EntityBinding entityBinding)
/*  95:    */   {
/*  96:207 */     Class<? extends EntityTuplizer> tuplizerClass = (Class)this.defaultImplClassByMode.get(entityMode);
/*  97:208 */     if (tuplizerClass == null) {
/*  98:209 */       throw new HibernateException("could not determine default tuplizer class to use [" + entityMode + "]");
/*  99:    */     }
/* 100:212 */     return constructTuplizer(tuplizerClass, metamodel, entityBinding);
/* 101:    */   }
/* 102:    */   
/* 103:    */   private boolean isEntityTuplizerImplementor(Class tuplizerClass)
/* 104:    */   {
/* 105:216 */     return ReflectHelper.implementsInterface(tuplizerClass, EntityTuplizer.class);
/* 106:    */   }
/* 107:    */   
/* 108:    */   private boolean hasProperConstructor(Class<? extends EntityTuplizer> tuplizerClass, Class[] constructorArgs)
/* 109:    */   {
/* 110:220 */     return (getProperConstructor(tuplizerClass, constructorArgs) != null) && (!ReflectHelper.isAbstractClass(tuplizerClass));
/* 111:    */   }
/* 112:    */   
/* 113:    */   private Constructor<? extends EntityTuplizer> getProperConstructor(Class<? extends EntityTuplizer> clazz, Class[] constructorArgs)
/* 114:    */   {
/* 115:227 */     Constructor<? extends EntityTuplizer> constructor = null;
/* 116:    */     try
/* 117:    */     {
/* 118:229 */       constructor = clazz.getDeclaredConstructor(constructorArgs);
/* 119:230 */       if (!ReflectHelper.isPublic(constructor)) {
/* 120:    */         try
/* 121:    */         {
/* 122:233 */           constructor.setAccessible(true);
/* 123:    */         }
/* 124:    */         catch (SecurityException e)
/* 125:    */         {
/* 126:236 */           constructor = null;
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:    */     catch (NoSuchMethodException ignore) {}
/* 131:243 */     return constructor;
/* 132:    */   }
/* 133:    */   
/* 134:    */   private static Map<EntityMode, Class<? extends EntityTuplizer>> buildBaseMapping()
/* 135:    */   {
/* 136:247 */     Map<EntityMode, Class<? extends EntityTuplizer>> map = new ConcurrentHashMap();
/* 137:248 */     map.put(EntityMode.POJO, PojoEntityTuplizer.class);
/* 138:249 */     map.put(EntityMode.MAP, DynamicMapEntityTuplizer.class);
/* 139:250 */     return map;
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.entity.EntityTuplizerFactory
 * JD-Core Version:    0.7.0.1
 */