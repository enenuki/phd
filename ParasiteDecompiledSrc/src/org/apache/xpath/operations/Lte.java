/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.objects.XBoolean;
/*  5:   */ import org.apache.xpath.objects.XObject;
/*  6:   */ 
/*  7:   */ public class Lte
/*  8:   */   extends Operation
/*  9:   */ {
/* 10:   */   static final long serialVersionUID = 6945650810527140228L;
/* 11:   */   
/* 12:   */   public XObject operate(XObject left, XObject right)
/* 13:   */     throws TransformerException
/* 14:   */   {
/* 15:47 */     return left.lessThanOrEqual(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Lte
 * JD-Core Version:    0.7.0.1
 */