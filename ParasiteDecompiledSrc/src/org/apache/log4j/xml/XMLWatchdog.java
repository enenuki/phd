/*    1:     */ package org.apache.log4j.xml;
/*    2:     */ 
/*    3:     */ import org.apache.log4j.LogManager;
/*    4:     */ import org.apache.log4j.helpers.FileWatchdog;
/*    5:     */ 
/*    6:     */ class XMLWatchdog
/*    7:     */   extends FileWatchdog
/*    8:     */ {
/*    9:     */   XMLWatchdog(String filename)
/*   10:     */   {
/*   11:1112 */     super(filename);
/*   12:     */   }
/*   13:     */   
/*   14:     */   public void doOnChange()
/*   15:     */   {
/*   16:1120 */     new DOMConfigurator().doConfigure(this.filename, LogManager.getLoggerRepository());
/*   17:     */   }
/*   18:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.xml.XMLWatchdog
 * JD-Core Version:    0.7.0.1
 */