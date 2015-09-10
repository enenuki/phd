/*  1:   */ package org.apache.log4j.lf5;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*  5:   */ 
/*  6:   */ public class AppenderFinalizer
/*  7:   */ {
/*  8:41 */   protected LogBrokerMonitor _defaultMonitor = null;
/*  9:   */   
/* 10:   */   public AppenderFinalizer(LogBrokerMonitor defaultMonitor)
/* 11:   */   {
/* 12:52 */     this._defaultMonitor = defaultMonitor;
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected void finalize()
/* 16:   */     throws Throwable
/* 17:   */   {
/* 18:66 */     System.out.println("Disposing of the default LogBrokerMonitor instance");
/* 19:67 */     this._defaultMonitor.dispose();
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.AppenderFinalizer
 * JD-Core Version:    0.7.0.1
 */