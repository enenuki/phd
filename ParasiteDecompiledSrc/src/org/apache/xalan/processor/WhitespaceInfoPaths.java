/*  1:   */ package org.apache.xalan.processor;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import org.apache.xalan.templates.ElemTemplate;
/*  5:   */ import org.apache.xalan.templates.Stylesheet;
/*  6:   */ import org.apache.xalan.templates.WhiteSpaceInfo;
/*  7:   */ 
/*  8:   */ public class WhitespaceInfoPaths
/*  9:   */   extends WhiteSpaceInfo
/* 10:   */ {
/* 11:   */   static final long serialVersionUID = 5954766719577516723L;
/* 12:   */   private Vector m_elements;
/* 13:   */   
/* 14:   */   public void setElements(Vector elems)
/* 15:   */   {
/* 16:48 */     this.m_elements = elems;
/* 17:   */   }
/* 18:   */   
/* 19:   */   Vector getElements()
/* 20:   */   {
/* 21:61 */     return this.m_elements;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void clearElements()
/* 25:   */   {
/* 26:66 */     this.m_elements = null;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public WhitespaceInfoPaths(Stylesheet thisSheet)
/* 30:   */   {
/* 31:76 */     super(thisSheet);
/* 32:77 */     setStylesheet(thisSheet);
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.processor.WhitespaceInfoPaths
 * JD-Core Version:    0.7.0.1
 */