/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.apache.xpath.objects.XString;
/*  8:   */ 
/*  9:   */ public class FuncDoclocation
/* 10:   */   extends FunctionDef1Arg
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 7469213946343568769L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:48 */     int whereNode = getArg0AsNode(xctxt);
/* 18:49 */     String fileLocation = null;
/* 19:51 */     if (-1 != whereNode)
/* 20:   */     {
/* 21:53 */       DTM dtm = xctxt.getDTM(whereNode);
/* 22:56 */       if (11 == dtm.getNodeType(whereNode)) {
/* 23:58 */         whereNode = dtm.getFirstChild(whereNode);
/* 24:   */       }
/* 25:61 */       if (-1 != whereNode) {
/* 26:63 */         fileLocation = dtm.getDocumentBaseURI();
/* 27:   */       }
/* 28:   */     }
/* 29:69 */     return new XString(null != fileLocation ? fileLocation : "");
/* 30:   */   }
/* 31:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncDoclocation
 * JD-Core Version:    0.7.0.1
 */