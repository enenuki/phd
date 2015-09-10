/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XBoolean;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class FuncContains
/* 10:   */   extends Function2Args
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 5084753781887919723L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:46 */     String s1 = this.m_arg0.execute(xctxt).str();
/* 18:47 */     String s2 = this.m_arg1.execute(xctxt).str();
/* 19:50 */     if ((s1.length() == 0) && (s2.length() == 0)) {
/* 20:51 */       return XBoolean.S_TRUE;
/* 21:   */     }
/* 22:53 */     int index = s1.indexOf(s2);
/* 23:   */     
/* 24:55 */     return index > -1 ? XBoolean.S_TRUE : XBoolean.S_FALSE;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncContains
 * JD-Core Version:    0.7.0.1
 */