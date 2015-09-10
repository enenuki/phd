/*   1:    */ package org.apache.http.impl.cookie;
/*   2:    */ 
/*   3:    */ import java.lang.ref.SoftReference;
/*   4:    */ import java.text.ParseException;
/*   5:    */ import java.text.SimpleDateFormat;
/*   6:    */ import java.util.Calendar;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Locale;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.TimeZone;
/*  12:    */ import org.apache.http.annotation.Immutable;
/*  13:    */ 
/*  14:    */ @Immutable
/*  15:    */ public final class DateUtils
/*  16:    */ {
/*  17:    */   public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
/*  18:    */   public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";
/*  19:    */   public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";
/*  20: 69 */   private static final String[] DEFAULT_PATTERNS = { "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy" };
/*  21:    */   private static final Date DEFAULT_TWO_DIGIT_YEAR_START;
/*  22: 77 */   public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
/*  23:    */   
/*  24:    */   static
/*  25:    */   {
/*  26: 80 */     Calendar calendar = Calendar.getInstance();
/*  27: 81 */     calendar.setTimeZone(GMT);
/*  28: 82 */     calendar.set(2000, 0, 1, 0, 0, 0);
/*  29: 83 */     calendar.set(14, 0);
/*  30: 84 */     DEFAULT_TWO_DIGIT_YEAR_START = calendar.getTime();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static Date parseDate(String dateValue)
/*  34:    */     throws DateParseException
/*  35:    */   {
/*  36: 99 */     return parseDate(dateValue, null, null);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static Date parseDate(String dateValue, String[] dateFormats)
/*  40:    */     throws DateParseException
/*  41:    */   {
/*  42:114 */     return parseDate(dateValue, dateFormats, null);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Date parseDate(String dateValue, String[] dateFormats, Date startDate)
/*  46:    */     throws DateParseException
/*  47:    */   {
/*  48:137 */     if (dateValue == null) {
/*  49:138 */       throw new IllegalArgumentException("dateValue is null");
/*  50:    */     }
/*  51:140 */     if (dateFormats == null) {
/*  52:141 */       dateFormats = DEFAULT_PATTERNS;
/*  53:    */     }
/*  54:143 */     if (startDate == null) {
/*  55:144 */       startDate = DEFAULT_TWO_DIGIT_YEAR_START;
/*  56:    */     }
/*  57:148 */     if ((dateValue.length() > 1) && (dateValue.startsWith("'")) && (dateValue.endsWith("'"))) {
/*  58:152 */       dateValue = dateValue.substring(1, dateValue.length() - 1);
/*  59:    */     }
/*  60:155 */     for (String dateFormat : dateFormats)
/*  61:    */     {
/*  62:156 */       SimpleDateFormat dateParser = DateFormatHolder.formatFor(dateFormat);
/*  63:157 */       dateParser.set2DigitYearStart(startDate);
/*  64:    */       try
/*  65:    */       {
/*  66:160 */         return dateParser.parse(dateValue);
/*  67:    */       }
/*  68:    */       catch (ParseException pe) {}
/*  69:    */     }
/*  70:167 */     throw new DateParseException("Unable to parse the date " + dateValue);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static String formatDate(Date date)
/*  74:    */   {
/*  75:179 */     return formatDate(date, "EEE, dd MMM yyyy HH:mm:ss zzz");
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String formatDate(Date date, String pattern)
/*  79:    */   {
/*  80:196 */     if (date == null) {
/*  81:196 */       throw new IllegalArgumentException("date is null");
/*  82:    */     }
/*  83:197 */     if (pattern == null) {
/*  84:197 */       throw new IllegalArgumentException("pattern is null");
/*  85:    */     }
/*  86:199 */     SimpleDateFormat formatter = DateFormatHolder.formatFor(pattern);
/*  87:200 */     return formatter.format(date);
/*  88:    */   }
/*  89:    */   
/*  90:    */   static final class DateFormatHolder
/*  91:    */   {
/*  92:216 */     private static final ThreadLocal<SoftReference<Map<String, SimpleDateFormat>>> THREADLOCAL_FORMATS = new ThreadLocal()
/*  93:    */     {
/*  94:    */       protected SoftReference<Map<String, SimpleDateFormat>> initialValue()
/*  95:    */       {
/*  96:220 */         return new SoftReference(new HashMap());
/*  97:    */       }
/*  98:    */     };
/*  99:    */     
/* 100:    */     public static SimpleDateFormat formatFor(String pattern)
/* 101:    */     {
/* 102:239 */       SoftReference<Map<String, SimpleDateFormat>> ref = (SoftReference)THREADLOCAL_FORMATS.get();
/* 103:240 */       Map<String, SimpleDateFormat> formats = (Map)ref.get();
/* 104:241 */       if (formats == null)
/* 105:    */       {
/* 106:242 */         formats = new HashMap();
/* 107:243 */         THREADLOCAL_FORMATS.set(new SoftReference(formats));
/* 108:    */       }
/* 109:247 */       SimpleDateFormat format = (SimpleDateFormat)formats.get(pattern);
/* 110:248 */       if (format == null)
/* 111:    */       {
/* 112:249 */         format = new SimpleDateFormat(pattern, Locale.US);
/* 113:250 */         format.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 114:251 */         formats.put(pattern, format);
/* 115:    */       }
/* 116:254 */       return format;
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.impl.cookie.DateUtils
 * JD-Core Version:    0.7.0.1
 */