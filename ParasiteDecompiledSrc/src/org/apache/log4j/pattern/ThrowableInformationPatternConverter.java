/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.spi.LoggingEvent;
/*   4:    */ import org.apache.log4j.spi.ThrowableInformation;
/*   5:    */ 
/*   6:    */ public class ThrowableInformationPatternConverter
/*   7:    */   extends LoggingEventPatternConverter
/*   8:    */ {
/*   9: 41 */   private int maxLines = 2147483647;
/*  10:    */   
/*  11:    */   private ThrowableInformationPatternConverter(String[] options)
/*  12:    */   {
/*  13: 49 */     super("Throwable", "throwable");
/*  14: 51 */     if ((options != null) && (options.length > 0)) {
/*  15: 52 */       if ("none".equals(options[0])) {
/*  16: 53 */         this.maxLines = 0;
/*  17: 54 */       } else if ("short".equals(options[0])) {
/*  18: 55 */         this.maxLines = 1;
/*  19:    */       } else {
/*  20:    */         try
/*  21:    */         {
/*  22: 58 */           this.maxLines = Integer.parseInt(options[0]);
/*  23:    */         }
/*  24:    */         catch (NumberFormatException ex) {}
/*  25:    */       }
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static ThrowableInformationPatternConverter newInstance(String[] options)
/*  30:    */   {
/*  31: 73 */     return new ThrowableInformationPatternConverter(options);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void format(LoggingEvent event, StringBuffer toAppendTo)
/*  35:    */   {
/*  36: 80 */     if (this.maxLines != 0)
/*  37:    */     {
/*  38: 81 */       ThrowableInformation information = event.getThrowableInformation();
/*  39: 83 */       if (information != null)
/*  40:    */       {
/*  41: 84 */         String[] stringRep = information.getThrowableStrRep();
/*  42:    */         
/*  43: 86 */         int length = stringRep.length;
/*  44: 87 */         if (this.maxLines < 0) {
/*  45: 88 */           length += this.maxLines;
/*  46: 89 */         } else if (length > this.maxLines) {
/*  47: 90 */           length = this.maxLines;
/*  48:    */         }
/*  49: 93 */         for (int i = 0; i < length; i++)
/*  50:    */         {
/*  51: 94 */           String string = stringRep[i];
/*  52: 95 */           toAppendTo.append(string).append("\n");
/*  53:    */         }
/*  54:    */       }
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean handlesThrowable()
/*  59:    */   {
/*  60:106 */     return true;
/*  61:    */   }
/*  62:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.ThrowableInformationPatternConverter
 * JD-Core Version:    0.7.0.1
 */