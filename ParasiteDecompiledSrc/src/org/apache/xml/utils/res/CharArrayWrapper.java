/*  1:   */ package org.apache.xml.utils.res;
/*  2:   */ 
/*  3:   */ public class CharArrayWrapper
/*  4:   */ {
/*  5:   */   private char[] m_char;
/*  6:   */   
/*  7:   */   public CharArrayWrapper(char[] arg)
/*  8:   */   {
/*  9:32 */     this.m_char = arg;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public char getChar(int index)
/* 13:   */   {
/* 14:36 */     return this.m_char[index];
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getLength()
/* 18:   */   {
/* 19:40 */     return this.m_char.length;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.res.CharArrayWrapper
 * JD-Core Version:    0.7.0.1
 */