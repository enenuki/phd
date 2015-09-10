/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XNumber;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class FuncRound
/* 10:   */   extends FunctionOneArg
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -7970583902573826611L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:45 */     XObject obj = this.m_arg0.execute(xctxt);
/* 18:46 */     double val = obj.num();
/* 19:47 */     if ((val >= -0.5D) && (val < 0.0D)) {
/* 20:47 */       return new XNumber(-0.0D);
/* 21:   */     }
/* 22:48 */     if (val == 0.0D) {
/* 23:48 */       return new XNumber(val);
/* 24:   */     }
/* 25:49 */     return new XNumber(Math.floor(val + 0.5D));
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncRound
 * JD-Core Version:    0.7.0.1
 */