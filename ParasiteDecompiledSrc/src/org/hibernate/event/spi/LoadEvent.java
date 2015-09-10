/*   1:    */ package org.hibernate.event.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.LockMode;
/*   5:    */ import org.hibernate.LockOptions;
/*   6:    */ 
/*   7:    */ public class LoadEvent
/*   8:    */   extends AbstractEvent
/*   9:    */ {
/*  10: 37 */   public static final LockMode DEFAULT_LOCK_MODE = LockMode.NONE;
/*  11:    */   private Serializable entityId;
/*  12:    */   private String entityClassName;
/*  13:    */   private Object instanceToLoad;
/*  14:    */   private LockOptions lockOptions;
/*  15:    */   private boolean isAssociationFetch;
/*  16:    */   private Object result;
/*  17:    */   
/*  18:    */   public LoadEvent(Serializable entityId, Object instanceToLoad, EventSource source)
/*  19:    */   {
/*  20: 47 */     this(entityId, null, instanceToLoad, new LockOptions(), false, source);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public LoadEvent(Serializable entityId, String entityClassName, LockMode lockMode, EventSource source)
/*  24:    */   {
/*  25: 51 */     this(entityId, entityClassName, null, lockMode, false, source);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public LoadEvent(Serializable entityId, String entityClassName, LockOptions lockOptions, EventSource source)
/*  29:    */   {
/*  30: 55 */     this(entityId, entityClassName, null, lockOptions, false, source);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public LoadEvent(Serializable entityId, String entityClassName, boolean isAssociationFetch, EventSource source)
/*  34:    */   {
/*  35: 59 */     this(entityId, entityClassName, null, new LockOptions(), isAssociationFetch, source);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean isAssociationFetch()
/*  39:    */   {
/*  40: 63 */     return this.isAssociationFetch;
/*  41:    */   }
/*  42:    */   
/*  43:    */   private LoadEvent(Serializable entityId, String entityClassName, Object instanceToLoad, LockMode lockMode, boolean isAssociationFetch, EventSource source)
/*  44:    */   {
/*  45: 73 */     this(entityId, entityClassName, instanceToLoad, new LockOptions().setLockMode(lockMode), isAssociationFetch, source);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private LoadEvent(Serializable entityId, String entityClassName, Object instanceToLoad, LockOptions lockOptions, boolean isAssociationFetch, EventSource source)
/*  49:    */   {
/*  50: 84 */     super(source);
/*  51: 86 */     if (entityId == null) {
/*  52: 87 */       throw new IllegalArgumentException("id to load is required for loading");
/*  53:    */     }
/*  54: 90 */     if (lockOptions.getLockMode() == LockMode.WRITE) {
/*  55: 91 */       throw new IllegalArgumentException("Invalid lock mode for loading");
/*  56:    */     }
/*  57: 93 */     if (lockOptions.getLockMode() == null) {
/*  58: 94 */       lockOptions.setLockMode(DEFAULT_LOCK_MODE);
/*  59:    */     }
/*  60: 97 */     this.entityId = entityId;
/*  61: 98 */     this.entityClassName = entityClassName;
/*  62: 99 */     this.instanceToLoad = instanceToLoad;
/*  63:100 */     this.lockOptions = lockOptions;
/*  64:101 */     this.isAssociationFetch = isAssociationFetch;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Serializable getEntityId()
/*  68:    */   {
/*  69:105 */     return this.entityId;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setEntityId(Serializable entityId)
/*  73:    */   {
/*  74:109 */     this.entityId = entityId;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getEntityClassName()
/*  78:    */   {
/*  79:113 */     return this.entityClassName;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setEntityClassName(String entityClassName)
/*  83:    */   {
/*  84:117 */     this.entityClassName = entityClassName;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Object getInstanceToLoad()
/*  88:    */   {
/*  89:121 */     return this.instanceToLoad;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setInstanceToLoad(Object instanceToLoad)
/*  93:    */   {
/*  94:125 */     this.instanceToLoad = instanceToLoad;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public LockOptions getLockOptions()
/*  98:    */   {
/*  99:129 */     return this.lockOptions;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public LockMode getLockMode()
/* 103:    */   {
/* 104:133 */     return this.lockOptions.getLockMode();
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setLockMode(LockMode lockMode)
/* 108:    */   {
/* 109:137 */     this.lockOptions.setLockMode(lockMode);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setLockTimeout(int timeout)
/* 113:    */   {
/* 114:141 */     this.lockOptions.setTimeOut(timeout);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int getLockTimeout()
/* 118:    */   {
/* 119:145 */     return this.lockOptions.getTimeOut();
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setLockScope(boolean cascade)
/* 123:    */   {
/* 124:149 */     this.lockOptions.setScope(cascade);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean getLockScope()
/* 128:    */   {
/* 129:153 */     return this.lockOptions.getScope();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Object getResult()
/* 133:    */   {
/* 134:157 */     return this.result;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setResult(Object result)
/* 138:    */   {
/* 139:161 */     this.result = result;
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.LoadEvent
 * JD-Core Version:    0.7.0.1
 */