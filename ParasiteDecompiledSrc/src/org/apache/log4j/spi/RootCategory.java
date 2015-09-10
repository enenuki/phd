/*  1:   */ package org.apache.log4j.spi;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.Category;
/*  4:   */ import org.apache.log4j.Level;
/*  5:   */ import org.apache.log4j.Logger;
/*  6:   */ import org.apache.log4j.helpers.LogLog;
/*  7:   */ 
/*  8:   */ /**
/*  9:   */  * @deprecated
/* 10:   */  */
/* 11:   */ public final class RootCategory
/* 12:   */   extends Logger
/* 13:   */ {
/* 14:   */   public RootCategory(Level level)
/* 15:   */   {
/* 16:37 */     super("root");
/* 17:38 */     setLevel(level);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public final Level getChainedLevel()
/* 21:   */   {
/* 22:49 */     return this.level;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public final void setLevel(Level level)
/* 26:   */   {
/* 27:60 */     if (level == null) {
/* 28:61 */       LogLog.error("You have tried to set a null level to root.", new Throwable());
/* 29:   */     } else {
/* 30:65 */       this.level = level;
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public final void setPriority(Level level)
/* 35:   */   {
/* 36:72 */     setLevel(level);
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.spi.RootCategory
 * JD-Core Version:    0.7.0.1
 */