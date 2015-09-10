/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XBoolean;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class And
/* 10:   */   extends Operation
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 392330077126534022L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:48 */     XObject expr1 = this.m_left.execute(xctxt);
/* 18:50 */     if (expr1.bool())
/* 19:   */     {
/* 20:52 */       XObject expr2 = this.m_right.execute(xctxt);
/* 21:   */       
/* 22:54 */       return expr2.bool() ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 23:   */     }
/* 24:57 */     return XBoolean.S_FALSE;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean bool(XPathContext xctxt)
/* 28:   */     throws TransformerException
/* 29:   */   {
/* 30:72 */     return (this.m_left.bool(xctxt)) && (this.m_right.bool(xctxt));
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.And
 * JD-Core Version:    0.7.0.1
 */