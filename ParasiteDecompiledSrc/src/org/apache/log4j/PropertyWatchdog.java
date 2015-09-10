/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.helpers.FileWatchdog;
/*   4:    */ 
/*   5:    */ class PropertyWatchdog
/*   6:    */   extends FileWatchdog
/*   7:    */ {
/*   8:    */   PropertyWatchdog(String filename)
/*   9:    */   {
/*  10:914 */     super(filename);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public void doOnChange()
/*  14:    */   {
/*  15:922 */     new PropertyConfigurator().doConfigure(this.filename, LogManager.getLoggerRepository());
/*  16:    */   }
/*  17:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.PropertyWatchdog
 * JD-Core Version:    0.7.0.1
 */