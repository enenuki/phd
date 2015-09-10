/*   1:    */ package org.apache.log4j;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InterruptedIOException;
/*   6:    */ import java.text.DateFormat;
/*   7:    */ import java.text.SimpleDateFormat;
/*   8:    */ import java.util.Date;
/*   9:    */ import java.util.Locale;
/*  10:    */ import java.util.TimeZone;
/*  11:    */ import org.apache.log4j.helpers.LogLog;
/*  12:    */ import org.apache.log4j.spi.ErrorHandler;
/*  13:    */ import org.apache.log4j.spi.LoggingEvent;
/*  14:    */ 
/*  15:    */ public class DailyRollingFileAppender
/*  16:    */   extends FileAppender
/*  17:    */ {
/*  18:    */   static final int TOP_OF_TROUBLE = -1;
/*  19:    */   static final int TOP_OF_MINUTE = 0;
/*  20:    */   static final int TOP_OF_HOUR = 1;
/*  21:    */   static final int HALF_DAY = 2;
/*  22:    */   static final int TOP_OF_DAY = 3;
/*  23:    */   static final int TOP_OF_WEEK = 4;
/*  24:    */   static final int TOP_OF_MONTH = 5;
/*  25:160 */   private String datePattern = "'.'yyyy-MM-dd";
/*  26:    */   private String scheduledFilename;
/*  27:176 */   private long nextCheck = System.currentTimeMillis() - 1L;
/*  28:178 */   Date now = new Date();
/*  29:    */   SimpleDateFormat sdf;
/*  30:182 */   RollingCalendar rc = new RollingCalendar();
/*  31:184 */   int checkPeriod = -1;
/*  32:187 */   static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
/*  33:    */   
/*  34:    */   public DailyRollingFileAppender() {}
/*  35:    */   
/*  36:    */   public DailyRollingFileAppender(Layout layout, String filename, String datePattern)
/*  37:    */     throws IOException
/*  38:    */   {
/*  39:203 */     super(layout, filename, true);
/*  40:204 */     this.datePattern = datePattern;
/*  41:205 */     activateOptions();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setDatePattern(String pattern)
/*  45:    */   {
/*  46:214 */     this.datePattern = pattern;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getDatePattern()
/*  50:    */   {
/*  51:219 */     return this.datePattern;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void activateOptions()
/*  55:    */   {
/*  56:223 */     super.activateOptions();
/*  57:224 */     if ((this.datePattern != null) && (this.fileName != null))
/*  58:    */     {
/*  59:225 */       this.now.setTime(System.currentTimeMillis());
/*  60:226 */       this.sdf = new SimpleDateFormat(this.datePattern);
/*  61:227 */       int type = computeCheckPeriod();
/*  62:228 */       printPeriodicity(type);
/*  63:229 */       this.rc.setType(type);
/*  64:230 */       File file = new File(this.fileName);
/*  65:231 */       this.scheduledFilename = (this.fileName + this.sdf.format(new Date(file.lastModified())));
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69:234 */       LogLog.error("Either File or DatePattern options are not set for appender [" + this.name + "].");
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   void printPeriodicity(int type)
/*  74:    */   {
/*  75:240 */     switch (type)
/*  76:    */     {
/*  77:    */     case 0: 
/*  78:242 */       LogLog.debug("Appender [" + this.name + "] to be rolled every minute.");
/*  79:243 */       break;
/*  80:    */     case 1: 
/*  81:245 */       LogLog.debug("Appender [" + this.name + "] to be rolled on top of every hour.");
/*  82:    */       
/*  83:247 */       break;
/*  84:    */     case 2: 
/*  85:249 */       LogLog.debug("Appender [" + this.name + "] to be rolled at midday and midnight.");
/*  86:    */       
/*  87:251 */       break;
/*  88:    */     case 3: 
/*  89:253 */       LogLog.debug("Appender [" + this.name + "] to be rolled at midnight.");
/*  90:    */       
/*  91:255 */       break;
/*  92:    */     case 4: 
/*  93:257 */       LogLog.debug("Appender [" + this.name + "] to be rolled at start of week.");
/*  94:    */       
/*  95:259 */       break;
/*  96:    */     case 5: 
/*  97:261 */       LogLog.debug("Appender [" + this.name + "] to be rolled at start of every month.");
/*  98:    */       
/*  99:263 */       break;
/* 100:    */     default: 
/* 101:265 */       LogLog.warn("Unknown periodicity for appender [" + this.name + "].");
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   int computeCheckPeriod()
/* 106:    */   {
/* 107:280 */     RollingCalendar rollingCalendar = new RollingCalendar(gmtTimeZone, Locale.getDefault());
/* 108:    */     
/* 109:282 */     Date epoch = new Date(0L);
/* 110:283 */     if (this.datePattern != null) {
/* 111:284 */       for (int i = 0; i <= 5; i++)
/* 112:    */       {
/* 113:285 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.datePattern);
/* 114:286 */         simpleDateFormat.setTimeZone(gmtTimeZone);
/* 115:287 */         String r0 = simpleDateFormat.format(epoch);
/* 116:288 */         rollingCalendar.setType(i);
/* 117:289 */         Date next = new Date(rollingCalendar.getNextCheckMillis(epoch));
/* 118:290 */         String r1 = simpleDateFormat.format(next);
/* 119:292 */         if ((r0 != null) && (r1 != null) && (!r0.equals(r1))) {
/* 120:293 */           return i;
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124:297 */     return -1;
/* 125:    */   }
/* 126:    */   
/* 127:    */   void rollOver()
/* 128:    */     throws IOException
/* 129:    */   {
/* 130:306 */     if (this.datePattern == null)
/* 131:    */     {
/* 132:307 */       this.errorHandler.error("Missing DatePattern option in rollOver().");
/* 133:308 */       return;
/* 134:    */     }
/* 135:311 */     String datedFilename = this.fileName + this.sdf.format(this.now);
/* 136:315 */     if (this.scheduledFilename.equals(datedFilename)) {
/* 137:316 */       return;
/* 138:    */     }
/* 139:320 */     closeFile();
/* 140:    */     
/* 141:322 */     File target = new File(this.scheduledFilename);
/* 142:323 */     if (target.exists()) {
/* 143:324 */       target.delete();
/* 144:    */     }
/* 145:327 */     File file = new File(this.fileName);
/* 146:328 */     boolean result = file.renameTo(target);
/* 147:329 */     if (result) {
/* 148:330 */       LogLog.debug(this.fileName + " -> " + this.scheduledFilename);
/* 149:    */     } else {
/* 150:332 */       LogLog.error("Failed to rename [" + this.fileName + "] to [" + this.scheduledFilename + "].");
/* 151:    */     }
/* 152:    */     try
/* 153:    */     {
/* 154:338 */       setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
/* 155:    */     }
/* 156:    */     catch (IOException e)
/* 157:    */     {
/* 158:341 */       this.errorHandler.error("setFile(" + this.fileName + ", true) call failed.");
/* 159:    */     }
/* 160:343 */     this.scheduledFilename = datedFilename;
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected void subAppend(LoggingEvent event)
/* 164:    */   {
/* 165:355 */     long n = System.currentTimeMillis();
/* 166:356 */     if (n >= this.nextCheck)
/* 167:    */     {
/* 168:357 */       this.now.setTime(n);
/* 169:358 */       this.nextCheck = this.rc.getNextCheckMillis(this.now);
/* 170:    */       try
/* 171:    */       {
/* 172:360 */         rollOver();
/* 173:    */       }
/* 174:    */       catch (IOException ioe)
/* 175:    */       {
/* 176:363 */         if ((ioe instanceof InterruptedIOException)) {
/* 177:364 */           Thread.currentThread().interrupt();
/* 178:    */         }
/* 179:366 */         LogLog.error("rollOver() failed.", ioe);
/* 180:    */       }
/* 181:    */     }
/* 182:369 */     super.subAppend(event);
/* 183:    */   }
/* 184:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.DailyRollingFileAppender
 * JD-Core Version:    0.7.0.1
 */