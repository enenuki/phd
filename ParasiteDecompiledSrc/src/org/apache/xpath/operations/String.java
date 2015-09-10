/*  1:   */ package org.apache.xpath.operations;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.objects.XObject;
/*  5:   */ import org.apache.xpath.objects.XString;
/*  6:   */ 
/*  7:   */ public class String
/*  8:   */   extends UnaryOperation
/*  9:   */ {
/* 10:   */   static final long serialVersionUID = 2973374377453022888L;
/* 11:   */   
/* 12:   */   public XObject operate(XObject right)
/* 13:   */     throws TransformerException
/* 14:   */   {
/* 15:45 */     return (XString)right.xstr();
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.operations.String
 * JD-Core Version:    0.7.0.1
 */