/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XNumber;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class Number
/* 10:   */   extends UnaryOperation
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 7196954482871619765L;
/* 13:   */   
/* 14:   */   public XObject operate(XObject right)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:47 */     if (2 == right.getType()) {
/* 18:48 */       return right;
/* 19:   */     }
/* 20:50 */     return new XNumber(right.num());
/* 21:   */   }
/* 22:   */   
/* 23:   */   public double num(XPathContext xctxt)
/* 24:   */     throws TransformerException
/* 25:   */   {
/* 26:66 */     return this.m_right.num(xctxt);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Number
 * JD-Core Version:    0.7.0.1
 */