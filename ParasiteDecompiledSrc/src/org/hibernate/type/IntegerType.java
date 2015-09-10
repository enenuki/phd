/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  8:   */ 
/*  9:   */ public class IntegerType
/* 10:   */   extends AbstractSingleColumnStandardBasicType<Integer>
/* 11:   */   implements PrimitiveType<Integer>, DiscriminatorType<Integer>, VersionType<Integer>
/* 12:   */ {
/* 13:42 */   public static final IntegerType INSTANCE = new IntegerType();
/* 14:45 */   public static final Integer ZERO = Integer.valueOf(0);
/* 15:   */   
/* 16:   */   public IntegerType()
/* 17:   */   {
/* 18:48 */     super(org.hibernate.type.descriptor.sql.IntegerTypeDescriptor.INSTANCE, org.hibernate.type.descriptor.java.IntegerTypeDescriptor.INSTANCE);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getName()
/* 22:   */   {
/* 23:52 */     return "integer";
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String[] getRegistrationKeys()
/* 27:   */   {
/* 28:57 */     return new String[] { getName(), Integer.TYPE.getName(), Integer.class.getName() };
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Serializable getDefaultValue()
/* 32:   */   {
/* 33:61 */     return ZERO;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Class getPrimitiveClass()
/* 37:   */   {
/* 38:65 */     return Integer.TYPE;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String objectToSQLString(Integer value, Dialect dialect)
/* 42:   */     throws Exception
/* 43:   */   {
/* 44:69 */     return toString(value);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Integer stringToObject(String xml)
/* 48:   */   {
/* 49:73 */     return (Integer)fromString(xml);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Integer seed(SessionImplementor session)
/* 53:   */   {
/* 54:77 */     return ZERO;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Integer next(Integer current, SessionImplementor session)
/* 58:   */   {
/* 59:82 */     return Integer.valueOf(current.intValue() + 1);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public Comparator<Integer> getComparator()
/* 63:   */   {
/* 64:86 */     return getJavaTypeDescriptor().getComparator();
/* 65:   */   }
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.IntegerType
 * JD-Core Version:    0.7.0.1
 */