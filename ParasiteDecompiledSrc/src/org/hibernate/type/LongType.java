/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  8:   */ import org.hibernate.type.descriptor.java.LongTypeDescriptor;
/*  9:   */ import org.hibernate.type.descriptor.sql.BigIntTypeDescriptor;
/* 10:   */ 
/* 11:   */ public class LongType
/* 12:   */   extends AbstractSingleColumnStandardBasicType<Long>
/* 13:   */   implements PrimitiveType<Long>, DiscriminatorType<Long>, VersionType<Long>
/* 14:   */ {
/* 15:44 */   public static final LongType INSTANCE = new LongType();
/* 16:47 */   private static final Long ZERO = Long.valueOf(0L);
/* 17:   */   
/* 18:   */   public LongType()
/* 19:   */   {
/* 20:50 */     super(BigIntTypeDescriptor.INSTANCE, LongTypeDescriptor.INSTANCE);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:54 */     return "long";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String[] getRegistrationKeys()
/* 29:   */   {
/* 30:59 */     return new String[] { getName(), Long.TYPE.getName(), Long.class.getName() };
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Serializable getDefaultValue()
/* 34:   */   {
/* 35:63 */     return ZERO;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Class getPrimitiveClass()
/* 39:   */   {
/* 40:67 */     return Long.TYPE;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Long stringToObject(String xml)
/* 44:   */     throws Exception
/* 45:   */   {
/* 46:71 */     return Long.valueOf(xml);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Long next(Long current, SessionImplementor session)
/* 50:   */   {
/* 51:76 */     return Long.valueOf(current.longValue() + 1L);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public Long seed(SessionImplementor session)
/* 55:   */   {
/* 56:80 */     return ZERO;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public Comparator<Long> getComparator()
/* 60:   */   {
/* 61:84 */     return getJavaTypeDescriptor().getComparator();
/* 62:   */   }
/* 63:   */   
/* 64:   */   public String objectToSQLString(Long value, Dialect dialect)
/* 65:   */     throws Exception
/* 66:   */   {
/* 67:88 */     return value.toString();
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.LongType
 * JD-Core Version:    0.7.0.1
 */