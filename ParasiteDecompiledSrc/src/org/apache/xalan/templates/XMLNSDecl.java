/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public class XMLNSDecl
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = 6710237366877605097L;
/*  9:   */   private String m_prefix;
/* 10:   */   private String m_uri;
/* 11:   */   private boolean m_isExcluded;
/* 12:   */   
/* 13:   */   public XMLNSDecl(String prefix, String uri, boolean isExcluded)
/* 14:   */   {
/* 15:41 */     this.m_prefix = prefix;
/* 16:42 */     this.m_uri = uri;
/* 17:43 */     this.m_isExcluded = isExcluded;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getPrefix()
/* 21:   */   {
/* 22:57 */     return this.m_prefix;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getURI()
/* 26:   */   {
/* 27:70 */     return this.m_uri;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean getIsExcluded()
/* 31:   */   {
/* 32:85 */     return this.m_isExcluded;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.XMLNSDecl
 * JD-Core Version:    0.7.0.1
 */