/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.apache.xpath.objects.XString;
/*  8:   */ 
/*  9:   */ public class FuncTranslate
/* 10:   */   extends Function3Args
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -1672834340026116482L;
/* 13:   */   
/* 14:   */   public XObject execute(XPathContext xctxt)
/* 15:   */     throws TransformerException
/* 16:   */   {
/* 17:46 */     String theFirstString = this.m_arg0.execute(xctxt).str();
/* 18:47 */     String theSecondString = this.m_arg1.execute(xctxt).str();
/* 19:48 */     String theThirdString = this.m_arg2.execute(xctxt).str();
/* 20:49 */     int theFirstStringLength = theFirstString.length();
/* 21:50 */     int theThirdStringLength = theThirdString.length();
/* 22:   */     
/* 23:   */ 
/* 24:   */ 
/* 25:54 */     StringBuffer sbuffer = new StringBuffer();
/* 26:56 */     for (int i = 0; i < theFirstStringLength; i++)
/* 27:   */     {
/* 28:58 */       char theCurrentChar = theFirstString.charAt(i);
/* 29:59 */       int theIndex = theSecondString.indexOf(theCurrentChar);
/* 30:61 */       if (theIndex < 0) {
/* 31:66 */         sbuffer.append(theCurrentChar);
/* 32:68 */       } else if (theIndex < theThirdStringLength) {
/* 33:73 */         sbuffer.append(theThirdString.charAt(theIndex));
/* 34:   */       }
/* 35:   */     }
/* 36:86 */     return new XString(sbuffer.toString());
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncTranslate
 * JD-Core Version:    0.7.0.1
 */