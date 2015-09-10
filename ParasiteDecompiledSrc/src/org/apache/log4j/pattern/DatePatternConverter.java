/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.FieldPosition;
/*   5:    */ import java.text.ParsePosition;
/*   6:    */ import java.text.SimpleDateFormat;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.TimeZone;
/*   9:    */ import org.apache.log4j.helpers.LogLog;
/*  10:    */ import org.apache.log4j.spi.LoggingEvent;
/*  11:    */ 
/*  12:    */ public final class DatePatternConverter
/*  13:    */   extends LoggingEventPatternConverter
/*  14:    */ {
/*  15:    */   private static final String ABSOLUTE_FORMAT = "ABSOLUTE";
/*  16:    */   private static final String ABSOLUTE_TIME_PATTERN = "HH:mm:ss,SSS";
/*  17:    */   private static final String DATE_AND_TIME_FORMAT = "DATE";
/*  18:    */   private static final String DATE_AND_TIME_PATTERN = "dd MMM yyyy HH:mm:ss,SSS";
/*  19:    */   private static final String ISO8601_FORMAT = "ISO8601";
/*  20:    */   private static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
/*  21:    */   private final CachedDateFormat df;
/*  22:    */   
/*  23:    */   private static class DefaultZoneDateFormat
/*  24:    */     extends DateFormat
/*  25:    */   {
/*  26:    */     private static final long serialVersionUID = 1L;
/*  27:    */     private final DateFormat dateFormat;
/*  28:    */     
/*  29:    */     public DefaultZoneDateFormat(DateFormat format)
/*  30:    */     {
/*  31: 88 */       this.dateFormat = format;
/*  32:    */     }
/*  33:    */     
/*  34:    */     public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition)
/*  35:    */     {
/*  36: 95 */       this.dateFormat.setTimeZone(TimeZone.getDefault());
/*  37: 96 */       return this.dateFormat.format(date, toAppendTo, fieldPosition);
/*  38:    */     }
/*  39:    */     
/*  40:    */     public Date parse(String source, ParsePosition pos)
/*  41:    */     {
/*  42:103 */       this.dateFormat.setTimeZone(TimeZone.getDefault());
/*  43:104 */       return this.dateFormat.parse(source, pos);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   private DatePatternConverter(String[] options)
/*  48:    */   {
/*  49:113 */     super("Date", "date");
/*  50:    */     String patternOption;
/*  51:    */     String patternOption;
/*  52:117 */     if ((options == null) || (options.length == 0)) {
/*  53:120 */       patternOption = null;
/*  54:    */     } else {
/*  55:122 */       patternOption = options[0];
/*  56:    */     }
/*  57:    */     String pattern;
/*  58:    */     String pattern;
/*  59:127 */     if ((patternOption == null) || (patternOption.equalsIgnoreCase("ISO8601")))
/*  60:    */     {
/*  61:130 */       pattern = "yyyy-MM-dd HH:mm:ss,SSS";
/*  62:    */     }
/*  63:    */     else
/*  64:    */     {
/*  65:    */       String pattern;
/*  66:131 */       if (patternOption.equalsIgnoreCase("ABSOLUTE"))
/*  67:    */       {
/*  68:132 */         pattern = "HH:mm:ss,SSS";
/*  69:    */       }
/*  70:    */       else
/*  71:    */       {
/*  72:    */         String pattern;
/*  73:133 */         if (patternOption.equalsIgnoreCase("DATE")) {
/*  74:134 */           pattern = "dd MMM yyyy HH:mm:ss,SSS";
/*  75:    */         } else {
/*  76:136 */           pattern = patternOption;
/*  77:    */         }
/*  78:    */       }
/*  79:    */     }
/*  80:139 */     int maximumCacheValidity = 1000;
/*  81:140 */     DateFormat simpleFormat = null;
/*  82:    */     try
/*  83:    */     {
/*  84:143 */       simpleFormat = new SimpleDateFormat(pattern);
/*  85:144 */       maximumCacheValidity = CachedDateFormat.getMaximumCacheValidity(pattern);
/*  86:    */     }
/*  87:    */     catch (IllegalArgumentException e)
/*  88:    */     {
/*  89:146 */       LogLog.warn("Could not instantiate SimpleDateFormat with pattern " + patternOption, e);
/*  90:    */       
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:151 */       simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
/*  95:    */     }
/*  96:155 */     if ((options != null) && (options.length > 1))
/*  97:    */     {
/*  98:156 */       TimeZone tz = TimeZone.getTimeZone(options[1]);
/*  99:157 */       simpleFormat.setTimeZone(tz);
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:159 */       simpleFormat = new DefaultZoneDateFormat(simpleFormat);
/* 104:    */     }
/* 105:162 */     this.df = new CachedDateFormat(simpleFormat, maximumCacheValidity);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static DatePatternConverter newInstance(String[] options)
/* 109:    */   {
/* 110:172 */     return new DatePatternConverter(options);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void format(LoggingEvent event, StringBuffer output)
/* 114:    */   {
/* 115:179 */     synchronized (this)
/* 116:    */     {
/* 117:180 */       this.df.format(event.timeStamp, output);
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void format(Object obj, StringBuffer output)
/* 122:    */   {
/* 123:188 */     if ((obj instanceof Date)) {
/* 124:189 */       format((Date)obj, output);
/* 125:    */     }
/* 126:192 */     super.format(obj, output);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void format(Date date, StringBuffer toAppendTo)
/* 130:    */   {
/* 131:201 */     synchronized (this)
/* 132:    */     {
/* 133:202 */       this.df.format(date.getTime(), toAppendTo);
/* 134:    */     }
/* 135:    */   }
/* 136:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.DatePatternConverter
 * JD-Core Version:    0.7.0.1
 */