/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.helpers.OptionConverter;
/*   4:    */ import org.apache.log4j.helpers.PatternConverter;
/*   5:    */ import org.apache.log4j.helpers.PatternParser;
/*   6:    */ import org.apache.log4j.pattern.BridgePatternConverter;
/*   7:    */ import org.apache.log4j.pattern.BridgePatternParser;
/*   8:    */ import org.apache.log4j.spi.LoggingEvent;
/*   9:    */ 
/*  10:    */ public class EnhancedPatternLayout
/*  11:    */   extends Layout
/*  12:    */ {
/*  13:    */   public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
/*  14:    */   public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
/*  15:    */   /**
/*  16:    */    * @deprecated
/*  17:    */    */
/*  18:435 */   protected final int BUF_SIZE = 256;
/*  19:    */   /**
/*  20:    */    * @deprecated
/*  21:    */    */
/*  22:441 */   protected final int MAX_CAPACITY = 1024;
/*  23:    */   public static final String PATTERN_RULE_REGISTRY = "PATTERN_RULE_REGISTRY";
/*  24:    */   private PatternConverter head;
/*  25:    */   private String conversionPattern;
/*  26:    */   private boolean handlesExceptions;
/*  27:    */   
/*  28:    */   public EnhancedPatternLayout()
/*  29:    */   {
/*  30:471 */     this("%m%n");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public EnhancedPatternLayout(String pattern)
/*  34:    */   {
/*  35:479 */     this.conversionPattern = pattern;
/*  36:480 */     this.head = createPatternParser(pattern == null ? "%m%n" : pattern).parse();
/*  37:482 */     if ((this.head instanceof BridgePatternConverter)) {
/*  38:483 */       this.handlesExceptions = (!((BridgePatternConverter)this.head).ignoresThrowable());
/*  39:    */     } else {
/*  40:485 */       this.handlesExceptions = false;
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setConversionPattern(String conversionPattern)
/*  45:    */   {
/*  46:497 */     this.conversionPattern = OptionConverter.convertSpecialChars(conversionPattern);
/*  47:    */     
/*  48:499 */     this.head = createPatternParser(this.conversionPattern).parse();
/*  49:500 */     if ((this.head instanceof BridgePatternConverter)) {
/*  50:501 */       this.handlesExceptions = (!((BridgePatternConverter)this.head).ignoresThrowable());
/*  51:    */     } else {
/*  52:503 */       this.handlesExceptions = false;
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getConversionPattern()
/*  57:    */   {
/*  58:512 */     return this.conversionPattern;
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected PatternParser createPatternParser(String pattern)
/*  62:    */   {
/*  63:524 */     return new BridgePatternParser(pattern);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void activateOptions() {}
/*  67:    */   
/*  68:    */   public String format(LoggingEvent event)
/*  69:    */   {
/*  70:542 */     StringBuffer buf = new StringBuffer();
/*  71:543 */     for (PatternConverter c = this.head; c != null; c = c.next) {
/*  72:546 */       c.format(buf, event);
/*  73:    */     }
/*  74:548 */     return buf.toString();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean ignoresThrowable()
/*  78:    */   {
/*  79:557 */     return !this.handlesExceptions;
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.EnhancedPatternLayout
 * JD-Core Version:    0.7.0.1
 */