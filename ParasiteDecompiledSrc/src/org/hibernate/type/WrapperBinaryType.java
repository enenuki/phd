/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.ByteArrayTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.VarbinaryTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class WrapperBinaryType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<Byte[]>
/*  8:   */ {
/*  9:35 */   public static final WrapperBinaryType INSTANCE = new WrapperBinaryType();
/* 10:   */   
/* 11:   */   public WrapperBinaryType()
/* 12:   */   {
/* 13:38 */     super(VarbinaryTypeDescriptor.INSTANCE, ByteArrayTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String[] getRegistrationKeys()
/* 17:   */   {
/* 18:43 */     return new String[] { getName(), "Byte[]", [Ljava.lang.Byte.class.getName() };
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String getName()
/* 22:   */   {
/* 23:48 */     return "wrapper-binary";
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.WrapperBinaryType
 * JD-Core Version:    0.7.0.1
 */