/*   1:    */ package org.apache.log4j.pattern;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.FieldPosition;
/*   5:    */ import java.text.NumberFormat;
/*   6:    */ import java.text.ParsePosition;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.TimeZone;
/*   9:    */ 
/*  10:    */ public final class CachedDateFormat
/*  11:    */   extends DateFormat
/*  12:    */ {
/*  13:    */   private static final long serialVersionUID = 1L;
/*  14:    */   public static final int NO_MILLISECONDS = -2;
/*  15:    */   private static final String DIGITS = "0123456789";
/*  16:    */   public static final int UNRECOGNIZED_MILLISECONDS = -1;
/*  17:    */   private static final int MAGIC1 = 654;
/*  18:    */   private static final String MAGICSTRING1 = "654";
/*  19:    */   private static final int MAGIC2 = 987;
/*  20:    */   private static final String MAGICSTRING2 = "987";
/*  21:    */   private static final String ZERO_STRING = "000";
/*  22:    */   private final DateFormat formatter;
/*  23:    */   private int millisecondStart;
/*  24:    */   private long slotBegin;
/*  25:105 */   private StringBuffer cache = new StringBuffer(50);
/*  26:    */   private final int expiration;
/*  27:    */   private long previousTime;
/*  28:122 */   private final Date tmpDate = new Date(0L);
/*  29:    */   
/*  30:    */   public CachedDateFormat(DateFormat dateFormat, int expiration)
/*  31:    */   {
/*  32:133 */     if (dateFormat == null) {
/*  33:134 */       throw new IllegalArgumentException("dateFormat cannot be null");
/*  34:    */     }
/*  35:137 */     if (expiration < 0) {
/*  36:138 */       throw new IllegalArgumentException("expiration must be non-negative");
/*  37:    */     }
/*  38:141 */     this.formatter = dateFormat;
/*  39:142 */     this.expiration = expiration;
/*  40:143 */     this.millisecondStart = 0;
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:148 */     this.previousTime = -9223372036854775808L;
/*  46:149 */     this.slotBegin = -9223372036854775808L;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static int findMillisecondStart(long time, String formatted, DateFormat formatter)
/*  50:    */   {
/*  51:163 */     long slotBegin = time / 1000L * 1000L;
/*  52:165 */     if (slotBegin > time) {
/*  53:166 */       slotBegin -= 1000L;
/*  54:    */     }
/*  55:169 */     int millis = (int)(time - slotBegin);
/*  56:    */     
/*  57:171 */     int magic = 654;
/*  58:172 */     String magicString = "654";
/*  59:174 */     if (millis == 654)
/*  60:    */     {
/*  61:175 */       magic = 987;
/*  62:176 */       magicString = "987";
/*  63:    */     }
/*  64:179 */     String plusMagic = formatter.format(new Date(slotBegin + magic));
/*  65:185 */     if (plusMagic.length() != formatted.length()) {
/*  66:186 */       return -1;
/*  67:    */     }
/*  68:189 */     for (int i = 0; i < formatted.length(); i++) {
/*  69:190 */       if (formatted.charAt(i) != plusMagic.charAt(i))
/*  70:    */       {
/*  71:193 */         StringBuffer formattedMillis = new StringBuffer("ABC");
/*  72:194 */         millisecondFormat(millis, formattedMillis, 0);
/*  73:    */         
/*  74:196 */         String plusZero = formatter.format(new Date(slotBegin));
/*  75:200 */         if ((plusZero.length() == formatted.length()) && (magicString.regionMatches(0, plusMagic, i, magicString.length())) && (formattedMillis.toString().regionMatches(0, formatted, i, magicString.length())) && ("000".regionMatches(0, plusZero, i, "000".length()))) {
/*  76:208 */           return i;
/*  77:    */         }
/*  78:210 */         return -1;
/*  79:    */       }
/*  80:    */     }
/*  81:216 */     return -2;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/*  85:    */   {
/*  86:229 */     format(date.getTime(), sbuf);
/*  87:    */     
/*  88:231 */     return sbuf;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public StringBuffer format(long now, StringBuffer buf)
/*  92:    */   {
/*  93:246 */     if (now == this.previousTime)
/*  94:    */     {
/*  95:247 */       buf.append(this.cache);
/*  96:    */       
/*  97:249 */       return buf;
/*  98:    */     }
/*  99:256 */     if ((this.millisecondStart != -1) && (now < this.slotBegin + this.expiration) && (now >= this.slotBegin) && (now < this.slotBegin + 1000L))
/* 100:    */     {
/* 101:265 */       if (this.millisecondStart >= 0) {
/* 102:266 */         millisecondFormat((int)(now - this.slotBegin), this.cache, this.millisecondStart);
/* 103:    */       }
/* 104:272 */       this.previousTime = now;
/* 105:273 */       buf.append(this.cache);
/* 106:    */       
/* 107:275 */       return buf;
/* 108:    */     }
/* 109:281 */     this.cache.setLength(0);
/* 110:282 */     this.tmpDate.setTime(now);
/* 111:283 */     this.cache.append(this.formatter.format(this.tmpDate));
/* 112:284 */     buf.append(this.cache);
/* 113:285 */     this.previousTime = now;
/* 114:286 */     this.slotBegin = (this.previousTime / 1000L * 1000L);
/* 115:288 */     if (this.slotBegin > this.previousTime) {
/* 116:289 */       this.slotBegin -= 1000L;
/* 117:    */     }
/* 118:296 */     if (this.millisecondStart >= 0) {
/* 119:297 */       this.millisecondStart = findMillisecondStart(now, this.cache.toString(), this.formatter);
/* 120:    */     }
/* 121:301 */     return buf;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private static void millisecondFormat(int millis, StringBuffer buf, int offset)
/* 125:    */   {
/* 126:313 */     buf.setCharAt(offset, "0123456789".charAt(millis / 100));
/* 127:314 */     buf.setCharAt(offset + 1, "0123456789".charAt(millis / 10 % 10));
/* 128:315 */     buf.setCharAt(offset + 2, "0123456789".charAt(millis % 10));
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setTimeZone(TimeZone timeZone)
/* 132:    */   {
/* 133:326 */     this.formatter.setTimeZone(timeZone);
/* 134:327 */     this.previousTime = -9223372036854775808L;
/* 135:328 */     this.slotBegin = -9223372036854775808L;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Date parse(String s, ParsePosition pos)
/* 139:    */   {
/* 140:339 */     return this.formatter.parse(s, pos);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public NumberFormat getNumberFormat()
/* 144:    */   {
/* 145:348 */     return this.formatter.getNumberFormat();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static int getMaximumCacheValidity(String pattern)
/* 149:    */   {
/* 150:364 */     int firstS = pattern.indexOf('S');
/* 151:366 */     if ((firstS >= 0) && (firstS != pattern.lastIndexOf("SSS"))) {
/* 152:367 */       return 1;
/* 153:    */     }
/* 154:370 */     return 1000;
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.pattern.CachedDateFormat
 * JD-Core Version:    0.7.0.1
 */