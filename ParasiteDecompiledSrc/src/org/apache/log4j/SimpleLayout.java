/*  1:   */ package org.apache.log4j;
/*  2:   */ 
/*  3:   */ import org.apache.log4j.spi.LoggingEvent;
/*  4:   */ 
/*  5:   */ public class SimpleLayout
/*  6:   */   extends Layout
/*  7:   */ {
/*  8:38 */   StringBuffer sbuf = new StringBuffer(128);
/*  9:   */   
/* 10:   */   public void activateOptions() {}
/* 11:   */   
/* 12:   */   public String format(LoggingEvent event)
/* 13:   */   {
/* 14:60 */     this.sbuf.setLength(0);
/* 15:61 */     this.sbuf.append(event.getLevel().toString());
/* 16:62 */     this.sbuf.append(" - ");
/* 17:63 */     this.sbuf.append(event.getRenderedMessage());
/* 18:64 */     this.sbuf.append(Layout.LINE_SEP);
/* 19:65 */     return this.sbuf.toString();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean ignoresThrowable()
/* 23:   */   {
/* 24:76 */     return true;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.SimpleLayout
 * JD-Core Version:    0.7.0.1
 */