/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.engine.internal.Cascade;
/*   6:    */ import org.hibernate.engine.spi.CascadingAction;
/*   7:    */ import org.hibernate.engine.spi.EntityEntry;
/*   8:    */ import org.hibernate.engine.spi.EntityKey;
/*   9:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.event.spi.EventSource;
/*  12:    */ import org.hibernate.event.spi.EvictEvent;
/*  13:    */ import org.hibernate.event.spi.EvictEventListener;
/*  14:    */ import org.hibernate.internal.CoreMessageLogger;
/*  15:    */ import org.hibernate.persister.entity.EntityPersister;
/*  16:    */ import org.hibernate.pretty.MessageHelper;
/*  17:    */ import org.hibernate.proxy.HibernateProxy;
/*  18:    */ import org.hibernate.proxy.LazyInitializer;
/*  19:    */ import org.jboss.logging.Logger;
/*  20:    */ 
/*  21:    */ public class DefaultEvictEventListener
/*  22:    */   implements EvictEventListener
/*  23:    */ {
/*  24: 55 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultEvictEventListener.class.getName());
/*  25:    */   
/*  26:    */   public void onEvict(EvictEvent event)
/*  27:    */     throws HibernateException
/*  28:    */   {
/*  29: 65 */     EventSource source = event.getSession();
/*  30: 66 */     Object object = event.getObject();
/*  31: 67 */     PersistenceContext persistenceContext = source.getPersistenceContext();
/*  32: 69 */     if ((object instanceof HibernateProxy))
/*  33:    */     {
/*  34: 70 */       LazyInitializer li = ((HibernateProxy)object).getHibernateLazyInitializer();
/*  35: 71 */       Serializable id = li.getIdentifier();
/*  36: 72 */       EntityPersister persister = source.getFactory().getEntityPersister(li.getEntityName());
/*  37: 73 */       if (id == null) {
/*  38: 74 */         throw new IllegalArgumentException("null identifier");
/*  39:    */       }
/*  40: 77 */       EntityKey key = source.generateEntityKey(id, persister);
/*  41: 78 */       persistenceContext.removeProxy(key);
/*  42: 80 */       if (!li.isUninitialized())
/*  43:    */       {
/*  44: 81 */         Object entity = persistenceContext.removeEntity(key);
/*  45: 82 */         if (entity != null)
/*  46:    */         {
/*  47: 83 */           EntityEntry e = event.getSession().getPersistenceContext().removeEntry(entity);
/*  48: 84 */           doEvict(entity, key, e.getPersister(), event.getSession());
/*  49:    */         }
/*  50:    */       }
/*  51: 87 */       li.unsetSession();
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55: 90 */       EntityEntry e = persistenceContext.removeEntry(object);
/*  56: 91 */       if (e != null)
/*  57:    */       {
/*  58: 92 */         persistenceContext.removeEntity(e.getEntityKey());
/*  59: 93 */         doEvict(object, e.getEntityKey(), e.getPersister(), source);
/*  60:    */       }
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void doEvict(Object object, EntityKey key, EntityPersister persister, EventSource session)
/*  65:    */     throws HibernateException
/*  66:    */   {
/*  67:105 */     if (LOG.isTraceEnabled()) {
/*  68:106 */       LOG.tracev("Evicting {0}", MessageHelper.infoString(persister));
/*  69:    */     }
/*  70:110 */     if (persister.hasCollections()) {
/*  71:111 */       new EvictVisitor(session).process(object, persister);
/*  72:    */     }
/*  73:120 */     new Cascade(CascadingAction.EVICT, 0, session).cascade(persister, object);
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultEvictEventListener
 * JD-Core Version:    0.7.0.1
 */