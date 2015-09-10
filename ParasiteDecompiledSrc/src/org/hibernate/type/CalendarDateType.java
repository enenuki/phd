/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.Calendar;
/*  4:   */ import org.hibernate.type.descriptor.java.CalendarDateTypeDescriptor;
/*  5:   */ import org.hibernate.type.descriptor.sql.DateTypeDescriptor;
/*  6:   */ 
/*  7:   */ public class CalendarDateType
/*  8:   */   extends AbstractSingleColumnStandardBasicType<Calendar>
/*  9:   */ {
/* 10:38 */   public static final CalendarDateType INSTANCE = new CalendarDateType();
/* 11:   */   
/* 12:   */   public CalendarDateType()
/* 13:   */   {
/* 14:41 */     super(DateTypeDescriptor.INSTANCE, CalendarDateTypeDescriptor.INSTANCE);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:45 */     return "calendar_date";
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CalendarDateType
 * JD-Core Version:    0.7.0.1
 */