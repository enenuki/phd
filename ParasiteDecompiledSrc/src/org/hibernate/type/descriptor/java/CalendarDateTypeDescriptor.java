/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.sql.Time;
/*   4:    */ import java.sql.Timestamp;
/*   5:    */ import java.util.Calendar;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.GregorianCalendar;
/*   8:    */ import org.hibernate.cfg.Environment;
/*   9:    */ import org.hibernate.internal.util.compare.CalendarComparator;
/*  10:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  11:    */ 
/*  12:    */ public class CalendarDateTypeDescriptor
/*  13:    */   extends AbstractTypeDescriptor<Calendar>
/*  14:    */ {
/*  15: 41 */   public static final CalendarDateTypeDescriptor INSTANCE = new CalendarDateTypeDescriptor();
/*  16:    */   
/*  17:    */   protected CalendarDateTypeDescriptor()
/*  18:    */   {
/*  19: 44 */     super(Calendar.class, CalendarTypeDescriptor.CalendarMutabilityPlan.INSTANCE);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String toString(Calendar value)
/*  23:    */   {
/*  24: 48 */     return DateTypeDescriptor.INSTANCE.toString(value.getTime());
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Calendar fromString(String string)
/*  28:    */   {
/*  29: 52 */     Calendar result = new GregorianCalendar();
/*  30: 53 */     result.setTime(DateTypeDescriptor.INSTANCE.fromString(string));
/*  31: 54 */     return result;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean areEqual(Calendar one, Calendar another)
/*  35:    */   {
/*  36: 59 */     if (one == another) {
/*  37: 60 */       return true;
/*  38:    */     }
/*  39: 62 */     if ((one == null) || (another == null)) {
/*  40: 63 */       return false;
/*  41:    */     }
/*  42: 66 */     return (one.get(5) == another.get(5)) && (one.get(2) == another.get(2)) && (one.get(1) == another.get(1));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public int extractHashCode(Calendar value)
/*  46:    */   {
/*  47: 73 */     int hashCode = 1;
/*  48: 74 */     hashCode = 31 * hashCode + value.get(5);
/*  49: 75 */     hashCode = 31 * hashCode + value.get(2);
/*  50: 76 */     hashCode = 31 * hashCode + value.get(1);
/*  51: 77 */     return hashCode;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Comparator<Calendar> getComparator()
/*  55:    */   {
/*  56: 82 */     return CalendarComparator.INSTANCE;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public <X> X unwrap(Calendar value, Class<X> type, WrapperOptions options)
/*  60:    */   {
/*  61: 87 */     if (value == null) {
/*  62: 88 */       return null;
/*  63:    */     }
/*  64: 90 */     if (Calendar.class.isAssignableFrom(type)) {
/*  65: 91 */       return value;
/*  66:    */     }
/*  67: 93 */     if (java.sql.Date.class.isAssignableFrom(type)) {
/*  68: 94 */       return new java.sql.Date(value.getTimeInMillis());
/*  69:    */     }
/*  70: 96 */     if (Time.class.isAssignableFrom(type)) {
/*  71: 97 */       return new Time(value.getTimeInMillis());
/*  72:    */     }
/*  73: 99 */     if (Timestamp.class.isAssignableFrom(type)) {
/*  74:100 */       return new Timestamp(value.getTimeInMillis());
/*  75:    */     }
/*  76:102 */     if (java.util.Date.class.isAssignableFrom(type)) {
/*  77:103 */       return new java.util.Date(value.getTimeInMillis());
/*  78:    */     }
/*  79:105 */     throw unknownUnwrap(type);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public <X> Calendar wrap(X value, WrapperOptions options)
/*  83:    */   {
/*  84:109 */     if (value == null) {
/*  85:110 */       return null;
/*  86:    */     }
/*  87:112 */     if (Calendar.class.isInstance(value)) {
/*  88:113 */       return (Calendar)value;
/*  89:    */     }
/*  90:116 */     if (!java.util.Date.class.isInstance(value)) {
/*  91:117 */       throw unknownWrap(value.getClass());
/*  92:    */     }
/*  93:120 */     Calendar cal = new GregorianCalendar();
/*  94:121 */     if (Environment.jvmHasTimestampBug())
/*  95:    */     {
/*  96:122 */       long milliseconds = ((java.util.Date)value).getTime();
/*  97:123 */       long nanoseconds = Timestamp.class.isInstance(value) ? ((Timestamp)value).getNanos() : 0L;
/*  98:    */       
/*  99:    */ 
/* 100:126 */       cal.setTime(new java.util.Date(milliseconds + nanoseconds / 1000000L));
/* 101:    */     }
/* 102:    */     else
/* 103:    */     {
/* 104:129 */       cal.setTime((java.util.Date)value);
/* 105:    */     }
/* 106:131 */     return cal;
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.CalendarDateTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */