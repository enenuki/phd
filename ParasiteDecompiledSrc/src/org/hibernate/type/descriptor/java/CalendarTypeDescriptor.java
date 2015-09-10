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
/*  12:    */ public class CalendarTypeDescriptor
/*  13:    */   extends AbstractTypeDescriptor<Calendar>
/*  14:    */ {
/*  15: 41 */   public static final CalendarTypeDescriptor INSTANCE = new CalendarTypeDescriptor();
/*  16:    */   
/*  17:    */   public static class CalendarMutabilityPlan
/*  18:    */     extends MutableMutabilityPlan<Calendar>
/*  19:    */   {
/*  20: 44 */     public static final CalendarMutabilityPlan INSTANCE = new CalendarMutabilityPlan();
/*  21:    */     
/*  22:    */     public Calendar deepCopyNotNull(Calendar value)
/*  23:    */     {
/*  24: 47 */       return (Calendar)value.clone();
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   protected CalendarTypeDescriptor()
/*  29:    */   {
/*  30: 52 */     super(Calendar.class, CalendarMutabilityPlan.INSTANCE);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String toString(Calendar value)
/*  34:    */   {
/*  35: 56 */     return DateTypeDescriptor.INSTANCE.toString(value.getTime());
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Calendar fromString(String string)
/*  39:    */   {
/*  40: 60 */     Calendar result = new GregorianCalendar();
/*  41: 61 */     result.setTime(DateTypeDescriptor.INSTANCE.fromString(string));
/*  42: 62 */     return result;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean areEqual(Calendar one, Calendar another)
/*  46:    */   {
/*  47: 67 */     if (one == another) {
/*  48: 68 */       return true;
/*  49:    */     }
/*  50: 70 */     if ((one == null) || (another == null)) {
/*  51: 71 */       return false;
/*  52:    */     }
/*  53: 74 */     return (one.get(14) == another.get(14)) && (one.get(13) == another.get(13)) && (one.get(12) == another.get(12)) && (one.get(11) == another.get(11)) && (one.get(5) == another.get(5)) && (one.get(2) == another.get(2)) && (one.get(1) == another.get(1));
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int extractHashCode(Calendar value)
/*  57:    */   {
/*  58: 85 */     int hashCode = 1;
/*  59: 86 */     hashCode = 31 * hashCode + value.get(14);
/*  60: 87 */     hashCode = 31 * hashCode + value.get(13);
/*  61: 88 */     hashCode = 31 * hashCode + value.get(12);
/*  62: 89 */     hashCode = 31 * hashCode + value.get(11);
/*  63: 90 */     hashCode = 31 * hashCode + value.get(5);
/*  64: 91 */     hashCode = 31 * hashCode + value.get(2);
/*  65: 92 */     hashCode = 31 * hashCode + value.get(1);
/*  66: 93 */     return hashCode;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Comparator<Calendar> getComparator()
/*  70:    */   {
/*  71: 98 */     return CalendarComparator.INSTANCE;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public <X> X unwrap(Calendar value, Class<X> type, WrapperOptions options)
/*  75:    */   {
/*  76:103 */     if (value == null) {
/*  77:104 */       return null;
/*  78:    */     }
/*  79:106 */     if (Calendar.class.isAssignableFrom(type)) {
/*  80:107 */       return value;
/*  81:    */     }
/*  82:109 */     if (java.sql.Date.class.isAssignableFrom(type)) {
/*  83:110 */       return new java.sql.Date(value.getTimeInMillis());
/*  84:    */     }
/*  85:112 */     if (Time.class.isAssignableFrom(type)) {
/*  86:113 */       return new Time(value.getTimeInMillis());
/*  87:    */     }
/*  88:115 */     if (Timestamp.class.isAssignableFrom(type)) {
/*  89:116 */       return new Timestamp(value.getTimeInMillis());
/*  90:    */     }
/*  91:118 */     if (java.util.Date.class.isAssignableFrom(type)) {
/*  92:119 */       return new java.util.Date(value.getTimeInMillis());
/*  93:    */     }
/*  94:121 */     throw unknownUnwrap(type);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public <X> Calendar wrap(X value, WrapperOptions options)
/*  98:    */   {
/*  99:125 */     if (value == null) {
/* 100:126 */       return null;
/* 101:    */     }
/* 102:128 */     if (Calendar.class.isInstance(value)) {
/* 103:129 */       return (Calendar)value;
/* 104:    */     }
/* 105:132 */     if (!java.util.Date.class.isInstance(value)) {
/* 106:133 */       throw unknownWrap(value.getClass());
/* 107:    */     }
/* 108:136 */     Calendar cal = new GregorianCalendar();
/* 109:137 */     if (Environment.jvmHasTimestampBug())
/* 110:    */     {
/* 111:138 */       long milliseconds = ((java.util.Date)value).getTime();
/* 112:139 */       long nanoseconds = Timestamp.class.isInstance(value) ? ((Timestamp)value).getNanos() : 0L;
/* 113:    */       
/* 114:    */ 
/* 115:142 */       cal.setTime(new java.util.Date(milliseconds + nanoseconds / 1000000L));
/* 116:    */     }
/* 117:    */     else
/* 118:    */     {
/* 119:145 */       cal.setTime((java.util.Date)value);
/* 120:    */     }
/* 121:147 */     return cal;
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.CalendarTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */