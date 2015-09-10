/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ 
/*   5:    */ @Deprecated
/*   6:    */ public class FileCleaner
/*   7:    */ {
/*   8: 45 */   static final FileCleaningTracker theInstance = new FileCleaningTracker();
/*   9:    */   
/*  10:    */   @Deprecated
/*  11:    */   public static void track(File file, Object marker)
/*  12:    */   {
/*  13: 60 */     theInstance.track(file, marker);
/*  14:    */   }
/*  15:    */   
/*  16:    */   @Deprecated
/*  17:    */   public static void track(File file, Object marker, FileDeleteStrategy deleteStrategy)
/*  18:    */   {
/*  19: 76 */     theInstance.track(file, marker, deleteStrategy);
/*  20:    */   }
/*  21:    */   
/*  22:    */   @Deprecated
/*  23:    */   public static void track(String path, Object marker)
/*  24:    */   {
/*  25: 91 */     theInstance.track(path, marker);
/*  26:    */   }
/*  27:    */   
/*  28:    */   @Deprecated
/*  29:    */   public static void track(String path, Object marker, FileDeleteStrategy deleteStrategy)
/*  30:    */   {
/*  31:107 */     theInstance.track(path, marker, deleteStrategy);
/*  32:    */   }
/*  33:    */   
/*  34:    */   @Deprecated
/*  35:    */   public static int getTrackCount()
/*  36:    */   {
/*  37:120 */     return theInstance.getTrackCount();
/*  38:    */   }
/*  39:    */   
/*  40:    */   @Deprecated
/*  41:    */   public static synchronized void exitWhenFinished()
/*  42:    */   {
/*  43:147 */     theInstance.exitWhenFinished();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static FileCleaningTracker getInstance()
/*  47:    */   {
/*  48:159 */     return theInstance;
/*  49:    */   }
/*  50:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.FileCleaner
 * JD-Core Version:    0.7.0.1
 */