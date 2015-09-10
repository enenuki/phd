/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.ByteArrayTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.BlobTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class WrappedMaterializedBlobType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<Byte[]>
/*  8:   */ {
/*  9:35 */   public static final WrappedMaterializedBlobType INSTANCE = new WrappedMaterializedBlobType();
/* 10:   */   
/* 11:   */   public WrappedMaterializedBlobType()
/* 12:   */   {
/* 13:38 */     super(BlobTypeDescriptor.DEFAULT, ByteArrayTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:43 */     return null;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.WrappedMaterializedBlobType
 * JD-Core Version:    0.7.0.1
 */