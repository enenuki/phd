/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.PrimitiveByteArrayTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.LongVarbinaryTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class ImageType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<byte[]>
/*  8:   */ {
/*  9:37 */   public static final ImageType INSTANCE = new ImageType();
/* 10:   */   
/* 11:   */   public ImageType()
/* 12:   */   {
/* 13:40 */     super(LongVarbinaryTypeDescriptor.INSTANCE, PrimitiveByteArrayTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:44 */     return "image";
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.ImageType
 * JD-Core Version:    0.7.0.1
 */