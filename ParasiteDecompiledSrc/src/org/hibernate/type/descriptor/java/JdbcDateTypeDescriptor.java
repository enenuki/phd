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
/*  12:    */ public class JdbcDateTypeDescriptor
/*  13:    */   extends AbstractTypeDescriptor<java.util.Date>
/*  14:    */ {
/*  15: 41 */   public static final JdbcDateTypeDescriptor INSTANCE = new JdbcDateTypeDescriptor();
/*  16:    */   public static final String DATE_FORMAT = "dd MMMM yyyy";
/*  17:    */   
/*  18:    */   public static class DateMutabilityPlan
/*  19:    */     extends MutableMutabilityPlan<java.util.Date>
/*  20:    */   {
/*  21: 45 */     public static final DateMutabilityPlan INSTANCE = new DateMutabilityPlan();
/*  22:    */     
/*  23:    */     public java.util.Date deepCopyNotNull(java.util.Date value)
/*  24:    */     {
/*  25: 48 */       return java.sql.Date.class.isInstance(value) ? new java.sql.Date(value.getTime()) : new java.util.Date(value.getTime());
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JdbcDateTypeDescriptor()
/*  30:    */   {
/*  31: 55 */     super(java.util.Date.class, DateMutabilityPlan.INSTANCE);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toString(java.util.Date value)
/*  35:    */   {
/*  36: 59 */     return new SimpleDateFormat("dd MMMM yyyy").format(value);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public java.util.Date fromString(String string)
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 64 */       return new java.util.Date(new SimpleDateFormat("dd MMMM yyyy").parse(string).getTime());
/*  44:    */     }
/*  45:    */     catch (ParseException pe)
/*  46:    */     {
/*  47: 67 */       throw new HibernateException("could not parse date string" + string, pe);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public boolean areEqual(java.util.Date one, java.util.Date another)
/*  52:    */   {
/*  53: 73 */     if (one == another) {
/*  54: 74 */       return true;
/*  55:    */     }
/*  56: 76 */     if ((one == null) || (another == null)) {
/*  57: 77 */       return false;
/*  58:    */     }
/*  59: 80 */     if (one.getTime() == another.getTime()) {
/*  60: 81 */       return true;
/*  61:    */     }
/*  62: 84 */     Calendar calendar1 = Calendar.getInstance();
/*  63: 85 */     Calendar calendar2 = Calendar.getInstance();
/*  64: 86 */     calendar1.setTime(one);
/*  65: 87 */     calendar2.setTime(another);
/*  66:    */     
/*  67: 89 */     return (calendar1.get(2) == calendar2.get(2)) && (calendar1.get(5) == calendar2.get(5)) && (calendar1.get(1) == calendar2.get(1));
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int extractHashCode(java.util.Date value)
/*  71:    */   {
/*  72: 96 */     Calendar calendar = Calendar.getInstance();
/*  73: 97 */     calendar.setTime(value);
/*  74: 98 */     int hashCode = 1;
/*  75: 99 */     hashCode = 31 * hashCode + calendar.get(2);
/*  76:100 */     hashCode = 31 * hashCode + calendar.get(5);
/*  77:101 */     hashCode = 31 * hashCode + calendar.get(1);
/*  78:102 */     return hashCode;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public <X> X unwrap(java.util.Date value, Class<X> type, WrapperOptions options)
/*  82:    */   {
/*  83:107 */     if (value == null) {
/*  84:108 */       return null;
/*  85:    */     }
/*  86:110 */     if (java.sql.Date.class.isAssignableFrom(type))
/*  87:    */     {
/*  88:111 */       java.sql.Date rtn = java.sql.Date.class.isInstance(value) ? (java.sql.Date)value : new java.sql.Date(value.getTime());
/*  89:    */       
/*  90:    */ 
/*  91:114 */       return rtn;
/*  92:    */     }
/*  93:116 */     if (Time.class.isAssignableFrom(type))
/*  94:    */     {
/*  95:117 */       Time rtn = Time.class.isInstance(value) ? (Time)value : new Time(value.getTime());
/*  96:    */       
/*  97:    */ 
/*  98:120 */       return rtn;
/*  99:    */     }
/* 100:122 */     if (Timestamp.class.isAssignableFrom(type))
/* 101:    */     {
/* 102:123 */       Timestamp rtn = Timestamp.class.isInstance(value) ? (Timestamp)value : new Timestamp(value.getTime());
/* 103:    */       
/* 104:    */ 
/* 105:126 */       return rtn;
/* 106:    */     }
/* 107:128 */     if (java.util.Date.class.isAssignableFrom(type)) {
/* 108:129 */       return value;
/* 109:    */     }
/* 110:131 */     if (Calendar.class.isAssignableFrom(type))
/* 111:    */     {
/* 112:132 */       GregorianCalendar cal = new GregorianCalendar();
/* 113:133 */       cal.setTimeInMillis(value.getTime());
/* 114:134 */       return cal;
/* 115:    */     }
/* 116:136 */     if (Long.class.isAssignableFrom(type)) {
/* 117:137 */       return Long.valueOf(value.getTime());
/* 118:    */     }
/* 119:139 */     throw unknownUnwrap(type);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public <X> java.util.Date wrap(X value, WrapperOptions options)
/* 123:    */   {
/* 124:144 */     if (value == null) {
/* 125:145 */       return null;
/* 126:    */     }
/* 127:147 */     if (java.util.Date.class.isInstance(value)) {
/* 128:148 */       return (java.util.Date)value;
/* 129:    */     }
/* 130:151 */     if (Long.class.isInstance(value)) {
/* 131:152 */       return new java.sql.Date(((Long)value).longValue());
/* 132:    */     }
/* 133:155 */     if (Calendar.class.isInstance(value)) {
/* 134:156 */       return new java.sql.Date(((Calendar)value).getTimeInMillis());
/* 135:    */     }
/* 136:159 */     if (java.util.Date.class.isInstance(value)) {
/* 137:160 */       return new java.sql.Date(((java.util.Date)value).getTime());
/* 138:    */     }
/* 139:163 */     throw unknownWrap(value.getClass());
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */