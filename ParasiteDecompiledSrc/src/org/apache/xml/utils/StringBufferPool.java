/*  1:   */ package org.apache.xml.utils;
/*  2:   */ 
/*  3:   */ public class StringBufferPool
/*  4:   */ {
/*  5:33 */   private static ObjectPool m_stringBufPool = new ObjectPool(FastStringBuffer.class);
/*  6:   */   
/*  7:   */   public static synchronized FastStringBuffer get()
/*  8:   */   {
/*  9:44 */     return (FastStringBuffer)m_stringBufPool.getInstance();
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static synchronized void free(FastStringBuffer sb)
/* 13:   */   {
/* 14:57 */     sb.setLength(0);
/* 15:58 */     m_stringBufPool.freeInstance(sb);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.StringBufferPool
 * JD-Core Version:    0.7.0.1
 */