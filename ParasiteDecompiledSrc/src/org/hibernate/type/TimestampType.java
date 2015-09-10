/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.sql.Timestamp;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import java.util.Date;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.dialect.Dialect;
/*  8:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  9:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/* 10:   */ import org.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor;
/* 11:   */ import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;
/* 12:   */ 
/* 13:   */ public class TimestampType
/* 14:   */   extends AbstractSingleColumnStandardBasicType<Date>
/* 15:   */   implements VersionType<Date>, LiteralType<Date>
/* 16:   */ {
/* 17:46 */   public static final TimestampType INSTANCE = new TimestampType();
/* 18:   */   
/* 19:   */   public TimestampType()
/* 20:   */   {
/* 21:49 */     super(TimestampTypeDescriptor.INSTANCE, JdbcTimestampTypeDescriptor.INSTANCE);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getName()
/* 25:   */   {
/* 26:53 */     return "timestamp";
/* 27:   */   }
/* 28:   */   
/* 29:   */   public String[] getRegistrationKeys()
/* 30:   */   {
/* 31:58 */     return new String[] { getName(), Timestamp.class.getName(), Date.class.getName() };
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Date next(Date current, SessionImplementor session)
/* 35:   */   {
/* 36:62 */     return seed(session);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Date seed(SessionImplementor session)
/* 40:   */   {
/* 41:66 */     return new Timestamp(System.currentTimeMillis());
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Comparator<Date> getComparator()
/* 45:   */   {
/* 46:70 */     return getJavaTypeDescriptor().getComparator();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String objectToSQLString(Date value, Dialect dialect)
/* 50:   */     throws Exception
/* 51:   */   {
/* 52:74 */     Timestamp ts = Timestamp.class.isInstance(value) ? (Timestamp)value : new Timestamp(value.getTime());
/* 53:   */     
/* 54:   */ 
/* 55:   */ 
/* 56:78 */     return StringType.INSTANCE.objectToSQLString(ts.toString(), dialect);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public Date fromStringValue(String xml)
/* 60:   */     throws HibernateException
/* 61:   */   {
/* 62:82 */     return (Date)fromString(xml);
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.TimestampType
 * JD-Core Version:    0.7.0.1
 */