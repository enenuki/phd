/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.apache.xpath.objects.XString;
/*  8:   */ 
/*  9:   */ public class FuncLocalPart
/* 10:   */   extends FunctionDef1Arg
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 7591798770325814746L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:47 */     int context = getArg0AsNode(xctxt);
/* 18:48 */     if (-1 == context) {
/* 19:49 */       return XString.EMPTYSTRING;
/* 20:   */     }
/* 21:50 */     DTM dtm = xctxt.getDTM(context);
/* 22:51 */     String s = context != -1 ? dtm.getLocalName(context) : "";
/* 23:52 */     if ((s.startsWith("#")) || (s.equals("xmlns"))) {
/* 24:53 */       return XString.EMPTYSTRING;
/* 25:   */     }
/* 26:55 */     return new XString(s);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncLocalPart
 * JD-Core Version:    0.7.0.1
 */