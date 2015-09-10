/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  5:   */ import org.hibernate.type.descriptor.java.PrimitiveByteArrayTypeDescriptor;
/*  6:   */ import org.hibernate.type.descriptor.sql.VarbinaryTypeDescriptor;
/*  7:   */ 
/*  8:   */ public class BinaryType
/*  9:   */   extends AbstractSingleColumnStandardBasicType<byte[]>
/* 10:   */   implements VersionType<byte[]>
/* 11:   */ {
/* 12:42 */   public static final BinaryType INSTANCE = new BinaryType();
/* 13:   */   
/* 14:   */   public String getName()
/* 15:   */   {
/* 16:45 */     return "binary";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public BinaryType()
/* 20:   */   {
/* 21:49 */     super(VarbinaryTypeDescriptor.INSTANCE, PrimitiveByteArrayTypeDescriptor.INSTANCE);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String[] getRegistrationKeys()
/* 25:   */   {
/* 26:54 */     return new String[] { getName(), "byte[]", [B.class.getName() };
/* 27:   */   }
/* 28:   */   
/* 29:   */   public byte[] seed(SessionImplementor session)
/* 30:   */   {
/* 31:63 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public byte[] next(byte[] current, SessionImplementor session)
/* 35:   */   {
/* 36:68 */     return current;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Comparator<byte[]> getComparator()
/* 40:   */   {
/* 41:73 */     return PrimitiveByteArrayTypeDescriptor.INSTANCE.getComparator();
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.BinaryType
 * JD-Core Version:    0.7.0.1
 */