/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.XPathContext;
/*  5:   */ import org.apache.xpath.objects.XNumber;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ 
/*  8:   */ public class FuncNumber
/*  9:   */   extends FunctionDef1Arg
/* 10:   */ {
/* 11:   */   static final long serialVersionUID = 7266745342264153076L;
/* 12:   */   
/* 13:   */   public XObject execute(XPathContext xctxt)
/* 14:   */     throws TransformerException
/* 15:   */   {
/* 16:45 */     return new XNumber(getArg0AsNumber(xctxt));
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncNumber
 * JD-Core Version:    0.7.0.1
 */