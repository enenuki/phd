/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import org.apache.log4j.helpers.PatternConverter;
/*   8:    */ import org.apache.log4j.spi.LoggingEvent;
/*   9:    */ 
/*  10:    */ public final class BridgePatternConverter
/*  11:    */   extends PatternConverter
/*  12:    */ {
/*  13:    */   private LoggingEventPatternConverter[] patternConverters;
/*  14:    */   private FormattingInfo[] patternFields;
/*  15:    */   private boolean handlesExceptions;
/*  16:    */   
/*  17:    */   public BridgePatternConverter(String pattern)
/*  18:    */   {
/*  19: 59 */     this.next = null;
/*  20: 60 */     this.handlesExceptions = false;
/*  21:    */     
/*  22: 62 */     List converters = new ArrayList();
/*  23: 63 */     List fields = new ArrayList();
/*  24: 64 */     Map converterRegistry = null;
/*  25:    */     
/*  26: 66 */     PatternParser.parse(pattern, converters, fields, converterRegistry, PatternParser.getPatternLayoutRules());
/*  27:    */     
/*  28:    */ 
/*  29:    */ 
/*  30: 70 */     this.patternConverters = new LoggingEventPatternConverter[converters.size()];
/*  31: 71 */     this.patternFields = new FormattingInfo[converters.size()];
/*  32:    */     
/*  33: 73 */     int i = 0;
/*  34: 74 */     Iterator converterIter = converters.iterator();
/*  35: 75 */     Iterator fieldIter = fields.iterator();
/*  36: 77 */     while (converterIter.hasNext())
/*  37:    */     {
/*  38: 78 */       Object converter = converterIter.next();
/*  39: 80 */       if ((converter instanceof LoggingEventPatternConverter))
/*  40:    */       {
/*  41: 81 */         this.patternConverters[i] = ((LoggingEventPatternConverter)converter);
/*  42: 82 */         this.handlesExceptions |= this.patternConverters[i].handlesThrowable();
/*  43:    */       }
/*  44:    */       else
/*  45:    */       {
/*  46: 84 */         this.patternConverters[i] = new LiteralPatternConverter("");
/*  47:    */       }
/*  48: 88 */       if (fieldIter.hasNext()) {
/*  49: 89 */         this.patternFields[i] = ((FormattingInfo)fieldIter.next());
/*  50:    */       } else {
/*  51: 91 */         this.patternFields[i] = FormattingInfo.getDefault();
/*  52:    */       }
/*  53: 94 */       i++;
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected String convert(LoggingEvent event)
/*  58:    */   {
/*  59:105 */     StringBuffer sbuf = new StringBuffer();
/*  60:106 */     format(sbuf, event);
/*  61:    */     
/*  62:108 */     return sbuf.toString();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void format(StringBuffer sbuf, LoggingEvent e)
/*  66:    */   {
/*  67:117 */     for (int i = 0; i < this.patternConverters.length; i++)
/*  68:    */     {
/*  69:118 */       int startField = sbuf.length();
/*  70:119 */       this.patternConverters[i].format(e, sbuf);
/*  71:120 */       this.patternFields[i].format(startField, sbuf);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public boolean ignoresThrowable()
/*  76:    */   {
/*  77:130 */     return !this.handlesExceptions;
/*  78:    */   }
/*  79:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.BridgePatternConverter
 * JD-Core Version:    0.7.0.1
 */