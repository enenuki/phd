/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.LockMode;
/*   5:    */ import org.hibernate.engine.internal.Versioning;
/*   6:    */ import org.hibernate.engine.spi.EntityEntry;
/*   7:    */ import org.hibernate.engine.spi.EntityKey;
/*   8:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   9:    */ import org.hibernate.engine.spi.Status;
/*  10:    */ import org.hibernate.event.spi.AbstractEvent;
/*  11:    */ import org.hibernate.event.spi.EventSource;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.persister.entity.EntityPersister;
/*  14:    */ import org.hibernate.pretty.MessageHelper;
/*  15:    */ import org.hibernate.type.TypeHelper;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class AbstractReassociateEventListener
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21: 50 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractReassociateEventListener.class.getName());
/*  22:    */   
/*  23:    */   protected final EntityEntry reassociate(AbstractEvent event, Object object, Serializable id, EntityPersister persister)
/*  24:    */   {
/*  25: 65 */     if (LOG.isTraceEnabled()) {
/*  26: 66 */       LOG.tracev("Reassociating transient instance: {0}", MessageHelper.infoString(persister, id, event.getSession().getFactory()));
/*  27:    */     }
/*  28: 69 */     EventSource source = event.getSession();
/*  29: 70 */     EntityKey key = source.generateEntityKey(id, persister);
/*  30:    */     
/*  31: 72 */     source.getPersistenceContext().checkUniqueness(key, object);
/*  32:    */     
/*  33:    */ 
/*  34: 75 */     Object[] values = persister.getPropertyValues(object);
/*  35: 76 */     TypeHelper.deepCopy(values, persister.getPropertyTypes(), persister.getPropertyUpdateability(), values, source);
/*  36:    */     
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42: 83 */     Object version = Versioning.getVersion(values, persister);
/*  43:    */     
/*  44: 85 */     EntityEntry newEntry = source.getPersistenceContext().addEntity(object, persister.isMutable() ? Status.MANAGED : Status.READ_ONLY, values, key, version, LockMode.NONE, true, persister, false, true);
/*  45:    */     
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57: 98 */     new OnLockVisitor(source, id, object).process(object, persister);
/*  58:    */     
/*  59:100 */     persister.afterReassociate(object, source);
/*  60:    */     
/*  61:102 */     return newEntry;
/*  62:    */   }
/*  63:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.AbstractReassociateEventListener
 * JD-Core Version:    0.7.0.1
 */