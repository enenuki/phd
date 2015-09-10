/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xml.utils.XMLString;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ import org.apache.xpath.objects.XString;
/*  9:   */ import org.xml.sax.ContentHandler;
/* 10:   */ import org.xml.sax.SAXException;
/* 11:   */ 
/* 12:   */ public class FuncNormalizeSpace
/* 13:   */   extends FunctionDef1Arg
/* 14:   */ {
/* 15:   */   static final long serialVersionUID = -3377956872032190880L;
/* 16:   */   
/* 17:   */   public XObject execute(XPathContext xctxt)
/* 18:   */     throws TransformerException
/* 19:   */   {
/* 20:48 */     XMLString s1 = getArg0AsString(xctxt);
/* 21:   */     
/* 22:50 */     return (XString)s1.fixWhiteSpace(true, true, false);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void executeCharsToContentHandler(XPathContext xctxt, ContentHandler handler)
/* 26:   */     throws TransformerException, SAXException
/* 27:   */   {
/* 28:70 */     if (Arg0IsNodesetExpr())
/* 29:   */     {
/* 30:72 */       int node = getArg0AsNode(xctxt);
/* 31:73 */       if (-1 != node)
/* 32:   */       {
/* 33:75 */         DTM dtm = xctxt.getDTM(node);
/* 34:76 */         dtm.dispatchCharactersEvents(node, handler, true);
/* 35:   */       }
/* 36:   */     }
/* 37:   */     else
/* 38:   */     {
/* 39:81 */       XObject obj = execute(xctxt);
/* 40:82 */       obj.dispatchCharactersEvents(handler);
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncNormalizeSpace
 * JD-Core Version:    0.7.0.1
 */