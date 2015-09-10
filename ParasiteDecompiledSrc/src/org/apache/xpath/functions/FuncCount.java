/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTMIterator;
/*  5:   */ import org.apache.xpath.Expression;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XNumber;
/*  8:   */ import org.apache.xpath.objects.XObject;
/*  9:   */ 
/* 10:   */ public class FuncCount
/* 11:   */   extends FunctionOneArg
/* 12:   */ {
/* 13:   */   static final long serialVersionUID = -7116225100474153751L;
/* 14:   */   
/* 15:   */   public XObject execute(XPathContext xctxt)
/* 16:   */     throws TransformerException
/* 17:   */   {
/* 18:58 */     DTMIterator nl = this.m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
/* 19:59 */     int i = nl.getLength();
/* 20:60 */     nl.detach();
/* 21:   */     
/* 22:62 */     return new XNumber(i);
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncCount
 * JD-Core Version:    0.7.0.1
 */