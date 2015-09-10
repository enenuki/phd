/*   1:    */ package org.apache.log4j.varia;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.Level;
/*   4:    */ import org.apache.log4j.Priority;
/*   5:    */ import org.apache.log4j.helpers.OptionConverter;
/*   6:    */ import org.apache.log4j.spi.Filter;
/*   7:    */ import org.apache.log4j.spi.LoggingEvent;
/*   8:    */ 
/*   9:    */ public class LevelMatchFilter
/*  10:    */   extends Filter
/*  11:    */ {
/*  12: 45 */   boolean acceptOnMatch = true;
/*  13:    */   Level levelToMatch;
/*  14:    */   
/*  15:    */   public void setLevelToMatch(String level)
/*  16:    */   {
/*  17: 54 */     this.levelToMatch = OptionConverter.toLevel(level, null);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public String getLevelToMatch()
/*  21:    */   {
/*  22: 59 */     return this.levelToMatch == null ? null : this.levelToMatch.toString();
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setAcceptOnMatch(boolean acceptOnMatch)
/*  26:    */   {
/*  27: 64 */     this.acceptOnMatch = acceptOnMatch;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public boolean getAcceptOnMatch()
/*  31:    */   {
/*  32: 69 */     return this.acceptOnMatch;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public int decide(LoggingEvent event)
/*  36:    */   {
/*  37: 86 */     if (this.levelToMatch == null) {
/*  38: 87 */       return 0;
/*  39:    */     }
/*  40: 90 */     boolean matchOccured = false;
/*  41: 91 */     if (this.levelToMatch.equals(event.getLevel())) {
/*  42: 92 */       matchOccured = true;
/*  43:    */     }
/*  44: 95 */     if (matchOccured)
/*  45:    */     {
/*  46: 96 */       if (this.acceptOnMatch) {
/*  47: 97 */         return 1;
/*  48:    */       }
/*  49: 99 */       return -1;
/*  50:    */     }
/*  51:101 */     return 0;
/*  52:    */   }
/*  53:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.LevelMatchFilter
 * JD-Core Version:    0.7.0.1
 */