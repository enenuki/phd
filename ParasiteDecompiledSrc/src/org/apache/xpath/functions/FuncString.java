/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.XPathContext;
/*  5:   */ import org.apache.xpath.objects.XObject;
/*  6:   */ import org.apache.xpath.objects.XString;
/*  7:   */ 
/*  8:   */ public class FuncString
/*  9:   */   extends FunctionDef1Arg
/* 10:   */ {
/* 11:   */   static final long serialVersionUID = -2206677149497712883L;
/* 12:   */   
/* 13:   */   public XObject execute(XPathContext xctxt)
/* 14:   */     throws TransformerException
/* 15:   */   {
/* 16:45 */     return (XString)getArg0AsString(xctxt);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncString
 * JD-Core Version:    0.7.0.1
 */