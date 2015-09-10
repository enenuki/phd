/*  1:   */ package antlr;
/*  2:   */ 
/*  3:   */ public class Utils
/*  4:   */ {
/*  5: 4 */   private static boolean useSystemExit = true;
/*  6: 5 */   private static boolean useDirectClassLoading = false;
/*  7:   */   
/*  8:   */   static
/*  9:   */   {
/* 10: 7 */     if ("true".equalsIgnoreCase(System.getProperty("ANTLR_DO_NOT_EXIT", "false"))) {
/* 11: 8 */       useSystemExit = false;
/* 12:   */     }
/* 13: 9 */     if ("true".equalsIgnoreCase(System.getProperty("ANTLR_USE_DIRECT_CLASS_LOADING", "false"))) {
/* 14:10 */       useDirectClassLoading = true;
/* 15:   */     }
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static Class loadClass(String paramString)
/* 19:   */     throws ClassNotFoundException
/* 20:   */   {
/* 21:   */     try
/* 22:   */     {
/* 23:16 */       ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
/* 24:17 */       if ((!useDirectClassLoading) && (localClassLoader != null)) {
/* 25:18 */         return localClassLoader.loadClass(paramString);
/* 26:   */       }
/* 27:20 */       return Class.forName(paramString);
/* 28:   */     }
/* 29:   */     catch (Exception localException) {}
/* 30:23 */     return Class.forName(paramString);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static Object createInstanceOf(String paramString)
/* 34:   */     throws ClassNotFoundException, InstantiationException, IllegalAccessException
/* 35:   */   {
/* 36:28 */     return loadClass(paramString).newInstance();
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static void error(String paramString)
/* 40:   */   {
/* 41:32 */     if (useSystemExit) {
/* 42:33 */       System.exit(1);
/* 43:   */     }
/* 44:34 */     throw new RuntimeException("ANTLR Panic: " + paramString);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static void error(String paramString, Throwable paramThrowable)
/* 48:   */   {
/* 49:38 */     if (useSystemExit) {
/* 50:39 */       System.exit(1);
/* 51:   */     }
/* 52:40 */     throw new RuntimeException("ANTLR Panic", paramThrowable);
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.Utils
 * JD-Core Version:    0.7.0.1
 */