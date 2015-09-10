/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import org.hibernate.type.descriptor.java.PrimitiveByteArrayTypeDescriptor;
/*  4:   */ import org.hibernate.type.descriptor.sql.BlobTypeDescriptor;
/*  5:   */ 
/*  6:   */ public class MaterializedBlobType
/*  7:   */   extends AbstractSingleColumnStandardBasicType<byte[]>
/*  8:   */ {
/*  9:37 */   public static final MaterializedBlobType INSTANCE = new MaterializedBlobType();
/* 10:   */   
/* 11:   */   public MaterializedBlobType()
/* 12:   */   {
/* 13:40 */     super(BlobTypeDescriptor.DEFAULT, PrimitiveByteArrayTypeDescriptor.INSTANCE);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:44 */     return "materialized_blob";
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.MaterializedBlobType
 * JD-Core Version:    0.7.0.1
 */