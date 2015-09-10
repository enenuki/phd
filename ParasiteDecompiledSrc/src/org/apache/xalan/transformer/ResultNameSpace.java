/*  1:   */ package org.apache.xalan.transformer;
/*  2:   */ 
/*  3:   */ public class ResultNameSpace
/*  4:   */ {
/*  5:33 */   public ResultNameSpace m_next = null;
/*  6:   */   public String m_prefix;
/*  7:   */   public String m_uri;
/*  8:   */   
/*  9:   */   public ResultNameSpace(String prefix, String uri)
/* 10:   */   {
/* 11:50 */     this.m_prefix = prefix;
/* 12:51 */     this.m_uri = uri;
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.transformer.ResultNameSpace
 * JD-Core Version:    0.7.0.1
 */