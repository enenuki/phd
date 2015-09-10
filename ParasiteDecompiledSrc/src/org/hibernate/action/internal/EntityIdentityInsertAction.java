/*   1:    */ package org.hibernate.action.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.engine.spi.EntityEntry;
/*   7:    */ import org.hibernate.engine.spi.EntityKey;
/*   8:    */ import org.hibernate.engine.spi.PersistenceContext;
/*   9:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  10:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  11:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  12:    */ import org.hibernate.event.spi.EventType;
/*  13:    */ import org.hibernate.event.spi.PostInsertEvent;
/*  14:    */ import org.hibernate.event.spi.PostInsertEventListener;
/*  15:    */ import org.hibernate.event.spi.PreInsertEvent;
/*  16:    */ import org.hibernate.event.spi.PreInsertEventListener;
/*  17:    */ import org.hibernate.persister.entity.EntityPersister;
/*  18:    */ import org.hibernate.stat.Statistics;
/*  19:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  20:    */ 
/*  21:    */ public final class EntityIdentityInsertAction
/*  22:    */   extends EntityAction
/*  23:    */ {
/*  24:    */   private transient Object[] state;
/*  25:    */   private final boolean isDelayed;
/*  26:    */   private final EntityKey delayedEntityKey;
/*  27:    */   private Serializable generatedId;
/*  28:    */   
/*  29:    */   public EntityIdentityInsertAction(Object[] state, Object instance, EntityPersister persister, SessionImplementor session, boolean isDelayed)
/*  30:    */     throws HibernateException
/*  31:    */   {
/*  32: 55 */     super(session, isDelayed ? generateDelayedPostInsertIdentifier() : null, instance, persister);
/*  33:    */     
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38: 61 */     this.state = state;
/*  39: 62 */     this.isDelayed = isDelayed;
/*  40: 63 */     this.delayedEntityKey = (isDelayed ? generateDelayedEntityKey() : null);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void execute()
/*  44:    */     throws HibernateException
/*  45:    */   {
/*  46: 68 */     EntityPersister persister = getPersister();
/*  47: 69 */     SessionImplementor session = getSession();
/*  48: 70 */     Object instance = getInstance();
/*  49:    */     
/*  50: 72 */     boolean veto = preInsert();
/*  51: 77 */     if (!veto)
/*  52:    */     {
/*  53: 78 */       this.generatedId = persister.insert(this.state, instance, session);
/*  54: 79 */       if (persister.hasInsertGeneratedProperties()) {
/*  55: 80 */         persister.processInsertGeneratedProperties(this.generatedId, instance, this.state, session);
/*  56:    */       }
/*  57: 84 */       persister.setIdentifier(instance, this.generatedId, session);
/*  58: 85 */       getSession().getPersistenceContext().registerInsertedKey(getPersister(), this.generatedId);
/*  59:    */     }
/*  60: 97 */     postInsert();
/*  61: 99 */     if ((session.getFactory().getStatistics().isStatisticsEnabled()) && (!veto)) {
/*  62:100 */       session.getFactory().getStatisticsImplementor().insertEntity(getPersister().getEntityName());
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean needsAfterTransactionCompletion()
/*  67:    */   {
/*  68:108 */     return hasPostCommitEventListeners();
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected boolean hasPostCommitEventListeners()
/*  72:    */   {
/*  73:113 */     return !listenerGroup(EventType.POST_COMMIT_INSERT).isEmpty();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void doAfterTransactionCompletion(boolean success, SessionImplementor session)
/*  77:    */   {
/*  78:123 */     postCommitInsert();
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void postInsert()
/*  82:    */   {
/*  83:127 */     if (this.isDelayed) {
/*  84:128 */       getSession().getPersistenceContext().replaceDelayedEntityIdentityInsertKeys(this.delayedEntityKey, this.generatedId);
/*  85:    */     }
/*  86:131 */     EventListenerGroup<PostInsertEventListener> listenerGroup = listenerGroup(EventType.POST_INSERT);
/*  87:132 */     if (listenerGroup.isEmpty()) {
/*  88:133 */       return;
/*  89:    */     }
/*  90:135 */     PostInsertEvent event = new PostInsertEvent(getInstance(), this.generatedId, this.state, getPersister(), eventSource());
/*  91:142 */     for (PostInsertEventListener listener : listenerGroup.listeners()) {
/*  92:143 */       listener.onPostInsert(event);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   private void postCommitInsert()
/*  97:    */   {
/*  98:148 */     EventListenerGroup<PostInsertEventListener> listenerGroup = listenerGroup(EventType.POST_COMMIT_INSERT);
/*  99:149 */     if (listenerGroup.isEmpty()) {
/* 100:150 */       return;
/* 101:    */     }
/* 102:152 */     PostInsertEvent event = new PostInsertEvent(getInstance(), this.generatedId, this.state, getPersister(), eventSource());
/* 103:159 */     for (PostInsertEventListener listener : listenerGroup.listeners()) {
/* 104:160 */       listener.onPostInsert(event);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   private boolean preInsert()
/* 109:    */   {
/* 110:165 */     EventListenerGroup<PreInsertEventListener> listenerGroup = listenerGroup(EventType.PRE_INSERT);
/* 111:166 */     if (listenerGroup.isEmpty()) {
/* 112:167 */       return false;
/* 113:    */     }
/* 114:169 */     boolean veto = false;
/* 115:170 */     PreInsertEvent event = new PreInsertEvent(getInstance(), null, this.state, getPersister(), eventSource());
/* 116:171 */     for (PreInsertEventListener listener : listenerGroup.listeners()) {
/* 117:172 */       veto |= listener.onPreInsert(event);
/* 118:    */     }
/* 119:174 */     return veto;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final Serializable getGeneratedId()
/* 123:    */   {
/* 124:178 */     return this.generatedId;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public EntityKey getDelayedEntityKey()
/* 128:    */   {
/* 129:182 */     return this.delayedEntityKey;
/* 130:    */   }
/* 131:    */   
/* 132:    */   private static synchronized DelayedPostInsertIdentifier generateDelayedPostInsertIdentifier()
/* 133:    */   {
/* 134:186 */     return new DelayedPostInsertIdentifier();
/* 135:    */   }
/* 136:    */   
/* 137:    */   private EntityKey generateDelayedEntityKey()
/* 138:    */   {
/* 139:190 */     if (!this.isDelayed) {
/* 140:191 */       throw new AssertionFailure("cannot request delayed entity-key for non-delayed post-insert-id generation");
/* 141:    */     }
/* 142:193 */     return getSession().generateEntityKey(getDelayedId(), getPersister());
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void afterDeserialize(SessionImplementor session)
/* 146:    */   {
/* 147:198 */     super.afterDeserialize(session);
/* 148:201 */     if (session != null)
/* 149:    */     {
/* 150:202 */       EntityEntry entityEntry = session.getPersistenceContext().getEntry(getInstance());
/* 151:203 */       this.state = entityEntry.getLoadedState();
/* 152:    */     }
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.EntityIdentityInsertAction
 * JD-Core Version:    0.7.0.1
 */