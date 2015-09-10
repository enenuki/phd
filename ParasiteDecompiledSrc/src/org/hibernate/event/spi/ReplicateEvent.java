/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.ReplicationMode;
/*  4:   */ 
/*  5:   */ public class ReplicateEvent
/*  6:   */   extends AbstractEvent
/*  7:   */ {
/*  8:   */   private Object object;
/*  9:   */   private ReplicationMode replicationMode;
/* 10:   */   private String entityName;
/* 11:   */   
/* 12:   */   public ReplicateEvent(Object object, ReplicationMode replicationMode, EventSource source)
/* 13:   */   {
/* 14:39 */     this(null, object, replicationMode, source);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public ReplicateEvent(String entityName, Object object, ReplicationMode replicationMode, EventSource source)
/* 18:   */   {
/* 19:43 */     super(source);
/* 20:44 */     this.entityName = entityName;
/* 21:46 */     if (object == null) {
/* 22:47 */       throw new IllegalArgumentException("attempt to create replication strategy with null entity");
/* 23:   */     }
/* 24:51 */     if (replicationMode == null) {
/* 25:52 */       throw new IllegalArgumentException("attempt to create replication strategy with null replication mode");
/* 26:   */     }
/* 27:57 */     this.object = object;
/* 28:58 */     this.replicationMode = replicationMode;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object getObject()
/* 32:   */   {
/* 33:62 */     return this.object;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setObject(Object object)
/* 37:   */   {
/* 38:66 */     this.object = object;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public ReplicationMode getReplicationMode()
/* 42:   */   {
/* 43:70 */     return this.replicationMode;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setReplicationMode(ReplicationMode replicationMode)
/* 47:   */   {
/* 48:74 */     this.replicationMode = replicationMode;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getEntityName()
/* 52:   */   {
/* 53:78 */     return this.entityName;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setEntityName(String entityName)
/* 57:   */   {
/* 58:81 */     this.entityName = entityName;
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.ReplicateEvent
 * JD-Core Version:    0.7.0.1
 */