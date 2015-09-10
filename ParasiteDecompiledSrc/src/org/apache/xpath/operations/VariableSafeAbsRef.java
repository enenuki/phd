/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xml.dtm.DTMManager;
/*  6:   */ import org.apache.xpath.Expression;
/*  7:   */ import org.apache.xpath.XPathContext;
/*  8:   */ import org.apache.xpath.axes.NodeSequence;
/*  9:   */ import org.apache.xpath.objects.XNodeSet;
/* 10:   */ import org.apache.xpath.objects.XObject;
/* 11:   */ 
/* 12:   */ public class VariableSafeAbsRef
/* 13:   */   extends Variable
/* 14:   */ {
/* 15:   */   static final long serialVersionUID = -9174661990819967452L;
/* 16:   */   
/* 17:   */   public XObject execute(XPathContext xctxt, boolean destructiveOK)
/* 18:   */     throws TransformerException
/* 19:   */   {
/* 20:61 */     XNodeSet xns = (XNodeSet)super.execute(xctxt, destructiveOK);
/* 21:62 */     DTMManager dtmMgr = xctxt.getDTMManager();
/* 22:63 */     int context = xctxt.getContextNode();
/* 23:64 */     if (dtmMgr.getDTM(xns.getRoot()).getDocument() != dtmMgr.getDTM(context).getDocument())
/* 24:   */     {
/* 25:67 */       Expression expr = (Expression)xns.getContainedIter();
/* 26:68 */       xns = (XNodeSet)expr.asIterator(xctxt, context);
/* 27:   */     }
/* 28:70 */     return xns;
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.VariableSafeAbsRef
 * JD-Core Version:    0.7.0.1
 */