/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.apache.xpath.objects.XString;
/*  8:   */ 
/*  9:   */ public class FuncSubstringBefore
/* 10:   */   extends Function2Args
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 4110547161672431775L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:46 */     String s1 = this.m_arg0.execute(xctxt).str();
/* 18:47 */     String s2 = this.m_arg1.execute(xctxt).str();
/* 19:48 */     int index = s1.indexOf(s2);
/* 20:   */     
/* 21:50 */     return -1 == index ? XString.EMPTYSTRING : new XString(s1.substring(0, index));
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncSubstringBefore
 * JD-Core Version:    0.7.0.1
 */