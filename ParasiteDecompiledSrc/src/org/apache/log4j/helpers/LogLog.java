/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ 
/*   5:    */ public class LogLog
/*   6:    */ {
/*   7:    */   public static final String DEBUG_KEY = "log4j.debug";
/*   8:    */   /**
/*   9:    */    * @deprecated
/*  10:    */    */
/*  11:    */   public static final String CONFIG_DEBUG_KEY = "log4j.configDebug";
/*  12: 60 */   protected static boolean debugEnabled = false;
/*  13: 65 */   private static boolean quietMode = false;
/*  14:    */   private static final String PREFIX = "log4j: ";
/*  15:    */   private static final String ERR_PREFIX = "log4j:ERROR ";
/*  16:    */   private static final String WARN_PREFIX = "log4j:WARN ";
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20: 72 */     String key = OptionConverter.getSystemProperty("log4j.debug", null);
/*  21: 74 */     if (key == null) {
/*  22: 75 */       key = OptionConverter.getSystemProperty("log4j.configDebug", null);
/*  23:    */     }
/*  24: 78 */     if (key != null) {
/*  25: 79 */       debugEnabled = OptionConverter.toBoolean(key, true);
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static void setInternalDebugging(boolean enabled)
/*  30:    */   {
/*  31: 89 */     debugEnabled = enabled;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static void debug(String msg)
/*  35:    */   {
/*  36: 99 */     if ((debugEnabled) && (!quietMode)) {
/*  37:100 */       System.out.println("log4j: " + msg);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static void debug(String msg, Throwable t)
/*  42:    */   {
/*  43:111 */     if ((debugEnabled) && (!quietMode))
/*  44:    */     {
/*  45:112 */       System.out.println("log4j: " + msg);
/*  46:113 */       if (t != null) {
/*  47:114 */         t.printStackTrace(System.out);
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static void error(String msg)
/*  53:    */   {
/*  54:127 */     if (quietMode) {
/*  55:128 */       return;
/*  56:    */     }
/*  57:129 */     System.err.println("log4j:ERROR " + msg);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static void error(String msg, Throwable t)
/*  61:    */   {
/*  62:140 */     if (quietMode) {
/*  63:141 */       return;
/*  64:    */     }
/*  65:143 */     System.err.println("log4j:ERROR " + msg);
/*  66:144 */     if (t != null) {
/*  67:145 */       t.printStackTrace();
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static void setQuietMode(boolean quietMode)
/*  72:    */   {
/*  73:158 */     quietMode = quietMode;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static void warn(String msg)
/*  77:    */   {
/*  78:168 */     if (quietMode) {
/*  79:169 */       return;
/*  80:    */     }
/*  81:171 */     System.err.println("log4j:WARN " + msg);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void warn(String msg, Throwable t)
/*  85:    */   {
/*  86:181 */     if (quietMode) {
/*  87:182 */       return;
/*  88:    */     }
/*  89:184 */     System.err.println("log4j:WARN " + msg);
/*  90:185 */     if (t != null) {
/*  91:186 */       t.printStackTrace();
/*  92:    */     }
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.LogLog
 * JD-Core Version:    0.7.0.1
 */