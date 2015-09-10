/*  1:   */ package org.hibernate.internal.util.compare;
/*  2:   */ 
/*  3:   */ import java.util.Calendar;
/*  4:   */ import java.util.Comparator;
/*  5:   */ 
/*  6:   */ public class CalendarComparator
/*  7:   */   implements Comparator<Calendar>
/*  8:   */ {
/*  9:34 */   public static final CalendarComparator INSTANCE = new CalendarComparator();
/* 10:   */   
/* 11:   */   public int compare(Calendar x, Calendar y)
/* 12:   */   {
/* 13:37 */     if (x.before(y)) {
/* 14:38 */       return -1;
/* 15:   */     }
/* 16:40 */     if (x.after(y)) {
/* 17:41 */       return 1;
/* 18:   */     }
/* 19:43 */     return 0;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.compare.CalendarComparator
 * JD-Core Version:    0.7.0.1
 */