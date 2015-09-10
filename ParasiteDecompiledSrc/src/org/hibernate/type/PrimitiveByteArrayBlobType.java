/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ @Deprecated
/*  4:   */ public class PrimitiveByteArrayBlobType
/*  5:   */   extends ByteArrayBlobType
/*  6:   */ {
/*  7:   */   public Class getReturnedClass()
/*  8:   */   {
/*  9:36 */     return [B.class;
/* 10:   */   }
/* 11:   */   
/* 12:   */   protected Object wrap(byte[] bytes)
/* 13:   */   {
/* 14:40 */     return bytes;
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected byte[] unWrap(Object bytes)
/* 18:   */   {
/* 19:44 */     return (byte[])bytes;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.PrimitiveByteArrayBlobType
 * JD-Core Version:    0.7.0.1
 */