/*  1:   */ package org.hibernate.event.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.LockMode;
/*  4:   */ import org.hibernate.LockOptions;
/*  5:   */ 
/*  6:   */ public class RefreshEvent
/*  7:   */   extends AbstractEvent
/*  8:   */ {
/*  9:   */   private Object object;
/* 10:   */   private String entityName;
/* 11:39 */   private LockOptions lockOptions = new LockOptions().setLockMode(LockMode.READ);
/* 12:   */   
/* 13:   */   public RefreshEvent(Object object, EventSource source)
/* 14:   */   {
/* 15:42 */     super(source);
/* 16:43 */     if (object == null) {
/* 17:44 */       throw new IllegalArgumentException("Attempt to generate refresh event with null object");
/* 18:   */     }
/* 19:46 */     this.object = object;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public RefreshEvent(String entityName, Object object, EventSource source)
/* 23:   */   {
/* 24:50 */     this(object, source);
/* 25:51 */     this.entityName = entityName;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public RefreshEvent(Object object, LockMode lockMode, EventSource source)
/* 29:   */   {
/* 30:55 */     this(object, source);
/* 31:56 */     if (lockMode == null) {
/* 32:57 */       throw new IllegalArgumentException("Attempt to generate refresh event with null lock mode");
/* 33:   */     }
/* 34:59 */     this.lockOptions.setLockMode(lockMode);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public RefreshEvent(Object object, LockOptions lockOptions, EventSource source)
/* 38:   */   {
/* 39:63 */     this(object, source);
/* 40:64 */     if (lockOptions == null) {
/* 41:65 */       throw new IllegalArgumentException("Attempt to generate refresh event with null lock request");
/* 42:   */     }
/* 43:67 */     this.lockOptions = lockOptions;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public RefreshEvent(String entityName, Object object, LockOptions lockOptions, EventSource source)
/* 47:   */   {
/* 48:70 */     this(object, lockOptions, source);
/* 49:71 */     this.entityName = entityName;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object getObject()
/* 53:   */   {
/* 54:75 */     return this.object;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public LockOptions getLockOptions()
/* 58:   */   {
/* 59:79 */     return this.lockOptions;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public LockMode getLockMode()
/* 63:   */   {
/* 64:83 */     return this.lockOptions.getLockMode();
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String getEntityName()
/* 68:   */   {
/* 69:87 */     return this.entityName;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public void setEntityName(String entityName)
/* 73:   */   {
/* 74:91 */     this.entityName = entityName;
/* 75:   */   }
/* 76:   */   
/* 77:   */   public int getLockTimeout()
/* 78:   */   {
/* 79:95 */     return this.lockOptions.getTimeOut();
/* 80:   */   }
/* 81:   */   
/* 82:   */   public boolean getLockScope()
/* 83:   */   {
/* 84:99 */     return this.lockOptions.getScope();
/* 85:   */   }
/* 86:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.RefreshEvent
 * JD-Core Version:    0.7.0.1
 */