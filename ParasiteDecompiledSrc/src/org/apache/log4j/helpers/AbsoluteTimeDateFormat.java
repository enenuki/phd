/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.FieldPosition;
/*   5:    */ import java.text.ParsePosition;
/*   6:    */ import java.util.Calendar;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.TimeZone;
/*   9:    */ 
/*  10:    */ public class AbsoluteTimeDateFormat
/*  11:    */   extends DateFormat
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -388856345976723342L;
/*  14:    */   public static final String ABS_TIME_DATE_FORMAT = "ABSOLUTE";
/*  15:    */   public static final String DATE_AND_TIME_DATE_FORMAT = "DATE";
/*  16:    */   public static final String ISO8601_DATE_FORMAT = "ISO8601";
/*  17:    */   private static long previousTime;
/*  18:    */   
/*  19:    */   public AbsoluteTimeDateFormat()
/*  20:    */   {
/*  21: 62 */     setCalendar(Calendar.getInstance());
/*  22:    */   }
/*  23:    */   
/*  24:    */   public AbsoluteTimeDateFormat(TimeZone timeZone)
/*  25:    */   {
/*  26: 67 */     setCalendar(Calendar.getInstance(timeZone));
/*  27:    */   }
/*  28:    */   
/*  29: 71 */   private static char[] previousTimeWithoutMillis = new char[9];
/*  30:    */   
/*  31:    */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/*  32:    */   {
/*  33: 85 */     long now = date.getTime();
/*  34: 86 */     int millis = (int)(now % 1000L);
/*  35: 88 */     if ((now - millis != previousTime) || (previousTimeWithoutMillis[0] == 0))
/*  36:    */     {
/*  37: 93 */       this.calendar.setTime(date);
/*  38:    */       
/*  39: 95 */       int start = sbuf.length();
/*  40:    */       
/*  41: 97 */       int hour = this.calendar.get(11);
/*  42: 98 */       if (hour < 10) {
/*  43: 99 */         sbuf.append('0');
/*  44:    */       }
/*  45:101 */       sbuf.append(hour);
/*  46:102 */       sbuf.append(':');
/*  47:    */       
/*  48:104 */       int mins = this.calendar.get(12);
/*  49:105 */       if (mins < 10) {
/*  50:106 */         sbuf.append('0');
/*  51:    */       }
/*  52:108 */       sbuf.append(mins);
/*  53:109 */       sbuf.append(':');
/*  54:    */       
/*  55:111 */       int secs = this.calendar.get(13);
/*  56:112 */       if (secs < 10) {
/*  57:113 */         sbuf.append('0');
/*  58:    */       }
/*  59:115 */       sbuf.append(secs);
/*  60:116 */       sbuf.append(',');
/*  61:    */       
/*  62:    */ 
/*  63:119 */       sbuf.getChars(start, sbuf.length(), previousTimeWithoutMillis, 0);
/*  64:    */       
/*  65:121 */       previousTime = now - millis;
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69:124 */       sbuf.append(previousTimeWithoutMillis);
/*  70:    */     }
/*  71:129 */     if (millis < 100) {
/*  72:130 */       sbuf.append('0');
/*  73:    */     }
/*  74:131 */     if (millis < 10) {
/*  75:132 */       sbuf.append('0');
/*  76:    */     }
/*  77:134 */     sbuf.append(millis);
/*  78:135 */     return sbuf;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Date parse(String s, ParsePosition pos)
/*  82:    */   {
/*  83:143 */     return null;
/*  84:    */   }
/*  85:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.AbsoluteTimeDateFormat
 * JD-Core Version:    0.7.0.1
 */