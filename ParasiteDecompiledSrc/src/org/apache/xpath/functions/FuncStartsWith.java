/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.utils.XMLString;
/*  5:   */ import org.apache.xpath.Expression;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XBoolean;
/*  8:   */ import org.apache.xpath.objects.XObject;
/*  9:   */ 
/* 10:   */ public class FuncStartsWith
/* 11:   */   extends Function2Args
/* 12:   */ {
/* 13:   */   static final long serialVersionUID = 2194585774699567928L;
/* 14:   */   
/* 15:   */   public XObject execute(XPathContext xctxt)
/* 16:   */     throws TransformerException
/* 17:   */   {
/* 18:45 */     return this.m_arg0.execute(xctxt).xstr().startsWith(this.m_arg1.execute(xctxt).xstr()) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncStartsWith
 * JD-Core Version:    0.7.0.1
 */