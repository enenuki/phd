/*  1:   */ package org.apache.xalan.transformer;
/*  2:   */ 
/*  3:   */ public class DecimalToRoman
/*  4:   */ {
/*  5:   */   public long m_postValue;
/*  6:   */   public String m_postLetter;
/*  7:   */   public long m_preValue;
/*  8:   */   public String m_preLetter;
/*  9:   */   
/* 10:   */   public DecimalToRoman(long postValue, String postLetter, long preValue, String preLetter)
/* 11:   */   {
/* 12:45 */     this.m_postValue = postValue;
/* 13:46 */     this.m_postLetter = postLetter;
/* 14:47 */     this.m_preValue = preValue;
/* 15:48 */     this.m_preLetter = preLetter;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.DecimalToRoman
 * JD-Core Version:    0.7.0.1
 */