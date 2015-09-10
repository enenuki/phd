/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.LockMode;
/*   6:    */ import org.hibernate.ReplicationMode;
/*   7:    */ import org.hibernate.TransientObjectException;
/*   8:    */ import org.hibernate.engine.internal.Cascade;
/*   9:    */ import org.hibernate.engine.spi.CascadingAction;
/*  10:    */ import org.hibernate.engine.spi.EntityKey;
/*  11:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.engine.spi.Status;
/*  14:    */ import org.hibernate.event.spi.EventSource;
/*  15:    */ import org.hibernate.event.spi.ReplicateEvent;
/*  16:    */ import org.hibernate.event.spi.ReplicateEventListener;
/*  17:    */ import org.hibernate.internal.CoreMessageLogger;
/*  18:    */ import org.hibernate.persister.entity.EntityPersister;
/*  19:    */ import org.hibernate.pretty.MessageHelper;
/*  20:    */ import org.hibernate.type.Type;
/*  21:    */ import org.jboss.logging.Logger;
/*  22:    */ 
/*  23:    */ public class DefaultReplicateEventListener
/*  24:    */   extends AbstractSaveEventListener
/*  25:    */   implements ReplicateEventListener
/*  26:    */ {
/*  27: 55 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultReplicateEventListener.class.getName());
/*  28:    */   
/*  29:    */   public void onReplicate(ReplicateEvent event)
/*  30:    */   {
/*  31: 66 */     EventSource source = event.getSession();
/*  32: 67 */     if (source.getPersistenceContext().reassociateIfUninitializedProxy(event.getObject()))
/*  33:    */     {
/*  34: 68 */       LOG.trace("Uninitialized proxy passed to replicate()");
/*  35: 69 */       return;
/*  36:    */     }
/*  37: 72 */     Object entity = source.getPersistenceContext().unproxyAndReassociate(event.getObject());
/*  38: 74 */     if (source.getPersistenceContext().isEntryFor(entity))
/*  39:    */     {
/*  40: 75 */       LOG.trace("Ignoring persistent instance passed to replicate()");
/*  41:    */       
/*  42: 77 */       return;
/*  43:    */     }
/*  44: 80 */     EntityPersister persister = source.getEntityPersister(event.getEntityName(), entity);
/*  45:    */     
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50: 86 */     Serializable id = persister.getIdentifier(entity, source);
/*  51: 87 */     if (id == null) {
/*  52: 88 */       throw new TransientObjectException("instance with null id passed to replicate()");
/*  53:    */     }
/*  54: 91 */     ReplicationMode replicationMode = event.getReplicationMode();
/*  55:    */     Object oldVersion;
/*  56:    */     Object oldVersion;
/*  57: 94 */     if (replicationMode == ReplicationMode.EXCEPTION) {
/*  58: 96 */       oldVersion = null;
/*  59:    */     } else {
/*  60:100 */       oldVersion = persister.getCurrentVersion(id, source);
/*  61:    */     }
/*  62:103 */     if (oldVersion != null)
/*  63:    */     {
/*  64:104 */       if (LOG.isTraceEnabled()) {
/*  65:105 */         LOG.tracev("Found existing row for {0}", MessageHelper.infoString(persister, id, source.getFactory()));
/*  66:    */       }
/*  67:109 */       Object realOldVersion = persister.isVersioned() ? oldVersion : null;
/*  68:    */       
/*  69:111 */       boolean canReplicate = replicationMode.shouldOverwriteCurrentVersion(entity, realOldVersion, persister.getVersion(entity), persister.getVersionType());
/*  70:120 */       if (canReplicate) {
/*  71:121 */         performReplication(entity, id, realOldVersion, persister, replicationMode, source);
/*  72:    */       } else {
/*  73:123 */         LOG.trace("No need to replicate");
/*  74:    */       }
/*  75:    */     }
/*  76:    */     else
/*  77:    */     {
/*  78:129 */       if (LOG.isTraceEnabled()) {
/*  79:130 */         LOG.tracev("No existing row, replicating new instance {0}", MessageHelper.infoString(persister, id, source.getFactory()));
/*  80:    */       }
/*  81:134 */       boolean regenerate = persister.isIdentifierAssignedByInsert();
/*  82:135 */       EntityKey key = regenerate ? null : source.generateEntityKey(id, persister);
/*  83:    */       
/*  84:137 */       performSaveOrReplicate(entity, key, persister, regenerate, replicationMode, source, true);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected boolean visitCollectionsBeforeSave(Object entity, Serializable id, Object[] values, Type[] types, EventSource source)
/*  89:    */   {
/*  90:153 */     OnReplicateVisitor visitor = new OnReplicateVisitor(source, id, entity, false);
/*  91:154 */     visitor.processEntityPropertyValues(values, types);
/*  92:155 */     return super.visitCollectionsBeforeSave(entity, id, values, types, source);
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected boolean substituteValuesIfNecessary(Object entity, Serializable id, Object[] values, EntityPersister persister, SessionImplementor source)
/*  96:    */   {
/*  97:165 */     return false;
/*  98:    */   }
/*  99:    */   
/* 100:    */   protected boolean isVersionIncrementDisabled()
/* 101:    */   {
/* 102:170 */     return true;
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void performReplication(Object entity, Serializable id, Object version, EntityPersister persister, ReplicationMode replicationMode, EventSource source)
/* 106:    */     throws HibernateException
/* 107:    */   {
/* 108:181 */     if (LOG.isTraceEnabled()) {
/* 109:182 */       LOG.tracev("Replicating changes to {0}", MessageHelper.infoString(persister, id, source.getFactory()));
/* 110:    */     }
/* 111:185 */     new OnReplicateVisitor(source, id, entity, true).process(entity, persister);
/* 112:    */     
/* 113:187 */     source.getPersistenceContext().addEntity(entity, persister.isMutable() ? Status.MANAGED : Status.READ_ONLY, null, source.generateEntityKey(id, persister), version, LockMode.NONE, true, persister, true, false);
/* 114:    */     
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:200 */     cascadeAfterReplicate(entity, persister, replicationMode, source);
/* 127:    */   }
/* 128:    */   
/* 129:    */   private void cascadeAfterReplicate(Object entity, EntityPersister persister, ReplicationMode replicationMode, EventSource source)
/* 130:    */   {
/* 131:208 */     source.getPersistenceContext().incrementCascadeLevel();
/* 132:    */     try
/* 133:    */     {
/* 134:210 */       new Cascade(CascadingAction.REPLICATE, 0, source).cascade(persister, entity, replicationMode);
/* 135:    */     }
/* 136:    */     finally
/* 137:    */     {
/* 138:214 */       source.getPersistenceContext().decrementCascadeLevel();
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected CascadingAction getCascadeAction()
/* 143:    */   {
/* 144:220 */     return CascadingAction.REPLICATE;
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultReplicateEventListener
 * JD-Core Version:    0.7.0.1
 */