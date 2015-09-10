/*  1:   */ package org.apache.james.mime4j.util;
/*  2:   */ 
/*  3:   */ final class EmptyByteSequence
/*  4:   */   implements ByteSequence
/*  5:   */ {
/*  6:23 */   private static final byte[] EMPTY_BYTES = new byte[0];
/*  7:   */   
/*  8:   */   public int length()
/*  9:   */   {
/* 10:26 */     return 0;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public byte byteAt(int index)
/* 14:   */   {
/* 15:30 */     throw new IndexOutOfBoundsException();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public byte[] toByteArray()
/* 19:   */   {
/* 20:34 */     return EMPTY_BYTES;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.util.EmptyByteSequence
 * JD-Core Version:    0.7.0.1
 */