/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xml.utils.XMLString;
/*  5:   */ import org.apache.xpath.Expression;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ import org.apache.xpath.objects.XString;
/*  9:   */ 
/* 10:   */ public class FuncSubstringAfter
/* 11:   */   extends Function2Args
/* 12:   */ {
/* 13:   */   static final long serialVersionUID = -8119731889862512194L;
/* 14:   */   
/* 15:   */   public XObject execute(XPathContext xctxt)
/* 16:   */     throws TransformerException
/* 17:   */   {
/* 18:47 */     XMLString s1 = this.m_arg0.execute(xctxt).xstr();
/* 19:48 */     XMLString s2 = this.m_arg1.execute(xctxt).xstr();
/* 20:49 */     int index = s1.indexOf(s2);
/* 21:   */     
/* 22:51 */     return -1 == index ? XString.EMPTYSTRING : (XString)s1.substring(index + s2.length());
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncSubstringAfter
 * JD-Core Version:    0.7.0.1
 */