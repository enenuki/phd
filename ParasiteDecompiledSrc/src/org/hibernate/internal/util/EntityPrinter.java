/*   1:    */ package org.hibernate.internal.util;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer;
/*   9:    */ import org.hibernate.engine.spi.EntityKey;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.TypedValue;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.persister.entity.EntityPersister;
/*  14:    */ import org.hibernate.type.Type;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public final class EntityPrinter
/*  18:    */ {
/*  19: 47 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EntityPrinter.class.getName());
/*  20:    */   private SessionFactoryImplementor factory;
/*  21:    */   
/*  22:    */   public String toString(String entityName, Object entity)
/*  23:    */     throws HibernateException
/*  24:    */   {
/*  25: 59 */     EntityPersister entityPersister = this.factory.getEntityPersister(entityName);
/*  26: 61 */     if (entityPersister == null) {
/*  27: 62 */       return entity.getClass().getName();
/*  28:    */     }
/*  29: 65 */     Map<String, String> result = new HashMap();
/*  30: 67 */     if (entityPersister.hasIdentifierProperty()) {
/*  31: 68 */       result.put(entityPersister.getIdentifierPropertyName(), entityPersister.getIdentifierType().toLoggableString(entityPersister.getIdentifier(entity), this.factory));
/*  32:    */     }
/*  33: 74 */     Type[] types = entityPersister.getPropertyTypes();
/*  34: 75 */     String[] names = entityPersister.getPropertyNames();
/*  35: 76 */     Object[] values = entityPersister.getPropertyValues(entity);
/*  36: 77 */     for (int i = 0; i < types.length; i++) {
/*  37: 78 */       if (!names[i].startsWith("_"))
/*  38:    */       {
/*  39: 79 */         String strValue = values[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY ? values[i].toString() : types[i].toLoggableString(values[i], this.factory);
/*  40:    */         
/*  41:    */ 
/*  42: 82 */         result.put(names[i], strValue);
/*  43:    */       }
/*  44:    */     }
/*  45: 85 */     return entityName + result.toString();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String toString(Type[] types, Object[] values)
/*  49:    */     throws HibernateException
/*  50:    */   {
/*  51: 89 */     StringBuilder buffer = new StringBuilder();
/*  52: 90 */     for (int i = 0; i < types.length; i++) {
/*  53: 91 */       if (types[i] != null) {
/*  54: 92 */         buffer.append(types[i].toLoggableString(values[i], this.factory)).append(", ");
/*  55:    */       }
/*  56:    */     }
/*  57: 95 */     return buffer.toString();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String toString(Map<String, TypedValue> namedTypedValues)
/*  61:    */     throws HibernateException
/*  62:    */   {
/*  63: 99 */     Map<String, String> result = new HashMap();
/*  64:100 */     for (Map.Entry<String, TypedValue> entry : namedTypedValues.entrySet()) {
/*  65:101 */       result.put(entry.getKey(), ((TypedValue)entry.getValue()).getType().toLoggableString(((TypedValue)entry.getValue()).getValue(), this.factory));
/*  66:    */     }
/*  67:108 */     return result.toString();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void toString(Iterable<Map.Entry<EntityKey, Object>> entitiesByEntityKey)
/*  71:    */     throws HibernateException
/*  72:    */   {
/*  73:113 */     if ((!LOG.isDebugEnabled()) || (!entitiesByEntityKey.iterator().hasNext())) {
/*  74:113 */       return;
/*  75:    */     }
/*  76:114 */     LOG.debug("Listing entities:");
/*  77:115 */     int i = 0;
/*  78:116 */     for (Map.Entry<EntityKey, Object> entityKeyAndEntity : entitiesByEntityKey)
/*  79:    */     {
/*  80:117 */       if (i++ > 20)
/*  81:    */       {
/*  82:118 */         LOG.debug("More......");
/*  83:119 */         break;
/*  84:    */       }
/*  85:121 */       LOG.debug(toString(((EntityKey)entityKeyAndEntity.getKey()).getEntityName(), entityKeyAndEntity.getValue()));
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public EntityPrinter(SessionFactoryImplementor factory)
/*  90:    */   {
/*  91:126 */     this.factory = factory;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.EntityPrinter
 * JD-Core Version:    0.7.0.1
 */