/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.spi.LoggingEvent;
/*   4:    */ 
/*   5:    */ public abstract class LogXF
/*   6:    */ {
/*   7: 33 */   protected static final Level TRACE = new Level(5000, "TRACE", 7);
/*   8: 37 */   private static final String FQCN = LogXF.class.getName();
/*   9:    */   
/*  10:    */   protected static Boolean valueOf(boolean b)
/*  11:    */   {
/*  12: 50 */     if (b) {
/*  13: 51 */       return Boolean.TRUE;
/*  14:    */     }
/*  15: 53 */     return Boolean.FALSE;
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected static Character valueOf(char c)
/*  19:    */   {
/*  20: 64 */     return new Character(c);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected static Byte valueOf(byte b)
/*  24:    */   {
/*  25: 75 */     return new Byte(b);
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected static Short valueOf(short b)
/*  29:    */   {
/*  30: 86 */     return new Short(b);
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected static Integer valueOf(int b)
/*  34:    */   {
/*  35: 97 */     return new Integer(b);
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected static Long valueOf(long b)
/*  39:    */   {
/*  40:108 */     return new Long(b);
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected static Float valueOf(float b)
/*  44:    */   {
/*  45:119 */     return new Float(b);
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected static Double valueOf(double b)
/*  49:    */   {
/*  50:130 */     return new Double(b);
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected static Object[] toArray(Object param1)
/*  54:    */   {
/*  55:140 */     return new Object[] { param1 };
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected static Object[] toArray(Object param1, Object param2)
/*  59:    */   {
/*  60:154 */     return new Object[] { param1, param2 };
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected static Object[] toArray(Object param1, Object param2, Object param3)
/*  64:    */   {
/*  65:170 */     return new Object[] { param1, param2, param3 };
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected static Object[] toArray(Object param1, Object param2, Object param3, Object param4)
/*  69:    */   {
/*  70:188 */     return new Object[] { param1, param2, param3, param4 };
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static void entering(Logger logger, String sourceClass, String sourceMethod)
/*  74:    */   {
/*  75:203 */     if (logger.isDebugEnabled()) {
/*  76:204 */       logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, sourceClass + "." + sourceMethod + " ENTRY", null));
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void entering(Logger logger, String sourceClass, String sourceMethod, String param)
/*  81:    */   {
/*  82:221 */     if (logger.isDebugEnabled())
/*  83:    */     {
/*  84:222 */       String msg = sourceClass + "." + sourceMethod + " ENTRY " + param;
/*  85:223 */       logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, msg, null));
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void entering(Logger logger, String sourceClass, String sourceMethod, Object param)
/*  90:    */   {
/*  91:240 */     if (logger.isDebugEnabled())
/*  92:    */     {
/*  93:241 */       String msg = sourceClass + "." + sourceMethod + " ENTRY ";
/*  94:242 */       if (param == null) {
/*  95:243 */         msg = msg + "null";
/*  96:    */       } else {
/*  97:    */         try
/*  98:    */         {
/*  99:246 */           msg = msg + param;
/* 100:    */         }
/* 101:    */         catch (Throwable ex)
/* 102:    */         {
/* 103:248 */           msg = msg + "?";
/* 104:    */         }
/* 105:    */       }
/* 106:251 */       logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, msg, null));
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static void entering(Logger logger, String sourceClass, String sourceMethod, Object[] params)
/* 111:    */   {
/* 112:268 */     if (logger.isDebugEnabled())
/* 113:    */     {
/* 114:269 */       String msg = sourceClass + "." + sourceMethod + " ENTRY ";
/* 115:270 */       if ((params != null) && (params.length > 0))
/* 116:    */       {
/* 117:271 */         String delim = "{";
/* 118:272 */         for (int i = 0; i < params.length; i++)
/* 119:    */         {
/* 120:    */           try
/* 121:    */           {
/* 122:274 */             msg = msg + delim + params[i];
/* 123:    */           }
/* 124:    */           catch (Throwable ex)
/* 125:    */           {
/* 126:276 */             msg = msg + delim + "?";
/* 127:    */           }
/* 128:278 */           delim = ",";
/* 129:    */         }
/* 130:280 */         msg = msg + "}";
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:282 */         msg = msg + "{}";
/* 135:    */       }
/* 136:284 */       logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, msg, null));
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static void exiting(Logger logger, String sourceClass, String sourceMethod)
/* 141:    */   {
/* 142:299 */     if (logger.isDebugEnabled()) {
/* 143:300 */       logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, sourceClass + "." + sourceMethod + " RETURN", null));
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static void exiting(Logger logger, String sourceClass, String sourceMethod, String result)
/* 148:    */   {
/* 149:318 */     if (logger.isDebugEnabled()) {
/* 150:319 */       logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, sourceClass + "." + sourceMethod + " RETURN " + result, null));
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static void exiting(Logger logger, String sourceClass, String sourceMethod, Object result)
/* 155:    */   {
/* 156:337 */     if (logger.isDebugEnabled())
/* 157:    */     {
/* 158:338 */       String msg = sourceClass + "." + sourceMethod + " RETURN ";
/* 159:339 */       if (result == null) {
/* 160:340 */         msg = msg + "null";
/* 161:    */       } else {
/* 162:    */         try
/* 163:    */         {
/* 164:343 */           msg = msg + result;
/* 165:    */         }
/* 166:    */         catch (Throwable ex)
/* 167:    */         {
/* 168:345 */           msg = msg + "?";
/* 169:    */         }
/* 170:    */       }
/* 171:348 */       logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, msg, null));
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static void throwing(Logger logger, String sourceClass, String sourceMethod, Throwable thrown)
/* 176:    */   {
/* 177:366 */     if (logger.isDebugEnabled()) {
/* 178:367 */       logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, sourceClass + "." + sourceMethod + " THROW", thrown));
/* 179:    */     }
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.LogXF
 * JD-Core Version:    0.7.0.1
 */