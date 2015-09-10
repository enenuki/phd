/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ public class Priority
/*   4:    */ {
/*   5:    */   transient int level;
/*   6:    */   transient String levelStr;
/*   7:    */   transient int syslogEquivalent;
/*   8:    */   public static final int OFF_INT = 2147483647;
/*   9:    */   public static final int FATAL_INT = 50000;
/*  10:    */   public static final int ERROR_INT = 40000;
/*  11:    */   public static final int WARN_INT = 30000;
/*  12:    */   public static final int INFO_INT = 20000;
/*  13:    */   public static final int DEBUG_INT = 10000;
/*  14:    */   public static final int ALL_INT = -2147483648;
/*  15:    */   /**
/*  16:    */    * @deprecated
/*  17:    */    */
/*  18: 45 */   public static final Priority FATAL = new Level(50000, "FATAL", 0);
/*  19:    */   /**
/*  20:    */    * @deprecated
/*  21:    */    */
/*  22: 50 */   public static final Priority ERROR = new Level(40000, "ERROR", 3);
/*  23:    */   /**
/*  24:    */    * @deprecated
/*  25:    */    */
/*  26: 55 */   public static final Priority WARN = new Level(30000, "WARN", 4);
/*  27:    */   /**
/*  28:    */    * @deprecated
/*  29:    */    */
/*  30: 60 */   public static final Priority INFO = new Level(20000, "INFO", 6);
/*  31:    */   /**
/*  32:    */    * @deprecated
/*  33:    */    */
/*  34: 65 */   public static final Priority DEBUG = new Level(10000, "DEBUG", 7);
/*  35:    */   
/*  36:    */   protected Priority()
/*  37:    */   {
/*  38: 72 */     this.level = 10000;
/*  39: 73 */     this.levelStr = "DEBUG";
/*  40: 74 */     this.syslogEquivalent = 7;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected Priority(int level, String levelStr, int syslogEquivalent)
/*  44:    */   {
/*  45: 82 */     this.level = level;
/*  46: 83 */     this.levelStr = levelStr;
/*  47: 84 */     this.syslogEquivalent = syslogEquivalent;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean equals(Object o)
/*  51:    */   {
/*  52: 93 */     if ((o instanceof Priority))
/*  53:    */     {
/*  54: 94 */       Priority r = (Priority)o;
/*  55: 95 */       return this.level == r.level;
/*  56:    */     }
/*  57: 97 */     return false;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final int getSyslogEquivalent()
/*  61:    */   {
/*  62:107 */     return this.syslogEquivalent;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isGreaterOrEqual(Priority r)
/*  66:    */   {
/*  67:123 */     return this.level >= r.level;
/*  68:    */   }
/*  69:    */   
/*  70:    */   /**
/*  71:    */    * @deprecated
/*  72:    */    */
/*  73:    */   public static Priority[] getAllPossiblePriorities()
/*  74:    */   {
/*  75:135 */     return new Priority[] { FATAL, ERROR, Level.WARN, INFO, DEBUG };
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final String toString()
/*  79:    */   {
/*  80:146 */     return this.levelStr;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public final int toInt()
/*  84:    */   {
/*  85:155 */     return this.level;
/*  86:    */   }
/*  87:    */   
/*  88:    */   /**
/*  89:    */    * @deprecated
/*  90:    */    */
/*  91:    */   public static Priority toPriority(String sArg)
/*  92:    */   {
/*  93:164 */     return Level.toLevel(sArg);
/*  94:    */   }
/*  95:    */   
/*  96:    */   /**
/*  97:    */    * @deprecated
/*  98:    */    */
/*  99:    */   public static Priority toPriority(int val)
/* 100:    */   {
/* 101:173 */     return toPriority(val, DEBUG);
/* 102:    */   }
/* 103:    */   
/* 104:    */   /**
/* 105:    */    * @deprecated
/* 106:    */    */
/* 107:    */   public static Priority toPriority(int val, Priority defaultPriority)
/* 108:    */   {
/* 109:182 */     return Level.toLevel(val, (Level)defaultPriority);
/* 110:    */   }
/* 111:    */   
/* 112:    */   /**
/* 113:    */    * @deprecated
/* 114:    */    */
/* 115:    */   public static Priority toPriority(String sArg, Priority defaultPriority)
/* 116:    */   {
/* 117:191 */     return Level.toLevel(sArg, (Level)defaultPriority);
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.Priority
 * JD-Core Version:    0.7.0.1
 */