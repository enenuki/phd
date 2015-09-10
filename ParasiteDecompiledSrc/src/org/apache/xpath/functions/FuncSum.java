/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xml.dtm.DTMIterator;
/*  6:   */ import org.apache.xml.utils.XMLString;
/*  7:   */ import org.apache.xpath.Expression;
/*  8:   */ import org.apache.xpath.XPathContext;
/*  9:   */ import org.apache.xpath.objects.XNumber;
/* 10:   */ import org.apache.xpath.objects.XObject;
/* 11:   */ 
/* 12:   */ public class FuncSum
/* 13:   */   extends FunctionOneArg
/* 14:   */ {
/* 15:   */   static final long serialVersionUID = -2719049259574677519L;
/* 16:   */   
/* 17:   */   public XObject execute(XPathContext xctxt)
/* 18:   */     throws TransformerException
/* 19:   */   {
/* 20:49 */     DTMIterator nodes = this.m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
/* 21:50 */     double sum = 0.0D;
/* 22:   */     int pos;
/* 23:53 */     while (-1 != (pos = nodes.nextNode()))
/* 24:   */     {
/* 25:   */       int i;
/* 26:55 */       DTM dtm = nodes.getDTM(i);
/* 27:56 */       XMLString s = dtm.getStringValue(i);
/* 28:58 */       if (null != s) {
/* 29:59 */         sum += s.toDouble();
/* 30:   */       }
/* 31:   */     }
/* 32:61 */     nodes.detach();
/* 33:   */     
/* 34:63 */     return new XNumber(sum);
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncSum
 * JD-Core Version:    0.7.0.1
 */