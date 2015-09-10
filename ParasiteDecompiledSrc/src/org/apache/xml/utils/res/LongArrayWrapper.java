/*  1:   */ package org.apache.xml.utils.res;
/*  2:   */ 
/*  3:   */ public class LongArrayWrapper
/*  4:   */ {
/*  5:   */   private long[] m_long;
/*  6:   */   
/*  7:   */   public LongArrayWrapper(long[] arg)
/*  8:   */   {
/*  9:32 */     this.m_long = arg;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public long getLong(int index)
/* 13:   */   {
/* 14:36 */     return this.m_long[index];
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getLength()
/* 18:   */   {
/* 19:40 */     return this.m_long.length;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.res.LongArrayWrapper
 * JD-Core Version:    0.7.0.1
 */