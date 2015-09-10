/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import java.util.TimeZone;
/*  5:   */ import org.hibernate.type.descriptor.WrapperOptions;
/*  6:   */ 
/*  7:   */ public class TimeZoneTypeDescriptor
/*  8:   */   extends AbstractTypeDescriptor<TimeZone>
/*  9:   */ {
/* 10:37 */   public static final TimeZoneTypeDescriptor INSTANCE = new TimeZoneTypeDescriptor();
/* 11:   */   
/* 12:   */   public static class TimeZoneComparator
/* 13:   */     implements Comparator<TimeZone>
/* 14:   */   {
/* 15:40 */     public static final TimeZoneComparator INSTANCE = new TimeZoneComparator();
/* 16:   */     
/* 17:   */     public int compare(TimeZone o1, TimeZone o2)
/* 18:   */     {
/* 19:43 */       return o1.getID().compareTo(o2.getID());
/* 20:   */     }
/* 21:   */   }
/* 22:   */   
/* 23:   */   public TimeZoneTypeDescriptor()
/* 24:   */   {
/* 25:48 */     super(TimeZone.class);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String toString(TimeZone value)
/* 29:   */   {
/* 30:52 */     return value.getID();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TimeZone fromString(String string)
/* 34:   */   {
/* 35:56 */     return TimeZone.getTimeZone(string);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Comparator<TimeZone> getComparator()
/* 39:   */   {
/* 40:61 */     return TimeZoneComparator.INSTANCE;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public <X> X unwrap(TimeZone value, Class<X> type, WrapperOptions options)
/* 44:   */   {
/* 45:66 */     if (value == null) {
/* 46:67 */       return null;
/* 47:   */     }
/* 48:69 */     if (String.class.isAssignableFrom(type)) {
/* 49:70 */       return toString(value);
/* 50:   */     }
/* 51:72 */     throw unknownUnwrap(type);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public <X> TimeZone wrap(X value, WrapperOptions options)
/* 55:   */   {
/* 56:76 */     if (value == null) {
/* 57:77 */       return null;
/* 58:   */     }
/* 59:79 */     if (String.class.isInstance(value)) {
/* 60:80 */       return fromString((String)value);
/* 61:   */     }
/* 62:82 */     throw unknownWrap(value.getClass());
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.TimeZoneTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */