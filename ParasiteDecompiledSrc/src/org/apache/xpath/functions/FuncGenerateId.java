/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.XPathContext;
/*  5:   */ import org.apache.xpath.objects.XObject;
/*  6:   */ import org.apache.xpath.objects.XString;
/*  7:   */ 
/*  8:   */ public class FuncGenerateId
/*  9:   */   extends FunctionDef1Arg
/* 10:   */ {
/* 11:   */   static final long serialVersionUID = 973544842091724273L;
/* 12:   */   
/* 13:   */   public XObject execute(XPathContext xctxt)
/* 14:   */     throws TransformerException
/* 15:   */   {
/* 16:47 */     int which = getArg0AsNode(xctxt);
/* 17:49 */     if (-1 != which) {
/* 18:57 */       return new XString("N" + Integer.toHexString(which).toUpperCase());
/* 19:   */     }
/* 20:60 */     return XString.EMPTYSTRING;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncGenerateId
 * JD-Core Version:    0.7.0.1
 */