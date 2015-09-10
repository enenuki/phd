/*  1:   */ package org.apache.xml.utils.res;
/*  2:   */ 
/*  3:   */ public class IntArrayWrapper
/*  4:   */ {
/*  5:   */   private int[] m_int;
/*  6:   */   
/*  7:   */   public IntArrayWrapper(int[] arg)
/*  8:   */   {
/*  9:32 */     this.m_int = arg;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public int getInt(int index)
/* 13:   */   {
/* 14:36 */     return this.m_int[index];
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getLength()
/* 18:   */   {
/* 19:40 */     return this.m_int.length;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.res.IntArrayWrapper
 * JD-Core Version:    0.7.0.1
 */