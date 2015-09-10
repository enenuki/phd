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
/*  12:    */ public class JdbcTimestampTypeDescriptor
/*  13:    */   extends AbstractTypeDescriptor<java.util.Date>
/*  14:    */ {
/*  15: 42 */   public static final JdbcTimestampTypeDescriptor INSTANCE = new JdbcTimestampTypeDescriptor();
/*  16:    */   public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
/*  17:    */   
/*  18:    */   public static class TimestampMutabilityPlan
/*  19:    */     extends MutableMutabilityPlan<java.util.Date>
/*  20:    */   {
/*  21: 46 */     public static final TimestampMutabilityPlan INSTANCE = new TimestampMutabilityPlan();
/*  22:    */     
/*  23:    */     public java.util.Date deepCopyNotNull(java.util.Date value)
/*  24:    */     {
/*  25: 49 */       if ((value instanceof Timestamp))
/*  26:    */       {
/*  27: 50 */         Timestamp orig = (Timestamp)value;
/*  28: 51 */         Timestamp ts = new Timestamp(orig.getTime());
/*  29: 52 */         ts.setNanos(orig.getNanos());
/*  30: 53 */         return ts;
/*  31:    */       }
/*  32: 56 */       java.util.Date orig = value;
/*  33: 57 */       return new java.util.Date(orig.getTime());
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public JdbcTimestampTypeDescriptor()
/*  38:    */   {
/*  39: 63 */     super(java.util.Date.class, TimestampMutabilityPlan.INSTANCE);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String toString(java.util.Date value)
/*  43:    */   {
/*  44: 67 */     return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public java.util.Date fromString(String string)
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 72 */       return new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string).getTime());
/*  52:    */     }
/*  53:    */     catch (ParseException pe)
/*  54:    */     {
/*  55: 75 */       throw new HibernateException("could not parse timestamp string" + string, pe);
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean areEqual(java.util.Date one, java.util.Date another)
/*  60:    */   {
/*  61: 81 */     if (one == another) {
/*  62: 82 */       return true;
/*  63:    */     }
/*  64: 84 */     if ((one == null) || (another == null)) {
/*  65: 85 */       return false;
/*  66:    */     }
/*  67: 88 */     long t1 = one.getTime();
/*  68: 89 */     long t2 = another.getTime();
/*  69:    */     
/*  70: 91 */     boolean oneIsTimestamp = Timestamp.class.isInstance(one);
/*  71: 92 */     boolean anotherIsTimestamp = Timestamp.class.isInstance(another);
/*  72:    */     
/*  73: 94 */     int n1 = oneIsTimestamp ? ((Timestamp)one).getNanos() : 0;
/*  74: 95 */     int n2 = anotherIsTimestamp ? ((Timestamp)another).getNanos() : 0;
/*  75: 97 */     if (t1 != t2) {
/*  76: 98 */       return false;
/*  77:    */     }
/*  78:101 */     if ((oneIsTimestamp) && (anotherIsTimestamp))
/*  79:    */     {
/*  80:103 */       int nn1 = n1 % 1000000;
/*  81:104 */       int nn2 = n2 % 1000000;
/*  82:105 */       return nn1 == nn2;
/*  83:    */     }
/*  84:109 */     return true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int extractHashCode(java.util.Date value)
/*  88:    */   {
/*  89:115 */     return Long.valueOf(value.getTime() / 1000L).hashCode();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public <X> X unwrap(java.util.Date value, Class<X> type, WrapperOptions options)
/*  93:    */   {
/*  94:120 */     if (value == null) {
/*  95:121 */       return null;
/*  96:    */     }
/*  97:123 */     if (Timestamp.class.isAssignableFrom(type))
/*  98:    */     {
/*  99:124 */       Timestamp rtn = Timestamp.class.isInstance(value) ? (Timestamp)value : new Timestamp(value.getTime());
/* 100:    */       
/* 101:    */ 
/* 102:127 */       return rtn;
/* 103:    */     }
/* 104:129 */     if (java.sql.Date.class.isAssignableFrom(type))
/* 105:    */     {
/* 106:130 */       java.sql.Date rtn = java.sql.Date.class.isInstance(value) ? (java.sql.Date)value : new java.sql.Date(value.getTime());
/* 107:    */       
/* 108:    */ 
/* 109:133 */       return rtn;
/* 110:    */     }
/* 111:135 */     if (Time.class.isAssignableFrom(type))
/* 112:    */     {
/* 113:136 */       Time rtn = Time.class.isInstance(value) ? (Time)value : new Time(value.getTime());
/* 114:    */       
/* 115:    */ 
/* 116:139 */       return rtn;
/* 117:    */     }
/* 118:141 */     if (java.util.Date.class.isAssignableFrom(type)) {
/* 119:142 */       return value;
/* 120:    */     }
/* 121:144 */     if (Calendar.class.isAssignableFrom(type))
/* 122:    */     {
/* 123:145 */       GregorianCalendar cal = new GregorianCalendar();
/* 124:146 */       cal.setTimeInMillis(value.getTime());
/* 125:147 */       return cal;
/* 126:    */     }
/* 127:149 */     if (Long.class.isAssignableFrom(type)) {
/* 128:150 */       return Long.valueOf(value.getTime());
/* 129:    */     }
/* 130:152 */     throw unknownUnwrap(type);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public <X> java.util.Date wrap(X value, WrapperOptions options)
/* 134:    */   {
/* 135:157 */     if (value == null) {
/* 136:158 */       return null;
/* 137:    */     }
/* 138:160 */     if (Timestamp.class.isInstance(value)) {
/* 139:161 */       return (Timestamp)value;
/* 140:    */     }
/* 141:164 */     if (Long.class.isInstance(value)) {
/* 142:165 */       return new Timestamp(((Long)value).longValue());
/* 143:    */     }
/* 144:168 */     if (Calendar.class.isInstance(value)) {
/* 145:169 */       return new Timestamp(((Calendar)value).getTimeInMillis());
/* 146:    */     }
/* 147:172 */     if (java.util.Date.class.isInstance(value)) {
/* 148:173 */       return (java.util.Date)value;
/* 149:    */     }
/* 150:176 */     throw unknownWrap(value.getClass());
/* 151:    */   }
/* 152:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor
 * JD-Core Version:    0.7.0.1
 */