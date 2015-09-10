/*   1:    */ package org.apache.log4j.varia;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.Level;
/*   4:    */ import org.apache.log4j.Priority;
/*   5:    */ import org.apache.log4j.spi.Filter;
/*   6:    */ import org.apache.log4j.spi.LoggingEvent;
/*   7:    */ 
/*   8:    */ public class LevelRangeFilter
/*   9:    */   extends Filter
/*  10:    */ {
/*  11: 58 */   boolean acceptOnMatch = false;
/*  12:    */   Level levelMin;
/*  13:    */   Level levelMax;
/*  14:    */   
/*  15:    */   public int decide(LoggingEvent event)
/*  16:    */   {
/*  17: 69 */     if ((this.levelMin != null) && 
/*  18: 70 */       (!event.getLevel().isGreaterOrEqual(this.levelMin))) {
/*  19: 72 */       return -1;
/*  20:    */     }
/*  21: 76 */     if ((this.levelMax != null) && 
/*  22: 77 */       (event.getLevel().toInt() > this.levelMax.toInt())) {
/*  23: 82 */       return -1;
/*  24:    */     }
/*  25: 86 */     if (this.acceptOnMatch) {
/*  26: 89 */       return 1;
/*  27:    */     }
/*  28: 93 */     return 0;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Level getLevelMax()
/*  32:    */   {
/*  33:101 */     return this.levelMax;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Level getLevelMin()
/*  37:    */   {
/*  38:109 */     return this.levelMin;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean getAcceptOnMatch()
/*  42:    */   {
/*  43:117 */     return this.acceptOnMatch;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setLevelMax(Level levelMax)
/*  47:    */   {
/*  48:125 */     this.levelMax = levelMax;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setLevelMin(Level levelMin)
/*  52:    */   {
/*  53:133 */     this.levelMin = levelMin;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setAcceptOnMatch(boolean acceptOnMatch)
/*  57:    */   {
/*  58:141 */     this.acceptOnMatch = acceptOnMatch;
/*  59:    */   }
/*  60:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.LevelRangeFilter
 * JD-Core Version:    0.7.0.1
 */