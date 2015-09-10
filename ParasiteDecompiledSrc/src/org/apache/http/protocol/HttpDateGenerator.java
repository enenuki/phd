/*  1:   */ package org.apache.http.protocol;
/*  2:   */ 
/*  3:   */ import java.text.DateFormat;
/*  4:   */ import java.text.SimpleDateFormat;
/*  5:   */ import java.util.Date;
/*  6:   */ import java.util.Locale;
/*  7:   */ import java.util.TimeZone;
/*  8:   */ 
/*  9:   */ public class HttpDateGenerator
/* 10:   */ {
/* 11:   */   public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
/* 12:49 */   public static final TimeZone GMT = TimeZone.getTimeZone("GMT");
/* 13:   */   private final DateFormat dateformat;
/* 14:54 */   private long dateAsLong = 0L;
/* 15:55 */   private String dateAsText = null;
/* 16:   */   
/* 17:   */   public HttpDateGenerator()
/* 18:   */   {
/* 19:59 */     this.dateformat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
/* 20:60 */     this.dateformat.setTimeZone(GMT);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public synchronized String getCurrentDate()
/* 24:   */   {
/* 25:64 */     long now = System.currentTimeMillis();
/* 26:65 */     if (now - this.dateAsLong > 1000L)
/* 27:   */     {
/* 28:67 */       this.dateAsText = this.dateformat.format(new Date(now));
/* 29:68 */       this.dateAsLong = now;
/* 30:   */     }
/* 31:70 */     return this.dateAsText;
/* 32:   */   }
/* 33:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.protocol.HttpDateGenerator
 * JD-Core Version:    0.7.0.1
 */