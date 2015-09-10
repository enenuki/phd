/*   1:    */ package org.hibernate.type.descriptor.java;
/*   2:    */ 
/*   3:    */ import java.sql.Time;
/*   4:    */ import java.sql.Timestamp;
/*   5:    */ import java.text.ParseException;
/*   6:    */ import java.text.SimpleDateFormat;
/*   7:    */ import java.util.Calendar;
/*   8:    */ import java.util.GregorianCalendar;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.type.descriptor.WrapperOptions;
/*  11:    */ 
/*  12:    */ public class DateTypeDescriptor
/*  13:    */   extends AbstractTypeDescriptor<java.util.Date>
/*  14:    */ {
/*  15: 41 */   public static final DateTypeDescriptor INSTANCE = new DateTypeDescriptor();
/*  16:    */   public static final String DATE_FORMAT = "dd MMMM yyyy";
/*  17:    */   
/*  18:    */   public static class DateMutabilityPlan
/*  19:    */     extends MutableMutabilityPlan<java.util.Date>
/*  20:    */   {
/*  21: 45 */     public static final DateMutabilityPlan INSTANCE = new DateMutabilityPlan();
/*  22:    */     
/*  23:    */     public java.util.Date deepCopyNotNull(java.util.Date value)
/*  24:    */     {
/*  25: 48 */       return new java.util.Date(value.getTime());
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public DateTypeDescriptor()
/*  30:    */   {
/*  31: 53 */     super(java.util.Date.class, DateMutabilityPlan.INSTANCE);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toString(java.util.Date value)
/*  35:    */   {
/*  36: 57 */     return new SimpleDateFormat("dd MMMM yyyy").format(value);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public java.util.Date fromString(String string)
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 62 */       return new SimpleDateFormat("dd MMMM yyyy").parse(string);
/*  44:    */     }
/*  45:    */     catch (ParseException pe)
/*  46:    */     {
/*  47: 65 */       throw new HibernateException("could not parse date string" + string, pe);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean areEqual(java.util.Date one, java.util.Date another)
/*  52:    */   {
/*  53: 71 */     if (one == another) {
/*  54: 72 */       return true;
/*  55:    */     }
/*  56: 74 */     if ((one == null) || (another == null)) {
/*  57: 75 */       return false;
/*  58:    */     }
/*  59: 78 */     return one.getTime() == another.getTime();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int extractHashCode(java.util.Date value)
/*  63:    */   {
/*  64: 83 */     Calendar calendar = Calendar.getInstance();
/*  65: 84 */     calendar.setTime(value);
/*  66: 85 */     return CalendarTypeDescriptor.INSTANCE.extractHashCode(calendar);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public <X> X unwrap(java.util.Date value, Class<X> type, WrapperOptions options)
/*  70:    */   {
/*  71: 90 */     if (value == null) {
/*  72: 91 */       return null;
/*  73:    */     }
/*  74: 93 */     if (java.sql.Date.class.isAssignableFrom(type))
/*  75:    */     {
/*  76: 94 */       java.sql.Date rtn = java.sql.Date.class.isInstance(value) ? (java.sql.Date)value : new java.sql.Date(value.getTime());
/*  77:    */       
/*  78:    */ 
/*  79: 97 */       return rtn;
/*  80:    */     }
/*  81: 99 */     if (Time.class.isAssignableFrom(type))
/*  82:    */     {
/*  83:100 */       Time rtn = Time.class.isInstance(value) ? (Time)value : new Time(value.getTime());
/*  84:    */       
/*  85:    */ 
/*  86:103 */       return rtn;
/*  87:    */     }
/*  88:105 */     if (Timestamp.class.isAssignableFrom(type))
/*  89:    */     {
/*  90:106 */       Timestamp rtn = Timestamp.class.isInstance(value) ? (Timestamp)value : new Timestamp(value.getTime());
/*  91:    */       
/*  92:    */ 
/*  93:109 */       return rtn;
/*  94:    */     }
/*  95:111 */     if (java.util.Date.class.isAssignableFrom(type)) {
/*  96:112 */       return value;
/*  97:    */     }
/*  98:114 */     if (Calendar.class.isAssignableFrom(type))
/*  99:    */     {
/* 100:115 */       GregorianCalendar cal = new GregorianCalendar();
/* 101:116 */       cal.setTimeInMillis(value.getTime());
/* 102:117 */       return cal;
/* 103:    */     }
/* 104:119 */     if (Long.class.isAssignableFrom(type)) {
/* 105:120 */       return Long.valueOf(value.getTime());
/* 106:    */     }
/* 107:122 */     throw unknownUnwrap(type);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public <X> java.util.Date wrap(X value, WrapperOptions options)
/* 111:    */   {
/* 112:127 */     if (value == null) {
/* 113:128 */       return null;
/* 114:    */     }
/* 115:130 */     if (java.util.Date.class.isInstance(value)) {
/* 116:131 */       return (java.util.Date)value;
/* 117:    */     }
/* 118:134 */     if (Long.class.isInstance(value)) {
/* 119:135 */       return new java.util.Date(((Long)value).longValue());
/* 120:    */     }
/* 121:138 */     if (Calendar.class.isInstance(value)) {
/* 122:139 */       return new java.util.Date(((Calendar)value).getTimeInMillis());
/* 123:    */     }
/* 124:142 */     throw unknownWrap(value.getClass());
/* 125:    */   }
/* 126:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.DateTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */