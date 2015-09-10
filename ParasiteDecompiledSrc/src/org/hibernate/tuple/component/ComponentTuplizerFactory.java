/*   1:    */ package org.hibernate.tuple.component;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.concurrent.ConcurrentHashMap;
/*   7:    */ import org.hibernate.EntityMode;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.internal.util.ReflectHelper;
/*  10:    */ import org.hibernate.mapping.Component;
/*  11:    */ 
/*  12:    */ public class ComponentTuplizerFactory
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15: 42 */   private static final Class[] COMPONENT_TUP_CTOR_SIG = { Component.class };
/*  16: 44 */   private Map<EntityMode, Class<? extends ComponentTuplizer>> defaultImplClassByMode = buildBaseMapping();
/*  17:    */   
/*  18:    */   public void registerDefaultTuplizerClass(EntityMode entityMode, Class<? extends ComponentTuplizer> tuplizerClass)
/*  19:    */   {
/*  20: 55 */     assert (isComponentTuplizerImplementor(tuplizerClass)) : ("Specified tuplizer class [" + tuplizerClass.getName() + "] does not implement " + ComponentTuplizer.class.getName());
/*  21:    */     
/*  22: 57 */     assert (hasProperConstructor(tuplizerClass)) : ("Specified tuplizer class [" + tuplizerClass.getName() + "] is not properly instantiatable");
/*  23:    */     
/*  24: 59 */     this.defaultImplClassByMode.put(entityMode, tuplizerClass);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ComponentTuplizer constructTuplizer(String tuplizerClassName, Component metadata)
/*  28:    */   {
/*  29:    */     try
/*  30:    */     {
/*  31: 76 */       Class<? extends ComponentTuplizer> tuplizerClass = ReflectHelper.classForName(tuplizerClassName);
/*  32: 77 */       return constructTuplizer(tuplizerClass, metadata);
/*  33:    */     }
/*  34:    */     catch (ClassNotFoundException e)
/*  35:    */     {
/*  36: 80 */       throw new HibernateException("Could not locate specified tuplizer class [" + tuplizerClassName + "]");
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ComponentTuplizer constructTuplizer(Class<? extends ComponentTuplizer> tuplizerClass, Component metadata)
/*  41:    */   {
/*  42: 95 */     Constructor<? extends ComponentTuplizer> constructor = getProperConstructor(tuplizerClass);
/*  43: 96 */     assert (constructor != null) : ("Unable to locate proper constructor for tuplizer [" + tuplizerClass.getName() + "]");
/*  44:    */     try
/*  45:    */     {
/*  46: 98 */       return (ComponentTuplizer)constructor.newInstance(new Object[] { metadata });
/*  47:    */     }
/*  48:    */     catch (Throwable t)
/*  49:    */     {
/*  50:101 */       throw new HibernateException("Unable to instantiate default tuplizer [" + tuplizerClass.getName() + "]", t);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ComponentTuplizer constructDefaultTuplizer(EntityMode entityMode, Component metadata)
/*  55:    */   {
/*  56:117 */     Class<? extends ComponentTuplizer> tuplizerClass = (Class)this.defaultImplClassByMode.get(entityMode);
/*  57:118 */     if (tuplizerClass == null) {
/*  58:119 */       throw new HibernateException("could not determine default tuplizer class to use [" + entityMode + "]");
/*  59:    */     }
/*  60:122 */     return constructTuplizer(tuplizerClass, metadata);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private boolean isComponentTuplizerImplementor(Class tuplizerClass)
/*  64:    */   {
/*  65:126 */     return ReflectHelper.implementsInterface(tuplizerClass, ComponentTuplizer.class);
/*  66:    */   }
/*  67:    */   
/*  68:    */   private boolean hasProperConstructor(Class tuplizerClass)
/*  69:    */   {
/*  70:131 */     return getProperConstructor(tuplizerClass) != null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private Constructor<? extends ComponentTuplizer> getProperConstructor(Class<? extends ComponentTuplizer> clazz)
/*  74:    */   {
/*  75:135 */     Constructor<? extends ComponentTuplizer> constructor = null;
/*  76:    */     try
/*  77:    */     {
/*  78:137 */       constructor = clazz.getDeclaredConstructor(COMPONENT_TUP_CTOR_SIG);
/*  79:138 */       if (!ReflectHelper.isPublic(constructor)) {
/*  80:    */         try
/*  81:    */         {
/*  82:141 */           constructor.setAccessible(true);
/*  83:    */         }
/*  84:    */         catch (SecurityException e)
/*  85:    */         {
/*  86:144 */           constructor = null;
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90:    */     catch (NoSuchMethodException ignore) {}
/*  91:151 */     return constructor;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private static Map<EntityMode, Class<? extends ComponentTuplizer>> buildBaseMapping()
/*  95:    */   {
/*  96:155 */     Map<EntityMode, Class<? extends ComponentTuplizer>> map = new ConcurrentHashMap();
/*  97:156 */     map.put(EntityMode.POJO, PojoComponentTuplizer.class);
/*  98:157 */     map.put(EntityMode.MAP, DynamicMapComponentTuplizer.class);
/*  99:158 */     return map;
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.component.ComponentTuplizerFactory
 * JD-Core Version:    0.7.0.1
 */