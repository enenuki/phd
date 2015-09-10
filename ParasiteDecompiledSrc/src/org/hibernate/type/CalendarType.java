/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.Calendar;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import java.util.GregorianCalendar;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.descriptor.java.CalendarTypeDescriptor;
/*  8:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  9:   */ import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;
/* 10:   */ 
/* 11:   */ public class CalendarType
/* 12:   */   extends AbstractSingleColumnStandardBasicType<Calendar>
/* 13:   */   implements VersionType<Calendar>
/* 14:   */ {
/* 15:44 */   public static final CalendarType INSTANCE = new CalendarType();
/* 16:   */   
/* 17:   */   public CalendarType()
/* 18:   */   {
/* 19:47 */     super(TimestampTypeDescriptor.INSTANCE, CalendarTypeDescriptor.INSTANCE);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getName()
/* 23:   */   {
/* 24:51 */     return "calendar";
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String[] getRegistrationKeys()
/* 28:   */   {
/* 29:56 */     return new String[] { getName(), Calendar.class.getName(), GregorianCalendar.class.getName() };
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Calendar next(Calendar current, SessionImplementor session)
/* 33:   */   {
/* 34:60 */     return seed(session);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Calendar seed(SessionImplementor session)
/* 38:   */   {
/* 39:64 */     return Calendar.getInstance();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Comparator<Calendar> getComparator()
/* 43:   */   {
/* 44:68 */     return getJavaTypeDescriptor().getComparator();
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.CalendarType
 * JD-Core Version:    0.7.0.1
 */