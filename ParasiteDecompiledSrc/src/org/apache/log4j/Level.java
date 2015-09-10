/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.ObjectStreamException;
/*   7:    */ import java.io.Serializable;
/*   8:    */ 
/*   9:    */ public class Level
/*  10:    */   extends Priority
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13:    */   public static final int TRACE_INT = 5000;
/*  14: 51 */   public static final Level OFF = new Level(2147483647, "OFF", 0);
/*  15: 57 */   public static final Level FATAL = new Level(50000, "FATAL", 0);
/*  16: 62 */   public static final Level ERROR = new Level(40000, "ERROR", 3);
/*  17: 67 */   public static final Level WARN = new Level(30000, "WARN", 4);
/*  18: 73 */   public static final Level INFO = new Level(20000, "INFO", 6);
/*  19: 79 */   public static final Level DEBUG = new Level(10000, "DEBUG", 7);
/*  20: 86 */   public static final Level TRACE = new Level(5000, "TRACE", 7);
/*  21: 92 */   public static final Level ALL = new Level(-2147483648, "ALL", 7);
/*  22:    */   static final long serialVersionUID = 3491141966387921974L;
/*  23:    */   
/*  24:    */   protected Level(int level, String levelStr, int syslogEquivalent)
/*  25:    */   {
/*  26:104 */     super(level, levelStr, syslogEquivalent);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static Level toLevel(String sArg)
/*  30:    */   {
/*  31:115 */     return toLevel(sArg, DEBUG);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Level toLevel(int val)
/*  35:    */   {
/*  36:126 */     return toLevel(val, DEBUG);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Level toLevel(int val, Level defaultLevel)
/*  40:    */   {
/*  41:136 */     switch (val)
/*  42:    */     {
/*  43:    */     case -2147483648: 
/*  44:137 */       return ALL;
/*  45:    */     case 10000: 
/*  46:138 */       return DEBUG;
/*  47:    */     case 20000: 
/*  48:139 */       return INFO;
/*  49:    */     case 30000: 
/*  50:140 */       return WARN;
/*  51:    */     case 40000: 
/*  52:141 */       return ERROR;
/*  53:    */     case 50000: 
/*  54:142 */       return FATAL;
/*  55:    */     case 2147483647: 
/*  56:143 */       return OFF;
/*  57:    */     case 5000: 
/*  58:144 */       return TRACE;
/*  59:    */     }
/*  60:145 */     return defaultLevel;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static Level toLevel(String sArg, Level defaultLevel)
/*  64:    */   {
/*  65:157 */     if (sArg == null) {
/*  66:158 */       return defaultLevel;
/*  67:    */     }
/*  68:160 */     String s = sArg.toUpperCase();
/*  69:162 */     if (s.equals("ALL")) {
/*  70:162 */       return ALL;
/*  71:    */     }
/*  72:163 */     if (s.equals("DEBUG")) {
/*  73:163 */       return DEBUG;
/*  74:    */     }
/*  75:164 */     if (s.equals("INFO")) {
/*  76:164 */       return INFO;
/*  77:    */     }
/*  78:165 */     if (s.equals("WARN")) {
/*  79:165 */       return WARN;
/*  80:    */     }
/*  81:166 */     if (s.equals("ERROR")) {
/*  82:166 */       return ERROR;
/*  83:    */     }
/*  84:167 */     if (s.equals("FATAL")) {
/*  85:167 */       return FATAL;
/*  86:    */     }
/*  87:168 */     if (s.equals("OFF")) {
/*  88:168 */       return OFF;
/*  89:    */     }
/*  90:169 */     if (s.equals("TRACE")) {
/*  91:169 */       return TRACE;
/*  92:    */     }
/*  93:173 */     if (s.equals("İNFO")) {
/*  94:173 */       return INFO;
/*  95:    */     }
/*  96:174 */     return defaultLevel;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void readObject(ObjectInputStream s)
/* 100:    */     throws IOException, ClassNotFoundException
/* 101:    */   {
/* 102:184 */     s.defaultReadObject();
/* 103:185 */     this.level = s.readInt();
/* 104:186 */     this.syslogEquivalent = s.readInt();
/* 105:187 */     this.levelStr = s.readUTF();
/* 106:188 */     if (this.levelStr == null) {
/* 107:189 */       this.levelStr = "";
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void writeObject(ObjectOutputStream s)
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:199 */     s.defaultWriteObject();
/* 115:200 */     s.writeInt(this.level);
/* 116:201 */     s.writeInt(this.syslogEquivalent);
/* 117:202 */     s.writeUTF(this.levelStr);
/* 118:    */   }
/* 119:    */   
/* 120:    */   private Object readResolve()
/* 121:    */     throws ObjectStreamException
/* 122:    */   {
/* 123:215 */     if (getClass() == Level.class) {
/* 124:216 */       return toLevel(this.level);
/* 125:    */     }
/* 126:221 */     return this;
/* 127:    */   }
/* 128:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.Level
 * JD-Core Version:    0.7.0.1
 */