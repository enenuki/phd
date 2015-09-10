/*   1:    */ package org.hibernate.event.spi;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.security.AccessController;
/*   5:    */ import java.security.PrivilegedAction;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Map;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ 
/*  11:    */ public class EventType<T>
/*  12:    */ {
/*  13: 41 */   public static final EventType<LoadEventListener> LOAD = new EventType("load", LoadEventListener.class);
/*  14: 43 */   public static final EventType<InitializeCollectionEventListener> INIT_COLLECTION = new EventType("load-collection", InitializeCollectionEventListener.class);
/*  15: 46 */   public static final EventType<SaveOrUpdateEventListener> SAVE_UPDATE = new EventType("save-update", SaveOrUpdateEventListener.class);
/*  16: 48 */   public static final EventType<SaveOrUpdateEventListener> UPDATE = new EventType("update", SaveOrUpdateEventListener.class);
/*  17: 50 */   public static final EventType<SaveOrUpdateEventListener> SAVE = new EventType("save", SaveOrUpdateEventListener.class);
/*  18: 52 */   public static final EventType<PersistEventListener> PERSIST = new EventType("create", PersistEventListener.class);
/*  19: 54 */   public static final EventType<PersistEventListener> PERSIST_ONFLUSH = new EventType("create-onflush", PersistEventListener.class);
/*  20: 57 */   public static final EventType<MergeEventListener> MERGE = new EventType("merge", MergeEventListener.class);
/*  21: 60 */   public static final EventType<DeleteEventListener> DELETE = new EventType("delete", DeleteEventListener.class);
/*  22: 63 */   public static final EventType<ReplicateEventListener> REPLICATE = new EventType("replicate", ReplicateEventListener.class);
/*  23: 66 */   public static final EventType<FlushEventListener> FLUSH = new EventType("flush", FlushEventListener.class);
/*  24: 68 */   public static final EventType<AutoFlushEventListener> AUTO_FLUSH = new EventType("auto-flush", AutoFlushEventListener.class);
/*  25: 70 */   public static final EventType<DirtyCheckEventListener> DIRTY_CHECK = new EventType("dirty-check", DirtyCheckEventListener.class);
/*  26: 72 */   public static final EventType<FlushEntityEventListener> FLUSH_ENTITY = new EventType("flush-entity", FlushEntityEventListener.class);
/*  27: 75 */   public static final EventType<EvictEventListener> EVICT = new EventType("evict", EvictEventListener.class);
/*  28: 78 */   public static final EventType<LockEventListener> LOCK = new EventType("lock", LockEventListener.class);
/*  29: 81 */   public static final EventType<RefreshEventListener> REFRESH = new EventType("refresh", RefreshEventListener.class);
/*  30: 84 */   public static final EventType<PreLoadEventListener> PRE_LOAD = new EventType("pre-load", PreLoadEventListener.class);
/*  31: 86 */   public static final EventType<PreDeleteEventListener> PRE_DELETE = new EventType("pre-delete", PreDeleteEventListener.class);
/*  32: 88 */   public static final EventType<PreUpdateEventListener> PRE_UPDATE = new EventType("pre-update", PreUpdateEventListener.class);
/*  33: 90 */   public static final EventType<PreInsertEventListener> PRE_INSERT = new EventType("pre-insert", PreInsertEventListener.class);
/*  34: 93 */   public static final EventType<PostLoadEventListener> POST_LOAD = new EventType("post-load", PostLoadEventListener.class);
/*  35: 95 */   public static final EventType<PostDeleteEventListener> POST_DELETE = new EventType("post-delete", PostDeleteEventListener.class);
/*  36: 97 */   public static final EventType<PostUpdateEventListener> POST_UPDATE = new EventType("post-update", PostUpdateEventListener.class);
/*  37: 99 */   public static final EventType<PostInsertEventListener> POST_INSERT = new EventType("post-insert", PostInsertEventListener.class);
/*  38:102 */   public static final EventType<PostDeleteEventListener> POST_COMMIT_DELETE = new EventType("post-commit-delete", PostDeleteEventListener.class);
/*  39:104 */   public static final EventType<PostUpdateEventListener> POST_COMMIT_UPDATE = new EventType("post-commit-update", PostUpdateEventListener.class);
/*  40:106 */   public static final EventType<PostInsertEventListener> POST_COMMIT_INSERT = new EventType("post-commit-insert", PostInsertEventListener.class);
/*  41:109 */   public static final EventType<PreCollectionRecreateEventListener> PRE_COLLECTION_RECREATE = new EventType("pre-collection-recreate", PreCollectionRecreateEventListener.class);
/*  42:111 */   public static final EventType<PreCollectionRemoveEventListener> PRE_COLLECTION_REMOVE = new EventType("pre-collection-remove", PreCollectionRemoveEventListener.class);
/*  43:113 */   public static final EventType<PreCollectionUpdateEventListener> PRE_COLLECTION_UPDATE = new EventType("pre-collection-update", PreCollectionUpdateEventListener.class);
/*  44:116 */   public static final EventType<PostCollectionRecreateEventListener> POST_COLLECTION_RECREATE = new EventType("post-collection-recreate", PostCollectionRecreateEventListener.class);
/*  45:118 */   public static final EventType<PostCollectionRemoveEventListener> POST_COLLECTION_REMOVE = new EventType("post-collection-remove", PostCollectionRemoveEventListener.class);
/*  46:120 */   public static final EventType<PostCollectionUpdateEventListener> POST_COLLECTION_UPDATE = new EventType("post-collection-update", PostCollectionUpdateEventListener.class);
/*  47:128 */   public static final Map<String, EventType> eventTypeByNameMap = (Map)AccessController.doPrivileged(new PrivilegedAction()
/*  48:    */   {
/*  49:    */     public Map<String, EventType> run()
/*  50:    */     {
/*  51:132 */       Map<String, EventType> typeByNameMap = new HashMap();
/*  52:133 */       Field[] fields = EventType.class.getDeclaredFields();
/*  53:134 */       int i = 0;
/*  54:134 */       for (int max = fields.length; i < max; i++) {
/*  55:135 */         if (EventType.class.isAssignableFrom(fields[i].getType())) {
/*  56:    */           try
/*  57:    */           {
/*  58:137 */             EventType typeField = (EventType)fields[i].get(null);
/*  59:138 */             typeByNameMap.put(typeField.eventName(), typeField);
/*  60:    */           }
/*  61:    */           catch (Exception t)
/*  62:    */           {
/*  63:141 */             throw new HibernateException("Unable to initialize EventType map", t);
/*  64:    */           }
/*  65:    */         }
/*  66:    */       }
/*  67:145 */       return typeByNameMap;
/*  68:    */     }
/*  69:128 */   });
/*  70:    */   private final String eventName;
/*  71:    */   private final Class<? extends T> baseListenerInterface;
/*  72:    */   
/*  73:    */   public static EventType resolveEventTypeByName(String eventName)
/*  74:    */   {
/*  75:160 */     if (eventName == null) {
/*  76:161 */       throw new HibernateException("event name to resolve cannot be null");
/*  77:    */     }
/*  78:163 */     EventType eventType = (EventType)eventTypeByNameMap.get(eventName);
/*  79:164 */     if (eventType == null) {
/*  80:165 */       throw new HibernateException("Unable to locate proper event type for event name [" + eventName + "]");
/*  81:    */     }
/*  82:167 */     return eventType;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static Collection<EventType> values()
/*  86:    */   {
/*  87:176 */     return eventTypeByNameMap.values();
/*  88:    */   }
/*  89:    */   
/*  90:    */   private EventType(String eventName, Class<? extends T> baseListenerInterface)
/*  91:    */   {
/*  92:184 */     this.eventName = eventName;
/*  93:185 */     this.baseListenerInterface = baseListenerInterface;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String eventName()
/*  97:    */   {
/*  98:189 */     return this.eventName;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public Class baseListenerInterface()
/* 102:    */   {
/* 103:193 */     return this.baseListenerInterface;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String toString()
/* 107:    */   {
/* 108:198 */     return eventName();
/* 109:    */   }
/* 110:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.EventType
 * JD-Core Version:    0.7.0.1
 */