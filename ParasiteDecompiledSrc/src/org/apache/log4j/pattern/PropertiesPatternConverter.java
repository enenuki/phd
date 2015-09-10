/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.Set;
/*   5:    */ import org.apache.log4j.helpers.LogLog;
/*   6:    */ import org.apache.log4j.helpers.MDCKeySetExtractor;
/*   7:    */ import org.apache.log4j.spi.LoggingEvent;
/*   8:    */ 
/*   9:    */ public final class PropertiesPatternConverter
/*  10:    */   extends LoggingEventPatternConverter
/*  11:    */ {
/*  12:    */   private final String option;
/*  13:    */   
/*  14:    */   private PropertiesPatternConverter(String[] options)
/*  15:    */   {
/*  16: 50 */     super((options != null) && (options.length > 0) ? "Property{" + options[0] + "}" : "Properties", "property");
/*  17: 54 */     if ((options != null) && (options.length > 0)) {
/*  18: 55 */       this.option = options[0];
/*  19:    */     } else {
/*  20: 57 */       this.option = null;
/*  21:    */     }
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static PropertiesPatternConverter newInstance(String[] options)
/*  25:    */   {
/*  26: 68 */     return new PropertiesPatternConverter(options);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/*  30:    */   {
/*  31: 77 */     if (this.option == null)
/*  32:    */     {
/*  33: 78 */       toAppendTo.append("{");
/*  34:    */       try
/*  35:    */       {
/*  36: 81 */         Set keySet = MDCKeySetExtractor.INSTANCE.getPropertyKeySet(event);
/*  37: 82 */         if (keySet != null) {
/*  38: 83 */           for (i = keySet.iterator(); i.hasNext();)
/*  39:    */           {
/*  40: 84 */             Object item = i.next();
/*  41: 85 */             Object val = event.getMDC(item.toString());
/*  42: 86 */             toAppendTo.append("{").append(item).append(",").append(val).append("}");
/*  43:    */           }
/*  44:    */         }
/*  45:    */       }
/*  46:    */       catch (Exception ex)
/*  47:    */       {
/*  48:    */         Iterator i;
/*  49: 91 */         LogLog.error("Unexpected exception while extracting MDC keys", ex);
/*  50:    */       }
/*  51: 94 */       toAppendTo.append("}");
/*  52:    */     }
/*  53:    */     else
/*  54:    */     {
/*  55: 97 */       Object val = event.getMDC(this.option);
/*  56: 99 */       if (val != null) {
/*  57:100 */         toAppendTo.append(val);
/*  58:    */       }
/*  59:    */     }
/*  60:    */   }
/*  61:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.PropertiesPatternConverter
 * JD-Core Version:    0.7.0.1
 */