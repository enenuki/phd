/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import org.apache.xpath.XPath;
/*  4:   */ 
/*  5:   */ public class WhiteSpaceInfo
/*  6:   */   extends ElemTemplate
/*  7:   */ {
/*  8:   */   static final long serialVersionUID = 6389208261999943836L;
/*  9:   */   private boolean m_shouldStripSpace;
/* 10:   */   
/* 11:   */   public boolean getShouldStripSpace()
/* 12:   */   {
/* 13:47 */     return this.m_shouldStripSpace;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public WhiteSpaceInfo(Stylesheet thisSheet)
/* 17:   */   {
/* 18:56 */     setStylesheet(thisSheet);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public WhiteSpaceInfo(XPath matchPattern, boolean shouldStripSpace, Stylesheet thisSheet)
/* 22:   */   {
/* 23:72 */     this.m_shouldStripSpace = shouldStripSpace;
/* 24:   */     
/* 25:74 */     setMatch(matchPattern);
/* 26:   */     
/* 27:76 */     setStylesheet(thisSheet);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void recompose(StylesheetRoot root)
/* 31:   */   {
/* 32:84 */     root.recomposeWhiteSpaceInfo(this);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.WhiteSpaceInfo
 * JD-Core Version:    0.7.0.1
 */