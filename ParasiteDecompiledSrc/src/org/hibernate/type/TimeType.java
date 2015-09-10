/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.sql.Time;
/*  4:   */ import java.util.Date;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.type.descriptor.java.JdbcTimeTypeDescriptor;
/*  7:   */ import org.hibernate.type.descriptor.sql.TimeTypeDescriptor;
/*  8:   */ 
/*  9:   */ public class TimeType
/* 10:   */   extends AbstractSingleColumnStandardBasicType<Date>
/* 11:   */   implements LiteralType<Date>
/* 12:   */ {
/* 13:42 */   public static final TimeType INSTANCE = new TimeType();
/* 14:   */   
/* 15:   */   public TimeType()
/* 16:   */   {
/* 17:45 */     super(TimeTypeDescriptor.INSTANCE, JdbcTimeTypeDescriptor.INSTANCE);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getName()
/* 21:   */   {
/* 22:49 */     return "time";
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String[] getRegistrationKeys()
/* 26:   */   {
/* 27:54 */     return new String[] { getName(), Time.class.getName() };
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String objectToSQLString(Date value, Dialect dialect)
/* 31:   */     throws Exception
/* 32:   */   {
/* 33:66 */     Time jdbcTime = Time.class.isInstance(value) ? (Time)value : new Time(value.getTime());
/* 34:   */     
/* 35:   */ 
/* 36:   */ 
/* 37:70 */     return StringType.INSTANCE.objectToSQLString(jdbcTime.toString(), dialect);
/* 38:   */   }
/* 39:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.TimeType
 * JD-Core Version:    0.7.0.1
 */