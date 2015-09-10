/*  1:   */ package org.apache.xml.utils;
/*  2:   */ 
/*  3:   */ public class NSInfo
/*  4:   */ {
/*  5:   */   public String m_namespace;
/*  6:   */   public boolean m_hasXMLNSAttrs;
/*  7:   */   public boolean m_hasProcessedNS;
/*  8:   */   public int m_ancestorHasXMLNSAttrs;
/*  9:   */   public static final int ANCESTORXMLNSUNPROCESSED = 0;
/* 10:   */   public static final int ANCESTORHASXMLNS = 1;
/* 11:   */   public static final int ANCESTORNOXMLNS = 2;
/* 12:   */   
/* 13:   */   public NSInfo(boolean hasProcessedNS, boolean hasXMLNSAttrs)
/* 14:   */   {
/* 15:44 */     this.m_hasProcessedNS = hasProcessedNS;
/* 16:45 */     this.m_hasXMLNSAttrs = hasXMLNSAttrs;
/* 17:46 */     this.m_namespace = null;
/* 18:47 */     this.m_ancestorHasXMLNSAttrs = 0;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public NSInfo(boolean hasProcessedNS, boolean hasXMLNSAttrs, int ancestorHasXMLNSAttrs)
/* 22:   */   {
/* 23:67 */     this.m_hasProcessedNS = hasProcessedNS;
/* 24:68 */     this.m_hasXMLNSAttrs = hasXMLNSAttrs;
/* 25:69 */     this.m_ancestorHasXMLNSAttrs = ancestorHasXMLNSAttrs;
/* 26:70 */     this.m_namespace = null;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public NSInfo(String namespace, boolean hasXMLNSAttrs)
/* 30:   */   {
/* 31:84 */     this.m_hasProcessedNS = true;
/* 32:85 */     this.m_hasXMLNSAttrs = hasXMLNSAttrs;
/* 33:86 */     this.m_namespace = namespace;
/* 34:87 */     this.m_ancestorHasXMLNSAttrs = 0;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.NSInfo
 * JD-Core Version:    0.7.0.1
 */