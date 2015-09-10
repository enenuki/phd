/*   1:    */ package org.hibernate.event.spi;
/*   2:    */ 
/*   3:    */ import org.hibernate.LockMode;
/*   4:    */ import org.hibernate.LockOptions;
/*   5:    */ 
/*   6:    */ public class LockEvent
/*   7:    */   extends AbstractEvent
/*   8:    */ {
/*   9:    */   private Object object;
/*  10:    */   private LockOptions lockOptions;
/*  11:    */   private String entityName;
/*  12:    */   
/*  13:    */   public LockEvent(String entityName, Object original, LockMode lockMode, EventSource source)
/*  14:    */   {
/*  15: 41 */     this(original, lockMode, source);
/*  16: 42 */     this.entityName = entityName;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public LockEvent(String entityName, Object original, LockOptions lockOptions, EventSource source)
/*  20:    */   {
/*  21: 46 */     this(original, lockOptions, source);
/*  22: 47 */     this.entityName = entityName;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public LockEvent(Object object, LockMode lockMode, EventSource source)
/*  26:    */   {
/*  27: 51 */     super(source);
/*  28: 52 */     this.object = object;
/*  29: 53 */     this.lockOptions = new LockOptions().setLockMode(lockMode);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public LockEvent(Object object, LockOptions lockOptions, EventSource source)
/*  33:    */   {
/*  34: 57 */     super(source);
/*  35: 58 */     this.object = object;
/*  36: 59 */     this.lockOptions = lockOptions;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public Object getObject()
/*  40:    */   {
/*  41: 63 */     return this.object;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setObject(Object object)
/*  45:    */   {
/*  46: 67 */     this.object = object;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public LockOptions getLockOptions()
/*  50:    */   {
/*  51: 71 */     return this.lockOptions;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public LockMode getLockMode()
/*  55:    */   {
/*  56: 75 */     return this.lockOptions.getLockMode();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setLockMode(LockMode lockMode)
/*  60:    */   {
/*  61: 79 */     this.lockOptions.setLockMode(lockMode);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setLockTimeout(int timeout)
/*  65:    */   {
/*  66: 83 */     this.lockOptions.setTimeOut(timeout);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getLockTimeout()
/*  70:    */   {
/*  71: 87 */     return this.lockOptions.getTimeOut();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setLockScope(boolean cascade)
/*  75:    */   {
/*  76: 91 */     this.lockOptions.setScope(cascade);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean getLockScope()
/*  80:    */   {
/*  81: 95 */     return this.lockOptions.getScope();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getEntityName()
/*  85:    */   {
/*  86: 99 */     return this.entityName;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setEntityName(String entityName)
/*  90:    */   {
/*  91:103 */     this.entityName = entityName;
/*  92:    */   }
/*  93:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.spi.LockEvent
 * JD-Core Version:    0.7.0.1
 */