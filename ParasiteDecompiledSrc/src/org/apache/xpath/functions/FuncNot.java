/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XBoolean;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class FuncNot
/* 10:   */   extends FunctionOneArg
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 7299699961076329790L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:45 */     return this.m_arg0.execute(xctxt).bool() ? XBoolean.S_FALSE : XBoolean.S_TRUE;
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncNot
 * JD-Core Version:    0.7.0.1
 */