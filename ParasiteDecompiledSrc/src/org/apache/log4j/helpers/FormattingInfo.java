/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ public class FormattingInfo
/*  4:   */ {
/*  5:31 */   int min = -1;
/*  6:32 */   int max = 2147483647;
/*  7:33 */   boolean leftAlign = false;
/*  8:   */   
/*  9:   */   void reset()
/* 10:   */   {
/* 11:36 */     this.min = -1;
/* 12:37 */     this.max = 2147483647;
/* 13:38 */     this.leftAlign = false;
/* 14:   */   }
/* 15:   */   
/* 16:   */   void dump()
/* 17:   */   {
/* 18:42 */     LogLog.debug("min=" + this.min + ", max=" + this.max + ", leftAlign=" + this.leftAlign);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.FormattingInfo
 * JD-Core Version:    0.7.0.1
 */