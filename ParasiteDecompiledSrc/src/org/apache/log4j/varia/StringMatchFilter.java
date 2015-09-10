/*   1:    */ package org.apache.log4j.varia;
/*   2:    */ 
/*   3:    */ import org.apache.log4j.helpers.OptionConverter;
/*   4:    */ import org.apache.log4j.spi.Filter;
/*   5:    */ import org.apache.log4j.spi.LoggingEvent;
/*   6:    */ 
/*   7:    */ public class StringMatchFilter
/*   8:    */   extends Filter
/*   9:    */ {
/*  10:    */   /**
/*  11:    */    * @deprecated
/*  12:    */    */
/*  13:    */   public static final String STRING_TO_MATCH_OPTION = "StringToMatch";
/*  14:    */   /**
/*  15:    */    * @deprecated
/*  16:    */    */
/*  17:    */   public static final String ACCEPT_ON_MATCH_OPTION = "AcceptOnMatch";
/*  18: 54 */   boolean acceptOnMatch = true;
/*  19:    */   String stringToMatch;
/*  20:    */   
/*  21:    */   /**
/*  22:    */    * @deprecated
/*  23:    */    */
/*  24:    */   public String[] getOptionStrings()
/*  25:    */   {
/*  26: 63 */     return new String[] { "StringToMatch", "AcceptOnMatch" };
/*  27:    */   }
/*  28:    */   
/*  29:    */   /**
/*  30:    */    * @deprecated
/*  31:    */    */
/*  32:    */   public void setOption(String key, String value)
/*  33:    */   {
/*  34: 73 */     if (key.equalsIgnoreCase("StringToMatch")) {
/*  35: 74 */       this.stringToMatch = value;
/*  36: 75 */     } else if (key.equalsIgnoreCase("AcceptOnMatch")) {
/*  37: 76 */       this.acceptOnMatch = OptionConverter.toBoolean(value, this.acceptOnMatch);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setStringToMatch(String s)
/*  42:    */   {
/*  43: 82 */     this.stringToMatch = s;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getStringToMatch()
/*  47:    */   {
/*  48: 87 */     return this.stringToMatch;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setAcceptOnMatch(boolean acceptOnMatch)
/*  52:    */   {
/*  53: 92 */     this.acceptOnMatch = acceptOnMatch;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean getAcceptOnMatch()
/*  57:    */   {
/*  58: 97 */     return this.acceptOnMatch;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int decide(LoggingEvent event)
/*  62:    */   {
/*  63:105 */     String msg = event.getRenderedMessage();
/*  64:107 */     if ((msg == null) || (this.stringToMatch == null)) {
/*  65:108 */       return 0;
/*  66:    */     }
/*  67:111 */     if (msg.indexOf(this.stringToMatch) == -1) {
/*  68:112 */       return 0;
/*  69:    */     }
/*  70:114 */     if (this.acceptOnMatch) {
/*  71:115 */       return 1;
/*  72:    */     }
/*  73:117 */     return -1;
/*  74:    */   }
/*  75:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.varia.StringMatchFilter
 * JD-Core Version:    0.7.0.1
 */