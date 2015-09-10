/*   1:    */ package org.hibernate.action.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.action.spi.AfterTransactionCompletionProcess;
/*   6:    */ import org.hibernate.action.spi.BeforeTransactionCompletionProcess;
/*   7:    */ import org.hibernate.action.spi.Executable;
/*   8:    */ import org.hibernate.engine.spi.EntityEntry;
/*   9:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  13:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  14:    */ import org.hibernate.event.spi.EventSource;
/*  15:    */ import org.hibernate.event.spi.EventType;
/*  16:    */ import org.hibernate.internal.util.StringHelper;
/*  17:    */ import org.hibernate.persister.entity.EntityPersister;
/*  18:    */ import org.hibernate.pretty.MessageHelper;
/*  19:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  20:    */ import org.hibernate.type.Type;
/*  21:    */ 
/*  22:    */ public abstract class EntityAction
/*  23:    */   implements Executable, Serializable, Comparable, AfterTransactionCompletionProcess
/*  24:    */ {
/*  25:    */   private final String entityName;
/*  26:    */   private final Serializable id;
/*  27:    */   private transient Object instance;
/*  28:    */   private transient SessionImplementor session;
/*  29:    */   private transient EntityPersister persister;
/*  30:    */   
/*  31:    */   protected EntityAction(SessionImplementor session, Serializable id, Object instance, EntityPersister persister)
/*  32:    */   {
/*  33: 66 */     this.entityName = persister.getEntityName();
/*  34: 67 */     this.id = id;
/*  35: 68 */     this.instance = instance;
/*  36: 69 */     this.session = session;
/*  37: 70 */     this.persister = persister;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public BeforeTransactionCompletionProcess getBeforeTransactionCompletionProcess()
/*  41:    */   {
/*  42: 75 */     return null;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public AfterTransactionCompletionProcess getAfterTransactionCompletionProcess()
/*  46:    */   {
/*  47: 80 */     return needsAfterTransactionCompletion() ? this : null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected abstract boolean hasPostCommitEventListeners();
/*  51:    */   
/*  52:    */   public boolean needsAfterTransactionCompletion()
/*  53:    */   {
/*  54: 88 */     return (this.persister.hasCache()) || (hasPostCommitEventListeners());
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getEntityName()
/*  58:    */   {
/*  59: 97 */     return this.entityName;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final Serializable getId()
/*  63:    */   {
/*  64:106 */     if ((this.id instanceof DelayedPostInsertIdentifier))
/*  65:    */     {
/*  66:107 */       Serializable eeId = this.session.getPersistenceContext().getEntry(this.instance).getId();
/*  67:108 */       return (eeId instanceof DelayedPostInsertIdentifier) ? null : eeId;
/*  68:    */     }
/*  69:110 */     return this.id;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final DelayedPostInsertIdentifier getDelayedId()
/*  73:    */   {
/*  74:114 */     return DelayedPostInsertIdentifier.class.isInstance(this.id) ? (DelayedPostInsertIdentifier)DelayedPostInsertIdentifier.class.cast(this.id) : null;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final Object getInstance()
/*  78:    */   {
/*  79:125 */     return this.instance;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final SessionImplementor getSession()
/*  83:    */   {
/*  84:134 */     return this.session;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final EntityPersister getPersister()
/*  88:    */   {
/*  89:143 */     return this.persister;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final Serializable[] getPropertySpaces()
/*  93:    */   {
/*  94:148 */     return this.persister.getPropertySpaces();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void beforeExecutions()
/*  98:    */   {
/*  99:153 */     throw new AssertionFailure("beforeExecutions() called for non-collection action");
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String toString()
/* 103:    */   {
/* 104:158 */     return StringHelper.unqualify(getClass().getName()) + MessageHelper.infoString(this.entityName, this.id);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int compareTo(Object other)
/* 108:    */   {
/* 109:163 */     EntityAction action = (EntityAction)other;
/* 110:    */     
/* 111:165 */     int roleComparison = this.entityName.compareTo(action.entityName);
/* 112:166 */     if (roleComparison != 0) {
/* 113:167 */       return roleComparison;
/* 114:    */     }
/* 115:171 */     return this.persister.getIdentifierType().compare(this.id, action.id);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void afterDeserialize(SessionImplementor session)
/* 119:    */   {
/* 120:181 */     if ((this.session != null) || (this.persister != null)) {
/* 121:182 */       throw new IllegalStateException("already attached to a session.");
/* 122:    */     }
/* 123:186 */     if (session != null)
/* 124:    */     {
/* 125:187 */       this.session = session;
/* 126:188 */       this.persister = session.getFactory().getEntityPersister(this.entityName);
/* 127:189 */       this.instance = session.getPersistenceContext().getEntity(session.generateEntityKey(this.id, this.persister));
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected <T> EventListenerGroup<T> listenerGroup(EventType<T> eventType)
/* 132:    */   {
/* 133:194 */     return ((EventListenerRegistry)getSession().getFactory().getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(eventType);
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected EventSource eventSource()
/* 137:    */   {
/* 138:202 */     return (EventSource)getSession();
/* 139:    */   }
/* 140:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.EntityAction
 * JD-Core Version:    0.7.0.1
 */