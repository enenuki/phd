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
/*  12:    */ public class JdbcTimeTypeDescriptor
/*  13:    */   extends AbstractTypeDescriptor<java.util.Date>
/*  14:    */ {
/*  15: 42 */   public static final JdbcTimeTypeDescriptor INSTANCE = new JdbcTimeTypeDescriptor();
/*  16:    */   public static final String TIME_FORMAT = "HH:mm:ss";
/*  17:    */   
/*  18:    */   public static class TimeMutabilityPlan
/*  19:    */     extends MutableMutabilityPlan<java.util.Date>
/*  20:    */   {
/*  21: 46 */     public static final TimeMutabilityPlan INSTANCE = new TimeMutabilityPlan();
/*  22:    */     
/*  23:    */     public java.util.Date deepCopyNotNull(java.util.Date value)
/*  24:    */     {
/*  25: 49 */       return Time.class.isInstance(value) ? new Time(value.getTime()) : new java.util.Date(value.getTime());
/*  26:    */     }
/*  27:    */   }
/*  28:    */   
/*  29:    */   public JdbcTimeTypeDescriptor()
/*  30:    */   {
/*  31: 56 */     super(java.util.Date.class, TimeMutabilityPlan.INSTANCE);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toString(java.util.Date value)
/*  35:    */   {
/*  36: 60 */     return new SimpleDateFormat("HH:mm:ss").format(value);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public java.util.Date fromString(String string)
/*  40:    */   {
/*  41:    */     try
/*  42:    */     {
/*  43: 65 */       return new Time(new SimpleDateFormat("HH:mm:ss").parse(string).getTime());
/*  44:    */     }
/*  45:    */     catch (ParseException pe)
/*  46:    */     {
/*  47: 68 */       throw new HibernateException("could not parse time string" + string, pe);
/*  48:    */     }
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int extractHashCode(java.util.Date value)
/*  52:    */   {
/*  53: 74 */     Calendar calendar = Calendar.getInstance();
/*  54: 75 */     calendar.setTime(value);
/*  55: 76 */     int hashCode = 1;
/*  56: 77 */     hashCode = 31 * hashCode + calendar.get(11);
/*  57: 78 */     hashCode = 31 * hashCode + calendar.get(12);
/*  58: 79 */     hashCode = 31 * hashCode + calendar.get(13);
/*  59: 80 */     hashCode = 31 * hashCode + calendar.get(14);
/*  60: 81 */     return hashCode;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean areEqual(java.util.Date one, java.util.Date another)
/*  64:    */   {
/*  65: 86 */     if (one == another) {
/*  66: 87 */       return true;
/*  67:    */     }
/*  68: 89 */     if ((one == null) || (another == null)) {
/*  69: 90 */       return false;
/*  70:    */     }
/*  71: 93 */     if (one.getTime() == another.getTime()) {
/*  72: 94 */       return true;
/*  73:    */     }
/*  74: 97 */     Calendar calendar1 = Calendar.getInstance();
/*  75: 98 */     Calendar calendar2 = Calendar.getInstance();
/*  76: 99 */     calendar1.setTime(one);
/*  77:100 */     calendar2.setTime(another);
/*  78:    */     
/*  79:102 */     return (calendar1.get(11) == calendar2.get(11)) && (calendar1.get(12) == calendar2.get(12)) && (calendar1.get(13) == calendar2.get(13)) && (calendar1.get(14) == calendar2.get(14));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public <X> X unwrap(java.util.Date value, Class<X> type, WrapperOptions options)
/*  83:    */   {
/*  84:110 */     if (value == null) {
/*  85:111 */       return null;
/*  86:    */     }
/*  87:113 */     if (Time.class.isAssignableFrom(type))
/*  88:    */     {
/*  89:114 */       Time rtn = Time.class.isInstance(value) ? (Time)value : new Time(value.getTime());
/*  90:    */       
/*  91:    */ 
/*  92:117 */       return rtn;
/*  93:    */     }
/*  94:119 */     if (java.sql.Date.class.isAssignableFrom(type))
/*  95:    */     {
/*  96:120 */       java.sql.Date rtn = java.sql.Date.class.isInstance(value) ? (java.sql.Date)value : new java.sql.Date(value.getTime());
/*  97:    */       
/*  98:    */ 
/*  99:123 */       return rtn;
/* 100:    */     }
/* 101:125 */     if (Timestamp.class.isAssignableFrom(type))
/* 102:    */     {
/* 103:126 */       Timestamp rtn = Timestamp.class.isInstance(value) ? (Timestamp)value : new Timestamp(value.getTime());
/* 104:    */       
/* 105:    */ 
/* 106:129 */       return rtn;
/* 107:    */     }
/* 108:131 */     if (java.util.Date.class.isAssignableFrom(type)) {
/* 109:132 */       return value;
/* 110:    */     }
/* 111:134 */     if (Calendar.class.isAssignableFrom(type))
/* 112:    */     {
/* 113:135 */       GregorianCalendar cal = new GregorianCalendar();
/* 114:136 */       cal.setTimeInMillis(value.getTime());
/* 115:137 */       return cal;
/* 116:    */     }
/* 117:139 */     if (Long.class.isAssignableFrom(type)) {
/* 118:140 */       return Long.valueOf(value.getTime());
/* 119:    */     }
/* 120:142 */     throw unknownUnwrap(type);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public <X> java.util.Date wrap(X value, WrapperOptions options)
/* 124:    */   {
/* 125:147 */     if (value == null) {
/* 126:148 */       return null;
/* 127:    */     }
/* 128:150 */     if (Time.class.isInstance(value)) {
/* 129:151 */       return (Time)value;
/* 130:    */     }
/* 131:154 */     if (Long.class.isInstance(value)) {
/* 132:155 */       return new Time(((Long)value).longValue());
/* 133:    */     }
/* 134:158 */     if (Calendar.class.isInstance(value)) {
/* 135:159 */       return new Time(((Calendar)value).getTimeInMillis());
/* 136:    */     }
/* 137:162 */     if (java.util.Date.class.isInstance(value)) {
/* 138:163 */       return (java.util.Date)value;
/* 139:    */     }
/* 140:166 */     throw unknownWrap(value.getClass());
/* 141:    */   }
/* 142:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.JdbcTimeTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */