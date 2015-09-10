/*  1:   */ package org.apache.james.mime4j.storage;
/*  2:   */ 
/*  3:   */ import org.apache.commons.logging.Log;
/*  4:   */ import org.apache.commons.logging.LogFactory;
/*  5:   */ 
/*  6:   */ public class DefaultStorageProvider
/*  7:   */ {
/*  8:   */   public static final String DEFAULT_STORAGE_PROVIDER_PROPERTY = "org.apache.james.mime4j.defaultStorageProvider";
/*  9:44 */   private static Log log = LogFactory.getLog(DefaultStorageProvider.class);
/* 10:46 */   private static volatile StorageProvider instance = null;
/* 11:   */   
/* 12:   */   static
/* 13:   */   {
/* 14:49 */     initialize();
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static StorageProvider getInstance()
/* 18:   */   {
/* 19:61 */     return instance;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static void setInstance(StorageProvider instance)
/* 23:   */   {
/* 24:71 */     if (instance == null) {
/* 25:72 */       throw new IllegalArgumentException();
/* 26:   */     }
/* 27:75 */     instance = instance;
/* 28:   */   }
/* 29:   */   
/* 30:   */   private static void initialize()
/* 31:   */   {
/* 32:79 */     String clazz = System.getProperty("org.apache.james.mime4j.defaultStorageProvider");
/* 33:   */     try
/* 34:   */     {
/* 35:81 */       if (clazz != null) {
/* 36:82 */         instance = (StorageProvider)Class.forName(clazz).newInstance();
/* 37:   */       }
/* 38:   */     }
/* 39:   */     catch (Exception e)
/* 40:   */     {
/* 41:85 */       log.warn("Unable to create or instantiate StorageProvider class '" + clazz + "'. Using default instead.", e);
/* 42:   */     }
/* 43:89 */     if (instance == null)
/* 44:   */     {
/* 45:90 */       StorageProvider backend = new TempFileStorageProvider();
/* 46:91 */       instance = new ThresholdStorageProvider(backend, 1024);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   static void reset()
/* 51:   */   {
/* 52:97 */     instance = null;
/* 53:98 */     initialize();
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.storage.DefaultStorageProvider
 * JD-Core Version:    0.7.0.1
 */