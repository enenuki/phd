/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.text.DateFormat;
/*  4:   */ import java.text.DateFormatSymbols;
/*  5:   */ import java.text.FieldPosition;
/*  6:   */ import java.text.ParsePosition;
/*  7:   */ import java.util.Calendar;
/*  8:   */ import java.util.Date;
/*  9:   */ import java.util.TimeZone;
/* 10:   */ 
/* 11:   */ public class DateTimeDateFormat
/* 12:   */   extends AbsoluteTimeDateFormat
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = 5547637772208514971L;
/* 15:   */   String[] shortMonths;
/* 16:   */   
/* 17:   */   public DateTimeDateFormat()
/* 18:   */   {
/* 19:42 */     this.shortMonths = new DateFormatSymbols().getShortMonths();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public DateTimeDateFormat(TimeZone timeZone)
/* 23:   */   {
/* 24:47 */     this();
/* 25:48 */     setCalendar(Calendar.getInstance(timeZone));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/* 29:   */   {
/* 30:61 */     this.calendar.setTime(date);
/* 31:   */     
/* 32:63 */     int day = this.calendar.get(5);
/* 33:64 */     if (day < 10) {
/* 34:65 */       sbuf.append('0');
/* 35:   */     }
/* 36:66 */     sbuf.append(day);
/* 37:67 */     sbuf.append(' ');
/* 38:68 */     sbuf.append(this.shortMonths[this.calendar.get(2)]);
/* 39:69 */     sbuf.append(' ');
/* 40:   */     
/* 41:71 */     int year = this.calendar.get(1);
/* 42:72 */     sbuf.append(year);
/* 43:73 */     sbuf.append(' ');
/* 44:   */     
/* 45:75 */     return super.format(date, sbuf, fieldPosition);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Date parse(String s, ParsePosition pos)
/* 49:   */   {
/* 50:83 */     return null;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.DateTimeDateFormat
 * JD-Core Version:    0.7.0.1
 */