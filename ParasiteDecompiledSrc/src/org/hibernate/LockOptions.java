/*   1:    */ package org.hibernate;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ 
/*  11:    */ public class LockOptions
/*  12:    */   implements Serializable
/*  13:    */ {
/*  14: 42 */   public static final LockOptions NONE = new LockOptions(LockMode.NONE);
/*  15: 46 */   public static final LockOptions READ = new LockOptions(LockMode.READ);
/*  16: 51 */   public static final LockOptions UPGRADE = new LockOptions(LockMode.UPGRADE);
/*  17:    */   
/*  18:    */   public LockOptions() {}
/*  19:    */   
/*  20:    */   public LockOptions(LockMode lockMode)
/*  21:    */   {
/*  22: 57 */     this.lockMode = lockMode;
/*  23:    */   }
/*  24:    */   
/*  25: 60 */   private LockMode lockMode = LockMode.NONE;
/*  26:    */   
/*  27:    */   public LockMode getLockMode()
/*  28:    */   {
/*  29: 71 */     return this.lockMode;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public LockOptions setLockMode(LockMode lockMode)
/*  33:    */   {
/*  34: 83 */     this.lockMode = lockMode;
/*  35: 84 */     return this;
/*  36:    */   }
/*  37:    */   
/*  38: 87 */   private Map aliasSpecificLockModes = null;
/*  39:    */   public static final int NO_WAIT = 0;
/*  40:    */   public static final int WAIT_FOREVER = -1;
/*  41:    */   
/*  42:    */   public LockOptions setAliasSpecificLockMode(String alias, LockMode lockMode)
/*  43:    */   {
/*  44:101 */     if (this.aliasSpecificLockModes == null) {
/*  45:102 */       this.aliasSpecificLockModes = new HashMap();
/*  46:    */     }
/*  47:104 */     this.aliasSpecificLockModes.put(alias, lockMode);
/*  48:105 */     return this;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public LockMode getAliasSpecificLockMode(String alias)
/*  52:    */   {
/*  53:120 */     if (this.aliasSpecificLockModes == null) {
/*  54:121 */       return null;
/*  55:    */     }
/*  56:123 */     return (LockMode)this.aliasSpecificLockModes.get(alias);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public LockMode getEffectiveLockMode(String alias)
/*  60:    */   {
/*  61:140 */     LockMode lockMode = getAliasSpecificLockMode(alias);
/*  62:141 */     if (lockMode == null) {
/*  63:142 */       lockMode = this.lockMode;
/*  64:    */     }
/*  65:144 */     return lockMode == null ? LockMode.NONE : lockMode;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getAliasLockCount()
/*  69:    */   {
/*  70:153 */     if (this.aliasSpecificLockModes == null) {
/*  71:154 */       return 0;
/*  72:    */     }
/*  73:156 */     return this.aliasSpecificLockModes.size();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Iterator getAliasLockIterator()
/*  77:    */   {
/*  78:165 */     if (this.aliasSpecificLockModes == null) {
/*  79:166 */       return Collections.emptyList().iterator();
/*  80:    */     }
/*  81:168 */     return this.aliasSpecificLockModes.entrySet().iterator();
/*  82:    */   }
/*  83:    */   
/*  84:182 */   private int timeout = -1;
/*  85:    */   
/*  86:    */   public int getTimeOut()
/*  87:    */   {
/*  88:195 */     return this.timeout;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public LockOptions setTimeOut(int timeout)
/*  92:    */   {
/*  93:210 */     this.timeout = timeout;
/*  94:211 */     return this;
/*  95:    */   }
/*  96:    */   
/*  97:214 */   private boolean scope = false;
/*  98:    */   
/*  99:    */   public boolean getScope()
/* 100:    */   {
/* 101:224 */     return this.scope;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public LockOptions setScope(boolean scope)
/* 105:    */   {
/* 106:235 */     this.scope = scope;
/* 107:236 */     return this;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static LockOptions copy(LockOptions from, LockOptions dest)
/* 111:    */   {
/* 112:247 */     dest.setLockMode(from.getLockMode());
/* 113:248 */     dest.setScope(from.getScope());
/* 114:249 */     dest.setTimeOut(from.getTimeOut());
/* 115:250 */     if (from.aliasSpecificLockModes != null) {
/* 116:251 */       dest.aliasSpecificLockModes = new HashMap(from.aliasSpecificLockModes);
/* 117:    */     }
/* 118:253 */     return dest;
/* 119:    */   }
/* 120:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.LockOptions
 * JD-Core Version:    0.7.0.1
 */