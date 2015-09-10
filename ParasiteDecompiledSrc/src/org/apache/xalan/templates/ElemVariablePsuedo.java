/*  1:   */ package org.apache.xalan.templates;
/*  2:   */ 
/*  3:   */ import javax.xml.transform.TransformerException;
/*  4:   */ import org.apache.xalan.transformer.TransformerImpl;
/*  5:   */ import org.apache.xpath.VariableStack;
/*  6:   */ import org.apache.xpath.XPath;
/*  7:   */ import org.apache.xpath.XPathContext;
/*  8:   */ 
/*  9:   */ public class ElemVariablePsuedo
/* 10:   */   extends ElemVariable
/* 11:   */ {
/* 12:   */   static final long serialVersionUID = 692295692732588486L;
/* 13:   */   XUnresolvedVariableSimple m_lazyVar;
/* 14:   */   
/* 15:   */   public void setSelect(XPath v)
/* 16:   */   {
/* 17:45 */     super.setSelect(v);
/* 18:46 */     this.m_lazyVar = new XUnresolvedVariableSimple(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void execute(TransformerImpl transformer)
/* 22:   */     throws TransformerException
/* 23:   */   {
/* 24:64 */     transformer.getXPathContext().getVarStack().setLocalVariable(this.m_index, this.m_lazyVar);
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemVariablePsuedo
 * JD-Core Version:    0.7.0.1
 */