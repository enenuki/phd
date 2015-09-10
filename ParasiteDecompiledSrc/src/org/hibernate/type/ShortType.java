/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  8:   */ import org.hibernate.type.descriptor.java.ShortTypeDescriptor;
/*  9:   */ import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;
/* 10:   */ 
/* 11:   */ public class ShortType
/* 12:   */   extends AbstractSingleColumnStandardBasicType<Short>
/* 13:   */   implements PrimitiveType<Short>, DiscriminatorType<Short>, VersionType<Short>
/* 14:   */ {
/* 15:44 */   public static final ShortType INSTANCE = new ShortType();
/* 16:47 */   private static final Short ZERO = Short.valueOf((short)0);
/* 17:   */   
/* 18:   */   public ShortType()
/* 19:   */   {
/* 20:50 */     super(SmallIntTypeDescriptor.INSTANCE, ShortTypeDescriptor.INSTANCE);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:54 */     return "short";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String[] getRegistrationKeys()
/* 29:   */   {
/* 30:59 */     return new String[] { getName(), Short.TYPE.getName(), Short.class.getName() };
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Serializable getDefaultValue()
/* 34:   */   {
/* 35:63 */     return ZERO;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Class getPrimitiveClass()
/* 39:   */   {
/* 40:67 */     return Short.TYPE;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String objectToSQLString(Short value, Dialect dialect)
/* 44:   */     throws Exception
/* 45:   */   {
/* 46:71 */     return value.toString();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Short stringToObject(String xml)
/* 50:   */     throws Exception
/* 51:   */   {
/* 52:75 */     return Short.valueOf(xml);
/* 53:   */   }
/* 54:   */   
/* 55:   */   public Short next(Short current, SessionImplementor session)
/* 56:   */   {
/* 57:80 */     return Short.valueOf((short)(current.shortValue() + 1));
/* 58:   */   }
/* 59:   */   
/* 60:   */   public Short seed(SessionImplementor session)
/* 61:   */   {
/* 62:84 */     return ZERO;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public Comparator<Short> getComparator()
/* 66:   */   {
/* 67:88 */     return getJavaTypeDescriptor().getComparator();
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ShortType
 * JD-Core Version:    0.7.0.1
 */