/*   1:    */ package org.apache.commons.lang.time;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.Date;
/*   5:    */ import java.util.Locale;
/*   6:    */ import java.util.TimeZone;
/*   7:    */ 
/*   8:    */ public class DateFormatUtils
/*   9:    */ {
/*  10: 44 */   public static final FastDateFormat ISO_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss");
/*  11: 51 */   public static final FastDateFormat ISO_DATETIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZZ");
/*  12: 58 */   public static final FastDateFormat ISO_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
/*  13: 67 */   public static final FastDateFormat ISO_DATE_TIME_ZONE_FORMAT = FastDateFormat.getInstance("yyyy-MM-ddZZ");
/*  14: 74 */   public static final FastDateFormat ISO_TIME_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ss");
/*  15: 81 */   public static final FastDateFormat ISO_TIME_TIME_ZONE_FORMAT = FastDateFormat.getInstance("'T'HH:mm:ssZZ");
/*  16: 90 */   public static final FastDateFormat ISO_TIME_NO_T_FORMAT = FastDateFormat.getInstance("HH:mm:ss");
/*  17: 99 */   public static final FastDateFormat ISO_TIME_NO_T_TIME_ZONE_FORMAT = FastDateFormat.getInstance("HH:mm:ssZZ");
/*  18:106 */   public static final FastDateFormat SMTP_DATETIME_FORMAT = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
/*  19:    */   
/*  20:    */   public static String formatUTC(long millis, String pattern)
/*  21:    */   {
/*  22:128 */     return format(new Date(millis), pattern, DateUtils.UTC_TIME_ZONE, null);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static String formatUTC(Date date, String pattern)
/*  26:    */   {
/*  27:139 */     return format(date, pattern, DateUtils.UTC_TIME_ZONE, null);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static String formatUTC(long millis, String pattern, Locale locale)
/*  31:    */   {
/*  32:151 */     return format(new Date(millis), pattern, DateUtils.UTC_TIME_ZONE, locale);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static String formatUTC(Date date, String pattern, Locale locale)
/*  36:    */   {
/*  37:163 */     return format(date, pattern, DateUtils.UTC_TIME_ZONE, locale);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static String format(long millis, String pattern)
/*  41:    */   {
/*  42:174 */     return format(new Date(millis), pattern, null, null);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static String format(Date date, String pattern)
/*  46:    */   {
/*  47:185 */     return format(date, pattern, null, null);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static String format(Calendar calendar, String pattern)
/*  51:    */   {
/*  52:198 */     return format(calendar, pattern, null, null);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static String format(long millis, String pattern, TimeZone timeZone)
/*  56:    */   {
/*  57:210 */     return format(new Date(millis), pattern, timeZone, null);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static String format(Date date, String pattern, TimeZone timeZone)
/*  61:    */   {
/*  62:222 */     return format(date, pattern, timeZone, null);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static String format(Calendar calendar, String pattern, TimeZone timeZone)
/*  66:    */   {
/*  67:236 */     return format(calendar, pattern, timeZone, null);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static String format(long millis, String pattern, Locale locale)
/*  71:    */   {
/*  72:248 */     return format(new Date(millis), pattern, null, locale);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static String format(Date date, String pattern, Locale locale)
/*  76:    */   {
/*  77:260 */     return format(date, pattern, null, locale);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static String format(Calendar calendar, String pattern, Locale locale)
/*  81:    */   {
/*  82:274 */     return format(calendar, pattern, null, locale);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static String format(long millis, String pattern, TimeZone timeZone, Locale locale)
/*  86:    */   {
/*  87:287 */     return format(new Date(millis), pattern, timeZone, locale);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static String format(Date date, String pattern, TimeZone timeZone, Locale locale)
/*  91:    */   {
/*  92:300 */     FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
/*  93:301 */     return df.format(date);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static String format(Calendar calendar, String pattern, TimeZone timeZone, Locale locale)
/*  97:    */   {
/*  98:316 */     FastDateFormat df = FastDateFormat.getInstance(pattern, timeZone, locale);
/*  99:317 */     return df.format(calendar);
/* 100:    */   }
/* 101:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.time.DateFormatUtils
 * JD-Core Version:    0.7.0.1
 */