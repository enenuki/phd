/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.spi.LoggingEvent;
/*   4:    */ 
/*   5:    */ public abstract class PatternConverter
/*   6:    */ {
/*   7:    */   public PatternConverter next;
/*   8: 38 */   int min = -1;
/*   9: 39 */   int max = 2147483647;
/*  10: 40 */   boolean leftAlign = false;
/*  11:    */   
/*  12:    */   protected PatternConverter() {}
/*  13:    */   
/*  14:    */   protected PatternConverter(FormattingInfo fi)
/*  15:    */   {
/*  16: 47 */     this.min = fi.min;
/*  17: 48 */     this.max = fi.max;
/*  18: 49 */     this.leftAlign = fi.leftAlign;
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected abstract String convert(LoggingEvent paramLoggingEvent);
/*  22:    */   
/*  23:    */   public void format(StringBuffer sbuf, LoggingEvent e)
/*  24:    */   {
/*  25: 65 */     String s = convert(e);
/*  26: 67 */     if (s == null)
/*  27:    */     {
/*  28: 68 */       if (0 < this.min) {
/*  29: 69 */         spacePad(sbuf, this.min);
/*  30:    */       }
/*  31: 70 */       return;
/*  32:    */     }
/*  33: 73 */     int len = s.length();
/*  34: 75 */     if (len > this.max) {
/*  35: 76 */       sbuf.append(s.substring(len - this.max));
/*  36: 77 */     } else if (len < this.min)
/*  37:    */     {
/*  38: 78 */       if (this.leftAlign)
/*  39:    */       {
/*  40: 79 */         sbuf.append(s);
/*  41: 80 */         spacePad(sbuf, this.min - len);
/*  42:    */       }
/*  43:    */       else
/*  44:    */       {
/*  45: 83 */         spacePad(sbuf, this.min - len);
/*  46: 84 */         sbuf.append(s);
/*  47:    */       }
/*  48:    */     }
/*  49:    */     else {
/*  50: 88 */       sbuf.append(s);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54: 91 */   static String[] SPACES = { " ", "  ", "    ", "        ", "                ", "                                " };
/*  55:    */   
/*  56:    */   public void spacePad(StringBuffer sbuf, int length)
/*  57:    */   {
/*  58:100 */     while (length >= 32)
/*  59:    */     {
/*  60:101 */       sbuf.append(SPACES[5]);
/*  61:102 */       length -= 32;
/*  62:    */     }
/*  63:105 */     for (int i = 4; i >= 0; i--) {
/*  64:106 */       if ((length & 1 << i) != 0) {
/*  65:107 */         sbuf.append(SPACES[i]);
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.PatternConverter
 * JD-Core Version:    0.7.0.1
 */