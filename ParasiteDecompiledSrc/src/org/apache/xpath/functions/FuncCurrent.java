/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import java.util.Vector;
/*  4:   */ import javax.xml.transform.TransformerException;
/*  5:   */ import org.apache.xalan.res.XSLMessages;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.axes.LocPathIterator;
/*  8:   */ import org.apache.xpath.axes.PredicatedNodeTest;
/*  9:   */ import org.apache.xpath.axes.SubContextList;
/* 10:   */ import org.apache.xpath.objects.XNodeSet;
/* 11:   */ import org.apache.xpath.objects.XObject;
/* 12:   */ import org.apache.xpath.patterns.StepPattern;
/* 13:   */ 
/* 14:   */ public class FuncCurrent
/* 15:   */   extends Function
/* 16:   */ {
/* 17:   */   static final long serialVersionUID = 5715316804877715008L;
/* 18:   */   
/* 19:   */   public XObject execute(XPathContext xctxt)
/* 20:   */     throws TransformerException
/* 21:   */   {
/* 22:55 */     SubContextList subContextList = xctxt.getCurrentNodeList();
/* 23:56 */     int currentNode = -1;
/* 24:58 */     if (null != subContextList)
/* 25:   */     {
/* 26:59 */       if ((subContextList instanceof PredicatedNodeTest))
/* 27:   */       {
/* 28:60 */         LocPathIterator iter = ((PredicatedNodeTest)subContextList).getLocPathIterator();
/* 29:   */         
/* 30:62 */         currentNode = iter.getCurrentContextNode();
/* 31:   */       }
/* 32:63 */       else if ((subContextList instanceof StepPattern))
/* 33:   */       {
/* 34:64 */         throw new RuntimeException(XSLMessages.createMessage("ER_PROCESSOR_ERROR", null));
/* 35:   */       }
/* 36:   */     }
/* 37:   */     else {
/* 38:69 */       currentNode = xctxt.getContextNode();
/* 39:   */     }
/* 40:71 */     return new XNodeSet(currentNode, xctxt.getDTMManager());
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void fixupVariables(Vector vars, int globalsSize) {}
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncCurrent
 * JD-Core Version:    0.7.0.1
 */