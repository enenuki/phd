/*   1:    */ package org.hibernate.proxy.pojo;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import org.hibernate.engine.spi.EntityKey;
/*   6:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   9:    */ import org.hibernate.internal.util.MarkerObject;
/*  10:    */ import org.hibernate.proxy.AbstractLazyInitializer;
/*  11:    */ import org.hibernate.type.CompositeType;
/*  12:    */ 
/*  13:    */ public abstract class BasicLazyInitializer
/*  14:    */   extends AbstractLazyInitializer
/*  15:    */ {
/*  16: 42 */   protected static final Object INVOKE_IMPLEMENTATION = new MarkerObject("INVOKE_IMPLEMENTATION");
/*  17:    */   protected final Class persistentClass;
/*  18:    */   protected final Method getIdentifierMethod;
/*  19:    */   protected final Method setIdentifierMethod;
/*  20:    */   protected final boolean overridesEquals;
/*  21:    */   protected final CompositeType componentIdType;
/*  22:    */   private Object replacement;
/*  23:    */   
/*  24:    */   protected BasicLazyInitializer(String entityName, Class persistentClass, Serializable id, Method getIdentifierMethod, Method setIdentifierMethod, CompositeType componentIdType, SessionImplementor session, boolean overridesEquals)
/*  25:    */   {
/*  26: 61 */     super(entityName, id, session);
/*  27: 62 */     this.persistentClass = persistentClass;
/*  28: 63 */     this.getIdentifierMethod = getIdentifierMethod;
/*  29: 64 */     this.setIdentifierMethod = setIdentifierMethod;
/*  30: 65 */     this.componentIdType = componentIdType;
/*  31: 66 */     this.overridesEquals = overridesEquals;
/*  32:    */   }
/*  33:    */   
/*  34:    */   protected abstract Object serializableProxy();
/*  35:    */   
/*  36:    */   protected final Object invoke(Method method, Object[] args, Object proxy)
/*  37:    */     throws Throwable
/*  38:    */   {
/*  39: 73 */     String methodName = method.getName();
/*  40: 74 */     int params = args.length;
/*  41: 76 */     if (params == 0)
/*  42:    */     {
/*  43: 77 */       if ("writeReplace".equals(methodName)) {
/*  44: 78 */         return getReplacement();
/*  45:    */       }
/*  46: 80 */       if ((!this.overridesEquals) && ("hashCode".equals(methodName))) {
/*  47: 81 */         return Integer.valueOf(System.identityHashCode(proxy));
/*  48:    */       }
/*  49: 83 */       if ((isUninitialized()) && (method.equals(this.getIdentifierMethod))) {
/*  50: 84 */         return getIdentifier();
/*  51:    */       }
/*  52: 86 */       if ("getHibernateLazyInitializer".equals(methodName)) {
/*  53: 87 */         return this;
/*  54:    */       }
/*  55:    */     }
/*  56: 90 */     else if (params == 1)
/*  57:    */     {
/*  58: 91 */       if ((!this.overridesEquals) && ("equals".equals(methodName))) {
/*  59: 92 */         return Boolean.valueOf(args[0] == proxy);
/*  60:    */       }
/*  61: 94 */       if (method.equals(this.setIdentifierMethod))
/*  62:    */       {
/*  63: 95 */         initialize();
/*  64: 96 */         setIdentifier((Serializable)args[0]);
/*  65: 97 */         return INVOKE_IMPLEMENTATION;
/*  66:    */       }
/*  67:    */     }
/*  68:102 */     if ((this.componentIdType != null) && (this.componentIdType.isMethodOf(method))) {
/*  69:103 */       return method.invoke(getIdentifier(), args);
/*  70:    */     }
/*  71:107 */     return INVOKE_IMPLEMENTATION;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private Object getReplacement()
/*  75:    */   {
/*  76:112 */     SessionImplementor session = getSession();
/*  77:113 */     if ((isUninitialized()) && (session != null) && (session.isOpen()))
/*  78:    */     {
/*  79:114 */       EntityKey key = session.generateEntityKey(getIdentifier(), session.getFactory().getEntityPersister(getEntityName()));
/*  80:    */       
/*  81:    */ 
/*  82:    */ 
/*  83:118 */       Object entity = session.getPersistenceContext().getEntity(key);
/*  84:119 */       if (entity != null) {
/*  85:119 */         setImplementation(entity);
/*  86:    */       }
/*  87:    */     }
/*  88:122 */     if (isUninitialized())
/*  89:    */     {
/*  90:123 */       if (this.replacement == null) {
/*  91:124 */         this.replacement = serializableProxy();
/*  92:    */       }
/*  93:126 */       return this.replacement;
/*  94:    */     }
/*  95:129 */     return getTarget();
/*  96:    */   }
/*  97:    */   
/*  98:    */   public final Class getPersistentClass()
/*  99:    */   {
/* 100:135 */     return this.persistentClass;
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.pojo.BasicLazyInitializer
 * JD-Core Version:    0.7.0.1
 */