/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import org.hibernate.LockMode;
/*   4:    */ import org.hibernate.LockOptions;
/*   5:    */ import org.hibernate.dialect.Dialect;
/*   6:    */ import org.hibernate.internal.util.StringHelper;
/*   7:    */ 
/*   8:    */ public class Select
/*   9:    */ {
/*  10:    */   private String selectClause;
/*  11:    */   private String fromClause;
/*  12:    */   private String outerJoinsAfterFrom;
/*  13:    */   private String whereClause;
/*  14:    */   private String outerJoinsAfterWhere;
/*  15:    */   private String orderByClause;
/*  16:    */   private String groupByClause;
/*  17:    */   private String comment;
/*  18: 46 */   private LockOptions lockOptions = new LockOptions();
/*  19:    */   public final Dialect dialect;
/*  20: 49 */   private int guesstimatedBufferSize = 20;
/*  21:    */   
/*  22:    */   public Select(Dialect dialect)
/*  23:    */   {
/*  24: 52 */     this.dialect = dialect;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String toStatementString()
/*  28:    */   {
/*  29: 59 */     StringBuffer buf = new StringBuffer(this.guesstimatedBufferSize);
/*  30: 60 */     if (StringHelper.isNotEmpty(this.comment)) {
/*  31: 61 */       buf.append("/* ").append(this.comment).append(" */ ");
/*  32:    */     }
/*  33: 64 */     buf.append("select ").append(this.selectClause).append(" from ").append(this.fromClause);
/*  34: 67 */     if (StringHelper.isNotEmpty(this.outerJoinsAfterFrom)) {
/*  35: 68 */       buf.append(this.outerJoinsAfterFrom);
/*  36:    */     }
/*  37: 71 */     if ((StringHelper.isNotEmpty(this.whereClause)) || (StringHelper.isNotEmpty(this.outerJoinsAfterWhere)))
/*  38:    */     {
/*  39: 72 */       buf.append(" where ");
/*  40: 75 */       if (StringHelper.isNotEmpty(this.outerJoinsAfterWhere))
/*  41:    */       {
/*  42: 76 */         buf.append(this.outerJoinsAfterWhere);
/*  43: 77 */         if (StringHelper.isNotEmpty(this.whereClause)) {
/*  44: 78 */           buf.append(" and ");
/*  45:    */         }
/*  46:    */       }
/*  47: 81 */       if (StringHelper.isNotEmpty(this.whereClause)) {
/*  48: 82 */         buf.append(this.whereClause);
/*  49:    */       }
/*  50:    */     }
/*  51: 86 */     if (StringHelper.isNotEmpty(this.groupByClause)) {
/*  52: 87 */       buf.append(" group by ").append(this.groupByClause);
/*  53:    */     }
/*  54: 90 */     if (StringHelper.isNotEmpty(this.orderByClause)) {
/*  55: 91 */       buf.append(" order by ").append(this.orderByClause);
/*  56:    */     }
/*  57: 94 */     if (this.lockOptions.getLockMode() != LockMode.NONE) {
/*  58: 95 */       buf.append(this.dialect.getForUpdateString(this.lockOptions));
/*  59:    */     }
/*  60: 98 */     return this.dialect.transformSelectString(buf.toString());
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Select setFromClause(String fromClause)
/*  64:    */   {
/*  65:106 */     this.fromClause = fromClause;
/*  66:107 */     this.guesstimatedBufferSize += fromClause.length();
/*  67:108 */     return this;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Select setFromClause(String tableName, String alias)
/*  71:    */   {
/*  72:112 */     this.fromClause = (tableName + ' ' + alias);
/*  73:113 */     this.guesstimatedBufferSize += this.fromClause.length();
/*  74:114 */     return this;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Select setOrderByClause(String orderByClause)
/*  78:    */   {
/*  79:118 */     this.orderByClause = orderByClause;
/*  80:119 */     this.guesstimatedBufferSize += orderByClause.length();
/*  81:120 */     return this;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Select setGroupByClause(String groupByClause)
/*  85:    */   {
/*  86:124 */     this.groupByClause = groupByClause;
/*  87:125 */     this.guesstimatedBufferSize += groupByClause.length();
/*  88:126 */     return this;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Select setOuterJoins(String outerJoinsAfterFrom, String outerJoinsAfterWhere)
/*  92:    */   {
/*  93:130 */     this.outerJoinsAfterFrom = outerJoinsAfterFrom;
/*  94:    */     
/*  95:    */ 
/*  96:133 */     String tmpOuterJoinsAfterWhere = outerJoinsAfterWhere.trim();
/*  97:134 */     if (tmpOuterJoinsAfterWhere.startsWith("and")) {
/*  98:135 */       tmpOuterJoinsAfterWhere = tmpOuterJoinsAfterWhere.substring(4);
/*  99:    */     }
/* 100:137 */     this.outerJoinsAfterWhere = tmpOuterJoinsAfterWhere;
/* 101:    */     
/* 102:139 */     this.guesstimatedBufferSize += outerJoinsAfterFrom.length() + outerJoinsAfterWhere.length();
/* 103:140 */     return this;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Select setSelectClause(String selectClause)
/* 107:    */   {
/* 108:149 */     this.selectClause = selectClause;
/* 109:150 */     this.guesstimatedBufferSize += selectClause.length();
/* 110:151 */     return this;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Select setWhereClause(String whereClause)
/* 114:    */   {
/* 115:159 */     this.whereClause = whereClause;
/* 116:160 */     this.guesstimatedBufferSize += whereClause.length();
/* 117:161 */     return this;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Select setComment(String comment)
/* 121:    */   {
/* 122:165 */     this.comment = comment;
/* 123:166 */     this.guesstimatedBufferSize += comment.length();
/* 124:167 */     return this;
/* 125:    */   }
/* 126:    */   
/* 127:    */   /**
/* 128:    */    * @deprecated
/* 129:    */    */
/* 130:    */   public LockMode getLockMode()
/* 131:    */   {
/* 132:176 */     return this.lockOptions.getLockMode();
/* 133:    */   }
/* 134:    */   
/* 135:    */   /**
/* 136:    */    * @deprecated
/* 137:    */    */
/* 138:    */   public Select setLockMode(LockMode lockMode)
/* 139:    */   {
/* 140:186 */     this.lockOptions.setLockMode(lockMode);
/* 141:187 */     return this;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public LockOptions getLockOptions()
/* 145:    */   {
/* 146:195 */     return this.lockOptions;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Select setLockOptions(LockOptions lockOptions)
/* 150:    */   {
/* 151:204 */     LockOptions.copy(lockOptions, this.lockOptions);
/* 152:205 */     return this;
/* 153:    */   }
/* 154:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.Select
 * JD-Core Version:    0.7.0.1
 */