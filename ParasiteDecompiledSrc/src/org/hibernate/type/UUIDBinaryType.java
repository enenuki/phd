/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ import java.util.UUID;
/*  4:   */ import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
/*  5:   */ import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
/*  6:   */ 
/*  7:   */ public class UUIDBinaryType
/*  8:   */   extends AbstractSingleColumnStandardBasicType<UUID>
/*  9:   */ {
/* 10:37 */   public static final UUIDBinaryType INSTANCE = new UUIDBinaryType();
/* 11:   */   
/* 12:   */   public UUIDBinaryType()
/* 13:   */   {
/* 14:40 */     super(BinaryTypeDescriptor.INSTANCE, UUIDTypeDescriptor.INSTANCE);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getName()
/* 18:   */   {
/* 19:44 */     return "uuid-binary";
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected boolean registerUnderJavaType()
/* 23:   */   {
/* 24:49 */     return true;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.UUIDBinaryType
 * JD-Core Version:    0.7.0.1
 */