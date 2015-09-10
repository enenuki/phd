/*  1:   */ package org.apache.xpath.functions;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPathContext;
/*  6:   */ import org.apache.xpath.objects.XObject;
/*  7:   */ import org.apache.xpath.objects.XString;
/*  8:   */ import org.apache.xpath.res.XPATHMessages;
/*  9:   */ 
/* 10:   */ public class FuncConcat
/* 11:   */   extends FunctionMultiArgs
/* 12:   */ {
/* 13:   */   static final long serialVersionUID = 1737228885202314413L;
/* 14:   */   
/* 15:   */   public XObject execute(XPathContext xctxt)
/* 16:   */     throws TransformerException
/* 17:   */   {
/* 18:47 */     StringBuffer sb = new StringBuffer();
/* 19:   */     
/* 20:   */ 
/* 21:50 */     sb.append(this.m_arg0.execute(xctxt).str());
/* 22:51 */     sb.append(this.m_arg1.execute(xctxt).str());
/* 23:53 */     if (null != this.m_arg2) {
/* 24:54 */       sb.append(this.m_arg2.execute(xctxt).str());
/* 25:   */     }
/* 26:56 */     if (null != this.m_args) {
/* 27:58 */       for (int i = 0; i < this.m_args.length; i++) {
/* 28:60 */         sb.append(this.m_args[i].execute(xctxt).str());
/* 29:   */       }
/* 30:   */     }
/* 31:64 */     return new XString(sb.toString());
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void checkNumberArgs(int argNum)
/* 35:   */     throws WrongNumberArgsException
/* 36:   */   {
/* 37:77 */     if (argNum < 2) {
/* 38:78 */       reportWrongNumberArgs();
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected void reportWrongNumberArgs()
/* 43:   */     throws WrongNumberArgsException
/* 44:   */   {
/* 45:88 */     throw new WrongNumberArgsException(XPATHMessages.createXPATHMessage("gtone", null));
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.functions.FuncConcat
 * JD-Core Version:    0.7.0.1
 */