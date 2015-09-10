/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import org.hibernate.LockMode;
/*   7:    */ import org.hibernate.LockOptions;
/*   8:    */ import org.hibernate.QueryException;
/*   9:    */ import org.hibernate.dialect.Dialect;
/*  10:    */ import org.hibernate.internal.util.StringHelper;
/*  11:    */ 
/*  12:    */ public class ForUpdateFragment
/*  13:    */ {
/*  14: 40 */   private final StringBuffer aliases = new StringBuffer();
/*  15:    */   private boolean isNowaitEnabled;
/*  16:    */   private final Dialect dialect;
/*  17:    */   private LockMode lockMode;
/*  18:    */   private LockOptions lockOptions;
/*  19:    */   
/*  20:    */   public ForUpdateFragment(Dialect dialect)
/*  21:    */   {
/*  22: 47 */     this.dialect = dialect;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public ForUpdateFragment(Dialect dialect, LockOptions lockOptions, Map keyColumnNames)
/*  26:    */     throws QueryException
/*  27:    */   {
/*  28: 51 */     this(dialect);
/*  29: 52 */     LockMode upgradeType = null;
/*  30: 53 */     Iterator iter = lockOptions.getAliasLockIterator();
/*  31: 54 */     this.lockOptions = lockOptions;
/*  32: 56 */     if (!iter.hasNext())
/*  33:    */     {
/*  34: 57 */       LockMode lockMode = lockOptions.getLockMode();
/*  35: 58 */       if (LockMode.READ.lessThan(lockMode))
/*  36:    */       {
/*  37: 59 */         upgradeType = lockMode;
/*  38: 60 */         this.lockMode = lockMode;
/*  39:    */       }
/*  40:    */     }
/*  41: 64 */     while (iter.hasNext())
/*  42:    */     {
/*  43: 65 */       Map.Entry me = (Map.Entry)iter.next();
/*  44: 66 */       LockMode lockMode = (LockMode)me.getValue();
/*  45: 67 */       if (LockMode.READ.lessThan(lockMode))
/*  46:    */       {
/*  47: 68 */         String tableAlias = (String)me.getKey();
/*  48: 69 */         if (dialect.forUpdateOfColumns())
/*  49:    */         {
/*  50: 70 */           String[] keyColumns = (String[])keyColumnNames.get(tableAlias);
/*  51: 71 */           if (keyColumns == null) {
/*  52: 72 */             throw new IllegalArgumentException("alias not found: " + tableAlias);
/*  53:    */           }
/*  54: 74 */           keyColumns = StringHelper.qualify(tableAlias, keyColumns);
/*  55: 75 */           for (int i = 0; i < keyColumns.length; i++) {
/*  56: 76 */             addTableAlias(keyColumns[i]);
/*  57:    */           }
/*  58:    */         }
/*  59:    */         else
/*  60:    */         {
/*  61: 80 */           addTableAlias(tableAlias);
/*  62:    */         }
/*  63: 82 */         if ((upgradeType != null) && (lockMode != upgradeType)) {
/*  64: 83 */           throw new QueryException("mixed LockModes");
/*  65:    */         }
/*  66: 85 */         upgradeType = lockMode;
/*  67:    */       }
/*  68:    */     }
/*  69: 89 */     if (upgradeType == LockMode.UPGRADE_NOWAIT) {
/*  70: 90 */       setNowaitEnabled(true);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ForUpdateFragment addTableAlias(String alias)
/*  75:    */   {
/*  76: 95 */     if (this.aliases.length() > 0) {
/*  77: 96 */       this.aliases.append(", ");
/*  78:    */     }
/*  79: 98 */     this.aliases.append(alias);
/*  80: 99 */     return this;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String toFragmentString()
/*  84:    */   {
/*  85:103 */     if (this.lockOptions != null) {
/*  86:104 */       return this.dialect.getForUpdateString(this.aliases.toString(), this.lockOptions);
/*  87:    */     }
/*  88:106 */     if (this.aliases.length() == 0)
/*  89:    */     {
/*  90:107 */       if (this.lockMode != null) {
/*  91:108 */         return this.dialect.getForUpdateString(this.lockMode);
/*  92:    */       }
/*  93:110 */       return "";
/*  94:    */     }
/*  95:113 */     return this.isNowaitEnabled ? this.dialect.getForUpdateNowaitString(this.aliases.toString()) : this.dialect.getForUpdateString(this.aliases.toString());
/*  96:    */   }
/*  97:    */   
/*  98:    */   public ForUpdateFragment setNowaitEnabled(boolean nowait)
/*  99:    */   {
/* 100:119 */     this.isNowaitEnabled = nowait;
/* 101:120 */     return this;
/* 102:    */   }
/* 103:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ForUpdateFragment
 * JD-Core Version:    0.7.0.1
 */