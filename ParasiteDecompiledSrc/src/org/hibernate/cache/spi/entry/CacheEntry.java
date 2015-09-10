/*   1:    */ package org.hibernate.cache.spi.entry;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.Interceptor;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.engine.spi.SessionImplementor;
/*   9:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  10:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  11:    */ import org.hibernate.event.spi.EventSource;
/*  12:    */ import org.hibernate.event.spi.EventType;
/*  13:    */ import org.hibernate.event.spi.PreLoadEvent;
/*  14:    */ import org.hibernate.event.spi.PreLoadEventListener;
/*  15:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  16:    */ import org.hibernate.persister.entity.EntityPersister;
/*  17:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  18:    */ import org.hibernate.type.TypeHelper;
/*  19:    */ 
/*  20:    */ public final class CacheEntry
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private final Serializable[] disassembledState;
/*  24:    */   private final String subclass;
/*  25:    */   private final boolean lazyPropertiesAreUnfetched;
/*  26:    */   private final Object version;
/*  27:    */   
/*  28:    */   public String getSubclass()
/*  29:    */   {
/*  30: 55 */     return this.subclass;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean areLazyPropertiesUnfetched()
/*  34:    */   {
/*  35: 59 */     return this.lazyPropertiesAreUnfetched;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public CacheEntry(Object[] state, EntityPersister persister, boolean unfetched, Object version, SessionImplementor session, Object owner)
/*  39:    */     throws HibernateException
/*  40:    */   {
/*  41: 71 */     this.disassembledState = TypeHelper.disassemble(state, persister.getPropertyTypes(), persister.isLazyPropertiesCacheable() ? null : persister.getPropertyLaziness(), session, owner);
/*  42:    */     
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49: 79 */     this.subclass = persister.getEntityName();
/*  50: 80 */     this.lazyPropertiesAreUnfetched = ((unfetched) || (!persister.isLazyPropertiesCacheable()));
/*  51: 81 */     this.version = version;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Object getVersion()
/*  55:    */   {
/*  56: 85 */     return this.version;
/*  57:    */   }
/*  58:    */   
/*  59:    */   CacheEntry(Serializable[] state, String subclass, boolean unfetched, Object version)
/*  60:    */   {
/*  61: 89 */     this.disassembledState = state;
/*  62: 90 */     this.subclass = subclass;
/*  63: 91 */     this.lazyPropertiesAreUnfetched = unfetched;
/*  64: 92 */     this.version = version;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Object[] assemble(Object instance, Serializable id, EntityPersister persister, Interceptor interceptor, EventSource session)
/*  68:    */     throws HibernateException
/*  69:    */   {
/*  70:103 */     if (!persister.getEntityName().equals(this.subclass)) {
/*  71:104 */       throw new AssertionFailure("Tried to assemble a different subclass instance");
/*  72:    */     }
/*  73:107 */     return assemble(this.disassembledState, instance, id, persister, interceptor, session);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private static Object[] assemble(Serializable[] values, Object result, Serializable id, EntityPersister persister, Interceptor interceptor, EventSource session)
/*  77:    */     throws HibernateException
/*  78:    */   {
/*  79:120 */     Object[] assembledProps = TypeHelper.assemble(values, persister.getPropertyTypes(), session, result);
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:129 */     PreLoadEvent preLoadEvent = new PreLoadEvent(session).setEntity(result).setState(assembledProps).setId(id).setPersister(persister);
/*  89:    */     
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:135 */     EventListenerGroup<PreLoadEventListener> listenerGroup = ((EventListenerRegistry)session.getFactory().getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(EventType.PRE_LOAD);
/*  95:140 */     for (PreLoadEventListener listener : listenerGroup.listeners()) {
/*  96:141 */       listener.onPreLoad(preLoadEvent);
/*  97:    */     }
/*  98:144 */     persister.setPropertyValues(result, assembledProps);
/*  99:    */     
/* 100:146 */     return assembledProps;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Serializable[] getDisassembledState()
/* 104:    */   {
/* 105:153 */     return this.disassembledState;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String toString()
/* 109:    */   {
/* 110:157 */     return "CacheEntry(" + this.subclass + ')' + ArrayHelper.toString(this.disassembledState);
/* 111:    */   }
/* 112:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.entry.CacheEntry
 * JD-Core Version:    0.7.0.1
 */