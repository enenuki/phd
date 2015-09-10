/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XBoolean;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class Equals
/* 10:   */   extends Operation
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -2658315633903426134L;
/* 13:   */   
/* 14:   */   public XObject operate(XObject left, XObject right)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:48 */     return left.equals(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean bool(XPathContext xctxt)
/* 21:   */     throws TransformerException
/* 22:   */   {
/* 23:65 */     XObject left = this.m_left.execute(xctxt, true);
/* 24:66 */     XObject right = this.m_right.execute(xctxt, true);
/* 25:   */     
/* 26:68 */     boolean result = left.equals(right);
/* 27:69 */     left.detach();
/* 28:70 */     right.detach();
/* 29:71 */     return result;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Equals
 * JD-Core Version:    0.7.0.1
 */