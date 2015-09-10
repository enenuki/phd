/*  1:   */ package org.apache.james.mime4j.util;
/*  2:   */ 
/*  3:   */ public abstract interface ByteSequence
/*  4:   */ {
/*  5:30 */   public static final ByteSequence EMPTY = new EmptyByteSequence();
/*  6:   */   
/*  7:   */   public abstract int length();
/*  8:   */   
/*  9:   */   public abstract byte byteAt(int paramInt);
/* 10:   */   
/* 11:   */   public abstract byte[] toByteArray();
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.util.ByteSequence
 * JD-Core Version:    0.7.0.1
 */