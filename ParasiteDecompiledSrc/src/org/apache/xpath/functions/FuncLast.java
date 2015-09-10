/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import javax.xml.transform.TransformerException;
/*  5:   */ import org.apache.xml.dtm.DTMIterator;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.axes.SubContextList;
/*  8:   */ import org.apache.xpath.compiler.Compiler;
/*  9:   */ import org.apache.xpath.objects.XNumber;
/* 10:   */ import org.apache.xpath.objects.XObject;
/* 11:   */ 
/* 12:   */ public class FuncLast
/* 13:   */   extends Function
/* 14:   */ {
/* 15:   */   static final long serialVersionUID = 9205812403085432943L;
/* 16:   */   private boolean m_isTopLevel;
/* 17:   */   
/* 18:   */   public void postCompileStep(Compiler compiler)
/* 19:   */   {
/* 20:47 */     this.m_isTopLevel = (compiler.getLocationPathDepth() == -1);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getCountOfContextNodeList(XPathContext xctxt)
/* 24:   */     throws TransformerException
/* 25:   */   {
/* 26:65 */     SubContextList iter = this.m_isTopLevel ? null : xctxt.getSubContextList();
/* 27:68 */     if (null != iter) {
/* 28:69 */       return iter.getLastPos(xctxt);
/* 29:   */     }
/* 30:71 */     DTMIterator cnl = xctxt.getContextNodeList();
/* 31:   */     int count;
/* 32:73 */     if (null != cnl) {
/* 33:75 */       count = cnl.getLength();
/* 34:   */     } else {
/* 35:79 */       count = 0;
/* 36:   */     }
/* 37:80 */     return count;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public XObject execute(XPathContext xctxt)
/* 41:   */     throws TransformerException
/* 42:   */   {
/* 43:93 */     XNumber xnum = new XNumber(getCountOfContextNodeList(xctxt));
/* 44:   */     
/* 45:95 */     return xnum;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void fixupVariables(Vector vars, int globalsSize) {}
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncLast
 * JD-Core Version:    0.7.0.1
 */