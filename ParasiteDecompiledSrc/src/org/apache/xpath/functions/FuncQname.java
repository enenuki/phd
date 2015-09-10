/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.apache.xpath.objects.XString;
/*  8:   */ 
/*  9:   */ public class FuncQname
/* 10:   */   extends FunctionDef1Arg
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -1532307875532617380L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:47 */     int context = getArg0AsNode(xctxt);
/* 18:   */     XObject val;
/* 19:50 */     if (-1 != context)
/* 20:   */     {
/* 21:52 */       DTM dtm = xctxt.getDTM(context);
/* 22:53 */       String qname = dtm.getNodeNameX(context);
/* 23:54 */       val = null == qname ? XString.EMPTYSTRING : new XString(qname);
/* 24:   */     }
/* 25:   */     else
/* 26:   */     {
/* 27:58 */       val = XString.EMPTYSTRING;
/* 28:   */     }
/* 29:61 */     return val;
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncQname
 * JD-Core Version:    0.7.0.1
 */