/*  1:   */ package org.apache.xml.utils.res;
/*  2:   */ 
/*  3:   */ public class StringArrayWrapper
/*  4:   */ {
/*  5:   */   private String[] m_string;
/*  6:   */   
/*  7:   */   public StringArrayWrapper(String[] arg)
/*  8:   */   {
/*  9:32 */     this.m_string = arg;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public String getString(int index)
/* 13:   */   {
/* 14:36 */     return this.m_string[index];
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getLength()
/* 18:   */   {
/* 19:40 */     return this.m_string.length;
/* 20:   */   }
/* 21:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.res.StringArrayWrapper
 * JD-Core Version:    0.7.0.1
 */