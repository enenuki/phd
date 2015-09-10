/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.FieldPosition;
/*   5:    */ import java.text.ParsePosition;
/*   6:    */ import java.util.Calendar;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.TimeZone;
/*   9:    */ 
/*  10:    */ public class ISO8601DateFormat
/*  11:    */   extends AbsoluteTimeDateFormat
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = -759840745298755296L;
/*  14:    */   private static long lastTime;
/*  15:    */   
/*  16:    */   public ISO8601DateFormat() {}
/*  17:    */   
/*  18:    */   public ISO8601DateFormat(TimeZone timeZone)
/*  19:    */   {
/*  20: 51 */     super(timeZone);
/*  21:    */   }
/*  22:    */   
/*  23: 55 */   private static char[] lastTimeString = new char[20];
/*  24:    */   
/*  25:    */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/*  26:    */   {
/*  27: 67 */     long now = date.getTime();
/*  28: 68 */     int millis = (int)(now % 1000L);
/*  29: 70 */     if ((now - millis != lastTime) || (lastTimeString[0] == 0))
/*  30:    */     {
/*  31: 75 */       this.calendar.setTime(date);
/*  32:    */       
/*  33: 77 */       int start = sbuf.length();
/*  34:    */       
/*  35: 79 */       int year = this.calendar.get(1);
/*  36: 80 */       sbuf.append(year);
/*  37:    */       String month;
/*  38: 83 */       switch (this.calendar.get(2))
/*  39:    */       {
/*  40:    */       case 0: 
/*  41: 84 */         month = "-01-"; break;
/*  42:    */       case 1: 
/*  43: 85 */         month = "-02-"; break;
/*  44:    */       case 2: 
/*  45: 86 */         month = "-03-"; break;
/*  46:    */       case 3: 
/*  47: 87 */         month = "-04-"; break;
/*  48:    */       case 4: 
/*  49: 88 */         month = "-05-"; break;
/*  50:    */       case 5: 
/*  51: 89 */         month = "-06-"; break;
/*  52:    */       case 6: 
/*  53: 90 */         month = "-07-"; break;
/*  54:    */       case 7: 
/*  55: 91 */         month = "-08-"; break;
/*  56:    */       case 8: 
/*  57: 92 */         month = "-09-"; break;
/*  58:    */       case 9: 
/*  59: 93 */         month = "-10-"; break;
/*  60:    */       case 10: 
/*  61: 94 */         month = "-11-"; break;
/*  62:    */       case 11: 
/*  63: 95 */         month = "-12-"; break;
/*  64:    */       default: 
/*  65: 96 */         month = "-NA-";
/*  66:    */       }
/*  67: 98 */       sbuf.append(month);
/*  68:    */       
/*  69:100 */       int day = this.calendar.get(5);
/*  70:101 */       if (day < 10) {
/*  71:102 */         sbuf.append('0');
/*  72:    */       }
/*  73:103 */       sbuf.append(day);
/*  74:    */       
/*  75:105 */       sbuf.append(' ');
/*  76:    */       
/*  77:107 */       int hour = this.calendar.get(11);
/*  78:108 */       if (hour < 10) {
/*  79:109 */         sbuf.append('0');
/*  80:    */       }
/*  81:111 */       sbuf.append(hour);
/*  82:112 */       sbuf.append(':');
/*  83:    */       
/*  84:114 */       int mins = this.calendar.get(12);
/*  85:115 */       if (mins < 10) {
/*  86:116 */         sbuf.append('0');
/*  87:    */       }
/*  88:118 */       sbuf.append(mins);
/*  89:119 */       sbuf.append(':');
/*  90:    */       
/*  91:121 */       int secs = this.calendar.get(13);
/*  92:122 */       if (secs < 10) {
/*  93:123 */         sbuf.append('0');
/*  94:    */       }
/*  95:125 */       sbuf.append(secs);
/*  96:    */       
/*  97:127 */       sbuf.append(',');
/*  98:    */       
/*  99:    */ 
/* 100:130 */       sbuf.getChars(start, sbuf.length(), lastTimeString, 0);
/* 101:131 */       lastTime = now - millis;
/* 102:    */     }
/* 103:    */     else
/* 104:    */     {
/* 105:134 */       sbuf.append(lastTimeString);
/* 106:    */     }
/* 107:138 */     if (millis < 100) {
/* 108:139 */       sbuf.append('0');
/* 109:    */     }
/* 110:140 */     if (millis < 10) {
/* 111:141 */       sbuf.append('0');
/* 112:    */     }
/* 113:143 */     sbuf.append(millis);
/* 114:144 */     return sbuf;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Date parse(String s, ParsePosition pos)
/* 118:    */   {
/* 119:152 */     return null;
/* 120:    */   }
/* 121:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.ISO8601DateFormat
 * JD-Core Version:    0.7.0.1
 */