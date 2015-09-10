/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.helpers.PatternConverter;
/*   4:    */ import org.apache.log4j.helpers.PatternParser;
/*   5:    */ import org.apache.log4j.spi.LoggingEvent;
/*   6:    */ 
/*   7:    */ public class PatternLayout
/*   8:    */   extends Layout
/*   9:    */ {
/*  10:    */   public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
/*  11:    */   public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
/*  12:413 */   protected final int BUF_SIZE = 256;
/*  13:414 */   protected final int MAX_CAPACITY = 1024;
/*  14:418 */   private StringBuffer sbuf = new StringBuffer(256);
/*  15:    */   private String pattern;
/*  16:    */   private PatternConverter head;
/*  17:    */   
/*  18:    */   public PatternLayout()
/*  19:    */   {
/*  20:430 */     this("%m%n");
/*  21:    */   }
/*  22:    */   
/*  23:    */   public PatternLayout(String pattern)
/*  24:    */   {
/*  25:437 */     this.pattern = pattern;
/*  26:438 */     this.head = createPatternParser(pattern == null ? "%m%n" : pattern).parse();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setConversionPattern(String conversionPattern)
/*  30:    */   {
/*  31:449 */     this.pattern = conversionPattern;
/*  32:450 */     this.head = createPatternParser(conversionPattern).parse();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String getConversionPattern()
/*  36:    */   {
/*  37:458 */     return this.pattern;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void activateOptions() {}
/*  41:    */   
/*  42:    */   public boolean ignoresThrowable()
/*  43:    */   {
/*  44:477 */     return true;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected PatternParser createPatternParser(String pattern)
/*  48:    */   {
/*  49:488 */     return new PatternParser(pattern);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String format(LoggingEvent event)
/*  53:    */   {
/*  54:497 */     if (this.sbuf.capacity() > 1024) {
/*  55:498 */       this.sbuf = new StringBuffer(256);
/*  56:    */     } else {
/*  57:500 */       this.sbuf.setLength(0);
/*  58:    */     }
/*  59:503 */     PatternConverter c = this.head;
/*  60:505 */     while (c != null)
/*  61:    */     {
/*  62:506 */       c.format(this.sbuf, event);
/*  63:507 */       c = c.next;
/*  64:    */     }
/*  65:509 */     return this.sbuf.toString();
/*  66:    */   }
/*  67:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.PatternLayout
 * JD-Core Version:    0.7.0.1
 */