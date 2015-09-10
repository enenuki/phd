/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import org.hibernate.HibernateException;
/*   4:    */ import org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer;
/*   5:    */ import org.hibernate.event.spi.EventSource;
/*   6:    */ import org.hibernate.persister.entity.EntityPersister;
/*   7:    */ import org.hibernate.type.CollectionType;
/*   8:    */ import org.hibernate.type.CompositeType;
/*   9:    */ import org.hibernate.type.EntityType;
/*  10:    */ import org.hibernate.type.Type;
/*  11:    */ 
/*  12:    */ public abstract class AbstractVisitor
/*  13:    */ {
/*  14:    */   private final EventSource session;
/*  15:    */   
/*  16:    */   AbstractVisitor(EventSource session)
/*  17:    */   {
/*  18: 48 */     this.session = session;
/*  19:    */   }
/*  20:    */   
/*  21:    */   void processValues(Object[] values, Type[] types)
/*  22:    */     throws HibernateException
/*  23:    */   {
/*  24: 59 */     for (int i = 0; i < types.length; i++) {
/*  25: 60 */       if (includeProperty(values, i)) {
/*  26: 61 */         processValue(i, values, types);
/*  27:    */       }
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void processEntityPropertyValues(Object[] values, Type[] types)
/*  32:    */     throws HibernateException
/*  33:    */   {
/*  34: 74 */     for (int i = 0; i < types.length; i++) {
/*  35: 75 */       if (includeEntityProperty(values, i)) {
/*  36: 76 */         processValue(i, values, types);
/*  37:    */       }
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   void processValue(int i, Object[] values, Type[] types)
/*  42:    */   {
/*  43: 82 */     processValue(values[i], types[i]);
/*  44:    */   }
/*  45:    */   
/*  46:    */   boolean includeEntityProperty(Object[] values, int i)
/*  47:    */   {
/*  48: 86 */     return includeProperty(values, i);
/*  49:    */   }
/*  50:    */   
/*  51:    */   boolean includeProperty(Object[] values, int i)
/*  52:    */   {
/*  53: 90 */     return values[i] != LazyPropertyInitializer.UNFETCHED_PROPERTY;
/*  54:    */   }
/*  55:    */   
/*  56:    */   Object processComponent(Object component, CompositeType componentType)
/*  57:    */     throws HibernateException
/*  58:    */   {
/*  59:101 */     if (component != null) {
/*  60:102 */       processValues(componentType.getPropertyValues(component, this.session), componentType.getSubtypes());
/*  61:    */     }
/*  62:107 */     return null;
/*  63:    */   }
/*  64:    */   
/*  65:    */   final Object processValue(Object value, Type type)
/*  66:    */     throws HibernateException
/*  67:    */   {
/*  68:119 */     if (type.isCollectionType()) {
/*  69:121 */       return processCollection(value, (CollectionType)type);
/*  70:    */     }
/*  71:123 */     if (type.isEntityType()) {
/*  72:124 */       return processEntity(value, (EntityType)type);
/*  73:    */     }
/*  74:126 */     if (type.isComponentType()) {
/*  75:127 */       return processComponent(value, (CompositeType)type);
/*  76:    */     }
/*  77:130 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   void process(Object object, EntityPersister persister)
/*  81:    */     throws HibernateException
/*  82:    */   {
/*  83:143 */     processEntityPropertyValues(persister.getPropertyValues(object), persister.getPropertyTypes());
/*  84:    */   }
/*  85:    */   
/*  86:    */   Object processCollection(Object collection, CollectionType type)
/*  87:    */     throws HibernateException
/*  88:    */   {
/*  89:158 */     return null;
/*  90:    */   }
/*  91:    */   
/*  92:    */   Object processEntity(Object value, EntityType entityType)
/*  93:    */     throws HibernateException
/*  94:    */   {
/*  95:171 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   final EventSource getSession()
/*  99:    */   {
/* 100:175 */     return this.session;
/* 101:    */   }
/* 102:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.AbstractVisitor
 * JD-Core Version:    0.7.0.1
 */