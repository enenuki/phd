/*   1:    */ package org.apache.commons.io;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.lang.ref.PhantomReference;
/*   5:    */ import java.lang.ref.ReferenceQueue;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.List;
/*  11:    */ 
/*  12:    */ public class FileCleaningTracker
/*  13:    */ {
/*  14:    */   ReferenceQueue<Object> q;
/*  15:    */   final Collection<Tracker> trackers;
/*  16:    */   final List<String> deleteFailures;
/*  17:    */   volatile boolean exitWhenFinished;
/*  18:    */   Thread reaper;
/*  19:    */   
/*  20:    */   public FileCleaningTracker()
/*  21:    */   {
/*  22: 50 */     this.q = new ReferenceQueue();
/*  23:    */     
/*  24:    */ 
/*  25:    */ 
/*  26: 54 */     this.trackers = Collections.synchronizedSet(new HashSet());
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30: 58 */     this.deleteFailures = Collections.synchronizedList(new ArrayList());
/*  31:    */     
/*  32:    */ 
/*  33:    */ 
/*  34: 62 */     this.exitWhenFinished = false;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void track(File file, Object marker)
/*  38:    */   {
/*  39: 79 */     track(file, marker, (FileDeleteStrategy)null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void track(File file, Object marker, FileDeleteStrategy deleteStrategy)
/*  43:    */   {
/*  44: 93 */     if (file == null) {
/*  45: 94 */       throw new NullPointerException("The file must not be null");
/*  46:    */     }
/*  47: 96 */     addTracker(file.getPath(), marker, deleteStrategy);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void track(String path, Object marker)
/*  51:    */   {
/*  52:109 */     track(path, marker, (FileDeleteStrategy)null);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void track(String path, Object marker, FileDeleteStrategy deleteStrategy)
/*  56:    */   {
/*  57:123 */     if (path == null) {
/*  58:124 */       throw new NullPointerException("The path must not be null");
/*  59:    */     }
/*  60:126 */     addTracker(path, marker, deleteStrategy);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private synchronized void addTracker(String path, Object marker, FileDeleteStrategy deleteStrategy)
/*  64:    */   {
/*  65:138 */     if (this.exitWhenFinished) {
/*  66:139 */       throw new IllegalStateException("No new trackers can be added once exitWhenFinished() is called");
/*  67:    */     }
/*  68:141 */     if (this.reaper == null)
/*  69:    */     {
/*  70:142 */       this.reaper = new Reaper();
/*  71:143 */       this.reaper.start();
/*  72:    */     }
/*  73:145 */     this.trackers.add(new Tracker(path, deleteStrategy, marker, this.q));
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getTrackCount()
/*  77:    */   {
/*  78:156 */     return this.trackers.size();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public List<String> getDeleteFailures()
/*  82:    */   {
/*  83:166 */     return this.deleteFailures;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public synchronized void exitWhenFinished()
/*  87:    */   {
/*  88:192 */     this.exitWhenFinished = true;
/*  89:193 */     if (this.reaper != null) {
/*  90:194 */       synchronized (this.reaper)
/*  91:    */       {
/*  92:195 */         this.reaper.interrupt();
/*  93:    */       }
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private final class Reaper
/*  98:    */     extends Thread
/*  99:    */   {
/* 100:    */     Reaper()
/* 101:    */     {
/* 102:207 */       super();
/* 103:208 */       setPriority(10);
/* 104:209 */       setDaemon(true);
/* 105:    */     }
/* 106:    */     
/* 107:    */     public void run()
/* 108:    */     {
/* 109:219 */       while ((!FileCleaningTracker.this.exitWhenFinished) || (FileCleaningTracker.this.trackers.size() > 0)) {
/* 110:    */         try
/* 111:    */         {
/* 112:222 */           FileCleaningTracker.Tracker tracker = (FileCleaningTracker.Tracker)FileCleaningTracker.this.q.remove();
/* 113:223 */           FileCleaningTracker.this.trackers.remove(tracker);
/* 114:224 */           if (!tracker.delete()) {
/* 115:225 */             FileCleaningTracker.this.deleteFailures.add(tracker.getPath());
/* 116:    */           }
/* 117:227 */           tracker.clear();
/* 118:    */         }
/* 119:    */         catch (InterruptedException e) {}
/* 120:    */       }
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private static final class Tracker
/* 125:    */     extends PhantomReference<Object>
/* 126:    */   {
/* 127:    */     private final String path;
/* 128:    */     private final FileDeleteStrategy deleteStrategy;
/* 129:    */     
/* 130:    */     Tracker(String path, FileDeleteStrategy deleteStrategy, Object marker, ReferenceQueue<? super Object> queue)
/* 131:    */     {
/* 132:259 */       super(queue);
/* 133:260 */       this.path = path;
/* 134:261 */       this.deleteStrategy = (deleteStrategy == null ? FileDeleteStrategy.NORMAL : deleteStrategy);
/* 135:    */     }
/* 136:    */     
/* 137:    */     public String getPath()
/* 138:    */     {
/* 139:270 */       return this.path;
/* 140:    */     }
/* 141:    */     
/* 142:    */     public boolean delete()
/* 143:    */     {
/* 144:280 */       return this.deleteStrategy.deleteQuietly(new File(this.path));
/* 145:    */     }
/* 146:    */   }
/* 147:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.FileCleaningTracker
 * JD-Core Version:    0.7.0.1
 */