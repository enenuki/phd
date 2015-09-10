/*   1:    */ package org.hibernate.tuple;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.lang.reflect.Constructor;
/*   7:    */ import org.hibernate.InstantiationException;
/*   8:    */ import org.hibernate.PropertyNotFoundException;
/*   9:    */ import org.hibernate.bytecode.spi.ReflectionOptimizer.InstantiationOptimizer;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.internal.util.ReflectHelper;
/*  12:    */ import org.hibernate.internal.util.Value;
/*  13:    */ import org.hibernate.mapping.Component;
/*  14:    */ import org.hibernate.mapping.PersistentClass;
/*  15:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  16:    */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  17:    */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  18:    */ import org.hibernate.metamodel.domain.Entity;
/*  19:    */ import org.jboss.logging.Logger;
/*  20:    */ 
/*  21:    */ public class PojoInstantiator
/*  22:    */   implements Instantiator, Serializable
/*  23:    */ {
/*  24: 47 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PojoInstantiator.class.getName());
/*  25:    */   private transient Constructor constructor;
/*  26:    */   private final Class mappedClass;
/*  27:    */   private final transient ReflectionOptimizer.InstantiationOptimizer optimizer;
/*  28:    */   private final boolean embeddedIdentifier;
/*  29:    */   private final Class proxyInterface;
/*  30:    */   private final boolean isAbstract;
/*  31:    */   
/*  32:    */   public PojoInstantiator(Component component, ReflectionOptimizer.InstantiationOptimizer optimizer)
/*  33:    */   {
/*  34: 58 */     this.mappedClass = component.getComponentClass();
/*  35: 59 */     this.isAbstract = ReflectHelper.isAbstractClass(this.mappedClass);
/*  36: 60 */     this.optimizer = optimizer;
/*  37:    */     
/*  38: 62 */     this.proxyInterface = null;
/*  39: 63 */     this.embeddedIdentifier = false;
/*  40:    */     try
/*  41:    */     {
/*  42: 66 */       this.constructor = ReflectHelper.getDefaultConstructor(this.mappedClass);
/*  43:    */     }
/*  44:    */     catch (PropertyNotFoundException pnfe)
/*  45:    */     {
/*  46: 69 */       LOG.noDefaultConstructor(this.mappedClass.getName());
/*  47: 70 */       this.constructor = null;
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public PojoInstantiator(PersistentClass persistentClass, ReflectionOptimizer.InstantiationOptimizer optimizer)
/*  52:    */   {
/*  53: 75 */     this.mappedClass = persistentClass.getMappedClass();
/*  54: 76 */     this.isAbstract = ReflectHelper.isAbstractClass(this.mappedClass);
/*  55: 77 */     this.proxyInterface = persistentClass.getProxyInterface();
/*  56: 78 */     this.embeddedIdentifier = persistentClass.hasEmbeddedIdentifier();
/*  57: 79 */     this.optimizer = optimizer;
/*  58:    */     try
/*  59:    */     {
/*  60: 82 */       this.constructor = ReflectHelper.getDefaultConstructor(this.mappedClass);
/*  61:    */     }
/*  62:    */     catch (PropertyNotFoundException pnfe)
/*  63:    */     {
/*  64: 85 */       LOG.noDefaultConstructor(this.mappedClass.getName());
/*  65: 86 */       this.constructor = null;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public PojoInstantiator(EntityBinding entityBinding, ReflectionOptimizer.InstantiationOptimizer optimizer)
/*  70:    */   {
/*  71: 91 */     this.mappedClass = entityBinding.getEntity().getClassReference();
/*  72: 92 */     this.isAbstract = ReflectHelper.isAbstractClass(this.mappedClass);
/*  73: 93 */     this.proxyInterface = ((Class)entityBinding.getProxyInterfaceType().getValue());
/*  74: 94 */     this.embeddedIdentifier = entityBinding.getHierarchyDetails().getEntityIdentifier().isEmbedded();
/*  75: 95 */     this.optimizer = optimizer;
/*  76:    */     try
/*  77:    */     {
/*  78: 98 */       this.constructor = ReflectHelper.getDefaultConstructor(this.mappedClass);
/*  79:    */     }
/*  80:    */     catch (PropertyNotFoundException pnfe)
/*  81:    */     {
/*  82:101 */       LOG.noDefaultConstructor(this.mappedClass.getName());
/*  83:102 */       this.constructor = null;
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void readObject(ObjectInputStream stream)
/*  88:    */     throws ClassNotFoundException, IOException
/*  89:    */   {
/*  90:108 */     stream.defaultReadObject();
/*  91:109 */     this.constructor = ReflectHelper.getDefaultConstructor(this.mappedClass);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Object instantiate()
/*  95:    */   {
/*  96:113 */     if (this.isAbstract) {
/*  97:114 */       throw new InstantiationException("Cannot instantiate abstract class or interface: ", this.mappedClass);
/*  98:    */     }
/*  99:116 */     if (this.optimizer != null) {
/* 100:117 */       return this.optimizer.newInstance();
/* 101:    */     }
/* 102:119 */     if (this.constructor == null) {
/* 103:120 */       throw new InstantiationException("No default constructor for entity: ", this.mappedClass);
/* 104:    */     }
/* 105:    */     try
/* 106:    */     {
/* 107:124 */       return this.constructor.newInstance((Object[])null);
/* 108:    */     }
/* 109:    */     catch (Exception e)
/* 110:    */     {
/* 111:127 */       throw new InstantiationException("Could not instantiate entity: ", this.mappedClass, e);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Object instantiate(Serializable id)
/* 116:    */   {
/* 117:133 */     boolean useEmbeddedIdentifierInstanceAsEntity = (this.embeddedIdentifier) && (id != null) && (id.getClass().equals(this.mappedClass));
/* 118:    */     
/* 119:    */ 
/* 120:136 */     return useEmbeddedIdentifierInstanceAsEntity ? id : instantiate();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isInstance(Object object)
/* 124:    */   {
/* 125:140 */     return (this.mappedClass.isInstance(object)) || ((this.proxyInterface != null) && (this.proxyInterface.isInstance(object)));
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.PojoInstantiator
 * JD-Core Version:    0.7.0.1
 */