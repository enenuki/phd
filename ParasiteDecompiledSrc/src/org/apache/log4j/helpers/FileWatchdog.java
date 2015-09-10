/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ 
/*   5:    */ public abstract class FileWatchdog
/*   6:    */   extends Thread
/*   7:    */ {
/*   8:    */   public static final long DEFAULT_DELAY = 60000L;
/*   9:    */   protected String filename;
/*  10: 45 */   protected long delay = 60000L;
/*  11:    */   File file;
/*  12: 48 */   long lastModif = 0L;
/*  13: 49 */   boolean warnedAlready = false;
/*  14: 50 */   boolean interrupted = false;
/*  15:    */   
/*  16:    */   protected FileWatchdog(String filename)
/*  17:    */   {
/*  18: 54 */     super("FileWatchdog");
/*  19: 55 */     this.filename = filename;
/*  20: 56 */     this.file = new File(filename);
/*  21: 57 */     setDaemon(true);
/*  22: 58 */     checkAndConfigure();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setDelay(long delay)
/*  26:    */   {
/*  27: 66 */     this.delay = delay;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected abstract void doOnChange();
/*  31:    */   
/*  32:    */   protected void checkAndConfigure()
/*  33:    */   {
/*  34:    */     boolean fileExists;
/*  35:    */     try
/*  36:    */     {
/*  37: 77 */       fileExists = this.file.exists();
/*  38:    */     }
/*  39:    */     catch (SecurityException e)
/*  40:    */     {
/*  41: 79 */       LogLog.warn("Was not allowed to read check file existance, file:[" + this.filename + "].");
/*  42:    */       
/*  43: 81 */       this.interrupted = true;
/*  44: 82 */       return;
/*  45:    */     }
/*  46: 85 */     if (fileExists)
/*  47:    */     {
/*  48: 86 */       long l = this.file.lastModified();
/*  49: 87 */       if (l > this.lastModif)
/*  50:    */       {
/*  51: 88 */         this.lastModif = l;
/*  52: 89 */         doOnChange();
/*  53: 90 */         this.warnedAlready = false;
/*  54:    */       }
/*  55:    */     }
/*  56: 93 */     else if (!this.warnedAlready)
/*  57:    */     {
/*  58: 94 */       LogLog.debug("[" + this.filename + "] does not exist.");
/*  59: 95 */       this.warnedAlready = true;
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void run()
/*  64:    */   {
/*  65:102 */     while (!this.interrupted)
/*  66:    */     {
/*  67:    */       try
/*  68:    */       {
/*  69:104 */         Thread.sleep(this.delay);
/*  70:    */       }
/*  71:    */       catch (InterruptedException e) {}
/*  72:108 */       checkAndConfigure();
/*  73:    */     }
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.FileWatchdog
 * JD-Core Version:    0.7.0.1
 */