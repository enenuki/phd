/*   1:    */ package org.apache.james.mime4j.field.datetime;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Calendar;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.GregorianCalendar;
/*   7:    */ import java.util.TimeZone;
/*   8:    */ 
/*   9:    */ public class DateTime
/*  10:    */ {
/*  11:    */   private final Date date;
/*  12:    */   private final int year;
/*  13:    */   private final int month;
/*  14:    */   private final int day;
/*  15:    */   private final int hour;
/*  16:    */   private final int minute;
/*  17:    */   private final int second;
/*  18:    */   private final int timeZone;
/*  19:    */   
/*  20:    */   public DateTime(String yearString, int month, int day, int hour, int minute, int second, int timeZone)
/*  21:    */   {
/*  22: 38 */     this.year = convertToYear(yearString);
/*  23: 39 */     this.date = convertToDate(this.year, month, day, hour, minute, second, timeZone);
/*  24: 40 */     this.month = month;
/*  25: 41 */     this.day = day;
/*  26: 42 */     this.hour = hour;
/*  27: 43 */     this.minute = minute;
/*  28: 44 */     this.second = second;
/*  29: 45 */     this.timeZone = timeZone;
/*  30:    */   }
/*  31:    */   
/*  32:    */   private int convertToYear(String yearString)
/*  33:    */   {
/*  34: 49 */     int year = Integer.parseInt(yearString);
/*  35: 50 */     switch (yearString.length())
/*  36:    */     {
/*  37:    */     case 1: 
/*  38:    */     case 2: 
/*  39: 53 */       if ((year >= 0) && (year < 50)) {
/*  40: 54 */         return 2000 + year;
/*  41:    */       }
/*  42: 56 */       return 1900 + year;
/*  43:    */     case 3: 
/*  44: 58 */       return 1900 + year;
/*  45:    */     }
/*  46: 60 */     return year;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static Date convertToDate(int year, int month, int day, int hour, int minute, int second, int timeZone)
/*  50:    */   {
/*  51: 65 */     Calendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT+0"));
/*  52: 66 */     c.set(year, month - 1, day, hour, minute, second);
/*  53: 67 */     c.set(14, 0);
/*  54: 69 */     if (timeZone != -2147483648)
/*  55:    */     {
/*  56: 70 */       int minutes = timeZone / 100 * 60 + timeZone % 100;
/*  57: 71 */       c.add(12, -1 * minutes);
/*  58:    */     }
/*  59: 74 */     return c.getTime();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Date getDate()
/*  63:    */   {
/*  64: 78 */     return this.date;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getYear()
/*  68:    */   {
/*  69: 82 */     return this.year;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getMonth()
/*  73:    */   {
/*  74: 86 */     return this.month;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getDay()
/*  78:    */   {
/*  79: 90 */     return this.day;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getHour()
/*  83:    */   {
/*  84: 94 */     return this.hour;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getMinute()
/*  88:    */   {
/*  89: 98 */     return this.minute;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getSecond()
/*  93:    */   {
/*  94:102 */     return this.second;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getTimeZone()
/*  98:    */   {
/*  99:106 */     return this.timeZone;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void print()
/* 103:    */   {
/* 104:110 */     System.out.println(toString());
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String toString()
/* 108:    */   {
/* 109:115 */     return getYear() + " " + getMonth() + " " + getDay() + "; " + getHour() + " " + getMinute() + " " + getSecond() + " " + getTimeZone();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int hashCode()
/* 113:    */   {
/* 114:120 */     int PRIME = 31;
/* 115:121 */     int result = 1;
/* 116:122 */     result = 31 * result + (this.date == null ? 0 : this.date.hashCode());
/* 117:123 */     result = 31 * result + this.day;
/* 118:124 */     result = 31 * result + this.hour;
/* 119:125 */     result = 31 * result + this.minute;
/* 120:126 */     result = 31 * result + this.month;
/* 121:127 */     result = 31 * result + this.second;
/* 122:128 */     result = 31 * result + this.timeZone;
/* 123:129 */     result = 31 * result + this.year;
/* 124:130 */     return result;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean equals(Object obj)
/* 128:    */   {
/* 129:135 */     if (this == obj) {
/* 130:136 */       return true;
/* 131:    */     }
/* 132:137 */     if (obj == null) {
/* 133:138 */       return false;
/* 134:    */     }
/* 135:139 */     if (getClass() != obj.getClass()) {
/* 136:140 */       return false;
/* 137:    */     }
/* 138:141 */     DateTime other = (DateTime)obj;
/* 139:142 */     if (this.date == null)
/* 140:    */     {
/* 141:143 */       if (other.date != null) {
/* 142:144 */         return false;
/* 143:    */       }
/* 144:    */     }
/* 145:145 */     else if (!this.date.equals(other.date)) {
/* 146:146 */       return false;
/* 147:    */     }
/* 148:147 */     if (this.day != other.day) {
/* 149:148 */       return false;
/* 150:    */     }
/* 151:149 */     if (this.hour != other.hour) {
/* 152:150 */       return false;
/* 153:    */     }
/* 154:151 */     if (this.minute != other.minute) {
/* 155:152 */       return false;
/* 156:    */     }
/* 157:153 */     if (this.month != other.month) {
/* 158:154 */       return false;
/* 159:    */     }
/* 160:155 */     if (this.second != other.second) {
/* 161:156 */       return false;
/* 162:    */     }
/* 163:157 */     if (this.timeZone != other.timeZone) {
/* 164:158 */       return false;
/* 165:    */     }
/* 166:159 */     if (this.year != other.year) {
/* 167:160 */       return false;
/* 168:    */     }
/* 169:161 */     return true;
/* 170:    */   }
/* 171:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.datetime.DateTime
 * JD-Core Version:    0.7.0.1
 */