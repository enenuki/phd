/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.dtm.DTM;
/*  5:   */ import org.apache.xpath.Expression;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ import org.apache.xpath.objects.XString;
/*  9:   */ 
/* 10:   */ public class FuncUnparsedEntityURI
/* 11:   */   extends FunctionOneArg
/* 12:   */ {
/* 13:   */   static final long serialVersionUID = 845309759097448178L;
/* 14:   */   
/* 15:   */   public XObject execute(XPathContext xctxt)
/* 16:   */     throws TransformerException
/* 17:   */   {
/* 18:46 */     String name = this.m_arg0.execute(xctxt).str();
/* 19:47 */     int context = xctxt.getCurrentNode();
/* 20:48 */     DTM dtm = xctxt.getDTM(context);
/* 21:49 */     int doc = dtm.getDocument();
/* 22:   */     
/* 23:51 */     String uri = dtm.getUnparsedEntityURI(name);
/* 24:   */     
/* 25:53 */     return new XString(uri);
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncUnparsedEntityURI
 * JD-Core Version:    0.7.0.1
 */