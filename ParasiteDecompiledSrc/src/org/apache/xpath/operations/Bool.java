/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XBoolean;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class Bool
/* 10:   */   extends UnaryOperation
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 44705375321914635L;
/* 13:   */   
/* 14:   */   public XObject operate(XObject right)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:47 */     if (1 == right.getType()) {
/* 18:48 */       return right;
/* 19:   */     }
/* 20:50 */     return right.bool() ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean bool(XPathContext xctxt)
/* 24:   */     throws TransformerException
/* 25:   */   {
/* 26:65 */     return this.m_right.bool(xctxt);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Bool
 * JD-Core Version:    0.7.0.1
 */