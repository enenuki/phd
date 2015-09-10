/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ import org.hibernate.dialect.Dialect;
/*  6:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  7:   */ import org.hibernate.type.descriptor.java.ByteTypeDescriptor;
/*  8:   */ import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
/*  9:   */ import org.hibernate.type.descriptor.sql.TinyIntTypeDescriptor;
/* 10:   */ 
/* 11:   */ public class ByteType
/* 12:   */   extends AbstractSingleColumnStandardBasicType<Byte>
/* 13:   */   implements PrimitiveType<Byte>, DiscriminatorType<Byte>, VersionType<Byte>
/* 14:   */ {
/* 15:45 */   public static final ByteType INSTANCE = new ByteType();
/* 16:47 */   private static final Byte ZERO = Byte.valueOf((byte)0);
/* 17:   */   
/* 18:   */   public ByteType()
/* 19:   */   {
/* 20:50 */     super(TinyIntTypeDescriptor.INSTANCE, ByteTypeDescriptor.INSTANCE);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getName()
/* 24:   */   {
/* 25:54 */     return "byte";
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String[] getRegistrationKeys()
/* 29:   */   {
/* 30:59 */     return new String[] { getName(), Byte.TYPE.getName(), Byte.class.getName() };
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Serializable getDefaultValue()
/* 34:   */   {
/* 35:63 */     return ZERO;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Class getPrimitiveClass()
/* 39:   */   {
/* 40:67 */     return Byte.TYPE;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String objectToSQLString(Byte value, Dialect dialect)
/* 44:   */   {
/* 45:71 */     return toString(value);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Byte stringToObject(String xml)
/* 49:   */   {
/* 50:75 */     return (Byte)fromString(xml);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Byte fromStringValue(String xml)
/* 54:   */   {
/* 55:79 */     return (Byte)fromString(xml);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Byte next(Byte current, SessionImplementor session)
/* 59:   */   {
/* 60:84 */     return Byte.valueOf((byte)(current.byteValue() + 1));
/* 61:   */   }
/* 62:   */   
/* 63:   */   public Byte seed(SessionImplementor session)
/* 64:   */   {
/* 65:88 */     return ZERO;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public Comparator<Byte> getComparator()
/* 69:   */   {
/* 70:92 */     return getJavaTypeDescriptor().getComparator();
/* 71:   */   }
/* 72:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ByteType
 * JD-Core Version:    0.7.0.1
 */