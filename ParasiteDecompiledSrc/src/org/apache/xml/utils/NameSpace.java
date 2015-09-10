/*  1:   */ package org.apache.xml.utils;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class NameSpace
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = 1471232939184881839L;
/*  9:37 */   public NameSpace m_next = null;
/* 10:   */   public String m_prefix;
/* 11:   */   public String m_uri;
/* 12:   */   
/* 13:   */   public NameSpace(String prefix, String uri)
/* 14:   */   {
/* 15:56 */     this.m_prefix = prefix;
/* 16:57 */     this.m_uri = uri;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.NameSpace
 * JD-Core Version:    0.7.0.1
 */