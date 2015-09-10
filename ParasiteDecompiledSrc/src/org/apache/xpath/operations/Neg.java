/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XNumber;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class Neg
/* 10:   */   extends UnaryOperation
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -6280607702375702291L;
/* 13:   */   
/* 14:   */   public XObject operate(XObject right)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:46 */     return new XNumber(-right.num());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public double num(XPathContext xctxt)
/* 21:   */     throws TransformerException
/* 22:   */   {
/* 23:62 */     return -this.m_right.num(xctxt);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Neg
 * JD-Core Version:    0.7.0.1
 */