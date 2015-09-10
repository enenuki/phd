/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xpath.Expression;
/*  5:   */ import org.apache.xpath.XPath;
/*  6:   */ import org.apache.xpath.XPathContext;
/*  7:   */ import org.apache.xpath.objects.XObject;
/*  8:   */ 
/*  9:   */ public class XUnresolvedVariableSimple
/* 10:   */   extends XObject
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = -1224413807443958985L;
/* 13:   */   
/* 14:   */   public XUnresolvedVariableSimple(ElemVariable obj)
/* 15:   */   {
/* 16:40 */     super(obj);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public XObject execute(XPathContext xctxt)
/* 20:   */     throws TransformerException
/* 21:   */   {
/* 22:55 */     Expression expr = ((ElemVariable)this.m_obj).getSelect().getExpression();
/* 23:56 */     XObject xobj = expr.execute(xctxt);
/* 24:57 */     xobj.allowDetachToRelease(false);
/* 25:58 */     return xobj;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getType()
/* 29:   */   {
/* 30:68 */     return 600;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getTypeString()
/* 34:   */   {
/* 35:79 */     return "XUnresolvedVariableSimple (" + object().getClass().getName() + ")";
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.XUnresolvedVariableSimple
 * JD-Core Version:    0.7.0.1
 */