/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.utils.XMLString;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XNumber;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class FuncStringLength
/* 10:   */   extends FunctionDef1Arg
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -159616417996519839L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:45 */     return new XNumber(getArg0AsString(xctxt).length());
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncStringLength
 * JD-Core Version:    0.7.0.1
 */