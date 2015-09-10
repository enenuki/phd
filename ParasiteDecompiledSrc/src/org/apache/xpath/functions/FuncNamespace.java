/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.apache.xpath.objects.XString;
/*  8:   */ 
/*  9:   */ public class FuncNamespace
/* 10:   */   extends FunctionDef1Arg
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -4695674566722321237L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:47 */     int context = getArg0AsNode(xctxt);
/* 18:   */     String s;
/* 19:50 */     if (context != -1)
/* 20:   */     {
/* 21:52 */       DTM dtm = xctxt.getDTM(context);
/* 22:53 */       int t = dtm.getNodeType(context);
/* 23:54 */       if (t == 1)
/* 24:   */       {
/* 25:56 */         s = dtm.getNamespaceURI(context);
/* 26:   */       }
/* 27:58 */       else if (t == 2)
/* 28:   */       {
/* 29:64 */         s = dtm.getNodeName(context);
/* 30:65 */         if ((s.startsWith("xmlns:")) || (s.equals("xmlns"))) {
/* 31:66 */           return XString.EMPTYSTRING;
/* 32:   */         }
/* 33:68 */         s = dtm.getNamespaceURI(context);
/* 34:   */       }
/* 35:   */       else
/* 36:   */       {
/* 37:71 */         return XString.EMPTYSTRING;
/* 38:   */       }
/* 39:   */     }
/* 40:   */     else
/* 41:   */     {
/* 42:74 */       return XString.EMPTYSTRING;
/* 43:   */     }
/* 44:76 */     return null == s ? XString.EMPTYSTRING : new XString(s);
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncNamespace
 * JD-Core Version:    0.7.0.1
 */