/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.FieldPosition;
/*   5:    */ import java.text.SimpleDateFormat;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.TimeZone;
/*   8:    */ import org.apache.log4j.Layout;
/*   9:    */ import org.apache.log4j.spi.LoggingEvent;
/*  10:    */ 
/*  11:    */ public abstract class DateLayout
/*  12:    */   extends Layout
/*  13:    */ {
/*  14:    */   public static final String NULL_DATE_FORMAT = "NULL";
/*  15:    */   public static final String RELATIVE_TIME_DATE_FORMAT = "RELATIVE";
/*  16: 51 */   protected FieldPosition pos = new FieldPosition(0);
/*  17:    */   /**
/*  18:    */    * @deprecated
/*  19:    */    */
/*  20:    */   public static final String DATE_FORMAT_OPTION = "DateFormat";
/*  21:    */   /**
/*  22:    */    * @deprecated
/*  23:    */    */
/*  24:    */   public static final String TIMEZONE_OPTION = "TimeZone";
/*  25:    */   private String timeZoneID;
/*  26:    */   private String dateFormatOption;
/*  27:    */   protected DateFormat dateFormat;
/*  28: 71 */   protected Date date = new Date();
/*  29:    */   
/*  30:    */   /**
/*  31:    */    * @deprecated
/*  32:    */    */
/*  33:    */   public String[] getOptionStrings()
/*  34:    */   {
/*  35: 79 */     return new String[] { "DateFormat", "TimeZone" };
/*  36:    */   }
/*  37:    */   
/*  38:    */   /**
/*  39:    */    * @deprecated
/*  40:    */    */
/*  41:    */   public void setOption(String option, String value)
/*  42:    */   {
/*  43: 88 */     if (option.equalsIgnoreCase("DateFormat")) {
/*  44: 89 */       this.dateFormatOption = value.toUpperCase();
/*  45: 90 */     } else if (option.equalsIgnoreCase("TimeZone")) {
/*  46: 91 */       this.timeZoneID = value;
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setDateFormat(String dateFormat)
/*  51:    */   {
/*  52:103 */     if (dateFormat != null) {
/*  53:104 */       this.dateFormatOption = dateFormat;
/*  54:    */     }
/*  55:106 */     setDateFormat(this.dateFormatOption, TimeZone.getDefault());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getDateFormat()
/*  59:    */   {
/*  60:114 */     return this.dateFormatOption;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setTimeZone(String timeZone)
/*  64:    */   {
/*  65:123 */     this.timeZoneID = timeZone;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getTimeZone()
/*  69:    */   {
/*  70:131 */     return this.timeZoneID;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void activateOptions()
/*  74:    */   {
/*  75:136 */     setDateFormat(this.dateFormatOption);
/*  76:137 */     if ((this.timeZoneID != null) && (this.dateFormat != null)) {
/*  77:138 */       this.dateFormat.setTimeZone(TimeZone.getTimeZone(this.timeZoneID));
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void dateFormat(StringBuffer buf, LoggingEvent event)
/*  82:    */   {
/*  83:144 */     if (this.dateFormat != null)
/*  84:    */     {
/*  85:145 */       this.date.setTime(event.timeStamp);
/*  86:146 */       this.dateFormat.format(this.date, buf, this.pos);
/*  87:147 */       buf.append(' ');
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setDateFormat(DateFormat dateFormat, TimeZone timeZone)
/*  92:    */   {
/*  93:157 */     this.dateFormat = dateFormat;
/*  94:158 */     this.dateFormat.setTimeZone(timeZone);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setDateFormat(String dateFormatType, TimeZone timeZone)
/*  98:    */   {
/*  99:177 */     if (dateFormatType == null)
/* 100:    */     {
/* 101:178 */       this.dateFormat = null;
/* 102:179 */       return;
/* 103:    */     }
/* 104:182 */     if (dateFormatType.equalsIgnoreCase("NULL"))
/* 105:    */     {
/* 106:183 */       this.dateFormat = null;
/* 107:    */     }
/* 108:184 */     else if (dateFormatType.equalsIgnoreCase("RELATIVE"))
/* 109:    */     {
/* 110:185 */       this.dateFormat = new RelativeTimeDateFormat();
/* 111:    */     }
/* 112:186 */     else if (dateFormatType.equalsIgnoreCase("ABSOLUTE"))
/* 113:    */     {
/* 114:188 */       this.dateFormat = new AbsoluteTimeDateFormat(timeZone);
/* 115:    */     }
/* 116:189 */     else if (dateFormatType.equalsIgnoreCase("DATE"))
/* 117:    */     {
/* 118:191 */       this.dateFormat = new DateTimeDateFormat(timeZone);
/* 119:    */     }
/* 120:192 */     else if (dateFormatType.equalsIgnoreCase("ISO8601"))
/* 121:    */     {
/* 122:194 */       this.dateFormat = new ISO8601DateFormat(timeZone);
/* 123:    */     }
/* 124:    */     else
/* 125:    */     {
/* 126:196 */       this.dateFormat = new SimpleDateFormat(dateFormatType);
/* 127:197 */       this.dateFormat.setTimeZone(timeZone);
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.DateLayout
 * JD-Core Version:    0.7.0.1
 */