/*  1:   */ package org.apache.log4j.spi;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.Category;
/*  4:   */ import org.apache.log4j.Level;
/*  5:   */ import org.apache.log4j.Logger;
/*  6:   */ import org.apache.log4j.helpers.LogLog;
/*  7:   */ 
/*  8:   */ public final class RootLogger
/*  9:   */   extends Logger
/* 10:   */ {
/* 11:   */   public RootLogger(Level level)
/* 12:   */   {
/* 13:45 */     super("root");
/* 14:46 */     setLevel(level);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public final Level getChainedLevel()
/* 18:   */   {
/* 19:54 */     return this.level;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public final void setLevel(Level level)
/* 23:   */   {
/* 24:63 */     if (level == null) {
/* 25:64 */       LogLog.error("You have tried to set a null level to root.", new Throwable());
/* 26:   */     } else {
/* 27:67 */       this.level = level;
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.RootLogger
 * JD-Core Version:    0.7.0.1
 */