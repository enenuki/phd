/*  1:   */ package org.apache.xpath.objects;
/*  2:   */ 
/*  3:   */ import org.apache.xml.dtm.DTM;
/*  4:   */ import org.apache.xpath.XPathContext;
/*  5:   */ 
/*  6:   */ public final class DTMXRTreeFrag
/*  7:   */ {
/*  8:   */   private DTM m_dtm;
/*  9:32 */   private int m_dtmIdentity = -1;
/* 10:   */   private XPathContext m_xctxt;
/* 11:   */   
/* 12:   */   public DTMXRTreeFrag(int dtmIdentity, XPathContext xctxt)
/* 13:   */   {
/* 14:36 */     this.m_xctxt = xctxt;
/* 15:37 */     this.m_dtmIdentity = dtmIdentity;
/* 16:38 */     this.m_dtm = xctxt.getDTM(dtmIdentity);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public final void destruct()
/* 20:   */   {
/* 21:42 */     this.m_dtm = null;
/* 22:43 */     this.m_xctxt = null;
/* 23:   */   }
/* 24:   */   
/* 25:   */   final DTM getDTM()
/* 26:   */   {
/* 27:46 */     return this.m_dtm;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public final int getDTMIdentity()
/* 31:   */   {
/* 32:47 */     return this.m_dtmIdentity;
/* 33:   */   }
/* 34:   */   
/* 35:   */   final XPathContext getXPathContext()
/* 36:   */   {
/* 37:48 */     return this.m_xctxt;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public final int hashCode()
/* 41:   */   {
/* 42:50 */     return this.m_dtmIdentity;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public final boolean equals(Object obj)
/* 46:   */   {
/* 47:52 */     if ((obj instanceof DTMXRTreeFrag)) {
/* 48:53 */       return this.m_dtmIdentity == ((DTMXRTreeFrag)obj).getDTMIdentity();
/* 49:   */     }
/* 50:55 */     return false;
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.objects.DTMXRTreeFrag
 * JD-Core Version:    0.7.0.1
 */