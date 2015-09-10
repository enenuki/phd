/*  1:   */ package org.hibernate.type;
/*  2:   */ 
/*  3:   */ /**
/*  4:   */  * @deprecated
/*  5:   */  */
/*  6:   */ public abstract class AbstractLongBinaryType
/*  7:   */   extends AbstractBynaryType
/*  8:   */ {
/*  9:   */   public Class getReturnedClass()
/* 10:   */   {
/* 11:37 */     return [B.class;
/* 12:   */   }
/* 13:   */   
/* 14:   */   protected Object toExternalFormat(byte[] bytes)
/* 15:   */   {
/* 16:41 */     return bytes;
/* 17:   */   }
/* 18:   */   
/* 19:   */   protected byte[] toInternalFormat(Object bytes)
/* 20:   */   {
/* 21:45 */     return (byte[])bytes;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.AbstractLongBinaryType
 * JD-Core Version:    0.7.0.1
 */