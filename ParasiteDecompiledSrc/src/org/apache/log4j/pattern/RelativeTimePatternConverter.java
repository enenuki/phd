/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.spi.LoggingEvent;
/*   4:    */ 
/*   5:    */ public class RelativeTimePatternConverter
/*   6:    */   extends LoggingEventPatternConverter
/*   7:    */ {
/*   8: 33 */   private CachedTimestamp lastTimestamp = new CachedTimestamp(0L, "");
/*   9:    */   
/*  10:    */   public RelativeTimePatternConverter()
/*  11:    */   {
/*  12: 39 */     super("Time", "time");
/*  13:    */   }
/*  14:    */   
/*  15:    */   public static RelativeTimePatternConverter newInstance(String[] options)
/*  16:    */   {
/*  17: 49 */     return new RelativeTimePatternConverter();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/*  21:    */   {
/*  22: 56 */     long timestamp = event.timeStamp;
/*  23: 58 */     if (!this.lastTimestamp.format(timestamp, toAppendTo))
/*  24:    */     {
/*  25: 59 */       String formatted = Long.toString(timestamp - LoggingEvent.getStartTime());
/*  26:    */       
/*  27: 61 */       toAppendTo.append(formatted);
/*  28: 62 */       this.lastTimestamp = new CachedTimestamp(timestamp, formatted);
/*  29:    */     }
/*  30:    */   }
/*  31:    */   
/*  32:    */   private static final class CachedTimestamp
/*  33:    */   {
/*  34:    */     private final long timestamp;
/*  35:    */     private final String formatted;
/*  36:    */     
/*  37:    */     public CachedTimestamp(long timestamp, String formatted)
/*  38:    */     {
/*  39: 86 */       this.timestamp = timestamp;
/*  40: 87 */       this.formatted = formatted;
/*  41:    */     }
/*  42:    */     
/*  43:    */     public boolean format(long newTimestamp, StringBuffer toAppendTo)
/*  44:    */     {
/*  45: 97 */       if (newTimestamp == this.timestamp)
/*  46:    */       {
/*  47: 98 */         toAppendTo.append(this.formatted);
/*  48:    */         
/*  49:100 */         return true;
/*  50:    */       }
/*  51:103 */       return false;
/*  52:    */     }
/*  53:    */   }
/*  54:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.RelativeTimePatternConverter
 * JD-Core Version:    0.7.0.1
 */