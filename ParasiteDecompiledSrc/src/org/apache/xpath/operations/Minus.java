/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XNumber;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class Minus
/* 10:   */   extends Operation
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -5297672838170871043L;
/* 13:   */   
/* 14:   */   public XObject operate(XObject left, XObject right)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:49 */     return new XNumber(left.num() - right.num());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public double num(XPathContext xctxt)
/* 21:   */     throws TransformerException
/* 22:   */   {
/* 23:65 */     return this.m_left.num(xctxt) - this.m_right.num(xctxt);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Minus
 * JD-Core Version:    0.7.0.1
 */