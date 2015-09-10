/*  1:   */ package com.gargoylesoftware.htmlunit.html.xpath;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.XPathContext;
/*  5:   */ import org.apache.xpath.functions.FunctionDef1Arg;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.apache.xpath.objects.XString;
/*  8:   */ 
/*  9:   */ public class LowerCaseFunction
/* 10:   */   extends FunctionDef1Arg
/* 11:   */ {
/* 12:   */   public XObject execute(XPathContext xctxt)
/* 13:   */     throws TransformerException
/* 14:   */   {
/* 15:39 */     return new XString(((XString)getArg0AsString(xctxt)).str().toLowerCase());
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.html.xpath.LowerCaseFunction
 * JD-Core Version:    0.7.0.1
 */