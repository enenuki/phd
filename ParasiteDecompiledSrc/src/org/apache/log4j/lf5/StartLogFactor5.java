/*  1:   */ package org.apache.log4j.lf5;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*  4:   */ 
/*  5:   */ public class StartLogFactor5
/*  6:   */ {
/*  7:   */   public static final void main(String[] args)
/*  8:   */   {
/*  9:57 */     LogBrokerMonitor monitor = new LogBrokerMonitor(LogLevel.getLog4JLevels());
/* 10:   */     
/* 11:   */ 
/* 12:60 */     monitor.setFrameSize(LF5Appender.getDefaultMonitorWidth(), LF5Appender.getDefaultMonitorHeight());
/* 13:   */     
/* 14:62 */     monitor.setFontSize(12);
/* 15:63 */     monitor.show();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.StartLogFactor5
 * JD-Core Version:    0.7.0.1
 */