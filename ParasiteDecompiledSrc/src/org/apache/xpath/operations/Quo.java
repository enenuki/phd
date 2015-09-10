/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.objects.XNumber;
/*  5:   */ import org.apache.xpath.objects.XObject;
/*  6:   */ 
/*  7:   */ /**
/*  8:   */  * @deprecated
/*  9:   */  */
/* 10:   */ public class Quo
/* 11:   */   extends Operation
/* 12:   */ {
/* 13:   */   static final long serialVersionUID = 693765299196169905L;
/* 14:   */   
/* 15:   */   public XObject operate(XObject left, XObject right)
/* 16:   */     throws TransformerException
/* 17:   */   {
/* 18:50 */     return new XNumber((int)(left.num() / right.num()));
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.Quo
 * JD-Core Version:    0.7.0.1
 */