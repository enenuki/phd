/*  1:   */ package org.apache.log4j.helpers;
/*  2:   */ 
/*  3:   */ import java.text.DateFormat;
/*  4:   */ import java.text.FieldPosition;
/*  5:   */ import java.text.ParsePosition;
/*  6:   */ import java.util.Date;
/*  7:   */ 
/*  8:   */ public class RelativeTimeDateFormat
/*  9:   */   extends DateFormat
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = 7055751607085611984L;
/* 12:   */   protected final long startTime;
/* 13:   */   
/* 14:   */   public RelativeTimeDateFormat()
/* 15:   */   {
/* 16:42 */     this.startTime = System.currentTimeMillis();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public StringBuffer format(Date date, StringBuffer sbuf, FieldPosition fieldPosition)
/* 20:   */   {
/* 21:55 */     return sbuf.append(date.getTime() - this.startTime);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Date parse(String s, ParsePosition pos)
/* 25:   */   {
/* 26:63 */     return null;
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.RelativeTimeDateFormat
 * JD-Core Version:    0.7.0.1
 */