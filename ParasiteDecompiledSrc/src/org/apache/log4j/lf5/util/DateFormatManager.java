/*   1:    */ package org.apache.log4j.lf5.util;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.ParseException;
/*   5:    */ import java.text.SimpleDateFormat;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.Locale;
/*   8:    */ import java.util.TimeZone;
/*   9:    */ 
/*  10:    */ public class DateFormatManager
/*  11:    */ {
/*  12: 50 */   private TimeZone _timeZone = null;
/*  13: 51 */   private Locale _locale = null;
/*  14: 53 */   private String _pattern = null;
/*  15: 54 */   private DateFormat _dateFormat = null;
/*  16:    */   
/*  17:    */   public DateFormatManager()
/*  18:    */   {
/*  19: 61 */     configure();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public DateFormatManager(TimeZone timeZone)
/*  23:    */   {
/*  24: 67 */     this._timeZone = timeZone;
/*  25: 68 */     configure();
/*  26:    */   }
/*  27:    */   
/*  28:    */   public DateFormatManager(Locale locale)
/*  29:    */   {
/*  30: 74 */     this._locale = locale;
/*  31: 75 */     configure();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public DateFormatManager(String pattern)
/*  35:    */   {
/*  36: 81 */     this._pattern = pattern;
/*  37: 82 */     configure();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public DateFormatManager(TimeZone timeZone, Locale locale)
/*  41:    */   {
/*  42: 88 */     this._timeZone = timeZone;
/*  43: 89 */     this._locale = locale;
/*  44: 90 */     configure();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public DateFormatManager(TimeZone timeZone, String pattern)
/*  48:    */   {
/*  49: 96 */     this._timeZone = timeZone;
/*  50: 97 */     this._pattern = pattern;
/*  51: 98 */     configure();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public DateFormatManager(Locale locale, String pattern)
/*  55:    */   {
/*  56:104 */     this._locale = locale;
/*  57:105 */     this._pattern = pattern;
/*  58:106 */     configure();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public DateFormatManager(TimeZone timeZone, Locale locale, String pattern)
/*  62:    */   {
/*  63:112 */     this._timeZone = timeZone;
/*  64:113 */     this._locale = locale;
/*  65:114 */     this._pattern = pattern;
/*  66:115 */     configure();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public synchronized TimeZone getTimeZone()
/*  70:    */   {
/*  71:123 */     if (this._timeZone == null) {
/*  72:124 */       return TimeZone.getDefault();
/*  73:    */     }
/*  74:126 */     return this._timeZone;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public synchronized void setTimeZone(TimeZone timeZone)
/*  78:    */   {
/*  79:131 */     this._timeZone = timeZone;
/*  80:132 */     configure();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized Locale getLocale()
/*  84:    */   {
/*  85:136 */     if (this._locale == null) {
/*  86:137 */       return Locale.getDefault();
/*  87:    */     }
/*  88:139 */     return this._locale;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public synchronized void setLocale(Locale locale)
/*  92:    */   {
/*  93:144 */     this._locale = locale;
/*  94:145 */     configure();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public synchronized String getPattern()
/*  98:    */   {
/*  99:149 */     return this._pattern;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public synchronized void setPattern(String pattern)
/* 103:    */   {
/* 104:156 */     this._pattern = pattern;
/* 105:157 */     configure();
/* 106:    */   }
/* 107:    */   
/* 108:    */   /**
/* 109:    */    * @deprecated
/* 110:    */    */
/* 111:    */   public synchronized String getOutputFormat()
/* 112:    */   {
/* 113:166 */     return this._pattern;
/* 114:    */   }
/* 115:    */   
/* 116:    */   /**
/* 117:    */    * @deprecated
/* 118:    */    */
/* 119:    */   public synchronized void setOutputFormat(String pattern)
/* 120:    */   {
/* 121:174 */     this._pattern = pattern;
/* 122:175 */     configure();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public synchronized DateFormat getDateFormatInstance()
/* 126:    */   {
/* 127:179 */     return this._dateFormat;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public synchronized void setDateFormatInstance(DateFormat dateFormat)
/* 131:    */   {
/* 132:183 */     this._dateFormat = dateFormat;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String format(Date date)
/* 136:    */   {
/* 137:188 */     return getDateFormatInstance().format(date);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String format(Date date, String pattern)
/* 141:    */   {
/* 142:192 */     DateFormat formatter = null;
/* 143:193 */     formatter = getDateFormatInstance();
/* 144:194 */     if ((formatter instanceof SimpleDateFormat))
/* 145:    */     {
/* 146:195 */       formatter = (SimpleDateFormat)formatter.clone();
/* 147:196 */       ((SimpleDateFormat)formatter).applyPattern(pattern);
/* 148:    */     }
/* 149:198 */     return formatter.format(date);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public Date parse(String date)
/* 153:    */     throws ParseException
/* 154:    */   {
/* 155:205 */     return getDateFormatInstance().parse(date);
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Date parse(String date, String pattern)
/* 159:    */     throws ParseException
/* 160:    */   {
/* 161:212 */     DateFormat formatter = null;
/* 162:213 */     formatter = getDateFormatInstance();
/* 163:214 */     if ((formatter instanceof SimpleDateFormat))
/* 164:    */     {
/* 165:215 */       formatter = (SimpleDateFormat)formatter.clone();
/* 166:216 */       ((SimpleDateFormat)formatter).applyPattern(pattern);
/* 167:    */     }
/* 168:218 */     return formatter.parse(date);
/* 169:    */   }
/* 170:    */   
/* 171:    */   private synchronized void configure()
/* 172:    */   {
/* 173:229 */     this._dateFormat = DateFormat.getDateTimeInstance(0, 0, getLocale());
/* 174:    */     
/* 175:    */ 
/* 176:232 */     this._dateFormat.setTimeZone(getTimeZone());
/* 177:234 */     if (this._pattern != null) {
/* 178:235 */       ((SimpleDateFormat)this._dateFormat).applyPattern(this._pattern);
/* 179:    */     }
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.util.DateFormatManager
 * JD-Core Version:    0.7.0.1
 */